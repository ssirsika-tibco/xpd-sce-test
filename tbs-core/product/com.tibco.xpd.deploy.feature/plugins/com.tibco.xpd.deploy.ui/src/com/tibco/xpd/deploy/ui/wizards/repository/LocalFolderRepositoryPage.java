/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.repository;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.BaseDeployWizardPage;
import com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPageExt;

/**
 * The wizard page with configuration parameters for local (or mapped to local)
 * folder.
 * <p>
 * <i>Created: 29 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class LocalFolderRepositoryPage extends BaseDeployWizardPage implements
        Listener, RepositoryConfigWizardPageExt {

    public void performHelp() {
        //PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    private static final String PARAMETER_INFO_DATA = "ParameterInfo"; //$NON-NLS-1$

    private static final String INQUIRY_URL_PARAMETER = "inquiryUrl"; //$NON-NLS-1$

    private static final String PUBLISHING_FOLDER_PARAMETER = "publishingFolder"; //$NON-NLS-1$

    private Group parametersParent;

    private Text folderText;

    private Button linkedCheckbox;

    private Text serverUrlText;

    private Button browse;

    private RepositoryConfig config;

    private boolean initialized = false;
    
    private boolean bIsDirty = false;

    public LocalFolderRepositoryPage() {
        super("RepositoryParametersPage"); //$NON-NLS-1$
        setTitle(Messages.LocalFolderRepositoryPage_Page_title);
        setDescription(Messages.LocalFolderRepositoryPage_Page_description);
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, getTitle());
        composite.setLayout(gridLayout);        

        parametersParent = new Group(composite, SWT.NULL);
        parametersParent
                .setText(Messages.LocalFolderRepositoryPage_RepositoryParametersGroup_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(serverGroupGridData);

        setPageComplete(false);
        setControl(composite);
        createParametersControls();
    }

    private void createParametersControls() {

        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        parametersParent.setLayout(paramGroupLayout);

        ConfigParameter publishFolderParam = getParamByKey(config,
                PUBLISHING_FOLDER_PARAMETER);
        createLabel(parametersParent, publishFolderParam
                .getConfigParameterInfo().getLabel());
        folderText = createText(parametersParent, ""); //$NON-NLS-1$
        folderText.addListener(SWT.Modify, this);
        browse = new Button(parametersParent, SWT.PUSH);
        browse.setText(Messages.LocalFolderRepositoryPage_Browse_label);
        browse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handelBrowseFolder();
            }
        });

        linkedCheckbox = createCheckbox(parametersParent, false);
        linkedCheckbox
                .setText(Messages.LocalFolderRepositoryPage_InquiryUrlDifferent_label);
        GridData inqDiffLabelLayautData = new GridData();
        inqDiffLabelLayautData.horizontalSpan = 3;
        linkedCheckbox.setLayoutData(inqDiffLabelLayautData);
        linkedCheckbox.addListener(SWT.Selection, this);

        ConfigParameter inquiryUrl = getParamByKey(config,
                INQUIRY_URL_PARAMETER);
        createLabel(parametersParent, inquiryUrl.getConfigParameterInfo()
                .getLabel());
        serverUrlText = createText(parametersParent, ""); //$NON-NLS-1$
        serverUrlText.setEnabled(linkedCheckbox.getSelection());
        serverUrlText.addListener(SWT.Modify, this);

        // parametersParent.layout();
        validatePage(true, false);
        setMessage(null);
        setErrorMessage(null);
    }

    @SuppressWarnings("unchecked")
    private ConfigParameter getParamByKey(RepositoryConfig config, String key) {
        for (ConfigParameter param : (List<ConfigParameter>) config
                .getConfigParameters()) {
            if (key.equals(param.getKey())) {
                return param;
            }
        }
        return null;
    }

    // TODO implement validation
    private boolean validatePage(boolean setFocus, boolean showMessage) {
        setMessage(null);
        setErrorMessage(null);

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setPageComplete(true);
        return true;
    }

    public void handleEvent(Event event) {
        bIsDirty = true;
        if (event.type == SWT.Modify && event.widget == folderText) {
            if (!linkedCheckbox.getSelection()) {
                URL url = null;
                try {
                    File file = new File(folderText.getText());
                    url = file.toURL();
                } catch (MalformedURLException e) {
                    // DO NOTHING
                }
                if (url != null) {
                    serverUrlText.setText(url.toString());
                }
            }
            validatePage(false, true);
        } else if (event.type == SWT.Modify && event.widget == serverUrlText) {
            validatePage(false, true);
        } else if (event.type == SWT.Selection
                && event.widget == linkedCheckbox) {
            if (linkedCheckbox.getSelection()) {
                serverUrlText.setEnabled(true);
            } else {
                serverUrlText.setEnabled(false);
                URL url = null;
                try {
                    File file = new File(folderText.getText());
                    url = file.toURL();
                } catch (MalformedURLException e) {
                    // DO NOTHING
                }
                if (url != null) {
                    serverUrlText.setText(url.toString());
                }
            }
            validatePage(false, true);
        }
    }

    @SuppressWarnings("unchecked")
    public ServerConfig getServerConfig() {
        DeployFactory factory = DeployFactory.eINSTANCE;
        ServerConfig serverConfig = factory.createServerConfig();
        List configParams = serverConfig.getConfigParameters();
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO_DATA) != null) {
                ConfigParameter configParam = factory.createConfigParameter();
                ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                        .getData(PARAMETER_INFO_DATA);
                String value = null;
                if (control instanceof Text) {
                    value = ((Text) control).getText();
                }
                if (control instanceof Button) {
                    value = String.valueOf(((Button) control).getSelection());
                }
                configParam.setKey(paramInfo.getKey());
                configParam.setValue(value);
                configParam.setConfigParameterInfo(paramInfo);
                configParams.add(configParam);
            }
        }
        return serverConfig;
    }

    protected void handelBrowseFolder() {
        DirectoryDialog dialog = new DirectoryDialog(getContainer().getShell(),
                SWT.OPEN);
        dialog
                .setMessage(Messages.LocalFolderRepositoryPage_FolderSelectionDialog_label);
        dialog
                .setText(Messages.LocalFolderRepositoryPage_FolderSelectionText_summary);
        dialog.setFilterPath(folderText.getText().trim());
        String selectedDirectoryName = dialog.open();
        if (selectedDirectoryName != null) {
            folderText.setText(selectedDirectoryName);
        }

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!initialized) {
            initialize();
            initialized = true;
        }
    }

    /**
     * Initialises fields.
     */
    private void initialize() {
        ConfigParameter publishFolderParam = getParamByKey(config,
                PUBLISHING_FOLDER_PARAMETER);
        folderText.setText(publishFolderParam.getValue());
        ConfigParameter inquiryUrl = getParamByKey(config,
                INQUIRY_URL_PARAMETER);
        serverUrlText.setText(inquiryUrl.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage#setRepositoryConfig(com.tibco.xpd.deploy.model.RepositoryType,
     *      com.tibco.xpd.deploy.model.RepositoryConfig)
     */
    public void init(RepositoryType type, RepositoryConfig config) {
        this.config = config;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage#transferStateToConfig()
     */
    public void transferStateToConfig() {
        ConfigParameter publishFolderParam = getParamByKey(config,
                PUBLISHING_FOLDER_PARAMETER);
        publishFolderParam.setValue(folderText.getText().trim());
        ConfigParameter inquiryUrl = getParamByKey(config,
                INQUIRY_URL_PARAMETER);
        inquiryUrl.setValue(serverUrlText.getText().trim());
    }
    
    @Override
    public IWizardPage getNextPage() {  
        return getWizard().getNextPage(this);
    }

    @Override
    public boolean canFlipToNextPage() {        
        // If a page exists after current then allow next
        IWizardPage page = getWizard().getNextPage(this);                
        if (page!=null){
            return true;
        }
        return false;              
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.RefereshRepositoryConfigWizardPage#refresh()
     */
    public void refresh() {
        initialize();        
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.ExtRepositoryConfigWizardPage#getDirty()
     */
    public boolean isDirty() {        
        return bIsDirty;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.ExtRepositoryConfigWizardPage#setDirty(boolean)
     */
    public void setDirty(boolean dirty) {
        bIsDirty = dirty;        
    }
}
