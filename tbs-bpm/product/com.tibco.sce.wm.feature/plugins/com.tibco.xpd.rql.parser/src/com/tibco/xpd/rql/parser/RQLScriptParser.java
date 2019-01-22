/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import com.tibco.n2.de.rql.parser.ParseException;
import com.tibco.n2.de.rql.parser.RqlParser;
import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.n2.de.rql.parser.TokenMgrError;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCProcessValidationStrategy;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCScriptParser;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCSymbolTable;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCValidationStrategy;
import com.tibco.xpd.rql.parser.validator.RQLSymbolTable;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class RQLScriptParser implements IJCCScriptParser {

    private RqlParser queryParser = null;

    private SimpleNode parseResult;

    private IJCCSymbolTable symbolTable = null;

    private List<IValidationStrategy> validationStrategyList = null;
    
    private String query;

    public RQLScriptParser(String query) {
        this.query = query;
        StringReader sr = new StringReader(query);
        queryParser = new RqlParser(sr);
    }

    public void startValidation() {
        if (parseResult != null) {
            validateExpression(parseResult);
        }
    }

    public IJCCSymbolTable getSymbolTable() {
        if (this.symbolTable == null) {
            this.symbolTable = new RQLSymbolTable();
        }
        return this.symbolTable;
    }

    public void setSymbolTable(IJCCSymbolTable sTable) {
        this.symbolTable = sTable;
    }

    public void setValidationStrategyList(
            List<IValidationStrategy> validationStrategyList) {
        this.validationStrategyList = validationStrategyList;
        initialiseValidationStrategy();
    }

    @SuppressWarnings("unchecked")
    public List<IValidationStrategy> getValidatorStrategyList() {
        if (validationStrategyList == null) {
            validationStrategyList = Collections.EMPTY_LIST;
        }
        return validationStrategyList;
    }

    private IVarNameResolver varNameResolver = null;

    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
        initialiseValidationStrategy();
    }

    public IVarNameResolver getVarNameResolver() {
        if (varNameResolver == null) {
            varNameResolver = new DefaultVarNameResolver();
        }
        return varNameResolver;
    }

    private void initialiseValidationStrategy() {
        if (validationStrategyList != null) {
            for (IValidationStrategy validationStrategy : validationStrategyList) {
                if (validationStrategy instanceof IJCCProcessValidationStrategy) {
                    IJCCProcessValidationStrategy rqlValidationStrategy =
                            (IJCCProcessValidationStrategy) validationStrategy;
                    rqlValidationStrategy.setScriptParser(this);
                    if (varNameResolver != null) {
                        rqlValidationStrategy
                                .setVarNameResolver(varNameResolver);
                    }
                }
            }
        }
    }

    private List<String> supportedClassList = Collections.EMPTY_LIST;

    public void setSupportedClasses(List<String> classList) {
        this.supportedClassList = classList;
    }

    public void parseQuery() throws ParseException, TokenMgrError {
        this.parseResult = queryParser.parse();
    }

    public SimpleNode getParseResult() {
        return parseResult;
    }

    public void validateExpression(SimpleNode node) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IJCCValidationStrategy) {
                IJCCValidationStrategy jccStrategy =
                        (IJCCValidationStrategy) strategy;
                 jccStrategy.validateExpression(node);
            }
        }
    }
    
    public String getQuery() {
        return query;
    }

}