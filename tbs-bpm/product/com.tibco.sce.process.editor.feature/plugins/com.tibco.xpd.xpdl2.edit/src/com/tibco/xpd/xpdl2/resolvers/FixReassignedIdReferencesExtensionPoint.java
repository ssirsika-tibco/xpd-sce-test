/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Extension point to fix the References which might need updating due to
 * Regeneration of IDs
 * 
 * @author kthombar
 * @since Apr 15, 2015
 */
public class FixReassignedIdReferencesExtensionPoint {

    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    private AbstractUniqueIdsReassignedListener contributedClass;

    private static Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    /**
     * @param elem
     */
    public FixReassignedIdReferencesExtensionPoint(IConfigurationElement element) {

        Object obj = null;

        try {
            obj = element.createExecutableExtension(ATTR_CLASS);
            if (obj instanceof AbstractUniqueIdsReassignedListener) {
                contributedClass = (AbstractUniqueIdsReassignedListener) obj;
            } else {

                logger.error(String
                        .format("Contributed class by the plugin '%1$s' is not instanceof 'com.tibco.xpd.xpdl2.resolvers.AbstractUniqueIdsReassignedListener'", element.getContributor().getName())); //$NON-NLS-1$

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return the {@link AbstractUniqueIdsReassignedListener} .
     */
    public AbstractUniqueIdsReassignedListener getUniqueIdsReassignedListener() {

        return contributedClass;
    }
}
