/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * Compare node for process unique id elements.
 * <p>
 * This is same as basic {@link EObjectCompareNode} behaviour <b>except</b> that
 * when comparing for location within model tree it will use the element's
 * unique id.
 * <p>
 * This ({@link #getLocationInParent()} is used in reference to whether a model
 * object in one model is equal to an object in another copy of the model. i.e.
 * This is used by the StructuredDiffViewer's differencer to see if an object in
 * one tree (say an old revision) is the equivalent of an object in another tree
 * and hence knows whether to compare the two for content equivalence or whether
 * it should be treated as an addition.
 * 
 * 
 * @author aallway
 * @since 4 Oct 2010
 */
public class UniqueIdElementCompareNode extends EObjectCompareNode {

    /**
     * @param uniqueIdElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public UniqueIdElementCompareNode(UniqueIdElement uniqueIdElement,
            int listIndex, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(uniqueIdElement, listIndex, feature, parentCompareNode,
                compareNodeFactory);
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
        if (object instanceof UniqueIdElement) {
            return ((UniqueIdElement) object).getId();
        }

        throw new RuntimeException(
                "Internal Error: Should never be passed a non-unique id element."); //$NON-NLS-1$
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

        if (false) {
            props.add(new XpdPropertyInfoNode(
                    Messages.UniqueIdElementCompareNode_Id_label
                            + ((UniqueIdElement) getEObject()).getId(), 100,
                    this, getCompareNodeFactory().getCompareNodeContentType(),
                    "uniqueElementId")); //$NON-NLS-1$
        }

        return props;
    }
}
