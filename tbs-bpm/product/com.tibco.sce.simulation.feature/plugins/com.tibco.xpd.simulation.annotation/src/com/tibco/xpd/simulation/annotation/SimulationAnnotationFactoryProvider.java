/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider;

/**
 * Provider of the AnnotationFactory. This simple implementation returns
 * annotation factory from AnnotationExamplePlugin<br>
 * 
 * @author wzurek
 */
public class SimulationAnnotationFactoryProvider implements
        AnnotationFactoryProvider {
    /**
     * @return The simulation annotation factory.
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider#
     *      getAnnotationFactory()
     */
    @Override
    public AnnotationFactory getAnnotationFactory() {
        /*
         * Sid - Doing some tidy up. We shouldn't really use a single static
         * factory as the process widget will call dispose on all annotation
         * factories when it closes.
         */
        return new SimulationAnnotationFactory();
    }
}
