/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Generally, you should not dispose the colors got from this registry.
 * 
 * @author aallway
 * @since 31 Jan 2012
 */
public class XpdColorRegistry {

    public static XpdColorRegistry defaultInstance = new XpdColorRegistry();

    /**
     * @return the defaultInstance
     */
    public static XpdColorRegistry getDefault() {
        return defaultInstance;
    }

    long numCreates = 0;

    long numReCreates = 0;

    long numActualCreates = 0;

    private Map<String, Color> colorMap = new HashMap<String, Color>();

    /**
     * @param device
     *            Device or <code>null</code> for default
     * @param red
     * @param green
     * @param blue
     * 
     * @return Color for given values nominally you should NOT dispose this
     *         color (although the color will be recreated if so)!
     */
    public Color getColor(Device device, int red, int green, int blue) {
        /* Put XPD at end cos it'll be more efficient doing lookups :) */
        String colorKey = String.format("%03d,%03d,%03d:Dev%d:XPD", //$NON-NLS-1$
                red,
                green,
                blue,
                (device != null ? device.hashCode() : 0));

        numCreates++;

        Color color = colorMap.get(colorKey);
        if (color == null || color.isDisposed()) {
            if (color == null) {
                numActualCreates++;
            } else {
                colorMap.remove(color);
                numReCreates++;
            }

            color = new Color(device, new RGB(red, green, blue));
            colorMap.put(colorKey, color);
        }

        if ((numCreates % 10) == 0) {
            // System.out
            // .println(String
            //                            .format("XpdColorRegistry: numCreateAttempts: %d  numRecreates: %d  numActualCreates: %d", //$NON-NLS-1$
            // numCreates,
            // numReCreates,
            // numActualCreates));
        }

        return color;
    }

    /**
     * @param c1
     * @param c2
     * @param weight
     * 
     * @return As per {@link FigureUtilities#mixColors(Color, Color, double)}
     *         except will return a cached colour if one is available. *
     *         Nominally you should NOT dispose this color (although the color
     *         will be recreated if so)
     */
    public Color mixColors(Color c1, Color c2, double weight) {

        return getColor(Display.getDefault(),
                (int) (c1.getRed() * weight + c2.getRed() * (1 - weight)),
                (int) (c1.getGreen() * weight + c2.getGreen() * (1 - weight)),
                (int) (c1.getBlue() * weight + c2.getBlue() * (1 - weight)));
    }

    /**
     * Dispose any cached colors.
     */
    public void dispose() {
        for (Color color : colorMap.values()) {
            if (color != null && !color.isDisposed()) {
                color.dispose();
            }
        }

        colorMap.clear();
    }

}
