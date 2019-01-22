/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessGroup;
import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project, package and process (optional) selection page used by the New
 * Wizards. Extends <code>PackageSelectionPage</code>
 * 
 * @see PackageSelectionPage
 * 
 * @author njpatel
 */
public class PackageOrProcessSelectionPage extends PackageSelectionPage {

    // SID MR 40840 - Removed special "isCorrelationData" from the
    // PackageOrProcessSelection class (MR 39259). The correct fix for
    // MR39259 is to use the ProcessElementSelectionPage class for
    // correlation data fields so that the process MUST be selected.

    private static final int NO_OF_COLUMNS = 3;

    private Text txtProcess;

    private Button btnProcessBrowse;

    private Button chkProcess;

    protected Process selectedProcess = null;

    // For Test-Instrumentation - this name will be set on the Page Control
    // after it is created.
    private String instrumentationName = ""; //$NON-NLS-1$

    /**
     * Project, package and process (optional) selection page used by the New
     * Wizards.
     */
    public PackageOrProcessSelectionPage(String instrumentationName) {
        super();

        this.instrumentationName = instrumentationName;

        // Set the title and message for this page
        setTitle(Messages.PackageOrProcessSelectionPage_TITLE);
        setDescription(Messages.PackageOrProcessSelectionPage_DESC);
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        // After control is created then we can store the wizard name (for
        // Test-Instrumentation use) in the control's data.
        getControl().setData("name", instrumentationName); //$NON-NLS-1$

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#init
     * (org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IStructuredSelection selection) {

        super.init(selection);

        if (selection != null && !selection.isEmpty()) {
            Object selectedElement = selection.getFirstElement();

            if (selectedElement instanceof AbstractAssetGroup
                    || selectedElement instanceof EObject) {
                // Process asset group / EObject selected so should be able to
                // get process if any

                if (selectedElement instanceof AbstractAssetGroup) {
                    AbstractAssetGroup group =
                            (AbstractAssetGroup) selectedElement;

                    // If the parent of the group is a Process then set
                    // selectedProcess
                    if (group.getParent() instanceof Process)
                        selectedProcess = (Process) group.getParent();
                } else {
                    // If the selected item is a Process then set
                    // selectedProcess
                    if (selectedElement instanceof Process
                            && XpdlFileContentPropertyTester
                                    .isXpdlFileContent((Process) selectedElement)) {
                        selectedProcess = (Process) selectedElement;
                    } else {
                        // This must be the data field / participant object
                        // so it's container should be the Process
                        EObject eo = (EObject) selectedElement;

                        if (eo.eContainer() instanceof Process) {
                            selectedProcess = (Process) eo.eContainer();
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the EObject container of this artifact. This may be a
     * <code>Package</code> or <code>Process</code>
     * 
     * @return <li><code>Package</code> - If user selected to create artifact in
     *         package. <li><code>Process</code> - If user selected to cerate
     *         artifact in process.
     */
    @Override
    public EObject getEContainer() {
        // It is possible that we get called before page controls are created.
        // If so we should return container from the data that has been set
        // rather than controls.
        if (chkProcess != null && !chkProcess.isDisposed()) {
            boolean toCreateInProcess = chkProcess.getSelection();

            if (toCreateInProcess) {
                // Return the process
                return selectedProcess;
            }
        } else if (selectedProcess != null) {
            return selectedProcess;
        }
        return super.getEContainer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#validatePage
     * ()
     */
    @Override
    protected boolean validatePage() {
        // Call the super version to validate the main part of this page
        boolean ret = super.validatePage();

        // Validate the process if selected
        if (ret && chkProcess.getSelection()) {
            String xpdlProcessName = txtProcess.getText();

            if (xpdlProcessName == null || xpdlProcessName.length() < 1) {
                setErrorMessage(Messages.PackageOrProcessSelectionPage_2);
                ret = false;
            } else {
                // Try to get the selected process
                selectedProcess = getProcess(packageFile, xpdlProcessName);
                if (selectedProcess == null) {
                    String msg =
                            MessageFormat
                                    .format(Messages.PackageOrProcessSelectionPage_3,
                                            new Object[] { xpdlProcessName });
                    setErrorMessage(msg);
                    return false;
                }
            }
        }

        // If valid then clear error messages
        if (ret)
            setErrorMessage(null);

        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#
     * createOptionSelection(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createOptionSelection(final Composite parent) {
        // Process selection
        // Composite processContainer = new Composite(parent, SWT.NULL);
        Group processContainer = new Group(parent, SWT.NULL);
        processContainer.setLayout(new GridLayout(NO_OF_COLUMNS, false));
        processContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        processContainer.setText(Messages.PackageOrProcessSelectionPage_4);

        chkProcess = new Button(processContainer, SWT.CHECK);
        GridData gridData = new GridData();
        gridData.horizontalSpan = NO_OF_COLUMNS;
        chkProcess.setLayoutData(gridData);
        chkProcess.setText(Messages.PackageOrProcessSelectionPage_5);
        chkProcess.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                checkButtonSelected(e);
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkButtonSelected(e);
            }
        });

        createLabel(processContainer,
                Messages.PackageOrProcessSelectionPage_6,
                30);

        txtProcess = createText(processContainer);
        txtProcess.setEditable(false);

        txtProcess.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });

        btnProcessBrowse = new Button(processContainer, SWT.NONE);
        btnProcessBrowse.setText(Messages.PackageOrProcessSelectionPage_7);
        btnProcessBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                browseForProcess();
            }
        });

        String tempProcessName =
                selectedProcess != null ? Xpdl2ModelUtil
                        .getDisplayNameOrName(selectedProcess) : ""; //$NON-NLS-1$

        if (tempProcessName != null && tempProcessName.length() > 0) {
            txtProcess.setText(tempProcessName);
            chkProcess.setSelection(true);
            txtProcess.setEnabled(true);
            btnProcessBrowse.setEnabled(true);
        } else {
            txtProcess.setEnabled(false);
            btnProcessBrowse.setEnabled(false);
        }
    }

    /**
     * Handle the selection of the process check box
     * 
     * @param e
     */
    private void checkButtonSelected(SelectionEvent e) {
        boolean btnSelected = chkProcess.getSelection();
        txtProcess.setEnabled(btnSelected);
        btnProcessBrowse.setEnabled(btnSelected);
        setPageComplete(validatePage());
    }

    /**
     * Display the process picker
     * 
     */
    protected void browseForProcess() {
        if (packageFile != null) {
            WorkingCopy wc = getWorkingCopy(packageFile);

            if (wc != null) {
                ProcessGroup group = new ProcessGroup(wc.getRootElement());

                if (group != null) {
                    ElementListSelectionDialog dialog =
                            new ElementListSelectionDialog(getShell(),
                                    new BpmContentLabelProvider());
                    dialog.setTitle(Messages.PackageOrProcessSelectionPage_9);
                    dialog.setMessage(Messages.PackageOrProcessSelectionPage_10);
                    dialog.setElements(group.getChildren().toArray());
                    dialog.setHelpAvailable(false);

                    if (selectedProcess != null) {
                        dialog.setInitialSelections(new Object[] { selectedProcess });
                    }

                    if (dialog.open() == Dialog.OK) {
                        // Update the selected process
                        selectedProcess = (Process) dialog.getFirstResult();

                        if (selectedProcess != null) {
                            // MR 39259 - begin
                            // txtProcess.setText(WorkingCopyUtil
                            // .getText(selectedProcess));
                            txtProcess.setText(Xpdl2ModelUtil
                                    .getDisplayNameOrName(selectedProcess));
                            // MR 39259 - end
                        } else {
                            txtProcess.setText(""); //$NON-NLS-1$
                        }
                    }

                } else {
                    MessageDialog.openError(getShell(),
                            Messages.PackageOrProcessSelectionPage_12,
                            Messages.PackageOrProcessSelectionPage_13);
                }
            }
        }

    }

    /**
     * Get the process with the given name
     * 
     * @param resource
     * @param xpdlProcessName
     * 
     * @return <code>Process</code> with the given process name, if not found
     *         then <strong>null</strong> will be returned.
     */
    private Process getProcess(IResource resource, String xpdlProcessName) {
        Process process = null;

        if (resource != null && xpdlProcessName != null) {
            WorkingCopy wc = getWorkingCopy(resource);

            if (wc != null) {
                // Get the process group of the Package
                ProcessGroup pg = new ProcessGroup(wc.getRootElement());
                List processes = pg.getChildren();
                pg.dispose();

                for (Iterator iter = processes.iterator(); iter.hasNext()
                        && process == null;) {
                    Process element = (Process) iter.next();

                    if (element != null
                            && xpdlProcessName.equals(Xpdl2ModelUtil
                                    .getDisplayNameOrName(element))) {
                        process = element;
                    }
                }
            }
        }

        return process;
    }

    /**
     * 
     * @param modifyListener
     */
    public void addProcessOrInterfaceModifyListeners(
            ModifyListener modifyListener) {
        if (txtProcess != null) {
            txtProcess.addModifyListener(modifyListener);
        }
    }

}
