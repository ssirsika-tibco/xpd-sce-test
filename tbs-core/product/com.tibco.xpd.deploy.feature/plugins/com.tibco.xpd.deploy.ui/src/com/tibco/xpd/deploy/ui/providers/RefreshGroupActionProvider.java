/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.deploy.ui.actions.RefreshServerGroupAction;

/**
 * Adds the "Refresh" action to the menu.
 * 
 */
public class RefreshGroupActionProvider extends CommonActionProvider {

    private RefreshServerGroupAction refreshServerGroupAction;

    @Override
    public void init(final ICommonActionExtensionSite aSite) {
        refreshServerGroupAction = new RefreshServerGroupAction(aSite
                .getStructuredViewer());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
     * action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        super.fillContextMenu(menu);
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                refreshServerGroupAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars
     * )
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);
        actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(),
                refreshServerGroupAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.
     * ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            refreshServerGroupAction
                    .selectionChanged((IStructuredSelection) context
                            .getSelection());
        }
    }

}
