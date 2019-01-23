/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

/**
 * 
 * @author mtorres
 *
 */
public class DefaultJsExpression implements JsExpression {
    
    String name;
    boolean isArray;
    JsExpression nextExpression;
    JsExpression arrayExpression;
    
    public DefaultJsExpression() {
     
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }
    
    public JsExpression getNextExpression() {
        return nextExpression;
    }

    public void setNextExpression(JsExpression nextExpression) {
        this.nextExpression = nextExpression;
    }
    

    public JsExpression getLastExpression() {
        if(nextExpression != null){
            JsExpression tempNextExpression = nextExpression;
            while (tempNextExpression.getNextExpression() != null) {
                tempNextExpression = tempNextExpression.getNextExpression();
            }
            return tempNextExpression;
        }
        return null;
    }

    public void setLastExpression(JsExpression jsLastExpression) {
        JsExpression thisLastExpression = getLastExpression();
        if(thisLastExpression != null){
            thisLastExpression.setNextExpression(jsLastExpression);
        }else{
            this.setNextExpression(jsLastExpression);
        }
    }

    public JsExpression getArrayExpression() {
        return arrayExpression;
    }

    public void setArrayExpression(JsExpression arrayExpression) {
        this.arrayExpression = arrayExpression;
    }
 

}
