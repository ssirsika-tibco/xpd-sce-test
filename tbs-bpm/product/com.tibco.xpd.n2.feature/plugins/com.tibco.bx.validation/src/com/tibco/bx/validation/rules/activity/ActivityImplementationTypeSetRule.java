/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * Validate activity implementation type is not unset.
 * 
 * @author aallway
 * @since 3.3 (18 Feb 2010)
 */
public class ActivityImplementationTypeSetRule extends
        FlowContainerValidationRule {

    private static final String NO_SERVICE_TYPE = "bx.noServiceTypeSelected"; //$NON-NLS-1$

    private static final String NO_SERVICE_TYPE_FOR_REST_CANDIDATE_ACTIVITIES =
            "bx.noServiceTypeSelected1";//$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            checkActivity(activity);
        }

        return;
    }

    /**
     * @param activity
     */
    private void checkActivity(Activity activity) {

        TaskType type = TaskObjectUtil.getTaskTypeStrict(activity);
        if (type != null) {
            if (TaskType.SERVICE_LITERAL.equals(type)
                    || TaskType.SEND_LITERAL.equals(type)
                    || TaskType.RECEIVE_LITERAL.equals(type)) {

                ImplementationType implType =
                        TaskObjectUtil.getTaskImplementationType(activity);
                if (implType == null
                        || ImplementationType.UNSPECIFIED_LITERAL
                                .equals(implType)) {

                    /*
                     * XPD-7721: Service task and send task with implementation
                     * = unspecified should have quick fix to set implementation
                     * = Web-Service or to REST service, hence we raise problem
                     * marker for them separately as they can have multiple
                     * resolutions.
                     */
                    if (TaskType.SERVICE_LITERAL.equals(type)
                            || TaskType.SEND_LITERAL.equals(type)) {

                        addIssue(NO_SERVICE_TYPE_FOR_REST_CANDIDATE_ACTIVITIES,
                                activity);
                    } else {

                        addIssue(NO_SERVICE_TYPE, activity);
                    }
                }
            }

        } else if (activity.getEvent() != null) {
            EventTriggerType eventType =
                    EventObjectUtil.getEventTriggerType(activity);
            if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(eventType)
                    || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                            .equals(eventType)) {
                ImplementationType implType =
                        EventObjectUtil.getEventImplementationType(activity);
                if (implType == null
                        || ImplementationType.UNSPECIFIED_LITERAL
                                .equals(implType)) {

                    /*
                     * XPD-7721: Intermediate/End Throw message events with
                     * implementation = unspecified should have quick fix to set
                     * implementation = Web-Service or to REST service, hence we
                     * raise problem marker for them separately as they can have
                     * multiple resolutions.
                     */
                    if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                            .equals(eventType)) {

                        addIssue(NO_SERVICE_TYPE_FOR_REST_CANDIDATE_ACTIVITIES,
                                activity);
                    } else {

                        addIssue(NO_SERVICE_TYPE, activity);
                    }
                }
            }
        }

        return;
    }
}
