/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.projectexplorer.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.tibco.xpd.bizassets.resources.BusinessassetsPlugin;
import com.tibco.xpd.bizassets.resources.projectexplorer.actions.SetActiveQualityProjectAction;

/**
 * @author nwilson
 */
public class SetActiveQualityProjectActionProvider extends CommonActionProvider {

    private boolean contribute = false;

    private SetActiveQualityProjectAction action = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    public void init(ICommonActionExtensionSite aSite) {

        if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {

            // Initialise the action
            action = new SetActiveQualityProjectAction();
            action.setImageDescriptor(BusinessassetsPlugin
                    .getImageDescriptor("icons/FolderIcon.png")); //$NON-NLS-1$

            contribute = true;
        }

        super.init(aSite);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        if (contribute && !getContext().getSelection().isEmpty()) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            // Add action to the context menu
            menu.insertAfter(ICommonMenuConstants.GROUP_NEW, action);

            // Update action's selection change
            action.selectionChanged(selection);
        }
    }
}
