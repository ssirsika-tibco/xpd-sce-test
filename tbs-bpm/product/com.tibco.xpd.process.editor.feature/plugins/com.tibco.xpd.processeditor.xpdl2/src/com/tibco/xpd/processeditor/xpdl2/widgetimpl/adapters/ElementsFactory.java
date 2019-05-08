/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.AssociationDirectionType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerIntermediateMultiple;
import com.tibco.xpd.xpdl2.TriggerMultiple;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Factory method for elements that are created in different places
 * 
 * @author wzurek
 */
public class ElementsFactory {

    private static final String EMPTY_STRING = "";//$NON-NLS-1$

    private static Xpdl2Factory xpdlFact = Xpdl2Factory.eINSTANCE;

    /**
     * Create task in given location with all default settings
     * 
     * @param loc
     * @param size
     * @param laneId
     *            , may be null if it belongs to the activity set
     * @return
     */
    public static Activity createTask(Point loc, Dimension size, String laneId,
            String fillColor, String lineColor) {
        return createTask(loc,
                size,
                laneId,
                TaskType.NONE_LITERAL,
                fillColor,
                lineColor);
    }

    /**
     * Create task in given location with all default settings
     * 
     * @param loc
     * @param size
     * @param laneId
     *            , may be null if it belongs to the activity set
     * @param tt
     * @return
     */
    public static Activity createTask(Point loc, Dimension size, String laneId,
            TaskType tt, String fillColor, String lineColor) {

        Activity act = createActivity(loc, size, laneId, fillColor, lineColor);

        /*
         * ABPM-911: Saket: An event subprocess should mostly behave like an
         * embedded sub-process.
         */
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(tt)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(tt)) {
            // create reference to internal (unknown now) activity set
            BlockActivity bAct = xpdlFact.createBlockActivity();
            bAct.setActivitySetId("-unknown-"); //$NON-NLS-1$
            act.setBlockActivity(bAct);
            bAct.setView(ViewType.EXPANDED);

            /*
             * ABPM-911: Saket: Set the xpdExt:IsEventSubProcess attribute based
             * on the type of the subprocess.
             */
            if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(tt)) {

                Xpdl2ModelUtil.setOtherAttribute(act.getBlockActivity(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsEventSubProcess(),
                        true);
            } else {
                Xpdl2ModelUtil.setOtherAttribute(act.getBlockActivity(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsEventSubProcess(),
                        false);
            }

        } else if (TaskType.SUBPROCESS_LITERAL.equals(tt)) {
            // create reference to external (unknown now) process
            SubFlow sFlow = xpdlFact.createSubFlow();
            sFlow.setProcessId("-unknown-"); //$NON-NLS-1$
            act.setImplementation(sFlow);

            /*
             * XPD-3445: With new Sub Process Start Strategy in place we now
             * don't initially have a default priority (because this will be
             * decided according to the type of sub-process selected in
             * SubProcessStartStrategyPreCommitCommandContributor
             */

        } else if (TaskType.REFERENCE_LITERAL.equals(tt)) {
            // XPDL2 has duplication of reference type
            // (xpdl2:implementation/xpdl:Reference and
            // xpdl2:Implementation/xpdl2:Task/xpdl2:TaskReference) but the
            // former works for both sub-proc and tasks so we'll use that.
            Reference ref = xpdlFact.createReference();
            ref.setActivityId("-unknown-"); //$NON-NLS-1$
            act.setImplementation(ref);

            // Now Default to configuration to Library Task reference
            if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
                TaskLibraryReference extActRef =
                        XpdExtensionFactory.eINSTANCE
                                .createTaskLibraryReference();
                extActRef.setTaskLibraryId(""); //$NON-NLS-1$
                Xpdl2ModelUtil.addOtherElement(ref,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TaskLibraryReference(),
                        extActRef);
            }

        } else if (TaskType.NONE_LITERAL.equals(tt)) {
            // Task type None is now Implementation/No can't have empty Task
            // attribute.
            act.setImplementation(xpdlFact.createNo());

        } else {
            Task task = createActivityTaskElement(tt);
            act.setImplementation(task);
            addResourcePatterns(act, tt);
        }

