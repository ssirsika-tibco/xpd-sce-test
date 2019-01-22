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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * @author wzurek
 */
public class PoolFigure extends BaseLogExceptionFigure {

    public static final int POOL_BORDER_HIT_TOLERANCE = 15;

    private PoolHeaderFigure header = null;

    private PoolContentFigure content;

    private boolean isClosed = false;

    public PoolFigure(DiagramViewType diagramViewType) {
        HeaderFigureStyle headerFigureStyle;

        if (DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(diagramViewType)) {
            headerFigureStyle = HeaderFigureStyle.NONE;
        }
        // POOLLESS PAGEFLOW
        else if (DiagramViewType.NO_POOLS.equals(diagramViewType)) {
            headerFigureStyle = HeaderFigureStyle.NONE;
        }
        // POOLLESS PAGEFLOW
        else {
            headerFigureStyle = HeaderFigureStyle.VERTICAL;
        }

        setMinimumSize(new Dimension(0, 9));

        header = new PoolHeaderFigure(headerFigureStyle);

        content = new PoolContentFigure();

        HeaderLayout headerLayout = new HeaderLayout(headerFigureStyle);
        headerLayout.setConstraint(header, HeaderLayout.HEADER);
        headerLayout.setConstraint(content, HeaderLayout.CONTENTS);

        if (!HeaderFigureStyle.NONE.equals(headerFigureStyle)) {
            add(header);
        }

        add(content);
        setLayoutManager(headerLayout);

    }

    public IFigure getContentPane() {
        return content;
    }

    public void setText(String text) {
        header.setText(text != null ? text : Messages.PoolFigure_label);
        invalidate();
    }

    @Override
    public void setBackgroundColor(Color bg) {
        // super.setBackgroundColor(bg);

        if (header != null) {
            header.setBackgroundColor(bg);
        }

        // if (content != null) {
        // content.setBackgroundColor(bg);
        // }
    }

    @Override
    public void setForegroundColor(Color fg) {
        super.setForegroundColor(fg);

        if (header != null) {
            header.setForegroundColor(fg);
        }

        if (content != null) {
            content.setForegroundColor(fg);
        }
    }

    public HeaderFigure getHeaderFigure() {
        return header;
    }

    public IFigure getContentFigure() {
        return content;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;

        if (isClosed) {
            getContentFigure().setVisible(false);

        } else {
            getContentFigure().setVisible(true);
        }

        header.setClosed(isClosed);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
     */
    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension prefSize = super.getPreferredSize(wHint, hHint);
        if (!isClosed) {
            return prefSize;
        }

        Dimension hdrSize = getHeaderFigure().getPreferredSize();

        prefSize.height = hdrSize.height;

        return prefSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     * 
     * When pool is closed, fill background so don't see grid when it's on.
     */
    @Override
    protected void paintFigure(Graphics gr) {
        if (isClosed) {
            gr.pushState();

            Rectangle b = getBounds().getCopy();

            gr.setBackgroundColor(ProcessWidgetPlugin.getDefault()
                    .getColor(new RGB(230, 230, 235)));
            gr.fillRectangle(b);

            gr.popState();

        } else {
            super.paintFigure(gr);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#containsPoint(int, int)
     */
    @Override
    public boolean containsPoint(int x, int y) {
        Rectangle b = getSelectionBounds();

        return b.contains(x, y);
    }

    /**
     * @return
     */
    public Rectangle getSelectionBounds() {
        Rectangle b = getBounds().getCopy();

        b.expand(0, POOL_BORDER_HIT_TOLERANCE);
        return b;
    }

    public boolean borderLineContainsPoint(Point pt) {
        Rectangle outer = getSelectionBounds().getCopy();
        if (outer.contains(pt)) {
            if (isClosed() || getChildren().size() == 0) {
                return true;
            }

            Rectangle bnds = getBounds().getCopy();

            if ((pt.y <= bnds.y) || (pt.y >= bnds.bottom())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Free any resources created by this figure.
     */
    public void dispose() {
        header.dispose();
    }
}
