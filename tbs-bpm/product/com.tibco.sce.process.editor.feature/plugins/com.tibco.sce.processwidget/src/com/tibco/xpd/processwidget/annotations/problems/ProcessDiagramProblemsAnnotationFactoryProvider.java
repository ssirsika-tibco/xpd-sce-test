/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processwidget.annotations.problems;

import org.eclipse.gef.EditPartViewer;

import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProviderEx;

/**
 * Provider for factory for problem marker annotations of process diagram
 * objects.
 * 
 * 
 * @author aallway
 * @since 15 Jun 2012
 */
public class ProcessDiagramProblemsAnnotationFactoryProvider extends
        AnnotationFactoryProviderEx {

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactoryProviderEx#createAnnotationFactory(com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter,
     *      org.eclipse.gef.EditPartViewer)
     * 
     * @param processDiagramAdapter
     * @param editPartViewer
     * @return
     */
    @Override
    public AnnotationFactoryEx createAnnotationFactory(
            ProcessDiagramAdapter processDiagramAdapter,
            EditPartViewer editPartViewer) {
        /*
         * Sid - Doing some tidy up. We shouldn't really use a single static
         * factory as the process widget will call dispose on all annotation
         * factories when it closes.
         */
        return new ProcessDiagramProblemsAnnotationFactory(
                processDiagramAdapter);
    }
}
