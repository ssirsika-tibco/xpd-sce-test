/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Build to do a simple clean of the Openspace export DAA staging folder
 * 
 * @author aallway
 * @since 19 Feb 2013
 */
public class OpenspaceCleanStagingFolderBuilder extends
        IncrementalProjectBuilder {

    public OpenspaceCleanStagingFolderBuilder() {
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param kind
     * @param args
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected IProject[] build(int kind, Map<String, String> args,
            IProgressMonitor monitor) throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        final IProject project = getProject();
        final IFolder stagingFolder =
                OpenspaceGadgetNature.getOpenspaceDAAStagingFolder(project,
                        false);
        if (stagingFolder != null && stagingFolder.isAccessible()) {

            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        monitor.beginTask(String
                                .format(Messages.OpenspaceProjectDAAGenerator_CleaningStagingFolder_status_message,
                                        project.getName()),
                                2);
                        stagingFolder.refreshLocal(IResource.DEPTH_INFINITE,
                                new NullProgressMonitor());

                        if (monitor.isCanceled())
                            throw new OperationCanceledException();

                        monitor.worked(1);

                        final IResource[] resources = stagingFolder.members();
                        if (resources != null && resources.length == 0)
                            return;
                        for (int i = 0; i < resources.length; i++) {
                            if (resources[i] != null
                                    && resources[i].isAccessible()) {
                                resources[i].delete(true,
                                        new NullProgressMonitor());
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
}
