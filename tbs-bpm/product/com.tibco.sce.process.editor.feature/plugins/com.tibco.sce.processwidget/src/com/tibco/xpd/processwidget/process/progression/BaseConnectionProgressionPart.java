/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;

/**
 * Progression part for BaseConnectionEditParts.
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Nov 2009)
 */
public class BaseConnectionProgressionPart extends
        AbstractPulsingProgressionPart {

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public BaseConnectionProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);

        setInitPulsingAlpha(75);
        setFinalPulsingAlpha(150);

    }

    /**
     * @see com.tibco.xpd.processwidget.process.progression.AbstractPulsingProgressionPart#createInProgressFigure(org.eclipse.draw2d.IFigure)
     * 
     * @param progressionFiguresLayer
     * @return
     */
    @Override
    protected IFadeableFigure createInProgressFigure(
            IFigure progressionFiguresLayer) {
        GraphicalEditPart diagramObjectEditPart = getDiagramObjectEditPart();
        IFigure figure = diagramObjectEditPart.getFigure();
        if (figure instanceof Polyline) {
            PointList points = ((Polyline) figure).getPoints().getCopy();

            figure.translateToAbsolute(points);

            progressionFiguresLayer.translateToRelative(points);

            ConnectionInProgressFigure progressionFigure =
                    new ConnectionInProgressFigure(points);

            if (isShouldAnimateInProgressFigure()) {
                progressionFigure.setLineWidth(11);
            } else {
                progressionFigure.setLineWidth(7);
            }

            return progressionFigure;
        }

        return null;
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
            IFigure figure) {
        if (figure instanceof Polyline) {
            PointList points = ((Polyline) figure).getPoints().getCopy();

            Rectangle bounds = figure.getBounds().getCopy();

            figure.translateToAbsolute(bounds);

            figure.translateToAbsolute(points);

            getProgressionFiguresLayer().translateToRelative(points);
            getProgressionFiguresLayer().translateToRelative(bounds);

            ((ConnectionInProgressFigure) inProgressFigure).setPoints(points);

            inProgressFigure.setBounds(bounds);
        }
        return;
    }

    /**
     * @see com.tibco.xpd.processwidget.progression.BaseFadingProgressionPart#getFigureAlphaForState(com.tibco.xpd.processwidget.progression.model.ProgressionState)
     * 
     * @param progressionState
     * @return
     */
    @Override
    protected int getFigureAlphaForState(ProgressionState progressionState) {
        int figureAlphaForState =
                super.getFigureAlphaForState(progressionState);

        /*
         * Don't obliterate the original connection line if we're not pulsing
         * the figure up and down)!
         */
        if (!isShouldAnimateInProgressFigure()) {
            if (figureAlphaForState > 160) {
                figureAlphaForState = 160;
            }
        }
        return figureAlphaForState;
    }

    /**
     * @see com.tibco.xpd.processwidget.process.progression.AbstractPulsingProgressionPart#setShouldAnimateInProgressFigure(boolean)
     * 
     * @param shouldAnimateInProgressFigure
     */
    @Override
    public void setShouldAnimateInProgressFigure(
            boolean shouldAnimateInProgressFigure) {
        IFadeableFigure inProgressFigure = getInProgressFigure();
        if (inProgressFigure instanceof ConnectionInProgressFigure) {
            if (shouldAnimateInProgressFigure) {
                ((ConnectionInProgressFigure) inProgressFigure)
                        .setLineWidth(11);
            } else {
                ((ConnectionInProgressFigure) inProgressFigure).setLineWidth(7);
            }
        }

        super.setShouldAnimateInProgressFigure(shouldAnimateInProgressFigure);

    }
}
