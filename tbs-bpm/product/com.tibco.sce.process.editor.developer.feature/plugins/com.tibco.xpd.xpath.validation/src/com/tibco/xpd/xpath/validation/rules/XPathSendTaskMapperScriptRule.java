/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class XPathSendTaskMapperScriptRule extends AbstractSendTaskMapperScriptRule {

	/** The issue id. */
	private static final String ERROR_ID = "xpath.validateScript"; //$NON-NLS-1$

	private static final String WARNING_ID = "xpath.warning.validateScript"; //$NON-NLS-1$

	@Override
	protected String getErrorId() {
		return ERROR_ID;
	}

	@Override
	protected String getWarningId() {
		return WARNING_ID;
	}

	@Override
	protected String getScriptGrammar() {
		return XPathScriptParserUtil.XPATH_GRAMMAR;
	}

}
