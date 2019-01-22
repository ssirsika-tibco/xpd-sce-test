/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer;

/**
 * Currently this class simply exists to remove some weight of code from the
 * {@link EMFCompareMergeViewer} when dealing with
 * "copy from one side to the other".
 * 
 * @author aallway
 * @since 15 Oct 2010
 */
public class EMFRecordingCommandContentMerger {
    private EMFCompareMergeViewer mergeViewer;

    Logger logger = XpdResourcesUIActivator.getDefault().getLogger();

    /**
     * @param viewer
     */
    public EMFRecordingCommandContentMerger(
            EMFCompareMergeViewer mergeViewer) {
        super();
        this.mergeViewer = mergeViewer;

    }

    /**
     * Replace left or right with copy of opposite side's value.
     * 
     * @param leftToRight
     * 
     * @return new node (which may be null when for instance copy from a
     *         deletion difference on right to left).
     */
    public void copy(final boolean leftToRight) {
        /*
         * The source of target of current merge input itself may be null (if
         * object exists on one side but not other).
         * 
         * So we need to operate from the perspective of the parent object of
         * the current input.
         */
        Object parentOfTarget = getParentOfTargetSide(leftToRight);

        if (!(parentOfTarget instanceof EMFCompareNode)) {
            throw new RuntimeException(
                    "Expected parent of input to be EMFCompareNode-based.");
        }

        final EMFCompareNode parentNodeOfTarget =
                (EMFCompareNode) parentOfTarget;

        EditingDomain editingDomain = getEditingDomain(parentNodeOfTarget);

        if (editingDomain != null) {
            /*
             * If have editingDomain must have working copy node ancestor and
             * working copy! (so no need to check again for null :)
             */
            WorkingCopyCompareNode workingCopyNode =
                    parentNodeOfTarget.getWorkingCopyCompareNodeAncestor();

            WorkingCopy targetWorkingCopy = parentNodeOfTarget.getWorkingCopy();

            try {
                /*
                 * Let every one know that the compare editor is doing the copy.
                 * (Top window diff viewer can ignore changes during this period
                 * if it wants to save it removing the items from differences
                 * that we have just copied)
                 */

                targetWorkingCopy
                        .getAttributes()
                        .put(WorkingCopyCompareNode.WORKINGCOPY_PROPERTY_CHANGE_MADE_BY_COMPARE_EDITOR,
                                mergeViewer);

                Command cmd = null;

                if (parentOfTarget instanceof WorkingCopyCompareNode
                        && !mergeViewer.isThreeWay()) {
                    /*
                     * Working copy compare node its a special case.
                     */
                    WorkingCopyCompareNode workingCopyCompareNode =
                            (WorkingCopyCompareNode) parentOfTarget;

                    Object sourceNode;
                    if (leftToRight) {
                        sourceNode = mergeViewer.getLeftViewer().getInput();
                    } else {
                        sourceNode = mergeViewer.getRightViewer().getInput();
                    }

                    cmd =
                            workingCopyCompareNode
                                    .getReplaceRootElementCommand(editingDomain,
                                            ((EObjectCompareNode) sourceNode)
                                                    .getEObject());

                } else {
                    /*
                     * Standard EMFCompareNode's are done in a RecordingCommand.
                     */
                    cmd =
                            new RecordingCommand(
                                    (TransactionalEditingDomain) editingDomain) {

                                @Override
                                protected void doExecute() {
                                    /*
                                     * At end of a branch, no merge is possible
                                     * so just replace one side with other the
                                     * same as we would in a two way merge.
                                     */
                                    boolean isLeaf = false;
                                    if (mergeViewer.getInput() instanceof IDiffContainer) {
                                        IDiffElement[] children =
                                                ((IDiffContainer) mergeViewer
                                                        .getInput())
                                                        .getChildren();
                                        if (children == null
                                                || children.length == 0) {
                                            isLeaf = true;
                                        }
                                    } else {
                                        isLeaf = true;
                                    }

                                    if (mergeViewer.isThreeWay() && !isLeaf) {
                                        /*
                                         * For three-way perform a merge (copy
                                         * non-conflicting differences
                                         */
                                        merge(parentNodeOfTarget, leftToRight);

                                    } else {
                                        /*
                                         * For 2-way have no choice but to
                                         * replace one side with a copy of the
                                         * other.
                                         */
                                        replace(parentNodeOfTarget, leftToRight);
                                    }
                                }

                            };
                }

                /*
                 * Now execute the command.
                 */
                if (cmd != null && cmd.canExecute()) {
                    if (editingDomain.getCommandStack() != null) {
                        /*
                         * the parent node (at least) will need refreshing after
                         * the operation.
                         */
                        parentNodeOfTarget.setNeedsRefresh();

                        /* Execute the command */
                        editingDomain.getCommandStack().execute(cmd);

                        /*
                         * Some operations may requrie refreshes in other parts
                         * of the tree so look for anyting tha needs a refresh
                         * by attempting arefrsh fromtop of tree (onlythose that
                         * need refresh will get one.
                         */
                        workingCopyNode.refreshAsNecessary();

                    }
                }

            } finally {
                /*
                 * Make sure we unset 'change made by compare editor.
                 */
                targetWorkingCopy
                        .getAttributes()
                        .put(WorkingCopyCompareNode.WORKINGCOPY_PROPERTY_CHANGE_MADE_BY_COMPARE_EDITOR,
                                null);
            }
        }
        return;
    }

