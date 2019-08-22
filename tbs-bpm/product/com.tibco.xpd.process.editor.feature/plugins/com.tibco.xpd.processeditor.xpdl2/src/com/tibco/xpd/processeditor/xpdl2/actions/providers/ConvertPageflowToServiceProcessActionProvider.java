/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processeditor.xpdl2.actions.PageflowToServiceProcessAction;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action provider to convert pageflow to service process.
 * 
 * @author bharge
 * @since 16 Feb 2015
 */
public class ConvertPageflowToServiceProcessActionProvider extends
        CommonActionProvider {

    private PageflowToServiceProcessAction convertPageflowToServiceProcess;

    private boolean isPageflowProcess;

    /**
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     * 
     * @param aSite
     */
    @Override
    public void init(ICommonActionExtensionSite aSite) {

        super.init(aSite);
        convertPageflowToServiceProcess = new PageflowToServiceProcessAction();
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
            convertPageflowToServiceProcess.setEnabled(!isLocked);
        }

        /*
         * Menu option 'Convert Pageflow Process to Service Process' must be
         * shown only if it is a pageflow process but not any of its sub types
         */
        if (isPageflowProcess) {

            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    convertPageflowToServiceProcess);
        }
    }

    /**
     * 
     */
    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        Object firstElement = selection.getFirstElement();
        if (firstElement instanceof Process) {

            if (Xpdl2ModelUtil.isPageflowProcess((Process) firstElement)) {

                isPageflowProcess = true;
                convertPageflowToServiceProcess.selectionChanged(selection);
            } else {

                isPageflowProcess = false;
            }
        }

    }
}
