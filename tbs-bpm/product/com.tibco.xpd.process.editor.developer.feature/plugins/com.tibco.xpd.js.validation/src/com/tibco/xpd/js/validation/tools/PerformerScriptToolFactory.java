/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author bharge
 * 
 */
public class PerformerScriptToolFactory extends JavaScriptToolFactory {

    /**
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#createPreProcessor(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param input
     * @return
     */
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof ProcessRelevantData) {
            ProcessRelevantData processRelevantData = (ProcessRelevantData) input;
            if (processRelevantData.getDataType() instanceof BasicType) {
                BasicType basicType = (BasicType) processRelevantData.getDataType();
                if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    boolean b =
                            ProcessScriptUtil
                                    .isScriptPerformerWithScriptType(processRelevantData,
                                            getScriptGrammar());
                    if (b) {
                        processor = new PerformerScriptTool(processRelevantData);
                    }
                }

            }

        }
        return processor;
    }

    /**
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     * 
     * @return
     */
    public Class<? extends IPreProcessor> getToolClass() {
        return PerformerScriptTool.class;
    }

}
