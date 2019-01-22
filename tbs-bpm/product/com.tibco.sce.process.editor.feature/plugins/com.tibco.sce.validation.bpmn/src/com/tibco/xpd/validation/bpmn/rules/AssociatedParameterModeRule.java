/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.DataFieldToParameterResolution;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class AssociatedParameterModeRule extends ProcessValidationRule {

    private static final String MODE = "bpmn.invalidAssociatedParameterMode"; //$NON-NLS-1$

    private static final String MANDATORY_ENFORCE =
            "bpmn.mandatoryFieldNeededSet"; //$NON-NLS-1$

    private static final String THROWERROREVENT_OUTPARAMONLY =
            "bpmn.throwErrorEventOutParamOnly"; //$NON-NLS-1$

    private static final String CANT_ASSOC_FIELD_WITH_APIACTIVITY =
            "bpmn.cantAssociateDataFieldWithApiActivity"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (isApiActivity(activity)) {

                boolean isEndError = isEndErrorActivity(activity);

                for (AssociatedParameter assoc : getAssociatedParameters(activity)) {
                    ProcessRelevantData data =
                            ProcessInterfaceUtil
                                    .getProcessRelevantDataFromAssociatedParam(assoc);
                    if (data instanceof FormalParameter) {
                        FormalParameter formal = (FormalParameter) data;
                        ModeType assocMode = assoc.getMode();
                        ModeType paramMode = formal.getMode();
                        if (assocMode == null || !assocMode.equals(paramMode)) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(assoc.getFormalParam());
                            addIssue(MODE, activity, messages);
                        }

                        if (formal.isRequired() && !(assoc.isMandatory())) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(Xpdl2ModelUtil
                                    .getDisplayName(activity));
                            addIssue(MANDATORY_ENFORCE, assoc, messages);
                        }

                        if (isEndError) {
                            if (ModeType.IN_LITERAL.equals(formal.getMode())) {
                                List<String> messages = new ArrayList<String>();
                                messages.add(Xpdl2ModelUtil
                                        .getDisplayName(formal));
                                addIssue(THROWERROREVENT_OUTPARAMONLY,
                                        activity,
                                        messages);
                            }
                        }

                    } else {
                        // Data fields etc.
                        if (isEndError) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(Xpdl2ModelUtil.getDisplayName(data));
                            addIssue(THROWERROREVENT_OUTPARAMONLY,
                                    activity,
                                    messages);
                        }

                        /*
                         * Input output parameters for api activities must be
                         * formal parameters
                         */
                        /*
                         * XPD-7075: Raise this validations only for non Signal
                         * Events.
                         */
                        if (data instanceof DataField
                                && !isSignalEvent(activity)) {
                            Map<String, String> info =
                                    new HashMap<String, String>();
                            info.put(DataFieldToParameterResolution.MARKERINFO_ACTIVITY_DATAREFERENCE_ID,
                                    data.getId());
                            addIssue(CANT_ASSOC_FIELD_WITH_APIACTIVITY,
                                    activity,
                                    Collections.singletonList(Xpdl2ModelUtil
                                            .getDisplayNameOrName(data)),
                                    info);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param activity
     *            the activity in consideration
     * @return <code>true</code> if the passed activity is an Signal Event else
     *         return <code>false</code>
     */
    private boolean isSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                return true;
            }
        }
        return false;
    }

    private boolean isEndErrorActivity(Activity activity) {
        if (activity.getEvent() instanceof EndEvent) {
            if (ResultType.ERROR_LITERAL
                    .equals(((EndEvent) activity.getEvent()).getResult())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Note that Process API activities are defined as:<br>
     * a) Start Events,<br>
     * b) Message-type Catch Intermediate Events<br>
     * c) Receive Tasks<br>
     * d) End Event if it is a response to Message-type Start Event or it is a
     * type None<br>
     * All others are non-API activities.
     * 
     * @param activity
     *            The activity to check.
     * @return true if it is regarded as an API activity.
     */
    private boolean isApiActivity(Activity activity) {
        boolean isApi = false;

        Implementation impl = activity.getImplementation();
        Event event = activity.getEvent();

        /*
         * XPD-7539 Message Events can be REST sdervce as well as WebService;
         * only want to deal with WebService
         */
        if (event != null) {
            if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {

                if (event instanceof StartEvent) {
                    isApi = true;
                } else if (event instanceof IntermediateEvent) {
                    IntermediateEvent intermediate = (IntermediateEvent) event;
                    if (TriggerType.MESSAGE_LITERAL.equals(intermediate
                            .getTrigger())) {
                        isApi = true;
                    }
                } else if (event instanceof EndEvent) {
                    EndEvent end = (EndEvent) event;
                    if (ResultType.NONE_LITERAL.equals(end.getResult())) {
                        isApi = true;
                    } else if (ResultType.MESSAGE_LITERAL.equals(end
                            .getResult())) {
                        isApi = isResponseMessage(end);
                    } else if (ResultType.ERROR_LITERAL.equals(end.getResult())) {
                        isApi = true;
                    }
                }
            }
        } else if (impl instanceof Task) {
            Task task = (Task) impl;
            if (task.getTaskReceive() != null) {
                isApi = true;
            }
        }
        return isApi;
    }

    private boolean isResponseMessage(EndEvent end) {
        boolean isResponse = false;
        TriggerResultMessage triggerResultMessage =
                end.getTriggerResultMessage();
        if (triggerResultMessage != null) {
            WebServiceOperation webServiceOperation =
                    triggerResultMessage.getWebServiceOperation();
            if (webServiceOperation != null) {
                String operationName = webServiceOperation.getOperationName();
                String portTypeName = null;
                String portOperationName = null;
                PortTypeOperation portTypeOperation =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(triggerResultMessage,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (portTypeOperation != null) {
                    portTypeName = portTypeOperation.getPortTypeName();
                    portOperationName = portTypeOperation.getOperationName();
                }

                Service s = webServiceOperation.getService();
                String portName = s.getPortName();
                String serviceName = s.getServiceName();
                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopyFor(webServiceOperation);
                IResource resource = wc.getEclipseResources().get(0);
                IProject project = resource.getProject();
                String filePath = null;
                if (s.getEndPoint() != null
                        && s.getEndPoint().getExternalReference() != null) {
                    filePath =
                            s.getEndPoint().getExternalReference()
                                    .getLocation();
                }
                WsdlServiceKey key =
                        new WsdlServiceKey(serviceName, portName,
                                operationName, portTypeName, portOperationName,
                                filePath);
                Operation operation =
                        WsdlIndexerUtil.getOperation(project, key, true, true);
                if (operation != null) {
                    boolean hasInput = operation.getInput() != null;
                    boolean hasOutput = false;

                    Output output = operation.getOutput();
                    if (output != null) {
                        if (output.getMessage() != null) {
                            if (output.getMessage().getParts() != null) {
                                if (!output.getMessage().getParts().isEmpty()) {
                                    hasOutput = true;
                                }
                            }
                        }
                    }
                    isResponse = (hasInput && hasOutput);
                }
            }
        }
        return isResponse;
    }

    public List<AssociatedParameter> getAssociatedParameters(Activity act) {
        List<AssociatedParameter> associatedParameters =
                new ArrayList<AssociatedParameter>();

        EObject obj =
                act.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;
            associatedParameters.addAll(associatedParams
                    .getAssociatedParameter());
        }

        return associatedParameters;
    }

}
