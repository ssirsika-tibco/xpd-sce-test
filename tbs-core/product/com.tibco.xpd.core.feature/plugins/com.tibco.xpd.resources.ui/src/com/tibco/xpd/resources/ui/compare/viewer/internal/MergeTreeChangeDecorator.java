/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;

/**
 * Decorates the left/right merge content tree views to highlight content
 * differences.
 * 
 * 
 * @author aallway
 * @since 1 Nov 2010
 */
public class MergeTreeChangeDecorator implements Listener {

    public static final Color changedColor = new Color(null, 84, 141, 212);

    public static final Color addedColor = new Color(null, 80, 208, 137);

    public static final Color conflictColor = new Color(null, 212, 84, 84);

    private MergeContentTreeViewer mergeContentViewer;

    /**
     * @param mergeViewer
     */
    public MergeTreeChangeDecorator(MergeContentTreeViewer mergeContentViewer) {
        this.mergeContentViewer = mergeContentViewer;

        mergeContentViewer.getTree().addListener(SWT.PaintItem, this);
    }

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * 
     * @param event
     */
    @Override
    public void handleEvent(Event event) {
        if (event.type == SWT.PaintItem && event.item instanceof TreeItem) {
            decorateItem(event.gc, (TreeItem) event.item);
        }

        return;
    }

    /**
     * @param gc
     * @param treeItem
     */
    private void decorateItem(GC gc, TreeItem treeItem) {
        Tree tree = treeItem.getParent();

        Rectangle treeBounds = tree.getBounds();
        Rectangle itemBounds = treeItem.getBounds();

        Rectangle imageBounds = treeItem.getImageBounds(0);
        if (imageBounds == null) {
            imageBounds = itemBounds;
        }

        Rectangle textBounds = treeItem.getTextBounds(0);
        if (textBounds == null) {
            textBounds = itemBounds;
        }

        if (treeItem.getData() instanceof MissingMergeContentPlaceHolder) {
            /* Use nearest sibling for size for missing place holder item. */
            TreeItem sizeItem = getSiblingTreeItem(treeItem);

            imageBounds.width = sizeItem.getImageBounds(0).width;
            textBounds.width = sizeItem.getTextBounds(0).width;

        }

        /* Push */
        Color oldFgColor = gc.getForeground();
        int oldAntiAlias = gc.getAntialias();
        int oldAlpha = gc.getAlpha();
        int oldLineWidth = gc.getLineWidth();

        if (treeItem.getData() instanceof MergeContentInfoNodesSeparatorLine) {
            int y = itemBounds.y + (itemBounds.height / 4);

            gc.setForeground(ColorConstants.titleBackground);

            int maxItemWidth =
                    tree.getHorizontalBar().getMaximum() + treeBounds.width;

            gc.setLineWidth(1);
            gc.drawLine(imageBounds.x, y, maxItemWidth - 5, y);

        } else if (!MergeContentViewerType.ANCESTOR.equals(mergeContentViewer
                .getViewerType())) {
            /*
             * We decorate only changed unexpanded nodes in left/right trees
             * (becuase a descendent will be changed) or if there are no
             * children we will see it it needs decorating.
             * 
             * If expanded then it is enough to see decoration on effected
             * descendent
             */
            if (MergeViewerCrossoverControl
                    .shouldConnectionDecorateTreeItem(treeItem)) {

                paintItemJoinerLine(treeItem,
                        treeBounds,
                        itemBounds,
                        textBounds,
                        imageBounds,
                        gc);
            }
        }

        /* Pop */
        gc.setLineWidth(oldLineWidth);
        gc.setAlpha(oldAlpha);
        gc.setAntialias(oldAntiAlias);
        gc.setForeground(oldFgColor);

        return;
    }

    /**
     * @param treeItem
     * @param treeBounds
     * @param itemBounds
     * @param textBounds
     * @param imageBounds
     * @param gc
     */
    private void paintItemJoinerLine(TreeItem treeItem, Rectangle treeBounds,
            Rectangle itemBounds, Rectangle textBounds, Rectangle imageBounds,
            GC gc) {
        int radius = (textBounds.height / 2) - 2;

        MergeViewerCrossoverControl.configureGCForConnectorLine(gc, treeItem);

        if (MergeContentViewerType.LEFT.equals(mergeContentViewer
                .getViewerType())) {
            /**
             * Paint for left viewer.
             */
            int centreX = textBounds.x + textBounds.width + (radius / 2);
            int centreY = itemBounds.y + (itemBounds.height / 2);

            if (true) {
                gc.drawArc(centreX - radius,
                        centreY - radius,
                        (radius * 2),
                        (radius * 2),
                        290,
                        140);
            }
            gc.drawLine(centreX + radius, centreY, treeBounds.width, centreY);

        } else if (MergeContentViewerType.RIGHT.equals(mergeContentViewer
                .getViewerType())) {
            /**
             * Paint for right viewer.
             */
            int centreX = imageBounds.x - 14;
            int centreY = itemBounds.y + (itemBounds.height / 2);

            if (true) {
                gc.drawArc(centreX - radius,
                        centreY - radius,
                        (radius * 2),
                        (radius * 2),
                        270,
                        -180);
            }

            gc.drawLine(centreX - radius, centreY, 0, centreY);
        }

        return;
    }

    /**
     * Get the nearest sibling item in list (preferring previous item.
     * <p>
     * This is so that we can attempt to draw connector line for a missing node
     * placeholder to a sensible length as compare with surroundings
     * 
     * @param treeItem
     * @return nearest sibling or item itself if has no siblings.
     */
    private TreeItem getSiblingTreeItem(TreeItem treeItem) {
        TreeItem[] items;

        TreeItem parentItem = treeItem.getParentItem();

        if (parentItem != null) {
            items = parentItem.getItems();
        } else {
            items = treeItem.getParent().getItems();
        }

        TreeItem nearestSibling = null;

        int i = 0;
        for (; i < items.length; i++) {
            TreeItem item = items[i];

            if (item == treeItem) {
                break;
            }

            if (!(item.getData() instanceof MissingMergeContentPlaceHolder)
                    && !(item.getData() instanceof MergeContentInfoNodesSeparatorLine)
                    && !(item.getData() instanceof XpdPropertyInfoNode)) {
                nearestSibling = item;
            }
        }

        if (nearestSibling != null) {
            return nearestSibling;
        }

        for (i += 1; i < items.length; i++) {
            TreeItem item = items[i];

            if (!(item.getData() instanceof MissingMergeContentPlaceHolder)
                    && !(item.getData() instanceof MergeContentInfoNodesSeparatorLine)
                    && !(item.getData() instanceof XpdPropertyInfoNode)) {
                return item;
            }
        }

        return treeItem;

    }
}