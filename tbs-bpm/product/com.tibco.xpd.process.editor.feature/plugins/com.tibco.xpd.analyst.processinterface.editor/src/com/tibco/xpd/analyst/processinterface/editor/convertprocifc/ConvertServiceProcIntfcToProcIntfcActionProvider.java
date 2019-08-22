/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.analyst.processinterface.editor.convertprocifc;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * Action provider to convert service process interface to process interface
 * 
 * @author bharge
 * @since 18 Feb 2015
 */
public class ConvertServiceProcIntfcToProcIntfcActionProvider extends
        CommonActionProvider {

    private ServiceProcIntfcToProcIntfcAction convertToProcIntfcAction;

    /**
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     * 
     * @param aSite
     */
    @Override
    public void init(ICommonActionExtensionSite aSite) {

        super.init(aSite);
        convertToProcIntfcAction = new ServiceProcIntfcToProcIntfcAction();
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     * 
     * @param menu
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {

        updateSelection();

        /*
         * ACE-2473: Saket: Action should be disabled for locked application.
         */
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        if (selection.getFirstElement() instanceof EObject) {
            boolean isLocked =
                    (new GovernanceStateService()).isLockedForProduction((EObject) (selection.getFirstElement()));
            convertToProcIntfcAction.setEnabled(!isLocked);
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                new Separator());
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                convertToProcIntfcAction);
    }

    /**
     * 
     */
    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        convertToProcIntfcAction.selectionChanged(selection);
    }
}
