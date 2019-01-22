/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.resolutions;

/**
 * Thrown by an IResoultion if there was a problem during the resolution.
 *
 * @author nwilson
 */
public class ResolutionException extends Exception {

    /** Default serial id. */
    private static final long serialVersionUID = 1L;

    /**
     * @param message The error message.
     */
    public ResolutionException(String message) {
        super(message);
    }

    /**
     * @param cause The actual cause of the error.
     */
    public ResolutionException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message The error message.
     * @param cause The actual cause of the error.
     */
    public ResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
