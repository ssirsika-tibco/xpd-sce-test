/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

/**
 * @author nwilson
 */
public class ToolTipFigure extends Figure {

    /** Border spacing. */
    private static final int SPACING = 2;

    /** The tooltip text. */
    private String text;

    /** The tooltip font. */
    private Font font;

    /**
     * @param text
     *            The tool tip text.
     */
    public ToolTipFigure(String text) {
        this.text = text;
        setLayoutManager(new ToolTipLayoutManager());
        setBackgroundColor(ColorConstants.tooltipBackground);
        setForegroundColor(ColorConstants.tooltipForeground);
        IThemeManager manager = PlatformUI.getWorkbench().getThemeManager();
        ITheme theme = manager.getTheme(IThemeManager.DEFAULT_THEME);
        FontRegistry registry = theme.getFontRegistry();
        font = registry.get("Text"); //$NON-NLS-1$
    }

    /**
     * Paint the round rectangle background.
     * 
     * @param gr
     *            The graphics context.
     */
    public void paintFigure(Graphics gr) {
        gr.pushState();
        Rectangle bnds = getClientArea().getCopy();
        bnds.width--;
        bnds.height--;
        gr.fillRectangle(bnds);

        if (text != null) {
            gr.setFont(font);
            gr.drawText(text, bnds.x + SPACING, bnds.y + SPACING);
        }
        gr.popState();
    }

    /**
     * @author nwilson
     */
    class ToolTipLayoutManager implements LayoutManager {

        /**
         * @param child
         *            The child figure to check.
         * @return null.
         * @see org.eclipse.draw2d.LayoutManager#getConstraint(
         *      org.eclipse.draw2d.IFigure)
         */
        public Object getConstraint(IFigure child) {
            return null;
        }

        /**
         * @param container
         *            The containing figure.
         * @param wHint
         *            The width hint.
         * @param hHint
         *            The height hint.
         * @return The minimum figure size.
         * @see org.eclipse.draw2d.LayoutManager#getMinimumSize(
         *      org.eclipse.draw2d.IFigure, int, int)
         */
        public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
            return getPreferredSize(container, wHint, hHint);
        }

        /**
         * @param container
         *            The containing figure.
         * @param wHint
         *            The width hint.
         * @param hHint
         *            The height hint.
         * @return The preferred figure size.
         * @see org.eclipse.draw2d.LayoutManager#getPreferredSize(
         *      org.eclipse.draw2d.IFigure, int, int)
         */
        public Dimension getPreferredSize(IFigure container, int wHint,
                int hHint) {
            int width = wHint;
            int height = 0;
            if (text != null) {
                Dimension dim = FigureUtilities.getTextExtents(text, font);
                height = dim.height + SPACING * 2;
                width = Math.max(width, dim.width + SPACING * 2);
            }
            return new Dimension(width, height);
        }

        /**
         * @param container
         *            The containing figure.
         * @see org.eclipse.draw2d.LayoutManager#layout(
         *      org.eclipse.draw2d.IFigure)
         */
        public void layout(IFigure container) {
        }

        /**
         * Invalidate the figure layout.
         * 
         * @see org.eclipse.draw2d.LayoutManager#invalidate()
         */
        public void invalidate() {
        }

        /**
         * @param child
         *            The figure to remove.
         * @see org.eclipse.draw2d.LayoutManager#remove(
         *      org.eclipse.draw2d.IFigure)
         */
        public void remove(IFigure child) {
        }

        /**
         * @param child
         *            The child to set the constraint on.
         * @param constraint
         *            The constraint.
         * @see org.eclipse.draw2d.LayoutManager#setConstraint(
         *      org.eclipse.draw2d.IFigure, java.lang.Object)
         */
        public void setConstraint(IFigure child, Object constraint) {
        }
    }
}
