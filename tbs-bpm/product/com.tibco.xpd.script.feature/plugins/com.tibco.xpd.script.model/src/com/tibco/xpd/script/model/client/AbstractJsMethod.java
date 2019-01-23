/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;

/**
 * Abstract java script class for methods that provides common functionality for
 * all methods. sub classes will have to provide the specific implementation
 * 
 * @author bharge
 * @since 28 Oct 2013
 */
public abstract class AbstractJsMethod implements IJsElementExt, JsMethod {

    protected Image image;

    protected String methodName;

    protected JsMethodParam returnType;

    protected String comment;

    protected List<JsMethodParam> inputParamsList;

    /**
     * @see com.tibco.xpd.script.model.internal.client.ContentAssistElement#getContentAssistString()
     * 
     * @return
     */
    @Override
    public String getContentAssistString() {

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
        String contentAssistString = buffer.toString();

        return contentAssistString;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.ContentAssistElement#getIcon()
     * 
     * @return
     */
    @Override
    public Image getIcon() {

        if (!XpdResourcesPlugin.isInHeadlessMode()) {

            image =
                    Activator.getDefault().getImageRegistry()
                            .get(JsConsts.JS_CLASS);
        }

        if (null != this.image) {

            return this.image;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#getName()
     * 
     * @return
     */
    @Override
    public abstract String getName();

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#getReturnType()
     * 
     * @return
     */
    @Override
    public abstract JsMethodParam getReturnType();

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#getParameterType()
     * 
     * @return
     */
    @Override
    public abstract List<JsMethodParam> getParameterType();

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#getComment()
     * 
     * @return
     */
    @Override
    public abstract String getComment();

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#isMultiple()
     * 
     * @return
     */
    @Override
    public boolean isMultiple() {

        JsMethodParam returnType = getReturnType();
        if (null != returnType) {

            return returnType.canRepeat();
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethod#getDataTypeForJSExpression(com.tibco.xpd.script.model.client.JsExpression,
     *      java.util.List)
     * 
     * @param jsExpression
     * @param supportedJsClasses
     * @return
     */
    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {

        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#getBaseDataType()
     * 
     * @return
     */
    @Override
    public String getBaseDataType() {

        JsMethodParam returnType = getReturnType();
        if (returnType instanceof IJsElementExt) {

            return ((IJsElementExt) returnType).getBaseDataType();
        }
        return null;

    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#isStatic()
     * 
     * @return
     */
    @Override
    public boolean isStatic() {

        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#isReadOnly()
     * 
     * @return
     */
    @Override
    public boolean isReadOnly() {

        return true;
    }

    /**
     * @param propertyName
     * @param uppercase
     *            if set case is upper otherwise lower
     * @return String
     */
    protected String changeCaseInitialChar(String propertyName,
            boolean uppercase) {

        if (uppercase) {

            return Character.toString(propertyName.charAt(0)).toUpperCase()
                    + propertyName.substring(1);
        }
        return Character.toString(propertyName.charAt(0)).toLowerCase()
                + propertyName.substring(1);
    }

}
