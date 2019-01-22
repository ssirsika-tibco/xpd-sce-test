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
import org.eclipse.draw2d.geometry.PointList;
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
public class CollapsingTargetRevealAnimator extends
        AbstractFigureRevealAnimator {

    private BrokenCircleFigure brokenCircle;

    private BrokenCircleFigure brokenCircle2;

    private static final int ANIM_FIGURE_MINSIZE = 70;
    private int scaledAnimFigureSize = ANIM_FIGURE_MINSIZE;

    private static final int INITAL_ANIM_SIZE = 200;
    private int scaledInitialAnimSize = INITAL_ANIM_SIZE;
    
    private static final int CIRCLE_SPACING = 12;
    private int scaledCircleSpacing = CIRCLE_SPACING;
    

    private double ACCEL = 0.6;

    private double posInc = 4;

    private int fadeCount = 0;

    private int NUM_FADE_FRAMES = 10;

    public CollapsingTargetRevealAnimator(Viewport viewPort,
            IFigure animationLayer, IFigure revealFigure) {
        super(viewPort, animationLayer, revealFigure);
    }

    @Override
    protected List<IFigure> createAnimationFigures() {
        // setFrameRate(500);

        posInc = getScaledSize(50);
        if (posInc < 1) {
            posInc = 1;
        }
        fadeCount = 0;

        IFigure revealFigure = getRevealFigure();
        Point revealCentre = getRevealCentre();
        
        scaledInitialAnimSize = getScaledSize(INITAL_ANIM_SIZE);
        
        List<IFigure> animFigures = new ArrayList<IFigure>();

        brokenCircle = new BrokenCircleFigure(true);
        brokenCircle.setLocation(revealCentre.getCopy()
                .translate(scaledInitialAnimSize / 2, scaledInitialAnimSize / 2));
        brokenCircle.setSize(scaledInitialAnimSize, scaledInitialAnimSize);
        brokenCircle.setForegroundColor(ColorConstants.darkBlue);
        brokenCircle.setAngle(45);
        animFigures.add(brokenCircle);

        scaledCircleSpacing = getScaledSize(CIRCLE_SPACING);

        int c2size = scaledInitialAnimSize - scaledCircleSpacing;

        brokenCircle2 = new BrokenCircleFigure(false);
        brokenCircle2.setLocation(revealCentre.getCopy().translate(c2size / 2,
                c2size / 2));
        brokenCircle2.setSize(c2size, c2size);
        brokenCircle2.setForegroundColor(ColorConstants.darkBlue);
        brokenCircle2.setAngle(135);
        animFigures.add(brokenCircle2);

        scaledAnimFigureSize = getScaledSize(ANIM_FIGURE_MINSIZE);

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

        Rectangle b = brokenCircle.getBounds();

        if (b.width > scaledAnimFigureSize) {

            b.width -= posInc;
            if (b.width < scaledAnimFigureSize) {
                b.width = scaledAnimFigureSize;
            }
            b.height = b.width;

            posInc *= ACCEL;

            Point revealCentre = getRevealCentre();

            int halfSz = b.width / 2;

            brokenCircle.setBounds(new Rectangle(revealCentre.x - halfSz,
                    revealCentre.y - halfSz, halfSz * 2, halfSz * 2));

            brokenCircle.setAngle((brokenCircle.getAngle() + 10) % 360);

            halfSz -= scaledCircleSpacing / 2;
            brokenCircle2.setBounds(new Rectangle(revealCentre.x - halfSz,
                    revealCentre.y - halfSz, halfSz * 2, halfSz * 2));

            brokenCircle2.setAngle((brokenCircle2.getAngle() - 10) % 360);

        } else if (fadeCount <= NUM_FADE_FRAMES) {
            if (getRevealFigure() instanceof IHighlightableFigure) {
                ((IHighlightableFigure) getRevealFigure()).setHighlight(false);
            }

            fadeCount++;
            int alpha = brokenCircle.getAlpha();

            alpha -= (150 / NUM_FADE_FRAMES);

            brokenCircle.setAngle((brokenCircle.getAngle() + 10) % 360);
            brokenCircle.setAlpha(alpha);
            brokenCircle.repaint();

            brokenCircle2.setAngle((brokenCircle2.getAngle() - 10) % 360);
            brokenCircle2.setAlpha(alpha);
            brokenCircle2.repaint();
        }

        if (fadeCount >= NUM_FADE_FRAMES) {
            endAnimation();
        }

        return;
    }

    @Override
    protected void disposeAnimationFigures() {

        return;
    }

    private class BrokenCircleFigure extends Figure {
        int alpha = 150;

        int angle = 0;

        boolean withCrossline;

        BrokenCircleFigure(boolean withCrossline) {
            this.withCrossline = withCrossline;
        }

        /**
         * @return the angle
         */
        public int getAngle() {
            return angle;
        }

        /**
         * @param angle
         *            the angle to set
         */
        public void setAngle(int angle) {
            this.angle = angle;
        }

        /**
         * @return the alpha
         */
        public int getAlpha() {
            return alpha;
        }

        /**
         * @param alpha
         *            the alpha to set
         */
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        protected void paintFigure(Graphics graphics) {
            graphics.pushState();
            graphics.setAlpha(alpha);
            graphics.setAntialias(SWT.ON);
            graphics.setForegroundColor(getForegroundColor());
            graphics.setBackgroundColor(getBackgroundColor());
            graphics.setLineWidth(2);
            Rectangle b = getBounds().getCopy();
            b.shrink(2, 2);


            if (withCrossline) {
                PointList pts = new PointList();
                Point center = b.getCenter();
                pts.addPoint(center);
                pts.addPoint(b.getTopLeft());

                Point offset =
                        XPDLineUtilities.getLinePointFromOffset(pts,
                                b.width / 2);
                int cx = center.x - offset.x;
                int cy = center.y - offset.y;

                int crossSz = 5;

                graphics.drawLine((center.x - cx) - crossSz, (center.y - cy)
                        - crossSz, (center.x - cx) + crossSz, (center.y - cy)
                        + crossSz);
                graphics.drawLine((center.x + cx) + crossSz, (center.y - cy)
                        - crossSz, (center.x + cx) - crossSz, (center.y - cy)
                        + crossSz);
                graphics.drawLine((center.x + cx) - crossSz, (center.y + cy)
                        - crossSz, (center.x + cx) + crossSz, (center.y + cy)
                        + crossSz);
                graphics.drawLine((center.x - cx) + crossSz, (center.y + cy)
                        - crossSz, (center.x - cx) - crossSz, (center.y + cy)
                        + crossSz);
            }
            graphics.drawArc(b, (angle % 360), 100);
            graphics.drawArc(b, ((angle + 120) % 360), 100);
            graphics.drawArc(b, ((angle + 240) % 360), 100);

            graphics.popState();
        }

    }

}
