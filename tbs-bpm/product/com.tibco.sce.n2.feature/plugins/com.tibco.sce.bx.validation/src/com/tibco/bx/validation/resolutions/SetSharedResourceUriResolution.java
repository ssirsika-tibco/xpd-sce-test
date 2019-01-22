/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.resolutions.RenameDialog;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetSharedResourceUriResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Nov 2009)
 */
public class SetSharedResourceUriResolution extends
        AbstractWorkingCopyResolution {

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
                        WsResource wsResource = sharedResource.getWebService();
                        WsInbound wsInbound = wsResource.getInbound();
                        if (null != wsInbound
                                && null != wsInbound.getSoapHttpBinding()) {
                            final WsSoapHttpInboundBinding soapHttpBinding =
                                    wsInbound.getSoapHttpBinding().get(0);
                            if (null != soapHttpBinding) {
                                String endpointUrlPath = ""; //$NON-NLS-1$
                                endpointUrlPath =
                                        soapHttpBinding.getEndpointUrlPath();

                                RenameDialog dlg =
                                        new RenameDialog(
                                                Display.getDefault()
                                                        .getActiveShell(),
                                                Messages.SetSharedResourceUriResolution_SetSharedResourceUri_title,
                                                endpointUrlPath);
                                if (dlg.open() == RenameDialog.OK) {
                                    final String newUri =
                                            dlg.getName() != null ? dlg
                                                    .getName() : ""; //$NON-NLS-1$

                                    if (!newUri.equals(endpointUrlPath)) {
                                        soapHttpBinding
                                                .setEndpointUrlPath(newUri);
                                    }
                                }
                            }
                        }
                    }
                }
            };
        }

        return null;
    }
}
