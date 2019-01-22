/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdTextCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EAttributeCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EFeatureMapCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareDiffViewer;

/**
 * Factory for creating generic compare nodes for EMF model trees.
 * 
 * <p>
 * By default the standard EMF model related objects (like
 * {@link EObjectCompareNode} return their raw contents.
 * 
 * <p>
 * You can subclass this factory to create specialised {@link XpdCompareNode} 's
 * for particular model elements.
 * 
 * <p>
 * For instance you could create a specific type for an element that ignored
 * certain children (or made it look objects from other parts of the model are
 * children) by subclassing {@link EObjectCompareNode#createChildren()}
 * 
 * <p>
 * Note: The compare nodes returned do not have to be instances of
 * {@link XpdCompareNode} <b>but if they are not</b> then you should ensure that
 * they implement the following interfaces:
 * <li>{@link ITypedElement}</li>
 * <li>{@link IStructureComparator}</li>
 * And optionally:
 * <li>{@link IDisposable} (the entire tree content will be disposed when
 * {@link EMFCompareStructureCreator} is disposed).</li>
 * <li>{@link IChildCompareNode} if you wish to use left/right merge viewer
 * based upon {@link EMFCompareDiffViewer}
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class EMFCompareNodeFactory {

    private String compareNodeContentType;

    /**
     * 
     * @param compareNodeContentType
     *            When factory used via the
     *            org.eclipse.compare.contentMergeViewers then the
     *            compareNodeContentType should match the contribution's
     *            "extensions" property.
     */
    public EMFCompareNodeFactory(String compareNodeContentType) {
        this.compareNodeContentType = compareNodeContentType;
    }

    /**
     * Create compare node for EObject
     * 
     * @param eObject
     * @param listIndex
     *            list index if EObject is contained within EList or
     *            {@link #NO_LIST_INDEX} if not a member of a list.
     * @param feature
     *            The feature for the given object in it's parent.
     * @return compare node
     */
    public Object createForEObject(EObject eObject, int listIndex,
            EStructuralFeature feature, Object parentCompareNode) {
        return new EObjectCompareNode(eObject, listIndex, feature,
                parentCompareNode, this);
    }

    /**
     * Create compare node for given attribute in given parent.
     * 
     * @param eAttribute
     * @param value
     * 
     * @return compare node.
     */
    public Object createForEAttribute(EAttribute eAttribute, Object value,
            Object parentCompareNode) {
        return new EAttributeCompareNode(eAttribute, value, parentCompareNode,
                this);
    }

    /**
     * Create a compare node for given elist
     * 
     * @param eList
     * @param feature
     * @return compare node.
     */
    public Object createForEList(EList eList, EStructuralFeature feature,
            Object parentCompareNode) {
        return new EListCompareNode(eList, feature, parentCompareNode, this);
    }

    /**
     * Create a compare node for given feature map
     * 
     * @param eList
     * @param feature
     * @return compare node.
     */
    public Object createForFeatureMap(FeatureMap featureMap, EObject parent,
            EStructuralFeature feature, Object parentCompareNode) {
        return new EFeatureMapCompareNode(featureMap, parent, feature,
                parentCompareNode, this);
    }

    /**
     * Create compare node for textual comparison.
     * 
     * @param text
     * @param rawObject
     *            The raw object that this text node represents.
     * @param parentCompareNode
     * 
     * @return compare node.
     */
    public Object createForText(String text, Object rawObject,
            Object parentCompareNode) {
        return new XpdTextCompareNode(text, rawObject, parentCompareNode,
                getCompareNodeContentType());
    }

    /**
     * @return the compareNodeContentType
     */
    public String getCompareNodeContentType() {
        return compareNodeContentType;
    }

    /**
     * Return true if the node to be created for the given feature of the given
     * EObjectCompareNode should be treated as atomic (otherwise it is treated
     * as an internal property).
     * <p>
     * Atomic nodes are always visible in the diff / merge content viewers. Only
     * atomic nodes can be copied in their own right (i.e. non-atomic nodes
     * cannot be merged in isolation.
     * <p>
     * Override to specialise your content (most models will want to do this to
     * limit the content that is shown to the user and to prevent parital
     * constructs being merged when this is not permitted.
     * 
     * @param feature
     * @param value
     * @param parentModelObject
     * 
     * @return <code>true</code> if object is atomic.
     */
    public boolean isAtomic(EStructuralFeature feature, Object value,
            Object parentModelObject) {
        /*
         * Treat everything as atomic by default, allow unrestricted merging in
         * isolation
         */
        return true;
    }
}
