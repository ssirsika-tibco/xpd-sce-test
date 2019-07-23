/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.mapping;

import com.tibco.xpd.js.validation.tools.JavaScriptToolFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * Mapped script tool factory for Catch Local Signal Events.
 *
 * @author sajain
 * @since Jul 18, 2019
 */
public class CatchLocalSignalMappedScriptToolFactory extends JavaScriptToolFactory {

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
        DataMapping mapping = null;
        if (input instanceof DataMapping) {
            mapping = (DataMapping) input;
        }

        if (mapping != null) {
            if (ProcessScriptUtil.isScriptInformationWithScriptType(
                    ProcessScriptUtil.getScriptInformationFromDataMapping(mapping),
                    getScriptGrammar())) {
                return new CatchLocalSignalMappedScriptTool(mapping);
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
        return CatchLocalSignalMappedScriptTool.class;
    }

}
