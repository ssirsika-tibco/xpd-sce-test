package com.tibco.xpd.n2.process.globalsignal.dragdrop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.LocalDropObjectUtils;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Global Signal Drop contribution that allows Drag Drop of Global Signal on
 * Process Editor.This will allow global signals from project explorer to be
 * dragged and dropped onto...
 * <p>
 * <b>Process diagram background</b> - Will prompt the user with 3 options to:
 * <li>Create Global Signal Event Sub-Process</li>
 * <li>Create Global Signal Event Handler Flow</li>>
 * <li>Create Throw Global Signal Event</li>
 * <p>
 * <b>Sequence flow</b> - Will prompt the user with 2 options to:
 * <li>Create Throw Global Signal Event</li>
 * <li>Create Catch Global Signal Event</li>>
 * <p>
 * <b>Existing throw signal event</b> - Will set the throw signal event to Throw
 * Global Signal Event.
 * <p>
 * <b>Existing catch signal event</b> - Will set the catch signal event to Catch
 * Global Signal Event.
 * 
 * 
 * @author kthombar
 * @since Feb 18, 2015
 */
public class GlobalSignalDropContribution implements IDropObjectContribution {

    public GlobalSignalDropContribution() {
        /*
         * default constructor.
         */
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution#getDropTypeInfo(java.lang.Object,
     *      java.util.List, org.eclipse.draw2d.geometry.Point, java.lang.Object,
     *      int)
     * 
     * @param targetContainerObject
     * @param dropObjects
     * @param location
     * @param actualTargetObject
     * @param userRequestedDropType
     * @return drop type if its a valid drop else return Drop_None if the drop
     *         to target is not allowed.
     */
    @Override
    public DropTypeInfo getDropTypeInfo(Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);

