/* 
 ** 
 **  MODULE:             $RCSfile: FlowConnectionEndpointPolicy.java $ 
 **                      $Revision: 1.5 $ 
 **                      $Date: 2005/05/27 09:13:08Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: FlowConnectionEndpointPolicy.java $ 
 **    Revision 1.5  2005/05/27 09:13:08Z  wzurek 
 **    fixed copyright 
 ** 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor;

/**
 * Endpoinds and selection policy
 * 
 * @author WojciechZ
 */
public class FlowConnectionEndpointPolicy extends ConnectionEndpointEditPolicy {

    private Request lastRequest = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy#
     * createSelectionHandles()
     */
    @Override
    protected List createSelectionHandles() {
        List handles = new ArrayList<ConnectionHandle>(2);

        ConnectionEditPart conn = (ConnectionEditPart) getHost();

        handles.add(new XpdConnectionEndHandle(conn));

        handles.add(new XpdConnectionStartHandle(conn));

        return handles;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy#showSourceFeedback
     * (org.eclipse.gef.Request)
     */
    @Override
    public void showSourceFeedback(Request request) {
        super.showSourceFeedback(request);

        // Save last request so that paint of handle can reflect correct anchor
        // status for request.
        lastRequest = request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy#eraseSourceFeedback
     * (org.eclipse.gef.Request)
     */
    @Override
    public void eraseSourceFeedback(Request request) {
        super.eraseSourceFeedback(request);

        // UnSave last request
        lastRequest = null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#deactivate()
     */
    @Override
    public void deactivate() {
        super.deactivate();

        // UnSave last request
        lastRequest = null;
    }

    private class XpdConnectionEndHandle extends XpdFixedLocationHandle {

        /**
         * Creates a new ConnectionEndHandle, sets its owner to
         * <code>owner</code>, and sets its locator to a
         * {@link ConnectionLocator}.
         * 
         * @param owner
         *            the ConnectionEditPart owner
         */
        public XpdConnectionEndHandle(ConnectionEditPart owner) {
            super(false);
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(),
                    ConnectionLocator.TARGET));
        }

        /**
         * Creates and returns a new {@link ConnectionEndpointTracker}.
         * 
         * @return the new ConnectionEndpointTracker
         */
        @Override
        protected DragTracker createDragTracker() {
            if (isFixed())
                return null;
            ConnectionEndpointTracker tracker;
            tracker =
                    new ConnectionEndpointTracker(
                            (ConnectionEditPart) getOwner());
            tracker.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
            tracker.setDefaultCursor(getCursor());
            return tracker;
        }

    }

    private class XpdConnectionStartHandle extends XpdFixedLocationHandle {

        /**
         * Creates a new ConnectionStartHandle, sets its owner to
         * <code>owner</code>, and sets its locator to a
         * {@link ConnectionLocator}.
         * 
         * @param owner
         *            the ConnectionEditPart owner
         */
        public XpdConnectionStartHandle(ConnectionEditPart owner) {
            super(true);
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(),
                    ConnectionLocator.SOURCE));
        }

