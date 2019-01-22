/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.client.AbstractResourceHandle;
import org.eclipse.webdav.client.CollectionHandle;
import org.eclipse.webdav.client.DAVClient;
import org.eclipse.webdav.internal.kernel.ClientException;
import org.eclipse.webdav.internal.kernel.DAVException;
import org.eclipse.webdav.internal.kernel.SystemException;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ContainerElement;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.Operation;
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
import com.tibco.xpd.deploy.ui.components.PasswordDialog;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * The WebDAV Connection implementation that handles the different operations
 * like deployment and delete form the server etc. on the individual files from
 * the work-space. The implementation is adapted from the work by Jan
 * Arciuchiewicz and Robert Hudson.
 * 
 * @author TIBCO Software, Inc.
 */
public class WebDAVConnection implements Connection {

    private static final Logger LOG = WebDAVPlugin.getDefault().getLogger();

    /** Server configuration parameter within the ServerElement. */
    private static final String URL_PARAM = "url"; //$NON-NLS-1$

    /** Default root folder. */
    private static String mRootFolderPath = "/Forms"; //$NON-NLS-1$

    /** This is server configuration object in the studio. */
    private final Server mServer;

    /** This contains the Deployment Configuration details. */
    private DeploymentData mDeploymentData;

    /**
     * This is server object that encapsulates the WebDAV server functionality.
     */
    private WebDAVServer mWebDAVServer;

    /** CollectionHandle for the WebDAV server. */
    protected CollectionHandle mSiteHandle;

    /** for delete file operation. */
    private OperationImpl mDeleteFileOperation;

    /** ServerElementType of file type. */
    private ServerElementType mFileType;

    /** ServerElementState for published state. */
    private ServerElementState mPublishedState;

    /** for delete Folder operation. */
    private OperationImpl mDeleteFolderOperation;

    /** ServerElementType of Folder type. */
    private ServerElementType mFolderType;

    /** Folder ServerElementState for published state. */
    private ServerElementState mFolderPublishedState;

    /**
     * Return the last segment of the given path.
     * 
     * @param path
     *            - path
     * @return String
     */
    private static String getLastSegment(final String path) {
        int index = path.lastIndexOf('/');
        if (index == -1) {
            return path;
        }
        if (index == path.length() - 1) {
            return getLastSegment(path.substring(0, index));
        }
        return path.substring(index + 1);

    }

    /**
     * Gets the file/folder name from the handle.
     * 
     * @param member
     *            - ResourceHandle to the member
     * @return String url for the member
     */
    private static String getMemberName(AbstractResourceHandle member) {
        return getLastSegment(member.getLocator().getResourceURL());
    }

    /**
     * Returns true in case the handle points to a collection.
     * 
     * @param member
     *            - ResourceHandle to the member
     * @return boolean - true if collection
     */
    private static boolean isCollectionMember(AbstractResourceHandle member) {
        return member instanceof CollectionHandle;
    }

