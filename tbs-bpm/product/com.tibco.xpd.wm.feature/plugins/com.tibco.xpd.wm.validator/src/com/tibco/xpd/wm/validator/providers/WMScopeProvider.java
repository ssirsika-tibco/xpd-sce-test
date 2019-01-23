/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wm.validator.providers;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * @author glewis
 *
 */
public class WMScopeProvider implements IScopeProvider {
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.destinations.Destination, java.lang.String, com.tibco.xpd.validation.provider.IValidationItem)
     */
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {        
        return Collections.emptyList();
    }

}
