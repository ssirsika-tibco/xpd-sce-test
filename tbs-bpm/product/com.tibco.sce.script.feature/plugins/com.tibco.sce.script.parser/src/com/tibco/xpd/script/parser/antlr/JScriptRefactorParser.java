/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.antlr;

import java.util.Collections;
import java.util.List;

import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.refactoring.IRefactoringStrategy;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringUtil;

/**
 * JScriptParser for the refactoring of elements
 * 
 * @author mtorres
 *
 */
public class JScriptRefactorParser extends JScriptParser{

    private List<IRefactoringStrategy> refactoringStrategyList = null;

    private RefactoringInfo refactoringInfo = null;
    
    public JScriptRefactorParser(TokenBuffer tokenBuf) {
        super(tokenBuf);
    }
    
    public JScriptRefactorParser(TokenStream lexer) {
        super(lexer);
    }
    
    @Override
    protected void validateUndefinedVariableUse(AST expression, Token token) {
        recordVariableUse(expression);
        RefactoringUtil.refactorUndefinedVariableUse(this, expression, token);
    }
    
    @Override
    protected void validateConditionalExpr(AST expressionAST, Token token) {
        RefactoringUtil.refactorConditionalExpr(this, expressionAST, token);
    }
    
    @Override
    protected void validateMethodCall(AST expressionAST, Token token) {
        RefactoringUtil.refactorMethodCallExpr(this, expressionAST, token);
    }
    
    @Override
    protected void validateExpression(AST expressionAST, Token token) {
        RefactoringUtil.refactorExpression(this, expressionAST, token);
    }
    
    @Override
    protected void validateVariableDeclaration(AST varDefAST, Token token) {
        RefactoringUtil.refactorVariableDeclaration(this, varDefAST, token);
    }
    
    
    public void setRefactoringStrategyList(
            List<IRefactoringStrategy> refactoringStrategyList) {
        this.refactoringStrategyList = refactoringStrategyList;
        initialiseRefactoringStrategy();
    }

    @SuppressWarnings("unchecked")
    public List<IRefactoringStrategy> getRefactoringStrategyList() {
        if (refactoringStrategyList == null) {
            refactoringStrategyList = Collections.EMPTY_LIST;
        }
        return refactoringStrategyList;
    }
    
    protected void initialiseRefactoringStrategy() {
        if (refactoringStrategyList != null) {
            for (IRefactoringStrategy refactoringStrategy : refactoringStrategyList) {
                refactoringStrategy.setScriptParser(this);
                refactoringStrategy.setRefactoringInfo(getRefactoringInfo());
                if (getVarNameResolver() != null) {
                    refactoringStrategy.setVarNameResolver(getVarNameResolver());
                }
            }
        }
    }
    
    public void setRefactoringInfo(RefactoringInfo refactoringInfo) {
        this.refactoringInfo = refactoringInfo;
    }
    
    public RefactoringInfo getRefactoringInfo() {
        return refactoringInfo;
    }
    
    protected void addLocalVariable(AST variableDeclaration, Token token) {
        RefactoringUtil.addLocalVariable(variableDeclaration, this, token);
    }

}
