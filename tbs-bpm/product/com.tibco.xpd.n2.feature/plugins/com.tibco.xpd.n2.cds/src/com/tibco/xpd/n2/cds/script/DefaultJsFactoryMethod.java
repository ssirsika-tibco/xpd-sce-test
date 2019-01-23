/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Operation;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;

/**
 * 
 * @author mtorres
 *
 */
public class DefaultJsFactoryMethod implements JsMethod, IJsElementExt {
    
    protected String contentAssistString;

    private String name;
    
    private Image image;
    
    private JsClass contextDataType;

    private IContentAssistIconProvider contentAssistIconProvider;
    
    private List<JsMethodParam> parameters;
    
    private JsMethodParam returnType;
    
    private boolean isMultiple;
    
    private boolean isStatic;
    
    private String comment;
    
    public DefaultJsFactoryMethod(String methodName,
            List<JsMethodParam> parameters, JsMethodParam returnType, boolean isMultiple, boolean isStatic, String comment) {
        this.name = methodName;
        this.parameters = parameters;
        this.returnType = returnType;
        this.isMultiple = isMultiple;
        this.isStatic = isStatic;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public List<JsMethodParam> getParameterType() {
        return parameters;
    }

    public JsMethodParam getReturnType() {
        return returnType;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public String getContentAssistString() {
        if (contentAssistString == null) {
            StringBuffer buffer = new StringBuffer(getName());
            buffer.append("("); //$NON-NLS-1$
            List<JsMethodParam> parameterType = getParameterType();
            boolean firstParameter = false;
            for (JsMethodParam jsParam : parameterType) {
                if (!firstParameter) {
                    firstParameter = true;
                } else {
                    buffer.append(","); //$NON-NLS-1$
                }
                buffer.append(jsParam.getType());
            }
            buffer.append(")"); //$NON-NLS-1$
            contentAssistString = buffer.toString();
        }
        return contentAssistString;
    }

    public String getComment() {
        return this.comment;
    }

    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

    public void setIcon(Image image) {
        this.image = image;
    }

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        dataType.setUndefinedCause(JsConsts.UNDEFINED_DATA_TYPE_CAUSE);
        return dataType;
    }
    
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }
    
    public IContentAssistIconProvider getContentAssistIconProvider(){
        return this.contentAssistIconProvider;
    }
    
    public boolean isStatic() {
        return this.isStatic;
    }


    public boolean isReadOnly() {        
        return true;
    }

    public String getBaseDataType() {
        JsMethodParam returnType = getReturnType();
        if(returnType instanceof IJsElementExt){
            return ((IJsElementExt)returnType).getBaseDataType();
        }
        return null;
    }
    
    public Operation getMethod() {
		return null;
	}
}
