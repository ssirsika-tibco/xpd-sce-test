/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * @author wzurek
 */
public class PoolLayoutEditPolicy extends XYLayoutEditPolicy {

    private Map feedback = new HashMap();

    private final AdapterFactory adapterFactory;

    private final EditingDomain editingDomain;

    public PoolLayoutEditPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;

    }

    protected EditPolicy createChildEditPolicy(EditPart child) {
        return new ResizableLaneEditPolicy();
    }

    protected Command getCreateCommand(CreateRequest req) {
        Object type = req.getNewObjectType();
        if (type == LaneAdapter.class) {

            Object parent = getHost().getModel();
            PoolAdapter ad =
                    (PoolAdapter) adapterFactory.adapt(parent,
                            ProcessWidgetConstants.ADAPTER_TYPE);
            int position =
                    findInsertLanePosition(req, (GraphicalEditPart) getHost());
            if (position < 0) {
                return UnexecutableCommand.INSTANCE;
            }

            ProcessWidgetColors colors = ProcessWidgetColors.getInstance(ad);

            // Get default fill / line colour id for object from tool-added
            // extended data
            WidgetRGB defaultFillColor =
                    colors.getGraphicalNodeColor(null, (String) req
                            .getExtendedData()
                            .get(PaletteFactory.EXTDATA_DEFAULT_FILL_COLORID));
            WidgetRGB fillColor = defaultFillColor;

            //
            // If adding the lane above the first or below a lane that has the
            // default lane colour then use the alternate lane colour (for task
            // libraryies)
            if (ProcessWidgetType.TASK_LIBRARY.equals((ad.getProcessType()))) {
                if (getHost().getChildren().size() > 0) {
                    LaneEditPart adjacentLane;
                    if (position == 0) {
                        adjacentLane =
                                (LaneEditPart) getHost().getChildren().get(0);
                    } else {
                        adjacentLane =
                                (LaneEditPart) getHost().getChildren()
                                        .get(position - 1);
                    }

                    WidgetRGB adjacentColor =
                            colors
                                    .getGraphicalNodeColor((GraphicalColorAdapter) adjacentLane
                                            .getModelAdapter(),
                                            adjacentLane
                                                    .getFillColorIDForPartType());

                    if (adjacentColor.equals(defaultFillColor)) {
                        fillColor =
                                colors
                                        .getGraphicalNodeColor(null,
                                                ProcessWidgetColors.LANE_ALTERNATE_FILL);
                    }

                }
            }

            WidgetRGB lineColor =
                    colors.getGraphicalNodeColor(null, (String) req
                            .getExtendedData()
                            .get(PaletteFactory.EXTDATA_DEFAULT_LINE_COLORID));

            return new EMFCommandWrapper(editingDomain, ad
                    .getCreateNewLaneCommand(editingDomain,
                            position,
                            fillColor.toString(),
                            lineColor.toString()));
        }
        return null;
    }

    /**
     * return index where to insert new element or -1 when it should not be
     * possible
     * 
     * @param req
     * @return
     */
    public static int findInsertLanePosition(CreateRequest req,
            GraphicalEditPart hostPoolPart) {
        Point loc = req.getLocation().getCopy();
        IFigure poolFigure = hostPoolPart.getFigure();
        poolFigure.translateToRelative(loc);

        List childLanes = hostPoolPart.getChildren();
        int childMargin = 10;
        int index = 0;
        boolean found = false;
        for (; index < childLanes.size(); index++) {
            GraphicalEditPart ep = (GraphicalEditPart) childLanes.get(index);
            Rectangle b = ep.getFigure().getBounds();

            // Improve locating insert point.
            // If the mouse is within the first childMargin pixels of top of
            // this lane then insert above this lane.
            if (loc.y >= (b.y - childMargin) && loc.y <= (b.y + childMargin)) {
                found = true;
                break;
            }
            // If the mouse is in the last childMargin pixels then insert below.
            else if (loc.y <= (b.y + b.height)
                    && loc.y >= ((b.y + b.height) - childMargin)) {
                index++;
                found = true;
                break;
            } else if (loc.y > b.y && loc.y <= (b.y + b.height)) {
                // Somewhere in the middle of this lane - forget it.
                index = -1;
                break;
            }
        }

        if (!found && index != -1) {
            // Location is beyond last lane in this pool
            // BUT if it is ABOVE next pool then allow it!
            index = -1;

            IFigure parent = poolFigure.getParent();
            if (parent != null) {
                loc = req.getLocation().getCopy();
                parent.translateToRelative(loc);

                Rectangle hostBnds = poolFigure.getBounds();
                if (loc.y >= hostBnds.y) {
                    List siblings = parent.getChildren();
                    IFigure nextSibling = null;
                    int nextSibIdx = 0;
                    for (Iterator iterator = siblings.iterator(); iterator
                            .hasNext();) {
                        IFigure sibling = (IFigure) iterator.next();
                        nextSibIdx++;

                        if (sibling == poolFigure) {
                            if (iterator.hasNext()) {
                                nextSibling = (IFigure) iterator.next();
                                nextSibIdx++;
                            }
                            break;
                        }
                    }

                    if (nextSibling == null) {
                        // location is under last pool figure (our host)!
                        index = childLanes.size();
                    } else {
                        Rectangle nextBnds = nextSibling.getBounds();

                        if (loc.y < (nextBnds.y - 0)) {
                            index = childLanes.size();
                        }
                    }
                }
            }
        }

        return index;
    }

    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }

    protected void showLayoutTargetFeedback(Request request) {
        if (REQ_RESIZE.equals(request.getType())) {
            ChangeBoundsRequest req = (ChangeBoundsRequest) request;
            Dimension sd = req.getSizeDelta();
            List parts = req.getEditParts();

            for (Iterator iter = parts.iterator(); iter.hasNext();) {

                GraphicalEditPart ep = (GraphicalEditPart) iter.next();
                Rectangle b = ep.getFigure().getBounds().getCopy();

                ep.getFigure().translateToAbsolute(b);

                IFigure l = getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
                int h = b.y + b.height + sd.height;
                PointList list =
                        new PointList(new int[] { b.x, h, b.x + b.width, h });

                l.translateToRelative(list);

                if (feedback.containsKey(ep)) {
                    ((Polyline) feedback.get(ep)).setPoints(list);
                } else {
                    Polyline f = new Polyline();
                    f.setForegroundColor(ColorConstants.lightGray);
                    f.setLineStyle(SWT.LINE_DASHDOT);
                    f.setPoints(list);
                    l.add(f);
                    feedback.put(ep, f);
                }
            }
        } else if (REQ_CREATE.equals(request.getType())) {
            CreateRequest req = (CreateRequest) request;
            if (req.getNewObjectType() == LaneAdapter.class) {
                // Point loc = req.getLocation().getCopy();
                IFigure fig = getHostFigure();
                // fig.translateToRelative(loc);
                Rectangle b = fig.getBounds();

                IFigure l = getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);

                int position =
                        findInsertLanePosition(req,
                                (GraphicalEditPart) getHost());
                if (position < 0) {
                    if (feedback.containsKey(getHost())) {
                        IFigure f = (IFigure) feedback.get(getHost());
                        if (f.getParent() != null) {
                            f.getParent().remove(f);
                        }
                        feedback.remove(getHost());
                    }
                    return;
                }

                int top;
                List children = getHost().getChildren();
                if (children.size() == position) {
                    top = b.y + b.height;
                } else {
                    GraphicalEditPart c =
                            (GraphicalEditPart) children.get(position);
                    top = c.getFigure().getBounds().y;
                }

                PointList list =
                        new PointList(
                                new int[] { b.x, top, b.x + b.width, top });

                if (feedback.containsKey(getHost())) {
                    ((Polyline) feedback.get(getHost())).setPoints(list);
                } else {
                    Polyline f = new Polyline();
                    f.setForegroundColor(ColorConstants.black);
                    f.setLineWidth(3);
                    f.setPoints(list);
                    l.add(f);
                    feedback.put(getHost(), f);
                }
            }
        }
    }

    protected void eraseLayoutTargetFeedback(Request request) {
        for (Iterator iter = feedback.values().iterator(); iter.hasNext();) {
            IFigure f = (IFigure) iter.next();
            if (f.getParent() != null) {
                f.getParent().remove(f);
            }
        }
        feedback.clear();
    }

    public void showTargetFeedback(Request request) {
        if (REQ_RESIZE.equals(request.getType())) {
            showLayoutTargetFeedback(request);

        } else if (REQ_SELECTION.equals(request.getType())) {
            // For selection requests store location relative to
            // Pool (for pop-up menu things like Paste.
            GraphicalEditPart gep = (GraphicalEditPart) getHost();

            if (request instanceof SelectionRequest) {
                SelectionRequest req = (SelectionRequest) request;
                Point loc = req.getLocation().getCopy();

                PoolFigure hostFigure = (PoolFigure) getHostFigure();
                Rectangle bnds = hostFigure.getBounds().getCopy();

                hostFigure.translateToRelative(loc);

                loc.translate(bnds.getLocation().negate());

                gep
                        .getViewer()
                        .setProperty(ProcessWidgetConstants.VIEWPROP_FLOWCONTAINER_LASTCLICKPOS,
                                loc);

            }
        } else {
            super.showTargetFeedback(request);
        }
    }

    public void eraseTargetFeedback(Request request) {

        if (REQ_RESIZE.equals(request.getType())) {
            eraseLayoutTargetFeedback(request);
        } else {
            super.eraseTargetFeedback(request);
        }
    }

    @Override
    protected Command createChangeConstraintCommand(EditPart child,
            Object constraint) {

        if (child instanceof LaneEditPart) {
            Rectangle locRec = ((Rectangle) constraint);

            int laneIdx = getHost().getChildren().indexOf(child);
            if (laneIdx != -1) {

                int finalIdx = getChangeConstraintInsertPos(child, locRec);

                if (finalIdx != -1 && finalIdx != laneIdx) {
                    PoolEditPart poolEP = (PoolEditPart) getHost();
                    PoolAdapter pda = (PoolAdapter) poolEP.getModelAdapter();

                    return new EMFCommandWrapper(editingDomain, pda
                            .getMoveLaneCommand(editingDomain,
                                    laneIdx,
                                    finalIdx));
                }
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Return the insert position in lane list from the location rectangle from
     * a change constraint / add existing lane to different pool command.
     * 
     * @param child
     * @param locRec
     * @param laneRects
     * @return
     */
    private int getChangeConstraintInsertPos(EditPart child, Rectangle locRec) {
        if (getHost().getChildren().size() == 0) {
            return 0;
        }

        List<Rectangle> laneRects = new ArrayList<Rectangle>();

        int idx = 0;
        for (Iterator iter = getHost().getChildren().iterator(); iter.hasNext(); idx++) {
            EditPart ep = (EditPart) iter.next();

            if (ep instanceof LaneEditPart) {
                if (ep != child) {
                    Rectangle bounds =
                            ((LaneEditPart) ep).getFigure().getBounds()
                                    .getCopy();
                    bounds.translate(getHostFigure().getBounds().getTopLeft()
                            .negate());
                    laneRects.add(bounds);
                }
            }
        }

        int index = 0;
        int finalIdx = -1;

        for (Iterator iter = laneRects.iterator(); iter.hasNext(); index++) {
            Rectangle rc = (Rectangle) iter.next();

            int halfY = rc.y + (rc.height / 2);
            int nextPoolY = Integer.MAX_VALUE;

            if ((index + 1) < laneRects.size()) {
                Rectangle r = laneRects.get(index + 1);
                nextPoolY = r.y;
            }

            if (locRec.y <= halfY && locRec.y >= rc.y) {
                finalIdx = index;
                break;
            } else if (locRec.y >= halfY && locRec.y < nextPoolY) {
                finalIdx = index + 1;
                break;
            }
        }
        return finalIdx;
    }

    @Override
    protected Command createAddCommand(EditPart child, Object constraint) {
        if (child instanceof LaneEditPart) {
            PoolEditPart poolEP = (PoolEditPart) getHost();
            PoolAdapter pda = (PoolAdapter) poolEP.getModelAdapter();

            int pos =
                    getChangeConstraintInsertPos(child, (Rectangle) constraint);

            if (pos != -1) {
                return new EMFCommandWrapper(editingDomain,
                        pda.getAddLaneCommand(editingDomain,
                                child.getModel(),
                                pos));
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

}
