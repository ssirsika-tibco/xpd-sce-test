package com.tibco.xpd.processwidget.figures;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;

public class RoundedPolylineConnection extends
        BaseLogExceptionPolylineConnection implements IHighlightableFigure,
        LineAnchor.LineAnchorLinesProvider, IFadeableFigure {
    private boolean highlightOn = false;

    private int alpha = 255;

    protected boolean showConstraints = false;

    private static final int CONSTRAINT_SIZE = 5;

    public RoundedPolylineConnection() {
        super();
    }

    private void drawLines(Graphics g, PointList p) {
        if (p.size() > 2) {
            int prevDistance = 0;
            for (int i = 1; i < p.size() - 1; i++) {
                Point prev = p.getPoint(i - 1);
                Point curr = p.getPoint(i);
                Point next = p.getPoint(i + 1);
                int roundSize = 10;

                if (prev.x == curr.x && curr.y == next.y) {
                    int l1 = prev.y - curr.y;
                    int l2 = curr.x - next.x;
                    int rs1 = Math.min(Math.abs(l1), roundSize);
                    int rs2 = Math.min(Math.abs(l2), roundSize);
                    if (l1 < 0)
                        rs1 = -rs1;
                    if (l2 < 0)
                        rs2 = -rs2;

                    Rectangle r = new Rectangle();
                    r.height = rs1;
                    r.width = rs2;
                    r.x = curr.x;
                    r.y = curr.y;

                    if (r.height < 0) {
                        r.y += r.height;
                        r.height *= -1;
                    }
                    if (r.width < 0) {
                        r.x += r.width;
                        r.width *= -1;
                    }

                    g.drawLine(prev.x, prev.y - prevDistance, curr.x, curr.y
                            + rs1 / 2);
                    prevDistance = rs2 / 2;
                    int sa;
                    int ao;
                    if (l1 < 0) {
                        if (l2 < 0) {
                            sa = 180;
                            ao = 90;
                        } else {
                            sa = 0;
                            ao = -90;
                        }
                    } else {
                        sa = 90;
                        if (l2 < 0) {
                            ao = 90;
                        } else {
                            ao = -90;
                        }
                    }

                    g.drawArc(r.translate(-rs2, 0), sa, ao);
                } else if (prev.y == curr.y && curr.x == next.x) {
                    int l1 = prev.x - curr.x;
                    int l2 = curr.y - next.y;
                    int rs1 = Math.min(Math.abs(l1), roundSize);
                    int rs2 = Math.min(Math.abs(l2), roundSize);
                    if (l1 < 0)
                        rs1 = -rs1;
                    if (l2 < 0)
                        rs2 = -rs2;
                    Rectangle r = new Rectangle();
                    r.height = rs2;
                    r.width = rs1;
                    r.x = curr.x;
                    r.y = curr.y;

                    if (r.height < 0) {
                        r.y += r.height;
                        r.height *= -1;
                    }
                    if (r.width < 0) {
                        r.x += r.width;
                        r.width *= -1;
                    }

                    g.drawLine(prev.x - prevDistance,
                            prev.y,
                            curr.x + rs1 / 2,
                            curr.y);
                    prevDistance = rs2 / 2;

                    int sa;
                    int ao;
                    if (l1 < 0) {
                        sa = 0;
                        if (l2 < 0) {
                            ao = 90;
                        } else {
                            ao = -90;
                        }
                    } else {
                        if (l2 < 0) {
                            sa = 90;
                            ao = 90;
                        } else {
                            sa = 270;
                            ao = -90;
                        }
                    }
                    g.drawArc(r.translate(0, -rs2), sa, ao);
                } else {
                    if (prev.y == curr.y) {
                        g.drawLine(prev.x - prevDistance,
                                prev.y,
                                curr.x,
                                curr.y);
                    } else {
                        g.drawLine(prev.x,
                                prev.y - prevDistance,
                                curr.x,
                                curr.y);
                    }
                    prevDistance = 0;
                }
            }

            // draw the last segment
            if (p.getPoint(p.size() - 2).x == p.getLastPoint().x) {
                g.drawLine(p.getPoint(p.size() - 2).x,
                        p.getPoint(p.size() - 2).y - prevDistance,
                        p.getLastPoint().x,
                        p.getLastPoint().y);
            } else {
                g.drawLine(p.getPoint(p.size() - 2).x - prevDistance,
                        p.getPoint(p.size() - 2).y,
                        p.getLastPoint().x,
                        p.getLastPoint().y);
            }
        } else {
            g.drawPolyline(p);
        }

        // If requested, paint the constraints as well.
        if (showConstraints) {
            List constraints = (List) getRoutingConstraint();

            if (constraints != null) {
                Rectangle rctBord =
                        new Rectangle(0, 0, CONSTRAINT_SIZE, CONSTRAINT_SIZE);
                Rectangle rctFill =
                        new Rectangle(0, 0, CONSTRAINT_SIZE - 1,
                                CONSTRAINT_SIZE - 1);

                Color fgCol = g.getForegroundColor();
                g.setForegroundColor(ColorConstants.white);
                Color bgCol = g.getBackgroundColor();
                g.setBackgroundColor(getForegroundColor());
                int lWidth = g.getLineWidth();
                g.setLineWidth(2);

                int style = g.getLineStyle();
                g.setLineStyle(SWT.LINE_SOLID);

                for (Iterator iter = constraints.iterator(); iter.hasNext();) {
                    Bendpoint bP = (Bendpoint) iter.next();

                    Point loc = bP.getLocation().getCopy();

                    rctBord.setLocation((loc.x - (CONSTRAINT_SIZE / 2)) - 1,
                            (loc.y - (CONSTRAINT_SIZE / 2)) - 1);
                    g.drawRectangle(rctBord);

                    rctFill.setLocation(rctBord.x + 1, rctBord.y + 1);
                    g.fillRectangle(rctFill);

                }

                g.setLineStyle(style);
                g.setLineWidth(lWidth);
                g.setBackgroundColor(bgCol);
                g.setForegroundColor(fgCol);
            }
        }

    }

    @Override
    protected void outlineShape(Graphics g) {
        int origWidth = getLineWidth();

        PointList p = getPoints();

        g.pushState();
        int aa = g.getAntialias();
        g.setAntialias(SWT.ON);

        if (highlightOn) {
            g.setLineWidth(origWidth * 2);
        }

        drawLines(g, p);

        g.setAntialias(aa);
        g.popState();

    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds().getCopy();

        // Make sure that we account for temporary constriant markers else
        // they'll get clipped at edges.
        b.x -= CONSTRAINT_SIZE / 2;
        b.y -= CONSTRAINT_SIZE / 2;
        b.width += CONSTRAINT_SIZE;
        b.height += CONSTRAINT_SIZE;

        return (b);

    }

    /**
     * Set flag that dictates whether connection firgure paints handles for it's
     * constraints.
     * 
     * @param on
     */
    public void setShowConstraints(boolean showConstraints) {
        this.showConstraints = showConstraints;
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
