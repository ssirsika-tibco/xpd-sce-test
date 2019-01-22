/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.neatstuff;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.handles.HandleBounds;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * AbstractFigureRevealAnimator
 * <p>
 * Class that provides simple animation control for highlight revealing a draw2d
 * figure.
 * 
 * @author aallway
 */
public abstract class AbstractFigureRevealAnimator extends
        AbstractFigureAnimator {

    private IFigure revealFigure;

    public AbstractFigureRevealAnimator(Viewport viewPort, IFigure animationLayer,
            IFigure revealFigure) {
        super(viewPort, animationLayer);
        this.revealFigure = revealFigure;

        return;
    }

    /**
     * @return the revealFigure
     */
    protected IFigure getRevealFigure() {
        return revealFigure;
    }

    /**
     * Get the centre location of the figure to be revealed translated to the
     * animation layer's coordinates.
     * 
     * @return the selectionCentre
     */
    protected Point getRevealCentre() {
        // calc the selection bounds within the layer's coordinate space.
        Point revealCentre;
        if (revealFigure instanceof Polyline) {
            revealCentre =
                    XPDLineUtilities
                            .getLinePointFromPortion(((Polyline) revealFigure)
                                    .getPoints(), 50);
        } else if (revealFigure instanceof HandleBounds) {
            revealCentre =
                    ((HandleBounds) revealFigure).getHandleBounds().getCenter();
        } else {
            revealCentre = revealFigure.getBounds().getCenter();
        }
        revealFigure.translateToAbsolute(revealCentre);

        getAnimationLayer().translateToRelative(revealCentre);

        return revealCentre;
    }
    
    protected Dimension getScaledSize(Dimension sz) {
        sz = sz.getCopy();
        revealFigure.translateToAbsolute(sz);
        getAnimationLayer().translateToRelative(sz);
        return sz;
    }

    protected int getScaledSize(int size) {
        Dimension sz = new Dimension(size, size);
        revealFigure.translateToAbsolute(sz);
        getAnimationLayer().translateToRelative(sz);
        return sz.width;
        
    }
}
