/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check for the following:
 * <ul>
 * <li>Catch Error Event referring to a WSDL fault with message part having a
 * type attribute is not supported.</li>
 * <li>Error End Event that results in a WSDL fault having RPC literal binding
 * is not supported.</li>
 * </ul>
 * 
 * @author njpatel
 */
public class WSDLFaultMessagePartTypeRule extends ProcessValidationRule {

    private static final String ISSUE_BOUNDARY_ERROR_EVENT =
            "bpmn.dev.boundaryErrorEventRefWsdlFaultWithMessageType"; //$NON-NLS-1$

    private static final String ISSUE_RPC =
            "bpmn.dev.errorEventThrowingWSDLFaultCannotBeRPC"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing here
    }

    @Override
    public void validate(Process process) {

        Collection<Activity> allActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActivities) {
            validateActivity(process, activity);
        }
    }

    /**
     * Validate the given activity.
     * 
     * @param process
     * @param activity
     */
    private void validateActivity(Process process, Activity activity) {
        EventTriggerType type = EventObjectUtil.getEventTriggerType(activity);

        if (type == EventTriggerType.EVENT_ERROR_LITERAL) {
            EventFlowType flowType = EventObjectUtil.getFlowType(activity);

            if (flowType == EventFlowType.FLOW_END_LITERAL) {
                validateErrorEndEvent(process, activity);
            } else if (flowType == EventFlowType.FLOW_INTERMEDIATE_LITERAL) {
                validateBoundaryError(process, activity);
            }
        }

    }

    /**
     * Validate the boundary error event. Catch Error Event referring to a WSDL
     * fault with message part having a type attribute is not supported.
     * 
     * @param process
     * @param activity
     */
    private void validateBoundaryError(Process process, Activity activity) {
        if (Xpdl2ModelUtil.isEventAttachedToTask(activity)) {
            Fault fault = CatchWsdlErrorEventUtil.getCaughtWsdlFault(activity);
            if (fault != null) {
                Message message = fault.getEMessage();
                if (message != null) {
                    EList<?> parts = message.getEParts();

                    if (!parts.isEmpty()) {
                        for (Object next : parts) {
                            if (next instanceof Part) {
                                if (((Part) next).getTypeDefinition() != null) {
                                    // "Catch error event referring to WSDL fault with message part having type definition is not supported".
                                    addIssue(ISSUE_BOUNDARY_ERROR_EVENT,
                                            activity);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate an Error End Event. Error End Event that results in a WSDL fault
     * having RPC literal binding is not supported.
     * 
     * @param process
     * @param activity
     */
    private void validateErrorEndEvent(Process process, Activity activity) {
        Activity requestActivity =
                ThrowErrorEventUtil.getRequestActivity(activity);
        if (requestActivity != null) {
            IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(requestActivity);

            if (wsdlFile != null
                    && Xpdl2ModelUtil.isWsdlStudioGenerated(wsdlFile)) {
                Participant participant =
                        Xpdl2ModelUtil
                                .getProcessApiActivityParticipant(process);

                if (participant != null) {
                    Object otherElement =
                            Xpdl2ModelUtil
                                    .getOtherElement(participant,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());

                    if (otherElement instanceof ParticipantSharedResource) {
                        EObject sharedResource =
                                ((ParticipantSharedResource) otherElement)
                                        .getSharedResource();
                        if (sharedResource instanceof WsResource) {
                            WsInbound inbound =
                                    ((WsResource) sharedResource).getInbound();

                            if (inbound != null) {
                                /*
                                 * XPD-4675 Ned to complain about throw fault
                                 * for RPC literal binding style for BOTH
                                 * Soap-Http AND Soap-Jms
                                 */
                                EList<WsBinding> bindings =
                                        inbound.getAllBindings();
                                inbound.getSoapHttpBinding();

                                if (!bindings.isEmpty()) {
                                    for (WsBinding binding : bindings) {
                                        if (binding instanceof WsSoapBinding) {
                                            WsSoapBinding soapBinding =
                                                    (WsSoapBinding) binding;
                                            if (soapBinding.getBindingStyle() == SoapBindingStyle.RPC_LITERAL) {
                                                // "Error Event throwing WSDL fault should not be RPC literal".
                                                addIssue(ISSUE_RPC,
                                                        activity,
                                                        Collections
                                                                .singletonList(Xpdl2ModelUtil
                                                                        .getDisplayNameOrName(activity)));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
