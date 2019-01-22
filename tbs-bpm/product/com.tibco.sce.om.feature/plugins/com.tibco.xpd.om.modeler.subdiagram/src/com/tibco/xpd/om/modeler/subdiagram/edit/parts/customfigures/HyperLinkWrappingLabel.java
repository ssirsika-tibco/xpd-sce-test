/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

/**
 * 
 * 
 * @author njpatel
 * @since 14 Oct 2013
 */
public class HyperLinkWrappingLabel extends WrappingLabel {

    private Color hyperLinkColor;

    private static final Color NORMAL_COLOR = ColorConstants.gray;

    private HyperlinkMouseListener mouseListener;

    /**
     * Indicates whether the user set this control in hyperlink mode.
     */
    private boolean isHyperLink;

    public interface IHyperLinkListener {
        void hyperLinkActivated();
    }

    /**
     * 
     */
    public HyperLinkWrappingLabel() {
        super();

        hyperLinkColor =
                JFaceResources.getColorRegistry()
                        .get(JFacePreferences.HYPERLINK_COLOR);

        if (hyperLinkColor == null) {
            hyperLinkColor = ColorConstants.blue;
        }

    }

    public void setHyperLinkListener(IHyperLinkListener listener) {
        this.mouseListener = new HyperlinkMouseListener(listener);
    }

    /**
     * @see org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel#setText(java.lang.String)
     * 
     * @param text
     */
    @Override
    public void setText(String text) {
        isHyperLink = false;
        super.setText(text);
        setCursor(null);
    }

    /**
     * Set the text and switch hyperlink mode on. Otherwise, use
     * {@link #setText(String)} to set normal text without hyperlink.
     * 
     * @param text
     */
    public void setHyperLinkText(String text) {
        if (mouseListener != null) {
            isHyperLink = true;
            setCursor(Cursors.HAND);
            addMouseListener(mouseListener);
            setToolTip(new Label(
                    "Click to open the Dynamic Organization editor"));
            super.setText(text);
        } else {
            setText(text);
        }
    }

    /**
     * @see org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel#paintClientArea(org.eclipse.draw2d.Graphics)
     * 
     * @param graphics
     */
    @Override
    protected void paintClientArea(Graphics graphics) {
        updateHyperLinkPaintMode(graphics);
        super.paintClientArea(graphics);
    }

    /**
     * Toggle the hyperlink mode.
     * 
     * @param graphics
     */
    private void updateHyperLinkPaintMode(Graphics graphics) {
        graphics.setForegroundColor(isHyperLink ? hyperLinkColor : NORMAL_COLOR);
    }

    private static class HyperlinkMouseListener implements MouseListener {
        private final IHyperLinkListener listener;

        /**
         * 
         */
        public HyperlinkMouseListener(IHyperLinkListener listener) {
            this.listener = listener;
        }

        /**
         * @see org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseDoubleClicked(org.eclipse.draw2d.MouseEvent me) {
        }

        /**
         * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mousePressed(org.eclipse.draw2d.MouseEvent me) {
            listener.hyperLinkActivated();
            me.consume();
        }

        /**
         * @see org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseReleased(org.eclipse.draw2d.MouseEvent me) {
        }
    }
}
