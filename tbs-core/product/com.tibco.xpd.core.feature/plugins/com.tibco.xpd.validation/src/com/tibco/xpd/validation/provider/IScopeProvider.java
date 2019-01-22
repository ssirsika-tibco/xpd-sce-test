/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.destinations.Destination;

/**
 * This must be implemented by all validation scope providers referenced in the
 * <code>com.tibco.xpd.validation.provider</code> extension point.
 * 
 * @author nwilson
 */
public interface IScopeProvider {

    /**
     * Get all objects that are affected by the validation of the
     * <code>IValidationItem</code>.
     * 
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item to get the affected objects for.
     * @return A collection of objects that will need validating.
     */
    Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item);

}
