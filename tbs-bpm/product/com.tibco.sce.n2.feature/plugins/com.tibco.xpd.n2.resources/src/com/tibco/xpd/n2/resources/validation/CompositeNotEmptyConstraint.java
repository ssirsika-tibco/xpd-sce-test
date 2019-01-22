/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Checks that if composite is not empty.
 * 
 * @since 3.4
 * @author Jan Arciuchiewicz
 */
public class CompositeNotEmptyConstraint extends AbstractModelConstraint {

    private static final Logger LOG = BundleActivator.getDefault().getLogger();

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        if (target instanceof Composite) {
            Composite composite = ((Composite) target);
            if (eventType == EMFEventType.NULL) { // In case of batch mode.
                if (composite.getComponents().isEmpty()) {
                    return ctx.createFailureStatus();
                }

            } else {// In case of live mode.
                LOG
                        .warn(String
                                .format("Life validation for for the rule '%s' is not supported.", //$NON-NLS-1$
                                        getClass().getName()));
            }
        }

        return ctx.createSuccessStatus();
    }
}
