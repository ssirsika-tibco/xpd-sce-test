/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * Factory for creating process flow analyser tools.
 * <p>
 * One is created per validation run of an individual process.
 * 
 * @author aallway
 * @since 3.3
 */
public class ProcessFlowAnalyserFactory implements IPreProcessorFactory {

    /**
     * @param scope
     *            The valisation scope.
     * @param input
     *            The input object.
     * @return A new loop analyser tool.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#
     *     
     *     
     *      createPreProcessor(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     */
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof FlowContainer) {
            processor = new ProcessFlowAnalyserPreProcessor((FlowContainer)input);
        }
        return processor;
    }

    /**
     * @return The loop analyser tool class.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     */
    public Class<? extends IPreProcessor> getToolClass() {
        return ProcessFlowAnalyserPreProcessor.class;
    }

}
