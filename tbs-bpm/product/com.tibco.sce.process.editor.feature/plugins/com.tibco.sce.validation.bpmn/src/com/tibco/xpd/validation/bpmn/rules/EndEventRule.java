/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule checks that end events which are direct children of a process
 * cannot be a cancel end event. In a embedded sub process, Cancel end events
 * are only allowed if it is transactional.
 * 
 * 
 * @author bharge
 * 
 */
public class EndEventRule extends ProcessValidationRule {

    private static final String CANCEL_END_EVENT = "bpmn.cancelEndEvent"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        EList<Activity> activities = process.getActivities();
        for (Activity activity : activities) {
            if (activity.getEvent() instanceof EndEvent) {
                reportIfCancelEndEvent(activity);
            } else if (activity != null && activity.getBlockActivity() != null) {
                validateEmbeddedSubProcessActivities(activity);

            }
        }
    }

    private void validateEmbeddedSubProcessActivities(Activity activity) {
        Collection<EObject> embeddedProcChildren =
                Xpdl2ModelUtil.getAllNodesInEmbeddedSubProc(activity);
        for (EObject eObject : embeddedProcChildren) {
            if (eObject instanceof Activity) {
                Activity act = (Activity) eObject;
                if (act.getEvent() instanceof EndEvent) {
                    if (!TaskObjectUtil.getSubprocessIsTransactional(activity)) {
                        reportIfCancelEndEvent(act);
                    }
                } else if (act != null && act.getBlockActivity() != null) {
                    validateEmbeddedSubProcessActivities(act);
                }
            }
        }
    }

    /**
     * 
     * @param activity
     */
    private void reportIfCancelEndEvent(Activity activity) {
        if (EventObjectUtil.getEventTriggerType(activity)
                .equals(EventTriggerType.EVENT_CANCEL_LITERAL)) {
            addIssue(CANCEL_END_EVENT, activity);
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        ;
        // nothing to do here, implemented to avoid
        // UnSupportedOperationException thrown by the base class
    }

}
