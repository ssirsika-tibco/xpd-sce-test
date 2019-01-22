/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.deploy.webdav;

import java.io.IOException;
import java.net.URL;

import org.eclipse.webdav.IContext;
import org.eclipse.webdav.ILocator;
import org.eclipse.webdav.IResponse;
import org.eclipse.webdav.client.RemoteDAVClient;
import org.eclipse.webdav.client.WebDAVFactory;
import org.eclipse.webdav.http.client.HttpClient;
import org.eclipse.webdav.http.client.Request;
import org.eclipse.webdav.internal.kernel.utils.Assert;
import org.eclipse.webdav.internal.utils.URLEncoder;
import org.w3c.dom.Document;

/**
 * Extends the Eclipse WebDAV Client functionality to support the IIS Server.
 * @since 1.1
 * @author TIBCO Software, Inc.
 */
public class FormsDAVClient extends RemoteDAVClient {

    /**
     * Constructs a new FormsDAVClient.
     * @param webDAVFactory - factory implementation
     * @param httpClient - client instance.
     */
    public FormsDAVClient(WebDAVFactory webDAVFactory, HttpClient httpClient) {
        super(webDAVFactory, httpClient);
    }

    /** {@inheritDoc} */
    public IResponse mkcol(ILocator locator, IContext userContext, Document document) throws IOException {
        Assert.isNotNull(locator);
        Assert.isNotNull(userContext);
        IContext context = newContext(userContext, locator);
        Request request = newRequest(locator, context, "MKCOL"); //$NON-NLS-1$
        return httpClient.invoke(request);
    }

    /**
     * Wrapper for creating Http Request for the MKCOL command.
     * @param locator - locater URL
     * @param context - Context
     * @param methodName - method
     * @return Request object
     * @throws IOException in case of error.
     * @since 1.1
     */
    private Request newRequest(ILocator locator, IContext context, String methodName) throws IOException {
        return new Request(methodName, URLEncoder.encode(new URL(locator.getResourceURL())), context);
    }

}
