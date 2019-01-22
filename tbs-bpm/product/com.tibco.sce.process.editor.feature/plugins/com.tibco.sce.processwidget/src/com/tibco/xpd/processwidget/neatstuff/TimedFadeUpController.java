/**
 * 
 */
package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collection;

import org.eclipse.swt.widgets.Display;

/**
 * Fade up a given list of figures over a period of time (or until cancelled).
 * 
 * @author aallway
 * @since 3.2
 */
public class TimedFadeUpController {
    private Collection<? extends IFadeableFigure> figures;

    private float initOpacity;

    private float finalOpacity;

    private float currentOpacity;

    private long fadeUpPeriodMillisecs;

    private int FRAME_RATE = 40;

    private boolean running = false;

    private boolean cancelled = false;

    private FadeUpCompleteListener fadeUpCompleteListener;

    private float opacityInc;

    private int frameCount = 0;

    /**
     * 
     * @param figures
     * @param initOpacity
     *            Intial opacity expressed as a value between 0.0 -> 1.0 (fully
     *            transparent -> fully opaque)
     * @param finalOpacity
     *            Final opacity.
     * @param fadeUpPeriodMillisecs
     */
    public TimedFadeUpController(Collection<? extends IFadeableFigure> figures,
            float initOpacity, float finalOpacity, long fadeUpPeriodMillisecs) {
        this(figures, initOpacity, finalOpacity, fadeUpPeriodMillisecs, null);
    }

    /**
     * 
     * @param figures
     * @param initOpacity
     *            Intial opacity expressed as a value between 0.0 -> 1.0 (fully
     *            transparent -> fully opaque)
     * @param finalOpacity
     *            Final opacity.
     * @param fadeUpPeriodMillisecs
     * @param fadeUpCompleteListener
     */
    public TimedFadeUpController(Collection<? extends IFadeableFigure> figures,
            float initOpacity, float finalOpacity, long fadeUpPeriodMillisecs,
            FadeUpCompleteListener fadeUpCompleteListener) {
        super();

        if (figures.isEmpty() || fadeUpPeriodMillisecs < (FRAME_RATE * 2)) {
            throw new IllegalArgumentException("Illegal Arguments"); //$NON-NLS-1$
        }

        this.figures = figures;
        this.finalOpacity = finalOpacity;
        this.initOpacity = initOpacity;
        this.fadeUpPeriodMillisecs = fadeUpPeriodMillisecs;
        this.fadeUpCompleteListener = fadeUpCompleteListener;

        opacityInc =
                (finalOpacity - initOpacity)
                        / (fadeUpPeriodMillisecs / FRAME_RATE);
        // System.out
        //                .println("Opacity Inc: " + opacityInc + "  Init: " + initOpacity + "  final: " + finalOpacity); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        setFiguresAlpha(initOpacity);
    }

    public void start(int initialDelayMillisecs) {
        cancelled = false;
        running = true;
        currentOpacity = initOpacity;
        frameCount = 0;

        Display.getDefault().timerExec(initialDelayMillisecs, new Runnable() {
            @Override
            public void run() {
                frameCount++;
                if (!cancelled && !Display.getDefault().isDisposed()) {

                    if ((opacityInc > 0 && currentOpacity <= finalOpacity)
                            || (opacityInc < 0 && currentOpacity >= finalOpacity)) {

                        setFiguresAlpha(currentOpacity);

                        if (opacityInc > 0) {
                            if (currentOpacity < finalOpacity) {
                                // Increment opacity if we haven't reached final
                                // yet.

                                currentOpacity += opacityInc;

                                if (currentOpacity > finalOpacity) {
                                    currentOpacity = finalOpacity;
                                }

                                //
                                // Repeat until reach final opacity.
                                //
                                if (!Display.getDefault().isDisposed()) {
                                    Display.getDefault().timerExec(FRAME_RATE,
                                            this);
                                    return;
                                }
                            }

                        } else {
                            // decrement opacity if we haven't reached final
                            // yet.
                            if (currentOpacity > finalOpacity) {
                                currentOpacity += opacityInc;

                                if (currentOpacity < finalOpacity) {
                                    currentOpacity = finalOpacity;
                                }

                                //
                                // Repeat until reach final opacity.
                                //
                                if (!Display.getDefault().isDisposed()) {
                                    Display.getDefault().timerExec(FRAME_RATE,
                                            this);
                                    return;
                                }
                            }
                        }
                    }
                }

                if (fadeUpCompleteListener != null) {
                    fadeUpCompleteListener.fadeUpControllerComplete();
                }

                running = false;
                // System.out
                //                        .println("Completed Anim in " + frameCount + " frames"); //$NON-NLS-1$//$NON-NLS-2$

                return;
            }

        });

    }

    private void setFiguresAlpha(float percentOpacity) {
        int alpha = (int) (255 * percentOpacity);

        for (IFadeableFigure fig : figures) {
            // Make sure that the figure has not been removed.
            if (fig.getParent() != null) {
                fig.setAlpha(alpha);

            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public boolean isRunning() {
        return running;
    }

    public interface FadeUpCompleteListener {
        /**
         * Notification that the fade up controller has faded up all figures
         * (over time) to the finalOpacity.
         * <p>
         * This is ALWAYS called on the UI thread.
         */
        void fadeUpControllerComplete();
    }
}
