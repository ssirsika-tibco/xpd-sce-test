/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.progression.AbstractDiagramProgression;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPartFactory;

/**
 * XPD-1431: P.O.C
 * <p>
 * Diagram progression used simply for highlighting editparts for various
 * reasons
 * 
 * @author aallway
 * @since 6 Jan 2011
 */
public class EditPartHighlighter extends AbstractDiagramProgression {

    private WidgetRootEditPart widgetRootEditPart;

    private boolean activationPrevented = false;

    private boolean persistIsActivated = false;

    /**
     * @param rootGraphicalEditPart
     */
    public EditPartHighlighter(WidgetRootEditPart widgetRootEditPart) {
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
        return null;
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
                .getLayer(ProcessWidgetConstants.EDITPART_HIGHLIGHTER_LAYER);
    }

    /**
     * This method allows the caller to prevent highlighter being activated even
     * when it is activate via activateProgression().
     * 
     * If the highlighter is already active then it will be deactivated.
     */
    public void preventActivation() {
        activationPrevented = true;

        if (super.isActivitated()) {
            super.deactivateProgression();
        }
    }

    public void allowActivation() {
        activationPrevented = false;

        if (persistIsActivated) {
            super.activateProgression();
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.progression.AbstractDiagramProgression#activateProgression()
     * 
     */
    @Override
    public void activateProgression() {
        /*
         * Keep track of activation state regardless of the real activation
         * (hence we allow activation to be prevented during certain times and
         * operations).
         */
        persistIsActivated = true;

        /* Only activate if permitted */
        if (!activationPrevented) {
            super.activateProgression();
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.progression.AbstractDiagramProgression#deactivateProgression()
     * 
     */
    @Override
    public void deactivateProgression() {
        persistIsActivated = false;
        super.deactivateProgression();
    }

}
