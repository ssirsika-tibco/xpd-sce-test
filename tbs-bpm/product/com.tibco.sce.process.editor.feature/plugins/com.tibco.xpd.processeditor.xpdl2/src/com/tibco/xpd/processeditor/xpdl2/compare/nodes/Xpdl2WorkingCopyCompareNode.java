/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Specialised WorkingCopyCompareNode for Xpdl2 resources (knows how to replace
 * the root element in actual emf resource root element DOcumentRoot.
 * 
 * 
 * @author aallway
 * @since 6 Oct 2010
 */
public class Xpdl2WorkingCopyCompareNode extends WorkingCopyCompareNode {

    /**
     * 
     * @param workingCopy
     * @param isEditable
     * @param freeWorkingCopyOnDispose
     * @param parentObject
     * @param compareNodeFactory
     */
    public Xpdl2WorkingCopyCompareNode(AbstractWorkingCopy workingCopy,
            boolean freeWorkingCopyOnDispose, Object parentObject,
            EMFCompareNodeFactory compareNodeFactory) {
        super(workingCopy, Xpdl2Package.eINSTANCE.getDocumentRoot_Package(),
                freeWorkingCopyOnDispose, parentObject, compareNodeFactory);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode#setWorkingCopyRootElement(com.tibco.xpd.resources.wc.AbstractWorkingCopy,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param workingCopy
     * @param newRootElement
     */
    @Override
    protected void setWorkingCopyRootElement(AbstractWorkingCopy workingCopy,
            EObject newRootElement) {
        ((Xpdl2WorkingCopyImpl) workingCopy).setRootElement(newRootElement);
        return;
    }

}