/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to reset the deadline activity value on an Activity.
 * 
 * @author njpatel
 */
public class ResetDeadlineActivityResolution extends
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
        if (target instanceof Activity) {
            Object value =
                    Xpdl2ModelUtil.getOtherAttribute((Activity) target,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ActivityDeadlineEventId());

            if (value instanceof String) {
                // Unset the value
                return Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                (Activity) target,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ActivityDeadlineEventId(),
                                null);
            }
        }
        return null;
    }

}
