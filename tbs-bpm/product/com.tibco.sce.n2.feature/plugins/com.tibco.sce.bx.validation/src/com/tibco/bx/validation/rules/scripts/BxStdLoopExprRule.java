/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractStdLoopExprRule;

/**
 * 
 * <p>
 * <i>Created: 9 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class BxStdLoopExprRule extends AbstractStdLoopExprRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.validateStdLoopExpr"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.validateStdLoopExpr"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
