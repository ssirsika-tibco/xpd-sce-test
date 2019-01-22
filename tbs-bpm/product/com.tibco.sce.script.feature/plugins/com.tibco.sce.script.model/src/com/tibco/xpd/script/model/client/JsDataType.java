/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import com.tibco.xpd.script.model.JsConsts;
/**
 * This class will define the data type for a given expression
 *
 **/
public class JsDataType {
    
    IScriptRelevantData type;
    int undefinedCause = JsConsts.UNDEFINED_DATA_TYPE_CAUSE;
    JsExpression jsExpression; 

    public JsDataType() {        
    }
    
    public JsDataType(IScriptRelevantData type) {
        this.type = type;
    }
    
    public IScriptRelevantData getType() {
        return type;
    }

    public void setType(IScriptRelevantData type) {
        this.type = type;
    }

    public int getUndefinedCause() {
        return undefinedCause;
    }

    public void setUndefinedCause(int undefinedCause) {
        this.undefinedCause = undefinedCause;
    }

    public JsExpression getJsExpression() {
        return jsExpression;
    }

    public void setJsExpression(JsExpression jsExpression) {
        this.jsExpression = jsExpression;
    }

    public boolean isTypeUndefined(){
        boolean typeUndefined = true;
        String typeName = getTypeName();
        if(typeName != null && !typeName.equals(JsConsts.UNDEFINED_DATA_TYPE)){
            return false;
        }
        return typeUndefined;
    }
    
    public String getTypeName(){
        if(type != null){
            String typeName = type.getType();
            if(typeName != null){
                return typeName;
            }
        }
        return JsConsts.UNDEFINED_DATA_TYPE;
    }


}
