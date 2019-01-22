/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate that embedded sub-process activities have at least one start
 * activity (an activity with no incoming flow that isn't an event handler).
 * 
 * @author aallway
 * @since 3 Apr 2012
 */
public class EmbeddedSubProcHasNoStartRule extends
        ProcessActivitiesValidationRule {

    public static final String ISSUE_NO_START_ACTIVITY =
            "bx.noStartActivityInEmbSubProc"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            ActivitySet activitySet =
                    Xpdl2ModelUtil.getEmbeddedSubProcessActivitySet(activity);

            if (activitySet != null) {
                boolean haveActWithNoIncoming = false;

                for (Activity embAct : activitySet.getActivities()) {
                    /*
                     * Must have something that' an acivity with no incoming
                     * flow that's not an event handler, compensation task, task
                     * boundary event etc
                     */
                    if (Xpdl2ModelUtil.isStartProcessActivity(embAct)) {
                        haveActWithNoIncoming = true;
                        break;
                    }
                }

                if (!haveActWithNoIncoming) {
                    addIssue(ISSUE_NO_START_ACTIVITY, activity);
                }
            }
        }
    }

}
