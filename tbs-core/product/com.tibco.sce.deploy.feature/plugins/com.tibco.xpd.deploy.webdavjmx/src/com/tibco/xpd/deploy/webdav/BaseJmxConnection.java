/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.client.AbstractResourceHandle;
import org.eclipse.webdav.client.CollectionHandle;
import org.eclipse.webdav.client.DAVClient;
import org.eclipse.webdav.internal.kernel.DAVException;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ContainerElement;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;
import com.tibco.xpd.deploy.ServerModule;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.impl.OperationImpl;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.model.extension.LateBindingSupport;
import com.tibco.xpd.deploy.model.extension.ResourceBinding;
import com.tibco.xpd.deploy.model.extension.ResourceBindingImpl;
import com.tibco.xpd.deploy.model.extension.SharedResource;
import com.tibco.xpd.deploy.model.extension.SharedResourceImpl;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus.Severity;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.PasswordDialog;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * Extends the WebDAV connection adding the additional JMX needs.
 * 
 * @author tstephen
 * @author glewis
 */
public class BaseJmxConnection implements Connection, LateBindingSupport {

    private final Server server;

    private String username;

    private String password;

    private boolean connected;

    private String connUrl = null;

    private final Logger log = DeployUIActivator.getDefault().getLogger();

    private String deDomainName;

    private String deObjectType;

    private JMXConnector jmxConnector = null;

    private final JMXConnector jmxConnector2 = null;

    private static final String URL_PARAM = "url"; //$NON-NLS-1$

    private ServerElementType fileType;

    private ServerElementState filePublishedState;

    private ServerElementType dirType;

    private ServerElementState dirPublishedState;

    private OperationImpl undeployOperation;

    private OperationImpl renotifyOperation;

    private OperationImpl deleteOperation;

    private static final String JMX_REMOTE_CREDENTIALS =
            "jmx.remote.credentials"; //$NON-NLS-1$

    private static final String stringParam = "java.lang.String"; //$NON-NLS-1$

    private static final String MBEAN_DEPLOY_OPERATION_NAME = "deploy"; //$NON-NLS-1$   

    private static final String MBEAN_UNDEPLOY_OPERATION_NAME = "undeploy"; //$NON-NLS-1$

    private final ArrayList<String> validExtensionsArr =
            new ArrayList<String>();

    private final BaseJmxDeploymentData deploymentData =
            new BaseJmxDeploymentData();

    private WebDAVServer webDavServer = null;

    public BaseJmxConnection(Server server) {
        this.server = server;
        initialiseServerElementTypes();
    }

