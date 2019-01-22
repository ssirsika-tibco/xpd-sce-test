/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;

/**
 * Syncrhonise selection between the owner viewer and the passed list of
 * viewers.
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class SynchSelectionListener implements ISelectionChangedListener {

    private Viewer[] synchViewers;

    private boolean ignoreEvents = false;

    /**
     * @param synchViewers
     */
    public SynchSelectionListener(Viewer[] synchViewers) {
        super();
        this.synchViewers = synchViewers;

    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     * 
     * @param event
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        if (!ignoreEvents) {
            try {
                ignoreEvents = true;

                /* Build path to source object. */
                if (event.getSource() instanceof AbstractTreeViewer
                        && event.getSelection() instanceof StructuredSelection) {
                    AbstractTreeViewer sourceViewer =
                            (AbstractTreeViewer) event.getSource();

                    StructuredSelection ss =
                            (StructuredSelection) event.getSelection();

                    Object element = null;
                    if (ss.getFirstElement() != null) {
                        element = ss.getFirstElement();
                    }

                    for (Viewer viewer : synchViewers) {
                        if (viewer.getControl() != null
                                && !viewer.getControl().isDisposed()) {
                            if (viewer != sourceViewer
                                    && viewer instanceof AbstractTreeViewer) {
                                AbstractTreeViewer targetViewer =
                                        (AbstractTreeViewer) viewer;

                                Object targetElement = null;
                                if (element != null) {
                                    targetElement =
                                            MergeContentViewerUtil
                                                    .locateElementOrNearestAncestorInOtherViewer(element,
                                                            sourceViewer,
                                                            targetViewer);
                                }

                                if (targetElement != null) {
                                    targetViewer
                                            .setSelection(new StructuredSelection(
                                                    targetElement),
                                                    true);
                                } else {
                                    targetViewer.setSelection(null, true);
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
