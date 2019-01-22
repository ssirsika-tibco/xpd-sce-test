/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.engine.DefaultScopeProvider;

/**
 * Simple scope provider that passes through only the objects that have changed.
 * 
 * @author nwilson
 */
public class AffectedItemsScopeProvider implements IScopeProvider {

    /**
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item.
     * @return A collection of objects that will need validation.
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(
     *      com.tibco.xpd.validation.destinations.Destination, java.lang.String,
     *      com.tibco.xpd.validation.provider.IValidationItem)
     */
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> objects;
        if (item.getClean()) {
            objects = new DefaultScopeProvider().getAffectedObjects(
                    destination, providerId, item);
        } else {
            objects = item.getObjects();
        }
        return objects;
    }

}
