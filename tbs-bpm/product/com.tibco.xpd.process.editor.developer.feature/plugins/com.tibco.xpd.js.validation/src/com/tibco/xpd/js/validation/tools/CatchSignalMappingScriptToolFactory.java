/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * Factory for {@link CatchSignalMappingScriptTool} (Validation for catch signal
 * event mappings.)
 * 
 * @author aallway
 * @since 9 May 2012
 */
public class CatchSignalMappingScriptToolFactory extends JavaScriptToolFactory {

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
                return new CatchSignalMappingScriptTool(scriptInformation);
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
        return CatchSignalMappingScriptTool.class;
    }

}
