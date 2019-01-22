/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.validator;

import java.util.ArrayList;
import java.util.List;

import antlr.LLkParser;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.xpd.process.js.parser.validator.IProcessValidator;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCExpressionValidator;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCProcessValidationStrategy;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCScriptParser;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCValidator;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLValidationStrategy implements IJCCProcessValidationStrategy {

    private String destinationName = null;

    private String scriptType = null;

    private String expectedResultDataType = null;

    private String scriptSubContext = null;

    protected List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

    protected List<ErrorMessage> warningList = new ArrayList<ErrorMessage>();

    private IJCCScriptParser parser = null;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public void setExpectedResultantDataType(String dataType) {
        this.expectedResultDataType = dataType;
    }

    public String getExpectedResultantDataType() {
        return this.expectedResultDataType;
    }

    public String getScriptSubContext() {
        return scriptSubContext;
    }

    public void setScriptSubContext(String scriptSubContext) {
        this.scriptSubContext = scriptSubContext;
    }

    protected void addErrorMessage(List<ErrorMessage> errorMessageList) {
        List<ErrorMessage> comprehensiveErrorList = getErrorList();
        if (errorMessageList != null) {
            for (ErrorMessage message : errorMessageList) {
                if (!comprehensiveErrorList.contains(message)) {
                    comprehensiveErrorList.add(message);
                }
            }
        }
    }

    protected void addWarningMessage(List<ErrorMessage> warningMessageList) {
        List<ErrorMessage> comprehensiveWarningList = getWarningList();
        if (warningMessageList != null) {
            for (ErrorMessage message : warningMessageList) {
                if (!comprehensiveWarningList.contains(message)) {
                    comprehensiveWarningList.add(message);
                }
            }
        }
    }

    protected void reportErrorsAndWarnings(IJCCValidator validator) {
        if (validator != null) {
            List<ErrorMessage> errorMessageList =
                    validator.getErrorMessageList();
            if (errorMessageList != null && errorMessageList.size() > 0) {
                addErrorMessage(errorMessageList);
            }
            List<ErrorMessage> warningMessageList =
                    validator.getWarningMessageList();
            if (warningMessageList != null && warningMessageList.size() > 0) {
                addWarningMessage(warningMessageList);
            }
        }
    }

    public List<ErrorMessage> getErrorList() {
        return errorList;
    }

    public List<ErrorMessage> getWarningList() {
        return warningList;
    }

    public List<JsClass> getSupportedJsClasses() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setScriptParser(IJCCScriptParser parser) {
        this.parser = parser;
    }

    public IJCCScriptParser getScriptParser() {
        return this.parser;
    }

    public void validateExpression(SimpleNode simpleNode) {
        IJCCExpressionValidator exprValidator = getExpressionValidator();
        if (exprValidator != null) {
            initialiseExpressionValidator(exprValidator);
            exprValidator.validate(simpleNode);
            reportErrorsAndWarnings(exprValidator);
        }
    }

    private IJCCExpressionValidator exprValidator = null;

    public IJCCExpressionValidator getExpressionValidator() {
        if (exprValidator == null) {
            exprValidator = new RQLExpressionValidator();
        }
        return exprValidator;
    }

    protected void initialiseExpressionValidator(
            IJCCExpressionValidator expressionValidator) {
        if (expressionValidator != null) {
            initialiseValidator(expressionValidator);
            expressionValidator.setScriptParser(getScriptParser());
            expressionValidator.initialize();
        }
    }

    protected void initialiseValidator(IJCCValidator validator) {        
        if (validator instanceof IProcessValidator) {
            IProcessValidator pValidator = (IProcessValidator) validator;
            pValidator.setProcessDestination(getDestinationName());
            pValidator.setScriptType(getScriptType());
        }
    }

    // ===Do nothing with this methods====

    public void setScriptParser(LLkParser parser) {
        // Do nothing
    }

    public void validateASTTree(AST astTree, Token token) {
        // Do nothing

    }

    public void validateConditionalExpression(AST expressionAST, Token token) {
        // Do nothing

    }

    public void validateExpression(AST expressionAST, Token token) {
        // Do nothing

    }

    public void validateMethodCall(AST methodAST, Token token) {
        // Do nothing

    }

    public void validateMethodDeclaration(AST methodAST, Token token) {
        // Do nothing

    }

    public void validateNewExpression(AST expressionAST, Token token) {
        // Do nothing

    }

    public void validateStatement(AST statementAST, Token token) {
        // Do nothing

    }

    public void validateUndefinedVariableUse(AST expression, Token token) {
        // Do nothing
    }

    public void validateVariableDeclaration(AST varDefAST, Token token) {
        // Do nothing
    }

    public IVarNameResolver getVarNameResolver() {
        return null;
    }

    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        // Do nothing

    }

}
