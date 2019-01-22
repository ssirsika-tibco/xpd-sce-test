/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator.jscript;

import java.util.ArrayList;
import java.util.List;

import antlr.collections.AST;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.parser.Activator;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptArrayDeclaratorExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptArrayIndexExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptBitwiseExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptComparisonExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptDivisionExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptEmptyStatValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptEqualityExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExponentiationExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptIdentifierExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptLiteralExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptLogicalExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptMinusExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptModExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptMultiplicationExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptNewExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptNumericExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptPlusExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptQuestionExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptUnaryExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidatorFactory;

/**
 * 
 * Factory class for JavaScript for the creation of Expression Validators
 * 
 * @author mtorres
 */
public class JSExpressionValidatorFactory implements
        IExpressionValidatorFactory {

    private static final Logger LOG = Activator.getDefault().getLogger();

    @Override
    public IExpressionValidator getExpressionValidator(IExpr expr)
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
                return getScriptNumericExpressionValidator();
            case JScriptTokenTypes.EQUAL:
            case JScriptTokenTypes.NOT_EQUAL:
            case JScriptTokenTypes.NOT_EQUAL2:
                return getScriptEqualityExpressionValidator();
            case JScriptTokenTypes.DOT:
                return getScriptDotExpressionValidator();
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
                return getScriptLiteralExpressionValidator();
            case JScriptTokenTypes.LOR:
            case JScriptTokenTypes.LAND:
                return getScriptLogicalExpressionValidator();
            case JScriptTokenTypes.METHOD_CALL:
                return getScriptMethodCallValidator();
            case JScriptTokenTypes.INDEX_OP:
                return getScriptArrayIndexExpressionValidator();
            case JScriptTokenTypes.IDENT:
                return getScriptIdentifierExpressionValidator();
            case JScriptTokenTypes.PLUS:
            case JScriptTokenTypes.PLUS_ASSIGN:
                return getScriptPlusExpressionValidator();
            case JScriptTokenTypes.MINUS:
            case JScriptTokenTypes.MINUS_ASSIGN:
                return getScriptMinusExpressionValidator();
            case JScriptTokenTypes.STAR:
            case JScriptTokenTypes.STAR_ASSIGN:
                return getScriptMultiplicationExpressionValidator();
            case JScriptTokenTypes.DIV:
            case JScriptTokenTypes.DIV_ASSIGN:
                return getScriptDivisionExpressionValidator();
            case JScriptTokenTypes.BXOR:
            case JScriptTokenTypes.BXOR_ASSIGN:
                return getScriptExponentiationExpressionValidator();
            case JScriptTokenTypes.EXPR:
                return getScriptExpressionValidator();
            case JScriptTokenTypes.GT:
            case JScriptTokenTypes.GE:
            case JScriptTokenTypes.LT:
            case JScriptTokenTypes.LE:
                return getScriptComparisonExpressionValidator();
            case JScriptTokenTypes.ARRAY_DECLARATOR:
                return getScriptArrayDeclaratorExpressionValidator();
            case JScriptTokenTypes.ASSIGN:
                return getScriptAssignmentExpressionValidator();
            case JScriptTokenTypes.UNARY_MINUS:
            case JScriptTokenTypes.UNARY_PLUS:
            case JScriptTokenTypes.POST_DEC:
            case JScriptTokenTypes.POST_INC:
            case JScriptTokenTypes.INC:
            case JScriptTokenTypes.DEC:
            case JScriptTokenTypes.LNOT:
                return getScriptUnaryExpressionValidator();
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
                return getScriptBitwiseExpressionValidator();
            case JScriptTokenTypes.QUESTION: // a==null?23:45
                return getScriptQuestionExpressionValidator();
            case JScriptTokenTypes.MOD: // %
            case JScriptTokenTypes.MOD_ASSIGN: // %=
                return getScriptModExpressionValidator();
            case JScriptTokenTypes.LITERAL_new:
                return getScriptNewExpressionValidator();
            case JScriptTokenTypes.VARIABLE_DEF:
                return getScriptVariableDeclarationValidator();
            case JScriptTokenTypes.EMPTY_STAT:
            case JScriptTokenTypes.LITERAL_if:
                return getScriptVariableDeclarationValidator();
            default:
                break;
            }
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(ast.toString());
            String warning =
                    String.format(Messages.JSExpressionValidatorFactory_Passed_Operator_is_not_supported,
                            additionalAttributes.toArray());
            LOG.warn(warning);
        }
        return null;
    }

    protected IExpressionValidator getScriptNumericExpressionValidator() {
        return new JScriptNumericExpressionValidator();
    }

    protected IExpressionValidator getScriptEqualityExpressionValidator() {
        return new JScriptEqualityExpressionValidator();
    }

    protected IExpressionValidator getScriptDotExpressionValidator() {
        return new JScriptDotExpressionValidator();
    }

    protected IExpressionValidator getScriptLiteralExpressionValidator() {
        return new JScriptLiteralExpressionValidator();
    }

    protected IExpressionValidator getScriptNewExpressionValidator() {
        return new JScriptNewExpressionValidator();
    }

    protected IExpressionValidator getScriptVariableDeclarationValidator() {
        return new JScriptVariableDeclarationValidator();
    }

    protected IExpressionValidator getScriptEmptyStatValidator() {
        return new JScriptEmptyStatValidator();
    }

    protected IExpressionValidator getScriptLogicalExpressionValidator() {
        return new JScriptLogicalExpressionValidator();
    }

    protected IExpressionValidator getScriptMethodCallValidator() {
        return new JScriptMethodCallValidator();
    }

    protected IExpressionValidator getScriptArrayIndexExpressionValidator() {
        return new JScriptArrayIndexExpressionValidator();
    }

    protected IExpressionValidator getScriptIdentifierExpressionValidator() {
        return new JScriptIdentifierExpressionValidator();
    }

    protected IExpressionValidator getScriptPlusExpressionValidator() {
        return new JScriptPlusExpressionValidator();
    }

    protected IExpressionValidator getScriptMinusExpressionValidator() {
        return new JScriptMinusExpressionValidator();
    }

    protected IExpressionValidator getScriptMultiplicationExpressionValidator() {
        return new JScriptMultiplicationExpressionValidator();
    }

    protected IExpressionValidator getScriptDivisionExpressionValidator() {
        return new JScriptDivisionExpressionValidator();
    }

    protected IExpressionValidator getScriptExponentiationExpressionValidator() {
        return new JScriptExponentiationExpressionValidator();
    }

    protected IExpressionValidator getScriptExpressionValidator() {
        return new JScriptExpressionValidator();
    }

    protected IExpressionValidator getScriptComparisonExpressionValidator() {
        return new JScriptComparisonExpressionValidator();
    }

    protected IExpressionValidator getScriptArrayDeclaratorExpressionValidator() {
        return new JScriptArrayDeclaratorExpressionValidator();
    }

    protected IExpressionValidator getScriptAssignmentExpressionValidator() {
        return new JScriptAssignmentExpressionValidator();
    }

    protected IExpressionValidator getScriptUnaryExpressionValidator() {
        return new JScriptUnaryExpressionValidator();
    }

    protected IExpressionValidator getScriptBitwiseExpressionValidator() {
        return new JScriptBitwiseExpressionValidator();
    }

    protected IExpressionValidator getScriptQuestionExpressionValidator() {
        return new JScriptQuestionExpressionValidator();
    }

    protected IExpressionValidator getScriptModExpressionValidator() {
        return new JScriptModExpressionValidator();
    }

}
