/*
 * Note object's node (connection from) edit policy.
 * 
 * Used to be DiagramGraphicalNodeEditPolicy - but now specific to note objects.
 * 
 * @author wzurek
 * 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.processwidget.figures.AssociationConnectionFigure;
import com.tibco.xpd.processwidget.figures.BpmnDirectRouter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.figures.MessageFlowFigure;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.figures.anchors.AnchorPositionFeedbackFigure;
import com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor;
import com.tibco.xpd.processwidget.parts.AssociationEditPart;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.MessageFlowEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * <p>
 * This class handles the basic 'connection between nodes' policy. i.e. handles
 * creation of sequence flow / message flow and associations.
 * </p>
 * <p>
 * This class handles all our base create connection facilities such as creation
 * of bend point during connection creation by right-clicking.
 * </p>
 * <p>
 * Subclass this class in order to apply rules according to connection type (by
 * implementing the understandsRequest() method.
 * </p>
 */
public abstract class ConnectableGraphicalNodeObjectEditPolicy extends
        GraphicalNodeEditPolicy {

    // =======================================================================
    // Object keys for request extended data hashmap.
    // =======================================================================

    /**
     * For AssociationToolEntry to pass last right-click position to
     * showSourceFeedBack Object associated with this key is: Point
     */
    public static final String NEW_BENDPOINT_KEY =
            "Create Connection New Bendpoint"; //$NON-NLS-1$

    /**
     * For FlowConnectionToolEntry to signal that last bendpoint should be
     * deleted. Object associated with this key is: Boolean
     */
    public static final String DEL_LAST_BENDPOINT_KEY =
            "Create Connection Delete Last BendPoint"; //$NON-NLS-1$

    /**
     * So that Edit Policy for create can access connection figure created by
     * different 'in progress edit policy Object associated with this key is:
     * FlowConnectionFigure
     */
    private static final String FEEDBACK_FIGURE_KEY =
            "Create Connection Feedback Figure"; //$NON-NLS-1$

    /**
     * List of dimension offsets from start anchor reference point. Object
     * associated with this key is: ArrayList of Dimension objects
     */
    public static final String NEW_BENDPOINT_OFFSETS_KEY =
            "Create Connection Bendpoint Offsets from source"; //$NON-NLS-1$

    private final EditingDomain editingDomain;

    private ConnectionRouter connectionRouter = null;

    private AnchorPositionFeedbackFigure startAnchorPositionFigure = null;

    public ConnectableGraphicalNodeObjectEditPolicy(EditingDomain editingDomain) {
        this.editingDomain = editingDomain;
    }

    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Get the type of connection expressed as an Adapter (SequenceFlowAdapter,
     * MessageFlowAdapter or AssociationAdapter) for the given create /
     * reconnection request.
     * 
     * @param req
     * @return <li>SequenceFlowAdapter.Class, MessageFlowAdapter.Class or
     *         AssociationAdapter.Class</li> <li>null on error.</li>
     */
    public Object getConnectionType(Request req) {
        if (REQ_CONNECTION_START.equals(req.getType())
                || REQ_CONNECTION_END.equals(req.getType())) {
            return ((CreateConnectionRequest) req).getNewObjectType();

        } else if (REQ_RECONNECT_TARGET.equals(req.getType())
                || REQ_RECONNECT_SOURCE.equals(req.getType())) {
            EditPart ep = ((ReconnectRequest) req).getConnectionEditPart();

            if (ep instanceof SequenceFlowEditPart) {
                return SequenceFlowAdapter.class;

            } else if (ep instanceof MessageFlowEditPart) {
                return MessageFlowAdapter.class;

            } else if (ep instanceof AssociationEditPart) {
                return AssociationAdapter.class;

            }
        }
        return null;
    }

    /**
     * Artificial GEF command that holds source and target of the connection
     */
    class CreateConnectionCommand extends Command {
        public Object source;

        public Object target;
    }

    protected Command getConnectionCompleteCommand(CreateConnectionRequest req) {
        Command cmd = UnexecutableCommand.INSTANCE;

        if (understandsRequest(req)) {
            if (REQ_CONNECTION_END.equals(req.getType())) {

                PolylineConnection connection =
                        (PolylineConnection) req.getExtendedData()
                                .get(FEEDBACK_FIGURE_KEY);

                if (connection != null) {
                    ConnectionAnchor srcAnchor = connection.getSourceAnchor();
                    ConnectionAnchor tgtAnchor = connection.getTargetAnchor();

                    List bendPoints = null;

                    ModelAdapterEditPart srcEP =
                            (ModelAdapterEditPart) req.getSourceEditPart();
                    ModelAdapterEditPart tarEP =
                            (ModelAdapterEditPart) req.getTargetEditPart();
                    if (tarEP != null && srcEP != null) {
                        // If bend points were added during connection creation
                        // add
                        // them to transition.
                        List bendOffsets =
                                (List) req.getExtendedData()
                                        .get(NEW_BENDPOINT_OFFSETS_KEY);

                        if (bendOffsets != null) {
                            bendPoints = new ArrayList();

                            // Cannot use connection feedback to calculate
                            // bendpoint offsets as at the point
                            // of actual connection creation it does not beloing
                            // to anything that handles zoom level.
                            // start and end should be in diagram-absolute
                            // co-ordinates (never change with zoom/scroll).
                            Point start = srcAnchor.getReferencePoint();

                            Point end = tgtAnchor.getReferencePoint();

                            // Bend offsets are stored in 'whole process'
                            // relative co-ords
                            // so make sure we calculate on that basis too

                            IFigure connectionLayer =
                                    getLayer(LayerConstants.CONNECTION_LAYER);
                            if (connectionLayer != null) {
                                connectionLayer.translateToRelative(start);

                                connectionLayer.translateToRelative(end);

                                for (Iterator iter = bendOffsets.iterator(); iter
                                        .hasNext();) {
                                    Dimension offset = (Dimension) iter.next();

                                    Point loc =
                                            new Point(start.x + offset.width,
                                                    start.y + offset.height);

                                    XPDBendpointType bP =
                                            new XPDBendpointType();
                                    bP.fromStart = offset;
                                    bP.fromEnd =
                                            new Dimension(loc.x - end.x, loc.y
                                                    - end.y);
                                    bP.weight = 0.5f;

                                    bendPoints.add(bP);
                                }
                            }
                        }
                    }

                    // Get the concrete sub-class to get the final model command
                    // that creates the connection.
                    Object connType = getConnectionType(req);
                    if (connType != null) {
                        Double startAnchorPos =
                                getFixedAnchorStartPos(srcAnchor);
                        Double endAnchorPos = getFixedAnchorStartPos(tgtAnchor);

                        BaseProcessAdapter adp =
                                ((ModelAdapterEditPart) getHost())
                                        .getModelAdapter();

                        ProcessWidgetColors colors =
                                    ProcessWidgetColors
                                            .getInstance(adp);

                        WidgetRGB lineColor =
                                colors
                                        .getGraphicalNodeColor(null,
                                                (String) req
                                                        .getExtendedData()
                                                        .get(PaletteFactory.EXTDATA_DEFAULT_LINE_COLORID));

                        if (adp instanceof NamedElementAdapter) {
                            if (connType == SequenceFlowAdapter.class) {
                                SequenceFlowNodeAdapter srcAdp =
                                        (SequenceFlowNodeAdapter) srcEP
                                                .getModelAdapter();

                                SequenceFlowType flowType =
                                        (SequenceFlowType) req
                                                .getExtendedData()
                                                .get(PaletteFactory.EXTDATA_SEQUENCEFLOW_TYPE);

                                cmd =
                                        new EMFCommandWrapper(
                                                getEditingDomain(),
                                                srcAdp
                                                        .getCreateSequenceFlowCommand(getEditingDomain(),
                                                                (EObject) tarEP
                                                                        .getModel(),
                                                                flowType,
                                                                null,
                                                                null,
                                                                bendPoints,
                                                                startAnchorPos,
                                                                endAnchorPos,
                                                                null,
                                                                null,
                                                                lineColor
                                                                        .toString()));

                            } else if (connType == MessageFlowAdapter.class) {
                                MessageFlowNodeAdapter srcAdp =
                                        (MessageFlowNodeAdapter) srcEP
                                                .getModelAdapter();

                                cmd =
                                        new EMFCommandWrapper(
                                                getEditingDomain(),
                                                srcAdp
                                                        .getCreateMessageFlowCommand(getEditingDomain(),
                                                                (EObject) tarEP
                                                                        .getModel(),
                                                                bendPoints,
                                                                startAnchorPos,
                                                                endAnchorPos,
                                                                lineColor
                                                                        .toString()));

                            } else if (connType == AssociationAdapter.class) {
                                // Associations can be from nodes...
                                Object srcAdp = srcEP.getModelAdapter();

                                if (srcAdp instanceof BaseGraphicalNodeAdapter) {

                                    cmd =
                                            new EMFCommandWrapper(
                                                    getEditingDomain(),
                                                    ((BaseGraphicalNodeAdapter) srcAdp)
                                                            .getCreateAssociationCommand(getEditingDomain(),
                                                                    (EObject) tarEP
                                                                            .getModel(),
                                                                    bendPoints,
                                                                    startAnchorPos,
                                                                    endAnchorPos,
                                                                    lineColor
                                                                            .toString()));
                                }
                                // OR to other connections.
                                else if (srcAdp instanceof BaseConnectionAdapter) {
                                    cmd =
                                            new EMFCommandWrapper(
                                                    getEditingDomain(),
                                                    ((BaseConnectionAdapter) srcAdp)
                                                            .getCreateAssociationCommand(getEditingDomain(),
                                                                    (EObject) tarEP
                                                                            .getModel(),
                                                                    bendPoints,
                                                                    startAnchorPos,
                                                                    endAnchorPos,
                                                                    lineColor
                                                                            .toString()));

                                }
                            }
                        }
                    }
                }
            }
        }

        return cmd;

    }

    /**
     * Sub-class should override this to filter requests appropriately for it's
     * connection type.
     */
    public boolean understandsRequest(Request req) {
        return (false);
    }

    /**
     * Get the GEF command for start of connection.
     */
    protected Command getConnectionCreateCommand(CreateConnectionRequest req) {
        if (understandsRequest(req)) {
            if (REQ_CONNECTION_START.equals(req.getType())) {
                CreateConnectionCommand cmd = new CreateConnectionCommand();
                req.setStartCommand(cmd);
                return cmd;
            }
        }
        return null;
    }

    /**
     * Get the connection target object changed EMF command.
     */
    protected Command getReconnectTargetCommand(ReconnectRequest req) {
        if (understandsRequest(req)) {
            ConnectionEditPart conn = req.getConnectionEditPart();
            if (conn instanceof BaseConnectionEditPart) {
                BaseConnectionAdapter a =
                        (BaseConnectionAdapter) ((BaseConnectionEditPart) conn)
                                .getModelAdapter();

                if (getHost() instanceof NodeEditPart) {
                    ConnectionAnchor anchor =
                            ((NodeEditPart) getHost())
                                    .getTargetConnectionAnchor(req);

                    Double endAnchorPos = getFixedAnchorStartPos(anchor);

                    BaseProcessAdapter adp =
                            ((ModelAdapterEditPart) getHost())
                                    .getModelAdapter();

                    if (adp instanceof NamedElementAdapter) {
                        return new EMFCommandWrapper(
                                getEditingDomain(),
                                a
                                        .getSetTargetCommand(getEditingDomain(),
                                                (EObject) ((ModelAdapterEditPart) getHost())
                                                        .getModel(),
                                                endAnchorPos));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the connection source object changed EMF command
     */
    protected Command getReconnectSourceCommand(ReconnectRequest req) {
        if (understandsRequest(req)) {
            ConnectionEditPart conn = req.getConnectionEditPart();
            if (conn instanceof BaseConnectionEditPart) {
                BaseConnectionAdapter a =
                        (BaseConnectionAdapter) ((BaseConnectionEditPart) conn)
                                .getModelAdapter();

                if (getHost() instanceof NodeEditPart) {
                    ConnectionAnchor anchor =
                            ((NodeEditPart) getHost())
                                    .getSourceConnectionAnchor(req);

                    Double startAnchorPos = getFixedAnchorStartPos(anchor);

                    return new EMFCommandWrapper(
                            getEditingDomain(),
                            a
                                    .getSetSourceCommand(getEditingDomain(),
                                            (EObject) ((ModelAdapterEditPart) getHost())
                                                    .getModel(),
                                            startAnchorPos));

                }
            }
        }
        return null;
    }

    /**
     * Return the user-fixed start anchor position or null if none set.
     * 
     * @param req
     * @return
     */
    private Double getFixedAnchorStartPos(ConnectionAnchor anchor) {
        Double startAnchorPos = null;

        if (anchor instanceof IFixedLocationAnchor) {
            IFixedLocationAnchor fAnch = (IFixedLocationAnchor) anchor;

            if (fAnch.isInFixedLocation()) {
                startAnchorPos = fAnch.getAnchorPosAsPortion();
            }
        }

        return startAnchorPos;
    }

    /**
     * Create the dummy connection figure to
     */
    protected Connection createDummyConnection(Request req) {
        Object connType = getConnectionType(req);
        connectionRouter = null;
        PolylineConnection conn = null;

        if (connType == SequenceFlowAdapter.class) {
            conn = new SequenceFlowFigure();
            ((SequenceFlowFigure) conn).setShowConstraints(true);

            SequenceFlowType flowType =
                    (SequenceFlowType) req.getExtendedData()
                            .get(PaletteFactory.EXTDATA_SEQUENCEFLOW_TYPE);
            if (flowType != null) {
                ((SequenceFlowFigure) conn).setFlowType(flowType);
            }

        } else if (connType == MessageFlowAdapter.class) {
            conn = new MessageFlowFigure();
            ((MessageFlowFigure) conn).setShowConstraints(true);

        } else if (connType == AssociationAdapter.class) {
            conn = new AssociationConnectionFigure();

        }

        conn.setLineStyle(SWT.LINE_DASH);

        conn.setForegroundColor(ColorConstants.lightGray);
        conn.setLineWidth(2);

        Map extData = req.getExtendedData();
        if (extData != null) {
            extData.put(FEEDBACK_FIGURE_KEY, conn);
        }

        return (conn);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getFeedbackHelper
     * (org.eclipse.gef.requests.CreateConnectionRequest)
     */
    @Override
    protected FeedbackHelper getFeedbackHelper(CreateConnectionRequest request) {
        FeedbackHelper helper = super.getFeedbackHelper(request);
        // Always reset the router for association edit parts as they might be
        // changing between flow type (compensation association) direct routing
        // (assosiacte artifact with activity etc
        Object connType = getConnectionType(request);

        if (connType == AssociationAdapter.class) {
            connectionFeedback
                    .setConnectionRouter(getDummyConnectionRouter(request));
        }

        return helper;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getDummyConnectionRouter
     * (org.eclipse.gef.requests.CreateConnectionRequest)
     */
    protected ConnectionRouter getDummyConnectionRouter(
            CreateConnectionRequest req) {

        Object connType = getConnectionType(req);

        if (connType == SequenceFlowAdapter.class) {
            connectionRouter = new BpmnFlowRouter();

        } else if (connType == MessageFlowAdapter.class) {
            connectionRouter = new BpmnFlowRouter();

        } else if (connType == AssociationAdapter.class) {
            if (req instanceof CreateConnectionRequest) {
                CreateConnectionRequest creq = (CreateConnectionRequest) req;

                boolean isCompensationAssociation = false;

                if (creq.getSourceEditPart() instanceof EventEditPart) {
                    EventEditPart eventEP =
                            (EventEditPart) creq.getSourceEditPart();

                    if (eventEP.isAttachedToTaskBorder()
                            && eventEP.getEventTriggerType() == EventTriggerType.EVENT_COMPENSATION_CATCH) {
                        EditPart tgt = creq.getTargetEditPart();

                        if (tgt instanceof BaseFlowNodeEditPart) {

                            isCompensationAssociation = true;
                        }
                    }
                }

                if (isCompensationAssociation) {
                    if (!(connectionRouter instanceof BpmnFlowRouter)) {
                        connectionRouter = new BpmnFlowRouter();
                    }
                } else {
                    if (!(connectionRouter instanceof BpmnDirectRouter)) {
                        connectionRouter = new BpmnDirectRouter();
                    }
                }

            } else {
                connectionRouter = null;
            }
        }

        return (connectionRouter);
    }

    /**
     * This override of showSourceFeedback discovers whether a new bend point
     * has been added/removed during connection creation (via right-click) and
     * adds it to the list of bendpopints in connection figure.
     */
    public void showSourceFeedback(Request request) {
        if (connectionFeedback != null) {
            //
            // If current command cannot be executed then change feed back
            // slightly.

            Map extData = request.getExtendedData();

            if (extData != null) {
                Point newBend = (Point) extData.get(NEW_BENDPOINT_KEY);

                if (newBend != null) {
                    // We have been given a new bendpoint position by the tool.

                    // Convert this to a bendpoint to same co-ordinate system as
                    // feedback connection.
                    // and add it to feedback connection constraints.
                    Point rel = newBend.getCopy();
                    connectionFeedback.translateToRelative(rel);

                    AbsoluteBendpoint bP = new AbsoluteBendpoint(rel);

                    List constraints =
                            (List) connectionFeedback.getRoutingConstraint();
                    if (constraints == null) {
                        constraints = new ArrayList();
                    }

                    constraints.add(bP);
                    connectionFeedback.setRoutingConstraint(constraints);

                    // Then add it to our list of bend points created during
                    // connection creation.
                    List bendOffsets =
                            (List) extData.get(NEW_BENDPOINT_OFFSETS_KEY);
                    if (bendOffsets == null) {
                        bendOffsets = new ArrayList();
                    }

                    // When storing convert bend offsets in 'whole process'
                    // co-ordinates.

                    IFigure connectionLayer =
                            getLayer(LayerConstants.CONNECTION_LAYER);
                    if (connectionLayer != null) {
                        // Translate everything to 'top left of process
                        // relative'
                        Point start =
                                connectionFeedback.getSourceAnchor()
                                        .getReferencePoint();
                        connectionLayer.translateToRelative(start);

                        Point bend = newBend.getCopy();
                        connectionLayer.translateToRelative(bend);

                        Dimension offset =
                                new Dimension(bend.x - start.x, bend.y
                                        - start.y);
                        bendOffsets.add(offset);
                    }

                    extData.put(NEW_BENDPOINT_OFFSETS_KEY, bendOffsets);

                    // Remove the new bend point so we don't add it again.
                    extData.remove(NEW_BENDPOINT_KEY);
                }

                // If tool has told us to delete last bendpoint...
                Boolean delLastBend =
                        (Boolean) extData.get(DEL_LAST_BENDPOINT_KEY);

                if (delLastBend != null) {
                    if (delLastBend.booleanValue()) {
                        if (connectionFeedback != null) {
                            List constraints =
                                    (List) connectionFeedback
                                            .getRoutingConstraint();

                            if (constraints != null && constraints.size() > 0) {
                                constraints.remove(constraints.size() - 1);
                            }
                        }

                        List bendOffsets =
                                (List) extData.get(NEW_BENDPOINT_OFFSETS_KEY);
                        if (bendOffsets != null && bendOffsets.size() > 0) {
                            bendOffsets.remove(bendOffsets.size() - 1);
                        }
                    }
                }

                // Remove the flag so we don't do it again.
                extData.remove(DEL_LAST_BENDPOINT_KEY);

            }

            ((PolylineConnection) connectionFeedback)
                    .setForegroundColor(ColorConstants.lightGray);
            if (REQ_CONNECTION_END.equals(request.getType())) {
                CreateConnectionRequest creq =
                        (CreateConnectionRequest) request;

                if (creq.getTargetEditPart() != null) {
                    // ((PolylineConnection)connectionFeedback).setLineWidth(1);
                    ((PolylineConnection) connectionFeedback)
                            .setForegroundColor(ColorConstants.gray);
                }
            }

        }

        super.showSourceFeedback(request);
    }

    /**
     * Overridden to highlight border of objects.
     * 
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
     */
    public void eraseTargetFeedback(Request request) {
        IFigure fig = getHostFigure();
        if (fig instanceof IHighlightableFigure) {
            ((IHighlightableFigure) fig).setHighlight(false);
        }

        if (startAnchorPositionFigure != null) {
            if (startAnchorPositionFigure.isVisible()) {
                startAnchorPositionFigure.setVisible(false);
            }
        }
    }

    /**
     * Overridden to highlight border of objects.
     * 
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org.eclipse.gef.Request)
     */
    public void showTargetFeedback(Request request) {
        boolean highlight = false;
        Point borderConnectionPoint = null;

        if (REQ_SELECTION.equals(request.getType())
                || REQ_SELECTION_HOVER.equals(request.getType())) {
            highlight = true;
        }
        if (getHost() instanceof NodeEditPart) {
            if (REQ_CONNECTION_START.equals(request.getType())
                    || REQ_RECONNECT_SOURCE.equals(request.getType())) {
                // On potential connection start, if we are in a position
                // that will create a fixed border anchor point,
                // highlight where that will be.
                ConnectionAnchor anchor =
                        ((NodeEditPart) getHost())
                                .getSourceConnectionAnchor(request);

                if (anchor instanceof IFixedLocationAnchor) {
                    if (((IFixedLocationAnchor) anchor).isInFixedLocation()) {
                        // This is a fixed location anchor.
                        borderConnectionPoint =
                                anchor.getLocation(((DropRequest) request)
                                        .getLocation().getCopy());
                    }
                }

                highlight = true;

            } else if (REQ_CONNECTION_END.equals(request.getType())
                    || REQ_RECONNECT_TARGET.equals(request.getType())) {
                // On potential connection end, if we are in a position
                // that will create a fixed border anchor point,
                // highlight where that will be.
                ConnectionAnchor anchor =
                        ((NodeEditPart) getHost())
                                .getTargetConnectionAnchor(request);

                if (anchor instanceof IFixedLocationAnchor) {
                    if (((IFixedLocationAnchor) anchor).isInFixedLocation()) {
                        // This is a fixed location anchor.
                        borderConnectionPoint =
                                anchor.getLocation(((DropRequest) request)
                                        .getLocation().getCopy());
                    }
                }

                highlight = true;
            }
        }

        IFigure fig = getHostFigure();

        // Don't override the event on border highlight.
        if (request.getExtendedData()
                .get(FlowContainerXYLayoutEditPolicy.EVENT_ON_BORDER) == null) {
            if (fig instanceof IHighlightableFigure) {
                ((IHighlightableFigure) fig).setHighlight(highlight);
            }
        }

        if (borderConnectionPoint != null) {
            if (startAnchorPositionFigure == null) {
                startAnchorPositionFigure = new AnchorPositionFeedbackFigure();
                startAnchorPositionFigure.setVisible(false);
                getFeedbackLayer().add(startAnchorPositionFigure);
            }

            getFeedbackLayer().translateToRelative(borderConnectionPoint);
            startAnchorPositionFigure.setCenterLocation(borderConnectionPoint);
            startAnchorPositionFigure.setVisible(true);
        } else {
            if (startAnchorPositionFigure != null) {
                if (startAnchorPositionFigure.isVisible()) {
                    startAnchorPositionFigure.setVisible(false);
                }
            }
        }
    }

}
