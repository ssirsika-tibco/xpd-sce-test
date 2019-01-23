/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.List;

import com.tibco.xpd.script.model.client.JsClass;

/**
 * 
 * @author mtorres
 *
 */
public interface IJCCExpressionValidator extends IJCCValidator {
	
	void setScriptParser(IJCCScriptParser scriptParser);
	
	List<JsClass> getSupportedJsClasses();
	
}
