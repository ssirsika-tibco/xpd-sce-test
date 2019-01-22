/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.preprocessors;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Process;

/**
 * Factory for RestPreProcessor class.
 * 
 * @author nwilson
 * @since 21 May 2015
 */
public class RestPreProcessorFactory implements IPreProcessorFactory {

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
        return new RestPreProcessor((Process) input);
    }

    /**
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     * 
     * @return
     */
    @Override
    public Class<? extends IPreProcessor> getToolClass() {
        return RestPreProcessor.class;
    }

}
