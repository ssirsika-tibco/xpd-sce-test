/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * The element type matcher for the OM model.
 * 
 * @author njpatel
 */
public class SubDiagramTypeContextElementMatcher implements IElementMatcher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.emf.type.core.IElementMatcher#matches(org.eclipse
     * .emf.ecore.EObject)
     */
    @Override
    public boolean matches(EObject object) {

        if (object != null
                && object.eClass().getEPackage() == OMPackage.eINSTANCE) {

            if (object.eResource() != null) {
                return WorkingCopyUtil.getWorkingCopyFor(object) != null;
            }
        }

        return false;
    }

}
