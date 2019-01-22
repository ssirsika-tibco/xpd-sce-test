/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.Objects;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Container class for Property objects used in the UmlTreeContentProvider. This
 * class help keep track of parent nodes for Property items.
 * 
 * @author nwilson
 * @since 18 Feb 2015
 */
public class UmlTreePropertyNode implements IWrapperItemProvider {
    private UmlTreePropertyNode parent;

    private Property item;

    /**
     * @param parent
     *            The parent property node, or null if the parent is the root
     *            class.
     * @param item
     *            The property item for this node.
     */
    public UmlTreePropertyNode(UmlTreePropertyNode parent, Property item) {
        super();
        this.parent = parent;
        this.item = item;
    }

    /**
     * @return The parent property node, or null if the parent is the root
     *         class.
     */
    public UmlTreePropertyNode getParent() {
        return parent;
    }

    /**
     * @return The property item for this node.
     */
    public Property getItem() {
        return item;
    }

    /**
     * @param property
     * @return
     */
    public boolean hasAncestor(Property property) {
        boolean hasAncestor = false;
        if (parent != null) {
            if (parent.getItem().equals(property)) {
                hasAncestor = true;
            } else {
                hasAncestor = parent.hasAncestor(property);
            }
        }
        return hasAncestor;
    }

    public boolean hasAncestorOfSameType() {
        boolean hasAncestor = false;
        Type type = item.getType();
        if (type instanceof Class) {
            Class cls = (Class) type;
            UmlTreePropertyNode node = this;
            while (node != null) {
                UmlTreePropertyNode parent = node.getParent();
                Class parentClass = null;
                if (parent == null) {
                    parentClass = node.getItem().getClass_();
                } else {
                    Type parentType = parent.getItem().getType();
                    if (parentType instanceof Class) {
                        parentClass = (Class) parentType;
                    }
                }
                if (cls.equals(parentClass)) {
                    hasAncestor = true;
                    break;
                }
                node = parent;
            }
        }
        return hasAncestor;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return item.hashCode() + (parent == null ? 0 : parent.hashCode());
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof UmlTreePropertyNode) {
            UmlTreePropertyNode other = (UmlTreePropertyNode) obj;
            equal =
                    item.equals(other.getItem())
                            && Objects.equals(parent, other.getParent());
        }
        return equal;
    }

    /**
     * @see org.eclipse.emf.edit.provider.IDisposable#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.emf.edit.provider.IWrapperItemProvider#getValue()
     * 
     * @return
     */
    @Override
    public Object getValue() {
        return getItem();
    }

    /**
     * @see org.eclipse.emf.edit.provider.IWrapperItemProvider#getOwner()
     * 
     * @return
     */
    @Override
    public Object getOwner() {
        return getParent();
    }

    /**
     * @see org.eclipse.emf.edit.provider.IWrapperItemProvider#getFeature()
     * 
     * @return
     */
    @Override
    public EStructuralFeature getFeature() {
        return UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE;
    }

    /**
     * @see org.eclipse.emf.edit.provider.IWrapperItemProvider#getIndex()
     * 
     * @return
     */
    @Override
    public int getIndex() {
        return item.getClass_().getOwnedAttributes().indexOf(item);
    }

    /**
     * @see org.eclipse.emf.edit.provider.IWrapperItemProvider#setIndex(int)
     * 
     * @param index
     */
    @Override
    public void setIndex(int index) {
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return item.getName();
    }

}
