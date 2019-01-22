/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.validator.xpath;

import org.jaxen.expr.Expr;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.xpath.model.AbstractXPathMappingTypeResolver;
/**
 * 
 * 
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class XPathExpressionValidator extends
        AbstractXPathProcessExpressionValidator {
    
    public void validate(AST expressionAST, Token token) {
        Expr xpathExpression = getXPathExpression();
        if (xpathExpression != null) {
            // Start with a null context
            Expr contextExpr = null;
            // Validate the expression
            validateExpressionXPathExpression(xpathExpression,
                    contextExpr,
                    token, false);
            // Validate the expression against the mapped element type
            validateMappedTypesMatch(xpathExpression, token);
        }
    }
    
    /**
     * @see com.tibco.xpd.process.xpath.parser.validator.xpath.AbstractXPathExpressionValidator#getMappingTypeResolver()
     *
     * @return
     */
    @Override
    protected AbstractXPathMappingTypeResolver getMappingTypeResolver() {
        return new XPathMappingTypeResolver();
    }
    
}
