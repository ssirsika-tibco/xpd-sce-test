/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.xpd.deploy.ui.actions.ServerElementManagementActionGroup;

/**
 * Provides actions representing operations on server elements.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerElementManagementActionProvider extends CommonActionProvider {

    private ServerElementManagementActionGroup serverElementManagementActionGroup;

    public ServerElementManagementActionProvider() {
    }

    @Override
    public void init(ICommonActionExtensionSite aSite) {
        serverElementManagementActionGroup =
                new ServerElementManagementActionGroup(aSite
                        .getStructuredViewer());
    }

    @Override
    public void dispose() {
        serverElementManagementActionGroup.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        serverElementManagementActionGroup.fillActionBars(actionBars);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        serverElementManagementActionGroup.fillContextMenu(menu);
    }

    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        serverElementManagementActionGroup.setContext(context);
    }

}
