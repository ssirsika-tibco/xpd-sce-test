/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.model.extension;

/**
 * This exception is used to wrap other exception thrown by the external code.
 * This class is for throwing important checked exceptions over non-checked
 * methods. It should be used with care, and in limited circumstances.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class WrappedDeploymentException extends RuntimeException {
    private static final long serialVersionUID = -4970873387120392697L;

    /**
     * Construct the class with a wrapped exception.
     * 
     * @param cause
     *            the wrapped exception.
     */
    public WrappedDeploymentException(Throwable cause) {
        super(cause);
    }

}
