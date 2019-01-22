/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.Collections;
import java.util.List;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * @author mtorres
 *
 */
public class WsMessageJsClass extends DefaultJsClass{
    
    private String prefix;
    
    private Part wsdlPart;
    
    public WsMessageJsClass(String prefix, Part wsdlPart) {
        super(JavaScriptConceptUtil.INSTANCE.getConceptClass(wsdlPart));
        this.prefix = prefix;
        this.wsdlPart = wsdlPart;
    }

    public List<JsMethod> getMethodList() {        
       return Collections.emptyList();
    }
    
    public List<JsMethod> getMethodList(String methodName) {
        return Collections.emptyList();
    }
    
    @Override
    public List<JsAttribute> getAttributeList() {
        if(getUmlClass() == null){
            return Collections.emptyList();
        }
        return super.getAttributeList();
    }
    
    @Override
    public List<JsReference> getReferenceList() {
        if(getUmlClass() == null){
            return Collections.emptyList();
        }
        return super.getReferenceList();
    }

    @Override
    public String getName() {
    	/*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
        return prefix + NameUtil.sanitizeMessagePartVariableName(wsdlPart.getName());
    }

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {     
        return new JsDataType();
    }
}