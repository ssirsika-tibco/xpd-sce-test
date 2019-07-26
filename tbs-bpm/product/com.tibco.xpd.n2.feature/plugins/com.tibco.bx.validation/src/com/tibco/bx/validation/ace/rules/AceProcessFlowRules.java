/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collection;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ACE specific Flow related rules...
 * <li>Message flows are not supported.</li>
 *
 * @author aallway
 * @since 24 Apr 2019
 */
public class AceProcessFlowRules extends ProcessValidationRule {

    private static final String ACE_ISSUE_MESSAGE_FLOW_NOT_SUPPORTED =
            "ace.message.flow.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_CANNOT_START_WITH_RECEIVETASK = "ace.cant.start.with.receive.task"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     *
     * @param process
     */
    @Override
    public void validate(Process process) {
        Collection<MessageFlow> messageFlowsInProc =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(process);

        for (MessageFlow messageFlow : messageFlowsInProc) {
            addIssue(ACE_ISSUE_MESSAGE_FLOW_NOT_SUPPORTED, messageFlow);
        }

        /*
         * Sid ACE-2019 don't allow receive task at start of flow (used to be equiv
         * of message start event, but now that it's not a message I don't think it's
         * a good idea to allow this pattern anymore.
         */
        for (Activity activity : process.getActivities()) {
            if (TaskType.RECEIVE_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(activity))) {
                if (activity.getIncomingTransitions().isEmpty()) {
                    addIssue(ACE_ISSUE_CANNOT_START_WITH_RECEIVETASK, activity);
                }
            }
        }

    }

}
