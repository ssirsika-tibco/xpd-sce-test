/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.compare;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;

/**
 * Specialised compare node that wraps up org model NamedElement EObject type
 * content.
 * 
 * @author aallway
 * @since 6 Dec 2010
 */
public class OMNamedElementCompareNode extends EObjectCompareNode {

    private NamedElement namedElement;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public OMNamedElementCompareNode(NamedElement namedElement, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(namedElement, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.namedElement = namedElement;
    }

    /**
     * @return the namedElement
     */
    public NamedElement getNamedElement() {
        return namedElement;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#calculateLocationInParent(java.lang.Object,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param object
     * @param feature
     * @param parentNode
     * @return
     */
    @Override
    public String calculateLocationInParent(Object object,
            EStructuralFeature feature, Object parentNode) {
        return namedElement.getId();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        String name = namedElement.getDisplayName();
        if (name == null || name.length() == 0) {
            name = namedElement.getName();
        }

        if (name == null || name.length() == 0) {
            name = super.getName();
        }
        return name;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        props.add(new XpdPropertyInfoNode("Name: " + getName(), 1, this, this
                .getType(), "nameInfo"));

        return props;
    }
}
