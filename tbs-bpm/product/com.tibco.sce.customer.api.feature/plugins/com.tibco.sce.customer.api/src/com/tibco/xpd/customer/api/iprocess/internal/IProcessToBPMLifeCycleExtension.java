/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.internal;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.customer.api.internal.Messages;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener;
import com.tibco.xpd.customer.api.plugin.BusinessStudioCustomerApiPlugin;

/**
 * Wrapper for iProcessToBPMConversion Extension LifeCycleListener elements.
 * 
 * @author aallway
 * @since 19 May 2014
 */
public class IProcessToBPMLifeCycleExtension {

    private static final String ID_ATTR = "ListenerId"; //$NON-NLS-1$

    private static final String DESC_ATTR = "ListenerDescription"; //$NON-NLS-1$

    private static final String CLASS_ATTR = "LifeCycleListenerClass"; //$NON-NLS-1$

    private IConfigurationElement element;

    private AbstractIProcessToBPMLifeCycleListener contributedtClass;

    /**
     * Construct the wrapper from the given extension point configuration
     * element.
     */
    public IProcessToBPMLifeCycleExtension(IConfigurationElement element) {
        this.element = element;

        try {
            Object obj = element.createExecutableExtension(CLASS_ATTR);

            if (obj instanceof AbstractIProcessToBPMLifeCycleListener) {
                contributedtClass =
                        (AbstractIProcessToBPMLifeCycleListener) obj;
            } else {
                throw (new Exception(
                        "Contributed class is not instanceof com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener")); //$NON-NLS-1$
            }
        } catch (Exception e) {
            BusinessStudioCustomerApiPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            String.format(Messages.IProcessToBPMLifeCycleExtension_InvalidLifeCycleListenerContrib_msg,
                                    element.getContributor().getName()));
        }
    }

    /**
     * @return The Life cycle listener id
     */
    public String getId() {
        return element.getAttribute(ID_ATTR);
    }

    /**
     * @return The Life cycle listener description
     */
    public String getDescription() {
        String desc = element.getAttribute(DESC_ATTR);

        if (desc != null && !desc.isEmpty()) {
            return desc;
        }

        return getId();
    }

    /**
     * @return The life cycle listener class.
     */
    public AbstractIProcessToBPMLifeCycleListener getLifeCycleListener() {
        return contributedtClass;
    }

}