        setActivityVisibility(act, tt);
        act.setName(NameUtil.getInternalName(tt.toString(), false));
        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                tt.toString());

        /*
         * Check if there's a default icon setup for this task type.
         */
        String defIconPath = TaskObjectUtil.getDefaultIconForTaskType(act);
        if (defIconPath != null) {
            Icon ico = Xpdl2Factory.eINSTANCE.createIcon();
            ico.setValue(defIconPath);
            act.setIcon(ico);
        }

        return act;
    }

    /**
     * @param act
     * @param tt
     */
    private static void addResourcePatterns(Activity act, TaskType tt) {
        ActivityResourcePatterns patterns = createActivityResourcePatterns(tt);

        Xpdl2ModelUtil.setOtherElement(act, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ActivityResourcePatterns(), patterns);

    }

    /**
     * @param tt
     * @return new {@link ActivityResourcePatterns} configured according to task
     *         type.
     */
    public static ActivityResourcePatterns createActivityResourcePatterns(
            TaskType tt) {
        ActivityResourcePatterns patterns =
                XpdExtensionFactory.eINSTANCE.createActivityResourcePatterns();
        patterns.setAllocationStrategy(createAllocationStrategy(tt));
        WorkItemPriority createWorkItemPriority =
                XpdExtensionFactory.eINSTANCE.createWorkItemPriority();
        createWorkItemPriority
                .setInitialPriority(Xpdl2ModelUtil.DEFAULT_WORK_ITEM_PRIORITY);
        switch (tt.getValue()) {

        case TaskType.USER:
            patterns.setWorkItemPriority(createWorkItemPriority);
        case TaskType.MANUAL:
            patterns.setWorkItemPriority(createWorkItemPriority);
        case TaskType.SCRIPT:
        case TaskType.RECEIVE:
        case TaskType.SEND:
        case TaskType.SERVICE:
        case TaskType.DTABLE:
            break;
        }
        return patterns;
    }

    /**
     * @param tt
     * @return new {@link AllocationStrategy} configured according to task type.
     */
    public static AllocationStrategy createAllocationStrategy(TaskType tt) {
        AllocationStrategy strategy =
                XpdExtensionFactory.eINSTANCE.createAllocationStrategy();
        switch (tt.getValue()) {

        case TaskType.USER:
            strategy.setOffer(AllocationType.OFFER_ALL);
            strategy.setReOfferOnCancel(true);
            strategy.setReOfferOnClose(true);
        case TaskType.MANUAL:
        case TaskType.SCRIPT:
        case TaskType.RECEIVE:
        case TaskType.SEND:
        case TaskType.SERVICE:
        case TaskType.DTABLE:
            strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
            break;
        }

        return strategy;
    }

    /**
     * Create activity's implementation for given TaskType
     * 
     * @param tt
     * @return
     */
    public static Task createActivityTaskElement(TaskType tt) {
        Task task = xpdlFact.createTask();

        switch (tt.getValue()) {
        case TaskType.NONE:
            throw new IllegalArgumentException(
                    "createActivityTaskElement(): Task Type None is not implemented as a Task element."); //$NON-NLS-1$

        case TaskType.MANUAL: {
            TaskManual mt = xpdlFact.createTaskManual();
            // Add blank elements for valid xpdl
            Performer performer = xpdlFact.createPerformer();
            performer.setValue("-defined-in-Activity-Performer-"); //$NON-NLS-1$

            mt.getPerformers().add(performer);

            task.setTaskManual(mt);
            break;
        }
        case TaskType.RECEIVE:
            /*
             * Sid ACE-484 - Don't default implementation type to WebService any
             * more.
             */
            TaskReceive recv = xpdlFact.createTaskReceive();
            recv.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);

            // Add blank elements for valid xpdl
            recv.setInstantiate(false);
            recv.setMessage(xpdlFact.createMessage());

            task.setTaskReceive(recv);
            break;
        case TaskType.REFERENCE:
            throw new IllegalArgumentException(
                    "createActivityTaskElement(): Task Type Reference is not implemented as a Task element."); //$NON-NLS-1$
        case TaskType.SCRIPT:
            TaskScript sct = xpdlFact.createTaskScript();

            // Add blank elements for valid xpdl
            sct.setScript(xpdlFact.createExpression());
            task.setTaskScript(sct);
            break;
        case TaskType.SEND:
            TaskSend sendt = xpdlFact.createTaskSend();
            /*
             * Sid ACE-484 - Don't default implementation type to WebService any
             * more.
             */
            /* Web service should be the default implementation. */
            sendt.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);

            // Add blank elements for valid xpdl
            sendt.setMessage(xpdlFact.createMessage());

            task.setTaskSend(sendt);
            break;
        case TaskType.SERVICE:
            TaskService sert = xpdlFact.createTaskService();
            sert.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);

            // Add blank elements for valid xpdl
            sert.setMessageIn(xpdlFact.createMessage());
            sert.setMessageOut(xpdlFact.createMessage());

            task.setTaskService(sert);
            break;
        case TaskType.DTABLE:
            TaskService sertDtable = xpdlFact.createTaskService();
            sertDtable.setImplementation(ImplementationType.OTHER_LITERAL);
            Xpdl2ModelUtil.setOtherAttribute(sertDtable,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    TaskImplementationTypeDefinitions.DECISION_SERVICE);
            // Add blank elements for valid xpdl
            sertDtable.setMessageIn(xpdlFact.createMessage());
            sertDtable.setMessageOut(xpdlFact.createMessage());

            task.setTaskService(sertDtable);
            break;
        case TaskType.USER: {
            TaskUser ut = xpdlFact.createTaskUser();
            ut.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);

            // Add blank elements for valid xpdl
            Performer performer = xpdlFact.createPerformer();
            performer.setValue("-defined-in-Activity-Performer-"); //$NON-NLS-1$
            ut.getPerformers().add(performer);
            ut.setMessageIn(xpdlFact.createMessage());
            ut.setMessageOut(xpdlFact.createMessage());

            task.setTaskUser(ut);
            break;
        }
        }
        return task;
    }

    /**
     * Create gateway in given location with all default settings
     * 
     * @param loc
     * @param laneId
     *            , may be null if it belongs to the activity set
     * @return
     */
    public static Activity createGateway(Point loc, Dimension size,
            String laneId, String fillColor, String lineColor) {
        return createGateway(loc,
                size,
                laneId,
                GatewayType.EXCLUSIVE_DATA_LITERAL,
                fillColor,
                lineColor);
    }

    /**
     * Create gateway in given location with all default settings
     * 
     * @param loc
     * @param size
     * @param laneId
     *            , may be null if it belongs to the activity set
     * @param gatewayType
     * @param fillColor
     * @param lineColor
     * @return
     */
    public static Activity createGateway(Point loc, Dimension size,
            String laneId, GatewayType gatewayType, String fillColor,
            String lineColor) {
        Activity act = xpdlFact.createActivity();

        NodeGraphicsInfo gn = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act);

        int width, height;
        width = size.width;
        height = size.height;
        gn.setCoordinates(xpdlFact.createCoordinates(loc.x, loc.y));
        gn.setLaneId(laneId);
        gn.setWidth(width);
        gn.setHeight(height);
        gn.setFillColor(fillColor);
        gn.setBorderColor(lineColor);

        Route r = xpdlFact.createRoute();
        act.setRoute(r);
        // act.setName("Gateway"); // Don't want default naming on gateways /
        // events.

        switch (gatewayType.getValue()) {
        case GatewayType.COMPLEX:
            r.setGatewayType(JoinSplitType.COMPLEX_LITERAL);
            break;
        case GatewayType.INCLUSIVE:
            r.setGatewayType(JoinSplitType.INCLUSIVE_LITERAL);
            break;
        case GatewayType.PARALLEL:
            r.setGatewayType(JoinSplitType.PARALLEL_LITERAL);
            break;
        case GatewayType.EXLCUSIVE_DATA:
            r.setGatewayType(JoinSplitType.EXCLUSIVE_LITERAL);
            r.setExclusiveType(ExclusiveType.DATA);
            r.setMarkerVisible(true);
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            r.setGatewayType(JoinSplitType.EXCLUSIVE_LITERAL);
            r.setExclusiveType(ExclusiveType.EVENT);
            break;
        }

        return act;
    }

    /**
     * Create event in given location with all default settings
     * 
     * @param loc
     * @param size
     * @param laneId
     *            , may be null if it belongs to the activity set
     * @param flowType
     * @param eventType
     * @param fillColor
     * @param lineColor
     * @param parentProcess
     *            Currently only used for creating signal/error events (for
     *            assigning default signal/error code names) - can be null
     *            otherwise.
     * @return
     */
    public static Activity createEvent(Point loc, Dimension size,
            String laneId, EventFlowType flowType, EventTriggerType eventType,
            String fillColor, String lineColor, Process parentProcess) {

        Activity act = xpdlFact.createActivity();

        NodeGraphicsInfo gn = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act);

        int width, height;
        width = size.width;
        height = size.height;
        gn.setCoordinates(xpdlFact.createCoordinates(loc.x, loc.y));
        gn.setLaneId(laneId);
        gn.setWidth(width);
        gn.setHeight(height);
        gn.setFillColor(fillColor);
        gn.setBorderColor(lineColor);

        switch (flowType.getValue()) {
        case EventFlowType.FLOW_END: {
            EndEvent endEvent = xpdlFact.createEndEvent();

            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_THROW:
                /*
                 * Sid ACE-484 - Don't default implementation type to WebService
                 * any more.
                 */
                endEvent.setResult(ResultType.MESSAGE_LITERAL);
                endEvent.setImplementation(
                        ImplementationType.UNSPECIFIED_LITERAL);
                endEvent.setTriggerResultMessage((TriggerResultMessage) createEventDetail(flowType,
                        eventType,
                        parentProcess));
                break;
            case EventTriggerType.EVENT_ERROR:
                endEvent.setResult(ResultType.ERROR_LITERAL);
                endEvent.setResultError((ResultError) createEventDetail(flowType,
                        eventType,
                        parentProcess));
                break;
            case EventTriggerType.EVENT_CANCEL:
                endEvent.setResult(ResultType.CANCEL_LITERAL);
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                endEvent.setResult(ResultType.COMPENSATION_LITERAL);
                endEvent.setTriggerResultCompensation((TriggerResultCompensation) createEventDetail(flowType,
                        eventType,
                        parentProcess));
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                endEvent.setResult(ResultType.SIGNAL_LITERAL);
                endEvent.setTriggerResultSignal((TriggerResultSignal) createEventDetail(flowType,
                        eventType,
                        parentProcess));
                /*
                 * Set default value to 'true' for implicit data association for
                 * new throw signal event
                 */
                disableImplicitAssociation(act);
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                endEvent.setResult(ResultType.MULTIPLE_LITERAL);
                endEvent.setResultMultiple((ResultMultiple) createEventDetail(flowType,
                        eventType,
                        parentProcess));
                break;
            case EventTriggerType.EVENT_TERMINATE:
                endEvent.setResult(ResultType.TERMINATE_LITERAL);
                break;
            default:
                endEvent.setResult(ResultType.NONE_LITERAL);
                break;
            }

            act.setEvent(endEvent);
            break;
        }
        case EventFlowType.FLOW_INTERMEDIATE: {
            IntermediateEvent intermediateEvent =
                    xpdlFact.createIntermediateEvent();

            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
            case EventTriggerType.EVENT_MESSAGE_THROW:
                /*
                 * Sid ACE-484 - Don't default implementation type to WebService
                 * any more.
                 */
                intermediateEvent.setTrigger(TriggerType.MESSAGE_LITERAL);
                intermediateEvent
                        .setTriggerResultMessage((TriggerResultMessage) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                intermediateEvent
                        .setImplementation(
                                ImplementationType.UNSPECIFIED_LITERAL);
                break;
            case EventTriggerType.EVENT_TIMER:
                intermediateEvent.setTrigger(TriggerType.TIMER_LITERAL);
                intermediateEvent
                        .setTriggerTimer((TriggerTimer) createEventDetail(flowType,
                                eventType,
                                parentProcess));

                act.getDeadline().add(createDeadline());
                break;
            case EventTriggerType.EVENT_ERROR:
                intermediateEvent.setTrigger(TriggerType.ERROR_LITERAL);
                intermediateEvent
                        .setResultError((ResultError) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_CANCEL:
                intermediateEvent.setTrigger(TriggerType.CANCEL_LITERAL);
                break;
            case EventTriggerType.EVENT_COMPENSATION_CATCH:
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                intermediateEvent.setTrigger(TriggerType.COMPENSATION_LITERAL);
                intermediateEvent
                        .setTriggerResultCompensation((TriggerResultCompensation) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                intermediateEvent.setTrigger(TriggerType.CONDITIONAL_LITERAL);
                intermediateEvent
                        .setTriggerConditional((TriggerConditional) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_LINK_CATCH:
            case EventTriggerType.EVENT_LINK_THROW:
                intermediateEvent.setTrigger(TriggerType.LINK_LITERAL);
                intermediateEvent
                        .setTriggerResultLink((TriggerResultLink) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                intermediateEvent.setTrigger(TriggerType.MULTIPLE_LITERAL);
                intermediateEvent
                        .setTriggerIntermediateMultiple((TriggerIntermediateMultiple) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                intermediateEvent.setTrigger(TriggerType.SIGNAL_LITERAL);
                intermediateEvent
                        .setTriggerResultSignal((TriggerResultSignal) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                intermediateEvent.setTrigger(TriggerType.SIGNAL_LITERAL);
                intermediateEvent
                        .setTriggerResultSignal((TriggerResultSignal) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                /*
                 * Set default value to 'true' for implicit data association for
                 * new throw signal event
                 */
                disableImplicitAssociation(act);
                break;
            default:
                intermediateEvent.setTrigger(TriggerType.NONE_LITERAL);
                break;
            }

            act.setEvent(intermediateEvent);
            break;
        }
        case EventFlowType.FLOW_START:
        default: {
            StartEvent startEvent = xpdlFact.createStartEvent();

            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                /*
                 * Sid ACE-484 - Don't default implementation type to WebService
                 * any more.
                 */
                startEvent.setTrigger(TriggerType.MESSAGE_LITERAL);
                startEvent
                        .setTriggerResultMessage((TriggerResultMessage) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                startEvent
                        .setImplementation(
                                ImplementationType.UNSPECIFIED_LITERAL);
                break;
            case EventTriggerType.EVENT_TIMER:
                startEvent.setTrigger(TriggerType.TIMER_LITERAL);
                startEvent
                        .setTriggerTimer((TriggerTimer) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                act.getDeadline().add(createDeadline());
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                startEvent.setTrigger(TriggerType.CONDITIONAL_LITERAL);
                startEvent
                        .setTriggerConditional((TriggerConditional) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                startEvent.setTrigger(TriggerType.MULTIPLE_LITERAL);
                startEvent
                        .setTriggerMultiple((TriggerMultiple) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                startEvent.setTrigger(TriggerType.SIGNAL_LITERAL);
                startEvent
                        .setTriggerResultSignal((TriggerResultSignal) createEventDetail(flowType,
                                eventType,
                                parentProcess));
                break;
            default:
                startEvent.setTrigger(TriggerType.NONE_LITERAL);
                break;
            }

            act.setEvent(startEvent);
        }
        }
        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PRIVATE);

        // XPDL21 Cater to event Message Throw for activity resource patterns.
        if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(eventType)) {
            ActivityResourcePatterns patterns =
                    XpdExtensionFactory.eINSTANCE
                            .createActivityResourcePatterns();
            AllocationStrategy strategy =
                    XpdExtensionFactory.eINSTANCE.createAllocationStrategy();
            patterns.setAllocationStrategy(strategy);
            strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
            Xpdl2ModelUtil.setOtherElement(act, XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ActivityResourcePatterns(), patterns);
        }

        /*
         * Set name from default for trigger type.
         */
        String name = getDefaultNameForEventType(flowType, eventType);

        act.setName(NameUtil.getInternalName(name, false));
        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);

        return act;
    }

    /**
     * @param act
     */
    private static void disableImplicitAssociation(Activity act) {
        AssociatedParameters assocParams =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameters();

        assocParams.setDisableImplicitAssociation(true);
        Xpdl2ModelUtil.setOtherElement(act, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_AssociatedParameters(), assocParams);
    }

    /**
     * @param flowType
     * @param eventType
     * @return default label name for event type.
     */
    public static String getDefaultNameForEventType(EventFlowType flowType,
            EventTriggerType eventType) {
        String name = eventType.toString();

        /*
         * Sid: For a long time, the name when created from palette for
         * "Untriggered Event" has not matched the name in templates which are
         * the better "Start Event"
         * 
         * Changing to match - BUT just for object creation / trigger-type
         * changing (otherwise I would have changed the TriggerType.toString()
         * however that is used for lots of other things like validation
         * problems where the actual type is preferred).
         */
        if (EventTriggerType.EVENT_NONE_LITERAL.equals(eventType)) {
            if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                name = Messages.ElementsFactory_DefaultStartEventName_label;

            } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(flowType)) {
                name =
                        Messages.ElementsFactory_DefaultIntermediateEventName_label;

            } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                name = Messages.ElementsFactory_DefaultEndEventName_label;
            }
        }
        return name;
    }

    /**
     * Create an blank event trigger/result type element.
     * 
     * @param flowType
     * @param eventType
     * @param parentProcess
     *            Currently only used for creating signal/error events (for
     *            assigning default signal/error code names) - can be null
     *            otherwise.
     * @return (May be null)
     */
    public static EObject createEventDetail(EventFlowType flowType,
            EventTriggerType eventType, Process parentProcess) {
        EObject eventDetail = null;

        switch (flowType.getValue()) {
        case EventFlowType.FLOW_END: {
            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_THROW:
                TriggerResultMessage msgThrow = createTriggerResultMessage();
                msgThrow.setCatchThrow(CatchThrow.THROW);
                // By Default End Throw Message Events are reply to upstream
                // activity. IF there is only one then set our end event to
                // reply to that.
                String replyToActId = ""; //$NON-NLS-1$
                if (parentProcess != null) {
                    List<Activity> reqActs =
                            ReplyActivityUtil
                                    .getAllIncomingRequestActivities(parentProcess);
                    if (reqActs != null && reqActs.size() == 1) {
                        replyToActId = reqActs.get(0).getId();
                    }
                }

                Xpdl2ModelUtil.setOtherAttribute(msgThrow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyToActivityId(),
                        replyToActId);

                eventDetail = msgThrow;

                break;
            case EventTriggerType.EVENT_ERROR: {
                ResultError resultError = xpdlFact.createResultError();
                eventDetail = resultError;
                if (parentProcess != null) {
                    /*
                     * If the parent process for this throw error event has a
                     * single incoming request event and no sub-process start
                     * activities.
                     */
                    List<Activity> reqActs =
                            ReplyActivityUtil
                                    .getAllIncomingRequestActivities(parentProcess);
                    String errorCode = getUniqueDefaultErrorCode(parentProcess);

                    if (reqActs != null && reqActs.size() == 1) {
                        Activity requestActivity = reqActs.get(0);

                        Xpdl2ModelUtil.setOtherAttribute(resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RequestActivityId(),
                                requestActivity.getId());

                        Message faultMessage =
                                Xpdl2Factory.eINSTANCE.createMessage();
                        Xpdl2ModelUtil.addOtherElement(resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_FaultMessage(),
                                faultMessage);

                        /*
                         * For user defined or interface implementing request
                         * activity then the user will have to chose a fault,
                         * for process auto-generating request the user must
                         * enter a code so give a default one.
                         */
                        if (Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestActivity)
                                && !ProcessInterfaceUtil
                                        .isEventImplemented(requestActivity)) {
                            resultError.setErrorCode(errorCode);

                            faultMessage.setFaultName(ThrowErrorEventUtil
                                    .getFaultNameForErrorCode(errorCode));
                        }

                    } else {
                        resultError.setErrorCode(errorCode);
                    }
                }
            }
                break;
            case EventTriggerType.EVENT_CANCEL:
                // No detail node for cancel.
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                TriggerResultCompensation compensationCatch =
                        xpdlFact.createTriggerResultCompensation();
                Xpdl2ModelUtil.setOtherAttribute(compensationCatch,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.THROW);
                eventDetail = compensationCatch;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                ResultMultiple resultMultiple = xpdlFact.createResultMultiple();
                Xpdl2ModelUtil.setOtherAttribute(resultMultiple,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.THROW);
                eventDetail = resultMultiple;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                TriggerResultSignal resultSignal = createTriggerResultSignal();
                resultSignal.setCatchThrow(CatchThrow.THROW);

                if (parentProcess != null) {
                    resultSignal
                            .setName(getUniqueDefaultSignalName(parentProcess));
                }

                eventDetail = resultSignal;
                break;
            case EventTriggerType.EVENT_TERMINATE:
                // No detail node for terminate
                break;
            }

            break;
        }

        case EventFlowType.FLOW_INTERMEDIATE: {
            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                TriggerResultMessage msgCatch = createTriggerResultMessage();
                msgCatch.setCatchThrow(CatchThrow.CATCH);
                eventDetail = msgCatch;
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                TriggerResultMessage msgThrow = createTriggerResultMessage();
                msgThrow.setCatchThrow(CatchThrow.THROW);

                // By Default End Throw Message Events are reply to upstream
                // activity. IF there is only one then set our end event to
                // reply to that.
                String replyToActId = ""; //$NON-NLS-1$
                if (parentProcess != null) {
                    List<Activity> reqActs =
                            ReplyActivityUtil
                                    .getAllIncomingRequestActivities(parentProcess);
                    if (reqActs != null && reqActs.size() == 1) {
                        replyToActId = reqActs.get(0).getId();
                    }
                }

                Xpdl2ModelUtil.setOtherAttribute(msgThrow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyToActivityId(),
                        replyToActId);

                eventDetail = msgThrow;
                break;
            case EventTriggerType.EVENT_TIMER:
                TriggerTimer triggerTimer = xpdlFact.createTriggerTimer();
                Expression expr = xpdlFact.createExpression();
                expr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                ""); //$NON-NLS-1$
                triggerTimer.setTimeDate(expr);
                eventDetail = triggerTimer;
                break;
            case EventTriggerType.EVENT_ERROR:
                eventDetail = xpdlFact.createResultError();
                ((ResultError) eventDetail).setErrorCode(EMPTY_STRING);
                break;
            case EventTriggerType.EVENT_CANCEL:
                // No detail node for cancel
                break;
            case EventTriggerType.EVENT_COMPENSATION_CATCH:
                TriggerResultCompensation compensationCatch =
                        xpdlFact.createTriggerResultCompensation();
                Xpdl2ModelUtil.setOtherAttribute(compensationCatch,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.CATCH);
                eventDetail = compensationCatch;

                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                TriggerResultCompensation compensationThrow =
                        xpdlFact.createTriggerResultCompensation();
                Xpdl2ModelUtil.setOtherAttribute(compensationThrow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.THROW);
                eventDetail = compensationThrow;

                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                eventDetail = xpdlFact.createTriggerConditional();
                ((TriggerConditional) eventDetail).setConditionName(""); //$NON-NLS-1$
                break;
            case EventTriggerType.EVENT_LINK_CATCH:
                TriggerResultLink catchLink =
                        xpdlFact.createTriggerResultLink();
                catchLink.setName("0"); //$NON-NLS-1$
                //                catchLink.setProcessRef("-unknown-"); //$NON-NLS-1$
                catchLink.setCatchThrow(CatchThrow.CATCH);
                eventDetail = catchLink;
                break;
            case EventTriggerType.EVENT_LINK_THROW:
                TriggerResultLink throwLink =
                        xpdlFact.createTriggerResultLink();
                throwLink.setName("0"); //$NON-NLS-1$
                //                throwLink.setProcessRef("-unknown-"); //$NON-NLS-1$
                throwLink.setCatchThrow(CatchThrow.THROW);
                eventDetail = throwLink;
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                TriggerIntermediateMultiple multCatch =
                        xpdlFact.createTriggerIntermediateMultiple();
                Xpdl2ModelUtil.setOtherAttribute(multCatch,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.CATCH);
                eventDetail = multCatch;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                TriggerIntermediateMultiple multThrow =
                        xpdlFact.createTriggerIntermediateMultiple();
                Xpdl2ModelUtil.setOtherAttribute(multThrow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.THROW);
                eventDetail = multThrow;
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                TriggerResultSignal signalCatch = createTriggerResultSignal();
                signalCatch.setCatchThrow(CatchThrow.CATCH);
                eventDetail = signalCatch;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                TriggerResultSignal signalThrow = createTriggerResultSignal();
                signalThrow.setCatchThrow(CatchThrow.THROW);
                if (parentProcess != null) {
                    signalThrow
                            .setName(getUniqueDefaultSignalName(parentProcess));
                }
                eventDetail = signalThrow;
                break;
            }

            break;
        }

        case EventFlowType.FLOW_START:
        default: {

            switch (eventType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                TriggerResultMessage msgCatch = createTriggerResultMessage();
                msgCatch.setCatchThrow(CatchThrow.CATCH);
                eventDetail = msgCatch;
                break;
            case EventTriggerType.EVENT_TIMER:
                TriggerTimer triggerTimer = xpdlFact.createTriggerTimer();
                Expression expr = xpdlFact.createExpression();
                expr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                ""); //$NON-NLS-1$
                triggerTimer.setTimeDate(expr);
                eventDetail = triggerTimer;
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                eventDetail = xpdlFact.createTriggerConditional();
                ((TriggerConditional) eventDetail).setConditionName(""); //$NON-NLS-1$
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                TriggerMultiple multCatch = xpdlFact.createTriggerMultiple();
                Xpdl2ModelUtil.setOtherAttribute(multCatch,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchThrow(),
                        CatchThrow.CATCH);
                eventDetail = multCatch;
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                TriggerResultSignal signal = createTriggerResultSignal();
                signal.setCatchThrow(CatchThrow.CATCH);
                eventDetail = signal;
                break;
            }

        }
        }

        return eventDetail;
    }

    /**
     * @return
     */
    private static TriggerResultSignal createTriggerResultSignal() {
        TriggerResultSignal signal = xpdlFact.createTriggerResultSignal();
        return signal;
    }

    /**
     * Create a message (for a trigger or result) with mandatory child objects.
     * 
     * @return
     */
    private static TriggerResultMessage createTriggerResultMessage() {
        TriggerResultMessage trm;
        trm = xpdlFact.createTriggerResultMessage();
        trm.setMessage(xpdlFact.createMessage());

        // WebServiceOperation wso = createWebServiceOperation();
        // trm.setWebServiceOperation(wso);

        return trm;
    }

    /**
     * Create a Web Service Operation object with its default child objects.
     * 
     * @return
     */
    public static WebServiceOperation createWebServiceOperation() {
        // Default implementation for all messages is Web Service, so create the
        // objects now.
        WebServiceOperation wso = xpdlFact.createWebServiceOperation();
        Service ws = xpdlFact.createService();
        wso.setService(ws);
        ws.setEndPoint(xpdlFact.createEndPoint());
        return wso;
    }

    /**
     * Create artifact of given type
     * 
     * @param loc
     * @param size
     *            (may be null for default)
     * @param type
     * @param laneId
     *            (may be null for artifact that do not belongs to any lane)
     * @return
     */
    public static Artifact createArtifact(Point loc, Dimension size,
            ArtifactType type, String laneId, String fillColor, String lineColor) {
        Artifact art = Xpdl2Factory.eINSTANCE.createArtifact();
        art.setArtifactType(type);

        NodeGraphicsInfo gn = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(art);

        gn.setCoordinates(Xpdl2Factory.eINSTANCE
                .createCoordinates(loc.x, loc.y));

        if (size != null) {
            gn.setWidth(size.width);
            gn.setHeight(size.height);
        } else {
            gn.setWidth(4);
            gn.setHeight(10);
        }

        if (fillColor != null) {
            gn.setFillColor(fillColor);
        }

        if (lineColor != null) {
            gn.setBorderColor(lineColor);
        }

        gn.setLaneId(laneId);
        art.getNodeGraphicsInfos().add(gn);

        // set specyfic default text
        switch (type.getValue()) {
        case ArtifactType.ANNOTATION:
            art.setTextAnnotation(Messages.ElementsFactory_DiagramNoteDefaultValue_shortdesc);
            break;
        case ArtifactType.GROUP:
            art.setName(EMPTY_STRING);
            break;
        case ArtifactType.DATA_OBJECT:
            String name = Messages.ElementsFactory_DataObjectDefaultName_label;
            Xpdl2ModelUtil
                    .setOtherAttribute(art, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);
            art.setName(NameUtil.getInternalName(name, false));

            break;
        }
        return art;
    }

    /**
     * Create message flow with specyfic endpoints
     * 
     * @param sourceId
     * @param target
     * @param bendPoints
     * @return
     */
    public static MessageFlow createMessageFlow(UniqueIdElement sourceNode,
            UniqueIdElement targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {

        MessageFlow flow = xpdlFact.createMessageFlow();
        flow.setSource(sourceNode.getId());
        flow.setTarget(targetNode.getId());

        ConnectorGraphicsInfo gn =
                Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(flow);
        gn.setBorderColor(lineColor);

        // Always preset name so that connection label edit part gets created so
        // that direct edit policy works from the get-go
        flow.setName(EMPTY_STRING);

        createInitialAnchorPos(startAnchorPos, endAnchorPos, flow);
        createInitialBendpoints(bendPoints, flow);

        return flow;
    }

    /**
     * 
     * @param sourceNode
     * @param targetNode
     * @param flowType
     * @param condition
     * @param bendPoints
     * @param startAnchorPos
     * @param endAnchorPos
     * @return
     */
    public static Transition createTransition(UniqueIdElement sourceNode,
            UniqueIdElement targetNode, SequenceFlowType flowType,
            String condition, String grammarId, List bendPoints,
            Double startAnchorPos, Double endAnchorPos, String name,
            ConnectionLabelPosition labelPos, String lineColor) {

        Transition transition = Xpdl2Factory.eINSTANCE.createTransition();
        transition.setFrom(sourceNode.getId());
        transition.setTo(targetNode.getId());

        if (flowType != null) {
            switch (flowType.getValue()) {
            case SequenceFlowType.DEFAULT: {
                Condition c = Xpdl2Factory.eINSTANCE.createCondition();
                c.setType(ConditionType.OTHERWISE_LITERAL);

                transition.setCondition(c);
                break;
            }
            case SequenceFlowType.CONDITIONAL: {
                Condition c = Xpdl2Factory.eINSTANCE.createCondition();
                c.setType(ConditionType.CONDITION_LITERAL);

                Expression exp = Xpdl2Factory.eINSTANCE.createExpression();

                exp.setScriptGrammar(grammarId);
                // User must now explicitly set gramma for scripts
                if (condition != null && condition.length() > 0) {
                    exp.getMixed()
                            .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                    condition);
                }
                c.setExpression(exp);

                transition.setCondition(c);
                break;
            }
            }
        }

        ConnectorGraphicsInfo gn =
                Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(transition);
        gn.setBorderColor(lineColor);

        createInitialAnchorPos(startAnchorPos, endAnchorPos, transition);
        createInitialBendpoints(bendPoints, transition);

        if (name != null) {
            transition.setName(NameUtil.getInternalName(name, false));
            Xpdl2ModelUtil
                    .setOtherAttribute(transition,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            name);
        } else {
            // Always preset name so that connection label edit part gets
            // created so
            // that direct edit policy works from the get-go
            transition.setName(EMPTY_STRING);
        }

        if (labelPos != null) {
            ConnectorGraphicsInfo labelAnchorPosition =
                    Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(transition,
                            Xpdl2ModelUtil.LABEL_ANCHOR_IDSUFFIX);

            labelAnchorPosition.getCoordinates()
                    .add(Xpdl2Factory.eINSTANCE.createCoordinates(labelPos
                            .getPercentAnchorOnConnection(), 0.0));
            labelAnchorPosition.getCoordinates()
                    .add(Xpdl2Factory.eINSTANCE.createCoordinates(labelPos
                            .getXOffsetFromAnchor(), labelPos
                            .getYOffsetFromAnchor()));

        }

        return transition;
    }

    /**
     * @param source
     * @param target
     * @param bendPoints
     * @return
     */
    public static Association createAssociation(UniqueIdElement sourceNode,
            UniqueIdElement targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {

        Association association = Xpdl2Factory.eINSTANCE.createAssociation();
        association.setSource(sourceNode.getId());
        association.setTarget(targetNode.getId());
        association
                .setAssociationDirection(AssociationDirectionType.NONE_LITERAL);

        // Association have a required Object element.
        association.setObject(Xpdl2Factory.eINSTANCE.createObject());

        ConnectorGraphicsInfo gn =
                Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(association);
        gn.setBorderColor(lineColor);

        // Always preset name so that connection label edit part gets created so
        // that direct edit policy works from the get-go
        association.setName(EMPTY_STRING);

        createInitialAnchorPos(startAnchorPos, endAnchorPos, association);
        createInitialBendpoints(bendPoints, association);

        return association;
    }

    /**
     * @param startAnchorPos
     * @param endAnchorPos
     * @param tr
     */
    private static void createInitialAnchorPos(Double startAnchorPos,
            Double endAnchorPos, GraphicalConnector tr) {

        if (startAnchorPos != null) {
            // Create graphical connector info and add it into the
            // transition.
            ConnectorGraphicsInfo gConnector =
                    Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(tr,
                            Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

            // Percent portion along line anchor position is stored as X-coord.
            gConnector
                    .getCoordinates()
                    .add(Xpdl2Factory.eINSTANCE.createCoordinates(startAnchorPos
                            .doubleValue(),
                            0.0));
        }

        if (endAnchorPos != null) {
            // Create graphical connector info and add it into the
            // transition.
            ConnectorGraphicsInfo gConnector =
                    Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(tr,
                            Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);

            // Percent portion along line anchor position is stored as X-coord.
            gConnector.getCoordinates()
                    .add(Xpdl2Factory.eINSTANCE.createCoordinates(endAnchorPos
                            .doubleValue(), 0.0));
        }
    }

    /**
     * @param bendPoints
     * @param tr
     */
    private static void createInitialBendpoints(List bendPoints,
            GraphicalConnector tr) {
        // Add initial bend points to transition if necessary.
        if (bendPoints != null && !bendPoints.isEmpty()) {
            // Create graphical connector info and add it into the
            // transition.
            ConnectorGraphicsInfo gConnector =
                    Xpdl2ModelUtil.getOrCreateConnectorGraphicsInfo(tr);

            // Add the bendpoints to it
            for (Iterator bpiter = bendPoints.iterator(); bpiter.hasNext();) {
                XPDBendpointType bp = (XPDBendpointType) bpiter.next();

                gConnector
                        .getCoordinates()
                        .add(Xpdl2Factory.eINSTANCE.createCoordinates(bp.fromStart.width,
                                bp.fromStart.height));
                gConnector
                        .getCoordinates()
                        .add(Xpdl2Factory.eINSTANCE.createCoordinates(bp.fromEnd.width,
                                bp.fromEnd.height));
            }

        }
    }

    /**
     * @param loc
     * @param size
     * @param laneId
     * @return
     */
    private static Activity createActivity(Point loc, Dimension size,
            String laneId, String fillColor, String lineColor) {
        Activity act = xpdlFact.createActivity();

        NodeGraphicsInfo gn = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act);

        if (size != null) {
            gn.setWidth(size.width);
            gn.setHeight(size.height);
        } else {
            gn.setWidth(4); // Shouldn't now ever get passed null, but just in
            // case
            gn.setHeight(4); // we'll put in a silly value.
        }
        gn.setCoordinates(xpdlFact.createCoordinates(loc.x, loc.y));
        gn.setLaneId(laneId);
        gn.setFillColor(fillColor);
        gn.setBorderColor(lineColor);

        return act;
    }

    /**
     * Create a list of one deadline structure with populated duration, but null
     * values.
     * 
     * @return
     */
    public static Deadline createDeadline() {
        Deadline dl = xpdlFact.createDeadline();
        Expression expr = xpdlFact.createExpression();
        dl.setDeadlineDuration(expr);
        return dl;
    }

    public static Pool createPool(String name, String processId) {
        Pool pool = Xpdl2Factory.eINSTANCE.createPool();
        pool.setName(NameUtil.getInternalName(name, false));
        Xpdl2ModelUtil.setOtherAttribute(pool,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);
        pool.setProcessId(processId);
        pool.setBoundaryVisible(true); // Ensure XPDL is valid.

        return pool;
    }

    private static void setActivityVisibility(Activity act, TaskType tt) {
        switch (tt.getValue()) {
        case TaskType.NONE:
        case TaskType.USER:
        case TaskType.SUBPROCESS:
        case TaskType.SCRIPT:
        case TaskType.SEND:
        case TaskType.SERVICE:
        case TaskType.MANUAL:
        case TaskType.RECEIVE:
            Xpdl2ModelUtil.setOtherAttribute(act,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                    Visibility.PRIVATE);
            break;

        }
    }

    public static String getUniqueDefaultSignalName(Process process) {
        // Auto allocate a signal name to signal events
        Set<String> existingSignals = new HashSet<String>();

        Collection<Activity> acts =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : acts) {
            if (act.getEvent() != null
                    && act.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {
                String signalName =
                        ((TriggerResultSignal) act.getEvent()
                                .getEventTriggerTypeNode()).getName();
                if (signalName != null && signalName.length() > 0) {
                    existingSignals.add(signalName);
                }
            }
        }

        int signalIdx = 1;
        String signalName = ""; //$NON-NLS-1$
        while (true) {
            signalName =
                    Messages.Xpdl2EventAdapter_DefaultSignalNamePrefix_label
                            + signalIdx;
            if (!existingSignals.contains(signalName)) {
                break;
            }
            signalIdx++;
        }
        return signalName;
    }

    public static String getUniqueDefaultErrorCode(Process process) {
        // Auto allocate a signal name to signal events
        Set<String> existingSignals = new HashSet<String>();

        Collection<Activity> acts =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : acts) {
            if (act.getEvent() != null
                    && act.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                String errorCode =
                        ((ResultError) act.getEvent().getEventTriggerTypeNode())
                                .getErrorCode();
                if (errorCode != null && errorCode.length() > 0) {
                    existingSignals.add(errorCode);
                }
            }
        }

        int errorIdx = 1;
        String errorCode = ""; //$NON-NLS-1$
        while (true) {
            errorCode =
                    Messages.Xpdl2EventAdapter_DefaultErrorCodePrefix_label
                            + errorIdx;
            if (!existingSignals.contains(errorCode)) {
                break;
            }
            errorIdx++;
        }

        return errorCode;
    }
}
