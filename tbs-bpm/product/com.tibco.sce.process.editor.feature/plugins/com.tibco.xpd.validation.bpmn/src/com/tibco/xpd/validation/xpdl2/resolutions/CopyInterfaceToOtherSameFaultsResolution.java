/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * CopyInterfaceToOtherSameFaultsResolution
 * <p>
 * Copy the associated parameter configuration from the given throw error end
 * event to all other errors for the same request activity that throw the same
 * fault name.
 * 
 * @author aallway
 * @since 3.3 (7 Dec 2009)
 */
public class CopyInterfaceToOtherSameFaultsResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity errorEvent = (Activity) target;

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.CopyInterfaceToOtherSameFaultsResolution_SynchThrowFaultPArameters_menu);

            String faultName =
                    ThrowErrorEventUtil.getThrowErrorFaultName(errorEvent);
            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(errorEvent);

            if (requestActivity != null && faultName != null
                    && faultName.length() > 0) {

                AssociatedParameters assocParams =
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(errorEvent,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());

                List<Activity> throwErrorEvents =
                        ThrowErrorEventUtil
                                .getThrowErrorEvents(requestActivity);

                for (Activity otherErrorEvent : throwErrorEvents) {
                    if (otherErrorEvent != errorEvent) {
                        if (faultName.equals(ThrowErrorEventUtil
                                .getThrowErrorFaultName(otherErrorEvent))) {
                            if (assocParams != null) {
                                EObject copyAssocParams =
                                        EcoreUtil.copy(assocParams);

                                cmd
                                        .append(Xpdl2ModelUtil
                                                .getSetOtherElementCommand(editingDomain,
                                                        otherErrorEvent,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters(),
                                                        copyAssocParams));
                            } else {
                                AssociatedParameters currAssocParams =
                                        (AssociatedParameters) Xpdl2ModelUtil
                                                .getOtherElement(otherErrorEvent,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                if (currAssocParams != null) {
                                    cmd
                                            .append(Xpdl2ModelUtil
                                                    .getRemoveOtherElementCommand(editingDomain,
                                                            otherErrorEvent,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_AssociatedParameters(),
                                                            currAssocParams));
                                }
                            }
                        }
                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }

        }
        return null;
    }
}
