/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Validate against the use of Ad-Hoc flag in embedded sub-process.
 * 
 * @author aallway
 * @since 4 Apr 2012
 */
public class AdHocNotSupportedRule extends ProcessActivitiesValidationRule {

    private static final String ISSUE_ADHOC_NOT_SUPPORTED =
            "bx.adHocNotSupported"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            if (TaskObjectUtil.getMarkers(activity)
                    .contains(ActivityMarkerType.MARKER_AD_HOC_LITERAL)) {
                addIssue(ISSUE_ADHOC_NOT_SUPPORTED, activity);
            }
        }
    }

}
