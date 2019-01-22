/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractUserTaskSubmitScriptRule;

/**
 * 
 * <p>
 * <i>Created: 26 Jul 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class BxUserTaskSubmitScriptRule extends
        AbstractUserTaskSubmitScriptRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.error.userTaskSubmitScript"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.userTaskSubmitScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
