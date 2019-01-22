/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.compare.structuremergeviewer.IStructureCreator;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;

/**
 * Class representing a node in comparison tree.
 * <p>
 * The {@link XpdCompareNode} wraps up some content object in an
 * {@link IStructureCreator}'s tree. It implements the necessary interfaces
 * required by the {@link StructureDiffViewer} to perform a comparison for the
 * top window of the comparison editor.
 * <p>
 * Nominally this is the base class for the {@link EMFCompareNode}'s that wrap
 * up and represent the objects in a XML EMF model (created by an EMF
 * model-specific sub-class of {@link EMFCompareStructureCreator} via it's
 * sub-class of {@link EMFCompareNodeFactory}. However it is legitimate to
 * provide your own {@link XpdCompareNode} implementations when you compare
 * content does not necessarily match the EMF model. Although this is unlikely.
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public abstract class XpdCompareNode implements IStructureComparator,
        ITypedElement, IDisposable, IChildCompareNode, IEditableContent {

    private Object parentCompareNode;

    private boolean childrenCached = false;

    private Object[] children = null;

    private boolean infoChildrenCached = false;

    private Collection<XpdPropertyInfoNode> infoChildren = null;

    private boolean disposed;

    private String locationPath = null;

    private String locationInParent = null;

    private Map<Object, Object> properties = null;

    private String contentType;

    private boolean atomic = false;

    private boolean needsRefresh = false;

    /**
     * Construct with given parent compare node.
     * <p>
     * Note that this does NOT have to be an {@link XpdCompareNode} <b>but</b>
     * if it is you will need to
     * 
     * @param parentCompareNode
     * @param contentType
     *            the content type that links up with contribution to the
     *            "extensions" listed in the
     *            <code>org.eclipse.compare.contentMergeViewers</code> and
     *            <code>org.eclispe.compare.structureMergeViewers</code>
     *            extension point contributions
     */
    public XpdCompareNode(Object parentCompareNode, String contentType) {
        super();
        this.parentCompareNode = parentCompareNode;
        this.contentType = contentType;
    }

    /**
     * Get the a content type that links up with contribution to the
     * "extensions" listed in the org.eclipse.compare.contentMergeViewers and
     * org.eclispe.compare.structureMergeViewers extension point contributions
     * 
     * @see org.eclipse.compare.ITypedElement#getType()
     * 
     * @return the content type.
     */
    @Override
    public String getType() {
        return contentType;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode#getParent()
     * 
     * @return parent compare node.
     */
    @Override
    public Object getParent() {
        return parentCompareNode;
    }

    /**
     * Create the child comparator nodes for this node (or null if there are
     * none).
     * <p>
     * Note that it is not necessary for these to be sub-classes of
     * {@link XpdCompareNode}.
     * 
     * @return The child comparator nodes or null if there are none.
     */
    protected abstract Object[] createChildren();

    /**
     * Info children are extra children that are not included in the general
     * structured difference but are included in the kleft/right tre of merge
     * content viewer.
     * <p>
     * The sub-class should create any merge content info children when
     * required.
     * 
     * @return info children or empty (non-immutable!) list if none.
     */
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        return new ArrayList<XpdPropertyInfoNode>();
    }

    /**
     * Get the object to be used for equivalence in two differne sides of the
     * comparison.
     * <p>
     * It is unlikely that the object from left and right side are directly
     * comparable (as they will be in separate emf models etc). Implement this
     * method to return an object to use for hascode and equals comparison
     * (Differencer relies on this).
     * 
     * @return The object to be used for comparison and hascode
     */
    public final String getLocationInParent() {
        if (locationInParent == null) {
            /*
             * Get location in parent first time only (locaiton may change
             * during merge (such as if locaiton of node is based on position in
             * list).
             */
            locationInParent = calculateLocationInParent();
        }
        return locationInParent;
    }

    /**
     * Calculate a string to locate this object in the parent node. This value
     * is used to correlate this node with the equivalent target node with
     * equivalent parent node.
     * 
     * @return a string to locate this object in the parent node.
     */
    protected abstract String calculateLocationInParent();

    /**
     * Returns the location path that should be the unique location of an object
     * within the tree.
     * <p>
     * Effectively it is a combination of all of the parent nodes into a single
     * "/" separated path.
     * 
     * @return location path.
     */
    public final String getLocationPath() {
        if (locationPath == null) {

            locationInParent = getLocationInParent();

            if (parentCompareNode instanceof XpdCompareNode) {
                locationPath =
                        ((XpdCompareNode) parentCompareNode).getLocationPath();
            } else {
                locationPath = ""; //$NON-NLS-1$
            }

            locationPath += "/" + locationInParent; //$NON-NLS-1$
        }
        return locationPath;
    }

    /**
     * Get the text content for diff comparison (or "" if there is none).
     * 
     * @return Default "" (child nodes are still compared.
     */
    public String getTextContent() {
        return ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     * 
     * @return default blank image (else we get a black square in comparison
     *         tree).
     */
    @Override
    public Image getImage() {
        return XpdResourcesUIActivator.getDefault().getImageRegistry()
                .get(XpdResourcesUIConstants.ICON_DEFAULT_COMPARATOR_NODE);
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureComparator#getChildren()
     * 
     * @return
     */
    @Override
    public final Object[] getChildren() {
        if (!childrenCached) {
            children = createChildren();
            if (children == null) {
                children = new Object[0];
            }
            childrenCached = true;
        }

        return children;
    }

    /**
     * Info children are extra children that are not included in the general
     * structured difference but are included in the left/right tree of merge
     * content viewer.
     * <p>
     * The sub-class should create any merge content info children when
     * required.
     * 
     * @return info children or Object[0] if there are none.
     */
    public final Collection<XpdPropertyInfoNode> getInfoPropertyChildren() {
        if (!infoChildrenCached) {
            infoChildren = createInfoPropertyChildren();
            if (infoChildren == null) {
                infoChildren = Collections.emptyList();
            }
            infoChildrenCached = true;
        }

        return infoChildren;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public final int hashCode() {
        Object comp = getLocationPath();
        if (comp != null) {
            return comp.hashCode();
        }

        return super.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public final boolean equals(Object obj) {
        /**
         * Handle special case where one or other node is a MATCH ANY node (see
         * note on XpdMatchAnyCompareNode class below)
         */
        if (obj instanceof XpdMatchAnyCompareNode
                || this instanceof XpdMatchAnyCompareNode) {
            return true;
        }

        if (obj instanceof XpdCompareNode) {
            Object comp = getLocationPath();
            Object comp2 = ((XpdCompareNode) obj).getLocationPath();

            if (comp != null) {
                return comp.equals(comp2);
            }
        }
        return super.equals(obj);
    }

    /**
     * Override this method to make the node editable (enables the
     * copy-right-to-left button in merge viewer)
     * 
     * @see org.eclipse.compare.IEditableContent#isEditable()
     * 
     * @return By default compare nodes are not editable (because this class
     *         would not know what to do with them).
     */
    @Override
    public boolean isEditable() {
        return false;
    }

    /**
     * The 'atomic' status is used by viewers to decide how far down the content
     * tree the user may select for individual comparison. i.e. if the user
     * selects a child of an atomic in the compare-overview view (top view of
     * compare editor) then the merge viewer (left/right) will show contents of
     * its first 'atomic' ancestor.
     * <p>
     * Note that the atomic nodes within atomic nodes should be individually
     * selectable for comparison (the viewer should find the first atomic node
     * ancestor of a given input (unless input itself is atomic) and redirect
     * its ICompareInput to that instead.
     * 
     * @return <code>true</code> if the node is atomic
     */
    public boolean isAtomic() {
        return atomic;
    }

    /**
     * The 'atomic' status is used by viewers to decide how far down the content
     * tree the user may select for individual comparison. i.e. if the user
     * selects a child of an atomic in the compare-overview view (top view of
     * compare editor) then the merge viewer (left/right) will show contents of
     * its first 'atomic' ancestor.
     * <p>
     * Note that the atomic nodes within atomic nodes should be individually
     * selectable for comparison (the viewer should find the first atomic node
     * ancestor of a given input (unless input itself is atomic) and redirect
     * its ICompareInput to that instead.
     * <p>
     * In other words:
     * <li>If ALL nodes in tree are atomic or NO nodes in tree are atomic then
     * any object in the top window (structure diff viewer) can be set directly
     * as input on lef/right merge viewers.</li>
     * <li>If only the ROOT node is atomic then all selections within the top
     * window tree will select root of tree as input for left/right merge views.
     * </li>
     * <li>If some are atomic then the left/right view input will be set to the
     * nearest atomic ancestor</li>
     * 
     * 
     * @param atomic
     */
    public void setAtomic(boolean atomic) {
        this.atomic = atomic;
    }

    /**
     * <b>Note: A sub-class that override's this method should call
     * {@link #disposeChildren()} after performing the replacement to ensure
     * that the children are reloaded.
     * 
     * @see org.eclipse.compare.IEditableContent#replace(org.eclipse.compare.ITypedElement,
     *      org.eclipse.compare.ITypedElement)
     * 
     * @param dest
     * @param src
     * @return dest or <code>null</code> if replacement was not possible.
     */
    @Override
    public ITypedElement replace(ITypedElement dest, ITypedElement src) {
        throw new RuntimeException(
                "replace() must be implemented by sub-class if isEditable() is true");
    }

    /**
     * @see org.eclipse.compare.IEditableContent#setContent(byte[])
     * 
     * @param newContent
     */
    @Override
    public void setContent(byte[] newContent) {
        throw new RuntimeException("Not expecting setContent() to be called.");
    }

    /**
     * Add an arbitrary property to node.
     * 
     * @param key
     * @param value
     */
    public void setProperty(Object key, Object value) {
        if (properties == null) {
            properties = new HashMap<Object, Object>();
        }

        properties.put(key, value);

        return;
    }

    /**
     * @param key
     * @return A property previously set with
     *         {@link #addProperty(Object, Object)}
     */
    public Object getProperty(Object key) {
        if (properties != null) {
            return properties.get(key);
        }
        return null;
    }

    /**
     * Remove proeprty previously added with
     * {@link #setProperty(Object, Object)}
     * 
     * @param key
     */
    public void removeProperty(Object key) {
        if (properties != null) {
            properties.remove(key);
        }

        return;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Tag this node as requiring a refresh.
     * 
     * @param needsRefresh
     */
    public void setNeedsRefresh() {
        this.needsRefresh = true;
    }

    /**
     * Refresh this element if it has been tagged as needing refresh. >p>
     * Otherwise recursively refresh any children that have been tagged as
     * requrieing refresh.
     */
    public void refreshAsNecessary() {
        if (needsRefresh) {
            /*
             * If thisnode needs a refresh then just dispose the children the
             * the new children will be loaded on next getChildren() anyway.
             */
            disposeChildren();

            needsRefresh = false;

        } else {
            /* Recursively check if children need a refresh. */
            if (children != null) {
                for (Object child : children) {
                    if (child instanceof XpdCompareNode) {
                        ((XpdCompareNode) child).refreshAsNecessary();
                    }
                }
            }
        }

        return;
    }

    /**
     * @see org.eclipse.ui.services.IDisposable#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (!disposed) {
            disposed = true;

            disposeChildren();
        }
        return;
    }

    /**
     * Clear the cache of child nodes after disposing them.
     */
    public void disposeChildren() {
        if (children != null) {
            for (Object child : children) {
                if (child instanceof IDisposable) {
                    ((IDisposable) child).dispose();
                }
            }

            children = null;
            childrenCached = false;
        }

        if (infoChildren != null) {
            for (Object child : infoChildren) {
                if (child instanceof IDisposable) {
                    ((IDisposable) child).dispose();
                }
            }

            infoChildren = null;
            infoChildrenCached = false;
        }
        return;
    }

    /**
     * This can be used for the root element compare node - we override
     * XpdCompareNode.equals() method so that we return
     * "yes these two node locations are equivalent" provided that ONE side of
     * the compare is this special case (this allows us to include a
     * "Can't load revision because it s got an old file format version".
     */
    public static final class XpdMatchAnyCompareNode extends XpdCompareNode {
        protected static String SPECIAL_MATCH_ANY_NODE_LOCATION =
                "$_$_MATCH_ANY_NODE_LOCATION_!_!"; //$NON-NLS-1$

        private String label;

        private Image image;

        /**
         * @param parentCompareNode
         * @param contentType
         */
        public XpdMatchAnyCompareNode(String label, Image image,
                Object parentCompareNode, String contentType) {
            super(parentCompareNode, contentType);
            this.label = label;
            this.image = image;
            this.setAtomic(true);
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#calculateLocationInParent()
         * 
         * @return
         */
        @Override
        protected String calculateLocationInParent() {
            return SPECIAL_MATCH_ANY_NODE_LOCATION;
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
         * 
         * @return
         */
        @Override
        protected Object[] createChildren() {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.compare.ITypedElement#getName()
         * 
         * @return
         */
        @Override
        public String getName() {
            return label;
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getImage()
         * 
         * @return
         */
        @Override
        public Image getImage() {
            if (image != null) {
                return image;
            }
            return super.getImage();
        }

    }
}
