/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.xpd.deploy.ui.actions.ConnectivityActionGroup;

/**
 * Provides "Project Explorer" view actions related to server connectivity.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectivityActionProvider extends CommonActionProvider {

    private ConnectivityActionGroup connectivityActionGroup;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    @Override
    public void init(ICommonActionExtensionSite aSite) {
        connectivityActionGroup = new ConnectivityActionGroup(aSite
                .getStructuredViewer());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#dispose()
     */
    @Override
    public void dispose() {
        connectivityActionGroup.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        connectivityActionGroup.fillContextMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        connectivityActionGroup.fillActionBars(actionBars);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        connectivityActionGroup.setContext(context);
    }

}
