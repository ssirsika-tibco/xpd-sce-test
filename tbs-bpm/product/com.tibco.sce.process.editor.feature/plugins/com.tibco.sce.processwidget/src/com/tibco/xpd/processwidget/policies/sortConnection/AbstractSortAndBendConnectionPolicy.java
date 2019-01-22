/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.sortConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.neatstuff.FigureFadeUpMouseListener;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.policies.FlowConnectionBendpointEditPolicy;
import com.tibco.xpd.processwidget.policies.sortConnection.SortableConnectionHandle.ISortableConnectionSetProvider;

/**
 * AbstractSortConnectionsAndBendpointPolicy
 * <p>
 * Connection bendpoint edit policy whcih also supports evaluation-sort-order
 * editing
 * <p>
 * When the connections has siblings from the same source node a marker is
 * placed on each one. The marker contains the order-of-evaluation sort index.
 * <p>
 * The user may grab and drag one marker onto a different marker (or other
 * sibling connection) and the policy will then request a command that switches
 * the two connections in the order of evaluation.
 * 
 * 
 * @author aallway
 */
public abstract class AbstractSortAndBendConnectionPolicy extends
        FlowConnectionBendpointEditPolicy implements
        ISortableConnectionSetProvider {

    private FigureFadeUpMouseListener fadeUp = null;

    private List<SortableConnectionHandle> sortHandles = null;

    private SortConnectionIndexFigure feedbackMarkerSrc = null;

    private SortConnectionIndexFigure feedbackMarkerTgt = null;

    private SortConnectionArrowFigure feedbackArrow = null;

    /**
     * @param adapterFactory
     * @param editingDomain
     */
    public AbstractSortAndBendConnectionPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain) {
        super(adapterFactory, editingDomain);
    }

    @Override
    protected List createSelectionHandles() {

        List selectionHandles = super.createSelectionHandles();

        List<ConnectionEditPart> connSet = getSortedConnectionSet();
        if (connSet != null && connSet.size() > 1) {
            if (selectionHandles == null
                    || selectionHandles == Collections.EMPTY_LIST) {
                selectionHandles = new ArrayList<Object>();
            }

            sortHandles = new ArrayList<SortableConnectionHandle>();

            for (ConnectionEditPart connEP : connSet) {
                sortHandles.add(new SortableConnectionHandle(connEP, this));
            }

            selectionHandles.addAll(sortHandles);

            int initialAlpha = 75;
            int fadeUpTime = 125;
            int fadeDownTime = 250;
            int maxAlpha = 255;
            FigureFadeUpMouseListener fadeUp =
                    new FigureFadeUpMouseListener(fadeUpTime, fadeDownTime,
                            initialAlpha, maxAlpha, sortHandles);

            for (IFadeableFigure sortHandle : sortHandles) {
                sortHandle.addMouseMotionListener(fadeUp);
                sortHandle.setAlpha(initialAlpha);

            }

        }

        return selectionHandles;
    }

    @Override
    protected void removeSelectionHandles() {
        if (fadeUp != null && sortHandles != null) {
            for (IFadeableFigure fig : sortHandles) {
                if (fig instanceof Figure) {
                    ((Figure) fig).removeMouseMotionListener(fadeUp);
                }
            }
        }

        super.removeSelectionHandles();

        fadeUp = null;
        sortHandles = null;
    }

    protected abstract List<ConnectionEditPart> getSortedConnectionSet();

    protected abstract Command getSortSwapConnectionsCommand(
            ConnectionEditPart connection);

    @Override
    public List<SortableConnectionHandle> getAllSortHandles() {
        return sortHandles;
    }

    @Override
    public Command getCommand(Request request) {
        if (request instanceof SortSwapConnectionsRequest) {

            SortSwapConnectionsRequest req =
                    (SortSwapConnectionsRequest) request;

            return getSortSwapConnectionsCommand(req.getTargetConnection());
        }

        return super.getCommand(request);
    }

    @Override
    public List<ConnectionEditPart> getSortedConnections() {
        return getSortedConnectionSet();
    }

    @Override
    public void showSourceFeedback(Request request) {
        if (request instanceof SortSwapConnectionsRequest) {
            SortSwapConnectionsRequest req =
                    (SortSwapConnectionsRequest) request;

            IFigure feedbackLayer = getFeedbackLayer();

            Point location = req.getLocation().getCopy();

            feedbackLayer.translateToRelative(location);

            //
            // Create the figure feedback representing source object.
            //
            if (feedbackMarkerSrc == null) {
                feedbackMarkerSrc = new SortConnectionIndexFigure();
                feedbackMarkerSrc.setAlpha(175);
                feedbackMarkerSrc.setForegroundColor(ColorConstants.darkBlue);
                feedbackMarkerSrc.setBackgroundColor(ColorConstants.white);
                feedbackLayer.add(feedbackMarkerSrc);
            }

            String srcIdx = "?"; //$NON-NLS-1$

            List<ConnectionEditPart> connSet = getSortedConnectionSet();

            EditPart hostConnection = req.getHostConnection();
            if (connSet != null && connSet.contains(hostConnection)) {
                srcIdx = Integer.toString(connSet.indexOf(hostConnection) + 1);
            }

            feedbackMarkerSrc.setSortOrderIndex(srcIdx);
            Dimension sz =
                    SortableConnectionHandle
                            .getOuterSizeForSortIndexSize(feedbackMarkerSrc
                                    .getSortOrderIndexSize());

            feedbackMarkerSrc.setBounds(new Rectangle(
                    (location.x - sz.width) - 4, location.y - sz.height,
                    sz.width, sz.height));

            //
            // Then sort out the feedback for the target connection marker.

            if (feedbackMarkerTgt == null) {
                feedbackMarkerTgt = new SortConnectionIndexFigure();
                feedbackMarkerTgt.setAlpha(feedbackMarkerSrc.getAlpha());
                feedbackLayer.add(feedbackMarkerTgt);
            }

            String tgtIdx = "?"; //$NON-NLS-1$

            ConnectionEditPart targetConnection = req.getTargetConnection();
            if (connSet != null && targetConnection != null
                    && targetConnection != hostConnection
                    && connSet.contains(targetConnection)) {
                tgtIdx =
                        Integer.toString(connSet.indexOf(targetConnection) + 1);
                feedbackMarkerTgt.setForegroundColor(ColorConstants.darkBlue);
                feedbackMarkerTgt.setBackgroundColor(ColorConstants.white);

            } else {
                feedbackMarkerTgt.setForegroundColor(ColorConstants.white);
                feedbackMarkerTgt.setBackgroundColor(ColorConstants.red);
            }

            feedbackMarkerTgt.setSortOrderIndex(tgtIdx);
            sz =
                    SortableConnectionHandle
                            .getOuterSizeForSortIndexSize(feedbackMarkerTgt
                                    .getSortOrderIndexSize());

            feedbackMarkerTgt.setBounds(new Rectangle(location.x + 2,
                    location.y - sz.height, sz.width, sz.height));

            //
            // And then the arrow figure between them.
            //
            if (feedbackArrow == null) {
                feedbackArrow = new SortConnectionArrowFigure();
                feedbackArrow.setSize(14, 8);
                feedbackArrow.setAlpha(feedbackMarkerSrc.getAlpha());
                feedbackArrow.setForegroundColor(ColorConstants.darkBlue);

                feedbackLayer.add(feedbackArrow);
            }

            Dimension arrowSz = feedbackArrow.getSize();
            feedbackArrow.setLocation(new Point(location.x
                    - (arrowSz.width / 2), location.y - (arrowSz.height / 2)
                    - (sz.height / 3)));

        } else {
            super.showSourceFeedback(request);
        }

        return;
    }

    @Override
    public void eraseSourceFeedback(Request request) {
        if (request instanceof SortSwapConnectionsRequest) {
            SortSwapConnectionsRequest req =
                    (SortSwapConnectionsRequest) request;

            IFigure layer = getFeedbackLayer();

            if (feedbackArrow != null) {
                layer.remove(feedbackArrow);
                feedbackArrow = null;
            }

            if (feedbackMarkerTgt != null) {
                layer.remove(feedbackMarkerTgt);
                feedbackMarkerTgt = null;
            }

            if (feedbackMarkerSrc != null) {
                layer.remove(feedbackMarkerSrc);
                feedbackMarkerSrc = null;
            }

        } else {
            super.eraseSourceFeedback(request);
        }
    }

    private class SortConnectionIndexFigure extends Figure implements
            IFadeableFigure {

        private String sortOrderIndex = ""; //$NON-NLS-1$ 

        private Dimension sortOrderIndexSize = new Dimension(0, 0);

        private int alpha = 255;

        @Override
        protected void paintFigure(Graphics graphics) {
            SortableConnectionHandle.paintSortIndexMarker(graphics,
                    this,
                    sortOrderIndex,
                    sortOrderIndexSize);
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

            sortOrderIndexSize =
                    SortableConnectionHandle.calcSortOrderIndexSize(this,
                            sortOrderIndex);
        }

        /**
         * @return the sortOrderIndexSize
         */
        public Dimension getSortOrderIndexSize() {
            return sortOrderIndexSize;
        }

        /**
         * @return the alpha
         */
        @Override
        public Integer getAlpha() {
            return alpha;
        }

        /**
         * @param alpha
         *            the alpha to set
         */
        @Override
        public void setAlpha(int alpha) {
            if (alpha != this.alpha) {
                this.alpha = alpha;
                revalidate();
                repaint();
            }
        }
    }

    private class SortConnectionArrowFigure extends Figure {

        private int alpha = 255;

        public SortConnectionArrowFigure() {
        }

        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.pushState();
            graphics.setAlpha(alpha);
            graphics.setAntialias(SWT.ON);
            graphics.setLineWidth(2);
            graphics.setForegroundColor(getForegroundColor());
            graphics.setBackgroundColor(getBackgroundColor());

            Rectangle b = getBounds().getCopy();
            b.shrink(1, 1);

            int halfHgt = b.height / 2;

            graphics.drawLine(b.x, b.y + halfHgt, b.x + b.width, b.y + halfHgt);

            graphics.drawLine(b.x, b.y + halfHgt, b.x + halfHgt, b.y);
            graphics.drawLine(b.x, b.y + halfHgt, b.x + halfHgt, b.y + b.height);

            graphics.drawLine(b.x + b.width - halfHgt, b.y, b.x + b.width, b.y
                    + halfHgt);
            graphics.drawLine(b.x + b.width - halfHgt, b.y + b.height, b.x
                    + b.width, b.y + halfHgt);

            graphics.popState();
        }

        /**
         * @return the alpha
         */
        public Integer getAlpha() {
            return alpha;
        }

        /**
         * @param alpha
         *            the alpha to set
         */
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }
    }

}
