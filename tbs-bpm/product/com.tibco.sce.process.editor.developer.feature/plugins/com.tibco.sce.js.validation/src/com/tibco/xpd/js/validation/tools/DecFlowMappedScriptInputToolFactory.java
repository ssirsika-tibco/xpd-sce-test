/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.DataMapping;

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
public class DecFlowMappedScriptInputToolFactory extends JavaScriptToolFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) input;
            boolean b =
                    ProcessScriptUtil.isDataMappingWithScriptType(dataMapping,
                            getScriptGrammar());
            if (b) {
                processor = new DecFlowMappedScriptInputTool(dataMapping);
            }
        }
        return processor;
    }

	public Class<? extends IPreProcessor> getToolClass() {
		return DecFlowMappedScriptInputTool.class;
	}
	
	protected String getScriptGrammar() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

}
