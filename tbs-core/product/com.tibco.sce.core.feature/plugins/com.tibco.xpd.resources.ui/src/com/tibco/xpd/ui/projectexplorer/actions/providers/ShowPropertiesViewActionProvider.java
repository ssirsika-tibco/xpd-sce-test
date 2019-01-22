/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.ui.actions.ShowPropertiesViewAction;

/**
 * Common action provider to show properties view in the project explorer. Uses
 * action <code>{@link ShowPropertiesViewAction}</code>.
 * 
 * @see ShowPropertiesViewAction
 * 
 * @author njpatel
 * 
 */
public class ShowPropertiesViewActionProvider extends CommonActionProvider {

    final private ShowPropertiesViewAction showAction;

    public ShowPropertiesViewActionProvider() {
        showAction = new ShowPropertiesViewAction();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES, showAction);
    }

}
