/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Synchronise scroll position between the owner viewer and the passed list of
 * viewers.
 * <p>
 * It is not enough to just listern to the scroll bar because when the scroll
 * position changes because the user causes a scroll by cursoring thru the view
 * then the scroll bar moves but does not notify thru the SelectionListeners
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class SynchScrollPosListener implements SelectionListener, KeyListener {

    private TreeViewer[] synchViewers;

    private boolean ignoreEvents = false;

    /**
     * @param synchViewers
     */
    public SynchScrollPosListener(TreeViewer[] synchViewers) {
        super();
        this.synchViewers = synchViewers;

        for (TreeViewer synchViewer : synchViewers) {
            synchViewer.getTree().getVerticalBar().addSelectionListener(this);
            synchViewer.getTree().addKeyListener(this);
        }
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     * 
     * @param e
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        /*
         * Make sure we ignore reciprocating scroll events from setting the top
         * items of other viewers (because they will be listening to this
         * viewer!
         */
        ScrollBar scrollbar = (ScrollBar) e.widget;
        if (scrollbar.getParent() instanceof Tree) {
            Tree sourceTree = (Tree) scrollbar.getParent();

            TreeViewer sourceViewer = getViewer(sourceTree);
            synchTopItem(sourceViewer);
        }
        return;
    }

    /**
     * @param sourceViewer
     */
    public void synchTopItem(TreeViewer sourceViewer) {
        if (!ignoreEvents) {
            try {
                ignoreEvents = true;
                TreeItem sourceTopItem = sourceViewer.getTree().getTopItem();

                if (sourceTopItem != null) {

                    if (sourceViewer != null) {
                        for (TreeViewer synchViewer : synchViewers) {

                            if (synchViewer != sourceViewer) {
                                /*
                                 * Find the matching visible item or the nearest
                                 * common ancestor in the other viewer and set
                                 * it as the top item of other viewer.
                                 */
                                TreeItem targetTopItem =
                                        MergeContentViewerUtil
                                                .locateElementOrNearestVisibleAncestor(sourceTopItem,
                                                        sourceViewer,
                                                        synchViewer);
                                if (targetTopItem != null) {
                                    synchViewer.getTree()
                                            .setTopItem(targetTopItem);
                                }
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                System.err.println(ex);
            } finally {
                ignoreEvents = false;
            }
        }
    }

    /**
     * @param sourceTree
     * 
     * @return viewer in synch viewers that sourceTree belongs to
     */
    private TreeViewer getViewer(Tree sourceTree) {
        TreeViewer hostViewer = null;

        for (TreeViewer treeViewer : synchViewers) {

            if (treeViewer.getTree() == sourceTree) {
                hostViewer = treeViewer;
                break;
            }
        }
        return hostViewer;
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

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     * 
     * @param e
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {

    }

    /**
     * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
     * 
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        /* Synch top items when up/down/pgup/pgdown/home/end pressed. */
        switch (e.keyCode) {
        case SWT.ARROW_LEFT:
        case SWT.ARROW_RIGHT:
        case SWT.ARROW_UP:
        case SWT.ARROW_DOWN:
        case SWT.PAGE_UP:
        case SWT.PAGE_DOWN:
        case SWT.HOME:
        case SWT.END:
            if (e.widget instanceof Tree) {
                TreeViewer viewer = getViewer((Tree) e.widget);
                if (viewer != null) {
                    synchTopItem(viewer);
                }
            }
            break;
        }
    }

    /**
     * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
     * 
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        /* Synch top items when up/down/pgup/pgdown/home/end pressed. */
        switch (e.keyCode) {
        case SWT.ARROW_LEFT:
        case SWT.ARROW_RIGHT:
        case SWT.ARROW_UP:
        case SWT.ARROW_DOWN:
        case SWT.PAGE_UP:
        case SWT.PAGE_DOWN:
        case SWT.HOME:
        case SWT.END:
            if (e.widget instanceof Tree) {
                TreeViewer viewer = getViewer((Tree) e.widget);
                if (viewer != null) {
                    synchTopItem(viewer);
                }
            }
            break;
        }
    }

}
