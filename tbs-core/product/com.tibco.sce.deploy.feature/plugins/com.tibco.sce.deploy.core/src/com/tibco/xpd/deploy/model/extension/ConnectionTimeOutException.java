/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.model.extension;

/**
 * @author kupadhya
 * 
 */
public class ConnectionTimeOutException extends ConnectionException {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     */
    public ConnectionTimeOutException() {
    }

    /**
     * The constructor.
     * 
     * @param message
     * @param cause
     */
    public ConnectionTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor.
     * 
     * @param message
     */
    public ConnectionTimeOutException(String message) {
        super(message);
    }

    /**
     * The constructor.
     * 
     * @param cause
     */
    public ConnectionTimeOutException(Throwable cause) {
        super(cause);
    }

}
