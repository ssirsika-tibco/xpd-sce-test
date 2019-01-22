/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;
import com.tibco.xpd.resources.ui.util.XPDLineUtilities;

/**
 * Crossover line control for {@link XpdCompareMergeViewer} - draws
 * the connector lines and difference type icons between difference nodes in the
 * left/right views.
 * 
 * @author aallway
 * @since 2 Nov 2010
 */
public class MergeViewerCrossoverControl extends Canvas implements
        PaintListener {

    private XpdCompareMergeViewer mergeViewer = null;

    private List<ConnectorLine> lastPaintedConnectors =
            new ArrayList<ConnectorLine>();

    private String defaultTooltip = null;

    public MergeViewerCrossoverControl(Composite parent) {
        super(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);

        this.addMouseListener(new LineSelectionListener());
        this.addMouseMoveListener(new LineTooltipMouseOverListener());
        this.addMouseWheelListener(new LineMouseWheelListener());
        this.addKeyListener(new LineKeyListener());
    }

    /**
     * Initialise the corssover control (must be called AFTER the left/right
     * viewers of merge viewer have been created.).
     * 
     * @param mergeViewer
     */
    public void initialise(XpdCompareMergeViewer mergeViewer) {
        this.mergeViewer = mergeViewer;

        /*
         * Listen to our own paint events (this will be triggered by window
         * being incovered etc).
         */
        this.addPaintListener(this);

        /*
         * Repaint ourselves when left/right view is painted - this may not be
         * the most efficient way to do things BUT has the advantage of catching
         * all circumstances (like scroll, select, expand etc etc) no matter
         * what
         */
        PaintListener repaintWhenLeftRightPaint = new PaintListener() {

            @Override
            public void paintControl(PaintEvent e) {
                /* Redraw ourselves when left/right is repainted. */
                MergeViewerCrossoverControl.this.redraw();
            }
        };

        mergeViewer.getLeftViewer().getTree()
                .addPaintListener(repaintWhenLeftRightPaint);
        mergeViewer.getRightViewer().getTree()
                .addPaintListener(repaintWhenLeftRightPaint);
    }

    /**
     * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
     * 
     * @param e
     */
    @Override
    public void paintControl(PaintEvent e) {
        Set<TreeItem> alreadyDecorated = new HashSet<TreeItem>();

        /* Cache some info about the connections we have painted. */
        lastPaintedConnectors.clear();

        Rectangle displayBounds = this.getBounds();
        Rectangle bounds =
                new Rectangle(0, 0, displayBounds.width, displayBounds.height);

        /* Save current state. */
        GC gc = e.gc;
        Color oldFGColor = gc.getForeground();
        Color oldBGColor = gc.getBackground();
        int oldAntialias = gc.getAntialias();
        int oldLineWidth = gc.getLineWidth();
        int oldAlpha = gc.getAlpha();

        /*
         * Clear the back ground and insert 2 narrow border lines left and right
         */
        gc.setBackground(this.getBackground());
        gc.setForeground(this.getForeground());
        gc.setAntialias(SWT.ON);

        gc.fillRectangle(bounds);

        gc.setForeground(ColorConstants.titleBackground);
        gc.setLineWidth(1);
        gc.setAlpha(130);
        gc.drawLine(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height);
        gc.drawLine(bounds.x + bounds.width - 1, bounds.y, bounds.x
                + bounds.width - 1, bounds.y + bounds.height);
        gc.setAlpha(255);

        /*
         * Go down the left hand side tree items painting connection lines from
         * them to correxponding item on other side.
         */
        drawConnectors(mergeViewer.getLeftViewer(),
                true,
                alreadyDecorated,
                gc,
                displayBounds,
                bounds);

        drawConnectors(mergeViewer.getRightViewer(),
                false,
                alreadyDecorated,
                gc,
                displayBounds,
                bounds);

        /*
         * Reset the gc state.
         */
        gc.setAlpha(oldAlpha);
        gc.setLineWidth(oldLineWidth);
        gc.setAntialias(oldAntialias);
        gc.setForeground(oldFGColor);
        gc.setBackground(oldBGColor);

        return;
    }

    /**
     * @param contentViewer
     * @param isLeft
     * @param alreadyDecorated
     * @param bounds
     * @param displayBounds
     * @param gc
     */
    private void drawConnectors(MergeContentTreeViewer contentViewer,
            boolean isLeft, Set<TreeItem> alreadyDecorated, GC gc,
            Rectangle displayBounds, Rectangle bounds) {

        Tree tree = contentViewer.getTree();

        if (tree != null && !tree.isDisposed()) {
            TreeItem[] items = tree.getItems();

            if (items != null) {
                List<ConnectorLine> unrenderedSelectedLines =
                        new ArrayList<ConnectorLine>();

                for (TreeItem treeItem : items) {
                    recursiveDrawConnector(treeItem,
                            isLeft,
                            alreadyDecorated,
                            gc,
                            displayBounds,
                            bounds,
                            unrenderedSelectedLines);
                }

                /*
                 * Paint selected lines after unselected ones (so selection box
                 * is drawn OVER lines of unselected items).
                 */
                for (ConnectorLine connectorLine : unrenderedSelectedLines) {
                    renderConnectorLine(gc, isLeft, connectorLine);
                }
            }
        }

        return;
    }

    /**
     * Decorate this tree item if necessary else check it's expanded
     * descendants.
     * 
     * @param treeItem
     * @param isLeft
     * @param alreadyDecorated
     * @param bounds
     * @param displayBounds
     * @param gc
     * @param unrenderedSelectedLines
     */
    private void recursiveDrawConnector(TreeItem treeItem, boolean isLeft,
            Set<TreeItem> alreadyDecorated, GC gc, Rectangle displayBounds,
            Rectangle bounds, List<ConnectorLine> unrenderedSelectedLines) {
        /*
         * We only decorate the loweest leaf of an expanded tree that has a
         * change in.
         */
        if (shouldConnectionDecorateTreeItem(treeItem)) {
            /*
             * Draw this individual connector.
             */
            createConnectorLine(treeItem,
                    isLeft,
                    alreadyDecorated,
                    gc,
                    displayBounds,
                    bounds,
                    unrenderedSelectedLines);

        } else {
            /* If this tree item is expanded then recurs and do descendents. */
            if (treeItem.getExpanded()) {
                TreeItem[] children = treeItem.getItems();

                if (children != null) {
                    for (TreeItem childItem : children) {
                        recursiveDrawConnector(childItem,
                                isLeft,
                                alreadyDecorated,
                                gc,
                                displayBounds,
                                bounds,
                                unrenderedSelectedLines);
                    }
                }
            }
        }

        return;
    }

    /**
     * Create a {@link ConnectorLine} for the given source tree item to the
     * equivalent item in the target tree.
     * <p>
     * Only one connector line is drawn for each pair (this method is called for
     * all items with the left tree as source tree then all the items with the
     * right tree as source tree - in case of items exist on one side and not on
     * other).
     * <p>
     * For selected tree item(s) the {@link ConnectorLine} is only created, it
     * is not rendered. The rendering will be done at the end by
     * {@link #drawConnectors(MergeContentTreeViewer, boolean, Set, GC, Rectangle, Rectangle)}
     * above.
     * 
     * @param sourceTreeItem
     * @param sourceItemIsFromLeft
     * @param alreadyDecorated
     * @param gc
     * @param displayBounds
     * @param bounds
     * @param unrenderedSelectedLines
     */
    private void createConnectorLine(TreeItem sourceTreeItem,
            boolean sourceItemIsFromLeft, Set<TreeItem> alreadyDecorated,
            GC gc, Rectangle displayBounds, Rectangle bounds,
            List<ConnectorLine> unrenderedSelectedLines) {

        if (alreadyDecorated.contains(sourceTreeItem)) {
            /*
             * This tree item has already been connected (as a result of
             * connecting from other side.
             */
            return;
        }
        /* Only need to decorate once. */
        alreadyDecorated.add(sourceTreeItem);

        /*
         * Get the corresponding item on opposite side.
         */
        MergeContentTreeViewer sourceViewer =
                sourceItemIsFromLeft ? mergeViewer.getLeftViewer()
                        : mergeViewer.getRightViewer();
        MergeContentTreeViewer targetViewer =
                sourceItemIsFromLeft ? mergeViewer.getRightViewer()
                        : mergeViewer.getLeftViewer();

        Object sourceElement = sourceTreeItem.getData();

        Object targetElement =
                MergeContentViewerUtil
                        .locateElementOrNearestAncestorInOtherViewer(sourceElement,
                                sourceViewer,
                                targetViewer);

        if (targetElement != null) {

            TreeItem targetTreeItem =
                    (TreeItem) targetViewer.getItem(targetElement);

            if (targetTreeItem != null) {
                /*
                 * Have both ends of the connection so draw a line between -
                 * simple!
                 */
                alreadyDecorated.add(targetTreeItem);

                Rectangle sourceBounds = sourceTreeItem.getBounds();
                Rectangle targetBounds = targetTreeItem.getBounds();

                ConnectorLine connectorLine;
                if (sourceItemIsFromLeft) {
                    connectorLine =
                            new ConnectorLine(0, sourceBounds.y
                                    + (sourceBounds.height / 2), bounds.width,
                                    targetBounds.y + (targetBounds.height / 2),
                                    sourceTreeItem, targetTreeItem,
                                    sourceItemIsFromLeft);

                } else {
                    connectorLine =
                            new ConnectorLine(bounds.width, sourceBounds.y
                                    + (sourceBounds.height / 2), 0,
                                    targetBounds.y + (targetBounds.height / 2),
                                    sourceTreeItem, targetTreeItem,
                                    sourceItemIsFromLeft);

                }

                lastPaintedConnectors.add(connectorLine);

                /*
                 * Only render unselected lines at this point (we want to render
                 * the selected lines last so we will just save them to list and
                 * then paint them afterwards.
                 */
                if (!connectorLine.isSelected()) {
                    renderConnectorLine(gc, sourceItemIsFromLeft, connectorLine);

                } else {
                    unrenderedSelectedLines.add(connectorLine);
                }

                return;
            }
        }

        /* Could not find target. */

        return;
    }

    /**
     * Do the final rendering of the given connector line.
     * 
     * @param gc
     * @param sourceItemIsFromLeft
     * @param connectorLine
     */
    private void renderConnectorLine(GC gc, boolean sourceItemIsFromLeft,
            ConnectorLine connectorLine) {
        if (connectorLine.isSelected()) {
            paintSelection(gc, connectorLine);

        } /* Finished paiting selection */

        /*
         * ====================
         * 
         * Draw the line over the selection.
         * 
         * ====================
         */
        configureGCForConnectorLine(gc, connectorLine.getTargetItem());

        connectorLine.drawLine(gc);

        /*
         * ==========================
         * 
         * Draw icon at appropriate end of the line.
         * 
         * ========================
         */
        addIcons(gc, sourceItemIsFromLeft, connectorLine);
    }

    /**
     * Configure GC to paint given line connector between left and right.
     * 
     * @param gc
     * @param treeItem
     */
    public static void configureGCForConnectorLine(GC gc, TreeItem treeItem) {
        Color color = getChangeTypeColor(treeItem.getData());
        if (color == null) {
            color = ColorConstants.gray;
        }

        boolean isSelected = MergeContentViewerUtil.isSelected(treeItem);

        gc.setForeground(color);
        gc.setAntialias(SWT.ON);
        gc.setLineWidth(2);

        if (isSelected) {
            gc.setAlpha(255);
        } else {
            gc.setAlpha(100);
        }

        return;
    }

    /**
     * Check whether the given tree item should sbe decorated with a merge
     * connection line.
     * 
     * @param treeItem
     * 
     * @return <code>true</code> to decorate.
     */
    public static boolean shouldConnectionDecorateTreeItem(TreeItem treeItem) {
        Color color = getChangeTypeColor(treeItem.getData());

        if (color != null) {

            TreeItem[] children = treeItem.getItems();

            if (!treeItem.getExpanded() || children == null
                    || children.length == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param diffEl
     * @return change type color according to difference kind or null if no
     *         difference can be ascertained.
     */
    public static Color getChangeTypeColor(Object itemData) {
        Color color = null;

        int diffKind =
                MergeContentViewerUtil.getDiffKindFromTreeItemData(itemData);

        int diffDirection = diffKind & Differencer.DIRECTION_MASK;
        int changeType = diffKind & Differencer.CHANGE_TYPE_MASK;

        if (diffDirection == Differencer.CONFLICTING) {
            color = MergeTreeChangeDecorator.conflictColor;

        } else if (changeType == Differencer.ADDITION
                || changeType == Differencer.DELETION) {
            /*
             * Deletion on one side is just an addition from other side - so
             * both the same colour.
             */
            color = MergeTreeChangeDecorator.addedColor;

        } else if (changeType == Differencer.CHANGE) {
            color = MergeTreeChangeDecorator.changedColor;

        }

        return color;
    }

    /**
     * @param gc
     * @param sourceTreeItem
     * @param sourceItemIsFromLeft
     * @param connectorLine
     */
    private void addIcons(GC gc, boolean sourceItemIsFromLeft,
            ConnectorLine connectorLine) {

        /* Difference kinds to display at left and right ends of line. */
        Integer leftDiffKind = null;
        Integer rightDiffKind = null;

        Integer diffKind = null;

        Object srcData = connectorLine.sourceItem.getData();
        if (srcData instanceof XpdCompareNode) {
            diffKind =
                    (Integer) ((XpdCompareNode) srcData)
                            .getProperty(XpdCompareMergeViewer.DIFF_NODE_KIND_PROPERTY);

        } else if (srcData instanceof MissingMergeContentPlaceHolder) {
            diffKind =
                    ((MissingMergeContentPlaceHolder) srcData)
                            .getDifferenceKind();
        }

        Object tgtData = connectorLine.targetItem.getData();
        if (diffKind == null) {
            if (tgtData instanceof XpdCompareNode) {
                diffKind =
                        (Integer) ((XpdCompareNode) tgtData)
                                .getProperty(XpdCompareMergeViewer.DIFF_NODE_KIND_PROPERTY);
            } else if (tgtData instanceof MissingMergeContentPlaceHolder) {
                diffKind =
                        ((MissingMergeContentPlaceHolder) tgtData)
                                .getDifferenceKind();
            }
        }

        if (diffKind == null) {
            return;
        }

        int oldAlpha = gc.getAlpha();
        if (oldAlpha < 255) {
            /*
             * Add some alpha over and above the line alpha when painteding
             * faded out (so line doesn't appear to go thru icon so much.
             */
            gc.setAlpha(oldAlpha + 80);
        }

        if (mergeViewer.isThreeWay()) {
            if ((diffKind & Differencer.DIRECTION_MASK) == Differencer.RIGHT) {

                /*
                 * For some reason the image decorator in CompareConfiguration
                 * seem to cache return the OPPOSITE image for side in dicated
                 * in diffKind for three-way compare. i.e. by default if
                 * diffKind=right+add then it returns the grey arrow with a + in
                 * (which in the top window indicates a addition to the right) -
                 * I think it is caching them in terms of diffKind direction =
                 * direction of arrow NOT direction of change!! :o(
                 * 
                 * So we have to swap the diff direction kind.
                 */
                rightDiffKind =
                        (diffKind & ~Differencer.DIRECTION_MASK)
                                | Differencer.LEFT;

            } else if ((diffKind & Differencer.DIRECTION_MASK) == Differencer.LEFT) {
                leftDiffKind =
                        (diffKind & ~Differencer.DIRECTION_MASK)
                                | Differencer.RIGHT;

            } else if ((diffKind & Differencer.DIRECTION_MASK) == Differencer.CONFLICTING) {
                leftDiffKind = diffKind;
                rightDiffKind = diffKind;
            }

        } else {
            leftDiffKind = diffKind;
        }

        org.eclipse.draw2d.geometry.Point startPoint =
                new org.eclipse.draw2d.geometry.Point(connectorLine.startX,
                        connectorLine.startY);
        org.eclipse.draw2d.geometry.Point endPoint =
                new org.eclipse.draw2d.geometry.Point(connectorLine.endX,
                        connectorLine.endY);

        Image base =
                XpdResourcesUIActivator.getDefault().getImageRegistry()
                        .get(XpdResourcesUIConstants.ICON_BLANK16);

        int itemHeight = connectorLine.sourceItem.getParent().getItemHeight();

        if (leftDiffKind != null) {
            Image img =
                    mergeViewer.getCompareConfiguration().getImage(base,
                            leftDiffKind);
            if (img != null) {
                PointList leftToRight = new PointList();
                if (sourceItemIsFromLeft) {
                    leftToRight.addPoint(startPoint);
                    leftToRight.addPoint(endPoint);
                } else {
                    leftToRight.addPoint(endPoint);
                    leftToRight.addPoint(startPoint);
                }

                org.eclipse.draw2d.geometry.Point pos =
                        XPDLineUtilities
                                .getLinePointFromOffset(leftToRight, 20);

                Rectangle ib = img.getBounds();

                /* Expand trhe standard icon a bit. */
                float hRatio = 1.5f;

                itemHeight = (int) (ib.height * hRatio);
                int iconWidth = (int) (ib.width * hRatio);

                gc.drawImage(img,
                        0,
                        0,
                        ib.width,
                        ib.height,
                        pos.x - (iconWidth / 2),
                        pos.y - (itemHeight / 2),
                        iconWidth,
                        itemHeight);
            }

        }

        if (rightDiffKind != null) {
            Image img =
                    mergeViewer.getCompareConfiguration().getImage(base,
                            rightDiffKind);
            if (img != null) {
                PointList rightToLeft = new PointList();

                if (!sourceItemIsFromLeft) {
                    rightToLeft.addPoint(startPoint);
                    rightToLeft.addPoint(endPoint);

                } else {
                    rightToLeft.addPoint(endPoint);
                    rightToLeft.addPoint(startPoint);
                }

                org.eclipse.draw2d.geometry.Point pos =
                        XPDLineUtilities
                                .getLinePointFromOffset(rightToLeft, 32);

                Rectangle ib = img.getBounds();

                /* Expand trhe standard icon a bit. */
                float hRatio = 1.5f;

                itemHeight = (int) (ib.height * hRatio);
                int iconWidth = (int) (ib.width * hRatio);

                gc.drawImage(img,
                        0,
                        0,
                        ib.width,
                        ib.height,
                        pos.x - (iconWidth / 2),
                        pos.y - (itemHeight / 2),
                        iconWidth,
                        itemHeight);
            }
        }

        gc.setAlpha(oldAlpha);

        return;
    }

    /**
     * @param gc
     * @param sourceBounds
     * @param connectorLine
     */
    private void paintSelection(GC gc, ConnectorLine connectorLine) {
        Color fgColor;
        Color bgColor;

        gc.setAlpha(255);
        gc.setAntialias(SWT.ON);

        if (isFocusControl()) {
            fgColor = ColorConstants.titleBackground;
            bgColor = ColorConstants.titleGradient;
        } else {
            fgColor = ColorConstants.button;
            bgColor = ColorConstants.button;
        }

        int halfHgt = connectorLine.getSourceItem().getBounds().height / 2;

        /*
         * ========================
         * 
         * In order to get the angle of the sleection gradient correct we must
         * get 2 points perpendiculuarly above and below the connector line so
         * that we and say that the upper point is 'start of gradient' and
         * loweer point is end of gradient'. In this way the gradient stays on
         * the axis of the connector line regardless of it's angle.
         * 
         * ===========================
         */
        org.eclipse.draw2d.geometry.Point startPoint =
                new org.eclipse.draw2d.geometry.Point(connectorLine.startX,
                        connectorLine.startY);
        org.eclipse.draw2d.geometry.Point endPoint =
                new org.eclipse.draw2d.geometry.Point(connectorLine.endX,
                        connectorLine.endY);

        PointList pl = new PointList();
        pl.addPoint(startPoint);
        pl.addPoint(endPoint);

        double angle = XPDLineUtilities.getAngleOfLine(startPoint, endPoint);

        double upAngle = (angle - 90) % 360.0f;
        double downAngle = (angle - 270) % 360.0f;

        /*
         * ========================================================== ========
         */

        org.eclipse.draw2d.geometry.Point centrePoint =
                XPDLineUtilities.getLinePointFromPortion(pl, 50);

        org.eclipse.draw2d.geometry.Point startUpPoint =
                XPDLineUtilities.getPointOnLineFromAngle(centrePoint,
                        halfHgt * 2,
                        upAngle);

        org.eclipse.draw2d.geometry.Point startDownPoint =
                XPDLineUtilities.getPointOnLineFromAngle(centrePoint,
                        halfHgt * 2,
                        downAngle);

        /*
         * ========================================================== ========
         */

        org.eclipse.draw2d.geometry.Point endUpPoint =
                XPDLineUtilities.getPointOnLineFromAngle(endPoint,
                        halfHgt * 2,
                        upAngle);

        org.eclipse.draw2d.geometry.Point endDownPoint =
                XPDLineUtilities.getPointOnLineFromAngle(endPoint,
                        halfHgt * 2,
                        downAngle);

        // gc.drawOval(startUpPoint.x - 5, startUpPoint.y - 5, 10,
        // 10);
        // gc.drawOval(startDownPoint.x - 5,
        // startDownPoint.y - 5,
        // 10,
        // 10);
        // gc.drawLine(startUpPoint.x,
        // startUpPoint.y,
        // startDownPoint.x,
        // startDownPoint.y);

        /*
         * ============================================
         * 
         * Draw around line around the selection
         * 
         * ============================================
         */
        gc.setLineWidth(1);
        gc.setForeground(fgColor);

        int[] points =
                new int[] { connectorLine.startX,
                        connectorLine.startY - halfHgt, connectorLine.endX - 1,
                        connectorLine.endY - halfHgt, connectorLine.endX - 1,
                        connectorLine.endY + (halfHgt - 1),
                        connectorLine.startX,
                        connectorLine.startY + (halfHgt - 1) };

        gc.drawPolygon(points);

        /*
         * =======================================================
         * 
         * Create the gradient pattern (for some reason works better using the
         * lowest point when line is on an angle.
         * 
         * =======================================================
         */
        Pattern pattern;
        if (startPoint.y >= endPoint.y) {
            pattern =
                    new Pattern(gc.getDevice(), startUpPoint.x, startUpPoint.y,
                            startDownPoint.x, startDownPoint.y,
                            ColorConstants.white, bgColor);
        } else {
            pattern =
                    new Pattern(gc.getDevice(), endUpPoint.x, endUpPoint.y,
                            endDownPoint.x, endDownPoint.y,
                            ColorConstants.white, bgColor);
        }

        /*
         * ========================================================== ========
         */
        int[] pointFill =
                new int[] { connectorLine.startX + 2,
                        connectorLine.startY - (halfHgt - 2),
                        connectorLine.endX - 2,
                        connectorLine.endY - (halfHgt - 2),
                        connectorLine.endX - 2,
                        connectorLine.endY + (halfHgt - 2),
                        connectorLine.startX + 2,
                        connectorLine.startY + (halfHgt - 2) };

        Pattern oldPatt = gc.getBackgroundPattern();
        gc.setBackgroundPattern(pattern);

        /* Then pattern. */
        gc.fillPolygon(pointFill);

        gc.setBackgroundPattern(oldPatt);
        pattern.dispose();
    }

    /**
     * @param line
     */
    private void selectLine(ConnectorLine line) {
        if (line.sourceItem != null) {
            if (line.sourceItem.getParent() != null) {
                line.sourceItem.getParent().setSelection(line.sourceItem);
            }
        }
        if (line.targetItem != null) {
            if (line.targetItem.getParent() != null) {
                line.targetItem.getParent().setSelection(line.targetItem);
            }
        }
    }

    /**
     * @param mpt
     * @return
     */
    private ConnectorLine getConnectorLineAtPoint(Point mpt) {
        ConnectorLine lineAtPoint = null;

        for (ConnectorLine line : lastPaintedConnectors) {
            if (line.containsPoint(mpt)) {
                lineAtPoint = line;
            }
        }
        return lineAtPoint;
    }

    /**
     * Get the index of the selected connector line.
     * 
     * @return
     */
    private int getSelection() {
        List<ConnectorLine> selectedLines = new ArrayList<ConnectorLine>();

        for (int i = 0; i < lastPaintedConnectors.size(); i++) {
            ConnectorLine connectorLine = lastPaintedConnectors.get(i);
            if (connectorLine.isSelected()) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Set the tooltip to use when a connector line is not under mouse.
     * 
     * @param defaultTooltip
     */
    public void setDefaultTooltip(String defaultTooltip) {
        this.defaultTooltip = defaultTooltip;
    }

    /**
     * Set selection in trees when line is clicked.
     * 
     * @author aallway
     * @since 5 Nov 2010
     */
    private final class LineSelectionListener extends MouseAdapter {
        /**
         * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
         * 
         * @param e
         */
        @Override
        public void mouseDown(MouseEvent e) {
            Point mpt = new Point(e.x, e.y);

            ConnectorLine lineAtPoint = getConnectorLineAtPoint(mpt);

            if (lineAtPoint != null) {
                selectLine(lineAtPoint);
            }

            MergeViewerCrossoverControl.this.setFocus();
        }

    }

    /**
     * Allow the user to cursor down thru the connector lines.
     * 
     * 
     * @author aallway
     * @since 19 Nov 2010
     */
    private class LineKeyListener implements KeyListener {

        /**
         * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
         * 
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int selection = getSelection();
            int newSelection = -1;

            ConnectorLine selectedLine = null;
            MergeContentTreeViewer sourceViewer = null;
            MergeContentTreeViewer targetViewer = null;
            if (selection >= 0 && selection < lastPaintedConnectors.size()) {
                selectedLine = lastPaintedConnectors.get(selection);

                sourceViewer =
                        selectedLine.isSourceFromLeftViewer() ? mergeViewer
                                .getLeftViewer() : mergeViewer.getRightViewer();
                targetViewer =
                        selectedLine.isSourceFromLeftViewer() ? mergeViewer
                                .getRightViewer() : mergeViewer.getLeftViewer();

            }

            if (e.keyCode == SWT.ARROW_DOWN) {
                newSelection = selection + 1;
            } else if (e.keyCode == SWT.ARROW_UP) {
                newSelection = selection - 1;
            } else if (e.keyCode == SWT.HOME) {
                newSelection = 0;
            } else if (e.keyCode == SWT.END) {
                newSelection = lastPaintedConnectors.size() - 1;
            }

            if (newSelection >= 0
                    && newSelection < lastPaintedConnectors.size()) {
                selectLine(lastPaintedConnectors.get(newSelection));
            }

            return;
        }

        /**
         * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
         * 
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * Listens for over a connector line and sets up the tooltip.
     * 
     * 
     * @author aallway
     * @since 22 Nov 2010
     */
    private class LineTooltipMouseOverListener implements MouseMoveListener {

        /**
         * @see org.eclipse.swt.events.MouseMoveListener#mouseMove(org.eclipse.swt.events.MouseEvent)
         * 
         * @param e
         */
        @Override
        public void mouseMove(MouseEvent e) {
            Point mpt = new Point(e.x, e.y);

            ConnectorLine lineAtPoint = getConnectorLineAtPoint(mpt);

            String tooltip = defaultTooltip;

            if (lineAtPoint != null) {
                tooltip = lineAtPoint.getTooltip();
                setCursor(Cursors.HAND);
            } else {
                setCursor(Cursors.ARROW);
            }

            String curr = getToolTipText();
            if ((curr == null) != (tooltip == null)
                    || (tooltip != null && !tooltip.equals(curr))) {
                setToolTipText(tooltip);
            }
        }
    }

    /**
     * Represents an individual connection line between left and right tree
     * items.
     * 
     * 
     * @author aallway
     * @since 5 Nov 2010
     */
    public static class ConnectorLine {
        private int startX, startY, endX, endY;

        private TreeItem sourceItem, targetItem;

        private boolean sourceIsFromLeftViewer;

        private int sourceDiffKind = 0;

        private int targetDiffKind = 0;

        /**
         * @param startX
         * @param startY
         * @param endX
         * @param endY
         */
        ConnectorLine(int startX, int startY, int endX, int endY,
                TreeItem sourceItem, TreeItem targetItem,
                boolean sourceIsFromLeftViewer) {
            super();
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.sourceItem = sourceItem;
            this.targetItem = targetItem;
            this.sourceIsFromLeftViewer = sourceIsFromLeftViewer;

            if (sourceItem != null) {
                sourceDiffKind =
                        MergeContentViewerUtil
                                .getDiffKindFromTreeItemData(sourceItem
                                        .getData());
            }
            if (targetItem != null) {
                targetDiffKind =
                        MergeContentViewerUtil
                                .getDiffKindFromTreeItemData(targetItem
                                        .getData());
            }
        }

        /**
         * @return The tooltip appropriate to this connector line.
         */
        public String getTooltip() {

            String leftNodeDesc = ""; //$NON-NLS-1$
            String rightNodeDesc = ""; //$NON-NLS-1$

            /*
             * DIfference kind is stored the same on source / target items (if
             * they exist, which is why we user the first that is defined)
             */
            int diffKind = 0;

            if (sourceDiffKind != 0) {
                diffKind = sourceDiffKind;
            } else if (targetDiffKind != 0) {
                diffKind = targetDiffKind;
            }

            if (sourceIsFromLeftViewer) {
                if (sourceItem != null) {
                    leftNodeDesc = sourceItem.getText();
                }
                if (targetItem != null) {
                    rightNodeDesc = targetItem.getText();
                }
            } else {
                if (sourceItem != null) {
                    rightNodeDesc = sourceItem.getText();
                }
                if (targetItem != null) {
                    leftNodeDesc = targetItem.getText();
                }
            }

            return MergeContentViewerUtil.getToolTip(diffKind,
                    leftNodeDesc,
                    rightNodeDesc);
        }

        /**
         * @return the sourceDiffKind
         */
        public int getSourceDiffKind() {
            return sourceDiffKind;
        }

        /**
         * @return the targetDiffKind
         */
        public int getTargetDiffKind() {
            return targetDiffKind;
        }

        /**
         * @return the sourceIsFromLeftViewer
         */
        public boolean isSourceFromLeftViewer() {
            return sourceIsFromLeftViewer;
        }

        public boolean isSelected() {
            if (sourceItem != null) {
                return MergeContentViewerUtil.isSelected(sourceItem);
            }

            if (targetItem != null) {
                return MergeContentViewerUtil.isSelected(targetItem);
            }

            return false;
        }

        /**
         * @return the sourceItem
         */
        public TreeItem getSourceItem() {
            return sourceItem;
        }

        /**
         * @return the targetItem
         */
        public TreeItem getTargetItem() {
            return targetItem;
        }

        /**
         * Draw the line
         * 
         * @param gc
         */
        private void drawLine(GC gc) {
            gc.drawLine(startX, startY, endX, endY);
        }

        /**
         * Is the given point within 6 pixels of the line
         * 
         * @param pt
         * @return
         */
        public boolean containsPoint(Point pt) {
            Point lpt =
                    XPDLineUtilities.getLinePointClosestToPoint(new Point(
                            startX, startY), new Point(endX, endY), pt);

            if (XPDLineUtilities.getLineLength(pt, lpt) <= 6) {
                return true;
            }

            return false;
        }

    }

    /**
     * SCroll left/right viewers up / down on mouse wheel move
     * 
     * 
     * @author aallway
     * @since 22 Nov 2010
     */
    private class LineMouseWheelListener implements MouseWheelListener {

        /**
         * @see org.eclipse.swt.events.MouseWheelListener#mouseScrolled(org.eclipse.swt.events.MouseEvent)
         * 
         * @param e
         */
        @Override
        public void mouseScrolled(MouseEvent e) {

            if (e.count >= 0) {
                /*
                 * Scroll down using left viewer, at least this is consistent
                 * for the user (even if there are more items in right viewer.
                 * benchmark.
                 */
                mergeViewer.getLeftViewer().scrollDown();
                mergeViewer.getSynchScrollPosListener()
                        .synchTopItem(mergeViewer.getLeftViewer());
            } else {
                /*
                 * Scroll up using left viewer, at least this is consistent for
                 * the user (even if there are more items in right viewer.
                 * benchmark.
                 */
                mergeViewer.getLeftViewer().scrollUp();
                mergeViewer.getSynchScrollPosListener()
                        .synchTopItem(mergeViewer.getLeftViewer());

            }

            return;
        }

    }

}
