/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import com.tibco.bpm.dt.rasc.VersionRange;

/**
 * A summary of an application on which the deployed application depends. Based
 * on RascAppSummary, it provides the additional information of the version
 * range on which the dependency relies.
 *
 * @see RascAppSummary#getReferencedProjects()
 * @author pwatson
 * @since 25 Jun 2019
 */
public interface RascDependency extends RascAppSummary {
    /**
     * Returns the range that should be applied to dependencies on this
     * application. This is used when iterating the applications on which the
     * deployed application depends.
     * 
     * @return the application dependency range
     */
    public VersionRange getDependencyRange();
}
