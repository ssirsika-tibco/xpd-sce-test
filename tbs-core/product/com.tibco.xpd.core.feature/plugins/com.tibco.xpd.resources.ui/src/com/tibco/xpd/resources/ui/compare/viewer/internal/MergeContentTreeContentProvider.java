/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectInternalPropertiesNode;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * Content provider for one side's (left/right/ancestor) viewer of a compare
 * merge viewer
 * <p>
 * <b>NOTE:</b> The content is provided based on the content of the MERGE
 * DIFFERENCE NODES.
 * <p>
 * If this side's viewer content contains the comparison element referenced by a
 * given difference node then that content is included.
 * <p>
 * Otherwise if this side's viewer content does not contain the comparison
 * element referenced by a given difference node the a
 * {@link MissingMergeContentPlaceHolder} is created in it's place.
 * <p>
 * This allows us to create blank 'place holders' for missing elements in eithe
 * side of comparison (which in turn allows us to display sensible cross-over
 * connection lines etc).
 * 
 * @author aallway
 * @since 1 Oct 2010
 */
public class MergeContentTreeContentProvider implements ITreeContentProvider {

    /**
     * This is the input for the merge viewer as a whole.
     * 
     * This will be used to decide what parts of the actual input (which will be
     * the whole model structure) so as to only show the content with
     * differences.
     */
    private ICompareInput mergeContentInput = null;

    /**
     * Defines which side of merge this viewer is for (left/right/ancestor).
     */
    private MergeContentViewerType viewerType;

    private Object input;

    private boolean expandInternalProperties = false;

    /**
     * 
     * @param viewerType
     */
    public MergeContentTreeContentProvider(MergeContentViewerType viewerType) {
        super();
        this.viewerType = viewerType;
    }

    /**
     * @param expandInternalProperties
     *            the expandInternalProperties to set
     */
    public void setExpandInternalProperties(boolean expandInternalProperties) {
        this.expandInternalProperties = expandInternalProperties;
    }

