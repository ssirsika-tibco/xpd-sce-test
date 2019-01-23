/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * Tree viewer for one side (left/right/ancestor) of a compare merge viewer
 * control
 * <p>
 * {@link #setInput(ICompareInput, Object)} <b>must be used</b> in preference to
 * {@link #setInput(Object)}
 * 
 * @author aallway
 * @since 1 Oct 2010
 */
public class MergeContentTreeViewer extends TreeViewer {

    private CompareConfiguration compareConfiguration;

    private MergeContentViewerType viewerType;

    private MergeContentLabelProvider labelProvider;

    private ITreeContentProvider contentProvider;

    /**
     * Construct tree viewer for one side (left/right/ancestor) of a compare
     * merge viewer
     * <p>
     * {@link #setInput(ICompareInput, Object)} <b>must be used</b> in
     * preference to {@link #setInput(Object)}
     * 
     * @param parent
     * @param style
     * @param compareConfiguration
     * @param viewerType
     * @param showOnlyDifferences
     * @param showNonAtomicDifferences
     */
    public MergeContentTreeViewer(Composite parent, int style,
            CompareConfiguration compareConfiguration,
            MergeContentViewerType viewerType, boolean showOnlyDifferences,
            boolean showNonAtomicDifferences) {
        super(parent, style);
        this.compareConfiguration = compareConfiguration;

        this.viewerType = viewerType;

        contentProvider = new MergeContentTreeContentProvider(viewerType);

        setContentProvider(contentProvider);

        labelProvider =
                new MergeContentLabelProvider(compareConfiguration, viewerType);
        setLabelProvider(labelProvider);
    }

    /**
     * @return the viewerType
     */
    public MergeContentViewerType getViewerType() {
        return viewerType;
    }

    /**
     * This method <b>must be used</b> in preference to
     * {@link #setInput(Object)}
     * <p>
     * The merge content content provider for each side of merge
     * (left/right/ancestor) needs to know BOTH the actual input (which is the
     * top of the tree of data it is displaying) AND the ICompareInput of the
     * merge content viewer as a whole.
     * <p>
     * The mergeContentInput should be an ICompareInput which references the
     * left, right and ancestor object of the model in the TOP compare overview
     * window.
     * <p>
     * The contentSideInput is the content input for the left, right or ancestor
     * viewer and will represent the top of a tree that represents the WHOLE
     * model for that side of the compare input (not just the differences.)
     * <p>
     * The contentSideInput in the left/right/ancestor view content is likely to
     * be from A different physical model instance than the top
     * mergeContentInput (from top compare overview window) referenced by the
     * mergeCOntentInput HOWEVER we can use the mergeContentInput to define
     * which parts of the contentSideInput should be included in tree
     * (IDiffContainer for tree content interface, IDiffElement for get
     * left/right/ancestor).
     * 
     * @param mergeContentInput
     * @param contentSideInput
     */
    public void setInput(Object mergeContentInput, Object contentSideInput) {
        if (contentProvider instanceof MergeContentTreeContentProvider) {
            ((MergeContentTreeContentProvider) contentProvider)
                    .setMergeContentInput(mergeContentInput);
        }

        super.setInput(contentSideInput);
    }

    /**
     * Expose findItem (not surte if we will need this)
     * 
     * @param element
     * 
     * @return TreeItem representing given content object
     */
    public Widget getItem(Object element) {
        return findItem(element);
    }

    /**
     * Scroll tree up by one item (sets the top item to the item below the
     * current item.
     */
    public void scrollUp() {

        TreeItem topItem = getTree().getTopItem();

        if (topItem != null) {
            TreeItem nextItem = getNextItem(topItem, true);

            if (nextItem != null) {
                getTree().setTopItem(nextItem);
            }
        }
    }

    /**
     * Scroll tree down by one item (sets the top item to the item above the
     * current item.
     */
    public void scrollDown() {
        TreeItem topItem = getTree().getTopItem();

        if (topItem != null) {
            TreeItem previousItem = getPreviousItem(topItem);

            if (previousItem != null) {
                getTree().setTopItem(previousItem);
            }
        }
    }

    protected TreeItem getPreviousItem(TreeItem item) {
        TreeItem[] siblings;

        TreeItem parent = item.getParentItem();
        if (parent == null) {
            siblings = item.getParent().getItems();
        } else {
            siblings = parent.getItems();
        }

        if (siblings != null) {
            int i = siblings.length - 1;
            for (; i >= 0; i--) {
                if (siblings[i] == item) {
                    break;
                }
            }

            if (i > 0) {
                return getLastExpandedDescendent(siblings[i - 1]);
            }

            /*
             * Item previous to first item in set of children is the parent if
             * it's un.
             */
            if (item.getParentItem() != null) {
                return item.getParentItem();
            }
        }

        return null;
    }

    /**
     * @param treeItem
     * 
     * @return last expanded descended of given item (or item itself if it is
     *         not expanded).
     */
    private TreeItem getLastExpandedDescendent(TreeItem treeItem) {
        if (!treeItem.getExpanded() || treeItem.getItemCount() < 1) {
            return treeItem;
        }

        /*
         * Get the item's children get the last object and return it or IT's
         * last expanded descendent.
         */
        return getLastExpandedDescendent(treeItem.getItem(treeItem
                .getItemCount() - 1));
    }

    private TreeItem getNextItem(TreeItem item, boolean includeChildren) {
        if (item == null) {
            return null;
        }

        if (includeChildren && getExpanded(item)) {
            TreeItem[] children = item.getItems();
            if (children != null && children.length > 0) {
                return children[0];
            }
        }

        // next item is either next sibling or next sibling of first
        // parent that has a next sibling.
        TreeItem[] siblings;

        TreeItem parent = item.getParentItem();
        if (parent == null) {
            siblings = item.getParent().getItems();
        } else {
            siblings = parent.getItems();
        }

        if (siblings != null) {
            for (int i = 0; i < siblings.length; i++) {
                if (siblings[i] == item && i < (siblings.length - 1)) {
                    return siblings[i + 1];
                }
            }
        }

        if (parent != null) {
            return getNextItem(parent, false);
        }

        return null;
    }
}