    /**
     * @param parentNodeOfTarget
     * @param leftToRight
     * 
     * @return Command to merge source with target.
     */
    private void merge(EMFCompareNode parentNodeOfTarget, boolean leftToRight) {
        if (true) {
            // TODO need to decide on atomic node strategy for three way merge
            // Detailed model below atomic nodes should not be individually
            // merged.
            /*
             * 3.3.1. Strategy 1…
             * 
             * 3.3.1.1. Whilst 3-way-merging, do not traverse below an atomic
             * node with no atomic children.
             * 
             * 3.3.1.2. In other words either a whole atomic node is copied or
             * removed or it is left alone as having ‘conflicting’ changes (when
             * content is changed in both sides).
             * 
             * 3.3.1.3. For instance when whole package is selected as input,
             * changes within individual processes will be merged (for instance
             * an additional task in an existing process on both sides would be
             * merged).
             * 
             * 3.3.1.4. All non-atomic nodes of current input are merged as
             * normal (non-conflicting differences are copied at raw model
             * level). Therefore it is vital that individual model
             * specializations (process, BOM, OM etc) carefully consider what is
             * atomic within each element that has both atomic and non-atomic
             * children.
             * 
             * 3.3.2. Strategy 2…
             * 
             * 3.3.2.1. Only the current left/right merge input’s level is
             * traversed and the notion of non-conflicting status is taken from
             * this level.
             * 
             * 3.3.2.2. For instance when whole package is selected as input,
             * changes within individual processes will not be merged. So a
             * whole process would be copied from right to left but only if
             * there were no changes on the left hand side as (compared with the
             * common ancestor revision).
             * 
             * 3.3.2.3. If there were changes to a process on both left and
             * right (as compared with common ancestor revision) then the whole
             * process would be seen as ‘conflicting’ and the process would not
             * be merged.
             * 
             * 3.3.2.4. To merge this process the user could then ‘drill-down’
             * into it (by selecting it in the top window) and attempt (again)
             * to copy all non-conflicting changes (which for instance would
             * copy new tasks from right to left but not tasks that had changed
             * in right and left).
             */
            throw new RuntimeException(
                    "Three-way merge not fully implemented yet");
        }

        if (mergeViewer.getInput() instanceof IDiffContainer) {
            IDiffContainer diffContainer =
                    (IDiffContainer) mergeViewer.getInput();

            Object left = mergeViewer.getLeftViewer().getInput();
            Object right = mergeViewer.getRightViewer().getInput();

            /*
             * If copying to or from null (regardless of side) then there is no
             * merging to do and we may as well do a simple replace.
             */
            if (!(left instanceof EMFCompareNode)
                    || !(right instanceof EMFCompareNode)) {
                replace(parentNodeOfTarget, leftToRight);
                return;
            }

            /*
             * Ok, good we know we are merging the children of the right (which
             * exists) with the children of the left (which exists) or visa
             * versa.
             */
            EMFCompareNode leftNode = (EMFCompareNode) left;
            EMFCompareNode rightNode = (EMFCompareNode) right;

            if (!leftToRight) {
                /*
                 * Merge right to left.
                 */
                mergeChildren(diffContainer,
                        rightNode,
                        MergeContentViewerType.RIGHT,
                        leftNode);

            } else {
                /*
                 * Merge right to left.
                 */
                mergeChildren(diffContainer,
                        leftNode,
                        MergeContentViewerType.LEFT,
                        rightNode);
            }

        }

        return;
    }

