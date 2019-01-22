/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.wizards;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;

/**
 * Util class used by the XSD and WSDL export wizards to determine whether
 * correct preference is set for export.
 * 
 * @author njpatel
 * 
 */
public final class ExportBOMUtil {

    private static final BOMValidatorActivator activator =
            BOMValidatorActivator.getDefault();

    private static final IBOMValidationPreferenceManager preferenceManager =
            activator.getPreferenceManager();

    /**
     * Check if auto-build is enabled in the workspace.
     * 
     * @return <code>true</code> if auto-build is enabled, <code>false</code>
     *         otherwise.
     */
    public static boolean isAutoBuildEnabled() {
        IWorkspaceDescription description =
                ResourcesPlugin.getWorkspace().getDescription();
        if (description != null) {
            return description.isAutoBuilding();
        }
        return true;
    }

    /**
     * Get all projects, from the collection of projects provided, that do not
     * have the given validation destination enabled.
     * 
     * @param projects
     *            projects to check
     * @param destination
     *            validation destination
     * @return collection of projects that do NOT have the given destination
     *         enabled. An empty collection will be returned validation is
     *         enabled on all the projects.
     */
    public static Collection<IProject> getProjectsWithoutValidationEnabled(
            Collection<IProject> projects, ValidationDestination destination) {
        Set<IProject> ret = new HashSet<IProject>();
        if (projects != null && !projects.isEmpty() && destination != null) {
            // Check if validation is enabled for each project
            for (IProject project : projects) {
                if (!activator.isValidationDestinationEnabled(project,
                        destination.getDestinationId())) {
                    ret.add(project);
                }
            }
        }
        return ret;
    }

    /**
     * Ask the user whether to apply validation preference. If the user agrees
     * the project/workspace preference will be applied and the appropriate
     * builder run.
     * 
     * @param shell
     * @param destination
     *            validation destination
     * @param projects
     *            projects that do not have the validation preference set
     * @return <code>true</code> if the user agreed to apply preference,
     *         <code>false</code> otherwise.
     * @throws CoreException
     */
    public static boolean askAndApplyPreference(Shell shell,
            ValidationDestination destination,
            final Collection<IProject> projects, IProgressMonitor monitor)
            throws CoreException {
        final Boolean[] ret = new Boolean[] { false };
        final SetPreferenceDialog dlg = new SetPreferenceDialog(shell);
        // Ask user if preference can be applied
        shell.getDisplay().syncExec(new Runnable() {
            public void run() {
                ret[0] = dlg.open() == Dialog.OK;
            }
        });

        if (ret[0]) {
            // User asked to apply preference
            if (dlg.applyToProject()) {
                monitor.beginTask(Messages.ExportBOMUtil_building_monitor_label, projects.size());
                for (IProject project : projects) {
                    monitor.subTask(project.getName());
                    // Set preference and build projects
                    preferenceManager.setPropertiesSetting(project,
                            destination,
                            true);
                    project.build(IncrementalProjectBuilder.FULL_BUILD,
                            new SubProgressMonitor(monitor, 1));
                }
            } else {
                // Apply to entire workspace
                preferenceManager.setPreferenceSetting(destination, true);
                // Build workspace
                ResourcesPlugin.getWorkspace()
                        .build(IncrementalProjectBuilder.FULL_BUILD, monitor);
            }
            return true;
        }
        return false;
    }

    /**
     * The set preference dialog that will ask the user if the preference can be
     * set. It will also provide an option to set preference at workspace level.
     * 
     * @author njpatel
     * 
     */
    private static class SetPreferenceDialog extends MessageDialog {

        private Button workspaceOpt;

        private boolean applyToProject = true;

        public SetPreferenceDialog(Shell parentShell) {
            super(
                    parentShell,
                    Messages.ExportBOMUtil_XSDValidationPreference_dialog_title,
                    null,
                    Messages.ExportBOMUtil_XSDValidationPreference_dialog_message,
                    QUESTION, new String[] { Messages.ExportBOMUtil_Enable_button, Messages.ExportBOMUtil_Cancel_button }, 1);
        }

        public boolean applyToProject() {
            return applyToProject;
        }

        @Override
        protected Control createCustomArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());

            workspaceOpt = new Button(root, SWT.CHECK);
            workspaceOpt.setText(Messages.ExportBOMUtil_EnableXSDValidation_checkBox_label);
            workspaceOpt.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    applyToProject = !workspaceOpt.getSelection();
                }
            });

            return root;
        }

        @Override
        protected void buttonPressed(int buttonId) {
            switch (buttonId) {
            case 0:
                okPressed();
                break;
            case 1:
                cancelPressed();
                break;
            }
        }
    }
}
