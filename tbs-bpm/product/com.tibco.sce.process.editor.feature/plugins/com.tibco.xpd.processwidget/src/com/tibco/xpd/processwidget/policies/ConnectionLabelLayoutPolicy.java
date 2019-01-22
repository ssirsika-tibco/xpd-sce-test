/**
 * Copyright 2006 TIBCO Software Inc.
 */
package com.tibco.xpd.processwidget.policies;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;

import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.ConnectionLabelEditPart;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Manage movements of the label
 * 
 * @author wzurek
 */
public class ConnectionLabelLayoutPolicy extends LayoutEditPolicy {

    private final AdapterFactory adapterFactory;

    private final EditingDomain editingDomain;

    private ConnectionLabelPosition connectionLabelPosition = null;

    public ConnectionLabelLayoutPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    protected EditPolicy createChildEditPolicy(EditPart child) {
        return new LabelConstraintEditPolicy();
    }

    /**
     * @return the editingDomain
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * @return the adapterFactory
     */
    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getMoveChildrenCommand(org.eclipse.gef.Request)
     */
    protected Command getMoveChildrenCommand(Request request) {

        if (request instanceof ChangeBoundsRequest) {
            ChangeBoundsRequest cbreq = (ChangeBoundsRequest) request;

            BaseConnectionEditPart connEP = (BaseConnectionEditPart) getHost();

            // Look for the label...
            ConnectionLabelEditPart labelEP = null;

            for (Iterator iter = cbreq.getEditParts().iterator(); iter
                    .hasNext();) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof ConnectionLabelEditPart) {
                    labelEP = (ConnectionLabelEditPart) ep;
                    break;
                }
            }

            if (labelEP != null) {
                // Get new centre point of label.
                Point d = cbreq.getMoveDelta();
                Dimension delta = new Dimension(d.x, d.y);
                labelEP.getFigure().translateToRelative(delta);

                Rectangle rc = labelEP.getFigure().getBounds().getCopy();
                rc.x += delta.width;
                rc.y += delta.height;

                // Make sure we're in the same co-ordinate space.
                labelEP.getFigure().translateToAbsolute(rc);
                connEP.getConnectionFigure().translateToRelative(rc);

                // Get position on parent line closest to centre of label.
                PointList pts = connEP.getConnectionFigure().getPoints()
                        .getCopy();

                // Work out how far down that line it is
                Point center = rc.getCenter();
                Point closest = new Point();
                double anchorpos = XPDLineUtilities.getLinePortionFromPoint(
                        pts, center, closest);

                connectionLabelPosition = new ConnectionLabelPosition(
                        anchorpos, center.x - closest.x, center.y - closest.y);

                return new EMFCommandWrapper(editingDomain, connEP
                        .getModelConnectionAdapter()
                        .getSetLabelPositionCommand(editingDomain,
                                connectionLabelPosition));
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
     */
    protected Command getCreateCommand(CreateRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * LinkToAnchorPointEditPolicy Subclass edit policy that handles move of
     * label to show feed back of line to the anchor position on parent
     * connection.
     */
    private class LabelConstraintEditPolicy extends ResizableEditPolicy {
        private Connection labelAnchorFeedback = null;

        private LineAnchor connectionAnchor = null;

        private AbstractConnectionAnchor labelAnchor = null;


        @Override
        protected List createSelectionHandles() {
            // Standard MoveHandle for ResizableEditPolicy will check whether
            // edit part under the label is still the label edit parts parent
            // and if not will change the move request into an Orphan request.
            //
            // But conneciton label is always outside its parent so it would
            // always be changed.
            //
            // Therefore we create the standard move/resize handles here and
            // then override the MoveHandle with one that always returns true
            // from isMove().
            //
            // This will prevent the DragEditPartsTracker from switching request to orphan.
            handles = super.createSelectionHandles();

            for (Iterator iter = handles.iterator(); iter.hasNext();) {
                Object hdl = (Object) iter.next();

                if (hdl instanceof MoveHandle) {
                    DragEditPartsTracker dragTracker = new DragEditPartsTracker(
                            getHost()) {
                        protected boolean isMove() {
                            return true;
                        }
                    };

                    ((MoveHandle) hdl).setDragTracker(dragTracker);
                }

            }

            return handles;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.editpolicies.ResizableEditPolicy#getResizeCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
         */
        @Override
        protected Command getResizeCommand(ChangeBoundsRequest request) {
            IFigure labelFig = getHostFigure();

            Rectangle b = labelFig.getBounds().getCopy();
            labelFig.translateToAbsolute(b);

            Rectangle newb = request.getTransformedRectangle(b);

            labelFig.translateToRelative(newb);

            Dimension newSize = newb.getSize();

            BaseConnectionEditPart connEP = ((ConnectionLabelEditPart) getHost())
                    .getConnectionEditPart();

            return new EMFCommandWrapper(editingDomain, connEP
                    .getModelConnectionAdapter().getSetLabelSizeCommand(
                            editingDomain, newSize));

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#showSourceFeedback(org.eclipse.gef.Request)
         */
        public void showSourceFeedback(Request request) {
            super.showSourceFeedback(request);

            if (REQ_MOVE.equals(request.getType())) {
                if (getHost() instanceof ConnectionLabelEditPart) {
                    ConnectionLabelEditPart labelEP = (ConnectionLabelEditPart) getHost();

                    IFigure moveFeedBack = getDragSourceFeedbackFigure();

                    if (labelAnchorFeedback == null) {
                        labelAnchorFeedback = new PolylineConnection();
                        labelAnchorFeedback
                                .setForegroundColor(ColorConstants.lightGray);
                        labelAnchorFeedback.setVisible(false);

                        connectionAnchor = new LineAnchor(labelEP
                                .getConnectionEditPart().getFigure());

                        labelAnchor = new AbstractConnectionAnchor(moveFeedBack) {
                            public Point getLocation(Point reference) {
                                return getReferencePoint();
                            }
                        };

                        labelAnchorFeedback.setSourceAnchor(connectionAnchor);
                        labelAnchorFeedback.setTargetAnchor(labelAnchor);

                        getFeedbackLayer().add(labelAnchorFeedback);
                    }

                    labelAnchorFeedback.setVisible(true);

                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
         */
        public void eraseSourceFeedback(Request request) {
            if (REQ_MOVE.equals(request.getType())) {
                if (labelAnchorFeedback != null) {
                    getFeedbackLayer().remove(labelAnchorFeedback);
                    labelAnchorFeedback = null;
                    labelAnchor = null;
                    connectionAnchor = null;
                }
            }

            super.eraseSourceFeedback(request);
        }

    }

}
