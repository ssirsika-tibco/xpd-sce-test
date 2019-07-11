/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collections;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Process;

/**
 * ACE specific general high level rules.
 * 
 * <li>Process names must not exceed 100 characters.</li>
 *
 * @author aallway
 * @since 10 Jul 2019
 */
public class AceProcessRules extends ProcessValidationRule {

    private static final String ACE_ISSUE_PROCESS_NAME_TOO_LONG = "ace.process.name.too.long"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     *
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (process.getName() != null && process.getName().length() > 100) {
            addIssue(ACE_ISSUE_PROCESS_NAME_TOO_LONG, process, Collections.singletonList("100")); //$NON-NLS-1$
        }

    }

}
