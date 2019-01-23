/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wm.pageflow.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.pfe.PFESpecification;
import com.tibco.xpd.n2.brm.BRMActivator;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Checks that if BRM component is present the required WP component is also
 * present.
 * 
 * @since 3.4
 * @author Jan Arciuchiewicz
 */
public class PageflowRequiresWPConstraint extends AbstractModelConstraint {

    private static final String WP_COMPONENT_IMPL_CLASS =
            "com.tibco.n2.model.common.impl.WPImplementationImpl"; //$NON-NLS-1$

    private static final Logger LOG = BRMActivator.getDefault().getLogger();

    @Override
    public IStatus validate(IValidationContext ctx) {
        EObject target = ctx.getTarget();
        EMFEventType eventType = ctx.getEventType();

        if (target instanceof PFESpecification) {
            PFESpecification brmImpl = ((PFESpecification) target);
            // In case of batch mode.
            if (eventType == EMFEventType.NULL) {
                EObject container = brmImpl.eContainer();
                if (container instanceof Component) {
                    Component brmComp = (Component) container;
                    Composite composite = brmComp.getComposite();
                    if (composite != null) {
                        for (Component c : composite.getComponents()) {
                            Implementation implementation =
                                    c.getImplementation();
                            if (WP_COMPONENT_IMPL_CLASS.equals(implementation
                                    .getClass().getName())) {
                                return ctx.createSuccessStatus();
                            }
                        }
                        return ctx
                                .createFailureStatus(String
                                        .format("Work Presentation component is not present. (Requred by Page Flow component.)")); //$NON-NLS-1$
                    }
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
