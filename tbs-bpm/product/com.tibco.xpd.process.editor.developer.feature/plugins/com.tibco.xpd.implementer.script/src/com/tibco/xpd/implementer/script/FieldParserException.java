/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.script.parser.validator.ErrorMessage;

/**
 * @author nwilson
 */
public class FieldParserException extends Exception {

	/** Default UID. */
	private static final long serialVersionUID = 1L;

	private List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

	/**
	 * @param message
	 *            The error message.
	 */
	public FieldParserException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            The error message.
	 */
	public FieldParserException(String message, List<ErrorMessage> errorList) {
		super(message);
		this.errorList = errorList;
	}

	public List<ErrorMessage> getErrorList() {
		return this.errorList;
	}

}
