/**
 * AnchorPositionFeedbackFigure.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * AnchorPositionFeedbackFigure
 *
 */
public class AnchorPositionFeedbackFigure extends Figure {
    
    private int direction = PositionConstants.NONE;
    
    private static final int CIRCLE_SIZE = 8;

    Point lastsetloc;
    public AnchorPositionFeedbackFigure() {
        super();
        
        setForegroundColor(ColorConstants.black);
        setBackgroundColor(ColorConstants.white);
        
        int d = CIRCLE_SIZE + 6;
        
        setSize(d, d);
        
    }
    
    /**
     * Convenience method to set centre point of figure.
     * 
     * @param loc
     */
    public void setCenterLocation (Point loc) {
        Dimension sz = getSize();
    
        lastsetloc = loc.getCopy();
        Point p = new Point(loc.x - (sz.width/2), loc.y - (sz.height/2));
        setLocation(p);
    }
    
    public void setDirection (int direction) {
        if (direction != this.direction) {
            this.direction = direction;
            repaint();
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics gr) {
        int aa = gr.getAntialias();
        gr.setAntialias(SWT.ON);
        
        Rectangle bnds = getBounds().getCopy();
        
        Point left = bnds.getLeft();
        Point right = bnds.getRight();
        Point top = bnds.getTop();
        Point bottom = bnds.getBottom();

        Color oldcl = gr.getForegroundColor();
        int oldlwidth = gr.getLineWidth();

        gr.setLineWidth(3);
        
        gr.setForegroundColor(ColorConstants.white);
        gr.drawLine(left.x, left.y, right.x, right.y);
        gr.drawLine(top.x, top.y, bottom.x, bottom.y);

        gr.setForegroundColor(oldcl);

        gr.setLineWidth(1);
        gr.drawLine(left.x+1, left.y-1, left.x+1, left.y + 1);
        gr.drawLine(right.x-1, right.y-1, right.x-1, right.y + 1);
        gr.drawLine(top.x - 1, top.y+1, top.x + 1, top.y+1);
        gr.drawLine(bottom.x - 1, bottom.y-1, bottom.x + 1, bottom.y-1);

        Point centre = bnds.getCenter();

        gr.drawLine(centre.x - 2, centre.y, centre.x+2, centre.y);
        gr.drawLine(centre.x, centre.y-2, centre.x, centre.y+2);
        
        gr.setForegroundColor(ColorConstants.white);
        gr.setLineWidth(3);
        gr.drawOval(centre.x - (CIRCLE_SIZE/2), centre.y - (CIRCLE_SIZE/2), CIRCLE_SIZE, CIRCLE_SIZE);
        
        gr.setLineWidth(oldlwidth);
        gr.setForegroundColor(oldcl);
        //gr.fillOval(centre.x - (CIRCLE_SIZE/2), centre.y - (CIRCLE_SIZE/2), CIRCLE_SIZE, CIRCLE_SIZE);
        gr.drawOval(centre.x - (CIRCLE_SIZE/2), centre.y - (CIRCLE_SIZE/2), CIRCLE_SIZE, CIRCLE_SIZE);

        gr.setAntialias(aa);
    }
    
}
