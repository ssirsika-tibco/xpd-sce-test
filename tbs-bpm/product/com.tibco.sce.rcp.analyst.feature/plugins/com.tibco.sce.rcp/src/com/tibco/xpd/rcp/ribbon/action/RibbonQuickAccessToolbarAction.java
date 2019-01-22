/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonCanvas;

/**
 * Quick-access toolbar action implementation.
 * 
 * @author njpatel
 * 
 */
public class RibbonQuickAccessToolbarAction extends AbstractRibbonAction {

    private final QuickAccessShellToolbar toolbar;

    public RibbonQuickAccessToolbarAction(QuickAccessShellToolbar toolbar,
            IAction action, String title, Image activeImage,
            Image disabledImage, int style, boolean showToolTip) {
        super(action, title, activeImage, disabledImage, 0, style, showToolTip);
        this.toolbar = toolbar;
        init();
    }

    @Override
    protected RibbonButton createButton() {
        return new RibbonButton(toolbar, getActiveImage(), getDisabledImage(),
                getStyle());
    }

    @Override
    protected void redraw() {
        Composite parent = toolbar.getParent();
        if (!parent.isDisposed()) {
            getButton().setHoverButton(false);
            if (parent instanceof RibbonCanvas) {
                ((RibbonCanvas) parent).redrawToolbar();
            }
        }
    }
}
