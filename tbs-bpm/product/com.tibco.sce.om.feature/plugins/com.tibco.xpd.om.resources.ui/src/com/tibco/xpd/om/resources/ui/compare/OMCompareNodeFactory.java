/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.compare;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;

/**
 * EXPERMIMENTAL P.O.C contribution of Comparison tool for Organisation Model.
 * <p>
 * This works but is incomplete (basic comparison is performed or OrgModel root
 * element but further compare node specialisation is required <b>INCLUDING</b>
 * the encapsulation of graphical property elements (from the Diagram elements
 * corresponding to each OrgModel element) within the children of the nodes for
 * the OrgModel elements).
 * <p>
 * The two contributions for org model diff viewer and merge viewer in
 * comp.tibco.xpd.om.resources.ui are currently commented out (look for
 * XPD-1128)
 * 
 * 
 * @author aallway
 * @since 3 Dec 2010
 */
public class OMCompareNodeFactory extends EMFCompareNodeFactory {

    /**
     * @param compareNodeContentType
     */
    public OMCompareNodeFactory(String compareNodeContentType) {
        super(compareNodeContentType);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#createForEObject(org.eclipse.emf.ecore.EObject,
     *      int, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param object
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @return
     */
    @Override
    public Object createForEObject(EObject eObject, int listIndex,
            EStructuralFeature feature, Object parentCompareNode) {
        // TODO Auto-generated method stub

        Object compareNode = null;

        if (eObject instanceof NamedElement) {
            compareNode =
                    new OMNamedElementCompareNode((NamedElement) eObject,
                            listIndex, feature, parentCompareNode, this);

        }
        if (compareNode == null) {
            compareNode =
                    super.createForEObject(eObject,
                            listIndex,
                            feature,
                            parentCompareNode);
        }

        return compareNode;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory#isAtomic(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param feature
     * @param value
     * @param parentModelObject
     * @return
     */
    @Override
    public boolean isAtomic(EStructuralFeature feature, Object value,
            Object parentModelObject) {
        if (value instanceof NamedElement) {
            return true;
        } else if (value instanceof EList) {
            return true;
        }

        return false;// super.isAtomic(feature, value, parentModelObject);
    }

}
