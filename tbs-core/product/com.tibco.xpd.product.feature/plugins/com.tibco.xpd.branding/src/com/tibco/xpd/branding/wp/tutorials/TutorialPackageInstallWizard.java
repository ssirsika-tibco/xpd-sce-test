/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

/**
 * 
 * 
 * 
 * @author rgreen
 * 
 */
class TutorialPackageInstallWizard extends Wizard {

    private final TutorialPackage project;

    private final String target;

    public TutorialPackageInstallWizard(TutorialPackage project, String target) {
        this.project = project;
        this.target = target;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        TutorialPackageInstallOperation runnable =
                new TutorialPackageInstallOperation(project, target);
        try {
            getContainer().run(true, true, runnable);
        } catch (InvocationTargetException e) {
            BrandingPlugin.logError(e);

            Throwable cause = e.getCause();
            IStatus status = null;
            if (cause instanceof CoreException) {
                status = ((CoreException) cause).getStatus();
            } else {
                status =
                        new Status(IStatus.ERROR, BrandingPlugin.PLUGIN_ID,
                                cause.getLocalizedMessage(), cause);
            }

            ErrorDialog.openError(getShell(),
                    "Error",
                    "Failed to import",
                    status);

            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (OperationCanceledException e) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        IntroPage introPage = new IntroPage(project, target);
        addPage(introPage);
    }

    class IntroPage extends WizardPage {

        private TutorialPackage project;

        private String target;

        public IntroPage(TutorialPackage project, String target) {
            super(Messages.TutorialPackageInstallWizard_IntroPage_label);
            setWindowTitle(Messages.TutorialPackageInstallWizard_WindowTitle);
            setDescription(Messages.TutorialPackageInstallWizard_Description);
            setTitle(Messages.TutorialPackageInstallWizard_Title);
            setDescription(Messages.TutorialPackageInstallWizard_LongDescription);

            this.project = project;
            this.target = target;

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt
         * .widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            Composite c = new Composite(parent, SWT.NULL);
            GridLayout layout = new GridLayout();
            layout.numColumns = 2;

            layout.horizontalSpacing = 10;

            c.setLayout(layout);
            c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            Label lblCategoryHeading = new Label(c, SWT.NONE);
            lblCategoryHeading
                    .setText(Messages.TutorialPackageInstallWizard_Category_label);

            Label lblCategory = new Label(c, SWT.NONE);
            TutorialCategory categoryById =
                    TutorialPackageManager.getINSTANCE()
                            .getCategoryById(project.getCategory());
            lblCategory.setText(categoryById.getCategoryLabel());

            Label lblTutorialHeading = new Label(c, SWT.NONE);
            lblTutorialHeading
                    .setText(Messages.TutorialPackageInstallWizard_Tutorial_label);

            Label lblTutorial = new Label(c, SWT.NONE);
            lblTutorial.setText(project.getLabel());

            Label lblTargetHeading = new Label(c, SWT.NONE);
            lblTargetHeading
                    .setText(Messages.TutorialPackageInstallWizard_ResourceType_label);

            Label lblTarget = new Label(c, SWT.NONE);

            if (target.equals(ITutorialConstants.InstallTarget.RESOURCES
                    .getValue())) {
                lblTarget
                        .setText(Messages.TutorialPackageInstallWizard_BaseResourcesLabel);

            } else if (target.equals(ITutorialConstants.InstallTarget.SOLUTION
                    .getValue())) {
                lblTarget
                        .setText(Messages.TutorialPackageInstallWizard_FullSolution_label);
            }

            setControl(c);
        }

        /**
         * Returns specific help page context ID.
         * 
         * @Note Current implementation returns <code>null</code>.
         * 
         * @return Help context id if available, otherwise <code>null</code>
         */
        protected String getHelpContextId() {
            return null;
        }

        /**
         * @see org.eclipse.jface.dialogs.DialogPage#performHelp() Extednig
         *      classes
         */
        @SuppressWarnings("restriction")
        @Override
        public void performHelp() {
            String contextId = getHelpContextId();
            if (contextId == null) {
                contextId = IWorkbenchHelpContextIds.MISSING;
            }

            PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);
        }

    }

}