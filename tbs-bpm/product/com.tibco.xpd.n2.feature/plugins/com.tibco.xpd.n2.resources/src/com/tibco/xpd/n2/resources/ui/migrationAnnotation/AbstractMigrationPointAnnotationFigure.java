/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import java.util.Collections;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure;
import com.tibco.xpd.processwidget.neatstuff.FigureFadeUpMouseListener;
import com.tibco.xpd.quickfixtooltip.api.StickyTooltipFigure;
import com.tibco.xpd.quickfixtooltip.api.TooltipButtonFigure;

/**
 * Process diagram object annotation figure to indicate when it is a valid
 * migration point.
 * 
 * @author aallway
 * @since 14 Jun 2012
 */
abstract class AbstractMigrationPointAnnotationFigure extends
        AbstractImageAnnotationFigure {

    public static final String MIGRATION_ANNOTATION_VISIBILITY_PREF =
            "MigrationPointAnnotationFigure.visibilityLevel"; //$NON-NLS-1$

    private static int DEFAULT_VISIBILITY = 150;

    private static int MINIMUM_VISIBILITY = 50;

    private static int MAXIMUM_VISIBILITY = 255;

    private static int INCREMENT_VISIBILITY = 25;

    private FigureFadeUpMouseListener figureFadeUpMouseListener;

    /**
     * @param diagramObjectFigure
     * @param img
     */
    AbstractMigrationPointAnnotationFigure(IFigure diagramObjectFigure) {
        super(diagramObjectFigure, BundleActivator
                .getImage(BundleActivator.ICON_MIGRATION_ANNOTATION));

        int normalAlpha = getPreferredVisibility();
        int mouseOverAlpha = MAXIMUM_VISIBILITY;
        super.setAlpha(normalAlpha);

        /*
         * create a figure fade up listener that fades up the annotation figure
         * when the mouse is over it
         */
        figureFadeUpMouseListener =
                new FigureFadeUpMouseListener(150, 100, normalAlpha,
                        mouseOverAlpha, Collections.singletonList(this));

        this.addMouseMotionListener(figureFadeUpMouseListener);

        /*
         * Create tooltip.
         */
        StickyTooltipFigure tooltip =
                new StickyTooltipFigure(new MigrationAnnotationTooltipFigure()) {
                    /**
                     * When mouse leaves migration annotation and enters tooltip
                     * then it would normally fade down, but we don't want that
                     * so force it to be fully visible on enter tooltip then
                     * back to current setting on leave.
                     */
                    @Override
                    public void shown() {
                        super.shown();

                        forceFigureVisibility(MAXIMUM_VISIBILITY);
                    }

                    /**
                     * @see com.tibco.xpd.processwidget.figures.StickyTooltipFigure#aboutToHide()
                     * 
                     */
                    @Override
                    public void hidden() {
                        super.hidden();

                        forceFigureVisibility(getPreferredVisibility());
                    }

                };

        this.setToolTip(tooltip);
    }

    /**
     * Force the figure's visibility regardless of whether it is in faded up or
     * down state (via the associated {@link FigureFadeUpMouseListener}), by
     * setting it's alpha and setting the nominal-alpha on the fade up
     * controller so it will return to that value rather than the preference
     * faded-down level.
     * 
     * @param visibility
     */
    private void forceFigureVisibility(int visibility) {
        figureFadeUpMouseListener.setInitialAlpha(visibility);
        AbstractMigrationPointAnnotationFigure.this.setAlpha(visibility);
    }

    /**
     * @return The preference setting of the migration annotation point figure
     *         (or default if not set).
     */
    private int getPreferredVisibility() {
        int visibility =
                BundleActivator.getDefault().getPreferenceStore()
                        .getInt(MIGRATION_ANNOTATION_VISIBILITY_PREF);

        if (visibility < 1) {
            visibility = DEFAULT_VISIBILITY;
        } else if (visibility > MAXIMUM_VISIBILITY) {
            visibility = MAXIMUM_VISIBILITY;
        }

        return visibility;
    }

    /**
     * Set the preference setting for the visibility level of migration
     * annotations.
     * 
     * @param value
     */
    private void setPreferredVisibility(int visibility) {
        if (visibility < MINIMUM_VISIBILITY) {
            visibility = MINIMUM_VISIBILITY;
        } else if (visibility > MAXIMUM_VISIBILITY) {
            visibility = MAXIMUM_VISIBILITY;
        }

        BundleActivator.getDefault().getPreferenceStore()
                .setValue(MIGRATION_ANNOTATION_VISIBILITY_PREF, visibility);

        figureFadeUpMouseListener.setInitialAlpha(visibility);
        this.setAlpha(visibility);
    }

    /**
     * Reset the migration annotation point figure's visibility from teh current
     * preference setting.
     */
    public void resetVisibilityFromPreference() {
        forceFigureVisibility(getPreferredVisibility());
    }

    /**
     * Migration annotation figure's tooltip.
     * <p>
     * Provides description of migration point and [-] Less Visible, [+] More
     * Visible button controls.
     * 
     * @author aallway
     * @since 12 Jul 2012
     */
    private class MigrationAnnotationTooltipFigure extends Figure {

        /* Margin between buttons. */
        private int marginWidth = 4;

        /* Margin between buttons and text. */
        private int marginHeight = 8;

        private TooltipButtonFigure btnLessVisible;

        private TooltipButtonFigure btnMoreVisible;

        private FlowPage flowPage;

        private Dimension btnSize;

        private TextFlow textFlow;

        private Dimension flowPageSize;

        /**
         * Construct the tooltip.
         */
        MigrationAnnotationTooltipFigure() {
            this.setOpaque(true);
            this.setForegroundColor(ColorConstants.tooltipForeground);
            this.setBackgroundColor(ColorConstants.tooltipBackground);

            /*
             * Create the wrapped text control for message.
             */
            textFlow =
                    new TextFlow(
                            Messages.AbstractMigrationPointAnnotationFigure_MigrationPoint_tooltip);

            BlockFlow blockFlow = new BlockFlow();
            blockFlow.setHorizontalAligment(PositionConstants.CENTER);
            blockFlow.add(textFlow);

            flowPage = new FlowPage();
            flowPage.add(blockFlow);

            this.add(flowPage);

            /*
             * Create the make migration annotation Less visible button.
             */
            btnLessVisible =
                    new TooltipButtonFigure(
                            Messages.AbstractMigrationPointAnnotationFigure_DecreaseVisibility_button,
                            BundleActivator
                                    .getImage(BundleActivator.ICON_MIGRATION_ANNOTATION_FADEDOWN));
            btnLessVisible.setFiringMethod(TooltipButtonFigure.REPEAT_FIRING);
            this.add(btnLessVisible);

            btnLessVisible.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    int preferredVisibility = getPreferredVisibility();

                    setPreferredVisibility(Math.max(MINIMUM_VISIBILITY,
                            preferredVisibility - INCREMENT_VISIBILITY));
                }
            });

            /*
             * Create the make migration annotation More visible button.
             */
            btnMoreVisible =
                    new TooltipButtonFigure(
                            Messages.AbstractMigrationPointAnnotationFigure_IncreaseVisibility_button,
                            BundleActivator
                                    .getImage(BundleActivator.ICON_MIGRATION_ANNOTATION_FADEUP));
            btnMoreVisible.setFiringMethod(TooltipButtonFigure.REPEAT_FIRING);
            this.add(btnMoreVisible);

            btnMoreVisible.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    int preferredVisibility = getPreferredVisibility();

                    setPreferredVisibility(Math.min(MAXIMUM_VISIBILITY,
                            preferredVisibility + INCREMENT_VISIBILITY));
                }
            });
        }

        /**
         * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
         * 
         * @param graphics
         */
        @Override
        protected void paintFigure(Graphics gr) {
            Rectangle fb = flowPage.getBounds();
            Rectangle b = getBounds();

            Color oldCol = gr.getForegroundColor();
            gr.setForegroundColor(ColorConstants.lightGray);

            int oldLineWidth = gr.getLineWidth();
            gr.setLineWidth(1);

            int lineY = fb.y + fb.height + marginHeight;

            gr.drawLine(fb.x, lineY, b.x + b.width, lineY);

            gr.setForegroundColor(oldCol);
            gr.setLineWidth(oldLineWidth);
        }

        /**
         * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
         * 
         * @param wHint
         * @param hHint
         * @return
         */
        @Override
        public Dimension getPreferredSize(int wHint, int hHint) {
            Dimension b1size = btnLessVisible.getPreferredSize();
            Dimension b2size = btnMoreVisible.getPreferredSize();

            btnSize =
                    new Dimension(Math.max(b1size.width, b2size.width),
                            Math.max(b1size.height, b2size.height));

            flowPageSize =
                    flowPage.getPreferredSize((btnSize.width * 2) + marginWidth,
                            -1);

            Dimension finalSize =
                    new Dimension(Math.max(btnSize.width * 2,
                            flowPageSize.width) + marginWidth,
                            flowPageSize.height + btnSize.height + marginHeight);

            return finalSize;
        }

        /**
         * @see org.eclipse.draw2d.Figure#layout()
         * 
         */
        @Override
        protected void layout() {
            Rectangle b = getBounds().getCopy();

            Rectangle textFlowBounds =
                    new Rectangle(0, 0, flowPageSize.width, flowPageSize.height);
            flowPage.setBounds(textFlowBounds);

            Rectangle btnLessBounds =
                    new Rectangle(0, b.height - btnSize.height, btnSize.width,
                            btnSize.height);

            btnLessVisible.setBounds(btnLessBounds);

            Rectangle btnMoreBounds =
                    new Rectangle(btnSize.width + marginWidth, b.height
                            - btnSize.height, btnSize.width, btnSize.height);
            btnMoreVisible.setBounds(btnMoreBounds);
        }

        /**
         * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
         * 
         * @return
         */
        @Override
        protected boolean useLocalCoordinates() {
            return true;
        }

    }

}
