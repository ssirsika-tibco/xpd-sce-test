/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rest.schema.validator.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.rest.schema.JsonSchemaWorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

public class JsonSchemaScopeProvider implements IScopeProvider {

    /**
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.destinations.Destination,
     *      java.lang.String, com.tibco.xpd.validation.provider.IValidationItem)
     * 
     * @param destination
     * @param providerId
     * @param item
     * @return
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> items = new ArrayList<>();
        WorkingCopy wc = item.getWorkingCopy();
        if (wc instanceof JsonSchemaWorkingCopy) {
            EObject root = wc.getRootElement();
            if (root instanceof Package) {
                items.add(root);
            }
        }
        return items;
    }

}
