/*
 * Copyright 2005 TIBCO Software Inc. 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SelectionRequest;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessContainerAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.commands.EnsureUniqueNameCommand;
import com.tibco.xpd.processwidget.commands.internal.EmbSubProcOptimiseSizeCommand;
import com.tibco.xpd.processwidget.commands.internal.MakeSpaceInParentCommand;
import com.tibco.xpd.processwidget.commands.internal.ResizeLaneIfNeededCommand;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.GroupEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.NoteEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Layout cor parts that can contain process - i.e. LaneEditPart or
 * ActivityEditPart (for subprocess)
 * 
 * @author wzurek
 */
public class FlowContainerXYLayoutEditPolicy extends XYLayoutEditPolicy {
    private static boolean BOGEY = false;

    private final AdapterFactory adapterFactory;

    private final EditingDomain editingDomain;

    private static boolean ENFORCE_TASK_MINIMUM_SIZE = true;

    public static final String EVENT_ON_BORDER = "EVENT_ON_BORDER"; //$NON-NLS-1$

    private TaskFigure lastEventOnBorderOfTask = null;

    private List actualMovingChildEditParts = Collections.EMPTY_LIST;

    private List actualAddingChildEditParts = Collections.EMPTY_LIST;

    private List ignoreMovingPartsForMakeSpace = Collections.EMPTY_LIST;

    private Request currentAddRequest = null;

