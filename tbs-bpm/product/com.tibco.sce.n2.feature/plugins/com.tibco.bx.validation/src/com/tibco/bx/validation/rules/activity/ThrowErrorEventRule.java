/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules specific to Throw Error Events.
 * 
 * @author kthombar
 * @since Sep 16, 2015
 */
public class ThrowErrorEventRule extends ProcessValidationRule {

    /**
     * Complex type data (%1$s) cannot be associated with 'Throw Process /
     * Sub-Process Error' events.
     */
    private static final String COMPLEX_TYPES_NOT_ALLOWED =
            "bx.complexTypeCannotBeAssociatedWithErrorCodeThrowErrorEvents"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActivitiesInProc) {

            if (ThrowErrorEventUtil.isThrowProcessErrorEvent(activity)) {

                Collection<ActivityInterfaceData> activityInterfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(activity);

                for (ActivityInterfaceData aID : activityInterfaceData) {

                    ProcessRelevantData prd = aID.getData();

                    if (prd != null) {
                        Object baseType =
                                BasicTypeConverterFactory.INSTANCE
                                        .getBaseType(prd, false);

                        /*
                         * XPD-7866: Only allow basic type data fields
                         * associated with throw error events configured to
                         * Throw Process/ Sub Process Error.
                         */
                        if (!(baseType instanceof BasicType)) {

                            addIssue(COMPLEX_TYPES_NOT_ALLOWED,
                                    activity,
                                    Collections.singletonList(Xpdl2ModelUtil
                                            .getDisplayNameOrName(prd)));

                        }
                    }
                }
            }
        }
    }
}
