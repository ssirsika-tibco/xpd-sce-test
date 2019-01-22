package com.tibco.xpd.processwidget.policies.connectgadget;

import java.util.Collection;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectConnectionType;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectObjectType;
import com.tibco.xpd.processwidget.figures.AssociationConnectionFigure;
import com.tibco.xpd.processwidget.figures.BpmnDirectRouter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.figures.anchors.TaskActivityAnchor;
import com.tibco.xpd.processwidget.parts.DataObjectEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;

/**
 * CreateAndConnectFeedback
 * <p>
 * Create new object and connect to it feed back controller.
 * 
 * @author aallway
 * @since 3.3 (21 Aug 2009)
 */
public class CreateAndConnectFeedback {

    private IFigure objectFigure;

    private PolylineConnection connectionFigure;

    private IFigure feedbackLayer;

    private ConnectionAnchor sourceConnectionAnchor;

    private boolean currFeedBackIsForAssocConnOnly = false;

    private boolean currFeedBackIsForArtifactOnly = false;

    private PointList artifactFigurePoints = null;

    public CreateAndConnectFeedback(IFigure feedbackLayer,
            ClickOrDragGadgetRequest clickOrDragGadgetRequest,
            ConnectionAnchor sourceConnectionAnchor) {
        this.sourceConnectionAnchor = sourceConnectionAnchor;

        this.feedbackLayer = feedbackLayer;
    }

    /**
     * @param feedbackLayer
     * @param creq
     * @param sourceConnectionAnchor
     */
    private void createFeedbackFigures(ClickOrDragGadgetRequest creq) {

        EditPart actualTarget =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getCreateAndConnectActualTarget(creq.getDragTarget(),
                                creq.getLocation());
        Collection<CreateAndConnectObjectPair> createAndConnectTypes =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getCreateAndConnectObjectPairs(creq.getHostEditPart(),
                                actualTarget,
                                creq.getLocation());

        createObjectFigure(feedbackLayer, createAndConnectTypes);

        if (sourceConnectionAnchor != null) {
            createConnectionFigure(sourceConnectionAnchor,
                    createAndConnectTypes);
        }

        return;
    }

    /**
     * @param feedbackLayer
     * @param absLocation
     * @param createAndConnectTypes
     */
    private void createObjectFigure(IFigure feedbackLayer,
            Collection<CreateAndConnectObjectPair> createAndConnectTypes) {
        // use isOnlyAssociationConenctions/Artifacts() to decide whether to use
        // association edit part feedback

        if (isOnlyArtifactTarget(createAndConnectTypes)) {
            Dimension dataObjectSize =
                    new Dimension(ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                            ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE);

            artifactFigurePoints =
                    DataObjectEditPart
                            .getDataObjectFigurePointlist(dataObjectSize);

            Polygon fig = new Polygon();
            fig.setFill(true);
            objectFigure = fig;

            fig.setPoints(artifactFigurePoints);

            FigureUtilities.makeGhostShape((Shape) objectFigure);
            ((Shape) objectFigure).setLineStyle(Graphics.LINE_DOT);
            objectFigure.setForegroundColor(ColorConstants.white);

            currFeedBackIsForArtifactOnly = true;

        } else {

            Rectangle rc =
                    new Rectangle(0, 0, ProcessWidgetConstants.TASK_WIDTH_SIZE,
                            ProcessWidgetConstants.TASK_HEIGHT_SIZE);

            objectFigure = new RoundedRectangle() {

                @Override
                public void paintFigure(Graphics graphics) {
                    super.paintFigure(graphics);

                    Rectangle b = getBounds().getCopy();
                    b.shrink(1, 1);

                    Point centre = b.getCenter();

                    // graphics.setXORMode(true);
                    // graphics.setLineStyle(SWT.LINE_DOT);

                    /*
                     * Draw the task part of feedback figure
                     */
                    // graphics.fillRoundRectangle(b,
                    // TaskFigure.CORNER_ARC,
                    // TaskFigure.CORNER_ARC);
                    // graphics.drawRoundRectangle(b,
                    // TaskFigure.CORNER_ARC,
                    // TaskFigure.CORNER_ARC);
                    /*
                     * Then the gateway part.
                     */
                    PointList points =
                            GatewayEditPart.getGatewayFigurePointlist();
                    points.translate(centre);

                    graphics.drawPolygon(points);

                    /*
                     * Then the event part
                     */

                    Rectangle eb =
                            new Rectangle(
                                    centre.x
                                            - (ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE / 2),
                                    centre.y
                                            - (ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE / 2),
                                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE);

                    graphics.drawOval(eb);

                    return;
                }

                @Override
                protected void fillShape(Graphics graphics) {
                    super.fillShape(graphics);
                }

                @Override
                protected void outlineShape(Graphics graphics) {
                    super.outlineShape(graphics);
                }
            };

            FigureUtilities.makeGhostShape((Shape) objectFigure);
            ((Shape) objectFigure).setLineStyle(Graphics.LINE_DOT);
            objectFigure.setForegroundColor(ColorConstants.white);

            currFeedBackIsForArtifactOnly = false;
        }

        return;
    }

