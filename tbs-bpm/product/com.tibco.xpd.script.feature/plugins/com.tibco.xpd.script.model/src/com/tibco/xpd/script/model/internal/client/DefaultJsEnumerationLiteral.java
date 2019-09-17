/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * @author mtorres
 * 
 */
public class DefaultJsEnumerationLiteral implements JsEnumerationLiteral {

    protected EnumerationLiteral enumerationLiteral;

    protected String contentAssistString;

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultJsEnumerationLiteral(EnumerationLiteral enumerationLiteral) {
        this.enumerationLiteral = enumerationLiteral;
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String getComment() {
        return JScriptUtils.getUmlElementComment(enumerationLiteral);
    }

    @Override
    public Enumeration getOwner() {
        Element owner = enumerationLiteral.getOwner();
        if (owner instanceof Enumeration) {
            return (Enumeration) owner;
        }
        return null;
    }

    @Override
    public String getDataType() {
        Enumeration owner = getOwner();
        String typeName = JsConsts.UNDEFINED_DATA_TYPE;
        if (owner != null) {
            typeName = owner.getName();
        }
        return typeName;
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public String getName() {
        return enumerationLiteral.getName();
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

    /**
     * Use setContentAssistIconProvider( IContentAssistIconProvider
     * contentAssistIconProvider) instead
     * 
     **/
    @Deprecated
    public void setIcon(Image image) {
        this.image = image;
    }

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (jsExpression != null) {
            if (!JScriptUtils.hasMoreJSChildren(jsExpression)) {
                DefaultJsEnumeration defaultJsEnumeration =
                        new DefaultJsEnumeration(getOwner());
                if (contentAssistIconProvider != null) {
                    defaultJsEnumeration
                            .setContentAssistIconProvider(contentAssistIconProvider);
                } else {
                    defaultJsEnumeration.setIcon(getIcon());
                }
                IScriptRelevantData scriptRelevantData =
                        new DefaultUMLScriptRelevantData(getName(),
                                getDataType(), false, defaultJsEnumeration);
                dataType.setType(scriptRelevantData);
                return dataType;
            } else {
                JsExpression nextJsExpression =
                        jsExpression.getNextExpression();
                if (nextJsExpression != null) {

                    if (getBaseDataType() != null
                            && !getBaseDataType()
                                    .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                        String name = nextJsExpression.getName();
                        if (getOwner() != null && name != null) {
                            EList<EnumerationLiteral> ownedLiterals =
                                    getOwner().getOwnedLiterals();
                            for (EnumerationLiteral enumerationLiteral : ownedLiterals) {
                                if (enumerationLiteral.getName().equals(name)) {
                                    dataType =
                                            new DefaultJsEnumerationLiteral(
                                                    enumerationLiteral)
                                                    .getDataTypeForJSExpression(nextJsExpression,
                                                            supportedJsClasses);
                                    return dataType;
                                }
                            }
                        }
                        if (nextJsExpression instanceof JsExpressionMethod) {
                            dataType
                                    .setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                        } else {
                            dataType
                                    .setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                        }
                    }
                }
            }
        }
        return dataType;
    }

    public String getBaseDataType() {
        return getName();
    }

    /** {@inheritDoc}. */
    @Override
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }

    @Override
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    @Override
    public IContentAssistIconProvider getContentAssistIconProvider() {
        return contentAssistIconProvider;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsAttribute#getUmlType()
     */
    @Override
    public Type getUmlType() {
        return null;
    }
}
