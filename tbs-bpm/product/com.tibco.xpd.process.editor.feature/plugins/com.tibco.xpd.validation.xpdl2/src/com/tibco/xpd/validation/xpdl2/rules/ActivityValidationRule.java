/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public abstract class ActivityValidationRule extends Xpdl2ValidationRule {
    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return Activity.class;
    }

    /**
     * @param o The object to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(
     *      java.lang.Object)
     */
    public void validate(Object o) {
        if (o instanceof Activity) {
            validate((Activity) o);
        }
    }

    /**
     * @param container The Activity to validate.
     */
    public abstract void validate(Activity container);
}
