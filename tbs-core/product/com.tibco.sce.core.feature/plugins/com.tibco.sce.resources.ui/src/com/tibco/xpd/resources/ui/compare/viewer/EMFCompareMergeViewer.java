/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.internal.EMFCompareNodeSortComparator;
import com.tibco.xpd.resources.ui.compare.viewer.internal.EMFRecordingCommandContentMerger;

/**
 * Content merge viewer (left/right/ancestor views) for EMF compare node tree
 * merging. Sub-class and contribute to
 * <code>org.eclipse.compare.contentMergeViewers</code> to provide these views
 * for you model.
 * <p>
 * In the standard compare editor each double click on the top-window tree
 * resets the input on the bottom 'merge compare view' (the left/right
 * comparison windows).
 * <p>
 * The {@link EMFCompareMergeViewer} detects the initial setting of the input
 * (from a local file / file revision) and asks the sub-class for an
 * {@link EMFCompareStructureCreator}-based structure creator that is specific
 * to the model in question {@link #createStructureCreator()}.
 * <p>
 * This is used to create {@link WorkingCopyCompareNode}-based nodes specific to
 * the model in question. The sub-class should return the same structure creator
 * as is used by their {@link EMFCompareDiffViewer}-based (compare editor top
 * window) difference viewer.
 * <p>
 * The working copies created for the 2 models compared in the top view ARE
 * SEPARATE from the working copies created for the same two models in the left
 * and right viewes here in this merge viewer.
 * <p>
 * When the user subsequently double-clicks around the top window, we will be
 * passed elements from ITS {@link WorkingCopyCompareNode} tree (which will be
 * different from ours though built from the same resource/revision) this viewer
 * will resolve the top window selected node into the appropriate node within
 * it's tree's.
 * 
 * @author aallway
 * @since 1 Oct 2010
 */
