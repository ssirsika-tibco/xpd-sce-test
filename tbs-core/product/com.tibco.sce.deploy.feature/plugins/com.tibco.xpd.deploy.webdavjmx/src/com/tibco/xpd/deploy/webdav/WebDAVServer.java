/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.client.CollectionHandle;
import org.eclipse.webdav.client.DAVClient;
import org.eclipse.webdav.client.WebDAVFactory;
import org.eclipse.webdav.http.client.HttpClient;
import org.eclipse.webdav.internal.kernel.DAVException;

import com.tibco.xpd.deploy.ui.util.SimpleBasicAuthenticator;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;

/**
 * Abstracts the WebDAV Server for the given configuration.
 * 
 * @since 1.1
 * @author TIBCO Software, Inc.
 */
public class WebDAVServer {
    /** Handle to the Collection a.k.a WebDAV Folder. */
    private final CollectionHandle mSiteHandle;

    /**
     * Creates a WebDAV connection.
     * 
     * @param siteUrl
     *            WebDAV URL
     * @param username
     *            WebDAV user name - Empty string if none
     * @param password
     *            WebDAV password - Empty string if none
     * @throws DAVException
     *             - if not able to connect to the server.
     */
    public WebDAVServer(String siteUrl, String username, String password)
            throws DAVException {
        WebDAVFactory davFactory = new WebDAVFactory();
        HttpClient httpClient = new HttpClient();
        httpClient.setAuthenticator(new SimpleBasicAuthenticator(username,
                password));

        // Install SSL aware socket factory.
        httpClient.setSocketFactory(SSLAwareSocketFactory.getInstance());
        DAVClient davClient = new FormsDAVClient(davFactory, httpClient);
        ILocator siteLocator = davFactory.newLocator(siteUrl);
        mSiteHandle = new CollectionHandle(davClient, siteLocator);

        if (!mSiteHandle.canTalkDAV()) {
            throw new DAVException(
                    Messages.WebDAVConnection_webdav_app_error_message);
        }
    }

    /**
     * Delete a remote file on the WebDAV Server.
     * 
     * @param url
     *            Remote URL to delete
     * @return An IResponse that gives the status of the operation
     * @throws IOException
     *             - in case of error while deleting file.
     */
    public IResponse deleteFile(String url) throws IOException {
        DAVClient client = mSiteHandle.getDAVClient();
        ILocator resourceLocator = client.getDAVFactory().newLocator(url);
        IResponse response =
                client.delete(resourceLocator, client.getDAVFactory()
                        .newContext());
        return response;
    }

    /**
     * Gets an Input Stream for the given WebDAV File.
     * 
     * @param remoteUrl
     *            Remote Target URL to get the Input Stream for
     * @return The Input Stream *NOTE* It is the callers responsibility to close
     *         the stream returned.
     */
    public InputStream getFileStream(String remoteUrl) {
        DAVClient client = mSiteHandle.getDAVClient();
        ILocator resourceLocator = client.getDAVFactory().newLocator(remoteUrl);
        InputStream inputStream = null;
        IResponse response = null;
        try {
            response =
                    client.get(resourceLocator, client.getDAVFactory()
                            .newContext());
            inputStream = response.getInputStream();
        } catch (IOException e) {
            return null;
        }

        return inputStream;
    }

    /**
     * Gets the WebDav root Site Handle.
     * 
     * @return The CollectionHandle of the WebDav root
     */
    public CollectionHandle getSiteHandle() {
        return mSiteHandle;
    }

    /**
     * Returns what the WebDAV Remote URL should be given a local URL and the
     * WebDAV target directory.
     * 
     * @param directory
     *            Remote WebDAV target directory
     * @param localUrl
     *            The local URL of the file
     * @return The WebDAV target location
     * @throws UnsupportedEncodingException
     *             - in case of encoding error.
     */
    public String getSiteRelativeURL(String directory, String localUrl)
            throws UnsupportedEncodingException {
        String pathSeparator = "/"; //$NON-NLS-1$
        String siteURL = mSiteHandle.getLocator().getResourceURL();
        String fileName =
                localUrl.substring(localUrl.lastIndexOf(pathSeparator) + 1);
        fileName = URLDecoder.decode(fileName, "UTF-8");
        if (directory.length() > 0) {
            if (!siteURL.endsWith(pathSeparator)) {
                siteURL += pathSeparator;
            }
            siteURL += directory;
        }
        if (!siteURL.endsWith(pathSeparator)) {
            siteURL += pathSeparator;
        }
        return siteURL + fileName;
    }

    /**
     * Puts a file onto the WebDAV Server.
     * 
     * @param url
     *            Source URL to copy from
     * @param remoteUrl
     *            Remote target URL to copy to
     * @return An IResponse that gives the status of the operation
     * @throws IOException
     *             in case of error
     */
    public IResponse putFile(String url, String remoteUrl) throws IOException {
        DAVClient client = mSiteHandle.getDAVClient();
        ILocator resourceLocator = client.getDAVFactory().newLocator(remoteUrl);
        URL localUrl = new URL(url);
        InputStream inputStream = null;
        IResponse response = null;
        try {
            inputStream = localUrl.openStream();
            response =
                    client.put(resourceLocator, client.getDAVFactory()
                            .newContext(), inputStream);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    /*
     * ===================================================== METHOD :
     * putTextAsFile =====================================================
     */
    /**
     * Puts text onto the WebDav Server as a file
     * 
     * @param fileText
     *            Text to put into the file
     * @param remoteUrl
     *            Remote target URL to copy to
     * @return An IResponse that gives the status of the operation
     * @throws IOException
     */
    public IResponse putTextAsFile(String fileText, String remoteUrl)
            throws IOException {
        DAVClient client = mSiteHandle.getDAVClient();
        ILocator resourceLocator = client.getDAVFactory().newLocator(remoteUrl);
        InputStream inputStream = null;
        IResponse response = null;
        try {
            inputStream = new ByteArrayInputStream(fileText.getBytes());
            response =
                    client.put(resourceLocator, client.getDAVFactory()
                            .newContext(), inputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

}