/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.Token;
import antlr.collections.AST;
/**
 * 
 * Expression validator for the validation of variable declarations
 * 
 * @author mtorres
 */
public class JScriptVariableDeclarationValidator extends
        AbstractExpressionValidator {

    @Override
	public void validate(AST variableDefAST, Token token) {
        if (variableDefAST != null) {
            String variableName =
                    getVariableName(variableDefAST.getFirstChild());
            if (variableName != null) {
                validateVarName(variableName, token);
            } else {
                logUnexpectedExpressionValidatorProblem();
            }

			AST lhsVariableExpression = getLHSVariableExpression(variableDefAST.getFirstChild());
            
			if (lhsVariableExpression != null)
			{
				AST equalOperatorExp = lhsVariableExpression.getNextSibling();
				if (equalOperatorExp != null && JScriptTokenTypes.ASSIGN == equalOperatorExp.getType())
				{
					AST rhsExp = equalOperatorExp.getFirstChild();
					
					if (rhsExp != null && JScriptTokenTypes.EXPR == rhsExp.getType())
					{
						AST rhsVaribleExpression = rhsExp.getFirstChild();
						validateVarDeclarationAssignment(rhsVaribleExpression, token);
					}

				}
			}
            
        } else {
            logUnexpectedExpressionValidatorProblem();
        }
    }
    
	/**
	 * Function to validate the variable declaration assignment.
	 * 
	 * <p>
	 * Chaitanya : As part of ACE-7866 we introduced new additional validation for varible declartion assignment of
	 * 'bpmScripts'.
	 * </p>
	 * <p>
	 * i.e. which basically prevented the assignment of below 'bpmScripts' expressions for varible declaration.
	 * </p>
	 * 
	 * <ul>
	 * <li>var [variable_name] = 'bpmScripts';</li>
	 * <li>var [variable_name] = 'bpmScripts.[ProjectName]'</li>
	 * </ul>
	 * 
	 * @param rhsExpression
	 * @param token
	 */
	protected void validateVarDeclarationAssignment(AST rhsExpression, Token token)
	{
		// Do Nothing.
	}

	/**
	 * Retrieves the left-hand side (LHS) variable expression from the given AST node representing a variable
	 * definition.
	 *
	 * @param variableDefAST
	 *            The abstract syntax tree (AST) node representing the variable definition.
	 * @return The AST node representing the LHS variable expression.
	 */
	private AST getLHSVariableExpression(AST variableDefAST)
	{
		if (variableDefAST != null)
		{
			return variableDefAST.getNextSibling();
		}
		return null;
	}

	@Override
    public IValidateResult evaluate(IExpr expression) {
        return super.evaluate(expression);
    }

    private String getVariableName(AST typeAST) {
        if (typeAST != null) {
            AST varNameAST = typeAST.getNextSibling();
            if (varNameAST != null) {
                return varNameAST.getText();
            }
        }
        return null;
    }

    private void validateVarName(String varName, Token token) {
        // Validate keywords
        List<String> symbolTableKeyWords =
                getSymbolTableKeyWords(getInfoObject());
        if (symbolTableKeyWords != null
                && symbolTableKeyWords.contains(varName)) {
            String errorMessage =
                    Messages.ExpressionValidator_VarNameCannotBeAKeyword;
            List<String> additionalInfo = new ArrayList<String>();
            additionalInfo.add(varName);
            addErrorMessage(token, errorMessage, additionalInfo);
            return;
        }
        // Validate future keywords
        List<String> symbolFutureTableKeyWords =
                getSymbolTableFutureKeyWords(getInfoObject());
        if (symbolFutureTableKeyWords != null
                && symbolFutureTableKeyWords.contains(varName)) {
            String errorMessage =
                    Messages.ExpressionValidator_VariableNameCannotBeAFutureKeyWord;
            List<String> additionalInfo = new ArrayList<String>();
            additionalInfo.add(varName);
            addErrorMessage(token, errorMessage, additionalInfo);
            return;
        }
        // Validate supported classes
        List<JsClass> supportedJsClasses =
                getSupportedJsClasses(getInfoObject());
        if (supportedJsClasses != null && !supportedJsClasses.isEmpty()) {
            for (JsClass jsClass : supportedJsClasses) {
                if (jsClass != null && jsClass.getName() != null
                        && jsClass.getName().equals(varName)) {
                    String errorMessage =
                            Messages.ExpressionValidator_VariableNameCannotBeAClassName;
                    List<String> additionalInfo = new ArrayList<String>();
                    additionalInfo.add(varName);
                    addErrorMessage(token, errorMessage, additionalInfo);
                    return;
                }
            }
        }
        // Validate scriptRelevantdata
        Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                getSupportedScriptRelevantDataMap(getInfoObject());
        if (supportedScriptRelevantDataMap != null && !supportedScriptRelevantDataMap.isEmpty()) {
            IScriptRelevantData scriptRelevantData = supportedScriptRelevantDataMap.get(varName);
            if(scriptRelevantData != null){
                String errorMessage =
                    Messages.ExpressionValidator_VariableNameCannotBeAFieldName;
            List<String> additionalInfo = new ArrayList<String>();
            additionalInfo.add(varName);
            addErrorMessage(token, errorMessage, additionalInfo);
            return;
            }
        }

    }

}
