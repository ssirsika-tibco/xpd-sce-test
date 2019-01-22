/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;

/**
 * 
 * Basically used this to share between {@link JavaMethod} and
 * {@link JavaMethodParameter}. However, {@link JavaMethod}s cannot be mapped to
 * or from in the mapper, as it wouldn't match with the types available.
 * 
 * @author rsomayaj
 * @since 3.3 (21 Jul 2010)
 */
public class JavaServiceMapperTreeItem extends MapperTreeItem {

    private JavaMethod javaMethod;

    private JavaMethodParameter javaMethodParameter;

    private final Object contextObject;

    /**
     * @param contextObject
     * @param mappingObject
     */
    public JavaServiceMapperTreeItem(Object contextObject, Object mappingObject) {
        super(mappingObject);
        this.contextObject = contextObject;
        if (mappingObject instanceof JavaMethod) {
            javaMethod = (JavaMethod) mappingObject;
        } else if (mappingObject instanceof JavaMethodParameter) {
            javaMethodParameter = (JavaMethodParameter) mappingObject;
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getParent()
     * 
     * @return
     */
    @Override
    public MapperTreeItem getParent() {
        if (javaMethod != null) {
            return null;
        } else if (javaMethodParameter != null) {
            return new JavaServiceMapperTreeItem(contextObject,
                    javaMethodParameter.getParent());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getType()
     * 
     * @return
     */
    @Override
    public Object getType() {
        if (javaMethodParameter != null) {
            return javaMethodParameter.getParameterType();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getNormalizedLabel()
     * 
     * @return
     */
    @Override
    protected String getNormalizedLabel() {
        if (javaMethod != null) {
            return javaMethod.getName();
        }
        if (javaMethodParameter != null) {
            return javaMethodParameter.getName();
        }
        return null;
    }
}
