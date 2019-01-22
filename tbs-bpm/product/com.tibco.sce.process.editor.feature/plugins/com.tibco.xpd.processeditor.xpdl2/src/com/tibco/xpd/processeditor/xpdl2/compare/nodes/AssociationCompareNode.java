/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Association connection compare node.
 * 
 * @author aallway
 * @since 28 Oct 2010
 */
public class AssociationCompareNode extends BaseConnectionCompareNode {

    private Association association;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public AssociationCompareNode(Association association, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(association, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.association = association;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#getConnectionSourceObject()
     * 
     * @return
     */
    @Override
    protected EObject getConnectionSourceObject() {
        Package pkg = Xpdl2ModelUtil.getPackage(association);

        if (pkg != null) {
            return pkg.findNamedElement(association.getSource());
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#getConnectionTargetObject()
     * 
     * @return
     */
    @Override
    protected EObject getConnectionTargetObject() {
        Package pkg = Xpdl2ModelUtil.getPackage(association);

        if (pkg != null) {
            return pkg.findNamedElement(association.getTarget());
        }

        return null;
    }

}
