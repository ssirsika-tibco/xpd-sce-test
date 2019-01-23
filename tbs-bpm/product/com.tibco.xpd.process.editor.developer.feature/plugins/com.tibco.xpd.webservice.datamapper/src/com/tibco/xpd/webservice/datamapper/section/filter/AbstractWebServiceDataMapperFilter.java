/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperPlugin;

/**
 * Abstract filter for WebService Datamapper
 * 
 * @author ssirsika
 * @since 27-Jan-2016
 */
public abstract class AbstractWebServiceDataMapperFilter implements IFilter,
        IPluginContribution {
    private IFilter filter;

    public AbstractWebServiceDataMapperFilter() {
        filter = createFilter();
    }

    /**
     * @return
     */
    protected abstract IFilter createFilter();

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public abstract String getLocalId();

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    @Override
    public String getPluginId() {
        return WebServiceDataMapperPlugin.PLUGIN_ID;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        IPluginContribution pluginContributon = this;
        if (PlatformUI.isWorkbenchRunning() && pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
            return false;
        }

        boolean result = filter.select(toTest);
        return result;
    }

}