    /** {@inheritDoc} */
    public final void connect() throws ConnectionException {
        connected = false;
        connUrl = null;

        String hostport =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.HOST_PORT);
        String path =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.PATH);
        String jmxConnectorType =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.MBEAN_CONNECTOR_TYPE,
                        server);
        username =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.USERNAME);
        password =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.PASSWORD);
        deDomainName =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.DOMAIN_NAME,
                        server);
        deObjectType =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.OBJECT_TYPE,
                        server);

        // find the valid extensions that this can have as an input file for
        // deployment
        String validExtsStr[] =
                BaseJmxConstants.getConfigParamValue(server.getServerConfig(),
                        BaseJmxConstants.VALID_EXTS,
                        server).split(" "); //$NON-NLS-1$
        for (String strExt : validExtsStr) {
            validExtensionsArr.add(strExt.trim());
        }

        if (username != null && username.length() != 0) {
            if (password == null) {
                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        Shell shell = Display.getDefault().getActiveShell();
                        PasswordDialog dialog = new PasswordDialog(shell);
                        dialog.setBlockOnOpen(true);
                        if (dialog.open() == PasswordDialog.OK) {
                            password = dialog.getPassword();
                        }
                    }

                });
                if (password == null) {
                    return;
                }
            } else {
                try {
                    password = EncryptionUtil.decrypt(password);
                } catch (IllegalArgumentException e) {
                    throw new ConnectionException(
                            Messages.BaseJmxConnection_ConnectionFailedOutOfDatePasswordMessage,
                            e);
                }
            }
        } else {
            password = ""; //$NON-NLS-1$
        }

        HashMap env = new HashMap();
        String[] credentials = new String[] { username, password };
        env.put(JMX_REMOTE_CREDENTIALS, credentials);

        if ("rmi".equals(jmxConnectorType)) { //$NON-NLS-1$
            connUrl = "service:jmx:" + jmxConnectorType //$NON-NLS-1$
                    + ":///jndi/" + jmxConnectorType + "://" + hostport //$NON-NLS-1$ //$NON-NLS-2$
                    + path;
        } else {
            if (!"ws-secure".equals(jmxConnectorType) //$NON-NLS-1$
                    && !"ws".equals(jmxConnectorType)) { //$NON-NLS-1$
                // Assume in this case that path contains the whole URL
                // This is a fallback approach and as only RMI and WS connectors
                // have been tested may well fail.
                String message =
                        String
                                .format(Messages.BaseJmxConnection_unsupportedJmxConnector_msg,
                                        jmxConnectorType);
                log.warn(message);
            }
            // http://java.sun.com/javase/technologies/core/mntr-mgmt/
            // javamanagement/JSR262_Interop.pdf
            connUrl = "service:jmx:" + jmxConnectorType //$NON-NLS-1$
                    + "://" + hostport + path; //$NON-NLS-1$
        }

        JMXServiceURL url;
        try {
            url = new JMXServiceURL(connUrl);

            log.debug(String.format("Connecting (%s) ...", connUrl)); //$NON-NLS-1$

            jmxConnector = JMXConnectorFactory.connect(url, env);

            // set up the webdav server for the current settings in this
            // connection
            setupWebDavServer();
        } catch (MalformedURLException e) {
            log.error(e, "MalformedURLException: " + connUrl); //$NON-NLS-1$           
            String message =
                    String
                            .format(Messages.BaseJmxConnection_ConnectionFailedIncorrectParametersMessage,
                                    connUrl);
            throw new ConnectionException(message, e);
        } catch (IOException e) {
            log.error(e); // only debug because it's quite
            String message =
                    String
                            .format(Messages.BaseJmxConnection_ConnectionFailedIOExceptionMessage,
                                    connUrl);
            throw new ConnectionException(message, e);
        } catch (Exception e) {
            log.error(e);
            String message = ""; //$NON-NLS-1$
            if (e.getLocalizedMessage() != null
                    && e.getLocalizedMessage().trim().length() > 0) {
                message = e.getLocalizedMessage();
            } else if (e.getMessage() != null
                    && e.getMessage().trim().length() > 0) {
                message = e.getMessage();
            }
            throw new ConnectionException(message);
        }
        connected = true;
        server.setServerState(ServerState.CONNECTED_LITERAL);
    }

    /**
     * @throws ConnectionException
     */
    private void setupWebDavServer() throws ConnectionException {
        Repository repo = server.getRepository();
        if (repo != null) {
            ConfigParameter siteUrlParam =
                    repo.getRepositoryConfig()
                            .getConfigParameter(DeploymentData.SITE_URL);
            ConfigParameter usernameParam =
                    repo.getRepositoryConfig()
                            .getConfigParameter(DeploymentData.USERNAME);
            ConfigParameter passwordParam =
                    repo.getRepositoryConfig()
                            .getConfigParameter(DeploymentData.PASSWORD);
            if (siteUrlParam == null) {
                throw new IllegalArgumentException(
                        Messages.BaseJmxConnection_RepoNeedsSiteUrlMessage);
            }
            if (usernameParam == null) {
                throw new IllegalArgumentException(
                        Messages.BaseJmxConnection_RepoNeedsUsernameMessage);
            }
            if (passwordParam == null) {
                throw new IllegalArgumentException(
                        Messages.BaseJmxConnection_RepoNeedsPasswordMessage);
            }
            String wdPass;
            String defaultVal =
                    passwordParam.getConfigParameterInfo().getDefaultValue();
            if (defaultVal.equals(passwordParam.getValue())) {
                wdPass = defaultVal;
            } else {
                wdPass = EncryptionUtil.decrypt(passwordParam.getValue());
            }
            String siteURL = siteUrlParam.getValue();
            boolean isValidSiteURL = true;
            try {
                webDavServer =
                        new WebDAVServer(siteURL, usernameParam.getValue(),
                                wdPass);
                webDavServer.getSiteHandle().getMembers();
            } catch (DAVException e) {
                isValidSiteURL = false;
            }

            if (!isValidSiteURL) {
                boolean isPortPath = false;
                Path path = new Path(siteURL);
                while (!isPortPath) {
                    try {
                        webDavServer =
                                new WebDAVServer(siteURL, usernameParam
                                        .getValue(), wdPass);
                        webDavServer.getSiteHandle().getMembers();
                        DAVClient client =
                                webDavServer.getSiteHandle().getDAVClient();

                        Path extraDirsPath =
                                new Path(siteUrlParam.getValue()
                                        .replace(siteURL, "")); //$NON-NLS-1$
                        for (int i = 0; i < extraDirsPath.segmentCount(); i++) {
                            String dir = extraDirsPath.segment(i);
                            String newLocation =
                                    new Path(siteURL).append(dir)
                                            .toPortableString();
                            // path changes the port to have :: so we replace
                            // this back to :
                            newLocation = newLocation.replace("::", ":"); //$NON-NLS-1$ //$NON-NLS-2$
                            ILocator resourceLocator =
                                    client.getDAVFactory()
                                            .newStableLocator(newLocation);
                            CollectionHandle collectionHandle =
                                    new CollectionHandle(client,
                                            resourceLocator);
                            collectionHandle.mkdirs();
                            webDavServer =
                                    new WebDAVServer(newLocation, usernameParam
                                            .getValue(), wdPass);
                            webDavServer.getSiteHandle().getMembers();
                        }
                        isPortPath = true;
                    } catch (DAVException e) {
                        siteURL = path.removeLastSegments(1).toString();
                        path = new Path(siteURL);
                        if (path.segmentCount() <= 1) {
                            isPortPath = true;
                            throw new ConnectionException(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * @return
     */
    private ObjectName getObjectName() throws MalformedObjectNameException,
            NullPointerException {
        return new ObjectName(deDomainName + ":type=" + deObjectType); //$NON-NLS-1$        
    }

    /**
     * @param operationName
     * @param params
     * @param signitures
     * @return
     * @throws Exception
     */
    protected String callJmxMethod(ObjectName objectName, String operationName,
            Object[] params, String[] signitures) throws Exception {
        String returnMsg =
                (String) jmxConnector.getMBeanServerConnection()
                        .invoke(objectName, operationName, params, signitures);
        return returnMsg;
    }

    /**
     * @param url
     * @param operationName
     * @param params
     * @param signitures
     * @return
     * @throws Exception
     */
    protected String callDeploy(String url, String operationName,
            Object[] params, String[] signitures) throws Exception {
        return callJmxMethod(getObjectName(), operationName, params, signitures);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.Connection#deployModule(java.lang
     * .String)
     */
    public DeploymentStatus deployModule(String url) throws DeploymentException {

        Severity deployStatus = DeploymentSimpleStatus.Severity.OK;
        String resultMessage =
                String.format(Messages.BaseJmxConnection_DeployingMessage, url);

        try {
            // DeployAction has already pushed the file to repo by now
            // so poke the server to deploy it
            if (deploymentData.isJmxNotificationsEnabled()) {
                try {

                    String returnMsg =
                            callDeploy(url,
                                    MBEAN_DEPLOY_OPERATION_NAME,
                                    new Object[] { url },
                                    new String[] { stringParam });
                    if (returnMsg.length() > 0) {
                        resultMessage += returnMsg + "\n"; //$NON-NLS-1$        
                        deployStatus = DeploymentSimpleStatus.Severity.ERROR;
                    }
                } catch (Exception e) {
                    deployStatus = DeploymentSimpleStatus.Severity.ERROR;
                }

                // If deployment was successful then create the deployment
                // record
                if (deployStatus != DeploymentSimpleStatus.Severity.ERROR) {
                    BaseJmxDeploymentRecord deployRec =
                            new BaseJmxDeploymentRecord(webDavServer);
                    deployRec.createRecord(deDomainName, url);
                }
            }
        } catch (Exception e) {
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, resultMessage, e);
        }

        return new DeploymentSimpleStatus(deployStatus, resultMessage, null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.Connection#disconnect()
     */
    public void disconnect() throws ConnectionException {
        connected = false;
        server.setServerState(ServerState.DISCONNECTED_LITERAL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.Connection#getServer()
     */
    public Server getServer() {
        return server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.Connection#isConnected()
     */
    public boolean isConnected() throws ConnectionException {
        return connected;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.Connection#performServerElementOperation
     * (com.tibco.xpd.deploy.ServerElement, com.tibco.xpd.deploy.Operation)
     */
    public Object performServerElementOperation(ServerElement serverElement,
            Operation operation) throws DeploymentException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.Connection#refreshServerContent()
     */
    public void refreshServerContent() throws ConnectionException {
        if (isConnected() && webDavServer != null) {
            try {
                refreshServer(webDavServer.getSiteHandle());
            } catch (DAVException e) {
                server.getServerElements().clear();
                disconnect();
                throw new ConnectionException(e);
            }
        } else {
            server.getServerElements().clear();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.Connection#validateModule(java.lang
     * .String)
     */
    public boolean validateModule(String url) throws ConnectionException {
        if (url != null) {
            if (isValidFileExtension(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param url
     * @return
     */
    private boolean isValidFileExtension(String url) {
        if (url != null) {
            int index = url.lastIndexOf('.');
            String urlExt = url.substring(index);
            if (validExtensionsArr.contains(urlExt)) {
                return true;
            }
        }
        return false;
    }

    public Object getAdapter(Class adapter) {
        if (LateBindingSupport.class == adapter)
            return this;
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /*
     * ===================================================== METHOD :
     * initialiseServerElementTypes
     * =====================================================
     */
    /**
     * Creates the right click menus for the deployment server elements This
     * includes the Renotify and Undeploy operations
     * 
     */
    @SuppressWarnings("unchecked")
    private void initialiseServerElementTypes() {
        DeployFactory f = DeployFactory.eINSTANCE;
        fileType = f.createServerElementType();
        dirType = f.createServerElementType();

        // states. States should not be shared between different element types
        filePublishedState = f.createServerElementState();
        dirPublishedState = f.createServerElementState();
        filePublishedState.setName("Published");
        dirPublishedState.setName("Published");

        // all states server element type could be in
        fileType.getStates().add(filePublishedState);
        dirType.getStates().add(dirPublishedState);

        // operations
        undeployOperation = new OperationImpl() {
            @Override
            public Object execute(ServerElement serverElement)
                    throws DeploymentException {
                try {
                    return undeploy(serverElement);
                } finally {
                    refreshServerContent();
                }
            }
        };
        undeployOperation.setName(Messages.BaseJmxConnection_UnDeployMessage);

        renotifyOperation = new OperationImpl() {
            @Override
            public Object execute(ServerElement serverElement)
                    throws DeploymentException {
                try {
                    return renotify(serverElement);
                } finally {
                    refreshServerContent();
                }
            }
        };
        renotifyOperation.setName(Messages.BaseJmxConnection_RenotifyMessage);

        deleteOperation = new OperationImpl() {
            @Override
            public Object execute(ServerElement serverElement)
                    throws DeploymentException {
                try {
                    return delete(serverElement);
                } finally {
                    refreshServerContent();
                }
            }
        };
        deleteOperation.setName(Messages.BaseJmxConnection_DeleteMessage);

        // available operations for server element type
        fileType.getOperations().add(undeployOperation);
        // possible operations for states
        filePublishedState.getPossibleOperations().add(undeployOperation);

        fileType.getOperations().add(deleteOperation);
        // possible operations for states
        filePublishedState.getPossibleOperations().add(deleteOperation);

        dirType.getOperations().add(deleteOperation);
        // possible operations for states
        dirPublishedState.getPossibleOperations().add(deleteOperation);

        // if (deploymentData.isJmxNotificationsEnabled())
        // {
        // fileType.getOperations().add(renotifyOperation);
        // publishedState.getPossibleOperations().add(renotifyOperation);
        // }
    }

    /*
     * ===================================================== METHOD : delete
     * =====================================================
     */
    /**
     * Performs a a delete on a given Server Element (Does not notify any
     * component of removal)
     * 
     * @param serverElement
     *            Element to be undeployed
     * @return Deployment status object
     * @throws DeploymentException
     */
    protected Object delete(ServerElement serverElement)
            throws DeploymentException {
        String url = serverElement.getConfigParameter(URL_PARAM).getValue();
        String deletingMsg =
                String.format(Messages.BaseJmxConnection_DeletingMessage, url);
        Severity deployStatus = DeploymentSimpleStatus.Severity.OK;

        IResponse response = null;
        try {
            response = webDavServer.deleteFile(url);
        } catch (IOException e) {
            throw new DeploymentException(e);
        }

        int statusCode = response.getStatusCode();
        String statusMessage = response.getStatusMessage();
        String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$
        if ((statusCode != IResponse.SC_NO_CONTENT)
                && (statusCode != IResponse.SC_OK)) {
            throw new DeploymentException(deletingMsg + responseMessage);
        }

        // Also delete the deployment record for this file
        BaseJmxDeploymentRecord deployRec =
                new BaseJmxDeploymentRecord(webDavServer);
        deployRec.removeRecord(url);

        // Now notify that the file has been copied
        return new DeploymentSimpleStatus(deployStatus, deletingMsg
                + responseMessage, null);
    }

    protected String callUndeploy(String url, String operationName,
            Object[] params, String[] signitures) throws Exception {
        return callJmxMethod(getObjectName(), operationName, params, signitures);
    }

    /*
     * ===================================================== METHOD : undeploy
     * =====================================================
     */
    /**
     * Performs an undeploy on a given Server Element
     * 
     * @param serverElement
     *            Element to be undeployed
     * @return Deployment status object
     * @throws DeploymentException
     */
    protected Object undeploy(ServerElement serverElement)
            throws DeploymentException {
        String url = serverElement.getConfigParameter(URL_PARAM).getValue();
        String deletingMsg =
                String.format(Messages.BaseJmxConnection_UndeployingMessage,
                        url);
        Severity deployStatus = DeploymentSimpleStatus.Severity.OK;

        // Send the undeploy message first and delete the file afterwards
        if (deploymentData.isJmxNotificationsEnabled()) {
            // Find out which component this object was deployed to
            BaseJmxDeploymentRecord deployRec =
                    new BaseJmxDeploymentRecord(webDavServer);
            // List<String> targets =
            // deployRec.getComponentForDeployedObject(url);

            try {
                // for (String target : targets)
                // {
                String returnMsg =
                        callUndeploy(url,
                                MBEAN_UNDEPLOY_OPERATION_NAME,
                                new Object[] { url },
                                new String[] { stringParam });
                if (returnMsg.length() > 0) {
                    deletingMsg += "\n" + returnMsg; //$NON-NLS-1$
                    deployStatus = DeploymentSimpleStatus.Severity.ERROR;
                    throw new Exception(" "); //$NON-NLS-1$
                }
                // }
            } catch (Exception e) {
                throw new DeploymentException(deletingMsg + e);
            }
        }

        IResponse response = null;
        try {
            response = webDavServer.deleteFile(url);
        } catch (IOException e) {
            throw new DeploymentException(e);
        }

        int statusCode = response.getStatusCode();
        String statusMessage = response.getStatusMessage();
        String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$
        if ((statusCode != IResponse.SC_NO_CONTENT)
                && (statusCode != IResponse.SC_OK)) {
            throw new DeploymentException(deletingMsg + responseMessage);
        }

        // Also delete the deployment record for this file
        BaseJmxDeploymentRecord deployRec =
                new BaseJmxDeploymentRecord(webDavServer);
        deployRec.removeRecord(url);

        // Now notify that the file has been copied
        return new DeploymentSimpleStatus(deployStatus, deletingMsg
                + responseMessage, null);
    }

    /*
     * ===================================================== METHOD : renotify
     * =====================================================
     */
    /**
     * Send a deployment renotify message for a given Server Element
     * 
     * @param serverElement
     *            Element to issue renotify on
     * @return Deployment status object
     * @throws DeploymentException
     */
    protected Object renotify(ServerElement serverElement)
            throws DeploymentException {
        if (!deploymentData.isJmxNotificationsEnabled()) {
            return null;
        }

        String url = serverElement.getConfigParameter(URL_PARAM).getValue();
        String renotifyMsg =
                String.format(Messages.BaseJmxConnection_RenotifyingMessage,
                        url);
        Severity deployStatus = DeploymentSimpleStatus.Severity.OK;

        // Find out which component this object was deployed to
        BaseJmxDeploymentRecord deployRec =
                new BaseJmxDeploymentRecord(webDavServer);
        try {
            String returnMsg =
                    callJmxMethod(getObjectName(),
                            MBEAN_DEPLOY_OPERATION_NAME,
                            new Object[] { url },
                            new String[] { stringParam });
            if (returnMsg.length() > 0) {
                renotifyMsg += returnMsg + "\n"; //$NON-NLS-1$
                deployStatus = DeploymentSimpleStatus.Severity.ERROR;
            }
        } catch (Exception e) {
            throw new DeploymentException(renotifyMsg + e);
        }

        // Now notify that the file has been copied
        return new DeploymentSimpleStatus(deployStatus, renotifyMsg, null);
    }

    /*
     * ===================================================== METHOD :
     * refreshServer =====================================================
     */
    /**
     * Re-construct the directory and element display structure for the
     * Deployment Server
     * 
     * @param site
     *            Handle to the display list
     * @throws DAVException
     */
    @SuppressWarnings("unchecked")
    private void refreshServer(CollectionHandle site) throws DAVException {
        boolean showSubfolders = true;
        HashSet<String> existingFileNames = new HashSet<String>();
        HashSet<String> existingDirNames = new HashSet<String>();
        Set<AbstractResourceHandle> members = site.getMembers();
        for (Iterator<ServerElement> iter =
                server.getServerElements().iterator(); iter.hasNext();) {
            ServerElement se = iter.next();
            String name = se.getName();
            if (se instanceof ModuleContainer) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        setCollectionProperties((ModuleContainer) se, member);
                        if (showSubfolders) {
                            refreshCollection((CollectionHandle) member,
                                    (ContainerElement) se);
                        }
                        exist = true;
                        existingDirNames.add(name);
                    }
                }
                if (!exist) {
                    iter.remove();
                }
            } else if (se instanceof ServerModule) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (!isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        setModuleProperties((ServerModule) se, member);
                        exist = true;
                        existingFileNames.add(name);
                    }
                }
                if (!exist) {
                    iter.remove();
                }
            }
        }
        for (AbstractResourceHandle member : members) {
            String memberName = getMemberName(member);
            if (isCollectionMember(member)) {
                if (!existingDirNames.contains(memberName)
                        && memberName.trim().length() > 0) {
                    ModuleContainer moduleContainer =
                            DeployFactory.eINSTANCE.createModuleContainer();
                    moduleContainer.setName(memberName);
                    if (showSubfolders) {
                        refreshCollection((CollectionHandle) member,
                                moduleContainer);
                    }
                    server.getServerElements().add(moduleContainer);
                    setCollectionProperties(moduleContainer, member);
                }
            } else {
                if (!existingFileNames.contains(memberName)
                        && memberName.trim().length() > 0) {
                    ServerModule module =
                            DeployFactory.eINSTANCE.createServerModule();
                    module.setName(memberName);
                    setModuleProperties(module, member);
                    server.getServerElements().add(module);
                }
            }
        }
    }

    /*
     * ===================================================== METHOD :
     * isCollectionMember =====================================================
     */
    /**
     * Find if the member is of type CollectionHandle
     * 
     * @param member
     *            Member to chaek
     * @return true if of type CollectionHandle, otherwise false
     */
    private static boolean isCollectionMember(AbstractResourceHandle member) {
        return member instanceof CollectionHandle;
    }

    /*
     * ===================================================== METHOD :
     * getMemberName =====================================================
     */
    /**
     * Gets the member name from the Resourse handle
     * 
     * @param member
     *            Resourse to get the name for
     * @return The name of the member
     */
    private static String getMemberName(AbstractResourceHandle member) {
        if (BaseJmxDeploymentRecord.isObjectViewable(member.getLocator()
                .getResourceURL()) != true) {
            return ""; //$NON-NLS-1$
        }
        return getLastSegment(member.getLocator().getResourceURL(), '/');
    }

    /*
     * ===================================================== METHOD :
     * setModuleProperties =====================================================
     */
    /**
     * Sets the operations that can be performed on the given member
     * 
     * @param se
     *            Service Module
     * @param member
     *            Resource member
     */
    @SuppressWarnings("unchecked")
    private void setModuleProperties(ServerModule se,
            AbstractResourceHandle member) {
        DeployFactory f = DeployFactory.eINSTANCE;
        ConfigParameter configParameter = se.getConfigParameter(URL_PARAM);
        String resourceURL = member.getLocator().getResourceURL();
        if (configParameter != null) {
            configParameter.setValue(resourceURL);
        } else {
            ConfigParameter parameter = f.createConfigParameter();
            parameter.setKey(URL_PARAM);
            parameter.setValue(resourceURL);
            se.getParameters().add(parameter);
        }
        se.setServerElementType(fileType);
        se.setServerElementState(filePublishedState);
    }

    /*
     * ===================================================== METHOD :
     * setCollectionProperties
     * =====================================================
     */
    /**
     * Sets the operations that can be performed on the given member
     * 
     * @param se
     *            Module Container
     * @param member
     *            Resource member
     */
    @SuppressWarnings("unchecked")
    private void setCollectionProperties(ModuleContainer se,
            AbstractResourceHandle member) {
        DeployFactory f = DeployFactory.eINSTANCE;
        ConfigParameter configParameter = se.getConfigParameter(URL_PARAM);
        String resourceURL = member.getLocator().getResourceURL();
        if (configParameter != null) {
            configParameter.setValue(resourceURL);
        } else {
            ConfigParameter parameter = f.createConfigParameter();
            parameter.setKey(URL_PARAM);
            parameter.setValue(resourceURL);
            se.getParameters().add(parameter);
        }
        se.setServerElementType(dirType);
        se.setServerElementState(dirPublishedState);
    }

    /*
     * ===================================================== METHOD :
     * refreshCollection =====================================================
     */
    /**
     * Re-generates the list of directories and files on the deployment server
     * 
     * @param collection
     * @param container
     * @throws DAVException
     */
    @SuppressWarnings("unchecked")
    private void refreshCollection(CollectionHandle collection,
            ContainerElement container) throws DAVException {
        HashSet<String> existingFileNames = new HashSet<String>();
        HashSet<String> existingDirNames = new HashSet<String>();
        Set<AbstractResourceHandle> members = collection.getMembers();
        for (Iterator<ServerElement> iter = container.getChildren().iterator(); iter
                .hasNext();) {
            ServerElement se = iter.next();
            String name = se.getName();
            if (se instanceof ModuleContainer) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        setCollectionProperties((ModuleContainer) se, member);
                        refreshCollection((CollectionHandle) member,
                                (ContainerElement) se);
                        exist = true;
                        existingDirNames.add(name);
                    }
                }
                if (!exist) {
                    iter.remove();
                }
            } else if (se instanceof ServerModule) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (!isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        setModuleProperties((ServerModule) se, member);
                        exist = true;
                        existingFileNames.add(name);
                    }
                }
                if (!exist) {
                    iter.remove();
                }
            } else {
                Assert
                        .isTrue(false,
                                String
                                        .format(Messages.BaseJmxConnection_ElementNotCorrectType,
                                                se.toString()));
            }
        }

        for (AbstractResourceHandle member : members) {
            String memberName = getMemberName(member);

            if (memberName == null || memberName.length() == 0) {
                continue;
            }

            if (isCollectionMember(member)) {
                if (!existingDirNames.contains(memberName)) {
                    ModuleContainer moduleContainer =
                            DeployFactory.eINSTANCE.createModuleContainer();
                    moduleContainer.setName(memberName);
                    refreshCollection((CollectionHandle) member,
                            moduleContainer);
                    container.getChildren().add(moduleContainer);
                    setCollectionProperties(moduleContainer, member);
                }
            } else {
                if (!existingFileNames.contains(memberName)) {
                    ServerModule module =
                            DeployFactory.eINSTANCE.createServerModule();
                    module.setName(memberName);
                    setModuleProperties(module, member);
                    container.getChildren().add(module);
                }
            }
        }
    }

    /*
     * ===================================================== METHOD :
     * getLastSegment =====================================================
     */
    /**
     * Gets the last segment identified by the given seperator from a string
     * 
     * @param path
     *            String to get the last segment from
     * @param seperator
     *            Seperator to determin the last segment
     * @return The last segment string
     */
    private static String getLastSegment(String path, char seperator) {
        int index = path.lastIndexOf(seperator);
        if (index == -1) {
            return path;
        }
        if (index == path.length() - 1) {
            return getLastSegment(path.substring(0, index), seperator);
        }
        return path.substring(index + 1);
    }

    /** {@inheritDoc} */
    public DeploymentStatus applyModuleBindings(URL localURL,
            Collection<ResourceBinding> bindings) {
        System.out.println("Applying bindings to: " + localURL);
        for (ResourceBinding resourceBinding : bindings) {
            System.out.println("Binding :" + resourceBinding);
        }
        return new DeploymentSimpleStatus();
    }

    /** {@inheritDoc} */
    public Collection<ResourceBinding> getModuleBindings(URL localURL) {
        List<ResourceBinding> bindings = new ArrayList<ResourceBinding>();
        bindings.add(new ResourceBindingImpl("part1", "participant 1",
                "PARTICIPANT", localURL));
        bindings.add(new ResourceBindingImpl("part2", "participant 2",
                "PARTICIPANT", localURL));
        bindings.add(new ResourceBindingImpl("end1", "endpoint 1", "ENDPOINT",
                localURL));
        bindings.add(new ResourceBindingImpl("end2", "endpoint 2", "ENDPOINT",
                localURL));
        return bindings;
    }

    /** {@inheritDoc} */
    public Collection<SharedResource> getSharedResources(Map parameters) {
        List<SharedResource> resources = new ArrayList<SharedResource>();
        resources.add(new SharedResourceImpl("remotePart1",
                "particiapant remote 1", "PARTICIPANT"));
        resources.add(new SharedResourceImpl("remotePart2",
                "particiapant remote 2", "PARTICIPANT"));
        resources.add(new SharedResourceImpl("remotePart3",
                "particiapant remote 3", "PARTICIPANT"));
        resources.add(new SharedResourceImpl("remotePart4",
                "particiapant remote 4", "PARTICIPANT"));
        resources.add(new SharedResourceImpl("remotePart5",
                "particiapant remote 5", "PARTICIPANT"));
        resources.add(new SharedResourceImpl("endpointRemote1",
                "endpoint remote 1", "ENDPOINT"));
        return resources;
    }

}
