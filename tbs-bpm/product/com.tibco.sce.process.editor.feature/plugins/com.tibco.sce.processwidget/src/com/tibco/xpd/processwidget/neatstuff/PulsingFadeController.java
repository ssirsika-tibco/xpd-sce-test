/**
 * 
 */
package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collection;

import org.eclipse.swt.widgets.Display;

/**
 * Pulse fade level of figure(s).
 * <p>
 * Given an initial opacity (0.0=fully transparent, 1.0=fully opaque) final
 * opacity then on {@link #start(int)}, animate the given figures to make them
 * pulse.
 * <p>
 * The creator also a period over which the pulse should fade up and down.
 * <p>
 * <b>Please Note: This controller MUST be cancelled EXPLICITLY using
 * {@link #cancel()} as the pulsing animation will not terminate on its own.
 * 
 * @author aallway
 * @since 3.2
 */
public class PulsingFadeController {
    private Collection<? extends IFadeableFigure> figures;

    private float initOpacity;

    private float finalOpacity;

    private float currentOpacity;

    private long fadeUpPeriodMillisecs;

    private long fadeDownPeriodMillisecs;

    private int FRAME_RATE = 40;

    private boolean running = false;

    private boolean fadeDownAndCancel = false;

    private boolean cancelled = false;

    private float fadeUpOpacityInc;

    private float fadeDownOpacityInc;

    private int frameCount = 0;

    private boolean fadingUp;

    private FadeDownAndCancelCompleteListener fadeDownAndCancelCompleteListener;

    private float fadeDownAndCancelOpacityInc;

    public PulsingFadeController(Collection<? extends IFadeableFigure> figures,
            float initOpacity, float finalOpacity, long fadeUpPeriodMillisecs,
            long fadeDownPeriodMillisecs) {
        this(figures, initOpacity, finalOpacity, fadeUpPeriodMillisecs,
                fadeDownPeriodMillisecs, null);
    }

    public PulsingFadeController(Collection<? extends IFadeableFigure> figures,
            float initOpacity, float finalOpacity, long fadeUpPeriodMillisecs,
            long fadeDownPeriodMillisecs,
            FadeDownAndCancelCompleteListener fadeDownAndCancelCompleteListener) {
        super();

        if (figures.isEmpty() || fadeUpPeriodMillisecs < (FRAME_RATE * 2)
                || fadeDownPeriodMillisecs < (FRAME_RATE * 2)
                || initOpacity == finalOpacity) {
            throw new IllegalArgumentException("Illegal Arguments"); //$NON-NLS-1$
        }

        this.figures = figures;
        this.finalOpacity = finalOpacity;
        this.initOpacity = initOpacity;
        this.fadeUpPeriodMillisecs = fadeUpPeriodMillisecs;
        this.fadeDownPeriodMillisecs = fadeDownPeriodMillisecs;
        this.fadeDownAndCancelCompleteListener =
                fadeDownAndCancelCompleteListener;

        if (initOpacity < finalOpacity) {
            fadingUp = true;

            fadeUpOpacityInc =
                    (finalOpacity - initOpacity)
                            / (this.fadeUpPeriodMillisecs / FRAME_RATE);

            fadeDownOpacityInc =
                    (finalOpacity - initOpacity)
                            / (this.fadeDownPeriodMillisecs / FRAME_RATE);

        } else {
            fadingUp = false;

            fadeUpOpacityInc =
                    (initOpacity - finalOpacity)
                            / (this.fadeUpPeriodMillisecs / FRAME_RATE);

            fadeDownOpacityInc =
                    (initOpacity - finalOpacity)
                            / (this.fadeDownPeriodMillisecs / FRAME_RATE);
        }

    }

