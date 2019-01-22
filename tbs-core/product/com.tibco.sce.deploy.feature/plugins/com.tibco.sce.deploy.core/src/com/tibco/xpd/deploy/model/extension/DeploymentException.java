/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

/**
 * Exception represents the general problem during deployment.
 * <p>
 * <i>Created: 27 Sep 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeploymentException extends Exception {

    /**
     * Parameter used as version of the class for serialization purposes.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The constructor.
     */
    public DeploymentException() {
    }

    /**
     * The constructor with message.
     * 
     * @param message
     *            description of exception.
     */
    public DeploymentException(String message) {
        super(message);
    }

    /**
     * The constructor with cause.
     * 
     * @param cause
     *            wrapped exception or error which has caused this exception.
     */
    public DeploymentException(Throwable cause) {
        super(cause);
    }

    /**
     * The constructor with message and cause.
     * 
     * @param message
     *            description of exception.
     * @param cause
     *            wrapped exception or error.
     */
    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

}
