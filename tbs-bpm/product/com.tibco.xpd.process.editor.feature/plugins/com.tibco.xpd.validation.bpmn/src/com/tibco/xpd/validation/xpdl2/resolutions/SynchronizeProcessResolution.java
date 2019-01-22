/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.util.DeleteActivityCommand;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.commands.ChangeEventTriggerTypeCommand;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
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
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This resolution goes through the entire process and fixes everything with
 * respect to the implementing process interface
 * 
 * <li>Sets trigger types of events appropriately to match those of the process
 * interface events.</li>
 * 
 * <li>Sets names, labels, error codes to those of the implementing process
 * interface</li>
 * 
 * <li>Deletes events that are not required because of changes in the process
 * interface.</li>
 * 
 * <li>Adds events that are required because of additions to the process
 * interface events.</li>
 * 
 * 
 * @author rsomayaj
 * 
 * 
 */
public class SynchronizeProcessResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Process) {
            Process process = (Process) target;

            LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();

            ProcessInterface processInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (null != processInterface) {
                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                /*
                 * Sid XPD-2087: We should ALWAYS validate everything regardless
                 * of whether it is local package or not. Otherwise if for some
                 * reason the pre-commit listener changes do not happen then the
                 * nothing catches the problem.
                 */
                resolveEvents(editingDomain,
                        cmd,
                        processInterface,
                        process,
                        allActivitiesInProc);

                resolveTriggersAndInvalidEvents(editingDomain,
                        cmd,
                        process,
                        processInterface,
                        allActivitiesInProc);

                removeNotRequiredEventsFromProcess(editingDomain,
                        cmd,
                        process,
                        allActivitiesInProc);
            }
            return cmd;
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param process
     * @param allActivitiesInProc
     */
    private void removeNotRequiredEventsFromProcess(
            EditingDomain editingDomain, LateExecuteCompoundCommand cmd,
            Process process, Collection<Activity> allActivitiesInProc) {
        for (Activity activity : allActivitiesInProc) {
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {

                if (implementedMethod == null
                        && ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity) == null) {
                    cmd.append(new DeleteActivityCommand(editingDomain,
                            activity));
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
                    if (isInterfaceEventNoneType(implementedMethod)
                            || !(ImplementInterfaceUtil
                                    .isRequestResponse(implementedMethod))) {
                        cmd.append(new DeleteActivityCommand(editingDomain,
                                activity));
                    }
                }
            }
        }
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
     * @param editingDomain
     * @param cmd
     * @param process
     * @param processInterface
     * @param allActivitiesInProc
     */
    private void resolveTriggersAndInvalidEvents(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd, Process process,
            ProcessInterface processInterface,
            Collection<Activity> allActivitiesInProc) {

        List<StartMethod> startMethods = processInterface.getStartMethods();
        for (StartMethod startMethod : startMethods) {
            resolveInvalidStartMethods(editingDomain,
                    cmd,
                    process,
                    allActivitiesInProc,
                    startMethod);

        }
        // look at all intermediate methods next
        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            // get the activity that corresponds to the implemented method
            resolveInvalidIntermediateMethods(editingDomain,
                    cmd,
                    process,
                    allActivitiesInProc,
                    intermediateMethod);
        }
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param process
     * @param allActivitiesInProc
     * @param intermediateMethod
     */
    private void resolveInvalidIntermediateMethods(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd, Process process,
            Collection<Activity> allActivitiesInProc,
            IntermediateMethod intermediateMethod) {
        // get the activity that corresponds to the implemented method
        resolveIntermediateEventTriggers(editingDomain,
                cmd,
                allActivitiesInProc,
                intermediateMethod);

        resolveIntermediateEventsIfNotPresent(editingDomain,
                cmd,
                process,
                allActivitiesInProc,
                intermediateMethod);

    }

