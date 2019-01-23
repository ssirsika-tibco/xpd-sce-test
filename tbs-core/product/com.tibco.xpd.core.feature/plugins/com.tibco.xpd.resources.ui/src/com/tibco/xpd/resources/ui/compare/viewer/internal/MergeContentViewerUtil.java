/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.Messages;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * Various useful utilities for compare node merge viewer
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class MergeContentViewerUtil {

    /**
     * Locate the equivalent of the sourceViewer element in the targetViewer's
     * content. If exact equivalent not found then return the nearest common
     * ancestor.
     * 
     * @param element
     * @param sourceViewer
     * @param targetViewer
     * 
     * @return Object or <code>null</code> if no euqivalent element or ancestor
     *         found.
     */
    public static Object locateElementOrNearestAncestorInOtherViewer(
            Object element, AbstractTreeViewer sourceViewer,
            AbstractTreeViewer targetViewer) {
        Object elementOrAncestorFound = null;

        List<Object> path = getSourcePath(element, sourceViewer);

        if (path.size() > 0) {
            ITreeContentProvider targetProvider =
                    (ITreeContentProvider) targetViewer.getContentProvider();

            Object[] children = new Object[] { targetViewer.getInput() };

            int pathIdx = 0;
            Object segment = path.get(pathIdx);

            while (children != null && children.length > 0
                    && pathIdx < path.size()) {

                boolean foundSegment = false;

                for (Object child : children) {
                    if (mergeContentEquals(segment, child)) {
                        foundSegment = true;
                        elementOrAncestorFound = child;

                        break;
                    }
                }

                if (foundSegment) {
                    pathIdx++;
                    if (pathIdx < path.size()) {
                        segment = path.get(pathIdx);

                        if (pathIdx == 1) {
                            /*
                             * for the first set of children under the input
                             * element (this will be the second segment of path)
                             * we should use getElements() not getChildren.
                             * 
                             * This is because the content provider returns
                             * slightly different children (like info nodes etc
                             * for getElements() than it can for getChildren()
                             */
                            children =
                                    targetProvider
                                            .getElements(elementOrAncestorFound);

                        } else {
                            children =
                                    targetProvider
                                            .getChildren(elementOrAncestorFound);
                        }
                    }
                } else {
                    children = null;
                }
            }
        }

        return elementOrAncestorFound;
    }

    /**
     * Locate and return the exact equivalent of the given sourceViewer element
     * in the targetViewer.
     * 
     * @param element
     * @param sourceViewer
     * @param targetViewer
     * 
     * @return Exact equivalent elemetn or null if none found.
     */
    public static Object locateElementInOtherViewer(Object element,
            AbstractTreeViewer sourceViewer, AbstractTreeViewer targetViewer) {
        Object elementExact =
                locateElementOrNearestAncestorInOtherViewer(element,
                        sourceViewer,
                        targetViewer);
        if (elementExact != null && elementExact.equals(element)) {
            return elementExact;
        }
        return null;
    }

    /**
     * @param element
     * @param sourceViewer
     * @return path of objects from viewer input to given element.
     */
    private static List<Object> getSourcePath(Object element,
            AbstractTreeViewer sourceViewer) {
        /* Build path to source object. */
        List<Object> path = new ArrayList<Object>();

        ITreeContentProvider sourceProvider =
                (ITreeContentProvider) sourceViewer.getContentProvider();

        while (element != null) {
            path.add(element);
            if (element == sourceViewer.getInput()) {
                /* Stop when we get to top of our input. */
                break;
            }
            element = sourceProvider.getParent(element);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Test equivalence between the various types of objects we deal with.
     * 
     * 
     * @param o1
     * @param o2
     * 
     * @return true if obnjects represent same comparison content.
     */
    public static boolean mergeContentEquals(Object o1, Object o2) {

        Object node1 = getNodeFromViewerContent(o1);
        Object node2 = getNodeFromViewerContent(o2);

        if (node1 == null) {
            if (node2 == null) {
                return true;
            }
        } else {
            return node1.equals(node2);
        }

        return false;
    }

    /**
     * @param contentElement
     * 
     * @return Object used for equivalnce testign of compare nodes from given
     *         (potentially wrapper) object.
     */
    public static Object getNodeFromViewerContent(Object contentElement) {
        Object node = null;
        if (contentElement instanceof ICompareInput) {
            /*
             * All compare node content (left/right/ancestor) for same differnce
             * are equal so any will do
             */
            ICompareInput ci = (ICompareInput) contentElement;
            if (ci.getAncestor() != null) {
                node = ci.getAncestor();
            } else if (ci.getLeft() != null) {
                node = ci.getLeft();
            } else if (ci.getRight() != null) {
                node = ci.getRight();
            }
        } else if (contentElement instanceof MissingMergeContentPlaceHolder) {
            node =
                    ((MissingMergeContentPlaceHolder) contentElement)
                            .getWrappedContentObject();
        } else {
            node = contentElement;
        }
        return node;
    }

    /**
     * @param itemData
     * @return Difference kind from the given left/right viewer tree item data
     */
    public static int getDiffKindFromTreeItemData(Object itemData) {
        int diffKind = 0;

        if (itemData instanceof XpdCompareNode) {
            /*
             * For Xpd compare node's the XpdMergeViewerContentProvider will
             * leave hint property in the node
             */
            Integer k =
                    (Integer) ((XpdCompareNode) itemData)
                            .getProperty(XpdCompareMergeViewer.DIFF_NODE_KIND_PROPERTY);
            if (k != null) {
                diffKind = k;
            }

        } else if (itemData instanceof MissingMergeContentPlaceHolder) {
            diffKind =
                    ((MissingMergeContentPlaceHolder) itemData)
                            .getDifferenceKind();
        }
        return diffKind;
    }

    /**
     * @param treeItem
     * 
     * @return <code>true</code> if tree itme is currently selected.
     */
    public static boolean isSelected(TreeItem treeItem) {
        boolean isSelected = false;

        Tree tree = treeItem.getParent();
        if (tree != null && !tree.isDisposed()) {
            TreeItem[] selection = tree.getSelection();
            if (selection != null) {
                for (TreeItem sel : selection) {
                    if (sel == treeItem) {
                        isSelected = true;
                        break;
                    }
                }
            }
        }

        return isSelected;
    }

    /**
     * Locate the equivalent of the sourceTreeItem from the sourceViewer in the
     * targetViewer and return IT or The nearest visible ancestor.
     * 
     * @param sourceTreeItem
     * @param sourceViewer
     * @param targetViewer
     * 
     * @return tree item that is equiv to the sourceTreeItem (or it's nearest
     *         visible ancestor )
     */
    public static TreeItem locateElementOrNearestVisibleAncestor(
            TreeItem sourceTreeItem, TreeViewer sourceViewer,
            TreeViewer targetViewer) {
        TreeItem targetTreeItem = null;

        Object targetElement =
                locateElementOrNearestAncestorInOtherViewer(sourceTreeItem
                        .getData(), sourceViewer, targetViewer);
        if (targetElement != null) {
            List<Object> path =
                    getPathFromAncestor(targetElement, targetViewer.getInput());

            TreeItem[] treeItems = targetViewer.getTree().getItems();
            if (treeItems != null) {

                for (int i = 0; i < path.size(); i++) {
                    Object segment = path.get(i);

                    boolean found = false;
                    for (TreeItem treeItem : treeItems) {
                        if (mergeContentEquals(segment, treeItem.getData())) {
                            targetTreeItem = treeItem;
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        /*
                         * Didn't find item at this level so go with what we
                         * found.
                         */
                        break;

                    } else if (!targetTreeItem.getExpanded()) {
                        /*
                         * Don't go below any unexpanded items as they're not
                         * visible.
                         */
                        break;
                    }

                    treeItems = targetTreeItem.getItems();
                }
            }
        }

        return targetTreeItem;
    }

    /**
     * @param element
     * 
     * @return path from (not inc) the top level input element to the given
     *         element
     */
    public static List<Object> getPathFromAncestor(Object element,
            Object ancestor) {
        List<Object> path = new ArrayList<Object>();

        while (element != null) {
            if (MergeContentViewerUtil.mergeContentEquals(element, ancestor)) {
                break;
            }

            path.add(element);

            if (element instanceof IChildCompareNode) {
                element = ((IChildCompareNode) element).getParent();
            } else {
                break;
            }
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * @param diffKind
     * @param leftNodeDesc
     * @param rightNodeDesc
     * 
     * @return Tooltip that is appropriate for the given difference
     */
    public static String getToolTip(int diffKind, String leftNodeDesc,
            String rightNodeDesc) {
        String tooltip = null;
        int changeType = 0;
        int direction = 0;

        changeType = diffKind & Differencer.CHANGE_TYPE_MASK;
        direction = diffKind & Differencer.DIRECTION_MASK;

        if (leftNodeDesc == null) {
            leftNodeDesc = ""; //$NON-NLS-1$
        }

        if (rightNodeDesc == null) {
            rightNodeDesc = "";
        }

        if (direction == Differencer.CONFLICTING) {
            tooltip =
                    String
                            .format(Messages.MergeViewerCrossoverControl_ConflictingChange_tooltip,
                                    (leftNodeDesc.length() != 0 ? leftNodeDesc
                                            : rightNodeDesc));

        } else if (direction == Differencer.LEFT) {
            if (changeType == Differencer.ADDITION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_AddedLeft_tooltip,
                                        leftNodeDesc);

            } else if (changeType == Differencer.DELETION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_RemovedLeft_tooltip,
                                        rightNodeDesc);

            } else if (changeType == Differencer.CHANGE) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_ChangedLeft_tooltip,
                                        leftNodeDesc);
            }

        } else if (direction == Differencer.RIGHT) {
            if (changeType == Differencer.ADDITION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_AddedRight_tooltip,
                                        rightNodeDesc);

            } else if (changeType == Differencer.DELETION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_RemovedRight_tooltip,
                                        leftNodeDesc);

            } else if (changeType == Differencer.CHANGE) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_ChangedRight_tooltip,
                                        rightNodeDesc);
            }

        } else if (direction == 0) {
            /*
             * If the direction is unspecified then this is a 2 way merge, in
             * which case we will keep it simple.
             */
            if (changeType == Differencer.ADDITION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_TwoWayAdded_tooltip,
                                        (leftNodeDesc.length() != 0 ? leftNodeDesc
                                                : rightNodeDesc));

            } else if (changeType == Differencer.DELETION) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_TwoWayRemoved_tooltip,
                                        (leftNodeDesc.length() != 0 ? leftNodeDesc
                                                : rightNodeDesc));

            } else if (changeType == Differencer.CHANGE) {
                tooltip =
                        String
                                .format(Messages.MergeViewerCrossoverControl_TwoWayChanged_tooltip,
                                        (leftNodeDesc.length() != 0 ? leftNodeDesc
                                                : rightNodeDesc));
            }
        }
        return tooltip;
    }
}
