/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.expr.IExpr;

/**
 * @author mtorres
 * 
 * Implementation of IExpr interface
 * 
 */
public class AntlrRefactoringExprImpl implements IExpr{

    private AST expr;
    private Token token;
    
    public AntlrRefactoringExprImpl(AST expr, Token token) {
        this.expr = expr;
        this.token = token;
    }
    
    public AST getExpr() {
        return expr;
    }

    public Token getToken() {
        return token;
    }

}
