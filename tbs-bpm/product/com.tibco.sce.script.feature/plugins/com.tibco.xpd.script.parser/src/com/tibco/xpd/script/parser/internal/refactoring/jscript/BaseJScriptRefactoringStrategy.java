/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import com.tibco.xpd.script.parser.internal.refactoring.AbstractRefactoringStrategy;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactor;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactorFactory;
import com.tibco.xpd.script.parser.internal.refactoring.IRefactor;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;

/**
 * 
 * @author mtorres
 *
 */
public abstract class BaseJScriptRefactoringStrategy extends
        AbstractRefactoringStrategy {

    private IExpressionRefactorFactory expressionRefactorFactory = null;

    private IExpressionRefactor identifierExpressionRefactor = null;

    private IExpressionRefactor conditionalExpressionRefactor = null;

    private IExpressionRefactor methodCallExpressionRefactor = null;

    private IExpressionRefactor expressionRefactor = null;

    private IExpressionRefactor variableDeclarationRefactor = null;

    public IExpressionRefactorFactory getExpresionRefactorFactory() {
        if (expressionRefactorFactory == null) {
            expressionRefactorFactory = new JSExpressionRefactorFactory();
        }
        return expressionRefactorFactory;
    }

    public IExpressionFactory getExpresionFactory() {
        return new AntlrRefactoringExpressionFactory();
    }

    @Override
    protected IExpressionRefactor getASTTreeRefactor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IExpressionRefactor getConditionalExpressionRefactor() {
        if (conditionalExpressionRefactor == null) {
            conditionalExpressionRefactor =
                    new JScriptConditionalExprRefactor();
        }
        return conditionalExpressionRefactor;
    }

    @Override
    protected IExpressionRefactor getExpressionRefactor() {
        if (expressionRefactor == null) {
            expressionRefactor = new JScriptExpressionRefactor();
        }
        return expressionRefactor;
    }

    @Override
    protected IExpressionRefactor getMethodCallRefactor() {
        if (methodCallExpressionRefactor == null) {
            methodCallExpressionRefactor = new JScriptMethodCallRefactor();
        }
        return methodCallExpressionRefactor;
    }

    @Override
    protected IExpressionRefactor getMethodDefinitionRefactor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IExpressionRefactor getNewExpressionRefactor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IRefactor getStatementRefactor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IExpressionRefactor getUndefinedVariableUseRefactor() {
        if (identifierExpressionRefactor == null) {
            identifierExpressionRefactor =
                    new JScriptIdentifierExpressionRefactor();
        }
        return identifierExpressionRefactor;
    }

    @Override
    protected IExpressionRefactor getVariableDeclarationRefactor() {
        if (variableDeclarationRefactor == null) {
            variableDeclarationRefactor =
                    new JScriptVariableDeclarationRefactor();
        }
        return variableDeclarationRefactor;
    }

}
