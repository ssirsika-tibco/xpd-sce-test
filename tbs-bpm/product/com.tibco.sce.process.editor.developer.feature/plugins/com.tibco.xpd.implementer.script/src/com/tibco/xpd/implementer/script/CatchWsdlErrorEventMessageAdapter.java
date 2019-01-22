/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity message provider for a Catch Error Intermediate Event that is set to
 * catch a specific error thrown by a task/event that invokes a WSDL operation.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchWsdlErrorEventMessageAdapter implements
        ActivityMessageProvider {

    @Override
    public void assignWebService(List<Object> objectsToCreate, Process process,
            Activity act, String endpoint, boolean isRemote, WsdlServiceKey key) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".assignWebService():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceCommand(EditingDomain ed,
            Process process, Activity act, String endpoint, boolean isRemote,
            WsdlServiceKey key) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceEndpointCommand(EditingDomain ed,
            Activity act, String endpoint) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceEndpointCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceIsRemoteCommand(EditingDomain ed,
            Activity act, boolean isRemote) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceIsRemoteCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getClearWebServiceCommand(EditingDomain ed, Activity act) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getClearWebServiceCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            Set<ProcessRelevantData> dataSet) {
        // Only local data refs would be handled by standard ref checker
        // enyway.,
        return Collections.emptySet();
    }

    @Override
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            Activity activity, ProcessRelevantData data) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    @Override
    public ImplementationType getImplementation(Activity act) {
        Object thrower = BpmnCatchableErrorUtil.getErrorThrower(act);
        if (thrower instanceof Activity) {
            if (isWsdlFaultThrowingActivityType((Activity) thrower)) {
                return ImplementationType.WEB_SERVICE_LITERAL;
            }
        }
        return null;
    }

    @Override
    public EObject getMessageContainer(Activity act) {
        ResultError resultError = EventObjectUtil.getResultError(act);
        if (resultError != null) {
            CatchErrorMappings catchErrorMappings =
                    (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CatchErrorMappings());

            return catchErrorMappings;
        }
        return null;
    }

    @Override
    public Message getMessageIn(Activity act) {
        return null;
    }

    @Override
    public Message getMessageOut(Activity act) {
        Object thrower = BpmnCatchableErrorUtil.getErrorThrower(act);
        if (thrower instanceof Activity) {
            if (isWsdlFaultThrowingActivityType((Activity) thrower)) {
                if (act.getEvent() instanceof IntermediateEvent
                        && act.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                    ResultError resError =
                            (ResultError) act.getEvent()
                                    .getEventTriggerTypeNode();

                    CatchErrorMappings catchErrorMappings =
                            (CatchErrorMappings) Xpdl2ModelUtil
                                    .getOtherElement(resError,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CatchErrorMappings());
                    if (catchErrorMappings != null) {
                        return catchErrorMappings.getMessage();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the message provider for the thrower of the given activity
     * 
     * @param catch thrower
     * @return message provider or null if cannot access.
     */
    private ActivityMessageProvider getThrowerMessageProvider(Activity thrower) {
        if (isWsdlFaultThrowingActivityType(thrower)) {
            return ActivityMessageProviderFactory.INSTANCE
                    .getMessageProvider(thrower);
        }
        return null;
    }

    @Override
    public PortTypeOperation getPortTypeOperation(Activity act) {
        //
        // Delegate the 'operation' to that of the activity whose wsdl operation
        // fault we are catching.
        //
        Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower(act);
        if (errorThrower instanceof Activity) {
            ActivityMessageProvider msgProvider =
                    getThrowerMessageProvider((Activity) errorThrower);
            if (msgProvider != null) {
                return msgProvider
                        .getPortTypeOperation((Activity) errorThrower);
            }
        }
        return null;
    }

    @Override
    public WebServiceOperation getWebServiceOperation(Activity act) {
        //
        // Delegate the 'operation' to that of the activity whose wsdl operation
        // fault we are catching.
        //
        Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower(act);
        if (errorThrower instanceof Activity) {
            ActivityMessageProvider msgProvider =
                    getThrowerMessageProvider((Activity) errorThrower);
            if (msgProvider != null) {
                return msgProvider
                        .getWebServiceOperation((Activity) errorThrower);
            }
        }
        return null;
    }

    @Override
    public boolean hasMappingIn(Activity act) {
        return false;
    }

    @Override
    public boolean hasMappingOut(Activity act) {
        return true;
    }

    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getSetImplementationCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Catch Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getSwapDataIdReferencesCommand(EditingDomain editingDomain,
            Activity activity, Map<String, String> idMap) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    @Override
    public Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    private boolean isWsdlFaultThrowingActivityType(Activity errorThrower) {
        //
        // We can handle Web Service Send Task, Service Task and Throw Message.
        //
        if (errorThrower.getImplementation() instanceof Task) {
            Task task = (Task) errorThrower.getImplementation();

            if (task.getTaskService() != null) {
                if (ImplementationType.WEB_SERVICE_LITERAL.equals(task
                        .getTaskService().getImplementation())) {
                    return true;
                }
            } else if (task.getTaskSend() != null) {
                if (ImplementationType.WEB_SERVICE_LITERAL.equals(task
                        .getTaskSend().getImplementation())) {
                    return true;
                }
            }

        } else if (errorThrower.getEvent() instanceof IntermediateEvent) {
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(errorThrower))) {
                return true;
            }

        } else if (errorThrower.getEvent() instanceof EndEvent) {
            //
            // ONLY interested in end event if it is effectively a send message
            // (i.e. not a reply to a different error).
            //
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(errorThrower))) {
                if (!WsdlUtil.isEndReplyEvent(errorThrower)) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.script.ActivityMessageProvider#
     * isActualWebServiceActivity()
     */
    @Override
    public boolean isActualWebServiceActivity() {
        return false;
    }
}
