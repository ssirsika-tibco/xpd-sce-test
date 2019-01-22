/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * 
 * 
 * <p>
 * <i>Created: 5 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class JavaServiceUnmappedScriptOutputToolFactory extends JavaScriptToolFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
		IPreProcessor processor = null;
		if (input instanceof ScriptInformation) {
			ScriptInformation scriptInformation = (ScriptInformation) input;
			boolean b = ProcessScriptUtil.isScriptInformationWithScriptType(scriptInformation,
					getScriptGrammar());
			if (b) {
				processor = new JavaServiceUnmappedScriptOutputTool(scriptInformation);
			}
		}
		return processor;
	}

	public Class<? extends IPreProcessor> getToolClass() {
		return JavaServiceUnmappedScriptOutputTool.class;
	}

}
