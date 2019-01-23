/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Factory for creating transition tools.
 * 
 * @author nwilson
 */
public class TransitionsToolFactory implements IPreProcessorFactory {

    /**
     * @param scope The validation scope.
     * @param input The input object.
     * @return A new transition tool.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#
     *      createPreProcessor(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     */
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof FlowContainer) {
            FlowContainer container = (FlowContainer) input;
            processor = new TransitionTool(container);
        }
        return processor;
    }

    /**
     * @return The transition tool class.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     */
    public Class<? extends IPreProcessor> getToolClass() {
        return TransitionTool.class;
    }

}
