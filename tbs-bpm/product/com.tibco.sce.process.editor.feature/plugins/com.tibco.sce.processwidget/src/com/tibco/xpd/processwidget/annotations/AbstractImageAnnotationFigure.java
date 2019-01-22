/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.annotations;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.neatstuff.FadeableImageFigure;

/**
 * A figure that can be used as an annotation image associated with a process
 * diagram object figure.
 * <p>
 * The sub-class is left to provide the image and the location of the given
 * image in the same co-ordinate system (so if your figure is to be located at
 * the top left of the anotation's host diagram object figure then the
 * {@link #calculateLocation(IFigure)} method can simply return
 * diagramObjectFigure.getTopLeft().
 * <p>
 * <b>The creator <i>must</i> clean up the figure using
 * {@link #aboutToRemove(IFigure)} when it is about to be removed from the
 * diagram for any reason, otherwise the figure will be left hanging, listening
 * to the host figure.</b>
 * <p>
 * This class deals with listening to the diagram object figure and it's
 * ancestors for events that would cause the need to the annotation to be moved
 * and recalculates the location as necessary.
 * <p>
 * Hides the annotation figure if the host figure is not visible (and visa
 * versa).
 * 
 * @author aallway
 * @since 14 Jun 2012
 */
public abstract class AbstractImageAnnotationFigure extends FadeableImageFigure
        implements FigureListener, AncestorListener {

    protected IFigure originalDiagramObjectFigure;

    public AbstractImageAnnotationFigure(IFigure diagramObjectFigure, Image img) {
        super(img);
        this.originalDiagramObjectFigure = diagramObjectFigure;

        setImage(img);
        setupFigureMovedListener(diagramObjectFigure);
        setupFigureAncestorListener(diagramObjectFigure);

        calculateBounds(diagramObjectFigure);
    }

    /**
     * @param diagramObjectFigure
     * 
     * @return The location of the image (in the same co-ordinate sytem as the
     *         host figure.
     */
    protected abstract Point calculateLocation(IFigure diagramObjectFigure);

    /**
     * Called when the annotation figure is about to be removed from the host.
     * 
     * @param diagramObjectFigure
     */
    public void aboutToRemove(IFigure diagramObjectFigure) {
        removeFigureMovedListener(diagramObjectFigure);
        removeFigureAncestorListener(diagramObjectFigure);
    }

    /**
     * Setup the listener for the diagram figure moving - by default this will
     * detect the top level diagramObjectFigure figure moving.
     * 
     * @param diagramObjectFigure
     */
    protected void setupFigureMovedListener(IFigure diagramObjectFigure) {
        diagramObjectFigure.addFigureListener(this);
    }

    protected void removeFigureMovedListener(IFigure diagramObjectFigure) {
        diagramObjectFigure.removeFigureListener(this);
    }

    /**
     * Setup the listener for the diagram figure moving - by default this will
     * detect the top level diagramObjectFigure figure moving.
     * 
     * @param diagramObjectFigure
     */
    protected void setupFigureAncestorListener(IFigure diagramObjectFigure) {
        diagramObjectFigure.addAncestorListener(this);
    }

    protected void removeFigureAncestorListener(IFigure diagramObjectFigure) {
        diagramObjectFigure.removeAncestorListener(this);
    }

    @Override
    public void ancestorAdded(IFigure ancestor) {
        calculateBounds(originalDiagramObjectFigure);
    }

    @Override
    public void ancestorMoved(IFigure ancestor) {
        calculateBounds(originalDiagramObjectFigure);
    }

    @Override
    public void ancestorRemoved(IFigure ancestor) {
        calculateBounds(originalDiagramObjectFigure);
    }

    /**
     * Callback to indicate that the base figure has moved.
     * 
     * @param diagramObjectFigure
     *            The diagram figure for the model element with a problem on it.
     * 
     * @see org.eclipse.draw2d.FigureListener#figureMoved(org.eclipse.draw2d.IFigure)
     */
    @Override
    public void figureMoved(IFigure diagramObjectFigure) {
        calculateBounds(diagramObjectFigure);
    }

    @Override
    protected void layout() {
        if (getLayoutManager() != null) {
            getLayoutManager().layout(this);
        } else {
            if (originalDiagramObjectFigure != null) {
                calculateBounds(originalDiagramObjectFigure);
            }
        }

    }

    /**
     * Convert from host figure's co-ord system to annotation's.
     * 
     * @param pt
     */
    protected void translateFromHostFigureToAnnotationLayer(Point pt) {
        if (originalDiagramObjectFigure != null && this.getParent() != null) {
            originalDiagramObjectFigure.translateToAbsolute(pt);
            this.getParent().translateToRelative(pt);
        }
    }

    /**
     * Calculate the bounds of this object according to the host figure's setup.
     * 
     * @param diagramObjectFigure
     */
    protected void calculateBounds(IFigure diagramObjectFigure) {

        if (diagramObjectFigure != null) {
            if (isHostVisible(diagramObjectFigure)) {
                org.eclipse.swt.graphics.Rectangle imgBnds =
                        getImage().getBounds();
                Dimension d = new Dimension(imgBnds.width, imgBnds.height);

                Point location =
                        calculateLocation(diagramObjectFigure).getCopy();

                translateFromHostFigureToAnnotationLayer(location);

                this.setBounds(new Rectangle(location, d));

                this.setVisible(true);

            } else {
                this.setVisible(false);
            }
        }

        return;
    }

    /**
     * This implementation of containsPoint() returns true if the coordinate is
     * within the associated annotation image or is within an 6 pixel 'extra
     * border' but NOT in the host diagram object figure.
     * <p>
     * Thus it provides an invisible 8 pixels border around the original image
     * except that where that intersects the host figure's bounds, the host
     * figure's bounds is allowed to take precedence (GEF will ask the
     * underlying host figure if it contains point ONLY if we return false).
     * 
     * @param x
     * @param y
     * @return <code>true</code> if this annotation figure contains the given
     *         point (with an 6 pixel boundary).
     */
    @Override
    public boolean containsPoint(int x, int y) {
        Rectangle b = this.getBounds().getCopy();
        if (b.contains(x, y)) {
            // If the marker itself contains the point then always return true.
            return true;
        }

        // Otherwise double the size of the marker detection and say that it
        // contains point IF mouse is inside this BUT NOT inside host
        // figure.
        Rectangle hb = originalDiagramObjectFigure.getBounds().getCopy();
        originalDiagramObjectFigure.translateToAbsolute(hb);
        this.getParent().translateToRelative(hb);

        b.expand(6, 6);

        if (b.contains(x, y) && !hb.contains(x, y)) {
            return true;
        }

        return false;
    }

    /**
     * Return if the given figure is visible (if any ancestor is invisible then
     * the given figure is counted as invisible too).
     * 
     * @param diagramObjectFigure
     * @return
     */
    private boolean isHostVisible(IFigure diagramObjectFigure) {

        IFigure parent = diagramObjectFigure;
        while (parent != null) {
            if (!parent.isVisible()) {
                return false;
            }

            parent = parent.getParent();
        }

        return true;
    }

}