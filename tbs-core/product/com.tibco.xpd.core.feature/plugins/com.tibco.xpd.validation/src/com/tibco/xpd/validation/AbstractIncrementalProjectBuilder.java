/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.validation;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.internal.Workbench;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Extension of the {@link IncrementalProjectBuilder} that only runs build if
 * the project is up-to-date and doesn't need migrating.
 * <p>
 * This class also provides method to check if a full build is required as a
 * result of project description or config change. See
 * {@link #isFullBuildRequired(IResourceDelta)}.
 * </p>
 * 
 * @author njpatel
 * @since 3.5
 * 
 */
public abstract class AbstractIncrementalProjectBuilder extends
        IncrementalProjectBuilder {

    @Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {

        if (shouldRun() && isProjectCurrent(getProject())) {
            return doBuild(kind, args, monitor);
        }
        return null;
    }

    /**
     * Run the build. See {@link #build(int, Map, IProgressMonitor)} for more
     * details on parameters.
     * <p>
     * Use {@link #isFullBuildRequired(IResourceDelta)} to check if a full build
     * is required. This would be the case, for example, when the project config
     * has been changed.
     * </p>
     * 
     * @see #isFullBuildRequired(IResourceDelta)
     * @see #isConfigUpdated(IResourceDelta)
     * 
     * @param kind
     * @param args
     * @param monitor
     * @return the list of projects for which this builder would like deltas the
     *         next time it is run or <code>null</code> if none
     */
    protected abstract IProject[] doBuild(int kind, Map<?, ?> args,
            IProgressMonitor monitor) throws CoreException;

    /**
     * Check if the project is up-to-date and doesn't need migrating.
     * 
     * @param project
     * 
     * @return <code>true</code> if the project is current, <code>false</code>
     *         if it needs migrating.
     * @throws CoreException
     */
    protected boolean isProjectCurrent(IProject project) throws CoreException {
        return !ProjectUtil.doesProjectHaveMigrationMarker(project);
    }

    /**
     * Check if this builder should run. Default implementation returns
     * <code>true</code> if this is not the RCP application. Subclasses may
     * override
     * 
     * @return
     * @since 3.5.2
     */
    protected boolean shouldRun() {
        // XPD-4044: Builder should not run when the workbench is in closing
        // process.
        boolean isClosing = false;
        if (!(XpdResourcesPlugin.isRCP() || XpdResourcesPlugin
                .isInHeadlessMode())) {
            if (Workbench.getInstance() != null) {

                isClosing = Workbench.getInstance().isClosing();
                return (!isClosing);
            }
        }
        return true;
    }

    /**
     * Check if a full build is required. This will check the following:
     * <ol>
     * <li>Check if the .config file has been updated</li>
     * <li>Check if the project description has changed</li>
     * <li>If the project has referenced projects then check if any of them had
     * their description or config file changed.</li>
     * </ol>
     * If any of the above has occurred then a full build may be required.
     * 
     * @see #isConfigUpdated(IResourceDelta)
     * 
     * @param delta
     *            current resource delta
     * @return <code>true</code> if full build is required, <code>false</code>
     *         otherwise.
     */
    protected boolean isFullBuildRequired(IResourceDelta delta) {

        boolean fullBuild = false;

        if (delta != null) {
            fullBuild = isConfigUpdated(delta);

            if (!fullBuild) {
                /*
                 * If the project description has changed of this project or any
                 * referenced project then run full build
                 */
                fullBuild = isPossibleExplicitProjectDescriptionChange(delta);

                // Check if description of referenced projects has changed
                if (!fullBuild) {
                    Set<IProject> referencedProjects =
                            ProjectUtil
                                    .getReferencedProjectsHierarchy(getProject(),
                                            null);

                    if (referencedProjects != null) {
                        for (IProject proj : referencedProjects) {
                            if (proj != getProject()) {
                                IResourceDelta refDelta = getDelta(proj);
                                // Check if the project description has changed
                                // or the .config file has changed
                                if (fullBuild =
                                        refDelta != null
                                                && (isPossibleExplicitProjectDescriptionChange(refDelta) || isConfigUpdated(refDelta))) {
                                    /*
                                     * Reference project's description or config
                                     * has changed
                                     */
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return fullBuild;
    }

    /**
     * Check if the project description has changed. This wil check if the delta
     * contains a DESCRIPTION flag and if the delta's affected children only
     * contains the ".project" file.
     * 
     * @param delta
     * @return true if project description has possibly changed,
     *         <code>false</code> otherwise.
     * @since 3.5
     */
    private boolean isPossibleExplicitProjectDescriptionChange(
            IResourceDelta delta) {
        /*
         * If the user has changed the project description then we expect the
         * delta flag will have the DESCRIPTION set and also the delta's
         * affected children should only contain the .project file. It seems
         * that when a file is imported into a project it is causing the
         * DESCRIPTION flag to be set (suspect that the project is being
         * "touched" which would set this flag).
         */
        if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
            IResourceDelta[] children = delta.getAffectedChildren();
            if (children.length == 1) {
                IResource resource = children[0].getResource();
                if (resource instanceof IFile
                        && ".project".equals(resource.getName())) { //$NON-NLS-1$
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if the config file has been updated. This can happen when special
     * folders have changed/created/removed. It will also check if the config
     * file has been deleted.
     * 
     * @param delta
     *            changed resource delta.
     * @return <code>true</code> if the config file is an affected child of the
     *         resource delta, <code>false</code> otherwise.
     */
    protected boolean isConfigUpdated(IResourceDelta delta) {
        boolean updated = false;

        if (delta != null) {
            IResourceDelta[] children = delta.getAffectedChildren();

            for (IResourceDelta child : children) {
                if (child.getResource().getProjectRelativePath().toString()
                        .equals(XpdResourcesPlugin.PROJECTCONFIGFILE)) {
                    // Only return true if contents of the file have changed or
                    // the file has been removed
                    updated =
                            child.getKind() == IResourceDelta.REMOVED
                                    || (child.getKind() == IResourceDelta.CHANGED && (child
                                            .getFlags() & IResourceDelta.CONTENT) != 0);
                    break;
                }
            }
        }

        return updated;
    }

    /**
     * Cancel current processing.
     * 
     * @param monitor
     *            Progress monitor
     * @param forgetBuildSate
     *            <code>true</code> if build state should be forgotten
     */
    protected void checkCancel(IProgressMonitor monitor, boolean forgetBuildSate) {
        IncrementalProjectBuilder builder = forgetBuildSate ? this : null;
        ProjectUtil.checkBuildCancel(monitor, builder);
    }
}
