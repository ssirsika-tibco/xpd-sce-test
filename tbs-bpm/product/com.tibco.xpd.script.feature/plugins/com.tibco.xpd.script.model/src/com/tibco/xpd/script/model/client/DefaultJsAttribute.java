/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * <p>
 * <i>Created: 10 Apr 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class DefaultJsAttribute extends DefaultMultipleJsClassResolver
        implements JsUmlAttribute, IUMLElement, IJsElementExt {

    protected Property property;

    protected String contentAssistString;

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultJsAttribute(Property property) {
        this.property = property;
    }

    public DefaultJsAttribute(Property property, Class multipleClass) {
        this.property = property;
        setMultipleClass(multipleClass);
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String getComment() {
        return JScriptUtils.getUmlElementComment(property);
    }

    @Override
    public String getDataType() {
        Type type = property.getType();
        String typeName = JsConsts.VOID_DATA_TYPE;
        if (type != null) {
            typeName = JScriptUtils.getUmlType(type);
        }
        return typeName;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsAttribute#getUmlType()
     */
    @Override
    public Type getUmlType() {
        return property.getType();
    }

    @Override
    public boolean isMultiple() {
        if (this.getDataType() != null
                && this.getDataType().equals(getMultipleJsClassName())) {
            return true;
        }
        if (isUmlModelMultiple()) {
            return true;
        }
        return false;
    }

    private boolean isUmlModelMultiple() {
        if (property != null) {
            int multipleValue = property.getUpper();
            if (multipleValue == -1 || multipleValue > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return property.getName();
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

    public void setIcon(Image image) {
        this.image = image;
    }

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (jsExpression != null) {
            IScriptRelevantData scriptRelevantData =
                    JScriptUtils.resolveJavaScriptStringType(getName(),
                            this.getBaseDataType(),
                            JScriptUtils.isDataTypeMultiple(jsExpression,
                                    isMultiple()),
                            supportedJsClasses,
                            getMultipleClass(),
                            null);
            // Check if multiplicity match
            if (!JScriptUtils.multiplicityMatch(jsExpression, isMultiple())) {
                if (isMultiple()) {
                    scriptRelevantData =
                            JScriptUtils.resolveJavaScriptStringType(getName(),
                                    getMultipleJsClassName(),
                                    false,
                                    supportedJsClasses,
                                    getMultipleClass(),
                                    null);
                } else {
                    dataType.setUndefinedCause(JsConsts.ARRAY_NOT_EXPECTED_DATA_TYPE_CAUSE);
                    return dataType;
                }
                dataType.setType(scriptRelevantData);
            }
            if (!JScriptUtils.hasMoreJSChildren(jsExpression)) {
                if (this.getBaseDataType() != null
                        && this.getBaseDataType()
                                .equals(getMultipleJsClassName())
                        && !JScriptUtils.isDataTypeMultiple(jsExpression,
                                isUmlModelMultiple())) {
                    scriptRelevantData =
                            JScriptUtils
                                    .resolveJavaScriptNotMultipleArrayType(getName(),
                                            JScriptUtils
                                                    .isDataTypeMultiple(jsExpression,
                                                            isMultiple()),
                                            supportedJsClasses,
                                            getMultipleClass());
                } else {
                    scriptRelevantData =
                            JScriptUtils.resolveJavaScriptStringType(getName(),
                                    this.getBaseDataType(),
                                    JScriptUtils
                                            .isDataTypeMultiple(jsExpression,
                                                    isMultiple()),
                                    supportedJsClasses,
                                    getMultipleClass(),
                                    null);
                }
                dataType.setType(scriptRelevantData);
                return dataType;
            } else {
                JsExpression nextJsExpression =
                        jsExpression.getNextExpression();
                if (nextJsExpression != null) {
                    if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                        JsClass attributeTypeClass =
                                ((IUMLScriptRelevantData) scriptRelevantData)
                                        .getJsClass();
                        attributeTypeClass.setIcon(this.getIcon());
                        dataType =
                                attributeTypeClass
                                        .getDataTypeForJSExpression(jsExpression,
                                                supportedJsClasses);
                        return dataType;
                    } else {
                        if (getBaseDataType() != null
                                && !getBaseDataType()
                                        .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                            if (nextJsExpression instanceof JsExpressionMethod) {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                            } else {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                            }
                        }
                    }
                }
            }
        }
        return dataType;
    }

    @Override
    public String getBaseDataType() {
        if (property != null && property.getType() instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) property.getType();
            String baseDataType =
                    JScriptUtils.getBasePrimitiveDataType(primitiveType);
            return baseDataType;
        }
        return getDataType();
    }

    /** {@inheritDoc}. */
    @Override
    public IScriptRelevantData getScriptRelevantData() {
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
    public Element getElement() {
        return property;
    }

    @Override
    public boolean isStatic() {
        if (property != null) {
            return property.isStatic();
        }
        return false;
    }

    @Override
    public boolean isReadOnly() {
        if (property != null) {
            return property.isReadOnly();
        }
        return false;
    }
}
