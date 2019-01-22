/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (10 May 2010)
 */
public class GenMsgPartParamsMustBeMandatory extends
        FlowContainerValidationRule {

    /*
     * These are same issue message but different Id so we can provide different
     * qick fixes
     */
    private static final String ISSUE_MSGPART_PARAMS_MUST_BE_MANDATORY =
            "bx.paramsForGenMsgMustBeMandatory"; //$NON-NLS-1$

    private static final String ISSUE_MSGPART_ASSOCPARAMS_MUST_BE_MANDATORY =
            "bx.assocParamsForGenMsgMustBeOptional"; //$NON-NLS-1$

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
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

                /*
                 * If parameters are associated then the association must be
                 * mandatory.
                 */
                List<AssociatedParameter> activityAssociatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                if (activityAssociatedParameters != null
                        && !activityAssociatedParameters.isEmpty()) {
                    boolean hasNonMandatoryParams = false;

                    for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                        if (!associatedParameter.isMandatory()) {
                            ModeType mode = associatedParameter.getMode();
                            if (ModeType.IN_LITERAL.equals(mode)
                                    || ModeType.INOUT_LITERAL.equals(mode)) {
                                hasNonMandatoryParams = true;
                                break;
                            }
                        }
                    }

                    if (hasNonMandatoryParams) {
                        addIssue(ISSUE_MSGPART_ASSOCPARAMS_MUST_BE_MANDATORY,
                                activity);
                    }

                } else {
                    /*
                     * Implicit association of all parameters.
                     */
                    boolean hasNonMandatoryParams = false;

                    List<FormalParameter> associatedFormalParameters =
                            ProcessInterfaceUtil
                                    .getAssociatedFormalParameters(activity);
                    for (FormalParameter formalParameter : associatedFormalParameters) {
                        if (!formalParameter.isRequired()) {
                            ModeType mode = formalParameter.getMode();
                            if (ModeType.IN_LITERAL.equals(mode)
                                    || ModeType.INOUT_LITERAL.equals(mode)) {
                                hasNonMandatoryParams = true;
                                break;
                            }
                        }
                    }

                    if (hasNonMandatoryParams) {
                        addIssue(ISSUE_MSGPART_PARAMS_MUST_BE_MANDATORY,
                                activity);
                    }
                }
            }
        }

        return;

    }

}
