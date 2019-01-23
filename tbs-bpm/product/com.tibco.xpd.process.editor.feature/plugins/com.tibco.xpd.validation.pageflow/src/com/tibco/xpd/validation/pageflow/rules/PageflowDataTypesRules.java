/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * PageflowDataTypesRule
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class PageflowDataTypesRules extends ProcessValidationRule {

    private static final String ISSUE_CORRELATION_DATA_NOT_SUPPORTED =
            "pageflow.correlationDataNotSupported"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        for (DataField field : process.getDataFields()) {
            if (field.isCorrelation()) {
                addIssue(ISSUE_CORRELATION_DATA_NOT_SUPPORTED, field);
            }
        }

        return;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Nothing to do with activities and transitions.
        return;
    }

}
