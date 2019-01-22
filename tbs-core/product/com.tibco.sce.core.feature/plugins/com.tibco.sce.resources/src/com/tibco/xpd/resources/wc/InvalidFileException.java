/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

/**
 * This exception should be thrown from doLoadModel when subclass wants that
 * AbstractWorkingCopy take care of InvalidFile marker problem.
 * 
 * @author wzurek
 */
public class InvalidFileException extends Exception {
    /** serial id. */
    private static final long serialVersionUID = -8002480412774836095L;

    /** constructor. */
    public InvalidFileException() {
    }

    /**
     * constructor.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public InvalidFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor.
     * 
     * @param message
     *            message
     */
    public InvalidFileException(String message) {
        super(message);
    }

    /**
     * constructor.
     * 
     * @param cause
     *            cause
     */
    public InvalidFileException(Throwable cause) {
        super(cause);
    }
}