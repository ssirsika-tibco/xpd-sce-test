/*
 *  Copyright 2005 TIBCO Software Inc. 
 */

package com.tibco.xpd.processwidget.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.actions.XPDSnapToGrid;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.figures.HeaderFigure;
import com.tibco.xpd.processwidget.figures.HeaderFigureStyle;
import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.LaneHeaderFigure;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.BpmnSnapToGeometry;
import com.tibco.xpd.processwidget.policies.FlowContainerXYLayoutEditPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;

/**
 * Representation of BPMN's Lane - the container of Activities, Gateways, Events
 * etc.
 * 
 * @author wzurek
 */
public class LaneEditPart extends BaseGraphicalEditPart implements
        NodeEditPart, HoverProvider {

    public static final String PROPERTY_LANE_CLOSESTATE =
            "LANE_CLOSE_STATE_CHANGE"; //$NON-NLS-1$

    private List<PropertyChangeListener> diagramRefreshListeners =
            new ArrayList<PropertyChangeListener>();

    boolean firstRefresh = true;

    boolean previousClosedState = false;

    // This is the embedded sub-proc opened/closed property change listener that
    // is set on each task added. This will fire a diagram refresh event that
    // allows other edit parts to find out when tsub-proc is closed without
    // having to check for individual task changes.
    private PropertyChangeListener embSubProcDiagramRefreshListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    // Propagate embedded exp/collapse to our listeners
                    for (PropertyChangeListener listener : diagramRefreshListeners) {
                        listener.propertyChange(evt);
                    }
                }
            };

    private LaneFigure laneFigure;

    public LaneEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.lane_sel"); //$NON-NLS-1$
    }

    @Override
    protected IFigure createFigure() {
        laneFigure = new LaneFigure(getDiagramViewType());

        laneFigure.getHeaderFigure().setToolTip(new TooltipFigure(this));

        // Add listener for close button being pressed.
        laneFigure.getHeaderFigure()
                .addPropertyChangeListener(HeaderFigure.CLOSED_PROPERTY_CHANGE,
                        new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                LaneAdapter la = getLane();

                                Command cmd =
                                        la.getSetIsClosedCommand(getEditingDomain(),
                                                ((Boolean) evt.getNewValue())
                                                        .booleanValue());
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);

                            }
                        });
        return laneFigure;
    }

    /**
     * @see com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#deactivate()
     * 
     */
    @Override
    public void deactivate() {
        if (laneFigure != null) {
            laneFigure.dispose();
        }
        super.deactivate();
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new FlowContainerXYLayoutEditPolicy(getAdapterFactory(),
                        getEditingDomain()));

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());
        installEditPolicy("Snap Feedback", new SnapFeedbackPolicy()); //$NON-NLS-1$

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
     */
    @Override
    public void refresh() {
        super.refresh();

        if (firstRefresh) {
            firstRefresh = false;

            previousClosedState = getLane().isClosed();
        }

        if (previousClosedState != getLane().isClosed()) {
            previousClosedState = getLane().isClosed();

            fireLaneCloseChange();
        }

    }

    @Override
    protected void refreshVisuals() {
        LaneAdapter lane = getLane();
        LaneFigure f = (LaneFigure) getFigure();
        f.setHeaderText(lane.getName());
        f.setSize(lane.getSize());

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(this);

        Color fillColor =
                colors.getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                        getFillColorIDForPartType()).getColor();

        Color lineColor =
                colors.getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                        getLineColorIDForPartType()).getColor();

        f.setBackgroundColor(fillColor);
        f.setForegroundColor(lineColor);

        ((LaneFigure) getFigure()).setClosed(getLane().isClosed());

        f.revalidate();
    }

    /**
     * @return
     */
    private LaneAdapter getLane() {
        LaneAdapter lane = (LaneAdapter) getModelAdapter();
        return lane;
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        // Force resize of lane when stuff changes.
        LaneFigure fig = (LaneFigure) getFigure();
        if (fig != null) {
            // Re validate lane (so that getPreferredSize() is recalc'd from
            // content.
            fig.validate();

            // Have to revalidate the pool as it takes it's size from the widest
            // lane's preferred size
            for (Figure pool = (Figure) fig.getParent(); pool != null; pool =
                    (Figure) pool.getParent()) {
                if (pool instanceof PoolFigure) {
                    pool.validate();
                }
            }
        }
        refresh();
    }

    @Override
    public IFigure getContentPane() {
        return ((LaneFigure) getFigure()).getContentPane();
    }

    @Override
    protected void refreshChildren() {
        super.refreshChildren();
    }

    @Override
    protected EditPart createChild(Object model) {
        EditPart ep = (EditPart) getViewer().getEditPartRegistry().get(model);
        if (ep != null && ep.getParent() != null) {
            ep.getParent().refresh();
        }
        return super.createChild(model);
    }

    @Override
    protected List getModelChildren() {
        LaneAdapter lane = (LaneAdapter) getModelAdapter();
        List children = lane.getChildGraphicalNodes();

        List attachedEvents = new ArrayList();

        // Make sure that events attached to border of tasks are last in list!
        // This ensures that edit parts and figures are kept in correct order
        // for updates and display.
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            Object child = iter.next();

            Object adapter =
                    getAdapterFactory().adapt(child,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            if (adapter instanceof EventAdapter) {
                attachedEvents.add(child);

                iter.remove();
            }
        }

        if (attachedEvents.size() > 0) {
            children.addAll(attachedEvents);
        }

        return children;
    }

    private List getAllContainedNodes() {
        LaneAdapter lane = (LaneAdapter) getModelAdapter();
        List result = new ArrayList();
        result.addAll(lane.getChildGraphicalNodes());
        return result;
    }

    @Override
    public Object getAdapter(Class key) {
        if (key == BpmnScrollingGraphicalViewer.RevealRectangle.class) {
            IFigure f = getFigure();
            Rectangle bnds = f.getBounds().getCopy();
            f.translateToParent(bnds);
            bnds.width += bnds.x;
            bnds.x = 0;
            bnds.expand(5, 5);
            return bnds;
        } else if (key == SnapToHelper.class) {
            Boolean grid =
                    (Boolean) getViewer()
                            .getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
            Boolean geom =
                    (Boolean) getViewer()
                            .getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
            if (grid != null && grid.booleanValue() && geom != null
                    && geom.booleanValue()) {
                SnapToHelper[] helpers =
                        new SnapToHelper[] { new XPDSnapToGrid(this),
                                new BpmnSnapToGeometry(this) };
                return new CompoundSnapToHelper(helpers);
            }
            if (geom != null && geom.booleanValue()) {
                return new BpmnSnapToGeometry(this);
            }
            if (grid != null && grid.booleanValue()) {
                return new XPDSnapToGrid(this);
            }
        }
        return super.getAdapter(key);
    }

    @Override
    public EditPart getTargetEditPart(Request request) {
        Boolean childInvalidated =
                (Boolean) request.getExtendedData()
                        .get(XPD_CHILD_INVALIDATED_REQUEST);
        if (childInvalidated != null && childInvalidated.booleanValue() == true) {
            // Another editpart has already chcked the validity of move/add so
            // we don't wantto re-check for ourselves.
            //
            // i.e. child has said 'nominally this request would be allowed but
            // because of some side effect then it is not" therefore we DON'T
            // want the parent edit part to say " but it's ok for me!
            return null;
        }

        if (REQ_ADD.equals(request.getType())
                || REQ_CREATE.equals(request.getType())) {
            // Disallow move into / create object in closed pool.
            if (getLane().isClosed()) {
                return null;
            }
        }

        if (RequestConstants.REQ_SELECTION == request.getType()) {
            if (request instanceof SelectionRequest) {
                // ignore selection requests thats are located
                // inside contents pane, the user can select the
                // lane only by selecting the header
                SelectionRequest req = (SelectionRequest) request;

                if (!(req.getLastButtonPressed() == 3)) {
                    Point loc = req.getLocation();
                    if (loc != null) {
                        loc = loc.getCopy();
                        IFigure fig = getFigure();
                        fig.translateToRelative(loc);
                        if (((LaneFigure) fig).getContentPane()
                                .containsPoint(loc)) {
                            return getRoot();
                        }
                    }
                }

                // POOLLESS PAGEFLOW
                if (DiagramViewType.NO_POOLS.equals(getDiagramViewType())) {
                    if (REQ_SELECTION.equals(request.getType())) {
                        // Disallow lane selection in PageFlow mode (because
                        // it's treated as invisible.
                        //
                        // So redirect requests for selection to the process
                        // edit part.
                        /*
                         * when a quick fix was selected from editor in the
                         * pageflow process NPE was being thrown (location in
                         * the request object was null)
                         */
                        if (request instanceof SelectionRequest
                                && ((SelectionRequest) request).getLocation() != null) {
                            FlowContainerXYLayoutEditPolicy
                                    .setLastClickPos(this,
                                            (SelectionRequest) request);
                        }

                        EditPart ep = this.getParent();
                        while (ep != null) {
                            if (ep instanceof ProcessEditPart) {
                                return ep;
                            }
                            ep = ep.getParent();
                        }

                        return null;
                    }
                }
                // POOLLESS PAGEFLOW

            }
        } else if (REQ_ADD.equals(request.getType())
                || REQ_MOVE.equals(request.getType())
                || REQ_CREATE.equals(request.getType())
                || REQ_CLONE.equals(request.getType())) {

            if (request instanceof DropRequest) {
                DropRequest dropReq = (DropRequest) request;

                if (request instanceof ChangeBoundsRequest) {
                    ChangeBoundsRequest cbr = (ChangeBoundsRequest) request;

                    List eps = cbr.getEditParts();
                    if (eps.size() > 0 && eps.get(0) instanceof PoolEditPart) {
                        // Pass move of pool edit part on thru so process edit
                        // (via Pool ep)
                        // part can deal with it.
                        return null;
                    }

                    if (eps.size() > 0 && eps.get(0) instanceof LaneEditPart) {
                        // Pass move of lane edit part on thru so pool edit
                        // part can deal with it.
                        return null;
                    }

                }

                // Check if the request is within our bounds,
                // if it is then just call the base stuff (for things like add
                // swimlane etc
                Point location = dropReq.getLocation().getCopy();
                LaneFigure lane = (LaneFigure) getFigure();

                lane.translateToRelative(location);

                Rectangle lBnds = lane.getBounds();
                if (location.y >= lBnds.y
                        && location.y <= (lBnds.y + lBnds.height)
                        && location.x >= lBnds.x) {
                    return super.getTargetEditPart(request);
                } else {
                    // Sent a request outside of ourselves forget it.
                    return null;
                }
            }
        }

        return super.getTargetEditPart(request);
    }

    @Override
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new CellEditorLocator() {
            @Override
            public void relocate(CellEditor celleditor) {

                LaneFigure lf = (LaneFigure) getFigure();
                LaneHeaderFigure hdr = lf.getHeaderFigure();

                Rectangle b = hdr.getTextBounds();

                if (!HeaderFigureStyle.HORIZONTAL.equals(hdr
                        .getHeaderFigureStyle())) {
                    int w = b.width;
                    b.width = b.height;
                    b.height = w;

                    if (isClosed()) {
                        b.x += 35;
                        b.y -= 7;
                    }
                }

                b.width += 60;

                figure.translateToAbsolute(b);
                Text t = (Text) celleditor.getControl();
                org.eclipse.swt.graphics.Point pref =
                        t.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                pref.x += 20;
                pref.x = Math.max(pref.x, b.width);
                t.setBounds(b.x, b.y, pref.x, pref.y);
            }
        };
    }

    public void setLaneAlpha(int laneAlpha) {
        IFigure fig = getFigure();

        if (fig instanceof LaneFigure) {
            ((LaneFigure) fig).setLaneAlpha(laneAlpha);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        return ProcessWidgetColors.LANE_FILL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.LANE_LINE;
    }

    /**
     * Add listener for diagram refresh.
     * 
     * Pool's will fire a diagram refresh event when the closed state is changed
     * OR when a child lane fires a diagram refresh event.
     * 
     * NOTE: If you wish to listen to ANY pool's close (current or pool added in
     * future) then you can add a listener to process edit part.
     * 
     * @param listener
     */
    public void addDiagramRefreshListener(PropertyChangeListener listener) {
        if (diagramRefreshListeners.contains(listener)) {
            diagramRefreshListeners.remove(listener);
        }

        diagramRefreshListeners.add(listener);
    }

    public void removeDiagramRefreshListener(PropertyChangeListener listener) {
        diagramRefreshListeners.remove(listener);
    }

    private void fireLaneCloseChange() {
        LaneAdapter la = getLane();

        boolean isClosed = la.isClosed();

        PropertyChangeEvent evt =
                new PropertyChangeEvent(this, PROPERTY_LANE_CLOSESTATE,
                        new Boolean(!isClosed), new Boolean(isClosed));

        for (PropertyChangeListener listener : diagramRefreshListeners) {
            listener.propertyChange(evt);
        }
    }

    /**
     * Return true if pool is closed.
     * 
     * @return
     */
    public boolean isClosed() {
        return getLane().isClosed();
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info = new HoverInfo(Messages.LaneEditPart_Hover_tooltip);

        LaneAdapter la = getLane();

        String name = la.getName();

        info.addProperty(Messages.LaneEditPart_HoverName_label,
                name != null ? name : Messages.LaneEditPart_HoverNotSet_label);

        info.setDocumentationURL(la.getDocumentationURL(), this);

        return info;
    }

    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);

        if (childEditPart instanceof TaskEditPart) {
            ((TaskEditPart) childEditPart)
                    .addDiagramRefreshListener(embSubProcDiagramRefreshListener);
        }
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        // remove diagram refresh listener from lanes.
        if (childEditPart instanceof TaskEditPart) {
            ((TaskEditPart) childEditPart)
                    .removeDiagramRefreshListener(embSubProcDiagramRefreshListener);
        }

        super.removeChildVisual(childEditPart);
    }

}
