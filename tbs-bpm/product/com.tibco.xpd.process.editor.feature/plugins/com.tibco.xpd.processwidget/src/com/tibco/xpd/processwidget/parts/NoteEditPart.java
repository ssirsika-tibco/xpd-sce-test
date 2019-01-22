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

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.figures.NoteFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.connectgadget.ClickOrDragCreateConnectionPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * @author wzurek
 */
public class NoteEditPart extends BaseConnectableNodeEditPart implements NodeEditPart {

    private ConnectionAnchor associationAnchor;

    public class NoteDirectEditPolicy extends DirectEditPolicy {

        /*
         * @see
         * org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand
         * (org.eclipse.gef.requests.DirectEditRequest)
         */
        protected Command getDirectEditCommand(DirectEditRequest request) {
            NoteAdapter adp =
                    (NoteAdapter) ((NoteEditPart) getHost()).getModelAdapter();

            EditingDomain editingDomain =
                    AdapterFactoryEditingDomain.getEditingDomainFor(adp
                            .getTarget());
            String val = (String) request.getCellEditor().getValue();
            return new EMFCommandWrapper(editingDomain, adp
                    .getSetTextCommand(editingDomain, val));
        }

        /*
         * @see
         * org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue
         * (org.eclipse.gef.requests.DirectEditRequest)
         */
        protected void showCurrentEditValue(DirectEditRequest request) {
            // do nothing
        }
    }

    public NoteEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    protected IFigure createFigure() {
        return new NoteFigure();
    }

    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NoteDirectEditPolicy());

        installEditPolicy("Animation", //$NON-NLS-1$
                new ConnectionsAnimatorEditPolicy(getProcessWidget()
                        .getEditPolicyEnablementProvider()));

        return;
     }

    private NoteAdapter getNoteAdapter() {
        return (NoteAdapter) getModelAdapter();
    }

    protected List getModelSourceConnections() {
        return getNoteAdapter().getSourceAssociations();
    }

    protected List getModelTargetConnections() {
        return getNoteAdapter().getTargetAssociations();
    }

    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refreshSourceConnections();
        refreshTargetConnections();
        refreshVisuals();
    }

    protected void refreshVisuals() {
        NoteAdapter na = (NoteAdapter) getModelAdapter();

        NoteFigure f = (NoteFigure) getFigure();
        f.setText(na.getText());

        Dimension sz = na.getSize();
        if (sz != null) {
            f.setPreferredSize(sz);
        }

        Color lineColor =
                ProcessWidgetColors
                        .getInstance(this)
                        .getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                                getLineColorIDForPartType()).getColor();

        f.setForegroundColor(lineColor);

        WidgetRGB wRGB =
                ProcessWidgetColors
                        .getInstance(this)
                        .getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                                getFillColorIDForPartType(),
                                true);
        if (wRGB != null) {
            Color fillColor = wRGB.getColor();

            f.setBackgroundColor(fillColor);
        } else {
            f.setBackgroundColor(null);
        }

        IFigure p = f.getParent();
        if (p != null) {
            Dimension size = f.getMinimumSize();
            Point loc = na.getLocation().getCopy();
            loc.y -= size.height / 2;

            LayoutManager layout = p.getLayoutManager();
            Rectangle r = new Rectangle(loc, f.getMinimumSize());
            if (!r.equals(layout.getConstraint(f))) {
                layout.setConstraint(f, r);
                layout.layout(p);
                p.revalidate();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#translateToModel
     * (org.eclipse.draw2d.geometry.Translatable)
     */
    public Translatable translateToModel(Translatable translatable) {
        Dimension size = getFigure().getMinimumSize();

        translatable.performTranslate(0, size.height / 2);
        return translatable;
    }

    public ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        ConnectionAnchor anchor = null;
        if (connection instanceof BaseConnectionEditPart) {
            Double pos =
                    ((BaseConnectionEditPart) connection)
                            .getStartAnchorPosition();

            if (pos != null) {
                anchor = new LineAnchor(getFigure(), pos);
            }
        }

        if (anchor == null) {
            anchor = getAnchor();
        }

        return anchor;
    }

    public ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        ConnectionAnchor anchor = null;
        if (connection instanceof BaseConnectionEditPart) {
            Double pos =
                    ((BaseConnectionEditPart) connection)
                            .getEndAnchorPosition();

            if (pos != null) {
                anchor = new LineAnchor(getFigure(), pos);
            }
        }

        if (anchor == null) {
            anchor = getAnchor();
        }

        return anchor;
    }

    public ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, true);
    }

    public ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, false);
    }

    /**
     * @return lazy created anchor from request
     */
    private ConnectionAnchor getAnchorFromRequest(Request request,
            boolean isSource) {
        ConnectionAnchor anchor = null;

        if (request instanceof DropRequest) {

            // If it's on border of task use a line anchor
            // for specific attachment position to border
            NoteFigure fig = (NoteFigure) getFigure();

            Point location = null;

            if (isSource && request instanceof CreateConnectionRequest) {
                location =
                        (Point) request
                                .getExtendedData()
                                .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
            }

            if (location == null) {
                location = ((DropRequest) request).getLocation();
            }

            // Check wether point is on text border.
            PointList pts = fig.getLineAnchorLinePoints();

            Point closest =
                    XPDLineUtilities.getPolylinePointClosestToPoint(pts,
                            location);

            double dist = XPDLineUtilities.getLineLength(location, closest);

            // But not too far within text
            PointList scaledPts =
                    XPDLineUtilities.scaleCentredPolyline(pts, 0.75);

            if (dist < ProcessWidgetConstants.BORDER_HIT_TOLERANCE
                    && !XPDLineUtilities.polygonContainsPoint(scaledPts,
                            location.x,
                            location.y)) {
                anchor = new LineAnchor(fig, location.getCopy());
            }

            if (anchor == null) {
                // If all else fails then get the default anchor.
                anchor = getAnchor();

            }
        }
        return anchor;
    }

    private ConnectionAnchor getAnchor() {
        if (associationAnchor == null) {
            NoteFigure fig = (NoteFigure) getFigure();
            associationAnchor = new ChopShapeAnchor(fig);
        }
        return associationAnchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    public String getFillColorIDForPartType() {
        return ProcessWidgetColors.NOTE_FILL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.NOTE_LINE;
    }

    /**
     * Override default bounds based locator with locator thats take care of
     * bounds of the text instead of the whole activity
     */
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new AutoSizingDirectEditLocator(false, true) {
            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.processwidget.parts.AutoSizingDirectEditLocator
             * #getTextBoundsLocation()
             */
            @Override
            public Rectangle getTextBoundsLocation() {
                IFigure tfig = ((NoteFigure) getFigure()).getTextFigure();
                Rectangle textBnds = tfig.getBounds().getCopy();

                tfig.translateToAbsolute(textBnds);

                return textBnds;
            }

            @Override
            public int getDesiredWidth() {
                IFigure tfig = ((NoteFigure) getFigure()).getTextFigure();

                Dimension figsize = tfig.getSize();

                Dimension size = getFigure().getPreferredSize();

                return Math.max(size.width, figsize.width);
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getDirectEditStyle
     * ()
     */
    @Override
    protected int getDirectEditStyle() {
        return SWT.MULTI | SWT.LEFT | SWT.WRAP;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getInitialDirectEditText()
     */
    @Override
    protected String getInitialDirectEditText() {
        return getNoteAdapter().getText();
    }

}
