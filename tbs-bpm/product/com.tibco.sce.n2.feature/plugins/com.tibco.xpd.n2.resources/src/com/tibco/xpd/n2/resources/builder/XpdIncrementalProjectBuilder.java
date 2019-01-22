/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.resources.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.AbstractIncrementalProjectBuilder;

/**
 * Base class of all builders which N2 builders should use
 * 
 * @author Tim Stephenson, Jan Arciuchiewicz, Miguel Torres
 */
public abstract class XpdIncrementalProjectBuilder extends
        AbstractIncrementalProjectBuilder {

    public static final Logger LOG = BundleActivator.getDefault().getLogger();

    protected Map<String, List<IFolder>> specialFolders;

    @Override
    protected IProject[] doBuild(int kind, Map<?, ?> options,
            IProgressMonitor monitor) throws CoreException {

        try {
            specialFolders =
                    getSpecialFolders(getProject(), getSpecialFolderKinds());
            if (!hasN2Destination(getProject())) {
                cleanGeneratedArtifacts(monitor, getProject());
            } else {
                IResourceDelta delta = getDelta(getProject());
                if (kind == FULL_BUILD || isFullBuildRequired(delta)) {
                    fullbuild(monitor);
                } else {
                    if (delta.getAffectedChildren().length > 0) {
                        incrementalBuild(delta, monitor);
                    }

                    /*
                     * Also process deltas from referenced projects, if any.
                     */
                    Set<IFile> filesToProcess = new HashSet<IFile>();
                    for (IProject refProject : getProject()
                            .getReferencedProjects()) {
                        if (refProject.isAccessible()
                                && refProject
                                        .hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                            delta = getDelta(refProject);
                            if (delta != null
                                    && delta.getAffectedChildren().length > 0) {
                                filesToProcess
                                        .addAll(getLocalFilesAffectedFromRefProject(delta));
                            }
                        }
                    }

                    for (IFile file : filesToProcess) {
                        checkCancel(monitor, true);
                        handleResourceChanged(monitor, file);
                    }
                }
            }
        } catch (Throwable th) {
            LOG.error(th);
        }
        // Return all referenced projects
        return getProject().getReferencedProjects();
    }

    /**
     * Run full build.
     * 
     * @param monitor
     */
    protected void fullbuild(final IProgressMonitor monitor) {
        for (List<IFolder> foldersOfKind : specialFolders.values()) {
            for (IFolder folder : foldersOfKind) {
                try {
                    folder.accept(new IResourceVisitor() {
                        @Override
                        public boolean visit(IResource resource)
                                throws CoreException {
                            checkCancel(monitor, true);
                            return handleResourceChanged(monitor, resource);
                        }

                    });
                } catch (CoreException e) {
                    LOG.error(e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Returns list of eclipse folders which are marked as special packages
     * folder for provided project.
     * 
     * @param project
     *            the eclipse project.
     * @return list of special packages folders for the project.
     */
    protected static Map<String, List<IFolder>> getSpecialFolders(
            IProject project, List<String> specialFolderKinds) {
        Map<String, List<IFolder>> specialIFolders =
                new LinkedHashMap<String, List<IFolder>>();
        SpecialFolders specialFolders =
                XpdResourcesPlugin.getDefault().getProjectConfig(project)
                        .getSpecialFolders();
        if (specialFolders != null) {
            for (String kind : specialFolderKinds) {
                specialIFolders.put(kind,
                        specialFolders.getEclipseIFoldersOfKind(kind));
            }
            return specialIFolders;
        }
        return Collections.emptyMap();
    }

    protected static boolean inSpecialFolder(IResource resource,
            Map<String, List<IFolder>> specialFolders) {
        for (List<IFolder> foldersOfKind : specialFolders.values()) {
            for (IFolder folder : foldersOfKind) {
                if (folder.getFullPath().isPrefixOf(resource.getFullPath())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static List<IFolder> getAllSpecialFolders(
            Map<String, List<IFolder>> specialFolders) {
        if (specialFolders.size() == 0) {
            return Collections.emptyList();
        } else if (specialFolders.size() == 1) {
            return specialFolders.values().iterator().next();
        }
        List<IFolder> allFolders = new ArrayList<IFolder>();
        for (List<IFolder> foldersOfKind : specialFolders.values()) {
            allFolders.addAll(foldersOfKind);
        }
        return allFolders;
    }

    protected static boolean inSpecialFolder(IResource resource,
            List<IFolder> specialFolders) {
        for (IFolder folder : specialFolders) {
            if (folder.getFullPath().isPrefixOf(resource.getFullPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param delta
     * @param monitor
     * @throws CoreException
     */
    protected void incrementalBuild(IResourceDelta delta,
            final IProgressMonitor monitor) throws CoreException {
        delta.accept(new IResourceDeltaVisitor() {

            @Override
            public boolean visit(IResourceDelta delta) throws CoreException {
                if (delta != null) {
                    int kind = delta.getKind();
                    IResource resource = delta.getResource();
                    if (resource != null) {
                        if (kind == IResourceDelta.REMOVED
                                && inSpecialFolder(resource, specialFolders)) {
                            handleResourceRemoved(monitor, resource);
                        } else if ((kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED)
                                && inSpecialFolder(resource, specialFolders)) {
                            checkCancel(monitor, true);
                            return handleResourceChanged(monitor, resource);
                        } else if (resource instanceof IProject) {
                            checkCancel(monitor, true);
                            return handleResourceChanged(monitor, resource);
                        }
                    }
                }
                return true;
            }
        });

    }

    /**
     * Get the set of files in the local project that may be affected by the
     * change to a resource in the referenced project.
     * 
     * @param delta
     * @return
     * @throws CoreException
     */
    private Set<IFile> getLocalFilesAffectedFromRefProject(IResourceDelta delta)
            throws CoreException {
        final Set<IFile> affectedFiles = new HashSet<IFile>();
        final IProject thisProject = getProject();

        delta.accept(new IResourceDeltaVisitor() {

            @Override
            public boolean visit(IResourceDelta d) throws CoreException {
                IResource resource = d.getResource();

                if (resource instanceof IFile) {
                    for (IResource affectedRes : WorkingCopyUtil
                            .getAffectedResources(resource)) {

                        if (thisProject.equals(affectedRes.getProject())) {
                            /*
                             * only interested in a file that has been added,
                             * removed or its contents changed.
                             */
                            if (d.getKind() == IResourceDelta.ADDED
                                    || d.getKind() == IResourceDelta.REMOVED
                                    || (d.getKind() == IResourceDelta.CHANGED && (d
                                            .getFlags() & IResourceDelta.CONTENT) != 0)) {
                                affectedFiles.add((IFile) affectedRes);
                            }
                        }
                    }
                    return false;
                }

                return true;
            }
        });
        return affectedFiles;
    }

    protected abstract List<String> getSpecialFolderKinds();

    protected abstract boolean handleResourceChanged(IProgressMonitor monitor,
            IResource resource) throws CoreException;

    protected abstract void handleResourceRemoved(IProgressMonitor monitor,
            IResource resource) throws CoreException;

    protected void cleanGeneratedArtifacts(IProgressMonitor monitor,
            IProject project) {
        // Default implementation does nothing
    }

    protected boolean isModelFile(IResource resource, String fileExtension,
            String specialFolderKind) {
        if (resource instanceof IFile
                && fileExtension.equalsIgnoreCase(resource.getFileExtension())
                && inSpecialFolder(resource,
                        specialFolders.get(specialFolderKind))) {
            return true;
        }
        return false;
    }

    protected void removeResource(IResource resource, IProgressMonitor monitor)
            throws CoreException {
        if (resource != null && resource.isAccessible()) {
            resource.delete(true, monitor);
        }
    }

    private boolean hasN2Destination(IProject project) {
        return GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                N2Utils.N2_GLOBAL_DESTINATION_ID);
    }

}
