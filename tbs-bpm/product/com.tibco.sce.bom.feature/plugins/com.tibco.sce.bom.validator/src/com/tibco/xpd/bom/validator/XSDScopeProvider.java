/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.validation.destinations.Destination;

/**
 * Scope provider for the XSD destination.
 * 
 * @author njpatel
 * 
 */
public class XSDScopeProvider extends BOMScopeProvider {

    @Override
    protected boolean isValidationEnabled(IProject project,
            Destination destination) {
        /* Is destination enabled normally */
        boolean enabled = super.isValidationEnabled(project, destination);
        if (enabled) {
            /* Re-check including project preference override setting */
            enabled =
                    BOMValidatorActivator.getDefault()
                            .isValidationDestinationEnabled(project,
                                    ValidationDestination.XSD
                                            .getDestinationId());

        }
        return enabled;
    }
}
