/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes;

/**
 * Implement this interface in any non {@link XpdCompareNode} sub-class that you
 * wish to include in a compare structure tree that you want to view with
 * {@link XpdCompareMergeViewerFactory} created tree viewer.
 * <p>
 * This will only be required if you do not sub-class XpdCompareNode or it's
 * derivatives.
 * 
 * @author aallway
 * @since 1 Oct 2010
 */
public interface IChildCompareNode {
    /**
     * @return The parent of this object.
     */
    Object getParent();
}
