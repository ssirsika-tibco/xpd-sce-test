/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import org.eclipse.gef.EditPartViewer;

import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProviderEx;

/**
 * Provider for Process diagram annotation factory for placing icon on process
 * activities that are valid AMX BPM process-instance migration points.
 * 
 * @author aallway
 * @since 14 Jun 2012
 */
public class MigrationPointAnnotationFactoryProvider extends
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
        return new MigrationPointAnnotationFactory(processDiagramAdapter,
                editPartViewer);
    }

}
