/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Abstract base class for rules that operate on an XPDL2 Process.
 * 
 * @author nwilson
 */
public abstract class FlowContainerValidationRule extends Xpdl2ValidationRule {

    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return FlowContainer.class;
    }

    /**
     * @param o
     *            The object to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     */
    @Override
    public void validate(Object o) {
        if (o instanceof FlowContainer) {
            validate((FlowContainer) o);
        }
    }

    /**
     * @param container
     *            The container to validate.
     */
    public abstract void validate(FlowContainer container);
}
