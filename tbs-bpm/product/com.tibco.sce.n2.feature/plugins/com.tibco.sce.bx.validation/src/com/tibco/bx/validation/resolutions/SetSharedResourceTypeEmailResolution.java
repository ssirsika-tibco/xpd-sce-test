/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetSharedResourceTypeEmailResolution
 * 
 * 
 * @author bharge
 * @since 3.3 (28 Jan 2010)
 */
public class SetSharedResourceTypeEmailResolution extends
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
        if (target instanceof Participant) {

            final Participant participant = (Participant) target;

            final ParticipantSharedResource sharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil
                            .getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());

            return new RecordingCommand(
                    (TransactionalEditingDomain) editingDomain) {
                @Override
                protected void doExecute() {

                    if (sharedResource == null) {
                        EReference sharedResFeature =
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantSharedResource();
                        ParticipantSharedResource psr =
                                XpdExtensionFactory.eINSTANCE
                                        .createParticipantSharedResource();
                        Xpdl2ModelUtil.addOtherElement(participant,
                                sharedResFeature,
                                psr);
                    }
                    if (sharedResource != null) {
                        EmailResource emailResource =
                                XpdExtensionFactory.eINSTANCE
                                        .createEmailResource();
                        sharedResource.setSharedResource(emailResource);
                    }
                }
            };

        }

        return null;
    }

}
