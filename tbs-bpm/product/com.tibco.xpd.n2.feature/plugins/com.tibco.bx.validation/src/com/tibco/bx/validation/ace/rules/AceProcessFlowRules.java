/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collection;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
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

    }

}
