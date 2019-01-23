/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * Syncrhonise expansion state between the owner viewer and the passed list of
 * viewers.
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class SynchExpandListener implements ITreeViewerListener {

    private Viewer[] synchViewers;

    private boolean ignoreEvents = false;

    /**
     * @param synchViewers
     */
    public SynchExpandListener(Viewer[] synchViewers) {
        super();
        this.synchViewers = synchViewers;

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        findAndExpand(event, false);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        findAndExpand(event, true);
    }

    private void findAndExpand(TreeExpansionEvent event, boolean expand) {
        if (!ignoreEvents) {
            try {
                ignoreEvents = true;

                AbstractTreeViewer sourceViewer = event.getTreeViewer();

                Object element = event.getElement();

                for (Viewer viewer : synchViewers) {
                    if (viewer.getControl() != null
                            && !viewer.getControl().isDisposed()) {

                        if (viewer != sourceViewer
                                && viewer instanceof AbstractTreeViewer) {
                            AbstractTreeViewer targetViewer =
                                    (TreeViewer) viewer;

                            Object elementOrAncestor =
                                    MergeContentViewerUtil
                                            .locateElementOrNearestAncestorInOtherViewer(element,
                                                    sourceViewer,
                                                    targetViewer);

                            if (elementOrAncestor != null) {
                                if (expand) {
                                    targetViewer
                                            .expandToLevel(elementOrAncestor, 1);
                                } else {
                                    targetViewer
                                            .collapseToLevel(elementOrAncestor,
                                                    1);
                                }
                            }

                        }
                    }

                }
            } finally {
                ignoreEvents = false;
            }
        }
        return;
    }

    /**
     * @return the ignoreEvents
     */
    public boolean isIgnoreEvents() {
        return ignoreEvents;
    }

    /**
     * @param ignoreEvents
     *            the ignoreEvents to set
     */
    public void setIgnoreEvents(boolean ignoreEvents) {
        this.ignoreEvents = ignoreEvents;
    }
}
