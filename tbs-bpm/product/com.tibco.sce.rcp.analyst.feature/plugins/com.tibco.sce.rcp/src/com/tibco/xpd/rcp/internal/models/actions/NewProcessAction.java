/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewProcessInterfaceWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewServiceProcessInterfaceWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessServiceWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewCaseServiceWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewServiceProcessWizard;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action to create a new {@link Process}, {@link ProcessInterface} or PageFlow
 * process.
 * 
 * @author njpatel
 * 
 */
public class NewProcessAction extends ModelAction {

    private final IFile xpdlFile;

    /**
     * @param window
     * @param label
     * @param image
     * @param xpdlFile
     */
    public NewProcessAction(IWorkbenchWindow window, String label,
            String image, IFile xpdlFile) {
        super(window, label, image);
        this.xpdlFile = xpdlFile;
    }

    @Override
    public void run() {
        NewProcessWizard wizard = new NewProcessWizard(xpdlFile);
        new WizardDialog(getWorkbenchWindow().getShell(), wizard).open();
    }

    @Override
    public boolean isNewAction() {
        return true;
    }

    /**
     * Wizard that will allow the user to select between creating a Business
     * Process, Process Interface or Pageflow Process.
     */
    private class NewProcessWizard extends Wizard {

        private final IFile xpdlFile;

        private ProcessTypeSelectionPage page;

        public NewProcessWizard(IFile xpdlFile) {
            this.xpdlFile = xpdlFile;
            setWindowTitle(Messages.NewProcessAction_title);
            setNeedsProgressMonitor(true);
            setForcePreviousAndNextButtons(true);
        }

        @Override
        public void addPages() {
            page = new ProcessTypeSelectionPage(xpdlFile);
            addPage(page);
        }

        @Override
        public boolean performFinish() {
            IWizardNode node = page.getSelectedNode();
            /*
             * Execute finish of the wizard if it hasn't already been executed -
             * it will already be executed if the user selected the next page -
             * which will be the first page of the selected wizard node
             */
            if (node != null && !node.isContentCreated()
                    && node.getWizard() != null && node.getWizard().canFinish()) {
                return node.getWizard().performFinish();
            }
            return true;
        }
    }

    /**
     * Page to select the type of Process.
     * 
     * @author njpatel
     * 
     */
    private class ProcessTypeSelectionPage extends WizardSelectionPage {

        private Button businessProcessBtn;

        private Button pageFlowProcessBtn;

        private Button bizServiceBtn;

        private Button caseServiceBtn;

        private Button processInterfaceBtn;

        private Button serviceProcessInterfaceBtn;

        private Button serviceProcessBtn;

        private final NewBusinessProcessWizard businessProcessWizard;

        private final NewPageflowProcessWizard pageFlowProcessWizard;

        private final NewBusinessServiceWizard bizServWizard;

        private final NewCaseServiceWizard caseServiceWizard;

        private final NewProcessInterfaceWizard processInterfaceWizard;

        private final NewServiceProcessWizard serviceProcessWizard;

        private final NewServiceProcessInterfaceWizard serviceProcessInterfaceWizard;

        protected ProcessTypeSelectionPage(IFile xpdlFile) {

            super("processType"); //$NON-NLS-1$
            setTitle(Messages.NewProcessAction_title);
            setDescription(Messages.NewProcessAction_description);

            businessProcessWizard = new NewBusinessProcessWizard();
            businessProcessWizard.setIsRCP(true);
            businessProcessWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            pageFlowProcessWizard = new NewPageflowProcessWizard();
            pageFlowProcessWizard.setIsRCP(true);
            pageFlowProcessWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            bizServWizard = new NewBusinessServiceWizard();
            bizServWizard.setIsRCP(true);
            bizServWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            caseServiceWizard = new NewCaseServiceWizard();
            caseServiceWizard.setIsRCP(true);
            caseServiceWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            processInterfaceWizard = new NewProcessInterfaceWizard();
            processInterfaceWizard.setIsRCP(true);
            processInterfaceWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            serviceProcessWizard = new NewServiceProcessWizard();
            serviceProcessWizard.setIsRCP(true);
            serviceProcessWizard.init(getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(xpdlFile));

            serviceProcessInterfaceWizard =
                    new NewServiceProcessInterfaceWizard();
            serviceProcessInterfaceWizard.setIsRCP(true);
            serviceProcessInterfaceWizard.init(getWorkbenchWindow()
                    .getWorkbench(), new StructuredSelection(xpdlFile));
            setPageComplete(false);
        }

        @Override
        public void dispose() {

            businessProcessWizard.dispose();
            pageFlowProcessWizard.dispose();
            bizServWizard.dispose();
            caseServiceWizard.dispose();
            processInterfaceWizard.dispose();
            serviceProcessInterfaceWizard.dispose();
            serviceProcessWizard.dispose();
            super.dispose();
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());

