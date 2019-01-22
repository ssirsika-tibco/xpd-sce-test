package com.tibco.xpd.processwidget.figures;

/**
 * XPD Grid layer, slightly different from base gef GridLayer in that
 * it paints alternate lines dashed and the whole thing slightly lighter.
 */

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.GridLayer;

import com.tibco.xpd.resources.ui.XpdColorRegistry;

public class XPDGridLayer extends GridLayer {
    private boolean ignorePaint = false;

    private static final int CENTRECROSS_SIZE = 6;

    public XPDGridLayer() {
        super();
        setForegroundColor(XpdColorRegistry.getDefault().getColor(null,
                234,
                234,
                234));

    }

    @Override
    protected void paintGrid(Graphics g) {
        if (!ignorePaint) {
            Rectangle clip = g.getClip(Rectangle.SINGLETON);

            Point org = origin.getCopy();
            g.pushState();

            if (gridX > 0 && gridY > 0) {
                if (org.x >= clip.x) {
                    while (org.x - gridX >= clip.x) {
                        org.x -= gridX;
                    }
                } else {
                    while (org.x < clip.x) {
                        org.x += gridX;
                    }
                }

                // Is first line on even or odd gridline boundary...
                int gridCX = gridX * 2;
                boolean firstVertEven = false;

                if ((org.x % (gridCX)) == 0) {
                    firstVertEven = true;
                }

                if (org.y >= clip.y) {
                    while (org.y - gridY >= clip.y) {
                        org.y -= gridY;
                    }
                } else {
                    while (org.y < clip.y) {
                        org.y += gridY;
                    }
                }

                // Is first line on even or odd gridline boundary...
                int gridCY = gridY * 2;
                boolean firstHorzEven = false;

                if ((org.y % (gridCY)) == 0) {
                    firstHorzEven = true;
                }

                //
                // VERTICAL LINES First...
                //
                // Draw in the solid lines first (on every second grid space).
                int x;
                int y;

                if (firstVertEven) {
                    x = org.x;
                } else {
                    x = org.x + gridX;
                }

                for (; x < (clip.x + clip.width); x += gridCX) {
                    g.drawLine(x, clip.y, x, clip.y + clip.height);
                }

                // Then draw in the short lines to make up crosses at entre of
                // each square.
                if (firstVertEven) {
                    x = org.x + gridX;
                } else {
                    x = org.x;
                }

                for (; x < (clip.x + clip.width); x += gridCX) {
                    if (firstHorzEven) {
                        y = org.y + gridY;
                    } else {
                        y = org.y;
                    }

                    for (; y < (clip.y + clip.height); y += gridCY) {
                        g.drawLine(x, y - CENTRECROSS_SIZE, x, y
                                + CENTRECROSS_SIZE);
                    }
                }

                //
                // Then HORIZONTAL LINES...
                //
                // Draw in the solid lines first (on every second grid space).

                if (firstHorzEven) {
                    y = org.y;
                } else {
                    y = org.y + gridY;
                }

                for (; y < (clip.y + clip.height); y += gridCY) {
                    g.drawLine(clip.x, y, clip.x + clip.width, y);
                }

                // Then draw in the short lines to make up crosses at entre of
                // each square.
                if (firstHorzEven) {
                    y = org.y + gridY;
                } else {
                    y = org.y;
                }

                for (; y < (clip.y + clip.height); y += gridCY) {
                    if (firstVertEven) {
                        x = org.x + gridX;
                    } else {
                        x = org.x;
                    }

                    for (; x < (clip.x + clip.width); x += gridCX) {
                        g.drawLine(x - CENTRECROSS_SIZE, y, x
                                + CENTRECROSS_SIZE, y);
                    }
                }

            }

            g.popState();
        }

        return;
    }

    /**
     * @param ignorePaint
     *            the ignorePaint to set
     */
    public void setIgnorePaint(boolean ignorePaint) {
        this.ignorePaint = ignorePaint;
    }
}
