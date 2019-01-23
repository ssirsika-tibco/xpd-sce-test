/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;


public interface JsExpressionMethod extends JsExpression{
    
    List<JsExpression> getMethodParameterList();
    
    void setMethodParameterList(List<JsExpression> methodParameterList);

    void addMethodParameter(JsExpression methodParameter);
    
    void insertMethodParameter(JsExpression methodParameter);
}
