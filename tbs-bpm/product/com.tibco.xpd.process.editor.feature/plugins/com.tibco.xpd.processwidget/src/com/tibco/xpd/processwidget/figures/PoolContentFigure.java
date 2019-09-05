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

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * @author wzurek
 */
public class PoolContentFigure extends BaseLogExceptionFigure {

    public PoolContentFigure() {
        setMinimumSize(new Dimension());

        setLayoutManager(new XYLayout() {
            @Override
            protected Dimension calculatePreferredSize(IFigure container,
                    int wHint, int hHint) {
                Dimension result = new Dimension();
                List c = container.getChildren();

                for (int i = 0; i < c.size(); i++) {
                    IFigure f = (IFigure) c.get(i);

                    Dimension d = f.getPreferredSize();

                    result.width = Math.max(result.width, d.width);
                    result.height += d.height;

                }
                Dimension res = result.expand(2, 2 + 2 * c.size());
                return res;
            }

            @Override
            public void layout(IFigure container) {
                Rectangle bnds = container.getClientArea();
                int yoff = 1;

                List c = container.getChildren();

                for (int i = 0; i < c.size(); i++) {

                    IFigure f = (IFigure) c.get(i);

                    Dimension dim = f.getPreferredSize();
                    Rectangle laneBnds =
                            new Rectangle(bnds.x, bnds.y + yoff,
                                    bnds.width - 2, dim.height);

                    f.setBounds(laneBnds);

                    yoff += dim.height + 2;
                }

            }

            @Override
            public Dimension getMinimumSize(IFigure container, int wHint,
                    int hHint) {
                return container.getMinimumSize(wHint, hHint);
            }
        });
    }

    @Override
    protected void paintFigure(Graphics gr) {
        if (!XPDFigureUtilities.isInTaskLibraryAlternateView(this)) {
            // 
            // Paint dividing lines between lanes.
            gr.pushState();

            gr.setForegroundColor(ColorConstants.gray);
            gr.setLineStyle(SWT.LINE_DASHDOTDOT);
            Rectangle b = getBounds();

            List c = getChildren();

            if (isOpaque() || c.size() == 0) {
                gr.setBackgroundColor(getBackgroundColor());
                gr.fillRectangle(b);
            }

            for (int i = 0; i < (c.size() - 1); i++) {
                IFigure f = (IFigure) c.get(i);

                if (f instanceof LaneFigure) {
                    LaneFigure lf = (LaneFigure) f;
                    Rectangle fb = f.getBounds();

                    gr.drawLine(b.x + 28,
                            fb.y + fb.height + 1,
                            b.x + b.width,
                            fb.y + fb.height + 1);
                }

            }

            gr.popState();
        }
    }

    /* Sid ACE-2879 - this override now superfluous as PoolEditPart sets closeability of pool header figure. */
    // Keep a check on things being added to enable the close/open button.
    // @Override
    // public void add(IFigure figure, Object constraint, int index) {
    //
    // if (getChildren().size() == 0) {
    // // First add - enable closeability.
    // PoolHeaderFigure hdr =
    // (PoolHeaderFigure) ((PoolFigure) getParent())
    // .getHeaderFigure();
    // hdr.setCloseable(true);
    // }
    //
    // super.add(figure, constraint, index);
    //
    // }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
     */
    @Override
    public void remove(IFigure figure) {
        super.remove(figure);

        // Removed last - Disable closeability.
        if (getChildren().size() == 0) {
            // First add
            PoolHeaderFigure hdr =
                    (PoolHeaderFigure) ((PoolFigure) getParent())
                            .getHeaderFigure();
            hdr.setCloseable(false);

            // Make sure that the pool is now open!
            hdr.setClosed(false);
        }
    }

}
