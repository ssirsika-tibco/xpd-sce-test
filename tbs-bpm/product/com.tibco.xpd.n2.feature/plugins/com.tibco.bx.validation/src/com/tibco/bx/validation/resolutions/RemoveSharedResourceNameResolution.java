/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RemoveSharedResourceNameResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Nov 2009)
 */
public class RemoveSharedResourceNameResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Participant) {
            Participant participant = (Participant) target;

            ParticipantSharedResource sharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil
                            .getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());
            if (null != sharedResource) {
                WsResource wsResource = sharedResource.getWebService();
                WsInbound wsInbound = wsResource.getInbound();
                if (null != wsInbound) {
                    if (null != wsInbound.getSoapHttpBinding()) {
                        final WsSoapHttpInboundBinding soapHttpInboundBinding =
                                wsInbound.getSoapHttpBinding().get(0);
                        if (null != soapHttpInboundBinding
                                && null != soapHttpInboundBinding
                                        .getEndpointUrlPath()) {
                            return new RecordingCommand(
                                    (TransactionalEditingDomain) editingDomain) {
                                @Override
                                protected void doExecute() {
                                    soapHttpInboundBinding
                                            .setHttpConnectorInstanceName(""); //$NON-NLS-1$
                                }
                            };
                        }
                    }
                }
            }
        }
        return null;
    }

}
