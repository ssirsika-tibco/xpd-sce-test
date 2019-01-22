/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.validator.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * Validation scope provider for RSD file validation.
 * 
 * @author nwilson
 * @since 10 Mar 2015
 */
public class RSDScopeProvider implements IScopeProvider {

    /**
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.destinations.Destination,
     *      java.lang.String, com.tibco.xpd.validation.provider.IValidationItem)
     * 
     * @param destination
     *            The validation destination.
     * @param providerId
     *            The validation provider id.
     * @param item
     *            The valiation item.
     * @return A collection to EObjects to revalidate.
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> items = new ArrayList<>();
        WorkingCopy wc = item.getWorkingCopy();
        if (wc instanceof RsdWorkingCopy) {
            EObject root = wc.getRootElement();
            if (root instanceof Service) {
                items.add(root);
            }
        }
        return items;
    }

}
