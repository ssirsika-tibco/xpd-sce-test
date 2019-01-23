/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Object matcher for the BOM client context.
 * 
 * @author njpatel
 * 
 */
public class BOMClientContextMatcher implements IElementMatcher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.emf.type.core.IElementMatcher#matches(org.eclipse
     * .emf.ecore.EObject)
     */
    public boolean matches(EObject object) {

        if (object != null
                && object.eClass().getEPackage() == UMLPackage.eINSTANCE) {

            if (object.eResource() != null) {
                return WorkingCopyUtil.getWorkingCopyFor(object) != null;
            }
        }

        return false;
    }
}
