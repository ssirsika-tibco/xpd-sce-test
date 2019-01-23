/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EFeatureMapCompareNode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Package/##other elements feature map compare node. This is standard EXCEPT
 * that it excludes the ProcessInterface element which the process package will
 * include directly.
 * 
 * @author aallway
 * @since 29 Nov 2010
 */
public class PackageOtherElementsCompareNode extends EFeatureMapCompareNode {

    /**
     * @param featureMap
     * @param parent
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public PackageOtherElementsCompareNode(FeatureMap featureMap,
            EObject parent, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(featureMap, parent, feature, parentCompareNode,
                compareNodeFactory);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#excludeChildFeature(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object)
     * 
     * @param feature
     * @param child
     * @return
     */
    @Override
    protected boolean excludeChildFeature(EStructuralFeature feature,
            Object child) {

        if (feature == XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ProcessInterfaces()) {
            /*
             * excludes the ProcessInterface element which the process package
             * will include directly.
             */
            return true;
        }
        return super.excludeChildFeature(feature, child);
    }
}
