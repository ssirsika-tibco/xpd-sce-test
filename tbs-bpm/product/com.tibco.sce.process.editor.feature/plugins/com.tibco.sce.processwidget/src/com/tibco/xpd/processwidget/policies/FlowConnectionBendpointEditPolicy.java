/* 
 ** 
 **  MODULE:             $RCSfile: FlowConnectionBendpointEditPolicy.java $ 
 **                      $Revision: 1.4 $ 
 **                      $Date: 2005/05/27 09:04:32Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 */
package com.tibco.xpd.processwidget.policies;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.processwidget.figures.BpmnDirectRouter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Bendpoints policy for FlowConnection
 * 
 * @author WojciechZ
 */
public class FlowConnectionBendpointEditPolicy extends
        SelectionHandlesEditPolicy implements PropertyChangeListener {

    private static final List NULL_CONSTRAINT = new ArrayList();

    private RectangleFigure feedback;

    private List originalConstraint;

    private boolean isDeleting;

    /**
     * Shows feedback when a bendpoint is being deleted. This method is only
     * called once when the bendpoint is first deleted, not every mouse move.
     * The original figure is used for feedback and the original constraint is
     * saved, so that it can be restored when feedback is erased.
     * 
     * @param request
     *            the BendpointRequest
     */
    protected void showDeleteBendpointFeedback(BendpointRequest request) {
        if (originalConstraint == null) {
            saveOriginalConstraint();
            List constraint = (List) getConnection().getRoutingConstraint();
            constraint.remove(request.getIndex());
            getConnection().setRoutingConstraint(constraint);
        }
    }

    /**
     * Since the original figure is used for feedback, this method saves the
     * original constraint, so that is can be restored when the feedback is
     * erased.
     */
    protected void saveOriginalConstraint() {
        originalConstraint = (List) getConnection().getRoutingConstraint();
        if (originalConstraint == null)
            originalConstraint = NULL_CONSTRAINT;
        getConnection().setRoutingConstraint(new ArrayList(originalConstraint));
    }

    /**
     * Shows feedback when a bendpoint is being created. The original figure is
     * used for feedback and the original constraint is saved, so that it can be
     * restored when feedback is erased.
     * 
     * @param request
     *            the BendpointRequest
     */
    protected void showCreateBendpointFeedback(BendpointRequest request) {
        Point p = request.getLocation().getCopy();
        getConnection().translateToRelative(p);

        List constraint;
        Bendpoint bp = new AbsoluteBendpoint(p);
        if (originalConstraint == null) {
            saveOriginalConstraint();
            constraint = (List) getConnection().getRoutingConstraint();
            constraint.add(request.getIndex(), bp);
        } else {
            constraint = (List) getConnection().getRoutingConstraint();
        }
        constraint.set(request.getIndex(), bp);
        getConnection().setRoutingConstraint(constraint);
    }

    private boolean isDeleteBendpoint(int index) {
        List constraint =
                new ArrayList((List) getConnection().getRoutingConstraint());
        if (index >= constraint.size()) {
            return false;
        }
        Point p = ((Bendpoint) constraint.remove(index)).getLocation();

        PolylineConnection tst = new PolylineConnection();
        tst.setSourceAnchor(getConnection().getSourceAnchor());
        tst.setTargetAnchor(getConnection().getTargetAnchor());
        ConnectionRouter router = getConnection().getConnectionRouter();
        router.setConstraint(tst, constraint);
        router.route(tst);
        router.remove(tst);

        return tst.getPoints()
                .intersects(new Rectangle(p.x - 3, p.y - 3, 7, 7));
    }

    /**
     * Shows feedback when a bendpoint is being moved. Also checks to see if the
     * bendpoint should be deleted and then calls
     * {@link #showDeleteBendpointFeedback(BendpointRequest)} if needed. The
     * original figure is used for feedback and the original constraint is
     * saved, so that it can be restored when feedback is erased.
     * 
     * @param request
     *            the BendpointRequest
     */
    protected void showMoveBendpointFeedback(BendpointRequest request) {
        Point p = request.getLocation().getCopy();

        if (isDeleteBendpoint(request.getIndex())) {
            if (!isDeleting) {
                isDeleting = true;
                eraseSourceFeedback(request);
                showDeleteBendpointFeedback(request);
            }
            return;
        }
        if (isDeleting) {
            isDeleting = false;
            eraseSourceFeedback(request);
        }
        if (originalConstraint == null)
            saveOriginalConstraint();
        List constraint = (List) getConnection().getRoutingConstraint();

        getConnection().translateToRelative(p);
        Bendpoint bp = new AbsoluteBendpoint(p);
        constraint.set(request.getIndex(), bp);
        getConnection().setRoutingConstraint(constraint);
    }

    protected boolean isValidBendpointPosition(Request req) {
        boolean ret = false;
        // Disallow move / create bendpoint outside pool(s).
        if (req instanceof BendpointRequest) {

            BendpointRequest request = (BendpointRequest) req;

            if (REQ_MOVE_BENDPOINT.equals(request.getType())
                    || REQ_CREATE_BENDPOINT.equals(request.getType())) {

                Point loc = request.getLocation().getCopy();

                Connection conn = (Connection) getHostFigure();
                if (conn != null) {
                    IFigure fig = conn.getSourceAnchor().getOwner();

                    // Find the pool that src object is in
                    while (fig != null && !(fig instanceof PoolFigure)) {
                        fig = fig.getParent();
                    }

                    if (fig == null) {
                        // Object not parented by pool, let the bend points to
                        // it go anywhere
                        ret = true;
                    } else {
                        fig = fig.getParent();

                        if (fig != null) {
                            Rectangle bnds = null;

                            List pools = fig.getChildren();
                            for (Iterator p = pools.iterator(); p.hasNext();) {
                                Object obj = (Object) p.next();

                                if (obj instanceof PoolFigure) {
                                    PoolFigure pool = (PoolFigure) obj;

                                    if (bnds == null) {
                                        bnds =
                                                pool.getContentFigure()
                                                        .getBounds().getCopy();
                                        pool.getContentFigure()
                                                .translateToAbsolute(bnds);
                                    } else {
                                        Rectangle b =
                                                pool.getContentFigure()
                                                        .getBounds().getCopy();
                                        pool.getContentFigure()
                                                .translateToAbsolute(b);
                                        bnds.union(b);
                                    }
                                }
                            }

                            if (bnds.contains(loc)) {
                                ret = true;
                            } else {
                                ret = false;
                            }

                        }
                    }

                }
            }
        }
        return (ret);
    }

    /**
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getCreateBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getCreateBendpointCommand(BendpointRequest request) {

        EObject host = (EObject) getHost().getModel();
        BaseConnectionAdapter tr =
                (BaseConnectionAdapter) adapterFactory.adapt(host,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        Point loc = request.getLocation().getCopy();

        ConnectionEditPart src = request.getSource();
        Connection c = (Connection) src.getFigure();
        Point start = c.getSourceAnchor().getReferencePoint();
        Point end = c.getTargetAnchor().getReferencePoint();

        // Translate to relative to make sure that all points we're coparing are
        // in same co-ord space.
        c.translateToRelative(loc);
        c.translateToRelative(start);
        c.translateToRelative(end);

        XPDBendpointType bp = new XPDBendpointType();

        bp.fromStart = new Dimension(loc.x - start.x, loc.y - start.y);
        bp.fromEnd = new Dimension(loc.x - end.x, loc.y - end.y);

        bp.weight = 0.5f;

        return new EMFCommandWrapper(editingDomain, tr
                .getCreateBendpointCommand(editingDomain,
                        request.getIndex(),
                        bp));
    }

    /**
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getDeleteBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getDeleteBendpointCommand(BendpointRequest request) {
        EObject host = (EObject) getHost().getModel();
        BaseConnectionAdapter tr =
                (BaseConnectionAdapter) adapterFactory.adapt(host,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        return new EMFCommandWrapper(editingDomain, tr
                .getDeleteBendpointCommand(editingDomain, request.getIndex()));
    }

    private final AdapterFactory adapterFactory;

    private final EditingDomain editingDomain;

    public FlowConnectionBendpointEditPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    /**
     * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getMoveBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
     */
    protected Command getMoveBendpointCommand(BendpointRequest request) {

        EObject host = (EObject) getHost().getModel();
        BaseConnectionAdapter tr =
                (BaseConnectionAdapter) adapterFactory.adapt(host,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        Point loc = request.getLocation().getCopy();

        ConnectionEditPart src = request.getSource();
        Connection c = (Connection) src.getFigure();
        Point start = c.getSourceAnchor().getReferencePoint();
        Point end = c.getTargetAnchor().getReferencePoint();

        // Translate to relative to make sure that all points we're coparing are
        // in same co-ord space.
        c.translateToRelative(loc);
        c.translateToRelative(start);
        c.translateToRelative(end);

        XPDBendpointType bp = new XPDBendpointType();
        bp.fromStart = new Dimension(loc.x - start.x, loc.y - start.y);
        bp.fromEnd = new Dimension(loc.x - end.x, loc.y - end.y);
        bp.weight = 0.5f;

        return new EMFCommandWrapper(editingDomain, tr
                .getMoveBendpointCommand(editingDomain, request.getIndex(), bp));
    }

    public void showSourceFeedback(Request req) {
        if (REQ_CREATE_BENDPOINT.equals(req.getType())) {
            BendpointRequest r = (BendpointRequest) req;
            showCreateBendpointFeedback(r);
        } else if (REQ_MOVE_BENDPOINT.equals(req.getType())) {
            BendpointRequest r = (BendpointRequest) req;
            showMoveBendpointFeedback(r);
        }
    }

    public void eraseSourceFeedback(Request request) {
        if (feedback != null) {
            getFeedbackLayer().remove(feedback);
            feedback = null;
        }
        if (originalConstraint != null) {
            if (originalConstraint == NULL_CONSTRAINT) {
                getConnection().setRoutingConstraint(null);
            } else {
                getConnection().setRoutingConstraint(originalConstraint);
            }
            originalConstraint = null;
        }
    }

    /*
     * @see
     * org.eclipse.gef.editpolicies.BendpointEditPolicy#createSelectionHandles()
     */
    protected List createSelectionHandles() {

        ConnectionEditPart connEP = (ConnectionEditPart) getHost();
        List list = new ArrayList();

        //
        // Disable bend point editing when either end of connection is in
        // closed pool or lane. If there aren't selection handles that you can't
        // create them!
        if (connEP.getSource() instanceof BaseGraphicalEditPart) {
            BaseGraphicalEditPart source =
                    ((BaseGraphicalEditPart) connEP.getSource());
            PoolEditPart parentPool = source.getParentPool();
            LaneEditPart parentLane = source.getParentLane();

            if ((parentPool != null && parentPool.isClosed())
                    || (parentLane != null && parentLane.isClosed())) {
                return list;
            }
        }

        if (connEP.getTarget() instanceof BaseGraphicalEditPart) {
            BaseGraphicalEditPart target =
                    ((BaseGraphicalEditPart) connEP.getTarget());
            PoolEditPart parentPool = target.getParentPool();
            LaneEditPart parentLane = target.getParentLane();

            if ((parentPool != null && parentPool.isClosed())
                    || (parentLane != null && parentLane.isClosed())) {
                return list;
            }
        }

        ConnectionRouter router = getConnection().getConnectionRouter();
        if (router instanceof BpmnFlowRouter) {

            PointList points = getConnection().getPoints();
            List bendPoints = (List) getConnection().getRoutingConstraint();
            if (bendPoints == null) {
                bendPoints = Collections.EMPTY_LIST;
            }

            int bendPointIndex = 0;
            Point currBendPoint = null;

            if (!bendPoints.isEmpty())
                currBendPoint = ((Bendpoint) bendPoints.get(0)).getLocation();// (
            // (
            // Point
            // )

            for (int i = 0; i < points.size() - 1; i++) {
                // Put a create handle on the middle of every segment
                list.add(new XpdBendpointCreationHandle(connEP, bendPointIndex,
                        i));

                // If the current user bendpoint matches a bend location,
                // show a move handle
                if (i < points.size() - 1 && bendPointIndex < bendPoints.size()) {

                    // PrecisionPoint location is a bit rubbish when comparing
                    // with std point (for some reason it doesn't use
                    // Math.round() when assigning it's x/y from float x/y.
                    // This can cause the following comparison to fail so we
                    // will do normal check AND a Math.round() check.

                    Point point = points.getPoint(i + 1);
                    if (currBendPoint.equals(point)
                            || (Math.round(currBendPoint.preciseX()) == point.x && Math
                                    .round(currBendPoint.preciseY()) == point.y)) {
                        list.add(new XpdBendpointMoveHandle(connEP,
                                bendPointIndex, i + 1));

                        // Go to the next user bendpoint
                        bendPointIndex++;
                        if (bendPointIndex < bendPoints.size()) {
                            Object bp = bendPoints.get(bendPointIndex);
                            currBendPoint =
                                    (Point) (bp instanceof Point ? bp
                                            : ((Bendpoint) bp).getLocation());
                        }
                    }
                }
            }
        } else if (router instanceof BpmnDirectRouter) {
            PointList points = getConnection().getPoints();
            List bendPoints = (List) getConnection().getRoutingConstraint();
            if (bendPoints == null) {
                bendPoints = Collections.EMPTY_LIST;
            }

            int bendPointIndex = 0;
            Point currBendPoint = null;

            if (!bendPoints.isEmpty())
                currBendPoint = ((Bendpoint) bendPoints.get(0)).getLocation();// (
            // (
            // Point
            // )
            // bendPoints.get(0)).getLocation();

            for (int i = 0; i < points.size() - 1; i++) {
                // Put a create handle on the middle of every segment
                list.add(new XpdBendpointCreationHandle(connEP, bendPointIndex,
                        i));

                // If the current user bendpoint matches a bend location,
                // show a move handle
                if (i < points.size() - 1 && bendPointIndex < bendPoints.size()) {
                    // PrecisionPoint location is a bit rubbish when comparing
                    // with std point (for some reason it doesn't use
                    // Math.round() when assigning it's x/y from float x/y.
                    // This can cause the following comparison to fail so we
                    // will do normal check AND a Math.round() check.

                    Point point = points.getPoint(i + 1);
                    if (currBendPoint.equals(point)
                            || (Math.round(currBendPoint.preciseX()) == point.x && Math
                                    .round(currBendPoint.preciseY()) == point.y)) {

                        list.add(new XpdBendpointMoveHandle(connEP,
                                bendPointIndex, i + 1));

                        // Go to the next user bendpoint
                        bendPointIndex++;
                        if (bendPointIndex < bendPoints.size()) {
                            Object bp = bendPoints.get(bendPointIndex);
                            currBendPoint =
                                    (Point) (bp instanceof Point ? bp
                                            : ((Bendpoint) bp).getLocation());
                        }
                    }
                }
            }

        }

        return list;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (getHost().getSelected() == EditPart.SELECTED_PRIMARY) {
            hideSelection();
            showSelection();
        }
    }

    /**
     * Convenience method for obtaining the host's <code>Connection</code>
     * figure.
     * 
     * @return the Connection figure
     */
    protected Connection getConnection() {
        return (Connection) ((ConnectionEditPart) getHost()).getFigure();
    }

    /**
     * <code>activate()</code> is extended to add a listener to the
     * <code>Connection</code> figure.
     * 
     * @see org.eclipse.gef.EditPolicy#activate()
     */
    public void activate() {
        super.activate();
        getConnection().addPropertyChangeListener(Connection.PROPERTY_POINTS,
                this);
    }

    /**
     * <code>deactivate()</code> is extended to remove the property change
     * listener on the <code>Connection</code> figure.
     * 
     * @see org.eclipse.gef.EditPolicy#deactivate()
     */
    public void deactivate() {
        getConnection()
                .removePropertyChangeListener(Connection.PROPERTY_POINTS, this);
        super.deactivate();
    }

    public Command getCommand(Request request) {
        if (REQ_MOVE_BENDPOINT.equals(request.getType())) {
            if (isDeleting)
                return getDeleteBendpointCommand((BendpointRequest) request);
            return getMoveBendpointCommand((BendpointRequest) request);
        }
        if (REQ_CREATE_BENDPOINT.equals(request.getType()))
            return getCreateBendpointCommand((BendpointRequest) request);
        return null;
    }

}