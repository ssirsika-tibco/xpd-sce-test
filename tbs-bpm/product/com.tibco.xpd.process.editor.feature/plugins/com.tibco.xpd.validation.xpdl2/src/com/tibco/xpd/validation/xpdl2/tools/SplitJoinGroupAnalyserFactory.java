/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Process;

/**
 * Factory for creating split-join analyser tools.
 *
 * @author nwilson
 */
public class SplitJoinGroupAnalyserFactory implements IPreProcessorFactory {

    /**
     * @param scope The validation scope.
     * @param input The input object.
     * @return The split-join group analyser tool.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#
     *      createPreProcessor(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     */
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof Process) {
            TransitionTool tool = scope.getTool(TransitionTool.class, input);
            LoopGrouper loopGrouper = scope.getTool(LoopGrouper.class, input);
            processor = new SplitJoinGroupAnalyser(tool, loopGrouper);
        }
        return processor;
    }

    /**
     * @return The split-join group analyser tool class.
     * @see com.tibco.xpd.validation.provider.IPreProcessorFactory#getToolClass()
     */
    public Class<? extends IPreProcessor> getToolClass() {
        return SplitJoinGroupAnalyser.class;
    }

}
