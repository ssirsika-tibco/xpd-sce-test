/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.js.validation.tools.JavaScriptToolFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * Script Tool Factor for Throw Global Signal Events.
 * 
 * 
 * @author kthombar
 * @since Feb 4, 2015
 */
public class ThrowGlobalSignalMappingScriptToolFactory extends
        JavaScriptToolFactory {

    /**
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#createPreProcessor(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param input
     * @return
     */
    @Override
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        ScriptInformation scriptInformation = null;

        if (input instanceof ScriptInformation) {
            /* Unmapped mapping script */
            scriptInformation = (ScriptInformation) input;
        } else if (input instanceof DataMapping) {
            /* Mapped mapping script. */
            scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping((DataMapping) input);
        }

        if (scriptInformation != null) {
            if (ProcessScriptUtil
                    .isScriptInformationWithScriptType(scriptInformation,
                            getScriptGrammar())) {
                return new ThrowGlobalSignalMappingScriptTool(scriptInformation);
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     * 
     * @return
     */
    @Override
    public Class<? extends IPreProcessor> getToolClass() {
        return ThrowGlobalSignalMappingScriptTool.class;
    }

}
