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
     * Returns summary information regarding the application being deployed.
     * 
     * @return the summary of the application being deployed.
     */
    public RascAppSummary getAppSummary();

    /**
     * Returns the OSGi version of the deployed Application for which the RASC
     * is being generated. This is a convenience method to provide the value
     * that can be obtained by {@link #getAppSummary()#getVersion()}.
     * 
     * @return the version number of the deployed Application.
     */
    public Version getVersion();
}