    /**
     * @return the expandInternalProperties
     */
    public boolean isExpandInternalProperties() {
        return expandInternalProperties;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param contentSideParent
     * @return
     */
    @Override
    public Object[] getChildren(Object contentSideParent) {
        boolean topLevel = false;

        return getChildren(contentSideParent, topLevel);
    }

    /**
     * @param contentSideParent
     * @param topLevel
     * @return
     */
    private Object[] getChildren(Object contentSideParent, boolean topLevel) {
        List<Object> children = new ArrayList<Object>();

        /*
         * Always include info children at top level or children of top level
         * info's.
         */
        if (topLevel || contentSideParent instanceof XpdPropertyInfoNode) {
            if (contentSideParent instanceof XpdCompareNode) {
                Collection<XpdPropertyInfoNode> infoPropertyChildren =
                        ((XpdCompareNode) contentSideParent)
                                .getInfoPropertyChildren();

                if (infoPropertyChildren != null
                        && infoPropertyChildren.size() > 0) {
                    children.addAll(infoPropertyChildren);
                }
            }
        }

        if (MergeContentViewerType.ANCESTOR.equals(viewerType)) {
            /*
             * For ancestor view return all atomic and info data in ancestor.
             */
            if (contentSideParent instanceof IStructureComparator) {
                boolean separatorAdded = false;

                for (Object child : ((IStructureComparator) contentSideParent)
                        .getChildren()) {
                    Object toAdd = null;

                    if (!(child instanceof XpdCompareNode)
                            || ((XpdCompareNode) child).isAtomic()) {
                        toAdd = child;

                    } else if (expandInternalProperties
                            && hasInternalPropertiesAncestor(child)) {
                        /*
                         * Show descendents of internal properties IF the user
                         * has elected to see these.
                         */
                        toAdd = child;
                    }

                    if (toAdd != null) {
                        if (!separatorAdded) {
                            separatorAdded = true;
                            if (!children.isEmpty()) {
                                children
                                        .add(new MergeContentInfoNodesSeparatorLine(
                                                contentSideParent));
                            }
                        }

                        children.add(toAdd);
                    }
                }
            }

        } else {
            /*
             * For left/right view return only contents involved in differences.
             */
            Object[] nonInfoChildren =
                    getDiffContentChildren(contentSideParent);
            if (nonInfoChildren != null && nonInfoChildren.length > 0) {
                if (!children.isEmpty()) {
                    children.add(new MergeContentInfoNodesSeparatorLine(
                            contentSideParent)); //$NON-NLS-1$
                }

                for (Object c : nonInfoChildren) {
                    children.add(c);
                }
            }

        }

        return children.toArray();
    }

    /**
     * @param objectCompareNode
     * @return <code>true</code> if the given node has an ancestor of type
     *         {@link EObjectInternalPropertiesNode}
     */
    private boolean hasInternalPropertiesAncestor(Object objectCompareNode) {
        if (objectCompareNode instanceof EObjectInternalPropertiesNode) {
            return true;

        } else if (objectCompareNode instanceof IChildCompareNode) {
            objectCompareNode =
                    ((IChildCompareNode) objectCompareNode).getParent();

            if (objectCompareNode != null) {
                return hasInternalPropertiesAncestor(objectCompareNode);
            }
        }

        return false;
    }

    /**
     * Get content that is included in differences.
     * 
     * @param contentSideParent
     * 
     * @return Different children or all children according to
     *         {@link #showOnlyDifferences}
     */
    private Object[] getDiffContentChildren(Object contentSideParent) {
        if (contentSideParent != null
                && !(contentSideParent instanceof MissingMergeContentPlaceHolder)) {

            /*
             * Only include descendents of InternalProperties node if user has
             * elected to do so
             */
            if (contentSideParent instanceof EObjectInternalPropertiesNode) {
                if (!expandInternalProperties) {
                    return new Object[0];
                }
            }

            /*
             * Get the equivalent merge difference node for this content
             */
            ICompareInput mergeNode = locateMergeContentNode(contentSideParent);

            if (mergeNode instanceof IDiffContainer) {
                /*
                 * Our content is based ion the children present in the merge
                 * node.
                 */
                IDiffElement[] mergeNodeChildren =
                        ((IDiffContainer) mergeNode).getChildren();

                if (mergeNodeChildren != null && mergeNodeChildren.length > 0) {
                    /*
                     * Create content for each diff child node
                     */
                    List<Object> contents = new ArrayList<Object>();

                    for (int i = 0; i < mergeNodeChildren.length; i++) {
                        IDiffElement mergeChild = mergeNodeChildren[i];

                        Object contentChild = null;

                        /*
                         * Get the model object node that is the subject of the
                         * merge (difference) node
                         */
                        Object subjectNode = getSubjectNodeOfDiff(mergeChild);

                        if (subjectNode != null) {
                            contentChild =
                                    getEquivalentChild(contentSideParent,
                                            subjectNode);
                        }

                        if (contentChild != null) {
                            contents.add(contentChild);

                            /*
                             * For XpdCompareNode's we can save the difference
                             * kind to help the label provider et al represent
                             * the content according to difference kind.
                             */
                            if (contentChild instanceof XpdCompareNode) {
                                ((XpdCompareNode) contentChild)
                                        .setProperty(XpdCompareMergeViewer.DIFF_NODE_KIND_PROPERTY,
                                                mergeChild.getKind());
                            }

                        } else if (mergeChild instanceof ICompareInput) {
                            /*
                             * Create a missing element place holder to
                             * represent the object that is missing
                             */
                            contents.add(new MissingMergeContentPlaceHolder(
                                    (ICompareInput) mergeChild, mergeChild
                                            .getKind(), contentSideParent));
                        }
                    }

                    return contents.toArray();
                }
            }
        }

        return new Object[0];
    }

    /**
     * @param contentSideParent
     * @param findChild
     * 
     * @return The equivalent of findChild from the children of
     *         contentSideParent.
     */
    private Object getEquivalentChild(Object contentSideParent, Object findChild) {
        Object contentChild = null;

        if (contentSideParent instanceof IStructureComparator) {
            for (Object child : ((IStructureComparator) contentSideParent)
                    .getChildren()) {
                if (MergeContentViewerUtil.mergeContentEquals(findChild, child)) {
                    contentChild = child;
                    break;
                }
            }
        }

        return contentChild;
    }

    /**
     * Get the difference node for the given content element.
     * 
     * @param contentElement
     * 
     * @return the difference node for the given content element.
     */
    public ICompareInput locateMergeContentNode(Object contentElement) {
        /* Get path from input to content element. */
        List<Object> path =
                MergeContentViewerUtil.getPathFromAncestor(contentElement,
                        input);

        /* Correlate this with an element in the merge difference nodes input. */
        Object mergeInputNode = mergeContentInput;

        for (int i = 0; i < path.size(); i++) {
            if (mergeInputNode instanceof IDiffContainer) {
                /* Find next path segment at this level. */
                IDiffElement[] mergeNodeChildren =
                        ((IDiffContainer) mergeInputNode).getChildren();

                Object segment = path.get(i);

                mergeInputNode = null;

                for (Object child : mergeNodeChildren) {
                    if (MergeContentViewerUtil.mergeContentEquals(child,
                            segment)) {
                        mergeInputNode = child;
                        break;
                    }
                }

                /* Go around again until get to end of path */

            } else {
                /* Can't go any further down diff tree. */
                break;
            }
        }

        if (mergeInputNode instanceof ICompareInput) {
            return (ICompareInput) mergeInputNode;
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        if (element instanceof IChildCompareNode) {
            return ((IChildCompareNode) element).getParent();

        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        Object[] children = getChildren(element);

        if (children != null && children.length > 0) {
            return true;
        }

        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement, true);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.input = newInput;

        if (newInput != null && mergeContentInput == null) {
            throw new RuntimeException(
                    "Expected mergeContentInput to be set prior to actual input (use MergeContentViewer.setInput(Object, Object) not setInput(Object)");
        }
    }

    /**
     * @param mergeContentInput
     *            the mergeContentInput to set
     */
    public void setMergeContentInput(Object mergeContentInput) {
        if (mergeContentInput != null) {
            if (!(mergeContentInput instanceof ICompareInput)) {
                throw new RuntimeException(
                        "Expected mergeContentInput to be instanceof ICompareInput");
            }
        }

        this.mergeContentInput = (ICompareInput) mergeContentInput;
    }

    /**
     * @param mergeNode
     * 
     * @return If the object is an ICOmpareINput then return the appropriate
     *         side of the data for our {@link MergeContentViewerType}
     */
    private Object getDiffContentSideObject(Object mergeNode) {
        Object mergeSide = null;

        if (mergeNode instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) mergeNode;

            if (isAncestorView()) {
                mergeSide = compareInput.getAncestor();
            }
            if (isLeftView()) {
                mergeSide = compareInput.getLeft();
            }
            if (isRightView()) {
                mergeSide = compareInput.getRight();
            }
        }
        return mergeSide;
    }

    /**
     * All objects in a diff node are "equivalent for comparison" so any of the
     * ancestor/left/right object will do.
     * 
     * @param mergeNode
     * 
     * @return The model node that is the subject of given difference node.
     */
    private Object getSubjectNodeOfDiff(Object mergeNode) {
        Object mergeSide = null;

        if (mergeNode instanceof ICompareInput) {
            ICompareInput compareInput = (ICompareInput) mergeNode;

            mergeSide = compareInput.getAncestor();
            if (mergeSide == null) {
                mergeSide = compareInput.getLeft();

                if (mergeSide == null) {
                    mergeSide = compareInput.getRight();
                }
            }
        }
        return mergeSide;
    }

    private boolean isAncestorView() {
        return MergeContentViewerType.ANCESTOR.equals(viewerType);
    }

    private boolean isLeftView() {
        return MergeContentViewerType.LEFT.equals(viewerType);
    }

    private boolean isRightView() {
        return MergeContentViewerType.RIGHT.equals(viewerType);
    }
}