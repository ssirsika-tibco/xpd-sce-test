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
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */

public class EmbeddedSubProcStartEventRule extends ProcessValidationRule {

    private static final String ID = "bpmn.startEventNoneEmbeddedSubProc"; //$NON-NLS-1$    

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */

    public void validate(Activity activity) {

        /*
         * ABPM-911: Saket: We don't need this problem marker for Event
         * subprocesses.
         */
        if (activity != null
                && activity.getBlockActivity() != null
                && TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
            Collection<EObject> eObject =
                    Xpdl2ModelUtil.getAllNodesInEmbeddedSubProc(activity);
            for (EObject object : eObject) {
                if (object instanceof Activity) {
                    Activity act = (Activity) object;
                    if (act.getEvent() instanceof StartEvent) {
                        if (!EventObjectUtil.getEventTriggerType(act)
                                .equals(EventTriggerType.EVENT_NONE_LITERAL)) {
                            addIssue(ID, act);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            validate(activity);
        }
    }

}
