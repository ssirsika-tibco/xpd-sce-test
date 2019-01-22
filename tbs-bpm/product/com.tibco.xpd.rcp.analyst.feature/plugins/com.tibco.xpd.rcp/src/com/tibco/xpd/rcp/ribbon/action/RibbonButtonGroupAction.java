/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;

/**
 * A ribbon button group implementation.
 * 
 * @author njpatel
 * 
 */
public class RibbonButtonGroupAction extends AbstractRibbonAction {

    private final RibbonButtonGroup group;

    public RibbonButtonGroupAction(RibbonButtonGroup buttonGroup,
            IAction action, String title, Image activeImage,
            Image disabledImage, int style, boolean showToolTip) {
        super(action, title, activeImage, disabledImage, 0, style, showToolTip);
        this.group = buttonGroup;
        init();
    }

    @Override
    protected AbstractRibbonGroupItem createButton() {
        return new RibbonButton(group, getActiveImage(), getDisabledImage(),
                getTitle(), getStyle());
    }

    @Override
    protected void redraw() {
        if (group != null && group.getParent() != null
                && !group.getParent().isDisposed()) {
            group.redraw();
        }
    }

}
