/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
/**
 * 
 * Interface that provides the result of the expression validation
 * 
 * @author mtorres
 */
public interface IValidateResult {
    /**
     * Returns the expression that was validated
     * 
     * @return the expression validated
     */
     IExpr getExpr();
     
     /**
      * Sets the expression that is to be validated
      * 
      * @param expr
      */
     void setExpr(IExpr expr);
     
     /**
      * Returns the type of the expression validated
      * 
      * @return the type
      */
     IScriptRelevantData getType();
     
     /**
      * Sets the type
      * 
      * @param type
      */
     void setType(IScriptRelevantData type);
}