        /*
         * Only a single global signal can be dropped to the process editor at a
         * time.
         */
        if (dropObjects != null && dropObjects.size() == 1
                && dropObjects.get(0) instanceof GlobalSignal) {
            /*
             * if the target is a lane or transition then the drop type is copy
             */
            if (actualTargetObject instanceof Lane
                    || actualTargetObject instanceof Transition) {
                dropType = new DropTypeInfo(DND.DROP_COPY);
                dropType.setFeedbackRectangles(DiagramDropObjectUtils
                        .getTaskFeedbackRectangles(1));
            }

            /*
             * If the target is a catch or throw signal event.
             */
            if (actualTargetObject instanceof Activity) {
                Activity activity = (Activity) actualTargetObject;
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);

                if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                        .equals(eventTriggerType)
                        || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(eventTriggerType)) {

                    dropType = new DropTypeInfo(DND.DROP_MOVE);
                    dropType.setFeedbackRectangles(DiagramDropObjectUtils
                            .getTaskFeedbackRectangles(0));

                } else if (activity.getBlockActivity() != null) {
                    dropType = new DropTypeInfo(DND.DROP_COPY);
                    dropType.setFeedbackRectangles(DiagramDropObjectUtils
                            .getTaskFeedbackRectangles(1));

                }
            }
        }
        return dropType;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution#getDropPopupItems(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object, java.util.List, org.eclipse.draw2d.geometry.Point,
     *      java.lang.Object, int)
     * 
     * @param editingDomain
     * @param targetObject
     * @param dropObjects
     * @param location
     * @param actualTargetObject
     * @param userRequestedDropType
     * @return The popup items to show the user so that he can choose the action
     *         to perform.
     */
    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, Object targetObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {
        /*
         * if the target is a lane or transition then the drop type is copy
         */
        if (targetObject instanceof EObject && dropObjects != null
                && dropObjects.size() == 1
                && dropObjects.get(0) instanceof GlobalSignal) {

            GlobalSignal droppedGlobalSignal =
                    (GlobalSignal) dropObjects.get(0);
            /*
             * add necessary project references if not already present
             */
            if (!ProcessUIUtil.checkAndAddProjectReference(Display.getDefault()
                    .getActiveShell(),
                    (EObject) targetObject,
                    droppedGlobalSignal)) {
                /*
                 * return immediately if the user does not choose to add project
                 * reference.
                 */
                return Collections.emptyList();
            }

            List<DropObjectPopupItem> popupItems =
                    new ArrayList<DropObjectPopupItem>();

            if (actualTargetObject instanceof Lane
                    || actualTargetObject instanceof Activity
                    || actualTargetObject instanceof Transition) {

                List<DropObjectPopupItem> items =
                        new ArrayList<DropObjectPopupItem>();

                if (actualTargetObject instanceof Activity) {

                    Activity activity = (Activity) actualTargetObject;
                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(activity);

                    if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                            .equals(eventTriggerType)
                            || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                    .equals(eventTriggerType)) {
                        /*
                         * If drop target is an existing throw or catch signal
                         * event then set the signal to global and set the
                         * signal name.
                         */
                        items.addAll(getSetSignalTypeToGlobalPopupItems(editingDomain,
                                (Activity) actualTargetObject,
                                droppedGlobalSignal));

                    } else if (activity.getBlockActivity() != null) {
                        /*
                         * Sid XPD-7630 - Allow drop onto Embedded Sub-process
                         * activity.
                         */
                        items.addAll(getCreateGlobalSignalEventsOnLanePopupItems(activity,
                                location,
                                droppedGlobalSignal));

                    }

                } else if (actualTargetObject instanceof Transition
                        /*
                         * Sid XPD-7630 - Drop onto flow in embedded sub-process
                         * did not work.
                         */
                        && (targetObject instanceof Lane || targetObject instanceof Activity)) {
                    /*
                     * If the drop target is Transition then popup the user with
                     * options to create a throw global signal or a catch global
                     * signal.
                     */
                    items.addAll(getCreateGlobalSignalEventsOnTransitionPopupItems((Transition) actualTargetObject,
                            location,
                            (GraphicalNode) targetObject,
                            droppedGlobalSignal));

                } else if (actualTargetObject instanceof Lane) {
                    /*
                     * If the drop target is lane then popup the user to create
                     * throw global signal, catch global signal event handler
                     * flow or start signal event sub process.
                     */
                    items.addAll(getCreateGlobalSignalEventsOnLanePopupItems((Lane) actualTargetObject,
                            location,
                            droppedGlobalSignal));

                }
                popupItems.addAll(items);
            }

            return popupItems;
        }
        return null;
    }

    /**
     * 
     * @param actualTargetObject
     *            the target Lane
     * @param location
     *            the point where the cursor currently is
     * @param droppedGlobalSignal
     *            the global signal being dropped.
     * @return creates and returns a list of popup items that allows the user to
     *         create Create Global Signal Event Sub-Process or Create Global
     *         Signal Event Handler Flow or Create Throw Global Signal Event
     */
    private List<DropObjectPopupItem> getCreateGlobalSignalEventsOnLanePopupItems(
            GraphicalNode actualTargetObject, Point location,
            GlobalSignal droppedGlobalSignal) {

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        NodeGraphicsInfo nodeInfo = null;

        Process targetProcess = Xpdl2ModelUtil.getProcess(actualTargetObject);

        if (actualTargetObject != null) {
            nodeInfo = Xpdl2ModelUtil.getNodeGraphicsInfo(actualTargetObject);
        }
        String laneId = nodeInfo != null ? nodeInfo.getLaneId() : null;

        /*
         * Sid XPD-7630 - Don't provide create event sub-process unless drop
         * target is a Lane.
         */
        if (actualTargetObject instanceof Lane) {
            /*
             * popup item to create global signal event sub-process
             */
            popupItems
                    .add(createGlobalSignalEventSubProcessFlowPopupItem(targetProcess,
                            location,
                            laneId,
                            droppedGlobalSignal,
                            Messages.GlobalSignalDropContribution_CreateGlobalSignalEventSubProcessPopup_label));
        }

        /*
         * popup item to create global signal event handler flow
         */
        popupItems
                .add(createGlobalSignalEventHandlerFlowPopupItem(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        Messages.GlobalSignalDropContribution_CreateGlobalSignalEventHandlerFlowPopup_label));

        /*
         * popup item to create global throw signal event.
         */
        popupItems
                .add(createThrowOrfCatchGlobalSignalEventsPopupItem(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        Messages.GlobalSignalDropContribution_CreateThrowGlobalSignalEventPopup_label,
                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL));

        /*
         * XPD-7630 - End Events can also throw signals
         */
        popupItems
                .add(createThrowOrfCatchGlobalSignalEventsPopupItem(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        Messages.GlobalSignalDropContribution_CreateEndThrowGlobalSignalEventPopup_label,
                        EventFlowType.FLOW_END_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL));

        return popupItems;
    }

    /**
     * 
     * @param targetProcess
     *            the target process
     * @param location
     *            the point where the cursor currently is
     * @param laneId
     *            the lane id
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @param label
     *            the popup label
     * @return Creates and returns pop-up item to create a Global Signal Event
     *         Sub-process flow.
     */
    private DropObjectPopupItem createGlobalSignalEventSubProcessFlowPopupItem(
            Process targetProcess, Point location, String laneId,
            GlobalSignal droppedGlobalSignal, String label) {

        List<Object> createObjs = new ArrayList<Object>();
        /*
         * create event sub process
         */
        Activity createEventSubProcess =
                DiagramDropObjectUtils.createTaskObject(targetProcess,
                        laneId,
                        location,
                        TaskType.EVENT_SUBPROCESS_LITERAL);
        LocalDropObjectUtils.setUniqueNameForActivity(createEventSubProcess,
                targetProcess);
        createObjs.add(createEventSubProcess);

        Point locationForActivitiesInEventSubProc =
                new Point(
                        ProcessWidgetConstants.EMBSUBFLOW_WIDTH_SIZE / 6,
                        (ProcessWidgetConstants.EMBSUBFLOW_HEIGHT_SIZE / 2) - 20);

        /*
         * Create Signal Flow activities which will go inside event sub process
         */
        List<Object> createCatchSignalFlowActivities =
                getCreateCatchSignalFlowActivities(targetProcess,
                        locationForActivitiesInEventSubProc,
                        laneId,
                        droppedGlobalSignal,
                        EventFlowType.FLOW_START_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);

        /*
         * create activity set
         */
        ActivitySet activitySet = Xpdl2Factory.eINSTANCE.createActivitySet();

        /*
         * Add the Start evtn flow activites and their transition to the
         * activity set
         */
        for (Object object : createCatchSignalFlowActivities) {

            if (object instanceof Activity) {
                activitySet.getActivities().add((Activity) object);
            } else if (object instanceof Transition) {
                activitySet.getTransitions().add((Transition) object);

            }
        }
        createObjs.add(activitySet);
        /*
         * set the event sub process process block activity id
         */
        createEventSubProcess.getBlockActivity()
                .setActivitySetId(activitySet.getId());

        return DropObjectPopupItem
                .createCreateDiagramObjectsItem(createObjs,
                        label,
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskType.EVENT_SUBPROCESS_LITERAL));
    }

    /**
     * 
     * @param actualTargetObject
     *            the target transition
     * @param location
     *            the location the cursor is at
     * @param targetContainerObject
     *            the container of the target
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @return creates List of popup items to create Throw global signal and
     *         Catch global signal and returns them.
     */
    private List<DropObjectPopupItem> getCreateGlobalSignalEventsOnTransitionPopupItems(
            Transition actualTargetObject, Point location,
            GraphicalNode targetContainerObject,
            GlobalSignal droppedGlobalSignal) {

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        NodeGraphicsInfo nodeInfo = null;

        Process targetProcess = Xpdl2ModelUtil.getProcess(actualTargetObject);

        if (targetContainerObject != null) {
            nodeInfo =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(targetContainerObject);
        }
        String laneId = nodeInfo != null ? nodeInfo.getLaneId() : null;

        /*
         * Create the popup item to create throw global signal
         */
        popupItems
                .add(createThrowOrfCatchGlobalSignalEventsPopupItem(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        Messages.GlobalSignalDropContribution_CreateThrowGlobalSignalEventPopup_label,
                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL));
        /*
         * Create the popup item to create catch global signal
         */
        popupItems
                .add(createThrowOrfCatchGlobalSignalEventsPopupItem(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        Messages.GlobalSignalDropContribution_CreateCatchGlobalSignalEventPopup_label,
                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL));

        return popupItems;
    }

    /**
     * 
     * @param targetProcess
     *            the target process
     * @param location
     *            the point where the cursor currently is
     * @param laneId
     *            the lane id
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @param label
     *            the label for the popup
     * @return Creates and returns a popupp item to create global signal event
     *         handler flow.
     */
    private DropObjectPopupItem createGlobalSignalEventHandlerFlowPopupItem(
            Process targetProcess, Point location, String laneId,
            GlobalSignal droppedGlobalSignal, String label) {

        /*
         * create Catch Signal Flow activities
         */
        List<Object> createCatchSignalFlowActivities =
                getCreateCatchSignalFlowActivities(targetProcess,
                        location,
                        laneId,
                        droppedGlobalSignal,
                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);

        return DropObjectPopupItem
                .createCreateDiagramObjectsItem(createCatchSignalFlowActivities,
                        label,
                        DiagramDropObjectUtils
                                .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL));

    }

    /**
     * 
     * @param targetProcess
     *            the target process
     * @param location
     *            the point the cursor currently is at
     * @param laneId
     *            the lane id
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @param flowLiteral
     *            the Flow listeral of the event to create
     * @param eventTriggerType
     *            the Trigger type of the event to create.
     * @return Creates and returns a List that conatains Catch Signal Event
     *         handler flow, the event handler type will be decided by the
     *         passed flow literal and event trigger type.
     */
    private List<Object> getCreateCatchSignalFlowActivities(
            Process targetProcess, Point location, String laneId,
            GlobalSignal droppedGlobalSignal, EventFlowType flowLiteral,
            EventTriggerType eventTriggerType) {

        List<Activity> allNewCreatedActivities = new ArrayList<Activity>();

        /*
         * create the catch global signal event
         */
        Activity catchGlobalSignalEvent =
                DiagramDropObjectUtils.createEventObject(targetProcess,
                        laneId,
                        location,
                        flowLiteral,
                        eventTriggerType);

        String label =
                Messages.GlobalSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                        + " " + droppedGlobalSignal.getName(); //$NON-NLS-1$

        Xpdl2ModelUtil.setOtherAttribute(catchGlobalSignalEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                label);

        catchGlobalSignalEvent.setName(NameUtil.getInternalName(label, false));

        LocalDropObjectUtils.setUniqueNameForActivity(catchGlobalSignalEvent,
                targetProcess);

        Event event = catchGlobalSignalEvent.getEvent();

        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;
                /*
                 * set the signal name
                 */
                signal.setName(GlobalSignalUtil
                        .getGlobalSignalQualifiedName(droppedGlobalSignal));
                /*
                 * set the signal type to global
                 */
                Xpdl2ModelUtil.setOtherAttribute(signal,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SignalType(),
                        SignalType.GLOBAL);
            }
        }

        allNewCreatedActivities.add(catchGlobalSignalEvent);

        Point p1 = new Point(location.x() + 95, location.y());
        /*
         * create task type none
         */
        Activity createNoneTaskTypeObject =
                DiagramDropObjectUtils.createTaskObject(targetProcess,
                        laneId,
                        p1,
                        TaskType.NONE_LITERAL);
        LocalDropObjectUtils.setUniqueNameForActivity(createNoneTaskTypeObject,
                targetProcess);
        allNewCreatedActivities.add(createNoneTaskTypeObject);

        /*
         * create transition from catch signal to task type none
         */

        Point p2 = new Point(p1.x() + 95, p1.y());

        /*
         * create end event
         */
        Activity createEndEvent =
                DiagramDropObjectUtils.createEventObject(targetProcess,
                        laneId,
                        p2,
                        EventFlowType.FLOW_END_LITERAL,
                        EventTriggerType.EVENT_NONE_LITERAL);
        LocalDropObjectUtils.setUniqueNameForActivity(createEndEvent,
                targetProcess);
        allNewCreatedActivities.add(createEndEvent);
        /*
         * create transition from task type none to end event.
         */

        List<Object> createObjs = new ArrayList<Object>();
        createObjs.addAll(allNewCreatedActivities);
        createObjs.addAll(DiagramDropObjectUtils
                .joinActivitiesWithTransitions(allNewCreatedActivities));

        return createObjs;
    }

    /**
     * 
     * @param targetProcess
     *            the target process
     * @param location
     *            the location of the cursor
     * @param laneId
     *            the lane id
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @param label
     *            the label for popup
     * @param eventFlowType
     *            the event flow type to create
     * @param eventTriggerType
     *            the event trigger type to create
     * @return Creates and returns the popup item to create the event type of
     *         the passed flow type and trigger type.
     */
    private DropObjectPopupItem createThrowOrfCatchGlobalSignalEventsPopupItem(
            Process targetProcess, Point location, String laneId,
            GlobalSignal droppedGlobalSignal, String label,
            EventFlowType eventFlowType, EventTriggerType eventTriggerType) {

        Activity createGlobalCatchSignalEvent =
                DiagramDropObjectUtils.createEventObject(targetProcess,
                        laneId,
                        location,
                        eventFlowType,
                        eventTriggerType);

        String displayName =
                Messages.GlobalSignalEventPrecommitListener_CatchGlobalSignalEvent_label
                        + " " //$NON-NLS-1$
                        + droppedGlobalSignal.getName();

        if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                .equals(eventTriggerType)) {
            displayName =
                    Messages.GlobalSignalEventPrecommitListener_ThrowGlobalSignalEvent_label
                            + " " + droppedGlobalSignal.getName(); //$NON-NLS-1$

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(createGlobalCatchSignalEvent,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedParameters());

            if (otherElement instanceof AssociatedParameters) {
                /*
                 * For Throw Global Signal Events By default the implicit data
                 * assocition should be available.
                 */
                AssociatedParameters associatedParameters =
                        (AssociatedParameters) otherElement;
                associatedParameters.setDisableImplicitAssociation(false);
            }
        }

        Xpdl2ModelUtil.setOtherAttribute(createGlobalCatchSignalEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                displayName);

        createGlobalCatchSignalEvent.setName(NameUtil
                .getInternalName(displayName, false));

        LocalDropObjectUtils
                .setUniqueNameForActivity(createGlobalCatchSignalEvent,
                        targetProcess);

        Event event = createGlobalCatchSignalEvent.getEvent();

        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                /*
                 * set the signal name
                 */
                signal.setName(GlobalSignalUtil
                        .getGlobalSignalQualifiedName(droppedGlobalSignal));

                /*
                 * set signal type to global
                 */
                Xpdl2ModelUtil.setOtherAttribute(signal,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SignalType(),
                        SignalType.GLOBAL);
            }
        }
        List<Object> createObjs = new ArrayList<Object>();
        createObjs.add(createGlobalCatchSignalEvent);

        return DropObjectPopupItem.createCreateDiagramObjectsItem(createObjs,
                label,
                DiagramDropObjectUtils.getImageForEventType(eventFlowType,
                        eventTriggerType));

    }

    /**
     * 
     * @param editingDomain
     * @param actualTargetObject
     *            the target activity
     * @param droppedGlobalSignal
     *            the global signal being dropped
     * @return creates a command to set the signal type to global and the signal
     *         name if the signal event , adds it t the popup item and returns
     *         it.
     */
    private List<DropObjectPopupItem> getSetSignalTypeToGlobalPopupItems(
            EditingDomain editingDomain, Activity actualTargetObject,
            GlobalSignal droppedGlobalSignal) {
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        CompoundCommand ccmd = new CompoundCommand();

        Event event = actualTargetObject.getEvent();
        if (event != null) {

            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                Object assoParam =
                        Xpdl2ModelUtil
                                .getOtherElement(actualTargetObject,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());

                if (assoParam instanceof AssociatedParameters) {
                    /*
                     * By default the implicit data assocition should be
                     * available.
                     */
                    AssociatedParameters associatedParameters =
                            (AssociatedParameters) assoParam;

                    if (associatedParameters.isDisableImplicitAssociation()) {

                        ccmd.append(SetCommand
                                .create(editingDomain,
                                        associatedParameters,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_DisableImplicitAssociation(),
                                        Boolean.FALSE));
                    }
                }

                if (!GlobalSignalUtil.isGlobalSignalEvent(actualTargetObject)) {
                    /*
                     * XPD-7549: Set the Signal Type to global only if it
                     * already wasn't global, this is because the precommit
                     * listener 'GlobalSignalEventPrecommitListener' clears the
                     * mappings and scripts(if any) when the signal type is
                     * changed , so we shouldn't set the signal type as that
                     * would clear these things. Also if a global signal is
                     * dropped to an existing global signal event then we would
                     * not want to clear the mappings and scripts.
                     */
                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;
                    /*
                     * Set the signal type to global
                     */
                    ccmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    signal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalType(),
                                    SignalType.GLOBAL));
                }

                /*
                 * set the signal name.
                 */
                ccmd.append(EventObjectUtil
                        .getSetSignalNameCommand(editingDomain,
                                actualTargetObject,
                                GlobalSignalUtil
                                        .getGlobalSignalQualifiedName(droppedGlobalSignal)));

                popupItems
                        .add(DropObjectPopupItem
                                .createCommandItem(ccmd,
                                        Messages.GlobalSignalDropContribution_AssignGlobalSignalToSignalEventPopup_label,
                                        Xpdl2ProcessEditorPlugin
                                                .getDefault()
                                                .getImageRegistry()
                                                .get(ProcessEditorConstants.IMG_DATA_INOUT)));
            }
        }
        return popupItems;
    }
}
