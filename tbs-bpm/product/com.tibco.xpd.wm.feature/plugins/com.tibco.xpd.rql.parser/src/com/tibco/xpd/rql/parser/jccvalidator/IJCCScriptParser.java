/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.List;

import com.tibco.n2.de.rql.parser.ParseException;
import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.n2.de.rql.parser.TokenMgrError;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * 
 * @author Miguel Torres
 * 
 */
public interface IJCCScriptParser {

    public void startValidation();

    public IJCCSymbolTable getSymbolTable();

    public void setSymbolTable(IJCCSymbolTable sTable);

    public void setValidationStrategyList(
            List<IValidationStrategy> validationStrategyList);

    public List<IValidationStrategy> getValidatorStrategyList();

    public void setSupportedClasses(List<String> classList);

    public void parseQuery() throws ParseException, TokenMgrError;

    public SimpleNode getParseResult();
    
    public String getQuery(); 

}