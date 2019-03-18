/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.exception;

/**
 *
 *
 * @author pwatson
 * @since 1 Mar 2019
 */
public abstract class RascGenerationException extends Exception {
    private static final long serialVersionUID = -6967249254612236378L;

    /**
     * @param message
     */
    public RascGenerationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RascGenerationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public RascGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public RascGenerationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