            Group grp = new Group(root, SWT.NONE);
            grp.setText(Messages.NewProcessAction_types_group_label);
            grp.setLayout(new GridLayout());
            grp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

            businessProcessBtn = new Button(grp, SWT.RADIO);
            businessProcessBtn
                    .setText(Messages.NewProcessAction_businessProcess_checkbox);
            businessProcessBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry()
                    .get(RCPImages.BUSINESS_PROCESS.getPath()));
            ProcessRadioListener listener = new ProcessRadioListener();
            businessProcessBtn.addSelectionListener(listener);

            pageFlowProcessBtn = new Button(grp, SWT.RADIO);
            pageFlowProcessBtn
                    .setText(Messages.NewProcessAction_pageFlow_checkbox);
            pageFlowProcessBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry()
                    .get(RCPImages.PAGEFLOW_PROCESS.getPath()));
            pageFlowProcessBtn.addSelectionListener(listener);

            bizServiceBtn = new Button(grp, SWT.RADIO);
            bizServiceBtn
                    .setText(Messages.NewProcessAction_business_service_checkbox);
            bizServiceBtn.setImage(RCPActivator.getDefault().getImageRegistry()
                    .get(RCPImages.BUSINESS_SERVICE.getPath()));
            bizServiceBtn.addSelectionListener(listener);

            caseServiceBtn = new Button(grp, SWT.RADIO);
            caseServiceBtn
                    .setText(Messages.NewProcessAction_case_service_checkbox);
            caseServiceBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry().get(RCPImages.CASE_SERVICE.getPath()));
            caseServiceBtn.addSelectionListener(listener);

            processInterfaceBtn = new Button(grp, SWT.RADIO);
            processInterfaceBtn
                    .setText(Messages.NewProcessAction_interface_checkbox);
            processInterfaceBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry()
                    .get(RCPImages.PROCESS_INTERFACE.getPath()));
            processInterfaceBtn.addSelectionListener(listener);

            serviceProcessBtn = new Button(grp, SWT.RADIO);
            serviceProcessBtn
                    .setText(Messages.NewProcessAction_service_process_checkbox);
            serviceProcessBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry()
                    .get(RCPImages.SERVICE_PROCESS.getPath()));
            serviceProcessBtn.addSelectionListener(listener);

            serviceProcessInterfaceBtn = new Button(grp, SWT.RADIO);
            serviceProcessInterfaceBtn
                    .setText(Messages.NewProcessAction_service_process_interface_checkbox);
            serviceProcessInterfaceBtn.setImage(RCPActivator.getDefault()
                    .getImageRegistry()
                    .get(RCPImages.SERVICE_PROCESS_INTERFACE.getPath()));
            serviceProcessInterfaceBtn.addSelectionListener(listener);

            // Set the business process as the default selection
            businessProcessBtn.setSelection(true);
            setNode(businessProcessWizard);
            setControl(root);
        }

        private class ProcessRadioListener extends SelectionAdapter {
            @Override
            public void widgetSelected(SelectionEvent e) {

                if (e.widget instanceof Button
                        && ((Button) e.widget).getSelection()) {

                    if (businessProcessBtn == e.widget) {

                        setNode(businessProcessWizard);
                    } else if (pageFlowProcessBtn != null
                            && pageFlowProcessBtn == e.widget) {

                        setNode(pageFlowProcessWizard);
                    } else if (processInterfaceBtn == e.widget) {

                        setNode(processInterfaceWizard);
                    } else if (null != bizServiceBtn
                            && bizServiceBtn == e.widget) {

                        setNode(bizServWizard);
                    } else if (null != caseServiceBtn
                            && caseServiceBtn == e.widget) {

                        setNode(caseServiceWizard);
                    } else if (null != serviceProcessBtn
                            && serviceProcessBtn == e.widget) {

                        setNode(serviceProcessWizard);
                    } else if (null != serviceProcessInterfaceBtn
                            && serviceProcessInterfaceBtn == e.widget) {

                        setNode(serviceProcessInterfaceWizard);
                    }
                }
            }
        }

        /**
         * Set the selected node.
         * 
         * @param wizard
         */
        private void setNode(INewWizard wizard) {
            wizard.setContainer(getContainer());
            setSelectedNode(new ProcessWizardNode(wizard));
        }
    }

    /**
     * Process selection node.
     * 
     * @author njpatel
     * 
     */
    private class ProcessWizardNode implements IWizardNode {

        private final IWizard wizard;

        private ProcessWizardNode(IWizard wizard) {
            this.wizard = wizard;
        }

        @Override
        public void dispose() {
            // Do nothing
        }

        @Override
        public Point getExtent() {
            return new Point(-1, -1);
        }

        @Override
        public IWizard getWizard() {
            return wizard;
        }

        @Override
        public boolean isContentCreated() {
            return wizard != null && wizard.getPageCount() > 0;
        }

    }
}
