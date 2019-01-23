/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.simulation.ui.popup.actions.PrepareSimulationAction;

/**
 * 
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ProcessActionProvider extends CommonActionProvider {

    /**
     * The constructor.
     */
    public ProcessActionProvider() {
        // TODO Auto-generated constructor stub
    }

    private PrepareSimulationAction prepareSimulationAction;

    public void init(final ICommonActionExtensionSite aSite) {
        prepareSimulationAction = new PrepareSimulationAction();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        super.fillContextMenu(menu);
        menu.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS, prepareSimulationAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);
//        actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(),
//                prepareSimulationAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    public void setContext(ActionContext context) {
        super.setContext(context);
        if(context != null) {
            prepareSimulationAction.selectionChanged((IStructuredSelection) context
                .getSelection());
       }
    }
}
