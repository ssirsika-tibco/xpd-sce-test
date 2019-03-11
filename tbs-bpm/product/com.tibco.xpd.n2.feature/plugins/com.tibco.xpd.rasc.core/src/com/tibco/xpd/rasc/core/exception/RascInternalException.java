/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.exception;

/**
 * Raised when an internal exception occurs during the generation of a
 * deployment RASC.
 *
 * @author pwatson
 * @since 1 Mar 2019
 */
public class RascInternalException extends RascGenerationException {
    private static final long serialVersionUID = 6941959371382990344L;

    /**
     * @param message
     * @param cause
     */
    public RascInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
