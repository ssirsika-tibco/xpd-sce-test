/**
 * 
 */
package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.tools.ConnectionBendpointTracker;

import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author wzurek
 * 
 */
public class XpdConnectionBendpointTracker extends ConnectionBendpointTracker {

    private XpdSnapFeedbackPolicy alignmentFeedbackHelper =
            new XpdSnapFeedbackPolicy();

    public XpdConnectionBendpointTracker(ConnectionEditPart part, int index) {
        super(part, index);
    }

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#showSourceFeedback()
     * 
     */
    @Override
    protected void showSourceFeedback() {
        super.showSourceFeedback();
        alignmentFeedbackHelper.showTargetFeedback(getSourceRequest());
    }

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#eraseSourceFeedback()
     * 
     */
    @Override
    protected void eraseSourceFeedback() {
        alignmentFeedbackHelper.eraseTargetFeedback(getSourceRequest());
        super.eraseSourceFeedback();
    }

    @Override
    protected void updateSourceRequest() {
        SnapToHelper snapToHelper =
                (SnapToHelper) getConnectionEditPart().getSource().getParent()
                        .getAdapter(SnapToHelper.class);

        BendpointRequest request = (BendpointRequest) getSourceRequest();
        // request.setLocation(getLocation());

        if (snapToHelper != null && !getCurrentInput().isAltKeyDown()
                && request.getSource() instanceof BaseConnectionEditPart) {

            BaseConnectionEditPart connEP =
                    (BaseConnectionEditPart) request.getSource();

            EditPart parent = connEP.getSource().getParent();
            if (parent instanceof TaskEditPart) {
                alignmentFeedbackHelper.setHost(parent);
            } else {
                alignmentFeedbackHelper
                        .setHost(request.getSource().getParent());
            }

            // PrecisionPoint preciseDelta = new PrecisionPoint(moveDelta );
            PrecisionRectangle src = new PrecisionRectangle();

            src.setX(getLocation().x);
            src.setY(getLocation().y);
            /*
             * Not having a width for the bendpoint when using snap locations
             * was causing problems (there's some fudging in
             * SnapToGeometry.getCorrectionFor() that doesn't work well without
             * some kind of width there).
             */
            src.setWidth(2);
            src.setHeight(2);

            PrecisionPoint preciseDelta = new PrecisionPoint(getLocation());
            snapToHelper.snapPoint(request,
                    PositionConstants.HORIZONTAL | PositionConstants.VERTICAL,
                    new PrecisionRectangle[] { src
                    /* baseRect, jointRect */},
                    preciseDelta);

            Point copyDelta = preciseDelta.getCopy();

            request.setLocation(copyDelta);

        } else {
            request.setLocation(getLocation());
        }
    }

    private class XpdSnapFeedbackPolicy extends SnapFeedbackPolicy {

        /**
         * @see org.eclipse.gef.editpolicies.SnapFeedbackPolicy#showTargetFeedback(org.eclipse.gef.Request)
         * 
         * @param req
         */
        @Override
        public void showTargetFeedback(Request req) {
            if (RequestConstants.REQ_CREATE_BENDPOINT.equals(req.getType())
                    || RequestConstants.REQ_MOVE_BENDPOINT
                            .equals(req.getType())) {
                if (!getCurrentInput().isAltKeyDown()) {
                    /*
                     * Kid the snap feedback into thing we're a requets it
                     * handles.
                     */
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
