/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.expr;

/**
 * Expression wrapper
 * 
 * @author mtorres
 * 
 */
public interface IExpr {
    /**
     * Returns the expression 
     * 
     * @return the expression object
     */
    public Object getExpr();
    
    /**
     * Returns the Token
     * 
     * @return the token object
     */
    public Object getToken();
}
