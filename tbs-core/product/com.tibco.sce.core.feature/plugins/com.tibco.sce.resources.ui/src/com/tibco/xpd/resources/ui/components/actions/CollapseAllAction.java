/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Collapses all nodes in the tree viewer.
 * 
 * @author nwilson
 * @since 12 Feb 2015
 */
public class CollapseAllAction extends Action {
    private TreeViewer viewer;

    public CollapseAllAction(TreeViewer viewer) {
        this.viewer = viewer;
        setImageDescriptor(XpdResourcesUIActivator
                .getImageDescriptor(XpdResourcesUIConstants.ICON_COMPARE_COLLAPSE_ALL));
        setToolTipText(Messages.CollapseAllAction_CollapseAllTooltip);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        viewer.collapseAll();
    }

}
