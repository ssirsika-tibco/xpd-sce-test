/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;


public interface JsExpression { 

    public String getName();

    public void setName(String name);
    
    boolean isArray();
    
    void setIsArray(boolean value);

    public JsExpression getNextExpression();

    public void setNextExpression(JsExpression nextExpression);
    
    public void setLastExpression(JsExpression jsLastExpression);
    
    public JsExpression getLastExpression();
    
    public JsExpression getArrayExpression();

    public void setArrayExpression(JsExpression arrayExpression);
    
}
