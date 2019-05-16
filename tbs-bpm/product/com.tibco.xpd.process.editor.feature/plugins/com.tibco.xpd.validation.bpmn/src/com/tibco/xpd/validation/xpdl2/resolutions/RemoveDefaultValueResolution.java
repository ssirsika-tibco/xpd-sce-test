/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Removes any default (initial) value for the given target object.
 *
 * @author pwatson
 * @since 16 May 2019
 */
public class RemoveDefaultValueResolution
        extends AbstractWorkingCopyResolution {

    /**
     * Creates a command, if applicable, to remove any default (initial) value
     * for the given target object.
     * 
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     *
     * @param aDomain
     * @param aTarget
     * @param aMarker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain aDomain,
            EObject aTarget, IMarker aMarker) throws ResolutionException {

        // initial values are held in "other elements"
        if (aTarget instanceof OtherElementsContainer) {
            // retrieve any initial values
            Object values = Xpdl2ModelUtil.getOtherElement(
                    (OtherElementsContainer) aTarget,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InitialValues());

            // if we found any
            if (values != null) {
                // issue a command to remove them - with undo option
                return Xpdl2ModelUtil.getRemoveOtherElementCommand(
                        aDomain,
                        (OtherElementsContainer) aTarget,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues(),
                        values);
            }
        }

        // no update required
        return null;
    }
}
