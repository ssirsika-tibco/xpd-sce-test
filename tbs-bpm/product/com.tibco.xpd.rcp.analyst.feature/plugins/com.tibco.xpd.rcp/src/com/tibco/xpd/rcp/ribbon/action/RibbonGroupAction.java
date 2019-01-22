/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonGroup;

/**
 * A group implementation.
 * 
 * @author njpatel
 * 
 */
public class RibbonGroupAction extends AbstractRibbonAction {

    private final RibbonGroup group;

    public RibbonGroupAction(RibbonGroup group, IAction action, String title,
            Image activeImage, Image disabledImage, int imgAlignment,
            int style, boolean showToolTip) {
        super(action, title, activeImage, disabledImage, imgAlignment, style,
                showToolTip);
        this.group = group;
        init();
    }

    @Override
    protected RibbonButton createButton() {
        return new RibbonButton(group, getActiveImage(), getDisabledImage(),
                getTitle(), getImgAlignment(), getStyle());
    }

    @Override
    protected void redraw() {
        if (group != null && !group.isDisposed()) {
            group.redraw();
        }
    }
}
