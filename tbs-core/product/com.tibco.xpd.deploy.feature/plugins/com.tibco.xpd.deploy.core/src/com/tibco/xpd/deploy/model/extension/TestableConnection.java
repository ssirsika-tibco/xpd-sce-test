/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.model.extension;

import org.eclipse.core.runtime.IStatus;

/**
 * Interface used to create customizable connection testing facility for a
 * deployment server. If a {@link Connection} implements of adapts to this
 * interface then provided {@link #testConnection()} method will be used
 * (instead of {@link Connection#connect()} / {@link Connection#disconnect()}
 * performed on a new temporary instance of the server).
 * 
 * @author Jan Arciuchiewicz
 */
public interface TestableConnection {

    /**
     * Performs test on a connection determining if it's possible to connect to
     * a server using current configuration.
     * 
     * @return Status with OK severity if test was successful.
     */
    IStatus testConnection();
}
