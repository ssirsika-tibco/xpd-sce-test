/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Exception thrown to indicate a version problem with a Model managed by a
 * {@link WorkingCopy}.
 * 
 * @author njpatel
 * @since 3.5
 */
public class InvalidVersionException extends InvalidFileException {

    private static final long serialVersionUID = -7266463593409559521L;

    public InvalidVersionException() {
    }

    /**
     * constructor.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public InvalidVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor.
     * 
     * @param message
     *            message
     */
    public InvalidVersionException(String message) {
        super(message);
    }

    /**
     * constructor.
     * 
     * @param cause
     *            cause
     */
    public InvalidVersionException(Throwable cause) {
        super(cause);
    }
}
