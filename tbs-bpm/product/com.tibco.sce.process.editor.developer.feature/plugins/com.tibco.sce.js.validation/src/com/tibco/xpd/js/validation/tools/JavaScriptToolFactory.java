package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;

public abstract class JavaScriptToolFactory implements IPreProcessorFactory {

	protected String getScriptGrammar() {
		return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
	}
}
