/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.neatstuff.AbstractFigureRevealAnimator;

/**
 * SpirallingFigureRevealAnimator
 * 
 * @author aallway
 */
public class SpirallingFigureRevealAnimator extends
        AbstractFigureRevealAnimator {

    private int NUM_SPIRAL_ARMS = 4;

    private List<List<CircleTrailerFigure>> spiralArms;

    private Rectangle animBounds;

    private int NUM_ANIM_FIGURES = 8;

    private int ANIM_FIGURE_SIZE = 15;

    private boolean initLaunchDone = false;

    // At FRAME_RATE frames per sec, how many frames to complete animation?
    // This effectively sets the rate at which the raius of the spiral decreases
    // (animation stops when all figures radius hits zero).
    private int NUM_FRAMES = 15;

    private static final int ANIM_START_SIZE = 300;

    private double ANGLE_INCREMENTS = 24;

    public SpirallingFigureRevealAnimator(Viewport viewPort,
            IFigure animationLayer, IFigure revealFigure) {
        super(viewPort, animationLayer, revealFigure);
    }

    @Override
    protected List<IFigure> createAnimationFigures() {
        // setFrameRate(250);

        //
        // Calc0luate the outer bounds of our animation.
        //
        IFigure revealFigure = getRevealFigure();
        Point selectionCentre = getRevealCentre();

        Dimension animSize = new Dimension(ANIM_START_SIZE, ANIM_START_SIZE);
        revealFigure.translateToAbsolute(animSize);

        getAnimationLayer().translateToRelative(animSize);

        animBounds =
                new Rectangle(selectionCentre.x - (animSize.width / 2),
                        selectionCentre.y - (animSize.height / 2),
                        animSize.width, animSize.height);

        spiralArms = new ArrayList<List<CircleTrailerFigure>>();

        double startPosAngle = 0;
        for (int i = 0; i < NUM_SPIRAL_ARMS; i++) {
            spiralArms.add(createSpiralArm(startPosAngle));

            startPosAngle += (360 / NUM_SPIRAL_ARMS);
        }

        
        List<IFigure> animFigures = new ArrayList<IFigure>();

        for (List<CircleTrailerFigure> arm : spiralArms) {
            animFigures.addAll(arm);
        }

        if (revealFigure instanceof IHighlightableFigure) {
            ((IHighlightableFigure) revealFigure).setHighlight(true);
        }

        return animFigures;
    }

    @Override
    protected void animateFigures(List<IFigure> animFigures, int frameCount) {
        if ((frameCount * getFrameRate()) > 5000) {
            // Just in case something hideous happens.
            endAnimation();
            return;
        }

        boolean doneAtLeastOne = false;

        for (List<CircleTrailerFigure> spiralArm : spiralArms) {
            if (animateSpiralArm(spiralArm)) {
                doneAtLeastOne = true;
            }
        }

        if (!doneAtLeastOne) {
            // Everything has reached the middle, end the animation.
            // Cancel and clean up on next timer trigger.
            endAnimation();
        }

        return;
    }

    @Override
    protected void disposeAnimationFigures() {
        spiralArms.clear();

        if (getRevealFigure() instanceof IHighlightableFigure) {
            ((IHighlightableFigure) getRevealFigure()).setHighlight(false);
        }

        return;
    }

    /**
     * Create the figure list for one spiral arm starting at the given position
     * on edge of outer circle.
     * 
     * @param startPoint360
     * @return
     */
    private List<CircleTrailerFigure> createSpiralArm(double startPoint360) {
        List<CircleTrailerFigure> armFigures =
                new ArrayList<CircleTrailerFigure>();

        for (int i = 0; i < NUM_ANIM_FIGURES; i++) {

            CircleTrailerFigure fig = new CircleTrailerFigure();

            // Fade down each figure a little more than previous
            fig.setAlpha(150 - (i * (130 / NUM_ANIM_FIGURES)));
            fig.setSize(ANIM_FIGURE_SIZE - i, ANIM_FIGURE_SIZE - i);
            fig.setForegroundColor(ColorConstants.darkBlue);

            fig.setAngle(startPoint360);
            fig.setAngleIncrement(ANGLE_INCREMENTS);
            fig.setRadius(animBounds.height / 2);

            fig.setRadiusDecrement((ANIM_START_SIZE / 2) / NUM_FRAMES);

            Point p =
                    XPDLineUtilities.getPointOnLineFromAngle(animBounds
                            .getCenter(), animBounds.height / 2, startPoint360);

            p.x -= ANIM_FIGURE_SIZE / 2;
            p.y -= ANIM_FIGURE_SIZE / 2;
            fig.setLocation(p);
            fig.setVisible(false);

            armFigures.add(fig);

        }

        return armFigures;
    }

    /**
     * @param spiralArm
     * @return
     */
    private boolean animateSpiralArm(List<CircleTrailerFigure> spiralArm) {
        if (spiralArm.size() < 1) {
            return false;
        }

        int lastFigToMove = 0;

        // Do initial launch (only move those that haven't moved yet...
        if (!initLaunchDone) {
            for (lastFigToMove = 0; lastFigToMove < (spiralArm.size() - 1); lastFigToMove++) {
                CircleTrailerFigure fig = spiralArm.get(lastFigToMove);
                CircleTrailerFigure figNext = spiralArm.get(lastFigToMove + 1);

                if (fig.getLocation().equals(figNext.getLocation())) {
                    break;
                }
            }

            if (lastFigToMove >= (spiralArm.size() - 1)) {
                initLaunchDone = true;
            }
        } else {
            lastFigToMove = spiralArm.size() - 1;
        }

        //
        // Go thru the figures to move moving them.
        //
        boolean doneOne = false;
        for (int i = 0; i <= lastFigToMove; i++) {
            CircleTrailerFigure fig = spiralArm.get(i);

            // Change the angle (i.e. the distance around circle of current
            // radius.
            double angle = fig.getAngle();
            double angleIncrement = fig.getAngleIncrement();

            angle += angleIncrement;
            angle = (angle % 360);

            // Gradually slow up the angle increment as we reach our target.
            // this 'flattens' the spiral and forces the figures closer
            // together.
            angleIncrement /= 1.1;
            if (angleIncrement < 2) {
                angleIncrement = 5;
            }

            // And store for next time.
            fig.setAngleIncrement(angleIncrement);
            fig.setAngle(angle);

            //
            // Decreas the radius (thus creating a 'spiral'.
            //
            int radiusDecrement = fig.getRadiusDecrement();
            Dimension adjustedRadiusDec =
                    new Dimension(radiusDecrement, radiusDecrement);

            // Translate radius decrement to allow for zoom etc.
            getRevealFigure().translateToAbsolute(adjustedRadiusDec);
            getAnimationLayer().translateToRelative(adjustedRadiusDec);

            int radius = fig.getRadius();

            if (radius != 0) {
                radius -= adjustedRadiusDec.width;

                if ((radius - adjustedRadiusDec.width) < 0) {
                    radius = 0;
                }

//                if (radius == 0) {
//                    radius = 0;
//                    fig.setBackgroundColor(ColorConstants.cyan);
//                    fig.setAlpha(255);
//                }

                fig.setRadius(radius);

                Point p;

                if (radius != 0) {
                    p =
                            XPDLineUtilities.getPointOnLineFromAngle(animBounds
                                    .getCenter(), fig.getRadius(), fig
                                    .getAngle());
                } else {
                    p = animBounds.getCenter();
                }

                p.x -= ANIM_FIGURE_SIZE / 2;
                p.y -= ANIM_FIGURE_SIZE / 2;

                fig.setLocation(p);

                if (!fig.isVisible()) {
                    fig.setVisible(true);
                }

                doneOne = true;
            } else {
                fig.setVisible(false);

            }
        }
        return doneOne;
    }

    private class CircleTrailerFigure extends Figure {

        private int alpha = 255;

        //
        // All of the following angle/radius are just per-figure storage for
        // convenience of animator.
        //
        private double angle = 0;

        private double angleIncrement = 0;

        private int radius = 0;

        private int radiusDecrement = 0;

        CircleTrailerFigure() {
        }

        /**
         * @param alpha
         *            the alpha to set
         */
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        /**
         * @param angle
         *            the angle to set
         */
        public void setAngle(double angle) {
            this.angle = angle;
        }

        /**
         * @return the angle
         */
        public double getAngle() {
            return angle;
        }

        /**
         * @return the angleIncrement
         */
        public double getAngleIncrement() {
            return angleIncrement;
        }

        /**
         * @param angleIncrement
         *            the angleIncrement to set
         */
        public void setAngleIncrement(double angleIncrement) {
            this.angleIncrement = angleIncrement;
        }

        /**
         * @return the radiusDecrement
         */
        public int getRadiusDecrement() {
            return radiusDecrement;
        }

        /**
         * @param radiusDecrement
         *            the radiusDecrement to set
         */
        public void setRadiusDecrement(int radiusDecrement) {
            this.radiusDecrement = radiusDecrement;
        }

        /**
         * @param radius
         *            the radius to set
         */
        public void setRadius(int radius) {
            this.radius = radius;
        }

        /**
         * @return the radius
         */
        public int getRadius() {
            return radius;
        }

        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.pushState();
            graphics.setAlpha(alpha);
            graphics.setAntialias(SWT.ON);
            graphics.setForegroundColor(getForegroundColor());
            graphics.setBackgroundColor(getBackgroundColor());

            graphics.setLineWidth(1);

            Rectangle b = getBounds().getCopy();
            b.shrink(2, 2);
            // graphics.fillOval(b);
            graphics.drawOval(b);

            graphics.popState();
        }
    }

}
