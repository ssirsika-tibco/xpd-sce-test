/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Expression validator for the validation of variable declarations
 * 
 * @author mtorres
 */
public class JScriptVariableDeclarationValidator extends
        AbstractExpressionValidator {

    public void validate(AST variableDefAST, Token token) {
        if (variableDefAST != null) {
            String variableName =
                    getVariableName(variableDefAST.getFirstChild());
            if (variableName != null) {
                validateVarName(variableName, token);
            } else {
                logUnexpectedExpressionValidatorProblem();
            }
        } else {
            logUnexpectedExpressionValidatorProblem();
        }
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
