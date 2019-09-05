/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * Menu action to lock projects for production.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class LockForProductionAction extends BaseSelectionListenerAction {

    private GovernanceStateService gss;

    private GovernanceStateUIService gsus;

    private List<IProject> projects;

    /**
     * Constructor for the action.
     * 
     * @param text
     *            The action label.
     * @param gss
     *            The governance state service.
     * @param projects
     *            The selected projects.
     */
    protected LockForProductionAction(String text, GovernanceStateService gss, GovernanceStateUIService gsus,
            List<IProject> projects) {
        super(text);
        this.gss = gss;
        this.gsus = gsus;
        this.projects = projects;
        setImageDescriptor(RascUiActivator.getImageDescriptor(RascUiConstants.ICON_LOCK_FOR_PRODUCTION));
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     *
     */
    @Override
    public void run() {
        IProgressMonitor monitor = new NullProgressMonitor();
        AtomicBoolean saved = new AtomicBoolean();
        // Check for unsaved changes
        Display.getDefault().syncExec(() -> {
            if (DeployUtil.saveAllDirtyResourcesInWS(monitor)) {
                saved.set(true);
            }
        });

        if (saved.get()) {
            // Check for validation markers
            IProject invalidProject = null;
            try {
                BuildSynchronizerUtil.waitForBuildsToFinish(monitor);
                for (IProject project : projects) {
                    try {
                        int severity = project.findMaxProblemSeverity(null, true, IResource.DEPTH_INFINITE);
                        if (severity == IMarker.SEVERITY_ERROR) {
                            invalidProject = project;
                            break;
                        }
                    } catch (CoreException e) {
                        RascUiActivator.getLogger().error("Error checking markers on project " + project.getName()); //$NON-NLS-1$
                    }
                }
            } catch (InterruptedException ie) {
                RascUiActivator.getLogger().error("Build interrupted"); //$NON-NLS-1$
            }

            // Lock projects
            if (invalidProject == null) {
                for (IProject project : projects) {
                    try {
                        gss.lockForProduction(project);
                        gsus.refreshEditorLabels();
                    } catch (CoreException e) {
                        RascUiActivator.getLogger().error("Could not lock project " + project.getName()); //$NON-NLS-1$
                    }
                }
            } else {
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.LockForProductionAction_invalidProjectTtitle,
                        String.format(
                                Messages.LockForProductionAction_invalidProjectMessage,
                                invalidProject.getName()));
            }
        } else {
            RascUiActivator.getLogger().error("Failed to save files"); //$NON-NLS-1$
        }
    }

}
