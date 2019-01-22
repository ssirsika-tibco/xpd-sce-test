/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.internal.wc;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gmf.runtime.common.core.util.Trace;

import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.XpdResourcesDebugOptions;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Workspace change listener that monitors changes to project so that the
 * XpdProjectResourceFactories can be updated as required.
 * 
 * @author njpatel
 * 
 */
public class ResourceFactoriesChangeListener implements IResourceChangeListener {

    private final WorkingCopyFactoriesManager wcManager;

    /**
     * 
     */
    private final class IResourceDeltaVisitorImplementation implements
            IResourceDeltaVisitor {
        public boolean visit(IResourceDelta delta) throws CoreException {
            XpdResourcesPlugin resourcesPlugin =
                    XpdResourcesPlugin.getDefault();
            IResource ires = delta.getResource();

            // tracing
            if (trace) {
                Trace.trace(resourcesPlugin,
                        XpdResourcesDebugOptions.DEBUG,
                        "Delta for: " //$NON-NLS-1$
                                + ires);
            }

            if (ires instanceof IProject) {
                IProject project = (IProject) ires;

                synchronized (resourcesPlugin) {
                    if (wcManager.getProjectResourceFactories()
                            .containsKey(project)) {
                        // If the project was closed or removed then dispose the
                        // factory
                        if (delta.getKind() == IResourceDelta.REMOVED
                                || isProjectClosed(delta)) {
                            XpdProjectResourceFactory fact =
                                    wcManager.getProjectResourceFactories()
                                            .remove(project);

                            if (fact instanceof XpdProjectResourceFactoryImpl) {
                                ((XpdProjectResourceFactoryImpl) fact)
                                        .dispose();
                            }

                            // clean index for this project
                            XpdResourcesPlugin.getDefault().getIndexerService()
                                    .clean(project, null);

                            // tracing
                            if (trace) {
                                Trace.trace(resourcesPlugin,
                                        XpdResourcesDebugOptions.DEBUG,
                                        "Removing Factory: " //$NON-NLS-1$
                                                + fact);
                            }

                            if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {

                                IProject newProject =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot().getProject(delta
                                                        .getMovedToPath()
                                                        .lastSegment());

                                if (newProject != null) {
                                    /*
                                     * This is required to re-create a factory
                                     * for new project location
                                     */
                                    wcManager
                                            .getXpdProjectResourceFactory(newProject);

                                    // Index the new project
                                    XpdResourcesPlugin.getDefault()
                                            .getIndexerService()
                                            .clean(newProject, null);
                                    wcManager.revalidateProjectReferences();
                                }
                            }
                        } else if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
                            /*
                             * if the project description has changed -
                             * re-validate if all project references are still
                             * valid.
                             */
                            wcManager.revalidateProjectReferences();
                        }
                    }

                    /*
                     * If the project was opened then recreate factory, re-index
                     * and re-validate the references
                     */
                    if (isProjectOpened(delta)) {
                        // This will recreate the factory
                        wcManager.getXpdProjectResourceFactory(project);
                        // Update index and re-validate references
                        XpdResourcesPlugin.getDefault().getIndexerService()
                                .clean(project, null);
                        wcManager.revalidateProjectReferences();
                    }

                    /*
                     * Return true if this project is a non-XPD nature project
                     * (ignoring projects that start with '.') as we may need to
                     * loaded working copies for resources in this project (see
                     * below).
                     */
                    if (project.isAccessible()
                            && !project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                        return !project.getName().startsWith("."); //$NON-NLS-1$
                    }
                    return true;
                }
            } else if (ires instanceof IWorkspaceRoot) {
                return true;
            } else {
                /*
                 * If a file has been added to the workspace then try loading
                 * it's working copy. If it has one then this will cause the
                 * file to be indexed.
                 */
                IProject project = ires.getProject();
                if (project != null && project.isAccessible() && ires.exists()) {
                    /*
                     * Also checking for change to content as if the editor of
                     * the resource, eg wsdl file, is already open and the user
                     * exits and re-launches studio the editor will be restored.
                     * If subsequently the user changes the resource and saves
                     * it it will not have had an ADDED trigger so the indexer
                     * will not be updated with the new changes (unless the user
                     * clicks on the file in the project explorer and the wc
                     * property tester will cause the working copy to load).
                     */
                    if (ires instanceof IFile
                            && (delta.getKind() == IResourceDelta.ADDED || (delta
                                    .getKind() == IResourceDelta.CHANGED && (delta
                                    .getFlags() & IResourceDelta.CONTENT) > 0))) {
                        resourcesPlugin.getWorkingCopy(ires);
                    }

                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Check whether the given delta contains an opened project
     * 
     * @param delta
     *            resource delta
     * @return <code>true</code> if the project was opened, <code>false</code>
     *         otherwise.
     */
    private boolean isProjectOpened(IResourceDelta delta) {
        boolean opened = false;

        if (delta != null && delta.getResource() instanceof IProject) {
            opened =
                    delta.getKind() == IResourceDelta.CHANGED
                            && (delta.getFlags() & IResourceDelta.OPEN) != 0
                            && delta.getResource().isAccessible();
        }

        return opened;
    }

    /**
     * Check whether the given delta contains a closed project
     * 
     * @param delta
     *            resource delta
     * @return <code>true</code> if the project was closed, <code>false</code>
     *         otherwise.
     */
    private boolean isProjectClosed(IResourceDelta delta) {
        boolean closed = false;

        if (delta != null && delta.getResource() instanceof IProject) {
            closed =
                    delta.getKind() == IResourceDelta.CHANGED
                            && (delta.getFlags() & IResourceDelta.OPEN) != 0
                            && !delta.getResource().isAccessible();
        }

        return closed;
    }

    /**
     * @param wcManager
     */
    public ResourceFactoriesChangeListener(WorkingCopyFactoriesManager wcManager) {
        this.wcManager = wcManager;

    }

    public static final boolean trace =
            Trace.shouldTrace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.DEBUG);

    /*
     * @see
     * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org
     * .eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        IResourceDelta delta = event.getDelta();
        if (delta != null) {
            try {
                delta.accept(new IResourceDeltaVisitorImplementation());
            } catch (CoreException e) {
                // ignore
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
    }
}