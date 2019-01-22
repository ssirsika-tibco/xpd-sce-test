/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.xpdl2.Process;

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
public class XPathWebServiceMappedScriptOutputRule extends AbstractWebServiceMappedScriptOutputRule {

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
	protected List<String> getSubstitutionList(ErrorMessage errorMessage) {
		List<String> tempMsgList = new ArrayList<String>();
		tempMsgList.add(Integer.toString(errorMessage.getLineNumber()));
		tempMsgList.add(Integer.toString(errorMessage.getColumnNumber()));
		tempMsgList.add(errorMessage.getErrorMessage());
		return tempMsgList;
	}

	@Override
	protected String getScriptGrammar() {
		return XPathScriptParserUtil.XPATH_GRAMMAR;
	}

}
