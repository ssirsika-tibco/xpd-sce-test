/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

public class DefaultJsMethod extends DefaultMultipleJsClassResolver implements
        JsMethod, IJsElementExt {
    protected String contentAssistString;

    private Operation method;

    private Image image;

    private JsClass contextDataType;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultJsMethod(Operation method) {
        this.method = method;
    }

    public DefaultJsMethod(Operation method, Class multipleClass) {
        this.method = method;
        setMultipleClass(multipleClass);
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsMethodParam> getParameterType() {
        List<Parameter> ownedParameters = method.getOwnedParameters();
        if (ownedParameters == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsMethodParam> paramList = new ArrayList<JsMethodParam>();
        for (Parameter parameter : ownedParameters) {
            ParameterDirectionKind direction = parameter.getDirection();
            if (direction != null
                    && direction.getValue() == ParameterDirectionKind.RETURN) {
                continue;
            }
            paramList.add(new DefaultJsMethodParam(parameter));
        }
        return paramList;
    }

    // TODO
    @Override
    public JsMethodParam getReturnType() {
        JsMethodParam returnParam = null;
        EList ownedParameters = method.getOwnedParameters();
        if (ownedParameters == null) {
            return returnParam;
        }
        for (Object item : ownedParameters) {
            if (item instanceof Parameter) {
                Parameter parameter = (Parameter) item;
                ParameterDirectionKind direction = parameter.getDirection();
                if (direction != null
                        && direction.getValue() == ParameterDirectionKind.RETURN) {
                    returnParam = new DefaultJsMethodParam(parameter);
                    break;
                }
            }
        }
        return returnParam;
    }

    @Override
    public boolean isMultiple() {

        if (isUmlModelMultiple()) {
            return true;
        }
        return false;
    }

    private boolean isUmlModelMultiple() {
        if (method != null) {
            int multipleValue = method.getUpper();
            if (multipleValue == -1 || multipleValue > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
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
                if (jsParam.canRepeat()) {

                    /* for List/Array */
                    buffer.append("[]"); //$NON-NLS-1$
                }
            }
            buffer.append(")"); //$NON-NLS-1$
            contentAssistString = buffer.toString();
        }
        return contentAssistString;
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String getComment() {
        return JScriptUtils.getUmlElementComment(method);
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
        if (jsExpression != null && jsExpression.getName() != null) {
            if (jsExpression.getName().equals(this.getName())) {
                if (this.method != null) {
                    Parameter parameter = this.method.getReturnResult();
                    if (parameter != null) {
                        Type paramType = parameter.getType();
                        if (paramType != null) {
                            IScriptRelevantData scriptRelevantData =
                                    JScriptUtils
                                            .evaluateScriptRelevantData(getName(),
                                                    paramType,
                                                    supportedJsClasses,
                                                    isMultiple(),
                                                    getMultipleClass());
                            if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                                JsExpression nextJsExpression =
                                        jsExpression.getNextExpression();
                                if (isMultiple()) {
                                    scriptRelevantData =
                                            JScriptUtils
                                                    .resolveJavaScriptStringType(getName(),
                                                            getMultipleJsClassName(),
                                                            false,
                                                            supportedJsClasses,
                                                            getMultipleClass(),
                                                            null);
                                }
                                if (nextJsExpression != null) {
                                    if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                                        JsClass returnTypeClass =
                                                ((IUMLScriptRelevantData) scriptRelevantData)
                                                        .getJsClass();
                                        returnTypeClass.setIcon(this.getIcon());
                                        dataType =
                                                returnTypeClass
                                                        .getDataTypeForJSExpression(jsExpression,
                                                                supportedJsClasses);
                                        return dataType;
                                    } else {
                                        if (nextJsExpression instanceof JsExpressionMethod) {
                                            dataType.setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                                        } else {
                                            dataType.setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                                        }
                                    }
                                }
                            } else {
                                if (scriptRelevantData.getType() != null
                                        && scriptRelevantData
                                                .getType()
                                                .equals(getMultipleJsClassName())
                                        && !isMultiple()) {
                                    scriptRelevantData =
                                            JScriptUtils
                                                    .resolveJavaScriptNotMultipleArrayType(getName(),
                                                            true,
                                                            supportedJsClasses,
                                                            getMultipleClass());
                                } else if (isMultiple()) {
                                    scriptRelevantData =
                                            JScriptUtils
                                                    .resolveJavaScriptStringType(getName(),
                                                            getMultipleJsClassName(),
                                                            false,
                                                            supportedJsClasses,
                                                            getMultipleClass(),
                                                            null);
                                }
                                dataType.setType(scriptRelevantData);
                                return dataType;
                            }
                        }
                    }
                }
            }
        }
        dataType.setUndefinedCause(JsConsts.UNDEFINED_DATA_TYPE_CAUSE);
        return dataType;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public IContentAssistIconProvider getContentAssistIconProvider() {
        return this.contentAssistIconProvider;
    }

    @Override
    public boolean isStatic() {
        if (method != null) {
            return method.isStatic();
        }
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public String getBaseDataType() {
        JsMethodParam returnType = getReturnType();
        if (returnType instanceof IJsElementExt) {
            return ((IJsElementExt) returnType).getBaseDataType();
        }
        return null;
    }

    public Operation getMethod() {
        return method;
    }
}
