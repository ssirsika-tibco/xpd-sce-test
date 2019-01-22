/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

class InstallSampleWizard extends Wizard {

    private final Context context;

    public InstallSampleWizard(Context context) {
        this.context = context;
        setNeedsProgressMonitor(true);
    }

    @Override
    public boolean performFinish() {

        InstallSampleProjectOperation runnable =
                new InstallSampleProjectOperation(context);
        try {
            getContainer().run(true, true, runnable);
        } catch (InvocationTargetException e) {
            BrandingPlugin.logError(e);
            String msg = null;
            if (e.getCause() != null) {
                msg = e.getCause().getLocalizedMessage();
            }
            if (msg == null) {
                msg = e.getLocalizedMessage();
            }
            if (msg == null) {
                msg = ""; //$NON-NLS-1$
            }
            MessageDialog.openError(getShell(),
                    Messages.InstallSampleWizard_ErrorDialogTitle_label,
                    msg);
        } catch (InterruptedException e) {
            // Do nothing
        } catch (OperationCanceledException e) {
            // Do nothing
        }
        return true;
    }

    @Override
    public void addPages() {
        IntroPage introPage = new IntroPage();
        introPage.setContext(context);
        addPage(introPage);
    }

    class IntroPage extends WizardPage implements ContextualWizardPage {
        private Context context;

        public IntroPage() {
            super(Messages.InstallSampleWizard_IntroPage_label);
            setWindowTitle(Messages.InstallSampleWizard_WindowTitle);
            setDescription(Messages.InstallSampleWizard_Description);
            setTitle(Messages.InstallSampleWizard_Title);
            setImageDescriptor(BrandingPlugin
                    .getImageDescriptor("intro/css/graphics/tibco.gif"));
            setDescription(Messages.InstallSampleWizard_LongDescription);

        }

        @Override
        public void createControl(Composite parent) {
            Composite c = new Composite(parent, SWT.NULL);
            GridLayout layout = new GridLayout();
            layout.numColumns = 1;
            c.setLayout(layout);
            c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            Label l = new Label(c, SWT.NONE);
            String label = context.getProject().getLabel();
            l.setText(label);
            l.setFont(JFaceResources.getBannerFont());

            Group grp = new Group(c, SWT.SHADOW_IN);
            grp.setText(Messages.InstallSampleWizard_DescriptionGroup_label);
            grp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            GridLayout grpGridLayout = new GridLayout();
            grpGridLayout.numColumns = 1;
            grpGridLayout.marginHeight = 8;
            grpGridLayout.marginWidth = 25;
            grp.setLayout(grpGridLayout);

            Label desc = new Label(grp, SWT.WRAP);
            GridData gdDesc = new GridData(SWT.RIGHT, SWT.FILL, false, false);
            gdDesc.widthHint = 500;
            desc.setLayoutData(gdDesc);
            desc.setText(this.context.getProject().getDescription());

            setControl(c);
        }

        @Override
        public void setContext(Context context) {
            this.context = context;
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