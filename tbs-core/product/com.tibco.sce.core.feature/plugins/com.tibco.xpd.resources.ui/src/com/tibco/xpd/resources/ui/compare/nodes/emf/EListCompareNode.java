/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;

/**
 * Compare node for an EList
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class EListCompareNode extends EMFCompareNode {

    private EList eList;

    /**
     * @param level
     */
    public EListCompareNode(EList eList, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(parentCompareNode, feature, compareNodeFactory);
        this.eList = eList;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return Wrapped EList
     */
    @Override
    public Object getObject() {
        return getEList();
    }

    /**
     * @return the eList
     */
    public EList getEList() {
        return eList;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        int index = 0;

        EStructuralFeature feature = getFeature();

        for (Object child : eList) {
            Object addCompareNode = null;

            if (child instanceof EObject) {
                if (!excludeChildFeature(feature, child)) {
                    addCompareNode =
                            compareNodeFactory
                                    .createForEObject((EObject) child, index,
                                    // TODO - I THINK THIS MAY ONLY WORKS FOR
                                            // WRAPPED LISTS like those in
                                            // process model?!
                                            feature,
                                            this);
                }

            } else if (child != null) {
                if (true) {
                    throw new RuntimeException(
                            "Code for non-EObject child of EList is untried");
                }

                if (!excludeChildFeature(feature, child)) {
                    addCompareNode =
                            compareNodeFactory.createForText(child.toString(),
                                    child,
                                    this);
                }
            }

            if (addCompareNode != null) {
                if (addCompareNode instanceof XpdCompareNode) {
                    ((XpdCompareNode) addCompareNode)
                            .setAtomic(compareNodeFactory.isAtomic(feature,
                                    child,
                                    eList));
                }
                children.add(addCompareNode);
            }

            index++;
        }

        return children.toArray();
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return XpdResourcesUIActivator.getDefault().getImageRegistry()
                .get(XpdResourcesUIConstants.ICON_COMPARATOR_FOLDER_NODE);
    }

    /**
     * Find the correct insertion point in this node's EList for the give source
     * child node.
     * <p>
     * The equivalent of the source child in the taeget (this) EList is expected
     * to <b>not</b> exist (addChild should not have been called if a matching
     * target object was found).
     * <p>
     * The insertion point is calculated by comparing objects surrounding the
     * source child in its parent list with objects in the target(this) list.
     * <p>
     * <b>The comparison is performed on the ACTUAL EList we contain NOT our
     * child list of compare nodes. This means that this method should still
     * work if teh child list is not recached between multiple
     * insertions/removals etc.</b>
     * 
     * @param sourceChildNode
     * @param sourceList
     * 
     * @return Insertion position in this list of objects for the given object
     *         (or -1 if cannot be ascertained).
     */
    public int calculateInsertionPoint(EMFCompareNode sourceChildNode,
            EListCompareNode sourceList) {

        /* Get the source node's index in source list. */
        int sourceChildNodeIndex;

        Object[] sourceChildren = sourceList.getChildren();
        for (sourceChildNodeIndex = 0; sourceChildNodeIndex < sourceChildren.length; sourceChildNodeIndex++) {
            if (sourceChildren[sourceChildNodeIndex] == sourceChildNode) {
                break;
            }
        }

        if (sourceChildNodeIndex < sourceChildren.length) {
            EList thisChildren = getEList();

            /*
             * First work towards the beginning of source list looking for match
             * item.
             */
            for (int sourceIdx = sourceChildNodeIndex - 1; sourceIdx >= 0; sourceIdx--) {
                if (sourceChildren[sourceIdx] instanceof EMFCompareNode) {
                    EMFCompareNode srcSibling =
                            (EMFCompareNode) sourceChildren[sourceIdx];

                    Object srcLocation = srcSibling.getLocationInParent();

                    /* Find this object in target list. */
                    for (int targetIdx = 0; targetIdx < thisChildren.size(); targetIdx++) {
                        Object targetChild = thisChildren.get(targetIdx);

                        /*
                         * Use the sourceChildNode to get the location id for
                         * this target object in it's parent (the source child
                         * knows how to calculate location it for it's object
                         * int it's parent so must be able to calc it for target
                         * object in target list!).
                         */
                        Object tgtLocation =
                                sourceChildNode
                                        .calculateLocationInParent(targetChild,
                                                srcSibling.getFeature(),
                                                this);

                        if (srcLocation.equals(tgtLocation)) {
                            /*
                             * Found prior object in source list in our target
                             * list, so should be able to just insert after that
                             * object in target list.
                             */
                            return targetIdx + 1;
                        }

                    } /* Next object in target eList list. */
                }

            } /* Get next object preceding sourceChild in sourceList. */

            /*
             * Then if haven't found a matching prior item in list find an item
             * that matches any object after the source object in its list and
             * insert before that.
             */
            for (int sourceIdx = sourceChildNodeIndex + 1; sourceIdx < sourceChildren.length; sourceIdx++) {
                if (sourceChildren[sourceIdx] instanceof EMFCompareNode) {
                    EMFCompareNode srcSibling =
                            (EMFCompareNode) sourceChildren[sourceIdx];

                    Object srcLocation = srcSibling.getLocationInParent();

                    /* Find this object in target list. */
                    for (int targetIdx = 0; targetIdx < thisChildren.size(); targetIdx++) {
                        Object targetChild = thisChildren.get(targetIdx);

                        /*
                         * Use the sourceChildNode to get the location id for
                         * this target object in it's parent (the source child
                         * knows how to calculate location it for it's object
                         * int it's parent so must be able to calc it for target
                         * object in target list!).
                         */
                        Object tgtLocation =
                                sourceChildNode
                                        .calculateLocationInParent(targetChild,
                                                srcSibling.getFeature(),
                                                this);

                        if (srcLocation.equals(tgtLocation)) {
                            /*
                             * Found post-src-object in source list in our
                             * target list, so should be able to just insert
                             * after that object in target list.
                             */
                            return targetIdx;
                        }

                    } /* Next object in target eList list. */
                }
            } /* Get next object succeeding sourceChild in sourceList. */
        }

        return -1;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        Collection copy = EcoreUtil.copyAll(getEList());

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    copy);
            return copy;
        }

        throw new RuntimeException(
                "Problem encountered adding: '" + this + "' To: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$    
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject()
                    .eUnset(getFeature());
            return;
        }

        throw new RuntimeException(
                "Problem encountered removing: '" + this + "' From: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      EMFCompareNode)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        Collection copy = EcoreUtil.copyAll(getEList());

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    copy);
            return copy;
        }
        throw new RuntimeException(
                "Problem encountered replacing equivalent of: '" + this + "' In: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
