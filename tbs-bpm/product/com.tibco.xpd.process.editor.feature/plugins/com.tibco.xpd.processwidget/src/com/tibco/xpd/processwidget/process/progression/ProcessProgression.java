/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.progression.AbstractDiagramProgression;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPartFactory;

/**
 * Overall setup /control of a BPMN Process Diagram progression's visuals and
 * model.
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public class ProcessProgression extends AbstractDiagramProgression {

    private WidgetRootEditPart widgetRootEditPart;

    /**
     * @param rootGraphicalEditPart
     */
    public ProcessProgression(WidgetRootEditPart widgetRootEditPart) {
        super(widgetRootEditPart);
        this.widgetRootEditPart = widgetRootEditPart;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.progression.AbstractDiagramProgression#
     * createProgressionPartFactory()
     */
    @Override
    protected AbstractProgressionPartFactory createProgressionPartFactory() {
        return new ProcessProgressionPartFactory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.progression.ObjectProgressionPartFactory#
     * getProgressionControlsLayer()
     */
    @Override
    public IFigure getProgressionControlsLayer() {
        return widgetRootEditPart
                .getLayer(ProcessWidgetConstants.PROGRESSION_CONTROLS_LAYER);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.progression.ObjectProgressionPartFactory#
     * getProgressionFiguresLayer()
     */
    @Override
    public IFigure getProgressionFiguresLayer() {
        return widgetRootEditPart
                .getLayer(ProcessWidgetConstants.PROGRESSION_FIGURES_LAYER);
    }

}
