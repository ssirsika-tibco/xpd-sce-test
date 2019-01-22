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
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetSharedResourceNameResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Nov 2009)
 */
public class SetSharedResourceNameResolution extends
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
                        EObject resource = sharedResource.getSharedResource();

                        if (resource instanceof WsResource) {
                            WsResource wsResource = (WsResource) resource;
                            if (null != wsResource
                                    && null != wsResource.getOutbound()) {

                                WsOutbound wsOutbound =
                                        wsResource.getOutbound();
                                if (null != wsOutbound.getSoapHttpBinding()) {
                                    final WsSoapHttpOutboundBinding soapHttpBinding =
                                            wsOutbound.getSoapHttpBinding();
                                    final String sharedResInstanceName =
                                            soapHttpBinding
                                                    .getHttpClientInstanceName();
                                    String newName =
                                            getNewName(sharedResInstanceName);

                                    if (!newName.equals(sharedResInstanceName)) {
                                        soapHttpBinding
                                                .setHttpClientInstanceName(newName);
                                    }
                                }
                            }
                        } else if (resource instanceof EmailResource) {
                            EmailResource emailResource =
                                    (EmailResource) resource;
                            String instanceName =
                                    emailResource.getInstanceName();
                            String newName = getNewName(instanceName);
                            if (!newName.equals(instanceName)) {
                                emailResource.setInstanceName(newName);
                            }
                        } else if (resource instanceof JdbcResource) {
                            JdbcResource jdbcResource = (JdbcResource) resource;
                            String instanceName =
                                    jdbcResource.getInstanceName();

                            String newName = getNewName(instanceName);
                            if (!newName.equals(instanceName)) {
                                jdbcResource.setInstanceName(newName);
                            }
                        }
                    }
                }
            };

        }

        return null;
    }

    private String getNewName(String instanceName) {
        String newName = null;
        RenameDialog dlg =
                new RenameDialog(
                        Display.getDefault().getActiveShell(),
                        Messages.SetSharedResourceNameResolution_SetSharedResoruceName_title,
                        instanceName);
        if (dlg.open() == RenameDialog.OK) {
            newName = dlg.getName();

            if (newName == null) {
                newName = ""; //$NON-NLS-1$
            }
        }
        return newName;
    }

}
