/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processeditor.xpdl2.actions.MoveProcessToOtherPackageAction;

/**
 * Action provider for Moving(refactoring) process to different package.
 * 
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
public class MoveProcessToOtherPackageActionProvider extends
        CommonActionProvider {
    protected MoveProcessToOtherPackageAction moveProcessToOtherPackageAction;

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        makeActions();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                new Separator());
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                moveProcessToOtherPackageAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        moveProcessToOtherPackageAction.selectionChanged(selection);
    }

    private void makeActions() {
        moveProcessToOtherPackageAction = new MoveProcessToOtherPackageAction();
    }
}
