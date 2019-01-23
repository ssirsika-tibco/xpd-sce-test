/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EAttributeCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EFeatureMapCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectInternalPropertiesNode;

/**
 * Sorts {@link EMFCompareNode}'s into order...
 * <p>
 * Grouped by
 * <li>Text (or other non EMF compare nodes)</li>
 * <li>EAttributes (simple values)</li>
 * <li>EObjects (complex types)</li>
 * <li>ELists</li>
 * <li>Feature Maps</li>
 * </p>
 * <p>
 * Each group is sorted into alphabetical.
 * 
 * @author aallway
 * @since 14 Oct 2010
 */
public class EMFCompareNodeSortComparator extends ViewerComparator {

    /**
     * 
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param e1
     * @param e2
     * 
     * @return a negative number if the first element is less than the second
     *         element; the value 0 if the first element is equal to the second
     *         element; and a positive number if the first element is greater
     *         than the second element
     */
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        /* First convert from object to various wrappers. */
        e1 = MergeContentViewerUtil.getNodeFromViewerContent(e1);

        e2 = MergeContentViewerUtil.getNodeFromViewerContent(e2);

        int group1 = objectTypeToGroupValue(e1);
        int group2 = objectTypeToGroupValue(e2);

        if (group1 == group2) {
            if (e1 instanceof XpdPropertyInfoNode
                    && e2 instanceof XpdPropertyInfoNode) {
                /*
                 * Don't sort property info nodes by name leav in the order they
                 * were given!
                 */
                XpdPropertyInfoNode n1 = (XpdPropertyInfoNode) e1;
                XpdPropertyInfoNode n2 = (XpdPropertyInfoNode) e2;

                return n1.getOrderPosition() - n2.getOrderPosition();
            }

            /* Use default sort. */
            if (e1 instanceof ITypedElement && e2 instanceof ITypedElement) {
                String name1 = ((ITypedElement) e1).getName();
                String name2 = ((ITypedElement) e2).getName();

                if (name1 != null && name2 != null) {
                    return name1.compareTo(name2);
                }
            }

            return super.compare(viewer, e1, e2);
        }

        return group1 - group2;

    }

    private int objectTypeToGroupValue(Object o) {
        if (o instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) o;

            /* Group according to left or right side (which ever is available. */
            o = compareInput.getLeft();
            if (o == null) {
                o = compareInput.getRight();
                if (o == null) {
                    o = compareInput.getAncestor();
                }
            }
        }

        if (o instanceof EFeatureMapCompareNode) {
            return 8;
        } else if (o instanceof EListCompareNode) {
            return 7;
        } else if (o instanceof EObjectCompareNode) {
            return 6;
        } else if (o instanceof EAttributeCompareNode) {
            return 5;
        } else if (o instanceof EObjectInternalPropertiesNode) {
            return 4;
        } else if (o instanceof MergeContentInfoNodesSeparatorLine) {
            return 3;
        } else if (!(o instanceof XpdPropertyInfoNode)) {
            return 2;
        }
        /* Property info nodes always on top. */
        return 1;
    }

}
