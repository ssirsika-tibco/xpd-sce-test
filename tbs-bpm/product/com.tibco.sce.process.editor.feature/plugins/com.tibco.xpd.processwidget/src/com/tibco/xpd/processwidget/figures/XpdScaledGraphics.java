/**
 * 
 */
package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Pattern;

/**
 * @author wzurek
 * 
 */
public class XpdScaledGraphics extends ScaledGraphics {

    private Graphics baseGraphics;

    private Pattern pattern;

    private double zoom;

    public XpdScaledGraphics(Graphics g) {
        super(g);
        zoom = 1;
        baseGraphics = g;
    }

    public void setBackgroundPattern(Device dev, float x1, float y1, float x2,
            float y2, Color color1, Color color2) {
        setBackgroundPattern(dev, x1, y1, x2, y2, color1, 255, color2, 255);
    }

    public void setBackgroundPattern(Device dev, float x1, float y1, float x2,
            float y2, Color color1, int alpha1, Color color2, int alpha2) {
        if (baseGraphics instanceof SWTGraphics) {
            x1 = (float) (x1 * getZoom());
            y1 = (float) (y1 * getZoom());
            x2 = (float) (x2 * getZoom());
            y2 = (float) (y2 * getZoom());

            if (pattern != null) {
                pattern.dispose();
            }
            pattern =
                    new Pattern(dev, x1, y1, x2, y2, color1, alpha1, color2,
                            alpha2);
            baseGraphics.setBackgroundPattern(pattern);
            return;
        }
        if (baseGraphics instanceof XpdScaledGraphics) {
            x1 = (float) (x1 * getZoom());
            y1 = (float) (y1 * getZoom());
            x2 = (float) (x2 * getZoom());
            y2 = (float) (y2 * getZoom());

            ((XpdScaledGraphics) baseGraphics).setBackgroundPattern(dev,
                    x1,
                    y1,
                    x2,
                    y2,
                    color1,
                    alpha1,
                    color2,
                    alpha2);
            return;
        }
        return;
    }

    public void resetBackgroundPattern() {
        if (pattern != null) {
            pattern.dispose();
        }
        if (baseGraphics instanceof SWTGraphics) {
            if (pattern != null) {
                pattern.dispose();
            }
            baseGraphics.setBackgroundPattern(null);
            return;
        }
        if (baseGraphics instanceof XpdScaledGraphics) {
            ((XpdScaledGraphics) baseGraphics).resetBackgroundPattern();
            return;
        }
        return;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (pattern != null) {
            pattern.dispose();
        }
    }

    @Override
    public void scale(double amount) {
        super.scale(amount);
        zoom = zoom * amount;
    }

    private double getZoom() {
        return zoom;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.ScaledGraphics#setLineDash(int[])
     */
    @Override
    public void setLineDash(int[] dash) {
        if (dash != null && dash.length > 0) {
            int[] scaledDash = new int[dash.length];

            for (int i = 0; i < scaledDash.length; i++) {
                scaledDash[i] = (int) Math.ceil((dash[i] * zoom));
            }

            super.setLineDash(scaledDash);
        } else {
            super.setLineDash(dash);
        }

    }

}
