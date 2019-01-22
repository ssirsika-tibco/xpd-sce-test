/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.compare;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.resources.wc.InputStreamOMWorkingCopy;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

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
public class OMCompareStructureCreator extends EMFCompareStructureCreator {

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createEMFCompareNodeFactory()
     * 
     * @return
     */
    @Override
    protected EMFCompareNodeFactory createEMFCompareNodeFactory() {
        return new OMCompareNodeFactory("omContent"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createWorkingCopyCompareNode(com.tibco.xpd.resources.wc.AbstractWorkingCopy,
     *      boolean, java.lang.Object,
     *      com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param workingCopy
     * @param freeWorkingCopyOnDispose
     * @param parentObject
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected WorkingCopyCompareNode createWorkingCopyCompareNode(
            AbstractWorkingCopy workingCopy, boolean freeWorkingCopyOnDispose,
            Object parentObject, EMFCompareNodeFactory compareNodeFactory) {
        // TODO Auto-generated method stub
        return new WorkingCopyCompareNode(workingCopy, null,
                freeWorkingCopyOnDispose, parentObject, compareNodeFactory) {

            @Override
            protected void setWorkingCopyRootElement(
                    AbstractWorkingCopy workingCopy, EObject newRootElement) {
                throw new RuntimeException(
                        "No merge available for root element of OM.");
            }
        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createWorkingCopyForInput(java.io.InputStream,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param inputStream
     * @param resource
     * @return
     */
    @Override
    protected AbstractWorkingCopy createWorkingCopyForInput(
            InputStream inputStream, IResource resource) {
        // TODO Auto-generated method stub
        try {
            InputStreamOMWorkingCopy wc =
                    new InputStreamOMWorkingCopy(resource, inputStream);
            return wc;
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return "Organisation Model Structure Compare";
    }

}
