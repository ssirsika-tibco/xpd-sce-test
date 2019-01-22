/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
/**
 * Wrapper class for the Pojo method
 * 
 * @author mtorres
 * 
 */
public class DefaultPojoJsMethod implements JsMethod, IJsElementExt {
    
    protected String contentAssistString;

    private IMethod method;

    private Image image;
    
    private JsClass contextDataType;

    private IContentAssistIconProvider contentAssistIconProvider;
    
    public DefaultPojoJsMethod(IMethod method) {
        this.method = method;
    }

    public String getName() {
        return method.getElementName();
    }    
  
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public IContentAssistIconProvider getContentAssistIconProvider() {
        return this.contentAssistIconProvider;
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
    
    @Override
    public boolean isStatic() {
        try {
            int flags = method.getFlags();
            Flags.isStatic(flags);
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }
    

    @Override
    public boolean isReadOnly() {
        return method.isReadOnly();
    }
    
    public JsMethodParam getReturnType() {
        JavaMethodReturnParameter returnParam = null;
        try {
            returnParam =
                    new JavaMethodReturnParameter(method.getDeclaringType(),
                            this, method.getReturnType());
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        DefaultPojoJsMethodParam returnType = new DefaultPojoJsMethodParam(returnParam);
        returnType.setContentAssistIconProvider(contentAssistIconProvider);
        return returnType;
    }
    

    public List<JsMethodParam> getParameterType() {
        List<JsMethodParam> paramList = new ArrayList<JsMethodParam>();
        try {
            if (method != null) {
                List<JavaMethodParameter> ownedParameterList =
                        new ArrayList<JavaMethodParameter>();
                String[] paramNames = method.getParameterNames();
                String[] paramTypes = method.getParameterTypes();
                int count = paramNames.length;
                for (int idx = 0; idx < count; idx++) {
                    ownedParameterList.add(new JavaMethodParameter(method
                            .getDeclaringType(), this, paramTypes[idx],
                            paramNames[idx]));
                }
                for (JavaMethodParameter parameter : ownedParameterList) {
                    if (parameter != null) {
                        DefaultPojoJsMethodParam defaultPojoJsMethodParam =
                                new DefaultPojoJsMethodParam(parameter);
                        defaultPojoJsMethodParam
                                .setContentAssistIconProvider(contentAssistIconProvider);
                        paramList.add(defaultPojoJsMethodParam);
                    }
                }
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return paramList;
    }
    

    @Override
    public String getComment() {
        try {
            if (method != null) {
                return method.getAttachedJavadoc(new NullProgressMonitor());
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return null;
    }
  

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        return null;
    }

    @Override
    public boolean isMultiple() {
        JsMethodParam returnType = getReturnType();
        if (returnType != null && returnType.canRepeat()) {
            return true;
        }
        return false;
    }

    @Override
    public String getBaseDataType() {
        if(getReturnType() != null){
            return getReturnType().getType();   
        }
        return null;
    }



}
