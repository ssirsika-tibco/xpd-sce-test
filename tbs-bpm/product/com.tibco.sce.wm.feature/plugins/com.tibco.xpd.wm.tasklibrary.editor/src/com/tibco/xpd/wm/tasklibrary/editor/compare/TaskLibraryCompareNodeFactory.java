/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.ProcessCompareNodeFactory;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Package;

/**
 * Compare node factory for task libraries.
 * <p>
 * Mainly based business process {@link ProcessCompareNodeFactory} as most of
 * the objects are similar.
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskLibraryCompareNodeFactory extends ProcessCompareNodeFactory {

    /**
     * @param compareNodeContentType
     */
    public TaskLibraryCompareNodeFactory(String compareNodeContentType) {
        super(compareNodeContentType);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.ProcessCompareNodeFactory#createForEObject(org.eclipse.emf.ecore.EObject,
     *      int, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param eObject
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @return
     */
    @Override
    public Object createForEObject(EObject eObject, int listIndex,
            EStructuralFeature feature, Object parentCompareNode) {
        Object compareNode = null;

        if (eObject instanceof Package) {
            compareNode =
                    new TaskLibraryPackageCompareNode((Package) eObject,
                            listIndex, feature, parentCompareNode, this);

        } else if (eObject instanceof Lane) {
            compareNode =
                    new TaskSetCompareNode((Lane) eObject, listIndex, feature,
                            parentCompareNode, this);
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

}
