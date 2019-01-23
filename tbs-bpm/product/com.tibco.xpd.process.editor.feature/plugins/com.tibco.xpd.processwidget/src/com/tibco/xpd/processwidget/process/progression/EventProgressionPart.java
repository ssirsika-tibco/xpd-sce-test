/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.HandleBounds;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;

/**
 * Proess progression part for task acvititys.
 * 
 * @author aallway
 * @since 3.3 (26 Jan 2010)
 */
public class EventProgressionPart extends AbstractPulsingProgressionPart {

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public EventProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);
    }

    @Override
    protected IFadeableFigure createInProgressFigure(
            IFigure progressionFiguresLayer) {
        EventInProgressFigure figure = new EventInProgressFigure();

        return figure;
    }

    /**
     * @see com.tibco.xpd.processwidget.process.progression.AbstractPulsingProgressionPart#locateInProgressFigure(org.eclipse.draw2d.IFigure,
     *      org.eclipse.draw2d.IFigure)
     * 
     * @param inProgressFigure
     * @param hostFigure
     */
    @Override
    protected void locateInProgressFigure(IFigure inProgressFigure,
            IFigure hostFigure) {
        Rectangle bnds;

        if (hostFigure instanceof HandleBounds) {
            bnds = ((HandleBounds) hostFigure).getHandleBounds();
        } else {
            bnds = hostFigure.getBounds().getCopy();
        }

        bnds
                .expand(((EventInProgressFigure) inProgressFigure)
                        .getLineWidth() - 1,
                        ((EventInProgressFigure) inProgressFigure)
                                .getLineWidth());

        hostFigure.translateToAbsolute(bnds);

        getProgressionFiguresLayer().translateToRelative(bnds);
        inProgressFigure.setBounds(bnds);

        return;
    }

}
