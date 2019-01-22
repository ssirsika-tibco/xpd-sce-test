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
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
/**
 * 
 * Expression validator for the validation of undefined variables
 * 
 * @author mtorres
 */
public class JScriptUndefinedVarUseValidator extends AbstractExpressionValidator {

    public void validate(AST expression, Token token) {
        List<String> supportedClasses = getSupportedClassNames(getInfoObject());
        IVarNameResolver varNameResolver = getVarNameResolver();
        if (varNameResolver == null) {
            throw new IllegalStateException(
                    "Instance of IVarNameResolver has not been registered to IValidationStrategy");
        }
        List<String> variableNameList =
                varNameResolver.getVariableNameList(expression,
                        supportedClasses);
        if (variableNameList.size() < 1) {
            return;
        }
        for (String varName : variableNameList) {
            // if the variable is not defined, then isVarDefined reports an
            // error.
            isVarDefined(varName, token);
            if (doVariableNameCollide(varName)) {
                String errorMessage =
                        Messages.ExpressionValidator_VariableNameCannotBeAClassName;
                List<String> additionalInfo = new ArrayList<String>();
                additionalInfo.add(varName);
                addErrorMessage(token, errorMessage, additionalInfo);
            }
        }
    }
    
    private boolean doVariableNameCollide(String varName) {
        if (varName != null) {
            List<String> supportedClasses =
                    getSupportedClassNames(getInfoObject());
            Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                    getSupportedScriptRelevantDataMap(getInfoObject());
            if (supportedClasses != null
                    && supportedScriptRelevantDataMap != null) {
                if (supportedClasses.contains(varName)
                        && supportedScriptRelevantDataMap.get(varName) != null) {
                    return true;
                }
            }
        }
        return false;
    }

}
