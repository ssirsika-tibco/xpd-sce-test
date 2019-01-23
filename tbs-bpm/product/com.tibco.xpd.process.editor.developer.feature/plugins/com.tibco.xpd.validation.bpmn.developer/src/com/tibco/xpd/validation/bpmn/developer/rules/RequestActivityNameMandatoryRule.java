/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * An API activity must have a name associated. While generating a WSDL
 * operation for the API acitivity it is invalid not to have a name.
 * 
 * @author rsomayaj
 * 
 */
public class RequestActivityNameMandatoryRule extends ActivityValidationRule {

    private static final String ISSUE_ID = "bpmn.dev.apiAcitivityNameMandatory"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param container
     */
    @Override
    public void validate(Activity activity) {
        boolean isProcessAPIActivity = isMandatoryNamedAPIActivity(activity);
        if (isProcessAPIActivity) {
            if (activity.getName() == null
                    || (activity.getName() != null && (activity.getName()
                            .equals("") || activity.getName().length() < 1))) { //$NON-NLS-1$
                addIssue(ISSUE_ID, activity);
            }
        }
    }

    /**
     * @param activity
     * @return true if the given activity is a process API activity that must
     *         have a name.
     */
    private boolean isMandatoryNamedAPIActivity(Activity activity) {
        boolean isProcessAPIActivity =
                Xpdl2ModelUtil.isProcessAPIActivity(activity);
        if (isProcessAPIActivity) {
            // End Error Event is a process API activity BUT does not require a
            // name (the error code is the only significant thing).
            if (!(activity.getEvent() instanceof EndEvent)
                    || !ResultType.ERROR_LITERAL.equals(((EndEvent) activity
                            .getEvent()).getResult())) {
                return true;
            }
        }
        return false;
    }

}
