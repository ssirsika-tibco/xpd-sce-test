/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * PageflowUserTaskParticipantRule
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class PageflowUserTaskParticipantRule extends ProcessValidationRule {

    private static final String ISSUE_USERTASK_HAS_PARTICIPANT =
            "pageflow.userTaskHasParticipant"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                EObject[] activityPerformers =
                        TaskObjectUtil.getActivityPerformers(activity);

                if (activityPerformers != null && activityPerformers.length > 0) {
                    addIssue(ISSUE_USERTASK_HAS_PARTICIPANT, activity);
                }
            }
        }

        return;
    }

}
