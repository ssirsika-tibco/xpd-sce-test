/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process manager specific rules for event subprocesses.
 * 
 * @author sajain
 * @since Aug 22, 2014
 */
public class EventSubProcessRule extends ProcessActivitiesValidationRule {

    /**
     * An event sub-process cannot be embedded in another sub-process.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_MUST_BE_TOP_LEVEL =
            "bx.eventSubProcessMustBeTopLevel"; //$NON-NLS-1$

    /**
     * Standard / Multiple Instance loops are not supported for Event
     * Sub-Processes.
     */
    private static final String ISSUE_STANDARD_MULTIINSTANCE_LOOPS_NOT_SUPPORTED =
            "bx.standardOrMultiInstanceLoopsNotSupportedInESP"; //$NON-NLS-1$

    /**
     * Ad-Hoc Event Sub-Processes are not supported.
     */
    private static final String ISSUE_ADHOC_EVENT_SUBPROCESS_NOT_SUPPORTED =
            "bx.AdhocEventSubprocessNotSupported"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity act) {
        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(act))) {

            /*
             * An event sub-process cannot be embedded in another sub-process.
             */
            if (!isEventSubProcessTopLevel(act)) {
                addIssue(ISSUE_EVENT_SUB_PROCESS_MUST_BE_TOP_LEVEL, act);
            }

            /*
             * Standard / Multiple Instance loops are not supported for Event
             * Sub-Processes.
             */
            if (isStandardOrMultiInstanceLoopSet(act)) {
                addIssue(ISSUE_STANDARD_MULTIINSTANCE_LOOPS_NOT_SUPPORTED, act);
            }

            /*
             * Ad-Hoc Event Sub-Processes are not supported.
             */
            if (isAdHocActivity(act)) {
                addIssue(ISSUE_ADHOC_EVENT_SUBPROCESS_NOT_SUPPORTED, act);
            }
        }
    }

    /**
     * Return <code>true</code> if the container of the specified activity is a
     * process, <code>false</code> otherwise.
     * 
     * @param act
     *            Event subprocess activity.
     * @return <code>true</code> if the container of the specified activity is a
     *         process, <code>false</code> otherwise.
     */
    private boolean isEventSubProcessTopLevel(Activity act) {

        /*
         * Return false if it's placed inside another activity set.
         */
        if (Xpdl2ModelUtil.getContainer(act) instanceof ActivitySet) {
            return false;
        }

        /*
         * Return true otherwise.
         */
        return true;
    }

    /**
     * Return <code>true</code> if standard loop or multiple instance loop is
     * set on the specified activity, <code>false</code> otherwise.
     * 
     * @param act
     *            Event subprocess activity.
     * @return <code>true</code> if standard loop or multiple instance loop is
     *         set on the specified activity, <code>false</code> otherwise.
     */
    private boolean isStandardOrMultiInstanceLoopSet(Activity act) {

        /*
         * Fetch loop.
         */
        Loop loop = act.getLoop();

        if (loop != null) {
            /*
             * Return true if loop type is either standard or multi-instance.
             */
            if (LoopType.STANDARD_LITERAL.equals(loop.getLoopType())
                    || LoopType.MULTI_INSTANCE_LITERAL.equals(loop
                            .getLoopType())) {

                return true;
            }
        }

        /*
         * Return false otherwise.
         */
        return false;

    }

    /**
     * Return <code>true</code> if the specified Event subprocess activity set
     * is set to AdHoc, <code>false</code> otherwise.
     * 
     * @param act
     *            Event subprocess activity.
     * @return <code>true</code> if the specified Event subprocess activity set
     *         is set to AdHoc, <code>false</code> otherwise.
     */
    private boolean isAdHocActivity(Activity act) {

        Process proc = act.getProcess();

        if (proc != null) {
            ActivitySet actSet =
                    proc.getActivitySet(act.getBlockActivity()
                            .getActivitySetId());

            if (actSet != null) {

                return actSet.isAdHoc();
            }
        }

        return false;
    }

}
