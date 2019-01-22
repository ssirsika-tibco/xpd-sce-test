/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Class to represent mapper target item as TreeNode to create tree structure
 * for script generation
 * 
 * @author aallway
 * @since 17 Jul 2015
 */
public class DataMapperTreeNode {

    private boolean isMapped;

    private Object item;

    /**
     * XPD-7599: The child nodes of this tree node <b>this MUST preserve the
     * original order of children in order to ensure the predictability of
     * script output (i.e. the target content is constructed in the order in
     * which it appears in the content.
     */
    private Set<DataMapperTreeNode> children = new LinkedHashSet<>();

    private boolean inLikeMapping = false;

    public DataMapperTreeNode(Object item) {
        this.item = item;
    }

    public void addChild(DataMapperTreeNode childNode) {
        children.add(childNode);
    }

    /**
     * @return the children
     */
    public List<DataMapperTreeNode> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * @return the item
     */
    public Object getItem() {
        return item;
    }

    /**
     * @param isMapped
     *            the isMapped to set
     */
    public void setMapped(boolean isMapped) {
        this.isMapped = isMapped;
    }

    /**
     * @return the isMapped
     */
    public boolean isMapped() {
        return isMapped;
    }

    /**
     * @return the inLikeMapping
     */
    public boolean isInLikeMapping() {
        return inLikeMapping;
    }

    /**
     * @param inLikeMapping
     *            the inLikeMapping to set
     */
    public void setInLikeMapping(boolean inLikeMapping) {
        this.inLikeMapping = inLikeMapping;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return item.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Object otherItem = null;
        if (obj instanceof DataMapperTreeNode) {
            otherItem = ((DataMapperTreeNode) obj).item;
        }
        return Objects.equals(item, otherItem);
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return "DataMapperTreeNode: " + item; //$NON-NLS-1$
    }
}