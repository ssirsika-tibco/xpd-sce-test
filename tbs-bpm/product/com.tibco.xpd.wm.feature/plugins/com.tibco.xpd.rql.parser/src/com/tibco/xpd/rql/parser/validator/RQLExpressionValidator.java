/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rql.parser.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.xpd.process.js.parser.validator.IProcessValidator;
import com.tibco.xpd.rql.parser.eval.QueryVisitor;
import com.tibco.xpd.rql.parser.eval.ValidationContext;
import com.tibco.xpd.rql.parser.jccvalidator.AbstractJCCValidator;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCScriptParser;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCSymbolTable;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * 
 * Class for the validation of the RQL expresssion
 * 
 * @author Miguel Torres
 * 
 */
public class RQLExpressionValidator extends AbstractJCCValidator implements
        IProcessValidator {


    private String processDestination;

    private String scriptType;
    
    private IJCCScriptParser scriptParser;
    
    private ValidationContext validationContext;

    public void setProcessDestination(String processDestination) {
        this.processDestination = processDestination;
    }

    protected String getProcessDestination() {
        return this.processDestination;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    protected String getScriptType() {
        return this.scriptType;
    }
    
    private String expectedResultantDataType = null;

    public void setExpectedResultantDataType(String dataType) {
        this.expectedResultantDataType = dataType;
    }

    public String getExpectedResultantDataType() {
        return this.expectedResultantDataType;
    }

    public void validate(AST expression, Token token) {
        // Do nothing
    }

    public void validate(SimpleNode node) {
        if (node != null) {
            node.jjtAccept(QueryVisitor.getInstance(), validationContext);
        }
    }

    public List<JsClass> getSupportedJsClasses() {
        return RQLParserUtil.getSupportedJsClasses(getProcessDestination(),
                getScriptType());
    }
    
    /**
     * Override this method if a filter to the supported script relevant data
     * classes is to be made, by default all the scriptRelevantDataMap is
     * returned
     * 
     * @return Map<String, List<IScriptRelevantData>> all the supported script
     *         relevant data classes
     */
    protected Map<String, List<IScriptRelevantData>> getSupportedScriptRelevantDataMap() {
        Map<String, List<IScriptRelevantData>> supportedScriptRelevantDataMap =
                new HashMap<String, List<IScriptRelevantData>>();
        IJCCSymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, List<IScriptRelevantData>> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();

            supportedScriptRelevantDataMap.putAll(scriptRelevantDataMap);
        }
        return supportedScriptRelevantDataMap;
    }
    

    protected IJCCSymbolTable getSymbolTable() {
        if (this.scriptParser != null) {
            return this.scriptParser.getSymbolTable();
        }
        return null;
    }

    public void setScriptParser(IJCCScriptParser scriptParser) {
        this.scriptParser = scriptParser;
    }

    public IValidateResult evaluate(IExpr expresion) {
        // TODO Auto-generated method stub
        return null;
    }

    public void initialize() {
        // Create the context
        validationContext =
                new ValidationContext(scriptParser.getQuery(), this);
        validationContext
                .setSupportedScriptRelevantDataMap(getSupportedScriptRelevantDataMap());
    }

}
