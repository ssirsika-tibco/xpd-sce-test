/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.script.FieldParserException;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * The content provider for the java (pojo) mapper.
 * 
 * @author njpatel
 */
public class PojoItemProvider implements ITreeContentProvider {

    private final MappingDirection direction;

    /**
     * Constructor.
     * 
     * @param adapterFactory
     * @param direction
     * @param dest
     */
    public PojoItemProvider(MappingDirection direction) {
        this.direction = direction;
    }

    public Object[] getChildren(Object object) {
        List<Object> children = new ArrayList<Object>();

        try {
            if (object instanceof Activity) {
                JavaMethod method = JavaServiceMappingUtil
                        .getJavaMethod((Activity) object);
                if (method != null) {
                    // Call getReturnParam to ensure model is valid.
                    method.getReturnParam();
                    children.add(method);
                }
            } else if (object instanceof JavaMethod) {
                JavaMethod method = (JavaMethod) object;
                children.addAll(JavaServiceMappingUtil.getJavaMethodParameters(
                        method, direction));
            } else if (object instanceof JavaMethodParameter) {
                children.addAll(JavaServiceMappingUtil.getJavaMethodParameters(
                        (JavaMethodParameter) object, direction));
            }
        } catch (FieldParserException e) {
            e.printStackTrace();
        } catch (JavaModelException e) {
            // Ignore
        }

        return children.toArray();
    }

    public Object getParent(Object object) {

        if (object instanceof JavaMethodParameter) {
            return ((JavaMethodParameter) object).getParent();
        }

        return null;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    /**
     * @param inputElement
     * @return
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
