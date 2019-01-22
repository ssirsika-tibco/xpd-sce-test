/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;

/**
 * Wrapper class for the Pojo field
 * 
 * @author mtorres
 * 
 */
public class DefaultPojoJsAttribute implements JsAttribute, IJsElementExt {

    protected JavaMethodParameter field;

    protected String contentAssistString;

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultPojoJsAttribute(JavaMethodParameter field) {
        this.field = field;
    }

    @Override
    public String getComment() {
        try {
            if (field != null && field.getIType() != null) {
                return field.getIType()
                        .getAttachedJavadoc(new NullProgressMonitor());
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return null;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public IContentAssistIconProvider getContentAssistIconProvider() {
        return contentAssistIconProvider;
    }

    @Override
    public String getDataType() {
        if (field != null && field.getTypeSignature() != null) {
            if (getType() == ParameterTypeEnum.CLASS) {
                return Signature.getSignatureSimpleName(Signature
                        .getElementType(field.getTypeSignature()));
            } else {
                return getType().getLabel();
            }
        }
        return JsConsts.VOID_DATA_TYPE;
    }

    private ParameterTypeEnum getType() {
        if (field != null && field.getTypeSignature() != null) {
            return ParameterTypeEnum.getType(field.getTypeSignature());
        }
        return null;
    }

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        return null;
    }

    @Override
    public String getName() {
        if (field != null) {
            return field.getElementName();
        }
        return null;
    }

    @Override
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }

    @Override
    public boolean isMultiple() {
        if (field != null && field.getTypeSignature() != null) {
            return Signature.getTypeSignatureKind(field.getTypeSignature()) == Signature.ARRAY_TYPE_SIGNATURE;
        }
        return false;
    }

    @Override
    public String getContentAssistString() {
        return getName();
    }

    @Override
    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

    @Override
    public String getBaseDataType() {
        return getDataType();
    }

    @Override
    public boolean isReadOnly() {
        try {
            if (field != null && field.getIType() != null) {
                int flags = field.getIType().getFlags();
                return Flags.isFinal(flags);
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }

    @Override
    public boolean isStatic() {
        try {
            if (field != null && field.getIType() != null) {
                int flags = field.getIType().getFlags();
                return Flags.isStatic(flags);
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }
}
