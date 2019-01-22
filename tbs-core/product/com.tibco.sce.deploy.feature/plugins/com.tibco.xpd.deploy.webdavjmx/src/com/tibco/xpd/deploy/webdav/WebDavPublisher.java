/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.internal.kernel.DAVException;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;
import com.tibco.xpd.deploy.model.extension.WrappedDeploymentException;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * @author glewis
 * 
 */
public class WebDavPublisher implements RepositoryPublisher {

    private WebDAVServer server = null;

    public WebDavPublisher() {

    }

    public URL getInquiryUrl(RepositoryConfig config, File file) {
        URL inquiryUrl = null;
        try {
            ConfigParameter siteUrl =
                    config.getConfigParameter(DeploymentData.SITE_URL);
            if (siteUrl == null) {
                throw new IllegalArgumentException(
                        "WebDAV repository must have siteUrl configuration parameter.");
            }
            inquiryUrl =
                    new URL(removeEndSlash(siteUrl.getValue()) + '/'
                            + file.getName());
        } catch (MalformedURLException e) {
            WebDAVPlugin.getDefault().getLogger().error(e);
            throw new WrappedDeploymentException(e);
        }
        return inquiryUrl;
    }

    private String removeEndSlash(String value) {
        if (value != null && value.endsWith("/")) { //$NON-NLS-1$
            return value.substring(0, value.length() - 1);
        }
        return value;
    }

    public void publish(RepositoryConfig config, File file) {
        server = null;
        try {
            ConfigParameter siteUrl =
                    config.getConfigParameter(DeploymentData.SITE_URL);
            ConfigParameter username =
                    config.getConfigParameter(DeploymentData.USERNAME);
            ConfigParameter password =
                    config.getConfigParameter(DeploymentData.PASSWORD);
            if (siteUrl == null || username == null || password == null) {
                throw new IllegalArgumentException(
                        "WebDAV repository must have siteUrl, username and password configuration parameters.");
            }

            final String decodedPassword =
                    password.getValue() != null ? EncryptionUtil
                            .decrypt(password.getValue()) : null;
            server =
                    new WebDAVServer(siteUrl.getValue(), username.getValue(),
                            decodedPassword);

            String url = file.toURL().toExternalForm();
            if (url != null && url.contains(" ")) {//$NON-NLS-1$
                // Deal with unscaped urls
                url = file.toURI().toURL().toExternalForm();
            }
            IResponse response =
                    server.putFile(url, removeEndSlash(siteUrl.getValue())
                            + '/' + file.getName());
            if ((response.getStatusCode() != IResponse.SC_CREATED)
                    && (response.getStatusCode() != IResponse.SC_NO_CONTENT)) {
                String message = String.format("%1$d : %2$s", //$NON-NLS-1$
                        response.getStatusCode(),
                        response.getStatusMessage());
                throw new WrappedDeploymentException(new DeploymentException(
                        message));
            }
        } catch (DAVException e) {
            WebDAVPlugin.getDefault().getLogger().error(e);
            throw new WrappedDeploymentException(e);
        } catch (MalformedURLException e) {
            WebDAVPlugin.getDefault().getLogger().error(e);
            throw new WrappedDeploymentException(e);
        } catch (IOException e) {
            WebDAVPlugin.getDefault().getLogger().error(e);
            throw new WrappedDeploymentException(e);
        }
    }
}
