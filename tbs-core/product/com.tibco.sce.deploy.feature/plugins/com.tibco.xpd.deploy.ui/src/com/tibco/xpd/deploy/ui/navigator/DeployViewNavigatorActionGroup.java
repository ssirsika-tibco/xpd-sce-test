/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.internal.navigator.CommonNavigatorActionGroup;
import org.eclipse.ui.internal.navigator.actions.LinkEditorAction;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.LinkHelperService;

/**
 * Action Group for Server Specific Actions. We have provided our own Action
 * Group (instead of using the one provided by eclipse) because we do not want
 * the 'link to editor' toolbar action(as it is of no use in deployment view)
 * and also we want to place the server specific actions(Add Server, Connect,
 * Disconnect , Refresh) before the 'collapse all' action.
 * 
 * @author kthombar
 * @since May 29, 2015
 */
@SuppressWarnings("restriction")
public class DeployViewNavigatorActionGroup extends CommonNavigatorActionGroup {

    /**
     * Seprator for Server Specific Actions, this will appear before the
     * "collapse all" action.
     */
    private static final String SERVER_ACTION_SEPARATOR_ID = "serverActions"; //$NON-NLS-1$

    /**
     * @param aNavigator
     * @param aViewer
     * @param linkHelperService
     */
    public DeployViewNavigatorActionGroup(CommonNavigator aNavigator,
            CommonViewer aViewer, LinkHelperService linkHelperService) {
        super(aNavigator, aViewer, linkHelperService);

    }

    /**
     * @see org.eclipse.ui.internal.navigator.CommonNavigatorActionGroup#fillToolBar(org.eclipse.jface.action.IToolBarManager)
     * 
     * @param toolBar
     */
    @Override
    protected void fillToolBar(IToolBarManager toolBar) {
        /*
         * Add our Server Specific separator
         */
        toolBar.add(new Separator(SERVER_ACTION_SEPARATOR_ID));
        toolBar.add(new Separator());
        /*
         * let the super class do all the work
         */
        super.fillToolBar(toolBar);

        IContributionItem[] items = toolBar.getItems();
        /*
         * If the super class has added the 'Link To Editor' Action to the
         * Toolbar then remove it as we do not want it.
         */
        for (IContributionItem iContributionItem : items) {

            if (iContributionItem instanceof ActionContributionItem) {

                ActionContributionItem actionContributionItem =
                        (ActionContributionItem) iContributionItem;

                IAction action = actionContributionItem.getAction();

                if (action instanceof LinkEditorAction) {

                    toolBar.remove(iContributionItem);
                    break;
                }
            }
        }
    }
}
