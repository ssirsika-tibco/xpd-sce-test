/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.List;

import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * 
 * @author mtorres
 *
 */
public interface IJCCValidationStrategy extends IValidationStrategy {
    /**
     * 
     * @param expressionAST
     * @param token
     */
    void validateExpression(SimpleNode simpleNode);
    /**
     * 
     * @param parser
     */
    void setScriptParser(IJCCScriptParser parser);

    /**
     * 
     * @return List<ErrorMessage>
     */
    List<ErrorMessage> getErrorList();

    /**
     * 
     * @return List<ErrorMessage>
     */
    List<ErrorMessage> getWarningList();

    /**
     * 
     * @return List<JsClass>
     */
    List<JsClass> getSupportedJsClasses();
    
    /**
     * 
     * @return
     */
    IJCCExpressionValidator getExpressionValidator();

}
