/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution that removes invalid privilege references from Ad-Hoc Activities
 * (references to privileges that are present in the Ad-Hoc Tasks but are not
 * available in the referenced org model)
 * 
 * 
 * @author kthombar
 * @since May 5, 2015
 */
public class RemoveAdhocTaskInvalidPrivilegeReferenceResolution extends
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

        if (target instanceof Activity) {

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement((Activity) target,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                return adhocConfigType.getRequiredAccessPrivileges();
            }
        }
        return null;
    }
}
