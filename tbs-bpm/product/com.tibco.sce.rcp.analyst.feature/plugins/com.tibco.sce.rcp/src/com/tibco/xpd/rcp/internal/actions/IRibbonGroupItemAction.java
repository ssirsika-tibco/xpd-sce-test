/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.action.IAction;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;

/**
 * To be implemented by the {@link IAction} that will be set for a Ribbon
 * Button. This will allow the action to get hold of the button that will invoke
 * it.
 * 
 * @author njpatel
 */
public interface IRibbonGroupItemAction {

    /**
     * Set the Ribbon group item that will invoke this action.
     * 
     * @param ribbonItem
     */
    void setRibbonGroupItem(AbstractRibbonGroupItem ribbonItem);
}
