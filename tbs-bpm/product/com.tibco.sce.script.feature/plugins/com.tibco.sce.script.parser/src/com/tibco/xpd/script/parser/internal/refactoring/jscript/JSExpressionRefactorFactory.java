/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.collections.AST;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.parser.Activator;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactor;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactorFactory;

/**
 * 
 * Factory class for JavaScript for the creation of Expression Refactors
 * 
 * @author mtorres
 */
public class JSExpressionRefactorFactory implements IExpressionRefactorFactory {

    private static final Logger LOG = Activator.getDefault().getLogger();

    @Override
    public IExpressionRefactor getExpressionRefactor(IExpr expr)
            throws UnsupportedOperationException {
        if (expr == null) {
            return null;
        }
        Object astExpr = expr.getExpr();
        if (astExpr instanceof AST) {
            AST ast = (AST) astExpr;
            int astType = ast.getType();
            switch (astType) {
            case JScriptTokenTypes.NUM_INT:
            case JScriptTokenTypes.NUM_FLOAT:
            case JScriptTokenTypes.NUM_LONG:
            case JScriptTokenTypes.NUM_DOUBLE:
            case JScriptTokenTypes.NUMBER:
                return getScriptNumericExpressionRefactor();
            case JScriptTokenTypes.EQUAL:
            case JScriptTokenTypes.NOT_EQUAL:
                return getScriptEqualityExpressionRefactor();
            case JScriptTokenTypes.DOT:
                return getScriptDotExpressionRefactor();
            case JScriptTokenTypes.LITERAL_for:
            case JScriptTokenTypes.LITERAL_while:
            case JScriptTokenTypes.LITERAL_do:
            case JScriptTokenTypes.LITERAL_break:
            case JScriptTokenTypes.LITERAL_continue:
            case JScriptTokenTypes.LITERAL_switch:
            case JScriptTokenTypes.LITERAL_throw:
            case JScriptTokenTypes.LITERAL_case:
            case JScriptTokenTypes.LITERAL_default:
            case JScriptTokenTypes.LITERAL_try:
            case JScriptTokenTypes.LITERAL_finally:
            case JScriptTokenTypes.LITERAL_catch:
            case JScriptTokenTypes.STRING_LITERAL:
            case JScriptTokenTypes.LITERAL_true:
            case JScriptTokenTypes.LITERAL_false:
            case JScriptTokenTypes.LITERAL_null:
            case JScriptTokenTypes.LITERAL_class:
            case JScriptTokenTypes.LITERAL_return:
            case JScriptTokenTypes.LITERAL_var:
                return getScriptLiteralExpressionRefactor();
            case JScriptTokenTypes.LOR:
            case JScriptTokenTypes.LAND:
                return getScriptLogicalExpressionRefactor();
            case JScriptTokenTypes.METHOD_CALL:
                return getScriptMethodCallRefactor();
            case JScriptTokenTypes.INDEX_OP:
                return getScriptArrayIndexExpressionRefactor();
            case JScriptTokenTypes.IDENT:
                return getScriptIdentifierExpressionRefactor();
            case JScriptTokenTypes.PLUS:
            case JScriptTokenTypes.PLUS_ASSIGN:
                return getScriptPlusExpressionRefactor();
            case JScriptTokenTypes.MINUS:
            case JScriptTokenTypes.MINUS_ASSIGN:
                return getScriptMinusExpressionRefactor();
            case JScriptTokenTypes.STAR:
            case JScriptTokenTypes.STAR_ASSIGN:
                return getScriptMultiplicationExpressionRefactor();
            case JScriptTokenTypes.DIV:
            case JScriptTokenTypes.DIV_ASSIGN:
                return getScriptDivisionExpressionRefactor();
            case JScriptTokenTypes.BXOR:
            case JScriptTokenTypes.BXOR_ASSIGN:
                return getScriptExponentiationExpressionRefactor();
            case JScriptTokenTypes.EXPR:
                return getScriptExpressionRefactor();
            case JScriptTokenTypes.GT:
            case JScriptTokenTypes.GE:
            case JScriptTokenTypes.LT:
            case JScriptTokenTypes.LE:
                return getScriptComparisonExpressionRefactor();
            case JScriptTokenTypes.ARRAY_DECLARATOR:
                return getScriptArrayDeclaratorExpressionRefactor();
            case JScriptTokenTypes.ASSIGN:
                return getScriptAssignmentExpressionRefactor();
            case JScriptTokenTypes.UNARY_MINUS:
            case JScriptTokenTypes.UNARY_PLUS:
            case JScriptTokenTypes.POST_DEC:
            case JScriptTokenTypes.POST_INC:
            case JScriptTokenTypes.INC:
            case JScriptTokenTypes.DEC:
            case JScriptTokenTypes.LNOT:
                return getScriptUnaryExpressionRefactor();
            case JScriptTokenTypes.SR: // >>
            case JScriptTokenTypes.SR_ASSIGN: // >>=
            case JScriptTokenTypes.BSR: // >>>
            case JScriptTokenTypes.BSR_ASSIGN: // >>>=
            case JScriptTokenTypes.SL: // <<
            case JScriptTokenTypes.SL_ASSIGN: // <<=
            case JScriptTokenTypes.BOR_ASSIGN: // <<
            case JScriptTokenTypes.BAND_ASSIGN: // <<=
            case JScriptTokenTypes.BOR: // |
            case JScriptTokenTypes.BAND: // &
            case JScriptTokenTypes.BNOT: // ~
                return getScriptBitwiseExpressionRefactor();
            case JScriptTokenTypes.QUESTION: // a==null?23:45
                return getScriptQuestionExpressionRefactor();
            case JScriptTokenTypes.MOD: // %
            case JScriptTokenTypes.MOD_ASSIGN: // %=
                return getScriptModExpressionRefactor();
            case JScriptTokenTypes.LITERAL_new:
                return getScriptNewExpressionRefactor();
            default:
                break;
            }
        }
        return null;
    }

