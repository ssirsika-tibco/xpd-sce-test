/**
 * AssociationConnectionFigure.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter.AssociationDirectionType;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;

/**
 * AssociationConnectionFigure
 * 
 */
public class AssociationConnectionFigure extends
        BaseLogExceptionPolylineConnection implements
        IScaleableConnectionFigure, LineAnchor.LineAnchorLinesProvider,
        IHighlightableFigure, IFadeableFigure {

    private int alpha = 255;

    private PolylineDecoration targetArrow;

    private PolylineDecoration sourceArrow;

    private boolean highlightOn = false;

    /**
     * 
     */
    public AssociationConnectionFigure() {
        targetArrow = new PolylineDecoration();
        sourceArrow = new PolylineDecoration();

        setLineWidth(1);
        setLineStyle(Graphics.LINE_CUSTOM);

        float dash[] = new float[] { 4, 2 };
        setLineDash(dash);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Shape#paintFigure(org.eclipse.draw2d.Graphics)
     */
    @Override
    public void paintFigure(Graphics graphics) {
        int origiWidth = getLineWidth();

        if (highlightOn) {
            setLineWidth(origiWidth * 2);
        }

        super.paintFigure(graphics);

        if (highlightOn) {
            setLineWidth(origiWidth);
        }
    }

    public void setAssociationDirectionType(
            AssociationDirectionType directionType) {
        if (directionType == AssociationAdapter.DIRECTION_NONE) {
            setTargetDecoration(null);
            setSourceDecoration(null);
        } else if (directionType == AssociationAdapter.DIRECTION_BOTH) {
            setTargetDecoration(targetArrow);
            setSourceDecoration(sourceArrow);
        } else if (directionType == AssociationAdapter.DIRECTION_TO_SOURCE) {
            setTargetDecoration(null);
            setSourceDecoration(sourceArrow);
        } else if (directionType == AssociationAdapter.DIRECTION_TO_TARGET) {
            setTargetDecoration(targetArrow);
            setSourceDecoration(null);
        }

    }

    @Override
    public void setSourceDecorationScale(double sourceDecorationScale) {
        // btw the 7 and 3 constants shamelessly ripped out of
        // PolygoDecoration class.
        sourceArrow.setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                * sourceDecorationScale,
                ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                        * sourceDecorationScale);
    }

    @Override
    public void setTargetDecorationScale(double targetDecorationScale) {
        targetArrow.setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                * targetDecorationScale,
                ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                        * targetDecorationScale);

    }

    /**
     * Override standard polyline contains point to provide easier selection.
     * 
     */
    private static final int EXTRA_HITTEST_TOLERANCE = 7;

    @Override
    public boolean containsPoint(int x, int y) {
        boolean ret = false;

        /*
         * XPD-1694: Connection can ONLY contain point if it is visible!! If we
         * don't check this then connecti0ons inside embedded sub-process can
         * pick up mouse clicks etc
         */
        if (this.isVisible()) {

            PointList pts = getPoints();

            Point testPoint = new Point(x, y);
            Point closest =
                    XPDLineUtilities.getPolylinePointClosestToPoint(pts,
                            testPoint);

            if (XPDLineUtilities.getLineLength(testPoint, closest) < EXTRA_HITTEST_TOLERANCE) {
                ret = true;

                // Ok it's within tolerance BUT don't include if the closest is
                // the
                // very end of the connection. This is so that the connection is
                // not
                // selected in preference to an a connected object when the test
                // point is just inside the object.
                Point first = pts.getFirstPoint();

                if (closest.x == first.x && closest.y == first.y) {
                    ret = false;

                } else {
                    Point last = pts.getLastPoint();

                    if (closest.x == last.x && closest.y == last.y) {
                        ret = false;
                    }
                }

            }

            // If not 'on' line then check children (i.e. the label)
            if (!ret) {
                List children = getChildren();

                for (Iterator iter = children.iterator(); iter.hasNext();) {
                    IFigure child = (IFigure) iter.next();

                    if (child.containsPoint(x, y)) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.figures.LineAnchor.LinePointsProvider#
     * getLinePoints()
     */
    @Override
    public PointList getLineAnchorLinePoints() {
        PointList pts = getPoints().getCopy();

        translateToAbsolute(pts);

        return pts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.figures.LineAnchor.LineAnchorLinesProvider
     * #getLineDirectionFromAnchorPoint(org.eclipse.draw2d.geometry.Point,
     * org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public int getLineDirectionFromAnchorPoint(Point anchorPos, Point refPoint) {
        return PositionConstants.NONE;
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
        if (on == highlightOn) {
            return;
        }
        highlightOn = on;

        repaint();
    }

    @Override
    public Integer getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            this.alpha = alpha;
            revalidate();
            repaint();
        }
        return;
    }

    @Override
    public void paint(Graphics graphics) {
        int oldAlpha = graphics.getAlpha();

        graphics.setAlpha(alpha);

        super.paint(graphics);

        graphics.setAlpha(oldAlpha);

        return;
    }

}
