/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.uml2.uml.Parameter;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.PojoJsMethodParam;

/**
 * 
 * @author mtorres
 * 
 */
public class DefaultPojoJsMethodParam implements PojoJsMethodParam, IJsElementExt {

	protected JavaMethodParameter parameter;

    private IContentAssistIconProvider contentAssistIconProvider;
    
	public DefaultPojoJsMethodParam(JavaMethodParameter parameter) {
		this.parameter = parameter;
	}

	public String getName() {
		return parameter.getElementName();
	}

	public String getType() {
		return parameter.getParameterType();
	}

    public boolean isReadOnly() {
        try {
            if (parameter != null && parameter.getIType() != null) {
                int flags = parameter.getIType().getFlags();
                return Flags.isFinal(flags);
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }

    public boolean isStatic() {
        try {
            if (parameter != null && parameter.getIType() != null) {
                int flags = parameter.getIType().getFlags();
                return Flags.isStatic(flags);
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }

    @Override
    public boolean canRepeat() {
        return parameter.isArray();
    }

    @Override
    public int getMaxRepeatCount() {
        if(canRepeat()){
            return -1;
        }
        return 1;
    }

    @Override
    public int getMinRepeatCount() {
        return 0;
    }

    @Override
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }

    @Override
    public Parameter getUMLParameter() {
        return null;
    }

    @Override
    public boolean isPassedByLiteral() {
        return false;
    }

    @Override
    public boolean isPassedByReference() {
        return true;
    }

    @Override
    public boolean isReturnType() {
        return (parameter instanceof JavaMethodReturnParameter);
    }

    @Override
    public String getBaseDataType() {
        return getType();
    }

    @Override
    public JsClass getReferencedReturnType() {
        if(parameter != null && parameter.getIType() != null){
            DefaultPojoJsClass referencedJsClass = new DefaultPojoJsClass(parameter);
            referencedJsClass.setContentAssistIconProvider(contentAssistIconProvider);
            return referencedJsClass;
        }
        return null;
    }

    @Override
    public boolean isReferenceReturnType() {
        if (parameter != null && parameter.getType() != null
                && parameter.getType().equals(ParameterTypeEnum.CLASS)) {
            return true;
        }
        return false;
    }
    
    
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public IContentAssistIconProvider getContentAssistIconProvider() {
        return this.contentAssistIconProvider;
    }
    
    
}
