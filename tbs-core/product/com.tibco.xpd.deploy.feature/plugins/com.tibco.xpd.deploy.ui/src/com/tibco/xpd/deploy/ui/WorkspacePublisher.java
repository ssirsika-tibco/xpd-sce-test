/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;

/**
 * Repository publisher that publish directly from workspace.
 * <p>
 * <i>Created: 17 Nov 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WorkspacePublisher implements RepositoryPublisher {

    /**
     * Returns local file system URL of the file.
     * 
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher#getInquiryUrl(com.tibco.xpd.deploy.model.RepositoryConfig,
     *      java.io.File)
     */
    public URL getInquiryUrl(RepositoryConfig config, File file) {

        try {
            return file.toURL();
        } catch (MalformedURLException e) {
            // TODO Log properly into eclipse log.
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Publishing file in workspace.
     * 
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher#publish(com.tibco.xpd.deploy.model.RepositoryConfig,
     *      java.io.File)
     */
    public void publish(RepositoryConfig config, File file) {
        // DO NOTHING
    }

}
