/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.rules;

import java.util.Collection;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract rule for any validation that simply wants to validate all activities
 * in a process.
 * <p>
 * This rule is executed whenever the parent process is affected by some change.
 * 
 * @author aallway
 * @since 3 Aug 2011
 */
public abstract class ProcessActivitiesValidationRule extends
        Xpdl2ValidationRule {

    /**
     * Validate the given activity.
     * 
     * @param activity
     */
    protected abstract void validate(Activity activity);

    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    @Override
    public Class<?> getTargetClass() {
        return Process.class;
    }

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     * 
     * @param o
     */
    @Override
    protected void validate(Object o) {
        if (o instanceof Process) {
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc((Process) o);

            for (Activity activity : allActivitiesInProc) {
                validate(activity);
            }
        }
    }
}
