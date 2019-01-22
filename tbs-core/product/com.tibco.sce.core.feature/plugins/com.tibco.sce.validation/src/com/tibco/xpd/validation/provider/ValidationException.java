/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

/**
 * Validation exceptions are thrown by the explicit Validator class if there
 * was a problem executing the validation.
 *
 * @author nwilson
 */
public class ValidationException extends Exception {

    /** Default serial UID. */
    private static final long serialVersionUID = 1L;

    /**
     * @param message The error message.
     */
    public ValidationException(String message) {
        super(message);
    }

}
