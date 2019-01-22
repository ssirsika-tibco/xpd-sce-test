/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.Messages;

/**
 * This compare node is used to wrap up all non-atomic children of an EObject
 * compare node.
 * <p>
 * This is so that non-atomic children can be treated as a whole unit for
 * comparison and merge (so basically all the nitty-gritty elements can be
 * copied as a single entity).
 * <p>
 * It MUST be created as a child of an {@link EObjectCompareNode} and will mimic
 * that node (when asked for the EObject, the feature and so on) so that child
 * attributes will copy themselves into correct EObject etc).
 * 
 * @author aallway
 * @since 16 Nov 2010
 */
public class EObjectInternalPropertiesNode extends EMFCompareNode {

    private EObjectCompareNode parentEObjectNode;

    /* The EObject that these properties belong to. */
    private EObject eObject;

    /**
     * @param object
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public EObjectInternalPropertiesNode(EObjectCompareNode parentCompareNode) {
        super(parentCompareNode, parentCompareNode.getFeature(),
                parentCompareNode.getCompareNodeFactory());
        parentEObjectNode = parentCompareNode;
        eObject = parentCompareNode.getEObject();
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
        return parentEObjectNode.calculateLocationInParent(object,
                feature,
                parentNode)
                + ".internalproperties"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        /*
         * The given target parent SHOULD be the target equivalent of THIS
         * node's EObjectCompareNode parent.
         * 
         * All the children THIS node's of this InternalPropertiesNode should be
         * capable of adding themselves to that EObjectCompareNode (to them it
         * will be as if they were under the EOIbjectCompareNode directly rather
         * than in this virutal wrapper).
         */
        for (Object child : getChildren()) {
            if (child instanceof EMFCompareNode) {
                ((EMFCompareNode) child).doAddYourself(targetParent);
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        /*
         * The given target parent SHOULD be the target equivalent of THIS
         * node's EObjectCompareNode parent.
         * 
         * All the children THIS node's of this InternalPropertiesNode should be
         * capable of removing themselves from that EObjectCompareNode (to them
         * it will be as if they were under the EOIbjectCompareNode directly
         * rather than in this virutal wrapper).
         */
        for (Object child : getChildren()) {
            if (child instanceof EMFCompareNode) {
                ((EMFCompareNode) child).doRemoveYourself(targetParent);
            }
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode)
     * 
     * @param targetParent
     * @param targetNode
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        /*
         * The given target parent SHOULD be the target equivalent of THIS
         * node's EObjectCompareNode parent.
         * 
         * All the children THIS node's of this InternalPropertiesNode should be
         * capable of adding themselves to that EObjectCompareNode (to them it
         * will be as if they were under the EOIbjectCompareNode directly rather
         * than in this virutal wrapper).
         */
        for (Object child : getChildren()) {
            if (child instanceof EMFCompareNode) {
                ((EMFCompareNode) child).doReplaceYourself(targetParent,
                        targetNode);
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#createChildren(com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        /*
         * Get all the non atomic children
         */

        EClass eClass = eObject.eClass();

        /*
         * Append child attributes
         */
        for (EAttribute eAttribute : eClass.getEAllAttributes()) {
            if (eObject.eIsSet(eAttribute)) {
                Object child = eObject.eGet(eAttribute);

                if (parentEObjectNode.excludeChildFeature(eAttribute, child)) {
                    continue;
                }

                /*
                 * Ignore atomic children - they will be placed directly
                 * underneath the parent eobject compare node
                 */
                if (compareNodeFactory.isAtomic(eAttribute, child, eObject)) {
                    continue;
                }

                Object addCompareNode = null;

                if (child instanceof FeatureMap) {
                    /* Add feature map as folder */
                    addCompareNode =
                            compareNodeFactory
                                    .createForFeatureMap((FeatureMap) child,
                                            eObject,
                                            eAttribute,
                                            this);

                } else if (child != null) {
                    addCompareNode =
                            compareNodeFactory.createForEAttribute(eAttribute,
                                    eObject.eGet(eAttribute),
                                    this);
                }

                if (addCompareNode != null) {
                    children.add(addCompareNode);

                    if (addCompareNode instanceof XpdCompareNode) {
                        ((XpdCompareNode) addCompareNode).setAtomic(false);
                    }
                }
            }
        }

        /* Then objects */
        EList<EReference> eReferences = eClass.getEAllReferences();
        for (EReference eReference : eReferences) {
            if (eObject.eIsSet(eReference)) {
                Object child = eObject.eGet(eReference);

                if (parentEObjectNode.excludeChildFeature(eReference, child)) {
                    continue;
                }

                /*
                 * Ignore atomic children - they will be placed directly
                 * underneath the parent eobject compare node
                 */
                if (compareNodeFactory.isAtomic(eReference, child, eObject)) {
                    continue;
                }

                Object addCompareNode = null;

                if (child instanceof FeatureMap) {
                    /* Add feature map as folder */
                    addCompareNode =
                            compareNodeFactory
                                    .createForFeatureMap((FeatureMap) child,
                                            eObject,
                                            eReference,
                                            this);

                } else if (child instanceof EObject) {
                    if (!EcoreUtil.isAncestor((EObject) child, eObject)) {
                        addCompareNode =
                                compareNodeFactory
                                        .createForEObject((EObject) child,
                                                EObjectCompareNode.NO_LIST_INDEX,
                                                eReference,
                                                this);
                    }
                } else if (child instanceof EList) {
                    /* Add list as folder */
                    addCompareNode =
                            compareNodeFactory.createForEList((EList) child,
                                    eReference,
                                    this);

                } else if (child != null) {
                    addCompareNode =
                            compareNodeFactory.createForText(child.toString(),
                                    child,
                                    this);
                }

                if (addCompareNode != null) {
                    children.add(addCompareNode);

                    if (addCompareNode instanceof XpdCompareNode) {
                        ((XpdCompareNode) addCompareNode).setAtomic(false);
                    }
                }
            }
        }

        return children.toArray();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return
     */
    @Override
    public Object getObject() {
        return eObject;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.EObjectInternalPropertiesNode_InternalProps_label;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return XpdResourcesUIActivator.getDefault().getImageRegistry()
                .get(XpdResourcesUIConstants.ICON_INTERNAL_PROPS_COMPARENODE);
    }
}
