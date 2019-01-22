/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.TestableConnection;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.FileControl;
import com.tibco.xpd.deploy.ui.components.StringControl;
import com.tibco.xpd.deploy.ui.components.TestableServerProvider;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * Page for editing server parameters.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerParametersPage extends WizardSelectionPage implements
        Listener, TestableServerProvider {

    /** Key used for storing parameter description in file. */
    private static final String PARAMETER_INFO = "ParameterInfo"; //$NON-NLS-1$

    private Group parametersParent;

    private ServerType serverType;

    private ComboViewer repositoryTypeViewer;

    private final Map<String, RepositoryConfigWizardNode> repositoryConfigWizardNodesCache;

    private RepositoryConfigWizardNode selectedRepositoryWizardNode;

    private final ServerManager serverManager = DeployUIActivator
            .getServerManager();

    private Button save;

    final public static String CONNECT_IMMEDIATELY_PREF_SETTING =
            "connectServerImmediatelyPrefSetting"; //$NON-NLS-1$

    /**
     * Check box that allows user to decide if he wants to immediately connect
     * to the server.
     */
    private Button connectImmediately;

    public ServerParametersPage() {
        super("ServerParametersPage"); //$NON-NLS-1$
        setTitle(Messages.ServerParametersPage_Page_title);
        setDescription(Messages.ServerParametersPage_Page_longdesc);
        repositoryConfigWizardNodesCache =
                new HashMap<String, RepositoryConfigWizardNode>();
    }

    @Override
    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, getTitle());

        parametersParent = new Group(composite, SWT.NULL);
        parametersParent
                .setText(Messages.ServerParametersPage_ServerParametersGroup_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(serverGroupGridData);

        // repository type
        Group serverTypeGroup = new Group(composite, SWT.NULL);
        serverTypeGroup.setText(Messages.ServerParametersPage_Repository_label);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
        serverTypeGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        serverTypeGroup.setLayout(paramGroupLayout);

        createLabel(serverTypeGroup,
                Messages.ServerParametersPage_Repository_type_label);
        Combo repositoryTypeCombo = createCombo(serverTypeGroup);
        ((GridData) repositoryTypeCombo.getLayoutData()).horizontalSpan = 2;
        repositoryTypeViewer = new ComboViewer(repositoryTypeCombo);
        repositoryTypeViewer.setContentProvider(new ArrayContentProvider());

        repositoryTypeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                serverManager.getAdapterFactory()));
        repositoryTypeViewer.setInput(serverManager.getServerContainer()
                .getRepositoryTypes());
        repositoryTypeViewer.addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof RepositoryType) {
                    RepositoryType rType = (RepositoryType) element;
                    return rType.isValid();
                }
                return false;
            }

        });
        repositoryTypeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        RepositoryType repoType =
                                (RepositoryType) ((IStructuredSelection) event
                                        .getSelection()).getFirstElement();
                        if (repoType == null)
                            return;

                        selectedRepositoryWizardNode =
                                repositoryConfigWizardNodesCache.get(repoType
                                        .getId());
                        if (selectedRepositoryWizardNode == null) {
                            selectedRepositoryWizardNode =
                                    new RepositoryConfigWizardNode(
                                            ServerParametersPage.this
                                                    .getWizard(), repoType);
                            repositoryConfigWizardNodesCache.put(repoType
                                    .getId(), selectedRepositoryWizardNode);
                        }
                        setSelectedNode(selectedRepositoryWizardNode);
                        setPageComplete(validatePage(true, true));
                    }
                });
        repositoryTypeViewer.addFilter(new RepositoryTypeFilter());
        setPageComplete(false);
        ServerType sType = null;
        if (getWizard() instanceof AddServerWizard) {
            sType =
                    ((AddServerWizard) getWizard()).getServerTypePage()
                            .getServerType();
        } else if (getWizard() instanceof ServerTypeConfigWizard) {
            sType = ((ServerTypeConfigWizard) getWizard()).getServerType();
        }
        if (sType != null) {
            setServerType(sType);
        }
        setControl(composite);
    }

    private void createParametersControls() {
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        parametersParent.setLayout(paramGroupLayout);

        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            children[i].dispose();
        }
        ServerConfigInfo serverConfigInfo = serverType.getServerConfigInfo();
        ConfigToolkit toolkit = ConfigToolkit.INSTANCE;
        if (serverConfigInfo == null)
            return;
        boolean hasPassword = false;
        for (Iterator<?> iter =
                serverConfigInfo.getConfigParameterInfos().iterator(); iter
                .hasNext();) {
            ConfigParameterInfo paramInfo = (ConfigParameterInfo) iter.next();
            ConfigParameterType paramType = paramInfo.getParameterType();
            if (paramInfo.isAutomatic()) {
                switch (paramType.getValue()) {
                case ConfigParameterType.STRING: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Map<String, String> facetsMap = paramInfo.getFacetsMap();
                    StringControl stringControl =
                            toolkit.createStringControl(parametersParent,
                                    paramInfo.getDefaultValue(),
                                    facetsMap, /* TestableServerProvider */
                                    this);
                    stringControl.setData(PARAMETER_INFO, paramInfo);
                    stringControl.getTextControl()
                            .addListener(SWT.Modify, this);
                    break;
                }
                case ConfigParameterType.PASSWORD: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Text text =
                            toolkit.createText(parametersParent,
                                    paramInfo.getDefaultValue());
                    text.setData(PARAMETER_INFO, paramInfo);
                    text.setEchoChar('*');
                    text.addListener(SWT.Modify, this);
                    hasPassword = true;
                    break;
                }
                case ConfigParameterType.INTEGER: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Text text =
                            toolkit.createText(parametersParent,
                                    paramInfo.getDefaultValue());
                    text.setData(PARAMETER_INFO, paramInfo);
                    text.addListener(SWT.Modify, this);
                    break;
                }
                case ConfigParameterType.BOOLEAN: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Button checkbox =
                            createCheckbox(parametersParent,
                                    Boolean.parseBoolean(paramInfo
                                            .getDefaultValue()));
                    checkbox.setData(PARAMETER_INFO, paramInfo);
                    checkbox.addListener(SWT.Modify, this);
                    break;
                }
                case ConfigParameterType.FILE: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Map<String, String> facetsMap = paramInfo.getFacetsMap();
                    String defaultValue = paramInfo.getDefaultValue();
                    if (defaultValue != null) {
                        facetsMap.put(FileControl.FILE_DEFAULT_VALUE,
                                defaultValue);
                    }
                    FileControl fileControl =
                            toolkit.createFileControl(parametersParent,
                                    facetsMap);
                    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                            .grab(true, false).applyTo(fileControl);
                    fileControl.setData(PARAMETER_INFO, paramInfo);
                    fileControl.addTextListener(SWT.Modify, this);
                    break;
                }
                default:
                    throw new IllegalStateException(
                            "Incorrect configuration type."); //$NON-NLS-1$
                }
            }
        }

        if (hasPassword) {
            Composite warningComposite =
                    new Composite(parametersParent, SWT.NONE);
            warningComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                    true, false, 2, 1));
            GridLayout layout = new GridLayout();
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.numColumns = 2;
            warningComposite.setLayout(layout);

            save = createCheckbox(warningComposite, false);
            save.setText(Messages.ServerParametersPage_SavePasswordCheckbox);
            save.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                    2, 1));

            Label warningIcon = createLabel(warningComposite, ""); //$NON-NLS-1$
            warningIcon.setImage(PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_OBJS_WARN_TSK));
            warningIcon.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
                    false));
            Label warningText =
                    createLabel(warningComposite,
                            Messages.ServerParametersPage_SavePasswordWarning);
            warningText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
        }
        /**
         * SCF-75: Create the connect immediately check box control.
         */
        createConnectImmediatelyControl();
        createTestConnectionButton(parametersParent);

        parametersParent.layout();
        validatePage(true, false);
        setMessage(null);
        setErrorMessage(null);
    }

    /**
     * Creates the connect immediately check box control.
     */
    private void createConnectImmediatelyControl() {

        /*
         * get the dialog setting pref store.
         */
        final IDialogSettings dialogSettings =
                DeployUIActivator.getDefault().getDialogSettings();

        boolean connectServerImmediately = true;

        if (dialogSettings.get(CONNECT_IMMEDIATELY_PREF_SETTING) != null) {
            /*
             * if the pref has the connect immediately value in it then use it.
             */
            connectServerImmediately =
                    dialogSettings.getBoolean(CONNECT_IMMEDIATELY_PREF_SETTING);
        } else {
            /*
             * default value is true
             */
            dialogSettings.put(CONNECT_IMMEDIATELY_PREF_SETTING, true);
        }

        connectImmediately =
                createCheckbox(parametersParent, connectServerImmediately);
        connectImmediately
                .setText(Messages.ServerParametersPage_ConnectServerImmediatelyButton_label);

        connectImmediately.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                /*
                 * set the pref value based on the button selection
                 */
                dialogSettings.put(CONNECT_IMMEDIATELY_PREF_SETTING,
                        connectImmediately.getSelection());

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // Do nothing here.
            }
        });

    }

    /**
     * 
     * @return <code>true</code> if the user selected to connect immediately to
     *         the server they just created else return <code>false</code>
     */
    public boolean shouldConnectServerImmediately() {
        return connectImmediately.getSelection();
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
                final Server server = getTestableServer();
                BusyIndicator.showWhile(null, new Runnable() {
                    @Override
                    public void run() {
                        // try to connect and show success or error dialog to
                        // user
                        Connection connection = server.getConnection();
                        TestableConnection tConn =
                                DeployUtil.getTestableConnection(connection);
                        if (tConn != null) {
                            IStatus status = tConn.testConnection();
                            if (status.isOK()) {
                                MessageDialog
                                        .openInformation(getShell(),
                                                Messages.ServerInfoPropertyPage_TestConnection_label,
                                                Messages.ServerParametersPage_TestConnection_message);
                            } else if (status.getSeverity() == IStatus.CANCEL) {
                                // ignore cancel.
                            } else if (status.getSeverity() == IStatus.ERROR) {
                                ErrorDialog
                                        .openError(getWizard().getContainer()
                                                .getShell(),
                                                Messages.ServerInfoPropertyPage_TestConnection_label,
                                                Messages.ServerParametersPage_TestConnectionFailed_message,
                                                status);
                            } else {
                                // INFO, WARNING
                                ErrorDialog
                                        .openError(getWizard().getContainer()
                                                .getShell(),
                                                Messages.ServerInfoPropertyPage_TestConnection_label,
                                                status.getMessage(),
                                                status);
                            }
                        } else {
                            // default method of testing connection.
                            try {
                                connection.connect();
                                MessageDialog
                                        .openInformation(getShell(),
                                                Messages.ServerInfoPropertyPage_TestConnection_label,
                                                Messages.ServerParametersPage_TestConnection_message);
                            } catch (ConnectionException e1) {
                                String errorCause = e1.getLocalizedMessage();
                                Status status =
                                        new Status(IStatus.ERROR,
                                                DeployUIActivator.PLUGIN_ID,
                                                IStatus.OK, errorCause, e1
                                                        .getCause());
                                DeployUIActivator.getDefault().getLog()
                                        .log(status);
                                ErrorDialog
                                        .openError(getWizard().getContainer()
                                                .getShell(),
                                                Messages.ServerInfoPropertyPage_TestConnection_label,
                                                Messages.ServerParametersPage_TestConnectionFailed_message,
                                                status);

                            } finally {
                                try {
                                    connection.disconnect();
                                } catch (ConnectionException e1) {
                                    String errorCause =
                                            e1.getLocalizedMessage();
                                    Status status =
                                            new Status(
                                                    IStatus.ERROR,
                                                    DeployUIActivator.PLUGIN_ID,
                                                    IStatus.OK, errorCause, e1
                                                            .getCause());
                                    DeployUIActivator.getDefault().getLog()
                                            .log(status);
                                    ErrorDialog
                                            .openError(getWizard()
                                                    .getContainer().getShell(),
                                                    Messages.ServerParametersPage_UnableToDisconnectFromServer_title,
                                                    errorCause,
                                                    status);
                                }
                            }
                        }
                    }

                });

            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Server getTestableServer() {
        ServerConfig serverConfig = getServerConfig(true);
        RepositoryConfigWizardNode node = getRepositoryWizardNode();
        RepositoryConfig tempRepoConfig = null;
        RepositoryType tempRepoType = null;
        if (node != null) {
            RepositoryConfigWizard repositoryConfigWizard =
                    ((RepositoryConfigWizard) node.getWizard());
            repositoryConfigWizard.transferStateToConfig();
            tempRepoConfig = repositoryConfigWizard.getRepositoryConfig();
            tempRepoType = repositoryConfigWizard.getRepositoryType();
        }
        RepositoryConfig repoConfig = tempRepoConfig;
        RepositoryType repoType = tempRepoType;

        // runtime
        EditingDomain ed = serverManager.getEditingDomain();
        DeployFactory factory = DeployFactory.eINSTANCE;

        // server
        Server server = factory.createServer();

        /* server type */
        ServerType privateServerType = serverType;
        server.setServerType(privateServerType);
        server.setServerConfig(serverConfig);
        return server;
    }

    private boolean validatePage(boolean setFocus, boolean showMessage) {
        setMessage(null);
        setErrorMessage(null);
        if (selectedRepositoryWizardNode == null) {
            String message =
                    Messages.ServerParametersPage_missingRepositoryType_message;
            setErrorMessage(message);
            setPageComplete(false);
            if (setFocus) {
                repositoryTypeViewer.getControl().forceFocus();
            }
            return false;
        }
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null
                    && control.getData(PARAMETER_INFO) instanceof ConfigParameterInfo) {
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                // errors
                if (paramInfo.getParameterType()
                        .equals(ConfigParameterType.STRING_LITERAL)
                        && paramInfo.isRequired()
                        && ((StringControl) control).getText().length() == 0) {
                    String message =
                            String.format(Messages.ServerParametersPage_EmptyField_message,
                                    paramInfo.getName());
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setPageComplete(false);
                    if (setFocus) {
                        control.forceFocus();
                    }
                    return false;
                }
                if (control instanceof Text
                        && paramInfo.getParameterType()
                                .equals(ConfigParameterType.INTEGER_LITERAL)
                        && !((Text) control).getText().matches("\\d*")) { //$NON-NLS-1$
                    String message =
                            String.format(Messages.ServerParametersPage_NotIntegerField_message,
                                    paramInfo.getName());
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setPageComplete(false);
                    if (setFocus) {
                        control.forceFocus();
                    }
                    return false;
                }
                if (control instanceof Text
                        && paramInfo.getParameterType()
                                .equals(ConfigParameterType.PASSWORD_LITERAL)
                        && ((Text) control).getText().length() > 256) {
                    String message =
                            String.format(Messages.ServerParametersPage_MaxPasswordLength_message,
                                    new Object[] { paramInfo.getName() });
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setPageComplete(false);
                    if (setFocus) {
                        control.forceFocus();
                    }
                    return false;
                }
                if (control instanceof FileControl
                        && paramInfo.getParameterType()
                                .equals(ConfigParameterType.FILE_LITERAL)) {
                    FileControl fileControl = (FileControl) control;
                    IStatus status = fileControl.validate();
                    if (status.getSeverity() != IStatus.OK) {

                        // ignore empty path error if not required
                        if (status.getCode() != FileControl.EMPTY_PATH_ERROR_CODE
                                || paramInfo.isRequired()) {
                            String message =
                                    String.format(Messages.ServerParametersPage_fileFieldValidationProblem_message,
                                            new Object[] { paramInfo.getName() });
                            if (status.getMessage() != null
                                    && status.getMessage().trim().length() > 0) {
                                message += ' ' + status.getMessage();
                            }
                            if (showMessage) {
                                if (status.getSeverity() == IStatus.ERROR) {
                                    setErrorMessage(message);
                                } else if (status.getSeverity() == IStatus.WARNING) {
                                    setMessage(message, WARNING);
                                } else {
                                    setMessage(message);
                                }
                            }
                            setPageComplete(false);
                            if (setFocus) {
                                control.forceFocus();
                            }
                            return false;
                        }
                    }

                }

                // TODO other checks ...
            }
        }

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setPageComplete(true);
        return true;
    }

    public void setServerType(ServerType sType) {
        if (parametersParent != null) {
            if (serverType == null || !sType.equals(serverType)) {
                serverType = sType;
                createParametersControls();
                repositoryTypeViewer.refresh();
                // set default repository type
                RepositoryType defaultRepositoryType =
                        serverManager
                                .getServerContainer()
                                .getRepositoryTypeById(ServerManager.DEFAULT_REPOSITORY_TYPE_ID);
                if (defaultRepositoryType != null
                        && sType.getSupportedRepositoryTypes()
                                .contains(defaultRepositoryType)) {
                    repositoryTypeViewer.setSelection(new StructuredSelection(
                            defaultRepositoryType));
                } else {
                    Object firstElement = repositoryTypeViewer.getElementAt(0);
                    if (firstElement != null) {
                        repositoryTypeViewer
                                .setSelection(new StructuredSelection(
                                        firstElement), true);
                    }
                }

            }
        }
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == SWT.Modify) {
            validatePage(false, true);
            getWizard().getContainer().updateButtons();
        }
    }

    public ServerConfig getServerConfig() {
        return getServerConfig(false);
    }

    public ServerConfig getServerConfig(boolean forcePasswordInclude) {
        DeployFactory factory = DeployFactory.eINSTANCE;
        ServerConfig serverConfig = factory.createServerConfig();
        List<ConfigParameter> configParams = serverConfig.getConfigParameters();
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null
                    && control.getData(PARAMETER_INFO) instanceof ConfigParameterInfo) {
                ConfigParameter configParam = factory.createConfigParameter();
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                String value = null;
                if (paramInfo.getParameterType() == ConfigParameterType.STRING_LITERAL) {
                    value = ((StringControl) control).getText();
                }
                if (paramInfo.getParameterType() == ConfigParameterType.INTEGER_LITERAL) {
                    value = ((Text) control).getText();
                }
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    value = ((Text) control).getText();
                }
                if (control instanceof Button) {
                    value = String.valueOf(((Button) control).getSelection());
                }
                if (control instanceof FileControl) {
                    value = ((FileControl) control).getText();
                }
                configParam.setKey(paramInfo.getKey());
                configParam.setConfigParameterInfo(paramInfo);
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    if (forcePasswordInclude || save.getSelection()) {
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
    public IWizardPage getNextPage() {
        if (selectedRepositoryWizardNode != null) {
            if (!selectedRepositoryWizardNode.hasDiscoveredPages()) {
                return getWizard().getNextPage(this);
            }
        }
        return super.getNextPage();
    }

    @Override
    public boolean canFlipToNextPage() {
        // if error then dont allow next page
        if (getErrorMessage() != null) {
            return false;
        } else if (selectedRepositoryWizardNode != null) {
            boolean canFlip = selectedRepositoryWizardNode.hasDiscoveredPages();
            if (!canFlip) {
                // If a page exists after current then allow next
                IWizardPage page = getWizard().getNextPage(this);
                if (page != null) {
                    return true;
                } else {
                    return false;
                }
            }
            return canFlip;
        }

        return super.canFlipToNextPage();
    }

    protected Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        return label;
    }

    protected Combo createCombo(Composite parent) {
        Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return combo;
    }

    protected Button createCheckbox(Composite parent, boolean defaultValue) {
        Button button = new Button(parent, SWT.CHECK);
        button.setSelection(defaultValue);
        return button;
    }

    @Override
    public void setSelectedNode(IWizardNode node) {
        super.setSelectedNode(node);
    }

    public RepositoryConfigWizardNode getRepositoryWizardNode() {
        return selectedRepositoryWizardNode;
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    private class RepositoryTypeFilter extends ViewerFilter {
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof RepositoryType
                    && serverType != null
                    && serverType.getSupportedRepositoryTypes()
                            .contains(element)) {
                return true;
            }
            return false;
        }
    }
}
