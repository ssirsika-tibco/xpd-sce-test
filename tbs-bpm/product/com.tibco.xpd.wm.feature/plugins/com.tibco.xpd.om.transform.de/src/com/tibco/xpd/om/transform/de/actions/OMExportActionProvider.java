/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.actions;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Generate action provider for the OM objects and file(s).
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class OMExportActionProvider extends CommonActionProvider {

    private ExportToDEAction exportToDEAction;

    /**
     * Default constructor.
     */
    public OMExportActionProvider() {

    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        TransactionalEditingDomain ed = XpdResourcesPlugin.getDefault()
                .getEditingDomain();

        exportToDEAction = new ExportToDEAction();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE,
                exportToDEAction);
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            exportToDEAction.selectionChanged((IStructuredSelection) context
                    .getSelection());
        }
    }
}
