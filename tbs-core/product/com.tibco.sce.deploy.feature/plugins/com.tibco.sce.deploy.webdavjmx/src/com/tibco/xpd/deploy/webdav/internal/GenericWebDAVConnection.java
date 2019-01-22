/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.webdav.internal;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.client.DAVClient;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.webdav.DeploymentConstants;
import com.tibco.xpd.deploy.webdav.Utils;
import com.tibco.xpd.deploy.webdav.WebDAVConnection;
import com.tibco.xpd.deploy.webdav.WebDAVPlugin;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Generic WebDAV server connection.
 * 
 * @author TIBCO Software, Inc.
 */
public class GenericWebDAVConnection extends WebDAVConnection {

    /**
     * @param server
     */
    public GenericWebDAVConnection(Server server) {
        super(server);
    }

    /**
     * {@inheritDoc} All files should be visible in general case.
     */
    @Override
    protected boolean isValidServerElement(String memberName) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public final DeploymentStatus deployModule(final String url)
            throws DeploymentException {

        Assert.isNotNull(mSiteHandle);
        Assert.isTrue(url != null && url.trim().length() > 0);
        InputStream inputStream = null;
        IResource deployResource = null;
        try {
            deployResource = Utils.getIResourceFromURL(url);
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
                        deployResource.getFullPath().toOSString());
        try {
            DAVClient client = mSiteHandle.getDAVClient();
            String targetRootFolderUrl =
                    mSiteHandle.getLocator().getResourceURL();

            String remoteFolderPath =
                    replicateSFRelativeFolderHierarchyOnServer(client,
                            targetRootFolderUrl,
                            deployResource,
                            WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND);

            if (deployResource instanceof IFolder) {
                String msg =
                        String
                                .format(Messages.GenericWebDAVConnection_PathReady_message);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, deploymentMsg
                                + "\n" + msg, null); //$NON-NLS-1$
            }

            // deployResource is instance of of IFile
            IFile deployFile = (IFile) deployResource;
            String localFileName = getFileName(deployFile);
            remoteFolderPath =
                    (remoteFolderPath == null || remoteFolderPath.trim()
                            .length() == 0) ? "" : remoteFolderPath //$NON-NLS-1$
                            + DeploymentConstants.PATH_SEPARATOR;
            String remoteUrl =
                    targetRootFolderUrl + DeploymentConstants.PATH_SEPARATOR
                            + remoteFolderPath + localFileName;

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
                String msg = Messages.WebDAVConnection_create_file_message;
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, deploymentMsg
                                + "\n" + msg, null); //$NON-NLS-1$
            } else if (statusCode == IResponse.SC_NO_CONTENT
                    || statusCode == IResponse.SC_OK) {
                String msg = Messages.WebDAVConnection_update_file_message;
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.OK, deploymentMsg
                                + "\n" + msg, null); //$NON-NLS-1$
            } else if (statusCode == IResponse.SC_FORBIDDEN) {
                String msg =
                        Messages.WebDAVConnection_deploy_write_access_error_message;
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                                + "\n" + msg, null); //$NON-NLS-1$
            } else {
                String msg =
                        String
                                .format(Messages.WebDAVConnection_deploy_error_message,
                                        responseMessage);
                return new DeploymentSimpleStatus(
                        DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                                + "\n" + msg, null); //$NON-NLS-1$
            }
        } catch (IOException e) {
            String msg =
                    String
                            .format(Messages.WebDAVConnection_deploy_error_message,
                                    ""); //$NON-NLS-1$
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + "\n" + msg, e); //$NON-NLS-1$
        } catch (CoreException ce) {
            String msg =
                    String
                            .format(Messages.WebDAVConnection_deploy_error_message,
                                    ""); //$NON-NLS-1$
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + "\n" + msg, ce); //$NON-NLS-1$
        } catch (DeploymentException de) {
            String msg = de.getMessage();
            return new DeploymentSimpleStatus(
                    DeploymentSimpleStatus.Severity.ERROR, deploymentMsg
                            + "\n" + msg, de); //$NON-NLS-1$
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

    protected IWorkspaceRoot getWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    /**
     * Check the folder hierarchy in the webdav server. Create the hierarchy if
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
    protected String replicateSFRelativeFolderHierarchyOnServer(
            DAVClient client, String targetRootUrl, IResource deployResource,
            String specialFolderKind) throws DeploymentException {

        // Get IPath for the file that is relative to the project.
        IPath relativePath =
                SpecialFolderUtil.getSpecialFolderRelativePath(deployResource,
                        specialFolderKind);

        String currentFolderPath = null;
        if (deployResource instanceof IFile) {
            relativePath = relativePath.removeLastSegments(1);
        }
        String[] segments = relativePath.segments();
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
}