    public void start(int initialDelayMillisecs) {
        setFiguresAlpha(initOpacity);

        cancelled = false;
        fadeDownAndCancel = false;
        running = true;
        currentOpacity = initOpacity;
        frameCount = 0;
        // System.out
        // .println(this.hashCode()
        //                        + ":********************************* START ******************************"); //$NON-NLS-1$

        Display.getDefault().timerExec(initialDelayMillisecs, new Runnable() {
            @Override
            public void run() {

                frameCount++;
                if (!cancelled && !Display.getDefault().isDisposed()) {

                    animateFrame();

                    /*
                     * Repeat until reach final opacity.
                     */
                    if (!cancelled) {
                        if (!Display.getDefault().isDisposed()) {
                            Display.getDefault().timerExec(FRAME_RATE, this);
                            return;
                        }
                    }
                }

                // System.out
                // .println(PulsingFadeController.this.hashCode()
                //                                + ":**************************** DONE **********************"); //$NON-NLS-1$

                if (fadeDownAndCancel) {
                    if (fadeDownAndCancelCompleteListener != null) {
                        fadeDownAndCancelCompleteListener
                                .fadeDownAndCancelCompleteEvent();
                    }
                }
                running = false;

                return;
            }

        });

        return;
    }

    private void setFiguresAlpha(float percentOpacity) {
        int alpha = (int) (255 * percentOpacity);

        for (IFadeableFigure fig : figures) {
            // Make sure that the figure has not been removed.
            if (fig.getParent() != null) {
                fig.setAlpha(alpha);
            }
        }

        return;
    }

    /**
     * Cancel the animation at the earliest available opportunity.
     */
    public void cancel() {
        cancelled = true;
    }

    /**
     * Fade the figure down from it's current opacity then cancel.
     */
    public void fadeDownAndCancel(long period) {
        fadeDownAndCancel = true;

        fadeDownAndCancelOpacityInc =
                (currentOpacity - 0) / (period / FRAME_RATE);
        return;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * @return the cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * 
     */
    private void animateFrame() {
        if (fadeDownAndCancel) {
            // System.out
            // .println(PulsingFadeController.this.hashCode()
            //                                        + ":fadeDownAndCancel: " + currentOpacity + "  inc(" + fadeDownAndCancelOpacityInc + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            /* Fade down to cancel mode. */
            /*
             * Fading Down!
             */
            if (fadeDownAndCancelOpacityInc > 0 && currentOpacity >= 0) {

                if (currentOpacity > 0) {
                    /*
                     * Increment opacity if we haven't reached final yet.
                     */
                    currentOpacity -= fadeDownAndCancelOpacityInc;

                    if (currentOpacity < 0) {
                        currentOpacity = 0;
                    }

                    // fadeDownAndCancelOpacityInc *= 1.25f;

                    setFiguresAlpha(currentOpacity);

                } else {
                    /*
                     * If fading down to cancel then cancel now we have hit
                     * minimum.
                     */
                    cancelled = true;
                }
            }

        } else if (fadingUp) {
            // System.out
            // .println(PulsingFadeController.this.hashCode()
            //                                        + ":fadeUp           : " + currentOpacity + "  inc(" + fadeUpOpacityInc + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            /*
             * Fading up!
             */
            if (fadeUpOpacityInc > 0 && currentOpacity <= finalOpacity) {

                setFiguresAlpha(currentOpacity);

                if (currentOpacity < finalOpacity) {
                    /*
                     * Increment opacity if we haven't reached final yet.
                     */
                    currentOpacity += fadeUpOpacityInc;

                    if (currentOpacity > finalOpacity) {
                        currentOpacity = finalOpacity;
                    }

                } else {
                    /* Reach max opacity, start fading down. */
                    fadingUp = false;
                }
            }

        } else {
            // System.out
            // .println(PulsingFadeController.this.hashCode()
            //                                        + ":fadeDown         : " + currentOpacity + "  inc(" + fadeDownOpacityInc + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            /*
             * Fading Down!
             */
            if (fadeDownOpacityInc > 0 && currentOpacity >= initOpacity) {

                setFiguresAlpha(currentOpacity);

                if (currentOpacity > initOpacity) {
                    /*
                     * Increment opacity if we haven't reached final yet.
                     */
                    currentOpacity -= fadeDownOpacityInc;

                    if (currentOpacity < initOpacity) {
                        currentOpacity = initOpacity;
                    }

                } else {
                    /*
                     * Reach min opacity, start fading up
                     */
                    fadingUp = true;

                }
            }
        }
    }

    public interface FadeDownAndCancelCompleteListener {
        /**
         * Notification that the fade up controller has faded up all figures
         * (over time) to the finalOpacity.
         * <p>
         * This is ALWAYS called on the UI thread.
         */
        void fadeDownAndCancelCompleteEvent();
    }
}
