/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.diagramdropobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.PortType;
import javax.wsdl.extensions.soap.SOAPBinding;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.EventBWMessageAdapter;
import com.tibco.xpd.implementer.script.EventMessageAdapter;
import com.tibco.xpd.implementer.script.TaskReceiveMessageAdapter;
import com.tibco.xpd.implementer.script.TaskSendMessageAdapter;
import com.tibco.xpd.implementer.script.TaskServiceMessageAdapter;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem.DropPopupCustomCommand;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.ImageCache;
import com.tibco.xpd.wsdl.ui.WsdlBindingOperationWrapper;
import com.tibco.xpd.wsdl.ui.WsdlOperationWrapper;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contributer to drop objects on process diagram objects extension point.
 * <p>
 * Allows dropping of web service operation to set operation on existing task
 * (after converting to choice of service/send/receive), set operation on event
 * and create sequence of tasks/events in lane/embedded sub-process.
 * 
 * @author aallway
 * 
 */
public class WebServiceOperationDiagramDropContribution implements
        IDropObjectContribution {

    /**
     * 
     */
    private static final String TARGET_ADDRESS = "targetAddress"; //$NON-NLS-1$

    private static String THROW_INTERMEDIATE_LABEL = "Throw Intermediate"; //$NON-NLS-1$

    private static String CATCH_INTERMEDIATE_LABEL = "Catch Intermediate"; //$NON-NLS-1$

    @Override
    public DropTypeInfo getDropTypeInfo(Object targetObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        /*
         * Overall, if actual target is not lane, activity or transition then we
         * can't do anything.
         */
        if (actualTargetObject instanceof Lane
                || actualTargetObject instanceof Activity
                || actualTargetObject instanceof Transition) {

            /*
             * SDA-59: Don't allow any kind of drop onto decision flow process,
             * as most aren't supported. <p> If some become available then these
             * should be contributed via dropObjectContribution extension point.
             */
            Process targetProcess =
                    Xpdl2ModelUtil.getProcess((EObject) actualTargetObject);
            if (!DecisionFlowUtil.isDecisionFlow(targetProcess)) {

                /* We can handle drop of service operation onto task. */
                if (checkAllAreValidWebServiceOps(dropObjects,
                        (EObject) actualTargetObject)) {
                    if (targetObject instanceof Activity) {
                        Activity act = (Activity) targetObject;

                        /*
                         * We can handle drop of single service onto various
                         * activity types (to set operation on task/event).
                         */

                        if (isWebServiceCapableActivity(act)) {
                            if (dropObjects.size() == 1) {
                                Object bindingOrInterfaceOperation =
                                        getBindingOrInterfaceOperation(dropObjects
                                                .get(0));
                                /*
                                 * We allow either DROP_COPY (sets operation AND
                                 * sets task name) or DROP_MOVE.
                                 */
                                if (userRequestedDropType == DND.DROP_COPY) {
                                    return DropTypeInfo.DROP_COPY;
                                }
                                return DropTypeInfo.DROP_MOVE;
                            }
                        }
                        /*
                         * We can handle drop of one or more operations onto
                         * embedded sub-process (to create objects that call
                         * operations).
                         */

                        else if (act.getBlockActivity() != null) {
                            DropTypeInfo dropType =
                                    new DropTypeInfo(DND.DROP_COPY);

                            dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                    .getTaskFeedbackRectangles(dropObjects
                                            .size()));
                            return dropType;
                        }
                    }

                    /*
                     * We can handle drop of one or more operations onto lane
                     * (to create objects that call operations).
                     */
                    else if (targetObject instanceof Lane) {
                        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_COPY);

                        dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                .getTaskFeedbackRectangles(dropObjects.size()));
                        return dropType;
                    }
                }

            }
        }
        return null;
    }

    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, Object targetObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        /*
         * Ask user to confirm for 'need to create a project reference' if any
         * are dragged from different project.
         */
        if (targetObject instanceof EObject) {
            List<EObject> dropEObjs = new ArrayList<EObject>();
            for (Object o : dropObjects) {
                EObject eo = getDropWsdlObjectEObject(o);
                if (eo != null) {
                    dropEObjs.add(eo);
                }
            }

            if (!ProcessUIUtil.checkAndAddProjectReferences(Display
                    .getDefault().getActiveShell(),
                    (EObject) targetObject,
                    dropEObjs)) {
                return Collections.emptyList();
            }
        }

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        /*
         * Overall, if actual target is not lane, activity or transition then we
         * can't do anything.
         */
        if (actualTargetObject instanceof Lane
                || actualTargetObject instanceof Activity
                || actualTargetObject instanceof Transition) {

            /* We can handle drop of service operation... */
            if (checkAllAreValidWebServiceOps(dropObjects,
                    (EObject) actualTargetObject)) {

                if (targetObject instanceof Activity) {
                    Activity act = (Activity) targetObject;

                    /*
                     * We can handle drop of single service onto various
                     * activity types (set operation on task).
                     */
                    if (isWebServiceCapableActivity(act)) {
                        if (dropObjects.size() == 1) {
                            Object bindingOrInterfaceOperation =
                                    getBindingOrInterfaceOperation(dropObjects
                                            .get(0));

                            if (bindingOrInterfaceOperation != null) {
                                List<DropObjectPopupItem> items =
                                        createSetWebSvcOpPopupItems(editingDomain,
                                                act,
                                                bindingOrInterfaceOperation,
                                                userRequestedDropType);

                                if (items != null && items.size() > 0) {
                                    popupItems.addAll(items);
                                }
                            }
                        }
                    }
                    /*
                     * We can handle drop of one or more operations onto
                     * embedded sub-process (to create objects that call
                     * operations).
                     */
                    else if (act.getBlockActivity() != null) {
                        List<DropObjectPopupItem> items =
                                createAddWebSvcOpPopupItems(editingDomain,
                                        act,
                                        dropObjects,
                                        location,
                                        actualTargetObject);

                        if (items != null && items.size() > 0) {
                            popupItems.addAll(items);
                        }
                    }
                }
                /*
                 * We can handle drop of one or more operations onto lane (to
                 * create objects that call operations).
                 */
                else if (targetObject instanceof Lane) {
                    List<DropObjectPopupItem> items =
                            createAddWebSvcOpPopupItems(editingDomain,
                                    (Lane) targetObject,
                                    dropObjects,
                                    location,
                                    actualTargetObject);

                    if (items != null && items.size() > 0) {
                        popupItems.addAll(items);
                    }

                }
            }
        }

        return popupItems;
    }

    /**
     * Create drop-popup items for creating tasks/events from given web svc
     * operation(s).
     * <p>
     * Possibilities are...
     * 
     * <pre>
     * Multiple service operations...
     *     Create Activity Sequence For Operations...
     *         Create Service Task For Each Operation
     *         Create Send Task For Each Operation
     *         Create Receive Task For Each Operation
     *         Create Intermediate Event For Each Operation
     *         
     *     Create Unsequenced Activities For Operations...
     *         Create Service Task For Each Operation
     *         Create Send Task For Each Operation
     *         Create Receive Task For Each Operation
     *         Create Start Event For Each Operation
     *         Create Intermediate Event For Each Operation
     *         Create End Event For Each Operation
     * 
     * Single service operation...
     *     Create Service Task For Operation
     *     Create Send Task For Operation
     *     Create Receive Task For Operation
     *     Create Start Event For Operation
     *     Create Intermediate Event For Operation
     *     Create End Event For Operation
     * 
     * </pre>
     * 
     * @param editingDomain
     * @param actOrLane
     * @param dropObjects
     * @param location
     * @return
     */
    private List<DropObjectPopupItem> createAddWebSvcOpPopupItems(
            EditingDomain editingDomain, EObject actOrLane,
            List<Object> dropObjects, Point location, Object actualTargetObject) {

        List<DropObjectPopupItem> finalPopupItems =
                new ArrayList<DropObjectPopupItem>();

        if (dropObjects.size() > 1) {
            /* Multiple service operations */

            /*
             * Check if we have any BW wsdl operations, if so we can only create
             * service task sequences.
             */
            boolean haveBWServices = false;

            for (Object dropObj : dropObjects) {
                Object bindingOrInterfaceOperation =
                        getBindingOrInterfaceOperation(dropObj);
                if (isBWServiceOperation(bindingOrInterfaceOperation)) {
                    haveBWServices = true;
                    break;
                }
            }

            /* Create activity sequence for operations submenu... */
            List<DropObjectPopupItem> actSequenceItems =
                    new ArrayList<DropObjectPopupItem>();

            actSequenceItems.add(getSelectActSeqCustomCommand(editingDomain,
                    dropObjects,
                    actOrLane,
                    location,
                    TaskType.SERVICE_LITERAL));

            /*
             * Check if we have any BW wsdl operations, if so we can only create
             * service task sequences.
             */
            /*
             * From XPD-2633 we allow creation of send task/receive task for bw
             * services as we have destination specific validation rule if it is
             * not supported
             */
            // if (!haveBWServices) {
            actSequenceItems.add(getSelectActSeqCustomCommand(editingDomain,
                    dropObjects,
                    actOrLane,
                    location,
                    TaskType.SEND_LITERAL));

            actSequenceItems.add(getSelectActSeqCustomCommand(editingDomain,
                    dropObjects,
                    actOrLane,
                    location,
                    TaskType.RECEIVE_LITERAL));
            // }

            actSequenceItems.add(getSelectActSeqCustomCommand(editingDomain,
                    dropObjects,
                    actOrLane,
                    location,
                    THROW_INTERMEDIATE_LABEL));

            actSequenceItems.add(getSelectActSeqCustomCommand(editingDomain,
                    dropObjects,
                    actOrLane,
                    location,
                    CATCH_INTERMEDIATE_LABEL));

            finalPopupItems
                    .add(DropObjectPopupItem
                            .createSubMenu(actSequenceItems,
                                    Messages.WebServiceOperationDiagramDropContribution_CreateActSeqForOp_menu,
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.IMG_TASKSEQUENCE)));

            /* Can't insert unsequenced objects into transition. */
            if (!(actualTargetObject instanceof Transition)) {
                /* And a sub-menu for create unsequenced. */
                List<DropObjectPopupItem> actUnseqItems =
                        new ArrayList<DropObjectPopupItem>();

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        TaskType.SERVICE_LITERAL,
                        false));

                /*
                 * Check if we have any BW wsdl operations, if so we can only
                 * create service task sequences.
                 */
                /*
                 * From XPD-2633 we allow creation of send task/receive task for
                 * bw services as we have destination specific validation rule
                 * if it is not supported
                 */
                // if (!haveBWServices) {
                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        TaskType.SEND_LITERAL,
                        false));

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        TaskType.RECEIVE_LITERAL,
                        false));
                // }

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        EventFlowType.FLOW_START_LITERAL,
                        false));

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        THROW_INTERMEDIATE_LABEL,
                        false));

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        CATCH_INTERMEDIATE_LABEL,
                        false));

                actUnseqItems.add(getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        dropObjects,
                        EventFlowType.FLOW_END_LITERAL,
                        false));

                finalPopupItems
                        .add(DropObjectPopupItem
                                .createSubMenu(actUnseqItems,
                                        Messages.WebServiceOperationDiagramDropContribution_CreateUnSeqActForOp_menu,
                                        Xpdl2ProcessEditorPlugin
                                                .getDefault()
                                                .getImageRegistry()
                                                .get(ProcessEditorConstants.IMG_TASKUNSEQUENCED)));
            }

        } else {
            /* Single service operations */
            Object bindingOrInterfaceOperation =
                    getBindingOrInterfaceOperation(dropObjects.get(0));

            if (bindingOrInterfaceOperation != null) {

                finalPopupItems.add(getCreateSingleActPopupItem(editingDomain,
                        actOrLane,
                        location,
                        bindingOrInterfaceOperation,
                        TaskType.SERVICE_LITERAL));

                /*
                 * Check if this is a BW wsdl operation, if so we can only
                 * create service task.
                 */
                /*
                 * From XPD-2633 we allow creation of send task/receive task for
                 * bw services as we have destination specific validation rule
                 * if it is not supported
                 */
                // if (!isBWServiceOperation(bindingOrInterfaceOperation)) {
                finalPopupItems.add(getCreateSingleActPopupItem(editingDomain,
                        actOrLane,
                        location,
                        bindingOrInterfaceOperation,
                        TaskType.SEND_LITERAL));

                finalPopupItems.add(getCreateSingleActPopupItem(editingDomain,
                        actOrLane,
                        location,
                        bindingOrInterfaceOperation,
                        TaskType.RECEIVE_LITERAL));
                // }

                /* Can't insert start/end into transition. */
                if (!(actualTargetObject instanceof Transition)) {
                    finalPopupItems
                            .add(getCreateSingleActPopupItem(editingDomain,
                                    actOrLane,
                                    location,
                                    bindingOrInterfaceOperation,
                                    EventFlowType.FLOW_START_LITERAL));
                }

                finalPopupItems.add(getCreateSingleActPopupItem(editingDomain,
                        actOrLane,
                        location,
                        bindingOrInterfaceOperation,
                        THROW_INTERMEDIATE_LABEL));

                finalPopupItems.add(getCreateSingleActPopupItem(editingDomain,
                        actOrLane,
                        location,
                        bindingOrInterfaceOperation,
                        CATCH_INTERMEDIATE_LABEL));

                /* Can't insert start/end into transition. */
                if (!(actualTargetObject instanceof Transition)) {
                    finalPopupItems
                            .add(getCreateSingleActPopupItem(editingDomain,
                                    actOrLane,
                                    location,
                                    bindingOrInterfaceOperation,
                                    EventFlowType.FLOW_END_LITERAL));
                }
            }
        }

        return finalPopupItems;
    }

    /**
     * Get the drop popup custom command to display a select sequence order
     * dialog for the given object type.
     * 
     * @param editingDomain
     * @param dropObjects
     * @param actOrLane
     * @param location
     * @param createObjectType
     * @return
     */
    private DropObjectPopupItem getSelectActSeqCustomCommand(
            EditingDomain editingDomain, List<Object> dropObjects,
            EObject actOrLane, Point location, Object createObjectType) {
        CreateSvcOpActivitySequencePopupCommand cmd =
                new CreateSvcOpActivitySequencePopupCommand(actOrLane,
                        dropObjects, location, createObjectType, editingDomain);

        return DropObjectPopupItem.createCustomCommand(cmd,
                getCreateActSeqMenuLabel(createObjectType),
                getCreateActSeqMenuIcon(createObjectType));
    }

    /**
     * Create drop popup item for creation of task/event of given type
     * optionally sequenced or unsequenced.
     * 
     * @param editingDomain
     * @param actOrLane
     * @param location
     * @param dropObjects
     * @param createObjType
     * @param sequenced
     * @return
     */
    private DropObjectPopupItem getCreateActSeqPopupItem(
            EditingDomain editingDomain, EObject actOrLane, Point location,
            List<Object> dropObjects, Object createObjType, boolean sequenced) {

        /* Create the new activities according to activity type. */
        List<Activity> createActs = new ArrayList<Activity>();

        Point actLoc = location.getCopy();

        List<Object> finalCreateObjects = new ArrayList<Object>();
        for (Object dropObj : dropObjects) {
            Object bindingOrInterfaceOperation =
                    getBindingOrInterfaceOperation(dropObj);

            if (bindingOrInterfaceOperation != null) {
                createNewWebSvcOpActivity(actLoc,
                        createObjType,
                        actOrLane,
                        bindingOrInterfaceOperation,
                        finalCreateObjects);

                actLoc.x +=
                        (int) (ProcessWidgetConstants.TASK_WIDTH_SIZE * 1.5f);
            }
        }

        /* If a sequence is required add the transition objects to join them. */
        for (Object next : finalCreateObjects) {
            if (next instanceof Activity) {
                createActs.add((Activity) next);
            }
        }

        if (sequenced) {
            List<Transition> transitions =
                    DiagramDropObjectUtils
                            .joinActivitiesWithTransitions(createActs);

            finalCreateObjects.addAll(transitions);
        }

        String label = getCreateActSeqMenuLabel(createObjType);
        Image img = getCreateActSeqMenuIcon(createObjType);

        return DropObjectPopupItem
                .createCreateDiagramObjectsItem(finalCreateObjects, label, img);
    }

    /**
     * Get the menu icon for create activity sequence popup items.
     * 
     * @param createObjType
     * @return
     */
    private Image getCreateActSeqMenuIcon(Object createObjType) {
        Image img = null;
        if (createObjType instanceof TaskType) {
            img =
                    DiagramDropObjectUtils
                            .getImageForTaskType((TaskType) createObjType);

        } else if (createObjType instanceof EventFlowType) {
            EventFlowType flowType = (EventFlowType) createObjType;
            /* XPDL21 Handle message event throw types. */
            img =
                    DiagramDropObjectUtils.getImageForEventType(flowType,
                            EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
        } else if (THROW_INTERMEDIATE_LABEL.equals(createObjType)) {
            /* XPDL21 Handle message event throw */
            img =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                    EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

        } else if (CATCH_INTERMEDIATE_LABEL.equals(createObjType)) {
            /* XPDL21 Handle message event throw */
            img =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

        }
        return img;
    }

    /**
     * Get the menu label for create activity sequence popup items.
     * 
     * @param createObjType
     * @return
     */
    private String getCreateActSeqMenuLabel(Object createObjType) {
        String label = ""; //$NON-NLS-1$

        if (createObjType instanceof TaskType) {
            if (TaskType.RECEIVE_LITERAL.equals(createObjType)) {
                label =
                        String.format(Messages.WebServiceOperationDiagramDropContribution_CreateReceiveTaskForEachOp_menu,
                                ((TaskType) createObjType).toString());
            } else {
                label =
                        String.format(Messages.WebServiceOperationDiagramDropContribution_CreateTaskForEachOp_menu,
                                ((TaskType) createObjType).toString());
            }

        } else if (createObjType instanceof EventFlowType) {
            EventFlowType flowType = (EventFlowType) createObjType;

            if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateStartEventForEachOp_menu;
            } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateEndEventForEachOp_menu;
            } else {
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateIntermediateEventForEachOp_menu;
            }
        } else if (THROW_INTERMEDIATE_LABEL.equals(createObjType)) {
            label =
                    Messages.WebServiceOperationDiagramDropContribution_CreateThrowIntermediateEventSeqForOp_menu;

        } else if (CATCH_INTERMEDIATE_LABEL.equals(createObjType)) {
            label =
                    Messages.WebServiceOperationDiagramDropContribution_CreateCatchIntermediateEventSeqForOp_menu;
        }

        return label;
    }

    /**
     * Create a new diagram object (task service/send/receive or event
     * start/intermediate/end) in the given parent object.
     * 
     * @param editingDomain
     * 
     * @param location
     * 
     * @param laneId
     * @param bindingOperation
     * @param createObjType
     * @return
     */
    private DropObjectPopupItem getCreateSingleActPopupItem(
            EditingDomain editingDomain, EObject actOrLane, Point location,
            Object bindingOrInterfaceOperation, Object createObjType) {

        String label = ""; //$NON-NLS-1$
        Image img = null;

        List<Object> objectsToCreate = new ArrayList<Object>();

        /* Handle Create task to call operation drop popup item. */
        if (createObjType instanceof TaskType) {
            /* Create the activity according to task type. */
            TaskType taskType = (TaskType) createObjType;

            if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                label =
                        String.format(Messages.WebServiceOperationDiagramDropContribution_CreateSingleReceiveTaskForOp_menu,
                                taskType.toString());
            } else {
                label =
                        String.format(Messages.WebServiceOperationDiagramDropContribution_CreateSingleTaskForOp_menu,
                                taskType.toString());
            }
            img = DiagramDropObjectUtils.getImageForTaskType(taskType);
        }
        /* Handle Create task to call operation drop popup item. */
        else if (createObjType instanceof EventFlowType) {
            /* Create the message event according to flow type. */
            EventFlowType flowType = (EventFlowType) createObjType;

            if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateStartEventForOp_menu;
            } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateEndEventForOp_menu;
            } else {
                /*
                 * SHould now be redundant as we use special
                 * THROW/CATCH_INTERMEDIATE_LABEL for intermediate events.
                 */
                label =
                        Messages.WebServiceOperationDiagramDropContribution_CreateIntermediateEventForOp_menu;
            }

            /* XPDL21 Handle message event throw */
            img =
                    DiagramDropObjectUtils.getImageForEventType(flowType,
                            EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

        } else if (THROW_INTERMEDIATE_LABEL.equals(createObjType)) {
            label =
                    Messages.WebServiceOperationDiagramDropContribution_CreateThrowIntermediateEventForOp_menu;
            /* XPDL21 Handle message event throw */
            img =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                    EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

        } else if (CATCH_INTERMEDIATE_LABEL.equals(createObjType)) {
            label =
                    Messages.WebServiceOperationDiagramDropContribution_CreateCatchIntermediateEventForOp_menu;
            /* XPDL21 Handle message event throw */
            img =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

        }

        createNewWebSvcOpActivity(location,
                createObjType,
                actOrLane,
                bindingOrInterfaceOperation,
                objectsToCreate);

        return DropObjectPopupItem
                .createCreateDiagramObjectsItem(objectsToCreate, label, img);
    }

    /**
     * Create a new task/event activity to call given operation.
     * 
     * @param editingDomain
     * @param location
     * @param createObjType
     * @param actOrLane
     * @param bindingOperation
     * @return
     */
    private void createNewWebSvcOpActivity(Point location,
            Object createObjType, EObject actOrLane,
            Object bindingOrInterfaceOperation, List<Object> objectsToCreate) {
        Activity act = null;

        String laneId = null;
        Process process = null;
        if (actOrLane instanceof Lane) {
            Lane lane = (Lane) actOrLane;
            laneId = lane.getId();
            Pool pool = lane.getParentPool();
            String processId = pool.getProcessId();
            Package pckg = pool.getParentPackage();
            process = pckg.getProcess(processId);
        } else if (actOrLane instanceof Activity) {
            Activity activity = (Activity) actOrLane;
            process = activity.getProcess();
        }

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(actOrLane);

        WsdlServiceKey wsdlServiceKey =
                getWsdlServiceKey(bindingOrInterfaceOperation);
        String wsdlEndPointURL =
                getWsdlUrl(wc.getEclipseResources().get(0).getProject(),
                        wsdlServiceKey);

        boolean isRemote = false;
        if (TaskObjectUtil.isRemoteURL(wsdlEndPointURL)) {
            isRemote = true;
        }
        /* Handle Create task to call operation drop popup item. */
        if (createObjType instanceof TaskType) {
            /* Create the activity according to task type. */
            TaskType taskType = (TaskType) createObjType;

            act =
                    DiagramDropObjectUtils.createTaskObject(process,
                            laneId,
                            location,
                            taskType);

            /*
             * Set name according to task type (and get the adapter for setting
             * service operation).
             */
            AbstractActivityMessageAdapter adapter = null;

            if (TaskType.SEND_LITERAL.equals(taskType)) {
                adapter = new TaskSendMessageAdapter();
            } else if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                adapter = new TaskReceiveMessageAdapter();
            } else {
                /*
                 * If its an operation from a BW style wsdl then use an adapter
                 * that creates BW Service implementation.
                 */
                /*
                 * XPD-1778: If the user drags & drops a WSDL operation from
                 * project explorer on the service task then Studio should not
                 * try to guess the implementation type to BW or WebService. It
                 * should always treat it as WebService.
                 */
                adapter = new TaskServiceMessageAdapter();
            }

            /* Get the command that will set the service operation. */
            adapter.assignWebService(objectsToCreate,
                    process,
                    act,
                    wsdlEndPointURL,
                    isRemote,
                    wsdlServiceKey);

        }
        /*
         * Handle Create task to call operation drop popup item. For START/END
         * event we will be passed EventFlowType as createObjType, for
         * intermediate we will be passed THROW/CATCH_INTERMEDIATE_LABEL
         */
        else if (createObjType instanceof EventFlowType
                || THROW_INTERMEDIATE_LABEL.equals(createObjType)
                || CATCH_INTERMEDIATE_LABEL.equals(createObjType)) {

            EventFlowType flowType;

            if (createObjType instanceof EventFlowType) {
                flowType = (EventFlowType) createObjType;
            } else {
                flowType = EventFlowType.FLOW_INTERMEDIATE_LITERAL;
            }

            if (CATCH_INTERMEDIATE_LABEL.equals(createObjType)
                    || EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                act =
                        DiagramDropObjectUtils.createEventObject(process,
                                laneId,
                                location,
                                flowType,
                                EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
            } else {
                /* THROW intermediate or END event. */
                act =
                        DiagramDropObjectUtils.createEventObject(process,
                                laneId,
                                location,
                                flowType,
                                EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
            }

            EventMessageAdapter adapter = null;
            if (isBWServiceOperation(bindingOrInterfaceOperation)) {
                adapter = new EventBWMessageAdapter();
            } else {
                adapter = new EventMessageAdapter();
            }

            adapter.assignWebService(objectsToCreate,
                    process,
                    act,
                    wsdlEndPointURL,
                    isRemote,
                    wsdlServiceKey);

        }

        String name =
                getCallOpActivityName(wsdlServiceKey.getOperation() != null ? wsdlServiceKey.getOperation()
                        : wsdlServiceKey.getPortTypeOperation(),
                        createObjType);
        act.setName(NameUtil.getInternalName(name, false));
        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);

        objectsToCreate.add(act);

        return;

    }

    private String getCallOpActivityName(String operationName,
            Object createObjectType) {
        if (operationName != null) {
            return operationName;
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Create drop popup items appropriate for drop of wsdl operation onto
     * existing task.
     * <p>
     * 
     * <pre>
     * Possibilities are...
     *     - If activity is already service/send/receive then just set the task.
     *     - Else give options to...
     *         - Set as Service task and set to given operation
     *         - Set as Send task and set to given operation
     *         - Set as Receive task and set to given operation
     * </pre>
     * 
     * @param editingDomain
     * @param act
     * @param bindingOperation
     * @param userRequestedDropType
     * @param taskType
     *            Must be one of TaskService/TaskReceive/TaskSend.
     * 
     *            &#064;return
     * 
     */
    private List<DropObjectPopupItem> createSetWebSvcOpPopupItems(
            EditingDomain editingDomain, Activity act,
            Object bindingOrInterfaceOperation, int userRequestedDropType) {

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        WsdlServiceKey wsdlServiceKey =
                getWsdlServiceKey(bindingOrInterfaceOperation);
        if (wsdlServiceKey != null) {

            /* If user Ctrl-Drops then we will set task name to operation name. */
            boolean setTaskNameToOperation =
                    (userRequestedDropType == DND.DROP_COPY);

            TaskType curTaskType = TaskObjectUtil.getTaskType(act);
            String objTypeString = curTaskType.toString();

            boolean isBWSvcOperation =
                    isBWServiceOperation(bindingOrInterfaceOperation);

            ActivityMessageProvider adapter =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(act, isBWSvcOperation);
            if (adapter != null) {
                /*
                 * If the activity is already appropriate type for web service
                 * operation then we just need a popup to set the operation.
                 */
                CompoundCommand cmd =
                        createSetWebServiceCommand(editingDomain,
                                act,
                                wsdlServiceKey,
                                adapter,
                                setTaskNameToOperation);

                popupItems
                        .add(DropObjectPopupItem
                                .createCommandItem(cmd,
                                        Messages.WebServiceOperationDiagramDropContribution_SetOperation_label,
                                        WsdlUIPlugin
                                                .getDefault()
                                                .getImageCache()
                                                .getImage(ImageCache.OPERATION_BINDING)));
            }
            /*
             * If activity is not already correct type for hosting service
             * operation then the user has more options (such as select actual
             * type of task)
             */
            else if (isWebServiceCapableActivity(act)) {
                /*
                 * XPD-2902: Do not want to drop into the handling for
                 * task-activities in the else just because this is a
                 * bw-(soap-over-jms)-operation. Because....
                 * 
                 * a) If it is an Event we are dropping onto we don't handle
                 * convert to event to task service in the else clause.
                 * 
                 * b) We no longer make a special case of bw wsdls (see XPD-2633
                 * comment in else clause).
                 * 
                 * Therefore removed the isBWOperation from the condition below.
                 */
                if (act.getEvent() != null) {
                    /*
                     * Convert event to messaqe type and set the message on it.
                     */
                    EventMessageAdapter evMA = new EventMessageAdapter();

                    CompoundCommand cmd =
                            createSetWebServiceCommand(editingDomain,
                                    act,
                                    wsdlServiceKey,
                                    evMA,
                                    setTaskNameToOperation);

                    popupItems
                            .add(DropObjectPopupItem.createCommandItem(cmd,
                                    String.format(Messages.WebServiceOperationDiagramDropContribution_ConvertToMessageEvent_menu,
                                            objTypeString),
                                    WsdlUIPlugin
                                            .getDefault()
                                            .getImageCache()
                                            .getImage(ImageCache.OPERATION_BINDING)));

                } else {
                    /*
                     * Must be one of the other task types, give option to set
                     * to Service/Send/Receive.
                     */

                    // SERVICE
                    ActivityMessageProvider taskSvcAdapter;
                    /*
                     * XPD-1778: If the user drags & drops a WSDL operation from
                     * project explorer on the service task then Studio should
                     * not try to guess the implementation type to BW or
                     * WebService. It should always treat it as WebService.
                     */
                    taskSvcAdapter = new TaskServiceMessageAdapter();

                    CompoundCommand cmd =
                            createSetWebServiceCommand(editingDomain,
                                    act,
                                    wsdlServiceKey,
                                    taskSvcAdapter,
                                    setTaskNameToOperation);

                    popupItems
                            .add(DropObjectPopupItem.createCommandItem(cmd,
                                    String.format(Messages.WebServiceOperationDiagramDropContribution_ConvertToServiceTask_menu,
                                            objTypeString),
                                    DiagramDropObjectUtils
                                            .getImageForTaskType(TaskType.SERVICE_LITERAL)));

                    /* BW Service can only be set on service task. */
                    /*
                     * From XPD-2633 we allow creation of send task/receive task
                     * for bw services as we have destination specific
                     * validation rule if it is not supported
                     */
                    // if (!isBWSvcOperation) {
                    // SEND
                    cmd =
                            createSetWebServiceCommand(editingDomain,
                                    act,
                                    wsdlServiceKey,
                                    new TaskSendMessageAdapter(),
                                    setTaskNameToOperation);

                    popupItems
                            .add(DropObjectPopupItem.createCommandItem(cmd,
                                    String.format(Messages.WebServiceOperationDiagramDropContribution_ConvertToSendTask_menu,
                                            objTypeString),
                                    DiagramDropObjectUtils
                                            .getImageForTaskType(TaskType.SEND_LITERAL)));

                    // RECEIVE
                    cmd =
                            createSetWebServiceCommand(editingDomain,
                                    act,
                                    wsdlServiceKey,
                                    new TaskReceiveMessageAdapter(),
                                    setTaskNameToOperation);

                    popupItems
                            .add(DropObjectPopupItem.createCommandItem(cmd,
                                    String.format(Messages.WebServiceOperationDiagramDropContribution_ConvertToReceiveTask_menu,
                                            objTypeString),
                                    DiagramDropObjectUtils
                                            .getImageForTaskType(TaskType.RECEIVE_LITERAL)));
                    // }
                }
            }

        }

        return popupItems;
    }

    /**
     * Create command for setting web service on given activity (via given
     * ActivityMessageAdapter.
     * 
     * @param editingDomain
     * @param act
     * @param wsdlServiceKey
     * @param adapter
     * @param setTaskNameToOperation
     * @return
     */
    private CompoundCommand createSetWebServiceCommand(
            EditingDomain editingDomain, Activity act,
            WsdlServiceKey wsdlServiceKey, ActivityMessageProvider adapter,
            boolean setTaskNameToOperation) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(act);

        String url =
                getWsdlUrl(wc.getEclipseResources().get(0).getProject(),
                        wsdlServiceKey);
        boolean isRemote = false;

        // WebServiceOperation webServiceOperation =
        // adapter.getWebServiceOperation(act);
        //
        // if (null != webServiceOperation) {
        // String aliasId =
        // (String) Xpdl2ModelUtil
        // .getOtherAttribute(webServiceOperation,
        // XpdExtensionPackage.eINSTANCE
        // .getDocumentRoot_Alias());
        // /*
        // * Ensure that when end point participant is set, the IsRemote flag
        // * is also set.
        // */
        // if (null != aliasId) {
        // isRemote = true;
        // }
        // }

        if (TaskObjectUtil.isRemoteURL(url)) {
            isRemote = true;
        }

        CompoundCommand cmd = new CompoundCommand();

        IProject project = WorkingCopyUtil.getProjectFor(act);
        updateWsdlServiceKeyWithTransportName(project, wsdlServiceKey);

        cmd.append(adapter.getAssignWebServiceCommand(editingDomain,
                act.getProcess(),
                act,
                url,
                isRemote,
                wsdlServiceKey));

        if (setTaskNameToOperation) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            act,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            wsdlServiceKey.getOperation()));
        }

        return cmd;
    }

    /**
     * @param project
     * @param key
     * @param isBindingNotAvailable
     */
    private void updateWsdlServiceKeyWithTransportName(IProject project,
            WsdlServiceKey key) {

        if (null != project && null != key) {
            String transportUri =
                    WsdlIndexerUtil.getTransportUri(project, key, true, true);

            if (transportUri == null) {
                /* Set default values */
                // IndexerItem item =
                // WsdlIndexerUtil.getOperationItem(project,
                // key,
                // true,
                // true);
                // if (item != null) {
                // if (WsdlIndexerUtil.isBW(item)) {
                // transportUri = Xpdl2WsdlUtil.XML_OVER_JMS_URL;
                // } else if (WsdlIndexerUtil.isSoapJms(item)) {
                // transportUri = Xpdl2WsdlUtil.SOAP_OVER_JMS_URL;
                // } else if (WsdlIndexerUtil.isSoapHttp(item)) {
                // transportUri = Xpdl2WsdlUtil.SOAP_OVER_HTTP_URL;
                // } else {
                // transportUri = Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
                // }
                // }

                /*
                 * XPD-614: null transportUri means its a abstract wsdl.
                 * defaulting abstract wsdls to service virtualisaton
                 */
                transportUri = Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
            }
            key.setTransportURI(transportUri);
        }
    }

    /**
     * @param wsdlServiceKey
     * @param project
     * @return
     */
    private String getWsdlUrl(IProject project, WsdlServiceKey key) {
        // return WsdlIndexerUtil.getWsdlUrl(project, key, true, true);
        IPath relativePath =
                WsdlIndexerUtil.getRelativePath(project, key, true, true);
        if (relativePath != null) {
            return relativePath.toPortableString();
        }
        return null;
    }

    /**
     * Is the activity capable of hosting a web service operation?
     * 
     * @param act
     */
    private boolean isWebServiceCapableActivity(Activity act) {
        if (act.getEvent() != null) {
            /*
             * XPD-3533 -It is possible to drag and drop wsdl operations onto a
             * process interface message event.
             */
            if (!Xpdl2ModelUtil.isEventImplemented(act)) {
                return true;
            }
        } else if (act.getRoute() == null && act.getBlockActivity() == null) {
            TaskType taskType = TaskObjectUtil.getTaskType(act);

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (!(TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType) || TaskType.EVENT_SUBPROCESS_LITERAL
                    .equals(taskType))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the WSDL service key for the given operation.
     * 
     * @param operation
     * @return
     */
    private WsdlServiceKey getWsdlServiceKey(Object bindingOrInterfaceOperation) {
        if (bindingOrInterfaceOperation instanceof BindingOperation) {
            BindingOperation bindingOperation =
                    (BindingOperation) bindingOrInterfaceOperation;
            if (bindingOperation.eContainer() instanceof Binding) {

                /*
                 * First find the Port that this operation's binding is bound
                 * to.
                 * 
                 * The binding name cross-ref's one of /service/port@name
                 */
                Binding binding = (Binding) bindingOperation.eContainer();

                Definition wsdlDef = binding.getEnclosingDefinition();

                Service service = null;
                Port port = null;

                for (Iterator iterator = wsdlDef.getEServices().iterator(); iterator
                        .hasNext();) {
                    Service svc = (Service) iterator.next();

                    for (Iterator iterator2 = svc.getEPorts().iterator(); iterator2
                            .hasNext();) {
                        Port p = (Port) iterator2.next();

                        if (p.getBinding().getQName()
                                .equals(binding.getQName())) {
                            service = svc;
                            port = p;
                            break;
                        }
                    }
                }
                if (service != null && port != null) {
                    /* get extra port interface details */
                    PortType portType = binding.getPortType();
                    Operation portOperation = bindingOperation.getEOperation();
                    String transportURI = ""; //$NON-NLS-1$
                    /*
                     * Get services special folder relative path to the wsdl
                     * file
                     */
                    IPath path =
                            SpecialFolderUtil
                                    .getSpecialFolderRelativePath(service,
                                            Activator.WSDL_SPECIALFOLDER_KIND);

                    /* get transport uri */
                    Iterator<?> it =
                            binding.getExtensibilityElements().iterator();
                    while (it.hasNext()) {
                        Object obj = it.next();
                        if (obj instanceof SOAPBinding) {
                            SOAPBinding soapBinding = (SOAPBinding) obj;
                            transportURI = soapBinding.getTransportURI();
                            break;
                        }
                    }

                    WsdlServiceKey wsdlServiceKey =
                            new WsdlServiceKey(service.getQName()
                                    .getLocalPart(), port.getName(),
                                    bindingOperation.getEOperation().getName(),
                                    portType.getQName().getLocalPart(),
                                    portOperation.getName(),
                                    path != null ? path.toString() : null);
                    wsdlServiceKey.setTransportURI(transportURI);
                    return wsdlServiceKey;
                }
            }
        } else if (bindingOrInterfaceOperation instanceof Operation) {
            Operation operation = (Operation) bindingOrInterfaceOperation;
            /*
             * Get services special folder relative path to the wsdl file
             */
            IPath path =
                    SpecialFolderUtil.getSpecialFolderRelativePath(operation,
                            Activator.WSDL_SPECIALFOLDER_KIND);
            WsdlServiceKey wsdlServiceKey =
                    new WsdlServiceKey(((PortType) operation.eContainer())
                            .getQName().getLocalPart(), operation.getName(),
                            path != null ? path.toString() : null);

            return wsdlServiceKey;
        }
        return null;

    }

    private boolean isBWServiceOperation(Object tempObject) {
        boolean isBWSvc = false;
        Definition wsdlDef = null;

        if (tempObject instanceof BindingOperation) {
            BindingOperation bindingOperation = (BindingOperation) tempObject;
            if (bindingOperation.eContainer() instanceof Binding) {

                /*
                 * First find the Port that this operation's binding is bound
                 * to.
                 * 
                 * The binding name cross-ref's one of /service/port@name
                 */
                Binding binding = (Binding) bindingOperation.eContainer();

                wsdlDef = binding.getEnclosingDefinition();
            }
        }

        if (tempObject instanceof Operation) {
            Operation operation = (Operation) tempObject;
            wsdlDef = operation.getEnclosingDefinition();
        }

        if (wsdlDef != null) {
            Collection services = wsdlDef.getServices().values();
            if (services.size() > 0) {
                javax.wsdl.Service service =
                        (javax.wsdl.Service) services.iterator().next();
                Collection<?> ports = service.getPorts().values();
                if (ports.size() > 0) {
                    org.eclipse.wst.wsdl.Port port =
                            (org.eclipse.wst.wsdl.Port) ports.iterator().next();

                    List<ExtensibilityElement> elems =
                            port.getEExtensibilityElements();
                    for (ExtensibilityElement elem : elems) {

                        if (elem instanceof EObject) {
                            EObject eo = elem;

                            Object eoValue =
                                    eo.eGet(WSDLPackage.eINSTANCE
                                            .getWSDLElement_Element());

                            if (eoValue instanceof Element) {
                                Element domElement = (Element) eoValue;
                                if (TARGET_ADDRESS.equals(domElement
                                        .getLocalName())) {
                                    if (domElement.getFirstChild() instanceof Text) {
                                        Text textContent =
                                                (Text) domElement
                                                        .getFirstChild();

                                        if (textContent.getData().length() > 0) {
                                            isBWSvc = true;
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
        return isBWSvc;
    }

    /**
     * Get the actual org.eclipse.wst.wsdl.Operation object from the given
     * object.
     * 
     * @param object
     * 
     * @return
     */
    private Operation getOperation(Object object) {
        if (object instanceof Operation) {
            return (Operation) object;
        } else if (object instanceof WsdlBindingOperationWrapper) {
            Object obj = ((WsdlBindingOperationWrapper) object).getObject();
            if (obj instanceof BindingOperation) {
                return ((BindingOperation) obj).getEOperation();
            }
        }
        return null;
    }

    private Object getBindingOrInterfaceOperation(Object object) {

        if (object instanceof BindingOperation) {
            return object;
        } else if (object instanceof WsdlBindingOperationWrapper) {
            Object obj = ((WsdlBindingOperationWrapper) object).getObject();
            if (obj instanceof BindingOperation) {
                return obj;
            }
        } else if (object instanceof Operation) {
            return object;
        } else if (object instanceof WsdlOperationWrapper) {
            Object obj = ((WsdlOperationWrapper) object).getObject();
            if (obj instanceof Operation) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Check that the given list contains web service operations
     * 
     * @param dropObjects
     * @return
     */
    private boolean checkAllAreValidWebServiceOps(List<Object> dropObjects,
            EObject targetObject) {

        if (dropObjects != null && dropObjects.size() > 0) {
            for (Object obj : dropObjects) {
                if ((!(obj instanceof WsdlBindingOperationWrapper))
                        && (!(obj instanceof WsdlOperationWrapper))) {
                    return false;
                }
                /*
                 * SID MR 40774 - don't restrict to "our project only" any more.
                 * The execute will create project references if needed.
                 */
            }
            return true;
        }
        return false;
    }

    private EObject getDropWsdlObjectEObject(Object dropObject) {
        if (dropObject instanceof WsdlBindingOperationWrapper) {
            if ((((WsdlBindingOperationWrapper) dropObject).getObject() instanceof EObject)) {
                return (EObject) ((WsdlBindingOperationWrapper) dropObject)
                        .getObject();
            }
        } else if (dropObject instanceof WsdlOperationWrapper) {
            if ((((WsdlOperationWrapper) dropObject).getObject() instanceof EObject)) {
                return (EObject) ((WsdlOperationWrapper) dropObject)
                        .getObject();
            }
        }
        return null;
    }

    /**
     * Simple DropPopupCommand that produces a dialog allowing user selection of
     * the order of a task/event sequence.
     * 
     * @author aallway
     * 
     */
    private class CreateSvcOpActivitySequencePopupCommand implements
            DropPopupCustomCommand, ILabelProvider {

        private EObject actOrLane;

        private List<Object> dropObjects;

        private Point location;

        private Object createObjectType;

        private EditingDomain editingDomain;

        public CreateSvcOpActivitySequencePopupCommand(EObject actOrLane,
                List<Object> dropObjects, Point location,
                Object createObjectType, EditingDomain editingDomain) {
            super();
            this.actOrLane = actOrLane;
            this.dropObjects = dropObjects;
            this.location = location;
            this.createObjectType = createObjectType;
            this.editingDomain = editingDomain;
        }

        @Override
        public void addListener(ILabelProviderListener listener) {
        }

        @Override
        public void dispose() {
        }

        @Override
        public DropObjectPopupItem execute(Control hostControl) {
            /* Run the select order dialog. */
            List<Object> orderedObjects =
                    DiagramDropObjectUtils.selectObjectOrder(hostControl
                            .getShell(), dropObjects, this, getTitle());

            if (orderedObjects != null && orderedObjects.size() > 0) {
                return getCreateActSeqPopupItem(editingDomain,
                        actOrLane,
                        location,
                        orderedObjects,
                        createObjectType,
                        true);
            }
            return null;
        }

        private String getTitle() {
            String title = ""; //$NON-NLS-1$

            if (createObjectType instanceof TaskType) {
                title =
                        String.format(Messages.WebServiceOperationDiagramDropContribution_SelectTaskSeqOrder_title,
                                ((TaskType) createObjectType).toString());
            } else if (createObjectType instanceof EventFlowType) {
                EventFlowType flowType = (EventFlowType) createObjectType;

                if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                    title =
                            Messages.WebServiceOperationDiagramDropContribution_SelectStartEventSeqOrder_title;
                } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                    title =
                            Messages.WebServiceOperationDiagramDropContribution_SelectEndEventSeqOrder_title;
                } else {
                    title =
                            Messages.WebServiceOperationDiagramDropContribution_SelectInermediateEventSeqOrder_title;
                }
            }
            return title;
        }

        @Override
        public Image getImage(Object element) {
            if (createObjectType instanceof EventFlowType) {
                /* XPDL21 Handle event message throw types. */
                return DiagramDropObjectUtils
                        .getImageForEventType((EventFlowType) createObjectType,
                                EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
            } else if (createObjectType instanceof TaskType) {
                return DiagramDropObjectUtils
                        .getImageForTaskType((TaskType) createObjectType);
            }

            return null;
        }

        @Override
        public String getText(Object element) {
            Object bindingOrInterfaceOperation =
                    getBindingOrInterfaceOperation(element);
            if (bindingOrInterfaceOperation != null) {
                WsdlServiceKey wsdlServiceKey =
                        getWsdlServiceKey(bindingOrInterfaceOperation);
                if (wsdlServiceKey != null) {
                    return getCallOpActivityName(wsdlServiceKey.getOperation() != null ? wsdlServiceKey
                            .getOperation() : wsdlServiceKey
                            .getPortTypeOperation(),
                            createObjectType);
                }
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {
        }

    }

}