    /**
     * @param cmd
     * @param editingDomain
     * @param process
     * @param allActivitiesInProc
     * @param intermediateMethod
     * @return
     */
    private void resolveIntermediateEventsIfNotPresent(
            EditingDomain editingDomain, LateExecuteCompoundCommand cmd,
            Process process, Collection<Activity> allActivitiesInProc,
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
            cmd.append(ImplementInterfaceUtil
                    .getAddOnlyImplementedIntermediateEvents(editingDomain,
                            process,
                            intermediateMethod,
                            new Point(120, 60)));
        }
        // End Message events and end error events are only required if the
        // interface method is a request-response operation.
        if (!ImplementInterfaceUtil
                .isNoneTypeInterfaceMethod(intermediateMethod)
                && ImplementInterfaceUtil.isRequestResponse(intermediateMethod)) {
            if (implementingEndActivity == null) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedEndResponseEventCommand(editingDomain,
                                process,
                                intermediateMethod,
                                new Point(200, 60)));
            }
        }

        int x = 200;
        for (ErrorMethod errMethod : intermediateMethod.getErrorMethods()) {
            if (!methodsPresent.contains(errMethod)) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedEndErrorEvent(editingDomain,
                                process,
                                errMethod,
                                new Point(x, 180)));
                x += 50;
            }
        }
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param allActivitiesInProc
     * @param startMethod
     */
    private void resolveIntermediateEventTriggers(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd,
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

                        EventTriggerType eventTriggerType =
                                EventTriggerType.EVENT_NONE_LITERAL;
                        if (TriggerType.MESSAGE_LITERAL
                                .equals(intermediateMethod.getTrigger())) {
                            eventTriggerType =
                                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
                        }
                        cmd.append(ChangeEventTriggerTypeCommand
                                .create(editingDomain,
                                        activity,
                                        eventTriggerType));
                    }
                } else if (activityEvent instanceof EndEvent) {
                    if (ImplementInterfaceUtil
                            .isRequestResponse(intermediateMethod)) {
                        ResultType type =
                                ((EndEvent) activityEvent).getResult();
                        if (intermediateMethod.getTrigger().getValue() != type
                                .getValue()) {
                            EventTriggerType eventTriggerType =
                                    EventTriggerType.EVENT_NONE_LITERAL;
                            if (TriggerType.MESSAGE_LITERAL
                                    .equals(intermediateMethod.getTrigger())) {
                                eventTriggerType =
                                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
                            }

                            cmd.append(ChangeEventTriggerTypeCommand
                                    .create(editingDomain,
                                            activity,
                                            eventTriggerType));
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
                            cmd.append(ChangeEventTriggerTypeCommand
                                    .create(editingDomain,
                                            activity,
                                            EventTriggerType.EVENT_ERROR_LITERAL));
                        }

                    }

                }

            }
        }
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param process
     * @param allActivitiesInProc
     * @param startMethod
     * @return
     */
    private void resolveInvalidStartMethods(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd, Process process,
            Collection<Activity> allActivitiesInProc, StartMethod startMethod) {
        // get the activity that corresponds to the implemented method
        resolveStartMethodTriggers(editingDomain,
                cmd,
                allActivitiesInProc,
                startMethod);

        resolveImplementedStartEventsIfNotPresent(editingDomain,
                cmd,
                process,
                allActivitiesInProc,
                startMethod);

    }

    /**
     * @param cmd
     * @param editingDomain
     * @param process
     * @param allActivitiesInProc
     * @param startMethod
     * @return
     */
    private void resolveImplementedStartEventsIfNotPresent(
            EditingDomain editingDomain, LateExecuteCompoundCommand cmd,
            Process process, Collection<Activity> allActivitiesInProc,
            StartMethod startMethod) {
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
            cmd.append(ImplementInterfaceUtil
                    .getAddOnlyImplementedStartEventCommand(editingDomain,
                            process,
                            startMethod,
                            new Point(60, 60)));
        }
        // End Message events and end error events are only required if the
        // interface method is a request-response operation.
        if (!ImplementInterfaceUtil.isNoneTypeInterfaceMethod(startMethod)
                && ImplementInterfaceUtil.isRequestResponse(startMethod)) {
            if (implementingEndActivity == null) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedEndResponseEventCommand(editingDomain,
                                process,
                                startMethod,
                                new Point(400, 60)));
            }
        }

        int x = 400;
        for (ErrorMethod errMethod : startMethod.getErrorMethods()) {
            if (!methodsPresent.contains(errMethod)) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedEndErrorEvent(editingDomain,
                                process,
                                errMethod,
                                new Point(x, 180)));
                x += 50;
            }
        }
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param allActivitiesInProc
     * @param startMethod
     */
    private void resolveStartMethodTriggers(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd,
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
                        EventTriggerType eventTriggerType =
                                EventTriggerType.EVENT_NONE_LITERAL;
                        if (TriggerType.MESSAGE_LITERAL.equals(startMethod
                                .getTrigger())) {
                            eventTriggerType =
                                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
                        }
                        cmd.append(ChangeEventTriggerTypeCommand
                                .create(editingDomain,
                                        activity,
                                        eventTriggerType));
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

                            EventTriggerType eventTriggerType =
                                    EventTriggerType.EVENT_NONE_LITERAL;
                            if (TriggerType.MESSAGE_LITERAL.equals(startMethod
                                    .getTrigger())) {
                                eventTriggerType =
                                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
                            }

                            cmd.append(ChangeEventTriggerTypeCommand
                                    .create(editingDomain,
                                            activity,
                                            eventTriggerType));
                        }
                    }

                }
            }
        }
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param processInterface
     * @param process
     * @param allActivitiesInProc
     */
    private void resolveEvents(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd, ProcessInterface processInterface,
            Process process, Collection<Activity> allActivitiesInProc) {
        // Need to verify if the events names are in sync,
        for (Activity act : allActivitiesInProc) {
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(act);
            if (null != implementedMethod) {
                EventFlowType flowType = EventObjectUtil.getFlowType(act);
                if (EventFlowType.FLOW_START_LITERAL.equals(flowType)
                        || EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                    if (!(implementedMethod.getName().equals(act.getName()))
                            || !(Xpdl2ModelUtil
                                    .getDisplayName(implementedMethod)
                                    .equals(Xpdl2ModelUtil.getDisplayName(act)))) {
                        // Set Name for activity
                        cmd.append(SetCommand.create(editingDomain,
                                act,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                implementedMethod.getName()));
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        act,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        Xpdl2ModelUtil
                                                .getDisplayName(implementedMethod)));

                    }
                    if (!areAssocParamsInSync(implementedMethod, act)) {
                        redoAssociatedParams(cmd,
                                editingDomain,
                                implementedMethod,
                                act);
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
                        cmd.append(SetCommand.create(editingDomain,
                                resultError,
                                Xpdl2Package.eINSTANCE
                                        .getResultError_ErrorCode(),
                                implementedErrorMethod.getErrorCode()));

                    }
                    if (!areAssocParamsInSync(implementedErrorMethod, act)) {
                        redoAssociatedParams(cmd,
                                editingDomain,
                                implementedErrorMethod,
                                act);
                    }
                }
            }
        }
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
     * @param cmd
     * @param editingDomain
     * @param assocParamsContainer
     * @param activity
     */
    private void redoAssociatedParams(CompoundCommand cmd,
            EditingDomain editingDomain,
            AssociatedParametersContainer assocParamsContainer,
            Activity activity) {
        List<AssociatedParameter> ifcAssocParams =
                assocParamsContainer.getAssociatedParameters();
        clearActivityAssocParams(cmd, editingDomain, activity);
        appendActivityAssocParams(cmd, editingDomain, activity, ifcAssocParams);
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param activity
     * @param ifcAssocParams
     */
    private void appendActivityAssocParams(CompoundCommand cmd,
            EditingDomain editingDomain, Activity activity,
            List<AssociatedParameter> ifcAssocParams) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        AssociatedParameters associatedParameters = null;
        if (otherElement == null) {
            associatedParameters =
                    XpdExtensionFactory.eINSTANCE.createAssociatedParameters();
        } else {
            associatedParameters = (AssociatedParameters) otherElement;
        }
        if (associatedParameters != null) {
            List<AssociatedParameter> assocParamsToBeAdded =
                    (List<AssociatedParameter>) EcoreUtil
                            .copyAll(ifcAssocParams);
            if (!assocParamsToBeAdded.isEmpty()) {
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters(),
                                associatedParameters));
                cmd.append(AddCommand.create(editingDomain,
                        associatedParameters,
                        XpdExtensionPackage.eINSTANCE
                                .getAssociatedParameters_AssociatedParameter(),
                        assocParamsToBeAdded));
                cmd.append(UpdateMappingsCommandFactory
                        .getUpdateMappingsCommand(editingDomain, activity));
            }
        }

    }

    /**
     * @param cmd
     * @param editingDomain
     * @param activity
     */
    private void clearActivityAssocParams(CompoundCommand cmd,
            EditingDomain editingDomain, Activity activity) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        if (otherElement instanceof AssociatedParameters) {
            AssociatedParameters associatedParameters =
                    (AssociatedParameters) otherElement;
            cmd.append(RemoveCommand.create(editingDomain,
                    associatedParameters,
                    XpdExtensionPackage.eINSTANCE
                            .getAssociatedParameters_AssociatedParameter(),
                    associatedParameters.getAssociatedParameter()));
        }
    }

}
