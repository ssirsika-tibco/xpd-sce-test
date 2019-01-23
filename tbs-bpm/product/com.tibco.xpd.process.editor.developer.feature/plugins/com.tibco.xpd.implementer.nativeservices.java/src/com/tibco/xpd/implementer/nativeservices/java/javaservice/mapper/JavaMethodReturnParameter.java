/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import org.eclipse.jdt.core.IType;

import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;

/**
 * Wrapper class that represents the method return type.
 * 
 * @author njpatel
 * 
 */
public class JavaMethodReturnParameter extends JavaMethodParameter {

    /**
     * Constructor.
     * 
     * @param containingClass
     * @param parent
     * @param parameterType
     */
    public JavaMethodReturnParameter(IType containingClass, Object parent,
            String parameterType) {
        super(containingClass, parent, parameterType,
                JavaConstants.RETURN_VALUE_ID);
    }

    /**
     * Constructor.
     * 
     * @param param
     * @param index
     */
    public JavaMethodReturnParameter(JavaMethodReturnParameter param, int index) {
        super(param, index);
    }

    /**
     * Constructor.
     * 
     * @param srcType
     * @param parent
     * @param paramSignature
     * @param paramName
     */
    public JavaMethodReturnParameter(IType srcType, Object parent,
            String paramSignature, String paramName) {
        super(srcType, parent, paramSignature, paramName);
    }

    /**
     * Get the path (to be used for mapping source/target identification).
     * 
     * @return the path.
     */
    @Override
    public String getPath() {
        return JavaConstants.RESPONSE_PREFIX
                + JavaConstants.PARAMETER_SEPARATOR + getQualifiedName();
    }

    @Override
    public String getLabel() {
        String label = super.getLabel();

        if (label != null
                && getElementName().equals(JavaConstants.RETURN_VALUE_ID)) {
            // Replace the ID with the Name in the label
            label =
                    label.replaceFirst(JavaConstants.RETURN_VALUE_ID,
                            JavaConstants.RETURN_VALUE_NAME);
        }

        return label;
    }

    @Override
    public JavaMethodParameter createElement(int index) {
        return new JavaMethodReturnParameter(this, index);
    }

    @Override
    protected JavaMethodParameter createChild(IType srcType, Object parent,
            String paramSignature, String paramName) {

        return new JavaMethodReturnParameter(srcType, parent, paramSignature,
                paramName);
    }

}
