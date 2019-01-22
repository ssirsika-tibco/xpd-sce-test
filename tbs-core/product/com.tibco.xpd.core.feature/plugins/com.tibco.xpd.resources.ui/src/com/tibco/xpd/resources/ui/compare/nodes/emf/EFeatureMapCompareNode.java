/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;

/**
 * COmpare node for Feature maps.
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class EFeatureMapCompareNode extends EMFCompareNode {

    private FeatureMap featureMap;

    private EObject parent;

    /**
     * @param level
     */
    public EFeatureMapCompareNode(FeatureMap featureMap, EObject parent,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(parentCompareNode, feature, compareNodeFactory);
        this.featureMap = featureMap;
        this.parent = parent;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return Wrapped featureMap
     */
    @Override
    public Object getObject() {
        return getFeatureMap();
    }

    /**
     * @return the featureMap
     */
    public FeatureMap getFeatureMap() {
        return featureMap;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        /* When doing feature maps - do simple attributes first. */
        for (Entry entry : featureMap) {
            Object child = entry.getValue();
            EStructuralFeature childFeature = entry.getEStructuralFeature();

            if (excludeChildFeature(childFeature, child)) {
                continue;
            }

            Object addCompareNode = null;

            if (child instanceof FeatureMap) {
                /*
                 * Add feature map as folder (don't think can be a direct child
                 * but hey-just in case!
                 */
                children.add(compareNodeFactory
                        .createForFeatureMap((FeatureMap) child,
                                parent,
                                childFeature,
                                this));

            } else if (childFeature instanceof EAttribute) {
                /* Attribute or simple type element. */
                children.add(compareNodeFactory
                        .createForEAttribute((EAttribute) childFeature,
                                child,
                                this));

            } else if (child instanceof EList) {
                /* Add list as folder */
                children.add(compareNodeFactory.createForEList((EList) child,
                        childFeature,
                        this));

            } else if (child instanceof EObject) {
                children.add(compareNodeFactory
                        .createForEObject((EObject) child,
                                EObjectCompareNode.NO_LIST_INDEX,
                                childFeature,
                                this));

            } else if (child != null) {
                children.add(compareNodeFactory.createForText(child.toString(),
                        child,
                        this));
            }

            if (addCompareNode != null) {
                if (addCompareNode instanceof XpdCompareNode) {
                    ((XpdCompareNode) addCompareNode)
                            .setAtomic(compareNodeFactory
                                    .isAtomic(childFeature, child, featureMap));
                }
                children.add(addCompareNode);
            }
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
     * Find the correct insertion point in this node's EFeatureMap for the give
     * source child node.
     * <p>
     * The equivalent of the source child in the target (this) EFeatureMap is
     * expected to <b>not</b> exist (addChild should not have been called if a
     * matching target object was found).
     * <p>
     * <p>
     * The insertion point is calculated by comparing objects surrounding the
     * source child in its parent feature map with objects in the target(this)
     * list.
     * <p>
     * <b>The comparison is performed on the ACTUAL EFeatureMap we contain NOT
     * our child list of compare nodes. This means that this method should still
     * work if the child list is not recached between multiple
     * insertions/removals etc.</b>
     * 
     * @param sourceChildNode
     * @param sourceFeatureMap
     * 
     * @return Insertion position in this list of objects for the given object
     *         (or -1 if cannot be ascertained).
     */
    public int calculateInsertionPoint(EMFCompareNode sourceChildNode,
            EFeatureMapCompareNode sourceFeatureMap) {

        /* Get the source node's index in source list. */
        int sourceChildNodeIndex;

        Object[] sourceChildren = sourceFeatureMap.getChildren();
        for (sourceChildNodeIndex = 0; sourceChildNodeIndex < sourceChildren.length; sourceChildNodeIndex++) {
            if (sourceChildren[sourceChildNodeIndex] == sourceChildNode) {
                break;
            }
        }

        if (sourceChildNodeIndex < sourceChildren.length) {

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
                    for (int targetIdx = 0; targetIdx < featureMap.size(); targetIdx++) {
                        Entry tgtEntry = featureMap.get(targetIdx);

                        /*
                         * The At least the feature should match (don't want to
                         * ask source child node for one feature to calc
                         * location of any old target feature in case they may
                         * be completely different types.
                         */
                        if (!tgtEntry.getEStructuralFeature().equals(srcSibling
                                .getFeature())) {
                            continue;
                        }

                        /*
                         * Use the sourceChildNode to get the location id for
                         * this target object in it's parent (the source child
                         * knows how to calculate location it for it's object
                         * int it's parent so must be able to calc it for target
                         * object in target list!).
                         */
                        Object tgtLocation =
                                sourceChildNode
                                        .calculateLocationInParent(tgtEntry
                                                .getValue(), tgtEntry
                                                .getEStructuralFeature(), this);

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
                    for (int targetIdx = 0; targetIdx < featureMap.size(); targetIdx++) {
                        Entry tgtEntry = featureMap.get(targetIdx);

                        /*
                         * The At least the feature should match (don't want to
                         * ask source child node for one feature to calc
                         * location of any old target feature in case they may
                         * be completely different types.
                         */
                        if (!tgtEntry.getEStructuralFeature().equals(srcSibling
                                .getFeature())) {
                            continue;
                        }

                        /*
                         * Use the sourceChildNode to get the location id for
                         * this target object in it's parent (the source child
                         * knows how to calculate location it for it's object
                         * int it's parent so must be able to calc it for target
                         * object in target list!).
                         */
                        Object tgtLocation =
                                sourceChildNode
                                        .calculateLocationInParent(tgtEntry
                                                .getValue(), tgtEntry
                                                .getEStructuralFeature(), this);

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
     * Copy the value of the given child node.
     * <p>
     * If its an EObject or EList then copy it, otherwise
     * 
     * @param sourceChildNode
     */
    private Object copyChildValue(EMFCompareNode sourceChildNode) {
        Object copyChildValue = sourceChildNode.getObject();

        if (sourceChildNode instanceof EObjectCompareNode) {
            copyChildValue =
                    EcoreUtil.copy(((EObjectCompareNode) sourceChildNode)
                            .getEObject());

        } else if (sourceChildNode instanceof EListCompareNode) {
            copyChildValue =
                    EcoreUtil.copyAll(((EListCompareNode) sourceChildNode)
                            .getEList());

        } else if (sourceChildNode instanceof EAttributeCompareNode) {
            copyChildValue =
                    ((EAttributeCompareNode) sourceChildNode)
                            .getEAttributeValue();
        }

        return copyChildValue;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#isAtomic()
     * 
     * @return
     */
    @Override
    public boolean isAtomic() {
        /*
         * SID XPD-1128 - there is a bad problem is addYourSelf() that I cannot
         * resolve - so ensure that whoel feature maps cannot be copied until
         * that is resolved.
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        if (true) {
            throw new RuntimeException(
                    "Add whole EFeatureMap has unresolved problems (the code below appears to work initially, and even works after an undo, but a subsequent reod COMPLETELY blows away the model and Save will create blank file.");
        }

        if (targetParent instanceof EObjectCompareNode) {
            Object targetObject =
                    ((EObjectCompareNode) targetParent).getEObject()
                            .eGet(getFeature());

            if (targetObject instanceof FeatureMap) {
                /*
                 * We can assume that the target map is empty else we would have
                 * been asked to replace not add.
                 * 
                 * So just add copies of our children into target map.
                 */
                FeatureMap targetMap = (FeatureMap) targetObject;

                List<Entry> copies = new ArrayList<Entry>();

                for (Entry entry : getFeatureMap()) {
                    EStructuralFeature childFeature =
                            entry.getEStructuralFeature();
                    Object childValue = entry.getValue();

                    Object copy = null;

                    if (childValue instanceof EObject) {
                        copy = EcoreUtil.copy((EObject) childValue);

                    } else if (childValue instanceof EList) {
                        copy = EcoreUtil.copyAll((EList) childValue);
                    } else {
                        copy = childValue;
                    }

                    Entry createEntry =
                            FeatureMapUtil
                                    .createEntry(childFeature, childValue);
                    copies.add(createEntry);
                }

                targetMap.addAll(copies);

                return copies;
            }

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
        if (true) {
            throw new RuntimeException("Remove whole EFeatureMap is untested.");
        }
        if (targetParent instanceof EObjectCompareNode) {

            Object targetObject =
                    ((EObjectCompareNode) targetParent).getEObject()
                            .eGet(getFeature());

            if (targetObject instanceof FeatureMap) {
                FeatureMap targetMap = (FeatureMap) targetObject;

                targetMap.clear();

                return;
            }
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
        if (true) {
            throw new RuntimeException("replace whole EFeatureMap is untested.");
        }

        if (targetParent instanceof EObjectCompareNode) {

            Object targetObject =
                    ((EObjectCompareNode) targetParent).getEObject()
                            .eGet(getFeature());

            if (targetObject instanceof FeatureMap) {
                /* Remove all the existing entries. */
                FeatureMap targetMap = (FeatureMap) targetObject;

                targetMap.clear();

                /* Add copies of all. */
                return doAddYourself(targetParent);

            }
        }

        throw new RuntimeException(
                "Problem encountered replacing equivalent of: '" + this + "' In: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
