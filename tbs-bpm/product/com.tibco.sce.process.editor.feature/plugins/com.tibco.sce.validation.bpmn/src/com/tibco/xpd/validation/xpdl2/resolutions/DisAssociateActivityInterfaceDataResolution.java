/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Resolution to remove associated data fields from activity interface.
 * 
 * @author rsawant
 * @since 22-Feb-2013
 */
public class DisAssociateActivityInterfaceDataResolution extends
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

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DisAssociateActivityInterfaceDataResolution_RemoveData);

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            List<DataField> dataFields = activity.getProcess().getDataFields();
            AssociatedParameters associatedParams = null;
            EObject obj =
                    activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters().getName());

            if (obj instanceof AssociatedParameters
                    && !((AssociatedParameters) obj).getAssociatedParameter()
                            .isEmpty()) {
                associatedParams = (AssociatedParameters) obj;
                List<AssociatedParameter> associatedParamList =
                        associatedParams.getAssociatedParameter();

                for (AssociatedParameter associatedData : associatedParamList) {
                    boolean isvalid = isDataField(dataFields, associatedData);

                    if (isvalid) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                associatedData));
                    }
                }
            }
        }
        return cmd;
    }

    boolean isDataField(List<DataField> dataFields,
            AssociatedParameter associatedparams) {

        for (DataField field : dataFields) {
            if (field.getName().equals(associatedparams.getFormalParam())) {
                return true;
            }
        }
        return false;
    }
}
