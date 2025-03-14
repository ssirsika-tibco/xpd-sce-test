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
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

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
 * <li>Incoming web-service message activities and their replies are not
 * supported. Use 'type none' instead and invoke these thru run-time API if
 * required.</li>
 * 
 * <li>Web-service error replies are no longer supported. Use 'Throw Process /
 * Sub-Process Error' instead.</li>
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

    private static final String ACE_ISSUE_INCOMING_MESSAGE_REPLY_NOTSUPPORTED =
            "ace.incoming.message.reply.not.supported"; //$NON-NLS-1$

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
            } else if (!Xpdl2ModelUtil.isEventImplemented(activity)) {
                /*
                 * ACE-1369: Saket: This rule should not be raised for process
                 * events that implement process interface events because it
                 * changes the type of the process event, but it is the
                 * interface event that is at fault.
                 */
                addIssue(ACE_ISSUE_INCOMING_MESSAGE_EVENT_NOTSUPPORTED,
                        activity);
            }

        } else if (ReplyActivityUtil.isReplyActivity(activity)) {
            /*
             * Sid ACE-2338 Just one separate issue now for all reply activities
             * (events and send-tasks (which will be false for
             * isEventImplemented)
             */
            if (!Xpdl2ModelUtil.isEventImplemented(activity)) {
                /*
                 * ACE-1369: Saket: This rule should not be raised for process
                 * events that implement process interface events because it
                 * changes the type of the process event, but it is the
                 * interface event that is at fault.
                 */
                addIssue(ACE_ISSUE_INCOMING_MESSAGE_REPLY_NOTSUPPORTED,
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
            /*
             * Sid ACE-484 - noticed that some template have older "Web Service"
             * (with space) implementation id, that highlighted that we should
             * be checking for it here.
             */
            if (TaskImplementationTypeDefinitions.WEB_SERVICE
                    .equals(implementationId)
                    || TaskImplementationTypeDefinitions.WEB_SERVICE_V2_0
                    .equals(implementationId)
                    || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL.equals(
                            EventObjectUtil.getEventTriggerType(activity))) {
                addIssue(ACE_ISSUE_WEBSERVICE_INVOKE_NOTSUPPORTED, activity);
            }
        }
    }


}
