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
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.ConvertParticipantToDataFieldAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * @author mtorres
 * 
 * 
 */
public class ParticipantToDataFieldActionProvider extends CommonActionProvider {

	protected ConvertParticipantToDataFieldAction converParticipantToDataFieldAction;

	
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
            converParticipantToDataFieldAction.setEnabled(!isLocked);
        }
        menu.add(converParticipantToDataFieldAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        converParticipantToDataFieldAction.selectionChanged(selection);
	}
	
	private void makeActions() {
	    converParticipantToDataFieldAction = new ConvertParticipantToDataFieldAction(
				Messages.ParticipantToDataFieldActionProvider_Action_label);
	    converParticipantToDataFieldAction.setImageDescriptor(Xpdl2ResourcesPlugin
				.getImageDescriptor(Xpdl2ResourcesConsts.ICON_PARAMTOFIELD));
	}
}