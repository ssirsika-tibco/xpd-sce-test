/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processeditor.xpdl2.actions.ServiceProcessToBusinessProcessAction;

/**
 * Action provider to convert service process to business process.
 * 
 * @author bharge
 * @since 16 Feb 2015
 */
public class ConvertServiceProcessToBusinessProcessActionProvider extends
        CommonActionProvider {

    private ServiceProcessToBusinessProcessAction convertToBusinessProcess;

    /**
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     * 
     * @param aSite
     */
    @Override
    public void init(ICommonActionExtensionSite aSite) {

        super.init(aSite);
        convertToBusinessProcess = new ServiceProcessToBusinessProcessAction();
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
                convertToBusinessProcess);
    }

    /**
     * 
     */
    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        convertToBusinessProcess.selectionChanged(selection);
    }

}
