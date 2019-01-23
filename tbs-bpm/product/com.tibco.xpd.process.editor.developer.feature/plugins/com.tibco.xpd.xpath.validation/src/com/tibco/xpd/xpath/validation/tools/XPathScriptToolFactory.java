/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;


import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;

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
public abstract class XPathScriptToolFactory implements IPreProcessorFactory {

	protected String getScriptGrammar() {
		return XPathScriptParserUtil.XPATH_GRAMMAR;
	}
}
