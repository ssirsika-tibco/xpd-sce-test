/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.deploy.model.extension;

import org.eclipse.core.runtime.IStatus;

/**
 * Exception with status to handle problems when connecting to server
 * 
 * @author agondal
 * @since 1 Jul 2013
 */
public class ConnectionCoreException extends ConnectionException {

    IStatus status;

    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     */
    public ConnectionCoreException() {
        super();
    }

    /**
     * The constructor with status
     * 
     * @param status
     * 
     */
    public ConnectionCoreException(IStatus status) {

        this.status = status;
    }

    /**
     * 
     * @return status or <code>null</code>
     */
    public IStatus getStatus() {
        return this.status;
    }

}
