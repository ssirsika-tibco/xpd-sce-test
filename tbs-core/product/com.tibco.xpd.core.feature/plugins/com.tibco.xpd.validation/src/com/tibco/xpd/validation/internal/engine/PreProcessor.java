/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.List;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;

/**
 * Internal class to contain pre-processor details.
 *
 * @author nwilson
 */
public class PreProcessor {
    /** The pre-processor class. */
    private Class<? extends IPreProcessor> clss;

    /** The pre-processor factory. */
    private IPreProcessorFactory factory;

    /** The list of pre-processors on which this one is dependant. */
    private List<String> dependencies;

    /**
     * @param clss The pre-processor class.
     * @param factory The pre-processor factory.
     * @param dependencies The list of pre-processors on which this one is
     *            dependant.
     */
    public PreProcessor(Class<? extends IPreProcessor> clss, IPreProcessorFactory factory,
            List<String> dependencies) {
        this.clss = clss;
        this.factory = factory;
        this.dependencies = dependencies;
    }

    /**
     * @return The list of pre-processors on which this one is dependant.
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * @return The pre-processor factory.
     */
    public IPreProcessorFactory getFactory() {
        return factory;
    }

    /**
     * @return The pre-processor class.
     */
    public Class<? extends IPreProcessor> getToolClass() {
        return clss;
    }
}
