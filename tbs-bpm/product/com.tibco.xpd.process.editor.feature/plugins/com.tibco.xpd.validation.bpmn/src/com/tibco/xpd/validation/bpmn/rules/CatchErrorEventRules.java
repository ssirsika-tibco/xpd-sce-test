/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Check various catch error event rules.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchErrorEventRules extends ProcessValidationRule {

    /**
     * Catch Error Event error thrower is reachable but no longer throws the
     * error.
     */
    private static final String ID_CATCHERROREVENT_SPECIFIC_ERROR_MISSING =
            "bpmn.catchErrorEventThrowerErrorMissing"; //$NON-NLS-1$

    /** Catch Error Event thrower fo rthe error is no longer reachable. */
    private static final String ID_CATCHERROREVENT_THROWER_NOT_REACHABLE =
            "bpmn.catchErrorEventThrowerNotReachable"; //$NON-NLS-1$

    /**
     * Catch Unspecific error by name appears not to be thrown by anything
     * (Warning).
     */
    private static final String ID_CATCHERROREVENT_UNSPECIFIC_ERROR_MISSING =
            "bpmn.catchErrorEventUnspecificErrorNotThrown"; //$NON-NLS-1$

    /** Catch Error Event error thrower of event no longer exists. */
    private static final String ID_CATCHERROREVENT_MISSING_THROWERPROCESS =
            "bpmn.catchErrorEventThrowerProcessMissing"; //$NON-NLS-1$

    /** Catch Error Event error thrower of event no longer exists. */
    private static final String ID_CATCHERROREVENT_MISSING_THROWERACTIVITY =
            "bpmn.catchErrorEventThrowerActivityMissing"; //$NON-NLS-1$

    /** Catch Error Event error thrower of event no longer exists . */
    private static final String ID_CATCHERROREVENT_MISSING_THROWERINTERFACE =
            "bpmn.catchErrorEventThrowerInterfaceMissing"; //$NON-NLS-1$

    /** Catch Error Event error thrower of event no longer exists. */
    private static final String ID_CATCHERROREVENT_MISSING_THROWERINTERFACEEVENT =
            "bpmn.catchErrorEventThrowerInterfaceEventMissing"; //$NON-NLS-1$

    /** Cannot catch sub-process errors configured to throw a fault message. */
    private static final String ID_CATCHERROREVENT_CANT_CATCH_THROWFAULT_ON_SUBPROCESS =
            "bpmn.catchErrorEventCantCatchThrowFaultOnSubProc"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity act : activities) {
            // See if it's a intermediate catch error event that catches a
            // specific error.
            if (act.getEvent() instanceof IntermediateEvent
                    && act.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                Activity catchErrorEvent = act;

                checkCatchErrorEvent(catchErrorEvent);
            }
        }

        return;
    }

    /**
     * Check y given catch error event is valid.
     * 
     * @param catchErrorEvent
     */
    private void checkCatchErrorEvent(Activity catchErrorEvent) {

        Activity attachedToTask = checkAttached(catchErrorEvent);
        if (attachedToTask != null) {

            ResultError resError =
                    (ResultError) catchErrorEvent.getEvent()
                            .getEventTriggerTypeNode();

            String errorCode = resError.getErrorCode();

            ErrorCatchType catchType =
                    BpmnCatchableErrorUtil.getCatchType(catchErrorEvent);

            if (ErrorCatchType.CATCH_SPECIFIC.equals(catchType)) {
                // BTW - the only way we can have CATCH_SPECIFIC is if we have
                // both an erorCode and the error thrower info defined in the
                // event.
                ErrorThrowerInfo errorThrowerInfo =
                        BpmnCatchableErrorUtil
                                .getExtendedErrorThrowerInfo(catchErrorEvent);

                if (errorCode != null && errorThrowerInfo != null) {
                    // 
                    // Do a quick check to see whether the throwing activity /
                    // method still exists.
                    //
                    String throwerName =
                            checkThrowerExists(catchErrorEvent,
                                    errorCode,
                                    attachedToTask,
                                    errorThrowerInfo);
                    if (throwerName != null) {
                        // 
                        // Finally ensure that the error is still thrown by
                        // something in or under the attached to task.
                        //
                        Collection<IBpmnCatchableErrorTreeItem> catchableErrors =
                                BpmnCatchableErrorUtil
                                        .getCatchableErrorsFlatList(attachedToTask);

                        boolean throwerStillReachable = false;
                        boolean exactMatch = false;

                        String throwerId = errorThrowerInfo.getThrowerId();
                        if (throwerId == null) {
                            throwerId = ""; //$NON-NLS-1$
                        }
                        String throwerContainerId =
                                errorThrowerInfo.getThrowerContainerId();
                        if (throwerContainerId == null) {
                            throwerContainerId = ""; //$NON-NLS-1$
                        }

                        for (IBpmnCatchableErrorTreeItem item : catchableErrors) {
                            if (item instanceof BpmnCatchableError) {
                                BpmnCatchableError catchableError =
                                        (BpmnCatchableError) item;

                                if (throwerId.equals(catchableError
                                        .getErrorThrowerId())
                                        && throwerContainerId
                                                .equals(catchableError
                                                        .getErrorThrowerContainerId())) {
                                    throwerStillReachable = true;

                                    if (errorCode.equals(catchableError
                                            .getErrorCode())) {
                                        exactMatch = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if (!throwerStillReachable) {
                            // Can't reach the throwing activity
                            List<String> param = new ArrayList<String>();
                            param.add(throwerName);
                            param.add(errorCode);
                            addIssue(ID_CATCHERROREVENT_THROWER_NOT_REACHABLE,
                                    catchErrorEvent,
                                    param);

                        } else if (!exactMatch) {
                            // The thrower is reachable but no longer throws
                            // this error.
                            List<String> param = new ArrayList<String>();
                            param.add(errorCode);
                            param.add(throwerName);
                            addIssue(ID_CATCHERROREVENT_SPECIFIC_ERROR_MISSING,
                                    catchErrorEvent,
                                    param);
                        }
                    }
                }
            } else if (ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
                Collection<IBpmnCatchableErrorTreeItem> catchableErrors =
                        BpmnCatchableErrorUtil
                                .getCatchableErrorsFlatList(attachedToTask);

                boolean errorThrowerFound = false;

                for (IBpmnCatchableErrorTreeItem item : catchableErrors) {
                    if (item instanceof BpmnCatchableError) {
                        BpmnCatchableError catchableError =
                                (BpmnCatchableError) item;

                        if (errorCode.equals(catchableError.getErrorCode())) {
                            errorThrowerFound = true;
                        }
                    }
                }

                if (!errorThrowerFound) {
                    addIssue(ID_CATCHERROREVENT_UNSPECIFIC_ERROR_MISSING,
                            catchErrorEvent,
                            Collections.singletonList(errorCode));
                }
            }
        }

        return;
    }

    /**
     * Check whether the thrower of the error that the event catches still
     * exists
     * 
     * @param catchErrorEvent
     * @param attachedToTask
     * @param errorThrowerInfo
     * @return throwerName if thrower exists else null.
     */
    private String checkThrowerExists(Activity catchErrorEvent,
            String errorCode, Activity attachedToTask,
            ErrorThrowerInfo errorThrowerInfo) {
        if (ErrorThrowerType.PROCESS_ACTIVITY.equals(errorThrowerInfo
                .getThrowerType())) {
            // 
            // The error is thrown by an activity, look for it.
            //
            String throwingProcessId = errorThrowerInfo.getThrowerContainerId();
            if (throwingProcessId != null && throwingProcessId.length() > 0) {
                Process throwingProcess =
                        Xpdl2WorkingCopyImpl.locateProcess(throwingProcessId);

                if (throwingProcess != null) {
                    String throwingActId = errorThrowerInfo.getThrowerId();
                    if (throwingActId != null && throwingActId.length() > 0) {
                        Activity throwingActivity =
                                Xpdl2ModelUtil.getActivityById(throwingProcess,
                                        throwingActId);
                        if (throwingActivity != null) {
                            /*
                             * Throwing activity found! Make a final check that
                             * it's not been changed to a fault message thrower.
                             */
                            boolean isOk = true;
                            if (throwingActivity.getEvent() instanceof EndEvent
                                    && EventTriggerType.EVENT_ERROR_LITERAL
                                            .equals(EventObjectUtil
                                                    .getEventTriggerType(throwingActivity))) {
                                if (ThrowErrorEventUtil
                                        .isThrowFaultMessageErrorEvent(throwingActivity)) {
                                    addIssue(ID_CATCHERROREVENT_CANT_CATCH_THROWFAULT_ON_SUBPROCESS,
                                            catchErrorEvent);
                                    isOk = false;
                                }
                            }

                            if (isOk) {
                                String name =
                                        Xpdl2ModelUtil
                                                .getDisplayName(throwingActivity);
                                return (name != null && name.length() > 0) ? name
                                        : Messages.CatchErrorEventRules_Unnamed_label;
                            }

                        } else {
                            addIssue(ID_CATCHERROREVENT_MISSING_THROWERACTIVITY,
                                    catchErrorEvent,
                                    Collections.singletonList(errorCode));
                        }

                    } else {
                        addIssue(ID_CATCHERROREVENT_MISSING_THROWERACTIVITY,
                                catchErrorEvent,
                                Collections.singletonList(errorCode));
                    }

                } else {
                    addIssue(ID_CATCHERROREVENT_MISSING_THROWERPROCESS,
                            catchErrorEvent,
                            Collections.singletonList(errorCode));
                }
            } else {
                addIssue(ID_CATCHERROREVENT_MISSING_THROWERPROCESS,
                        catchErrorEvent,
                        Collections.singletonList(errorCode));
            }

        } else if (ErrorThrowerType.INTERFACE_EVENT.equals(errorThrowerInfo
                .getThrowerType())) {
            String throwingInterfaceId =
                    errorThrowerInfo.getThrowerContainerId();
            if (throwingInterfaceId != null && throwingInterfaceId.length() > 0) {
                ProcessInterface throwingInterface =
                        Xpdl2WorkingCopyImpl
                                .locateProcessInterface(throwingInterfaceId);
                if (throwingInterface != null) {
                    String throwingEventId = errorThrowerInfo.getThrowerId();
                    if (throwingEventId != null && throwingEventId.length() > 0) {
                        InterfaceMethod throwingEvent = null;

                        for (StartMethod method : throwingInterface
                                .getStartMethods()) {
                            if (throwingEventId.equals(method.getId())) {
                                throwingEvent = method;
                            }
                        }

                        if (throwingEvent == null) {
                            for (IntermediateMethod method : throwingInterface
                                    .getIntermediateMethods()) {
                                if (throwingEventId.equals(method.getId())) {
                                    throwingEvent = method;
                                }
                            }
                        }

                        if (throwingEvent != null) {
                            /*
                             * Throwing event found! Make a final check that
                             * it's not been changed to a fault message thrower.
                             */
                            if (TriggerType.MESSAGE_LITERAL
                                    .equals(throwingEvent.getTrigger())) {
                                addIssue(ID_CATCHERROREVENT_CANT_CATCH_THROWFAULT_ON_SUBPROCESS,
                                        catchErrorEvent);
                                return null;
                            }

                            // Throwing interface event found!
                            String name =
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName(throwingEvent);
                            return (name != null && name.length() > 0) ? name
                                    : Messages.CatchErrorEventRules_Unnamed_label;
                        }
                    }

                    addIssue(ID_CATCHERROREVENT_MISSING_THROWERINTERFACEEVENT,
                            catchErrorEvent,
                            Collections.singletonList(errorCode));

                } else {
                    addIssue(ID_CATCHERROREVENT_MISSING_THROWERINTERFACE,
                            catchErrorEvent,
                            Collections.singletonList(errorCode));
                }
            } else {
                addIssue(ID_CATCHERROREVENT_MISSING_THROWERINTERFACE,
                        catchErrorEvent,
                        Collections.singletonList(errorCode));
            }

        } else {
            // Type is 'Other' which means we don't know how to check it exists.
            // (Not without creating the tree of catchable errors in calling
            // method.
            return null;
        }

        return null;
    }

    /**
     * Check if error event is attached to task boundary (add issue if not).
     * 
     * @param catchErrorEvent
     * @return Activity that event is attached to or null if not attached.
     */
    private Activity checkAttached(Activity catchErrorEvent) {
        Activity attachedToTask =
                BpmnCatchableErrorUtil.getAttachedToTask(catchErrorEvent);
        if (attachedToTask == null) {
            // Not attached to a task! There's another rule to check that so we
            // don't need to fuss.
            // addIssue(ID_CATCHERROREVENT_NOT_ATTACHED, catchErrorEvent);
        }
        return attachedToTask;
    }

}
