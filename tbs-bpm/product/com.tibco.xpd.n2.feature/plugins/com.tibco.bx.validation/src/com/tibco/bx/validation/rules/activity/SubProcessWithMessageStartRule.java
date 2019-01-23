/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A sub-process must have a start-type-none to be invoked as a sub-process if
 * there are any message start activities.
 * 
 * @author aallway
 * @since 26 Aug 2011
 */
public class SubProcessWithMessageStartRule
        extends ProcessActivitiesValidationRule {

    private static final String ISSUE_SUBPROC_MUST_HAVE_TYPE_NONE_START =
            "bx.subProcWithMsgStartMustHaveNoneStart"; //$NON-NLS-1$

    private static final String ISSUE_IFC_MUST_HAVE_TYPE_NONE_START =
            "bx.interfaceWithMsgStartMustHaveNoneStart"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (TaskType.SUBPROCESS_LITERAL
                .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {

            EObject subProcOrIfc =
                    TaskObjectUtil.getSubProcessOrInterface(activity);

            if (subProcOrIfc instanceof Process) {
                Process process = (Process) subProcOrIfc;

                boolean hasMessageStarter = false;
                boolean hasTypeNoneStarter = false;

                for (Activity subActivity : process.getActivities()) {
                    if (Xpdl2ModelUtil
                            .isMessageProcessStartActivity(subActivity)) {
                        hasMessageStarter = true;

                    } else if (subActivity.getEvent() instanceof StartEvent
                            && TriggerType.NONE_LITERAL.equals(
                                    ((StartEvent) subActivity.getEvent())
                                            .getTrigger())) {
                        hasTypeNoneStarter = true;

                        /*
                         * We're only worried at all if there is no start type
                         * none so don't need to check any futehr if we find
                         * one!
                         */
                        break;
                    }
                }

                if (hasMessageStarter && !hasTypeNoneStarter) {
                    addIssue(ISSUE_SUBPROC_MUST_HAVE_TYPE_NONE_START, activity);
                }

            } else if (subProcOrIfc instanceof ProcessInterface) {
                ProcessInterface processInterface =
                        (ProcessInterface) subProcOrIfc;

                boolean hasMessageStarter = false;
                boolean hasTypeNoneStarter = false;

                for (StartMethod startMethod : processInterface
                        .getStartMethods()) {
                    TriggerType trigger = startMethod.getTrigger();

                    if (TriggerType.MESSAGE_LITERAL.equals(trigger)) {
                        hasMessageStarter = true;

                    } else if (TriggerType.NONE_LITERAL.equals(trigger)) {
                        hasTypeNoneStarter = true;

                        /*
                         * We're only worried at all if there is no start type
                         * none so don't need to check any futehr if we find
                         * one!
                         */
                        break;
                    }

                }

                if (hasMessageStarter && !hasTypeNoneStarter) {
                    addIssue(ISSUE_IFC_MUST_HAVE_TYPE_NONE_START, activity);
                }
            }
        }
    }

}
