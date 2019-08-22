/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.ConvertToDataFieldAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ParameterToDataFieldActionProvider extends CommonActionProvider {

	private ConvertToDataFieldAction convertToDataFieldAction;

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		makeActions();
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
            convertToDataFieldAction.setEnabled(!isLocked);
        }
        menu.add(convertToDataFieldAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        convertToDataFieldAction.selectionChanged(selection);
	}

	private void makeActions() {
		convertToDataFieldAction = new ConvertToDataFieldAction(
				Messages.ParameterToDataFieldActionProvider_Action_label);
		convertToDataFieldAction.setImageDescriptor(Xpdl2ResourcesPlugin
				.getImageDescriptor(Xpdl2ResourcesConsts.ICON_PARAMTOFIELD));
	}

}
