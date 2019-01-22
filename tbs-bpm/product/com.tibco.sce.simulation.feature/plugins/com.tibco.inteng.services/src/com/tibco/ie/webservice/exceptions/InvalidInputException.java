package com.tibco.ie.webservice.exceptions;

/**
 * This class represents an exception which is thrown when the input to the
 * Service is bad/corrupt and cannot be converted to a document.
 * 
 * @author KamleshU
 * 
 */
public class InvalidInputException extends Exception {
	/**
	 * 
	 * @param message
	 */
	public InvalidInputException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

}
