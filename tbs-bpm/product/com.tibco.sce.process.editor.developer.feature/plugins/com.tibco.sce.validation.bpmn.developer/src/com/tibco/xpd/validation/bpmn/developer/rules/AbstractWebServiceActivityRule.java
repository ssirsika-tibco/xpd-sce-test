/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;

import org.eclipse.wst.wsdl.Fault;

import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Abstract validation rule class for web service tasks,
 * 
 * @author rsomayaj
 * @since 3.3 (29 Apr 2010)
 */
public abstract class AbstractWebServiceActivityRule extends
        ProcessValidationRule {

    /**
     * 
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            validateActivity(activity);
        }

    }

    /**
     * @param activity
     */
    abstract protected void validateActivity(Activity activity);

    /**
     * @param activity
     * @return
     */
    protected boolean isServiceInvocationTask(Activity activity) {
        TaskType taskType = TaskObjectUtil.getTaskType(activity);
        if (TaskType.SERVICE_LITERAL.equals(taskType)) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    protected boolean isOneWayServiceInvocationTask(Activity activity) {

        if (!ReplyActivityUtil.isReplyActivity(activity)) {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.SEND_LITERAL.equals(taskType)) {
                return true;
            }

            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    protected boolean isNonGeneratedThrowFaultMessage(Activity activity) {
        boolean ret = false;
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            ret = true;

            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(activity);
            if (requestActivity != null) {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {
                    ret = false;
                }
            }
        }

        return ret;
    }

    /**
     * @param activity
     * @return
     */
    protected boolean isNonGeneratedReplyActivity(Activity activity) {
        boolean ret = false;
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            ret = true;

            Activity requestActivity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (requestActivity != null) {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {
                    ret = false;
                }
            }
        }

        return ret;
    }

    /**
     * Returns if it is an incoming request activity and not a generated one
     * 
     * @param activity
     * @return
     */
    protected boolean isNonGeneratedIncomingRequestActivity(Activity activity) {
        return ReplyActivityUtil.isIncomingRequestActivity(activity)
                && !(Xpdl2ModelUtil.isGeneratedRequestActivity(activity));

    }

    /**
     * Returns if it is WSDL fault catching activity, mostly applies to those on
     * the border of Web Service tasks.
     * 
     * @param activity
     * @return
     */
    protected boolean isCatchErrorActivity(Activity activity) {
        Fault caughtWsdlFault =
                CatchWsdlErrorEventUtil.getCaughtWsdlFault(activity);
        return caughtWsdlFault != null;
    }

    /**
     * This validation needn't be run for Process API activity whose mappings
     * have been generated, or those reply activities whose request activities
     * have their mappings generated.
     * 
     * @param activity
     * @return
     */
    protected boolean shouldNotValidateForActivity(Activity activity) {
        boolean processAPIActivity =
                Xpdl2ModelUtil.isProcessAPIActivity(activity);
        if (processAPIActivity) {
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                return true;
            } else {
                if (ReplyActivityUtil.isReplyActivity(activity)) {
                    Activity requestActivityForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(activity);
                    if (requestActivityForReplyActivity != null
                            && Xpdl2ModelUtil
                                    .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                        return true;
                    }
                } else if (ThrowErrorEventUtil
                        .isThrowFaultMessageErrorEvent(activity)) {
                    if (ThrowErrorEventUtil
                            .shouldGenerateMappingsForErrorEvent(activity)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
