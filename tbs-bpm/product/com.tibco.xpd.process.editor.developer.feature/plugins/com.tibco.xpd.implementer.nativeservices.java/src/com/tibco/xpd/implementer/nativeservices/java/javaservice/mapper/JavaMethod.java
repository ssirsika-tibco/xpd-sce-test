/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Wrapper class to represent the Java method class <code>IMethod</code>. This
 * wrapper additionally provides a mechanism to retrieve parameters of the
 * method (<code>JavaMethodParameter</code>).
 * 
 * @author njpatel
 * 
 */
public class JavaMethod implements IAdaptable {

    // The Java method this class wraps
    private final IMethod method;

    // The containing class of this method
    private final IType containingClass;

    // Parameters of this method
    private JavaMethodParameter[] parameters;

    // Return parameter of this method
    private JavaMethodParameter returnParam;

    private final Activity activity;

    /**
     * Constructor
     * 
     * @param containingClass
     * @param method
     */
    public JavaMethod(Activity activity, IType containingClass, IMethod method) {

        this.activity = activity;
        if (activity == null) {
            throw new NullPointerException("Activity is null."); //$NON-NLS-1$
        }

        this.containingClass = containingClass;
        if (containingClass == null) {
            throw new NullPointerException("IType is null."); //$NON-NLS-1$
        }

        if (method == null) {
            throw new NullPointerException("Method is null."); //$NON-NLS-1$
        }
        this.method = method;
    }

    /**
     * Get the class container of this method.
     * 
     * @return <code>IType</code>
     */
    public IType getContainer() {
        return containingClass;
    }

    /**
     * Get the name of this method.
     * 
     * @return
     */
    public String getName() {
        return method.getElementName();
    }

    /**
     * Get the activity in which this POJO is defined
     * 
     * @return
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Get a list of parameters of this method.
     * 
     * @return Array of <code>JavaMethodParameter</code> objects. If method has
     *         no parameters then an empty array will be returned.
     * @throws JavaModelException
     */
    public JavaMethodParameter[] getParameters() throws JavaModelException {

        if (parameters == null) {
            List<JavaMethodParameter> paramList =
                    new ArrayList<JavaMethodParameter>();

            String[] paramNames = method.getParameterNames();
            String[] paramTypes = method.getParameterTypes();
            int count = paramNames.length;
            for (int idx = 0; idx < count; idx++) {
                paramList.add(new JavaMethodParameter(containingClass, this,
                        paramTypes[idx], paramNames[idx]));
            }

            parameters =
                    paramList
                            .toArray(new JavaMethodParameter[paramList.size()]);
        }

        return parameters;
    }

    /**
     * Get the return value of this parameter.
     * 
     * @return <code>JavaMethodParameter</code> return value of this parameter.
     *         If the return type is void then the
     *         <code>{@link JavaMethodParameter#getType()}</code> will return
     *         <code>{@link ParameterTypeEnum#VOID}</code>.
     * @throws JavaModelException
     */
    public JavaMethodParameter getReturnParam() throws JavaModelException {

        if (returnParam == null) {
            // Create the return param
            returnParam =
                    new JavaMethodReturnParameter(containingClass, this, method
                            .getReturnType());
        }

        return returnParam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        Object obj = null;

        if (adapter == IMethod.class) {
            obj = method;
        }

        return obj;
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof JavaMethod) {
            JavaMethod other = (JavaMethod) obj;
            try {
                if (method.getSignature().equals(other.method.getSignature())) {
                    equal = true;
                }
            } catch (JavaModelException e) {
                // Assume unequal.
            }
        }
        return equal;
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        try {
            return method.getSignature().hashCode();
        } catch (JavaModelException e) {
            return 0;
        }
    }

}
