/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Class representing an EAttribute within an emf model tree (an xml attribute /
 * simple content type element).
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class EAttributeCompareNode extends EMFCompareNode {

    private EAttribute eAttribute;

    private Object value;

    /**
     * @param attribute
     * @param parent
     */
    public EAttributeCompareNode(EAttribute eAttribute, Object value,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(parentCompareNode, eAttribute, compareNodeFactory);

        this.eAttribute = eAttribute;
        this.value = value;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return Wrapped raw value.
     */
    @Override
    public Object getObject() {
        return getEAttributeValue();
    }

    /**
     * @return the value
     */
    public Object getEAttributeValue() {
        return value;
    }

    /**
     * @return the eAttribute
     */
    public EAttribute getEAttribute() {
        return eAttribute;
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
        String location =
                super.calculateLocationInParent(object, feature, parentNode);

        if (parentNode instanceof EListCompareNode) {
            if (true) {
                throw new RuntimeException(
                        "Code for non-EObject child of EList is untried");
            }

            /*
             * If this is a simple value attribute in a list then use a
             * combinaiton of feature name AND value as there may be multiple
             * items in list of same feature.
             */
            String text =
                    getEAttributeValue() != null ? getEAttributeValue()
                            .toString() : "?"; //$NON-NLS-1$
            location = location + "[" + text + "]"; //$NON-NLS-1$
        }
        return location;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        /*
         * Return children as text node containing the value of this attribute /
         * simple content type element.
         */
        return new Object[] { compareNodeFactory
                .createForText(value.toString(), value, this) };
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getTextContent()
     * 
     * @return
     */
    @Override
    public String getTextContent() {
        return super.getTextContent();
        //        return value != null ? value.toString() : ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        Image img = WorkingCopyUtil.getImage(eAttribute);
        if (img == null) {
            img = super.getImage();
        }
        return img;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    getEAttributeValue());
            return getEAttributeValue();

        } else if (targetParent instanceof EListCompareNode) {
            if (true) {
                throw new RuntimeException(
                        "Code for non-EObject child of EList is untried");
            }

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
                    targetList.getEList().add(targetIdx, getEAttributeValue());

                } else {
                    /* Could not find appropriate location so append to end. */
                    targetList.getEList().add(targetIdx, getEAttributeValue());
                }

                return getEAttributeValue();
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
                            getEAttributeValue());

                } else {
                    /* Could not find appropriate location so append to end. */
                    targetMap.getFeatureMap().add(this.getFeature(),
                            getEAttributeValue());
                }

                return getEAttributeValue();
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
            if (true) {
                throw new RuntimeException(
                        "Code for non-EObject child of EList is untried");
            }

            EListCompareNode targetList = (EListCompareNode) targetParent;

            /* FInd our object in parent list and remove it. */
            if (targetList.getEList().contains(this.getObject())) {
                targetList.getEList().remove(this.getObject());

                return;
            }

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
                    return;
                }
            }

        }

        throw new RuntimeException(
                "Problem encountered removing: '" + this + "' From: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @param sourceNode
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent, EMFCompareNode targetNode) {
        Object newValue = getEAttributeValue();

        if (targetParent instanceof EObjectCompareNode) {
            ((EObjectCompareNode) targetParent).getEObject().eSet(getFeature(),
                    newValue);
            return newValue;

        } else if (targetParent instanceof EListCompareNode) {
            if (true) {
                throw new RuntimeException(
                        "Code for non-EObject child of EList is untried");
            }
            EListCompareNode targetList = (EListCompareNode) targetParent;

            /* FInd our object in parent list and replace it. */
            int idx = targetList.getEList().indexOf(this.getObject());
            if (idx >= 0) {
                targetList.getEList().set(idx, newValue);

                return newValue;
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

                if (tgtFeature == this.getFeature() && tgtValue == getObject()) {
                    featureMap.setValue(i, newValue);
                    return newValue;
                }
            }
        }

        throw new RuntimeException(
                "Problem encountered replacing equivalent of: '" + this + "' In: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
