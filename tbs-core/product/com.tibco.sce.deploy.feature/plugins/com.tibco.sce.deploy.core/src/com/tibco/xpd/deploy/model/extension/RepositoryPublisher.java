/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.io.File;
import java.net.URL;

import com.tibco.xpd.deploy.RepositoryConfig;

/**
 * RepositoryPublisher publishes file or directory into repository before it is
 * deployed.
 * <p>
 * <i>Created: 11 Sep 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface RepositoryPublisher {

    /**
     * Publishes file into repository using configuration.
     * 
     * @param config
     *            repository configuration.
     * @param file
     *            file or directory to be published.
     */
    public void publish(RepositoryConfig config, File file);

    /**
     * Delivers location of deployed file in repository as visible from the
     * server point of view.
     * 
     * @param config
     *            repository configuration.
     * @param file
     *            file or directory for which we would like to obtain URL
     */
    public URL getInquiryUrl(RepositoryConfig config, File file);
}