        /**
         * Creates and returns a new {@link ConnectionEndpointTracker}.
         * 
         * @return the new ConnectionEndpointTracker
         */
        @Override
        protected DragTracker createDragTracker() {
            if (isFixed())
                return null;
            ConnectionEndpointTracker tracker;
            tracker =
                    new ConnectionEndpointTracker(
                            (ConnectionEditPart) getOwner());
            tracker.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
            tracker.setDefaultCursor(getCursor());

            return tracker;
        }

    }

    /**
     * XpdConnectionEndHandle
     * 
     * Simple class to handle painting of fixed location OR std default anchor
     * handles.
     */
    private abstract class XpdFixedLocationHandle extends ConnectionHandle {
        boolean isStart;

        /**
         * 
         */
        public XpdFixedLocationHandle(boolean isStart) {
            super();
            this.isStart = isStart;
        }

        /**
         * Initializes the handle.
         */
        @Override
        protected void init() {
            setPreferredSize(new Dimension(9, 9));
        }

        @Override
        public void paintFigure(Graphics gr) {
            gr.pushState();
            int aa = gr.getAntialias();
            gr.setAntialias(SWT.ON);

            // If handle currently represents a fixed location anchor then draw
            // differenty graphic.
            if (isFixedLocationAnchor()) {

                if (false) {
                    paintTargetHandle(gr);

                } else {
                    paintRoundHandle(gr);
                }

            } else {
                paintNonFixedHandle(gr);
            }

            gr.setAntialias(aa);
            gr.popState();
        }

        /**
         * @param gr
         */
        private void paintNonFixedHandle(Graphics gr) {
            // Non-fixed default anchor.
            Rectangle bnds = getBounds().getCopy();

            gr.setBackgroundColor(getFillColor());
            gr.setForegroundColor(getBorderColor());

            bnds.shrink(1, 1);
            gr.fillRectangle(bnds);
            gr.drawRectangle(bnds.x, bnds.y, bnds.width - 1, bnds.height - 1);
        }

        /**
         * @param gr
         */
        private void paintRoundHandle(Graphics gr) {
            // TODO Auto-generated method stub
            Rectangle bnds = getBounds().getCopy();

            bnds.shrink(1, 1);
            setBackgroundColor(ColorConstants.green);
            gr.fillOval(bnds);

            if (false) {
                gr.drawLine(bnds.getLeft(), bnds.getRight());
                gr.drawLine(bnds.getTop(), bnds.getBottom());
            }

            bnds.width -= 1;
            bnds.height -= 1;

            setForegroundColor(ColorConstants.black);
            gr.drawOval(bnds);

        }

        /**
         * @param gr
         */
        private void paintTargetHandle(Graphics gr) {
            Rectangle bnds = getBounds().getCopy();

            Point left = bnds.getLeft();
            Point right = bnds.getRight();
            Point top = bnds.getTop();
            Point bottom = bnds.getBottom();
            Point centre = bnds.getCenter();

            //
            // paint a thick black cross.
            gr.setLineWidth(3);
            gr.drawLine(left, right);
            gr.drawLine(top, bottom);

            //
            // Then overlay it with a thin white cross.
            gr.setForegroundColor(ColorConstants.white);
            gr.setLineWidth(1);
            gr.drawLine(left.x + 1, left.y, right.x - 2, right.y);
            gr.drawLine(top.x, top.y + 1, bottom.x, bottom.y - 2);

            //
            // Then a thick white circle (to give a white edge around the
            // graphic to improve contrast)

            gr.setForegroundColor(ColorConstants.white);
            gr.setLineWidth(3);
            gr.drawOval(centre.x - (bnds.width / 2), centre.y
                    - (bnds.height / 2), bnds.width - 1, bnds.height - 1);

            //
            // Then a thin black circle inside the thick white one.
            gr.setLineWidth(1);
            gr.setForegroundColor(ColorConstants.black);
            gr.drawOval(centre.x - (bnds.width / 2), centre.y
                    - (bnds.height / 2), bnds.width - 1, bnds.height - 1);
        }

        private boolean isFixedLocationAnchor() {
            boolean isFixed = false;

            ConnectionEditPart conn = (ConnectionEditPart) getOwner();

            if (isStart) {
                // If move in progrees then we'll have a request, if so get info
                // from there.
                if (lastRequest instanceof ReconnectRequest
                        && REQ_RECONNECT_SOURCE.equals(lastRequest.getType())) {
                    ReconnectRequest recon = (ReconnectRequest) lastRequest;

                    if (recon.getTarget() instanceof NodeEditPart) {
                        ConnectionAnchor anchor =
                                ((NodeEditPart) recon.getTarget())
                                        .getSourceConnectionAnchor(recon);

                        if (anchor instanceof IFixedLocationAnchor) {
                            isFixed =
                                    ((IFixedLocationAnchor) anchor)
                                            .isInFixedLocation();
                        }

                    }

                } else if (conn.getSource() instanceof NodeEditPart) {
                    // If not reconnect in progress then get anchoir info from
                    // edit part.
                    // ConnectionAnchor anchor = ((NodeEditPart)
                    // conn.getSource())
                    // .getSourceConnectionAnchor(conn);
                    ConnectionAnchor anchor =
                            ((PolylineConnection) conn.getFigure())
                                    .getSourceAnchor();

                    if (anchor instanceof IFixedLocationAnchor) {
                        isFixed =
                                ((IFixedLocationAnchor) anchor)
                                        .isInFixedLocation();
                    }
                }

            } else {
                // If move in progrees then we'll have a request, if so get info
                // from there.
                if (lastRequest instanceof ReconnectRequest
                        && REQ_RECONNECT_TARGET.equals(lastRequest.getType())) {
                    ReconnectRequest recon = (ReconnectRequest) lastRequest;

                    if (recon.getTarget() instanceof NodeEditPart) {
                        // ConnectionAnchor anchor =
                        // ((NodeEditPart) recon.getTarget())
                        // .getTargetConnectionAnchor(recon);
                        ConnectionAnchor anchor =
                                ((PolylineConnection) conn.getFigure())
                                        .getTargetAnchor();
                        if (anchor instanceof IFixedLocationAnchor) {
                            isFixed =
                                    ((IFixedLocationAnchor) anchor)
                                            .isInFixedLocation();
                        }
                    }

                } else if (conn.getTarget() instanceof NodeEditPart) {
                    // If not reconnect in progress then get anchoir info from
                    // edit part.
                    ConnectionAnchor anchor =
                            ((NodeEditPart) conn.getTarget())
                                    .getTargetConnectionAnchor(conn);

                    if (anchor instanceof IFixedLocationAnchor) {
                        isFixed =
                                ((IFixedLocationAnchor) anchor)
                                        .isInFixedLocation();
                    }
                }
            }

            return isFixed;
        }

    }

}
