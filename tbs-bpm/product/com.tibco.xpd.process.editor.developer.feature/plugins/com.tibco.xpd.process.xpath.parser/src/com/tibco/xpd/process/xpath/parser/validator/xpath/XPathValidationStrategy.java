/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.validator.xpath;

import com.tibco.xpd.process.js.parser.validator.AbstractProcessValidationStrategy;
import com.tibco.xpd.process.js.parser.validator.IProcessValidationStrategy;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidatorFactory;
import com.tibco.xpd.script.parser.validator.jscript.JSExpressionValidatorFactory;
 
/**
* 
* 
* <p>
* <i>Created: 12 Feb 2008</i>
* </p>
* 
* @author Miguel Torres
* 
*/
public class XPathValidationStrategy extends
        AbstractProcessValidationStrategy implements IProcessValidationStrategy {

    @Override
    protected IExpressionValidator getNewExpressionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getConditionalExpressionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getMethodCallValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getMethodDefinitionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getUndefinedVariableUseValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getVariableDeclarationValidator() {
        return null;
    }

    private IExpressionValidator exprValidator = null;

    @Override
    protected IExpressionValidator getExpressionValidator() {
        if (exprValidator == null) {
            exprValidator = new XPathExpressionValidator();
        }
        return exprValidator;
    }

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        return null;
    }

    @Override
    protected IValidator getStatementValidator() {
        return null;
    }
    
    @Override
    protected IExpressionValidatorFactory getExpresionValidatorFactory(){
        //TODO Return here the XPATH Expression validator factory
        return null;
     }


}
