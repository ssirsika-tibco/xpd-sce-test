/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.jface.action.IAction;

import com.tibco.xpd.ui.actions.ShowPropertiesViewAction;



/**
 * @author aallway
 *
 */
public class NOTREQD_GmfDiagramContributionItemProvider extends
        AbstractContributionItemProvider {

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContributionItemProvider#createAction(java.lang.String, org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor)
     */
    @Override
    protected IAction createAction(String actionId, IWorkbenchPartDescriptor partDescriptor) {
        if (actionId.equals(ActionIds.ACTION_SHOW_PROPERTIES_VIEW)) {
            return new ShowPropertiesViewAction();
        }
        
        return super.createAction(actionId, partDescriptor);
    }
}
