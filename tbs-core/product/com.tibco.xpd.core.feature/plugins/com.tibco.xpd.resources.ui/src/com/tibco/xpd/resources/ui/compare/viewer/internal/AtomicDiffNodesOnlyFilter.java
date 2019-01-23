/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;

/**
 * Filter that only allows {@link XpdCompareNode} with isAtomic()==true (or
 * object that have atomic children descendents).
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public class AtomicDiffNodesOnlyFilter extends ViewerFilter {

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        /* Only include objects that ARE atomic OR contain atomic children */
        ITreeContentProvider cp =
                (ITreeContentProvider) ((TreeViewer) viewer)
                        .getContentProvider();

        return isAtomicOrHasAtomicDescendents(element, cp);
    }

    /**
     * @param element
     * @param cp
     * @return
     */
    public static boolean isAtomicOrHasAtomicDescendents(Object element,
            ITreeContentProvider cp) {
        if (isAtomicNode(element)) {
            return true;
        }

        if (hasAtomicDescendents(element, cp)) {
            return true;
        }

        return false;
    }

    /**
     * @param element
     * @return
     */
    private static boolean isAtomicNode(Object element) {
        XpdCompareNode compareNode = getXpdCompareNode(element);
        if (compareNode != null) {
            if (compareNode.isAtomic()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAtomicDescendents(Object element,
            ITreeContentProvider cp) {
        Object[] children = cp.getChildren(element);
        if (children != null) {
            for (Object child : children) {
                if (isAtomicNode(child)) {
                    return true;
                }
            }

            /* Recurs. */
            for (Object child : children) {
                if (hasAtomicDescendents(child, cp)) {
                    return true;
                }
            }

        }

        return false;
    }

    private static XpdCompareNode getXpdCompareNode(Object element) {
        if (element instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) element;

            if (compareInput.getAncestor() instanceof XpdCompareNode) {
                return (XpdCompareNode) compareInput.getAncestor();
            } else if (compareInput.getLeft() instanceof XpdCompareNode) {
                return (XpdCompareNode) compareInput.getLeft();
            } else if (compareInput.getRight() instanceof XpdCompareNode) {
                return (XpdCompareNode) compareInput.getRight();
            }

        } else if (element instanceof XpdCompareNode) {
            return (XpdCompareNode) element;
        }

        return null;
    }
}
