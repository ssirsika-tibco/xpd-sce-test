/**
 * ConnectionLabelPosition.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;


/**
 * ConnectionLabelPosition
 * 
 */
public class ConnectionLabelPosition {

    private double percentAnchorOnConnection = 50.0;

    private int xOffsetFromAnchor = 0;

    private int yOffsetFromAnchor = 0;

    /**
     * Default constructor - position = Zero offset from 50% position on line
     */
    public ConnectionLabelPosition() {
    }

    public ConnectionLabelPosition(double percentAnchorOnConnection,
            int xOffsetFromAnchor, int yOffsetFromAnchor) {
        this.percentAnchorOnConnection = percentAnchorOnConnection;
        this.xOffsetFromAnchor = xOffsetFromAnchor;
        this.yOffsetFromAnchor = yOffsetFromAnchor;
    }

    /**
     * @return the percentAnchorOnConnection
     */
    public double getPercentAnchorOnConnection() {
        return percentAnchorOnConnection;
    }

    /**
     * @param percentAnchorOnConnection
     *            the percentAnchorOnConnection to set
     */
    public void setPercentAnchorOnConnection(double percentAnchorOnConnection) {
        this.percentAnchorOnConnection = percentAnchorOnConnection;
    }

    /**
     * @return the xOffsetFromAnchor
     */
    public int getXOffsetFromAnchor() {
        return xOffsetFromAnchor;
    }

    /**
     * @param offsetFromAnchor
     *            the xOffsetFromAnchor to set
     */
    public void setXOffsetFromAnchor(int offsetFromAnchor) {
        xOffsetFromAnchor = offsetFromAnchor;
    }

    /**
     * @return the yOffsetFromAnchor
     */
    public int getYOffsetFromAnchor() {
        return yOffsetFromAnchor;
    }

    /**
     * @param offsetFromAnchor
     *            the yOffsetFromAnchor to set
     */
    public void setYOffsetFromAnchor(int offsetFromAnchor) {
        yOffsetFromAnchor = offsetFromAnchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return "AnchorPos %: " + getPercentAnchorOnConnection() + "  xOffset: " //$NON-NLS-1$ //$NON-NLS-2$
                + getXOffsetFromAnchor() + " yOffset: " //$NON-NLS-1$
                + getYOffsetFromAnchor();
    }
}
