/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * GrowFigureAnimationMouseMotionListener
 * 
 * 
 * @author aallway
 * @since 3.3 (7 Sep 2009)
 */
public class GrowFigureAnimationMouseMotionListener extends
        AbstractFigureChangeAnimationMouseListener {

    private Dimension initialSize;

    private Dimension finalSize;

    private int growTimeMillisecs;

    private int shrinkTimeMillisecs;

    private int frameCount = 0;

    private double cxRate;

    private double cyRate;

    private double preciseWidth = 0;

    private double preciseHeight = 0;

    /**
     * @param figures
     */
    public GrowFigureAnimationMouseMotionListener(IFigure figure,
            Dimension initialSize, Dimension finalSize, int growTimeMillisecs,
            int shrinkTimeMillisecs) {
        super(Collections.singletonList(figure));

        this.initialSize = initialSize;
        this.finalSize = finalSize;
        this.growTimeMillisecs = growTimeMillisecs;
        this.shrinkTimeMillisecs = shrinkTimeMillisecs;

    }

    @Override
    protected void prepareMouseEnterAnimate(
            Collection<? extends IFigure> figures) {
        frameCount = 0;

        IFigure figure = figures.iterator().next();

        if (figure != null) {

            /*
             * Calculate:
             * "In order to go from initial size to final size must add this amount each frame."
             */
            double numFrames = (double) growTimeMillisecs / FRAME_RATE;
            if (numFrames < 1) {
                numFrames = 1;
            }

            cxRate = ((double) finalSize.width - initialSize.width) / numFrames;
            cyRate =
                    ((double) finalSize.height - initialSize.height)
                            / numFrames;

            Dimension curSize = figure.getSize();
            preciseWidth = curSize.width;
            preciseHeight = curSize.height;

        }
        return;
    }

    @Override
    protected boolean mouseEnterAnimate(Collection<? extends IFigure> figures) {
        IFigure figure = figures.iterator().next();

        if (figure != null) {
            if (finalSize.equals(figure.getSize())) {
                return true;
            }

            frameCount++;

            preciseWidth += cxRate;
            preciseHeight += cyRate;

            Dimension newSize =
                    new Dimension((int) preciseWidth, (int) preciseHeight);

            if (newSize.width > finalSize.width) {
                newSize.width = finalSize.width;
            }

            if (newSize.height > finalSize.height) {
                newSize.height = finalSize.height;
            }

            setFigureBounds(figure, newSize);

        }

        return false;
    }

    @Override
    protected void prepareMouseExitAnimate(Collection<? extends IFigure> figures) {
        frameCount = 0;
        IFigure figure = figures.iterator().next();

        if (figure != null) {

            /*
             * Calculate:
             * "In order to go from initial size to final size must add this amount each frame."
             */
            double numFrames = (double) shrinkTimeMillisecs / FRAME_RATE;

            if (numFrames < 1) {
                numFrames = 1;
            }

            cxRate = ((double) initialSize.width - finalSize.width) / numFrames;
            cyRate =
                    ((double) initialSize.height - finalSize.height)
                            / numFrames;

            Dimension curSize = figure.getSize();
            preciseWidth = curSize.width;
            preciseHeight = curSize.height;

        }

        return;
    }

    @Override
    protected boolean mouseExitAnimate(Collection<? extends IFigure> figures) {
        IFigure figure = figures.iterator().next();

        if (figure != null) {
            if (initialSize.equals(figure.getSize())) {
                return true;
            }

            frameCount++;

            preciseWidth += cxRate;
            preciseHeight += cyRate;

            Dimension newSize =
                    new Dimension((int) preciseWidth, (int) preciseHeight);

            if (newSize.width < initialSize.width) {
                newSize.width = initialSize.width;
            }

            if (newSize.height < initialSize.height) {
                newSize.height = initialSize.height;
            }

            setFigureBounds(figure, newSize);

        }

        return false;
    }

    @Override
    protected void resetToInitialState(Collection<? extends IFigure> figures) {
        IFigure figure = figures.iterator().next();
        if (figure != null) {
            setFigureBounds(figure, initialSize);
        }

        return;
    }

    /**
     * @return the initialSize
     */
    public Dimension getInitialSize() {
        return initialSize;
    }

    /**
     * @param initialSize
     *            the initialSize to set
     */
    public void setInitialSize(Dimension initialSize) {
        this.initialSize = initialSize;
    }

    /**
     * @return the finalSize
     */
    public Dimension getFinalSize() {
        return finalSize;
    }

    /**
     * @param finalSize
     *            the finalSize to set
     */
    public void setFinalSize(Dimension finalSize) {
        this.finalSize = finalSize;
    }

    private void setFigureBounds(IFigure figure, Dimension size) {
        Rectangle b = figure.getBounds().getCopy();

        Point c = b.getCenter();
        b.x = c.x - (size.width / 2);
        b.y = c.y - (size.height / 2);

        b.width = size.width;
        b.height = size.height;

        /*
         * Reset the bounds.
         */
        figure.setBounds(b);

        /*
         * If the figure is subject to layout in its parent that is based on
         * rectangle constraint then reset the constraint too.
         */
        if (figure.getParent() != null) {
            LayoutManager layout = figure.getParent().getLayoutManager();
            if (layout != null) {
                if (layout.getConstraint(figure) instanceof Rectangle) {
                    b = ((Rectangle) layout.getConstraint(figure)).getCopy();
                    c = b.getCenter();
                    b.x = c.x - (size.width / 2);
                    b.y = c.y - (size.height / 2);

                    b.width = size.width;
                    b.height = size.height;

                    layout.setConstraint(figure, b);
                }
            }
        }

        /*
         * Force a Layout the content of the figure.
         */
        if (figure.getLayoutManager() != null) {
            figure.getLayoutManager().layout(figure);
        }

        return;
    }

}
