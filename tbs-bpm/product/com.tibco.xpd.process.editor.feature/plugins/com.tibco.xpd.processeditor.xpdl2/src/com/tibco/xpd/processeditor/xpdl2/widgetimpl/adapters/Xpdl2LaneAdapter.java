/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 **
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * BPMN's Lane wrapper for XPDL2's Lane
 * 
 * @author wzurek
 */
public class Xpdl2LaneAdapter extends Xpdl2BaseProcessAdapter
        implements LaneAdapter, OclQueryListener {

    public static final int LANE_MIN_CONTENT_MARGIN = 10;

    private OclBasedNotifier processLst;

    private OclBasedNotifier toolLst;

    private OclBasedNotifier oldToolLst;

    private Lane getLane() {
        return (Lane) getTarget();
    }

    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {

        Lane lane = getLane();

        CompoundCommand cmd = new CompoundCommand();
        appendDeleteCommands(editingDomain, cmd, getChildGraphicalNodes());

        cmd.append(RemoveCommand.create(editingDomain,
                lane.eContainer(),
                lane.eContainingFeature(),
                lane));

        cmd.setLabel(Messages.Xpdl2LaneAdapter_DeleteLane_menu);
        return cmd;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param l
     */
    private void appendDeleteCommands(EditingDomain editingDomain,
            CompoundCommand cmd, List l) {
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            EObject eo = (EObject) iter.next();
            BaseProcessAdapter adapter = (BaseProcessAdapter) factory.adapt(eo,
                    ProcessWidgetConstants.ADAPTER_TYPE);
            cmd.append(adapter.getDeleteCommand(editingDomain));
        }
    }

    @Override
    public List getChildGraphicalNodes() {
        Lane lane = getLane();
        if (lane == null || lane.getId() == null || getProcess() == null) {
            return Collections.EMPTY_LIST;
        }
        String id = lane.getId();
        ArrayList result = new ArrayList();
        Process process = getEProcess();
        for (Iterator iter = process.getActivities().iterator(); iter
                .hasNext();) {
            Activity act = (Activity) iter.next();

            NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(act);
            if (tool != null) {
                if (id.equals(tool.getLaneId())) {
                    result.add(act);
                }
            }
        }
        Package pck = process.getPackage();
        for (Iterator iter = pck.getArtifacts().iterator(); iter.hasNext();) {
            Artifact art = (Artifact) iter.next();
            int aType = art.getArtifactType().getValue();
            // display only annotations and data objects, groups should be
            // displayed on the process level
            if (aType == ArtifactType.ANNOTATION
                    || aType == ArtifactType.DATA_OBJECT) {

                NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(art);
                if (tool != null) {
                    if (id.equals(tool.getLaneId())) {
                        result.add(art);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        String name = null;

        if (getLane() != null) {
            name = Xpdl2ModelUtil.getDisplayNameOrName(getLane());
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getTokenName() {
        String name = null;

        if (getLane() != null) {
            name = getLane().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getId() {
        return getLane().getId();
    }

    @Override
    public Command getAddGraphicalNodeCommand(EditingDomain editingDomain,
            Object graphicalNode, Point location) {

        if (!(graphicalNode instanceof Activity)
                && !(graphicalNode instanceof Artifact)) {
            // not possible to add non-activity based element
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand cmd = new CompoundCommand();
        if (graphicalNode instanceof GraphicalNode) {
            GraphicalNode gNode = (GraphicalNode) graphicalNode;
            NodeGraphicsInfo gn = Xpdl2ModelUtil
                    .getOrCreateNodeGraphicsInfo(gNode, editingDomain, cmd);
            Xpdl2BaseGraphicalNodeAdapter actAd =
                    (Xpdl2BaseGraphicalNodeAdapter) getAdapterFactory()
                            .adapt(gNode, ProcessWidgetConstants.ADAPTER_TYPE);
            cmd.append(actAd.getSetLocationCommand(editingDomain,
                    location,
                    actAd.getSize()));

            if (gNode instanceof Activity) {
                if (((Activity) gNode).getFlowContainer() != getProcess()) {
                    Activity act = (Activity) gNode;

                    // NOTE: Although AddCommand is clever enough to remove the
                    // activity from the original (process or activity set) list
                    // of activities, when it's added to our activity set IT'S
                    // UNDO IS NOT SMART ENOUGH - so we do an explicit delete
                    // and re-add.

                    cmd.append(RemoveCommand.create(editingDomain, gNode));

                    cmd.append(AddCommand.create(editingDomain,
                            getProcess(),
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities(),
                            gNode));

                    // Any transitions have to be moved into activity set too.
                    // (Don't need to do incoming transitions, it's been
                    // prevalidated that all transitions are between objects
                    // being moved so one objects incoming will be another
                    // object's outgoing (don't want to do twice).
                    List trans = act.getOutgoingTransitions();

                    for (Iterator t = trans.iterator(); t.hasNext();) {
                        Transition tran = (Transition) t.next();
                        cmd.append(RemoveCommand.create(editingDomain, tran));

                        cmd.append(AddCommand.create(editingDomain,
                                getProcess(),
                                Xpdl2Package.eINSTANCE
                                        .getFlowContainer_Transitions(),
                                tran));
                    }

                }
            }

            cmd.append(SetCommand.create(editingDomain,
                    gn,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                    getLane().getId()));

        } else {
            return UnexecutableCommand.INSTANCE;
        }
        return cmd;
    }

    private Artifact createArtifact(EditingDomain editingDomain, Point loc,
            Dimension size, CompoundCommand cmd, ArtifactType type,
            String fillColor, String lineColor) {

        // artifact
        Artifact art = ElementsFactory.createArtifact(loc,
                size,
                type,
                getLane().getId(),
                fillColor,
                lineColor);

        return art;
    }

    @Override
    public void notifyChanged(Notification msg) {
        if (msg.getEventType() == Notification.REMOVING_ADAPTER) {
            // unregister();
        } else {
            if (msg.getNotifier() == getLane()) {
                if (msg.getFeatureID(
                        GraphicalNode.class) == Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS) {
                    switch (msg.getEventType()) {
                    case Notification.REMOVE:
                        // ((EObject) msg.getOldValue()).eAdapters().remove(
                        // tooladapter);
                        break;
                    case Notification.REMOVE_MANY:
                        for (Iterator iter = ((Collection) msg.getOldValue())
                                .iterator(); iter.hasNext();) {
                            // EObject eo = (EObject) iter.next();
                            // eo.eAdapters().remove(tooladapter);
                        }
                        break;
                    case Notification.ADD:
                    case Notification.ADD_MANY:
                        // register();
                        break;
                    }
                }
            }
            /* Sid XPD-8302 - pass message in so can ignore adapter removal */
            fireDiagramNotification(msg);
        }
    }

    @Override
    public int getSize() {
        NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(getLane());

        if (tool != null) {
            return (int) Math.ceil(tool.getHeight());
        }
        return DEFAULT_LANE_SIZE;
    }

    @Override
    public Command getSetSizeCommand(EditingDomain editingDomain, int size) {
        int min = 0;
        List acts = getChildGraphicalNodes();
        for (Iterator iter = acts.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            if (obj instanceof GraphicalNode) {
                // Discount groups as they are allowed to extend past lane.
                if (obj instanceof Artifact) {
                    Artifact art = (Artifact) obj;

                    if (art.getArtifactType()
                            .getValue() == ArtifactType.GROUP) {
                        continue;
                    }
                } else {
                    // Ignore Events attached to border of task.
                    if (obj instanceof Activity) {
                        Event ev = ((Activity) obj).getEvent();

                        if (ev instanceof IntermediateEvent) {
                            if (((IntermediateEvent) ev).getTarget() != null) {
                                continue;
                            }
                        }
                    }
                }

                GraphicalNode act = (GraphicalNode) obj;
                NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(act);

                if (gn.getCoordinates() != null) {
                    /*
                     * XPD-6903 : Add additional padding below events/gateways
                     * so that their labels do not get cut off.
                     */
                    min = Math.max(
                            (int) Math.ceil(gn.getCoordinates().getYCoordinate()
                                    + (gn.getHeight() / 2))
                                    + LANE_MIN_CONTENT_MARGIN
                                    + getAdditionalHeightForPadding(obj),
                            min);
                }
            }
        }

        if (size >= min) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.Xpdl2LaneAdapter_ResizeLane_menu);
            NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                    .getOrCreateNodeGraphicsInfo(getLane(), editingDomain, cmd);
            cmd.append(SetCommand.create(editingDomain,
                    gInfo,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                    new Double(size)));
            return cmd;
        } else {
            // Ok, the selected size is less than minimum for content.
            // BUT is the current lane size big enough for content?
            // This acts as a 'catch all'... if it's not big enough
            // then set to minimum.
            size = getSize();

            if (size < min) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.Xpdl2LaneAdapter_ResizeLane_menu);
                NodeGraphicsInfo gInfo =
                        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(getLane(),
                                editingDomain,
                                cmd);
                cmd.append(SetCommand.create(editingDomain,
                        gInfo,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                        new Double(min)));
                return cmd;
            }

        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Gets the additional height for padding in case of events/gateways because
     * they have labels/text controls below them. Hence this method roughly
     * calculates how much size the text control below might take and returns
     * the additional height to add as padding.
     * 
     * @param obj
     * @return the additional height that should be added to the lane so that
     *         the text label below events and gateways should be visible.
     */
    private int getAdditionalHeightForPadding(Object obj) {

        int additionalPadding = 0;

        if (obj instanceof Activity) {
            /*
             * Applicable only for events and gateways.
             */
            if (((Activity) obj).getEvent() != null
                    || ((Activity) obj).getRoute() != null) {

                /*
                 * Either the display name OR name(if display name is null) will
                 * be shown in the process editor hence get it.
                 */
                String displayNameOrName =
                        Xpdl2ModelUtil.getDisplayNameOrName((Activity) obj);

                if (displayNameOrName != null && !displayNameOrName.isEmpty()) {
                    /*
                     * split the display name across empty spaces.
                     */
                    String[] splittedNameAcrossSpaces =
                            displayNameOrName.split(" "); //$NON-NLS-1$

                    /*
                     * If there is one signal name then no need for padding as
                     * it will be displayed right below the event/gateway.
                     */
                    if (splittedNameAcrossSpaces.length > 1) {

                        int chunkLength = 0;

                        for (String eachSplittedChunk : splittedNameAcrossSpaces) {
                            int length = eachSplittedChunk.length();

                            if (length > 10) {
                                /*
                                 * If the length is greated than 10 then the
                                 * signal splitted word itself is BIG then add a
                                 * padding as it might go on the next line.
                                 * Making chunkLength=11 so that the code below
                                 * immediately adds a padding.
                                 */
                                chunkLength = 11;
                            } else {
                                /*
                                 * else keep on adding the length to the chunk
                                 * size.
                                 */
                                chunkLength = chunkLength + length;
                            }

                            if (chunkLength > 10) {
                                /*
                                 * Add padding every time the chunk size goes
                                 * above 10.
                                 */
                                additionalPadding += LANE_MIN_CONTENT_MARGIN;
                                chunkLength = 0;
                            }
                        }
                    }
                }
            }
        }
        return additionalPadding;
    }

    @Override
    public Command getSetNameCommand(EditingDomain editingDomain, String name) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2LaneAdapter_SetLabelLane_menu);
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                getLane(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name));
        return cmd;
    }

    @Override
    public CreateAccessibleObjectCommand getCreateEventCommand(
            EditingDomain domain, EventFlowType flowType, EventTriggerType type,
            Point loc, Dimension size, String fillColor, String lineColor) {

        Activity act = ElementsFactory.createEvent(loc,
                size,
                getLane().getId(),
                flowType,
                type,
                fillColor,
                lineColor,
                getEProcess());

        Process proc = getEProcess();
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2LaneAdapter_AddEventLane_menu);

        NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(act);

        cmd.append(AddCommand.create(domain,
                proc,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                act));

        return new CreateAccessibleObjectCommand(cmd, act);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateGatewayCommand(
            EditingDomain domain, GatewayType gatewayType, Point loc,
            Dimension size, String fillColor, String lineColor) {

        Activity gw = ElementsFactory.createGateway(loc,
                size,
                getLane().getId(),
                gatewayType,
                fillColor,
                lineColor);

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2LaneAdapter_AddGatewayLane_menu);
        NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(gw);

        cmd.append(AddCommand.create(domain,
                getProcess(),
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                gw));

        return new CreateAccessibleObjectCommand(cmd, gw);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateTaskCommand(
            EditingDomain editingDomain, TaskType taskType, Point loc,
            Dimension size, String fillColor, String lineColor) {

        return getCreateTaskInLaneCommand(editingDomain,
                getLane(),
                taskType,
                loc,
                size,
                fillColor,
                lineColor);
    }

    /**
     * @param editingDomain
     * @param taskType
     * @param loc
     * @param size
     * @param fillColor
     * @param lineColor
     * 
     * @return Command to create a new task of given type in the given lane.
     */
    public static CreateAccessibleObjectCommand getCreateTaskInLaneCommand(
            EditingDomain editingDomain, Lane lane, TaskType taskType,
            Point loc, Dimension size, String fillColor, String lineColor) {

        Process process = Xpdl2ModelUtil.getProcess(lane);

        Activity act = ElementsFactory.createTask(loc,
                size,
                lane.getId(),
                taskType,
                fillColor,
                lineColor);

        CompoundCommand cmd = new CompoundCommand();

        // For embedded/event sub-process create activity set up-front.
        // Stops inconsistency when dealing with moving/creating sub-objects.

        /*
         * ABPM-911: Saket: An event subprocess should mostly behave like an
         * embedded sub-process.
         */
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {
            BlockActivity blockAct = act.getBlockActivity();

            ActivitySet activitySet =
                    Xpdl2Factory.eINSTANCE.createActivitySet();

            blockAct.setActivitySetId(activitySet.getId());

            cmd.append(AddCommand.create(editingDomain,
                    process,
                    Xpdl2Package.eINSTANCE.getProcess_ActivitySets(),
                    activitySet));

        } else if (TaskType.SEND_LITERAL.equals(taskType)) {
            /*
             * XPD-7721: When we drag drop send task to the lane by default its
             * implementation should be 'Unspecified' because now send tasks can
             * be configured as REST or Web-service and hence setting it to
             * unspecified makes more sense.
             */
            Implementation implementation = act.getImplementation();

            if (implementation instanceof Task) {
                TaskSend taskSend = ((Task) implementation).getTaskSend();

                cmd.append(SetCommand.create(editingDomain,
                        taskSend,
                        Xpdl2Package.eINSTANCE.getTaskSend_Implementation(),
                        ImplementationType.UNSPECIFIED_LITERAL));

                cmd.append(SetCommand.create(editingDomain,
                        taskSend,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        null));
            }

        }

        NodeGraphicsInfo tool = Xpdl2ModelUtil.getNodeGraphicsInfo(act);

        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                act));

        cmd.setLabel(Messages.Xpdl2LaneAdapter_AddObjectLane_menu
                + taskType.toString());

        return new CreateAccessibleObjectCommand(cmd, act);
    }

    @Override
    public void setTarget(Notifier newTarget) {
        if (getTarget() != null) {
            if (processLst != null) {
                processLst.getTarget().eAdapters().remove(processLst);
            }
            if (toolLst != null) {
                toolLst.getTarget().eAdapters().remove(toolLst);
            }
            if (oldToolLst != null) {
                oldToolLst.getTarget().eAdapters().remove(oldToolLst);
            }
        }
        super.setTarget(newTarget);
        if (getTarget() != null) {
            if (processLst == null) {
                processLst = new OclBasedNotifier(
                        "self.parentPool.parentPackage.getProcess(self.parentPool.processId)", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getLane(), this);

            }
            getTarget().eAdapters().add(processLst);

            if (toolLst == null) {
                // We now use NO ToolId for standard XPDL usage graphics infos
                toolLst = new OclBasedNotifier(
                        "self.getNodeGraphicsInfoForTool('')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getLane(), this);

            }
            getTarget().eAdapters().add(toolLst);

            if (oldToolLst == null) {
                // We now use NO ToolId for standard XPDL usage graphics infos
                oldToolLst = new OclBasedNotifier(
                        "self.getNodeGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getLane(), this);

            }
            getTarget().eAdapters().add(oldToolLst);

        }
    }

    @Override
    public void oclNotifyChanged(EObject base, Object target,
            Notification notification) {
        /* Sid XPD-8302 - pass message in so can ignore adapter removal */
        fireDiagramNotification(notification);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateArtifactCommand(
            EditingDomain editingDomain, Object type, Point loc, Dimension size,
            String fillColor, String lineColor) {
        CompoundCommand cmd = new CompoundCommand();

        Artifact art = null;

        if (type == GroupAdapter.class) {
            art = createArtifact(editingDomain,
                    loc,
                    size,
                    cmd,
                    ArtifactType.GROUP_LITERAL,
                    fillColor,
                    lineColor);
            cmd.setLabel(Messages.Xpdl2LaneAdapter_AddGroupLane_menu);
        } else if (type == DataObjectAdapter.class) {
            art = createArtifact(editingDomain,
                    loc,
                    size,
                    cmd,
                    ArtifactType.DATA_OBJECT_LITERAL,
                    fillColor,
                    lineColor);
            cmd.setLabel(Messages.Xpdl2LaneAdapter_AddDataObjectLane_menu);
        } else if (type == NoteAdapter.class) {
            art = createArtifact(editingDomain,
                    loc,
                    size,
                    cmd,
                    ArtifactType.ANNOTATION_LITERAL,
                    fillColor,
                    lineColor);
            cmd.setLabel(Messages.Xpdl2LaneAdapter_AddDiagramNoteLane_menu);
        }

        if (art != null) {
            Package pck = getEProcess().getPackage();
            cmd.append(AddCommand.create(editingDomain,
                    pck,
                    Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                    art));

        } else {
            cmd.append(UnexecutableCommand.INSTANCE);
        }

        return new CreateAccessibleObjectCommand(cmd, art);
    }

    @Override
    public String getFillColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getLane());
        if (gi != null) {
            return gi.getFillColor();
        }
        return null;
    }

    @Override
    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String newColor) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2LaneAdapter_SetFillColorLane_menu);
        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(getLane(), editingDomain, cmd);
        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_FillColor(),
                newColor));
        return cmd;
    }

    @Override
    public String getLineColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getLane());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    @Override
    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String newColor) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2LaneAdapter_SetLineColorLane_menu);
        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(getLane(), editingDomain, cmd);
        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_BorderColor(),
                newColor));
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.PoolAdapter#getSetIsClosedCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, boolean)
     */
    @Override
    public Command getSetIsClosedCommand(EditingDomain editingDomain,
            boolean isClosed) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode gn = getLane();

        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(gn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_IsVisible(),
                !isClosed));

        if (isClosed) {
            cmd.setLabel(Messages.Xpdl2LaneAdapter_OpenLane_menu);
        } else {
            cmd.setLabel(Messages.Xpdl2LaneAdapter_CloseLane_menu);
        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.PoolAdapter#isClosed()
     */
    @Override
    public boolean isClosed() {
        boolean closed = false;

        NodeGraphicsInfo gi = getGraphicalInfo(getLane());
        if (gi != null) {
            closed = !gi.isIsVisible();
        }

        return closed;
    }

    @Override
    public DropTypeInfo getDropTypeInfo(List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {
        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);

        Process targetProcess = Xpdl2ModelUtil.getProcess(getLane());

        /*
         * SDA-59: Don't allow any kind of drop onto decision flow process, as
         * most aren't supported. <p> If some become available then these should
         * be contributed via dropObjectContribution extension point.
         */
        if (!DecisionFlowUtil.isDecisionFlow(targetProcess)) {

            //
            // Overall, if actual target is not lane, activity or transition
            // then we
            // can't do anything.
            //
            if (actualTarget instanceof Lane || actualTarget instanceof Activity
                    || actualTarget instanceof Transition) {
                //
                // Participant / Performer Fields are effectively the same
                // thing.
                //
                if (LocalDropObjectUtils.checkAllAreValidTaskParticipants(
                        getEProcess(),
                        dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(getEProcess())) {

                    if (DiagramDropObjectUtils.checkFromSamePackage(dropObjects,
                            Xpdl2ModelUtil.getPackage(getLane()))) {

                        dropType.setDndDropType(DND.DROP_COPY);

                        dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                .getTaskFeedbackRectangles(1));
                    }
                }
                //
                // Drop of process(es) = create sequence of indi-subprocess
                // tasks.
                //
                else if (LocalDropObjectUtils.checkAllAreValidProcesses(
                        getEProcess(),
                        dropObjects)) {

                    /*
                     * XPD-7516: Saket: The code placed here initially was
                     * preventing a process interface being dropped on a process
                     * which is now a valid use case.
                     */

                    dropType.setDndDropType(DND.DROP_COPY);

                    dropType.setFeedbackRectangles(DiagramDropObjectUtils
                            .getTaskFeedbackRectangles(dropObjects.size()));

                } else if (LocalDropObjectUtils.checkAllAreProcessRelevantData(
                        dropObjects,
                        targetProcess)) {
                    dropType.setDndDropType(DND.DROP_COPY);

                    dropType.setFeedbackRectangles(DiagramDropObjectUtils
                            .getTaskFeedbackRectangles(1));

                }
                //
                // Drop Task from task library onto business process / pageflow
                // process - create reference task.
                //
                else if (LocalDropObjectUtils
                        .checkAllAreLibraryTasks(dropObjects, targetProcess)) {
                    if (!ProcessWidgetType.TASK_LIBRARY
                            .equals(getProcessType())) {
                        dropType.setDndDropType(DND.DROP_COPY);

                        dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                .getTaskFeedbackRectangles(dropObjects.size()));
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

        Process targetProcess = Xpdl2ModelUtil.getProcess(getLane());

        /*
         * SDA-59: Don't allow any kind of drop onto decision flow process, as
         * most aren't supported. <p> If some become available then these should
         * be contributed via dropObjectContribution extension point.
         */
        if (!DecisionFlowUtil.isDecisionFlow(targetProcess)) {

            //
            // Overall, if actual target is not lane, activity or transition
            // then we
            // can't do anything.
            //
            if (actualTarget instanceof Lane || actualTarget instanceof Activity
                    || actualTarget instanceof Transition) {

                List<DropObjectPopupItem> myPopupItems = null;

                //
                // Drop participant or performer data field/formalparam on lane
                // = create task with given participant.
                //
                if (LocalDropObjectUtils.checkAllAreValidTaskParticipants(
                        getEProcess(),
                        dropObjects)
                        && !Xpdl2ModelUtil.isPageflow(getEProcess())) {
                    myPopupItems = LocalDropObjectUtils
                            .getCreateTaskFromParticDropPopupItems(
                                    getLane().getId(),
                                    dropObjects,
                                    location,
                                    actualTarget);

                }
                //
                // Drop Process(es) = create Independent Sub-Process Task(s)
                //
                else if (LocalDropObjectUtils.checkAllAreValidProcesses(
                        getEProcess(),
                        dropObjects)) {
                    myPopupItems = LocalDropObjectUtils
                            .getCreateTaskFromProcessDropPopupItems(
                                    getEProcess(),
                                    getLane().getId(),
                                    dropObjects,
                                    location,
                                    Xpdl2ModelUtil.getPackage(getLane()),
                                    actualTarget);
                } else if (LocalDropObjectUtils.checkAllAreProcessRelevantData(
                        dropObjects,
                        targetProcess)) {
                    myPopupItems = LocalDropObjectUtils
                            .getCreateTaskFromFieldsDropPopupItems(
                                    getEProcess(),
                                    getLane().getId(),
                                    dropObjects,
                                    location);

                }
                //
                // Drop Task from task library onto business process / pageflow
                // process - create reference task.
                //
                else if (LocalDropObjectUtils
                        .checkAllAreLibraryTasks(dropObjects, targetProcess)) {
                    if (!ProcessWidgetType.TASK_LIBRARY
                            .equals(getProcessType())) {
                        myPopupItems = LocalDropObjectUtils
                                .getCreateLibraryTaskReferenceDropPopupItems(
                                        getEProcess(),
                                        getLane().getId(),
                                        dropObjects,
                                        location,
                                        Xpdl2ModelUtil.getPackage(getLane()),
                                        actualTarget);
                    }
                }

                //
                // If we have any drop popup items then add to return list.
                if (myPopupItems != null && myPopupItems.size() > 0) {
                    returnPopupItems.addAll(myPopupItems);
                }
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
                getLane(),
                connectionSourceObject);
    }

}
