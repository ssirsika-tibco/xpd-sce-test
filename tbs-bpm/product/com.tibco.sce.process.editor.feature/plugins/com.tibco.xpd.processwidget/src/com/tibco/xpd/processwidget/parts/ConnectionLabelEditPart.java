/**
 * Copyright 2005 TIBCO Software Inc.
 */
package com.tibco.xpd.processwidget.parts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.ConfigurableDirectEditManager;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;
import com.tibco.xpd.processwidget.viewer.WidgetTextActionHandlers;

/**
 * Edit part that represents label of the connection. It works on artificial
 * model that holds reference to parents EditPart
 * 
 * @author wzurek
 */
public class ConnectionLabelEditPart extends AbstractGraphicalEditPart
        implements Locator {

    DragTracker dragTracker = null;

    private DirectEditManager manager;


    FlowPage    page;
    TextFlow    text;
    
    /**
     * 
     */
    public ConnectionLabelEditPart() {
        super();

        // Use a slightly modified drag tracker that always returns true
        // from isMove() (label is never contained within it's parent
        // connection, so you get a REQ_ORPHAN instead).
        dragTracker = new DragEditPartsTracker(this) {
            protected boolean isMove() {
                return true;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    protected IFigure createFigure() {
        page = new FlowPage();
        BlockFlow bfig = new BlockFlow();
        text = new TextFlow();
        
        bfig.add(text);
        bfig.setHorizontalAligment(PositionConstants.CENTER);

        
        page.add(bfig);
        return page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
                new ConnectionLabelNodeEditPolicy());
        
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new ConnectionLabelDirectEditPolicy());

    }

    protected void refreshVisuals() {
        BaseConnectionEditPart connEP = getConnectionEditPart();
        
        text.setText(connEP.getLabel());
        
        Dimension prefSize;
        
        Dimension setSize = connEP.getModelConnectionAdapter().getLabelSize(); 
        
        if (setSize != null) {
            prefSize = page.getPreferredSize(setSize.width, setSize.height);
        } else {
            prefSize = page.getPreferredSize();
        }
        
        page.setSize(prefSize);

        IFigure p = page.getParent();
        if (p != null) {
            // Dimension size = aa.getSize().getCopy();
            // Point loc = aa.getLocation().getCopy();

            LayoutManager layout = p.getLayoutManager();
            layout.setConstraint(page, this);
            layout.layout(p);
            p.invalidate();
        }
    }

    /**
     * @return
     */
    public BaseConnectionEditPart getConnectionEditPart() {
        return ((FlowLabelModel) getModel()).getConnectionEditPart();
    }

    public void relocate(IFigure target) {
        BaseConnectionEditPart conn = getConnectionEditPart();

        double  percentAnchor = 50.0;
        int     xOff = 0;
        int     yOff = 0;

        ConnectionLabelPosition labelPos = conn.getModelConnectionAdapter().getLabelPosition();
        
        if (labelPos != null) {
            percentAnchor = labelPos.getPercentAnchorOnConnection();
            xOff = labelPos.getXOffsetFromAnchor();
            yOff = labelPos.getYOffsetFromAnchor();
        }

        // Calculate position along line represented by portion of line.
        PointList pts = conn.getConnectionFigure().getPoints();
        
        Point anchorPos = XPDLineUtilities.getLinePointFromPortion(pts, percentAnchor);
        
        // get where centre of text should be. 
        Point loc = new Point(anchorPos.x + xOff, anchorPos.y + yOff);
        
        Dimension size = target.getSize();
        
        loc.x -= size.width / 2;
        
        // If no user specified anchor position then adjust up 
        // so that if line is horizontal, it doesn't go straight thru text.
        if (labelPos == null) {
            loc.y -= size.height;
        }
        else {
            loc.y -= size.height / 2;
        }

        target.setLocation(loc);
    }

    public Object getAdapter(Class key) {
        if (EObject.class.isAssignableFrom(key)) {
            return ((FlowLabelModel) getModel()).getConnectionEditPart()
                    .getAdapter(key);
        } else if (key == ILabelProvider.class) {
            return getViewer().getProperty(
                    ProcessWidgetConstants.PROP_LABEL_PROVIDER);
        }
        return super.getAdapter(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getDragTracker(org.eclipse.gef.Request)
     */
    public DragTracker getDragTracker(Request request) {
        return dragTracker;
    }

    
    public void performRequest(Request request) {
        if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request
                .getType() == RequestConstants.REQ_OPEN)
                && getEditPolicy(EditPolicy.DIRECT_EDIT_ROLE) != null) {
            performDirectEdit();
        } else {
            super.performRequest(request);
        }
    }
    
    /**
     * Return the required Text control SWT.xxx styles for the direct edit text
     * control.
     * 
     * @return
     */
    protected int getDirectEditStyle() {
        Dimension setSize = getConnectionEditPart().getModelConnectionAdapter().getLabelSize();
        
        if (setSize != null) {
            // If we have a user set size then wrap to user set size.
            return SWT.WRAP | SWT.CENTER;
        }
        
        return SWT.CENTER;
        
    }

    protected CellEditorLocator getDirectEditEditorLocator() {
        boolean autoWidth = true;

        Dimension setSize = getConnectionEditPart().getModelConnectionAdapter().getLabelSize();
        
        if (setSize != null) {
            autoWidth = false;
        }

        return new AutoSizingDirectEditLocator(autoWidth, true) {

            public Rectangle getTextBoundsLocation() {
                Rectangle textBnds = text.getBounds().getCopy();

                text.translateToAbsolute(textBnds);

                return textBnds;
            }

            public int getDesiredWidth() {
                
                Dimension figsize = text.getPreferredSize();
                
                Dimension setSize = getConnectionEditPart().getModelConnectionAdapter().getLabelSize();
                
                if (setSize != null) {
                    return Math.max(30, Math.max(setSize.width, figsize.width));
                }
                
                return Math.max(30, figsize.width);
            }
        };
    }


    public void performDirectEdit() {
        if (manager == null) {
            CellEditorLocator locator = getDirectEditEditorLocator();

            manager = new ConfigurableDirectEditManager(this, locator,
                    getDirectEditStyle()) {

                protected void initCellEditor() {
                    String name = getInitialDirectEditText();
                    if (name == null) {
                        name = ""; //$NON-NLS-1$
                    }
                    getCellEditor().setValue(name);
                    Text textControl = (Text) getCellEditor().getControl();
                    textControl.selectAll();

                    EditPartViewer viewer = getViewer();
                    WidgetTextActionHandlers handlers = ((WidgetTextActionHandlers) viewer
                            .getProperty(ProcessWidgetConstants.PROP_TEXT_HANDLERS));
                    handlers.addText(textControl);
                }

                /**
                 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
                 */
                protected void bringDown() {
                    manager = null;
                    super.bringDown();
                    refreshVisuals();
                }
                
            };
        }
        
        manager.show();

    }

    /**
     * return initial falue for direct edit
     */
    protected String getInitialDirectEditText() {
        BaseConnectionEditPart connEP = getConnectionEditPart();
        
        return connEP.getModelConnectionAdapter().getName();
    }

    /**
     * ConnectionLabelDirectEditPolicy
     *
     * Handles direct edit of the connection label.
     */
    private class ConnectionLabelDirectEditPolicy extends DirectEditPolicy {

        /*
         * @see org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
         */
        protected Command getDirectEditCommand(DirectEditRequest request) {
            BaseConnectionAdapter adp = getConnectionEditPart().getModelConnectionAdapter();

            EditingDomain editingDomain = AdapterFactoryEditingDomain
                    .getEditingDomainFor(adp.getTarget());
            
            String val = (String) request.getCellEditor().getValue();
            
            return new EMFCommandWrapper(editingDomain, adp.getSetNameCommand(
                    editingDomain, val));
        }

        /*
         * @see org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
         */
        protected void showCurrentEditValue(DirectEditRequest request) {
            // do nothing
        }
    }


    
    /**
     * ConnectionLabelNodeEditPolicy
     *
     * Handles showing link back to connection line anchor position on move of label.
     */
    private class ConnectionLabelNodeEditPolicy extends GraphicalEditPolicy {
        private Connection labelAnchorFeedback = null;
        private LineAnchor connectionAnchor = null;
        private AbstractConnectionAnchor labelAnchor = null; 
        
        /* (non-Javadoc)
         * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#showSourceFeedback(org.eclipse.gef.Request)
         */
        public void showTargetFeedback(Request request) {
            super.showTargetFeedback(request);
            
            if (REQ_SELECTION.equals(request.getType())) {
                if (getHost() instanceof ConnectionLabelEditPart) {
                    ConnectionLabelEditPart labelEP = (ConnectionLabelEditPart)getHost();

                    IFigure moveFeedBack = labelEP.getFigure();
                    
                    if (labelAnchorFeedback == null) {
                        labelAnchorFeedback = new PolylineConnection();
                        labelAnchorFeedback.setForegroundColor(ColorConstants.lightGray);
                        labelAnchorFeedback.setVisible(false);
                        
                        connectionAnchor = new LineAnchor (labelEP.getConnectionEditPart().getFigure());
    
                        labelAnchor = new AbstractConnectionAnchor (moveFeedBack) {
                            public Point getLocation(Point reference) {
                                return getReferencePoint();
                            }
                        };
    
                        labelAnchorFeedback.setSourceAnchor(connectionAnchor);
                        labelAnchorFeedback.setTargetAnchor(labelAnchor);
                        
                        getFeedbackLayer().add(labelAnchorFeedback);
                    }

                    labelAnchorFeedback.setVisible(true);

                }
            }
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
         */
        public void eraseTargetFeedback(Request request) {
            if (REQ_SELECTION.equals(request.getType())) {
                if (labelAnchorFeedback != null) {
                    getFeedbackLayer().remove(labelAnchorFeedback);
                    labelAnchorFeedback = null;
                    labelAnchor = null;
                    connectionAnchor = null;
                }
            }
            
            super.eraseTargetFeedback(request);
        }
        
    }
    
}
