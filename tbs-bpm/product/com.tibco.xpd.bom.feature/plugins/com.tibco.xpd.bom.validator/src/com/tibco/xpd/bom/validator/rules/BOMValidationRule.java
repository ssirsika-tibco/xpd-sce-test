/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules;

import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * <p>
 * <i>Created: 23 Jun 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class BOMValidationRule implements IValidationRule {

    /**
     * Check if the contribution provided by section should be enabled
     * concerning current set of capabilities.
     * 
     * @return true if section should be enabled, false otherwise.
     * 
     */
    protected boolean enabledInCapabilities() {
        IPluginContribution pluginContributon = getPluginContributon();
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            // JA: We assume that the capabilities are always switch on in the
            // headless mode.
            if (pluginContributon != null
                    && WorkbenchActivityHelper.filterItem(pluginContributon)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns plug-in contribution descriptor if the section should be
     * capabilities enabled (highly recommended) or null otherwise.
     * <p/>
     * Returned descriptor should reflect plug-in contribution which contributes
     * this section so the <code>getPluginId()</code> should return id of the
     * contributing plug-in and <code>getLocalId()</code> should return id of
     * the section declared in the plug-in.
     * 
     * @return plug-in contribution descriptor if the section should be
     *         capabilities enabled (highly recommended) or null otherwise.
     * @see IPluginContribution
     * @see AbstractEObjectSection#select(Object)
     */
    public IPluginContribution getPluginContributon() {
        return null;
    }

}
