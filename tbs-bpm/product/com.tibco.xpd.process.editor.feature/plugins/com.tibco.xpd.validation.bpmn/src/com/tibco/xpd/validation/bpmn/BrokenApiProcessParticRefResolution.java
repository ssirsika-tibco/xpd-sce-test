/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 24 Jun 2013
 */
public class BrokenApiProcessParticRefResolution extends
        AbstractWorkingCopyResolution implements IResolution {

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
        if (target instanceof Process) {
            Process process = (Process) target;

            Participant generateWsdlActivityParticipant = null;

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    generateWsdlActivityParticipant =
                            Xpdl2ModelUtil
                                    .getEndPointAliasParticipant(activity);

                    if (generateWsdlActivityParticipant != null) {
                        break;
                    }
                }
            }

            if (generateWsdlActivityParticipant != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.BrokenApiProcessParticRefResolution_FixBrokenRef_menu);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ApiEndPointParticipant(),
                                generateWsdlActivityParticipant.getId()));

                return cmd;
            }
        }

        return null;
    }

}
