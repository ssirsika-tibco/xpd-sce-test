/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class ReplyActivitySimpleRules extends ProcessValidationRule {

    private static final String REQUEST_ACTIVITY_NOTDEFINED =
            "bpmn.requestActivityNotDefined"; //$NON-NLS-1$

    private static final String REQUEST_ACTIVITY_NOTFOUND =
            "bpmn.requestActivityNotFound"; //$NON-NLS-1$

    private static final String REQUEST_ACTIVITY_OUTOFSCOPE =
            "bpmn.requestActivityOutOfScope"; //$NON-NLS-1$

    private static final String REQUEST_ACTIVITY_INCORRECTTYPE =
            "bpmn.requestActivityBadType"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            if (ReplyActivityUtil.isReplyActivity(act)) {
                String requestActId =
                        ReplyActivityUtil
                                .getRequestActivityIdForReplyActivity(act);
                if (requestActId == null || requestActId.length() == 0) {
                    addIssue(REQUEST_ACTIVITY_NOTDEFINED, act);

                } else {
                    Activity reqAct =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(act);
                    if (reqAct == null) {
                        addIssue(REQUEST_ACTIVITY_NOTFOUND, act);
                    } else {
                        if (reqAct.eContainer() != act.eContainer()) {
                            addIssue(REQUEST_ACTIVITY_OUTOFSCOPE, act);
                        }

                        if (!ReplyActivityUtil
                                .isIncomingRequestActivity(reqAct)) {
                            addIssue(REQUEST_ACTIVITY_INCORRECTTYPE, act);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing because we have scope checking to do we deo everything in
        // validate(process) above
        return;
    }
}
