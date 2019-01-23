/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to change the SOAP binding style of
 * 
 * 1. the process API activity to Document literal
 * 
 * 2. if the target object is a process interface method, then change the WSDL
 * generation flag to document Literal
 * 
 * XPD-4675 - moved from com.tibco.xpd.wsdlgen
 * 
 * @author aallway
 * @since 24 Feb 2011
 */
public class ChangeToDocumentLitResolution extends
        AbstractWorkingCopyResolution {

    /**
     * 
     */
    public ChangeToDocumentLitResolution() {
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
             * SID XPD-1655: Should use the participant referenced from the
             * activity NOT ALWAYS the process api endpoint! This is only
             * maintained for auto-generated activities and not defined in some
             * circumstances - besides this rule should work for invokes too.
             */
            EObject[] activityPerformers = null;

            if (ReplyActivityUtil.isReplyActivity(activity)) {
                /*
                 * Participant is defined on request activity for reply
                 * activity.
                 */
                Activity requestActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (requestActivity != null) {
                    activityPerformers =
                            TaskObjectUtil
                                    .getActivityPerformers(requestActivity);
                }
            } else {
                activityPerformers =
                        TaskObjectUtil.getActivityPerformers(activity);
            }

            /*
             * Should really have multiple partic but if there are we'll do all!
             */
            if (activityPerformers != null && activityPerformers.length > 0) {
                CompoundCommand cmd = new CompoundCommand();

                for (EObject object : activityPerformers) {
                    if (object instanceof Participant) {
                        Participant participant = (Participant) object;

                        Object participantSharedResObj =
                                Xpdl2ModelUtil
                                        .getOtherElement(participant,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource());

                        if (participantSharedResObj instanceof ParticipantSharedResource) {
                            ParticipantSharedResource participantSharedResource =
                                    (ParticipantSharedResource) participantSharedResObj;

                            EObject sharedResource =
                                    participantSharedResource
                                            .getSharedResource();

                            if (sharedResource instanceof WsResource) {
                                WsResource wsResource =
                                        (WsResource) sharedResource;
                                WsInbound inbound = wsResource.getInbound();

                                if (inbound != null) {
                                    List<WsSoapHttpInboundBinding> soapHttpBinding =
                                            inbound.getSoapHttpBinding();
                                    if (!soapHttpBinding.isEmpty()) {
                                        WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                                soapHttpBinding.get(0);
                                        cmd.append(SetCommand
                                                .create(editingDomain,
                                                        wsSoapHttpInboundBinding,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getWsSoapBinding_BindingStyle(),
                                                        SoapBindingStyle.DOCUMENT_LITERAL));
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

        } else if (target instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) target;
            ProcessInterface processInterface =
                    ProcessInterfaceUtil.getProcessInterface(interfaceMethod);
            CompoundCommand cmd = new CompoundCommand();
            Object wsdlGenElement =
                    Xpdl2ModelUtil.getOtherElement(processInterface,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_WsdlGeneration());

            WsdlGeneration wsdlGeneration = null;
            if (wsdlGenElement instanceof WsdlGeneration) {
                wsdlGeneration = (WsdlGeneration) wsdlGenElement;

            } else {
                wsdlGeneration =
                        XpdExtensionFactory.eINSTANCE.createWsdlGeneration();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                processInterface,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_WsdlGeneration(),
                                wsdlGeneration));
            }

            cmd.append(SetCommand.create(editingDomain,
                    wsdlGeneration,
                    XpdExtensionPackage.eINSTANCE
                            .getWsdlGeneration_SoapBindingStyle(),
                    SoapBindingStyle.DOCUMENT_LITERAL));
            return cmd;

        }
        return null;
    }
}
