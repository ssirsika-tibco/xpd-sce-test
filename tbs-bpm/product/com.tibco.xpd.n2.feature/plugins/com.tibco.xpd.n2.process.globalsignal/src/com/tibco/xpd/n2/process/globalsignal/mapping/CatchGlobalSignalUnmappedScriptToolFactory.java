/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.js.validation.tools.JavaScriptToolFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Script Tool Factory for Catch Global Signal Unmapped Events.
 * 
 * @author ssirsika
 * @since 02-May-2016
 */
public class CatchGlobalSignalUnmappedScriptToolFactory extends
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
        }
        if (scriptInformation != null) {
            if (ProcessScriptUtil
                    .isScriptInformationWithScriptType(scriptInformation,
                            getScriptGrammar())) {
                return new CatchGlobalSignalUnmappedScriptTool(
                        scriptInformation);
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
        return CatchGlobalSignalUnmappedScriptTool.class;
    }

}
