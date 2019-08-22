/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.ConvertToCorrelationDataAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * @author nwilson
 * 
 */
public class DataFieldToCorrelationDataActionProvider extends
        CommonActionProvider {

    protected ConvertToCorrelationDataAction converToCorrelationDataAction;

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        final Shell shell = site.getViewSite().getShell();
        IShellProvider shellProvider = new IShellProvider() {
            @Override
            public Shell getShell() {
                return shell;
            }
        };
        converToCorrelationDataAction =
                new ConvertToCorrelationDataAction(
                        shellProvider,
                        Messages.DataFieldToCorrelationDataActionProvider_ActionLabel);
    }

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
            converToCorrelationDataAction.setEnabled(!isLocked);
        }
        menu.add(converToCorrelationDataAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        converToCorrelationDataAction.selectionChanged(selection);
    }

}
