/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.wsdl.Port;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * XPD-1237 : Rule added to ensure that the WSDL generated for a given API
 * activity has only one parameter if the WSDL Generation flag is
 * Document-literal. This is to maintain WS-I compliance.
 * <p>
 * XPD-4675 - moved from com.tibco.xpd.wsdlgen
 * <p>
 * XPD-4675: Extended tests to Output and Fault messages.
 * 
 * 
 * This rule is to ensure the check for activities in the process.
 * 
 * @author rsomayaj
 * @since 20 Jan 2011
 */
public class DocLitApiActivitySingleParamRule extends
        ProcessActivitiesValidationRule {

    private static final String ISSUE_GENWSDL_MULTI_INPUT =
            "bpmn.dev.genWsdlSoapDocLitMultiInputParts"; //$NON-NLS-1$

    private static final String ISSUE_GENWSDL_MULTI_OUTPUT =
            "bpmn.dev.genWsdlSoapDocLitMultiOutputParts"; //$NON-NLS-1$

    private static final String ISSUE_GENWSDL_MULTI_FAULT =
            "bpmn.dev.genWsdlSoapDocLitMultiFaultParts"; //$NON-NLS-1$

    private static final String ISSUE_USERWSDL_MULTI_INPUT =
            "bpmn.dev.userWsdlSoapDocLitMultiInputParts"; //$NON-NLS-1$

    private static final String ISSUE_USERWSDL_MULTI_OUTPUT =
            "bpmn.dev.userWsdlSoapDocLitMultiOutputParts"; //$NON-NLS-1$

    private static final String ISSUE_USERWSDL_MULTI_FAULT =
            "bpmn.dev.userWsdlSoapDocLitMultiFaultParts"; //$NON-NLS-1$

    private static final String ACTIVITY_WSDL_BINDING_RPC_LIT_MUST_HAVE_TYPES =
            "bpmn.dev.apiActWsdlBindingRpcLitMustHaveTypes"; //$NON-NLS-1$

    private static final String ACTIVITY_WSDL_BINDING_DOC_LIT_MUST_HAVE_ELEMENT =
            "bpmn.dev.apiActWsdlBindingDocLitMustHaveElement"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        if (isServiceInvocationTask(activity)) {
            /** Check In/Out/Fault messages on invoked operation. */
            validateWebServiceInvocationActivity(activity);

        } else if (isCatchErrorOnServiceInvocation(activity)) {
            /** Check fault message for catch fault error events. */
            validateCatchWebServiceErrorActivity(activity);

        } else if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {

            /** Check incoming request/reply/fault activities. */

            /*
             * Check reply fault messages...
             */
            if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
                Activity requestActivity =
                        ThrowErrorEventUtil.getRequestActivity(activity);

                if (requestActivity != null) {
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)) {
                        validateGenWsdlFaultMessages(activity, requestActivity);

                    } else {
                        validateUserWsdlFaultMessages(activity, requestActivity);
                    }
                }
            }
            /*
             * Then normal reply messages
             */
            else if (ReplyActivityUtil.isReplyActivity(activity)) {
                Activity requestActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);

                if (requestActivity != null) {
                    if (Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)) {
                        validateGenWsdlOutputMessages(activity, requestActivity);

                    } else {
                        validateUserWsdlOutputMessages(activity,
                                requestActivity);
                    }
                }
            }
            /*
             * Everything else must be incoming request messages
             */
            else {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    validateGenWsdlInputMessages(activity);

                } else {
                    validateUserWsdlInputMessages(activity);
                }
            }
        }
    }

    /**
     * Check if the process API activity participant has got virtualization
     * binding only.
     * 
     * @param activity
     * @return
     */
    private boolean isWSInboundVirtualizationOnly(Activity activity) {
        Participant participant =
                Xpdl2ModelUtil.getProcessApiActivityParticipant(activity
                        .getProcess());

        if (participant != null) {
            WsInbound wsInbound =
                    SharedResourceUtil.getWsResourceInbound(participant);

            if (wsInbound != null) {
                EList<WsBinding> allBindings = wsInbound.getAllBindings();

                return allBindings.size() == 1
                        && allBindings.get(0) instanceof WsVirtualBinding;
            }
        }
        return false;
    }

    /**
     * Validate input message for incoming request activity.
     * <p>
     * For generated WSDL this is derived from request activity associated data.
     * 
     * @param activity
     */
    private void validateGenWsdlInputMessages(Activity activity) {

        /*
         * XPD-5126: If the process API activity participant has virtualization
         * binding only then don't raise this issue.
         */
        if (!isWSInboundVirtualizationOnly(activity)
                && isBindingTypeSetAndDocLiteral(activity)) {
            /*
             * XPD-5679: Get the endpoint participsnt using the activity and not
             * the process.
             */
            if (countParamType(activity, ModeType.IN_LITERAL) > 1) {
                addIssue(ISSUE_GENWSDL_MULTI_INPUT, activity);
            }
        }
    }

    /**
     * Returns true if the activity participant's binding style is DocLiteral or
     * false if it is RPCLiteral or participant is not configured
     * 
     * @param activity
     * @return true if the activity participant's binding style is DocLiteral or
     *         false if it is RPCLiteral or participant is not configured
     */
    private boolean isBindingTypeSetAndDocLiteral(Activity activity) {
        EObject[] activityPerformers =
                TaskObjectUtil.getActivityPerformers(activity);

        if (activityPerformers != null && activityPerformers.length > 0) {
            for (EObject object : activityPerformers) {
                if (object instanceof Participant) {
                    Participant participant = (Participant) object;

                    Object participantSharedResObj =
                            Xpdl2ModelUtil
                                    .getOtherElement(participant,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());

                    if (participantSharedResObj instanceof ParticipantSharedResource) {
                        SoapBindingStyle soapBindingStyle =
                                Xpdl2ModelUtil
                                        .getInboundWsdlBindingStyle(participant);
                        if (SoapBindingStyle.DOCUMENT_LITERAL
                                .equals(soapBindingStyle)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validate output message for incoming request activity.
     * <p>
     * For generated WSDL this is derived from request activity associated data.
     * 
     * @param activity
     * @param requestActivity
     */
    private void validateGenWsdlOutputMessages(Activity activity,
            Activity requestActivity) {
        /*
         * XPD-5126: If the process API activity participant has virtualization
         * binding only then don't raise this issue.
         */
        if (!isWSInboundVirtualizationOnly(activity)
                && isBindingTypeSetAndDocLiteral(requestActivity)) {
            /*
             * XPD-5679: Get the endpoint participsnt using the activity and not
             * the process.
             */

            /*
             * Output params are defiend on request activity (because there is
             * only one output message it is defined in this single place rather
             * than each individual reply)
             */
            if (countParamType(requestActivity, ModeType.OUT_LITERAL) > 1) {
                addIssue(ISSUE_GENWSDL_MULTI_OUTPUT, activity);
            }
        }
    }

    /**
     * Validate fault message for incoming request activity.
     * <p>
     * For generated WSDL this is derived from throw error activity associated
     * data.
     * 
     * @param activity
     * @param requestActivity
     */
    private void validateGenWsdlFaultMessages(Activity activity,
            Activity requestActivity) {
        /*
         * XPD-5126: If the process API activity participant has virtualization
         * binding only then don't raise this issue.
         */
        if (!isWSInboundVirtualizationOnly(activity)
                && isBindingTypeSetAndDocLiteral(requestActivity)) {
            /*
             * XPD-5679: Get the endpoint participsnt using the activity and not
             * the process.
             */

            /*
             * Fault params are defined on throw error event as each one has
             * it's own individual fault message.
             */
            if (countParamType(activity, ModeType.OUT_LITERAL) > 1) {
                addIssue(ISSUE_GENWSDL_MULTI_FAULT, activity);
            }
        }
    }

    /**
     * Validate input message for user defined WSDL operation.
     * 
     * @param activity
     */
    private void validateUserWsdlInputMessages(Activity activity) {
        /*
         * For user defined WSDL concrete this is taken from the WSDL operation
         * message itself.
         */
        WsdlServiceKey serviceKey =
                ProcessUIUtil.getSpecificWsdlServiceKey(activity);

        IProject project = WorkingCopyUtil.getProjectFor(activity);

        Collection<Part> inputParts =
                WsdlIndexerUtil.getInputParts(project, serviceKey, true, true);

        Boolean isDocLitBinding =
                isUserWsdlDocLiteralBindingStyle(activity, serviceKey, project);

        if (isDocLitBinding == null) {
            /* Cannot determine one way or another that it is doc-lit binding. */
            return;
        }

        if (isDocLitBinding) {
            if (inputParts.size() > 1) {
                addIssue(ISSUE_USERWSDL_MULTI_INPUT, activity);
            }

            /*
             * For DOC literal, check that message parts are not type-based
             */
            if (inputParts.size() > 0 && hasTypeBasedParts(inputParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_DOC_LIT_MUST_HAVE_ELEMENT,
                        activity);
            }

        } else {
            /*
             * For RPC literal, check that message parts are not
             * top-level-element-based
             */
            if (inputParts.size() > 0 && hasElementBasedParts(inputParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_RPC_LIT_MUST_HAVE_TYPES,
                        activity);
            }
        }

    }

    /**
     * Validate output message for user defined WSDL operation.
     * 
     * @param activity
     * @param requestActivity
     */
    private void validateUserWsdlOutputMessages(Activity activity,
            Activity requestActivity) {
        /*
         * For user defined WSDL concrete this is taken from the WSDL operation
         * message itself.
         */
        WsdlServiceKey serviceKey =
                ProcessUIUtil.getSpecificWsdlServiceKey(requestActivity);

        IProject project = WorkingCopyUtil.getProjectFor(requestActivity);

        Collection<Part> outputParts =
                WsdlIndexerUtil.getOutputParts(project, serviceKey, true, true);

        Boolean isDocLitBinding =
                isUserWsdlDocLiteralBindingStyle(requestActivity,
                        serviceKey,
                        project);

        if (isDocLitBinding == null) {
            /* Cannot determine one way or another that it is doc-lit binding. */
            return;
        }

        if (isDocLitBinding) {
            if (outputParts.size() > 1) {
                addIssue(ISSUE_USERWSDL_MULTI_OUTPUT, activity);
            }

            /*
             * For DOC literal, check that message parts are not type-based
             */
            if (outputParts.size() > 0 && hasTypeBasedParts(outputParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_DOC_LIT_MUST_HAVE_ELEMENT,
                        activity);
            }

        } else {
            /*
             * For RPC literal, check that message parts are not
             * top-level-element-based
             */
            if (outputParts.size() > 0 && hasElementBasedParts(outputParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_RPC_LIT_MUST_HAVE_TYPES,
                        activity);
            }
        }

    }

    /**
     * Validate fault message for user defined WSDL operation.
     * 
     * @param activity
     * @param requestActivity
     */
    private void validateUserWsdlFaultMessages(Activity activity,
            Activity requestActivity) {
        /*
         * For user defined WSDL concrete this is taken from the WSDL operation
         * message itself.
         */
        String faultName = ThrowErrorEventUtil.getThrowErrorFaultName(activity);

        if (faultName == null) {
            return;
        }

        WsdlServiceKey serviceKey =
                ProcessUIUtil.getSpecificWsdlServiceKey(requestActivity);

        IProject project = WorkingCopyUtil.getProjectFor(requestActivity);

        Collection<Part> faultParts =
                WsdlIndexerUtil.getFaultParts(project,
                        serviceKey,
                        faultName,
                        true,
                        true);

        Boolean isDocLitBinding =
                isUserWsdlDocLiteralBindingStyle(requestActivity,
                        serviceKey,
                        project);

        if (isDocLitBinding == null) {
            /* Cannot determine one way or another that it is doc-lit binding. */
            return;
        }

        if (isDocLitBinding) {
            if (faultParts.size() > 1) {
                addIssue(ISSUE_USERWSDL_MULTI_FAULT, activity);
            }

            /*
             * For DOC literal, check that message parts are not type-based
             */
            if (faultParts.size() > 0 && hasTypeBasedParts(faultParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_DOC_LIT_MUST_HAVE_ELEMENT,
                        activity);
            }

        } else {
            /*
             * For RPC literal, check that message parts are not
             * top-level-element-based
             */
            if (faultParts.size() > 0 && hasElementBasedParts(faultParts)) {
                addIssue(ACTIVITY_WSDL_BINDING_RPC_LIT_MUST_HAVE_TYPES,
                        activity);
            }
        }
    }

    /**
     * Validate fault message for catch error event referencing user defined
     * wsdl.
     * 
     * @param activity
     * @param requestActivity
     */
    private void validateCatchWebServiceErrorActivity(Activity activity) {

        Activity requestActivity = EventObjectUtil.getTaskAttachedTo(activity);
        if (requestActivity == null) {
            return;
        }

        WsdlServiceKey serviceKey =
                ProcessUIUtil.getSpecificWsdlServiceKey(requestActivity);

        IProject project = WorkingCopyUtil.getProjectFor(requestActivity);

        Boolean isDocLitBinding =
                isUserWsdlDocLiteralBindingStyle(requestActivity,
                        serviceKey,
                        project);

        if (isDocLitBinding == null) {
            /* Cannot determine one way or another that it is doc-lit binding. */
            return;
        }

        Fault fault = CatchWsdlErrorEventUtil.getCaughtWsdlFault(activity);

        if (fault != null) {
            List<Part> faultParts = new ArrayList<Part>();

            Message message = fault.getMessage();
            if (message != null) {
                List<?> orderedParts = message.getOrderedParts(null);
                for (Object next : orderedParts) {
                    if (next instanceof Part) {
                        faultParts.add((Part) next);
                    }
                }
            }

            if (isDocLitBinding) {
                if (faultParts.size() > 1) {
                    addIssue(ISSUE_USERWSDL_MULTI_FAULT, activity);
                }

                /*
                 * For DOC literal, check that message parts are not type-based
                 */
                if (faultParts.size() > 0 && hasTypeBasedParts(faultParts)) {
                    addIssue(ACTIVITY_WSDL_BINDING_DOC_LIT_MUST_HAVE_ELEMENT,
                            activity);
                }

            } else {
                /*
                 * For RPC literal, check that message parts are not
                 * top-level-element-based
                 */
                if (faultParts.size() > 0 && hasElementBasedParts(faultParts)) {
                    addIssue(ACTIVITY_WSDL_BINDING_RPC_LIT_MUST_HAVE_TYPES,
                            activity);
                }
            }
        }
    }

    /**
     * Validate against multiple the input, output and fault message parts for
     * given web service invocation activity.
     * 
     * @param activity
     */
    private void validateWebServiceInvocationActivity(Activity activity) {
        validateUserWsdlInputMessages(activity);

        if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            validateUserWsdlOutputMessages(activity, activity);
        }
    }

    /**
     * @param activity
     * @param inOrOut
     * 
     * @return The number of IN or OUT params as requested.
     */
    private int countParamType(Activity activity, ModeType inOrOut) {
        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);
        int countInParam = 0;

        for (ActivityInterfaceData interfaceData : activityInterfaceData) {
            ModeType mode = interfaceData.getMode();

            if (inOrOut.equals(mode) || ModeType.INOUT_LITERAL.equals(mode)) {
                countInParam++;
            }
        }

        return countInParam;
    }

    /**
     * For reference to concrete (bound) operation then the binding style
     * (doc-literal vs rpc-literal) is taken from the binding in WSDL itself.
     * <p>
     * For abstract (unbound) operation on <b>for process-API-activities</b>
     * then the binding style is defined by the activity participant for service
     * invocation activities we cannot tell whether the binding is doc-lit or
     * rpc-lit.
     * 
     * @param requestActivity
     * @param project
     * @param serviceKey
     * 
     * @return <code>true</code> if the given request activity uses doc-literal
     *         binding, false if not and <code>null</code> if not determinable.
     */
    private Boolean isUserWsdlDocLiteralBindingStyle(Activity requestActivity,
            WsdlServiceKey serviceKey, IProject project) {

        if (isConcreteBinding(requestActivity)) {

            SOAPBinding binding =
                    getSOAPBindingFromWsdl(requestActivity, project, serviceKey);

            if (binding != null) {
                String style = binding.getStyle();

                if ("document".equals(style)) { //$NON-NLS-1$
                    return Boolean.TRUE;
                }

                return Boolean.FALSE;
            }
        }

        /*
         * For generated and abstract operations on process API activities,
         * we'll use the participant to tell us what binding style to use.
         */
        if (Xpdl2ModelUtil.isProcessAPIActivity(requestActivity)) {

            if (SoapBindingStyle.DOCUMENT_LITERAL.equals(Xpdl2ModelUtil
                    .getWsdlBindingStyle(requestActivity))) {
                /*
                 * XPD-5126: If the process API activity participant has
                 * virtualization only then we cannot determine the binding
                 * style.
                 */
                if (isWSInboundVirtualizationOnly(requestActivity)) {
                    return null;
                }

                return Boolean.TRUE;
            }
        }

        /*
         * For invocations on abstract operation we cannot tell.
         */
        return null;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity references a concrete binding.
     */
    private boolean isConcreteBinding(Activity activity) {
        WebServiceOperation wso =
                Xpdl2ModelUtil.getWebServiceOperation(activity);

        if (wso != null) {
            Service service = wso.getService();

            if (service != null) {
                if (service.getPortName() != null
                        && !service.getPortName().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param messageParts
     * 
     * @return <code>true</code> if any part is top level element based
     */
    private boolean hasElementBasedParts(Collection<Part> messageParts) {
        for (Part part : messageParts) {
            if (part.getElementName() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param messageParts
     * 
     * @return <code>true</code> if any part is type-based
     */
    private boolean hasTypeBasedParts(Collection<Part> messageParts) {
        for (Part part : messageParts) {
            if (part.getTypeName() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the SOAP binding from the end-point port for the given activity.
     * 
     * @param activity
     * @param project
     * @param serviceKey
     * @return
     */
    private SOAPBinding getSOAPBindingFromWsdl(Activity activity,
            IProject project, WsdlServiceKey serviceKey) {
        if (serviceKey != null) {
            Port port =
                    WsdlIndexerUtil.getPort(project, serviceKey, true, true);
            if (port != null) {
                Binding binding = (Binding) port.getBinding();
                EList<?> extensibilityElements =
                        binding.getEExtensibilityElements();
                for (Object ee : extensibilityElements) {
                    if (ee instanceof SOAPBinding) {
                        return (SOAPBinding) ee;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity is a web-service invocation task.
     */
    private boolean isServiceInvocationTask(Activity activity) {
        if (!ReplyActivityUtil.isReplyActivity(activity)) {

            TaskType taskType = TaskObjectUtil.getTaskType(activity);

            if (TaskType.SERVICE_LITERAL.equals(taskType)
                    || TaskType.SEND_LITERAL.equals(taskType)) {
                return true;
            }

            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity is catch error event on service
     *         invocation task.
     */
    private boolean isCatchErrorOnServiceInvocation(Activity activity) {
        if (EventObjectUtil.isAttachedToTask(activity)
                && EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(activity))) {
            Activity task = EventObjectUtil.getTaskAttachedTo(activity);

            if (task != null && isServiceInvocationTask(task)) {
                return true;
            }
        }
        return false;
    }
}
