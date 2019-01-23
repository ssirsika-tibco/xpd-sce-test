/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.resolutions.AbstractRemoveInvalidPrivilegeReferenceResolution;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution that removes invalid privilege references (references to
 * privileges that are present in the xpdl model but are not available in the
 * referenced org model)
 * 
 * @author bharge
 * @since 14 Aug 2014
 */
public class RemoveInvalidPrivilegeReferenceResolution extends
        AbstractRemoveInvalidPrivilegeReferenceResolution {

    /**
     * @see com.tibco.bx.validation.resolutions.AbstractRemoveInvalidPrivilegeReferenceResolution#getRequiredAccessPrivileges(org.eclipse.emf.ecore.EObject)
     * 
     * @param target
     * @return
     */
    @Override
    protected RequiredAccessPrivileges getRequiredAccessPrivileges(
            EObject target) {

        if (target instanceof OtherElementsContainer) {

            OtherElementsContainer otherElementsContainer =
                    (OtherElementsContainer) target;

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(otherElementsContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RequiredAccessPrivileges());

            if (otherElement instanceof RequiredAccessPrivileges) {

                return (RequiredAccessPrivileges) otherElement;
            }
        }
        return null;
    }
}
