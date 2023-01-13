/*
 * Copyright (c) TIBCO Software Inc 2004, 2022. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collections;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.resolutions.RemoveCorrelationAssociationResolution;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sid ACE-6366 Validation rule for correlation data associations.
 *
 * <li>Mode 'Join' for correlation data '<name>' is no longer supported for incoming request activities</li>
 * <li>Mode 'Initialize' for correlation data '<name>' is no longer supported for incoming request activities</li>
 * 
 * 
 * @author aallway
 * @since Oct 2022
 */
public class AceCorrelationAssociationsValidationRules extends ProcessActivitiesValidationRule {

    private static final String ACE_ISSUE_CORRELATION_JOIN_NOT_SUPPORTED = "ace.correlation.join.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_CORRELATION_INIT_NOT_SUPPORTED = "ace.correlation.init.not.supported"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     *
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (isIncomingRequestEvent(activity)) {
            AssociatedCorrelationFields associatedCorrelations =
                    (AssociatedCorrelationFields) Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_AssociatedCorrelationFields());

            if (associatedCorrelations != null && !associatedCorrelations.getAssociatedCorrelationField().isEmpty()) {

                for (AssociatedCorrelationField correlation : associatedCorrelations.getAssociatedCorrelationField()) {
                    if (CorrelationMode.INITIALIZE.equals(correlation.getCorrelationMode())) {
                        addIssue(ACE_ISSUE_CORRELATION_INIT_NOT_SUPPORTED,
                                activity,
                                Collections
                                        .singletonList(correlation.getName()),
                                Collections.singletonMap(RemoveCorrelationAssociationResolution.FIELD_NAME,
                                        correlation.getName()));

                    } else if (CorrelationMode.JOIN.equals(correlation.getCorrelationMode())) {
                        addIssue(ACE_ISSUE_CORRELATION_JOIN_NOT_SUPPORTED,
                                activity,
                                Collections
                                        .singletonList(correlation.getName()),
                                Collections.singletonMap(RemoveCorrelationAssociationResolution.FIELD_NAME,
                                        correlation.getName()));
                    }
                }
            }
        }
    }

    /**
     * @param activity
     * 
     * @return <code>true</code> if the activity is incoming request activity (receive task or catch event type none)
     */
    private boolean isIncomingRequestEvent(Activity activity) {
        if (TaskType.RECEIVE_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(activity))) {
            return true;

        } else if (activity.getEvent() != null && CatchThrow.CATCH.equals(EventObjectUtil.getCatchThrowType(activity))
                && EventTriggerType.EVENT_NONE_LITERAL.equals(EventObjectUtil.getEventTriggerType(activity))) {
            return true;
        }

        return false;
    }

}
