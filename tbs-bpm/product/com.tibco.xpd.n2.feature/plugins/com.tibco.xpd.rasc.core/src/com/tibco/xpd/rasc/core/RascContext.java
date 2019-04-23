/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import com.tibco.bpm.dt.rasc.Version;

/**
 * Provides contextual information about the application deployment.
 *
 * @author pwatson
 * @since 23 Apr 2019
 */
public interface RascContext {
    /**
     * Returns the OSGi version of the deployed Application for which the RASC
     * is being generated.
     * 
     * @return the version number of the deployed Application.
     */
    public Version getVersion();
}