    private void mergeChildren(IDiffContainer diffContainer,
            EMFCompareNode sourceParent, MergeContentViewerType sourceSide,
            EMFCompareNode targetParent) {

        /*
         * Go thru the diffContainer's children (the diffCOntainer comes from
         * the top window's structure diff content which uses object sin a
         * separate model from ours).
         */
        Object[] sourceChildren = sourceParent.getChildren();
        Object[] targetChildren = targetParent.getChildren();

        IDiffElement[] diffChildren = diffContainer.getChildren();

        for (IDiffElement diffChild : diffChildren) {
            if (!(diffChild instanceof ICompareInput)) {
                logger.warn("DiffChild is not an ICompareInput: " + diffChild);
            }

            /*
             * Check difference was caused by a change on the side we are
             * merging from.
             */
            int diffKind = diffChild.getKind();
            int diffDirection = (diffKind & Differencer.DIRECTION_MASK);
            int changeType = (diffKind & Differencer.CHANGE_TYPE_MASK);

            boolean leftToRight =
                    MergeContentViewerType.LEFT.equals(sourceSide);
            if ((MergeContentViewerType.RIGHT.equals(sourceSide) && (diffDirection & Differencer.RIGHT) != 0)
                    || (leftToRight && (diffDirection & Differencer.LEFT) != 0)) {

                ITypedElement diffSrcNode =
                        MergeContentViewerType.RIGHT.equals(sourceSide) ? ((ICompareInput) diffChild)
                                .getRight()
                                : ((ICompareInput) diffChild).getLeft();

                ITypedElement diffTgtNode =
                        MergeContentViewerType.RIGHT.equals(sourceSide) ? ((ICompareInput) diffChild)
                                .getLeft()
                                : ((ICompareInput) diffChild).getRight();

                /*
                 * Now act according to change type.
                 */
                if (changeType == Differencer.ADDITION) {
                    /*
                     * Node added on source side. Ask target parent node to
                     * replace 'null' with sourceChild.
                     */
                    addNodeFromSource(diffSrcNode, sourceChildren, targetParent);

                } else if (changeType == Differencer.DELETION) {
                    /*
                     * Node removed on source side Ask target parent to replace
                     * target child with null.
                     */
                    removeNodeFromTarget(diffTgtNode,
                            targetChildren,
                            targetParent);

                } else if (changeType == Differencer.CHANGE) {
                    /*
                     * When a 'leaf' of tree is tagged as changed then we can
                     * simply ask parent to replace the parent to replace one
                     * side with the other.
                     * 
                     * If it's not a leaf then we simply recurs and do the
                     * children.
                     */
                    IDiffElement[] grandChildren = null;

                    if (diffChild instanceof IDiffContainer) {
                        IDiffContainer childContainer =
                                (IDiffContainer) diffChild;

                        grandChildren = childContainer.getChildren();
                    }

                    if (grandChildren == null || grandChildren.length == 0) {
                        /*
                         * Found a changed leaf node, replace target with
                         * source.
                         * 
                         * But ONLY if this is not a conflicting change.
                         */
                        if (!(diffDirection == Differencer.CONFLICTING)) {
                            replaceNodeFromSource(diffSrcNode,
                                    sourceChildren,
                                    diffTgtNode,
                                    targetChildren,
                                    targetParent);
                        }

                    } else if (diffSrcNode instanceof EMFCompareNode
                            && diffTgtNode instanceof EMFCompareNode) {
                        /*
                         * This changed node has children, so simply recurs and
                         * deal with them individually.
                         */
                        mergeChildren((IDiffContainer) diffChild,
                                (EMFCompareNode) locateNode(sourceChildren,
                                        diffSrcNode),
                                sourceSide,
                                (EMFCompareNode) locateNode(targetChildren,
                                        diffTgtNode));
                    } else {
                        /*
                         * Cannot deal with non EMFCompareNode's by recursing do
                         * a simple replace.
                         * 
                         * But ONLY if this is not a conflicting change.
                         */
                        if (!(diffDirection == Differencer.CONFLICTING)) {
                            replace(targetParent, leftToRight);
                        }
                    }
                }
            }

        }

        return;
    }

