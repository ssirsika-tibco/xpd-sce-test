/**
 * 
 */
package com.tibco.xpd.processwidget.adapters;

import org.eclipse.draw2d.geometry.Dimension;

/**
 * Representation of widget's bendpoint - distance from the start of hte line,
 * distance of the end of the line and weight
 * 
 * @author wzurek
 */
public class XPDBendpointType {

    public XPDBendpointType() {
        this(new Dimension(), new Dimension(), 0.5f);
    }

    public XPDBendpointType(int sx, int sy, int ex, int ey, float weight) {
        this(new Dimension(sx, sy), new Dimension(ex, ey), 0.5f);
    }

    public XPDBendpointType(Dimension fromStart, Dimension fromEnd, float weight) {
        this.fromStart = fromStart;
        this.fromEnd = fromEnd;
        this.weight = weight;
    }

    public Dimension fromStart;

    public Dimension fromEnd;

    public float weight;

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof XPDBendpointType) {
            XPDBendpointType rbp = (XPDBendpointType) obj;
            return equals(fromEnd, rbp.fromEnd)
                    && equals(fromStart, rbp.fromStart);// && weight == rbp.weight;
        }
        return false;
    }

    private boolean equals(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null)
                || (obj1 != null && obj1.equals(obj2));
    }

    public int hashCode() {
        return (fromEnd == null ? 0 : fromEnd.hashCode())
                | (fromStart == null ? 0 : fromStart.hashCode());
    }

    public String toString() {
        return "Bendpoint(" + String.valueOf(fromStart) //$NON-NLS-1$
                + String.valueOf(fromEnd) + ";" + weight + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
