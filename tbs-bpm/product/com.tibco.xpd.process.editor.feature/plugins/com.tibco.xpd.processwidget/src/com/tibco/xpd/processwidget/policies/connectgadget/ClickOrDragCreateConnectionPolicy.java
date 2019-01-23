/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.connectgadget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.commands.internal.RevealReferencedEditPartCommand;
import com.tibco.xpd.processwidget.figures.AssociationConnectionFigure;
import com.tibco.xpd.processwidget.figures.BpmnDirectRouter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.MessageFlowFigure;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.AssociationEditPart;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.MessageFlowEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.BpmnClickOrDragGadgetAnchorFactory;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.policies.ShapeWithDescCrossReferenceClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.ShapeWithDescGenericClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo.GADGET_SHAPE;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.CrossReferenceClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.GadgetHandleFeedback;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click/Drag gadget policy that displays a gadget(s) that allows the user to...
 * <p>
 * <li>Drag to valid target object to create a connection of appropriate type to
 * it.</li>
 * <li>Goto any of the existing objects that connect to / from the selected
 * object</li>
 * <li>Drag to valid container / sequence flow to create a new object to connect
 * to and connect to it - user selects object type via popup menu. (available
 * since 3.3)</li>
 * </p>
 * <br/>
 * 
 * aaaaa
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragCreateConnectionPolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "connection.click.gadget.policy"; //$NON-NLS-1$

    private static final String CREATE_CONNECTION_GADGET_TYPE =
            "CREATE_CONNECTION_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    private PolylineConnection feedbackConnection = null;

    private EditPart targetForFeedbackConnection = null;

    private CreateAndConnectFeedback createAndConnectFeedback = null;

    private XpdSnapFeedbackPolicy alignmentFeedbackHelper =
            new XpdSnapFeedbackPolicy();

    public ClickOrDragCreateConnectionPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(editingDomain, BpmnClickOrDragGadgetAnchorFactory.INSTANCE,
                policyEnabledmentProvider, EDIT_POLICY_ID);
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    @Override
    protected Command createGadgetClickedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (creq.getClickOrDragGadgetInfo() instanceof CrossReferenceClickOrDragGadgetInfo) {
            if (CREATE_CONNECTION_GADGET_TYPE.equals(clickOrDragInfoType)) {
                CrossReferenceClickOrDragGadgetInfo xRefInfo =
                        (CrossReferenceClickOrDragGadgetInfo) creq
                                .getClickOrDragGadgetInfo();

                return new RevealReferencedEditPartCommand(
                        (GraphicalEditPart) creq.getHostEditPart(),
                        xRefInfo.getReferencedEditPart());
            }
        }
        return null;
    }

    @Override
    protected boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CREATE_CONNECTION_GADGET_TYPE.equals(clickOrDragInfoType)) {
            return true;
        }
        return false;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CREATE_CONNECTION_GADGET_TYPE.equals(clickOrDragInfoType)) {
            Command cmd = UnexecutableCommand.INSTANCE;

            /*
             * Check for drag over a valid container for new objects container.
             * 
             * If the target edit part is process or root edit part then we need
             * to redirect the target to an appropriate lane.
             */
            EditPart actualTarget =
                    CreateAndConnectGadgetHelper.INSTANCE
                            .getCreateAndConnectActualTarget(creq
                                    .getDragTarget(), creq.getLocation());

            Collection<CreateAndConnectObjectPair> createAndConnectTypes =
                    getCreateAndConnectTypes(creq, actualTarget);

            if (createAndConnectTypes != null
                    && !createAndConnectTypes.isEmpty()) {
                cmd =
                        getCreateAndConnectObjectCommand((BaseGraphicalEditPart) getHost(),
                                (ModelAdapterEditPart) actualTarget,
                                createAndConnectTypes,
                                creq);
            } else {
                /*
                 * Decide on connection type to create (if any) between source
                 * and target.
                 */
                Class connType = getConnectionType(creq);
                if (connType == SequenceFlowEditPart.class) {
                    cmd = getCreateSequenceFlowCommand(creq);

                } else if (connType == MessageFlowEditPart.class) {
                    cmd = getCreateMessageFlowCommand(creq);

                } else if (connType == AssociationEditPart.class) {
                    cmd = getCreateAssociationCommand(creq);

                }
            }

            return cmd;
        }

        return null;
    }

    /**
     * For teh given request return a list of possible create object and
     * connection pairs that are applicable.
     * 
     * @param creq
     * @param actualTarget
     * 
     * @return list of possible create object and connection pairs that are
     *         applicable.
     */
    private Collection<CreateAndConnectObjectPair> getCreateAndConnectTypes(
            ClickOrDragGadgetRequest creq, EditPart actualTarget) {

        if (getHost() instanceof BaseGraphicalEditPart
                && actualTarget instanceof ModelAdapterEditPart) {

            return CreateAndConnectGadgetHelper.INSTANCE
                    .getCreateAndConnectObjectPairs(getHost(),
                            actualTarget,
                            creq.getLocation());
        }

        return null;
    }

    /**
     * @param source
     * @param target
     * @param createAndConnectTypes
     * 
     * @return Command to produce a popup menu giving user appropriate options
     *         to create and connect to new objects of appropriate type for
     *         source of connection (host).
     */
    private Command getCreateAndConnectObjectCommand(
            BaseGraphicalEditPart source, ModelAdapterEditPart target,
            Collection<CreateAndConnectObjectPair> createAndConnectTypes,
            ClickOrDragGadgetRequest creq) {

        BaseGraphicalEditPart targetContainer =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getTargetContainer(target, creq.getLocation());

        if (targetContainer != null) {
            if (source.getParent() instanceof TaskEditPart) {
                alignmentFeedbackHelper.setHost(source.getParent());
            } else {
                alignmentFeedbackHelper.setHost(source.getRoot());
            }

            return new CreateAndConnectObjectPopupCommand(
                    (BaseGraphicalEditPart) getHost(), target,
                    createAndConnectTypes, creq);

        }

        return null;
    }

    /**
     * @param source
     * @param target
     * 
     * @return GEF Command to connect the source and target with a sequence
     *         flow.
     */
    private Command getCreateSequenceFlowCommand(ClickOrDragGadgetRequest creq) {
        Command cmd = UnexecutableCommand.INSTANCE;

        EditPart source = creq.getHostEditPart();
        EditPart target = creq.getDragTarget();

        if (source instanceof BaseFlowNodeEditPart
                && target instanceof BaseFlowNodeEditPart
                && !isStartEvent(target)) {
            BaseFlowNodeEditPart sourceNode = (BaseFlowNodeEditPart) source;
            BaseFlowNodeEditPart targetNode = (BaseFlowNodeEditPart) target;

            // Can't do sequence flow connections in task librarys
            if (!ProcessWidgetType.TASK_LIBRARY.equals(sourceNode
                    .getProcessWidgetType())) {
                if (sourceNode.getModelAdapter() instanceof FlowNodeAdapter) {
                    FlowNodeAdapter adapter =
                            (FlowNodeAdapter) sourceNode.getModelAdapter();

                    Double endAnchorPos = getTargetAnchor(creq, targetNode);

                    cmd =
                            new EMFCommandWrapper(
                                    editingDomain,

                                    adapter.getCreateSequenceFlowCommand(editingDomain,
                                            (EObject) targetNode.getModel(),
                                            SequenceFlowType.UNCONTROLLED_LITERAL,
                                            "", //$NON-NLS-1$
                                            "", //$NON-NLS-1$
                                            Collections.emptyList(),
                                            null,
                                            endAnchorPos,
                                            "", //$NON-NLS-1$
                                            new ConnectionLabelPosition(),
                                            ProcessWidgetColors
                                                    .getInstance()
                                                    .getGraphicalNodeColor(null,
                                                            ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE)
                                                    .toString()));

                }
            }
        }
        return cmd;
    }

    /**
     * @param creq
     * @param targetNode
     * @return
     */
    private Double getTargetAnchor(ClickOrDragGadgetRequest creq,
            EditPart targetNode) {
        Double endAnchorPos = null;
        if (targetNode instanceof NodeEditPart) {
            ConnectionAnchor targetAnchor =
                    ((NodeEditPart) targetNode).getTargetConnectionAnchor(creq);
            if (targetAnchor instanceof IFixedLocationAnchor) {
                IFixedLocationAnchor fixedAnchor =
                        (IFixedLocationAnchor) targetAnchor;
                if (fixedAnchor.isInFixedLocation()) {
                    endAnchorPos = fixedAnchor.getAnchorPosAsPortion();
                }
            }
        }
        return endAnchorPos;
    }

    /**
     * @param ep
     * @return true is edit part is a start event.
     */
    private boolean isStartEvent(EditPart ep) {
        if (ep instanceof EventEditPart) {
            EventEditPart eep = (EventEditPart) ep;

            if (EventFlowType.FLOW_START == eep.getEventFlowType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param source
     * @param target
     * @return GEF Command to connect the source and target with a message flow.
     */
    private Command getCreateMessageFlowCommand(ClickOrDragGadgetRequest creq) {
        Command cmd = UnexecutableCommand.INSTANCE;

        EditPart source = creq.getHostEditPart();
        EditPart target = creq.getDragTarget();

        if (source instanceof BaseFlowNodeEditPart
                && target instanceof BaseFlowNodeEditPart) {

            BaseFlowNodeEditPart sourceNode = (BaseFlowNodeEditPart) source;
            BaseFlowNodeEditPart targetNode = (BaseFlowNodeEditPart) target;

            if (!ProcessWidgetType.TASK_LIBRARY.equals(sourceNode
                    .getProcessWidgetType())) {
                if (sourceNode.getModelAdapter() instanceof MessageFlowNodeAdapter) {
                    MessageFlowNodeAdapter adapter =
                            (MessageFlowNodeAdapter) sourceNode
                                    .getModelAdapter();

                    Double endAnchorPos = getTargetAnchor(creq, target);

                    cmd =
                            new EMFCommandWrapper(
                                    editingDomain,

                                    adapter.getCreateMessageFlowCommand(editingDomain,
                                            (EObject) targetNode.getModel(),
                                            Collections.emptyList(),
                                            null,
                                            endAnchorPos,
                                            ProcessWidgetColors
                                                    .getInstance()
                                                    .getGraphicalNodeColor(null,
                                                            ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE)
                                                    .toString()));
                }
            }
        }

        return cmd;
    }

    /**
     * @param source
     * @param target
     * @return GEF Command to connect the source and target with an association
     *         connection.
     */
    private Command getCreateAssociationCommand(ClickOrDragGadgetRequest creq) {
        Command cmd = UnexecutableCommand.INSTANCE;

        EditPart dragSource = creq.getHostEditPart();
        EditPart dragTarget = creq.getDragTarget();

        if (dragSource instanceof BaseGraphicalEditPart) {
            BaseGraphicalEditPart gepSrc = (BaseGraphicalEditPart) dragSource;

            if (gepSrc.getModelAdapter() instanceof BaseGraphicalNodeAdapter) {
                BaseGraphicalNodeAdapter adapter =
                        (BaseGraphicalNodeAdapter) gepSrc.getModelAdapter();

                // Can only create association between artifacts and flow
                // node or sequenceflow or message flow.
                if (isValidAssociationPair(dragSource, dragTarget)) {

                    Double startAnchorPos = null;
                    if ((dragTarget instanceof SequenceFlowEditPart)
                            || (dragTarget instanceof MessageFlowEditPart)) {
                        startAnchorPos = new Double(50.0);
                    }

                    Double endAnchorPos = getTargetAnchor(creq, dragTarget);
                    if (endAnchorPos == null) {
                        if ((dragTarget instanceof SequenceFlowEditPart)
                                || (dragTarget instanceof MessageFlowEditPart)) {
                            endAnchorPos = new Double(50.0);
                        }
                    }

                    cmd =
                            new EMFCommandWrapper(
                                    editingDomain,
                                    adapter.getCreateAssociationCommand(editingDomain,
                                            (EObject) dragTarget.getModel(),
                                            Collections.EMPTY_LIST,
                                            startAnchorPos,
                                            endAnchorPos,
                                            ProcessWidgetColors
                                                    .getInstance()
                                                    .getGraphicalNodeColor(null,
                                                            ProcessWidgetColors.ASSOCIATION_LINE)
                                                    .toString()));

                }
            }
        }

        return cmd;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {

        List<AbstractClickOrDragGadgetInfo> infos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();

        if (getHost() instanceof BaseGraphicalEditPart) {
            BaseGraphicalEditPart host = (BaseGraphicalEditPart) getHost();

            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_CREATECONN_GADGET_ICON);

            List sourceConnections = host.getSourceConnections();
            List targetConnections = host.getTargetConnections();

            //
            // Add infos for outgoing connections
            for (Iterator iterator = sourceConnections.iterator(); iterator
                    .hasNext();) {
                EditPart ep = (EditPart) iterator.next();
                if (ep instanceof BaseConnectionEditPart) {
                    BaseConnectionEditPart conn = (BaseConnectionEditPart) ep;

                    if (conn.getTarget() instanceof BaseGraphicalEditPart) {
                        BaseGraphicalEditPart target =
                                (BaseGraphicalEditPart) conn.getTarget();

                        String name = null;
                        if (target.getModelAdapter() instanceof NamedElementAdapter) {
                            name =
                                    ((NamedElementAdapter) target
                                            .getModelAdapter()).getName();
                        }

                        if (name == null || name.length() == 0) {
                            name =
                                    Messages.ClickOrDragCreateConnectionPolicy_UnnamedObject_label;
                        }

                        ShapeWithDescCrossReferenceClickOrDragGadgetInfo info =
                                new CreateConnectionWithCrossRefGadget(
                                        CREATE_CONNECTION_GADGET_TYPE,
                                        target,
                                        String.format(Messages.ClickOrDragCreateConnectionPolicy_GotoOutgoingTarget_tooltip,
                                                name),
                                        gadgetImage,
                                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE);
                        info.setGadgetShape(GADGET_SHAPE.CIRCLE);
                        infos.add(info);
                    }

                }
            }

            //
            // Add infos for incoming connections
            for (Iterator iterator = targetConnections.iterator(); iterator
                    .hasNext();) {
                EditPart ep = (EditPart) iterator.next();
                if (ep instanceof BaseConnectionEditPart) {
                    BaseConnectionEditPart conn = (BaseConnectionEditPart) ep;

                    if (conn.getSource() instanceof BaseGraphicalEditPart) {
                        BaseGraphicalEditPart source =
                                (BaseGraphicalEditPart) conn.getSource();

                        String name = null;
                        if (source.getModelAdapter() instanceof NamedElementAdapter) {
                            name =
                                    ((NamedElementAdapter) source
                                            .getModelAdapter()).getName();
                        }

                        if (name == null || name.length() == 0) {
                            name =
                                    Messages.ClickOrDragCreateConnectionPolicy_UnnamedObject_label;
                        }

                        ShapeWithDescCrossReferenceClickOrDragGadgetInfo info =
                                new CreateConnectionWithCrossRefGadget(
                                        CREATE_CONNECTION_GADGET_TYPE,
                                        source,
                                        String.format(Messages.ClickOrDragCreateConnectionPolicy_GotoIncoming_tooltip,
                                                name),
                                        gadgetImage,
                                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE);
                        info.setGadgetShape(GADGET_SHAPE.CIRCLE);
                        infos.add(info);
                    }
                }
            }

            //
            // If there are no gadgets for existing connections then create one
            // for creating first connection.
            if (infos.isEmpty()) {
                infos.add(new CreateConnectionGadgetInfo(
                        CREATE_CONNECTION_GADGET_TYPE,
                        Messages.ClickOrDragCreateConnectionPolicy_DragDropToCreateConnection_tooltip,
                        gadgetImage, GADGET_SHAPE.CIRCLE));

            }
        }

        return infos;
    }

    /**
     * @param creq
     * @return true if given request will generate a command to do a
     *         create-object-and-connect.
     */
    protected boolean isCreateAndConnect(ClickOrDragGadgetRequest creq) {
        EditPart actualTarget =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getCreateAndConnectActualTarget(creq.getDragTarget(),
                                creq.getLocation());

        Collection<CreateAndConnectObjectPair> createAndConnectTypes =
                getCreateAndConnectTypes(creq, actualTarget);

        if (createAndConnectTypes != null && !createAndConnectTypes.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * If the request amount to a create-object-and-connect request then adjust
     * the location to the appropriate snap position.
     * 
     * @param creq
     */
    private void adjustLocationForSnap(ClickOrDragGadgetRequest creq) {
        if (isCreateAndConnect(creq)) {
            Point snapLocation =
                    CreateAndConnectGadgetHelper.getSnapLocation(creq,
                            new Dimension(2, 2));

            creq.setLocation(snapLocation);
        }

        return;
    }

    @Override
    public void showSourceFeedback(Request request) {
        super.showSourceFeedback(request);

        // If we've dragged gadget onto a valid target part then show a proper
        // feedback figure.
        if (request instanceof ClickOrDragGadgetRequest
                && CREATE_CONNECTION_GADGET_TYPE
                        .equals(((ClickOrDragGadgetRequest) request)
                                .getClickOrDragGadgetInfo()
                                .getClickOrDragRequestType())
                && ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                        .equals(request.getType())) {

            ClickOrDragGadgetRequest creq = (ClickOrDragGadgetRequest) request;

            /*
             * Check whether feedback should be for create and connect...
             */
            EditPart actualTarget =
                    CreateAndConnectGadgetHelper.INSTANCE
                            .getCreateAndConnectActualTarget(creq
                                    .getDragTarget(), creq.getLocation());

            Collection<CreateAndConnectObjectPair> createAndConnectTypes =
                    getCreateAndConnectTypes(creq, actualTarget);

            if (createAndConnectTypes != null
                    && !createAndConnectTypes.isEmpty()) {
                removeConnectFeedback();

                addOrUpdateCreateAndConnectFeedback(creq,
                        actualTarget,
                        createAndConnectTypes);

            } else {
                removeCreateAndConnectFeedback();

                Class connType = getConnectionType(creq);

                if (connType != null) {
                    /*
                     * We're in a position for "create a connection" between
                     * source and existing target.
                     */

                    addOrUpdateConnectFeedBack(creq, connType);

                } else {
                    removeConnectFeedback();
                }
            }

            // If we are showing our own feedback line then hide the standard
            // one for gadget.
            if (feedbackConnection != null || createAndConnectFeedback != null) {

                GadgetHandleFeedback gadget = creq.getClickOrDragGadgetHandle();

                if (gadget != null && createAndConnectFeedback != null) {
                    gadget.setVisible(false);
                } else {
                    gadget.setVisible(true);
                }

                if (gadget != null && gadget.getDragLine().isVisible()) {
                    gadget.getDragLine().setVisible(false);
                }

            } else {
                GadgetHandleFeedback gadget = creq.getClickOrDragGadgetHandle();
                if (gadget != null) {
                    gadget.setVisible(true);
                    gadget.getDragLine().setVisible(true);
                }
            }

        } else {
            removeConnectFeedback();
            removeCreateAndConnectFeedback();
        }

        alignmentFeedbackHelper.showTargetFeedback(request);

        return;
    }

    /**
     * Add or update the "create a new object and connect to it" feedback
     * 
     * @param creq
     * @param actualTarget
     * @param createAndConnectTypes
     */
    private void addOrUpdateCreateAndConnectFeedback(
            ClickOrDragGadgetRequest creq, EditPart actualTarget,
            Collection<CreateAndConnectObjectPair> createAndConnectTypes) {

        if (createAndConnectFeedback == null) {
            ProcessWidgetImpl processWidget =
                    (ProcessWidgetImpl) getHost().getViewer()
                            .getProperty(ProcessWidgetConstants.PROP_WIDGET);

            ConnectionAnchor srcAnchor = null;
            if (getHost() instanceof NodeEditPart) {
                srcAnchor =
                        ((NodeEditPart) getHost())
                                .getSourceConnectionAnchor(creq);
                if (srcAnchor == null) {
                    srcAnchor = new ChopboxAnchor(getHostFigure());
                }
            }

            createAndConnectFeedback =
                    new CreateAndConnectFeedback(
                            processWidget.getFeedbackLayer(), creq, srcAnchor);

            createAndConnectFeedback.showFeedback(creq);

        } else {
            createAndConnectFeedback.updateFeedback(creq);
        }

        return;
    }

    /**
     * Add or update the "create a connection to existing object" feedback
     * 
     * @param creq
     * @param connType
     */
    private void addOrUpdateConnectFeedBack(ClickOrDragGadgetRequest creq,
            Class connType) {
        if (connType != Exception.class) {
            if (targetForFeedbackConnection != creq.getDragTarget()) {
                removeConnectFeedback();

                // And add a new one.
                targetForFeedbackConnection = creq.getDragTarget();

                feedbackConnection = createFeedbackConnection(creq, connType);
            }
        }

        if (feedbackConnection != null) {
            setFeedbackConnectionAnchors(creq, connType, feedbackConnection);
            feedbackConnection.revalidate();
        }

        return;
    }

    /**
     * @param creq
     */
    private PolylineConnection createFeedbackConnection(
            ClickOrDragGadgetRequest creq, Class connType) {
        PolylineConnection connection = null;

        //
        // Decide on connection type to create (if any) between source
        // and target.
        if (connType != null) {
            if (connType == AssociationEditPart.class) {
                connection = new AssociationConnectionFigure();
                connection.setConnectionRouter(new BpmnDirectRouter());
            } else if (connType == MessageFlowEditPart.class) {
                connection = new MessageFlowFigure();
                connection.setConnectionRouter(new BpmnFlowRouter());
            } else {
                connection = new SequenceFlowFigure();
                connection.setConnectionRouter(new BpmnFlowRouter());
            }

            connection.setForegroundColor(ColorConstants.gray);
            connection.setLineWidth(2);
            getFeedbackLayer().add(connection);
        }

        return connection;
    }

    private void setFeedbackConnectionAnchors(ClickOrDragGadgetRequest creq,
            Class connType, PolylineConnection connection) {
        ConnectionAnchor srcAnchor =
                ((NodeEditPart) getHost()).getSourceConnectionAnchor(creq);
        if (srcAnchor == null) {
            srcAnchor = new ChopboxAnchor(getHostFigure());
        }

        ConnectionAnchor tgtAnchor =
                ((NodeEditPart) creq.getDragTarget())
                        .getTargetConnectionAnchor(creq);
        if (tgtAnchor == null) {
            tgtAnchor =
                    new ChopboxAnchor(
                            ((GraphicalEditPart) creq.getDragTarget())
                                    .getFigure());
        }

        connection.setSourceAnchor(srcAnchor);
        connection.setTargetAnchor(tgtAnchor);

        return;
    }

    @Override
    public void eraseSourceFeedback(Request request) {
        super.eraseSourceFeedback(request);

        removeConnectFeedback();

        removeCreateAndConnectFeedback();

        if (request instanceof ClickOrDragGadgetRequest
                && CREATE_CONNECTION_GADGET_TYPE
                        .equals(((ClickOrDragGadgetRequest) request)
                                .getClickOrDragGadgetInfo()
                                .getClickOrDragRequestType())
                && ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                        .equals(request.getType())) {

            ClickOrDragGadgetRequest creq = (ClickOrDragGadgetRequest) request;

            GadgetHandleFeedback gadget = creq.getClickOrDragGadgetHandle();
            if (gadget != null) {
                gadget.setVisible(true);

                gadget.getDragLine().setVisible(true);
            }

        }

        alignmentFeedbackHelper.eraseTargetFeedback(request);

        return;
    }

    /**
     * Remove the feedback for "Create a connection"
     */
    private void removeConnectFeedback() {
        if (feedbackConnection != null) {
            getFeedbackLayer().remove(feedbackConnection);
            feedbackConnection = null;
        }

        targetForFeedbackConnection = null;

        return;
    }

    /**
     * remove the "Create New Object and Connect" feedback
     */
    private void removeCreateAndConnectFeedback() {
        if (createAndConnectFeedback != null) {
            createAndConnectFeedback.eraseFeedback();
            createAndConnectFeedback = null;
        }

        return;
    }

    /**
     * Given the source and target objects return the class of the type of
     * connection to create.
     * 
     * @param dragSource
     * @param dragTarget
     * 
     * @return Either class of connection that is appropriate between the source
     *         and target object or null if 2 objects cannot be connected.
     */
    private Class getConnectionType(ClickOrDragGadgetRequest creq) {
        EditPart dragSource = getHost();
        EditPart dragTarget = creq.getDragTarget();

        Class retClass = null;

        if (dragSource != dragTarget) {

            if (EditPartUtil.isArtifactEditPart(dragSource)
                    || EditPartUtil.isArtifactEditPart(dragTarget)) {
                if (isValidAssociationPair(dragSource, dragTarget)) {
                    retClass = AssociationEditPart.class;
                }

            } else if (dragSource instanceof BaseFlowNodeEditPart
                    && dragTarget instanceof BaseFlowNodeEditPart) {
                BaseFlowNodeEditPart sourceNode =
                        (BaseFlowNodeEditPart) dragSource;
                BaseFlowNodeEditPart targetNode =
                        (BaseFlowNodeEditPart) dragTarget;

                if (sourceNode.getParentPool() == targetNode.getParentPool()) {
                    if (!(sourceNode instanceof EventEditPart)
                            || ((EventEditPart) sourceNode).getEventFlowType() != EventFlowType.FLOW_END) {

                        if (sourceNode.getParent() instanceof TaskEditPart
                                || targetNode.getParent() instanceof TaskEditPart) {
                            /*
                             * If src or tgt is in embedded sub-proc then both
                             * must be in same one (seq flow can't cross emb
                             * subproc boundary.
                             */
                            if (sourceNode.getParent() == targetNode
                                    .getParent()) {
                                if (EditPartUtil
                                        .isAttachedCatchCompensation(sourceNode)) {
                                    retClass = AssociationEditPart.class;
                                } else {
                                    retClass = SequenceFlowEditPart.class;
                                }
                            }

                        } else {
                            /*
                             * If neither are in emb subproc then both are in
                             * lanes and are valid to be connected.
                             */
                            if (EditPartUtil
                                    .isAttachedCatchCompensation(sourceNode)) {
                                retClass = AssociationEditPart.class;
                            } else {
                                retClass = SequenceFlowEditPart.class;
                            }
                        }
                    }

                } else {
                    retClass = MessageFlowEditPart.class;
                }

                if (retClass == SequenceFlowEditPart.class
                        || retClass == MessageFlowEditPart.class) {
                    // Only associations are allowed in Task Library
                    if (ProcessWidgetType.TASK_LIBRARY.equals(sourceNode
                            .getProcessWidgetType())) {
                        retClass = null;
                    }
                }
            }
        }

        if (retClass != null && dragSource instanceof GraphicalEditPart
                && dragTarget instanceof GraphicalEditPart) {
            // Finally just do a quick check to ensure that there is not
            // already a connection between the 2 types., if there is then
            // don't create another duplicate one.

            GraphicalEditPart gepSource = (GraphicalEditPart) dragSource;
            GraphicalEditPart gepTarget = (GraphicalEditPart) dragTarget;
            List existingConnections = gepSource.getSourceConnections();
            for (Object o : existingConnections) {
                if (o instanceof ConnectionEditPart) {
                    ConnectionEditPart conn = (ConnectionEditPart) o;

                    if (conn.getTarget() == gepTarget) {
                        retClass = null;
                        break;
                    }
                }
            }
        }

        return retClass;
    }

    /**
     * @param source
     * @param target
     * @return true if the pair of objects can validly have an association
     *         connection between them.
     */
    private boolean isValidAssociationPair(EditPart source, EditPart target) {
        if (EditPartUtil.isArtifactEditPart(source)) {
            if ((target instanceof BaseFlowNodeEditPart
                    || (target instanceof SequenceFlowEditPart) || (target instanceof MessageFlowEditPart))) {
                return true;
            }
        } else if ((source instanceof BaseFlowNodeEditPart
                || (source instanceof SequenceFlowEditPart) || (source instanceof MessageFlowEditPart))) {
            if (EditPartUtil.isArtifactEditPart(target)) {
                return true;
            }

            if (target instanceof BaseFlowNodeEditPart) {
                if (source instanceof EventEditPart) {
                    EventEditPart ev = (EventEditPart) source;
                    if (ev.isAttachedToTaskBorder()) {
                        if (EventFlowType.FLOW_INTERMEDIATE == ev
                                .getEventFlowType()
                                && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                        .getEventTriggerType()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * CreateConnectionWithCrossRefGadget
     * 
     * 
     * @author aallway
     * @since 3.3 (1 Sep 2009)
     */
    private final class CreateConnectionWithCrossRefGadget extends
            ShapeWithDescCrossReferenceClickOrDragGadgetInfo {
        /**
         * @param clickOrDragRequestType
         * @param referencedEditPart
         * @param description
         * @param referenceTypeImage
         * @param bpmnClickOrDragGadgetAnchorFactory
         */
        private CreateConnectionWithCrossRefGadget(
                String clickOrDragRequestType,
                GraphicalEditPart referencedEditPart,
                String description,
                Image referenceTypeImage,
                BpmnClickOrDragGadgetAnchorFactory bpmnClickOrDragGadgetAnchorFactory) {
            super(clickOrDragRequestType, referencedEditPart, description,
                    referenceTypeImage, bpmnClickOrDragGadgetAnchorFactory);
        }

        @Override
        public void adjustLocation(ClickOrDragGadgetRequest creq) {
            adjustLocationForSnap(creq);
        }
    }

    /**
     * CreateConnectionGadgetInfo
     * 
     * 
     * @author aallway
     * @since 3.3 (1 Sep 2009)
     */
    private final class CreateConnectionGadgetInfo extends
            ShapeWithDescGenericClickOrDragGadgetInfo {
        /**
         * @param clickOrDragRequestType
         * @param description
         * @param referenceTypeImage
         * @param gadgetShape
         */
        private CreateConnectionGadgetInfo(String clickOrDragRequestType,
                String description, Image referenceTypeImage,
                GADGET_SHAPE gadgetShape) {
            super(clickOrDragRequestType, description, referenceTypeImage,
                    gadgetShape);
        }

        @Override
        public void adjustLocation(ClickOrDragGadgetRequest creq) {
            adjustLocationForSnap(creq);
        }

    }

    private static class XpdSnapFeedbackPolicy extends SnapFeedbackPolicy {

        /**
         * @see org.eclipse.gef.editpolicies.SnapFeedbackPolicy#showTargetFeedback(org.eclipse.gef.Request)
         * 
         * @param req
         */
        @Override
        public void showTargetFeedback(Request req) {
            if (ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_DRAGGED
                    .equals(req.getType())
                    && req instanceof ClickOrDragGadgetRequest
                    && getHost() != null) {

                /* Kid the snap feedback into thing we're a request it handles. */
                if (((ClickOrDragGadgetRequest) req).isSnappedToLocation()) {
                    Request newReq = new Request(RequestConstants.REQ_MOVE);
                    newReq.setExtendedData(req.getExtendedData());
                    super.showTargetFeedback(newReq);

                    return;
                }
            }

            super.eraseTargetFeedback(req);
        }
    }
}