    /**
     * The Constructor
     * 
     * @param adapterFactory
     * @param editingDomain
     */
    public FlowContainerXYLayoutEditPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    /**
     * Child edit policy, Gateways, Data Objects and Events are non-resizable,
     * tasks are default = resizable
     */
    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        if (child instanceof GatewayEditPart || child instanceof EventEditPart
        /* || child instanceof DataObjectEditPart */) {
            return new NonResizableEditPolicy();
        }
        return super.createChildEditPolicy(child);
    }

    /**
     * Add graphical node that already exist (from different lane for example)
     */
    @Override
    protected Command createAddCommand(EditPart child, Object constraint) {

        if (getHost() instanceof LaneEditPart) {
            LaneEditPart h = (LaneEditPart) getHost();
            if (h.isClosed()) {
                return UnexecutableCommand.INSTANCE;
            }
        } else if (getHost() instanceof TaskEditPart) {

            // Check if this is an add of an event to border of task.
            Command evOnBorderCmd =
                    checkAddEventToTaskBorder(child, constraint);

            if (evOnBorderCmd != null) {
                return evOnBorderCmd;
            }

            // Creating embedded sub-procs by creating objects in was just
            // causing too many problems
            // so only allow this for tasks that are ALREADY embedded sub-procs.

            if (!((TaskEditPart) getHost()).isEmbeddedSubProc()) {
                return UnexecutableCommand.INSTANCE;
            }
        }

        Object model = child.getModel();
        if (!(model instanceof EObject)) {
            return UnexecutableCommand.INSTANCE;
        }
        Rectangle rectangle = ((Rectangle) constraint).getCopy();
        if (rectangle.x < 0 || rectangle.y < 0) {
            return UnexecutableCommand.INSTANCE;
        }
        if (child instanceof BaseGraphicalEditPart) {
            rectangle =
                    (Rectangle) ((BaseGraphicalEditPart) child)
                            .translateToModel(rectangle);
        }
        if (rectangle.x < 0 || rectangle.y < 0) {
            return UnexecutableCommand.INSTANCE;
        }

        Object container = getHost().getModel();
        ProcessContainerAdapter containerAd =
                (ProcessContainerAdapter) adapterFactory.adapt(container,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        CompoundCommand cmd = new CompoundCommand();

        //
        // If adding previously attached event that is becoming detached then
        // unset attachment. This will happen when attached event is dragged off
        // of border of task into content of a different Embedded Sub-Proc /
        // Lane.
        if (child instanceof EventEditPart) {
            EventEditPart eventEP = (EventEditPart) child;

            TaskEditPart taskEP = eventEP.getBorderAttachmentTask();

            if (taskEP != null) {
                if (!actualAddingChildEditParts.contains(taskEP)) {
                    // We're not also adding the task that event is attached to
                    // so we need to detach it from original task.
                    cmd.append(eventEP.getEventAdapter()
                            .getSetTaskBorderAttachmentCommand(editingDomain,
                                    null,
                                    new Double(0)));
                }
            }
        }

        cmd.append(containerAd.getAddGraphicalNodeCommand(editingDomain,
                model,
                rectangle.getLocation()));

        // resize parent if necessary
        if (child instanceof BaseGraphicalEditPart) {
            cmd.append(getMakeSpaceForObjectCommand((BaseGraphicalEditPart) child,
                    actualAddingChildEditParts));
        }

        return new EMFCommandWrapper(editingDomain, cmd.unwrap());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getAddCommand
     * (org.eclipse.gef.Request)
     */
    @Override
    protected Command getAddCommand(Request generic) {
        // Add any events that are attached to the border of tasks to the add
        // list.

        ChangeBoundsRequest request = (ChangeBoundsRequest) generic;
        List editParts = request.getEditParts();

        actualAddingChildEditParts = new ArrayList();
        actualAddingChildEditParts.addAll(editParts);

        EditPartUtil.addAttachedEventsToEditParts(actualAddingChildEditParts);

        request.setEditParts(actualAddingChildEditParts);

        currentAddRequest = request;

        Command cmd = super.getAddCommand(request);

        currentAddRequest = null;
        actualAddingChildEditParts = Collections.EMPTY_LIST;

        request.setEditParts(editParts);

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
     * getMoveChildrenCommand(org.eclipse.gef.Request)
     */
    @Override
    protected Command getMoveChildrenCommand(Request req) {
        // Add any events that are attached to the border of tasks to the add
        // list.
        ChangeBoundsRequest request = (ChangeBoundsRequest) req;
        List editParts = request.getEditParts();

        actualMovingChildEditParts = new ArrayList();
        actualMovingChildEditParts.addAll(editParts);

        // When moving multiple SELECTED objects (for instance a task AND an
        // event that is attached to it) GEF gives a bunch of separate move
        // commands.
        // But we rely on being able to tell whether an attached event is being
        // moved on it's own or not. Just below here we make sure we pick up
        // attached events to tasks that are moving, so here it is safe to
        // ignore any selected attached event IF the task whose border it is
        // attached to is also selected (we'll catch moving the event as part of
        // the task).
        if (editParts.size() == 1 && editParts.get(0) instanceof EventEditPart) {

            EventEditPart eventEP = (EventEditPart) editParts.get(0);

            if (eventEP instanceof EventEditPart) {
                TaskEditPart taskEP = (eventEP).getBorderAttachmentTask();

                if (getHost().getViewer().getSelectedEditParts()
                        .contains(taskEP)) {
                    return null;
                }
            }
        }

        EditPartUtil.addAttachedEventsToEditParts(actualMovingChildEditParts);

        ignoreMovingPartsForMakeSpace = new ArrayList();
        ignoreMovingPartsForMakeSpace.addAll(getHost().getViewer()
                .getSelectedEditParts());
        EditPartUtil
                .addAttachedEventsToEditParts(ignoreMovingPartsForMakeSpace);

        request.setEditParts(actualMovingChildEditParts);

        Command cmd = super.getMoveChildrenCommand(request);

        ignoreMovingPartsForMakeSpace = Collections.EMPTY_LIST;

        actualMovingChildEditParts = Collections.EMPTY_LIST;

        request.setEditParts(editParts);

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
     * getResizeChildrenCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    @Override
    protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {

        // When resizing tasks we have to make sure that we ignore any attached
        // events.
        ignoreMovingPartsForMakeSpace = new ArrayList();
        ignoreMovingPartsForMakeSpace.addAll(getHost().getViewer()
                .getSelectedEditParts());
        EditPartUtil
                .addAttachedEventsToEditParts(ignoreMovingPartsForMakeSpace);

        Command cmd = super.getResizeChildrenCommand(request);

        ignoreMovingPartsForMakeSpace = Collections.EMPTY_LIST;

        return cmd;
    }

    /**
     * Move graphical node.
     */
    @Override
    protected Command createChangeConstraintCommand(EditPart child,
            Object constraint) {

        if (getHost() instanceof LaneEditPart) {
            LaneEditPart h = (LaneEditPart) getHost();
            if (h.isClosed()) {
                return UnexecutableCommand.INSTANCE;
            }
        }

        Object model = child.getModel();

        Rectangle rectangle = ((Rectangle) constraint);

        if (rectangle.x < 0 || rectangle.y < 0) {
            return UnexecutableCommand.INSTANCE;
        }

        if (child instanceof BaseGraphicalEditPart) {
            rectangle =
                    (Rectangle) ((BaseGraphicalEditPart) child)
                            .translateToModel(rectangle);
        }
        BaseGraphicalNodeAdapter ad =
                (BaseGraphicalNodeAdapter) adapterFactory.adapt(model,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        if (rectangle.x < 0 || rectangle.y < 0) {
            return UnexecutableCommand.INSTANCE;
        }

        if (ENFORCE_TASK_MINIMUM_SIZE) {
            // Disallow resize of embedded sub-process smaller than minimum for
            // content.
            if (child instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) child;

                if (taskEP.isEmbeddedSubProc()) {
                    TaskAdapter ta = taskEP.getActivityAdapter();
                    if (!ta.isEmbeddedSubProcessCollapsed()) {
                        Dimension curSize = ad.getSize();

                        Dimension newSize = rectangle.getSize();

                        if (!newSize.equals(curSize)) {
                            Dimension minSize =
                                    taskEP.getMinimumSizeForContent();

                            if (newSize.width < minSize.width
                                    || newSize.height < minSize.height) {
                                return UnexecutableCommand.INSTANCE;
                            }
                        }
                    }
                }
            }
        }

        CompoundCommand result = new CompoundCommand();

        //
        // When moving event attached to border of task into the parent lane /
        // embedded sub-proc of the task it is
        // effectively a 'change constraint' because the event is actually the
        // child of lane whether or not it is attached to task border.
        //
        // So if this an attached event move and the task it's attached to is
        // not moving, then the command is different (detach it from task).
        if (child instanceof EventEditPart) {
            TaskEditPart taskEP =
                    ((EventEditPart) child).getBorderAttachmentTask();

            if (taskEP != null && !actualMovingChildEditParts.contains(taskEP)) {
                // Border task not also selected, detach the event.
                result.append(((EventEditPart) child).getEventAdapter()
                        .getSetTaskBorderAttachmentCommand(editingDomain,
                                null,
                                new Double(0)));
            }

        }

        result.append(ad.getSetLocationCommand(editingDomain,
                rectangle.getLocation(),
                rectangle.getSize()));
        result.setLabel(Messages.FlowContainerXYLayoutEditPolicy_MoveSizeObject_menu);

        //
        // Auto resize / re-jig parent on move / size child of task (don't do it
        // for note's)
        //
        if (child instanceof BaseGraphicalEditPart) {
            result.append(getMakeSpaceForObjectCommand((BaseGraphicalEditPart) child,
                    ignoreMovingPartsForMakeSpace));
        }

        return new EMFCommandWrapper(editingDomain, result);

    }

    /**
     * Return "Create new object" command and return it's Id (if it's a flow
     * node object.
     */
    public org.eclipse.emf.common.command.Command getEMFCreateCommand(
            CreateRequest req) {
        org.eclipse.emf.common.command.Command errorRet = null;

        if (getHost() instanceof LaneEditPart) {
            LaneEditPart h = (LaneEditPart) getHost();
            if (h.isClosed()) {
                return errorRet;
            }
        } else if (getHost() instanceof TaskEditPart) {

            org.eclipse.emf.common.command.Command eventOnBorderCmd =
                    checkCreateEventOnTaskBorder(req);

            if (eventOnBorderCmd != null) {
                return eventOnBorderCmd;
            }

            // Creating embedded sub-procs by creating objects in was just
            // causing too many problems
            // so only allow this for tasks that are ALREADY embedded sub-procs.
            if (!((TaskEditPart) getHost()).isEmbeddedSubProc()) {
                return null;
            }
        }

        Object type = req.getNewObjectType();

        Object parent = getHost().getModel();
        ProcessContainerAdapter containerAdp =
                (ProcessContainerAdapter) adapterFactory.adapt(parent,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        Point loc = req.getLocation().getCopy();

        ((GraphicalEditPart) getHost()).getContentPane()
                .translateToRelative(loc);

        Point org = getLayoutOrigin().getNegated();
        loc.translate(org);

        if (loc.x < 0 || loc.y < 0) {
            return errorRet;
        }

        if (getHost() instanceof BaseFlowNodeEditPart) {
            loc =
                    (Point) ((BaseFlowNodeEditPart) getHost())
                            .translateToModel(loc);
        }

        if (loc.x < 0 || loc.y < 0 || req.getSize() == null) {
            return errorRet;
        }

        // SID - If there is a size in the request then location is top left.
        // But the command should be centre location, so adjust it.
        Dimension size = null;

        if (req.getSize() != null) {
            size = req.getSize().getCopy();
            ((GraphicalEditPart) getHost()).getContentPane()
                    .translateToRelative(size);
            loc.x += size.width / 2;
            loc.y += size.height / 2;
        }
        org.eclipse.emf.common.command.Command cmd = null;

        //
        // Preprepare a make space command for new object.
        MakeSpaceInParentCommand makeSpaceCmd = null;

        if (type != NoteAdapter.class && size != null) {
            // make space for the area to be occupied by the new object.
            Rectangle spaceBounds =
                    new Rectangle(loc.x - (size.width / 2), loc.y
                            - (size.height / 2), size.width, size.height);

            makeSpaceCmd =
                    new MakeSpaceInParentCommand(
                            editingDomain,
                            spaceBounds,
                            (BaseGraphicalEditPart) getHost(),
                            0,
                            MakeSpaceInParentCommand.DEFAULT_EXTRA_OVERLAY_MARGIN,
                            Collections.EMPTY_LIST);

            // Making space may mean shitfting new object a little.
            Dimension spaceOffset = makeSpaceCmd.getSpaceBoundsOffset();

            loc.x += spaceOffset.width;
            loc.y += spaceOffset.height;

        }

        ProcessWidgetColors colors =
                ProcessWidgetColors.getInstance(containerAdp);

        // Get default fill / line colour id for object from tool-added extended
        // data
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(null,
                        (String) req
                                .getExtendedData()
                                .get(PaletteFactory.EXTDATA_DEFAULT_FILL_COLORID));
        WidgetRGB lineColor =
                colors.getGraphicalNodeColor(null,
                        (String) req
                                .getExtendedData()
                                .get(PaletteFactory.EXTDATA_DEFAULT_LINE_COLORID));

        CompoundCommand ccmd = new CompoundCommand();
        CreateAccessibleObjectCommand accessibleObjectCommand = null;

        if (EventAdapter.class == type) {
            EventFlowType flowType =
                    (EventFlowType) req.getExtendedData()
                            .get(PaletteFactory.EXTDATA_EVENT_FLOW_TYPE);
            EventTriggerType eventType =
                    (EventTriggerType) req.getExtendedData()
                            .get(PaletteFactory.EXTDATA_EVENT_TYPE);

            accessibleObjectCommand =
                    containerAdp.getCreateEventCommand(editingDomain,
                            flowType,
                            eventType,
                            loc,
                            size,
                            fillColor.toString(),
                            lineColor.toString());
            ccmd.append(accessibleObjectCommand);

            // Make sure that the new task name is different from existing
            // ones.
            ProcessEditPart processEP =
                    ((BaseGraphicalEditPart) getHost()).getParentProcess();

            ProcessDiagramAdapter processAdp =
                    (ProcessDiagramAdapter) processEP.getModelAdapter();

            List<NamedElementAdapter> tasks = processAdp.getActivityList(0 /*
                                                                            * Unique
                                                                            * in
                                                                            * all
                                                                            * activities
                                                                            */);

            ccmd.append(new EnsureUniqueNameCommand(editingDomain,
                    adapterFactory, accessibleObjectCommand.getCreatedNode(),
                    tasks));

            ccmd.setLabel(Messages.FlowContainerXYLayoutEditPolicy_AddObjectPrefix_menu
                    + " " //$NON-NLS-1$
                    + flowType.toString() + " " //$NON-NLS-1$
                    + eventType.toString());

        } else if (type == GatewayAdapter.class) {
            GatewayType gatewayType =
                    (GatewayType) req.getExtendedData()
                            .get(PaletteFactory.EXTDATA_GATEWAY_TYPE);
            accessibleObjectCommand =
                    containerAdp.getCreateGatewayCommand(editingDomain,
                            gatewayType,
                            loc,
                            size,
                            fillColor.toString(),
                            lineColor.toString());
            ccmd.append(accessibleObjectCommand);

        } else if (type == DataObjectAdapter.class || type == NoteAdapter.class) {
            accessibleObjectCommand =
                    containerAdp.getCreateArtifactCommand(editingDomain,
                            type,
                            loc,
                            size,
                            fillColor != null ? fillColor.toString() : null,
                            lineColor.toString());
            ccmd.append(accessibleObjectCommand);

        } else {
            if (type == TaskAdapter.class) {
                TaskType taskType =
                        (TaskType) req.getExtendedData()
                                .get(PaletteFactory.EXTDATA_TASK_TYPE);

                accessibleObjectCommand =
                        containerAdp.getCreateTaskCommand(editingDomain,
                                taskType,
                                loc,
                                size,
                                fillColor.toString(),
                                lineColor.toString());

                ccmd.append(accessibleObjectCommand);

                // Make sure that the new task name is different from existing
                // ones.
                ProcessEditPart processEP =
                        ((BaseGraphicalEditPart) getHost()).getParentProcess();

                ProcessDiagramAdapter processAdp =
                        (ProcessDiagramAdapter) processEP.getModelAdapter();

                List<NamedElementAdapter> tasks =
                        processAdp
                                .getActivityList(0 /* Unique in all activities */);

                ccmd.append(new EnsureUniqueNameCommand(editingDomain,
                        adapterFactory, accessibleObjectCommand
                                .getCreatedNode(), taskType.toString(), tasks));
                ccmd.setLabel(Messages.FlowContainerXYLayoutEditPolicy_AddObjectPrefix_menu
                        + " " + taskType.toString()); //$NON-NLS-1$

            }
        }

        if (accessibleObjectCommand != null) {
            Object newObject = accessibleObjectCommand.getCreatedNode();

            // If we created a make space command then add it.
            if (makeSpaceCmd != null) {

                List ignoreObjects = new ArrayList();
                ignoreObjects.add(newObject);

                makeSpaceCmd.setIgnoredSiblings(ignoreObjects);

                ccmd.append(makeSpaceCmd);

            } else {
                // For text annotations don't make space, just make parent big
                // enough.
                ccmd.append(getResizeParentIfNeededCommand());
            }

            // Wrap the whole thing in accessible objkect to grant access to
            // the created object to the caller
            CreateAccessibleObjectCommand accessibleCompound =
                    new CreateAccessibleObjectCommand(ccmd,
                            accessibleObjectCommand.getCreatedNode());
            cmd = accessibleCompound;

            req.getExtendedData()
                    .put(PaletteFactory.EXTDATA_CREATED_MODEL_OBJECT, newObject);

            return cmd;
        }

        return null;
    }

    /**
     * Check if the given create request is for an intermediate event on the
     * border of a task. If so, return the command that creates this.
     * 
     * @param req
     * @return
     */
    private org.eclipse.emf.common.command.Command checkCreateEventOnTaskBorder(
            CreateRequest req) {
        if (getHost() instanceof TaskEditPart) {
            TaskEditPart taskEP = (TaskEditPart) getHost();

            Object type = req.getNewObjectType();

            if (type == EventAdapter.class) {
                EventFlowType flowType =
                        (EventFlowType) req.getExtendedData()
                                .get(PaletteFactory.EXTDATA_EVENT_FLOW_TYPE);

                if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(flowType)) {
                    // Ok, it's an intermediate event that can be attached to
                    // border.
                    TaskFigure taskFigure = (TaskFigure) taskEP.getFigure();

                    Point loc = req.getLocation().getCopy();

                    taskFigure.getParent().translateToRelative(loc);

                    // SID - If there is a size in the request then location is
                    // top left.
                    // But the command should be centre location, so adjust it.
                    Dimension size = null;

                    if (req.getSize() != null) {
                        size = req.getSize().getCopy();
                        taskFigure.getParent().translateToRelative(size);
                        loc.x += size.width / 2;
                        loc.y += size.height / 2;
                    }

                    taskFigure.getParent().translateToAbsolute(loc);

                    // Check if we're in the region of the border.
                    Point rel = loc.getCopy();
                    taskFigure.getParent().translateToRelative(rel);

                    Rectangle newRC =
                            new Rectangle(rel.x - (size.width / 2), rel.y
                                    - (size.height / 2), size.width,
                                    size.height);

                    if (taskFigure.getSelectionBounds().intersects(newRC)
                            && !taskFigure.getHandleBounds().contains(newRC)) {
                        // DEFINITELY a create event on border of task.
                        // Calculate the percent portion around the border that
                        // the closest point to location is.
                        PointList pts = taskFigure.getLineAnchorLinePoints();

                        double portion =
                                XPDLineUtilities.getLinePortionFromPoint(pts,
                                        loc);

                        // And get the creation command.
                        TaskAdapter ta = taskEP.getActivityAdapter();

                        EventTriggerType eventType =
                                (EventTriggerType) req.getExtendedData()
                                        .get(PaletteFactory.EXTDATA_EVENT_TYPE);

                        ProcessWidgetColors colors =
                                ProcessWidgetColors.getInstance(ta);

                        WidgetRGB fillColor =
                                colors.getGraphicalNodeColor(null,
                                        (String) req
                                                .getExtendedData()
                                                .get(PaletteFactory.EXTDATA_DEFAULT_FILL_COLORID));
                        WidgetRGB lineColor =
                                colors.getGraphicalNodeColor(null,
                                        (String) req
                                                .getExtendedData()
                                                .get(PaletteFactory.EXTDATA_DEFAULT_LINE_COLORID));

                        CompoundCommand cmd = new CompoundCommand();

                        CreateAccessibleObjectCommand accessCmd =
                                ta.getCreateEventOnBorderCommand(editingDomain,
                                        eventType,
                                        new Double(portion),
                                        size,
                                        fillColor.toString(),
                                        lineColor.toString());
                        cmd.append(accessCmd);
                        cmd.setLabel(Messages.FlowContainerXYLayoutEditPolicy_AddEventToBorder_menu);

                        if (taskEP.getParent() instanceof TaskEditPart) {
                            cmd.append(new EmbSubProcOptimiseSizeCommand(
                                    (TaskEditPart) taskEP.getParent(), false,
                                    false));
                        }

                        else if (getHost().getParent() instanceof LaneEditPart) {
                            cmd.append(new ResizeLaneIfNeededCommand(
                                    editingDomain, (LaneEditPart) taskEP
                                            .getParent()));
                        }

                        // Reset location in request.
                        Point newLoc =
                                XPDLineUtilities.getLinePointFromPortion(pts,
                                        portion);
                        if (size != null) {
                            taskFigure.getParent().translateToAbsolute(size);

                            newLoc.x -= size.width / 2;
                            newLoc.y -= size.height / 2;
                        }
                        req.setLocation(newLoc);

                        req.getExtendedData().put(EVENT_ON_BORDER, taskFigure);

                        return new CreateAccessibleObjectCommand(cmd,
                                accessCmd.getCreatedNode());

                    }

                }

            }

        }
        return null;
    }

    /**
     * Check if this is an event being mopved onto the border of a task on it's
     * own (i.e. without the original task it was attached to).
     * 
     * If so return the command to handle that.
     * 
     * @param child
     * @param constraint
     * @return
     */
    private Command checkAddEventToTaskBorder(EditPart child, Object constraint) {
        if (getHost() instanceof TaskEditPart) {
            TaskEditPart taskEP = (TaskEditPart) getHost();

            if (child instanceof EventEditPart
                    && actualAddingChildEditParts.size() == 1) {
                EventEditPart eventEP = (EventEditPart) child;

                EventAdapter eventAdp =
                        (EventAdapter) eventEP.getModelAdapter();

                TaskFigure taskFigure = (TaskFigure) taskEP.getFigure();

                Rectangle rectangle = ((Rectangle) constraint).getCopy();

                Point loc = rectangle.getCenter();

                Dimension size = rectangle.getSize();

                Rectangle taskBnds = taskFigure.getBounds().getCopy();
                loc.x += taskBnds.x;
                loc.y += taskBnds.y;

                if (taskEP.isEmbeddedSubProc()) {
                    // For emb sub-proc constraint is given as if in content.
                    Rectangle cntRC =
                            taskFigure.getContentPane().getBounds().getCopy();

                    loc.x += cntRC.x;
                    loc.y += cntRC.y;

                }

                Rectangle newRC =
                        new Rectangle(loc.x - (size.width / 2), loc.y
                                - (size.height / 2), size.width, size.height);

                if (taskFigure.getSelectionBounds().intersects(newRC)
                        && !taskFigure.getHandleBounds().contains(newRC)) {
                    // It's on the task boundary - set the attachment to the
                    // task.

                    // But first make sure that we're not going to cause seq
                    // flow to cross embedded sub-proc etc
                    List seqFlows = eventEP.getSourceConnections();
                    boolean seqCrossBoundary = false;

                    for (Iterator iter = seqFlows.iterator(); iter.hasNext();) {
                        BaseConnectionEditPart conn =
                                (BaseConnectionEditPart) iter.next();

                        if (conn instanceof SequenceFlowEditPart) {
                            BaseGraphicalEditPart tgt =
                                    (BaseGraphicalEditPart) conn.getTarget();

                            if (!actualAddingChildEditParts.contains(tgt)) {
                                // We're moving the event but not the other end
                                // of connection. Only allow this if the parent
                                // of the task to attach to and the target of
                                // connection are the same.
                                if (taskEP.getParent() != tgt.getParent()) {
                                    seqCrossBoundary = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (seqCrossBoundary) {
                        String badMove =
                                Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;

                        ((WidgetRootEditPart) taskEP.getRoot())
                                .setErrorTipHelperText(taskFigure, badMove);

                        // Set flag on request to tell our parent (task or lane)
                        // that we've already
                        // said 'no can do' so don't return yourself as valid
                        // target edit part.
                        currentAddRequest
                                .getExtendedData()
                                .put(BaseGraphicalEditPart.XPD_CHILD_INVALIDATED_REQUEST,
                                        new Boolean(true));

                    } else {
                        ((WidgetRootEditPart) taskEP.getRoot())
                                .clearErrorTipHelper();

                        PointList pts = taskFigure.getLineAnchorLinePoints();

                        taskFigure.getParent().translateToAbsolute(loc);
                        double portion =
                                XPDLineUtilities.getLinePortionFromPoint(pts,
                                        loc);

                        TaskAdapter ta = (TaskAdapter) taskEP.getModelAdapter();

                        currentAddRequest.getExtendedData()
                                .put(EVENT_ON_BORDER, taskFigure);
                        showTargetFeedback(currentAddRequest);

                        return new EMFCommandWrapper(
                                editingDomain,
                                eventAdp.getSetTaskBorderAttachmentCommand(editingDomain,
                                        ta.getId(),
                                        new Double(portion)));
                    }
                }

            }
        }
        return null;
    }

    /**
     */
    private org.eclipse.emf.common.command.Command getMakeSpaceForObjectCommand(
            BaseGraphicalEditPart existingObject, List ignoreSiblings) {
        org.eclipse.emf.common.command.Command cmd =
                org.eclipse.emf.common.command.UnexecutableCommand.INSTANCE;

        // For everything except Text Annotations then make space for the
        // placed/sized object.
        if (!(existingObject instanceof NoteEditPart)) {

            cmd =
                    new MakeSpaceInParentCommand(
                            editingDomain,
                            existingObject,
                            (BaseGraphicalEditPart) getHost(),
                            0,
                            MakeSpaceInParentCommand.DEFAULT_EXTRA_OVERLAY_MARGIN,
                            ignoreSiblings);
        } else {
            cmd = getResizeParentIfNeededCommand();
        }

        return cmd;
    }

    /**
     * @param cmd
     * @return
     */
    private org.eclipse.emf.common.command.Command getResizeParentIfNeededCommand() {
        org.eclipse.emf.common.command.Command cmd =
                org.eclipse.emf.common.command.UnexecutableCommand.INSTANCE;

        if (getHost() instanceof TaskEditPart) {
            cmd =
                    new EmbSubProcOptimiseSizeCommand((TaskEditPart) getHost(),
                            false, false);
        }

        else if (getHost() instanceof LaneEditPart) {
            cmd =
                    new ResizeLaneIfNeededCommand(editingDomain,
                            (LaneEditPart) getHost());
        }
        return cmd;
    }

    @Override
    public Command getCreateCommand(CreateRequest req) {
        req.getExtendedData().remove(EVENT_ON_BORDER);

        return new EMFCommandWrapper(editingDomain, getEMFCreateCommand(req));
    }

    /**
     * Redirect create of lane to the parent
     */
    @Override
    public EditPart getTargetEditPart(Request request) {
        if (REQ_ADD.equals(request.getType())
                || REQ_MOVE.equals(request.getType())
                || REQ_CREATE.equals(request.getType())
                || REQ_CLONE.equals(request.getType())) {

            // forward creation/addition of Group to Process Edit Part
            // TODO WZ move this to edit policy
            if (REQ_ADD.equals(request.getType())) {

                ChangeBoundsRequest cbr = (ChangeBoundsRequest) request;
                List parts = cbr.getEditParts();
                if (parts != null) {
                    boolean group = false;
                    for (Iterator iter = parts.iterator(); iter.hasNext();) {
                        Object ep = iter.next();
                        if (ep instanceof GroupEditPart) {
                            group = true;
                        }
                        if (group) {
                            EditPart part = getHost();
                            while (!(part instanceof ProcessEditPart)
                                    && part != null) {
                                part = part.getParent();
                            }
                            if (part != null) {
                                return part;
                            }
                        }
                    }
                }
            } else if (request instanceof CreateRequest) {
                CreateRequest req = (CreateRequest) request;
                if (req.getNewObjectType() == LaneAdapter.class) {
                    return getHost().getParent();
                } else if (req.getNewObjectType() == GroupAdapter.class) {
                    EditPart part = getHost();
                    while (!(part instanceof ProcessEditPart) && part != null) {
                        part = part.getParent();
                    }
                    if (part != null) {
                        return part;
                    }
                }
            }

        }
        return super.getTargetEditPart(request);
    }

    /**
     * do nothing
     */
    @Override
    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }

    /**
     * Have to override getXYLayout() because we ONLY have an XYLayout when we
     * are an embedded subflow, but tasks aren't always.
     */
    @Override
    protected XYLayout getXYLayout() {
        IFigure container = getLayoutContainer();
        AbstractLayout xyl = (AbstractLayout) container.getLayoutManager();

        if (xyl instanceof XYLayout) {
            return (XYLayout) xyl;
        }

        return null;
    }

    /**
     * Have to override getLayoutOrigin() because we ONLY have an XYLayout when
     * we are an embedded subflow, but tasks aren't always.
     */
    @Override
    protected Point getLayoutOrigin() {
        XYLayout xyl = getXYLayout();
        if (xyl != null) {
            return xyl.getOrigin(getLayoutContainer());
        }
        return (new Point(0, 0));
    }

    /**
     * Override showTargetFeedback() so that we can store the last position that
     * the flow container was clicked on (right or left). Very handy for
     * location-sensitive pop-up menu items like paste.
     */
    @Override
    public void showTargetFeedback(Request request) {
        if (REQ_SELECTION.equals(request.getType())) {
            GraphicalEditPart gep = (GraphicalEditPart) getHost();

            if (request instanceof SelectionRequest) {
                SelectionRequest req = (SelectionRequest) request;
                setLastClickPos(gep, req);
            }
        }

        TaskFigure evOnBorder =
                (TaskFigure) request.getExtendedData().get(EVENT_ON_BORDER);

        if (evOnBorder != lastEventOnBorderOfTask) {
            if (lastEventOnBorderOfTask != null) {
                lastEventOnBorderOfTask.setHighlight(false);
                lastEventOnBorderOfTask = null;
            }

            if (evOnBorder != null) {

                lastEventOnBorderOfTask = evOnBorder;
                lastEventOnBorderOfTask.setHighlight(true);

            }
        }

        super.showTargetFeedback(request);
    }

    /**
     * @param gep
     * @param req
     */
    public static void setLastClickPos(GraphicalEditPart gep,
            SelectionRequest req) {
        Point loc = req.getLocation().getCopy();
        IFigure cnt = gep.getContentPane();

        cnt.translateToRelative(loc);

        if (cnt.getLayoutManager() instanceof XYLayout) {
            XYLayout lay = (XYLayout) cnt.getLayoutManager();
            loc.translate(lay.getOrigin(cnt).getCopy().negate());

            gep.getViewer()
                    .setProperty(ProcessWidgetConstants.VIEWPROP_FLOWCONTAINER_LASTCLICKPOS,
                            loc);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#deactivate()
     */
    @Override
    public void deactivate() {
        super.deactivate();

        if (lastEventOnBorderOfTask != null) {
            lastEventOnBorderOfTask.setHighlight(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.AbstractEditPolicy#showSourceFeedback(org
     * .eclipse.gef.Request)
     */
    @Override
    public void showSourceFeedback(Request request) {
        if (REQ_RESIZE.equals(request.getType())) {

            // Don't allow feedback smaller than minimum size for embedded
            // sub-proc / tasks.
            if (getHost() instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) getHost();

                if (ENFORCE_TASK_MINIMUM_SIZE) {
                    ChangeBoundsRequest cbreq = (ChangeBoundsRequest) request;

                    IFigure fig = getHostFigure();

                    Dimension sizeDelta = cbreq.getSizeDelta().getCopy();
                    Point moveDeltaPoint = cbreq.getMoveDelta().getCopy();
                    Dimension moveDelta =
                            new Dimension(moveDeltaPoint.x, moveDeltaPoint.y);

                    fig.translateToRelative(sizeDelta);
                    fig.translateToRelative(moveDelta);

                    if (sizeDelta.width != 0 || sizeDelta.height != 0) {

                        Dimension minSize = taskEP.getMinimumSizeForContent();

                        Rectangle curBnds = fig.getBounds().getCopy();

                        Rectangle absCurBnds = curBnds.getCopy();
                        fig.translateToAbsolute(absCurBnds);

                        Rectangle newBnds =
                                cbreq.getTransformedRectangle(absCurBnds);
                        fig.translateToRelative(newBnds);

                        Dimension newSize = newBnds.getSize();

                        int resizeDirection = cbreq.getResizeDirection();

                        if (newSize.width < minSize.width) {
                            // Adjust the move / size delta depending on
                            // direction of of the resize.
                            if ((resizeDirection & PositionConstants.EAST) != 0) {
                                // Resizing to the right, only need to change
                                // width.
                                sizeDelta.width = minSize.width - curBnds.width;

                                if (cbreq.isCenteredResize()) {
                                    moveDelta.width = 0;
                                }

                            } else if ((resizeDirection & PositionConstants.WEST) != 0) {
                                sizeDelta.width = minSize.width - curBnds.width;
                                moveDelta.width = -sizeDelta.width;
                            }
                        }

                        if (newSize.height < minSize.height) {
                            // Adjust the move / size delta depending on
                            // direction of of the resize.
                            if ((resizeDirection & PositionConstants.SOUTH) != 0) {
                                // Resizing downwards, only need to change
                                // height.
                                sizeDelta.height =
                                        minSize.height - curBnds.height;

                                if (cbreq.isCenteredResize()) {
                                    moveDelta.height = 0;
                                }

                            } else if ((resizeDirection & PositionConstants.NORTH) != 0) {
                                sizeDelta.height =
                                        minSize.height - curBnds.height;
                                moveDelta.height = -sizeDelta.height;
                            }
                        }

                        fig.translateToAbsolute(sizeDelta);
                        fig.translateToAbsolute(moveDelta);
                        moveDeltaPoint.x = moveDelta.width;
                        moveDeltaPoint.y = moveDelta.height;

                        cbreq.setSizeDelta(sizeDelta);
                        cbreq.setMoveDelta(moveDeltaPoint);
                    }
                }
            }

        }

        super.showSourceFeedback(request);
    }

    /**
     * @see org.eclipse.gef.editpolicies.XYLayoutEditPolicy#getConstraintFor(org.eclipse.gef.requests.ChangeBoundsRequest,
     *      org.eclipse.gef.GraphicalEditPart)
     * 
     * @param request
     * @param child
     * @return
     */
    @Override
    protected Object getConstraintFor(ChangeBoundsRequest request,
            GraphicalEditPart child) {
        /*
         * Override default behaviour for AlignRequests because for
         * events/gateways we need to only take into account the bounds of the
         * object not the text as well.
         */
        if (RequestConstants.REQ_ALIGN_CHILDREN.equals(request.getType())) {
            /**
             * Copied from underlying super class method as there was no other
             * way of overriding the get bounds on child edit part.
             * 
             * Use shape bounds if it's a evrent/gateway instead
             */
            Rectangle childBounds = child.getFigure().getBounds().getCopy();
            Rectangle alignBounds = null;

            if (child.getFigure() instanceof ShapeWithDescriptionFigure) {
                alignBounds =
                        ((ShapeWithDescriptionFigure) child.getFigure())
                                .getShape().getBounds().getCopy();

            } else {
                alignBounds = child.getFigure().getBounds();
            }

            Rectangle rect = new PrecisionRectangle(alignBounds);
            Rectangle original = rect.getCopy();
            child.getFigure().translateToAbsolute(rect);
            rect = request.getTransformedRectangle(rect);
            child.getFigure().translateToRelative(rect);
            rect.translate(getLayoutOrigin().getNegated());

            if (request.getSizeDelta().width == 0
                    && request.getSizeDelta().height == 0) {
                Rectangle cons = getCurrentConstraintFor(child);
                if (cons != null) {// Bug 86473 allows for unintended use of
                                   // this
                                   // method
                    /**
                     * Original code didn't re-center width ((for overhanging
                     * text) according to the complete width.
                     * 
                     * After aligning on the shape bounds, offset the actual
                     * bounds by the same amount the shape was originally offset
                     * in the whole figure.
                     */

                    rect.setLocation(new Point(rect.x
                            - (alignBounds.x - childBounds.x), rect.y
                            - (alignBounds.y - childBounds.y)));
                    rect.setSize(cons.width, cons.height);

                }

            } else { // resize
                Dimension minSize = getMinimumSizeFor(child);
                if (rect.width < minSize.width) {
                    rect.width = minSize.width;
                    if (rect.x > (original.right() - minSize.width))
                        rect.x = original.right() - minSize.width;
                }
                if (rect.height < minSize.height) {
                    rect.height = minSize.height;
                    if (rect.y > (original.bottom() - minSize.height))
                        rect.y = original.bottom() - minSize.height;
                }
            }
            return getConstraintFor(rect);
            /** End of copy from super class method. */
        }

        return super.getConstraintFor(request, child);
    }
}
