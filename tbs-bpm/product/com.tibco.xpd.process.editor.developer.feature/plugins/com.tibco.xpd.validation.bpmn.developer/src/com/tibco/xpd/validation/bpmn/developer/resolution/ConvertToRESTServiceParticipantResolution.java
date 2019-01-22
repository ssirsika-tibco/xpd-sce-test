/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.bpmn.developer.rules.RestServiceActivityRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to convert a participant to a REST Service participant.
 * 
 * @author sajain
 * @since Aug 27, 2015
 */
public class ConvertToRESTServiceParticipantResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    public ConvertToRESTServiceParticipantResolution() {
    }

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

            Activity activity = (Activity) target;

            /*
             * Get additional info attached to the marker to get the referenced
             * participant ID.
             */
            Properties info = ValidationUtil.getAdditionalInfo(marker);

            if (info != null) {

                /*
                 * Get ID of the participant referenced by the activity from the
                 * additional info attached to the marker.
                 */
                String participantId =
                        info.getProperty(RestServiceActivityRule.REFERENCED_PARTICIPANT_ID);

                if (null != participantId && !participantId.isEmpty()) {

                    Process proc = activity.getProcess();

                    Participant referencedParticipant =
                            proc.getParticipant(participantId);

                    if (null == referencedParticipant) {

                        Package pkg = Xpdl2ModelUtil.getPackage(activity);

                        if (pkg != null) {

                            referencedParticipant =
                                    pkg.getParticipant(participantId);
                        }
                    }

                    if (null != referencedParticipant) {

                        CompoundCommand compoundCmd =
                                new CompoundCommand(
                                        Messages.ConvertToRESTServiceParticipantResolution_command_label);

                        ParticipantSharedResource psr =
                                XpdExtensionFactory.eINSTANCE
                                        .createParticipantSharedResource();

                        RestServiceResource rsr =
                                XpdExtensionFactory.eINSTANCE
                                        .createRestServiceResource();

                        psr.setRestService(rsr);

                        compoundCmd
                                .append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(editingDomain,
                                                referencedParticipant,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource(),
                                                psr));

                        return compoundCmd;
                    }
                }
            }
        }

        return null;
    }
}