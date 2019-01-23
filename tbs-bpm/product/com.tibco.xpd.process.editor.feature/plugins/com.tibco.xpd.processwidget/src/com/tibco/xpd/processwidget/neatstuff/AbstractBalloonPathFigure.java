package com.tibco.xpd.processwidget.neatstuff;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Path;

import com.tibco.xpd.processwidget.figures.BaseLogExceptionFadeableFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Figure to draw the sub-class provided graphics path(s) as a thick line that
 * fades toward the outer edges.
 * 
 * @author aallway
 * @since 3.3 (25 Jan 2010)
 */
public abstract class AbstractBalloonPathFigure extends
        BaseLogExceptionFadeableFigure {

    private Path[] paths = null;

    private int lineWidth = 5;

    private int maxGranularity = 5;

    /**
     * 
     * @param lineWidth
     *            Width of the lines drawn.
     * @param maxGranularity
     *            The granularity factor. Bsaically the lower the granularity
     *            the more 'blocky' the balloon effect is. The paths are drawn
     *            maxGranularity times with increasing opacity to create the
     *            balloon effect. Therefore a low value here will create a
     *            blocky effect and a high value (>=lineWidth) will create a
     *            smooth effect at 100% zoom (more block as Zoom in). It is
     *            recommended that you do not use high values on larger
     *            linewidths as this will be quite ineffecient.
     */
    public AbstractBalloonPathFigure(int lineWidth, int maxGranularity) {
        setOpaque(false);
        setLineWidth(lineWidth);
        setMaxGranularity(maxGranularity);
    }

    public AbstractBalloonPathFigure() {
        this(5, 5);
    }

    /**
     * @param maxGranularity
     *            The granularity factor. Bsaically the lower the granularity
     *            the more 'blocky' the balloon effect is. The paths are drawn
     *            maxGranularity times with increasing opacity to create the
     *            balloon effect. Therefore a low value here will create a
     *            blocky effect and a high value (>=lineWidth) will create a
     *            smooth effect at 100% zoom (more block as Zoom in). It is
     *            recommended that you do not use high values on larger
     *            linewidths as this will be quite ineffecient.
     */
    public void setMaxGranularity(int maxGranularity) {
        if (maxGranularity < 2) {
            throw new RuntimeException("maxGranularity  cannot be < 2"); //$NON-NLS-1$
        }
        this.maxGranularity = maxGranularity;
    }

    /**
     * @param lineWidth
     *            the lineWidth to set
     */
    public void setLineWidth(int lineWidth) {
        if (lineWidth < 3) {
            throw new RuntimeException("Line width cannot be < 3 pixels"); //$NON-NLS-1$
        }

        this.lineWidth = lineWidth;
    }

    /**
     * @return the lineWidth
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * @return Create the paths that will be drawn to highlight activity
     *         in-progress
     */
    protected abstract Path[] createPaths();

    /**
     * @see org.eclipse.draw2d.Figure#setBounds(org.eclipse.draw2d.geometry.Rectangle)
     * 
     * @param rect
     */
    @Override
    public void setBounds(Rectangle rect) {
        super.setBounds(rect);

        disposePaths();
    }

    /**
     * @see org.eclipse.draw2d.Figure#setSize(int, int)
     * 
     * @param w
     * @param h
     */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        disposePaths();
    }

    /**
     * @see org.eclipse.draw2d.Figure#setLocation(org.eclipse.draw2d.geometry.Point)
     * 
     * @param p
     */
    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
        disposePaths();
    }

    /**
     * dispose the paths
     */
    protected void disposePaths() {
        if (paths != null) {
            for (Path path : paths) {
                path.dispose();
            }

            paths = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    @Override
    protected void paintFigure(Graphics graphics) {
        //        System.out.println("==> paintFigure(progressionFigure)"); //$NON-NLS-1$
        if (paths == null) {
            paths = createPaths();
        }

        if (paths != null) {
            graphics.pushState();
            graphics.setAntialias(SWT.ON);
            graphics.setLineCap(SWT.CAP_ROUND);

            /*
             * Scale the line width according to the zoom scale.
             */
            int scaleWidth =
                    (int) (lineWidth * XPDFigureUtilities.getFigureScale(this));
            if (scaleWidth < 1) {
                scaleWidth = 1;
            }

            /*
             * We will draw the paths multiple times,
             * 
             * First time at full width then we reduce the linewidth by one and
             * paint again and so on until lineWidth is only 1.
             * 
             * We do this at a transparency level calculated so that by the time
             * the center of the line has been overlaid #lineWidth times it will
             * appear to be fully opaque (or rather the nominal alpha level
             * already set on this figure via setAlpha().
             */
            float decreasingWidth = scaleWidth;

            /*
             * Calculate the number of times we should re-draw the paths.
             * Nominally this is the lineWidth but if the lineWidth gets larger
             * we don't want to repaint too many times.
             * 
             * But we let the caller decide.
             */
            int numPaintIterationsToDo = Math.min(scaleWidth, maxGranularity);

            /*
             * The transparency level is calculated so that overlay-painting the
             * paths #lineWidth times will make the middle of path equal to the
             * figure's nominal alpha.
             */
            int transparencyLevel = (getAlpha() / (numPaintIterationsToDo + 1));
            graphics.setAlpha(transparencyLevel);

            while (decreasingWidth > 0) {
                graphics.setLineWidth((int) decreasingWidth);
                drawPaths(graphics);

                decreasingWidth -=
                        (float) scaleWidth / (float) numPaintIterationsToDo;
            }

            graphics.popState();
        }
        //        System.out.println("<== paintFigure(progressionFigure)"); //$NON-NLS-1$
        return;
    }

    /**
     * @param graphics
     */
    private void drawPaths(Graphics graphics) {
        for (Path path : paths) {
            graphics.drawPath(path);
        }
    }

}