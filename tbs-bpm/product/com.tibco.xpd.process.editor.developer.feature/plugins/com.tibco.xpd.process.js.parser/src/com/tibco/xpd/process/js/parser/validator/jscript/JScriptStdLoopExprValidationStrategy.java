/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.validator.jscript;

import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * <p>
 * <i>Created: 17 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class JScriptStdLoopExprValidationStrategy extends
        JScriptValidationStrategy {

    private IExpressionValidator booleanTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (booleanTreeValidator == null) {
            booleanTreeValidator = new ProcessJScriptBooleanASTTreeValidator();
        }
        return booleanTreeValidator;
    }

}
