/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule validates the process against the implementing process interface
 * and complains if it isn't in sync.
 * 
 * If a process implements a process interface, it must implement the
 * events(methods) in the process interface, and should have the same trigger
 * types
 * 
 * @author rsomayaj
 * @since 3.3 (4 Feb 2010)
 */
public class ProcImplementingProcIfcRule extends ProcessValidationRule {

    private static final String PROCESS_OUTOFSYNC =
            "bpmn.processOutOfSyncProcIfc"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {

        ProcessInterface processInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);

        if (null != processInterface) {
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            /*
             * Sid XPD-2087: We should ALWAYS validate everything regardless of
             * whether it is local package or not. Otherwise if for some reason
             * the pre-commit listener changes do not happen then the nothing
             * catches the problem.
             */
            Boolean areEventsInSync =
                    areEventInExternalPckgProcInSync(processInterface,
                            process,
                            allActivitiesInProc);
            if (!areEventsInSync) {
                addIssue(PROCESS_OUTOFSYNC, process);
                return;
            }

            // Validates start method and intermediate methods whether their
            // trigger types don't match or whether there needs to be some which
            // are absent.
            boolean areTriggersAndMethodsInvalid =
                    areTriggersAndMethodsInvalid(process,
                            processInterface,
                            allActivitiesInProc);

            if (areTriggersAndMethodsInvalid) {
                addIssue(PROCESS_OUTOFSYNC, process);
            } else {
                // Validates if the are few events which exist which should
                // actually do
                boolean areNotRqdEventsPresent =
                        areNotRequiredEventsPresent(process,
                                allActivitiesInProc);

                if (areNotRqdEventsPresent) {
                    addIssue(PROCESS_OUTOFSYNC, process);
                }
            }

        }
    }

    /**
     * @param procIfc
     * @param proc
     * @param allActivitiesInProc
     * @return
     */
    private Boolean areEventInExternalPckgProcInSync(ProcessInterface procIfc,
            Process proc, Collection<Activity> allActivitiesInProc) {
        for (Activity act : allActivitiesInProc) {
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(act);
            if (implementedMethod != null) {
                EventFlowType flowType = EventObjectUtil.getFlowType(act);
                if (EventFlowType.FLOW_START_LITERAL.equals(flowType)
                        || EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                    if (!(implementedMethod.getName().equals(act.getName()))
                            || !(Xpdl2ModelUtil
                                    .getDisplayName(implementedMethod)
                                    .equals(Xpdl2ModelUtil.getDisplayName(act)))) {
                        return Boolean.FALSE;
                    }
                    if (!areAssocParamsInSync(implementedMethod, act)) {
                        return Boolean.FALSE;
                    }
                }
            } else {
                ErrorMethod implementedErrorMethod =
                        ProcessInterfaceUtil.getImplementedErrorMethod(act);
                if (implementedErrorMethod != null) {
                    ResultError resultError =
                            EventObjectUtil.getResultError(act);
                    if (resultError.getErrorCode() != null
                            && !resultError
                                    .getErrorCode()
                                    .equals(implementedErrorMethod.getErrorCode())) {
                        return Boolean.FALSE;
                    }
                    if (!areAssocParamsInSync(implementedErrorMethod, act)) {
                        return Boolean.FALSE;
                    }
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * @param process
     * @param allActivitiesInProc
     * @return
     */
    private Boolean areNotRequiredEventsPresent(Process process,
            Collection<Activity> allActivitiesInProc) {
        for (Activity activity : allActivitiesInProc) {
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                if (implementedMethod == null
                        && ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity) == null) {
                    return Boolean.TRUE;
                }

            }

            // if the activity is an end event, and the implemented end event is
            // no more a request-response operation, then the end event needs to
            // be deleted. Only applies to message events.
            EventFlowType flowType = EventObjectUtil.getFlowType(activity);
            if (implementedMethod != null
                    && EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    if (!(ImplementInterfaceUtil
                            .isRequestResponse(implementedMethod))) {
                        if (!isInterfaceEventNoneType(implementedMethod)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @param interfaceMethod
     * @return
     */
    private boolean isInterfaceEventNoneType(InterfaceMethod interfaceMethod) {
        if (interfaceMethod.getTrigger() == TriggerType.NONE_LITERAL) {
            return true;
        }
        return false;
    }

    /**
     * @param process
     * @param processInterface
     * @param allActivitiesInProc
     * @return
     */
    private Boolean areTriggersAndMethodsInvalid(Process process,
            ProcessInterface processInterface,
            Collection<Activity> allActivitiesInProc) {
        List<StartMethod> startMethods = processInterface.getStartMethods();
        for (StartMethod startMethod : startMethods) {
            if (isStartMethodInvalid(process, allActivitiesInProc, startMethod)) {
                return Boolean.TRUE;
            }
        }
        // look at all intermediate methods next
        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            // get the activity that corresponds to the implemented method
            if (isIntermediateMethodInvalid(process,
                    allActivitiesInProc,
                    intermediateMethod)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @param process
     * @param allActivitiesInProc
     * @param intermediateMethod
     */
    private Boolean isIntermediateMethodInvalid(Process process,
            Collection<Activity> allActivitiesInProc,
            IntermediateMethod intermediateMethod) {
        // get the activity that corresponds to the implemented method
        boolean areIntermediateEventTriggersInvalid =
                areIntermediateEventTriggersInvalid(allActivitiesInProc,
                        intermediateMethod);

        if (areIntermediateEventTriggersInvalid) {
            return Boolean.TRUE;
        }

        boolean areImplementedIntermediateEventsPresent =
                areImplementedIntermediateEventsPresent(process,
                        allActivitiesInProc,
                        intermediateMethod);

        if (!areImplementedIntermediateEventsPresent) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param process
     * @param allActivitiesInProc
     * @param intermediateMethod
     * @return
     */
    private boolean areImplementedIntermediateEventsPresent(Process process,
            Collection<Activity> allActivitiesInProc,
            IntermediateMethod intermediateMethod) {
        List<ErrorMethod> methodsPresent = new ArrayList<ErrorMethod>();
        Activity implementingIntermediateActivity = null;
        Activity implementingEndActivity = null;

        for (Activity activity : allActivitiesInProc) {
            if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(EventObjectUtil
                    .getFlowType(activity))) {
                if (intermediateMethod.equals(ProcessInterfaceUtil
                        .getImplementedIntermediateMethod(activity))) {
                    implementingIntermediateActivity = activity;
                }
            } else if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                    .getFlowType(activity))) {
                if (intermediateMethod.equals(ProcessInterfaceUtil
                        .getImplementedIntermediateMethod(activity))) {
                    implementingEndActivity = activity;
                }
                ErrorMethod implementedErrorMethod =
                        ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity);
                if (implementedErrorMethod != null) {
                    EObject objInList =
                            EMFSearchUtil.findInList(intermediateMethod
                                    .getErrorMethods(),
                                    Xpdl2Package.eINSTANCE
                                            .getUniqueIdElement_Id(),
                                    implementedErrorMethod.getId());
                    if (implementedErrorMethod.equals(objInList)) {
                        methodsPresent.add((ErrorMethod) objInList);
                    }
                }
            }
        }
        if (implementingIntermediateActivity == null) {
            return Boolean.FALSE;
        }
        // End Message events and end error events are only required if the
        // interface method is a request-response operation.
        if (!ImplementInterfaceUtil
                .isNoneTypeInterfaceMethod(intermediateMethod)
                && ImplementInterfaceUtil.isRequestResponse(intermediateMethod)) {
            if (implementingEndActivity == null) {
                return Boolean.FALSE;
            }
        }
        for (ErrorMethod errMethod : intermediateMethod.getErrorMethods()) {
            if (!methodsPresent.contains(errMethod)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * @param process
     * @param allActivitiesInProc
     * @param startMethod
     * @return
     */
    private boolean isStartMethodInvalid(Process process,
            Collection<Activity> allActivitiesInProc, StartMethod startMethod) {
        // get the activity that corresponds to the implemented method
        boolean areStartEventTriggersInvalid =
                areStartEventTriggersInvalid(allActivitiesInProc, startMethod);

        if (areStartEventTriggersInvalid) {
            return Boolean.TRUE;
        }

        boolean areImplementedStartEventsPresent =
                areImplementedStartEventsPresent(process,
                        allActivitiesInProc,
                        startMethod);

        if (!areImplementedStartEventsPresent) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param allActivitiesInProc
     * @param startMethod
     */
    private boolean areIntermediateEventTriggersInvalid(
            Collection<Activity> allActivitiesInProc,
            IntermediateMethod intermediateMethod) {
        List implementedActs =
                EMFSearchUtil.findManyInList((List) allActivitiesInProc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements(),
                        intermediateMethod.getId());
        for (Object obj : implementedActs) {
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                // if activity is an intermediate event then check to
                // see if
                // the trigger type matches the
                // implemented method one and if not we add an issue
                Event activityEvent = activity.getEvent();
                if (activityEvent instanceof IntermediateEvent) {
                    TriggerType type =
                            ((IntermediateEvent) activityEvent).getTrigger();
                    if (!(intermediateMethod.getTrigger().equals(type))) {
                        return Boolean.TRUE;
                    }
                } else if (activityEvent instanceof EndEvent) {
                    if (ImplementInterfaceUtil
                            .isRequestResponse(intermediateMethod)) {
                        ResultType type =
                                ((EndEvent) activityEvent).getResult();
                        if (intermediateMethod.getTrigger().getValue() != type
                                .getValue()) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        }
        if (ImplementInterfaceUtil.isRequestResponse(intermediateMethod)) {
            List<ErrorMethod> errorMethods =
                    intermediateMethod.getErrorMethods();
            for (ErrorMethod errMethod : errorMethods) {
                EObject object =
                        EMFSearchUtil.findInList((List) allActivitiesInProc,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Implements(),
                                errMethod.getId());
                if (object instanceof Activity) {
                    Activity activity = (Activity) object;
                    if (activity.getEvent() instanceof EndEvent) {
                        ResultType type =
                                ((EndEvent) activity.getEvent()).getResult();
                        if (!ResultType.ERROR_LITERAL.equals(type)) {
                            return Boolean.TRUE;
                        }
                    }

                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @param allActivitiesInProc
     * @param startMethod
     */
    private boolean areStartEventTriggersInvalid(
            Collection<Activity> allActivitiesInProc, StartMethod startMethod) {
        List implementedActs =
                EMFSearchUtil.findManyInList((List) allActivitiesInProc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements(),
                        startMethod.getId());
        for (Object obj : implementedActs) {
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                // if activity is a start event then check to see if the
                // trigger type matches the
                // implemented method one and if not we add an issue
                Event activityEvent = activity.getEvent();
                if (activityEvent instanceof StartEvent) {
                    TriggerType type =
                            ((StartEvent) activityEvent).getTrigger();
                    if (type.getValue() != startMethod.getTrigger().getValue()) {
                        return Boolean.TRUE;
                    }
                } else if (activityEvent instanceof EndEvent) {
                    if (!ImplementInterfaceUtil
                            .isNoneTypeInterfaceMethod(startMethod)
                            && ImplementInterfaceUtil
                                    .isRequestResponse(startMethod)) {
                        ResultType type =
                                ((EndEvent) activityEvent).getResult();
                        if (startMethod.getTrigger().getValue() != type
                                .getValue()) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @param process
     * @param allActivitiesInProc
     * @param startMethod
     * @return
     */
    private boolean areImplementedStartEventsPresent(Process process,
            Collection<Activity> allActivitiesInProc, StartMethod startMethod) {
        List<ErrorMethod> methodsPresent = new ArrayList<ErrorMethod>();
        Activity implementingStartActivity = null;
        Activity implementingEndActivity = null;

        for (Activity activity : allActivitiesInProc) {
            if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                    .getFlowType(activity))) {
                if (startMethod.equals(ProcessInterfaceUtil
                        .getImplementedStartMethod(activity))) {
                    implementingStartActivity = activity;
                }
            } else if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                    .getFlowType(activity))) {
                if (startMethod.equals(ProcessInterfaceUtil
                        .getImplementedStartMethod(activity))) {
                    implementingEndActivity = activity;
                }
                ErrorMethod implementedErrorMethod =
                        ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity);
                if (implementedErrorMethod != null) {
                    EObject objInList =
                            EMFSearchUtil.findInList(startMethod
                                    .getErrorMethods(),
                                    Xpdl2Package.eINSTANCE
                                            .getUniqueIdElement_Id(),
                                    implementedErrorMethod.getId());
                    if (implementedErrorMethod.equals(objInList)) {
                        methodsPresent.add((ErrorMethod) objInList);
                    }
                }
            }
        }
        if (implementingStartActivity == null) {
            return Boolean.FALSE;
        }
        // End Message events and end error events are only required if the
        // interface method is a request-response operation.
        if (!ImplementInterfaceUtil.isNoneTypeInterfaceMethod(startMethod)
                && ImplementInterfaceUtil.isRequestResponse(startMethod)) {
            if (implementingEndActivity == null) {
                return Boolean.FALSE;
            }
        }
        for (ErrorMethod errMethod : startMethod.getErrorMethods()) {
            if (!methodsPresent.contains(errMethod)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * @param implementedProcessInterface
     * @param process
     * @return
     */
    private boolean isExternalPkgProcIfc(
            ProcessInterface implementedProcessInterface, Process process) {

        WorkingCopy ifcWC = null;
        if (implementedProcessInterface != null) {
            ifcWC =
                    WorkingCopyUtil
                            .getWorkingCopyFor(implementedProcessInterface);
        }
        WorkingCopy procWC = WorkingCopyUtil.getWorkingCopyFor(process);
        if (procWC != null && !procWC.equals(ifcWC)) {
            return true;
        }
        return false;
    }

    /**
     * @param assocParamsContainer
     * @param act
     */
    private Boolean areAssocParamsInSync(
            AssociatedParametersContainer assocParamsContainer, Activity act) {
        List<AssociatedParameter> ifcMethodAssocParams =
                assocParamsContainer.getAssociatedParameters();
        List<AssociatedParameter> activityAssociatedParameters =
                ProcessInterfaceUtil.getActivityAssociatedParameters(act);
        if (ifcMethodAssocParams.size() != activityAssociatedParameters.size()) {
            return Boolean.FALSE;
        } else {
            Comparator<AssociatedParameter> comparator =
                    new Comparator<AssociatedParameter>() {
                        /**
                         * @see java.util.Comparator#compare(java.lang.Object,
                         *      java.lang.Object)
                         * 
                         * @param o1
                         * @param o2
                         * @return
                         */
                        @Override
                        public int compare(AssociatedParameter o1,
                                AssociatedParameter o2) {
                            return (o1).getFormalParam()
                                    .compareTo((o2).getFormalParam());
                        }
                    };
            EqualityHelperXpd equalityHelper = new EqualityHelperXpd();
            equalityHelper.addDefaultValue(XpdExtensionPackage.eINSTANCE
                    .getAssociatedParameter_Mandatory(), Boolean.FALSE);
            if (!(equalityHelper.equals(ifcMethodAssocParams,
                    activityAssociatedParameters,
                    comparator))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}