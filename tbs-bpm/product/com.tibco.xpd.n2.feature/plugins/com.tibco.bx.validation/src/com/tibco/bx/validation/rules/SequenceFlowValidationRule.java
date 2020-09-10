/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;

/**
 *
 *
 * @author aallway
 * @since 4 Sep 2020
 */
public class SequenceFlowValidationRule extends FlowContainerValidationRule {

    private static final String ISSUE_FLOW_NAME_TOO_LONG = "bx.flowNameToLong"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     *
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {

        for (Transition flow : container.getTransitions()) {
            if (flow.getName() != null && flow.getName().length() > 64) {
                addIssue(ISSUE_FLOW_NAME_TOO_LONG, flow);
            }
        }
    }


}
