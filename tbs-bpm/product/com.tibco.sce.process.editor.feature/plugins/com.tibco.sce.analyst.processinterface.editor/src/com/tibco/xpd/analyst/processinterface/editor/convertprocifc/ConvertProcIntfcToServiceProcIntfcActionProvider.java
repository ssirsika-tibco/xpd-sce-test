/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.analyst.processinterface.editor.convertprocifc;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Action provider to convert process interface to service process interface
 * 
 * @author bharge
 * @since 18 Feb 2015
 */
public class ConvertProcIntfcToServiceProcIntfcActionProvider extends
        CommonActionProvider {

    private ProcIntfcToServiceProcIntfcAction convertToServiceProcIntfcAction;

    /**
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     * 
     * @param aSite
     */
    @Override
    public void init(ICommonActionExtensionSite aSite) {

        super.init(aSite);
        convertToServiceProcIntfcAction =
                new ProcIntfcToServiceProcIntfcAction();
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     * 
     * @param menu
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {

        updateSelection();
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                new Separator());
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                convertToServiceProcIntfcAction);
    }

    /**
     * 
     */
    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        convertToServiceProcIntfcAction.selectionChanged(selection);
    }
}
