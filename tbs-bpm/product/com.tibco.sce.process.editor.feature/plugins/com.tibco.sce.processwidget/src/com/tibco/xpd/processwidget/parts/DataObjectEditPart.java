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
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor.LineAnchorLinesProvider;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;

/**
 * Representation of BPMN's Data Object
 * 
 * @author wzurek
 */
public class DataObjectEditPart extends BaseConnectableNodeEditPart implements
        HoverProvider, NodeEditPart {

    private ConnectionAnchor associationAnchor;

    private Image curExtRefImage = null;

    private String curExtRef = null;

    public DataObjectEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    protected IFigure createFigure() {
        ShapeWithDescriptionFigure fig = new ShapeWithDescriptionFigure();
        setShapePoints(fig);

        fig.setToolTip(new TooltipFigure(this));
        return fig;
    }

    /**
     * @return
     */
    private void setShapePoints(ShapeWithDescriptionFigure fig) {

        Dimension dataObjectSize;

        if (getDataObjectAdapter() != null) {
            dataObjectSize = getDataObjectAdapter().getSize().getCopy();
        } else {
            dataObjectSize =
                    new Dimension(ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                            ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE);
        }

        PointList list = getDataObjectFigurePointlist(dataObjectSize);

        fig.setReferenceShapePoints(list);

        if (curExtRefImage != null) {
            //
            // The data object has an image from an external objectr reference.
            //
            ImageData imgData = curExtRefImage.getImageData();

            // Maintain the original aspect ratio
            // base scale on the largest side.

            Dimension sz =
                    new Dimension(
                            dataObjectSize.width
                                    - (ProcessWidgetConstants.DATAOBJECT_CORNERBEND_SIZE * 2),
                            dataObjectSize.height
                                    - (ProcessWidgetConstants.DATAOBJECT_CORNERBEND_SIZE * 2));

            double imageScaleX = (double) sz.width / (double) imgData.width;
            double imageScaleY = (double) sz.height / (double) imgData.height;

            double imgScale;

            if (imageScaleY < imageScaleX) {
                imgScale = imageScaleY;
            } else {
                imgScale = imageScaleX;
            }

            if (imgScale > 1.0) {
                imgScale = 1.0;
            }

            fig.setImageScale(imgScale);

        }

        return;
    }

    /**
     * @param dataObjectSize
     * @return pointlist for the data object figure shape.
     */
    public static PointList getDataObjectFigurePointlist(
            Dimension dataObjectSize) {
        PointList list = new PointList();

        int bend = ProcessWidgetConstants.DATAOBJECT_CORNERBEND_SIZE;
        int hor = dataObjectSize.width / 2;
        int vert = dataObjectSize.height / 2;

        list.addPoint(-hor, vert);
        list.addPoint(-hor, -vert);

        list.addPoint(hor - bend, -vert);
        list.addPoint(hor, -vert + bend);

        list.addPoint(hor - bend, -vert);
        list.addPoint(hor - bend, -vert + bend);
        list.addPoint(hor, -vert + bend);
        list.addPoint(hor, vert);
        list.addPoint(-hor, vert);
        return list;
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());

        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));

        installEditPolicy("Animation", //$NON-NLS-1$ 
                new ConnectionsAnimatorEditPolicy(getProcessWidget()
                        .getEditPolicyEnablementProvider()));
        return;
    }

    @Override
    public Translatable translateToModel(Translatable t) {
        ShapeWithDescriptionFigure f = (ShapeWithDescriptionFigure) getFigure();

        if (t instanceof Rectangle) {
            // we will have been given the whole figure (including label) size
            // knock this down to just the size of the shape.
            Rectangle figB = f.getBounds();

            Rectangle figS = f.getShape().getBounds();

            PointList pts = f.getReferenceShapePoints();
            if (pts != null) {
                Rectangle pb = pts.getBounds();

                figS.width = pb.width;
                figS.height = pb.height;
            }

            Rectangle r = (Rectangle) t;

            // Convert top-left location to centre.
            r.x += r.width / 2;
            r.y += r.height / 2;

            // Calc diif between overall size and shape size
            int cx = figB.width - figS.width;
            int cy = figB.height - figS.height;

            // Remove that from new size.
            r.width -= cx;
            r.height -= cy;

            // Then allow for difference between shapoe centre and whole object
            // centre.
            r.x -= (figB.getCenter().x - figS.getCenter().x);
            r.y -= (figB.getCenter().y - figS.getCenter().y);

        } else {
            Point offset = f.getReferencePointOffset();
            t.performTranslate(offset.x, offset.y);
        }

        return t;
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info =
                new HoverInfo(Messages.DataObjectEditPart_Hover_tooltip);
        String name = getDataObjectAdapter().getName();
        info.addProperty(Messages.DataObjectEditPart_HoverName_label,
                name == null ? Messages.DataObjectEditPart_HoverNotSet_label
                        : name);

        info.setDocumentationURL(getDataObjectAdapter().getDocumentationURL(),
                this);
        return info;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchorFromEditPart(connection, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchorFromEditPart(connection, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, false);
    }

    /**
     * Get the appropriate source/target connection anchor according to the
     * given connection edit part.
     * 
     * @return
     */
    private ConnectionAnchor getAnchorFromEditPart(
            ConnectionEditPart connection, boolean isSource) {
        ConnectionAnchor anchor = null;

        IFigure fig = ((ShapeWithDescriptionFigure) getFigure()).getShape();

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double fixedPos;

            if (isSource) {
                fixedPos =
                        ((BaseConnectionEditPart) connection)
                                .getStartAnchorPosition();
            } else {
                fixedPos =
                        ((BaseConnectionEditPart) connection)
                                .getEndAnchorPosition();
            }

            if (fixedPos != null) {
                anchor = new LineAnchor(fig, fixedPos);
            }
        }

        if (anchor == null) {
            // Not a fixed line anchor location
            if (associationAnchor == null) {
                associationAnchor = new ChopShapeAnchor(fig);
            }
            anchor = associationAnchor;

        }

        return anchor;
    }

    /**
     * Get the appropriate source/target connection anchor according to the
     * given create/retarget connection request.
     * 
     * @return
     */
    private ConnectionAnchor getAnchorFromRequest(Request request,
            boolean isSource) {
        ConnectionAnchor anchor = null;

        IFigure fig = ((ShapeWithDescriptionFigure) getFigure()).getShape();

        if (request instanceof DropRequest) {

            if (fig instanceof LineAnchorLinesProvider) {

                // If it's on border of gateway use a line anchor
                // for specific attachment position to border
                Point location = null;

                if (isSource && request instanceof CreateConnectionRequest) {
                    // We may be asked for start connection anchor many times,
                    // we have to use the original location clicked NOT the
                    // current location whilst drag line is in progress.
                    location =
                            (Point) request
                                    .getExtendedData()
                                    .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
                }

                if (location == null) {
                    location = ((DropRequest) request).getLocation();
                }

                // We'll count this as a fixed anchor if the mouse is within a
                // few pixels of any line
                PointList points =
                        ((LineAnchorLinesProvider) fig)
                                .getLineAnchorLinePoints();

                Point closest =
                        XPDLineUtilities.getPolylinePointClosestToPoint(points,
                                location);

                if (XPDLineUtilities.getLineLength(closest, location) <= ProcessWidgetConstants.BORDER_HIT_TOLERANCE
                        && !((Polygon) fig).containsPoint(location)) {
                    anchor = new LineAnchor(fig, closest.getCopy());
                }
            }
        }

        if (anchor == null) {
            // Not a fixed position anchor so get the default positioning one
            // appropriate to connection type.

            // For associations use a polain chop box anchor.
            if (associationAnchor == null) {
                associationAnchor = new ChopShapeAnchor(fig);
            }

            anchor = associationAnchor;
        }

        return anchor;
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refreshSourceConnections();
        refreshTargetConnections();
        refreshVisuals();
    }

    @Override
    protected List getModelSourceConnections() {
        return getDataObjectAdapter().getSourceAssociations();
    }

    @Override
    protected List getModelTargetConnections() {
        return getDataObjectAdapter().getTargetAssociations();
    }

    @Override
    protected void refreshVisuals() {

        ShapeWithDescriptionFigure f = (ShapeWithDescriptionFigure) getFigure();
        DataObjectAdapter aa = getDataObjectAdapter();

        String state = aa.getState();
        String text = aa.getName();
        if (state != null && state.length() > 0) {
            text = text + "\n[" + state + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        f.setText(text);

        String extRef = aa.getExternalReference();

        if (extRef == null || extRef.length() == 0) {
            if (curExtRefImage != null) {
                f.setImage(null);
                curExtRefImage.dispose();
                curExtRefImage = null;
            }

        } else {
            if (curExtRefImage == null || !extRef.equals(curExtRef)) {
                if (curExtRefImage != null) {
                    f.setImage(null);
                    curExtRefImage.dispose();
                    curExtRefImage = null;
                }

                ImageData id = aa.getExternalReferenceImage();
                if (id != null) {
                    curExtRefImage = new Image(null, id);

                    f.setImage(curExtRefImage);
                }
            }
        }

        curExtRef = extRef;

        setShapePoints(f);

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(aa);

        // Get colors for object.
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(aa, getFillColorIDForPartType());
        WidgetRGB borderColor =
                colors.getGraphicalNodeColor(aa, getLineColorIDForPartType());

        f.setBackgroundColor(fillColor.getColor());
        f.setForegroundColor(borderColor.getColor());

        IFigure p = f.getParent();
        if (p != null) {
            Dimension size = f.getPreferredSize();

            Point ref = f.getReferencePointOffset();
            Point loc = aa.getLocation().getCopy();
            loc.x -= ref.x;
            loc.y -= ref.y;

            LayoutManager layout = p.getLayoutManager();
            Rectangle r = new Rectangle(loc, size);
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
     * @see com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#deactivate()
     */
    @Override
    public void deactivate() {
        if (curExtRefImage != null) {
            ShapeWithDescriptionFigure f =
                    (ShapeWithDescriptionFigure) getFigure();
            if (f != null) {
                f.setImage(null);
            }

            curExtRefImage.dispose();
            curExtRefImage = null;
        }
        super.deactivate();
    }

    private DataObjectAdapter getDataObjectAdapter() {
        return (DataObjectAdapter) getModelAdapter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        return ProcessWidgetColors.DATAOBJECT_FILL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.DATAOBJECT_LINE;
    }

    /**
     * Override default bounds based locator with locator thats take care of
     * bounds of the text instead of the whole activity
     */
    @Override
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new AutoSizingDirectEditLocator() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.processwidget.parts.AutoSizingDirectEditLocator
             * #getTextBoundsLocation()
             */
            @Override
            public Rectangle getTextBoundsLocation() {
                IFigure tfig =
                        ((ShapeWithDescriptionFigure) getFigure())
                                .getTextFigure();
                Rectangle textBnds = tfig.getBounds().getCopy();

                tfig.translateToAbsolute(textBnds);

                return textBnds;
            }

            @Override
            public int getDesiredWidth() {
                IFigure tfig =
                        ((ShapeWithDescriptionFigure) getFigure())
                                .getTextFigure();
                Dimension size = tfig.getSize();
                return size.width;
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
        return SWT.WRAP | SWT.CENTER;
    }

}
