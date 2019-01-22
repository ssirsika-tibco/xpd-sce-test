/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * AbstractFigureAnimator
 * <p>
 * Class that provides simple animation control.
 * 
 * @author aallway
 */
public abstract class AbstractFigureAnimator implements Runnable {

    private boolean cancelled = false;

    private Viewport viewPort;

    private IFigure animationLayer;

    private int frameRate = 40;

    private int frameCount = 0;

    private List<IFigure> animFigures = Collections.EMPTY_LIST;

    private long startTime;

    public AbstractFigureAnimator(Viewport viewPort, IFigure layer) {
        this.viewPort = viewPort;

        this.animationLayer = layer;

        return;
    }

    /**
     * @return the frameRate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * @param frameRate
     *            the frameRate to set
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    /**
     * @return the animationLayer
     */
    public IFigure getAnimationLayer() {
        return animationLayer;
    }

    /**
     * @return the viewPort
     */
    public Viewport getViewPort() {
        return viewPort;
    }

    /**
     * Start the animation.
     */
    public void startAnimation() {
        frameCount = 0;
        cancelled = false;

        animFigures = createAnimationFigures();
        if (animFigures == null) {
            animFigures = Collections.EMPTY_LIST;
        }

        // Only add figures as they come into visible areas (i.e. during the
        // run).
        // Figure canvas isn't very clever when you move figures outside of
        // viewport.
        // for (IFigure fig : animFigures) {
        // animationLayer.add(fig);
        // }

        startTime = System.currentTimeMillis();

        Display d = Display.getCurrent();
        if (d != null && !d.isDisposed()) {
            d.timerExec(frameRate, this);
        }

        return;
    }

    /**
     * Get the figures to add to the animation layer.
     * <p>
     * The returned figures will be added to the annotation layer.
     * <p>
     * If the animation does not require extra figures (i.e. animation involves
     * changing some original figure) then return null or empty list.
     * <p>
     * This method is called each time the animation is started with
     * startAnimation().
     * 
     * @return the figures that form the animation (if any).
     */
    protected abstract List<IFigure> createAnimationFigures();

    /**
     * Perform next frame of animation.
     * <p>
     * Note that it is permitted to end the animation (endAnimation()) during
     * this method call. For instance when the animation - the animation
     * 
     * @param animFigures
     *            List that was originally returned by createAnimationFigures()
     * @param frameCount
     *            Current accumulated frameCount
     */
    protected abstract void animateFigures(List<IFigure> animFigures,
            int frameCount);

    /**
     * Perform any disposal require at end of life of animation.
     * <p>
     * The figures will already have been removed from animation layer.
     * <p>
     * This method is called 'the frame after' a call to endAnimation or
     * cacnelAnsimation.
     */
    protected abstract void disposeAnimationFigures();

    /**
     * End the animation.
     */
    public void endAnimation() {
        cancelled = true;
        return;
    }

    /**
     * @param cancelled
     *            the cancelled to set
     */
    public void cancelAnimation(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Perform the next step in animation on a timed basis.
     */
    public void run() {
        //
        // Check for end of animation or animation cancelled.
        //
        frameCount++;
        if (cancelled) {
            long endTime = System.currentTimeMillis();

            // System.out
            //                    .println("Average Frame Time (ms): " + ((endTime - startTime) / frameCount)); //$NON-NLS-1$

            for (IFigure fig : animFigures) {
                if (fig.getParent() != null) {
                    animationLayer.remove(fig);
                }
            }
            disposeAnimationFigures();

            animationLayer.repaint();
            return;
        }

        // Save total area occupiued by anim figures before moving.
        Rectangle animBoundsBefore = getAnimFiguresBounds();

        //
        // Let sub-class move/change figures as required.
        //
        animateFigures(animFigures, frameCount);

        // 
        // Only add figures as they come into visible areas (i.e. during the
        // run).
        // Figure canvas isn't very clever when you move figures outside of
        // viewport.
        //
        Rectangle animBoundsAfter = getAnimFiguresBounds();
        Rectangle visibleAnimBounds = null;

        if (animBoundsBefore != null && animBoundsAfter != null) {
            Rectangle animTotal =
                    animBoundsBefore.getCopy().union(animBoundsAfter);

            visibleAnimBounds = getVisibleBounds();
            visibleAnimBounds.intersect(animTotal);
        }

        for (IFigure fig : animFigures) {
            Rectangle b = fig.getBounds().getCopy();

            // Get the intersection between the figure and the visible bounds.
            Rectangle intersect = b.getCopy().intersect(visibleAnimBounds);

            // If the intersection is not the same as figure bounds then it is
            // not completely within the visible bounds remove it.
            // Otherwise add it.
            if (!b.intersects(visibleAnimBounds)) {
                if (fig.getParent() != null) {
                    animationLayer.remove(fig);
                }
            } else {
                if (fig.getParent() == null) {
                    animationLayer.add(fig);
                }
            }
        }

        if (visibleAnimBounds != null) {
            animationLayer.repaint(visibleAnimBounds);
        }

        Display d = Display.getCurrent();
        if (d != null && !d.isDisposed()) {
            d.timerExec(frameRate, this);
        }

        return;
    }

    /**
     * @return the cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @param cancelled
     *            the cancelled to set
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return The bounds of the view port, translated appropriately to the
     *         animationLayer's coordinates.
     */
    public Rectangle getVisibleBounds() {
        Rectangle vpb = viewPort.getBounds().getCopy();
        viewPort.translateToAbsolute(vpb);
        getAnimationLayer().translateToRelative(vpb);

        return vpb;
    }

    /**
     * @return
     */
    private Rectangle getAnimFiguresBounds() {
        Rectangle animBounds = null;
        for (IFigure fig : animFigures) {
            if (animBounds == null) {
                animBounds = fig.getBounds().getCopy();
            } else {
                animBounds = animBounds.union(fig.getBounds().getCopy());
            }
        }
        return animBounds;
    }

}
