/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. validates if a process has more than one start event of type none
 * 
 * 
 * @author bharge
 * @since 3.3 (7 May 2010)
 */
public class NoneStartEventRule extends ProcessValidationRule {

    private static final String ISSUE_ID = "bx.noneStartEventMoreThanOne"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        int noneStartEventsCnt = 0;
        for (Activity activity : activities) {
            Event event = activity.getEvent();
            if (event instanceof StartEvent) {
                TriggerType triggerType = ((StartEvent) event).getTrigger();
                if (TriggerType.NONE_LITERAL.equals(triggerType)) {
                    noneStartEventsCnt++;
                    if (noneStartEventsCnt > 1)
                        break;
                }
            }
        }
        if (noneStartEventsCnt > 1) {
            addIssue(ISSUE_ID, process);
            /*
             * XPD-6813: Initially we incorrectly raised problem marker on all
             * the none start events in the process, rather we should raise
             * problem markers only on the activites in the specific flow
             * container.
             */
            raiseIssueOnAllNoneStartEvents(process, activities);
        }
    }

    /**
     * Raises problem markers on all the none start events in the passed set of
     * activities.
     * 
     * @param process
     * 
     * @param activities
     *            the activities in consideration
     */
    private void raiseIssueOnAllNoneStartEvents(Process process,
            EList<Activity> activities) {

        for (Activity activity : activities) {
            /*
             * XPD-6813 : standard BPMN rule specifies that an event sub process
             * cannot have multiple start events, so we do not need Process
             * Manager to duplicate this issue un-necessarily if the parent flow
             * container is event sub process
             */
            if (!isActivityInEventSubProcess(process, activity)) {

                Event event = activity.getEvent();
                if (event instanceof StartEvent) {

                    TriggerType triggerType = ((StartEvent) event).getTrigger();
                    if (TriggerType.NONE_LITERAL.equals(triggerType)) {

                        addIssue(ISSUE_ID, activity);
                    }
                }
            }
        }
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
