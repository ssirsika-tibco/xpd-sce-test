/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * Validation rules related to the presence of WSDL related activities left in
 * Processes after migration
 * <li>Business process service activities are not supported. Use a sub-process
 * task in asynchronous mode to invoke business processes from business service
 * or pageflow processes.</li>
 * 
 * <li>Web-service invocation activities are not supported. Use REST service
 * activities to invoke 3rd party services or asynchronous sub-process for
 * invoking business processes from business service or pageflow processes</li>
 * 
 * <li>Incoming web-service message activities and their are replies not
 * supported. Use 'type none' instead and invoke these thru run-time API if
 * required.</li>
 * 
 * @author aallway
 * @since 17 Apr 2019
 */
public class AceProcessWsdlActivityRules
        extends ProcessActivitiesValidationRule {

    /*
     * Different issues for tasks and event as events have an available quick
     * fix to set to type none.
     */
    private static final String ACE_ISSUE_INCOMING_MESSAGE_EVENT_NOTSUPPORTED =
            "ace.incoming.message.event.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_INCOMING_MESSAGE_TASK_NOTSUPPORTED =
            "ace.incoming.message.task.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_INVOKE_BUSINESSPROCESS_NOTSUPPORTED =
            "ace.invoke.businessprocess.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_WSDL_ERROR_REPLY_NOTSUPPORTED =
            "ace.wsdl.error.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_WEBSERVICE_INVOKE_NOTSUPPORTED =
            "ace.webservice.invoke.not.supported"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     *
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        /*
         * Check against Business Process Service activities.
         */
        String implementationId =
                TaskObjectUtil.getTaskImplementationExtensionId(activity);

        if (TaskImplementationTypeDefinitions.INVOKE_BUSINESS_PROCESS
                .equals(implementationId)) {
            addIssue(ACE_ISSUE_INVOKE_BUSINESSPROCESS_NOTSUPPORTED, activity);
        }

        /*
         * Check against incoming web-service activities and their replies
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {

            if (TaskType.RECEIVE_LITERAL
                    .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {

                if (TaskImplementationTypeDefinitions.WEB_SERVICE
                        .equals(TaskObjectUtil
                                .getTaskImplementationExtensionId(activity))) {
                    /*
                     * Exclude receive task if the implementation type is not
                     * WebService. At some point we'll probably have to support
                     * 'type none' Receive Task for invocation via via the
                     * Public API
                     */
                    addIssue(ACE_ISSUE_INCOMING_MESSAGE_TASK_NOTSUPPORTED,
                            activity);
                }
            } else {
                addIssue(ACE_ISSUE_INCOMING_MESSAGE_EVENT_NOTSUPPORTED,
                        activity);
            }

        } else if (ReplyActivityUtil.isReplyActivity(activity)) {
            if (TaskType.SEND_LITERAL
                    .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {
                addIssue(ACE_ISSUE_INCOMING_MESSAGE_TASK_NOTSUPPORTED,
                        activity);
            } else {
                addIssue(ACE_ISSUE_INCOMING_MESSAGE_EVENT_NOTSUPPORTED,
                        activity);
            }

        } else if (ThrowErrorEventUtil
                .isThrowFaultMessageErrorEvent(activity)) {
            addIssue(ACE_ISSUE_WSDL_ERROR_REPLY_NOTSUPPORTED, activity);

        } else {
            /*
             * If it's not an incoming request or reply to one then check if
             * it's an outgoing WebService invocation
             */
            if (TaskImplementationTypeDefinitions.WEB_SERVICE
                    .equals(implementationId)
                    || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL.equals(
                            EventObjectUtil.getEventTriggerType(activity))) {
                addIssue(ACE_ISSUE_WEBSERVICE_INVOKE_NOTSUPPORTED, activity);
            }
        }
    }


}
