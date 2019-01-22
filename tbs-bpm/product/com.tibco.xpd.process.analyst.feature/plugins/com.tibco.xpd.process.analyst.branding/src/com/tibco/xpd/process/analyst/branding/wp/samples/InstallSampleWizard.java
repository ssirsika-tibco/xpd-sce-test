/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

import com.tibco.xpd.process.analyst.branding.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

class InstallSampleWizard extends Wizard {

    private final Context context;

    private final Logger log = LoggerFactory
            .createLogger(SampleProjectsViewer.PLUGIN_ID);

    public InstallSampleWizard(Context context) {
        this.context = context;
    }

    @Override
    public boolean performFinish() {

        InstallSampleProjectOperation runnable =
                new InstallSampleProjectOperation(context);
        try {
            getContainer().run(true, true, runnable);
        } catch (InvocationTargetException e) {
            log.error(e);
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (OperationCanceledException e) {
            return false;
        }
        return true;
    }

    @Override
    public void addPages() {
        IntroPage introPage = new IntroPage();
        introPage.setContext(context);
        addPage(introPage);
        // InitialSettingsPage initialSettingsPage=new InitialSettingsPage();
        // initialSettingsPage.setContext(context);
        // addPage(initialSettingsPage);

        // switch(context.getProject().getInstallationType()){
        // case ARCHIVE:
        // break;
        // default:
        // break;
        // }
    }

    class IntroPage extends WizardPage implements ContextualWizardPage {

        private Context context;

        public IntroPage() {
            super(Messages.InstallSampleWizard_IntroPage_label);
            setWindowTitle(Messages.InstallSampleWizard_title);
            setDescription(Messages.InstallSampleWizard_title);
            setTitle(Messages.InstallSampleWizard_title);
            setDescription(Messages.InstallSampleWizard_longdesc);
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
            Label l2 = new Label(c, SWT.NONE);
            String description2 = context.getProject().getDescription();
            l2.setText(description2);

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

    // class InitialSettingsPage extends WizardPage implements
    // ContextualWizardPage{
    //
    // private Context context;
    //
    // public InitialSettingsPage() {
    // super("InitialSettingsPage");
    // // super("pageName","title",titleImage);
    // }
    //
    // public void createControl(Composite parent) {
    // Composite c=new Composite(parent,SWT.NONE);
    //
    // Label l=new Label(c, SWT.NONE);
    // String suggestedProjectName = context.getProject().getProjectName();
    // l.setText(suggestedProjectName);
    // context.put("projectName",suggestedProjectName);
    //
    // setControl(c);
    // }
    //
    // public void setContext(Context context) {
    // this.context = context;
    // }
    // }

}