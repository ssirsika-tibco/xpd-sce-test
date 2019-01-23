/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Check that incoming request activities that represent request/response
 * operations have a downstream reply activity
 * 
 * @author aallway
 * @since 3.2
 */
public class RequestResponseHasReply extends FlowContainerValidationRule {

    private static final String REQUESTRESPONSE_NEEDS_REPLY =
            "bpmn.dev.requestResponseNeedsDownstreamReply"; //$NON-NLS-1$

    private static final String GENERATED_WITH_OUTPARAM_NEEDS_REPLY =
            "bpmn.dev.generatedRequestWithOutParamsNeedsReply"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        //
        // Check that incoming request activities that represent
        // request/response operations have a downstream reply activity

        EList<Activity> activities = container.getActivities();

        for (Activity act : activities) {
            if (ReplyActivityUtil.isIncomingRequestActivity(act)) {
                List<Activity> replies =
                        ReplyActivityUtil.getReplyActivities(act);
                if (replies.isEmpty()) {

                    if (Xpdl2ModelUtil.isGeneratedRequestActivity(act)) {
                        /*
                         * For activities that generate their WSDL operations,
                         * we want to word the warning in terms of the fact that
                         * having out or in/out parameters is the reason for
                         * needing a reply.
                         */
                        // XPD-3704
                        if (hasOutParameters(act)
                                && !ReplyActivityUtil
                                        .isStartMessageWithReplyImmediate(act)) {
                            addIssue(GENERATED_WITH_OUTPARAM_NEEDS_REPLY, act);
                        }

                    } else {
                        if (Xpdl2WsdlUtil.isRequestResponse(act)
                                && !ReplyActivityUtil
                                        .isStartMessageWithReplyImmediate(act)) {
                            /*
                             * Otherwise (it's a non-generated operation that's
                             * got in and out messages. Then we need to say that
                             * a reply activity is needed because operation is
                             * request/response.
                             */
                            addIssue(REQUESTRESPONSE_NEEDS_REPLY, act);
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param act
     * @return true if activity has out or in/out mode parameters.
     */
    private boolean hasOutParameters(Activity activity) {
        List<AssociatedParameter> associatedParameters =
                ProcessInterfaceUtil.getActivityAssociatedParameters(activity);
        if (associatedParameters != null && !associatedParameters.isEmpty()) {
            for (AssociatedParameter associatedParameter : associatedParameters) {
                ModeType mode = associatedParameter.getMode();

                if (ModeType.OUT_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {
                    return true;
                }
            }

        } else {
            List<FormalParameter> formalParameters =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters(activity);
            for (FormalParameter formalParameter : formalParameters) {
                ModeType mode = formalParameter.getMode();

                if (ModeType.OUT_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {
                    return true;
                }
            }

        }
        return false;
    }

}
