/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

/**
 * Exception represets problem with connection to server.
 * <p>
 * <i>Created: 7 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectionException extends DeploymentException {

	/**
	 * The constructor.
	 */
	public ConnectionException() {
		super();
	}

	/**
	 * The constructor with message and cause.
	 * 
	 * @param message
	 *            description of exception.
	 * @param cause
	 *            wrapped exception or error.
	 */
	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The constructor with message.
	 * 
	 * @param message
	 *            description of exception.
	 */
	public ConnectionException(String message) {
		super(message);
	}

	/**
	 * The constructor with cause.
	 * 
	 * @param cause
	 *            wrapped exception or error.
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Serialization UID.
	 */
	private static final long serialVersionUID = 1L;

}
