/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.Collection;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;

/**
 * Helper class to show ghosted feedback rectangles on process widget's feedback
 * layer.
 * 
 * @author aallway
 * 
 */
public class DropDiagramObjectFeedbackHelper {

    private ProcessWidgetImpl processWidgetImpl;

    private IFigure feedbackParent = null;

    DropDiagramObjectFeedbackHelper(ProcessWidgetImpl processWidgetImpl) {
        this.processWidgetImpl = processWidgetImpl;
    }

    /**
     * Create (or re-create) feedback figures for the given rectangles.
     * 
     * @param feedbackRects
     */
    public void setupFeedbackFigures(Collection<Rectangle> feedbackRects) {
        if (feedbackParent != null) {
            disposeFeedbackFigures();
        }

        if (feedbackRects != null && feedbackRects.size() > 0) {
            IFigure feebackLayer = processWidgetImpl.getFeedbackLayer();

            feedbackParent = createRawFeedbackFigures(feedbackRects);

            feebackLayer.add(feedbackParent);

        }
    }

    /**
     * Just create the feedback figures.
     * 
     * @param feedbackRects
     * @return
     */
    public static IFigure createRawFeedbackFigures(
            Collection<Rectangle> feedbackRects) {
        IFigure feedbackParent = new Figure();

        Rectangle outerBounds = null;

        for (Rectangle rc : feedbackRects) {
            // Create a ghosting figure.
            IFigure fig = createRectangleFeedbackFigure(rc);

            // fig.setVisible(false); // Don't show until first move
            feedbackParent.add(fig);

            if (outerBounds == null) {
                outerBounds = rc.getCopy();
            } else {
                outerBounds.union(rc);
            }
        }

        Dimension sz = outerBounds.getSize();
        sz.width += 2;
        sz.height += 2;
        feedbackParent.setSize(sz);
        feedbackParent.setVisible(false);

        return feedbackParent;
    }

    /**
     * @param rc
     * @return Ghosted rectangle feedback figure.
     */
    public static IFigure createRectangleFeedbackFigure(Rectangle rc) {
        IFigure fig = new RectangleFigure();

        FigureUtilities.makeGhostShape((Shape) fig);
        ((Shape) fig).setLineStyle(Graphics.LINE_DASHDOT);
        fig.setForegroundColor(ColorConstants.white);

        fig.setBounds(rc.getCopy());
        return fig;
    }

    public void updateFeedbackFigures(Point location) {
        if (feedbackParent != null) {
            IFigure feebackLayer = processWidgetImpl.getFeedbackLayer();

            feedbackParent.setLocation(location);
            if (!feedbackParent.isVisible()) {
                feedbackParent.setVisible(true);
            }
        }
    }

    /**
     * Clear and dispose of feedback figures.
     * 
     */
    public void disposeFeedbackFigures() {
        if (feedbackParent != null) {
            IFigure feebackLayer = processWidgetImpl.getFeedbackLayer();

            feebackLayer.remove(feedbackParent);
            feedbackParent = null;
        }
    }

    /**
     * @return the feedbackParent
     */
    public IFigure getFeedbackParent() {
        return feedbackParent;
    }

}
