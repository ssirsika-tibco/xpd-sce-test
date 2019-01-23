/*
 * Copyright (c) TIBCO Software Inc 2004, 2018. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to suppress the error message and show it as a warning when number
 * of mappings on an activity exceeds the supported count.
 *
 * @author aallway
 * @since 21 Jan, 2019
 */
public class IgnoreMappingCountResolution
        extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     *
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof ScriptDataMapper) {
            ScriptDataMapper sdm = (ScriptDataMapper) target;
            boolean suppressMaxOutputMappingsError = false;

            suppressMaxOutputMappingsError =
                    Xpdl2ModelUtil.getOtherAttributeAsBoolean(sdm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SuppressMaxMappingsError());
            if (!suppressMaxOutputMappingsError) {
                return Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                        sdm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SuppressMaxMappingsError(),
                        true);
            }
        }

        return null;
    }

}
