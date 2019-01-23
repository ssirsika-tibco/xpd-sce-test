/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.misc;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.custom.BusyIndicator;

/**
 * Automatically checks/unchecks all children of checked element and updates
 * checked/grayed states of all parents elements in the hierarchy.
 * <p>
 * <i>Created: 14 Jan 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class AutoTreeCheckStateListener implements ICheckStateListener {

    private final CheckboxTreeViewer treeViewer;

    /**
     * The constructor.
     * 
     * @param treeViewer
     *            viewer for to which this listener is going to be added.
     *            Provided tree viewer should have already assigned content
     *            provider.
     * @throws IllegalStateException
     *             if viewer doesn't have associated conten't provider.
     */
    public AutoTreeCheckStateListener(CheckboxTreeViewer treeViewer) {
        // assert that content provider was set for treeViewer
        if (treeViewer.getContentProvider() == null) {
            throw new IllegalStateException(
                    "Content provider have to be set before attaching this listener."); //$NON-NLS-1$
        }
        this.treeViewer = treeViewer;
    }

    /**
     * Item was checked/unchecked.
     * 
     * @param event
     *            CheckStateChangedEvent
     */
    public void checkStateChanged(final CheckStateChangedEvent event) {
        // Potentially long operation - show a busy cursor
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                new Runnable() {
                    public void run() {
                        treeItemChecked(event.getElement(), event.getChecked());
                    }
                });
    }

    /**
     * Callback that's invoked when the checked status of an item in the tree is
     * changed by the user.
     */
    private void treeItemChecked(Object treeElement, boolean state) {
        // recursively adjust all child tree elements appropriately
        if (state) {
            treeViewer
                    .expandToLevel(treeElement, CheckboxTreeViewer.ALL_LEVELS);
        }
        treeViewer.setSubtreeChecked(treeElement, state);

        // An element that is explicitly checked by user cannot be grayed
        // 'semi-checked' itself.
        treeViewer.setGrayed(treeElement, false);

        Object parent = getContentProvider().getParent(treeElement);
        if (parent == null) {
            return;
        }

        // now update upwards in the tree hierarchy
        if (state) {
            grayCheckHierarchy(parent);
        } else {
            ungrayUncheckHierarchy(parent);
        }
    }

    /**
     * Logically gray-check all ancestors of treeItem by ensuring that they
     * appear in the checked table
     */
    private void grayCheckHierarchy(Object treeElement) {
        if (!areAllChildrenChecked(treeElement)) {
            // if this tree element is already gray then its ancestors all are
            // as well
            if (treeViewer.getChecked(treeElement)
                    && treeViewer.getGrayed(treeElement)) {
                return;
            }
            treeViewer.setGrayChecked(treeElement, true);
        } else {
            treeViewer.setGrayed(treeElement, false);
            treeViewer.setChecked(treeElement, true);
        }
        Object parent = getContentProvider().getParent(treeElement);
        if (parent != null) {
            grayCheckHierarchy(parent);
        }
    }

    /**
     * @return
     */
    private ITreeContentProvider getContentProvider() {
        return ((ITreeContentProvider) treeViewer.getContentProvider());
    }

    /**
     * Logically un-gray-uncheck all ancestors of treeItem if appropriate.
     */
    private void ungrayUncheckHierarchy(Object treeElement) {
        boolean isExactlyOneChildUncheckedOrGrayed =
                isExactlyOneChildCUncheckedOrGrayed(treeElement);
        boolean oneOfChildrenCheckedOrGrayed =
                isOneOfChildrenCheckedOrGrayed(treeElement);
        if (oneOfChildrenCheckedOrGrayed && !isExactlyOneChildUncheckedOrGrayed) {
            return;
        }

        if (!oneOfChildrenCheckedOrGrayed) {
            treeViewer.setGrayed(treeElement, false);
            treeViewer.setChecked(treeElement, false);
        } else if (isExactlyOneChildUncheckedOrGrayed) {
            treeViewer.setGrayChecked(treeElement, true);
        }
        Object parent = getContentProvider().getParent(treeElement);
        if (parent != null) {
            ungrayUncheckHierarchy(parent);
        }
    }

    private boolean areAllChildrenChecked(Object treeElement) {
        Object[] children = getContentProvider().getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!treeViewer.getChecked(children[i])
                    || treeViewer.getGrayed(children[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isOneOfChildrenCheckedOrGrayed(Object treeElement) {
        Object[] children = getContentProvider().getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (treeViewer.getChecked(children[i])
                    || treeViewer.getGrayed(children[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isExactlyOneChildCUncheckedOrGrayed(Object treeElement) {
        int index = 0;
        Object[] children = getContentProvider().getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!treeViewer.getChecked(children[i])
                    || (treeViewer.getGrayed(children[i]) && treeViewer
                            .getChecked(children[i]))) {
                index++;
            }
        }
        return (index == 1);
    }
}
