/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.neatstuff;

import java.util.Collection;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener.Stub;
import org.eclipse.swt.widgets.Display;

/**
 * FigureFadeUpMouseListener
 * <p>
 * Fades up the given list of figures when the mouse enters the host figure.
 * <p>
 * Add this to as a motion listener to all the figures you want to fade up and
 * down together. Or add to some separate figure.
 * 
 * @author aallway
 */
public class FigureFadeUpMouseListener extends Stub {

    private int initialAlpha;

    private int maxAlpha;

    private int fadeUpTime;

    private int fadeDownTime;

    private Collection<? extends IFadeableFigure> figures;

    private boolean in = false;

    private int FRAME_RATE = 40;

    public FigureFadeUpMouseListener(int fadeUpTime, int fadeDownTime,
            int initialAlpha, int maxAlpha,
            Collection<? extends IFadeableFigure> figuresToFadeUp) {
        super();
        this.fadeUpTime = fadeUpTime;
        this.fadeDownTime = fadeDownTime;
        this.initialAlpha = initialAlpha;
        this.maxAlpha = maxAlpha;
        this.figures = figuresToFadeUp;

    }

    /**
     * @return the initialAlpha
     */
    public int getInitialAlpha() {
        return initialAlpha;
    }

    /**
     * @param initialAlpha
     *            the initialAlpha to set
     */
    public void setInitialAlpha(int initialAlpha) {
        this.initialAlpha = initialAlpha;
    }

    /**
     * @return the maxAlpha
     */
    public int getMaxAlpha() {
        return maxAlpha;
    }

    /**
     * @param maxAlpha
     *            the maxAlpha to set
     */
    public void setMaxAlpha(int maxAlpha) {
        this.maxAlpha = maxAlpha;
    }

    /**
     * @return the fadeUpTime
     */
    public int getFadeUpTime() {
        return fadeUpTime;
    }

    /**
     * @param fadeUpTime
     *            the fadeUpTime to set
     */
    public void setFadeUpTime(int fadeUpTime) {
        this.fadeUpTime = fadeUpTime;
    }

    /**
     * @return the fadeDownTime
     */
    public int getFadeDownTime() {
        return fadeDownTime;
    }

    /**
     * @param fadeDownTime
     *            the fadeDownTime to set
     */
    public void setFadeDownTime(int fadeDownTime) {
        this.fadeDownTime = fadeDownTime;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        in = true;

        final Display d = Display.getCurrent();

        if (d != null && !d.isDisposed()) {
            d.timerExec(FRAME_RATE, new Runnable() {

                @Override
                public void run() {
                    if (in == true) {

                        boolean done = true;

                        for (IFadeableFigure fig : figures) {

                            int alpha = fig.getAlpha();
                            int origAlpha = alpha;

                            if (alpha < maxAlpha) {
                                int numFrames = fadeUpTime / FRAME_RATE;

                                if (numFrames < 1) {
                                    numFrames = 1;
                                }

                                int inc = (maxAlpha - initialAlpha) / numFrames;

                                if (inc < 1) {
                                    inc = 1;
                                }

                                alpha += inc;
                                if (alpha > maxAlpha) {
                                    alpha = maxAlpha;
                                }

                                fig.setAlpha(alpha);

                                if (origAlpha == 0) {
                                    fig.setVisible(true);
                                } else {
                                    fig.repaint();
                                }

                                if (alpha < maxAlpha
                                        && fig.getAlpha() != origAlpha) {
                                    done = false;
                                }
                            }
                        }

                        if (!done) {
                            d.timerExec(FRAME_RATE, this);
                        }
                    }
                }
            });
        }

    }

    @Override
    public void mouseExited(MouseEvent me) {
        in = false;

        final Display d = Display.getCurrent();
        if (d != null && !d.isDisposed()) {
            d.timerExec(FRAME_RATE, new Runnable() {

                @Override
                public void run() {
                    if (in == false) {

                        boolean done = true;

                        for (IFadeableFigure fig : figures) {
                            int alpha = fig.getAlpha();
                            int origAlpha = alpha;

                            if (alpha > initialAlpha) {
                                int numFrames = fadeDownTime / FRAME_RATE;

                                if (numFrames < 1) {
                                    numFrames = 1;
                                }

                                int inc = (maxAlpha - initialAlpha) / numFrames;

                                if (inc < 1) {
                                    inc = 1;
                                }

                                alpha -= inc;

                                if (alpha < initialAlpha) {
                                    alpha = initialAlpha;
                                }

                                fig.setAlpha(alpha);

                                if (fig.getAlpha() == 0) {
                                    fig.setVisible(false);
                                } else {
                                    fig.repaint();
                                }

                                if (alpha > initialAlpha
                                        && fig.getAlpha() != origAlpha) {
                                    done = false;
                                }
                            }
                        }

                        if (!done) {
                            d.timerExec(FRAME_RATE, this);
                        }
                    }
                }
            });
        }

    }

    public void removeListenerAndFadeDown(
            final Collection<IFadeableFigure> fadeDownFigures) {
        if (fadeDownFigures.isEmpty()) {
            return;
        }

        for (IFadeableFigure fig : fadeDownFigures) {
            fig.removeMouseMotionListener(this);
        }

        final int startAlpha = fadeDownFigures.iterator().next().getAlpha();

        final Display d = Display.getCurrent();
        if (d != null && !d.isDisposed()) {
            d.timerExec(FRAME_RATE, new Runnable() {

                @Override
                public void run() {
                    boolean done = true;

                    for (IFadeableFigure fadeDownFigure : fadeDownFigures) {
                        int alpha = fadeDownFigure.getAlpha();
                        int origAlpha = alpha;

                        if (alpha > initialAlpha) {
                            int numFrames = fadeDownTime / FRAME_RATE;

                            if (numFrames < 1) {
                                numFrames = 1;
                            }

                            int inc = (startAlpha - initialAlpha) / numFrames;

                            if (inc < 1) {
                                inc = 1;
                            }

                            alpha -= inc;
                            if (alpha < initialAlpha) {
                                alpha = initialAlpha;
                            }

                            fadeDownFigure.setAlpha(alpha);

                            if (fadeDownFigure.getAlpha() == 0) {
                                fadeDownFigure.setVisible(false);
                            } else {
                                fadeDownFigure.repaint();
                            }

                            if (alpha > initialAlpha
                                    && fadeDownFigure.getAlpha() != origAlpha) {
                                done = false;
                            }

                        }
                    }

                    if (!done) {
                        d.timerExec(FRAME_RATE, this);
                    }
                }
            });
        }

        return;
    }

}
