/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * In-flow request activities cannot have correlation data association disabled.
 * 
 * @author sajain
 * @since Sep 22, 2014
 */
public class InFlowRequestActivityMustHaveCorrelationDataRule extends
        ProcessActivitiesValidationRule {

    /**
     * Correlation data initialisation is always required in in-flow request
     * activities.
     */
    private static final String ISSUE_ESP_MESSAGE_START_EVENTS_ALWAYS_REQUIRE_CORRELATION =
            "bx.inFlowRequestActivitiesMustAlwaysRequireCorrelation"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        /*
         * Check if the activity is an incoming request activity that does not
         * start a new process instance.
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)
                && !Xpdl2ModelUtil.isMessageProcessStartActivity(activity)) {

            /*
             * Check whether we need to raise
             * "In-flow request activities cannot have correlation data association disabled."
             * problem marker.
             */
            if (isNoCorrelationDataInitializationRequiredSet(activity)) {

                addIssue(ISSUE_ESP_MESSAGE_START_EVENTS_ALWAYS_REQUIRE_CORRELATION,
                        activity);

            }
        }

    }

    /**
     * Return <code>true</code> if the user has checked
     * "No correlation data initialisation required" option on the specified
     * activity, <code>false</code> otherwise.
     * 
     * @param activity
     *            Activity to look in.
     * @return <code>true</code> if the user has checked
     *         "No correlation data initialisation required" option on the
     *         specified activity, <code>false</code> otherwise.
     */
    private boolean isNoCorrelationDataInitializationRequiredSet(
            Activity activity) {

        /*
         * Fetch associated correlation fields element.
         */
        AssociatedCorrelationFields acfs =
                (AssociatedCorrelationFields) Xpdl2ModelUtil
                        .getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedCorrelationFields());

        /*
         * Check if associated correlation fields is null.
         */
        if (acfs != null) {

            /*
             * Check if user has set
             * "No correlation data initialisation required" option.
             */
            if (acfs.isDisableImplicitAssociation()) {

                return true;
            }
        }

        return false;
    }
}
