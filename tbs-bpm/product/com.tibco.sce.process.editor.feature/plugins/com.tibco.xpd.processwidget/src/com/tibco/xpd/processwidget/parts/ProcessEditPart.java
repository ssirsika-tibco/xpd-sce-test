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

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.figures.MessageFlowFigure;
import com.tibco.xpd.processwidget.figures.PaginationLayer;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.ProcessConnectionLayer;
import com.tibco.xpd.processwidget.figures.ProcessFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.policies.DiagramLayoutPolicy;

/**
 * @author wzurek
 */
public class ProcessEditPart extends BaseGraphicalEditPart {

    private static final int MINIMUM_DIAGRAM_EXTENT_HEIGHT = 100;

    private static final int MINIMUM_DIAGRAM_EXTENT_WIDTH = 700;

    public static final int POOL_MARGIN_CX = 5;

    public static final int POOL_MARGIN_CY = 5;

    public static final int POOL_SPACING = 30;

    private List<PropertyChangeListener> diagramRefreshListeners =
            new ArrayList<PropertyChangeListener>();

    public ProcessEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    protected IFigure createFigure() {
        ProcessConnectionLayer processConnectionLayer =
                (ProcessConnectionLayer) LayerManager.Helper.find(this)
                        .getLayer(LayerConstants.CONNECTION_LAYER);

        ProcessFigure f =
                new ProcessFigure(getDiagramViewType(), isReadOnly(),
                        processConnectionLayer);

        f.addPropertyChangeListener("parent", new PropertyChangeListener() { //$NON-NLS-1$
                    FigureListener fl;

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getOldValue() != null && fl != null) {
                            IFigure op = (IFigure) evt.getNewValue();
                            op.removeFigureListener(fl);
                        }
                        IFigure p = (IFigure) evt.getNewValue();
                        if (fl == null)
                            fl = new FigureListener() {
                                @Override
                                public void figureMoved(IFigure source) {
                                    getFigure().getLayoutManager()
                                            .layout(getFigure());
                                }
                            };
                        if (p != null)
                            p.addFigureListener(fl);
                    }
                });

        f.setLayoutManager(new FreeformLayout() {
            @Override
            public void layout(IFigure parent) {

                // Don't use viewport as default minimum size of diagram
                // anymore.
                // This caused problems in printing/save as image etc
                // because the output would depend on the window size.
                Dimension minSize =
                        new Dimension(MINIMUM_DIAGRAM_EXTENT_WIDTH,
                                MINIMUM_DIAGRAM_EXTENT_HEIGHT);

                for (Iterator iter = parent.getChildren().iterator(); iter
                        .hasNext();) {
                    IFigure f = (IFigure) iter.next();
                    Dimension pSize = f.getPreferredSize();
                    minSize.width = Math.max(minSize.width, pSize.width);
                }

                int topOffset = POOL_MARGIN_CY;

                List c = parent.getChildren();
                for (int i = 0; i < c.size(); i++) {
                    IFigure f = (IFigure) c.get(i);

                    Dimension pSize = f.getPreferredSize();

                    // Add space at top if this is a closed pool (unless it's
                    // the first).
                    if (f instanceof PoolFigure) {
                        Rectangle poolBnds =
                                new Rectangle(POOL_MARGIN_CX, topOffset, Math
                                        .max(minSize.width, pSize.width),
                                        pSize.height);

                        // SID - set bounds rectangle for process to origin 5,5
                        // to create a permanent margin (instead of one
                        // controoled by the pool's reveal override).
                        f.setBounds(poolBnds);

                        topOffset += pSize.height + POOL_SPACING;
                    }
                }
            }
        });
        f.setBorder(new MarginBorder(5));
        return f;
    }

    @Override
    protected void createEditPolicies() {
        //
        // DEFINITELY DO NOT WANT TO RUN ExternalAnnotation policies for proces
        // figure. This causes all sorts of problems including infinite looping!
        //
        // super.createEditPolicies();

        installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutPolicy(
                getAdapterFactory(), getEditingDomain()));
    }

    @Override
    public void activate() {
        super.activate();

    }

    @Override
    public void deactivate() {

        super.deactivate();
    }

    /**
     * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
     * 
     */
    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        ProcessDiagramAdapter pda = ((ProcessDiagramAdapter) getModelAdapter());
        if (pda != null) {
            XPDFigureUtilities.getRootWidgetLayer(getFigure())
                    .setFlowRoutingStyle(pda.getFlowRoutingStyle());
        }
    }

    @Override
    protected void refreshChildren() {
        super.refreshChildren();

        Display display = Display.getCurrent();
        if (display == null)
            SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
        int laneCount = 0;

        IFigure parent = getFigure();
        int yoff = 0;
        for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
            GraphicalEditPart ep = (GraphicalEditPart) iter.next();
            if (ep instanceof PoolEditPart) {
                IFigure fig = ep.getFigure();

                Dimension size = fig.getPreferredSize();
                Point p = new Point(0, yoff);

                yoff += size.height;

                parent.setConstraint(fig, new Rectangle(p, size));

                // Set alternate colors for lanes
                List lanes = ep.getChildren();

                if (lanes != null) {
                    Iterator ln = lanes.iterator();

                    while (ln.hasNext()) {

                        EditPart e = (LaneEditPart) ln.next();

                        if (e instanceof LaneEditPart) {
                            LaneEditPart le = (LaneEditPart) e;

                            if ((laneCount & 1) == 1) {
                                le.setLaneAlpha(25);
                            } else {
                                le.setLaneAlpha(0);
                            }
                            laneCount++;
                        }
                    }
                }

            }
        }
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    @Override
    protected List getModelChildren() {
        ArrayList children = new ArrayList();
        ProcessDiagramAdapter pda = ((ProcessDiagramAdapter) getModelAdapter());
        children.addAll(pda.getPools());
        children.addAll(pda.getGroups());
        return children;
    }

    private void setPaginationDiagramSize(Dimension extent) {
        PaginationLayer pagLayer =
                (PaginationLayer) LayerManager.Helper
                        .find(ProcessEditPart.this)
                        .getLayer(ProcessWidgetConstants.PAGINATION_LAYER);
        if (pagLayer != null) {
            pagLayer.setDiagramSize(extent);
        }
    }

    // This FigureListener listens for pools being moved / sized
    // and tells the pagination layer to re-jig.
    private FigureListener poolFigureListener = new FigureListener() {

        @Override
        public void figureMoved(IFigure source) {
            setPaginationDiagramSize(getDiagramExtent());

            // Pool sizes automatically according to lane sizes.
            // When it does change size / location we need to refresh the
            // connection layer.
            IFigure conns = getLayer(LayerConstants.CONNECTION_LAYER);

            for (Iterator iter = conns.getChildren().iterator(); iter.hasNext();) {
                IFigure conn = (IFigure) iter.next();

                if (conn instanceof MessageFlowFigure) {
                    conn.invalidateTree();
                }
            }

            conns.revalidate();

        }
    };

    // Listener for pool firing a diagram refresh required event.
    // This will fire a process level diagram refresh event that allows other
    // edit parts to find out when ANY pool is closed without having to listen
    // to every pool.
    private PropertyChangeListener poolDiagramRefreshListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    // Propagate pool close to our listeners
                    for (PropertyChangeListener listener : diagramRefreshListeners) {
                        listener.propertyChange(evt);
                    }
                }
            };

    // Each child (pool) that gets removed gets the figure moved/sized listener
    // added to it.
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {

        if (childEditPart instanceof GroupEditPart) {
            GroupEditPart gep = (GroupEditPart) childEditPart;
            IFigure layer = getLayer(ProcessWidgetConstants.GROUP_LAYER);
            layer.add(gep.getFigure());
        } else {
            super.addChildVisual(childEditPart, index);

            if (childEditPart instanceof PoolEditPart) {
                PoolEditPart poolEP = (PoolEditPart) childEditPart;

                IFigure fig = poolEP.getFigure();

                if (fig != null) {
                    fig.addFigureListener(poolFigureListener);
                }

                // Add the pool close status listener.
                poolEP.addDiagramRefreshListener(poolDiagramRefreshListener);
            }
        }
    }

    // Each child that gets removed gets the figure moved/sized listener removed
    // from it.
    @Override
    protected void removeChildVisual(EditPart childEditPart) {

        if (childEditPart instanceof GroupEditPart) {
            GroupEditPart gep = (GroupEditPart) childEditPart;
            IFigure layer = getLayer(ProcessWidgetConstants.GROUP_LAYER);
            layer.remove(gep.getFigure());
        } else {
            if (childEditPart instanceof PoolEditPart) {
                PoolEditPart poolEP = (PoolEditPart) childEditPart;
                IFigure fig = poolEP.getFigure();

                if (fig != null) {
                    fig.removeFigureListener(poolFigureListener);
                }

                // Remove the pool close status listener.
                poolEP.removeDiagramRefreshListener(poolDiagramRefreshListener);
            }

            super.removeChildVisual(childEditPart);
        }
    }

    /**
     * Calculate the extent of the process diagram
     * 
     * @return
     */
    public Dimension getDiagramExtent() {
        Dimension diagramExtent = new Dimension(0, 0); // Make sure we cover
        // the margin.

        // Get the Pool(s);
        List pools = getChildren();
        for (Iterator iter = pools.iterator(); iter.hasNext();) {
            GraphicalEditPart pool = (GraphicalEditPart) iter.next();

            // The vertical diagramExtent of the pool is fixed (i.e. it is the
            // sum of all it's lanes.
            IFigure poolFig = pool.getFigure();

            Dimension size = poolFig.getSize();

            diagramExtent.height += size.height;
            if (size.width > diagramExtent.width) {
                diagramExtent.width = size.width;
            }
        }

        // Take account of pool spacing.
        if (pools.size() > 1) {
            diagramExtent.height += (pools.size() - 1) * POOL_SPACING;
        }

        diagramExtent.width += POOL_MARGIN_CX * 2;
        diagramExtent.height += POOL_MARGIN_CY * 2;

        return (diagramExtent);
    }

    // SID Defect:25921 - moved getTargetEditPart to DiagramLayoutPolicy

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        return null; // Not applicable for whole process.
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        return null; // Not applicable for whole process.
    }

    /**
     * Sometimes the whole diagram needs to be forced to refresh - specifically
     * on occasions where changes to particular diagram edit parts affect other
     * editparts that are not related (in the normal parent-child hierarchy).
     * 
     * For instance, this is the case when pools/lanes are closed and opened.
     * When this happens, all connections need to be refreshed to give them the
     * chance to decide whether they should show/hide or re-connect to an
     * src/target object's visible parent container.
     * 
     * On when the event is fired it carries the original firer's details and
     * details of the event that caused it.
     * 
     * @param listener
     * 
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

    public LaneEditPart getLaneFromPosition(Point location) {

        List pools = getChildren();
        if (pools != null) {
            for (Iterator iterator = pools.iterator(); iterator.hasNext();) {
                EditPart pool = (EditPart) iterator.next();

                if (pool instanceof PoolEditPart) {
                    int poolY = 0;// ((PoolEditPart)pool).getFigure().getBounds()
                    // .y;

                    List lanes = pool.getChildren();
                    for (Iterator iterator2 = lanes.iterator(); iterator2
                            .hasNext();) {
                        EditPart lane = (EditPart) iterator2.next();
                        if (lane instanceof LaneEditPart) {
                            Rectangle bnds =
                                    ((LaneEditPart) lane).getFigure()
                                            .getBounds();
                            if (location.y >= (poolY + bnds.y)
                                    && location.y <= (poolY + bnds.y + bnds.height)) {
                                return (LaneEditPart) lane;
                            }
                        }
                    }
                }

            }
        }
        return null;
    }

}
