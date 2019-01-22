/**
 * SequenceFlowLayoutEditPolicy.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.processwidget.figures.NonNegativeBendpoint;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.NESWAnchor;
import com.tibco.xpd.processwidget.figures.anchors.TaskActivityAnchor;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * SequenceFlowLayoutEditPolicy
 * 
 */
public class SequenceFlowLayoutEditPolicy extends ConnectionLabelLayoutPolicy {

    // For natty feed back things...
    SequenceFlowFigure newFlowFig1, newFlowFig2;

    Color origConnectionColor = null;

    EditPolicy containerFeedbackEditPolicy = null;

    IFigure ownObjCreateFeedback = null;

    Request targetRequest = null;

    EditPartViewer.Conditional targetConditional;

    private static int LINKEVENT_INSERT_GAP = 16;

    public SequenceFlowLayoutEditPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        super(adapterFactory, editingDomain);

        targetConditional = new EditPartViewer.Conditional() {
            @Override
            public boolean evaluate(EditPart editpart) {
                return editpart.getTargetEditPart(targetRequest) != null;
            }
        };
    }

    /**
     * Find the lane or task (embedde sub-proc) that new object to splice should
     * be created in
     * 
     * @param request
     * @return
     */
    public ModelAdapterEditPart getContainerForSpliceObject(
            CreateRequest request) {
        ModelAdapterEditPart containerEP = null;

        SequenceFlowEditPart connEP = (SequenceFlowEditPart) getHost();

        // As sequence flows cannot cross sub-proc boundary then the container
        // will be the same as the parent of the current source object
        // if that source object is in a sub-proc OR the lane under mouse
        // if the source object is not in a sub-proc.
        //
        BaseGraphicalEditPart srcEP =
                (BaseGraphicalEditPart) connEP.getSource();

        ModelAdapterEditPart srcContainer = srcEP.getParentPoolOrTask();

        if (srcContainer instanceof TaskEditPart) {
            containerEP = srcContainer;

        } else {
            // Not in a sub-process find the lane under the mouse.
            ArrayList exclude = new ArrayList(1);
            exclude.add(connEP.getConnectionFigure());

            targetRequest = request;
            EditPart laneEP =
                    connEP.getViewer()
                            .findObjectAtExcluding(request.getLocation(),
                                    exclude,
                                    targetConditional);

            while (laneEP != null && !(laneEP instanceof LaneEditPart)) {
                laneEP = laneEP.getParent();
            }

            if (laneEP != null) {
                // It is possible that the connection extends (temporarily) into
                // a
                // lane that is in another pool, it would be illegal to create
                // this connection (can't have sequence flows crossin pools).
                // So check that the lane is in same pool as original source
                // object.
                EditPart targetPool = laneEP.getParent();

                while (targetPool != null
                        && !(targetPool instanceof PoolEditPart)) {
                    targetPool = targetPool.getParent();
                }

                if (targetPool == srcContainer) {
                    // target lane is in same pool as original source object so
                    // it's ok
                    containerEP = (ModelAdapterEditPart) laneEP;
                }
            }

        }

        return containerEP;
    }

    /**
     * Return the list of bendpoints before and after given object rectangle is
     * inserted.
     * 
     * @param newObjRect
     *            Relative to connection.
     * @param before
     *            List of Point's of bendpoints before inserted rect.
     * @param after
     *            List of Point's pf bendpoints after inserted rect.
     */
    private void getBendpointsBeforeAndAfter(SequenceFlowEditPart connEP,
            Rectangle newObjRect, List before, List after) {

        if (before != null && after != null) {
            Point center = newObjRect.getCenter();

            PointList points = connEP.getConnectionFigure().getPoints();

            double insertPos =
                    XPDLineUtilities.getLinePortionFromPoint(points, center);

            for (Iterator iter = connEP.getBendpoints().iterator(); iter
                    .hasNext();) {
                Bendpoint bp = (Bendpoint) iter.next();

                // First discount any points that are within rect of new object.
                // These will have to be deleted.
                Point bpPos = bp.getLocation();
                if (!newObjRect.contains(bpPos)) {

                    double pos =
                            XPDLineUtilities.getLinePortionFromPoint(points,
                                    bpPos);

                    if (pos < insertPos) {
                        before.add(bpPos.getCopy());
                    } else if (pos > insertPos) {
                        after.add(bpPos.getCopy());
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.policies.ConnectionLabelLayoutPolicy#
     * getCreateCommand(org.eclipse.gef.requests.CreateRequest)
     */
    @Override
    protected Command getCreateCommand(CreateRequest request) {
        // A create of object on a sequence flow always means
        // splice the object into the sequence flow.
        String invalid = isValidSpliceIntoConnection(request);

        if (invalid == null && request.getSize() != null) {

            SequenceFlowEditPart connEP = (SequenceFlowEditPart) getHost();

            // Get the object that is underneath the sequence flow under the
            // mouse
            ModelAdapterEditPart containerEP =
                    getContainerForSpliceObject(request);
            if (containerEP != null) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.SequenceFlowLayoutEditPolicy_InsertObject_menu);

                //
                // Delegate creation of object command to container's layout
                // policy.
                //
                EditPolicy editPolicy =
                        containerEP.getEditPolicy(EditPolicy.LAYOUT_ROLE);

                if (editPolicy instanceof FlowContainerXYLayoutEditPolicy) {
                    FlowContainerXYLayoutEditPolicy xyPolicy =
                            (FlowContainerXYLayoutEditPolicy) editPolicy;

                    // Intermediate link events are special case, for these we
                    // split the link with 2 objects.
                    boolean isLinkEvent = false;

                    if (request.getNewObjectType() == EventAdapter.class) {
                        EventFlowType flowType =
                                (EventFlowType) request
                                        .getExtendedData()
                                        .get(PaletteFactory.EXTDATA_EVENT_FLOW_TYPE);
                        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                .equals(flowType)) {
                            EventTriggerType eventType =
                                    (EventTriggerType) request
                                            .getExtendedData()
                                            .get(PaletteFactory.EXTDATA_EVENT_TYPE);

                            if (EventTriggerType.EVENT_LINK_CATCH_LITERAL
                                    .equals(eventType)
                                    || EventTriggerType.EVENT_LINK_THROW_LITERAL
                                            .equals(eventType)) {

                                // Creating a Throw link event is equivalent to
                                // creating a Catch link event when dropped on a
                                // sequence flow.
                                request.getExtendedData()
                                        .put(PaletteFactory.EXTDATA_EVENT_TYPE,
                                                EventTriggerType.EVENT_LINK_THROW_LITERAL);
                                isLinkEvent = true;
                            }
                        }

                    }

                    // Set location in request as closest point on line to
                    // centre of object.
                    Connection connectionFigure = connEP.getConnectionFigure();
                    PointList connectionPoints = connectionFigure.getPoints();

                    Rectangle newObjRectRelToConn = new Rectangle();

                    Point loc = request.getLocation().getCopy();
                    connectionFigure.translateToRelative(loc);

                    Dimension objSize = request.getSize().getCopy();

                    connectionFigure.translateToRelative(objSize);
                    loc.x += objSize.width / 2;
                    loc.y += objSize.height / 2;

                    Point closest =
                            XPDLineUtilities
                                    .getPolylinePointClosestToPoint(connectionPoints,
                                            loc);

                    // 2 potential create object commands.
                    Object obj1TmpCreateCmd = null;
                    Object obj2TmpCreateCmd = null;

                    if (isLinkEvent) {
                        // We are inserting 2 objects find out whether this
                        // should be horizontal or vertical.
                        boolean isHorizontal = true;
                        boolean isBackwards = false;

                        for (int p = 0; p < (connectionPoints.size() - 1); p++) {
                            Point pt1 = connectionPoints.getPoint(p);
                            Point pt2 = connectionPoints.getPoint(p + 1);

                            // Check if vertical
                            if (closest.x == pt1.x && closest.x == pt2.x) {
                                isHorizontal = false;

                                if (pt2.y < pt1.y) {
                                    isBackwards = true;
                                }
                                break;

                            } else if (closest.y == pt1.y && closest.y == pt2.y) {
                                isHorizontal = true;

                                if (pt2.x < pt1.x) {
                                    isBackwards = true;
                                }
                                break;
                            }
                        }

                        // Allow for double object size
                        Point loc1;
                        Point loc2;
                        if (isHorizontal) {
                            int width =
                                    (objSize.width * 2) + LINKEVENT_INSERT_GAP;

                            newObjRectRelToConn.setSize(width, objSize.height);

                            loc.x = closest.x - (width / 2);
                            loc.y = closest.y - (objSize.height / 2);

                            newObjRectRelToConn.setLocation(loc);

                            if (!isBackwards) {
                                loc1 =
                                        new Point(closest.x - (width / 2),
                                                closest.y
                                                        - (objSize.height / 2));
                                loc2 =
                                        new Point(closest.x + (width / 2)
                                                - objSize.width, closest.y
                                                - (objSize.height / 2));

                            } else {
                                loc2 =
                                        new Point(closest.x - (width / 2),
                                                closest.y
                                                        - (objSize.height / 2));
                                loc1 =
                                        new Point(closest.x + (width / 2)
                                                - objSize.width, closest.y
                                                - (objSize.height / 2));
                            }

                            // Adjust co-ords back to absolute and get create
                            // obj command for it.
                            connectionFigure.translateToAbsolute(loc1);
                            request.setLocation(loc1);
                            obj1TmpCreateCmd =
                                    xyPolicy.getEMFCreateCommand(request);

                            // And same again for second link event.
                            connectionFigure.translateToAbsolute(loc2);
                            request.setLocation(loc2);
                            obj2TmpCreateCmd =
                                    xyPolicy.getEMFCreateCommand(request);

                        } else {
                            // Vertical
                            int height =
                                    (objSize.height * 2) + LINKEVENT_INSERT_GAP;

                            newObjRectRelToConn.setSize(objSize.width, height);

                            loc.x = closest.x - (objSize.width / 2);
                            loc.y = closest.y - (height / 2);

                            newObjRectRelToConn.setLocation(loc);

                            if (!isBackwards) {
                                loc1 =
                                        new Point(closest.x
                                                - (objSize.width / 2),
                                                closest.y - (height / 2));
                                loc2 =
                                        new Point(closest.x
                                                - (objSize.width / 2),
                                                closest.y + (height / 2)
                                                        - objSize.height);

                            } else {
                                loc2 =
                                        new Point(closest.x
                                                - (objSize.width / 2),
                                                closest.y - (height / 2));
                                loc1 =
                                        new Point(closest.x
                                                - (objSize.width / 2),
                                                closest.y + (height / 2)
                                                        - objSize.height);

                            }

                            // Adjust co-ords back to absolute and get create
                            // obj command for it.
                            connectionFigure.translateToAbsolute(loc1);
                            request.setLocation(loc1);
                            obj1TmpCreateCmd =
                                    xyPolicy.getEMFCreateCommand(request);

                            // And same again for second link event.
                            connectionFigure.translateToAbsolute(loc2);
                            request.setLocation(loc2);
                            obj2TmpCreateCmd =
                                    xyPolicy.getEMFCreateCommand(request);
                        }

                        loc.x = closest.x - (objSize.width / 2);
                        loc.y = closest.y - (objSize.height / 2);

                        connectionFigure.translateToAbsolute(loc);
                        request.setLocation(loc);

                    } else {

                        loc.x = closest.x - (objSize.width / 2);
                        loc.y = closest.y - (objSize.height / 2);

                        newObjRectRelToConn.setSize(objSize);
                        newObjRectRelToConn.setLocation(loc);

                        connectionFigure.translateToAbsolute(loc);
                        request.setLocation(loc);

                        obj1TmpCreateCmd =
                                xyPolicy.getEMFCreateCommand(request);
                    }

                    // Add new object to container.
                    // Only continue with command if the container was happy to
                    // drop object where at location.
                    if ((obj1TmpCreateCmd instanceof CreateAccessibleObjectCommand)
                            && (obj2TmpCreateCmd == null || obj2TmpCreateCmd instanceof CreateAccessibleObjectCommand)) {

                        CreateAccessibleObjectCommand createCmd =
                                (CreateAccessibleObjectCommand) obj1TmpCreateCmd;
                        EObject newNodeObject = createCmd.getCreatedNode();

                        CreateAccessibleObjectCommand createLinkEventCmd2 =
                                null;
                        EObject newLinkEventObject2 = null;

                        if (obj2TmpCreateCmd != null) {
                            createLinkEventCmd2 =
                                    (CreateAccessibleObjectCommand) obj2TmpCreateCmd;
                            newLinkEventObject2 =
                                    createLinkEventCmd2.getCreatedNode();
                        }

                        //
                        // Check we have everything we need (new obj create
                        // command and object + same for second object if there
                        // is to be one)...
                        if (newNodeObject != null
                                && createCmd.canExecute()
                                && (createLinkEventCmd2 == null || (createLinkEventCmd2
                                        .canExecute() && newLinkEventObject2 != null))) {

                            cmd.add(new EMFCommandWrapper(getEditingDomain(),
                                    createCmd));

                            SequenceFlowAdapter connAdp =
                                    (SequenceFlowAdapter) connEP
                                            .getModelConnectionAdapter();

                            if (createLinkEventCmd2 != null) {
                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(), createLinkEventCmd2));

                                // set link reference on 1st linked event.
                                EventAdapter evAdp =
                                        (EventAdapter) getAdapterFactory()
                                                .adapt(newNodeObject,
                                                        ProcessWidgetConstants.ADAPTER_TYPE);
                                EventAdapter evAdpTgt =
                                        (EventAdapter) getAdapterFactory()
                                                .adapt(newLinkEventObject2,
                                                        ProcessWidgetConstants.ADAPTER_TYPE);
                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        evAdpTgt.getSetInverseCatchThrowOnLinkCommand(getEditingDomain())));

                                Object srcObj = connAdp.getSourceNode();
                                FlowNodeAdapter nodeAdp =
                                        (FlowNodeAdapter) getAdapterFactory()
                                                .adapt(srcObj,
                                                        ProcessWidgetConstants.ADAPTER_TYPE);
                                ProcessDiagramAdapter procAdp =
                                        (ProcessDiagramAdapter) getAdapterFactory()
                                                .adapt(nodeAdp.getProcess(),
                                                        ProcessWidgetConstants.ADAPTER_TYPE);

                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        evAdp.getSetEventLinkIdCommand(getEditingDomain(),
                                                evAdpTgt.getId(),
                                                procAdp.getId())));

                            }

                            //
                            // Now reconnect the source of the sequence flow to
                            // the new object.
                            // Have to do it this way around (Creating new from
                            // orig src to new object) because its the source
                            // object of conneciton that provides the create
                            // sequence flow command).
                            //

                            SequenceFlowType origSrcFlowType =
                                    connAdp.getFlowType();
                            String origSrcCondition = connAdp.getCondition();
                            String origSrcGrammarId =
                                    connAdp.getExistingSetScriptGrammarId();
                            Double origSrcAnchor =
                                    connAdp.getStartAnchorPosition();

                            if (newLinkEventObject2 != null) {
                                // If we're adding 2 new objects (Link events)
                                // re - source orig connection from the second.

                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getSetSourceCommand(getEditingDomain(),
                                                newLinkEventObject2,
                                                null)));

                            } else {
                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getSetSourceCommand(getEditingDomain(),
                                                newNodeObject,
                                                null)));
                            }

                            // To make sense we need to move label to new
                            // connection.
                            String label = connAdp.getName();
                            ConnectionLabelPosition labelPos =
                                    connAdp.getLabelPosition();

                            if (labelPos != null) {
                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getSetLabelPositionCommand(getEditingDomain(),
                                                null)));
                            }

                            // If spliting link with link event keep original
                            // label.
                            if (!isLinkEvent) {
                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getSetNameCommand(getEditingDomain(),
                                                ""))); //$NON-NLS-1$
                            }

                            // Sort out bend points for nice behaviour.
                            // basically this means...
                            //
                            // Deleting any bend point that new object overlaps.
                            //
                            // Get list of and delete any bendpoints before the
                            // insert location re-add the original bendpoints
                            // before insertion point on new
                            // connection.
                            List beforeBP = new ArrayList();
                            List afterBP = new ArrayList();

                            getBendpointsBeforeAndAfter(connEP,
                                    newObjRectRelToConn,
                                    beforeBP,
                                    afterBP);

                            // Delete all existing bendpoints on line.
                            // for now just delete all of them.
                            int size = connAdp.getBendpoints().size();
                            for (int i = 0; i < size; i++) {

                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getDeleteBendpointCommand(getEditingDomain(),
                                                i)));
                            }

                            // Re-add the bendpoints after rect to be inserted.
                            int bpIndex = 0;
                            for (Iterator iter = afterBP.iterator(); iter
                                    .hasNext(); bpIndex++) {
                                Point bpPos = (Point) iter.next();

                                XPDBendpointType bp = new XPDBendpointType();

                                Point end =
                                        connectionFigure.getTargetAnchor()
                                                .getReferencePoint().getCopy();
                                connectionFigure.translateToRelative(end);

                                Point start = newObjRectRelToConn.getCenter();

                                bp.fromStart =
                                        new Dimension(bpPos.x - start.x,
                                                bpPos.y - start.y);
                                bp.fromEnd =
                                        new Dimension(bpPos.x - end.x, bpPos.y
                                                - end.y);
                                bp.weight = 0.5f;

                                cmd.add(new EMFCommandWrapper(
                                        getEditingDomain(),
                                        connAdp.getCreateBendpointCommand(getEditingDomain(),
                                                bpIndex,
                                                bp)));
                            }

                            // And create a list of the bendpoints to add to new
                            // sequence flow (i.e. the bendpoints that were
                            // before the inserted object rect
                            List newFlowBendpoints = new ArrayList();

                            for (Iterator iter = beforeBP.iterator(); iter
                                    .hasNext();) {
                                Point bpPos = (Point) iter.next();

                                XPDBendpointType bp = new XPDBendpointType();

                                Point start =
                                        connectionFigure.getSourceAnchor()
                                                .getReferencePoint().getCopy();
                                connectionFigure.translateToRelative(start);

                                Point end = newObjRectRelToConn.getCenter();

                                bp.fromStart =
                                        new Dimension(bpPos.x - start.x,
                                                bpPos.y - start.y);
                                bp.fromEnd =
                                        new Dimension(bpPos.x - end.x, bpPos.y
                                                - end.y);
                                bp.weight = 0.5f;

                                newFlowBendpoints.add(bp);
                            }

                            // And set the flow type to none
                            cmd.add(new EMFCommandWrapper(
                                    getEditingDomain(),
                                    connAdp.getSetFlowTypeCommand(getEditingDomain(),
                                            SequenceFlowType.UNCONTROLLED_LITERAL)));

                            //
                            // Then create a new sequence flow from the original
                            // source object to new. Picking up the original
                            // sequence flow type, bendpoints, condition and
                            // anchorpos.
                            //
                            SequenceFlowNodeAdapter srcAdp =
                                    (SequenceFlowNodeAdapter) ((ModelAdapterEditPart) connEP
                                            .getSource()).getModelAdapter();

                            ProcessWidgetColors colors =
                                    ProcessWidgetColors.getInstance(connEP);

                            WidgetRGB lineColor =
                                    colors.getGraphicalNodeColor(connAdp,
                                            connEP.getLineColorIDForPartType());

                            cmd.add(new EMFCommandWrapper(
                                    getEditingDomain(),
                                    srcAdp.getCreateSequenceFlowCommand(getEditingDomain(),
                                            newNodeObject,
                                            origSrcFlowType,
                                            origSrcCondition,
                                            origSrcGrammarId,
                                            newFlowBendpoints,
                                            origSrcAnchor,
                                            null,
                                            label,
                                            labelPos,
                                            lineColor.toString())));

                            return cmd;
                        } else {
                            WidgetRootEditPart root =
                                    (WidgetRootEditPart) getHost().getRoot();
                            root.setErrorTipHelperText(getHostFigure(),
                                    Messages.SequenceFlowLayoutEditPolicy_CannotPlaceObject_tooltip);
                        }
                    }
                }

            } else {
                WidgetRootEditPart root =
                        (WidgetRootEditPart) getHost().getRoot();
                root.setErrorTipHelperText(getHostFigure(),
                        Messages.SequenceFlowLayoutEditPolicy_OutOfScopeForFlow_message);
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Check whether it is valid to splice the given object into this sequence
     * flow.
     * <p>
     * If the new object class will never be valid on any sequecne flow (i.e. it
     * is not a flow related object type) the return is "".
     * </p>
     * <p>
     * If the new object class is a valid flow object but not valid in this
     * place then a String describing the reason is returned.
     * </p>
     * <p>
     * If the new object is valid to splice into sequence flow then null is
     * returned.
     * </p>
     * 
     * @param request
     *            CreateRequest for new object.
     * 
     * @return String - See description above.
     */
    protected String isValidSpliceIntoConnection(CreateRequest request) {
        Object spliceObjectClass = request.getNewObjectType();

        if (spliceObjectClass == EventAdapter.class) {
            EventFlowType eventType =
                    (EventFlowType) request.getExtendedData()
                            .get(PaletteFactory.EXTDATA_EVENT_FLOW_TYPE);

            // Cannot splice start or end event into a sequence flow
            // as one cannot have input and end cannot have output.
            if (EventFlowType.FLOW_START_LITERAL.equals(eventType)) {
                return Messages.SequenceFlowLayoutEditPolicy_CannotInsertStart_message;
            }

            // Cannot connect From a start event...
            if (EventFlowType.FLOW_END_LITERAL.equals(eventType)) {
                return Messages.SequenceFlowLayoutEditPolicy_CannotInsertEnd_message;
            }
        }

        if (spliceObjectClass == EventAdapter.class
                || spliceObjectClass == TaskAdapter.class
                || spliceObjectClass == GatewayAdapter.class
                || spliceObjectClass == TaskAdapter.class) {

            // Otherwise, disallow if the new object will overlap the current
            // source and target
            if (request.getSize() != null) {
                return null;
            }
        }

        return ""; //$NON-NLS-1$ // This object is never valid on any sequence flow.
    }

    /*
     * @see
     * org.eclipse.gef.editpolicies.LayoutEditPolicy#getTargetEditPart(org.eclipse
     * .gef.Request)
     */
    @Override
    public EditPart getTargetEditPart(Request request) {
        if (REQ_CREATE.equals(request.getType())) {
            //
            // Create requests on a sequence flow mean 'splice object into
            // flow'.
            //
            CreateRequest creq = (CreateRequest) request;

            WidgetRootEditPart root = (WidgetRootEditPart) getHost().getRoot();

            String invalidStr = isValidSpliceIntoConnection(creq);

            if (invalidStr != null) {
                if (!invalidStr.equals("")) { //$NON-NLS-1$
                    // Invalid for a reason...
                    root.setErrorTipHelperText(getHostFigure(), invalidStr);
                } else {
                    root.clearErrorTipHelper();
                }
            } else {
                root.clearErrorTipHelper();
            }

            // Return ourselves as the target edit part, else request will be
            // passsed onto lane behind.
            return getHost();
        }
        return super.getTargetEditPart(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.AbstractEditPolicy#showSourceFeedback(org
     * .eclipse.gef.Request)
     */
    @Override
    public void showTargetFeedback(Request request) {
        super.showTargetFeedback(request);

        if (REQ_SELECTION.equals(request.getType())) {
            // Store last click position within object behind sequence flow.
            // Excluding any connection.

            if (request instanceof SelectionRequest) {

                SelectionRequest req = (SelectionRequest) request;

                // Save the last clicked position and flow container behind the
                // click-position so that if the user performs a paste then the
                // past command has the info it needs.
                setupSavedClickPos(req);

            }
        }

        else if (REQ_CREATE.equals(request.getType())) {
            CreateRequest createRequest = (CreateRequest) request;

            // Setup the feedback figures for inserting an object into
            // connection
            setupFeebackForNewObjOnConnection(request, createRequest);
        }
    }

    /**
     * Setup the feedback figures for inserting an object into connection
     * 
     * @param request
     * @param createRequest
     */
    private void setupFeebackForNewObjOnConnection(Request request,
            CreateRequest createRequest) {
        IFigure feedbackLayer = getFeedbackLayer();

        // Steal the feed back from the container that will receive
        // the object being spliced into sequence flow.
        Command cmd = getCreateCommand(createRequest);

        boolean cmdValid = cmd.canExecute();

        SequenceFlowEditPart connEP = (SequenceFlowEditPart) getHost();
        SequenceFlowFigure hostFigure = (SequenceFlowFigure) getHostFigure();

        // fading out real connection.
        if (origConnectionColor == null) {
            origConnectionColor = hostFigure.getForegroundColor();
            hostFigure.setForegroundColor(ColorConstants.lightBlue);
        }

        hostFigure.setLineWidth(2);

        if (cmdValid) {
            ModelAdapterEditPart srcContainer =
                    getContainerForSpliceObject(createRequest);

            if (srcContainer != null) {

                if (ownObjCreateFeedback != null) {
                    feedbackLayer.remove(ownObjCreateFeedback);
                    ownObjCreateFeedback = null;
                }

                containerFeedbackEditPolicy =
                        srcContainer.getEditPolicy(EditPolicy.LAYOUT_ROLE);

                containerFeedbackEditPolicy.showTargetFeedback(request);

                IFigure objFeedbackFig = null;
                for (Iterator iter = feedbackLayer.getChildren().iterator(); iter
                        .hasNext();) {
                    IFigure fig = (IFigure) iter.next();

                    if (!(fig instanceof Polyline)) {
                        objFeedbackFig = fig;
                    }
                }

                Object type = (createRequest).getNewObjectType();

                if (newFlowFig1 == null) {
                    newFlowFig1 = hostFigure.getCopy();
                    newFlowFig1.setForegroundColor(origConnectionColor);
                    newFlowFig1.setLineWidth(2);

                    ConnectionAnchor anchor = null;

                    if (type == TaskAdapter.class || type == TaskAdapter.class) {
                        anchor = new TaskActivityAnchor(objFeedbackFig, false);
                    } else {
                        anchor = new NESWAnchor(objFeedbackFig);
                    }
                    newFlowFig1.setTargetAnchor(anchor);

                    feedbackLayer.add(newFlowFig1);
                }

                if (newFlowFig2 == null) {
                    newFlowFig2 = hostFigure.getCopy();
                    newFlowFig2
                            .setFlowType(SequenceFlowType.UNCONTROLLED_LITERAL);
                    newFlowFig2.setForegroundColor(origConnectionColor);
                    newFlowFig2.setLineWidth(2);

                    ConnectionAnchor anchor = null;

                    if (type == TaskAdapter.class || type == TaskAdapter.class) {
                        anchor = new TaskActivityAnchor(objFeedbackFig, true);
                    } else {
                        anchor = new NESWAnchor(objFeedbackFig);
                    }

                    newFlowFig2.setSourceAnchor(anchor);
                    feedbackLayer.add(newFlowFig2);
                }

                // Get the before and after insert rectangle bendpoints.
                Rectangle newObjRectRelToConn = new Rectangle();

                newObjRectRelToConn.setLocation(createRequest.getLocation());
                Dimension size = createRequest.getSize();
                if (size != null) {
                    newObjRectRelToConn.setSize(size);
                }

                hostFigure.translateToRelative(newObjRectRelToConn);

                List beforeBP = new ArrayList();
                List afterBP = new ArrayList();

                getBendpointsBeforeAndAfter(connEP,
                        newObjRectRelToConn,
                        beforeBP,
                        afterBP);

                // First seq flow, bend points are start relative to
                // original source anhor, end relative to new object centre.
                Point start =
                        hostFigure.getSourceAnchor().getReferencePoint()
                                .getCopy();
                hostFigure.translateToRelative(start);

                Point newObjRefPoint = newObjRectRelToConn.getCenter();

                List fig1BP = new ArrayList();
                for (Iterator iter = beforeBP.iterator(); iter.hasNext();) {
                    Point bpPos = (Point) iter.next();

                    RelativeBendpoint rb =
                            new NonNegativeBendpoint(newFlowFig1);

                    Dimension fromStart =
                            new Dimension(bpPos.x - start.x, bpPos.y - start.y);
                    Dimension fromEnd =
                            new Dimension(bpPos.x - newObjRefPoint.x, bpPos.y
                                    - newObjRefPoint.y);

                    hostFigure.translateToAbsolute(fromStart);
                    newFlowFig1.translateToRelative(fromStart);
                    hostFigure.translateToAbsolute(fromEnd);
                    newFlowFig1.translateToRelative(fromEnd);

                    rb.setRelativeDimensions(fromStart, fromEnd);
                    rb.setWeight(0.5f);

                    fig1BP.add(rb);
                }

                newFlowFig1.setRoutingConstraint(fig1BP);

                // Second seq flow, bend points are END relative to
                // original source anchor, STARTrelative to new object
                // centre.
                Point end =
                        hostFigure.getTargetAnchor().getReferencePoint()
                                .getCopy();
                hostFigure.translateToRelative(end);

                List fig2BP = new ArrayList();
                for (Iterator iter = afterBP.iterator(); iter.hasNext();) {
                    Point bpPos = (Point) iter.next();

                    RelativeBendpoint rb = new RelativeBendpoint(newFlowFig2);

                    Dimension fromStart =
                            new Dimension(bpPos.x - newObjRefPoint.x, bpPos.y
                                    - newObjRefPoint.y);
                    Dimension fromEnd =
                            new Dimension(bpPos.x - end.x, bpPos.y - end.y);

                    hostFigure.translateToAbsolute(fromStart);
                    newFlowFig1.translateToRelative(fromStart);
                    hostFigure.translateToAbsolute(fromEnd);
                    newFlowFig1.translateToRelative(fromEnd);

                    rb.setRelativeDimensions(fromStart, fromEnd);
                    rb.setWeight(0.5f);

                    fig2BP.add(rb);
                }

                newFlowFig2.setRoutingConstraint(fig2BP);

            }
        } else {
            Dimension size = createRequest.getSize();
            if (size != null) {
                // If command is invalid then container's policy won't show
                // feedback.
                // But it's nicer for user if it stays there.
                // So we will create our own.
                if (containerFeedbackEditPolicy != null) {
                    containerFeedbackEditPolicy.eraseTargetFeedback(request);
                    containerFeedbackEditPolicy = null;
                }

                if (ownObjCreateFeedback == null) {
                    ownObjCreateFeedback = new RectangleFigure();
                    FigureUtilities
                            .makeGhostShape((Shape) ownObjCreateFeedback);
                    ((Shape) ownObjCreateFeedback)
                            .setLineStyle(Graphics.LINE_DASHDOT);
                    ownObjCreateFeedback
                            .setForegroundColor(ColorConstants.white);

                    feedbackLayer.add(ownObjCreateFeedback);

                }

                ownObjCreateFeedback.setSize(size);
                Point location = createRequest.getLocation().getCopy();
                feedbackLayer.translateToRelative(location);
                ownObjCreateFeedback.setLocation(location);

                if (newFlowFig1 != null) {
                    feedbackLayer.remove(newFlowFig1);
                    newFlowFig1 = null;
                }

                if (newFlowFig2 != null) {
                    feedbackLayer.remove(newFlowFig2);
                    newFlowFig2 = null;
                }
            }
        }
    }

    /**
     * Save the last clicked position and flow container behind the
     * click-position so that if the user performs a paste then the paste
     * command has the info it needs.
     * 
     * @param req
     */
    private void setupSavedClickPos(SelectionRequest req) {
        GraphicalEditPart gep = (GraphicalEditPart) getHost();

        Point loc = req.getLocation().getCopy();

        BpmnScrollingGraphicalViewer viewer =
                (BpmnScrollingGraphicalViewer) gep.getViewer();

        // This exclusion condition will only return Lane or embedded sub-proc
        // task.
        // (Or ProcessEditPart if all else fails).
        EditPartViewer.Conditional exclusionConditional =
                new EditPartViewer.Conditional() {
                    @Override
                    public boolean evaluate(EditPart editpart) {
                        if (editpart instanceof LaneEditPart) {
                            return true;
                        } else if (editpart instanceof TaskEditPart) {
                            TaskEditPart tep = (TaskEditPart) editpart;
                            if (tep.isEmbeddedSubProc()) {
                                return true;
                            }
                        }
                        return false;
                    }

                };

        EditPart behindSeq =
                viewer.findObjectAtExcluding(loc,
                        Collections.EMPTY_LIST,
                        exclusionConditional);

        if (behindSeq instanceof LaneEditPart
                || behindSeq instanceof TaskEditPart) {
            IFigure cnt =
                    ((AbstractGraphicalEditPart) behindSeq).getContentPane();

            cnt.translateToRelative(loc);

            if (cnt.getLayoutManager() instanceof XYLayout) {
                XYLayout lay = (XYLayout) cnt.getLayoutManager();
                loc.translate(lay.getOrigin(cnt).getCopy().negate());

                gep.getViewer()
                        .setProperty(ProcessWidgetConstants.VIEWPROP_FLOWCONTAINER_LASTCLICKPOS,
                                loc);
            }

            // Save flow container that was behind click position.
            gep.getViewer()
                    .setProperty(ProcessWidgetConstants.VIEWPROP_SEQFLOW_LASTCLICKFLOWCONTAINER,
                            behindSeq);

        } else {
            gep.getViewer()
                    .setProperty(ProcessWidgetConstants.VIEWPROP_SEQFLOW_LASTCLICKFLOWCONTAINER,
                            null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseSourceFeedback(org
     * .eclipse.gef.Request)
     */
    @Override
    public void eraseTargetFeedback(Request request) {
        super.eraseSourceFeedback(request);

        if (REQ_CREATE.equals(request.getType())) {
            // Re-show main connection
            if (origConnectionColor != null) {
                getHostFigure().setForegroundColor(origConnectionColor);
                origConnectionColor = null;
            }

            ((SequenceFlowFigure) getHostFigure()).setLineWidth(1);

            IFigure fbl = getFeedbackLayer();

            if (newFlowFig1 != null) {
                fbl.remove(newFlowFig1);
                newFlowFig1 = null;
            }

            if (newFlowFig2 != null) {
                fbl.remove(newFlowFig2);
                newFlowFig2 = null;
            }

            // Steal the feed back from the container that will receive
            // the object being spliced into sequence flow.
            if (containerFeedbackEditPolicy != null) {
                containerFeedbackEditPolicy.eraseTargetFeedback(request);
                containerFeedbackEditPolicy = null;
            }

            if (ownObjCreateFeedback != null) {
                fbl.remove(ownObjCreateFeedback);
                ownObjCreateFeedback = null;
            }

        }

    }
}
