/*
 * Copyright 2006 TIBCO Software Inc.
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.GroupEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Diagram layout - it can contain Pools and Groups
 * 
 * @author wzurek
 */
public class DiagramLayoutPolicy extends XYLayoutEditPolicy {

    private final AdapterFactory adapterFactory;

    private final EditingDomain editingDomain;

    public DiagramLayoutPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        if (child instanceof PoolEditPart) {
            return new NonResizableEditPolicy();
        } else if (child instanceof GroupEditPart) {
            return new ResizableEditPolicy() {
                @Override
                protected IFigure createDragSourceFeedbackFigure() {
                    // Use a ghost rectangle for feedback
                    RoundedRectangle r = new RoundedRectangle();
                    r.setCornerDimensions(new Dimension(10, 10));
                    r.setLineWidth(2);
                    r.setOutlineXOR(true);
                    r.setLineStyle(Graphics.LINE_DASHDOT);
                    r.setFill(false);
                    r.setForegroundColor(ColorConstants.lightBlue);
                    r.setBounds(getInitialFeedbackBounds());
                    addFeedback(r);
                    return r;
                }
            };
        }
        return super.createChildEditPolicy(child);
    }

    @Override
    protected Command createAddCommand(EditPart child, Object constraint) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected Command createChangeConstraintCommand(EditPart child,
            Object constraint) {
        if (child instanceof GroupEditPart) {

            Object model = child.getModel();

            Rectangle rectangle = ((Rectangle) constraint);

            if (rectangle.x < 0 || rectangle.y < 0) {
                return UnexecutableCommand.INSTANCE;
            }

            if (child instanceof BaseGraphicalEditPart) {
                rectangle =
                        (Rectangle) ((BaseGraphicalEditPart) child)
                                .translateToModel(rectangle);
            }
            BaseGraphicalNodeAdapter ad =
                    (BaseGraphicalNodeAdapter) adapterFactory.adapt(model,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            if (rectangle.x < 0 || rectangle.y < 0) {
                return UnexecutableCommand.INSTANCE;
            }

            CompoundCommand result = new CompoundCommand();
            result.setLabel(Messages.DiagramLayoutPolicy_MoveGroup_menu);
            result.append(ad.getSetLocationCommand(editingDomain, rectangle
                    .getLocation(), rectangle.getSize()));

            if (!result.canExecute()) {
                System.out.println("ZONK!"); //$NON-NLS-1$
            }

            return new EMFCommandWrapper(editingDomain, result);

        } else if (child instanceof PoolEditPart
                && getHost() instanceof ProcessEditPart) {

            Rectangle locRec = ((Rectangle) constraint);

            List<Rectangle> poolRects = new ArrayList<Rectangle>();

            int poolIdx = 0;
            int idx = 0;
            for (Iterator iter = getHost().getChildren().iterator(); iter
                    .hasNext(); idx++) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof PoolEditPart) {
                    if (ep == child) {
                        poolIdx = idx;
                    } else {
                        poolRects.add(((PoolEditPart) ep).getFigure()
                                .getBounds());
                    }
                }
            }

            int index = 0;
            int finalIdx = -1;

            for (Iterator iter = poolRects.iterator(); iter.hasNext(); index++) {
                Rectangle rc = (Rectangle) iter.next();

                int halfY = rc.y + (rc.height / 2);
                int nextPoolY = Integer.MAX_VALUE;

                if ((index + 1) < poolRects.size()) {
                    Rectangle r = poolRects.get(index + 1);
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

            if (finalIdx != -1 && finalIdx != poolIdx) {
                ProcessEditPart procEP = (ProcessEditPart) getHost();
                ProcessDiagramAdapter pda =
                        (ProcessDiagramAdapter) procEP.getModelAdapter();

                return new EMFCommandWrapper(editingDomain, pda
                        .getMovePoolCommand(editingDomain, poolIdx, finalIdx));
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        ProcessEditPart pep = (ProcessEditPart) getHost();
        ProcessDiagramAdapter proc =
                (ProcessDiagramAdapter) pep.getModelAdapter();
        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(proc);

        // Get default fill / line colour id for object from tool-added extended
        // data
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(null, (String) request
                        .getExtendedData()
                        .get(PaletteFactory.EXTDATA_DEFAULT_FILL_COLORID));
        WidgetRGB lineColor =
                colors.getGraphicalNodeColor(null, (String) request
                        .getExtendedData()
                        .get(PaletteFactory.EXTDATA_DEFAULT_LINE_COLORID));

        if (request.getNewObjectType() == GroupAdapter.class) {

            Point loc = request.getLocation().getCopy();
            getHostFigure().translateToRelative(loc);
            Point org = getLayoutOrigin().getNegated();
            loc.translate(org);

            return new EMFCommandWrapper(pep.getEditingDomain(), proc
                    .getCreateNewGroupCommand(pep.getEditingDomain(),
                            loc,
                            request.getSize(),
                            lineColor.toString()));
        } else if (request.getNewObjectType() == PoolAdapter.class) {

            int position = findInsertPoolPosition(request);
            if (position < 0) {
                return UnexecutableCommand.INSTANCE;
            }

            return new EMFCommandWrapper(pep.getEditingDomain(), proc
                    .getCreateNewPoolCommand(pep.getEditingDomain(),
                            position,
                            Messages.DiagramLayoutPolicy_DefaultPoolName_label,
                            fillColor.toString(),
                            lineColor.toString()));
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * return index where to insert new element or -1 when it should not be
     * possible
     * 
     * @param req
     * @return
     */
    private int findInsertPoolPosition(CreateRequest req) {
        // Point loc = req.getLocation().getCopy();
        // getHostFigure().translateToRelative(loc);
        //
        // List children = getHost().getChildren();
        // int childMargin = 10;
        // int index = 0;
        // int bottomYOfLast = 0;
        //        
        // for (; index < children.size(); index++) {
        // GraphicalEditPart ep = (GraphicalEditPart) children.get(index);
        // Rectangle b = ep.getFigure().getBounds();
        //
        // if (loc.y >= bottomYOfLast && loc.y <= b.y) {
        // return index;
        // }
        //            
        // bottomYOfLast = b.bottom();
        // }

        Point loc = req.getLocation().getCopy();
        getHostFigure().translateToRelative(loc);

        List children = getHost().getChildren();
        int childMargin = 10;
        int index = 0;
        boolean first = true;
        for (; index < children.size(); index++) {
            GraphicalEditPart ep = (GraphicalEditPart) children.get(index);
            Rectangle b = ep.getFigure().getBounds().getCopy();

            if (first) {
                first = false;
                b.y = 0;
            }

            // Improve locating insert point.
            // If the mouse is within the first childMargin pixels of top of
            // this lane then insert above this lane.
            if (loc.y >= b.y && loc.y <= (b.y + childMargin)) {
                break;
            }
            // If the mouse is in the last childMargin pixels then insert below.
            else if (loc.y <= (b.y + b.height + ProcessEditPart.POOL_SPACING)
                    && loc.y >= ((b.y + b.height) - childMargin)) {
                index++;
                break;
            } else if (loc.y > b.y && loc.y <= (b.y + b.height)) {
                // Somewhere in the middle of this lane - forget it.
                index = -1;
                break;
            }
        }

        return index;
    }

    @Override
    protected Command getDeleteDependantCommand(Request request) {
        return UnexecutableCommand.INSTANCE;
    }

    // SID Defect:25921 - moved getTargetEditPart to DiagramLayoutPolicy

    // Our getTargetEditPart() for process will check for Addm Move Create and
    // Clone
    // reqauests (for any object except pool) and ask the pools whether the
    // request has
    // anything to do with them and give them the chance to say that drop object
    // to the right of their swim-lanes is ok.
    @Override
    public EditPart getTargetEditPart(Request request) {
        EditPart retEP = null;
        if (REQ_ADD.equals(request.getType())
                || REQ_MOVE.equals(request.getType())
                || REQ_CREATE.equals(request.getType())
                || REQ_CLONE.equals(request.getType())) {

            boolean redirectToPool = true;

            if (request instanceof CreateRequest) {
                if (((CreateRequest) request).getNewObjectType() == PoolAdapter.class
                /*
                 * || ((CreateRequest) request).getNewObjectType() ==
                 * LaneAdapter.class
                 */) {
                    redirectToPool = false;
                }
            } else if (request instanceof ChangeBoundsRequest) {
                ChangeBoundsRequest chgBnds = (ChangeBoundsRequest) request;

                List eps = chgBnds.getEditParts();
                if (eps != null) {
                    int numPools = 0;
                    boolean haveNonPool = false;

                    for (Iterator epi = eps.iterator(); epi.hasNext();) {
                        Object ep = epi.next();

                        if (ep instanceof PoolEditPart) {
                            numPools++;
                        } else {
                            haveNonPool = true;
                        }

                        if (ep instanceof PoolEditPart
                        /* || ep instanceof LaneEditPart */) {
                            redirectToPool = false;
                        }

                    }

                    if (numPools > 0 && haveNonPool) {
                        return null; // disallow pool + something else moves.
                    }

                } else {
                    redirectToPool = false;
                }

            }

            if (redirectToPool) {

                // Nothing doing from edit part policies, see if it's in one
                // of our children
                PoolEditPart lastPool = null;

                List pools = getHost().getChildren();
                for (Iterator iter = pools.iterator(); retEP == null
                        && iter.hasNext();) {
                    Object ep = iter.next();
                    if (ep instanceof PoolEditPart) {
                        PoolEditPart pool = (PoolEditPart) ep;

                        if (pool != null) {
                            retEP = pool.getTargetEditPart(request);
                            lastPool = pool;
                        }
                    }
                }

                // If none of the lanes came up with anything (i.e. drop
                // request was not right of anything
                // then pick on last lane of last pool).
                if (retEP == null && lastPool != null) {
                    // Do not allow add or create in closed pool
                    boolean noAdd = false;
                    if (REQ_ADD.equals(request.getType())
                            || REQ_CREATE.equals(request.getType())) {
                        PoolAdapter pa =
                                (PoolAdapter) lastPool.getModelAdapter();

                        if (pa.isClosed()) {
                            noAdd = true;
                        }
                    }

                    if (!noAdd) {
                        Boolean childInvalidated =
                                (Boolean) request
                                        .getExtendedData()
                                        .get(BaseGraphicalEditPart.XPD_CHILD_INVALIDATED_REQUEST);

                        if (childInvalidated != null
                                && childInvalidated.booleanValue() == true) {
                            // Another editpart has already chcked the validity
                            // of move/add so
                            // we don't wantto re-check for ourselves.
                            //
                            // i.e. child has said 'nominally this request would
                            // be allowed but
                            // because of some side effect then it is not"
                            // therefore we DON'T
                            // want the parent edit part to say " but it's ok
                            // for me!
                            return null;
                        }

                        List lanes = lastPool.getChildren();

                        if (lanes != null && !lanes.isEmpty()) {
                            // find last lane.
                            retEP = null;
                            for (Iterator iter = lanes.iterator(); iter
                                    .hasNext();) {
                                EditPart lane = (EditPart) iter.next();

                                if (lane instanceof LaneEditPart) {
                                    retEP = lane;
                                }
                            }

                            // Do not allow add or create in closed lane
                            if (retEP != null) {
                                if (((LaneEditPart) retEP).isClosed()) {
                                    retEP = null;
                                }
                            }

                        }
                    }
                }
            }
        }

        // If we didn't redirect the target edit part then get the default
        if (retEP == null) {
            retEP = super.getTargetEditPart(request);
        }

        return retEP;
    }

}