public abstract class EMFCompareMergeViewer extends XpdCompareMergeViewer
        implements PropertyChangeListener {

    /*
     * These are the working copy compare node's that we have created for the
     * input.
     */
    private boolean firstInputSet = true;

    private WorkingCopyCompareNode leftWorkingCopyNode = null;

    private WorkingCopyCompareNode rightWorkingCopyNode = null;

    private WorkingCopyCompareNode ancestorWorkingCopyNode = null;

    protected EMFCompareStructureCreator compareSructureCreator;

    private ICompareInputChangeListener compareInputChangeListener;

    private ICompareInput listeningToCompareInput = null;

    private boolean alreadyInCompareInputChanged = false;

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
    public final static boolean USE_GLOBAL_WORKING_COPIES = false;

    /**
     * Parameters are as passed to {@link IViewerCreator}
     * 
     * @param parent
     * @param style
     * @param cc
     */
    public EMFCompareMergeViewer(Composite parent, int style,
            CompareConfiguration cc) {
        super(parent, style, cc);

    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#init()
     * 
     */
    @Override
    protected void init() {
        /*
         * Create the structure creator to use for creating
         * WorkingCopCompaeNode's from input.
         * 
         * And Tell it NOT to use the actual local resource working copy (same
         * as for standard xpd resource editors) for editable resource inputs.
         */
        compareSructureCreator = createStructureCreator();
        compareSructureCreator
                .setUseLocalResourceWorkingCopyForEditable(USE_GLOBAL_WORKING_COPIES);

        compareInputChangeListener = new ICompareInputChangeListener() {

            @Override
            public void compareInputChanged(ICompareInput source) {
                /*
                 * Compare input changed is fire when local resource is saved or
                 * changed outside editor.
                 * 
                 * Throw away our current working copies and reset input so as
                 * to reallocate them.
                 */
                if (!alreadyInCompareInputChanged) {
                    /* A little protection in case we re-enter for some reason. */
                    alreadyInCompareInputChanged = true;

                    try {

                        // System.out.println("CLEANING UP ON INPUT CHANGE.");
                        cleanUpWorkingCopyNodes();

                        if (listeningToCompareInput != null) {
                            listeningToCompareInput
                                    .removeCompareInputChangeListener(this);
                            listeningToCompareInput = null;
                        }

                        /* Force refresh. */
                        setInput(null);
                        setInput(source);
                    } finally {
                        alreadyInCompareInputChanged = false;
                    }
                }
            }
        };

        return;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#createControls(org.eclipse.swt.widgets.Composite)
     * 
     * @param composite
     */
    @Override
    protected void createControls(Composite composite) {

        super.createControls(composite);

        /* USe the same tree sorter as top compare window */
        EMFCompareNodeSortComparator comparator =
                new EMFCompareNodeSortComparator();

        getAncestorViewer().setComparator(comparator);

        getLeftViewer().setComparator(comparator);
        getRightViewer().setComparator(comparator);

    }

    private void updateActions() {
        // TODO - need to handle UNDO / REDO actions.
    }

    /**
     * Create the structure creator that is to be used for creating the content
     * from file input streams.
     * <p>
     * This should be the same as the structure creator contributed to the
     * StructureDiffViewer (or org.eclipse.comparestructureCreators extension
     * point for your model.
     * 
     * @return the structure creator.
     */
    protected abstract EMFCompareStructureCreator createStructureCreator();

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#doUpdateContent(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param ancestor
     * @param left
     * @param right
     */
    @Override
    protected void doUpdateContent(Object ancestor, Object left, Object right) {

        /*
         * First time in, locate the working copy nodes that should be at the
         * top of the XpdCompareNode tree input (for
         * EMFCompareNodeContentMergeViewer's anyhow).
         */
        if (firstInputSet) {
            createWorkingCopyCompareNodes(ancestor, left, right);
        }

        /*
         * When the user double-clicks items in top window of compare editor we
         * get our input set. and this method gets called with ancestor, left
         * and right objects from the top window (StrcutureDiffViewer's)
         * content.
         * 
         * This content will be from SEPARATE working copies not OURS - so we
         * must find the equivalent in our XpdCompareNode tree to the given
         * input.
         * 
         * For each of ancestor, left and right we must therefore find its path
         * from its WorkingCopyCompareNode and then find the equivalent path
         * from ours.
         * 
         * We should be able to use whichever of the nodes is non-null as they
         * all have to be 'equivalent' in terms of matching nodes in different
         * models.
         */
        Object matchNode = ancestor;
        if (matchNode == null) {
            matchNode = left;
            if (matchNode == null) {
                matchNode = right;
            }
        }

        Object ancestorInOurTree = getAncestorMergeNodeFromDiffNode(matchNode);
        getAncestorViewer().setInput(getInput(), ancestorInOurTree);

        /* And then the left input. */
        Object leftInOurTree = getLeftMergeNodeFromDiffNode(matchNode);
        getLeftViewer().setInput(getInput(), leftInOurTree);

        /* And then the right input. */
        Object rightInOurTree = getRightMergeNodeFromDiffNode(matchNode);
        getRightViewer().setInput(getInput(), rightInOurTree);

        /*
         * For the merge title label provider to show correct status we need to
         * store the difference kind in the 'alternative' copies of input
         * elements. This property is normally added by content provider for
         * children beut we need to add it to the main input elements for title
         * area purposes.
         */
        if (getInput() instanceof IDiffElement) {
            IDiffElement diffElement = (IDiffElement) getInput();

            if (ancestorInOurTree instanceof XpdCompareNode) {
                ((XpdCompareNode) ancestorInOurTree)
                        .setProperty(DIFF_NODE_KIND_PROPERTY,
                                diffElement.getKind());
            }
            if (leftInOurTree instanceof XpdCompareNode) {
                ((XpdCompareNode) leftInOurTree)
                        .setProperty(DIFF_NODE_KIND_PROPERTY,
                                diffElement.getKind());
            }
            if (rightInOurTree instanceof XpdCompareNode) {
                ((XpdCompareNode) rightInOurTree)
                        .setProperty(DIFF_NODE_KIND_PROPERTY,
                                diffElement.getKind());
            }

        }

        updateActions();

        return;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#getLeftContent()
     * 
     * @return
     */
    @Override
    public Object getLeftContent(Object input) {
        /*
         * Match the ICompareInput element (which ever is set) to the
         * corresponding element in our content tree. DOesn't matter which
         * left/right/ancestor from our input we match against because they are
         * all 'equivalent' in terms of locating in tree.
         */
        if (input instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) getInput();
            if (null != compareInput) {
                Object matchNode = compareInput.getLeft();
                if (matchNode == null) {
                    matchNode = compareInput.getAncestor();
                    if (matchNode == null) {
                        matchNode = compareInput.getRight();
                    }
                }

                if (matchNode != null) {
                    return getLeftMergeNodeFromDiffNode(matchNode);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#getRightContent()
     * 
     * @return
     */
    @Override
    public Object getRightContent(Object input) {
        /*
         * Match the ICompareInput element (which ever is set) to the
         * corresponding element in our content tree. DOesn't matter which
         * left/right/ancestor from our input we match against becaus ethey are
         * all 'equivalent in terms of locating in tree.
         */
        if (input instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) getInput();
            if (null != compareInput) {
                Object matchNode = compareInput.getRight();
                if (matchNode == null) {
                    matchNode = compareInput.getAncestor();
                    if (matchNode == null) {
                        matchNode = compareInput.getLeft();
                    }
                }

                if (matchNode != null) {
                    return getRightMergeNodeFromDiffNode(matchNode);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#getAncestorContent()
     * 
     * @return
     */
    @Override
    public Object getAncestorContent(Object input) {
        /*
         * Match the ICompareInput element (which ever is set) to the
         * corresponding element in our content tree. DOesn't matter which
         * left/right/ancestor from our input we match against because they are
         * all 'equivalent in terms of locating in tree.
         */
        if (input instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) getInput();
            if (null != compareInput) {
                Object matchNode = compareInput.getAncestor();
                if (matchNode == null) {
                    matchNode = compareInput.getLeft();
                    if (matchNode == null) {
                        matchNode = compareInput.getRight();
                    }
                }

                if (matchNode != null) {
                    return getAncestorMergeNodeFromDiffNode(matchNode);
                }
            }
        }
        return null;
    }

    /**
     * Get ancestor view merge tree node that is the equivalent of the input
     * (which contains nodes from a different copy of model).
     * 
     * @param ancestor
     * 
     * @return ancestor hand merge tree view node equivalent to ancestor hand
     *         side of our input.
     */
    public Object getAncestorMergeNodeFromDiffNode(Object ancestor) {
        return getMergeNodeFromDiffNode(ancestor, ancestorWorkingCopyNode);
    }

    /**
     * Get left hand view merge tree node that is the equivalent of the input
     * (which contains nodes from a different copy of model).
     * 
     * @param left
     * 
     * @return left hand merge tree view node equivalent to left hand side of
     *         our input.
     */
    public Object getLeftMergeNodeFromDiffNode(Object left) {
        return getMergeNodeFromDiffNode(left, leftWorkingCopyNode);
    }

    /**
     * Get right hand view merge tree node that is the equivalent of the input
     * (which contains nodes from a different copy of model).
     * 
     * @param right
     * 
     * @return Right hand merge tree view node equivalent to right hand side of
     *         our input.
     */
    public Object getRightMergeNodeFromDiffNode(Object right) {
        return getMergeNodeFromDiffNode(right, rightWorkingCopyNode);
    }

    /**
     * We override this so we can redirect the selection from input
     * {@link ICompareInput} to the actual content model in our trees.
     * 
     * @see com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer#initialSelection(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param ancestor
     * @param left
     * @param right
     */
    @Override
    protected void initialSelection(Object ancestor, Object left, Object right) {
        Object ancestorInOurTree = getAncestorMergeNodeFromDiffNode(ancestor);
        getAncestorViewer()
                .setSelection(ancestorInOurTree != null ? new StructuredSelection(
                        ancestorInOurTree) : null,
                        true);

        Object leftInOurTree = getLeftMergeNodeFromDiffNode(left);
        getLeftViewer()
                .setSelection(leftInOurTree != null ? new StructuredSelection(
                        leftInOurTree) : null,
                        true);

        Object rightInOurTree = getRightMergeNodeFromDiffNode(right);
        getRightViewer()
                .setSelection(rightInOurTree != null ? new StructuredSelection(
                        rightInOurTree) : null,
                        true);
    }

    /**
     * We want to use DIFFERENT working copies that teh diff viewer (top window
     * of compare editor) so that it does not change when we change the model my
     * mergeing right/left left/right etc.
     * <p>
     * ON first set input that we are given an input it _should_ be the file /
     * file revision input. Create our working copy compare node's from this.
     * <p>
     * When user dbl-clicks item in top window of compare editor updateContent()
     * will be called with content from the top window's structure so we just
     * need to find the equivalent objects in our working copy compare node
     * tree.
     * 
     * @param ancestor
     * @param left
     * @param right
     */
    private void createWorkingCopyCompareNodes(Object ancestor, Object left,
            Object right) {

        if (left instanceof IStreamContentAccessor) {
            // System.out.println("LOADED LEFT");
            firstInputSet = false;

            leftWorkingCopyNode =
                    compareSructureCreator
                            .getWorkingCopyCompareNodeStructure(left);

            if (leftWorkingCopyNode != null) {
                leftWorkingCopyNode.addListener(this);

                /*
                 * Set our initial dirty state from the working copy's (in case
                 * it is for a local resoruce's actual xpd working copy.
                 */
                setLeftDirty(leftWorkingCopyNode.getWorkingCopy()
                        .isWorkingCopyDirty());
            }
        }

        if (right instanceof IStreamContentAccessor) {
            firstInputSet = false;

            rightWorkingCopyNode =
                    compareSructureCreator
                            .getWorkingCopyCompareNodeStructure(right);

            if (rightWorkingCopyNode != null) {
                rightWorkingCopyNode.addListener(this);

                /*
                 * Set our initial dirty state from the working copy's (in case
                 * it is for a local resoruce's actual xpd working copy.
                 */
                setRightDirty(rightWorkingCopyNode.getWorkingCopy()
                        .isWorkingCopyDirty());
            }
        }

        if (ancestor instanceof IStreamContentAccessor) {
            firstInputSet = false;

            ancestorWorkingCopyNode =
                    compareSructureCreator
                            .getWorkingCopyCompareNodeStructure(ancestor);

            if (ancestorWorkingCopyNode != null) {
                ancestorWorkingCopyNode.addListener(this);
            }
        }

        /*
         * Listen for compare input changing so we can throw away our working
         * copies when that happens (when user saves it will cause the local
         * file resources etc to be reloaded in top window diff viewer so we
         * should follow suit.
         */
        if (!firstInputSet) {
            if (getInput() instanceof ICompareInput) {
                listeningToCompareInput = (ICompareInput) getInput();

                listeningToCompareInput
                        .addCompareInputChangeListener(compareInputChangeListener);
            }
        }

        return;
    }

    /**
     * Copy changes from one side to another.
     * 
     * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#copy(boolean)
     * 
     * @param leftToRight
     */
    @Override
    protected void copy(boolean leftToRight) {
        // EMFCompareNodeContentMerger compareNodeContentMerger =
        // new EMFCompareNodeContentMerger(this);

        EMFRecordingCommandContentMerger compareNodeContentMerger =
                new EMFRecordingCommandContentMerger(this);

        /*
         * Replace left with copy of right or visa-versa
         */
        compareNodeContentMerger.copy(leftToRight);

        return;
    }

    /**
     * We ignore getContents() which is only used by flushContent() (which is
     * called on save of editor). This is because we handle flushContent
     * differently.
     * 
     * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#getContents(boolean)
     * 
     * @param left
     * @return null
     */
    @Override
    protected byte[] getContents(boolean left) {
        /*
         * We ignore getContents() which is only used by flushContent() (which
         * is called on save of editor). This is because we handle flushContent
         * differently.
         */
        throw new RuntimeException(
                "Not expecting getContents() to be called by super-class."); //$NON-NLS-1$
    }

    /**
     * We override flush content so that the working copy used to flush the EMF
     * model to the file.
     * 
     * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#flushContent(java.lang.Object,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param input
     * @param monitor
     */
    @Override
    protected void flushContent(Object input, IProgressMonitor monitor) {

        /* Get root input - should be a working copy compare node. */
        if (isLeftDirty()) {
            leftWorkingCopyNode.save(monitor);
            setLeftDirty(false);
        }

        /* Get root input - should be a working copy compare node. */
        if (isRightDirty()) {
            rightWorkingCopyNode.save(monitor);
            setRightDirty(false);
        }

        return;
    }

    /**
     * doSave() is <b>only</b> utilised by super class when the input changes
     * NOT when the user explicitly saves.
     * <p>
     * In our case we do NOT want to save just because the user selects
     * (dbl-clicks) a different object in the top window.
     * <p>
     * Instead we will only save in the (should be impossible) case that the
     * root of the new input left side / right side is not the equivalent of the
     * root of the current left/right side input.
     * 
     * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#doSave(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param newInput
     * @param oldInput
     * 
     * @return true if save actually performed because root input changed.
     */
    @Override
    protected boolean doSave(Object newInput, Object oldInput) {
        if (oldInput != null) {
            boolean sameLeftRootInput = true;
            boolean sameRightRootInput = true;

            if (newInput instanceof ICompareInput
                    && oldInput instanceof ICompareInput) {
                ICompareInput newCompareInput = (ICompareInput) newInput;
                ICompareInput oldCompareInput = (ICompareInput) oldInput;

                /*
                 * Check whether the new input is within the original input tree
                 * (allowing new / old input to be null because there is
                 * sometimes not equivalence.
                 */
                Object newRootLeft = getTreeRoot(newCompareInput.getLeft());
                Object oldRootLeft = getTreeRoot(oldCompareInput.getLeft());

                Object newRootRight = getTreeRoot(newCompareInput.getRight());
                Object oldRootRight = getTreeRoot(oldCompareInput.getRight());

                /* If either are null then treat as 'same as current' */
                if (newRootLeft != null && oldRootLeft != null) {
                    if (!newRootLeft.equals(oldRootLeft)) {
                        sameLeftRootInput = false;
                    }
                }

                /* If either are null then treat as 'same as current' */
                if (newRootRight != null && oldRootRight != null) {
                    if (!newRootRight.equals(oldRootRight)) {
                        sameLeftRootInput = false;
                    }
                }
            }

            if (!sameLeftRootInput || !sameRightRootInput) {
                /*
                 * looks like the root of the inputs has changed best ask user
                 * to save.
                 */
                cleanUpWorkingCopyNodes();

                return super.doSave(newInput, oldInput);
            }
        }
        /*
         * The actual input is not changing so don't need to save!
         * 
         * Returning null should prevent super class from unsetting dirty flag
         * on either side.
         */
        return false;
    }

    /**
     * Working copy property change listener (for listening to
     * {@link WorkingCopyCompareNode} ancestor of current input and detecting
     * the file becoming dirty / non-dirty.
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * 
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        boolean changed =
                WorkingCopy.CHANGED.equals(evt.getPropertyName())
                        || WorkingCopy.PROP_RELOADED.equals(evt
                                .getPropertyName())
                        || WorkingCopy.PROP_REMOVED.equals(evt
                                .getPropertyName());

        if (changed) {

            /*
             * If we refresh this whole merge content viewer then it will end up
             * calling updateCOntent() and our doUpdateCOntent() will re-find
             * the equivalent of the currently selected nodes in modified
             * working copy model of each tree..
             */
            if (!getControl().isDisposed()) {
                this.refresh();
            }

        } else if (WorkingCopy.PROP_DIRTY.equals(evt.getPropertyName())) {
            /*
             * Find out which side we got notification from and set it's dirty
             * status accordingly.
             */
            if (!getControl().isDisposed()) {
                Boolean dirty = (Boolean) evt.getNewValue();

                if (evt.getSource() == leftWorkingCopyNode) {
                    setLeftDirty(dirty);

                } else if (evt.getSource() == rightWorkingCopyNode) {
                    setRightDirty(dirty);
                }
            }
        }

        updateActions();

        return;
    }

    /**
     * The input objects that the compare editor sets upon this viewer (see
     * {@link #updateContent(Object, Object, Object)} when the user dbl-clicks
     * top window are the DiffNode's containing left/right content references to
     * the EMF models created for THAT window.
     * <p>
     * These are a DIFFERENT working copy of the model use for our
     * left/right/ancestor we are comparing (this is so that our changes will
     * not effect the top window).
     * <p>
     * Therefore we often need to resolve a given object from the top window
     * into an object in one of our (ancestor, left or right) windows
     * <p>
     * This method helps to do that be calculating the path from the given
     * inputObject to it's ancestor {@link WorkingCopyCompareNode} and then
     * searching down from the given (our {@link WorkingCopyCompareNode} for OUR
     * tree node that matches the given inputObject
     * 
     * @param inputObject
     * 
     * @return equivalent node in given ourWorkingCopyCompareNodeInput tree or
     *         <code>null</code> if cannot be found.
     */
    private Object getMergeNodeFromDiffNode(Object inputObject,
            WorkingCopyCompareNode ourWorkingCopyCompareNodeInput) {
        Object nodeInOurTree = null;

        List<Object> path = getInputNodePathFromWorkingCopyNode(inputObject);

        if (path != null) {
            nodeInOurTree =
                    locateInputTreeNode(ourWorkingCopyCompareNodeInput, path);
        }

        return nodeInOurTree;
    }

    /**
     * Traverse up thru tree and return the path of objects (as a list) on the
     * tree until the working copy node is found.
     * 
     * @param element
     * @return list of children below the ancestor working copy node that lead
     *         to (and include) the given element or <code>null</code> if there
     *         is no ancestor WorkingCopyCompareNode.
     */
    private List<Object> getInputNodePathFromWorkingCopyNode(Object element) {
        List<Object> path = new ArrayList<Object>();

        while (element != null) {
            if (element instanceof WorkingCopyCompareNode) {
                break;
            } else if (!(element instanceof IChildCompareNode)) {
                break;
            }

            path.add(element);
            element = ((IChildCompareNode) element).getParent();
        }

        if (!(element instanceof WorkingCopyCompareNode)) {
            return null;
        }

        /* Reverse the list so that it's in descending order. */
        Collections.reverse(path);
        return path;
    }

    /**
     * @return the leftWorkingCopyNode
     */
    public WorkingCopyCompareNode getLeftWorkingCopyNode() {
        return leftWorkingCopyNode;
    }

    /**
     * @return the rightWorkingCopyNode
     */
    public WorkingCopyCompareNode getRightWorkingCopyNode() {
        return rightWorkingCopyNode;
    }

    /**
     * @return the ancestorWorkingCopyNode
     */
    public WorkingCopyCompareNode getAncestorWorkingCopyNode() {
        return ancestorWorkingCopyNode;
    }

    /**
     * @param currentListeningLeftCompareNode2
     * @param path
     * 
     * @return Element that exists on given path from given
     *         {@link WorkingCopyCompareNode}.
     */
    private ITypedElement locateInputTreeNode(
            WorkingCopyCompareNode workingCopyCompareNode, List<Object> path) {

        if (workingCopyCompareNode != null) {
            Object currentTreeNode = workingCopyCompareNode;

            int pathIdx = 0;

            while (pathIdx < path.size()
                    && currentTreeNode instanceof IStructureComparator) {
                /* Find next element in path in children of curretnTreeNode */
                Object[] children =
                        ((IStructureComparator) currentTreeNode).getChildren();
                if (children == null || children.length == 0) {
                    break;
                }

                Object toFind = path.get(pathIdx);
                boolean found = false;
                for (Object child : children) {
                    if (toFind.equals(child)) {
                        found = true;
                        currentTreeNode = child;
                        break;
                    }
                }

                if (!found) {
                    break;
                }

                /*
                 * Ok we've found next element in path in this level of tree. Do
                 * next level.
                 */
                pathIdx++;

            }

            if (pathIdx >= path.size()
                    && currentTreeNode instanceof ITypedElement) {
                /* must have found it, cos found all items leading up to it. */
                return (ITypedElement) currentTreeNode;
            }

        }

        return null;
    }

    /**
     * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
     * 
     * @param event
     */
    @Override
    protected void handleDispose(DisposeEvent event) {
        if (listeningToCompareInput != null) {
            listeningToCompareInput
                    .removeCompareInputChangeListener(compareInputChangeListener);
            listeningToCompareInput = null;
        }
        cleanUpWorkingCopyNodes();

        super.handleDispose(event);
        return;
    }

    /**
     * Clean up working copy compare nodes created for this viewer. Remove any
     * working copy listeners we may have and dispose the nodes.
     */
    private void cleanUpWorkingCopyNodes() {
        if (ancestorWorkingCopyNode != null) {
            ancestorWorkingCopyNode.removeListener(this);
            ancestorWorkingCopyNode.dispose();
            ancestorWorkingCopyNode = null;
        }
        if (leftWorkingCopyNode != null) {
            leftWorkingCopyNode.removeListener(this);
            leftWorkingCopyNode.dispose();
            leftWorkingCopyNode = null;
        }
        if (rightWorkingCopyNode != null) {
            rightWorkingCopyNode.removeListener(this);
            rightWorkingCopyNode.dispose();
            rightWorkingCopyNode = null;
        }

        firstInputSet = true;

        return;
    }

}