    /**
     * Constructs a new WebDAVConnection.
     * 
     * @param server
     *            server corresponding to the WebDAV server.
     */
    public WebDAVConnection(final Server server) {
        mServer = server;
        try {
            mDeploymentData = new DeploymentData(server.getServerConfig());
            mRootFolderPath = mDeploymentData.getWebDavRootFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiseServerElementTypes();
    }

    /**
     * Constructs a new WebDAVConnection.
     * 
     * @param server
     *            server corresponding to the WebDAV server.
     */
    public WebDAVConnection(final Server server, String configSiteURLKey,
            String configRootFolderKey, String configUsernameKey,
            String configPasswordKey) {
        mServer = server;
        try {
            mDeploymentData =
                    new DeploymentData(server.getServerConfig(),
                            configSiteURLKey, configRootFolderKey,
                            configUsernameKey, configPasswordKey);
            mRootFolderPath = mDeploymentData.getWebDavRootFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiseServerElementTypes();
    }

    private String password;

    /** {@inheritDoc} */
    public final void connect() throws ConnectionException {
        try {
            String username = mDeploymentData.getUsername();
            password = mDeploymentData.getPassword();
            if (username != null && username.length() != 0 && password == null) {
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
            }
            checkSiteUrl(mDeploymentData.getSiteUrl(), username, password);

            mWebDAVServer =
                    new WebDAVServer(mDeploymentData.getSiteUrl(), username,
                            password);
            mRootFolderPath = mDeploymentData.getWebDavRootFolder();

        } catch (DAVException e) {
            if (e instanceof SystemException) {
                SystemException se = (SystemException) e;
                Exception wrappedException = se.getWrappedException();
                if (wrappedException instanceof ConnectException) {
                    ConnectionException ce =
                            new ConnectionException(
                                    Messages.WebDAVConnection_server_connection_error_message);
                    ce.fillInStackTrace();
                    throw ce;
                } else if (wrappedException instanceof UnknownHostException) {
                    ConnectionException ce =
                            new ConnectionException(
                                    Messages.WebDAVConnection_server_unknownhost_error_message);
                    ce.fillInStackTrace();
                    throw ce;
                } else {
                    ConnectionException ce =
                            new ConnectionException(e.getMessage());
                    ce.fillInStackTrace();
                    throw ce;
                }
            } else if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;
                if (ce.getStatusCode() == IResponse.SC_UNAUTHORIZED) {
                    ConnectionException connectExc =
                            new ConnectionException(
                                    Messages.WebDAVConnection_server_auth_error_message);
                    throw connectExc;
                }
            } else {
                ConnectionException ce =
                        new ConnectionException(e.getMessage());
                ce.fillInStackTrace();
                throw ce;
            }
        }
        mSiteHandle = mWebDAVServer.getSiteHandle();
        try {
            createFolderInServer(mSiteHandle.getDAVClient(), mSiteHandle
                    .getLocator().getResourceURL(), mRootFolderPath);
        } catch (DeploymentException de) {
            throw new ConnectionException(String
                    .format(Messages.WebDAVConnection_create_root_folder_error,
                            de.getMessage()));
        }
        mServer.setServerState(ServerState.CONNECTED_LITERAL);

    }

    /**
     * Check that the site URL exists and if not then create it
     * 
     * @param tmpSiteUrl
     * @param username
     * @param pass
     * @throws ConnectionException
     *             if cannot create the site url path
     */
    public final void checkSiteUrl(final String tmpSiteUrl, String username,
            String pass) throws ConnectionException {
        String siteURL = tmpSiteUrl;
        boolean isValidSiteURL = true;
        WebDAVServer webDavServer = null;
        try {
            webDavServer = new WebDAVServer(siteURL, username, pass);
            webDavServer.getSiteHandle().getMembers();
        } catch (DAVException e) {
            isValidSiteURL = false;
        }

        if (!isValidSiteURL) {
            boolean isPortPath = false;
            Path path = new Path(siteURL);
            while (!isPortPath) {
                try {
                    webDavServer = new WebDAVServer(siteURL, username, pass);
                    webDavServer.getSiteHandle().getMembers();
                    DAVClient client =
                            webDavServer.getSiteHandle().getDAVClient();

                    Path extraDirsPath =
                            new Path(tmpSiteUrl.replace(siteURL, "")); //$NON-NLS-1$
                    for (int i = 0; i < extraDirsPath.segmentCount(); i++) {
                        String dir = extraDirsPath.segment(i);
                        String newLocation =
                                new Path(siteURL).append(dir)
                                        .toPortableString();
                        // path changes the port to have :: so we replace
                        // this back to :
                        newLocation =
                                newLocation
                                        .replace(Messages.WebDAVConnection_0,
                                                Messages.WebDAVConnection_1);
                        ILocator resourceLocator =
                                client.getDAVFactory()
                                        .newStableLocator(newLocation);
                        CollectionHandle collectionHandle =
                                new CollectionHandle(client, resourceLocator);
                        collectionHandle.mkdirs();
                        webDavServer =
                                new WebDAVServer(newLocation, username, pass);
                        webDavServer.getSiteHandle().getMembers();
                    }
                    isPortPath = true;
                } catch (DAVException e) {
                    siteURL = path.removeLastSegments(1).toString();
                    path = new Path(siteURL);
                    if (path.segmentCount() <= 1) {
                        if (e instanceof SystemException) {
                            Exception we =
                                    ((SystemException) e).getWrappedException();
                            if (we != null) {
                                LOG.warn(we, "WebDAV connection failed."); //$NON-NLS-1$
                            }
                        }
                        throw new ConnectionException(
                                Messages.WebDAVConnection_connectionAttempFailed);
                    }
                } catch (Exception e) {
                    LOG.warn(e, "WebDAV connection failed."); //$NON-NLS-1$
                    throw new ConnectionException(
                            Messages.WebDAVConnection_connectionAttempFailed);
                }

            }
        }
    }

    /**
     * Creates the folder hierarchy in the WebDAV Server.
     * 
     * @param client
     *            - WebDAV Client
     * @param targetRootUrl
     *            - URL for the WebDAV server
     * @param folderPath
     *            - Folder path to be created
     * @throws DeploymentException
     *             - in case of any error
     * @since 1.0
     */
    protected void createFolderInServer(final DAVClient client,
            final String targetRootUrl, final String folderPath)
            throws DeploymentException {

        // Check the folder path in the server and create if it does not exist.
        ILocator locator =
                client.getDAVFactory().newLocator(targetRootUrl
                        + DeploymentConstants.PATH_SEPARATOR + folderPath
                        + DeploymentConstants.PATH_SEPARATOR);
        try {

            IResponse response =
                    client.get(locator, client.getDAVFactory().newContext());

            int statusCode = response.getStatusCode();
            if (statusCode == IResponse.SC_NOT_FOUND) {
                // Folder does not exist. Create it now.
                response =
                        client.mkcol(locator, client.getDAVFactory()
                                .newContext(), null);
                statusCode = response.getStatusCode();
                if (statusCode == IResponse.SC_CREATED) {
                    return;
                }
                if (statusCode == IResponse.SC_FORBIDDEN) {
                    throw new DeploymentException(
                            String
                                    .format(Messages.WebDAVConnection_create_folder_write_access_error,
                                            locator.toString()));
                } else {
                    throw new DeploymentException(
                            String
                                    .format(Messages.WebDAVConnection_create_folder_generic_error,
                                            locator.toString(),
                                            statusCode));
                }
            } else if (statusCode == IResponse.SC_OK) {
                // Folder exists. No action needed.
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            DeploymentException de = new DeploymentException(ioe.getMessage());
            de.fillInStackTrace();
            throw de;
        }

    }

    /**
     * Deletes the file or folder form the server.
     * 
     * @param serverElement
     *            - Element to delete for the server.
     * @return Object - DeploymentStatus
     * @throws DeploymentException
     *             - in case of error
     */
    public final Object deleteRemoteFile(final ServerElement serverElement)
            throws DeploymentException {
        String url = serverElement.getConfigParameter(URL_PARAM).getValue();
        try {
            DAVClient client = mSiteHandle.getDAVClient();
            ILocator resourceLocator = client.getDAVFactory().newLocator(url);
            IResponse response =
                    client.delete(resourceLocator, client.getDAVFactory()
                            .newContext());
            int statusCode = response.getStatusCode();
            String statusMessage = response.getStatusMessage();
            //String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$
            if (statusCode == IResponse.SC_NO_CONTENT
                    || statusCode == IResponse.SC_OK) {
                String msg =
                        String.format(Messages.WebDAVConnection_delete_message,
                                url);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, msg, null);
            } else if (statusCode == IResponse.SC_FORBIDDEN) {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_delete_write_access_error_message,
                                        url);
                throw new DeploymentException(msg);
            } else {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_delete_error_message,
                                        url,
                                        statusCode,
                                        statusMessage);
                throw new DeploymentException(msg);
            }
        } catch (IOException e) {
            throw new DeploymentException(e);
        }
    }

    /** {@inheritDoc} */
    public DeploymentStatus deployModule(final String url)
            throws DeploymentException {

        Assert.isNotNull(mSiteHandle);
        Assert.isTrue(url != null && url.trim().length() > 0);
        InputStream inputStream = null;
        IFile deployFile = null;
        try {
            IResource deployResource = Utils.getIResourceFromURL(url);
            if (deployResource instanceof IFile) {
                deployFile = (IFile) deployResource;
            } else {
                throw new Exception(String.format(Messages.WebDAVConnection_4,
                        url));
            }
        } catch (Exception e1) {
            String msg =
                    String
                            .format(Messages.GenericWebDAVConnection_InvalidURL_message,
                                    url);
            WebDAVPlugin.getDefault().getLogger().error(e1, msg);
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, msg, e1);
        }
        String deploymentMsg =
                String.format(Messages.WebDAVConnection_deploy_start_message,
                        deployFile.getFullPath().toOSString());
        try {
            DAVClient client = mSiteHandle.getDAVClient();
            String targetRootFolderUrl =
                    mSiteHandle.getLocator().getResourceURL() + mRootFolderPath;

            String remoteFolderPath =
                    replicateFolderHierarchyInServer(client,
                            targetRootFolderUrl,
                            deployFile);

            String localFileName = getFileName(deployFile);
            String remoteUrl =
                    targetRootFolderUrl + DeploymentConstants.PATH_SEPARATOR;
            if (remoteFolderPath != null) {
                remoteUrl =
                        remoteUrl + remoteFolderPath
                                + DeploymentConstants.PATH_SEPARATOR;
            }
            remoteUrl += localFileName;

            ILocator resourceLocator =
                    client.getDAVFactory().newLocator(remoteUrl);
            inputStream = deployFile.getContents();
            IResponse response =
                    client.put(resourceLocator, client.getDAVFactory()
                            .newContext(), inputStream);
            int statusCode = response.getStatusCode();
            String statusMessage = response.getStatusMessage();
            String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$
            if (statusCode == IResponse.SC_CREATED) {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_create_file_message);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, deploymentMsg
                                + Messages.WebDAVConnection_5 + msg, null);
            } else if (statusCode == IResponse.SC_NO_CONTENT
                    || statusCode == IResponse.SC_OK) {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_update_file_message);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, deploymentMsg
                                + Messages.WebDAVConnection_6 + msg, null);
            } else if (statusCode == IResponse.SC_FORBIDDEN) {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_deploy_write_access_error_message);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                                + Messages.WebDAVConnection_7 + msg, null);
            } else {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_deploy_error_message,
                                        responseMessage);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                                + Messages.WebDAVConnection_8 + msg, null);
            }
        } catch (IOException e) {
            String msg =
                    String
                            .format(Messages.WebDAVConnection_deploy_error_message,
                                    ""); //$NON-NLS-1$
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + Messages.WebDAVConnection_9 + msg, e);
        } catch (CoreException ce) {
            String msg =
                    String
                            .format(Messages.WebDAVConnection_deploy_error_message,
                                    ""); //$NON-NLS-1$
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + Messages.WebDAVConnection_10 + msg, ce);
        } catch (DeploymentException de) {
            String msg = de.getMessage();
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + Messages.WebDAVConnection_11 + msg, de);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                // TODO Log error
                e.printStackTrace();
            }
        }
    }

    /** {@inheritDoc} */
    public void disconnect() throws ConnectionException {
        mServer.getServerElements().clear();
        mServer.setServerState(ServerState.DISCONNECTED_LITERAL);
    }

    /** {@inheritDoc} */
    public Object getAdapter(Class adapter) {
        return null;
    }

    /**
     * Gets the decoded file name from the IFile resource.
     * 
     * @param file
     *            - IFile resource.
     * @return String - decoded file name.
     * @throws UnsupportedEncodingException
     *             in case of decoding error.
     * @since 1.0
     */
    protected String getFileName(IFile file)
            throws UnsupportedEncodingException {
        String fileName = file.getProjectRelativePath().lastSegment();
        //fileName = URLDecoder.decode(fileName, "UTF-8"); //$NON-NLS-1$
        return fileName;
    }

    public Server getServer() {
        return mServer;
    }

    /*
     * private String getSiteRelativeURL(CollectionHandle site, String localUrl)
     * throws UnsupportedEncodingException { String siteURL =
     * site.getLocator().getResourceURL(); String fileName =
     * localUrl.substring(localUrl
     * .lastIndexOf(DeploymentConstants.PATH_SEPARATOR) + 1); fileName =
     * URLDecoder.decode(fileName, "UTF-8"); //$NON-NLS-1$ if
     * (!siteURL.endsWith(DeploymentConstants.PATH_SEPARATOR)) { siteURL +=
     * DeploymentConstants.PATH_SEPARATOR; } return siteURL + mRootFolderPath +
     * fileName; }
     */
    /**
     * Modules types initialization.
     */
    private void initialiseServerElementTypes() {
        DeployFactory f = DeployFactory.eINSTANCE;
        mFileType = f.createServerElementType();
        mFolderType = f.createServerElementType();

        // states. States should not be shared between different element types
        mPublishedState = f.createServerElementState();
        mPublishedState
                .setName(Messages.WebDAVConnection_published_state_label);
        mFolderPublishedState = f.createServerElementState();
        mFolderPublishedState
                .setName(Messages.WebDAVConnection_published_state_label);

        // all states server element type could be in
        mFileType.getStates().add(mPublishedState);
        mFolderType.getStates().add(mFolderPublishedState);

        // operations
        mDeleteFileOperation = new OperationImpl() {
            @Override
            public Object execute(final ServerElement serverElement)
                    throws DeploymentException {
                try {
                    return deleteRemoteFile(serverElement);
                } finally {
                    refreshServerContent();
                }
            }
        };
        mDeleteFileOperation.setGlobalActionId(ActionFactory.DELETE.getId());
        mDeleteFileOperation
                .setName(Messages.WebDAVConnection_delete_action_label);

        // operations
        mDeleteFolderOperation = new OperationImpl() {
            @Override
            public Object execute(final ServerElement serverElement)
                    throws DeploymentException {
                try {
                    return deleteRemoteFile(serverElement);
                } finally {
                    refreshServerContent();
                }
            }
        };
        mDeleteFolderOperation.setGlobalActionId(ActionFactory.DELETE.getId());
        mDeleteFolderOperation
                .setName(Messages.WebDAVConnection_delete_action_label);

        // all available operations for server element type
        mFileType.getOperations().add(mDeleteFileOperation);
        mFolderType.getOperations().add(mDeleteFolderOperation);

        // possible operations for states
        mPublishedState.getPossibleOperations().add(mDeleteFileOperation);
        mFolderPublishedState.getPossibleOperations()
                .add(mDeleteFolderOperation);
    }

    /** {@inheritDoc} */
    public boolean isConnected() throws ConnectionException {
        return mServer.getServerState() == ServerState.CONNECTED_LITERAL;
    }

    /**
     * 
     * Returns true if the member is valid for the forms deployment.
     * 
     * @param memberName
     *            - file name
     * @return true in case of valid filename extension for the forms artifacts.
     * @since 1.0
     */
    protected boolean isValidServerElement(String memberName) {
        // Just hide any .* resources - everything else should be visible.
        return memberName.charAt(0) != '.';
    }

    /** {@inheritDoc} */
    public Object performServerElementOperation(ServerElement arg0,
            Operation arg1) throws DeploymentException {
        return null;
    }

    /**
     * Refreshes the contents for the given collection.
     * 
     * @param collection
     *            - CollectionHandle.
     * @param container
     *            - container element.
     * @throws DAVException
     *             - in case of error.
     */
    private void refreshCollection(CollectionHandle collection,
            ContainerElement container) throws DAVException {
        Set<String> existingFileNames = new HashSet<String>();
        Set<String> existingDirNames = new HashSet<String>();
        Set<AbstractResourceHandle> members = collection.getMembers();
        for (ListIterator<ServerElement> iter =
                container.getChildren().listIterator(); iter.hasNext();) {
            ServerElement se = iter.next();
            String name = se.getName();
            if (se instanceof ModuleContainer) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        refreshCollection((CollectionHandle) member,
                                (ContainerElement) se);
                        setModuleContainerProperties((ModuleContainer) se,
                                member);
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
                                        .format(Messages.WebDAVConnection_not_module_container_message,
                                                se.toString()));
            }
        }

        for (AbstractResourceHandle member : members) {
            String memberName = getMemberName(member);
            if (isCollectionMember(member)) {
                if (!existingDirNames.contains(memberName)) {
                    ModuleContainer moduleContainer =
                            DeployFactory.eINSTANCE.createModuleContainer();
                    moduleContainer.setName(memberName);
                    refreshCollection((CollectionHandle) member,
                            moduleContainer);
                    setModuleContainerProperties(moduleContainer, member);
                    container.getChildren().add(moduleContainer);
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

    @SuppressWarnings("unchecked")
    private void refreshServer(CollectionHandle site) throws DAVException {
        boolean showSubfolders = true;
        HashSet<String> existingFileNames = new HashSet<String>();
        HashSet<String> existingDirNames = new HashSet<String>();
        Set<AbstractResourceHandle> members = site.getMembers();
        for (ListIterator<ServerElement> iter =
                mServer.getServerElements().listIterator(); iter.hasNext();) {
            ServerElement se = iter.next();
            String name = se.getName();
            if (se instanceof ModuleContainer) {
                boolean exist = false;
                for (AbstractResourceHandle member : members) {
                    if (isCollectionMember(member)
                            && name.equals(getMemberName(member))) {
                        if (showSubfolders) {
                            refreshCollection((CollectionHandle) member,
                                    (ContainerElement) se);
                        }
                        setModuleContainerProperties((ModuleContainer) se,
                                member);
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
                                        .format(Messages.WebDAVConnection_not_module_container_message,
                                                se.toString()));
            }
        }

        for (AbstractResourceHandle member : members) {
            String memberName = getMemberName(member);
            if (isCollectionMember(member)) {
                if (!existingDirNames.contains(memberName)) {
                    ModuleContainer moduleContainer =
                            DeployFactory.eINSTANCE.createModuleContainer();
                    moduleContainer.setName(memberName);
                    if (showSubfolders) {
                        refreshCollection((CollectionHandle) member,
                                moduleContainer);
                    }
                    setModuleContainerProperties(moduleContainer, member);
                    mServer.getServerElements().add(moduleContainer);
                }
            } else {
                if (!existingFileNames.contains(memberName)
                        && isValidServerElement(memberName)) {
                    ServerModule module =
                            DeployFactory.eINSTANCE.createServerModule();
                    module.setName(memberName);
                    setModuleProperties(module, member);
                    mServer.getServerElements().add(module);
                }
            }
        }
    }

    public void refreshServerContent() throws ConnectionException {
        if (isConnected() && mSiteHandle != null) {
            try {
                refreshServer(mSiteHandle);
            } catch (DAVException e) {
                disconnect();
                throw new ConnectionException(e);
            }
        } else {
            mServer.getServerElements().clear();
        }

    }

    /**
     * Check the folder hierarchy in the WebDAV server. Create the hierarchy if
     * it does not exist.
     * 
     * @param client
     *            WebDAVClient
     * @param targetRootUrl
     *            url for the target root folder
     * @param localUrl
     *            local path for the file.
     * @return folderPath path for the created folder.
     * @throws DeploymentException
     */
    private String replicateFolderHierarchyInServer(DAVClient client,
            String targetRootUrl, IFile deployFile) throws DeploymentException {

        // Compute the special folder-relative path for the parent of the file
        // to be deployed, defaulting to the project-relative path if not an
        // XPD project.
        String currentFolderPath = null;
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(deployFile
                        .getProject());
        IPath relativePath =
                config == null ? deployFile.getProjectRelativePath()
                        : SpecialFolderUtil
                                .getSpecialFolderRelativePath(deployFile, null);
        String[] segments = relativePath.removeLastSegments(1).segments();
        for (String currentSegment : segments) {
            // This segment corresponds to a folder
            if (currentFolderPath == null) {
                currentFolderPath = currentSegment;
            } else {
                currentFolderPath =
                        currentFolderPath + DeploymentConstants.PATH_SEPARATOR
                                + currentSegment;
            }
            createFolderInServer(client, targetRootUrl, currentFolderPath);
        }

        return currentFolderPath;
    }

    /**
     * @param se
     * @param member
     */
    private void setModuleContainerProperties(ModuleContainer se,
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
        se.setServerElementType(mFolderType);
        se.setServerElementState(mFolderPublishedState);
    }

    /**
     * @param se
     * @param member
     */
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
        se.setServerElementType(mFileType);
        se.setServerElementState(mPublishedState);
    }

    public boolean validateModule(String arg0) throws ConnectionException {
        return true;
    }

}
