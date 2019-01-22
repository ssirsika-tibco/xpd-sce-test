/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.script;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractRQLJsClass implements JsClass {    
    
    Image icon;
    
    private IContentAssistIconProvider contentAssistIconProvider;
    
    /**
     * Create the js Attribute
     * @param attribute
     * @return
     */
    protected JsAttribute createJsAttribute(Attribute attribute) {
        RQLJsAttribute jsAttribute = new RQLJsAttribute(attribute);
        jsAttribute.setIcon(getIcon());
        return jsAttribute;
    }
    
    public void addAttribute(JsAttribute jsAttribute) {
        // TODO Auto-generated method stub
        
    }

    public void addMethod(JsMethod jsMethod) {
        // TODO Auto-generated method stub
        
    }

    public void addReference(JsReference jsReference) {
        // TODO Auto-generated method stub
        
    }

    public List<JsAttribute> getAttributeList() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getComment() {
        // TODO Auto-generated method stub
        return null;
    }

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<JsMethod> getMethodList() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<JsMethod> getMethodList(String methodName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<JsReference> getReferenceList() {
        // TODO Auto-generated method stub
        return null;
    }

    public IScriptRelevantData getScriptRelevantData(String variableName,
            boolean isArray) {
        // TODO Auto-generated method stub
        return null;
    }

    public Class getUmlClass() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isParameterCheckingStrict() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getContentAssistString() {        
        return getName();
    }

    public Image getIcon() {
        return icon;
    }
    
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }
    
}
