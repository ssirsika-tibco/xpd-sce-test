package com.tibco.bx.validation.internal.validator;

import com.tibco.bx.validation.validator.N2NewExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.jscript.JSExpressionValidatorFactory;

public class N2JSExpressionValidatorFactory extends
        JSExpressionValidatorFactory {

    @Override
    protected IExpressionValidator getScriptDotExpressionValidator() {
        return new N2JScriptDotExpressionValidator();
    }

    @Override
    protected IExpressionValidator getScriptArrayIndexExpressionValidator() {
        return new N2JScriptArrayIndexExpressionValidator();
    }

    @Override
    protected IExpressionValidator getScriptNewExpressionValidator() {
        return new N2NewExpressionValidator();
    }

    @Override
    protected IExpressionValidator getScriptAssignmentExpressionValidator() {
        return new N2JScriptAssignmentExpressionValidator();
    }

    /**
     * @see com.tibco.xpd.script.parser.validator.jscript.JSExpressionValidatorFactory#getScriptNumericExpressionValidator()
     * 
     * @return
     */
    @Override
    protected IExpressionValidator getScriptNumericExpressionValidator() {
        return new N2JScriptNumericExpressionValidator();
    }

    @Override
    protected IExpressionValidator getScriptMethodCallValidator() {

        return new N2JScriptMethodCallValidator();
    }
}
