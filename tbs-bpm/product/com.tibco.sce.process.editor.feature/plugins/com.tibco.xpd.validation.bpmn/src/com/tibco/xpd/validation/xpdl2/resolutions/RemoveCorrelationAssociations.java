/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class RemoveCorrelationAssociations extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand cmd = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedCorrelationFields();
            Object other = Xpdl2ModelUtil.getOtherElement(activity, feature);
            if (other instanceof AssociatedCorrelationFields) {
                cmd =
                        new CompoundCommand(
                                Messages.RemoveCorrelationAssociations_RemoveCommand);
                cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                        activity,
                        feature,
                        other));
            }
        }
        return cmd;
    }

}
