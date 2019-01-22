/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.ui.misc;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Nominally, SWT {@link Tree} control does not support tool-tips for individual
 * {@link TreeItem}'s. This class allows you to simply implement a method that
 * returns the tooltip for a given TreeItem and then it will set the tooltip for
 * the whole Tree according to the TreeItem that is actually under the mouse at
 * any given time.
 * 
 * @author aallway
 * @since 3.3 (21 Dec 2009)
 */
public abstract class AbstractTreeItemToolTipProvider implements
        MouseMoveListener {

    protected Tree tree;

    /**
     * Add this tooltip provider to the given tree.
     */
    public AbstractTreeItemToolTipProvider(Tree tree) {
        this.tree = tree;
        tree.addMouseMoveListener(this);
    }

    /**
     * Return the tooltip text for the given tree item.
     * 
     * @param treeItem
     * 
     * @return The tooltip text for the given tree item or null if none
     *         available.
     */
    protected abstract String getToolTipText(TreeItem treeItem);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.MouseMoveListener#mouseMove(org.eclipse.swt.events
     * .MouseEvent)
     */
    public final void mouseMove(MouseEvent e) {
        if (tree != null && !tree.isDisposed()) {
            String tooltip = null;

            TreeItem item =
                    tree.getItem(new org.eclipse.swt.graphics.Point(e.x, e.y));

            if (item != null) {
                tooltip = getToolTipText(item);
            }

            if (tooltip == null) {
                tooltip = ""; //$NON-NLS-1$
            }

            if (!tooltip.equals(tree.getToolTipText())) {
                tree.setToolTipText(tooltip);
            }
        }
        return;
    }

}
