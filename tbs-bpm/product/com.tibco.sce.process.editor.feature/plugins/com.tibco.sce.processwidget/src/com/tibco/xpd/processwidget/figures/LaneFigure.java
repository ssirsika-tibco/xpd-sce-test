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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.resources.ui.XpdColorRegistry;

/**
 * @author wzurek
 */
public class LaneFigure extends BaseLogExceptionFigure implements HandleBounds {

    private LaneHeaderFigure header;

    private LaneContentFigure content;

    private boolean isClosed = false;

    public LaneFigure(DiagramViewType diagramViewType) {
        HeaderFigureStyle headerFigureStyle;

        if (DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(diagramViewType)) {
            headerFigureStyle = HeaderFigureStyle.HORIZONTAL;
        }
        // POOLLESS PAGEFLOW
        else if (DiagramViewType.NO_POOLS.equals(diagramViewType)) {
            headerFigureStyle = HeaderFigureStyle.NONE;
        }
        // POOLLESS PAGEFLOW
        else {
            headerFigureStyle = HeaderFigureStyle.VERTICAL;
        }

        header = new LaneHeaderFigure(headerFigureStyle);
        if (DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(diagramViewType)) {
            header.setBoldText(true);
            header.setLeftText(true);
        }

        content = new LaneContentFigure();
        content.setLayoutManager(new ProcessXYLayout());

        if (!HeaderFigureStyle.NONE.equals(headerFigureStyle)) {
            add(header);
        }

        add(content);

        HeaderLayout headerLayout = new HeaderLayout(headerFigureStyle);
        headerLayout.setConstraint(header, HeaderLayout.HEADER);
        headerLayout.setConstraint(content, HeaderLayout.CONTENTS);
        headerLayout.setMargin(new Dimension(10, 10));
        headerLayout.setContentMargin(new Dimension(10, 10));

        setLayoutManager(headerLayout);

        // Color bg = ProcessWidgetPlugin.getDefault().getColor(new RGB(230,
        // 230, 238));

        super.setBackgroundColor(ColorConstants.white);

    }

    /**
     * Set the lane alpha blend (fade level)
     * 
     * @param alpha
     *            Lane content alpha blend (0 transparent - 255 opaque)
     */
    public void setLaneAlpha(int alpha) {
        if (header != null) {
            header.setLaneAlpha(alpha);
        }

        if (content != null) {
            content.setLaneAlpha(alpha);
        }
    }

    @Override
    public void setForegroundColor(Color c) {
        super.setForegroundColor(c);

        if (header != null) {
            header.setForegroundColor(c);
        }

        if (content != null) {
            content.setForegroundColor(c);
        }
    }

    @Override
    public void setBackgroundColor(Color c) {
        if (header != null) {
            header.setBackgroundColor(c);
        }

        if (content != null) {
            content.setLaneColor(c);
        }

        if (XPDFigureUtilities.isInTaskLibraryAlternateView(this)) {
            // Have to do this otherwise lane content color won't refresh
            // properly on color changes.
            super.setBackgroundColor(c);
        }
    }

    public void setHeaderText(String headerText) {
        header.setText(headerText);
    }

    public Figure getContentPane() {
        return content;
    }

    public void setSize(int size) {
        setMinimumSize(new Dimension(50, size));
        invalidate();
    }

    public LaneHeaderFigure getHeaderFigure() {
        return header;
    }

    public LaneContentFigure getContentFigure() {
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

        if (!DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(XPDFigureUtilities
                .getDiagramViewType(this))) {
            prefSize.height = HeaderFigure.DEFAULT_MIN_HEIGHT;

        } else {
            prefSize.height = hdrSize.height + 3;
        }

        return prefSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.handles.HandleBounds#getHandleBounds()
     */
    @Override
    public Rectangle getHandleBounds() {
        return header.getBounds().getCopy();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     * 
     * When lane is closed, fill background so don't see grid when it's on.
     */
    @Override
    protected void paintFigure(Graphics gr) {
        gr.pushState();
        Rectangle b = getBounds().getCopy();

        if (XPDFigureUtilities.isInTaskLibraryAlternateView(this)) {
            gr.setForegroundColor(header.getBackgroundColor());
            gr.setBackgroundColor(XpdColorRegistry.getDefault()
                    .mixColors(header.getBackgroundColor(),
                            ColorConstants.white,
                            0.15));

            gr.setAntialias(SWT.ON);
            gr.setLineWidth(2);

            //
            // In case grid is on, we need to paint lane content slightly
            // transparent to allow it to show thru.
            gr.setAlpha(150);
            gr.fillRoundRectangle(b, 10, 10);
            gr.setAlpha(255);

            b.width -= 2;
            b.height -= 2;
            b.x += 1;
            b.y += 1;

            gr.setAlpha(200);
            gr.drawRoundRectangle(b, 10, 10);

        } else {
            if (isClosed) {
                gr.setBackgroundColor(ProcessWidgetPlugin.getDefault()
                        .getColor(new RGB(230, 230, 235)));
                gr.fillRectangle(b);

            } else {
                super.paintFigure(gr);
            }
        }

        gr.popState();
        return;
    }

    /**
     * Free any resources created by this figure.
     */
    public void dispose() {
        header.dispose();
    }

}
