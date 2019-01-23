/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

/**
 * Manifests disconnection from outside of the application.
 * <p>
 * <i>Created: 27 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectionLostException extends ConnectionException {

    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     */
    public ConnectionLostException() {
    }

    /**
     * The constructor.
     * 
     * @param message
     * @param cause
     */
    public ConnectionLostException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor.
     * 
     * @param message
     */
    public ConnectionLostException(String message) {
        super(message);
    }

    /**
     * The constructor.
     * 
     * @param cause
     */
    public ConnectionLostException(Throwable cause) {
        super(cause);
    }

}
