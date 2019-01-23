/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.destinations.Destination;

/**
 * Extension to IScopeProvider interface that provide validation scope as
 * argument. If the scope provider implements this interface then only this
 * method will be called by the framework.
 * 
 * @author wzurek
 */
public interface IScopeProvider2 extends IScopeProvider {

    /**
     * Get all objects that are affected by the validation of the
     * <code>IValidationItem</code>.
     * 
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param scope
     *            Validation scope.
     * @param item
     *            The validation item to get the affected objects for.
     * @return A collection of objects that will need validating.
     */
    Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationScope scope, IValidationItem item);

}
