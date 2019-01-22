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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowBox;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;

/**
 * @author wzurek
 */
public class NoteFigure extends Figure implements IHighlightableFigure,
        LineAnchor.LineAnchorLinesProvider,
        ChopShapeAnchor.ShapeAnchorLinesProvider {

    /**
     * 
     */
    private static final int BRACKET_WIDTH = 10;

    TextFlow textFlow;

    FlowPage page;

    BlockFlow block;

    PointList lastTextFlowBounds = null;

    private boolean highlightOn = false;

    public NoteFigure() {

        // setMinimumSize(new Dimension(20, 20));
        textFlow = new TextFlow();
        page = new FlowPage();
        // page.setForegroundColor(getForegroundColor());
        block = new BlockFlow();
        page.add(block);
        block.add(textFlow);
        block.setHorizontalAligment(PositionConstants.LEFT);
        add(page);

        setLayoutManager(new AbstractLayout() {
            protected Dimension calculatePreferredSize(IFigure container,
                    int wHint, int hHint) {

                // If owner of figure (edit part) has set a preferred size then
                // use it as the hint.
                Dimension setSize = NoteFigure.this.getPreferredSize()
                        .getCopy();

                if (setSize != null) {
                    wHint = setSize.width - (BRACKET_WIDTH / 2);
                    hHint = setSize.height;
                }

                Dimension prefSize = page.getPreferredSize(wHint, hHint).getCopy();
                
                if (setSize.width < (prefSize.width + (BRACKET_WIDTH / 2))) {
                    setSize.width = prefSize.width + (BRACKET_WIDTH / 2);
                }


                Dimension ret = new Dimension(Math.max(prefSize.width, setSize.width), prefSize.height + 4);
                
                return ret;
            }

            public void layout(IFigure container) {
                lastTextFlowBounds = null;

                Rectangle b = container.getBounds();
                IFigure f = (IFigure) container.getChildren().get(0);
                f.setBounds(new Rectangle(b.x + 5, b.y + 2, b.width - 2,
                        b.height - 4));

            }
        });
    }

    private int[] getBracketPointList() {
        Rectangle b = getBounds();
        return (new int[] { b.x + BRACKET_WIDTH, b.y, b.x, b.y, b.x,
                b.y + b.height - 1, b.x + BRACKET_WIDTH, b.y + b.height - 1 });

    }

    /**
     * Get the bounds rectangle of the Diagram Note bracket. (in co-ords
     * relative to note figure
     * 
     * @return
     */
    public Rectangle getBracketBounds() {
        Rectangle b = getBounds().getCopy();

        b.width = BRACKET_WIDTH;

        return b;

    }

    protected void paintFigure(Graphics gr) {
        super.paintFigure(gr);
        gr.pushState();

        if (highlightOn) {

            Color oldcl = gr.getForegroundColor();
            PointList pts = getTextFlowBounds();
            gr.setBackgroundColor(ColorConstants.lightGray);

            gr.setAlpha(75);
            gr.fillPolygon(pts);
            gr.setAlpha(255);
            gr.setBackgroundColor(oldcl);

            gr.setLineWidth(3);
        } else {
            gr.setLineWidth(1);
        }
        gr.setForegroundColor(getForegroundColor());
        gr.drawPolyline(getBracketPointList());
        gr.popState();

    }

    public void setText(String text) {
        lastTextFlowBounds = null;
        textFlow.setText(text);
        invalidate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#setBackgroundColor(org.eclipse.swt.graphics.Color)
     */
    public void setBackgroundColor(Color bg) {
        if (bg != null) {
            super.setBackgroundColor(bg);
            textFlow.setBackgroundColor(bg);
            textFlow.setOpaque(true);
            setOpaque(true);
        } else {
            textFlow.setOpaque(false);
            setOpaque(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#setForegroundColor(org.eclipse.swt.graphics.Color)
     */
    public void setForegroundColor(Color fg) {
        super.setForegroundColor(fg);
        textFlow.setForegroundColor(fg);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.IHighlightableFigure#setHighlight(boolean,
     *      org.eclipse.draw2d.geometry.Point)
     */
    public void setHighlight(boolean on) {
        if (on == highlightOn) {
            return;
        }
        highlightOn = on;

        repaint();
    }

    /**
     * Get a point list that defines a line draw around the figure including in
     * and out's for text
     * 
     * @return
     */
    public PointList getTextFlowBounds() {

        PointList pts = new PointList();

        List list = textFlow.getFragments();

        Rectangle noteBounds = getBounds().getCopy();
        Rectangle textPageBounds = page.getBounds().getCopy();

        Point prevPoint = noteBounds.getTopLeft().getCopy();
        pts.addPoint(prevPoint);

        FlowBox box;
        for (int i = 0; i < list.size(); i++) {
            box = (FlowBox) list.get(i);

            // line to top right of line
            prevPoint.x = textPageBounds.x + box.getWidth() + 2;
            if (prevPoint.x > (noteBounds.x + noteBounds.width)) {
                prevPoint.x = (noteBounds.x + noteBounds.width) - 1;
            }
            pts.addPoint(prevPoint);

            // Line down to next line
            if (i == (list.size() - 1)) {
                prevPoint.y = noteBounds.y + noteBounds.height - 1;
            } else {
                prevPoint.y = textPageBounds.y + box.getBaseline()
                        + box.getDescent();
            }
            pts.addPoint(prevPoint);
        }

        // line back along bottom of last line
        prevPoint.x = noteBounds.x;
        pts.addPoint(prevPoint);

        // line back to top of bounds.
        prevPoint.y = noteBounds.y;
        pts.addPoint(prevPoint);

        lastTextFlowBounds = pts.getCopy();
        return (pts);
    }

    /**
     * Get a point list that defines a line draw around the figure including in
     * and out's for text
     * 
     * @return
     */
    public List getTextFlowRects() {
        ArrayList rects = new ArrayList();

        List list = textFlow.getFragments();

        Rectangle noteBounds = getBounds().getCopy();
        Rectangle textPageBounds = page.getBounds().getCopy();

        int nextY = noteBounds.y - 20;

        FlowBox box;
        for (int i = 0; i < list.size(); i++) {
            box = (FlowBox) list.get(i);

            Rectangle rc = new Rectangle();
            rc.x = noteBounds.x;
            rc.y = nextY;

            rc.width = (textPageBounds.x + box.getWidth() + 2) - rc.x;
            if (i == (list.size() - 1)) {
                rc.height = (noteBounds.y + noteBounds.height) - rc.y;
            } else {
                rc.height = (textPageBounds.y + box.getBaseline() + box
                        .getDescent())
                        - rc.y;
            }

            rects.add(rc);

            nextY = rc.y + rc.height;

        }

        return (rects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.ChopShapeAnchor.ShapeAnchorLinesProvider#getLinePoints()
     */
    public PointList getLineAnchorLinePoints() {
        PointList pts = getTextFlowBounds();

        translateToAbsolute(pts);

        return pts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.LineAnchor.LineAnchorLinesProvider#getLineDirectionFromAnchorPoint(org.eclipse.draw2d.geometry.Point,
     *      org.eclipse.draw2d.geometry.Point)
     */
    public int getLineDirectionFromAnchorPoint(Point anchorPos, Point refPoint) {
        return PositionConstants.NONE;
    }

    public IFigure getTextFigure() {
        return textFlow;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.ChopShapeAnchor.ShapeAnchorLinesProvider#getShapeAnchorLinePoints()
     */
    public PointList getShapeAnchorLinePoints() {
        PointList pts = getTextFlowBounds();

        translateToAbsolute(pts);
        return pts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#containsPoint(int, int)
     */
    @Override
    public boolean containsPoint(int x, int y) {
        Rectangle b = getBounds().getCopy();

        // Treat selection/connection bounds as just outside of bounding
        // recatangle.

        // First do a quick check if it's anywhere near.
        b.expand(2 * ProcessWidgetConstants.BORDER_HIT_TOLERANCE,
                2 * ProcessWidgetConstants.BORDER_HIT_TOLERANCE);

        if (b.contains(x, y)) {

            // Ok, the quick check is done, do a more thorough check against the
            // polygoin that bounds the text (scaled by an appropriate amount
            // for border tolerance).
            PointList pts = getTextFlowBounds();

            double factor = 1.0f
                    + ((double) ProcessWidgetConstants.BORDER_HIT_TOLERANCE * 2)
                    / ((double) b.width);

            PointList scaledPts = XPDLineUtilities.scaleCentredPolyline(pts,
                    factor);

            if (XPDLineUtilities.polygonContainsPoint(scaledPts, x, y)) {
                return true;
            }

        }
        return false;
    }

    
    
}
