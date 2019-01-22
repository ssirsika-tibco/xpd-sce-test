package com.tibco.xpd.script.model.client;

import java.util.ArrayList;
import java.util.List;


public class DefaultJsExpressionMethod extends DefaultJsExpression implements JsExpressionMethod {
    
    List<JsExpression> methodParameterList;
    
    
    public void addMethodParameter(JsExpression methodParameter) {
        List<JsExpression> methodParameterList = getMethodParameterList();
        methodParameterList.add(methodParameter);
    }
    
    public void insertMethodParameter(JsExpression methodParameter) {
        List<JsExpression> methodParameterList = getMethodParameterList();
        methodParameterList.add(0, methodParameter); 
    }
    
    public List<JsExpression> getMethodParameterList() {
        if(methodParameterList == null){
            methodParameterList = new ArrayList<JsExpression>();
        }
        return this.methodParameterList;
    }
    
    public void setMethodParameterList(List<JsExpression> methodParameterList) {
        this.methodParameterList = methodParameterList;
    }
    

}
