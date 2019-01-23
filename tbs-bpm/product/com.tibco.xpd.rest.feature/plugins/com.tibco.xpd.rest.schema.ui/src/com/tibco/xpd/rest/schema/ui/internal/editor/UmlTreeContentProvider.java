/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.rest.schema.JsonSchemaUtil;

/**
 * Content provider for the JSON Schema tree structure. This has the root Class
 * at the top with a hierarchy of Property objects beneath it.
 * 
 * @author nwilson
 * @since 2 Dec 2014
 */
public class UmlTreeContentProvider implements ITreeContentProvider {

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
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     *            UML Package, Class or Property.
     * @return for Package returns the root Class, otherwise an array of child
     *         Property objects.
     */
    @Override
    public Object[] getElements(Object parentElement) {
        return getChildren(parentElement);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object inputElement) {

        List<Object> children = new ArrayList<>();
        if (inputElement instanceof Package) {
            Package pkg = (Package) inputElement;
            EList<PackageableElement> elements = pkg.getPackagedElements();
            for (PackageableElement element : elements) {
                if (element instanceof Class) {
                    Class cls = (Class) element;
                    if (new JsonSchemaUtil().isRootClass(cls)) {
                        children.add(element);
                    }
                }
            }
        } else if (inputElement instanceof Class) {
            Class cls = (Class) inputElement;
            for (Property property : cls.getAttributes()) {
                children.add(new UmlTreePropertyNode(null, property));
            }
        } else if (inputElement instanceof UmlTreePropertyNode) {
            UmlTreePropertyNode node = (UmlTreePropertyNode) inputElement;
            Property property = node.getItem();
            Type type = property.getType();
            if (type instanceof Class && type.eContainer() != null) {
                Class cls = (Class) type;
                for (Property child : cls.getAttributes()) {
                    children.add(new UmlTreePropertyNode(node, child));
                }
            }
        }
        return children.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     *            The element to get the parent for.
     * @return The parent object, or null if no parent.
     */
    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (element instanceof Class) {
            parent = ((Class) element).getPackage();
        } else if (element instanceof UmlTreePropertyNode) {
            parent = ((UmlTreePropertyNode) element).getParent();
            if (parent == null) {
                parent = ((UmlTreePropertyNode) element).getItem().getClass_();
            }
        }
        return parent;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     *            The element to check.
     * @return true if the element has children.
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof Package) {
            Package pkg = (Package) element;
            hasChildren = pkg.getPackagedElements().size() > 0;
        } else if (element instanceof Class) {
            Class cls = (Class) element;
            hasChildren = cls.getAttributes().size() > 0;
        } else if (element instanceof UmlTreePropertyNode) {
            UmlTreePropertyNode node = (UmlTreePropertyNode) element;
            Property property = node.getItem();
            if (!node.hasAncestorOfSameType()) {
                Type type = property.getType();
                if (type instanceof Class) {
                    hasChildren = true;
                }
            }
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
