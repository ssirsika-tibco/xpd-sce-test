/**
 * Copyright TIBCO inc (c) 2007
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;

import com.tibco.xpd.processwidget.figures.FadeablePolylineConnection;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.neatstuff.TimedFadeUpController;
import com.tibco.xpd.processwidget.neatstuff.TimedFadeUpController.FadeUpCompleteListener;

/**
 * Edit policy designed to be set as the SELECTION_FEEDBACK_ROLE of objects that
 * reference other objects.
 * <p>
 * The subclass can provide a list of references to / from the edit part that
 * the policy is installed on and on mouse-over feedback lines will be drawn to
 * the returned objects.
 * 
 * @author aallway
 */
public abstract class HighlightReferencesPolicy extends GraphicalEditPolicy
        implements FadeUpCompleteListener {

    /**
     * As only one edit policy can be installed for a particular policy role,
     * this class allows the creator to wrap another policy if they wish.
     */
    private GraphicalEditPolicy wrappedEditPolicy = null;

    /**
     * List of feedback connection lines currently on display.
     */
    private List<FadeablePolylineConnection> refConnectionFeedbacks =
            Collections.emptyList();

    private TimedFadeUpController fadeUpController = null;

    /**
     * Because only one edit policy can be installed for a particular policy
     * role, this class allows the creator to wrap another policy if they wish.
     * <p>
     * This method allows the owner to set the wrapped edit policy. Each
     * GraphicalEditPolicy method will call the wrapped edit policy first.
     * 
     * @param wrappedEditPolicy
     */
    public void setWrappedEditPolicy(GraphicalEditPolicy wrappedEditPolicy) {
        this.wrappedEditPolicy = wrappedEditPolicy;
    }

    /**
     * Override to return a list of graphical nodes that the host of this policy
     * is referenced by.
     * <p>
     * i.e. the arrow direction of feedback line is into the host.
     * 
     * @return List of referenced object or null if none.
     */
    protected abstract Collection<GraphicalEditPart> getReferencedFromObjects();

    /**
     * Override to return a list of graphical nodes that the host of this policy
     * references.
     * <p>
     * i.e. the arrow direction of feedback line is into the referenced object.
     * 
     * @return List of referenced object or null if none.
     */
    protected abstract Collection<GraphicalEditPart> getReferenceToObjects();

    /**
     * Get the reference highlight line connection anchor for the given edit
     * part (which is either the host or a referenced/referencing object.
     * 
     * @param editPart
     */
    protected abstract ConnectionAnchor getConnectionAnchor(
            GraphicalEditPart editPart);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.policies.ConnectableGraphicalNodeObjectEditPolicy
     * #showTargetFeedback(org.eclipse.gef.Request)
     */
    @Override
    public void showTargetFeedback(Request request) {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.showTargetFeedback(request);
        }

        super.showTargetFeedback(request);

        if (REQ_SELECTION.equals(request.getType())) {

            // Only create figures the first time select request gets fired
            // (l;ist will be cleared when we are deactivated.
            if (refConnectionFeedbacks.size() < 1) {
                refConnectionFeedbacks =
                        new ArrayList<FadeablePolylineConnection>();

                // Create feedback for all the objects hosty references.
                Collection<GraphicalEditPart> referencedEditParts =
                        getReferenceToObjects();

                if (referencedEditParts != null
                        && referencedEditParts.size() > 0) {

                    for (GraphicalEditPart referencedEditPart : referencedEditParts) {

                        FadeablePolylineConnection feedbackLine =
                                createReferenceFeebackFigure((GraphicalEditPart) getHost(),
                                        referencedEditPart);

                        if (feedbackLine != null) {
                            refConnectionFeedbacks.add(feedbackLine);
                            getFeedbackLayer().add(feedbackLine);
                        }
                    }
                }

                // Create feedback for all the objects host is referenced by.
                Collection<GraphicalEditPart> referencingEditParts =
                        getReferencedFromObjects();

                if (referencingEditParts != null
                        && referencingEditParts.size() > 0) {

                    for (GraphicalEditPart referencingEditPart : referencingEditParts) {

                        FadeablePolylineConnection feedbackLine =
                                createReferenceFeebackFigure(referencingEditPart,
                                        (GraphicalEditPart) getHost());

                        if (feedbackLine != null) {
                            refConnectionFeedbacks.add(feedbackLine);
                            getFeedbackLayer().add(feedbackLine);
                        }
                    }
                }

                if (refConnectionFeedbacks.size() > 0) {
                    fadeUpController =
                            new TimedFadeUpController(refConnectionFeedbacks,
                                    0.0f, 0.75f, 125, this);
                    fadeUpController.start(500);
                    
                }
            }
        }
    }

    private FadeablePolylineConnection createReferenceFeebackFigure(
            GraphicalEditPart srcEP, GraphicalEditPart tgtEP) {

        FadeablePolylineConnection feedbackLine =
                new FadeablePolylineConnection();

        feedbackLine.setLineStyle(Graphics.LINE_DASHDOT);
        feedbackLine.setForegroundColor(ColorConstants.lightGray);
        feedbackLine.setLineWidth(2);

        PolygonDecoration arrow = new PolygonDecoration();
        arrow.setTemplate(new PointList(new int[] { 0, 0, -1, 1, -1, -1 }));
        feedbackLine.setTargetDecoration(arrow);

//        feedbackLine.setVisible(false);

        feedbackLine.setSourceAnchor(getConnectionAnchor(srcEP));
        feedbackLine.setTargetAnchor(getConnectionAnchor(tgtEP));

        return feedbackLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.policies.ConnectableGraphicalNodeObjectEditPolicy
     * #eraseTargetFeedback(org.eclipse.gef.Request)
     */
    @Override
    public void eraseTargetFeedback(Request request) {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.eraseTargetFeedback(request);
        }

        super.eraseTargetFeedback(request);

        eraseFeedbackLines();
    }

    private void eraseFeedbackLines() {
        if (fadeUpController != null) {
            if (fadeUpController.isRunning()) {
                fadeUpController.cancel();
            }
            fadeUpController = null;
        }

        for (FadeablePolylineConnection feedbackLine : refConnectionFeedbacks) {
            if (feedbackLine != null) {
                IFigure tgtFigure = feedbackLine.getTargetAnchor().getOwner();
                IFigure srcFigure = feedbackLine.getSourceAnchor().getOwner();

                getFeedbackLayer().remove(feedbackLine);

                if (tgtFigure instanceof IHighlightableFigure) {
                    ((IHighlightableFigure) tgtFigure).setHighlight(false);
                }
                if (srcFigure instanceof IHighlightableFigure) {
                    ((IHighlightableFigure) srcFigure).setHighlight(false);
                }
            }
        }

        refConnectionFeedbacks = Collections.emptyList();
    }

    /**
     * When fade up completes highlight the two objects and the end of the
     * feedback lines.
     */
    public void fadeUpControllerComplete() {
        for (FadeablePolylineConnection feedbackLine : refConnectionFeedbacks) {
            if (feedbackLine != null) {
                IFigure tgtFigure = feedbackLine.getTargetAnchor().getOwner();
                IFigure srcFigure = feedbackLine.getSourceAnchor().getOwner();

                if (tgtFigure instanceof IHighlightableFigure) {
                    ((IHighlightableFigure) tgtFigure).setHighlight(true);
                }
                if (srcFigure instanceof IHighlightableFigure) {
                    ((IHighlightableFigure) srcFigure).setHighlight(true);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#deactivate()
     */
    @Override
    public void deactivate() {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.deactivate();
        }

        super.deactivate();

        //
        // Note, when task is re-parented then we don't always get an
        // eraseFeedback()
        // So this is the last-chance-saloon to do so!
        eraseFeedbackLines();
    }

    /**
     * 
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#activate()
     */
    public void activate() {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.activate();
        }
        super.activate();
    }

    /**
     * @param request
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
     */
    public void eraseSourceFeedback(Request request) {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.eraseSourceFeedback(request);
        }
        super.eraseSourceFeedback(request);
    }

    /**
     * @param request
     * @return
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getCommand(org.eclipse.gef.Request)
     */
    public Command getCommand(Request request) {
        Command ret = null;
        if (wrappedEditPolicy != null) {
            ret = wrappedEditPolicy.getCommand(request);
        }

        if (ret == null) {
            ret = super.getCommand(request);
        }
        return ret;
    }

    /**
     * @param request
     * @return
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
     */
    public EditPart getTargetEditPart(Request request) {
        EditPart ret = null;
        if (wrappedEditPolicy != null) {
            ret = wrappedEditPolicy.getTargetEditPart(request);
        }

        if (ret == null) {
            ret = super.getTargetEditPart(request);
        }

        return ret;
    }

    /**
     * @param request
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#showSourceFeedback(org.eclipse.gef.Request)
     */
    public void showSourceFeedback(Request request) {
        if (wrappedEditPolicy != null) {
            wrappedEditPolicy.showSourceFeedback(request);
        }
        super.showSourceFeedback(request);
    }

    /**
     * @param req
     * @return
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#understandsRequest(org.eclipse.gef.Request)
     */
    public boolean understandsRequest(Request req) {
        if (wrappedEditPolicy != null) {
            return wrappedEditPolicy.understandsRequest(req);
        }
        return super.understandsRequest(req);
    }
}