    /**
     * Node removed on source side Ask target parent to replace target child
     * with null.
     * 
     * @param editingDomain
     * @param cmd
     * @param diffTgtNode
     * @param targetChildren
     * @param targetParent
     */
    private void removeNodeFromTarget(ITypedElement diffTgtNode,
            Object[] targetChildren, EMFCompareNode targetParent) {
        /* Locate the node to delete in actual target model.. */
        Object targetNode = locateNode(targetChildren, diffTgtNode);

        if (targetNode instanceof EMFCompareNode) {
            ((EMFCompareNode) targetNode).removeYourself(targetParent);

        } else {
            /*
             * If we didn't find it then don't worry! User may be doing
             * something for the second time! So don't log as error.
             */
        }
    }

    /**
     * Node added on source side. Ask target parent node to replace 'null' with
     * sourceChild.
     * 
     * @param editingDomain
     * @param cmd
     * @param diffSrcNode
     * @param sourceChildren
     * @param targetParent
     */
    private void addNodeFromSource(ITypedElement diffSrcNode,
            Object[] sourceChildren, EMFCompareNode targetParent) {

        Object sourceNode = locateNode(sourceChildren, diffSrcNode);

        if (sourceNode instanceof EMFCompareNode) {
            ((EMFCompareNode) sourceNode).addYourself(targetParent);

        } else {
            logger.error("Could not find added node in source content: "
                    + diffSrcNode);
        }
    }

    /**
     * Ask parent of target ndoe to replace target node with source node.
     * 
     * @param editingDomain
     * @param cmd
     * @param diffSrcNode
     * @param sourceChildren
     * @param diffTgtNode
     * @param targetChildren
     * @param targetParent
     */
    private void replaceNodeFromSource(ITypedElement diffSrcNode,
            Object[] sourceChildren, ITypedElement diffTgtNode,
            Object[] targetChildren, EMFCompareNode targetParent) {

        Object sourceNode = locateNode(sourceChildren, diffSrcNode);

        if (sourceNode instanceof EMFCompareNode) {
            Object targetNode = locateNode(targetChildren, diffTgtNode);

            if (targetNode instanceof EMFCompareNode) {
                ((EMFCompareNode) sourceNode).replaceYourself(targetParent,
                        (EMFCompareNode) targetNode);

            } else {
                logger.error("Could not find original node in target content: "
                        + diffTgtNode);
            }

        } else {
            logger.error("Could not find changed node in source content: "
                    + diffSrcNode);
        }
    }

    /**
     * Find the equivalent of the diffSrcNode in the array from actual content
     * model we are using.
     * 
     * @param childNodes
     * @param diffSrcNode
     *            Node form DiffNode merge input.
     * 
     * @return FInd the given node in the given array.
     */
    private Object locateNode(Object[] childNodes, ITypedElement diffSrcNode) {
        /*
         * Should be ok to use equals because XpdCompareNode etc use have
         * euals() designed for equivalence between same objects in different
         * models
         */
        for (Object child : childNodes) {
            if (child.equals(diffSrcNode)) {
                return child;
            }
        }

        return null;
    }

