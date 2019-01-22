/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

/**
 * 
 * <p>
 * <i>Created: 9 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class JsMILoopExprRule extends AbstractMILoopExprRule {

    /** The issue id. */
    private static final String ERROR_ID = "js.validateMILoopExpr"; //$NON-NLS-1$

    private static final String WARNING_ID = "js.warning.validateMILoopExpr"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
