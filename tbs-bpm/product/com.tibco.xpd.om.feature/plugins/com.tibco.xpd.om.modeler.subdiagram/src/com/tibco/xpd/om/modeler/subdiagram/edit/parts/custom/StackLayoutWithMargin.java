/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Extension to the {@link StackLayout} - this version adds a margin to the
 * layout (similar to {@link GridLayout}).
 * 
 * @author njpatel
 */
public class StackLayoutWithMargin extends StackLayout {

    private final int marginWidth;

    private final int marginHeight;

    /**
     * StackLayout with a default margin of 5 pixels all around.
     */
    public StackLayoutWithMargin() {
        this(5, 5);
    }

    /**
     * StackLayout with a margin as specified
     * 
     * @param marginWidth
     *            left and right margin, in pixels
     * @param marginHeight
     *            top and bottom margin, in pixels
     */
    public StackLayoutWithMargin(int marginWidth, int marginHeight) {
        Assert.isLegal(marginWidth >= 0);
        Assert.isLegal(marginHeight >= 0);

        this.marginWidth = marginWidth;
        this.marginHeight = marginHeight;
    }

    /**
     * @see org.eclipse.draw2d.StackLayout#calculateMinimumSize(org.eclipse.draw2d.IFigure,
     *      int, int)
     * 
     * @param figure
     * @param wHint
     * @param hHint
     * @return
     */
    @Override
    protected Dimension calculateMinimumSize(IFigure figure, int wHint,
            int hHint) {
        return addMargin(super.calculateMinimumSize(figure, wHint, hHint));
    }

    /**
     * @see org.eclipse.draw2d.StackLayout#calculatePreferredSize(org.eclipse.draw2d.IFigure,
     *      int, int)
     * 
     * @param figure
     * @param wHint
     * @param hHint
     * @return
     */
    @Override
    protected Dimension calculatePreferredSize(IFigure figure, int wHint,
            int hHint) {
        return addMargin(super.calculatePreferredSize(figure, wHint, hHint));
    }

    /**
     * Add margin to the given dimension.
     * 
     * @param d
     * @return
     */
    private Dimension addMargin(Dimension d) {
        return new Dimension(d.width + (marginWidth * 2), d.height
                + (marginHeight * 2));
    }

    /**
     * @see org.eclipse.draw2d.StackLayout#layout(org.eclipse.draw2d.IFigure)
     * 
     * @param figure
     */
    @Override
    public void layout(IFigure figure) {
        /*
         * Copied from the StackLayout superclass and adjusted for the margin.
         */
        Rectangle r = figure.getClientArea();
        r.shrink(new Insets(marginHeight, marginWidth, marginHeight,
                marginWidth));

        List<?> children = figure.getChildren();
        IFigure child;
        for (int i = 0; i < children.size(); i++) {
            child = (IFigure) children.get(i);
            child.setBounds(r);
        }
    }
}