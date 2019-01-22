/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.client.CollectionHandle;
import org.eclipse.webdav.client.DAVClient;
import org.eclipse.webdav.client.RemoteDAVClient;
import org.eclipse.webdav.client.WebDAVFactory;
import org.eclipse.webdav.http.client.HttpClient;
import org.eclipse.webdav.internal.kernel.DAVException;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.SimpleBasicAuthenticator;
import com.tibco.xpd.deploy.ui.wizards.BaseDeployWizardPage;
import com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPageExt;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * The wizard page with configuration parameters for Directory Engine
 * Repository.
 * <p>
 * <i>Created: 23 September 2008</i>
 * 
 * @author glewis
 * 
 */
public class WebDavRepositoryPage extends BaseDeployWizardPage implements
        Listener, RepositoryConfigWizardPageExt {

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp();
    }

    private static final String PARAMETER_INFO_DATA = "ParameterInfo"; //$NON-NLS-1$    

    private static final String SITE_URL_PARAMETER = "siteUrl"; //$NON-NLS-1$

    private static final String USERNAME_PARAMETER = "username"; //$NON-NLS-1$

    private static final String PASSWORD_PARAMETER = "password"; //$NON-NLS-1$

    private Group parametersParent;

    private Text siteURLText;

    private Text usernameText;

    private Text passwordText;

    private RepositoryConfig config;

    private boolean initialized = false;

    private boolean bIsDirty = false;

    private Button save;

    private final ServerManager serverManager =
            DeployUIActivator.getServerManager();

    public WebDavRepositoryPage() {
        super("DirectoryEngineRepositoryPage"); //$NON-NLS-1$
        setTitle(Messages.LocalFolderRepositoryPage_Page_title);
        setDescription(Messages.LocalFolderRepositoryPage_Page_description);
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, getTitle());
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

    /**
     * 
     */
    private void createParametersControls() {

        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        parametersParent.setLayout(paramGroupLayout);

        ConfigParameter siteURLParam =
                getParamByKey(config, SITE_URL_PARAMETER);
        createLabel(parametersParent, siteURLParam.getConfigParameterInfo()
                .getLabel());
        siteURLText = createText(parametersParent, ""); //$NON-NLS-1$
        siteURLText.addListener(SWT.Modify, this);

        ConfigParameter usernameParam =
                getParamByKey(config, USERNAME_PARAMETER);
        createLabel(parametersParent, usernameParam.getConfigParameterInfo()
                .getLabel());
        usernameText = createText(parametersParent, ""); //$NON-NLS-1$
        usernameText.addListener(SWT.Modify, this);

        ConfigParameter passwordParam =
                getParamByKey(config, PASSWORD_PARAMETER);
        createLabel(parametersParent, passwordParam.getConfigParameterInfo()
                .getLabel());
        passwordText = createPasswordText(parametersParent, ""); //$NON-NLS-1$

        passwordText.addListener(SWT.Modify, this);

        Composite warningComposite = new Composite(parametersParent, SWT.NONE);
        warningComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false, 2, 1));
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.numColumns = 2;
        warningComposite.setLayout(layout);

        if (passwordParam.getValue() == null) {
            save = createCheckbox(warningComposite, false);
        } else {
            save = createCheckbox(warningComposite, true);
        }
        save.setText(Messages.WebDavRepositoryPage_SavePasswordCheckbox);
        save
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                        2, 1));

        Label warningIcon = createLabel(warningComposite, ""); //$NON-NLS-1$
        warningIcon.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage(ISharedImages.IMG_OBJS_WARN_TSK));
        warningIcon
                .setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
        Label warningText =
                createLabel(warningComposite,
                        Messages.WebDavRepositoryPage_SavePasswordWarning);
        warningText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        createTestConnectionButton(parametersParent);

        validatePage(true, false);
        setMessage(null);
        setErrorMessage(null);
    }

    /**
     * Creates the button used for testing the connection for current server
     * configuration
     * 
     * @param paramsGroup
     */
    private void createTestConnectionButton(Group paramsGroup) {
        final Button button = new Button(paramsGroup, SWT.PUSH);
        button.setText(Messages.ServerParametersPage_TestConnection_label);
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gridData.horizontalSpan = 2;
        button.setLayoutData(gridData);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                BusyIndicator.showWhile(null, new Runnable() {
                    public void run() {
                        String siteURL = null;
                        String username = null;
                        String password = null;
                        try {
                            siteURL = siteURLText.getText();
                            username = usernameText.getText();
                            password = passwordText.getText();

                            WebDAVFactory davFactory = new WebDAVFactory();
                            HttpClient httpClient = new HttpClient();
                            httpClient
                                    .setAuthenticator(new SimpleBasicAuthenticator(
                                            username, password));
                            httpClient.setSocketFactory(SSLAwareSocketFactory
                                    .getInstance());
                            DAVClient davClient =
                                    new RemoteDAVClient(davFactory, httpClient);
                            ILocator siteLocator =
                                    davFactory.newLocator(siteURL);
                            CollectionHandle mSiteHandle =
                                    new CollectionHandle(davClient, siteLocator);
                            mSiteHandle.getMembers();

                            MessageDialog
                                    .openInformation(getShell(),
                                            Messages.WebDavRepositoryPage_TestConnection_title,
                                            Messages.WebDavRepositoryPage_TestConnection_message);

                        } catch (Exception e1) {
                            try {
                                testConnectionCreatingDirectoryStructure(siteURL,
                                        username,
                                        password);
                                MessageDialog
                                        .openInformation(getShell(),
                                                Messages.WebDavRepositoryPage_TestConnection_title,
                                                Messages.WebDavRepositoryPage_TestConnection_message);
                            } catch (ConnectionException e2) {
                                String errorCause = e2.getLocalizedMessage();
                                Status status =
                                        new Status(IStatus.ERROR,
                                                DeployUIActivator.PLUGIN_ID,
                                                IStatus.OK, errorCause,
                                                new Throwable());
                                ErrorDialog
                                        .openError(getShell(),
                                                Messages.WebDavRepositoryPage_UnableToConnectToServer_title,
                                                errorCause,
                                                status);
                            }
                        }
                    }
                });

            }
        });
    }

    /**
     * Attempts to create the directory structure first and then test to see if
     * it is a valid connection
     * 
     * @param siteURL
     * @param username
     * @param password
     * @throws ConnectionException
     */
    private void testConnectionCreatingDirectoryStructure(String siteURL,
            String username, String password) throws ConnectionException {
        boolean isPortPath = false;
        Path path = new Path(siteURL);
        while (!isPortPath) {
            try {
                WebDAVFactory davFactory = new WebDAVFactory();
                HttpClient httpClient = new HttpClient();
                httpClient.setAuthenticator(new SimpleBasicAuthenticator(
                        username, password));
                httpClient
                        .setSocketFactory(SSLAwareSocketFactory.getInstance());
                DAVClient davClient =
                        new RemoteDAVClient(davFactory, httpClient);
                ILocator siteLocator = davFactory.newLocator(siteURL);
                CollectionHandle mSiteHandle =
                        new CollectionHandle(davClient, siteLocator);
                mSiteHandle.getMembers();

                Path extraDirsPath =
                        new Path(siteURLText.getText().replace(siteURL, "")); //$NON-NLS-1$
                for (int i = 0; i < extraDirsPath.segmentCount(); i++) {
                    String dir = extraDirsPath.segment(i);
                    String newLocation =
                            new Path(siteURL).append(dir).toPortableString();
                    // path changes the port to have :: so we replace
                    // this back to :
                    newLocation = newLocation.replace("::", ":"); //$NON-NLS-1$ //$NON-NLS-2$
                    ILocator resourceLocator =
                            davClient.getDAVFactory()
                                    .newStableLocator(newLocation);
                    CollectionHandle collectionHandle =
                            new CollectionHandle(davClient, resourceLocator);
                    collectionHandle.mkdirs();

                    httpClient = new HttpClient();
                    httpClient.setAuthenticator(new SimpleBasicAuthenticator(
                            username, password));
                    httpClient.setSocketFactory(SSLAwareSocketFactory
                            .getInstance());
                    davClient = new RemoteDAVClient(davFactory, httpClient);
                    siteLocator = davFactory.newLocator(newLocation);
                    mSiteHandle = new CollectionHandle(davClient, siteLocator);
                    mSiteHandle.getMembers();
                }
                isPortPath = true;
            } catch (DAVException e) {
                siteURL = path.removeLastSegments(1).toString();
                path = new Path(siteURL);
                if (path.segmentCount() <= 1) {
                    isPortPath = true;
                    throw new ConnectionException(e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private ConfigParameter getParamByKey(RepositoryConfig config, String key) {
        for (ConfigParameter param : config.getConfigParameters()) {
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
     * Event)
     */
    public void handleEvent(Event event) {
        bIsDirty = true;
        if (event.type == SWT.Modify
                && (event.widget == siteURLText || event.widget == usernameText || event.widget == passwordText)) {
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
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control
                                .getData(PARAMETER_INFO_DATA);
                String value = null;
                if (control instanceof Text) {
                    value = ((Text) control).getText();
                }
                if (control instanceof Button) {
                    value = String.valueOf(((Button) control).getSelection());
                }
                configParam.setKey(paramInfo.getKey());
                configParam.setConfigParameterInfo(paramInfo);
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    if (save.getSelection()) {
                        value = EncryptionUtil.encrypt(value);
                    } else {
                        value = null;
                    }
                }
                configParam.setValue(value);
                configParams.add(configParam);
            }
        }
        return serverConfig;
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
        ConfigParameter siteURLParam =
                getParamByKey(config, SITE_URL_PARAMETER);
        siteURLText.setText(siteURLParam.getValue());
        ConfigParameter usernameParam =
                getParamByKey(config, USERNAME_PARAMETER);
        usernameText.setText(usernameParam.getValue());
        ConfigParameter passwordParam =
                getParamByKey(config, PASSWORD_PARAMETER);
        if (passwordParam.getValue() != null) {
            String defaultVal =
                    passwordParam.getConfigParameterInfo().getDefaultValue();
            if (defaultVal.equals(passwordParam.getValue())) {
                passwordText.setText(passwordParam.getValue());
            } else {
                passwordText.setText(EncryptionUtil.decrypt(passwordParam
                        .getValue()));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage#
     * setRepositoryConfig(com.tibco.xpd.deploy.model.RepositoryType,
     * com.tibco.xpd.deploy.model.RepositoryConfig)
     */
    public void init(RepositoryType type, RepositoryConfig config) {
        this.config = config;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage#
     * transferStateToConfig()
     */
    public void transferStateToConfig() {
        ConfigParameter siteURLParam =
                getParamByKey(config, SITE_URL_PARAMETER);
        siteURLParam.setValue(siteURLText.getText().trim());

        ConfigParameter usernameParam =
                getParamByKey(config, USERNAME_PARAMETER);
        usernameParam.setValue(usernameText.getText().trim());

        ConfigParameter passwordParam =
                getParamByKey(config, PASSWORD_PARAMETER);
        if (save.getSelection()) {
            passwordParam.setValue(EncryptionUtil.encrypt(passwordText
                    .getText().trim()));
        } else {
            passwordParam.setValue(null);
        }
    }

    @Override
    public IWizardPage getNextPage() {
        return getWizard().getNextPage(this);
    }

    @Override
    public boolean canFlipToNextPage() {
        // If a page exists after current then allow next
        IWizardPage page = getWizard().getNextPage(this);
        if (page != null) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.RefereshRepositoryConfigWizardPage#refresh
     * ()
     */
    public void refresh() {
        initialize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.ExtRepositoryConfigWizardPage#getDirty()
     */
    public boolean isDirty() {
        return bIsDirty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.ExtRepositoryConfigWizardPage#setDirty
     * (boolean)
     */
    public void setDirty(boolean dirty) {
        bIsDirty = dirty;
    }
}
