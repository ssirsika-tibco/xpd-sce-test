package com.tibco.xpd.process.js.parser.validator.jscript;

import com.tibco.xpd.process.js.parser.validator.AbstractProcessValidationStrategy;
import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptConditionalExprValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptMethodDefinitionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptNewExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptUndefinedVarUseValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.jscript.JScriptMethodCallValidator;

public class JScriptValidationStrategy extends
        AbstractProcessValidationStrategy {
    
    private IExpressionValidator newExprValidator = null;

    @Override
    protected IExpressionValidator getNewExpressionValidator() {
        if (newExprValidator == null) {
            newExprValidator = new JScriptNewExpressionValidator();
        }
        return newExprValidator;
    }

    private IExpressionValidator conditionalExprValidator = null;

    @Override
    protected IExpressionValidator getConditionalExpressionValidator() {
        if (conditionalExprValidator == null) {
            conditionalExprValidator = new JScriptConditionalExprValidator();
        }
        return conditionalExprValidator;
    }

    private IExpressionValidator methodCallValidator = null;

    @Override
    protected IExpressionValidator getMethodCallValidator() {
        if (methodCallValidator == null) {
            methodCallValidator = new JScriptMethodCallValidator();
        }
        return methodCallValidator;
    }

    private IExpressionValidator methodDefValidator = null;

    @Override
    protected IExpressionValidator getMethodDefinitionValidator() {
        if (methodDefValidator == null) {
            methodDefValidator = new JScriptMethodDefinitionValidator();
        }
        return methodDefValidator;
    }

    private IExpressionValidator undefinedVarUseValidator = null;

    @Override
    protected IExpressionValidator getUndefinedVariableUseValidator() {
        if (undefinedVarUseValidator == null) {
            undefinedVarUseValidator = new JScriptUndefinedVarUseValidator(); 
        }
        return undefinedVarUseValidator;
    }

    private IExpressionValidator varDeclaratorValidator = null;

    @Override
    protected IExpressionValidator getVariableDeclarationValidator() {
        if (varDeclaratorValidator == null) {
            varDeclaratorValidator = new JScriptVariableDeclarationValidator();
        }
        return varDeclaratorValidator;
    }

    IExpressionValidator expressionValidator = null;
    @Override
    protected IExpressionValidator getExpressionValidator() {
        if(expressionValidator == null){ 
            expressionValidator = new JScriptExpressionValidator();
        }
        return expressionValidator;
    }

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IValidator getStatementValidator() {
        return null;
    }
}
