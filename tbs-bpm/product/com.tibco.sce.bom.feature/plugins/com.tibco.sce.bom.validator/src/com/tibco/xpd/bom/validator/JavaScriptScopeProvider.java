/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.validation.destinations.Destination;

/**
 * Scope provider for the Javascript validation destination.
 * 
 * @author rgreen
 * 
 */
public class JavaScriptScopeProvider extends BOMScopeProvider {

    private static final String BPM =
            "com.tibco.xpd.n2.core.n2GlobalDestination";

    @Override
    protected boolean isValidationEnabled(IProject project,
            Destination destination) {
        /* Is destination enabled normally */
        boolean enabled = super.isValidationEnabled(project, destination);
        if (enabled) {
            /* Re-check for global destination specific setting */
            enabled =
                    GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                            BPM);

        }
        return enabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.validator.BOMScopeProvider#include(org.eclipse.emf.
     * ecore.EObject)
     */
    protected boolean include(EObject eo) {
        return eo != null
                && (eo instanceof Class || eo instanceof Property
                        || eo instanceof Operation || eo instanceof Model);
    }
}
