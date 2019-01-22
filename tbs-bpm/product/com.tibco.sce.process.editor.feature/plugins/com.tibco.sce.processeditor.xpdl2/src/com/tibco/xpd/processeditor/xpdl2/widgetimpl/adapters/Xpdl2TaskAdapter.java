/*
 * * * MODULE: $RCSfile: $ * $Revision: $ * $Date: $ * * ENVIRONMENT: Java -
 * Platform independent * * COPYRIGHT: (c) 2005 TIBCO Software Inc., All Rights
 * Reserved. * * MODIFICATION HISTORY: * * $Log: $ *
 *
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorIndiSubInlineCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectsCommand;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.util.ImagePicker;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.OverlayImageDescriptor;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.NotificationsConstants;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * Adapter for xpdl activity that are tasks/subprocs, it acts as activity on
 * bpmn diagram
 * 
 * @author wzurek
 */
public class Xpdl2TaskAdapter extends Xpdl2BaseFlowObjectAdapter
        implements TaskAdapter {

    private static final String EMPTY_STRING = "";//$NON-NLS-1$

    private OclBasedNotifier implListener;

    private OclBasedNotifier blockActListener;

    private OclBasedNotifier activitySetListener;

    private OclBasedNotifier activityLoopListener;

    private OclBasedNotifier activityMultiLoopListener;

    private OclBasedNotifier activityIconListener;

    private EList list;

    /*
     * For decorated sub-process images.
     */
    private Map<Image, Image> syncSubProcImageReg = new HashMap<Image, Image>();

    private Map<Image, Image> asyncAttachSubProcImageReg =
            new HashMap<Image, Image>();

    private Map<Image, Image> asyncDetachSubProcImageReg =
            new HashMap<Image, Image>();

    @Override
    public EObject[] getActivityPerformers() {
        return TaskObjectUtil.getActivityPerformers(getActivity());
    }

    @Override
    public Command getAddGraphicalNodeCommand(EditingDomain editingDomain,
            Object graphicalNode, Point location) {

        if (!(graphicalNode instanceof Activity)
                && !(graphicalNode instanceof Artifact)) {
            // not possible to add non-activity based element
            return UnexecutableCommand.INSTANCE;
        }

        //
        // Create the command to add object to activity set that we reference
        CompoundCommand cmd = new CompoundCommand();

        ActivitySet activitySet =
                getOrCreateActivitySet(editingDomain, cmd, getActivity());

        GraphicalNode gNode = (GraphicalNode) graphicalNode;
        NodeGraphicsInfo gn = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(gNode, editingDomain, cmd);
        Xpdl2BaseGraphicalNodeAdapter actAd =
                (Xpdl2BaseGraphicalNodeAdapter) getAdapterFactory().adapt(gNode,
                        ProcessWidgetConstants.ADAPTER_TYPE);
        cmd.append(actAd.getSetLocationCommand(editingDomain,
                location,
                actAd.getSize()));

        // If this is an activity then add it to the activity set
        // And unset the lane id
        if (graphicalNode instanceof Activity) {
            Activity act = (Activity) graphicalNode;

            cmd.append(SetCommand.create(editingDomain,
                    gn,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                    null));

            // NOTE: Although AddCommand is clever enough to remove the activity
            // from
            // the original (process or activity set) list of activities,
            // when it's added to our activity set IT'S UNDO IS NOT SMART
            // ENOUGH - so we do an explicit delete and re-add.
            cmd.append(RemoveCommand.create(editingDomain, graphicalNode));

            cmd.append(AddCommand.create(editingDomain,
                    activitySet,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    graphicalNode));

            // Any transitions have to be moved into activity set too.
            // (Don't need to do incoming transitions, it's been prevalidated
            // that all transitions are between objects being moved so one
            // objects
            // incoming will be another object's outgoing (don't want to do
            // twice).
            List trans = act.getOutgoingTransitions();

            for (Iterator t = trans.iterator(); t.hasNext();) {
                Transition tran = (Transition) t.next();
                cmd.append(RemoveCommand.create(editingDomain, tran));

                cmd.append(AddCommand.create(editingDomain,
                        activitySet,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Transitions(),
                        tran));
            }

        } else {
            // It's an artifact (DataObject / Note) -
            // just set it's lane id to activity set if.
            // xpdl2 Artifacts are always in Package/Artifacts
            cmd.append(SetCommand.create(editingDomain,
                    gn,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                    activitySet.getId()));

        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#getAttachedEvents()
     */
    @Override
    public Collection getAttachedEvents() {
        Collection<Activity> attachedEvents =
                TaskObjectUtil.getAttachedEvents(getActivity());
        if (attachedEvents == null || attachedEvents.size() == 0) {
            return null;
        }
        return attachedEvents;
    }

    /**
     * Get all nodes inside embedded sub-proc
     */
    @Override
    public List getChildGraphicalNodes() {
        ArrayList nodes = new ArrayList();

        BlockActivity ba = getActivity().getBlockActivity();
        if (ba != null && getActivity().getProcess() != null) {
            //
            // Add the activities from referenced activity set.
            String activitySetId = ba.getActivitySetId();
            ActivitySet aSet =
                    getActivity().getProcess().getActivitySet(activitySetId);
            if (aSet != null) {
                nodes.addAll(aSet.getActivities());
            }

            //
            // Add all the package artifacts (data objects, groups and notes)
            // whose lane id = ActivitySet Id
            List pkgArtifacts =
                    getActivity().getProcess().getPackage().getArtifacts();
            for (Iterator pA = pkgArtifacts.iterator(); pA.hasNext();) {
                Artifact art = (Artifact) pA.next();

                NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                if (gn != null) {
                    if (activitySetId.equals(gn.getLaneId())) {
                        nodes.add(art);
                    }
                }
            }
        }
        return nodes;
    }

    /**
     * Create Command to Create new DataObject / Group / Note
     */
    @Override
    public CreateAccessibleObjectCommand getCreateArtifactCommand(
            EditingDomain editingDomain, Object type, Point loc, Dimension size,
            String fillColor, String lineColor) {

        // Validate artifact type.
        ArtifactType artType = null;

        CompoundCommand cmd = new CompoundCommand();

        if (type == DataObjectAdapter.class) {
            artType = ArtifactType.DATA_OBJECT_LITERAL;
            cmd.setLabel(Messages.Xpdl2TaskAdapter_AddDataObjectTask_menu);

        } else if (type == NoteAdapter.class) {
            artType = ArtifactType.ANNOTATION_LITERAL;
            cmd.setLabel(Messages.Xpdl2TaskAdapter_AddTextAnnotTask_menu);

        } /*
           * Groups always handledd by Process currently... else if (type ==
           * GroupAdapter.class) { artType = ArtifactType.GROUP_LITERAL; }
           * cmd.setLabel("Add Group");
           */
        else {
            return new CreateAccessibleObjectCommand(
                    UnexecutableCommand.INSTANCE, null);
        }

        // First off, create activity set for activity if it's not there yet.
        ActivitySet actSet =
                getOrCreateActivitySet(editingDomain, cmd, getActivity());

        // The create the artifact
        Artifact artifact = ElementsFactory.createArtifact(loc,
                size,
                artType,
                actSet.getId(),
                fillColor,
                lineColor);

        // and add it to package artifacts.
        cmd.append(AddCommand.create(editingDomain,
                getActivity().getProcess().getPackage(),
                Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                artifact));

        return new CreateAccessibleObjectCommand(cmd, artifact);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateEventCommand(
            EditingDomain editingDomain, EventFlowType flowType,
            EventTriggerType eventType, Point location, Dimension size,
            String fillColor, String lineColor) {

        Activity event = ElementsFactory.createEvent(location,
                size,
                null,
                flowType,
                eventType,
                fillColor,
                lineColor,
                getEProcess());

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_AddEventTask_menu);
        return createSubActivity(editingDomain, cmd, getActivity(), event);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.TaskAdapter#
     * getCreateEventOnBorderCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.processwidget.adapters.EventTriggerType, java.lang.Double,
     * org.eclipse.draw2d.geometry.Dimension, java.lang.String,
     * java.lang.String)
     */
    @Override
    public CreateAccessibleObjectCommand getCreateEventOnBorderCommand(
            EditingDomain ed, EventTriggerType intermediateEventType,
            Double positionOnBorder, Dimension size, String fillColor,
            String lineColor) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_AddEventTaskBorderTask_menu);
        // Get the parent lane / or activity set to add new event to.
        Activity newEvent = null;

        EObject container = Xpdl2ModelUtil.getContainer(getActivity());
        if (container instanceof ActivitySet) {
            newEvent = ElementsFactory.createEvent(new Point(0, 0),
                    size,
                    null,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                    intermediateEventType,
                    fillColor,
                    lineColor,
                    getEProcess());

        } else if (container instanceof Lane) {
            newEvent = ElementsFactory.createEvent(new Point(0, 0),
                    size,
                    ((Lane) container).getId(),
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                    intermediateEventType,
                    fillColor,
                    lineColor,
                    getEProcess());

        }

        IntermediateEvent iev = (IntermediateEvent) newEvent.getEvent();
        iev.setTarget(getActivity().getId());

        // Add the position on border node graphics info.
        NodeGraphicsInfo gi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(newEvent,
                        Xpdl2ModelUtil.BORDER_EVENTPOS_IDSUFFIX);

        // Percent portion position along task border is stored as X-coord.
        Coordinates coords = Xpdl2Factory.eINSTANCE
                .createCoordinates(positionOnBorder.doubleValue(), 0.0);

        gi.setCoordinates(coords);

        if (container instanceof ActivitySet) {
            cmd.append(AddCommand.create(ed,
                    container,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    newEvent));

        } else if (container instanceof Lane) {
            cmd.append(AddCommand.create(ed,
                    getEProcess(),
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    newEvent));
        }

        return new CreateAccessibleObjectCommand(cmd, newEvent);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateGatewayCommand(
            EditingDomain domain, GatewayType gatewayType, Point loc,
            Dimension size, String fillColor, String lineColor) {

        Activity gw = ElementsFactory.createGateway(loc,
                size,
                null,
                gatewayType,
                fillColor,
                lineColor);

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_AddGatewayTask_menu);
        return createSubActivity(domain, cmd, getActivity(), gw);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateTaskCommand(
            EditingDomain domain, TaskType taskType, Point loc, Dimension size,
            String fillColor, String lineColor) {

        return getCreateTaskInEmbeddedSubProcCommand(domain,
                getActivity(),
                taskType,
                loc,
                size,
                fillColor,
                lineColor);
    }

    /**
     * @param domain
     * @param taskType
     * @param loc
     * @param size
     * @param fillColor
     * @param lineColor
     * 
     * @return Command to create task of given type and add it to the embedded
     *         sub-process for this task.
     */
    public static CreateAccessibleObjectCommand getCreateTaskInEmbeddedSubProcCommand(
            EditingDomain domain, Activity activity, TaskType taskType,
            Point loc, Dimension size, String fillColor, String lineColor) {

        Process process = activity.getProcess();

        Activity tsk = ElementsFactory
                .createTask(loc, size, null, taskType, fillColor, lineColor);

        CompoundCommand cmd = new CompoundCommand();

        /*
         * ABPM-911: Saket: An event subprocess should mostly behave like an
         * embedded sub-process.
         */
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {
            // For embedded sub-process create activity set up-front.
            // Stops inconsistency when dealing with moving/creating
            // sub-objects.
            BlockActivity blockAct = tsk.getBlockActivity();

            ActivitySet activitySet =
                    Xpdl2Factory.eINSTANCE.createActivitySet();

            blockAct.setActivitySetId(activitySet.getId());

            cmd.append(AddCommand.create(domain,
                    process,
                    Xpdl2Package.eINSTANCE.getProcess_ActivitySets(),
                    activitySet));

        } else if (TaskType.SEND_LITERAL.equals(taskType)) {
            /*
             * XPD-7721: When we drag drop send task to the embedded-sub proc by
             * default its implementation should be 'Unspecified' because now
             * send tasks can be configured as REST or Web-service and hence
             * setting it to unspecified makes more sense.
             */
            Implementation implementation = tsk.getImplementation();

            if (implementation instanceof Task) {
                TaskSend taskSend = ((Task) implementation).getTaskSend();

                cmd.append(SetCommand.create(domain,
                        taskSend,
                        Xpdl2Package.eINSTANCE.getTaskSend_Implementation(),
                        ImplementationType.UNSPECIFIED_LITERAL));

                cmd.append(SetCommand.create(domain,
                        taskSend,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        null));
            }

        }

        cmd.setLabel(Messages.Xpdl2TaskAdapter_AddObjectTask_menu
                + taskType.toString());

        return createSubActivity(domain, cmd, activity, tsk);
    }

    /**
     * Override default getDeleteCommand() If this is an embedded sub-proc then
     * we should also delete any nodes that are embedded in it. And also delete
     * the activity set itself.
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand cmd = new CompoundCommand();

        String typeStr = getTaskType().toString();

        cmd.setLabel(Messages.Xpdl2TaskAdapter_DeleteTask_menu + typeStr);

        ActivitySet actSet = getActivitySet();

        appendDeleteDependenciesCommand(editingDomain, cmd);

        // Delete the activity first, then the activity set
        // if this is embedded aub-proc.
        // Then on undo, the activity set will be re-created before
        // the activity that references it.
        cmd.append(super.getDeleteCommand(editingDomain));

        // Delete the activity set.
        if (actSet != null) {
            List containedObjects = getChildGraphicalNodes();

            for (Iterator iter = containedObjects.iterator(); iter.hasNext();) {
                EObject obj = (EObject) iter.next();

                Xpdl2BaseGraphicalNodeAdapter objAd =
                        (Xpdl2BaseGraphicalNodeAdapter) getAdapterFactory()
                                .adapt(obj,
                                        ProcessWidgetConstants.ADAPTER_TYPE);

                cmd.append(objAd.getDeleteCommand(editingDomain));
            }

            cmd.append(RemoveCommand.create(editingDomain, actSet));

        }

        return cmd;
    }

    /**
     * @param editingDomain
     * @param cmd
     */
    private void appendDeleteDependenciesCommand(EditingDomain editingDomain,
            CompoundCommand cmd) {
        Activity activity = getActivity();
        if (activity != null) {
            String id = activity.getId();
            Process process = activity.getProcess();
            if (id != null && process != null) {
                Object other = Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
                if (other instanceof ProcessResourcePatterns) {
                    ProcessResourcePatterns patterns =
                            (ProcessResourcePatterns) other;
                    for (Object next : patterns
                            .getSeparationOfDutiesActivities()) {
                        if (next instanceof SeparationOfDutiesActivities) {
                            SeparationOfDutiesActivities separation =
                                    (SeparationOfDutiesActivities) next;
                            for (Object nextRef : separation.getActivityRef()) {
                                if (nextRef instanceof ActivityRef) {
                                    ActivityRef ref = (ActivityRef) nextRef;
                                    if (id.equals(ref.getIdRef())) {
                                        /**
                                         * since we consider separation of
                                         * duties group as top level element, we
                                         * don't delete the group, even if, the
                                         * only activity ref inside it is
                                         * deleted
                                         */
                                        // if
                                        // (separation.getActivityRef().size()
                                        // == 1) {
                                        // cmd
                                        // .append(RemoveCommand
                                        // .create(editingDomain,
                                        // patterns,
                                        // XpdExtensionPackage.eINSTANCE
                                        // .
                                        // getProcessResourcePatterns_SeparationOfDutiesActivities
                                        // (),
                                        // separation));
                                        // } else {
                                        cmd.append(RemoveCommand.create(
                                                editingDomain,
                                                separation,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getSeparationOfDutiesActivities_ActivityRef(),
                                                ref));
                                        // }
                                    }
                                }
                            }
                        }
                    }
                    // MR 38147 - begin
                    for (Object next : patterns.getRetainFamiliarActivities()) {
                        if (next instanceof RetainFamiliarActivities) {
                            RetainFamiliarActivities retainFamiliarActivities =
                                    (RetainFamiliarActivities) next;
                            for (Object nextRef : retainFamiliarActivities
                                    .getActivityRef()) {
                                if (nextRef instanceof ActivityRef) {
                                    ActivityRef activityRef =
                                            (ActivityRef) nextRef;
                                    if (id.equals(activityRef.getIdRef())) {
                                        cmd.append(RemoveCommand.create(
                                                editingDomain,
                                                retainFamiliarActivities,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getRetainFamiliarActivities_ActivityRef(),
                                                activityRef));
                                    }
                                }
                            }
                        }
                    }
                    // MR 38147 - end
                }
            }
        }
    }

    @Override
    public Set getMarkers() {
        return TaskObjectUtil.getMarkers(getActivity());
    }

    @Override
    public Object getPerformerDescription(EObject performer) {
        return TaskObjectUtil.getPerformerDescriptionStatic(performer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#getReferencedTask()
     */
    @Override
    public String getReferencedTask() {
        return ReferenceTaskUtil.getReferencedTaskId(getActivity());
    }

    @Override
    public TaskType getReferencedTaskType() {
        Activity refAct = ReferenceTaskUtil.getReferencedTask(getActivity());
        if (refAct != null) {
            return TaskObjectUtil.getTaskType(refAct);
        }
        return null;
    }

    @Override
    public Command getSetReferencedTaskCommand(EditingDomain ed,
            String refTaskId) {
        return ReferenceTaskUtil
                .getSetReferencedLocalTaskCommand(ed, getActivity(), refTaskId);
    }

    @Override
    public Collection<EObject> getReferencingTasks() {
        List<EObject> referencingTasks = new ArrayList<EObject>();

        Activity thisAct = getActivity();

        Collection<Activity> activities = Xpdl2ModelUtil
                .getAllActivitiesInProc(Xpdl2ModelUtil.getProcess(thisAct));

        for (Activity act : activities) {
            if (act != thisAct && act.getImplementation() instanceof Task) {
                if (TaskType.RECEIVE_LITERAL
                        .equals(TaskObjectUtil.getTaskType(act))) {

                    if (thisAct.getId().equals(
                            ReferenceTaskUtil.getReferencedTaskId(act))) {
                        referencingTasks.add(act);
                    }
                }
            }
        }

        return referencingTasks;
    }

    /**
     * TODO This is temporary for 90 day challenge.
     */
    public String getScript() {
        if (getTaskType() == TaskType.SCRIPT_LITERAL) {
            Activity activity = getActivity();
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task tsk = (Task) implementation;
                TaskScript taskScript = tsk.getTaskScript();
                Expression script = taskScript.getScript();
                if (script == null) {
                    return EMPTY_STRING; // There is a script but it is not
                    // defined.
                    // //$NON-NLS-1$
                }
                FeatureMap mixed = script.getMixed();
                List texts = (List) mixed.get(
                        XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        false);
                if (texts == null) {
                    return null;
                } else if (texts.size() == 1) {
                    return (String) texts.get(0);
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (Iterator iter = texts.iterator(); iter.hasNext();) {
                        sb.append(iter.next());
                    }
                    return sb.toString();
                }
            }
        }
        return null;
    }

    @Override
    public Command getSetMarkersCommand(EditingDomain ed, Set markers) {
        return TaskObjectUtil.getSetMarkersCommand(ed, getActivity(), markers);
    }

    @Override
    public Command getSetPerformersCommand(EditingDomain ed,
            EObject[] newPerformers) {
        return TaskObjectUtil
                .getSetPerformersCommand(ed, getActivity(), newPerformers);
    }

    @Override
    public Command getSetSubprocessCommand(EditingDomain ed, EObject subflow) {
        return TaskObjectUtil
                .getSetSubprocessCommand(ed, getActivity(), subflow);
    }

    @Override
    public Command getSetSubProcessIsTransactionalCommand(EditingDomain ed,
            boolean transactional) {
        return TaskObjectUtil.getSetSubProcessIsTransactionalCommand(ed,
                getActivity(),
                transactional);
    }

    @Override
    public Command getSetTaskTypeCommand(EditingDomain ed,
            TaskType newTaskType) {
        return TaskObjectUtil.getSetTaskTypeCommandEx(ed,
                getActivity(),
                newTaskType,
                getEProcess(),
                true,
                true,
                true);
    }

    @Override
    public EObject getSubprocess() {
        return TaskObjectUtil.getSubProcessOrInterface(getActivity());
    }

    @Override
    public String getSubprocessDescription() {
        return TaskObjectUtil.getSubprocessDescription(getActivity());
    }

    @Override
    public boolean getSubprocessIsTransactional() {
        return TaskObjectUtil.getSubprocessIsTransactional(getActivity());
    }

    @Override
    public String getSubprocessLocationDescription() {
        return TaskObjectUtil.getSubprocessLocationDescription(getActivity());
    }

    private Task getTask() {

        Activity activity = getActivity();
        if (activity != null) {
            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {
                return (Task) impl;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.TaskAdapter#
     * getTaskServiceTypeExtensionName()
     */
    @Override
    public String getTaskImplementationExtensionId() {
        return TaskObjectUtil.getTaskImplementationExtensionId(getActivity());
    }

    @Override
    public TaskType getTaskType() {
        return TaskObjectUtil.getTaskType(getActivity());
    }

    @Override
    public void notifyChanged(Notification msg) {

        if (msg.getNotifier() == getActivity() && msg.getFeatureID(
                Activity.class) == Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY) {
            if (getActivity().getBlockActivity() != null) {
                if (!getActivity().eAdapters().contains(activitySetListener)) {
                    getActivity().eAdapters().add(activitySetListener);
                }
            } else {
                getActivity().eAdapters().remove(activitySetListener);
            }
        }
        if (!msg.isTouch()) {
            super.notifyChanged(msg);
        }
    }

    @Override
    public void setTarget(Notifier newTarget) {

        super.setTarget(newTarget);

        if (target != null) {
            OclQueryListener lstnr = new OclQueryListener() {
                @Override
                public void oclNotifyChanged(EObject base, Object target,
                        Notification n) {
                    if (n.getNotifier() != base) {
                        Set ids = new HashSet();
                        if (n.getNotifier() instanceof FlowContainer) {
                            if (n.getNewValue() instanceof Transition) {
                                ids.add(((Transition) n.getNewValue())
                                        .getFrom());
                                ids.add(((Transition) n.getNewValue()).getTo());
                            }
                            if (n.getOldValue() instanceof Transition) {
                                ids.add(((Transition) n.getOldValue())
                                        .getFrom());
                                ids.add(((Transition) n.getOldValue()).getTo());
                            }
                        }
                        if (!ids.isEmpty()) {
                            Set<Activity> activities = new HashSet<>();

                            for (Iterator iter = ids.iterator(); iter
                                    .hasNext();) {
                                String actId = (String) iter.next();

                                if (getActivitySet() != null) {
                                    Activity act =
                                            getActivitySet().getActivity(actId);
                                    if (act != null) {
                                        act.eNotify(n);

                                        activities.add(act);
                                    }
                                }
                            }

                            /*
                             * SID XPD-8373 - Deleting flows from embedded
                             * sub-processes does not remove the editpart/figure
                             * from diagram visuals.
                             * 
                             * Basically, it would seem that in Eclipse 4.7 it
                             * seems that the correct notifications are ending
                             * up at the sequenceflow edit part /adapter to
                             * cause it to remove the visuals.
                             * 
                             * As things worked fine for flows in the main
                             * process I checked how that managed to refresh the
                             * visuals. This happens in
                             * Xpdl2ProcessDiagramAdapter.notifyChanged() where
                             * it looks for addition or removal of flows and
                             * posts a
                             * NotificationsConstants.REFRESH_REFERENCED_ELEMENT
                             * event to the activities connected to the flow.
                             * 
                             * By doing this, the activity deletes it's
                             * outgoing/incoming connection edit parts and this
                             * in turn causes the flow visuals to get removed.
                             * 
                             * This must have happened by some other
                             * notification prior to Eclipse 4.7.
                             * 
                             * So I have changed this code to post a
                             * REFRESH_REFERENCED_ELEMENT to each activity
                             * connected to a flow that is changing/deleting
                             * etc. This APPEARS to work.
                             */
                            NotificationImpl ev = new NotificationImpl(
                                    NotificationsConstants.REFRESH_REFERENCED_ELEMENT,
                                    null, null);

                            for (Activity activity : activities) {
                                activity.eNotify(ev);
                            }

                        } else {
                            /*
                             * Sid XPD-8302 - pass message in so can ignore
                             * adapter removal
                             */
                            fireDiagramNotification(n);
                        }
                    }
                }
            };

            if (implListener != null) {
                implListener.getTarget().eAdapters().remove(implListener);
            } else {
                implListener = new OclBasedNotifier("self.implementation", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                implListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(implListener);

            if (blockActListener != null) {
                blockActListener.getTarget().eAdapters()
                        .remove(blockActListener);
            } else {
                blockActListener = new OclBasedNotifier("self.blockActivity", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                blockActListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(blockActListener);

            if (activityLoopListener != null) {
                activityLoopListener.getTarget().eAdapters()
                        .remove(activityLoopListener);
            } else {
                activityLoopListener = new OclBasedNotifier("self.loop", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                activityLoopListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(activityLoopListener);

            if (activityMultiLoopListener != null) {
                activityMultiLoopListener.getTarget().eAdapters()
                        .remove(activityMultiLoopListener);
            } else {
                activityMultiLoopListener =
                        new OclBasedNotifier("self.loop.loopMultiInstance", //$NON-NLS-1$
                                Xpdl2Package.eINSTANCE.getActivity());
                activityMultiLoopListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(activityMultiLoopListener);

            if (activitySetListener != null) {
                activitySetListener.getTarget().eAdapters()
                        .remove(activitySetListener);
            } else {
                activitySetListener = new OclBasedNotifier(
                        "self.process.getActivitySet(self.blockActivity.activitySetId)", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                activitySetListener.addListener(lstnr);
            }
            if (getActivity().getBlockActivity() != null) {
                getTarget().eAdapters().add(activitySetListener);
            }

            if (activityIconListener != null) {
                activityIconListener.getTarget().eAdapters()
                        .remove(activityIconListener);
            } else {
                activityIconListener = new OclBasedNotifier("self.icon.value", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getActivity());
                activityIconListener.addListener(lstnr);
            }
            getTarget().eAdapters().add(activityIconListener);

        }
    }

    private static CreateAccessibleObjectCommand createSubActivity(
            EditingDomain editingDomain, CompoundCommand cmd,
            Activity embSubProcActivity, Activity activity) {

        if (activity instanceof Activity) {

            ActivitySet activitySet = getOrCreateActivitySet(editingDomain,
                    cmd,
                    embSubProcActivity);

            cmd.append(AddCommand.create(editingDomain,
                    activitySet,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    activity));

            return new CreateAccessibleObjectCommand(cmd, activity);
        }

        return new CreateAccessibleObjectCommand(UnexecutableCommand.INSTANCE,
                null);

    }

    /**
     * @return assigned activity set, or null
     */
    private ActivitySet getActivitySet() {
        return TaskObjectUtil.getActivitySet(getActivity());
    }

    /**
     * Either get the existing activity set and return it or create one, add the
     * command to add it to model and return that.
     * 
     * @param editingDomain
     * @param baseAct
     * @param cmd
     * @return Existing or New Activity set.
     */
    private static ActivitySet getOrCreateActivitySet(
            EditingDomain editingDomain, CompoundCommand cmd,
            Activity activity) {

        ActivitySet activitySet = TaskObjectUtil.getActivitySet(activity);

        if (activitySet == null) {
            // Create new activity set.
            activitySet = Xpdl2Factory.eINSTANCE.createActivitySet();

            BlockActivity blockAct =
                    Xpdl2Factory.eINSTANCE.createBlockActivity();

            blockAct.setActivitySetId(activitySet.getId());
            blockAct.setView(ViewType.EXPANDED);

            cmd.append(AddCommand.create(editingDomain,
                    activity.getProcess(),
                    Xpdl2Package.eINSTANCE.getProcess_ActivitySets(),
                    activitySet));
            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_BlockActivity(),
                    blockAct));
        }

        return activitySet;
    }

    @Override
    public DropTypeInfo getDropTypeInfo(List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {
        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);

        Process targetProcess = getEProcess();

        //
        // Overall, if actual target is not lane, activity or transition
        // then we
        // can't do anything.
        //
        if (actualTarget instanceof Lane || actualTarget instanceof Activity
                || actualTarget instanceof Transition) {

            /*
             * SDA-59: Don't allow any kind of drop onto decision flow process,
             * as most aren't supported. <p> If some become available then these
             * should be contributed via dropObjectContribution extension point.
             */
            if (!DecisionFlowUtil.isDecisionFlow(targetProcess)) {

                //
                // Participant / Performer Fields are effectively the same
                // thing.
                //
                if (LocalDropObjectUtils.checkAllAreValidTaskParticipants(
                        Xpdl2ModelUtil.getProcess(getActivity()),
                        dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(targetProcess)) {

                    if (DiagramDropObjectUtils.checkFromSamePackage(dropObjects,
                            Xpdl2ModelUtil.getPackage(getActivity()))) {

                        /*
                         * ABPM-911: Saket: An event subprocess should mostly
                         * behave like an embedded sub-process.
                         */
                        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                                .equals(getTaskType())
                                || TaskType.EVENT_SUBPROCESS_LITERAL
                                        .equals(getTaskType())) {
                            // Drop partics on embedded subproc = create tasks
                            // for
                            // partics
                            if (!isEmbeddedSubProcessCollapsed()) {
                                dropType.setDndDropType(DND.DROP_COPY);

                                dropType.setFeedbackRectangles(
                                        DiagramDropObjectUtils
                                                .getTaskFeedbackRectangles(1));
                            }
                        } else {
                            // Drop partics onto normal task = set partics.
                            // We allow either DROP_COPY (sets operation AND
                            // sets task name) or DROP_MOVE.
                            if (userRequestedDropType == DND.DROP_COPY) {
                                dropType = DropTypeInfo.DROP_COPY;
                            } else {
                                dropType = DropTypeInfo.DROP_MOVE;
                            }

                        }
                    }
                } else if (LocalDropObjectUtils
                        .checkAllAreValidOMNamedElements(dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(targetProcess)) {

                    /*
                     * ABPM-911: Saket: An event subprocess should mostly behave
                     * like an embedded sub-process.
                     */
                    if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                            .equals(getTaskType())
                            || TaskType.EVENT_SUBPROCESS_LITERAL
                                    .equals(getTaskType())) {
                        // Drop partics on embedded subproc = create tasks for
                        // partics
                        if (!isEmbeddedSubProcessCollapsed()) {
                            dropType.setDndDropType(DND.DROP_COPY);

                            dropType.setFeedbackRectangles(
                                    DiagramDropObjectUtils
                                            .getTaskFeedbackRectangles(1));
                        }
                    } else {
                        // Drop partics onto normal task = set partics.
                        // We allow either DROP_COPY (sets operation AND
                        // sets task name) or DROP_MOVE.
                        if (userRequestedDropType == DND.DROP_COPY) {
                            dropType = DropTypeInfo.DROP_COPY;
                        } else {
                            dropType = DropTypeInfo.DROP_MOVE;
                        }
                    }
                }
                //
                // Drop Process(es) = create Independent Sub-Process Task(s)
                //
                else if (LocalDropObjectUtils.checkAllAreValidProcesses(
                        targetProcess,
                        dropObjects)) {

                    /*
                     * XPD-7514: Saket: The code placed here initially was
                     * preventing valid drag-n-drop use cases (e.g. Service
                     * process being dragged-dropped on an embedded/event
                     * sub-process inside a pageflow).
                     */

                    /*
                     * ABPM-911: Saket: An event subprocess should mostly behave
                     * like an embedded sub-process.
                     */
                    if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                            .equals(getTaskType())
                            || TaskType.EVENT_SUBPROCESS_LITERAL
                                    .equals(getTaskType())) {
                        // Drop process on embedded subproc = create
                        // independent
                        // sub-process task
                        if (!isEmbeddedSubProcessCollapsed()) {

                            dropType.setDndDropType(DND.DROP_COPY);

                            dropType.setFeedbackRectangles(
                                    DiagramDropObjectUtils
                                            .getTaskFeedbackRectangles(
                                                    dropObjects.size()));
                        }
                    } else {
                        // Can Drop Single Process onto a task to set it as
                        // an
                        // independent sub-process task.
                        if (dropObjects.size() == 1) {
                            // We allow either DROP_COPY (sets operation AND
                            // sets task name) or DROP_MOVE.
                            if (userRequestedDropType == DND.DROP_COPY) {
                                dropType.setDndDropType(DND.DROP_COPY);
                            } else {
                                dropType.setDndDropType(DND.DROP_MOVE);
                            }
                        }
                    }
                }
                //
                // Drop data field / formal parameter = create user task with
                // assigned
                // parameters in em
                //
                else if (LocalDropObjectUtils.checkAllAreProcessRelevantData(
                        dropObjects,
                        Xpdl2ModelUtil.getProcess(getActivity()))) {

                    /*
                     * ABPM-911: Saket: An event subprocess should mostly behave
                     * like an embedded sub-process.
                     */
                    if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                            .equals(getTaskType())
                            || TaskType.EVENT_SUBPROCESS_LITERAL
                                    .equals(getTaskType())) {
                        // Drop fields on embedded subproc = create user task
                        // with
                        // fields as params.
                        if (!isEmbeddedSubProcessCollapsed()) {
                            dropType.setDndDropType(DND.DROP_COPY);

                            dropType.setFeedbackRectangles(
                                    DiagramDropObjectUtils
                                            .getTaskFeedbackRectangles(1));
                        }
                    } else if (TaskType.USER_LITERAL.equals(getTaskType())) {
                        // Drop fields on user task (only) = set parameters from
                        // fields.
                        dropType.setDndDropType(DND.DROP_MOVE);
                    }

                }
                //
                // Drop Task(s) from task library onto business process /
                // pageflow...
                //
                else if (LocalDropObjectUtils
                        .checkAllAreLibraryTasks(dropObjects, targetProcess)) {
                    if (!ProcessWidgetType.TASK_LIBRARY
                            .equals(getProcessType())) {

                        /*
                         * ABPM-911: Saket: An event subprocess should mostly
                         * behave like an embedded sub-process.
                         */
                        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                                .equals(getTaskType())
                                || TaskType.EVENT_SUBPROCESS_LITERAL
                                        .equals(getTaskType())) {
                            // Drop task on embedded subproc = create reference
                            // task
                            if (!isEmbeddedSubProcessCollapsed()) {
                                dropType.setDndDropType(DND.DROP_COPY);

                                dropType.setFeedbackRectangles(
                                        DiagramDropObjectUtils
                                                .getTaskFeedbackRectangles(
                                                        dropObjects.size()));
                            }
                        } else {
                            if (dropObjects.size() == 1)
                                // Drop fields on reference task or task type
                                // none =
                                // create reference to library task.
                                dropType.setDndDropType(DND.DROP_MOVE);
                        }
                    }
                }
            }

            //
            // Drop image file onto task can set the icon for it.
            // (This can be done for decision flow as well.)
            //
            if (dropType.getDndDropType() == DND.DROP_NONE) {
                if (dropObjects.size() == 1
                        && dropObjects.get(0) instanceof IFile) {
                    IFile file = (IFile) dropObjects.get(0);

                    IProject project =
                            WorkingCopyUtil.getProjectFor(getActivity());
                    if (project.equals(file.getProject())) {

                        String ext = file.getFileExtension();
                        if (ImagePicker.getImageFileExtensions()
                                .contains(ext)) {

                            if (TaskObjectUtil
                                    .getTaskTypeStrict(getActivity()) != null) {
                                dropType.setDndDropType(DND.DROP_MOVE);
                            }
                        }
                    }
                }
            }
        }

        //
        // If we don't have anything that handles drop type then all other
        // contributers to have a go.
        if (dropType.getDndDropType() == DND.DROP_NONE) {
            dropType = super.getDropTypeInfo(dropObjects,
                    location,
                    actualTarget,
                    userRequestedDropType);
        }
        return dropType;
    }

    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {

        List<DropObjectPopupItem> returnPopupItems =
                new ArrayList<DropObjectPopupItem>();

        //
        // Overall, if actual target is not lane, activity or transition then we
        // can't do anything.
        //
        if (actualTarget instanceof Lane || actualTarget instanceof Activity
                || actualTarget instanceof Transition) {
            Process process = null;
            if (actualTarget instanceof EObject) {
                process = Xpdl2ModelUtil.getProcess((EObject) actualTarget);
            }

            List<DropObjectPopupItem> myPopupItems = null;

            Object firstDropObj = dropObjects.get(0);

            //
            // Slightly different stuff todo depending on whether we're an
            // embedded subproc or a normal task (we can create new objects
            // within emb sub-proc)
            //

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(getTaskType())
                    || TaskType.EVENT_SUBPROCESS_LITERAL
                            .equals(getTaskType())) {

                if (!isEmbeddedSubProcessCollapsed()) {
                    /*
                     * Drop participant or performer data field/formalparam on
                     * lane = create task with given participant.
                     */
                    if (LocalDropObjectUtils.checkAllAreValidTaskParticipants(
                            Xpdl2ModelUtil.getProcess(getActivity()),
                            dropObjects)) {
                        myPopupItems = LocalDropObjectUtils
                                .getCreateTaskFromParticDropPopupItems(null,
                                        dropObjects,
                                        location,
                                        actualTarget);
                    }
                    /*
                     * Drop Process(es) = create Independent Sub-Process Task(s)
                     */
                    else if (LocalDropObjectUtils.checkAllAreValidProcesses(
                            getEProcess(),
                            dropObjects)) {
                        returnPopupItems = LocalDropObjectUtils
                                .getCreateTaskFromProcessDropPopupItems(process,
                                        null,
                                        dropObjects,
                                        location,
                                        Xpdl2ModelUtil
                                                .getPackage(getActivity()),
                                        actualTarget);
                    }
                    /*
                     * Drop data field / formal parameter = create user task
                     * with assigned parameters.
                     */
                    else if (LocalDropObjectUtils
                            .checkAllAreProcessRelevantData(dropObjects,
                                    getEProcess())) {
                        returnPopupItems = LocalDropObjectUtils
                                .getCreateTaskFromFieldsDropPopupItems(process,
                                        null,
                                        dropObjects,
                                        location);
                    }
                    /*
                     * Drop Task from task library onto business process /
                     * pageflow process - create reference task.
                     */
                    else if (LocalDropObjectUtils.checkAllAreLibraryTasks(
                            dropObjects,
                            getEProcess())) {
                        if (!ProcessWidgetType.TASK_LIBRARY
                                .equals(getProcessType())) {
                            myPopupItems = LocalDropObjectUtils
                                    .getCreateLibraryTaskReferenceDropPopupItems(
                                            getEProcess(),
                                            null,
                                            dropObjects,
                                            location,
                                            getEProcess().getPackage(),
                                            actualTarget);
                        }
                    }
                }
            }
            //
            // Not an embedded sub-process...
            //
            else {
                //
                // Set/add participants to task.
                //
                if (LocalDropObjectUtils.checkAllAreValidTaskParticipants(
                        Xpdl2ModelUtil.getProcess(getActivity()),
                        dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(getEProcess())) {
                    myPopupItems =
                            createSetTaskPerformerDropPopupItems(editingDomain,
                                    dropObjects,
                                    userRequestedDropType == DND.DROP_COPY);

                } else if (LocalDropObjectUtils
                        .checkAllAreValidOMNamedElements(dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(getEProcess())) {
                    myPopupItems =
                            createSetTaskOMExternalReferencePerformerDropPopupItems(
                                    editingDomain,
                                    dropObjects,
                                    userRequestedDropType == DND.DROP_COPY);
                }
                //
                // Set task as independent sub-process call to given process.
                //
                else if (((firstDropObj instanceof Process && !DecisionFlowUtil
                        .isDecisionFlow((Process) firstDropObj))
                        || firstDropObj instanceof ProcessInterface)
                        && dropObjects.size() == 1) {
                    myPopupItems =
                            createSetTaskProcessDropPopupItems(editingDomain,
                                    (NamedElement) firstDropObj,
                                    userRequestedDropType == DND.DROP_COPY);
                }
                //
                // Drop Fields onto user task = set parameters from fields.
                //
                else if (LocalDropObjectUtils.checkAllAreProcessRelevantData(
                        dropObjects,
                        Xpdl2ModelUtil.getProcess(getActivity()))
                        && TaskType.USER_LITERAL.equals(getTaskType())) {

                    myPopupItems = LocalDropObjectUtils
                            .createAddTaskParamsDropPopupItems(editingDomain,
                                    dropObjects,
                                    getActivity());

                }
                //
                // Drop Task from task library onto business process / pageflow
                // process - create reference task.
                //
                else if (LocalDropObjectUtils
                        .checkAllAreLibraryTasks(dropObjects, getEProcess())
                        && dropObjects.size() == 1) {
                    if (!ProcessWidgetType.TASK_LIBRARY
                            .equals(getProcessType())) {
                        myPopupItems =
                                createSetTaskRefDropPopupItems(editingDomain,
                                        dropObjects);
                    }
                }
                //
                // Drop image file onto task can set the icon for it.
                //
                else if (dropObjects.size() == 1
                        && dropObjects.get(0) instanceof IFile) {
                    IFile file = (IFile) dropObjects.get(0);

                    IProject project =
                            WorkingCopyUtil.getProjectFor(getActivity());
                    if (project.equals(file.getProject())) {

                        String ext = file.getFileExtension();
                        if (ImagePicker.getImageFileExtensions()
                                .contains(ext)) {
                            if (TaskObjectUtil
                                    .getTaskTypeStrict(getActivity()) != null) {
                                IPath path = file.getProjectRelativePath();

                                returnPopupItems.add(
                                        DropObjectPopupItem.createCommandItem(
                                                getSetTaskIconCommand(
                                                        editingDomain,
                                                        path.toString()),
                                                Messages.Xpdl2TaskAdapter_SetTaskIcon_menu,
                                                null));

                            }
                        }
                    }
                }

            }

            //
            // If we have any drop popup items then add to return list.
            if (myPopupItems != null && myPopupItems.size() > 0) {
                returnPopupItems.addAll(myPopupItems);
            }
        }

        //
        // Call super to allow any other external contributions of drop popup
        // items.
        List<DropObjectPopupItem> otherPopupItems =
                super.getDropPopupItems(editingDomain,
                        dropObjects,
                        location,
                        actualTarget,
                        userRequestedDropType);

        if (otherPopupItems != null && otherPopupItems.size() > 0) {
            returnPopupItems.addAll(otherPopupItems);
        }

        return returnPopupItems;
    }

    /**
     * Create the drop popup item to set this task to a reference to the dropped
     * library task
     * 
     * @param editingDomain
     * @param dropObjects
     * @return the drop popup item to set this task to a reference to the
     *         dropped object
     */
    private List<DropObjectPopupItem> createSetTaskRefDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects) {
        Activity libraryTask = (Activity) dropObjects.get(0);

        CompoundCommand cmd = new CompoundCommand(
                Messages.Xpdl2TaskAdapter_SetTaskReference_menu);

        Activity activity = getActivity();
        TaskType curTaskType = getTaskType();

        //
        // Change task type if not already reference task.
        Command setTaskTypeCommandEx =
                TaskObjectUtil.getSetTaskTypeCommandEx(editingDomain,
                        activity,
                        TaskType.REFERENCE_LITERAL,
                        activity.getProcess(),
                        false,
                        true,
                        true);

        if (setTaskTypeCommandEx != null && setTaskTypeCommandEx.canExecute()) {
            cmd.append(setTaskTypeCommandEx);
        }

        // Reset name if cur name is default for cur type
        String curName = Xpdl2ModelUtil.getDisplayNameOrName(activity);
        if (curName.startsWith(curTaskType.toString())
                || (TaskType.REFERENCE_LITERAL.equals(curTaskType)
                        && ReferenceTaskUtil.isDefaultTaskName(curName))) {
            String displayName =
                    ReferenceTaskUtil.getDefaultReferenceTaskName(libraryTask);
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                    displayName));
        }

        //
        // Set the reference.
        cmd.append(ReferenceTaskUtil.getSetReferencedLibraryTaskCommand(
                editingDomain,
                activity,
                getEProcess(),
                libraryTask.getId(),
                libraryTask.getProcess()));

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                Messages.Xpdl2TaskAdapter_SetTaskReference_menu,
                DiagramDropObjectUtils
                        .getImageForTaskType(TaskType.REFERENCE_LITERAL)));
        return popupItems;
    }

    /**
     * Create drop popup item to set this task to an independent sub-process
     * call for the given process. If the task is a User task and the dropped
     * process is a Pageflow Process then set the Form URL instead.
     * 
     * @param editingDomain
     * @param firstDropObj
     * @param popupItems
     */
    private List<DropObjectPopupItem> createSetTaskProcessDropPopupItems(
            EditingDomain editingDomain, NamedElement subProcess,
            boolean setTaskNameFromProcess) {

        /*
         * Check for need to add external projeft reference ater confirmation
         * with user.
         */
        List<EObject> refObjects = new ArrayList<EObject>();
        refObjects.add(subProcess);

        if (!ProcessUIUtil.checkAndAddProjectReferences(Display.getDefault()
                .getActiveShell(), getActivity(), refObjects)) {
            return Collections.emptyList();
        }

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();
        CompoundCommand cmd = new CompoundCommand();

        if (TaskType.USER_LITERAL.equals(getTaskType())
                && subProcess instanceof Process
                && Xpdl2ModelUtil.isPageflow((Process) subProcess)) {

            FormImplementation formImpl =
                    XpdExtensionFactory.eINSTANCE.createFormImplementation();
            formImpl.setFormType(FormImplementationType.PAGEFLOW);
            formImpl.setFormURI(
                    TaskObjectUtil.getUserTaskFormURIFromPageflowProcess(
                            (Process) subProcess));

            cmd.append(TaskObjectUtil.getUserTaskSetFormImplementationCommand(
                    editingDomain,
                    getActivity(),
                    formImpl));

            Command addExtraDataCmd = new AbstractLateExecuteCommand(
                    editingDomain, new Object[] { getActivity(), subProcess,
                            getActivity().getProcess() }) {

                @Override
                protected Command createCommand(EditingDomain editingDomain,
                        Object contextObject) {
                    Activity userTaskActivity =
                            (Activity) ((Object[]) contextObject)[0];
                    Process pageflowProcess =
                            (Process) ((Object[]) contextObject)[1];
                    Process targetProcess =
                            (Process) ((Object[]) contextObject)[2];

                    Command cmd = PageflowUtil
                            .getCreateUserTaskDataForPageflowCommand(
                                    editingDomain,
                                    userTaskActivity,
                                    targetProcess,
                                    pageflowProcess,
                                    true);
                    if (cmd != null) {
                        return cmd;
                    }

                    return UnexecutableCommand.INSTANCE;
                }

            };
            cmd.append(addExtraDataCmd);

            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.Xpdl2TaskAdapter_SetPageflowCommand,
                    DiagramDropObjectUtils
                            .getImageForTaskType(TaskType.USER_LITERAL)));
        } else {
            // Set as a sub-process task if it's not already.
            if (!TaskType.SUBPROCESS_LITERAL.equals(getTaskType())) {
                cmd.append(TaskObjectUtil.getSetTaskTypeCommand(editingDomain,
                        getActivity(),
                        TaskType.SUBPROCESS_LITERAL));
            }
            // Set process.
            cmd.append(getSetSubprocessCommand(editingDomain, subProcess));

            if (setTaskNameFromProcess) {
                cmd.append(
                        getSetNameCommand(editingDomain, subProcess.getName()));
            }

            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.Xpdl2TaskAdapter_SetTaskProcess_menu,
                    DiagramDropObjectUtils
                            .getImageForTaskType(TaskType.SUBPROCESS_LITERAL)));
        }
        return popupItems;
    }

    /**
     * Create the drop-popup menu items appropriate for dropping
     * participants/performer fields on task
     * 
     * @param editingDomain
     * @param dropObjects
     * @param setTaskNameFromParticipant
     * @return
     */
    private List<DropObjectPopupItem> createSetTaskPerformerDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            boolean setTaskNameFromParticipant) {
        // Drop participant / performer fields on task = set/add
        // participants.
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        EObject[] existingPerformers = getActivityPerformers();

        String taskName = ""; //$NON-NLS-1$

        // Use a set to ensure that we don't add duplicates.
        Set<EObject> addPerformersList = new HashSet<EObject>();
        int i;
        for (i = 0; i < existingPerformers.length; i++) {
            addPerformersList.add(existingPerformers[i]);

            if (i != 0) {
                taskName += ", "; //$NON-NLS-1$
            }
            taskName += getPerformerDescription(existingPerformers[i]);
        }

        for (Iterator iterator = dropObjects.iterator(); iterator.hasNext();) {
            EObject dropObj = (EObject) iterator.next();
            addPerformersList.add(dropObj);

            if (i != 0) {
                taskName += ", "; //$NON-NLS-1$
            }
            taskName += getPerformerDescription(dropObj);

            i++;
        }

        CompoundCommand cmd = new CompoundCommand();
        Command perfCommand = getSetPerformersCommand(editingDomain,
                addPerformersList
                        .toArray(new EObject[addPerformersList.size()]));
        if (perfCommand != null) {
            cmd.append(perfCommand);
        }
        addSetAliasWithParticipantCommand(cmd,
                editingDomain,
                addPerformersList.toArray());

        if (setTaskNameFromParticipant) {
            cmd.append(getSetNameCommand(editingDomain, taskName));
        }

        popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                Messages.Xpdl2TaskAdapter_AddTaskPartics_menu,
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_ADDPARTICIPANT)));

        if (existingPerformers.length > 0) {
            // If there are current participants set on task then have
            // to give choice to replace or add
            EObject[] replacePerformersList =
                    dropObjects.toArray(new EObject[dropObjects.size()]);

            taskName = ""; //$NON-NLS-1$
            for (int j = 0; j < replacePerformersList.length; j++) {
                if (j > 0) {
                    taskName += ", "; //$NON-NLS-1$
                }
                taskName += getPerformerDescription(replacePerformersList[j]);
            }

            Image img;

            if (dropObjects.size() > 1) {
                img = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_MULTIPARTICIPANT);
            } else {
                img = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PARTICIPANT);
            }

            cmd = new CompoundCommand();
            Command perfCommand2 = getSetPerformersCommand(editingDomain,
                    replacePerformersList);
            if (perfCommand2 != null) {
                cmd.append(perfCommand2);
            }
            addSetAliasWithParticipantCommand(cmd,
                    editingDomain,
                    replacePerformersList);

            if (setTaskNameFromParticipant) {
                cmd.append(getSetNameCommand(editingDomain, taskName));

            }
            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.Xpdl2TaskAdapter_ReplaceTaskPArtics_menu,
                    img));

        }

        return popupItems;
    }

    /**
     * Create the drop-popup menu items appropriate for dropping external om
     * participants/performer fields on task
     * 
     * @param editingDomain
     * @param dropObjects
     * @param setTaskNameFromParticipant
     * @return
     */
    private List<DropObjectPopupItem> createSetTaskOMExternalReferencePerformerDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            boolean setTaskNameFromParticipant) {
        // Drop participant / performer fields on task = set/add
        // participants.

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        EObject[] existingPerformers = getActivityPerformers();

        String taskName = ""; //$NON-NLS-1$

        // Use a set to ensure that we don't add duplicates.
        Set<EObject> addPerformersList = new HashSet<EObject>();
        int i;
        for (i = 0; i < existingPerformers.length; i++) {
            addPerformersList.add(existingPerformers[i]);

            if (i != 0) {
                taskName += ", "; //$NON-NLS-1$
            }
            taskName += getPerformerDescription(existingPerformers[i]);
        }

        EObject[] replacePerformersList =
                dropObjects.toArray(new EObject[dropObjects.size()]);

        int newItemIndex = 0;
        Set<Participant> newParticsToCreate = new HashSet<Participant>();

        for (Iterator iterator = dropObjects.iterator(); iterator.hasNext();) {
            EObject dropObj = (EObject) iterator.next();

            // See if we already have a participant that references the given
            // org model entity.
            Participant participant = TaskObjectUtil
                    .getParticipantForExternalReference(getActivity(), dropObj);
            if (participant != null) {
                //
                // The drop object is an OM entity for which there is already a
                // process/package participant - so use the existing one.
                participant = TaskObjectUtil.getParticipantForExternalReference(
                        getActivity(),
                        dropObj);
                replacePerformersList[newItemIndex] = participant;
                addPerformersList.add(participant);

            } else {
                //
                // Don't yet have a participant for this org model entity,
                // create a new participant and use that has the performer.
                Participant newParticipant = TaskObjectUtil
                        .createParticipantFromOMEntity(editingDomain,
                                getActivity(),
                                dropObj);

                newParticsToCreate.add(newParticipant);

                replacePerformersList[newItemIndex] = newParticipant;

                addPerformersList.add(newParticipant);
            }

            if (i != 0) {
                taskName += ", "; //$NON-NLS-1$
            }
            taskName += getPerformerDescription(dropObj);

            newItemIndex++;
            i++;
        }

        CompoundCommand cmd = new CompoundCommand();

        if (!newParticsToCreate.isEmpty()) {
            // If we neede3d to create new participants to represent OM entities
            // then add them to process participants.
            /*
             * Add participants into package now (process level participants are
             * discouraged.
             */
            cmd.append(AddCommand.create(editingDomain,
                    getEProcess().getPackage(),
                    Xpdl2Package.eINSTANCE
                            .getParticipantsContainer_Participants(),
                    newParticsToCreate));
        }

        Command perfCommand = getSetPerformersCommand(editingDomain,
                addPerformersList
                        .toArray(new EObject[addPerformersList.size()]));
        if (perfCommand != null) {
            cmd.append(perfCommand);
        }

        if (setTaskNameFromParticipant) {
            cmd.append(getSetNameCommand(editingDomain, taskName));
        }

        popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                Messages.Xpdl2TaskAdapter_AddTaskOmElements_menu,
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_ADDPARTICIPANT)));

        if (existingPerformers.length > 0) {
            taskName = ""; //$NON-NLS-1$
            for (int j = 0; j < replacePerformersList.length; j++) {
                if (j > 0) {
                    taskName += ", "; //$NON-NLS-1$
                }
                taskName += getPerformerDescription(replacePerformersList[j]);
            }

            Image img;

            if (dropObjects.size() > 1) {
                img = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_MULTIPARTICIPANT);
            } else {
                img = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PARTICIPANT);
            }

            cmd = new CompoundCommand();
            if (!newParticsToCreate.isEmpty()) {
                // If we neede3d to create new participants to represent OM
                // entities
                // then add them to process participants.
                cmd.append(AddCommand.create(editingDomain,
                        getEProcess().getPackage(),
                        Xpdl2Package.eINSTANCE
                                .getParticipantsContainer_Participants(),
                        newParticsToCreate));
            }

            Command perfCommand2 = getSetPerformersCommand(editingDomain,
                    replacePerformersList);
            if (perfCommand2 != null) {
                cmd.append(perfCommand2);
            }

            if (setTaskNameFromParticipant) {
                cmd.append(getSetNameCommand(editingDomain, taskName));

            }
            popupItems.add(DropObjectPopupItem.createCommandItem(cmd,
                    Messages.Xpdl2TaskAdapter_ReplaceTaskOmElements_menu,
                    img));

        }

        if (!popupItems.isEmpty()) {
            //
            // Ask user to confirm for 'need to create a project reference' if
            // any
            // are dragged from different process.
            List<EObject> dropEObjs = new ArrayList<EObject>();
            for (Object o : dropObjects) {
                if (o instanceof EObject) {
                    dropEObjs.add((EObject) o);
                }
            }

            if (!ProcessUIUtil.checkAndAddProjectReferences(Display.getDefault()
                    .getActiveShell(), getActivity(), dropEObjs)) {
                return Collections.emptyList();
            }
        }

        return popupItems;
    }

    /**
     * Set alias using the first participant details
     * 
     * @param cmd
     * @param editingDomain
     * @param performers
     */
    private void addSetAliasWithParticipantCommand(CompoundCommand cmd,
            EditingDomain editingDomain, Object[] performers) {
        if (performers.length > 0
                && getActivity().getImplementation() instanceof Task) {
            Task task = (Task) getActivity().getImplementation();
            WebServiceOperation wso = null;
            if (task.getTaskService() != null) {
                TaskService taskService = task.getTaskService();
                wso = taskService.getWebServiceOperation();
            } else if (task.getTaskSend() != null) {
                TaskSend taskSend = task.getTaskSend();
                wso = taskSend.getWebServiceOperation();
            } else if (task.getTaskReceive() != null) {
                TaskReceive taskReceive = task.getTaskReceive();
                wso = taskReceive.getWebServiceOperation();
            }
            if (wso != null) {
                cmd.append(Xpdl2ModelUtil
                        .getSetEndpointFromDataPickerSelectionCommand(
                                editingDomain,
                                performers[0],
                                wso));
            }
        }
    }

    @Override
    public CreateAccessibleObjectsCommand getInlineSubProcessContentCommand(
            EditingDomain ed,
            DiagramModelImageProvider callingProcessImageProvider,
            DiagramModelImageProvider subProcessImageProvider) {

        RefactorIndiSubInlineCommand cmd =
                new RefactorIndiSubInlineCommand(ed, getActivity(),
                        callingProcessImageProvider, subProcessImageProvider);

        // NOTE: Although cmd.getCreatedObjects() returns a list that is
        // initially empty; by the time the command has been executed it will
        // have been populated by RefactorIndiSubInlineCommand and hence will be
        // available when process widget needs it.
        return new CreateAccessibleObjectsCommand(cmd, cmd.getCreatedObjects());
    }

    @Override
    public boolean isInlineEnabled() {
        EObject processOrInterface = getSubprocess();
        if (processOrInterface instanceof Process) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMultiInstanceLoopParallel() {
        LoopMultiInstance lmi =
                TaskObjectUtil.getLoopMultiInstance(getActivity());
        if (lmi != null) {
            if (MIOrderingType.PARALLEL_LITERAL.equals(lmi.getMIOrdering())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMultiInstanceLoopSequential() {
        LoopMultiInstance lmi =
                TaskObjectUtil.getLoopMultiInstance(getActivity());
        if (lmi != null) {
            if (MIOrderingType.SEQUENTIAL_LITERAL.equals(lmi.getMIOrdering())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidPageflowUserTask() {
        if (TaskObjectUtil.getUserTaskPageflowProcess(getActivity()) != null) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#openPageflow()
     * 
     */
    @Override
    public void openPageflow() {
        if (isUserTask()) {
            Activity activity = getActivity();
            Process process =
                    TaskObjectUtil.getUserTaskPageflowProcess(activity);
            if (process != null) {
                IConfigurationElement facConfig = XpdResourcesUIActivator
                        .getEditorFactoryConfigFor(process);
                if (facConfig != null) {
                    String editorId = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                    try {
                        EditorInputFactory f = (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                        IEditorInput input = f.getEditorInputFor(process);
                        IWorkbenchPage page = PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getActivePage();
                        IDE.openEditor(page, input, editorId);
                    } catch (CoreException e) {
                        Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                .error(e);
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#removePageflow()
     * 
     */
    @Override
    public void removePageflow() {
        if (isUserTask()) {
            Activity activity = getActivity();
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
            Command cmd =
                    TaskObjectUtil.getUserTaskSetFormImplementationCommand(ed,
                            activity,
                            null);
            if (cmd.canExecute()) {
                ed.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#generatePageflow()
     * 
     */
    @Override
    public void generatePageflow() {
        if (isUserTask()) {
            PageflowUtil.generatePageFlowForUserTask(getActivity());
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#updatePageflow()
     * 
     */
    @Override
    public void updatePageflow() {
        if (isUserTask()) {
            Activity activity = getActivity();
            PageflowUtil.synchronizePageflowParameters(activity);
        }
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#useExistingPageflow()
     * 
     */
    @Override
    public void useExistingPageflow() {
        if (isUserTask()) {

            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();

            Activity activity = getActivity();

            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);

            Command cmd = TaskObjectUtil
                    .getUserTaskSetPageflowProcessFromPickerCommand(shell,
                            ed,
                            activity);
            if (cmd != null && cmd.canExecute()) {
                ed.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * @return
     */
    private boolean isUserTask() {
        boolean user = false;
        Activity activity = getActivity();
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null) {
                    user = true;
                }
            }
        }
        return user;
    }

    @Override
    public Command getSetRequestActivityForReplyActivityCommand(
            EditingDomain ed, String requestActivityId) {
        Task task = getTask();
        if (task != null && task.getTaskSend() != null) {
            return ReplyActivityUtil
                    .getSetRequestActivityForReplyActivityCommand(ed,
                            getActivity(),
                            requestActivityId);
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Collection<String> getReplyActivityIds() {
        List<String> ids = Collections.emptyList();

        Activity act = getActivity();
        if (act != null) {
            List<Activity> replies = ReplyActivityUtil.getReplyActivities(act);
            if (replies != null && !replies.isEmpty()) {
                ids = new ArrayList<String>();

                for (Activity reply : replies) {
                    ids.add(reply.getId());
                }

            }
        }
        return ids;
    }

    @Override
    public String getRequestActivityId() {
        Task task = getTask();
        if (task != null && task.getTaskSend() != null) {
            return ReplyActivityUtil
                    .getRequestActivityIdForReplyActivity(getActivity());
        }

        return null;
    }

    @Override
    public EObject getReferencedTaskObject() {
        return ReferenceTaskUtil.getReferencedTask(getActivity());
    }

    @Override
    public boolean isReferencedTaskLocal() {
        if (TaskType.REFERENCE_LITERAL.equals(getTaskType())) {
            if (ReferenceTaskUtil
                    .getReferencedLocalActivity(getActivity()) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmbeddedSubProcessCollapsed() {
        boolean ret = false;

        if (getActivity().getBlockActivity() != null) {
            if (getActivity().getBlockActivity()
                    .eIsSet(Xpdl2Package.eINSTANCE.getBlockActivity_View())) {
                if (ViewType.COLLAPSED
                        .equals(getActivity().getBlockActivity().getView())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Command getCollapseSubProcessCommand(EditingDomain ed,
            Dimension defaultCollapsedSize, Dimension returnCollapsedSize) {
        if (!isEmbeddedSubProcessCollapsed()
                && getActivity().getBlockActivity() != null) {
            CompoundCommand cmd = new CompoundCommand(
                    Messages.Xpdl2TaskAdapter_CollapseEmbSubProc_menu);

            //
            // Set the collapse/expanded state.
            cmd.append(SetCommand.create(ed,
                    getActivity().getBlockActivity(),
                    Xpdl2Package.eINSTANCE.getBlockActivity_View(),
                    ViewType.COLLAPSED));

            //
            // If this is not the first time collapsed then the last
            // set size for this state (i.e. the last set collapsed size)
            // is stored as extension attribute.
            Dimension lastSavedCollapsedSize =
                    getEmbeddedSubProcOtherStateSize();
            if (lastSavedCollapsedSize == null) {
                lastSavedCollapsedSize = defaultCollapsedSize;
            }

            //
            // Return the new size to the caller in param.
            if (returnCollapsedSize != null) {
                returnCollapsedSize.width = lastSavedCollapsedSize.width;
                returnCollapsedSize.height = lastSavedCollapsedSize.height;
            }

            //
            // Swap the current (expanded) size for the last (collapsed) size.
            Dimension currExpandedSize = getSize();

            // Set current size = last saved collapsed size.
            NodeGraphicsInfo ngi = Xpdl2ModelUtil
                    .getOrCreateNodeGraphicsInfo(getActivity(), ed, cmd);
            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                    new Double(lastSavedCollapsedSize.width)));
            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                    new Double(lastSavedCollapsedSize.height)));

            // Recenter the actviity (to keep top left in same place.
            Point location = getLocation().getCopy();

            location.x -= (currExpandedSize.width / 2);
            location.y -= (currExpandedSize.height / 2);

            location.x += (lastSavedCollapsedSize.width / 2);
            location.y += (lastSavedCollapsedSize.height / 2);

            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE.createCoordinates(location.x,
                            location.y)));

            // Set otherstatesize = current expanded size
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    getActivity().getBlockActivity(),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateWidth(),
                    new Double(currExpandedSize.width)));
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    getActivity().getBlockActivity(),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateHeight(),
                    new Double(currExpandedSize.height)));

            return cmd;
        }
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getExpandSubProcessCommand(EditingDomain ed,
            Dimension minimumExpandedSize, Dimension returnExpandedSize) {
        if (isEmbeddedSubProcessCollapsed()
                && getActivity().getBlockActivity() != null) {
            CompoundCommand cmd = new CompoundCommand(
                    Messages.Xpdl2TaskAdapter_ExpandEmbSubProc_menu);

            //
            // Set the collapse/expanded state.
            cmd.append(SetCommand.create(ed,
                    getActivity().getBlockActivity(),
                    Xpdl2Package.eINSTANCE.getBlockActivity_View(),
                    ViewType.EXPANDED));

            //
            // If this is not the first time expanded then the last
            // set size for this state (i.e. the last set expanded size)
            // is stored as extension attribute.
            Dimension lastSavedExpandedSize =
                    getEmbeddedSubProcOtherStateSize();
            if (lastSavedExpandedSize == null) {
                lastSavedExpandedSize = minimumExpandedSize;
            } else {
                if (lastSavedExpandedSize.width < minimumExpandedSize.width) {
                    lastSavedExpandedSize.width = minimumExpandedSize.width;
                }

                if (lastSavedExpandedSize.height < minimumExpandedSize.height) {
                    lastSavedExpandedSize.height = minimumExpandedSize.height;
                }
            }

            //
            // Return the new size to the caller in param.
            if (returnExpandedSize != null) {
                returnExpandedSize.width = lastSavedExpandedSize.width;
                returnExpandedSize.height = lastSavedExpandedSize.height;
            }

            //
            // Swap the current (collapsed) size for the last (expanded) size.
            Dimension currCollapsedSize = getSize();

            // Set current size = last saved expanded size.
            NodeGraphicsInfo ngi = Xpdl2ModelUtil
                    .getOrCreateNodeGraphicsInfo(getActivity(), ed, cmd);
            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                    new Double(lastSavedExpandedSize.width)));
            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                    new Double(lastSavedExpandedSize.height)));

            // Recenter the actviity (to keep top left in same place.
            Point location = getLocation().getCopy();

            location.x -= (currCollapsedSize.width / 2);
            location.y -= (currCollapsedSize.height / 2);

            location.x += (lastSavedExpandedSize.width / 2);
            location.y += (lastSavedExpandedSize.height / 2);

            cmd.append(SetCommand.create(ed,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE.createCoordinates(location.x,
                            location.y)));

            // Set otherstatesize = current collapsed size
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    getActivity().getBlockActivity(),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateWidth(),
                    new Double(currCollapsedSize.width)));
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    getActivity().getBlockActivity(),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateHeight(),
                    new Double(currCollapsedSize.height)));

            return cmd;
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Once the expand/collapse state of embedded sub-process has been changed
     * then the size it was last set to in its OPPOSITE state is stored in the
     * BlockActivity so that when the state changes back again we can return it
     * to its last size in that state.
     * <p>
     * This methid returns that opposite state size or <code>null</code>
     * 
     * @return The BlockActivity/EmbSubprocOtherStateWidth&Height as a Dimension
     *         or <code>null</code> if not set.
     */
    private Dimension getEmbeddedSubProcOtherStateSize() {
        Dimension ret = null;

        BlockActivity ba = getActivity().getBlockActivity();
        if (ba != null) {
            Double width = (Double) Xpdl2ModelUtil.getOtherAttribute(ba,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateWidth());
            Double height = (Double) Xpdl2ModelUtil.getOtherAttribute(ba,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EmbSubprocOtherStateHeight());

            if (width != null && width > 0 && height != null && height > 0) {
                return new Dimension(width.intValue(), height.intValue());
            }
        }
        return null;
    }

    @Override
    public Command getSetLocationCommand(EditingDomain ed, Point loc,
            Dimension dim) {

        return super.getSetLocationCommand(ed, loc, dim);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessContainerAdapter#
     * getCreateAndConnectPopupItem(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair,
     * org.eclipse.draw2d.geometry.Point, java.lang.Object)
     */
    @Override
    public DropObjectPopupItem getCreateAndConnectPopupItem(
            EditingDomain editingDomain, CreateAndConnectObjectPair cacPair,
            Point adjustableRelativeLocation, Object actualDropTarget,
            Object connectionSourceObject) {

        return LocalDropObjectUtils.getCreateAndConnectDropPopupItem(
                editingDomain,
                cacPair,
                adjustableRelativeLocation,
                getEProcess(),
                actualDropTarget,
                getActivity(),
                connectionSourceObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.TaskAdapter#getSetTaskIconCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.String)
     */
    @Override
    public Command getSetTaskIconCommand(EditingDomain editingDomain,
            String iconPath) {
        Activity activity = getActivity();

        if (activity != null) {
            if (iconPath == null || iconPath.length() == 0) {
                if (activity.getIcon() != null) {
                    return RemoveCommand.create(editingDomain,
                            activity.getIcon());
                }

            } else {
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.Xpdl2TaskAdapter_SetTaskIcon_menu);
                Icon icon = Xpdl2Factory.eINSTANCE.createIcon();
                icon.setValue(iconPath);
                cmd.append(SetCommand.create(editingDomain,
                        activity,
                        Xpdl2Package.eINSTANCE.getActivity_Icon(),
                        icon));
                return cmd;
            }

        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#getTaskIcon()
     */
    @Override
    public String getTaskIcon() {
        Activity activity = getActivity();

        if (activity != null) {
            Icon icon = activity.getIcon();
            if (icon != null) {
                return icon.getValue();
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#getDefaultTaskIcon()
     * 
     * @return
     */
    @Override
    public Image getDefaultTaskIcon() {
        Image image = null;
        Activity activity = getActivity();

        if (activity != null) {
            if (TaskType.SUBPROCESS_LITERAL
                    .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {

                image = getBaseSubprocessIcon(activity);

                if (image != null) {
                    /*
                     * Decorate with call-mode indicators (synch, asynch
                     * detached, asynch attached).
                     */
                    SubFlow subFlow = (SubFlow) activity.getImplementation();

                    Object execModeObject = Xpdl2ModelUtil.getOtherAttribute(
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AsyncExecutionMode());

                    if (AsyncExecutionMode.ATTACHED.equals(execModeObject)) {
                        Image cachedImage =
                                asyncAttachSubProcImageReg.get(image);

                        if (cachedImage != null) {
                            image = cachedImage;

                        } else {
                            String overlayImagePath =
                                    "icons/taskSubProcAsynchAttachOverlay.png";

                            Image overlayImage =
                                    overlayImage(image, overlayImagePath);

                            asyncAttachSubProcImageReg.put(image, overlayImage);

                            image = overlayImage;
                        }

                    } else if (AsyncExecutionMode.DETACHED
                            .equals(execModeObject)) {
                        Image cachedImage =
                                asyncDetachSubProcImageReg.get(image);

                        if (cachedImage != null) {
                            image = cachedImage;

                        } else {
                            String overlayImagePath =
                                    "icons/taskSubProcAsynchDetachOverlay.png";

                            Image overlayImage =
                                    overlayImage(image, overlayImagePath);

                            asyncDetachSubProcImageReg.put(image, overlayImage);

                            image = overlayImage;
                        }

                    } else {
                        Image cachedImage = syncSubProcImageReg.get(image);

                        if (cachedImage != null) {
                            image = cachedImage;

                        } else {
                            String overlayImagePath =
                                    "icons/taskSubProcSynchOverlay.png"; //$NON-NLS-1$

                            Image overlayImage =
                                    overlayImage(image, overlayImagePath);

                            syncSubProcImageReg.put(image, overlayImage);

                            image = overlayImage;
                        }
                    }
                }
            }
        }

        return image;
    }

    /**
     * @param image
     * @param activity
     * @return The base sub-process invocation image for given referenced
     *         process type.
     */
    private Image getBaseSubprocessIcon(Activity activity) {
        Image image = null;

        EObject subProcessOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);

        if (subProcessOrInterface instanceof Process) {
            Process process = (Process) subProcessOrInterface;

            if (Xpdl2ModelUtil.isPageflowProcess(process)) {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_PAGEFLOW);

            } else if (Xpdl2ModelUtil.isCaseService(process)) {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_CASESERVICE);

            } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_BIZSERVICE);

            } else if (Xpdl2ModelUtil.isServiceProcess(process)) {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_SERVICEPROCESS);
            } else {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_PROCESS);
            }

        } else if (subProcessOrInterface instanceof ProcessInterface) {
            if (Xpdl2ModelUtil.isServiceProcessInterface(
                    (ProcessInterface) subProcessOrInterface)) {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_SERVICEINTERFACE);

            } else {
                image = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.ICON_TASK_SUBPROCESS_INTERFACE);

            }
        }

        return image;
    }

    /**
     * @param image
     * @param overlayImagePath
     * @return
     */
    private Image overlayImage(Image image, String overlayImagePath) {
        Image overlayImage = null;

        ImageData synchOverlay =
                Xpdl2ProcessEditorPlugin.getImageDescriptor(overlayImagePath) // $NON-NLS-1$
                        .getImageData();
        if (synchOverlay != null) {
            overlayImage = new OverlayImageDescriptor(image.getImageData(),
                    synchOverlay).createImage();
        }
        return overlayImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.TaskAdapter#
     * getTaskTypeIconRegistryId ()
     */
    @Override
    public String getTaskTypeIconRegistryId() {
        Activity activity = getActivity();

        if (activity != null) {
            return TaskObjectUtil.getTaskTypeIconRegistryId(activity);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.TaskAdapter#
     * getThrowErrorForRequestActivityIds()
     */
    @Override
    public Collection<String> getThrowErrorIdsForRequestActivity() {
        if (getActivity() != null) {
            List<Activity> throwErrorEvents =
                    ThrowErrorEventUtil.getThrowErrorEvents(getActivity());
            if (!throwErrorEvents.isEmpty()) {
                List<String> ids = new ArrayList<String>();

                for (Activity activity : throwErrorEvents) {
                    ids.add(activity.getId());
                }
                return ids;
            }
        }
        return Collections.emptyList();
    }

    /**
     * Return <code>true</code> if the activity has at least one incoming flow,
     * <code>false</code> otherwise.
     * 
     * @param act
     *            Activity to look into.
     * @return <code>true</code> if the activity has at least one incoming flow,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean hasIncomingFlowsToActivity() {

        Activity act = getActivity();

        if (act != null) {
            EList<Transition> transitions = act.getIncomingTransitions();

            if (transitions != null && !transitions.isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
