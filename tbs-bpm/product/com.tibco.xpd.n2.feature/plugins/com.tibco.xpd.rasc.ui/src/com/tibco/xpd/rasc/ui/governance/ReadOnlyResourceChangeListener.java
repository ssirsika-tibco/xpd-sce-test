/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rasc.core.governance.GovernanceStateService;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;

/**
 * Listens for changes to read-only projects and shows a warning dialog.
 *
 * @author nwilson
 * @since 2 Aug 2019
 */
public class ReadOnlyResourceChangeListener implements IResourceChangeListener {

    private static final int RELEVANT_FLAGS = IResourceDelta.CONTENT;

    private static final int RELEVANT_KIND = IResourceDelta.ADDED | IResourceDelta.REMOVED;

    /**
     * Service for checking the project governance state.
     */
    private GovernanceStateService gss;

    /**
     * Service for governance state related UI utilities.
     */
    private GovernanceStateUIService gsus;

    /**
     * Constructor, initialise the governance state service.
     */
    public ReadOnlyResourceChangeListener() {
        gss = new GovernanceStateService();
        gsus = new GovernanceStateUIService();
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
                boolean shouldShowChangeWarning = false;
                Set<IProject> projects = new HashSet<>();
                for (IResourceDelta child : children) {
                    IResource resource = child.getResource();
                    if (resource != null) {
                        IProject project = resource.getProject();
                        try {
                            if (gss.isLockedForProduction(project)) {
                                if (hasSignificantChanges(child)) {
                                    logChanges(child);
                                    // Change to a locked project, show a
                                    // warning
                                    shouldShowChangeWarning = true;
                                    projects.add(project);
                                }
                            }
                        } catch (CoreException e) {
                            RascUiActivator.getLogger().error(e);
                        }
                    }
                }
                if (shouldShowChangeWarning) {
                    showChangeWarning(projects);
                }
            }
        }
    }

    /**
     * Log the change that triggered the warning.
     * 
     * @param delta
     *            The delta for the change.
     */
    private void logChanges(IResourceDelta delta) {
        IResource resource = delta.getResource();
        IProject project = resource.getProject();
        RascUiActivator.getLogger()
                .warn(String.format("Resource '%1$s' changed in locked project '%2$s'", resource, project)); //$NON-NLS-1$
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
        IResource resource = delta.getResource();
        // Ignore project OPEN events as it generates ADD events for all
        // contents
        if (!(resource instanceof IProject) || (delta.getFlags() & IResourceDelta.OPEN) == 0) {
            if (!resource.getName().startsWith(".")) { //$NON-NLS-1$
                if (delta.getAffectedChildren().length == 0) {
                    int flags = delta.getFlags();
                    int kind = delta.getKind();
                    boolean relevantFlags = (flags & RELEVANT_FLAGS) != 0;
                    boolean relevantKind = (kind & RELEVANT_KIND) != 0;
                    boolean derived = resource.isDerived(IResource.CHECK_ANCESTORS);
                    if ((relevantKind || relevantFlags) && !derived) {
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
            }
        }
        return changed;
    }

    /**
     * @param project
     *            The project to show a warning for.
     */
    private void showChangeWarning(Set<IProject> projects) {
        String title = Messages.ReadOnlyResourceChangeListener_ChangeDetectedTitle;
        StringBuilder projectNames = new StringBuilder();
        for (IProject project : projects) {
            if (projectNames.length() != 0) {
                projectNames.append(","); //$NON-NLS-1$
            }
            projectNames.append(project.getName());
        }
        String message = String.format(Messages.ReadOnlyResourceChangeListener_ChangeDetectedMessage, projectNames);
        String createNewDraftLabel = Messages.LifecycleActionProvider_CreateDraftMenuLabel;
        Display.getDefault().asyncExec(() -> {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            int result =
                    MessageDialog.open(MessageDialog.WARNING, shell, title, message, SWT.NONE, createNewDraftLabel);
            if (result == IStatus.OK) {
                Job job =
                        Job.createSystem(Messages.ReadOnlyResourceChangeListener_CreatingDraftJob, new ICoreRunnable() {

                            @Override
                            public void run(IProgressMonitor monitor) throws CoreException {
                                for (IProject project : projects) {
                                    try {
                                        gss.createNewDraft(project);
                                    } catch (IOException e) {
                                        RascUiActivator.getLogger()
                                                .error("Could not update version on project " + project.getName()); //$NON-NLS-1$
                                    }
                                }
                                Display.getDefault().asyncExec(() -> {
                                    gsus.refreshEditorLabels();
                                });
                            }
                        });
                job.schedule();
            }
        });
    }

}