    /**
     * @param sourceConnectionAnchor
     * @param createAndConnectTypes
     */
    private void createConnectionFigure(
            ConnectionAnchor sourceConnectionAnchor,
            Collection<CreateAndConnectObjectPair> createAndConnectTypes) {

        // use isOnlyAssociationConenctions() to decide whether to use
        // association edit part feedback
        if (isOnlyAssociationConnections(createAndConnectTypes)) {
            connectionFigure = new AssociationConnectionFigure();
            connectionFigure.setConnectionRouter(new BpmnDirectRouter());
            currFeedBackIsForAssocConnOnly = true;
        } else {
            connectionFigure = new SequenceFlowFigure();
            connectionFigure.setConnectionRouter(new BpmnFlowRouter());
            currFeedBackIsForAssocConnOnly = false;
        }

        connectionFigure.setForegroundColor(ColorConstants.gray);
        connectionFigure.setLineWidth(1);

        ConnectionAnchor tgtAnchor =
                new TaskActivityAnchor(objectFigure, false);

        connectionFigure.setSourceAnchor(sourceConnectionAnchor);
        connectionFigure.setTargetAnchor(tgtAnchor);

        return;
    }

    /**
     * Show the create and connect feedback.
     * 
     * @param absLocation
     */
    public void showFeedback(ClickOrDragGadgetRequest clickOrDragGadgetRequest) {
        if (objectFigure == null) {
            createFeedbackFigures(clickOrDragGadgetRequest);

            addFeebackToFeedbackLayer();
        }

        updateFeedback(clickOrDragGadgetRequest);

        return;
    }

    /**
     * Update the location if the feedback.
     * 
     * @param absLocation
     */
    public void updateFeedback(ClickOrDragGadgetRequest creq) {

        boolean recreateFigures = false;

        EditPart actualTarget =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getCreateAndConnectActualTarget(creq.getDragTarget(),
                                creq.getLocation());
        Collection<CreateAndConnectObjectPair> createAndConnectTypes =
                CreateAndConnectGadgetHelper.INSTANCE
                        .getCreateAndConnectObjectPairs(creq.getHostEditPart(),
                                actualTarget,
                                creq.getLocation());

        if (isOnlyAssociationConnections(createAndConnectTypes) != currFeedBackIsForAssocConnOnly) {
            recreateFigures = true;
        }

        if (isOnlyArtifactTarget(createAndConnectTypes) != currFeedBackIsForArtifactOnly) {
            recreateFigures = true;
        }

        if (recreateFigures || objectFigure == null) {
            eraseFeedback();

            createFeedbackFigures(creq);
            addFeebackToFeedbackLayer();
        }

        Point feedbackPos = creq.getLocation().getCopy();
        feedbackLayer.translateToRelative(feedbackPos);

        Rectangle rc =
                new Rectangle(
                        feedbackPos.x
                                - (ProcessWidgetConstants.TASK_WIDTH_SIZE / 2),
                        feedbackPos.y
                                - (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2),
                        ProcessWidgetConstants.TASK_WIDTH_SIZE,
                        ProcessWidgetConstants.TASK_HEIGHT_SIZE);

        if (objectFigure instanceof Polygon) {
            Polygon poly = (Polygon) objectFigure;

            PointList points = artifactFigurePoints.getCopy();

            points.translate(rc.getCenter());
            poly.setPoints(points);

        } else {
            objectFigure.setBounds(rc);
        }

        if (connectionFigure != null) {
            connectionFigure.revalidate();
        }

        return;
    }

    /**
     * Hide the create and connect feedback.
     */
    public void eraseFeedback() {
        if (connectionFigure != null) {
            if (connectionFigure.getParent() != null) {
                feedbackLayer.remove(connectionFigure);
            }
            connectionFigure = null;
        }

        if (objectFigure != null) {
            if (objectFigure.getParent() != null) {
                feedbackLayer.remove(objectFigure);
            }
            objectFigure = null;
        }

        return;
    }

    /**
     * add the created feedback to the feedback layer
     */
    private void addFeebackToFeedbackLayer() {
        if (objectFigure != null) {
            if (objectFigure.getParent() == null) {
                feedbackLayer.add(objectFigure);
            }
        }

        if (connectionFigure != null) {
            if (connectionFigure.getParent() == null) {
                feedbackLayer.add(connectionFigure);
            }
        }
    }

    /**
     * @param createAndConnectTypes
     * @return true if the given set of create and connect types have only types
     *         that create associations.
     */
    private boolean isOnlyAssociationConnections(
            Collection<CreateAndConnectObjectPair> createAndConnectTypes) {
        boolean hasAssociationType = false;

        for (CreateAndConnectObjectPair cacPair : createAndConnectTypes) {
            if (!CreateAndConnectObjectType.SEPARATOR.equals(cacPair
                    .getObjectType())) {

                if (!CreateAndConnectConnectionType.ASSOCIATION.equals(cacPair
                        .getConnectionType())) {
                    // There's another connection type, definitely not only
                    // associations.
                    return false;
                } else {
                    hasAssociationType = true;
                }
            }
        }

        return hasAssociationType;
    }

    /**
     * @param createAndConnectTypes
     * @return true if the given set of create and connect types have only types
     *         that create artifacts.
     */
    private boolean isOnlyArtifactTarget(
            Collection<CreateAndConnectObjectPair> createAndConnectTypes) {
        boolean hasArtifactType = false;

        for (CreateAndConnectObjectPair cacPair : createAndConnectTypes) {
            if (!CreateAndConnectObjectType.SEPARATOR.equals(cacPair
                    .getObjectType())) {

                if (!CreateAndConnectObjectType.ANNOTATION.equals(cacPair
                        .getObjectType())
                        && !CreateAndConnectObjectType.DATAOBJECT
                                .equals(cacPair.getObjectType())) {
                    // There's a non-artifact option, definitely not artifacts
                    // only.
                    return false;

                } else {
                    hasArtifactType = true;
                }
            }
        }

        return hasArtifactType;
    }

}