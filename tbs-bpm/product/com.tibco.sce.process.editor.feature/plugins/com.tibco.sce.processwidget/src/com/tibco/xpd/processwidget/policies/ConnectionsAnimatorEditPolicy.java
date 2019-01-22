/*
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.NodeListener;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.parts.AssociationEditPart;
import com.tibco.xpd.processwidget.parts.DataObjectEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.NoteEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.AnimatorService.FigureAnimation;

/**
 * EditPolicy that can be installed on any GraphicalEditPart that hace outgoing
 * connections. When EditPart is primary selected, this EditPolicy will display
 * animations on every outgoing transition that is represented by polyline.
 * 
 * When installed on the Connection it will display animation when conection is
 * selected
 * 
 * @author Wojciech Zurek (wzurek@tibco.com)
 */
public class ConnectionsAnimatorEditPolicy extends GraphicalEditPolicy
        implements FigureAnimation, EditPartListener, NodeListener {

    public static final String EDIT_POLICY_ID = "animate.connections.policy"; //$NON-NLS-1$

    private boolean animationActive = false;

    private EditPolicyEnablementProvider policyEnablementProvider;

    private class FeedbacksAndPolicy {
        List feedbackList = null;

        ConnectionsAnimatorEditPolicy policy;

    }

    /**
     * Runnable with the animation
     */
    private final class AnimationStepRunnable implements Runnable {
        private final List scs;

        /**
         * the Constructor
         * 
         * @param scs
         *            - list of connections that should be animated
         */
        private AnimationStepRunnable(List scs) {
            super();
            this.scs = scs;
        }

        public void run() {
            if (!getHost().isActive()) {
                removeAnimationFeedback();
                return;
            }
            for (Iterator iter = scs.iterator(); iter.hasNext();) {
                GraphicalEditPart gep = (GraphicalEditPart) iter.next();
                IFigure fig = gep.getFigure();

                FeedbacksAndPolicy fap = (FeedbacksAndPolicy) feedback.get(fig);

                if (fap != null) {
                    fixFeedbackCount(fap.feedbackList, (Polyline) fig);
                    for (int i = 0; i < fap.feedbackList.size(); i++) {
                        IFigure f = (IFigure) fap.feedbackList.get(i);
                        if (f != null) {
                            final Polyline p = (Polyline) fig;
                            PointList points = p.getPoints();
                            Point point = tracer(points, step + (i * period));

                            if (point.equals(p.getPoints().getLastPoint())) {
                                point = p.getPoints().getFirstPoint();
                                f.setVisible(false);
                            } else {
                                Point nextP =
                                        tracer(points, step + 2 + (i * period));
                                fap.policy.incCycle(f, point, nextP);
                                f.setVisible(true);
                            }

                            Dimension ps = f.getPreferredSize();
                            f.setSize(ps);
                            f.setLocation(point.getCopy()
                                    .translate(-ps.width / 2, -ps.height / 2));
                        }
                    }
                }
            }
        }
    }

    /**
     * Elipse with Antialiasing switched on
     */
    public static final class SmoothElipse extends Ellipse {
        protected void outlineShape(Graphics graphics) {
            int aa = graphics.getAntialias();
            if (aa != SWT.ON) {
                graphics.setAntialias(SWT.ON);
                super.outlineShape(graphics);
                graphics.setAntialias(aa);
            } else {
                super.outlineShape(graphics);
            }
        }
    }

    /**
     * The Constructor
     * 
     * Show animation with default ellipse shape
     */
    public ConnectionsAnimatorEditPolicy(
            EditPolicyEnablementProvider policyEnablementProvider) {
        this.policyEnablementProvider = policyEnablementProvider;
    }

    // distance between points
    private int period = 120;

    // current step, if <0 animation is not running
    private int step = -1;

    // map connection figure to list of points
    private Map feedback = new HashMap();

    private Set animConnections = null;

    /**
     * Create feedback figures for given line and add them to Feedback Layer
     * 
     * @param line
     * @param policy
     */
    private void createFeedback(Polyline line,
            ConnectionsAnimatorEditPolicy policy) {
        PointList list = line.getPoints();

        // count lenght of the line
        double length = getLength(list);

        int count = (int) Math.ceil(length / period);

        List feedbacks = new ArrayList();
        for (int i = 0; i < count; i++) {
            IFigure el = policy.createFeedbackFigure();
            feedbacks.add(el);
            getLayer(LayerConstants.SCALED_FEEDBACK_LAYER).add(el);
            // addFeedback(el);
        }

        FeedbacksAndPolicy fap = new FeedbacksAndPolicy();
        fap.feedbackList = feedbacks;
        fap.policy = policy;
        feedback.put(line, fap);

    }

    /**
     * Create feedback for the host
     */
    private boolean start() {
        if (policyEnablementProvider.isPolicyEnabled(EDIT_POLICY_ID)) {
            EditPart ep = getHost();
            if (ep instanceof GraphicalEditPart) {
                GraphicalEditPart nep = (GraphicalEditPart) ep;

                if (animConnections == null) {
                    animConnections = getAnimatedConnections(nep);
                }

                for (Iterator iter = animConnections.iterator(); iter.hasNext();) {
                    GraphicalEditPart gep = (GraphicalEditPart) iter.next();

                    EditPolicy subEP = gep.getEditPolicy("Animation"); //$NON-NLS-1$
                    IFigure fig = gep.getFigure();
                    if (fig instanceof Polyline) {
                        if (subEP instanceof ConnectionsAnimatorEditPolicy) {
                            createFeedback((Polyline) fig,
                                    (ConnectionsAnimatorEditPolicy) subEP);
                        }
                    }
                }
                animationActive = true;
                return true;
            }
        }
        return false;
    }

    /**
     * starts listening to the host edit part
     */
    public void activate() {
        super.activate();
        getHost().addEditPartListener(this);
        if (getHost() instanceof NodeEditPart) {
            ((NodeEditPart) getHost()).addNodeListener(this);
        }
    }

    /**
     * stops listening to the host edit part
     */
    public void deactivate() {
        removeAnimationFeedback();
        if (getHost() instanceof NodeEditPart) {
            ((NodeEditPart) getHost()).removeNodeListener(this);
        }
        getHost().removeEditPartListener(this);
        super.deactivate();
    }

    /**
     * Step of the animation
     */
    public boolean step() {

        if (step < 0 || !getHost().isActive()) {
            return false;
        }

        EditPart ep = getHost();
        GraphicalEditPart nep = (GraphicalEditPart) ep;

        if (animConnections == null) {
            animConnections = getAnimatedConnections(nep);
        }

        if (animConnections != null && animConnections.size() > 0) {
            final List scs = new ArrayList();
            scs.addAll(animConnections);

            Display d;

            Control c = getHost().getViewer().getControl();
            if (c == null)
                return false;
            d = c.getDisplay();
            if (d == null)
                return false;

            // do animation in the UI thread
            d.syncExec(new AnimationStepRunnable(scs));
            boolean result = !feedback.isEmpty();
            step = result ? step + 5 : -1;
            if (step >= period) {
                step -= period;
            }
            return result;
        }

        return true;
    }

    /**
     * Returns list of connection editparts to animate.
     * 
     * @return
     */
    private Set getAnimatedConnections(GraphicalEditPart gep) {
        Set connections = new HashSet();

        GraphicalEditPart firstNode = null;

        if (!(gep instanceof ConnectionEditPart)) {
            firstNode = gep;
        }
        //
        // Disable animation of selected connection - just gets too confusing
        // when tring to edit bendpoints etc.
        else if (false) {
            connections.add(gep);

            EditPart target = ((ConnectionEditPart) gep).getTarget();
            if (isRecursThruObject(target)) {
                firstNode = (GraphicalEditPart) target;
            }
        }

        if (firstNode != null) {
            recursiveAddConnections(connections, firstNode);
        }

        return connections;
    }

    /**
     * Add animated connections to given list of connections recursing thru
     * gateways and link events
     * 
     * @param connections
     * @param firstNode
     */
    private void recursiveAddConnections(Set connections, GraphicalEditPart node) {
        // getSourceConnections may return EMPTY_LIST, but we may need to add
        // target connections so have to create our own.
        List nodeAnimatedConns = new ArrayList();

        List srcConns = node.getSourceConnections();

        nodeAnimatedConns.addAll(srcConns);

        // For association connections, include the connections that have node
        // as target.
        List tgtConns = node.getTargetConnections();

        for (Iterator iter = tgtConns.iterator(); iter.hasNext();) {
            ConnectionEditPart conn = (ConnectionEditPart) iter.next();

            if (conn instanceof AssociationEditPart) {
                if (!nodeAnimatedConns.contains(conn)) {
                    nodeAnimatedConns.add(conn);
                }
            }
        }

        List extraTargets = new ArrayList();

        for (Iterator iter = nodeAnimatedConns.iterator(); iter.hasNext();) {
            ConnectionEditPart conn = (ConnectionEditPart) iter.next();

            // Don't add same connection twice (in case we have a looping
            // connection).
            if (!connections.contains(conn) && conn.getFigure().isVisible()) {
                connections.add(conn);

                // Don't recurs for association connections that were picked up
                // because node was target object.
                if (!tgtConns.contains(conn)) {
                    // Get ready for recursion later by adding to list of
                    // gateways /
                    // link events
                    EditPart target = conn.getTarget();

                    if (isRecursThruObject(target)) {
                        extraTargets.add(target);

                    } else if (target instanceof EventEditPart) {

                        EventEditPart eventEditPart = (EventEditPart) target;
                        if (eventEditPart.getEventTriggerType() == EventTriggerType.EVENT_LINK_CATCH) {
                            // XPDL21 event link anything to do with
                            // EVENT_LINK_THROW
                            EventEditPart dest =
                                    eventEditPart.getLinkedToEditPart();

                            if (dest != null) {
                                // DO NOT add destination link event if it
                                // itself is the source of a link pair (it could
                                // be us OR lead back to us which would make us
                                // recurs forever!!!
                                if (dest.getLinkedToEditPart() == null) {
                                    extraTargets.add(target);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Recurs into any gateways/link events as target of conenctions.
        for (Iterator iter = extraTargets.iterator(); iter.hasNext();) {
            GraphicalEditPart extraEP = (GraphicalEditPart) iter.next();

            recursiveAddConnections(connections, extraEP);

        }

        if (node instanceof TaskEditPart) {
            // Add events that are attached to border of task.
            TaskEditPart taskEP = (TaskEditPart) node;

            Collection<EventEditPart> attachedEvents =
                    taskEP.getAttachedEvents();
            if (attachedEvents != null) {
                for (EventEditPart eventEP : attachedEvents) {
                    recursiveAddConnections(connections, eventEP);
                }
            }
        }

        // Finally check for link events and cross the gap.
        if (node instanceof EventEditPart) {

            EventEditPart eventEditPart = (EventEditPart) node;
            if (eventEditPart.getEventTriggerType() == EventTriggerType.EVENT_LINK_CATCH) {
                // XPDL21 event link anything to do with
                // EVENT_LINK_THROW

                EventEditPart dest = eventEditPart.getLinkedToEditPart();

                if (dest != null) {
                    // DO NOT add destination link event if it
                    // itself is the source of a link pair (it could
                    // be us OR lead back to us which would make us
                    // recurs forever!!!
                    if (dest.getLinkedToEditPart() == null) {
                        recursiveAddConnections(connections, dest);
                    }
                }
            }
        }
        return;
    }

    private boolean isRecursThruObject(EditPart target) {
        if (target instanceof GatewayEditPart
                || target instanceof DataObjectEditPart
                || target instanceof NoteEditPart) {
            return true;
        }

        return false;

    }

    /**
     * Create feedback figure that is animated on the path. Clients may extend.
     * 
     * @return newly created figure
     */
    protected IFigure createFeedbackFigure() {
        SmoothElipse el = new SmoothElipse();
        el.setPreferredSize(5, 5);
        el.setFill(true);
        el.setBackgroundColor(ColorConstants.lightBlue);
        el.setForegroundColor(ColorConstants.darkBlue);
        return el;
    }

    /**
     * Note: have to be invoked fromUI thread (Sid: I <i>think<i> that this
     * method fixzes up the number of rolling balls when the distance between
     * start and end object may have changed)
     * 
     * @param feedbackList
     * @param fig
     */
    protected void fixFeedbackCount(List feedbackList, Polyline fig) {
        // Only do this when the animaiton is active, this is run in a separate
        // thread which may get kicjed off after the feedback is removed but
        // BEFORE the animation stops - and it has the effect that the figures
        // get re-added :o(
        if (animationActive) {
            IFigure layer = getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);

            double length = getLength(fig.getPoints());
            int count = (int) Math.ceil(length / period);
            if (count < feedbackList.size()) {
                // remove additional points
                while (feedbackList.size() > count) {
                    IFigure f =
                            (IFigure) feedbackList.get(feedbackList.size() - 1);
                    if (f.getParent() != null) {
                        // removeFeedback(f);
                        layer.remove(f);
                    }
                    feedbackList.remove(feedbackList.size() - 1);
                }
            } else if (count > feedbackList.size()) {
                // add new points
                for (int i = feedbackList.size(); i < count; i++) {
                    IFigure ff = createFeedbackFigure();
                    layer.add(ff);
                    // addFeedback(ff);
                    feedbackList.add(ff);
                }
            }
        }
    }

    /*
     * @see
     * org.eclipse.gef.EditPartListener#childAdded(org.eclipse.gef.EditPart,
     * int)
     */
    public void childAdded(EditPart child, int index) {
        // do nothing
    }

    /*
     * @see
     * org.eclipse.gef.EditPartListener#partActivated(org.eclipse.gef.EditPart)
     */
    public void partActivated(EditPart editpart) {
        // do nothing
    }

    /*
     * @see
     * org.eclipse.gef.EditPartListener#partDeactivated(org.eclipse.gef.EditPart
     * )
     */
    public void partDeactivated(EditPart editpart) {
        // stop animation and remove feedback
        // do nothing
        removeAnimationFeedback();
    }

    /*
     * @see
     * org.eclipse.gef.EditPartListener#removingChild(org.eclipse.gef.EditPart,
     * int)
     */
    public void removingChild(EditPart child, int index) {
        // do nothing
    }

    /**
     * Respond for changes to Selection state of the host edit part by starting
     * or stoping the animation
     * 
     * @see org.eclipse.gef.EditPartListener#selectedStateChanged(org.eclipse.gef.EditPart)
     */
    public void selectedStateChanged(EditPart editpart) {
        int sel = getHost().getSelected();
        if (sel == EditPart.SELECTED_PRIMARY) {
            // start animation if not started already
            if (step < 0) {
                if (start()) {
                    new Thread(new Runnable() {
                        public void run() {
                            AnimatorService.animator
                                    .addAnimation(ConnectionsAnimatorEditPolicy.this);
                            step = 0;
                        }
                    }).start();
                }
            }
        } else {
            removeAnimationFeedback();
        }
    }

    /**
     * 
     */
    protected void removeAnimationFeedback() {
        animationActive = false;

        // IFigure fLayer = getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
        for (Iterator iter = feedback.values().iterator(); iter.hasNext();) {
            FeedbacksAndPolicy fap = (FeedbacksAndPolicy) iter.next();
            List l = fap.feedbackList;

            for (Iterator iterator = l.iterator(); iterator.hasNext();) {
                IFigure fig = (IFigure) iterator.next();
                if (fig.getParent() != null) {
                    fig.getParent().remove(fig);
                }
            }
        }
        new Thread(new Runnable() {
            public void run() {
                AnimatorService.animator
                        .removeAnimation(ConnectionsAnimatorEditPolicy.this);
                step = -1;
            }
        }).start();

        animConnections = null;
    }

    private double getLength(PointList list) {
        double length = 0;
        if (list.size() == 0) {
            return length;
        }
        Point s = list.getFirstPoint();
        Point p = null;
        double l = 0;
        int x;
        int y;
        for (int i = 1; i < list.size(); i++) {
            p = list.getPoint(i);
            x = s.x - p.x;
            y = s.y - p.y;
            l = Math.sqrt(x * x + y * y);
            length += l;
            s = p;
        }
        return length;
    }

    /**
     * Helper method to find position of given distance from start of the
     * poliline
     * 
     * @param list
     * @param distance
     * @return
     */
    private Point tracer(PointList list, int distance) {
        // find interesting segment
        double d = distance;
        double cl = 0;
        Point s = list.getFirstPoint();
        Point p = null;
        double l = 0;
        for (int i = 1; i < list.size(); i++) {
            p = list.getPoint(i);
            l =
                    Math.sqrt((s.x - p.x) * (s.x - p.x) + (s.y - p.y)
                            * (s.y - p.y));
            cl += l;
            if (cl > d) {
                break;
            }
            s = p;
        }
        if (cl < d || p == null) {
            return list.getLastPoint();
        }
        double st = (d - (cl - l)) / l;
        double x = (p.x - s.x) * st;
        double y = (p.y - s.y) * st;
        return new Point(s.x + x, s.y + y);
    }

    public void removingSourceConnection(ConnectionEditPart connection,
            int index) {
        // do nothing
    }

    public void removingTargetConnection(ConnectionEditPart connection,
            int index) {
        // do nothing
    }

    public void sourceConnectionAdded(ConnectionEditPart connection, int index) {
        // do nothing
    }

    public void targetConnectionAdded(ConnectionEditPart connection, int index) {
        // do nothing
    }

    public void incCycle(IFigure feedbackFigure, Point thisP, Point nextP) {
    };

}
