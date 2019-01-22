/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;

/**
 * Merge viewer content object representing the separator line between info
 * nodes and compare nodes in the merge trees.
 * 
 * @author aallway
 * @since 17 Nov 2010
 */
public class MergeContentInfoNodesSeparatorLine implements IChildCompareNode {

    private Object parent;

    /**
     * @param parent
     */
    public MergeContentInfoNodesSeparatorLine(Object parent) {
        super();
        this.parent = parent;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode#getParent()
     * 
     * @return
     */
    @Override
    public Object getParent() {
        return parent;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * 
     * @return as far as we're concerned the separator line in merge content is
     *         equivalent to the separator line in the same parent
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;

        } else if (obj instanceof MergeContentInfoNodesSeparatorLine) {
            MergeContentInfoNodesSeparatorLine other =
                    ((MergeContentInfoNodesSeparatorLine) obj);

            if (MergeContentViewerUtil.mergeContentEquals(parent, other.parent)) {
                return true;
            }
        }

        return false;
    }

}
