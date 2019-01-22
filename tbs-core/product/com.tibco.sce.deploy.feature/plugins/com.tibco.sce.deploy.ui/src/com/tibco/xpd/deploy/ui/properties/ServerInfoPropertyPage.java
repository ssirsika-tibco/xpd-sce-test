/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.TestableConnection;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.actions.ConnectServerAction;
import com.tibco.xpd.deploy.ui.components.FileControl;
import com.tibco.xpd.deploy.ui.components.StringControl;
import com.tibco.xpd.deploy.ui.components.TestableServerProvider;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.deploy.ui.wizards.addserver.ServerParametersPage;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * Property page for displaying (and changing) general server information.
 * <p>
 * <i>Created: 28 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerInfoPropertyPage extends PropertyPage implements Listener,
        TestableServerProvider {

    /** Key used for storing parameter description in file. */
    private static final String PARAMETER_INFO = "ParameterInfo"; //$NON-NLS-1$

    private Server selectedServer;

    private Text nameText;

    private Text serverTypeTextLabel;

    private Group parametersParent;

    private boolean initialised = false;

    private ComboViewer repositoryTypeViewer;

    private Button save;

    /**
     * Check box that allows user to decide if he wants to immediately connect
     * to the server.
     */
    private Button connectImmediately;

    /**
     * Constructor for SamplePropertyPage.
     */
    public ServerInfoPropertyPage() {
        super();
    }

    private void addFirstSection(Composite parent) {
        Composite composite = createDefaultComposite(parent);

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText(Messages.ServerInfoPropertyPage_ServerName_label);

        nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        nameText.setText(selectedServer.getName());
        nameText.addListener(SWT.Modify, this);

        Label serverTypeLabel = new Label(composite, SWT.NONE);
        serverTypeLabel
                .setText(Messages.ServerInfoPropertyPage_ServerRuntime_label);

        serverTypeTextLabel = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
        if (selectedServer.getServerType() != null) {
            serverTypeTextLabel.setText(selectedServer.getServerType()
                    .getName());
        } else {
            serverTypeTextLabel
                    .setText(Messages.ServerInfoPropertyPage_UnknownRuntime_label);
        }
    }

    private void addSeparator(Composite parent) {
        Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        separator.setLayoutData(gridData);
    }

    private void addSecondSection(Composite parent) {
        parametersParent = new Group(parent, SWT.NULL);
        parametersParent
                .setText(Messages.ServerInfoPropertyPage_RuntimeServerParameters_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(serverGroupGridData);
        createParametersControls();
        fillServerConfigControls(selectedServer, true);

        // repository type
        Group repositoryTypeGroup = new Group(parent, SWT.NULL);
        repositoryTypeGroup
                .setText(Messages.ServerInfoPropertyPage_Repository_label);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
        repositoryTypeGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        repositoryTypeGroup.setLayout(paramGroupLayout);

        createLabel(repositoryTypeGroup,
                Messages.ServerInfoPropertyPage_RepositoryType_label);
        Combo repositoryTypeCombo = createCombo(repositoryTypeGroup);
        repositoryTypeCombo.setEnabled(false);
        ((GridData) repositoryTypeCombo.getLayoutData()).horizontalSpan = 2;
        repositoryTypeViewer = new ComboViewer(repositoryTypeCombo);
        repositoryTypeViewer.setContentProvider(new ArrayContentProvider());
        ServerManager serverManager = DeployUIActivator.getServerManager();
        repositoryTypeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                serverManager.getAdapterFactory()));
        repositoryTypeViewer.setInput(serverManager.getServerContainer()
                .getRepositoryTypes());
        repositoryTypeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        RepositoryType repoType =
                                (RepositoryType) ((IStructuredSelection) event
                                        .getSelection()).getFirstElement();
                        if (repoType == null)
                            return;
                    }
                });
        repositoryTypeViewer.addFilter(new RepositoryTypeFilter());
        RepositoryType repositoryType =
                selectedServer.getRepository().getRepositoryType();
        repositoryTypeViewer.setSelection(new StructuredSelection(
                repositoryType), true);
    }

    /**
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        selectedServer = (Server) (getElement()).getAdapter(Server.class);

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL);
        data.grabExcessHorizontalSpace = true;
        composite.setLayoutData(data);

        addFirstSection(composite);
        addSeparator(composite);
        addSecondSection(composite);
        initialised = true;
        return composite;
    }

    private Composite createDefaultComposite(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        composite.setLayout(layout);

        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);

        return composite;
    }

    private void createParametersControls() {
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        parametersParent.setLayout(paramGroupLayout);

        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            children[i].dispose();
        }
        ConfigToolkit toolkit = ConfigToolkit.INSTANCE;
        ServerConfigInfo serverConfigInfo =
                selectedServer.getServerType().getServerConfigInfo();
        if (serverConfigInfo == null)
            return;
        boolean hasPassword = false;
        for (Iterator<?> iter =
                serverConfigInfo.getConfigParameterInfos().iterator(); iter
                .hasNext();) {
            ConfigParameterInfo paramInfo = (ConfigParameterInfo) iter.next();
            ConfigParameterType paramType = paramInfo.getParameterType();
            if (paramInfo.isAutomatic()) {
                Control control = null;
                switch (paramType.getValue()) {
                case ConfigParameterType.STRING: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    Map<String, String> facetsMap = paramInfo.getFacetsMap();
                    StringControl stringControl =
                            toolkit.createStringControl(parametersParent,
                                    "",
                                    facetsMap, /* TestableServerProvider */
                                    this);
                    stringControl.getTextControl()
                            .addListener(SWT.Modify, this);
                    stringControl.setData(PARAMETER_INFO, paramInfo);
                    control = stringControl;
                    break;
                }
                case ConfigParameterType.PASSWORD: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    control = createText(parametersParent, ""); //$NON-NLS-1$
                    control.setData(PARAMETER_INFO, paramInfo);
                    ((Text) control).setEchoChar('*');
                    hasPassword = true;
                    break;
                }
                case ConfigParameterType.INTEGER: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    control = createText(parametersParent, ""); //$NON-NLS-1$
                    control.setData(PARAMETER_INFO, paramInfo);
                    break;
                }
                case ConfigParameterType.BOOLEAN: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    control =
                            createCheckbox(parametersParent,
                                    Boolean.parseBoolean(paramInfo
                                            .getDefaultValue()));
                    control.setData(PARAMETER_INFO, paramInfo);
                    break;
                }
                case ConfigParameterType.FILE: {
                    createLabel(parametersParent, paramInfo.getLabel());
                    control =
                            toolkit.createFileControl(parametersParent,
                                    paramInfo.getFacetsMap());
                    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                            .grab(true, false).applyTo(control);
                    ((FileControl) control).addTextListener(SWT.Modify, this);
                    break;
                }
                default:
                    throw new IllegalStateException(
                            "Incorrect parameter configuration type."); //$NON-NLS-1$
                }
                control.setData(PARAMETER_INFO, paramInfo);
                control.addListener(SWT.Modify, this);
                control.setEnabled(false);
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

        parametersParent.layout();
    }

    /**
     * Creates the connect immediately check box control.
     * <p>
     * Note that this control is created only if the selected server is in
     * disconnected state.
     */
    private void createConnectImmediatelyControl() {

        Connection connection = selectedServer.getConnection();

        try {
            boolean connected = connection.isConnected();

            if (!connected) {
                /*
                 * get the dialog setting pref store.
                 */
                final IDialogSettings dialogSettings =
                        DeployUIActivator.getDefault().getDialogSettings();

                boolean connectServerImmediately = true;

                if (dialogSettings
                        .get(ServerParametersPage.CONNECT_IMMEDIATELY_PREF_SETTING) != null) {
                    /*
                     * if the pref has the connect immediately value in it then
                     * use it.
                     */
                    connectServerImmediately =
                            dialogSettings
                                    .getBoolean(ServerParametersPage.CONNECT_IMMEDIATELY_PREF_SETTING);
                } else {
                    /*
                     * default value is true
                     */
                    dialogSettings
                            .put(ServerParametersPage.CONNECT_IMMEDIATELY_PREF_SETTING,
                                    true);
                }
                connectImmediately =
                        createCheckbox(parametersParent,
                                connectServerImmediately);
                connectImmediately
                        .setText(Messages.ServerParametersPage_ConnectServerImmediatelyButton_label);

                connectImmediately
                        .addSelectionListener(new SelectionListener() {

                            @Override
                            public void widgetSelected(SelectionEvent e) {
                                /*
                                 * set the pref value based on the button
                                 * selection
                                 */
                                dialogSettings
                                        .put(ServerParametersPage.CONNECT_IMMEDIATELY_PREF_SETTING,
                                                connectImmediately
                                                        .getSelection());

                            }

                            @Override
                            public void widgetDefaultSelected(SelectionEvent e) {
                                // Do nothing here.
                            }
                        });
            }
        } catch (ConnectionException e) {

            e.printStackTrace();
        }
    }

    private void fillServerConfigControls(Server server, boolean enableEdit) {
        Control[] children = parametersParent.getChildren();
        boolean savePassword = true;
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                ConfigParameter configParameter = null;
                if (server != null) {
                    configParameter =
                            server.getServerConfig()
                                    .getConfigParameter(paramInfo.getKey());
                }
                if (configParameter == null) {
                    // this may happen if new parameters are added
                    // in other words this migrates user's config to new
                    // versions.
                    configParameter =
                            DeployPackage.eINSTANCE.getDeployFactory()
                                    .createConfigParameter();
                    configParameter.setConfigParameterInfo(paramInfo);
                    configParameter.setKey(paramInfo.getKey());
                    configParameter.setValue(paramInfo.getDefaultValue());
                    server.getServerConfig().getConfigParameters()
                            .add(configParameter);
                }
                String value = configParameter.getValue();
                if (paramInfo.getParameterType() == ConfigParameterType.STRING_LITERAL) {
                    ((StringControl) control).setText(value);
                }
                if (paramInfo.getParameterType() == ConfigParameterType.INTEGER_LITERAL) {
                    ((Text) control).setText(value);
                }
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    if (value == null) {
                        value = ""; //$NON-NLS-1$
                        savePassword = false;
                    } else {
                        try {
                            value = EncryptionUtil.decrypt(value);
                        } catch (IllegalArgumentException e) {
                            String title =
                                    Messages.ServerInfoPropertyPage_ServerParameterProblem_title;
                            String message =
                                    String.format(Messages.ServerInfoPropertyPage_CorruptedEncryptedField_message,
                                            paramInfo.getName());
                            MessageDialog.openError(getShell(), title, message);
                            value = ""; //$NON-NLS-1$
                        }
                    }
                    ((Text) control).setText(value != null ? value : ""); //$NON-NLS-1$
                }
                if (paramInfo.getParameterType() == ConfigParameterType.BOOLEAN_LITERAL
                        && control instanceof Button) {
                    ((Button) control)
                            .setSelection(Boolean.parseBoolean(value));
                }
                if (paramInfo.getParameterType() == ConfigParameterType.FILE_LITERAL
                        && control instanceof FileControl) {
                    ((FileControl) control).setText(value != null ? value : ""); //$NON-NLS-1$
                }
            }
            control.setEnabled(enableEdit);
        }
        save.setSelection(savePassword);
    }

    private boolean validatePage(boolean setFocus, boolean showMessage) {
        if (!initialised) {
            return true;
        }
        setMessage(null);
        setErrorMessage(null);

        // Name
        String serverName = nameText.getText().trim();
        if (serverName.length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.ServerInfoPropertyPage_EmptyName_message);
            }
            nameText.setFocus();
            return false;
        }
        if (!DeployUIActivator.getServerManager().getServerContainer()
                .isValidNewServerName(serverName)
                && !serverName.equals(selectedServer.getName())) {
            if (showMessage) {
                setErrorMessage(Messages.ServerInfoPropertyPage_NotUniqueName_message);
            }
            nameText.setFocus();
            return false;
        }

        // Configuration Parameters
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                // errors
                if (paramInfo.getParameterType() == ConfigParameterType.STRING_LITERAL
                        && paramInfo.isRequired()
                        && ((StringControl) control).getText().length() == 0) {
                    String message =
                            String.format(Messages.ServerInfoPropertyPage_EmptyField_message,
                                    paramInfo.getName());
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setValid(false);
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
                            MessageFormat
                                    .format(Messages.ServerInfoPropertyPage_IntegerField_message,
                                            paramInfo.getName());
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setValid(false);
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
                            String.format(Messages.ServerInfoPropertyPage_EncryptedFieldMaxLenght_message,
                                    paramInfo.getName());
                    if (showMessage) {
                        setErrorMessage(message);
                    }
                    setValid(false);
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
                            setValid(false);
                            if (setFocus) {
                                control.forceFocus();
                            }
                            return false;
                        }
                    }

                }
            }
        }

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setValid(true);
        return true;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.widget.getData(PARAMETER_INFO) instanceof ConfigParameterInfo
                || event.widget == nameText) {
            if (event.type == SWT.Modify) {
                validatePage(false, true);
            }
        }
    }

    /**
     * Populates the page with the default values.
     */
    @Override
    protected void performDefaults() {
        nameText.setText(selectedServer.getName());
        if (selectedServer.getServerType() != null) {
            serverTypeTextLabel.setText(selectedServer.getServerType()
                    .getName());
        } else {
            serverTypeTextLabel
                    .setText(Messages.ServerInfoPropertyPage_UnknownRuntime_label);
        }

        Control[] children = parametersParent.getChildren();
        boolean savePassword = true;
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                ConfigParameter configParameter = null;
                if (selectedServer != null) {
                    configParameter =
                            selectedServer.getServerConfig()
                                    .getConfigParameter(paramInfo.getKey());
                }
                if (configParameter == null) {
                    // this may happen if new parameters are added
                    // in other words this migrates user's config to new
                    // versions.
                    configParameter =
                            DeployPackage.eINSTANCE.getDeployFactory()
                                    .createConfigParameter();
                    configParameter.setConfigParameterInfo(paramInfo);
                    configParameter.setKey(paramInfo.getKey());
                    configParameter.setValue(paramInfo.getDefaultValue());
                    selectedServer.getServerConfig().getConfigParameters()
                            .add(configParameter);
                }
                String value = configParameter.getValue();
                if (paramInfo.getParameterType() == ConfigParameterType.STRING_LITERAL) {
                    ((StringControl) control).setText(value);
                }
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    if (value == null) {
                        value = ""; //$NON-NLS-1$
                        savePassword = false;
                    } else {
                        try {
                            value = EncryptionUtil.decrypt(value);
                        } catch (IllegalArgumentException e) {
                            String title =
                                    Messages.ServerInfoPropertyPage_ServerParameterProblem_title;
                            String message =
                                    String.format(Messages.ServerInfoPropertyPage_CorruptedEncryptedField_message,
                                            paramInfo.getName());
                            MessageDialog.openError(getShell(), title, message);
                            value = ""; //$NON-NLS-1$
                        }
                    }
                    ((Text) control).setText(value != null ? value : ""); //$NON-NLS-1$
                }
                if (paramInfo.getParameterType() == ConfigParameterType.BOOLEAN_LITERAL
                        && control instanceof Button) {
                    ((Button) control)
                            .setSelection(Boolean.parseBoolean(value));
                }
                if (paramInfo.getParameterType() == ConfigParameterType.FILE_LITERAL
                        && control instanceof FileControl) {
                    ((FileControl) control).setText(value != null ? value : ""); //$NON-NLS-1$
                }
            }
        }

        // performOk();
    }

    @Override
    public boolean performOk() {
        selectedServer.setName(nameText.getText().trim());
        applyChangesToServerConfig(selectedServer);
        DeployUIActivator.getServerManager().saveServerContainer();
        /*
         * SCF-75: check and connect server immediately
         */
        checkAndConnectServerImmediately();
        return true;
    }

    /**
     * Checks if the user wants to connect immediately to the server whose pref
     * page is open. If yes then connects to the server.
     */
    private void checkAndConnectServerImmediately() {
        /*
         * Connect immediately if the checkimmediately button is present(i.e.
         * created) and it is selected.
         */
        if (connectImmediately != null && connectImmediately.getSelection()) {

            if (null != PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().getActivePart()) {

                /*
                 * get the workbench active part.
                 */
                IWorkbenchPart activePart =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage().getActivePart();

                if (activePart instanceof CommonNavigator) {
                    CommonNavigator navigator = (CommonNavigator) activePart;
                    /*
                     * get the viewer and the selection
                     */
                    final CommonViewer commonViewer =
                            navigator.getCommonViewer();

                    if (commonViewer.getSelection() instanceof IStructuredSelection) {
                        /*
                         * Calling the 'ConnectServerAction' for 2 reasons , 1.
                         * Code reusability, 2. ConnectServerAction has
                         * everything that we need to connect the server(i.e.
                         * Monitor based job, exception handling etc)
                         */
                        ConnectServerAction connectServerAction =
                                new ConnectServerAction(commonViewer) {
                                    /**
                                     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
                                     * 
                                     * @return
                                     */
                                    @Override
                                    public IStructuredSelection getStructuredSelection() {

                                        return (IStructuredSelection) commonViewer
                                                .getSelection();
                                    }
                                };

                        connectServerAction.run();
                    }
                }
            }
        }
    }

    /**
     * This method is used to test connection as the "Apply" button is reused as
     * "Test Connection".
     * 
     */
    @Override
    protected void performApply() {
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
            public void run() {
                // save details then check connection
                // try connecting to server using new details
                Server tempServer = getTestableServer();

                TestableConnection tConn =
                        DeployUtil.getTestableConnection(tempServer
                                .getConnection());
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
                                .openError(getShell(),
                                        Messages.ServerInfoPropertyPage_TestConnection_label,
                                        Messages.ServerParametersPage_TestConnectionFailed_message,
                                        status);
                    } else {
                        // INFO, WARNING
                        ErrorDialog
                                .openError(getShell(),
                                        Messages.ServerInfoPropertyPage_TestConnection_label,
                                        status.getMessage(),
                                        status);
                    }
                } else {
                    try {
                        tempServer.getConnection().connect();
                        if (tempServer.getConnection().isConnected()) {
                            // show success message if connected and then
                            // disconnect to
                            // free connections
                            MessageDialog
                                    .openInformation(getShell(),
                                            Messages.ServerInfoPropertyPage_TestConnection_label,
                                            Messages.ServerInfoPropertyPage_TestConnection_success_message);
                            tempServer.getConnection().disconnect();
                        } else { // show error message if not connected
                            MessageDialog
                                    .openInformation(getShell(),
                                            Messages.ServerInfoPropertyPage_TestConnection_label,
                                            Messages.ServerInfoPropertyPage_TestConnection_error_message);
                        }
                    } catch (ConnectionException e) {
                        // show error message if exception occurred when
                        // trying
                        // to
                        // connect
                        MessageDialog
                                .openInformation(getShell(),
                                        Messages.ServerInfoPropertyPage_TestConnection_label,
                                        Messages.ServerInfoPropertyPage_TestConnection_error_message);
                    }
                }
            }
        });
    }

    /**
     * @param selectedServer2
     * @return
     */
    private Server cloneServer(Server sourceServer) {
        DeployFactory factory = DeployFactory.eINSTANCE;
        Server newServer = factory.createServer();
        newServer.setServerType(sourceServer.getServerType());
        newServer.setRuntime(sourceServer.getRuntime());

        // deep copy of repository
        Repository newRepository = factory.createRepository();
        RepositoryConfig newRepositoryConfig = factory.createRepositoryConfig();
        for (ConfigParameter parameter : sourceServer.getRepository()
                .getRepositoryConfig().getConfigParameters()) {
            ConfigParameter newParameter = factory.createConfigParameter();
            newParameter.setConfigParameterInfo(parameter
                    .getConfigParameterInfo());
            newParameter.setKey(parameter.getKey());
            newParameter.setValue(parameter.getValue());
            newRepositoryConfig.getConfigParameters().add(newParameter);
        }
        newRepository.setRepositoryType(sourceServer.getRepository()
                .getRepositoryType());
        newRepository.setRepositoryConfig(newRepositoryConfig);
        newServer.setRepository(newRepository);

        // deep copy of serverConfig
        ServerConfig newServerConfig = factory.createServerConfig();
        for (ConfigParameter parameter : sourceServer.getServerConfig()
                .getConfigParameters()) {
            ConfigParameter newParameter = factory.createConfigParameter();
            newParameter.setConfigParameterInfo(parameter
                    .getConfigParameterInfo());
            newParameter.setKey(parameter.getKey());
            newParameter.setValue(parameter.getValue());
            newServerConfig.getConfigParameters().add(newParameter);

        }
        newServer.setServerConfig(newServerConfig);

        return newServer;
    }

    @SuppressWarnings("unchecked")
    private void applyChangesToServerConfig(Server server) {
        if (parametersParent == null)
            return;
        ServerConfig serverConfig = server.getServerConfig();
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameterInfo paramInfo =
                        (ConfigParameterInfo) control.getData(PARAMETER_INFO);
                String key = paramInfo.getKey();
                ConfigParameter configParam =
                        serverConfig.getConfigParameter(key);
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
                if (paramInfo.getParameterType() == ConfigParameterType.PASSWORD_LITERAL) {
                    if (save != null && save.getSelection()) {
                        value = EncryptionUtil.encrypt(value);
                    } else {
                        value = null;
                    }
                }
                if (control instanceof FileControl) {
                    value = ((FileControl) control).getText();
                }
                configParam.setValue(value);
                configParam.setConfigParameterInfo(paramInfo);
            }
        }
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

    protected Text createText(Composite parent, String defaultText) {
        Text textFeld = new Text(parent, SWT.SINGLE | SWT.BORDER);
        textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textFeld.setText(defaultText);
        return textFeld;
    }

    protected Button createCheckbox(Composite parent, boolean defaultValue) {
        Button button = new Button(parent, SWT.CHECK);
        button.setSelection(defaultValue);
        return button;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        // change apply button to be test connection
        getApplyButton()
                .setText(Messages.ServerInfoPropertyPage_TestConnection_label);
        getApplyButton().setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));

    }

    private class RepositoryTypeFilter extends ViewerFilter {
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof RepositoryType
                    && selectedServer.getServerType() != null
                    && selectedServer.getServerType()
                            .getSupportedRepositoryTypes().contains(element)) {
                return true;
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Server getTestableServer() {
        Server testableServer = cloneServer(selectedServer);
        testableServer.setName(nameText.getText().trim());
        applyChangesToServerConfig(testableServer);
        return testableServer;
    }
}