    protected IExpressionRefactor getScriptNumericExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptEqualityExpressionRefactor() {
        return new JScriptEqualityExpressionRefactor();
    }

    protected IExpressionRefactor getScriptDotExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptLiteralExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptNewExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptLogicalExpressionRefactor() {
        return new JScriptLogicalExpressionRefactor();
    }

    protected IExpressionRefactor getScriptMethodCallRefactor() {
        return new JScriptMethodCallRefactor();
    }

    protected IExpressionRefactor getScriptArrayIndexExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptIdentifierExpressionRefactor() {
        return new JScriptIdentifierExpressionRefactor();
    }

    protected IExpressionRefactor getScriptPlusExpressionRefactor() {
        return new JScriptPlusExpressionRefactor();
    }

    protected IExpressionRefactor getScriptMinusExpressionRefactor() {
        return new JScriptMinusExpressionRefactor();
    }

    protected IExpressionRefactor getScriptMultiplicationExpressionRefactor() {
        return new JScriptMultiplicationExpressionRefactor();
    }

    protected IExpressionRefactor getScriptDivisionExpressionRefactor() {
        return new JScriptDivisionExpressionRefactor();
    }

    protected IExpressionRefactor getScriptExponentiationExpressionRefactor() {
        return new JScriptExponentiationExpressionRefactor();
    }

    protected IExpressionRefactor getScriptExpressionRefactor() {
        return new JScriptExpressionRefactor();
    }

    protected IExpressionRefactor getScriptComparisonExpressionRefactor() {
        return new JScriptComparisonExpressionRefactor();
    }

    protected IExpressionRefactor getScriptArrayDeclaratorExpressionRefactor() {
        return null;
    }

    protected IExpressionRefactor getScriptAssignmentExpressionRefactor() {
        return new JScriptAssignmentExpressionRefactor();
    }

    protected IExpressionRefactor getScriptUnaryExpressionRefactor() {
        return new JScriptUnaryExpressionRefactor();
    }

    protected IExpressionRefactor getScriptBitwiseExpressionRefactor() {
        return new JScriptBitwiseExpressionRefactor();
    }

    protected IExpressionRefactor getScriptQuestionExpressionRefactor() {
        return new JScriptQuestionExpressionRefactor();
    }

    protected IExpressionRefactor getScriptModExpressionRefactor() {
        return new JScriptModExpressionRefactor();
    }

}
