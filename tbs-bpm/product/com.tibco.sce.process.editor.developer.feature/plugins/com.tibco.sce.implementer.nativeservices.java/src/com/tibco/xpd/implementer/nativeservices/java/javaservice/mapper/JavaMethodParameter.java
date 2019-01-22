/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * A wrapper class for the Java method parameter. This class will be used to
 * represent the parameters to a method and also the return type. A parameter
 * can either be a simple type or can be a complex (JavaBean) type. Calling
 * <code>{@link #getType()}</code> will return the type of parameter; for
 * complex types this method will return
 * <code>{@link ParameterTypeEnum#CLASS}</code>.
 * 
 * @author njpatel
 * 
 */
public class JavaMethodParameter {

    private ParameterTypeEnum paramType;

    // Class that contains the parent method
    private final IType containingClass;

    /** qualified Type of this parameter. */
    private IType parameterType = null;

    /** Whether the attempt to get and cache qualified type has been made. */
    private boolean iTypeGetAttempted = false;

    // If this parameter is the complex type then this will contain the bean
    // info
    private JavaBean javaBean;

    private final String typeSignature;

    private final String name;

    private final Object parent;

    // Array index if this param is an array type
    private int index;

    /*
     * Set to true (by the content provider) to indicate that this element is
     * the highest in the array. This will allow the label to be printed
     * accordingly
     */
    private boolean isHighestIndex = false;

    // Set to true when this parameter, if an array type, is mapped to an array
    // type data field
    private boolean isMappedToArrayDataField = false;

    /**
     * Constructor.
     * 
     * @param containingClass
     *            The containing class of the parent method.
     * @param parent
     *            The parent of this parameter, this could either be the
     *            <code>JavaMethod</code> or it could be a
     *            <code>JavaMethodParameter</code> (for beans).
     * @param parameterType
     *            The type signature of the parameter as returned by JDT.
     * @param parameterName
     *            The name of the parameter.
     */
    public JavaMethodParameter(IType containingClass, Object parent,
            String typeSignature, String name) {

        this(containingClass, parent, typeSignature, name, 0);
    }

    /**
     * Constructor.
     * 
     * @param containingClass
     *            The containing class of the parent method.
     * @param parent
     *            The parent of this parameter, this could either be the
     *            <code>JavaMethod</code> or it could be a
     *            <code>JavaMethodParameter</code> (for beans).
     * @param typeSignature
     *            The type signature of the parameter as returned by JDT.
     * @param name
     *            The name of the parameter.
     * @param index
     *            Index of the array element if this is an array type parameter.
     */
    public JavaMethodParameter(IType containingClass, Object parent,
            String typeSignature, String name, int index) {

        this.containingClass = containingClass;
        this.parent = parent;
        this.typeSignature = typeSignature;
        this.name = name;
        this.index = index;
    }

    /**
     * Constructor. Use this constructor when adding an array element of the
     * <i>param</i>.
     * 
     * @param param
     * @param index
     */
    public JavaMethodParameter(JavaMethodParameter param, int index) {
        this(param.getContainingClass(), param.getParent(), param
                .getTypeSignature(), param.getElementName(), index);
    }

    /**
     * Set the index of this element array
     * 
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get the index of this array element.
     * 
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set to true to indicate if this is the highest index of an array element.
     * 
     * @param highest
     */
    public void setIsHighestIndex(boolean highest) {
        isHighestIndex = highest;
    }

    /**
     * Is this is the highest index of an array element?
     * 
     * @return
     */
    public boolean isHighestIndex() {
        return isHighestIndex;
    }

    /**
     * Get the name of this parameter without any array decoration.
     * 
     * @return
     */
    public String getElementName() {
        return name;
    }

    /**
     * Get the name of this parameter with the array decoration. The array
     * decoration will be in the form <i>name</i>[x].
     * 
     * @return Name of the parameter
     */
    public String getName() {
        String ret = name;

        if (parent instanceof JavaMethodParameter
                && ((JavaMethodParameter) parent).isArray()) {
            ret += "[" + getIndex() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }

        return ret;
    }

    /**
     * This is the label of this parameter with array decoration if the
     * parameter is an array. The array decoration will be in the form
     * <i>label</i>[x]. Default output is the same as
     * <code>{@link #getName()}</code>, subclasses can override to display a
     * different label. If this an array element and the highest element then
     * the array decoration will be '[..]'
     * 
     * @return
     */
    public String getLabel() {
        String label = getName();

        // if (isArray()) {
        // if (isMappedToArrayDataField) {
        // label += "[]"; //$NON-NLS-1$
        // } else if (isHighestIndex()) {
        // label =
        // label
        // .replaceFirst(
        // "\\[\\d+\\]", String.format("[%d]", getIndex())); //$NON-NLS-1$
        // //$NON-NLS-2$
        // }
        // }
        if (isArray()) {
            label += "[]"; //$NON-NLS-1$
        }

        return label;
    }

    /**
     * Get the fully qualified type of this parameter:
     * <ul>
     * <li>If this parameter is a primitive type then the type name will be
     * returned.</li>
     * <li>If this parameter is a class type then the qualified class name will
     * be returned.</li>
     * <li>If this is an array type the name will be suffixed with '[]'.</li>
     * </ul>
     * 
     * @return Qualified name of the parameter type, <code>null</code> if one
     *         cannot be resolved.
     * @throws JavaModelException
     */
    public String getQualifiedType() throws JavaModelException {
        return getQualifiedType(false);
    }

    /**
     * @param useElementClassNameIfArray
     *            If <code>true</code> and signature is an array then resort to
     *            qualified type name of the array's declared element. This may
     *            be desired if want the return value to be used by
     *            <code>IProject#findType:IType</code>, as an <code>IType</code>
     *            cannot be obtained for an array. And it should be safe to
     *            assume that under the covers the object supporting arrays will
     *            itself always support serialisation.
     * @return Qualified name of the parameter type, <code>null</code> if one
     *         cannot be resolved.
     * @throws JavaModelException
     */
    public String getQualifiedType(boolean useElementClassNameIfArray)
            throws JavaModelException {
        String className = null;

        if (containingClass != null && typeSignature != null) {
            if (Signature.getTypeSignatureKind(Signature
                    .getElementType(typeSignature)) == Signature.BASE_TYPE_SIGNATURE) {
                ParameterTypeEnum type =
                        ParameterTypeEnum.getType(typeSignature);

                if (type != null) {
                    className = type.getLabel();
                }

            } else {
                String[][] resolved =
                        JavaModelUtil.resolveType(containingClass,
                                typeSignature);

                if (resolved != null && resolved[0] != null) {
                    className =
                            resolved[0][0] + Signature.C_DOT + resolved[0][1];
                }
            }

            if (className != null && isArray() && !useElementClassNameIfArray) {
                className += "[]"; //$NON-NLS-1$
            }
        }

        return className;
    }

    /**
     * Get the parent of the parameter
     * 
     * @return <code>JavaMethod</code> if this a parameter of a method or
     *         <code>JavaMethodParameter</code> if this is a parameter of a
     *         JavaBean.
     */
    public Object getParent() {
        return parent;
    }

    /**
     * Get the <code>Activity</code> in which this POJO is defined
     * 
     * @return
     */
    public Activity getActivity() {
        Activity activity = null;

        Object prn = getParent();

        while (prn instanceof JavaMethodParameter) {
            prn = ((JavaMethodParameter) prn).getParent();
        }

        if (prn instanceof JavaMethod) {
            activity = ((JavaMethod) prn).getActivity();
        }

        return activity;
    }

    /**
     * Check if this parameter is defined as an array.
     * 
     * @return <code>true</code> if this parameter is an array.
     */
    public boolean isArray() {
        return Signature.getTypeSignatureKind(typeSignature) == Signature.ARRAY_TYPE_SIGNATURE;
    }

    /**
     * Get type of the parameter.
     * 
     * @return <code>ParameterType</code> value.
     */
    public ParameterTypeEnum getType() {

        if (paramType == null) {
            paramType = ParameterTypeEnum.getType(typeSignature);
        }

        return paramType;
    }

    /**
     * Get the parameter name, for classes this will return the class name.
     * 
     * @return
     */
    public String getParameterType() {
        String name = ""; //$NON-NLS-1$
        if (getType() == ParameterTypeEnum.CLASS) {
            name =
                    Signature.getSignatureSimpleName(Signature
                            .getElementType(typeSignature));
        } else {
            name = getType().getLabel();
        }
        return name;
    }

    /**
     * Get the children of this parameter.
     * 
     * @return Empty array if this is a simple type parameter, or a list of
     *         <code>JavaMethodParameter</code> objects if this is a java bean
     *         parameter.
     */
    public JavaMethodParameter[] getChildren(MappingDirection direction) {
        JavaMethodParameter[] children = new JavaMethodParameter[0];

        if (isArray()) {
            String elementSignature = Signature.getElementType(typeSignature);
            JavaMethodParameter child =
                    createChild(containingClass, this, elementSignature, name);
            children = new JavaMethodParameter[] { child };
        } else {
            if (getType() == ParameterTypeEnum.CLASS) {
                // Get the bean class
                if (javaBean == null) {
                    try {
                        IType beanType =
                                JavaModelUtil.resolveIType(containingClass,
                                        typeSignature);

                        if (beanType != null) {
                            javaBean = new JavaBean(this, beanType);
                        }

                    } catch (JavaModelException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (javaBean != null) {
                    try {
                        children = javaBean.getParameters(direction);

                    } catch (JavaModelException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return children;
    }

    /**
     * Set to <code>true</code> when this parameter, if an array, is mapped to
     * an array data field. This value will be typically set by the mapper
     * content provider.
     * 
     * @param mapped
     */
    public void setIsMappedToArrayDataField(boolean mapped) {
        isMappedToArrayDataField = mapped;
        isHighestIndex = true;
    }

    /**
     * Is this array parameter mapped to an array data field
     * 
     * @return <code>true</code> if mapped to an array data field,
     *         <code>false</code> if not mapped to an array data field, or this
     *         data field is not of an array type.
     */
    public boolean isMappedToArrayDataField() {
        return isMappedToArrayDataField;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", getLabel(), getType().getLabel()); //$NON-NLS-1$
    }

    /**
     * Get the IType for this parameter IF it can be resolved to a single type.
     * <p>
     * If the target project is not compiled or compiled with errors there may
     * be duplicate possible types for any given parameter, in this case this
     * method will return null.
     * 
     * @return IType of this parameter or <code>null</code> if cannot be
     *         resovled to a single definitive IType definition.
     */
    public IType getIType() {
        if (!iTypeGetAttempted) {
            iTypeGetAttempted = true;

            try {
                parameterType =
                        JavaModelUtil.resolveIType(containingClass,
                                typeSignature);
            } catch (JavaModelException e) {
            }
        }

        return parameterType;
    }

    /**
     * Get the class that the parent method belongs to.
     * 
     * @return the containingClass
     */
    public IType getContainingClass() {
        return containingClass;
    }

    /**
     * Get type signature of this parameter
     * 
     * @return
     */
    protected String getTypeSignature() {
        return typeSignature;
    }

    /**
     * Create a child of this parameter using the info given.
     * 
     * @param srcType
     * @param parent
     * @param paramSignature
     * @param paramName
     * @return
     */
    protected JavaMethodParameter createChild(IType srcType, Object parent,
            String paramSignature, String paramName) {
        return new JavaMethodParameter(srcType, parent, paramSignature,
                paramName);
    }

    /**
     * A wrapper classed to represent a Java Bean. This method will explore the
     * <code>IType</code> source of the bean to discover the parameters by
     * checking for getters and setters.
     * 
     * @author njpatel
     * 
     */
    private class JavaBean {

        private static final String GETTER_PREFIX = "get"; //$NON-NLS-1$

        private static final String SETTER_PREFIX = "set"; //$NON-NLS-1$

        private static final String IS_PREFIX = "is"; //$NON-NLS-1$

        private final IType src;

        List<JavaMethodParameter> params = null;

        private final Object beanParent;

        /**
         * Constructor.
         * 
         * @param parent
         *            The parent of this parameter, typically a
         *            <code>JavaMethodParameter</code>.
         * @param src
         *            The java bean source.
         */
        public JavaBean(Object parent, IType src) {
            beanParent = parent;
            this.src = src;
        }

        /**
         * Get the parameters of this bean.
         * 
         * @return
         * @throws JavaModelException
         */
        public JavaMethodParameter[] getParameters(MappingDirection direction)
                throws JavaModelException {

            if (params == null) {
                params = new ArrayList<JavaMethodParameter>();
                // Find all methods
                Set<IMethod> allMethods = new HashSet<IMethod>();
                addPublicMethods(allMethods, src, null);
                List<IMethod> getters = new ArrayList<IMethod>();
                List<IMethod> setters = new ArrayList<IMethod>();
                // Get all getter and setter methods
                for (IMethod method : allMethods) {
                    if ((method.getElementName().startsWith(GETTER_PREFIX) || method
                            .getElementName().startsWith(IS_PREFIX))
                            && method.getNumberOfParameters() == 0) {
                        getters.add(method);
                    } else if (method.getElementName()
                            .startsWith(SETTER_PREFIX)
                            && method.getNumberOfParameters() == 1) {
                        setters.add(method);
                    }
                }

                if (direction.equals(MappingDirection.IN)) {
                    addGetterAndSetterJavaParams(setters, direction);
                } else {
                    addGetterAndSetterJavaParams(getters, direction);
                }
            }

            return params.toArray(new JavaMethodParameter[params.size()]);
        }

        /**
         * Adds both setter and getter attributes depending on the current
         * direction tab selected. i.e. Direction IN tab is only interested in
         * showing the setter attributes of the parent class and vice versa for
         * the Direction OUT tab.
         * 
         * @param methodList
         * @param direction
         * @throws JavaModelException
         */
        private void addGetterAndSetterJavaParams(List<IMethod> methodList,
                MappingDirection direction) throws JavaModelException {
            String paramType = null;

            // go through each method and check it
            for (IMethod method : methodList) {
                // get the actual name of the attribute (will work for get or
                // set methods)
                String elementName = method.getElementName();

                String paramName = null;

                if (elementName.startsWith(IS_PREFIX)) {
                    paramName = elementName.substring(IS_PREFIX.length());
                } else if (elementName.startsWith(GETTER_PREFIX)) {
                    paramName = elementName.substring(GETTER_PREFIX.length());
                } else if (elementName.startsWith(SETTER_PREFIX)) {
                    paramName = elementName.substring(SETTER_PREFIX.length());
                }

                /* Ignore methods called "get()", "set()" and "is()" */
                if (paramName != null && paramName.length() > 0) {
                    paramName =
                            paramName.substring(0, 1).toLowerCase()
                                    + paramName.substring(1);

                    // if we are currently looking at the mapping in tab then we
                    // are
                    // only interested
                    // in the setters so get the parameter type (assumes only
                    // one)
                    if (direction.equals(MappingDirection.IN)
                            && method.getParameterTypes() != null
                            && method.getParameterTypes().length > 0) {
                        paramType = method.getParameterTypes()[0];
                    } else { // else this is on the OUT mapping tab and so we
                        // look at the getter methods return type
                        paramType = method.getReturnType();
                    }

                    // add the attribute to the JavaMethodParameter list so user
                    // can
                    // view these children attributes
                    params.add(createChild(src,
                            beanParent,
                            paramType,
                            paramName));
                }
            }
        }

        /**
         * @param allMethods
         * @param src2
         * @throws JavaModelException
         */
        private void addPublicMethods(Set<IMethod> allMethods, IType type,
                Set<String> alreadyProcessedMethodSigs)
                throws JavaModelException {

            if (alreadyProcessedMethodSigs == null) {
                alreadyProcessedMethodSigs = new HashSet<String>();
            }

            IMethod[] methods = type.getMethods();
            for (IMethod method : methods) {
                /*
                 * Only add methods we haven't already added for subclass! (i.e.
                 * don't include overriden methods.
                 */
                if ((method.getFlags() & Flags.AccPublic) > 0) {
                    String methodSig =
                            method.getElementName() + method.getSignature();
                    if (!alreadyProcessedMethodSigs.contains(methodSig)) {
                        alreadyProcessedMethodSigs.add(methodSig);
                        allMethods.add(method);
                    }
                }
            }
            String superTypeSig = type.getSuperclassTypeSignature();
            if (superTypeSig != null) {
                IType superType =
                        JavaModelUtil.resolveIType(containingClass,
                                superTypeSig);
                if (superType != null) {
                    addPublicMethods(allMethods,
                            superType,
                            alreadyProcessedMethodSigs);
                }
            }
            return;
        }

        /**
         * Find the getter of the given parameter that matches the return type.
         * 
         * @param getters
         * @param paramName
         * @param returnType
         * @return
         * @throws JavaModelException
         */
        private IMethod findGetter(List<IMethod> getters, String paramName,
                String returnType) throws JavaModelException {

            String methodParamName =
                    paramName.substring(0, 1).toUpperCase()
                            + paramName.substring(1);
            String methodName = GETTER_PREFIX + methodParamName;

            if (getters != null && getters.size() > 0) {
                for (IMethod method : getters) {
                    if (method.getElementName().equals(methodName)
                            && method.getReturnType().equals(returnType)) {
                        return method;
                    }
                }

                /*
                 * If the getter is not found and the return type is a boolean
                 * then the method could start with is...
                 */
                if (ParameterTypeEnum.getType(returnType) == ParameterTypeEnum.BOOLEAN) {
                    methodName = IS_PREFIX + methodParamName;

                    for (IMethod method : getters) {
                        if (method.getElementName().equals(methodName)
                                && method.getReturnType().equals(returnType)) {
                            return method;
                        }
                    }
                }
            }

            return null;
        }
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj != null) {
            if (this == obj) {
                equal = true;
            } else if (obj.getClass().equals(getClass())) {
                JavaMethodParameter other = (JavaMethodParameter) obj;
                if (parent.equals(other.parent) && name.equals(other.name)) {
                    if (JavaServiceMappingUtil.parentIsArray(this)) {
                        equal =
                                JavaServiceMappingUtil.parentIsArray(other)
                                        && getIndex() == other.getIndex();
                    } else {
                        equal = true;
                    }
                }
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
        int code = 0;
        if (parent != null) {
            code = parent.hashCode();
        }
        return code + name.hashCode();
    }

    /**
     * Create an element of this parameter with the given <i>index</i>.
     * 
     * @param param
     * @param index
     * @return
     */
    public JavaMethodParameter createElement(int index) {
        return new JavaMethodParameter(this, index);
    }

    /**
     * Get the path (to be used for mapping source/target identification).
     * 
     * @return the path.
     */
    public String getPath() {
        return JavaConstants.PARAMETER_PREFIX
                + JavaConstants.PARAMETER_SEPARATOR + getQualifiedName();
    }

    /**
     * Get fully qualified element name of this parameter, if in a bean this
     * will provide the full path to this parameter from the method parameter.
     * The name of this parameter will not be decorated with the array element.
     * 
     * @see #getQualifiedName()
     * 
     * @return
     */
    public String getQualifiedElementName() {
        String path = internalGetPath();

        return path != null ? path + JavaConstants.PARAMETER_SEPARATOR
                + getElementName() : getElementName();
        // return path != null ? path : getElementName();
    }

    /**
     * Get fully qualified name of this parameter, if in a bean this will
     * provide the full path to this parameter from the method parameter. The
     * name of this parameter will be decorated with the array element.
     * 
     * @see #getQualifiedElementName()
     * 
     * @return
     */
    public String getQualifiedName() {
        String path = internalGetPath();
        // if (parent instanceof JavaMethodParameter) {
        // JavaMethodParameter parentParam = (JavaMethodParameter) parent;
        // if (parentParam.isArray()) {
        //                return path + "[" + getIndex() + "]"; //$NON-NLS-1$ //$NON-NLS-2$;
        // }
        // }
        return path != null ? path + JavaConstants.PARAMETER_SEPARATOR
                + getName() : getName();
    }

    /**
     * Get the qualified path of this parameter from the method parameter.
     * 
     * @return Full path of this parameter from the java method. If no path then
     *         <code>null</code> will be returned.
     */
    private String internalGetPath() {
        String path = ""; //$NON-NLS-1$
        if (parent instanceof JavaMethodParameter) {
            JavaMethodParameter parentParam = (JavaMethodParameter) parent;
            String parentPath = parentParam.internalGetPath();
            if (parentPath != null) {
                if (parentParam.isArray()) {
                    path += parentPath;
                    String parentName = parentParam.getName();
                    int sb = parentName.indexOf('[');
                    if (sb != -1) {
                        path += parentName.substring(sb);
                    }
                } else {
                    path +=
                            parentPath + JavaConstants.PARAMETER_SEPARATOR
                                    + parentParam.getName();
                }
            } else if (!parentParam.isArray()) {
                path += parentParam.getName();
            }
        }

        return "".equals(path) ? null : path; //$NON-NLS-1$
    }

}
