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
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Class representing an {@link EObject} node compare editor's comparison tree.
 * <p>
 * This wraps the given EObject to present it in the tree view.
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class EObjectCompareNode extends EMFCompareNode {

    public static int NO_LIST_INDEX = -1;

    private EObject eObject;

    private int listIndex = NO_LIST_INDEX;

    /**
     * 
     * @param eObject
     * @param listIndex
     *            list index if EObject is contained within EList or
     *            {@link #NO_LIST_INDEX} if not a member of a list.
     * @param feature
     *            The feature for the given object in it's parent.
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public EObjectCompareNode(EObject eObject, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(parentCompareNode, feature, compareNodeFactory);
        this.eObject = eObject;
        this.listIndex = listIndex;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return wrapped Eobject
     */
    @Override
    public Object getObject() {
        return getEObject();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return By default returns child elements / attributes.
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        boolean hasInternalPropertiesAncestor =
                hasInternalPropertiesAncestor(this);

        boolean hasNonAtomicChildren = false;

        EClass eClass = eObject.eClass();

        /*
         * Append child attributes
         */
        for (EAttribute eAttribute : eClass.getEAllAttributes()) {
            if (eObject.eIsSet(eAttribute)) {
                Object child = eObject.eGet(eAttribute);

                if (excludeChildFeature(eAttribute, child)) {
                    continue;
                }

                /*
                 * Ignore non atomic children - they will be placed inside a
                 * wrapper InternalPropertiesNode.
                 */
                boolean childIsAtomic =
                        compareNodeFactory.isAtomic(eAttribute, child, eObject);
                if (!childIsAtomic) {
                    hasNonAtomicChildren = true;

                    /*
                     * But only if we're not already within InternalProperties
                     * folder in which case we want to include everything.
                     */
                    if (!hasInternalPropertiesAncestor) {
                        continue;
                    }
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
                        ((XpdCompareNode) addCompareNode)
                                .setAtomic(childIsAtomic);
                    }
                }
            }
        }

        /* Then objects */
        EList<EReference> eReferences = eClass.getEAllReferences();
        for (EReference eReference : eReferences) {
            /*
             * Only include reference IF it is set AND it is a containment
             * reference (a non-containment eReference is a reference to a model
             * object in a DIFFERENT part of the model).
             */
            if (eObject.eIsSet(eReference) && eReference.isContainment()) {
                Object child = eObject.eGet(eReference);

                if (excludeChildFeature(eReference, child)) {
                    continue;
                }

                /*
                 * Ignore non atomic children - they will be placed inside a
                 * wrapper InternalPropertiesNode.
                 */
                boolean childIsAtomic =
                        compareNodeFactory.isAtomic(eReference, child, eObject);
                if (!childIsAtomic) {
                    hasNonAtomicChildren = true;

                    /*
                     * But only if we're not already within InternalProperties
                     * folder in which case we want to include everything.
                     */
                    if (!hasInternalPropertiesAncestor) {
                        continue;
                    }
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
                    addCompareNode =
                            compareNodeFactory
                                    .createForEObject((EObject) child,
                                            EObjectCompareNode.NO_LIST_INDEX,
                                            eReference,
                                            this);

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
                        ((XpdCompareNode) addCompareNode)
                                .setAtomic(childIsAtomic);
                    }
                }
            }
        }

        if (hasNonAtomicChildren) {
            /*
             * Wrap up all non-atomic nodes in a single atomic
             * internalPropertiesNode
             * 
             * But only if we're not already within InternalProperties folder in
             * which case we want to include everything.
             */
            if (!hasInternalPropertiesAncestor) {
                EObjectInternalPropertiesNode internalPropsNode =
                        new EObjectInternalPropertiesNode(this);

                internalPropsNode.setAtomic(true);

                children.add(0, internalPropsNode);
            }
        }

        return children.toArray();
    }

    /**
     * @param objectCompareNode
     * @return <code>true</code> if the given node has an ancestor of type
     *         {@link EObjectInternalPropertiesNode}
     */
    private boolean hasInternalPropertiesAncestor(Object objectCompareNode) {
        if (objectCompareNode instanceof EObjectInternalPropertiesNode) {
            return true;

        } else if (objectCompareNode instanceof IChildCompareNode) {
            objectCompareNode =
                    ((IChildCompareNode) objectCompareNode).getParent();

            if (objectCompareNode != null) {
                return hasInternalPropertiesAncestor(objectCompareNode);
            }
        }

        return false;
    }

    /**
     * @return the eObject
     */
    public EObject getEObject() {
        return eObject;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        Image img = WorkingCopyUtil.getImage(eObject);
        if (img == null) {
            img = super.getImage();
        }
        return img;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getName()
     * 
     * @return Default name + "[index]" if element is in a list.
     */
    @Override
    public String getName() {
        String name = super.getName();

        if (listIndex != NO_LIST_INDEX) {
            name += "[" + listIndex + "]";
        }

        return name;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#calculateLocationInParent(java.lang.Object,
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

        String ret =
                super.calculateLocationInParent(object, feature, parentNode);

        /*
         * When in a list then qualify location string with position in list.
         */
        if (parentNode instanceof EListCompareNode) {
            int idxInParent =
                    ((EListCompareNode) parentNode).getEList().indexOf(object);

            ret += "[" + idxInParent + "]";
        }

        return ret;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        EObject copy = EcoreUtil.copy(getEObject());

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    copy);
            return copy;

        } else if (targetParent instanceof EListCompareNode) {
            EListCompareNode targetList = (EListCompareNode) targetParent;

            if (this.getParent() instanceof EListCompareNode) {
                EListCompareNode sourceList =
                        (EListCompareNode) this.getParent();

                /*
                 * Ask the target list where best to add ourselves, this will be
                 * based upon our position relative to our siblings (if
                 * possible).
                 */
                int targetIdx =
                        targetList.calculateInsertionPoint(this, sourceList);
                if (targetIdx >= 0) {
                    /* Insert at given position. */
                    targetList.getEList().add(targetIdx, copy);

                } else {
                    /* Could not find appropriate location so append to end. */
                    targetList.getEList().add(targetIdx, copy);
                }

                return copy;
            }

        } else if (targetParent instanceof EFeatureMapCompareNode) {
            EFeatureMapCompareNode targetMap =
                    (EFeatureMapCompareNode) targetParent;

            if (this.getParent() instanceof EFeatureMapCompareNode) {
                EFeatureMapCompareNode sourceMap =
                        (EFeatureMapCompareNode) this.getParent();

                /*
                 * Ask the target list where best to add ourselves, this will be
                 * based upon our position relative to our siblings (if
                 * possible).
                 */
                int targetIdx =
                        targetMap.calculateInsertionPoint(this, sourceMap);

                if (targetIdx >= 0) {
                    /* Insert at given position. */
                    targetMap.getFeatureMap().add(targetIdx,
                            this.getFeature(),
                            copy);

                } else {
                    /* Could not find appropriate location so append to end. */
                    targetMap.getFeatureMap().add(this.getFeature(), copy);
                }

                return copy;
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
        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject()
                    .eUnset(getFeature());
            return;

        } else if (targetParent instanceof EListCompareNode) {
            EListCompareNode targetList = (EListCompareNode) targetParent;

            /* FInd our object in parent list and remove it. */
            if (targetList.getEList().contains(this.getObject())) {
                targetList.getEList().remove(this.getObject());
            }

            /*
             * We won't compalain if we didn't find it as a reparented object
             * may not exist any more.
             */
            return;

        } else if (targetParent instanceof EFeatureMapCompareNode) {
            EFeatureMapCompareNode targetMap =
                    (EFeatureMapCompareNode) targetParent;

            /* FInd our object in parent map and remove it. */
            for (Entry targetEntry : targetMap.getFeatureMap()) {
                EStructuralFeature tgtFeature =
                        targetEntry.getEStructuralFeature();
                Object tgtValue = targetEntry.getValue();

                if (tgtFeature == this.getFeature() && tgtValue == getObject()) {
                    targetMap.getFeatureMap().remove(targetEntry);
                }
            }

            /*
             * We won't compalain if we didn't find it as a reparented object
             * may not exist any more.
             */
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
        EObject copy = EcoreUtil.copy(getEObject());

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    copy);
            return copy;

        } else if (targetParent instanceof EListCompareNode) {
            EListCompareNode targetList = (EListCompareNode) targetParent;

            /* FInd our object in parent list and replace it. */
            int idx = targetList.getEList().indexOf(targetNode.getObject());
            if (idx >= 0) {
                targetList.getEList().set(idx, copy);

                return copy;
            }

        } else if (targetParent instanceof EFeatureMapCompareNode) {
            EFeatureMapCompareNode targetMap =
                    (EFeatureMapCompareNode) targetParent;

            /* FInd our object in parent map and replace it. */
            FeatureMap featureMap = targetMap.getFeatureMap();

            for (int i = 0; i < featureMap.size(); i++) {
                Entry targetEntry = featureMap.get(i);

                EStructuralFeature tgtFeature =
                        targetEntry.getEStructuralFeature();
                Object tgtValue = targetEntry.getValue();

                if (tgtFeature == this.getFeature()
                        && tgtValue == targetNode.getObject()) {
                    featureMap.setValue(i, copy);
                    return copy;
                }
            }
        }

        throw new RuntimeException(
                "Problem encountered replacing equivalent of: '" + this + "' In: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
