/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

/**
 * Content provider to return all of the Classes defined in a JSON Schema file.
 * 
 * @author nwilson
 * @since 2 Dec 2014
 */
public class UmlClassContentProvider implements ITreeContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider
     *      #getElements(java.lang.Object)
     * 
     * @param inputElement
     *            The UML Package.
     * @return An array of Class elements within the Package.
     */
    @Override
    public Object[] getElements(Object inputElement) {

        return getChildren(inputElement);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        List<Object> children = new ArrayList<>();
        if (parentElement instanceof Package) {
            Package pkg = (Package) parentElement;
            EList<PackageableElement> elements = pkg.getPackagedElements();
            for (PackageableElement element : elements) {
                if (element instanceof Class) {
                    children.add(element);
                }
            }
        } else if (parentElement instanceof Class) {
            Class cls = (Class) parentElement;
            children.addAll(cls.getOwnedAttributes());
        }
        return children.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (element instanceof EObject) {
            parent = ((EObject) element).eContainer();
        }
        return parent;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof Class) {
            Class cls = (Class) element;
            hasChildren = cls.getOwnedAttributes().size() > 0;
        }
        return hasChildren;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

}
