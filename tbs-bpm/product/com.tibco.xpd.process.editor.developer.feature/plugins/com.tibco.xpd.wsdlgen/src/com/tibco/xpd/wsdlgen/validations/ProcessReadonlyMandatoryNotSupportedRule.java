/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule which validates for process API activities. This rule raises a WARNING
 * LEVEL marker for activities which have parameters marked Mandatory or
 * Read-only
 * 
 * @author rsomayaj
 * @since 3.3 (6 Jul 2010)
 */
public class ProcessReadonlyMandatoryNotSupportedRule extends
        ProcessActivitiesValidationRule {

    private static final String READONLY_IGNORED =
            "bpmn.warning.readonlyignored";

    private static final String NON_MANDATORY_STILL_REQD_MESSAGE_PART =
            "bpmn.warning.nonMandatoryInsignificant";

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            List<FormalParameter> associatedFormalParameters =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters(activity);
            for (FormalParameter formalParameter : associatedFormalParameters) {
                if (formalParameter.isReadOnly()) {
                    addIssue(READONLY_IGNORED,
                            activity,
                            Collections.singletonList(Xpdl2ModelUtil
                                    .getDisplayName(formalParameter)));
                }
            }

            List<AssociatedParameter> activityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            if (activityAssociatedParameters.isEmpty()) {
                /*
                 * Sid XPD-2087: Only use all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    for (FormalParameter formalParameter : associatedFormalParameters) {
                        if (!formalParameter.isRequired()) {
                            addIssue(NON_MANDATORY_STILL_REQD_MESSAGE_PART,
                                    activity,
                                    Collections.singletonList(Xpdl2ModelUtil
                                            .getDisplayName(formalParameter)));
                        }
                    }
                }
            } else {
                for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                    if (!associatedParameter.isMandatory()) {
                        ProcessRelevantData processRelevantDataFromAssociatedParam =
                                ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                        addIssue(NON_MANDATORY_STILL_REQD_MESSAGE_PART,
                                activity,
                                Collections.singletonList(Xpdl2ModelUtil
                                        .getDisplayName(processRelevantDataFromAssociatedParam)));
                    }
                }
            }
        }
    }
}
