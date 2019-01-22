/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.processwidget.parts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.GatewayActivityAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor.LineAnchorLinesProvider;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.policies.cycletypegadget.CycleGatewayTypeGadgetPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * @author wzurek
 */
public class GatewayEditPart extends BaseFlowNodeEditPart implements
        HoverProvider, NodeEditPart {

    private ConnectionAnchor associationAnchor;

    private ActivityHyperLinkListener activityHyperLinkListener =
            new ActivityHyperLinkListener(this);

    public GatewayEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.gate_sel"); //$NON-NLS-1$
    }

    public GatewayAdapter getGatewayAdapter() {
        return (GatewayAdapter) getModelAdapter();
    }

    @Override
    protected IFigure createFigure() {
        ShapeWithDescriptionFigure shape = new ShapeWithDescriptionFigure();
        // diamond shape
        // SID DI:24879 - Reduce size of gateway objects.

        PointList pointList = getGatewayFigurePointlist();

        shape.setReferenceShapePoints(pointList);
        shape.setToolTip(new TooltipFigure(this));
        return shape;
    }

    /**
     * @return pointlist for gateway shape.
     */
    public static PointList getGatewayFigurePointlist() {
        PointList pointList =
                new PointList(new int[] { 0,
                        -((ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE) / 2),
                        ((ProcessWidgetConstants.GATEWAY_WIDTH_SIZE) / 2), 0,
                        0, ((ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE) / 2),
                        -((ProcessWidgetConstants.GATEWAY_WIDTH_SIZE) / 2), 0 });
        return pointList;
    }

    @Override
    public Translatable translateToModel(Translatable t) {
        ShapeWithDescriptionFigure f = (ShapeWithDescriptionFigure) getFigure();
        Point offset = f.getReferencePointOffset();
        t.performTranslate(offset.x, offset.y);
        return t;
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

    @Override
    protected void refreshVisuals() {
        ShapeWithDescriptionFigure f = (ShapeWithDescriptionFigure) getFigure();

        /*
         * Unset the figure used as hyperlink listener (the icon fig) in case we
         * end up without one.
         */
        activityHyperLinkListener.setHyperlinkFigure(null);

        f.setText(getGatewayAdapter().getName());

        GatewayAdapter ga = getGatewayAdapter();
        GatewayType gatewayType = ga.getGatewayType();

        /*
         * Sid XPD-2516: Allow overrides from processEditorConfiguration
         * extension point.
         */
        ProcessEditorObjectType processEditorObjectType =
                gatewayType.getProcessEditorObjectType();

        Image overrideImage =
                getActivityIconOverride(processEditorObjectType, getModel());

        if (overrideImage != null) {
            f.setImage(overrideImage, 1, 1);

        } else {
            ImageRegistry ir =
                    ProcessWidgetPlugin.getDefault().getImageRegistry();

            switch (gatewayType.getValue()) {
            case GatewayType.EXLCUSIVE_DATA:
                if (ga.isXORDataMarkerVisible()) {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_XORDATA_GATEWAY_ICON),
                            1,
                            1);
                } else {
                    f.setImage(null);
                }
                break;
            case GatewayType.PARALLEL:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_PARALLEL_GATEWAY_ICON),
                        1,
                        1);
                break;
            case GatewayType.EXCLUSIVE_EVENT:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_XOREVENT_GATEWAY_ICON),
                        1,
                        1);
                break;
            case GatewayType.INCLUSIVE:
                f.setImage(ir.get(ProcessWidgetConstants.IMG_OR_GATEWAY_ICON),
                        1,
                        1);
                break;
            case GatewayType.COMPLEX:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_COMPLEX_GATEWAY_ICON),
                        1,
                        1);
                break;
            default:
                f.setImage(null);
                break;
            }
        }

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(ga);

        // Get colors for object.
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(ga, getFillColorIDForPartType());
        WidgetRGB borderColor =
                colors.getGraphicalNodeColor(ga, getLineColorIDForPartType());

        f.setBackgroundColor(fillColor.getColor());
        f.setForegroundColor(borderColor.getColor());

        // calculate reference point
        Dimension size = f.getPreferredSize();
        Point ref = f.getReferencePointOffset();
        Point loc = ga.getLocation().getCopy();
        loc.x -= ref.x;
        loc.y -= ref.y;

        /*
         * If we have an icon figure then make it the hyperlink action figure.
         * The hyperlink is handled via contribution to
         * activityHyperLinkProvider extension point.
         */
        if (f.getIconFigure() != null) {
            activityHyperLinkListener.setHyperlinkFigure(f.getIconFigure());
        }

        IFigure p = f.getParent();
        LayoutManager layout = p.getLayoutManager();
        Rectangle r = new Rectangle(loc, size);
        if (!r.equals(layout.getConstraint(f))) {
            layout.setConstraint(f, r);
            layout.layout(p);
            p.revalidate();
        }
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        // installEditPolicy("Hover", new HoverInfoEditPolicy());

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());

        return;
    }

    @Override
    protected void createClickOrDragGadgetPolicies() {
        // Install the change type policy before the standard ones (so that it
        // always stays in the same place visually
        ProcessWidget processWidget = getProcessWidget();

        installEditPolicy(CycleGatewayTypeGadgetPolicy.EDIT_POLICY_ID,
                new CycleGatewayTypeGadgetPolicy(getAdapterFactory(),
                        getEditingDomain(), processWidget
                                .getEditPolicyEnablementProvider()));

        super.createClickOrDragGadgetPolicies();

        return;
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info = new HoverInfo(Messages.GatewayEditPart_Hover_title);

        String name;
        GatewayAdapter gatewayAdapter = getGatewayAdapter();
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            name = String.format("%s (%s)", gatewayAdapter.getName(), //$NON-NLS-1$
                    gatewayAdapter.getTokenName());
        } else {
            name = gatewayAdapter.getName();
        }

        if (name != null && name.length() > 0) {
            info.addProperty(Messages.GatewayEditPart_HoverName_label, name);
        }
        info.addProperty(Messages.GatewayEditPart_HoverType_label,
                gatewayAdapter.getGatewayType().toString());

        info.setDocumentationURL(gatewayAdapter.getDocumentationURL(), this);
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

            boolean isFromCatchCompensation = false;
            if (!isSource && connection.getSource() instanceof EventEditPart) {
                EventEditPart ev = (EventEditPart) connection.getSource();
                if (ev.isAttachedToTaskBorder()) {
                    if (EventFlowType.FLOW_INTERMEDIATE == ev
                            .getEventFlowType()
                            && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                    .getEventTriggerType()) {
                        isFromCatchCompensation = true;
                    }
                }
            }

            if (connection instanceof AssociationEditPart
                    && !isFromCatchCompensation) {
                if (associationAnchor == null) {
                    associationAnchor = new ChopShapeAnchor(fig);
                }
                anchor = associationAnchor;

            } else {
                anchor = new GatewayActivityAnchor(fig, isSource);
            }
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
                Point relLocation = location.getCopy();
                fig.translateToRelative(relLocation);

                if (((ShapeWithDescriptionFigure) getFigure())
                        .borderContainsPoint(relLocation)) {
                    PointList points =
                            ((LineAnchorLinesProvider) fig)
                                    .getLineAnchorLinePoints();

                    Point closest =
                            XPDLineUtilities
                                    .getPolylinePointClosestToPoint(points,
                                            location);
                    anchor = new LineAnchor(fig, closest.getCopy());

                }
            }
        }

        if (anchor == null) {
            // Not a fixed position anchor so get the default positioning one
            // appropriate to connection type.
            boolean isFromCatchCompensation = false;

            if (!isSource && request instanceof CreateConnectionRequest) {
                CreateConnectionRequest creq =
                        (CreateConnectionRequest) request;

                if (creq.getSourceEditPart() instanceof EventEditPart) {
                    EventEditPart ev = (EventEditPart) creq.getSourceEditPart();
                    if (ev.isAttachedToTaskBorder()) {
                        if (EventFlowType.FLOW_INTERMEDIATE == ev
                                .getEventFlowType()
                                && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                        .getEventTriggerType()) {
                            isFromCatchCompensation = true;
                        }
                    }
                }
            }
            // For associations use a polain chop box anchor.
            if (!isFromCatchCompensation
                    && (((request instanceof CreateConnectionRequest) && ((CreateConnectionRequest) request)
                            .getNewObjectType() == AssociationAdapter.class) || ((request instanceof ReconnectRequest) && ((ReconnectRequest) request)
                            .getConnectionEditPart() instanceof AssociationEditPart))) {
                if (associationAnchor == null) {
                    associationAnchor = new ChopShapeAnchor(fig);
                }

                anchor = associationAnchor;
            }
            // For sequence / message flow use special activity anchor
            else {
                anchor = new GatewayActivityAnchor(fig, isSource);
            }
        }

        return anchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        String colorID = null;

        GatewayType gt = getGatewayAdapter().getGatewayType();
        return gt.getGetDefaultFillColorId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        String colorID = null;

        GatewayType gt = getGatewayAdapter().getGatewayType();
        return gt.getGetDefaultLineColorId();
    }

    /**
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#removeChildVisual(org.eclipse.gef.EditPart)
     * 
     * @param childEditPart
     */
    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        /* Detach the hyperlink listener from the figure. */
        if (activityHyperLinkListener != null) {
            activityHyperLinkListener.setHyperlinkFigure(null);
        }

        super.removeChildVisual(childEditPart);
    }

}
