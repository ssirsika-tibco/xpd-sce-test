/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.daa.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.tibco.xpd.daa.internal.util.DAANamingUtils;

/**
 * Abstract DAA project generator. Provides the generator's client API.
 * 
 * @author jarciuch
 * @since 15 Dec 2011
 */
public abstract class BaseProjectDAAGenerator {

    /**
     * This version of clean allows you to select whether to preserve the DAA
     * that has just been generated or not.
     * <p>
     * This can be used by things (like generate-DAA ++ Deploy to ensure that
     * the DAA remains to actually BE deployed afterwards.
     * 
     * @param project
     * @param preserveDAA
     * @param monitor
     * @throws CoreException
     */
    public void clean(final IProject project, final boolean preserveDAA,
            IProgressMonitor monitor) throws CoreException {

        final IFolder daaFolder = getModuleOutputFolder(project, false);
        if (daaFolder != null && daaFolder.isAccessible()) {
            /*
             * XPD-5638: Moved refresh into workspace operation so there is no
             * issue with resources not being refreshed.
             */
            /*
             * XPD-7573: Refresh was moved outside of the workspace job to avoid
             * resource tree lockups.
             */
            daaFolder.refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        monitor.beginTask(String
                                .format(Messages.BaseProjectDAAGenerator2_CleanDAAStagingFolder_description,
                                        project.getName()),
                                2);
                        /*
                         * XPD-7573: Local refresh from workspace cause
                         * intermittent problems with deletion of files (files
                         * are locked) which started to show after ~ V15 of 4.0.
                         * Refresh has been moved outside of this job
                         * 
                         * daaFolder.refreshLocal(IResource.DEPTH_INFINITE, new
                         * NullProgressMonitor());
                         */
                        if (monitor.isCanceled())
                            throw new OperationCanceledException();

                        monitor.worked(1);

                        final IResource[] resources = daaFolder.members();
                        if (resources != null && resources.length == 0)
                            return;
                        for (int i = 0; i < resources.length; i++) {
                            if (resources[i] != null
                                    && resources[i].isAccessible()) {

                                if (preserveDAA
                                        && DAANamingUtils.DAA_FILE_EXTENSION.equalsIgnoreCase(resources[i]
                                                .getFileExtension())) {
                                    continue;
                                }

                                boolean force = true;

                                if (force) {
                                    resources[i].delete(true,
                                            new NullProgressMonitor());
                                } else {
                                    resources[i].delete(false,
                                            new NullProgressMonitor());
                                }
                            }
                        }
                        if (monitor.isCanceled()) {
                            throw new OperationCanceledException();
                        }
                    } finally {
                        monitor.done();
                    }
                }
            },
                    ResourcesPlugin.getWorkspace().getRoot(),
                    IWorkspace.AVOID_UPDATE,
                    monitor);
        }
    }

    /**
     * @see com.tibco.xpd.daa.internal.BaseProjectDAAGenerator#clean(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    public void clean(IProject project, IProgressMonitor monitor)
            throws CoreException {
        clean(project, false, monitor);
    }

    /**
     * Get the DAA FIle that was generated into the staging folder (will only
     * function AFTER gernation has completed).
     * 
     * @param project
     * 
     * @return The DAA File if it exists.
     */
    public IFile getDAAFile(IProject project) {
        IFile daaFile = null;
        IFolder moduleOutputFolder = getModuleOutputFolder(project, false);

        if (moduleOutputFolder == null || !moduleOutputFolder.isAccessible()) {
            return daaFile;
        }

        try {
            IResource[] members = moduleOutputFolder.members();
            for (IResource eachRes : members) {
                if (!(eachRes instanceof IFile)) {
                    continue;
                }
                if (DAANamingUtils.DAA_FILE_EXTENSION.equalsIgnoreCase(eachRes
                        .getFileExtension())) {
                    daaFile = (IFile) eachRes;
                    break;
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return daaFile;
    }

    public abstract IFolder getModuleOutputFolder(IProject project,
            boolean create);

    protected abstract String getCompositeContributorContext();

    public abstract IStatus generateDAA(IProject project,
            final IProgressMonitor monitor, final boolean replaceWithTS);

}