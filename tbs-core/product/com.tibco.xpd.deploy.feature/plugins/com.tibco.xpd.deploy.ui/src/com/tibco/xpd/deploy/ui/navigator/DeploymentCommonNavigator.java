/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Common navigator which provides integration with tabbed properties.
 * 
 * @author Jan Arciuchiewicz
 */
public class DeploymentCommonNavigator extends CommonNavigator implements
        ITabbedPropertySheetPageContributor {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContributorId() {
        // Usual eclipse practice is that contributor id is the same as a main
        // view id.
        return getSite().getId();
    }

    /**
     * @see org.eclipse.ui.navigator.CommonNavigator#getFrameToolTipText(java.lang.Object)
     * 
     * @param anElement
     * @return
     */
    // XPD-3146 mouse hover on Deployment Server View displays "R/" in tooltip
    @Override
    public String getFrameToolTipText(Object anElement) {
        return getPartName();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class)
            return new TabbedPropertySheetPage(this);
        return super.getAdapter(adapter);
    }

    /**
     * @see org.eclipse.ui.navigator.CommonNavigator#createCommonActionGroup()
     * 
     * @return
     */
    @Override
    protected ActionGroup createCommonActionGroup() {
        /*
         * SCF-75: Providing our own Action Group because we do not want the
         * 'link to editor' toolbar action and also we want to place the server
         * specific actions before the 'collapse all' action.
         */
        return new DeployViewNavigatorActionGroup(this, getCommonViewer(),
                getLinkHelperService());
    }

}
