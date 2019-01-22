/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon.action;

import org.eclipse.jface.action.IAction;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonCheckbox;

/**
 * A ribbon checkbox group action implementation.
 * 
 * @author mtorres
 * 
 */
public class RibbonCheckboxGroupAction extends AbstractRibbonAction {

    private final RibbonButtonGroup group;

    public RibbonCheckboxGroupAction(RibbonButtonGroup buttonGroup,
            IAction action, String title, int style, boolean showToolTip) {
        super(action, title, null, null, 0, style, showToolTip);
        this.group = buttonGroup;
        init();
    }

    @Override
    protected AbstractRibbonGroupItem createButton() {
        return new RibbonCheckbox(group, getTitle(), getStyle());
    }

    @Override
    protected void redraw() {
        if (group != null && group.getParent() != null
                && !group.getParent().isDisposed()) {
            group.redraw();
        }
    }

}
