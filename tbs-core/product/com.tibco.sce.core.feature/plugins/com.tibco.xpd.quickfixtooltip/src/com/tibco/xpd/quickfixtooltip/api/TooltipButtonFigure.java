/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * Standard tooltip button figure - can be added to sticky tooltips for actions
 * to be performed.
 * <p>
 * Use {@link #addActionListener(org.eclipse.draw2d.ActionListener)} for
 * notifications.
 * 
 * 
 * @author aallway
 * @since 3.5.10
 */
public class TooltipButtonFigure extends Clickable {

    private Label labelFigure;

    private int marginWidth = 2;

    private int marginHeight = 2;

    private Dimension preferredSize;

    private boolean mouseOver = false;

    private boolean mouseDown = false;

    /**
     * @param image
     * @param text
     */
    public TooltipButtonFigure(String text, Image image) {
        super();
        labelFigure = new Label(text, image);
        labelFigure.setIconAlignment(PositionConstants.LEFT);
        this.add(labelFigure);
    }

    /**
     * @return the labelFigure
     */
    public Label getLabelFigure() {
        return labelFigure;
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     * 
     * @param gr
     */
    @Override
    protected void paintFigure(Graphics gr) {
        try {
            gr.pushState();

            if (mouseOver || mouseDown) {
                /*
                 * Paint border
                 */
                gr.setLineWidth(1);
                gr.setAntialias(SWT.ON);

                Rectangle b = getBounds().getCopy();
                b.x++;
                b.y++;
                b.width -= 2;
                b.height -= 2;

                gr.setForegroundColor(ColorConstants.gray);
                gr.setAlpha(200);
                gr.setLineWidth(2);
                gr.drawRoundRectangle(b, 4, 4);

                Rectangle innerB = b.getCopy();
                innerB.width -= 1;
                innerB.height -= 1;

                gr.setAlpha(200);
                gr.setLineWidth(1);

                gr.setForegroundColor(ColorConstants.white);
                gr.drawRoundRectangle(innerB, 4, 4);

                /*
                 * Paint 3-d rounding on lower half.
                 */
                int startY = b.y + (int) (b.height * 0.6);
                int endY = (b.y + b.height) - 1;

                gr.setForegroundColor(ColorConstants.white);
                gr.setAlpha(175);
                gr.drawLine(innerB.x + 1, startY - 2, innerB.x + innerB.width
                        - 1, startY - 2);
                gr.setAlpha(255);
                gr.drawLine(innerB.x + 1, startY - 1, innerB.x + innerB.width
                        - 1, startY - 1);

                int initAlpha = 120;
                int alphaDecrement = initAlpha / (endY - startY);

                gr.setForegroundColor(ColorConstants.lightGray);

                for (; startY < endY; startY++, initAlpha -= alphaDecrement) {
                    gr.setAlpha(initAlpha);
                    gr.drawLine(b.x, startY, b.x + b.width, startY);
                }

            }

        } finally {
            gr.popState();
        }
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
        preferredSize = labelFigure.getPreferredSize().getCopy();
        preferredSize.width += 4;

        /* Room to shift it on click. */
        return new Dimension(preferredSize.width + (marginWidth * 2) + 2,
                preferredSize.height + (marginHeight * 2));
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

    /**
     * @see org.eclipse.draw2d.Figure#layout()
     * 
     */
    @Override
    protected void layout() {

        int offset = 0;
        if (mouseDown) {
            offset += 1;
        }

        labelFigure.setBounds(new Rectangle(marginWidth + offset, marginHeight,
                preferredSize.width, preferredSize.height));
    }

    /**
     * @param mouseDown
     *            the mouseDown to set
     */
    private void setButtonDown(boolean mouseDown) {
        if (mouseDown != this.mouseDown) {
            this.mouseDown = mouseDown;
            layout();
            repaint();
        }
    }

    /**
     * @param mouseOver
     *            the mouseDown to set
     */
    private void setMouseOver(boolean mouseOver) {
        if (mouseOver != this.mouseOver) {
            this.mouseOver = mouseOver;
            layout();
            repaint();
        }
    }

    /**
     * @see org.eclipse.draw2d.Figure#handleMouseEntered(org.eclipse.draw2d.MouseEvent)
     * 
     * @param event
     */
    @Override
    public void handleMouseEntered(MouseEvent event) {
        setMouseOver(true);
        super.handleMouseEntered(event);
    }

    /**
     * @see org.eclipse.draw2d.Figure#handleMouseExited(org.eclipse.draw2d.MouseEvent)
     * 
     * @param event
     */
    @Override
    public void handleMouseExited(MouseEvent event) {
        super.handleMouseExited(event);
        setMouseOver(false);
        setButtonDown(false);
    }

    /**
     * @see org.eclipse.draw2d.Figure#handleMousePressed(org.eclipse.draw2d.MouseEvent)
     * 
     * @param event
     */
    @Override
    public void handleMousePressed(MouseEvent event) {
        setButtonDown(true);
        super.handleMousePressed(event);
    }

    /**
     * @see org.eclipse.draw2d.Figure#handleMouseReleased(org.eclipse.draw2d.MouseEvent)
     * 
     * @param event
     */
    @Override
    public void handleMouseReleased(MouseEvent event) {
        super.handleMouseReleased(event);

        setButtonDown(false);
    }

}