/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import org.eclipse.jface.viewers.TreeViewer;

import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;

/**
 * Delete operation base class. This is implemented by delete operations.
 * 
 * @author njpatel
 * 
 */
public abstract class DeleteOperation extends FragmentElementOperation {

    /**
     * Delete operation base class
     * 
     * @param label
     *            operation label.
     * @param context
     *            the fragments operation context
     */
    public DeleteOperation(String label, IFragmentElement context) {
        super(label, context);
    }

    /**
     * Get index of the given child item in the parent category. This will take
     * care of the sort order when getting the index.
     * 
     * @param viewer
     *            fragment tree viewer
     * @param parent
     *            parent category
     * @param item
     *            child object to get index of
     * @return index of item or -1 if item not found.
     */
    protected int getIndexof(IFragmentCategory parent, Object item) {
        TreeViewer viewer = getViewer();
        int idx = -1;
        boolean found = false;

        if (viewer != null && parent != null && item != null) {
            Object[] children = parent.getChildren().toArray();
            FragmentsManager.getInstance().getFragmentsViewerComparator().sort(
                    viewer, children);

            for (Object child : children) {
                ++idx;
                if (found = child.equals(item)) {
                    break;
                }
            }

            if (!found) {
                idx = -1;
            }
        }

        return idx;
    }

    /**
     * Reselect the item at the given index after the delete operation. If no
     * item is found at the index then attempt will be made to select the
     * previous sibling; Failing that the parent will be selected.
     * 
     * @param parent
     * @param indexToSelect
     */
    protected void reselectItem(final FragmentCategoryImpl parent,
            final int indexToSelect) {

        final TreeViewer viewer = getViewer();
        // Update the selection in the fragment viewer after update
        if (viewer != null && !viewer.getTree().isDisposed()) {
            viewer.getTree().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    IFragmentElement toSelect = parent;
                    int idx = indexToSelect;
                    if (idx >= 0) {
                        Object[] children = parent.getChildren().toArray();

                        if (children.length > 0) {
                            FragmentsManager.getInstance()
                                    .getFragmentsViewerComparator().sort(
                                            viewer, children);
                            if (idx >= children.length) {
                                idx = children.length - 1;
                            }
                            toSelect = (IFragmentElement) children[idx];
                        }
                    }

                    refreshAndSelectInFragmentView(parent, toSelect);
                }

            });
        }
    }

    /**
     * Get the fragment tree viewer.
     * 
     * @return tree viewer if accessible, <code>null</code> otherwise.
     */
    protected TreeViewer getViewer() {
        return FragmentsManager.getInstance().getFragmentsViewPart() != null ? FragmentsManager
                .getInstance().getFragmentsViewPart().getTreeViewer()
                : null;
    }
}
