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

package com.tibco.xpd.processwidget.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.figures.HeaderFigure;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.anchors.FixedOrientationAnchor;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.AllConnectionTypesNodeEditPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.policies.PoolLayoutEditPolicy;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;

/**
 * @author wzurek
 */
public class PoolEditPart extends BaseGraphicalEditPart implements
        HoverProvider {

    public static final String PROPERTY_POOL_CLOSESTATE =
            "POOL_CLOSE_STATE_CHANGE"; //$NON-NLS-1$

    private static final String POOL_SOURCEANCHOR_FIXED =
            "POOL_SOURCEANCHOR_FIXED"; //$NON-NLS-1$

    private static final String POOL_TARGETANCHOR_FIXED =
            "POOL_TARGETANCHOR_FIXED"; //$NON-NLS-1$

    Color oddLaneColor = null;

    Color evenLaneColor = null;

    Color poolColor = null;

    private List<PropertyChangeListener> diagramRefreshListeners =
            new ArrayList<PropertyChangeListener>();

    boolean firstRefresh = true;

    boolean previousClosedState = false;

    // This is the pool closed property change listener that is set on each
    // pool added. This will fire a diagram refresh event that allows other edit
    // parts to find out when ANY pool is closed without having to check for
    // pool changes.
    private PropertyChangeListener laneDiagramRefreshListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    // Propagate pool close to our listeners
                    for (PropertyChangeListener listener : diagramRefreshListeners) {
                        listener.propertyChange(evt);
                    }
                }
            };

    private PoolFigure poolFigure;

    public PoolEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.pool_sel"); //$NON-NLS-1$
    }

    @Override
    protected IFigure createFigure() {
        poolFigure = new PoolFigure(getDiagramViewType());

        poolFigure.getHeaderFigure().setToolTip(new TooltipFigure(this));

        poolFigure
                .addPropertyChangeListener("parent", new PropertyChangeListener() { //$NON-NLS-1$
                            FigureListener fl;

                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if (evt.getOldValue() != null && fl != null) {
                                    IFigure op = (IFigure) evt.getNewValue();
                                    if (op != null) {
                                        op.removeFigureListener(fl);
                                    }
                                }
                                if (fl == null) {
                                    fl = new FigureListener() {
                                        @Override
                                        public void figureMoved(IFigure source) {
                                            getFigure().getLayoutManager()
                                                    .layout(getFigure());
                                        }
                                    };
                                }
                                IFigure p = (IFigure) evt.getNewValue();
                                if (p != null)
                                    p.addFigureListener(fl);
                            }
                        });

        // Add listener for close button being pressed.
        poolFigure.getHeaderFigure()
                .addPropertyChangeListener(HeaderFigure.CLOSED_PROPERTY_CHANGE,
                        new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                PoolAdapter pa = getPool();

                                Command cmd =
                                        pa.getSetIsClosedCommand(getEditingDomain(),
                                                ((Boolean) evt.getNewValue())
                                                        .booleanValue());
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);

                            }
                        });

        return poolFigure;
    }

    /**
     * @see com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#deactivate()
     * 
     */
    @Override
    public void deactivate() {
        if (poolFigure != null) {
            poolFigure.dispose();
        }
        super.deactivate();
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy(EditPolicy.LAYOUT_ROLE, new PoolLayoutEditPolicy(
                getAdapterFactory(), getEditingDomain()));

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());

        // Handle message flow connections.
        AllConnectionTypesNodeEditPolicy nodePolicy =
                new AllConnectionTypesNodeEditPolicy(getEditingDomain());
        nodePolicy.removeValidConnectionAdapterType(SequenceFlowAdapter.class);
        nodePolicy.removeValidConnectionAdapterType(AssociationAdapter.class);

        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, nodePolicy);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#addChildVisual(org
     * .eclipse.gef.EditPart, int)
     */
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);

        // Add diagram refresh listener so that we can propagate lane diagram
        // refreshes.
        if (childEditPart instanceof LaneEditPart) {
            ((LaneEditPart) childEditPart)
                    .addDiagramRefreshListener(laneDiagramRefreshListener);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#removeChildVisual
     * (org.eclipse.gef.EditPart)
     */
    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        // remove diagram refresh listener from lanes.
        if (childEditPart instanceof LaneEditPart) {
            ((LaneEditPart) childEditPart)
                    .removeDiagramRefreshListener(laneDiagramRefreshListener);
        }

        super.removeChildVisual(childEditPart);
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    private PoolAdapter getPool() {
        return (PoolAdapter) getModelAdapter();
    }

    @Override
    public void refresh() {
        super.refresh();
        PoolFigure f = (PoolFigure) getFigure();
        IFigure p = f.getParent();
        if (p != null) {
            f.setText(getPool().getName());
            LayoutManager layout = p.getLayoutManager();
            Rectangle r = new Rectangle(new Point(), f.getPreferredSize());
            if (!r.equals(layout.getConstraint(f))) {
                layout.setConstraint(f, r);
                layout.layout(p);
            }
        }

        if (firstRefresh) {
            firstRefresh = false;

            previousClosedState = getPool().isClosed();
        }

        if (previousClosedState != getPool().isClosed()) {
            previousClosedState = getPool().isClosed();

            firePoolCloseChange();
        }
    }

    @Override
    protected List getModelChildren() {
        PoolAdapter pool = (PoolAdapter) getModelAdapter();
        return pool.getLanes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
     * ()
     */
    @Override
    protected List getModelSourceConnections() {
        return getPool().getSourceMessageFlows();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
     * ()
     */
    @Override
    protected List getModelTargetConnections() {
        return getPool().getTargetMessageFlows();
    }

    @Override
    public IFigure getContentPane() {
        return ((PoolFigure) getFigure()).getContentPane();
    }

    @Override
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new CellEditorLocator() {
            @Override
            public void relocate(CellEditor celleditor) {
                Rectangle b = getFigure().getBounds().getCopy();
                b.x += 5;
                b.y = b.y + b.height / 2;
                b.height = 10;
                b.width = 10;

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

    /**
     * Set child lane's to alternate lane colours.
     */
    @Override
    protected void refreshChildren() {
        super.refreshChildren();

    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        IFigure fig = getFigure();

        // if (DiagramViewType.PAGE_FLOW.equals(getDiagramViewType())) {
        // if (fig.getBorder() != null) {
        // fig.setBorder(null);
        // }
        // } else

        // Don't display Pool border for Task Library alternate / page flow view
        // (which doesn'ty have visible pool)
        if (!DiagramViewType.TASK_LIBRARY_ALTERNATE
                .equals(getDiagramViewType())
        // POOLLESS PAGEFLOW
                && !DiagramViewType.NO_POOLS.equals(getDiagramViewType())
        // POOLLESS PAGEFLOW
        ) {
            if (fig.getBorder() == null) {
                fig.setBorder(new LineBorder());
            }
        } else {
            if (fig.getBorder() != null) {
                fig.setBorder(null);
            }
        }

        Display display = Display.getCurrent();
        if (display == null)
            SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);

        PoolAdapter pa = getPool();
        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(this);

        Color fillColor =
                colors.getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                        getFillColorIDForPartType()).getColor();

        Color lineColor =
                colors.getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                        getLineColorIDForPartType()).getColor();

        fig.setBackgroundColor(fillColor);
        fig.setForegroundColor(lineColor);

        ((PoolFigure) getFigure()).setClosed(getPool().isClosed());

    }

    // Our getTargetEditPart() for pool will ask the lanes whether the request
    // has anything to do with them and give them the chance to say that drop
    // object to the right of their content figure is ok.
    @Override
    public EditPart getTargetEditPart(Request request) {

        EditPart retEP = null;

        Boolean childInvalidated =
                (Boolean) request.getExtendedData()
                        .get(XPD_CHILD_INVALIDATED_REQUEST);
        if (childInvalidated != null && childInvalidated.booleanValue() == true) {
            // Another EditPart has already checked the validity of move/add so
            // we don't want to re-check for ourselves.
            //
            // i.e. child has said 'nominally this request would be allowed but
            // because of some side effect then it is not" therefore we DON'T
            // want the parent edit part to say " but it's ok for me!
            return null;
        }

        // POOLLESS PAGEFLOW
        if (DiagramViewType.NO_POOLS.equals(getDiagramViewType())) {
            if (REQ_SELECTION.equals(request.getType())) {
                // Disallow pool selection in PageFlow mode.
                //
                // So redirect requests for selection to the process edit part.
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

        if (DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(getDiagramViewType())) {
            if (REQ_SELECTION.equals(request.getType())) {
                // Disallow pool selection in PageFlow mode.
                return null;
            }
        }

        if (REQ_ADD.equals(request.getType())
                || REQ_CREATE.equals(request.getType())) {
            // Disallow move into / create object in closed pool.
            if (getPool().isClosed()) {
                return null;
            }
        }

        if (REQ_ADD.equals(request.getType())
                || REQ_MOVE.equals(request.getType())
                || REQ_CREATE.equals(request.getType())
                || REQ_CLONE.equals(request.getType())) {

            if (request instanceof DropRequest) {
                DropRequest dropReq = (DropRequest) request;

                boolean passToProcess = false;
                int haveLaneCount = 0;
                boolean haveNonLane = false;

                if (request instanceof ChangeBoundsRequest) {
                    ChangeBoundsRequest cbr = (ChangeBoundsRequest) request;

                    List eps = cbr.getEditParts();
                    if (eps != null) {
                        for (Iterator iter = eps.iterator(); iter.hasNext();) {
                            EditPart ep = (EditPart) iter.next();

                            if (ep instanceof PoolEditPart) {
                                // Pass move of pool edit part on thru so
                                // process edit
                                // part can deal with it.
                                passToProcess = true;
                            }

                            if (ep instanceof LaneEditPart) {
                                haveLaneCount++;
                            } else {
                                haveNonLane = true;
                            }
                        }
                    }
                }

                if (haveLaneCount > 0) {
                    WidgetRootEditPart root = (WidgetRootEditPart) getRoot();

                    if (haveNonLane) {
                        // Disallow lane + non lane moves/adds.
                        return null;
                    } else if (haveLaneCount > 1) {
                        // Disallow move of multiple lanes (just too many issues
                        // with insert position to get it to work nicely.
                        // If not valid, set up an Error Tool Tip.
                        root.setErrorTipHelperText(getFigure(),
                                Messages.PoolEditPart_NoMultilaneMove_longdesc);

                        // And say that we're not a valid target for this
                        // request.
                        return null;

                    } else {
                        root.clearErrorTipHelper();
                    }

                    // We may have been forwarded the request by diagram
                    // (process) edit policy if mouse is outside of pool.
                    // In whioch case we want our layout policy to handle it...

                    Point loc = dropReq.getLocation().getCopy();
                    translateToModel(loc);

                    this.getParentProcess().getFigure()
                            .translateToRelative(loc);

                    int startY = Integer.MIN_VALUE;
                    int endY = Integer.MAX_VALUE;

                    List pools = getParent().getChildren();

                    // If we are not the end pool then end Y is start of next
                    // pool
                    int idx = pools.indexOf(this);
                    if (idx != -1) {
                        if (idx != 0) {
                            // If we are not the first pool then start Y is our
                            // y pos.
                            startY = getFigure().getBounds().y;
                        }

                        if (idx < (pools.size() - 1)) {
                            endY =
                                    ((GraphicalEditPart) pools.get(idx + 1))
                                            .getFigure().getBounds().y;
                        }

                        if (loc.y >= startY && loc.y <= endY) {
                            retEP = super.getTargetEditPart(request);

                        }
                    }

                } else if (!passToProcess) {
                    // Check if the request is within our bounds,
                    // if it is then just call the base stuff (for things like
                    // add
                    // swimlane etc
                    Point location = dropReq.getLocation().getCopy();
                    PoolFigure pool = (PoolFigure) getFigure();

                    pool.translateToRelative(location);

                    Rectangle pBnds = pool.getBounds();

                    if (location.y >= pBnds.y
                            && location.y <= (pBnds.y + pBnds.height)
                            && location.x >= pBnds.x) {
                        // Check if it's valid for any lane...
                        List lanes = getChildren();
                        for (Iterator iter = lanes.iterator(); retEP == null
                                && iter.hasNext();) {
                            LaneEditPart lane = (LaneEditPart) iter.next();

                            if (lane != null) {
                                retEP = lane.getTargetEditPart(request);
                            }
                        }

                        // In pool but not lane.
                        if (retEP == null) {
                            if (request instanceof CreateRequest) {
                                if (((CreateRequest) request)
                                        .getNewObjectType() == PoolAdapter.class) {
                                    retEP = null; // Allow the process edit
                                    // part
                                    // to take copntrol of pool
                                    // creation.
                                } else {
                                    retEP = super.getTargetEditPart(request);
                                }
                            } else {
                                retEP = super.getTargetEditPart(request);
                            }
                        }
                    } else {
                        // Sent a request outside of ourselves forget it unless
                        // it's create a lane.
                        retEP = null;

                        if (request instanceof CreateRequest) {
                            if (((CreateRequest) request).getNewObjectType() == LaneAdapter.class) {
                                if (PoolLayoutEditPolicy
                                        .findInsertLanePosition((CreateRequest) request,
                                                (GraphicalEditPart) this) >= 0) {
                                    retEP = this;
                                }
                            }
                        }
                    }
                }
            }

        } else if (REQ_CONNECTION_START.equals(request.getType())
                || REQ_CONNECTION_END.equals(request.getType())) {
            // Disable connection start / end unless within small margin of
            // boundary.
            CreateConnectionRequest creq = (CreateConnectionRequest) request;

            if (creq.getNewObjectType() == MessageFlowAdapter.class) {

                Point loc = creq.getLocation().getCopy();
                getFigure().translateToRelative(loc);

                if (isValidConnectionTargetPoint(loc)) {
                    // Ok it's within border limits so pass it on.
                    retEP = super.getTargetEditPart(request);
                }
            }

        } else if (REQ_RECONNECT_TARGET.equals(request.getType())
                || REQ_RECONNECT_SOURCE.equals(request.getType())) {
            // Disable connection start / end unless within small margin of
            // boundary.
            ReconnectRequest creq = (ReconnectRequest) request;

            if (creq.getConnectionEditPart() instanceof MessageFlowEditPart) {
                Point loc = creq.getLocation().getCopy();
                getFigure().translateToRelative(loc);

                if (isValidConnectionTargetPoint(loc)) {
                    // Ok it's within border limits so pass it on.
                    retEP = super.getTargetEditPart(request);
                }
            }

        } else {
            retEP = super.getTargetEditPart(request);

        }

        return retEP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        return ProcessWidgetColors.POOL_HEADER_FILL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.POOL_HEADER_LINE;
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

    private void firePoolCloseChange() {
        PoolAdapter pa = getPool();

        boolean isClosed = pa.isClosed();

        PropertyChangeEvent evt =
                new PropertyChangeEvent(this, PROPERTY_POOL_CLOSESTATE,
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
        return getPool().isClosed();
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info = new HoverInfo(Messages.PoolEditPart_Hover_tooltip);

        PoolAdapter pa = getPool();

        String name = pa.getName();

        info.addProperty(Messages.PoolEditPart_HoverName_label,
                name != null ? name : Messages.PoolEditPart_HoverNotSet_label);

        List lanes = pa.getLanes();

        if (lanes != null && lanes.size() > 0) {

            for (Iterator iter = lanes.iterator(); iter.hasNext();) {
                Object lane = iter.next();

                LaneAdapter la =
                        (LaneAdapter) getAdapterFactory().adapt(lane,
                                ProcessWidgetConstants.ADAPTER_TYPE);
                if (la != null) {
                    String laneName = la.getName();

                    info.addProperty(lane == lanes.get(0) ? Messages.PoolEditPart_HoverLanes_label
                            : "", //$NON-NLS-1$
                            laneName != null ? laneName
                                    : Messages.PoolEditPart_HoverNotSet_label);
                }
            }
        }

        info.setDocumentationURL(pa.getDocumentationURL(), this);

        return info;
    }

    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double startPos =
                    ((BaseConnectionEditPart) connection)
                            .getStartAnchorPosition();

            if (startPos != null) {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                (int) startPos.doubleValue(),
                                PositionConstants.VERTICAL);
            } else {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                PositionConstants.VERTICAL);
            }
        }

        return anchor;
    }

    @Override
    protected ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double endPos =
                    ((BaseConnectionEditPart) connection)
                            .getEndAnchorPosition();

            if (endPos != null) {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                (int) endPos.doubleValue(),
                                PositionConstants.VERTICAL);
            } else {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                PositionConstants.VERTICAL);
            }
        }

        return anchor;
    }

    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        ConnectionAnchor anchor = null;

        if (request instanceof DropRequest) {
            Point location = null;

            // On creation use the ORIGINAL click-point to set the source anchor
            // rather than the
            if (request instanceof CreateConnectionRequest) {
                location =
                        (Point) request
                                .getExtendedData()
                                .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
            }

            if (location == null) {
                location = ((DropRequest) request).getLocation();
            }

            Point rel = location.getCopy();
            getFigure().translateToRelative(rel);

            // When reconnecting connections, if other end of
            // connection is to another pool that is using an UN-fixed position
            // anchor then always use a fixed position anchor at this end.
            boolean alwaysFixed = false;

            if (request instanceof ReconnectRequest) {
                EditPart ep =
                        ((ReconnectRequest) request).getConnectionEditPart()
                                .getTarget();

                if (ep instanceof PoolEditPart) {
                    ConnectionAnchor tgtAnch =
                            ((PoolEditPart) ep)
                                    .getTargetConnectionAnchor(((ReconnectRequest) request)
                                            .getConnectionEditPart());

                    if (tgtAnch instanceof FixedOrientationAnchor) {
                        if (((FixedOrientationAnchor) tgtAnch)
                                .getOppositeAxisOffset() == FixedOrientationAnchor.DEFAULT_OPPOSITEAXIS) {
                            alwaysFixed = true;
                        }
                    }
                }
            }

            // If location is within a small margin of actual boundary line then
            // used fixed location anchor else use default (i.e. pos is
            // according to opposite end of conenction)
            PoolFigure fig = (PoolFigure) getFigure();

            if (alwaysFixed || fig.borderLineContainsPoint(rel)) {

                anchor =
                        new FixedOrientationAnchor(getFigure(), rel.x,
                                PositionConstants.VERTICAL);

                request.getExtendedData().put(POOL_SOURCEANCHOR_FIXED, true);

            } else {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                PositionConstants.VERTICAL);
                request.getExtendedData().put(POOL_SOURCEANCHOR_FIXED, false);
            }

        }
        return anchor;
    }

    @Override
    protected ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        ConnectionAnchor anchor = null;

        if (request instanceof DropRequest) {
            Point rel = ((DropRequest) request).getLocation().getCopy();

            getFigure().translateToRelative(rel);

            // When creating or reconnecting connections, if other end of
            // connection is to another pool that is using an UN-fixed position
            // anchor then always use a fixed position anchor at this end.
            Boolean srcFixed = null;

            if (request instanceof CreateConnectionRequest) {
                srcFixed =
                        (Boolean) request.getExtendedData()
                                .get(POOL_SOURCEANCHOR_FIXED);

            } else if (request instanceof ReconnectRequest) {
                EditPart ep =
                        ((ReconnectRequest) request).getConnectionEditPart()
                                .getSource();
                if (ep instanceof PoolEditPart) {
                    ConnectionAnchor srcAnch =
                            ((PoolEditPart) ep)
                                    .getSourceConnectionAnchor(((ReconnectRequest) request)
                                            .getConnectionEditPart());

                    if (srcAnch instanceof FixedOrientationAnchor) {
                        if (((FixedOrientationAnchor) srcAnch)
                                .getOppositeAxisOffset() == FixedOrientationAnchor.DEFAULT_OPPOSITEAXIS) {
                            srcFixed = new Boolean(false);
                        } else {
                            srcFixed = new Boolean(true);
                        }
                    }
                }
            }

            boolean alwaysFixed = false;
            if (srcFixed != null) {
                alwaysFixed = !srcFixed.booleanValue();
            }

            // If location is within a small margin of actual boundary line then
            // used fixed location anchor else use default (i.e. pos is
            // according to opposite end of conenction)
            Rectangle bnds = getFigure().getBounds().getCopy();
            Rectangle selBnds =
                    ((PoolFigure) getFigure()).getSelectionBounds().getCopy();

            if (alwaysFixed || (rel.y >= selBnds.y && rel.y <= bnds.y)
                    || (rel.y >= bnds.bottom() && rel.y <= selBnds.bottom())) {
                anchor =
                        new FixedOrientationAnchor(getFigure(), rel.x,
                                PositionConstants.VERTICAL);

                request.getExtendedData().put(POOL_TARGETANCHOR_FIXED, true);

            } else {
                anchor =
                        new FixedOrientationAnchor(getFigure(),
                                PositionConstants.VERTICAL);

                request.getExtendedData().put(POOL_TARGETANCHOR_FIXED, false);
            }

        }
        return anchor;
    }

    /**
     * Return whether the given point (relative to pool figure) is a valid
     * connection startr/end point position.
     * 
     * This will be the case if the point is within the selection bounds and...
     * - Is a Closed or Empty Pool - Or is a max of POOL_BORDER_HIT_TOLERANCE
     * pixels within the selection bounds.
     * 
     * @param pt
     * @return
     */
    private boolean isValidConnectionTargetPoint(Point pt) {
        Rectangle outer =
                ((PoolFigure) getFigure()).getSelectionBounds().getCopy();
        if (outer.contains(pt)) {
            if (isClosed() || getChildren().size() == 0) {
                return true;
            }

            Rectangle bnds = ((PoolFigure) getFigure()).getBounds().getCopy();

            if ((pt.y <= (bnds.y + PoolFigure.POOL_BORDER_HIT_TOLERANCE))
                    || (pt.y >= (bnds.bottom() - PoolFigure.POOL_BORDER_HIT_TOLERANCE))) {
                return true;
            }
        }

        return false;
    }

}
