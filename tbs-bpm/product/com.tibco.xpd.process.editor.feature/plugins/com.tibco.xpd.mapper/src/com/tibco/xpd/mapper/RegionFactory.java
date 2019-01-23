/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.graphics.Region;

/**
 * Factory for creating SWT Region objects.
 * @author nwilson
 */
public final class RegionFactory {

    /** Correction for finding pixel mid point. */
    private static final double CORRECTION = 0.5;

    /** Constant for finding mid point of arc from radius (1/sqrt(2)). */
    private static final double MID_POINT = 0.7071;

    /** Top left corner identifier. */
    public static final int TOP_LEFT = 1;

    /** Top right corner identifier. */
    public static final int TOP_RIGHT = 2;

    /** Bottom left corner identifier. */
    public static final int BOTTOM_LEFT = 3;

    /** Bottom right corner identifier. */
    public static final int BOTTOM_RIGHT = 4;

    /**
     * Utility class, cannot be instantiated.
     */
    private RegionFactory() {
        
    }
    
    /**
     * Creates a rectangular Region with rounded corners.
     * @param width The region width.
     * @param height The region height.
     * @param radius The radius of the corners.
     * @return The region.
     */
    public static Region createRoundedRectangleRegion(int width, int height,
            int radius) {
        Region region = new Region();
        int[] points;
        IntBuffer buffer = new IntBuffer();
        buffer.append(radius);
        buffer.append(0);
        addCorner(buffer, width - radius, radius, radius, TOP_RIGHT);
        addCorner(buffer, width - radius, height - radius, radius, BOTTOM_RIGHT);
        addCorner(buffer, radius, height - radius, radius, BOTTOM_LEFT);
        addCorner(buffer, radius, radius, radius, TOP_LEFT);
        points = buffer.toArray();
        region.add(points);
        return region;
    }

    /**
     * Adds a circular arc corner to the buffer.
     * @param buffer The buffer to add to.
     * @param x The x offset of the circle origin.
     * @param y The y offset of the circle origin.
     * @param radius The radius of the arc.
     * @param type The corner type.
     */
    private static void addCorner(IntBuffer buffer, int x, int y, int radius,
            int type) {
        int mid = (int) (radius * MID_POINT);
        int sq = radius * radius;
        for (int i = 0; i < mid; i++) {
            int j = (int) (Math.sqrt(sq - (i * i)) + CORRECTION);
            addCornerPoint(buffer, x, y, i, j, type);
        }
        for (int i = mid; i > 0; i--) {
            int j = (int) (Math.sqrt(sq - (i * i)) + CORRECTION);
            addCornerPoint(buffer, x, y, j, i, type);
        }
    }

    /**
     * Adds the offset corner point to the buffer.
     * @param buffer The buffer to add to.
     * @param x The x offset of the corner origin.
     * @param y The y offset of the corner origin.
     * @param i The point x coordinate relative to the corner origin.
     * @param j The point y coordinate relative to the corner origin.
     * @param type The corner type.
     */
    private static void addCornerPoint(IntBuffer buffer, int x, int y, int i,
            int j, int type) {
        int p1 = x;
        int p2 = y;
        if (type == TOP_RIGHT) {
            p1 += i;
            p2 -= j;
        } else if (type == BOTTOM_RIGHT) {
            p1 += j;
            p2 += i;
        } else if (type == BOTTOM_LEFT) {
            p1 -= i;
            p2 += j;
        } else if (type == TOP_LEFT) {
            p1 -= j;
            p2 -= i;
        }
        buffer.append(p1);
        buffer.append(p2);
    }
}