    /**
     * Perform a straight replacement of one side with a copy of the other side.
     * <p>
     * 
     * @param parentOfTarget
     *            {@link EMFCompareNode} parent of of the merge viewer's input
     *            for the destination side of copy (or root EMFCompareNode if
     *            input is the root of the EMFCompareNode tree).
     * @param leftToRight
     * 
     */
    private void replace(EMFCompareNode parentNodeOfTarget, boolean leftToRight) {
        Command cmd = null;

        /*
         * Copy right to left or left to right. The IEditableContent.replace()
         * method is documented as being called on the _parent_ of an element.
         * 
         * Our current input is 'the thing to copy' so we have to tell it's
         * parent to replace it our input from one right side to left.
         * 
         * I think this is done so that if right or left side is null (part of
         * model doesn't exist on the right so should be removed from left.
         */

        Object targetNode, sourceNode;

        if (leftToRight) {
            sourceNode = mergeViewer.getLeftViewer().getInput();
            targetNode = mergeViewer.getRightViewer().getInput();
        } else {
            sourceNode = mergeViewer.getRightViewer().getInput();
            targetNode = mergeViewer.getLeftViewer().getInput();
        }

        if (targetNode instanceof EMFCompareNode
                && sourceNode instanceof EMFCompareNode) {
            ((EMFCompareNode) sourceNode).replaceYourself(parentNodeOfTarget,
                    (EMFCompareNode) targetNode);

        } else if (targetNode == null && sourceNode instanceof EMFCompareNode) {
            ((EMFCompareNode) sourceNode).addYourself(parentNodeOfTarget);

        } else if (targetNode instanceof EMFCompareNode && sourceNode == null) {
            ((EMFCompareNode) targetNode).removeYourself(parentNodeOfTarget);
        }

        return;
    }

    /**
     * @param node
     * @return working copy for given node.
     */
    private EditingDomain getEditingDomain(EMFCompareNode node) {
        WorkingCopy workingCopy = node.getWorkingCopy();
        if (workingCopy != null) {
            return workingCopy.getEditingDomain();
        }
        return null;
    }

    /**
     * This viewer's input should be an {@link ICompareInput} (in fact we expect
     * it to be a {@link DiffNode}.
     * <p>
     * However for purpose of copy one side to other then there is the
     * possibility that our input's left or right may be null (for an addition
     * for instance).
     * <p>
     * This method gets the 'parent left side object of the current input's left
     * side' or the 'parent right side of the current input's right side'.
     * <p>
     * At the moment I am assuming that there MUST be a parent object for the
     * current input left/right side even if the input left/right side itself is
     * null. (I think this must be true as there must have bee a common ancestor
     * because the top window does NOT expand the children of objects that were
     * added; therefore for instance it only shows 'right node was added to the
     * equivalent of an existing parent left node')
     * 
     * @param leftToRight
     * 
     * @return parent or left or right side of current input.
     */
    protected Object getParentOfTargetSide(boolean leftToRight) {
        Object input = mergeViewer.getInput();

        if (input instanceof DiffNode) {
            DiffNode diffNode = (DiffNode) input;

            IDiffContainer parent = diffNode.getParent();
            if (parent instanceof ICompareInput) {
                ICompareInput parentInput = (ICompareInput) parent;

                /*
                 * Note that the input DiffNode is an element from the TOP
                 * window's tree of DiffNodes and the left/right/ancestor are
                 * versions on the working copy model that are different from
                 * ours in the compare view.
                 * 
                 * So have to convert from it's idea of left/right to ours.
                 */
                if (leftToRight) {
                    return mergeViewer
                            .getRightMergeNodeFromDiffNode(parentInput
                                    .getRight());
                } else {
                    return mergeViewer.getLeftMergeNodeFromDiffNode(parentInput
                            .getLeft());
                }

            }
        }
        return null;
    }

}
