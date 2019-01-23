/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * @author nwilson
 */
public class RestServiceJScriptToolFactory extends JavaScriptToolFactory {

    @Override
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof ScriptInformation) {
            ScriptInformation scriptInformation = (ScriptInformation) input;
            boolean b =
                    ProcessScriptUtil
                            .isScriptInformationWithScriptType(scriptInformation,
                                    getScriptGrammar());
            if (b) {
                processor = new RestServiceJScriptTool(scriptInformation);
            }
        } else if (input instanceof DataMapping) {
            DataMapping mapping = (DataMapping) input;
            boolean b =
                    ProcessScriptUtil.isDataMappingWithScriptType(mapping,
                            getScriptGrammar());
            if (b) {
                processor = new RestServiceJScriptTool(mapping);
            }
        }
        return processor;
    }

    @Override
    public Class<? extends IPreProcessor> getToolClass() {
        return RestServiceJScriptTool.class;
    }

}
