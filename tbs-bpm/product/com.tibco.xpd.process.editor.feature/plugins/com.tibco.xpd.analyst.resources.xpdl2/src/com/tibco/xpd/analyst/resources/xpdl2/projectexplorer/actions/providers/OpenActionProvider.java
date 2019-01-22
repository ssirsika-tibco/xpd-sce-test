/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.OpenAction;

/**
 * Project Explorer's Open action provider
 * 
 * @author njpatel
 *
 */
public class OpenActionProvider extends CommonActionProvider {

	private boolean contribute = false;
	
	private OpenAction openAction = null;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite aSite) {
		if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			openAction = new OpenAction();
			contribute = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		if (!contribute || getContext().getSelection().isEmpty()) {
			return;
		}

		IStructuredSelection selection = (IStructuredSelection) getContext()
				.getSelection();

		openAction.selectionChanged(selection);
		if (openAction.isEnabled()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN, openAction);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	public void fillActionBars(IActionBars actionBars) {
		if (!contribute) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) getContext()
				.getSelection();

		if (selection.size() == 1
				&& selection.getFirstElement() instanceof IFile) {
			openAction.selectionChanged(selection);
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN,
					openAction);
		}
        
	}
}
