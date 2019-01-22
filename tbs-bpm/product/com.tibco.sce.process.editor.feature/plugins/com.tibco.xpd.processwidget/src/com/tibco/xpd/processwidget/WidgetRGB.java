/**
 * WidgetRGB.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget;

import java.util.StringTokenizer;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * WidgetRGB
 * 
 */
public class WidgetRGB {
    private RGB rgb = null;

    public WidgetRGB(int red, int green, int blue) {
        rgb = new RGB(red, green, blue);
    }

    /**
     * Construct widget diagram object color from SWT RGB value.
     * 
     * @param rgb
     */
    public WidgetRGB(RGB rgb) {
        this.rgb = rgb;
    }

    /**
     * Contruct from "r,g,b" formatted string.
     * 
     * @param colorStr
     */
    public WidgetRGB(String colorStr) {
        StringTokenizer tok = new StringTokenizer(colorStr, ","); //$NON-NLS-1$

        int r = 0;
        int g = 0;
        int b = 0;

        if (tok.hasMoreTokens()) {
            r = Integer.parseInt(tok.nextToken());

            if (tok.hasMoreTokens()) {
                g = Integer.parseInt(tok.nextToken());
            }

            if (tok.hasMoreTokens()) {
                b = Integer.parseInt(tok.nextToken());
            }
        }
        rgb = new RGB(r, g, b);
    }

    private WidgetRGB() {
    };

    /**
     * Override default RGB toString() for simpler "r,g,b" format.
     */
    public String toString() {
        return (rgb.red + "," + rgb.green + "," + rgb.blue); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Get pooled color from ProcessWidget plugin color registry for sharing
     * colors. NOTE: This means you don't have to and MUST NOT dispose() the
     * returned color!!
     */
    public Color getColor() {
        return (ProcessWidgetPlugin.getDefault().getColor(rgb));
    }

    /**
     * Return the SWT RGB object that represents this process widget diagram
     * object color
     * 
     * @return RGB
     */
    public RGB getRGB() {
        return (rgb);
    }

    /**
     * WidgetRGB is considered equal if it's red, green and blue values are the
     * same.
     * 
     * @return true if colors are the same.
     */
    public boolean equals(Object obj) {
        if (obj instanceof WidgetRGB) {
            RGB rgb = ((WidgetRGB) obj).getRGB();

            if (rgb.red == this.rgb.red && rgb.green == this.rgb.green
                    && rgb.blue == this.rgb.blue) {
                return true;
            }
        }
        return false;
    }
}
