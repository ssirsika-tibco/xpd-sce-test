/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.actions.OptimiseProcessPackageAction;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author aallway
 * 
 */
public class OptimiseProcessPackageActionProvider extends CommonActionProvider {

    protected OptimiseProcessPackageAction optimisePkgAction;

    boolean iProcessDestinationEnable = true;

    /**
     * 
     */
    public OptimiseProcessPackageActionProvider() {
        init();
    }

    private void init() {
        optimisePkgAction = new OptimiseProcessPackageAction();

    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();
        /*
         * XPD-3833 Hide the Create Optimised Package menu option and
         * "Inline Sub-Process" part of process properties general tab unless
         * XPDL has iProcess Destination selected
         */
        if (iProcessDestinationEnable) {
            ISelectionProvider sp =
                    this.getActionSite().getViewSite().getSelectionProvider();
            optimisePkgAction.setSelectionProvider(sp);

            // Add a separator to separate ourselves from delete etc above.
            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    new Separator());
            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    optimisePkgAction);
        }
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        /*
         * XPD-3833 Hide the Create Optimised Package menu option and
         * "Inline Sub-Process" part of process properties general tab unless
         * XPDL has iProcess Destination selected
         */
        if (selection.getFirstElement() instanceof IResource) {
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopy((IResource) selection
                            .getFirstElement());
            iProcessDestinationEnable =
                    ProcessDestinationUtil.isIProcessDestinationSelected(wc
                            .getRootElement());
        }
        optimisePkgAction.selectionChanged(selection);
    }

}
