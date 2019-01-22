/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.sortConnection;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;

/**
 * SortableConnectionHandle
 * 
 * @author aallway
 */
public class SortableConnectionHandle extends AbstractHandle implements
        IFadeableFigure {

    private ConnectionEditPart hostConnection;

    private ISortableConnectionSetProvider connectionSetProvider = null;

    private static final int TXT_MARGIN_HEIGHT = 1;

    private static final int TXT_MARGIN_WIDTH = 1;

    private String sortOrderIndex = ""; //$NON-NLS-1$

    private Dimension sortOrderIndexSize;

    private static Font font;

    private int alpha = 255;

    public SortableConnectionHandle(ConnectionEditPart hostConnection,
            ISortableConnectionSetProvider connectionSetProvider) {
        super(hostConnection, new SortConnectionHandleLocator(hostConnection));
        this.hostConnection = hostConnection;
        this.connectionSetProvider = connectionSetProvider;

        this.setForegroundColor(ColorConstants.darkBlue);
        this.setBackgroundColor(ColorConstants.white);

        // this.setOpaque(true);

        this.setCursor(Cursors.HAND);

    }

    /**
     * Only use this constructor for the handle as a Figure (i.e. just the
     * visual aspects).
     * 
     * @deprecated
     */
    @Deprecated
    public SortableConnectionHandle() {
        this(null, null);
    }

    /**
     * @return the hostConnection
     */
    public ConnectionEditPart getHostConnection() {
        return hostConnection;
    }

    /**
     * @return the sortOrderIndex
     */
    public String getSortOrderIndex() {
        return sortOrderIndex;
    }

    /**
     * @param sortOrderIndex
     *            the sortOrderIndex to set
     */
    public void setSortOrderIndex(String sortOrderIndex) {
        this.sortOrderIndex = sortOrderIndex;

        sortOrderIndexSize = calcSortOrderIndexSize(this, sortOrderIndex);
    }

    /**
     * @return the sortOrderIdnexSize
     */
    public Dimension getSortOrderIndexSize() {
        return sortOrderIndexSize;
    }

    @Override
    public Integer getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            this.alpha = alpha;
            revalidate();
            repaint();
        }
    }

    /**
     * @return
     */
    private void calculateSortOrderIndex() {
        if (connectionSetProvider != null && hostConnection != null) {
            String newSortOrderIndex = "?";//$NON-NLS-1$

            List<ConnectionEditPart> connSet =
                    connectionSetProvider.getSortedConnections();
            if (connSet != null) {
                int i = connSet.indexOf(hostConnection);
                if (i >= 0) {
                    newSortOrderIndex = Integer.toString(i + 1);
                }
            }

            setSortOrderIndex(newSortOrderIndex);
        }

        return;
    }

    /**
     * 
     */
    public static Dimension calcSortOrderIndexSize(IFigure hostFig,
            String sortOrderIndex) {
        if (font == null) {
            // Create our label font.
            FontData fontData = hostFig.getFont().getFontData()[0];

            int oldStyle = fontData.getStyle();

            fontData.setStyle(SWT.BOLD);
            font = new Font(Display.getCurrent(), fontData);

            fontData.setStyle(oldStyle);
        }

        return FigureUtilities.getTextExtents(sortOrderIndex, font);
    }

    @Override
    protected DragTracker createDragTracker() {
        if (connectionSetProvider == null || hostConnection == null) {
            throw new RuntimeException(
                    "Can't use sort connection handle as a drag handle without a connectionSetProvider"); //$NON-NLS-1$
        }
        return new SortableConnectionHandleTracker(hostConnection,
                connectionSetProvider);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        calculateSortOrderIndex();
        paintSortIndexMarker(graphics, this, sortOrderIndex, sortOrderIndexSize);

    }

    private List<SortableConnectionHandle> getAllSortHandles() {
        if (connectionSetProvider != null) {
            return connectionSetProvider.getAllSortHandles();
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * The guts of painting a sort order marker (so that can be used for drag
     * feebback figures also.
     * 
     * @param graphics
     */
    public static void paintSortIndexMarker(Graphics graphics,
            IFadeableFigure hostFig, String sortOrderIndex,
            Dimension sortOrderIndexSize) {

        graphics.pushState();
        graphics.setAlpha(hostFig.getAlpha());

        graphics.setAntialias(SWT.ON);
        // graphics.setLineWidth(2);
        graphics.setBackgroundColor(hostFig.getBackgroundColor());
        graphics.setForegroundColor(hostFig.getForegroundColor());

        Rectangle b = hostFig.getBounds().getCopy();

        graphics.fillOval(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
        graphics.drawOval(b.x + 1, b.y + 1, b.width - 2, b.height - 2);

        int textX = b.x + 1 + ((b.width - sortOrderIndexSize.width) / 2);
        int textY = b.y + ((b.height - sortOrderIndexSize.height) / 2);

        graphics.setFont(font);
        graphics.drawText(sortOrderIndex, textX, textY);

        graphics.popState();
    }

    public static Dimension getOuterSizeForSortIndexSize(Dimension sortIndexSize) {
        Dimension sz = new Dimension();

        int size = Math.max(sortIndexSize.width, sortIndexSize.height);

        sz.width = size + TXT_MARGIN_WIDTH * 2;
        sz.height = size + TXT_MARGIN_HEIGHT * 2;

        return sz;
    }

    private static class SortConnectionHandleLocator implements Locator {
        private ConnectionEditPart hostConnection;

        public SortConnectionHandleLocator(ConnectionEditPart hostConnection) {
            this.hostConnection = hostConnection;
        }

        @Override
        public void relocate(IFigure target) {

            if (hostConnection != null) {
                if (hostConnection.getFigure() instanceof Connection
                        && target instanceof SortableConnectionHandle) {
                    SortableConnectionHandle handle =
                            (SortableConnectionHandle) target;

                    handle.calculateSortOrderIndex();

                    Connection connFig =
                            (Connection) hostConnection.getFigure();

                    Dimension sz = handle.getSortOrderIndexSize().getCopy();

                    int size = Math.max(sz.width, sz.height);

                    sz.width = size + TXT_MARGIN_WIDTH * 2;
                    sz.height = size + TXT_MARGIN_HEIGHT * 2;

                    PointList pts = connFig.getPoints();

                    //
                    // Somehow we DON'T want to put this marker ontop of any
                    // other marker.
                    //
                    List<SortableConnectionHandle> sortHandles =
                            handle.getAllSortHandles();

                    int offsetOnLine = 16;

                    Rectangle bnds = null;
                    Point lastLoc = null;

                    int myHdlIdx = sortHandles.indexOf(handle);

                    while (true) {
                        Point loc =
                                XPDLineUtilities.getLinePointFromOffset(pts,
                                        offsetOnLine);

                        if (loc.equals(lastLoc)) {
                            // We must have reached end of line, have no choice
                            // but to place it there.
                            break;
                        }

                        lastLoc = loc.getCopy();

                        connFig.translateToAbsolute(loc);
                        target.translateToRelative(loc);

                        loc.x -= sz.width / 2;
                        loc.y -= sz.height / 2;

                        bnds = new Rectangle(loc.x, loc.y, sz.width, sz.height);

                        // Check that this handles bounds are not intersecting
                        // with a previous ones (this kind of relies on this
                        // locator getting called in the same order as the
                        // things in sortHandles but that should be the case as
                        // that was the order they should have been added to the
                        // handle layer in).

                        if (myHdlIdx > 0) {
                            int i = 0;
                            for (; i < myHdlIdx; i++) {
                                Rectangle b = sortHandles.get(i).getBounds();

                                if (bnds.intersects(b)) {
                                    offsetOnLine += 4;
                                    break;
                                }
                            }

                            if (i >= myHdlIdx) {
                                // we didn't find an overlap so we can stop
                                // calculating now!
                                break;
                            }

                        } else {
                            // Don't need to check overlaps on the first handle
                            // in list
                            break;
                        }

                    }

                    handle.setBounds(bnds);
                }
            }

            return;
        }
    }

    /**
     * ISortableConnectionSetProvider
     * <p>
     * Simple interface that allows the SortableConnectionHandle to update the
     * sort-location in the list of conenctions returned.
     * 
     * @author aallway
     */
    public interface ISortableConnectionSetProvider {
        /**
         * @return the relevant set of collections in order of evaluation.
         */
        List<ConnectionEditPart> getSortedConnections();

        /**
         * @return The list of sort handles that this is one of.
         */
        List<SortableConnectionHandle> getAllSortHandles();
    }

}
