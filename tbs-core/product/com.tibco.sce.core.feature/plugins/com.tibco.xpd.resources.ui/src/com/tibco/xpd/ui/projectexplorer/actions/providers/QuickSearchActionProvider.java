/**
 * QuickSearchActionProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;
import com.tibco.xpd.resources.ui.internal.Messages;


/**
 * QuickSearchActionProvider
 * 
 */
public class QuickSearchActionProvider extends CommonActionProvider {

    private QuickSearchPopupAction quickSearchAction =
            new QuickSearchPopupAction(
                    Messages.QuickSearchActionProvider_FindWorkspaceElements_tooltip);

    /**
     * 
     */
    public QuickSearchActionProvider() {
        super();
    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);

        quickSearchAction
                .setToolTipText(Messages.QuickSearchActionProvider_FindWorkspaceElements_tooltip);

        if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            ((ICommonViewerWorkbenchSite) site.getViewSite()).getActionBars()
                    .getToolBarManager().add(quickSearchAction);
        }
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);

        actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),
                quickSearchAction);
    }

}
