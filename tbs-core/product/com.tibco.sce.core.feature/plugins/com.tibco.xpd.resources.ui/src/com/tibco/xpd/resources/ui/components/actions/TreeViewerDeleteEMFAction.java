/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.components.actions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * An implementation of a delete action for tree elements in EMF model. This
 * class additionally provides functionality that selects the element below the
 * element being deleted in the tree so that user can perform repeated delete
 * operations on tree elements. Note: This class assumes that the node being
 * deleted has a parent node and uses the parent node to find the next sibling
 * to select. In case the viewer has multiple root elements then implementers
 * can override the {@link #getNextTreeItemToSelectPostDelete(StructuredViewer)}
 * method to return the appropriate element they wish to select.
 * 
 * @author kthombar
 * @since Jul 31, 2015
 */
public class TreeViewerDeleteEMFAction extends ViewerDeleteEMFAction {

    /**
     * @param viewer
     * @param deletableFeatures
     */
    public TreeViewerDeleteEMFAction(StructuredViewer viewer,
            Set<? extends EStructuralFeature> deletableFeatures) {
        super(viewer, deletableFeatures);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction#run()
     * 
     */
    @Override
    public void run() {
        final StructuredViewer viewer = getViewer();

        /*
         * get the next element to select post delete.
         */
        final Object nxtElementToSelect =
                getNextTreeItemToSelectPostDelete(viewer);
        /*
         * call the super.run() to delete the selected element in tree.
         */
        super.run();
        /*
         * call in async thread because we do not want the next element to be
         * selected before the previous element is deleted. Hence let the
         * deletion work first followed by the selection.
         */
        Display.getCurrent().asyncExec(new Runnable() {

            @Override
            public void run() {
                /*
                 * call the code to delete the selected elements
                 */
                if (viewer != null && nxtElementToSelect != null) {
                    /*
                     * select the next element post delete.
                     */
                    viewer.setSelection(new StructuredSelection(
                            nxtElementToSelect));

                }
            }
        });
    }

    /**
     * Gets the next element to select after the current selected element is
     * deleted.<li>Logic: If an elmeent is deleted , then select the next
     * element from the same parent</li> <li>If there is no next element then
     * select the previous element</li> <li>If deleting an element deletes all
     * the elements from its parent then select the parent.</li>
     * 
     * @param viewer
     * @return the next element to select after the current selected element is
     *         deleted.
     */
    protected Object getNextTreeItemToSelectPostDelete(StructuredViewer viewer) {

        if (viewer instanceof TreeViewer) {

            Tree rsdTree = ((TreeViewer) viewer).getTree();

            if (rsdTree != null) {

                int selectionCount = rsdTree.getSelectionCount();
                if (selectionCount > 0) {

                    TreeItem[] sels = rsdTree.getSelection();
                    List<TreeItem> selectedElements = Arrays.asList(sels);

                    TreeItem lastSelectionInTree = sels[sels.length - 1];
                    TreeItem parentOfLastSelection =
                            lastSelectionInTree.getParentItem();

                    if (parentOfLastSelection != null) {

                        List<TreeItem> allChildrenOfLastParent =
                                Arrays.asList(parentOfLastSelection.getItems());

                        if (selectedElements
                                .containsAll(allChildrenOfLastParent)) {
                            /*
                             * If all elements are removed form the last parent
                             * then select last parent (or patent's parent if
                             * it's also selected).
                             */
                            TreeItem parentItem = parentOfLastSelection;
                            while (parentItem != null
                                    && selectedElements.contains(parentItem)) {
                                parentItem = parentItem.getParentItem();
                            }
                            return parentItem.getData();
                        } else {
                            /* Going backwards */
                            Collections.reverse(allChildrenOfLastParent);

                            TreeItem elementToSelect = null;
                            boolean foundLastElement = false;

                            for (TreeItem eachChild : allChildrenOfLastParent) {

                                if (eachChild.equals(lastSelectionInTree)) {
                                    foundLastElement = true;
                                    if (elementToSelect != null) {
                                        return elementToSelect.getData();
                                    }
                                } else {
                                    if (!foundLastElement) {
                                        /*
                                         * Store previous element that was not
                                         * selected, so if the last selected was
                                         * found then the last stored one will
                                         * be used to select.
                                         */
                                        elementToSelect = eachChild;
                                    } else {
                                        /*
                                         * The last selected was passed and
                                         * there were no previous unselected so
                                         * we will try to find first unselected
                                         * item (still going backwards).
                                         */
                                        if (!selectedElements
                                                .contains(eachChild)) {
                                            return eachChild.getData();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
