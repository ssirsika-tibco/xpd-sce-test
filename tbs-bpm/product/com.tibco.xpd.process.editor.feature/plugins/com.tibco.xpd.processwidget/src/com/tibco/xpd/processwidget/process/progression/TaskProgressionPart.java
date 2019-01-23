/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;

/**
 * Process progression part for task activities.
 * 
 * @author aallway
 * @since 3.3 (26 Jan 2010)
 */
public class TaskProgressionPart extends AbstractPulsingProgressionPart {

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public TaskProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);
    }

    @Override
    protected IFadeableFigure createInProgressFigure(
            IFigure progressionFiguresLayer) {
        TaskInProgressFigure figure = new TaskInProgressFigure();

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
        Rectangle bnds = hostFigure.getBounds().getCopy();

        bnds
                .expand(((TaskInProgressFigure) inProgressFigure)
                        .getLineWidth() + 2,
                        ((TaskInProgressFigure) inProgressFigure)
                                .getLineWidth() + 2);

        hostFigure.translateToAbsolute(bnds);

        getProgressionFiguresLayer().translateToRelative(bnds);
        inProgressFigure.setBounds(bnds);

        return;
    }

}
