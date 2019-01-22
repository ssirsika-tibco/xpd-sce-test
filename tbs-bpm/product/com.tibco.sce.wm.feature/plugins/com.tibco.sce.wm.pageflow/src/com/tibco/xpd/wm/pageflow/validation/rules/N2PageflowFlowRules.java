/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * N2PageflowFlowRules
 * 
 * @author aallway
 * @since 3.3 (13 Nov 2009)
 */
public class N2PageflowFlowRules extends ProcessValidationRule {

    private static final String ISSUE_MUTLISTART_NOT_SUPPORTED =
            "wm.pageflow.multiStartNotSupported"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        List<Activity> startActivities = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (Xpdl2ModelUtil.isStartProcessActivity(activity)) {
                /*
                 * XPD-6813 : standard BPMN rule specifies that an event sub
                 * process cannot have multiple start events, so we do not need
                 * Work Manager to duplicate this issue un-necessarily if the
                 * parent flow container is event sub process.
                 */
                if (!isActivityInEventSubProcess(process, activity)) {

                    startActivities.add(activity);
                }
            }
        }

        if (startActivities.size() > 1) {
            for (Activity startAct : startActivities) {
                addIssue(ISSUE_MUTLISTART_NOT_SUPPORTED, startAct);
            }
        }

        return;
    }

    /**
     * 
     * @param process
     *            the parent process
     * @param activity
     *            the activity under consideration
     * @return <code>true</code> if the Activity is contained in an Event Sub
     *         process else return <code>false</code>
     */
    private boolean isActivityInEventSubProcess(Process process,
            Activity activity) {

        FlowContainer flowContainer = activity.getFlowContainer();

        if (flowContainer instanceof ActivitySet) {

            ActivitySet activitySet = (ActivitySet) flowContainer;

            Activity embSubProcActivityForActSet =
                    Xpdl2ModelUtil.getEmbSubProcActivityForActSet(process,
                            activitySet.getId());

            TaskType taskTypeStrict =
                    TaskObjectUtil
                            .getTaskTypeStrict(embSubProcActivityForActSet);

            return (TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskTypeStrict));
        }
        return false;
    }
}
