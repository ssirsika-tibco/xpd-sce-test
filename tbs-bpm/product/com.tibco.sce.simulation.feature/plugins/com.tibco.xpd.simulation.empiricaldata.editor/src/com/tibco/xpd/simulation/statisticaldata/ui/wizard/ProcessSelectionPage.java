/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdl2.Process;


/**
 * Project, package and process selection page used by the New
 * Wizards. This extends <code>PackageSelectionPage</code>.
 * 
 * @see PackageSelectionPage
 * 
 * @author njpatel
 */
public class ProcessSelectionPage extends AbstractXpdWizardPage {
    
    private static final int NO_OF_COLUMNS = 3;
    private static final String BROWSE = "ProcessSelectionPage_browse"; //$NON-NLS-1$
    private static final String PROCESS = "ProcessSelectionPage_process"; //$NON-NLS-1$
    private static final String TITLE = "ProcessSelectionPage_title"; //$NON-NLS-1$
    private static final String DESCRIPTION = "ProcessSelectionPage_description"; //$NON-NLS-1$
    private Text txtProcess;
    private Button btnProcessBrowse;
    
    private Process selectedProcess;
    
    private Object initial;
    
    public ProcessSelectionPage() {
        super(EmpircalDataEditorPlugin.INSTANCE.getString(TITLE));
        // Set the title and message for this page
        setTitle(EmpircalDataEditorPlugin.INSTANCE.getString(TITLE));
        setDescription(EmpircalDataEditorPlugin.INSTANCE.getString(DESCRIPTION));
    }
    
    public void init(IStructuredSelection selection) {

        if (selection != null && selection.size() == 1) {
            initial = selection.getFirstElement();
        }
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage#getEContainer()
     */
    public EObject getEContainer() {
        //Return the selected process
        return selectedProcess;
    }

    public void createControl(Composite parent) {
        //Process selection
        Composite processContainer = new Composite(parent, SWT.NONE);
        processContainer.setLayout(new GridLayout(NO_OF_COLUMNS, false));
        processContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label processLabel = new Label(processContainer, SWT.NONE);
        GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_END);
        processLabel.setLayoutData(gData);
        processLabel.setText(EmpircalDataEditorPlugin.INSTANCE.getString(PROCESS));

        txtProcess = new Text(processContainer, SWT.BORDER);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txtProcess.setLayoutData(gData);
        txtProcess.setEditable(false);
        txtProcess.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });

        btnProcessBrowse = new Button(processContainer, SWT.NONE);
        btnProcessBrowse
                .setText(EmpircalDataEditorPlugin.INSTANCE.getString(BROWSE));
        btnProcessBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                browseForProcess();
            }
        });

        if (selectedProcess != null) {
            txtProcess.setText (selectedProcess.getName());
        }
        setControl(processContainer);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#validatePage()
     */
    protected boolean validatePage() {
        // Call the super version to validate the main part of this page
        boolean ret = selectedProcess != null;

        // If valid then clear error messages
        if (ret) {
            setErrorMessage(null);
        } else {
            setErrorMessage(Messages.ProcessSelectionPage_SpecifyProcess);
        }

        return ret;
    }
    
    /**
     * Display the process picker
     * 
     */
    protected void browseForProcess() {
        ProcessFilterPicker picker =
                new ProcessFilterPicker(getShell(), ProcessPickerType.PROCESS,
                        false);
        if (picker.open() == Dialog.OK) {
            // Update the selected process
            selectedProcess = (Process) picker.getFirstResult();

            if (selectedProcess != null) {
                txtProcess.setText(WorkingCopyUtil.getText(selectedProcess));
            } else {
                txtProcess.setText(""); //$NON-NLS-1$
            }
        }

    }
}
