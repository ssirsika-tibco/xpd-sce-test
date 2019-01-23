/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to change 'like' mappings back to standard mappings.
 * 
 * @author nwilson
 * @since 22 Apr 2015
 */
public class SetAsStandardMappingResolution extends
        AbstractWorkingCopyResolution {

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
        Command cmd = null;
        if (target instanceof DataMapping) {
            DataMapping dm = (DataMapping) target;
            cmd =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                            dm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_LikeMapping(),
                            Boolean.FALSE);
        }
        return cmd;
    }

}
