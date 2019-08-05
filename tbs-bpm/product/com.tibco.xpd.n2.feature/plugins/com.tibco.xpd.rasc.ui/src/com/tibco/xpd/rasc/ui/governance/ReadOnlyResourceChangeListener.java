/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;

/**
 * Listens for changes to read-only projects and shows a warning dialog.
 *
 * @author nwilson
 * @since 2 Aug 2019
 */
public class ReadOnlyResourceChangeListener implements IResourceChangeListener {

    /**
     * Service for checking the project governance state.
     */
    private GovernanceStateService gss;

    /**
     * Constructor, initialise the governance state service.
     */
    public ReadOnlyResourceChangeListener() {
        gss = new GovernanceStateService();
    }

    /**
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     *
     * @param event
     *            The change event.
     */
    @Override
    public void resourceChanged(IResourceChangeEvent event) {
        IResourceDelta delta = event.getDelta();
        if (delta != null) {
            IResourceDelta[] children = delta.getAffectedChildren();
            if (children != null) {
                for (IResourceDelta child : children) {
                    if (hasSignificantChanges(child)) {
                        IResource resource = child.getResource();
                        if (resource != null) {
                            IProject project = resource.getProject();
                            try {
                                if (gss.isLockedForProduction(project)) {
                                    // Change to a locked project, show a
                                    // warning
                                    showChangeWarning(project);
                                }
                            } catch (CoreException e) {
                                RascUiActivator.getLogger().error(e);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks a delta for any changes that we shouldn't allow on locked
     * projects.
     * 
     * @param delta
     *            The delta to check for changes.
     * @return true if there are any changes that shouldn't occur on locked
     *         projects.
     */
    private boolean hasSignificantChanges(IResourceDelta delta) {
        boolean changed = false;
        if (delta.getAffectedChildren().length == 0) {
            IResource resource = delta.getResource();
            boolean derived = resource.isDerived(IResource.CHECK_ANCESTORS);
            if (!derived && !resource.getName().startsWith(".")) { //$NON-NLS-1$
                changed = true;
            }
        } else {
            for (IResourceDelta child : delta.getAffectedChildren()) {
                changed = hasSignificantChanges(child);
                if (changed) {
                    break;
                }
            }
        }
        return changed;
    }

    /**
     * @param project
     *            The project to show a warning for.
     */
    private void showChangeWarning(IProject project) {
        String title = Messages.ReadOnlyResourceChangeListener_ChangeDetectedTitle;
        String message = String.format(
                Messages.ReadOnlyResourceChangeListener_ChangeDetectedMessage,
                project.getName());
        String createNewDraftLabel = Messages.LifecycleActionProvider_CreateDraftMenuLabel;
        String ignoreLabel = Messages.ReadOnlyResourceChangeListener_IgnoreChangesButton;
        Display.getDefault().asyncExec(() -> {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            int result = MessageDialog
                    .open(MessageDialog.WARNING, shell, title, message, SWT.NONE, createNewDraftLabel, ignoreLabel);
            if (result == IStatus.OK) {
                Job job = Job.createSystem(Messages.ReadOnlyResourceChangeListener_CreatingDraftJob, jobMonitor -> {
                    gss.createNewDraft(project);
                });
                job.schedule();
            }
        });
    }

}
