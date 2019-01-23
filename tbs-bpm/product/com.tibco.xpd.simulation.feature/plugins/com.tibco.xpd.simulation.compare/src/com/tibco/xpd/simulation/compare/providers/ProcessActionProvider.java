/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.simulation.compare.CompareResultsAction;

/**
 * Provides actions for process.
 * <p>
 * <i>Created: 26 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ProcessActionProvider extends CommonActionProvider {

    private CompareResultsAction compareResultsAction;

    /**
     * The constructor.
     */
    public ProcessActionProvider() {
    }

    /**
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    public void init(final ICommonActionExtensionSite aSite) {
        compareResultsAction = new CompareResultsAction();
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        super.fillContextMenu(menu);
        menu.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS,
                compareResultsAction);
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            compareResultsAction
                    .selectionChanged((IStructuredSelection) context
                            .getSelection());
        }
    }
}
