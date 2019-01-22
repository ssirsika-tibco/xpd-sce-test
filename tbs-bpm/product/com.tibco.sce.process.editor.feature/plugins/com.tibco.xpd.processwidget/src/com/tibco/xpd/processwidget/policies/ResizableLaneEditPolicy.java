package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RelativeLocator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

public class ResizableLaneEditPolicy extends SelectionHandlesEditPolicy
        implements Locator {

    public class ResizeLaneHandle extends SquareHandle {
        protected DragTracker createDragTracker() {
            return new ResizeTracker(getOwner(), PositionConstants.SOUTH);
        }

        public void paintFigure(Graphics g) {
            // TODO Auto-generated method stub
            super.paintFigure(g);
        }

        /**
         * Creates a MoveHandle for the given <code>GraphicalEditPart</code>
         * using the given <code>Locator</code>.
         * 
         * @param owner
         *            The GraphicalEditPart to be moved by this handle.
         * @param loc
         *            The Locator used to place the handle.
         */
        public ResizeLaneHandle(GraphicalEditPart owner, Locator loc) {
            super(owner, loc);
            initialize();
        }

        /**
         * Returns <code>true</code> if the point (x,y) is contained within
         * this handle.
         * 
         * @param x
         *            The x coordinate.
         * @param y
         *            The y coordinate.
         * @return <code>true</code> if the point (x,y) is contained within
         *         this handle.
         */
        public boolean containsPoint(int x, int y) {
            Rectangle rct = getBounds().getCopy();
            rct.expand(0, rct.height);

            return (rct.contains(x, y));
        }

        /**
         * Initializes the handle. Sets the {@link DragTracker} and DragCursor.
         */
        protected void initialize() {
            setOpaque(true);
            // setBackgroundColor(ColorConstants.black);
            setCursor(Cursors.SIZENS);
        }
    }

    private IFigure feedback;

    protected List createSelectionHandles() {

        List result = new ArrayList();

        result.add(new MoveHandle((GraphicalEditPart) getHost(), new Locator() {
            public void relocate(IFigure target) {
                // Use HandleBounds interface if it's supported.
                IFigure fig = getHostFigure();

                if (fig instanceof HandleBounds) {
                    Rectangle rct = ((HandleBounds) fig).getHandleBounds()
                            .getCopy();

                    // Translate to absolute for host figure then back to
                    // relative to MoveHandle figure
                    // (to take position / zoom into account).
                    fig.translateToAbsolute(rct);
                    target.translateToRelative(rct);

                    target.setBounds(rct);
                } else {
                    // Doesn't support HandleBounds so just use full bounds.
                    Rectangle rct = fig.getBounds().getCopy();

                    // Translate to absolute for host figure then back to
                    // relative to MoveHandle figure
                    // (to take position / zoom into account).
                    fig.translateToAbsolute(rct);
                    target.translateToRelative(rct);

                    target.setBounds(rct);
                }

            }
        }));

        // Resize locator and handle.

        IFigure fig = getHostFigure();
        if (fig instanceof LaneFigure) {

            RelativeLocator hdlLoc = new RelativeLocator(fig,
                    PositionConstants.SOUTH) {
                public void relocate(IFigure target) {

                    IFigure fig = getHostFigure();
                    if (fig instanceof LaneFigure) {
                        Rectangle rct = fig.getBounds().getCopy();

                        fig.translateToAbsolute(rct);

                        Dimension d = target.getPreferredSize().getCopy();

                        rct.y = (rct.y + rct.height) - (d.height / 2);
                        rct.height = d.height;

                        target.translateToRelative(rct);
                        target.setBounds(rct);
                    } else {
                        super.relocate(target);
                    }

                };
            };

            // Disallow resize on closed lane.
            if (!((LaneEditPart) getHost()).isClosed()) {

                ResizeLaneHandle hdl = new ResizeLaneHandle(
                        (GraphicalEditPart) getHost(), hdlLoc);

                Dimension dx = hdl.getPreferredSize().getCopy();

                dx.height = dx.height - 2;
                hdl.setSize(dx);
                hdl.setPreferredSize(dx);

                result.add(hdl);
            }
        }

        return result;
    }

    public Command getCommand(Request request) {
        Command cmd = null;

        if (REQ_RESIZE.equals(request.getType())) {
            cmd = getResizeCommand((ChangeBoundsRequest) request);
        } else if (REQ_MOVE.equals(request.getType())) {
            cmd = getMoveCommand((ChangeBoundsRequest) request);
        } else {
            cmd = super.getCommand(request);
        }

        return cmd;
    }

    /**
     * Returns the command contribution to a change bounds request. The
     * implementation actually redispatches the request to the host's parent
     * editpart as a {@link RequestConstants#REQ_MOVE_CHILDREN} request. The
     * parent's contribution is returned.
     * 
     * @param request
     *            the change bounds request
     * @return the command contribution to the request
     */
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_MOVE_CHILDREN);
        req.setEditParts(getHost());

        req.setMoveDelta(request.getMoveDelta());
        req.setSizeDelta(request.getSizeDelta());
        req.setLocation(request.getLocation());
        req.setExtendedData(request.getExtendedData());
        return getHost().getParent().getCommand(req);
    }

    private Command getResizeCommand(ChangeBoundsRequest request) {
        LaneEditPart laneEP = (LaneEditPart) getHost();

        if (laneEP.isClosed()) {
            // Disallow resize of closed lane.
            return UnexecutableCommand.INSTANCE;
        }

        LaneAdapter lane = (LaneAdapter) laneEP.getModelAdapter();

        int h = getHostFigure().getBounds().height;
        h += (int) (request.getSizeDelta().height / XPDFigureUtilities
                .getFigureScale(getHostFigure()));

        return new EMFCommandWrapper(laneEP.getEditingDomain(), lane
                .getSetSizeCommand(laneEP.getEditingDomain(), h));
    }

    public boolean understandsRequest(Request req) {
        if (REQ_RESIZE.equals(req.getType())) {
            return true;
        } else if (REQ_MOVE.equals(req.getType())) {
            return true;
        }

        return super.understandsRequest(req);
    }

    public void relocate(IFigure target) {
        Rectangle bnds = getHostFigure().getBounds();
        target
                .setBounds(new Rectangle(bnds.x, bnds.y + bnds.height - 1, 30,
                        3));
    }

    public void showSourceFeedback(Request request) {
        if ((REQ_MOVE.equals(request.getType()))
                || REQ_ADD.equals(request.getType())
                || REQ_CLONE.equals(request.getType())) {
            showChangeBoundsFeedback((ChangeBoundsRequest) request);
        }
    }

    /**
     * @see org.eclipse.gef.EditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
     */
    public void eraseSourceFeedback(Request request) {
        if ((REQ_MOVE.equals(request.getType()))
                || REQ_CLONE.equals(request.getType())
                || REQ_ADD.equals(request.getType())) {
            eraseChangeBoundsFeedback((ChangeBoundsRequest) request);
        }
    }

    protected void eraseChangeBoundsFeedback(ChangeBoundsRequest request) {
        if (feedback != null) {
            removeFeedback(feedback);
        }
        feedback = null;
    }

    /**
     * Lazily creates and returns the feedback figure used during drags.
     * 
     * @return the feedback figure
     */
    protected IFigure getDragSourceFeedbackFigure() {
        if (feedback == null)
            feedback = createDragSourceFeedbackFigure();
        return feedback;
    }

    protected IFigure createDragSourceFeedbackFigure() {
        // Use a ghost rectangle for feedback
        RectangleFigure r = new RectangleFigure();
        FigureUtilities.makeGhostShape(r);
        r.setLineStyle(Graphics.LINE_DOT);
        r.setForegroundColor(ColorConstants.white);
        r.setBounds(getInitialFeedbackBounds());
        addFeedback(r);
        return r;
    }

    protected Rectangle getInitialFeedbackBounds() {
        if (((GraphicalEditPart) getHost()).getFigure() instanceof HandleBounds)
            return ((HandleBounds) ((GraphicalEditPart) getHost()).getFigure())
                    .getHandleBounds();
        return ((GraphicalEditPart) getHost()).getFigure().getBounds();
    }

    protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
        IFigure feedback = getDragSourceFeedbackFigure();

        PrecisionRectangle rect = new PrecisionRectangle(
                getInitialFeedbackBounds().getCopy());

        // move / size delta are in relative co-ordinates so adjust before
        // translateToAbsolute
        // otherwise doesn't take zoom into account.
        getHostFigure().translateToAbsolute(rect);
        rect.translate(request.getMoveDelta());
        rect.resize(request.getSizeDelta());

        feedback.translateToRelative(rect);
        feedback.setBounds(rect);
    }

}
