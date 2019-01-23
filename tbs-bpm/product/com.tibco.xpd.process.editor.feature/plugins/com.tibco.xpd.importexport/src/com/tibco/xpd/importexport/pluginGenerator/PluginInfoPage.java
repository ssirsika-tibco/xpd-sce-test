/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.IPluginDataProvider;
import com.tibco.xpd.importexport.pluginGenerator.NewPluginGeneratorWizard.WizardType;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * The plug-in information collection page of the generate custom plug-in
 * wizard.
 * 
 * @author njpatel
 */
public class PluginInfoPage extends AbstractXpdWizardPage implements IPluginDataProvider,
        ModifyListener, SelectionListener {

    private Text txtPluginID = null;

    private String pluginId = ""; //$NON-NLS-1$

    private Text txtPluginVersion = null;

    private String pluginVersion = ""; //$NON-NLS-1$

    private Text txtPluginName = null;

    private String pluginName = ""; //$NON-NLS-1$

    private Button chkDefaultName = null;

    private Text txtPluginVendor = null;

    private String pluginVendor = ""; //$NON-NLS-1$

    private String szUserDefinedPluginName = null;

    private WizardType wizardType;

    /**
     * Create the plug-in Information wizard page
     * 
     * @param pageName
     *            (String)
     * @param title
     *            (String)
     * @param titleImage
     *            (ImageDescriptor)
     */
    public PluginInfoPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(pageName);
        setTitle(title);
        setImageDescriptor(titleImage);
    }

    /**
     * Get the wizard type selected, import or export.
     * 
     * @return
     */
    public WizardType getWizardType() {
        return wizardType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Create the plugin info control
        Composite cntrl = createPluginInfoControl(container);
        cntrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cntrl = createWizardTypeControl(container);
        cntrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        setControl(container);

        // Set page to incomplete
        setMessage(Messages.PluginPage_DefaultMsg);

        doValidatePage();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
     */
    public void modifyText(ModifyEvent evt) {
        // If default name not selected then keep a copy of the name the user
        // enters
        if (evt.getSource() == txtPluginName && !chkDefaultName.getSelection()) {
            szUserDefinedPluginName = getPluginName();
        } else if (evt.getSource() == txtPluginID
                && chkDefaultName.getSelection()) {
            setDefaultName();
        }

        doValidatePage();
    }

    /**
     * Create the Plug-in information collection group
     * 
     * @param parent
     *            (Composite)
     */
    private Composite createPluginInfoControl(Composite parent) {

        Group grpInfo = new Group(parent, SWT.NONE);
        grpInfo.setText(Messages.PluginPage_PluginGrpTitle);

        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.horizontalSpacing = 10;
        gridLayout.marginTop = 5;
        gridLayout.marginBottom = 5;
        gridLayout.marginLeft = 5;
        gridLayout.marginRight = 5;
        grpInfo.setLayout(gridLayout);

        // Plug-in ID
        createLabel(grpInfo, Messages.PluginPage_IdLabel);
        txtPluginID = createText(grpInfo);
        txtPluginID.setText(Messages.PluginPage_DefaultImportId_notrans+1);
        txtPluginID.addModifyListener(this);

        // Plug-in Version
        createLabel(grpInfo, Messages.PluginPage_VersionLabel);
        txtPluginVersion = createText(grpInfo);
        txtPluginVersion.setText(Messages.PluginPage_DefaultPluginVersion);
        txtPluginVersion.addModifyListener(this);

        // Plug-in vendor
        createLabel(grpInfo, Messages.PluginPage_ProviderLabel);
        txtPluginVendor = createText(grpInfo);
        txtPluginVendor.setText(Messages.PluginPage_DefaultProvider);
        txtPluginVendor.addModifyListener(this);

        // Add empty cell
        createLabel(grpInfo, ""); //$NON-NLS-1$

        // Default name checkbox
        chkDefaultName = new Button(grpInfo, SWT.CHECK);
        chkDefaultName.setSelection(true);
        chkDefaultName.setText(Messages.PluginPage_DefaultPluginNameChkBox);
        chkDefaultName.setLayoutData(new GridData());
        chkDefaultName.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                // Enable the name text control if this checkbox is unticked
                if (chkDefaultName.getSelection()) {
                    // Disable the name text control
                    txtPluginName.setEnabled(false);
                    // Set the default name
                    setDefaultName();
                } else {
                    // If the user had defined a name previously then re-set it
                    if (szUserDefinedPluginName != null) {
                        txtPluginName.setText(szUserDefinedPluginName);
                    }
                    // Enable the name text control
                    txtPluginName.setEnabled(true);
                }

                super.widgetSelected(e);
            }
        });

        // Plug-in Name
        createLabel(grpInfo, Messages.PluginPage_NameLabel);
        txtPluginName = createText(grpInfo);
        txtPluginName.setEnabled(false);
        // Set the default name
        setDefaultName();
        txtPluginName.addModifyListener(this);

        // Set focus to the first item on this page
        txtPluginID.setFocus();
             
        return grpInfo;
    }

    /**
     * Create the wizard type selection control.
     * 
     * @param parent
     * @return
     */
    private Composite createWizardTypeControl(Composite parent) {
        Group grpType = new Group(parent, SWT.NONE);
        grpType.setText(Messages.PluginInfoPage_wizardTypeGroup_label);

        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.horizontalSpacing = 10;
        gridLayout.marginTop = 5;
        gridLayout.marginBottom = 5;
        gridLayout.marginLeft = 5;
        gridLayout.marginRight = 5;
        grpType.setLayout(gridLayout);

        createLabel(grpType, Messages.PluginInfoPage_type_label);

        Composite btnContainer = new Composite(grpType, SWT.NONE);
        btnContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnContainer.setLayout(new RowLayout(SWT.VERTICAL));
        WizardType[] wizardTypes = WizardType.values();
        Button btns[] = new Button[wizardTypes.length];

        for (int idx = 0; idx < btns.length; idx++) {
            btns[idx] = new Button(btnContainer, SWT.RADIO);
            btns[idx].setText(wizardTypes[idx].getName());
            btns[idx].setData(wizardTypes[idx]);
            btns[idx].addSelectionListener(this);

            // Set the first item as selected
            if (idx == 0) {
                btns[idx].setSelection(true);
                wizardType = wizardTypes[idx];
                btns[idx].addSelectionListener(new SelectionAdapter() {                
                    @Override
                    public void widgetSelected(SelectionEvent e) {                     
                        super.widgetSelected(e);                        
                        createUniquePlugin(Messages.PluginPage_DefaultImportId_notrans, 1);
                    }                
                });
            }else{
                btns[idx].addSelectionListener(new SelectionAdapter() {                
                    @Override
                    public void widgetSelected(SelectionEvent e) {                     
                        super.widgetSelected(e);
                        createUniquePlugin(Messages.PluginPage_DefaultExportId_notrans, 1);
                    }                
                });
            }
        }

        return grpType;
    }
    
    /**
     * Keeps incrementing pluginid until the plugin does not exist! 
     * @param pluginID
     * @param index
     * @return
     */
    private void createUniquePlugin(String pluginID, int index){
        boolean idExists = false;        

        // Get all plugins
        Bundle[] allBundles = ImportExportPlugin.getDefault()
                .getPluginContext().getBundles();

        // Find a bundle with the matching ID - if any.        
        for (int x = 0; x < allBundles.length && !idExists; x++) {
            if (allBundles[x].getSymbolicName().equalsIgnoreCase(
                    (pluginID+index))) {
                idExists = true;
            }
        }

        if (idExists) {
           createUniquePlugin(pluginID, (index + 1));
        }else{
            txtPluginID.setText(pluginID+index);
        }
    }

    /**
     * Validate the page. Make sure all required data is available before
     * progressing
     */
    private void doValidatePage() {
        String szErrMsg = null;

        setPageComplete(false);

        pluginId = txtPluginID.getText().trim();
        pluginName = txtPluginName.getText().trim();
        pluginVersion = txtPluginVersion.getText().trim();
        pluginVendor = txtPluginVendor.getText().trim();

        // If no data is provided then just set message
        if (pluginId.length() == 0 && pluginVersion.length() == 0
                && pluginName.length() == 0) {
            setMessage(Messages.PluginPage_DefaultMsg);
            szErrMsg = null;
        } else {
            // Check the plug-in ID input
            // Plug-in ID should not be empty, should not start or end with a
            // non alpanumeric character,
            // and should not have non alphanumeric characters in it except the
            // fullstop
            if (pluginId.length() == 0) {
                szErrMsg = Messages.PluginPage_IDIsEmpty;
            } else if (!pluginId.matches("(\\w+\\.?)*\\w")) { //$NON-NLS-1$
                szErrMsg = Messages.PluginPage_InvalidID;
            } else {
                szErrMsg = null;
            }

            // Check the plug-in version input
            // The version number should be of the format: major.minor.service
            if (szErrMsg == null) {
                if (pluginVersion.length() == 0) {
                    szErrMsg = Messages.PluginPage_VersionNotSet;
                } else if (!pluginVersion.matches("\\d+(\\.\\d+){0,3}")) { //$NON-NLS-1$
                    szErrMsg = Messages.PluginPage_InvalidVersion;
                } else {
                    szErrMsg = null;
                }
            }

            // Check the plug-in Name input (if not set to default name)
            // The plug-in name should not be empty
            if (szErrMsg == null && !chkDefaultName.getSelection()) {
                if (pluginName.length() == 0) {
                    szErrMsg = Messages.PluginPage_NameNotSet;
                } else {
                    szErrMsg = null;
                }
            }
        }

        // Set error message
        setErrorMessage(szErrMsg);

        if (szErrMsg == null) {
            setPageComplete(true);
        }
    }

    /**
     * Set the default plug-in name (based on the plug-in ID)
     */
    private void setDefaultName() {
        String id = txtPluginID.getText().trim();
        if (id != null && id.length() > 0) {

            int nIdx = id.lastIndexOf('.');
            String szName = null;
            String szCapitalizedName = null;

            // If the . is at the end then don't update the name
            if (nIdx < id.length() - 1) {

                if (nIdx >= 0)
                    szName = id.substring(nIdx + 1);
                else
                    szName = id;

                if (Character.isLetter(szName.charAt(0))) {
                    // Capitalize the first character
                    szCapitalizedName = Character.toUpperCase(szName.charAt(0))
                            + szName.substring(1);
                } else {
                    // No need to capitalize the first character
                    szCapitalizedName = szName;
                }

                txtPluginName.setText(String.format(
                        Messages.PluginPage_DefaultPluginName,
                        szCapitalizedName));
            }
        } else {
            txtPluginName.setText(Messages.PluginPage_Plugin);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginDataProvider#getPluginId()
     */
    public String getPluginId() {
        return pluginId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginDataProvider#getPluginVersion()
     */
    public String getPluginVersion() {
        return pluginVersion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginDataProvider#getPluginName()
     */
    public String getPluginName() {
        return pluginName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginDataProvider#getPluginVendor()
     */
    public String getPluginVendor() {
        return pluginVendor;
    }

    /**
     * Create a <code>Label</code> control in the <i>parent</i>
     * <code>Composite</code> with the given <i>label</i>.
     * 
     * @param parent
     * @param label
     */
    private void createLabel(Composite parent, String label) {
        Label lbl = new Label(parent, SWT.NONE);
        lbl.setText(label);
    }

    /**
     * Create a <code>Text</code> control in the given <i>parent</i>
     * <code>Composite</code>. Set it's width hint to a fixed value.
     * 
     * @param parent
     * @return
     */
    private Text createText(Composite parent) {
        Text txt = new Text(parent, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txt.setLayoutData(gData);
        return txt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() instanceof Button) {
            Object data = ((Button) e.getSource()).getData();

            if (data instanceof WizardType) {
                // Update the current selected wizard type
                wizardType = (WizardType) data;
            }
        }
    }

}
