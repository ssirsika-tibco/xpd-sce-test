/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.internal.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptNumericExpressionValidator;

/**
 * Validation rule to check maximum suggested number length. As numbers with
 * more than 17 significant digits will be truncated, a warning is put
 * beforehand to inform user.
 * 
 * @author rsawant
 * @since 21-Feb-2013
 */
@SuppressWarnings("restriction")
public class N2JScriptNumericExpressionValidator extends
        JScriptNumericExpressionValidator {

    /** Pattern to search for decimal points within the number. */
    static Pattern decPointPattern = Pattern.compile("\\."); //$NON-NLS-1$

    /** Pattern to search for leading and trailing zeros present in number */
    static Pattern zerosPattern = Pattern.compile("^0*|0*$"); //$NON-NLS-1$

    /** Numbers with more than 17 significant digits will be truncated. */
    static int MAX_NUM_LENGTH = 17;

    /**
     * Validate an numeric expression for more than 17 significant digits.
     * 
     * @param expression
     * @return
     */
    @Override
    public IValidateResult evaluate(IExpr expression) {

        if (expression != null) {
            Object expr = expression.getExpr();

            if (expr instanceof AST) {
                Matcher matacher = null;
                String tempStr = null;

                String expText = ((AST) expr).getText();
                matacher = decPointPattern.matcher(expText);
                tempStr = matacher.replaceAll(""); //$NON-NLS-1$
                matacher = zerosPattern.matcher(tempStr);
                tempStr = matacher.replaceAll(""); //$NON-NLS-1$

                if (tempStr.length() > MAX_NUM_LENGTH) {
                    List<String> tempList = new ArrayList<String>();
                    tempList.add(expText);

                    addWarningMessage((Token) expression.getToken(),
                            Messages.N2JScriptNumericExpressionValidator_MaxLeghtExceeded,
                            tempList);
                }
            }
        }
        return super.evaluate(expression);
    }
}
