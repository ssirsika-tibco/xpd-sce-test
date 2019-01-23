/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.annotations;

import org.eclipse.gef.EditPartViewer;

import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;

/**
 * Extension of the {@link AnnotationFactoryProvider} interface that Process
 * Widget recognizes specially and will provide more information to the method
 * to create the annotation.
 * <p>
 * Contribute sub-class via the
 * <code>com.tibcop.xpd.process.widget.diagramAnnotation</code> extension point.
 * 
 * @author aallway
 * @since 15 Jun 2012
 */
public abstract class AnnotationFactoryProviderEx implements
        AnnotationFactoryProvider {

    /**
     * @see com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider#getAnnotationFactory()
     * 
     * @return
     */
    @Override
    public final AnnotationFactory getAnnotationFactory() {
        throw new RuntimeException(
                "AnnotationFactoryProviderEx.getAnnotationFactory() method should not be called when using AnnotationFactoryProviderEx()"); //$NON-NLS-1$
    }

    /**
     * Create an annotation factory.
     * 
     * @param processDiagramAdapter
     * @param editPartViewer
     * 
     * @return
     */
    public abstract AnnotationFactoryEx createAnnotationFactory(
            ProcessDiagramAdapter processDiagramAdapter,
            EditPartViewer editPartViewer);

}
