/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectInternalPropertiesNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.internal.AtomicDiffNodesOnlyFilter;
import com.tibco.xpd.resources.ui.compare.viewer.internal.EMFCompareNodeSortComparator;

/**
 * Specialisation of the {@link StructureDiffViewer} (used for top window of
 * compare editor). The sub-class provides an {@link EMFCompareStructureCreator}
 * that wraps up their EMF model content in {@link EMFCompareNode}'s.
 * <p>
 * Sub-class and contribute to
 * <code>org.eclipse.compare.structureMergeViewers</code> (via a factory that
 * creates this viewer (via an IViewerCreator which is what actually must be
 * contributed in reality).
 * <p>
 * You can also create contribute the left/right merge viewer part of the
 * compare editor using the same {@link EMFCompareStructureCreator} for your
 * model by sub-classing {@link EMFCompareMergeViewer}.
 * 
 * @author aallway
 * @since 12 Oct 2010
 */
public abstract class EMFCompareDiffViewer extends StructureDiffViewer
        implements PropertyChangeListener {

    private EMFCompareStructureCreator structureCreator;

    /**
     * EXPERIMENTAL - this tag governs whether we are using THE global working
     * copy for editable side of compares or ALWAYS using temporary stream based
     * working copy and tagging it as editable.
     * <p>
     * Both of these has pro's and cons. However I currently think that the
     * 'con' of the fact that during the merger of 2 models the user could be
     * making the model something completely invalid for the normal editors and
     * hecne shouldn't chnage the global 'live working copy'
     * 
     */
    public final static boolean USE_GLOBAL_WORKING_COPIES =
            EMFCompareMergeViewer.USE_GLOBAL_WORKING_COPIES;

    /**
     * @param parent
     * @param configuration
     */
    public EMFCompareDiffViewer(Composite parent,
            CompareConfiguration configuration,
            EMFCompareStructureCreator structureCreator) {

        super(parent, configuration);

        this.structureCreator = structureCreator;
        structureCreator.addListener(this);

        setComparator(new EMFCompareNodeSortComparator());

        setFilters(new ViewerFilter[] { new AtomicDiffNodesOnlyFilter() });

        structureCreator
                .setUseLocalResourceWorkingCopyForEditable(USE_GLOBAL_WORKING_COPIES);

        setStructureCreator(structureCreator);

        this.setComparator(new EMFCompareNodeSortComparator());
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.StructureDiffViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
     * 
     * @param event
     */
    @Override
    protected void handleDispose(DisposeEvent event) {
        structureCreator.removeListener(this);
        super.handleDispose(event);
    }

    /**
     * @see org.eclipse.jface.viewers.AbstractTreeViewer#expandToLevel(int)
     * 
     * @param level
     */
    @Override
    public void expandToLevel(int level) {
        /*
         * StructureDiffViewer does some arbitrary expandToLevel's that mess
         * things up for us (we don't want arbitrary depth of expansion.
         * 
         * Ignoring expandToLevel() allows diff() & autoExpandCompareNodes() to
         * function correctly.
         */
        return;
    }

    /**
     * @see org.eclipse.jface.viewers.AbstractTreeViewer#setAutoExpandLevel(int)
     * 
     * @param level
     */
    @Override
    public void setAutoExpandLevel(int level) {
        /*
         * StructureDiffViewer does some arbitrary expandToLevel's that mess
         * things up for us (we don't want arbitrary depth of expansion.
         * 
         * Ignoring expandToLevel() allows diff() & autoExpandCompareNodes() to
         * function correctly.
         */
        return;
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.StructureDiffViewer#diff()
     * 
     */
    @Override
    protected void diff(IProgressMonitor monitor) {
        super.diff(monitor);

        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                try {
                    getTree().setRedraw(false);
                    autoExpandCompareNodes(getRoot());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    getTree().setRedraw(true);

                }
            }
        });

    }

    /**
     * Expand each tree to the required level (as indicated by sub-class's
     * choice of what should {@link #showInitially(Object)})
     * 
     * @param node
     */
    private boolean autoExpandCompareNodes(Object node) {
        boolean expanded = false;

        if (getContentProvider() instanceof ITreeContentProvider) {
            ITreeContentProvider scp =
                    (ITreeContentProvider) getContentProvider();

            Object[] children = scp.getChildren(node);

            if (children != null && children.length > 0) {
                for (Object object : children) {
                    if (autoExpandCompareNodes(object)) {
                        expanded = true;
                    }
                }
            }

            /*
             * No need to expand this object if children are expanded.
             */
            if (!expanded) {

                if (node instanceof ICompareInput) {
                    ICompareInput ci = (ICompareInput) node;

                    if (internalShowInitially(ci.getLeft())
                            || internalShowInitially(ci.getRight())) {
                        /* Expand to show this object. */
                        expandToLevel(node, 0);
                        expanded = true;
                    }

                } else if (internalShowInitially(node)) {
                    /* Expand to show this object. */
                    expandToLevel(node, 0);
                    expanded = true;
                }
            }
        }

        return expanded;
    }

    /**
     * @param node
     * 
     * @return true if the given object should be expanded-to after initial
     *         creation of diff viewer.
     */
    protected boolean internalShowInitially(Object node) {
        /*
         * Don't show internal properties by default.
         */
        if (!(node instanceof EObjectInternalPropertiesNode)) {

            if (node instanceof XpdCompareNode
                    && "$_$_MATCH_ANY_NODE_LOCATION_!_!"
                            .equals(((XpdCompareNode) node)
                                    .getLocationInParent())) {
                return true;
            }
            return showInitially(node);
        }
        return false;
    }

    /**
     * Sub-class should decide whether it wants the given node to be visible
     * auto initial selection. The parent tree will be expanded to show this
     * item.
     * 
     * @param node
     * 
     * @return <code>true</code> to have this object auto expand.
     */
    protected abstract boolean showInitially(Object node);

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * 
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (EMFCompareStructureCreator.WORKINGCOPY_NODE_CREATED.equals(evt
                .getPropertyName())) {
            /*
             * When working copy node is created by structure creator start
             * listening to it.
             */
            if (evt.getNewValue() instanceof WorkingCopyCompareNode) {
                ((WorkingCopyCompareNode) evt.getNewValue()).addListener(this);
            }

        } else if (evt.getSource() instanceof WorkingCopyCompareNode) {
            if (WorkingCopyCompareNode.WORKINGCOPY_NODE_DISPOSED.equals(evt
                    .getPropertyName())) {
                /* When working copy is disposed then stop listening to it. */
                ((WorkingCopyCompareNode) evt.getSource()).removeListener(this);

            } else {

                /*
                 * If we are using the global working copy (the one used by
                 * normal editors for the model then we should refresh ourself
                 * (re-perform the difference) if the working copy is changed
                 * OUTSIDE of this editor.
                 * 
                 * If we aren't using global working copy then never re-diff
                 */
                if (USE_GLOBAL_WORKING_COPIES) {
                    boolean changed =
                            WorkingCopy.CHANGED.equals(evt.getPropertyName())
                                    || WorkingCopy.PROP_RELOADED.equals(evt
                                            .getPropertyName())
                                    || WorkingCopy.PROP_REMOVED.equals(evt
                                            .getPropertyName());

                    if (changed) {
                        /*
                         * Working copy node will have disposed it's children so
                         * all we need toi do is re-diff which is what
                         * compareInputChanged does.
                         * 
                         * BUT only if the change was not made by the userf
                         * performing a copy right/left (in which case we don't
                         * want to re-diff else the objects the user just merged
                         * WOULD DISAppear FROM TREE BECAUSE THEY won't be
                         * different anymore.
                         */
                        if (!getTree().isDisposed()) {
                            if (((WorkingCopyCompareNode) evt.getSource())
                                    .getWorkingCopy()
                                    .getAttributes()
                                    .get(WorkingCopyCompareNode.WORKINGCOPY_PROPERTY_CHANGE_MADE_BY_COMPARE_EDITOR) == null) {

                                this.diff();
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @see org.eclipse.jface.viewers.StructuredViewer#handlePostSelect(org.eclipse.swt.events.SelectionEvent)
     * 
     * @param e
     */
    @Override
    protected void handlePostSelect(SelectionEvent e) {
        /*
         * Normally merge viewer contents is only updated on double-click of the
         * DiffViewer.
         * 
         * Currently we think it is better to do the same on single-select.
         */
        super.handlePostSelect(e);

        super.fireOpen(new OpenEvent(this, getSelection()));
    }

}
