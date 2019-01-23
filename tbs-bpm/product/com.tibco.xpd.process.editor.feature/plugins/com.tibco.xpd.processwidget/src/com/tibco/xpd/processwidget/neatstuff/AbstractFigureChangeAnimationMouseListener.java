/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener.Stub;
import org.eclipse.swt.widgets.Display;

/**
 * FigureChangeAnimationMouseListener
 * <p>
 * Performs a subclass derived change(s) to one or more figures on a timed basis
 * (25 frames per sec) when mouse enters and cancels when mouse exits.
 * <p>
 * Add this to as a motion listener to all the figures you want to change
 * together. Or add to some separate figure.
 * 
 * @author aallway
 */
public abstract class AbstractFigureChangeAnimationMouseListener extends Stub {

    private Collection<? extends IFigure> figures;

    private boolean in = false;

    protected int FRAME_RATE = 40;

    protected int mouseEnterDelay = FRAME_RATE;

    protected int mouseExitDelay = FRAME_RATE;

    boolean enabled = true;

    public AbstractFigureChangeAnimationMouseListener(
            Collection<? extends IFigure> figures) {
        super();
        this.figures = figures;
    }

    protected abstract void prepareMouseEnterAnimate(
            Collection<? extends IFigure> figures);

    /**
     * Animate next frame after mouse entered.
     * 
     * @param figures
     * 
     * @return true when mouse entered animation has completed.
     */
    protected abstract boolean mouseEnterAnimate(
            Collection<? extends IFigure> figures);

    protected abstract void prepareMouseExitAnimate(
            Collection<? extends IFigure> figures);

    /**
     * Animate next frame after mouse exited.
     * 
     * @param figures
     * @param frameNum
     * @param maxFrames
     * @return true when mouse exit animation complete.
     */
    protected abstract boolean mouseExitAnimate(
            Collection<? extends IFigure> figures);

    protected abstract void resetToInitialState(
            Collection<? extends IFigure> figures);

    @Override
    public void mouseEntered(MouseEvent me) {
        if (enabled) {
            if (in == true) {
                resetToInitialState(figures);
            }

            in = true;
            prepareMouseEnterAnimate(figures);

            final Display d = Display.getCurrent();

            if (d != null && !d.isDisposed()) {
                d.timerExec(mouseEnterDelay, new Runnable() {

                    public void run() {
                        if (in == true) {

                            if (!mouseEnterAnimate(figures)) {
                                d.timerExec(FRAME_RATE, this);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (in && enabled) {
            in = false;

            prepareMouseExitAnimate(figures);

            final Display d = Display.getCurrent();
            if (d != null && !d.isDisposed()) {
                d.timerExec(mouseExitDelay, new Runnable() {

                    public void run() {
                        if (in == false) {

                            if (!mouseExitAnimate(figures)) {
                                d.timerExec(FRAME_RATE, this);
                            } else {
                                resetToInitialState(figures);
                            }
                        }
                    }
                });
            }
        }
        return;
    }

    public void returnToInitialStateAndSetEnabled(final boolean enabled) {
        in = false;

        prepareMouseExitAnimate(figures);

        final Display d = Display.getCurrent();
        if (d != null && !d.isDisposed()) {
            d.timerExec(mouseExitDelay, new Runnable() {

                public void run() {
                    if (in == false) {

                        if (!mouseExitAnimate(figures)) {
                            d.timerExec(FRAME_RATE, this);
                        } else {
                            setEnabled(enabled);
                        }
                    }
                }
            });
        }
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            resetToInitialState(figures);
        } else {
            in = false;
        }
    }

    /**
     * @return is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return the mouseEnterDelay
     */
    public int getMouseEnterDelay() {
        return mouseEnterDelay;
    }

    /**
     * @param mouseEnterDelay
     *            the mouseEnterDelay to set
     */
    public void setMouseEnterDelay(int mouseEnterDelay) {
        this.mouseEnterDelay = mouseEnterDelay;
    }

    /**
     * @return the mouseExitDelay
     */
    public int getMouseExitDelay() {
        return mouseExitDelay;
    }

    /**
     * @param mouseExitDelay
     *            the mouseExitDelay to set
     */
    public void setMouseExitDelay(int mouseExitDelay) {
        this.mouseExitDelay = mouseExitDelay;
    }

}
