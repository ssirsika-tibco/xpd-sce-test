/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * Action to refresh the workspace (to sync workspace with the file-system).
 * 
 */
public class RefreshWorkspaceAction extends RegisterCommandAction {

    /**
     * Action to sync the workspace with the file system
     * 
     * @param window
     */
    public RefreshWorkspaceAction(IWorkbenchWindow window) {
        super(window, Messages.RefreshWorkspaceAction_refresh_action);
        setToolTipText(Messages.RefreshWorkspaceAction_refresh_action_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.REFRESH
                .getPath()));
        registerCommand("com.tibco.xpd.rcp.command.refreshWorkspace"); //$NON-NLS-1$

        setEnabled(false);
        RCPResourceManager.addOpenListener(new IOpenResourceListener() {

            @Override
            public void opened(IRCPContainer resource) {
                if (resource != null && resource.getProjectResources() != null
                        && !resource.getProjectResources().isEmpty()) {
                    setEnabled(true);

                    resource.addChangeListener(new RCPResourceChangeListener() {

                        @Override
                        public void resourceChanged(IRCPResource resource,
                                RCPResourceChangeEvent event) {
                            if (event.eventType == RCPResourceEventType.DISPOSED) {
                                setEnabled(false);
                            }
                        }
                    });
                } else {
                    setEnabled(false);
                }
            }

        });
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        scheduleWorkspaceRefresh();
    }

    /**
     * Schedule a workspace refresh job.
     */
    public static void scheduleWorkspaceRefresh() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (workspace != null) {
            Job job = new Job(Messages.ProjectViewer_refreshProject_job_label) {

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    if (workspace != null) {
                        try {
                            workspace.getRoot()
                                    .refreshLocal(IResource.DEPTH_INFINITE,
                                            monitor);
                        } catch (CoreException e) {
                            return e.getStatus();
                        }
                    }
                    return Status.OK_STATUS;
                }
            };
            job.setUser(true);
            job.setRule(workspace.getRuleFactory().refreshRule(workspace
                    .getRoot()));
            job.schedule();
        }
    }
}