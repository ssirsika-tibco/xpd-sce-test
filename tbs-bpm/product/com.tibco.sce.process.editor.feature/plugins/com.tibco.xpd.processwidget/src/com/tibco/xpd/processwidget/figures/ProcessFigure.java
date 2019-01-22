/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.FreeformLayer;

/**
 * Simple figure for containing the whole process
 * 
 * @author aallway
 * @since 3.2
 */
public class ProcessFigure extends FreeformLayer {

    /**
     * Process Diagram View type.
     * 
     * @author aallway
     * @since 3.2
     */
    public enum DiagramViewType {
        /** Standard Process View Type */
        PROCESS,
        /**
         * Task Library alternate view type (no pool headers, horizontal lane
         * headers etc.
         */
        TASK_LIBRARY_ALTERNATE,
        /**
         * NO_POOLS for diagrams with no pools/lanes.
         */
        NO_POOLS
    }

    /** XPD-1140: Allow tag process editor figure as readonly. */
    private boolean readOnly = false;

    private DiagramViewType diagramViewType;

    private ProcessConnectionLayer processConnectionLayer;

    public ProcessFigure(DiagramViewType diagramViewType, boolean readOnly,
            ProcessConnectionLayer processConnectionLayer) {
        super();
        this.diagramViewType = diagramViewType;
        this.readOnly = readOnly;
        this.processConnectionLayer = processConnectionLayer;
    }

    /**
     * @return the diagramViewType
     */
    public DiagramViewType getDiagramViewType() {
        return diagramViewType;
    }

    /**
     * @param readOnly
     *            the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @return the processConnectionLayer
     */
    public ProcessConnectionLayer getProcessConnectionLayer() {
        return processConnectionLayer;
    }
}
