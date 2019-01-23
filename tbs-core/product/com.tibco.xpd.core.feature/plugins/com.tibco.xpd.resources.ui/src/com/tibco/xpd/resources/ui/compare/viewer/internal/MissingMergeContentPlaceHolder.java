package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.structuremergeviewer.ICompareInput;

import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;

/**
 * Class that is a blank entry place holder when the oopposite side's content
 * contains an object that is missing from this viewer's content.
 * 
 * @author aallway
 * @since 3 Nov 2010
 */
public class MissingMergeContentPlaceHolder implements IChildCompareNode {
    /*
     * The wrapped content object that IS NOT in this viewer's content but is in
     * other side's content.
     */
    private Object wrappedContentObject;

    private int differenceKind;

    private Object parent;

    /**
     * @param ci
     * @param differenceKind
     */
    public MissingMergeContentPlaceHolder(ICompareInput ci, int differenceKind,
            Object parent) {
        super();

        this.differenceKind = differenceKind;
        this.parent = parent;

        if (ci.getAncestor() != null) {
            wrappedContentObject = ci.getAncestor();
        } else if (ci.getLeft() != null) {
            wrappedContentObject = ci.getLeft();
        } else if (ci.getRight() != null) {
            wrappedContentObject = ci.getRight();
        } else {
            throw new RuntimeException("No Content in comparison input");
        }
    }

    /**
     * Get the object from other side of comparison that this missing object
     * place holder is in place for.
     * 
     * @return the wrappedContentObject
     */
    public Object getWrappedContentObject() {
        return wrappedContentObject;
    }

    /**
     * @return the differenceKind
     */
    public int getDifferenceKind() {
        return differenceKind;
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
     * @see java.lang.Object#hashCode()
     * 
     * @return uses has code of actual wrapped other-side's compare object in
     *         case usefd in HashSet etc
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return delegate euals to wrapped compare node if necessary.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof MissingMergeContentPlaceHolder
                && wrappedContentObject != null) {
            return wrappedContentObject
                    .equals(((MissingMergeContentPlaceHolder) obj)
                            .getWrappedContentObject());
        }
        return false;
    }
}