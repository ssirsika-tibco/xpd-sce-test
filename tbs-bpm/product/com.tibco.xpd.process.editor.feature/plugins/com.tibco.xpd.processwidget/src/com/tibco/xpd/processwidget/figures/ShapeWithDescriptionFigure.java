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

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * Shape with description figure
 * 
 * @author wzurek
 */
public class ShapeWithDescriptionFigure extends BaseLogExceptionFadeableFigure
        implements HandleBounds, IHighlightableFigure {

    private Point referencePointOffset = new Point();

    private PointList referenceShapePoints;

    private Dimension shapeSize;

    private FlowPage page;

    private TextFlow flow;

    private Shape shape;

    private ImageFigure imageFigure;

    private int imageXOffset = 0;

    private int imageYOffset = 0;

    private double imageScale = 1.0;

    private String text;

    private IFigure type;

    Ellipse innerEllipse = null;

    private boolean highlightOn = false;

    private int ellipseLineWidth = 0;

    private boolean isDashed = false;

    private static int[] NON_CANCEL_LINE_DASH = new int[] { 6, 4 };

    /*
     * XPD-4917 - Keeps track of bounds last time we did a layout (can use this
     * to ascertain whether a layout of child figures is required before
     * performing various operations such as getHandleBounds().
     */
    private Rectangle boundsAtLastLayout = new Rectangle();

    /**
     * HighlightableEllipse
     * 
     */
    private final class HighlightableEllipse extends Ellipse implements
            IHighlightableFigure {
        @Override
        protected void outlineShape(Graphics g) {
            g.setBackgroundColor(getBackgroundColor());
            g.setForegroundColor(getForegroundColor());

            int oldLineStyle = g.getLineStyle();
            if (isDashed) {
                g.setLineStyle(SWT.LINE_CUSTOM);
                g.setLineDash(NON_CANCEL_LINE_DASH);
            }

            int aa = g.getAntialias();
            g.setAntialias(SWT.ON);

            super.outlineShape(g);

            g.setAntialias(aa);
            g.setLineDash((int[]) null);
            g.setLineStyle(oldLineStyle);
        }

        @Override
        protected void fillShape(Graphics gr) {
            if (innerEllipse != null) {
                // If we have an inner ellipse then fill between
                // inner and outer with end of gradient colour.
                gr.setBackgroundColor(ProcessWidgetColors
                        .getGradientEndColor(getBackgroundColor()));

                super.fillShape(gr);
            } else {
                gr.setBackgroundColor(getBackgroundColor());
                gr.setForegroundColor(ProcessWidgetColors
                        .getGradientEndColor(gr.getBackgroundColor()));

                Pattern p =
                        XPDFigureUtilities
                                .setBackgroundPattern(gr, getBounds());

                super.fillShape(gr);
                XPDFigureUtilities.resetBackgroundPattern(gr, p);

                gr.setForegroundColor(getForegroundColor());
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processwidget.figures.IHighlightableFigure#setHighlight
         * (boolean)
         */
        @Override
        public void setHighlight(boolean on) {
            ShapeWithDescriptionFigure.this.setHighlight(on);
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return ShapeWithDescriptionFigure.this.toString();
        }
    }

    public class ShapeWithDesctiptionLayout extends AbstractLayout {

        @Override
        protected Dimension calculatePreferredSize(IFigure container,
                int wHint, int hHint) {

            Dimension min = shapeSize.getCopy();
            Dimension result;
            referencePointOffset.y = 1 + min.height / 2;

            if (text == null || text.length() == 0) {
                result = new Dimension(min.width + 2, min.height + 2);
            } else {
                // NOTE!!! DO NOT REMOVE THIS SEEMINGLY SUPERFLUOUS
                // page.getPreferredSize()
                // because if this is not done before the child text flow it
                // adversely affects the result of the preferred size of flow.
                Dimension prefPageSize =
                        page.getPreferredSize((int) (shapeSize.width * 2.5f),
                                -1);
                if (prefPageSize.width > 0) {
                    // Leave this!
                }
                // ==============================================================
                // ==================

                Dimension prefTextSize =
                        flow.getPreferredSize((int) (shapeSize.width * 2.5f),
                                -1);

                min.width = Math.max(min.width, prefTextSize.width);
                min.height = 1 + min.height + prefTextSize.height;
                result = min.expand(2, 2);
            }
            referencePointOffset.x = result.width / 2;
            return result;
        }

        @Override
        public void layout(IFigure container) {
            Rectangle b = container.getBounds();

            if (referenceShapePoints != null) {
                Point offset = b.getLocation().translate(referencePointOffset);
                int[] src = referenceShapePoints.toIntArray();
                int[] dest = new int[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                PointList pointList = new PointList(dest);
                pointList.translate(offset);

                ((Polygon) shape).setPoints(pointList);

            } else {
                Point topLeft =
                        new Point(b.x + b.width / 2 - shapeSize.width / 2,
                                b.y + 1);
                shape.setBounds(new Rectangle(topLeft, shapeSize));
            }
            if (type != null) {
                Point topLeft =
                        new Point(b.x + b.width / 2 - shapeSize.width / 2,
                                b.y + 1);
                type.setBounds(new Rectangle(topLeft, shapeSize));
            }
            page.setBounds(new Rectangle(b.x, b.y + shapeSize.height + 1,
                    b.width, b.height - shapeSize.height - 1));

            if (shape != null) {
                shape.getLayoutManager().layout(shape);
            }
        }
    }

    public ShapeWithDescriptionFigure() {
        setLayoutManager(new ShapeWithDesctiptionLayout());

        page = new FlowPage();
        flow = new TextFlow();
        flow.setText(Messages.ShapeWithDescriptionFigure_Default_label);
        flow.setRequestFocusEnabled(true);
        BlockFlow block = new BlockFlow();
        block.add(flow);
        block.setHorizontalAligment(PositionConstants.CENTER);
        page.add(block);
        add(page);
        page.setForegroundColor(getForegroundColor());
    }

    public void setReferenceShapePoints(PointList referenceShapePoints) {

        this.referenceShapePoints = referenceShapePoints;

        Rectangle sb = referenceShapePoints.getBounds();

        this.shapeSize = sb.getSize();

        if (!(shape instanceof Polygon)) {
            if (shape != null) {
                remove(shape);
            }
            shape = new PolygonWithShapeAnchorSupport();
            shape.setForegroundColor(getForegroundColor());
            shape.setBackgroundColor(getBackgroundColor());
            shape.setLineWidth(1);
            shape.setFill(true);

            ShapeLayout shapeLayout = new ShapeLayout();
            shape.setLayoutManager(shapeLayout);
            add(shape);
        }

        layout();
        invalidate();

    }

    public void setEllipseSize(int diameter, int lineWidth) {
        ellipseLineWidth = lineWidth;

        //
        // Alignment guides snap policy will give us horrible 1 pixel
        // misalignments if the total diameter is an even number. Therefore make
        // sure it is not.
        int totalDiameter = diameter + lineWidth;
        if ((totalDiameter % 2) == 0) {
            totalDiameter += 1;
        }

        this.shapeSize = new Dimension(totalDiameter, totalDiameter);

        if (!(shape instanceof Ellipse)) {
            if (shape != null) {
                remove(shape);
            }

            referenceShapePoints = null;

            shape = new HighlightableEllipse();
            shape.setForegroundColor(getForegroundColor());
            shape.setBackgroundColor(getBackgroundColor());
            shape.setFill(true);
            shape.setLayoutManager(new ShapeLayout());
            add(shape);
        }
        shape.setLineWidth(lineWidth);
    }

    public void setInnerEllipseSize(int radius, int lineWidth) {
        // this.shapeSize = new Dimension(radinus + lineWidth, radinus +
        // lineWidth);

        if (shape instanceof Ellipse && radius > 0) {

            if (innerEllipse == null) {
                innerEllipse = new Ellipse() {

                    @Override
                    protected void outlineShape(Graphics g) {
                        g.setBackgroundColor(getBackgroundColor());
                        g.setForegroundColor(getForegroundColor());

                        int oldLineStyle = g.getLineStyle();
                        if (isDashed) {
                            g.setLineStyle(SWT.LINE_CUSTOM);
                            g.setLineDash(NON_CANCEL_LINE_DASH);
                        }

                        int aa = g.getAntialias();
                        g.setAntialias(SWT.ON);

                        super.outlineShape(g);

                        g.setAntialias(aa);

                        g.setLineDash((int[]) null);
                        g.setLineStyle(oldLineStyle);
                    }

                    @Override
                    protected void fillShape(Graphics gr) {
                        gr.setBackgroundColor(getBackgroundColor());
                        gr.setForegroundColor(ProcessWidgetColors
                                .getGradientEndColor(gr.getBackgroundColor()));

                        Pattern p =
                                XPDFigureUtilities.setBackgroundPattern(gr,
                                        getBounds());

                        super.fillShape(gr);

                        XPDFigureUtilities.resetBackgroundPattern(gr, p);

                        gr.setBackgroundColor(getBackgroundColor());
                        gr.setForegroundColor(getForegroundColor());

                    }

                };

                shape.setLayoutManager(new ShapeLayout() {
                    @Override
                    protected Dimension calculatePreferredSize(
                            IFigure container, int wHint, int hHint) {
                        return container.getSize();
                    }

                    @Override
                    public void layout(IFigure container) {
                        Point c = container.getBounds().getCenter();
                        if (innerEllipse != null) {
                            Dimension ps = innerEllipse.getPreferredSize();

                            innerEllipse.setBounds(new Rectangle(c.x - ps.width
                                    / 2, c.y - ps.height / 2, ps.width,
                                    ps.height));
                        }

                        super.layout(container);

                    }
                });

                shape.add(innerEllipse);
            }

            innerEllipse.setPreferredSize(radius, radius);
            innerEllipse.setLineWidth(lineWidth);

        } else if (radius == 0) {
            if (innerEllipse != null) {
                shape.remove(innerEllipse);
                shape.setLayoutManager(new ShapeLayout());
                innerEllipse = null;
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        flow.setText(text);

        // Change to GEF 4.0 caused our text figure to get resized to nothing
        // when value changed to null and then back to a value.
        // Ensure we throw away the layout manager's cached info.
        if (getLayoutManager() != null) {
            getLayoutManager().invalidate();
        }

        layout();
    }

    @Override
    protected void paintFigure(Graphics gr) {
    }

    public Point getReferencePointOffset() {
        return referencePointOffset;
    }

    public IFigure getShape() {
        return shape;
    }

    public IFigure getTextFigure() {
        return page;
    }

    @Override
    protected void fireFigureMoved() {
        super.fireFigureMoved();
        layout();
    }

    /**
     * @see org.eclipse.draw2d.Figure#layout()
     * 
     */
    @Override
    protected void layout() {
        /*
         * XPD-4917 - Keeps track of bounds last time we did a layout (can use
         * this to ascertain whether a layout of child figures is required
         * before performing various operations such as getHandleBounds().
         */
        boundsAtLastLayout = getBounds().getCopy();
        super.layout();
    }

    /**
     * @return <code>true</code> if figure content needs re-layout (checks if
     *         bounds has changed since the last layout).
     */
    protected boolean layoutRequired() {
        return !boundsAtLastLayout.equals(getBounds());
    }

    @Override
    public Rectangle getHandleBounds() {
        /*
         * XPD-4917: It is possible that we get called to get handle-bounds in
         * between a setBounds/Location and a layout of our children.
         * 
         * As we base our handle bounds on our shape-child-figure, if the layout
         * hasn't happened since last set bounds then the shape-figure's bounds
         * will be out of date and we will return the wrong co-ordinates.
         * 
         * So make sure we do a layout before returning handle bounds (if
         * necessary)
         */
        if (layoutRequired()) {
            layout();
        }

        Rectangle b = this.shape.getBounds().getCopy();
        if (shape instanceof Ellipse) {
            b.expand(2, 2);
        }

        return b;
    }

    public void setType(IFigure type) {
        if (this.type != type) {
            if (this.type != null) {
                remove(this.type);
            }
            this.type = type;
            if (this.type != null) {
                add(type);
            }
            invalidate();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.figures.IHighlightableFigure#setHighlight
     * (boolean, org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public void setHighlight(boolean on) {
        if (on == highlightOn) {
            return;
        }

        highlightOn = on;

        if (shape instanceof Ellipse) {
            if (highlightOn) {
                shape.setLineWidth(ellipseLineWidth + 2);
            } else {
                shape.setLineWidth(ellipseLineWidth);
            }
        } else {
            if (highlightOn) {
                shape.setLineWidth(2);
            } else {
                shape.setLineWidth(1);
            }
        }

    }

    /**
     * PolygonWithShapeAnchorSupport
     * 
     */
    private final class PolygonWithShapeAnchorSupport extends Polygon implements
            ChopShapeAnchor.ShapeAnchorLinesProvider,
            LineAnchor.LineAnchorLinesProvider, IHighlightableFigure,
            HandleBounds {

        @Override
        protected void outlineShape(Graphics g) {
            int aa = g.getAntialias();
            g.setAntialias(SWT.ON);

            super.outlineShape(g);

            g.setAntialias(aa);

        }

        @Override
        protected void fillShape(Graphics gr) {
            gr.setBackgroundColor(getBackgroundColor());
            gr.setForegroundColor(ProcessWidgetColors.getGradientEndColor(gr
                    .getBackgroundColor()));

            Pattern p =
                    XPDFigureUtilities.setBackgroundPattern(gr, getBounds());

            super.fillShape(gr);

            XPDFigureUtilities.resetBackgroundPattern(gr, p);

            gr.setBackgroundColor(getBackgroundColor());
            gr.setForegroundColor(getForegroundColor());

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processwidget.figures.ChopShapeAnchor.
         * ShapeAnchorLinesProvider#getShapeAnchorLinePoints()
         */
        @Override
        public PointList getShapeAnchorLinePoints() {
            PointList pts;

            pts = getPoints().getCopy();
            translateToAbsolute(pts);

            return pts;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processwidget.figures.IHighlightableFigure#setHighlight
         * (boolean)
         */
        @Override
        public void setHighlight(boolean on) {
            ShapeWithDescriptionFigure.this.setHighlight(on);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processwidget.figures.anchors.LineAnchor.
         * LineAnchorLinesProvider#getLineAnchorLinePoints()
         */
        @Override
        public PointList getLineAnchorLinePoints() {
            PointList pts;

            pts = getPoints().getCopy();
            translateToAbsolute(pts);

            pts.addPoint(pts.getFirstPoint());

            return pts;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processwidget.figures.anchors.LineAnchor.
         * LineAnchorLinesProvider
         * #getLineDirectionFromAnchorPoint(org.eclipse.draw2d.geometry.Point,
         * org.eclipse.draw2d.geometry.Point)
         */
        @Override
        public int getLineDirectionFromAnchorPoint(Point anchorPos,
                Point refPoint) {
            return PositionConstants.NONE;
        }

        /**
         * @see org.eclipse.gef.handles.HandleBounds#getHandleBounds()
         * 
         * @return
         */
        @Override
        public Rectangle getHandleBounds() {
            /*
             * Gateway fig is a PolyGon which is a PolyLine which takes the
             * linewidth into accoutn when calculating the bounds. Unfortunately
             * that means that when the figure is highlighted when we cacluate
             * the anchor loation it can get offset by a little because the
             * linewidth is set to 2 during highlighting. So we will do our on
             * calc.
             */
            Rectangle b = ((Polygon) shape).getPoints().getBounds();
            return b;
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return ShapeWithDescriptionFigure.this.toString();
        }
    }

    public void setImage(Image image, int xOffset, int yOffset) {
        if (image == null && imageFigure == null) {
            return;
        } else if (imageFigure != null && image == imageFigure.getImage()) {
            return;
        }

        imageXOffset = xOffset;
        imageYOffset = yOffset;

        if (image == null) {
            if (imageFigure != null) {
                shape.remove(imageFigure);
                imageFigure = null;
            }
            layout();
            invalidate();
            return;
        }

        if (imageFigure == null) {
            imageFigure = new PrintableImageFigure();
            shape.add(imageFigure);
        }

        imageFigure.setImage(image);
        layout();
        invalidate();
    }

    public void setImage(Image image) {
        setImage(image, 0, 0);
    }

    private class ShapeLayout extends AbstractLayout {

        @Override
        protected Dimension calculatePreferredSize(IFigure container,
                int wHint, int hHint) {
            Dimension ms = container.getMinimumSize();
            return new Dimension(Math.max(ms.width, wHint), Math.max(ms.height,
                    hHint));
        }

        @Override
        public void layout(IFigure container) {
            Rectangle bnds = container.getBounds();

            if (imageFigure != null) {
                Dimension size = imageFigure.getPreferredSize().getCopy();

                size.width = (int) (size.width * imageScale);
                size.height = (int) (size.height * imageScale);

                int iX =
                        (bnds.x + (bnds.width / 2)) - (size.width / 2)
                                + imageXOffset;
                int iY =
                        (bnds.y + (bnds.height / 2)) - (size.height / 2)
                                + imageYOffset;

                Rectangle b = new Rectangle(iX, iY, size.width, size.height);

                imageFigure.setBounds(b);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#containsPoint(int, int)
     */
    @Override
    public boolean containsPoint(int x, int y) {
        boolean ret = false;

        Rectangle wholeBnds = getBounds().getCopy();

        // Treat selection/connection bounds as just outside of bounding
        // recatangle.

        // First do a quick check if it's anywhere near.
        wholeBnds.expand(2 * ProcessWidgetConstants.BORDER_HIT_TOLERANCE,
                2 * ProcessWidgetConstants.BORDER_HIT_TOLERANCE);

        if (wholeBnds.contains(x, y)) {

            if (shape instanceof Ellipse) {
                // Ellipse figure contain point if the figure is within ellipse
                // +
                // hit tolerance.
                Rectangle b = shape.getBounds();

                double radius = ((double) b.width) / 2;

                double lineLen =
                        XPDLineUtilities.getLineLength(b.getCenter(),
                                new Point(x, y));

                if (lineLen < (radius + ProcessWidgetConstants.BORDER_HIT_TOLERANCE)) {
                    ret = true;
                }

            } else if (shape instanceof Polygon) {
                // For polygons scale up the polygon for extra border tolerance.
                Polygon polygon = (Polygon) shape;

                PointList pts = polygon.getPoints();

                Rectangle b = shape.getBounds();

                double factor =
                        1.0f
                                + ((double) ProcessWidgetConstants.BORDER_HIT_TOLERANCE * 2)
                                / (b.width);

                PointList scaledPts =
                        XPDLineUtilities.scaleCentredPolyline(pts, factor);

                if (XPDLineUtilities.polygonContainsPoint(scaledPts, x, y)) {
                    ret = true;
                }

            } else {
                ret = shape.containsPoint(x, y);
            }

            if (!ret) {
                // Shape not hit, check hit on text.
                // Selection ONLY on actual used text area.
                Rectangle b = page.getBounds().getCopy();
                Point c = b.getCenter();

                Dimension psz = flow.getPreferredSize();

                Rectangle r = Rectangle.SINGLETON;

                r.x = c.x - (psz.width / 2);
                r.y = c.y - (psz.height / 2);
                r.width = psz.width;
                r.height = psz.height;

                ret = r.contains(x, y);

            }
        }

        return ret;
    }

    public boolean borderContainsPoint(int x, int y) {
        boolean ret = false;

        if (containsPoint(x, y)) {

            // Point is within the OUTER selection boundary.
            // If it's outside of the inner(visible) portion of shape then its
            // on the border.

            if (shape instanceof Ellipse) {
                Rectangle b = shape.getBounds();

                double radius = ((double) b.width) / 2;

                double lineLen =
                        XPDLineUtilities.getLineLength(b.getCenter(),
                                new Point(x, y));

                if (lineLen >= radius) {
                    ret = true;
                }

            } else if (shape instanceof Polygon) {
                Polygon polygon = (Polygon) shape;

                PointList pts = polygon.getPoints();

                if (!XPDLineUtilities.polygonContainsPoint(pts, x, y)) {
                    ret = true;
                }

            } else {
                ret = true;
            }
        }
        return ret;
    }

    public boolean borderContainsPoint(Point pt) {
        return borderContainsPoint(pt.x, pt.y);
    }

    /**
     * @param imageScale
     *            the imageScale to set
     */
    public void setImageScale(double imageScale) {
        this.imageScale = imageScale;
    }

    /**
     * @return the referenceShapePoints
     */
    public PointList getReferenceShapePoints() {
        return referenceShapePoints;
    }

    /**
     * @return icon figure or <code>null</code> if no icon.
     */
    public IFigure getIconFigure() {
        return imageFigure;
    }

    /**
     * Set line dashing (for non-cancelling events etc) on or off.
     * 
     * @param isDashed
     */
    public void setLineDashing(boolean isDashed) {
        /*
         * Make sure to invalidate the figure when line dash changed otherwise
         * it won't repaint until something else changes.
         */
        if (this.isDashed != isDashed) {
            this.isDashed = isDashed;

            /*
             * Sid XPD-7581: invalidateTree() was not enough to cause an
             * immediate repaint() - need repaint() instead.
             */
            repaint();
        }
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (flow != null) {
            return String.format("%s-%s[%d]: %s", //$NON-NLS-1$
                    this.getClass().getSimpleName(),
                    shape != null ? shape.getClass().getSimpleName()
                            : "<no-shape>", //$NON-NLS-1$
                    hashCode(),
                    flow.getText());
        }
        return super.toString();
    }

}
