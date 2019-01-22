/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

/**
 * The IPreProcessorFactory interface must be implemented by factories defined
 * in the preProcessor extension point. It allows creation of IPreProcessor
 * tools that can be accessed by rules through the IValidationScope
 * interface.
 *
 * @author nwilson
 */
public interface IPreProcessorFactory {

    /**
     * @param scope The validation scope.
     * @param input The input object for this pre-processor.
     * @return The new pre-processor.
     */
    IPreProcessor createPreProcessor(IValidationScope scope, Object input);

    /**
     * @return The class of the pre-processor tool.
     */
    Class<? extends IPreProcessor> getToolClass();

}
