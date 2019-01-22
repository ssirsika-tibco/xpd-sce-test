/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EStructuralFeatureExtendedMetaData;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification.AncestorOperationType;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Base comparison node for any EMF model element
 * <p>
 * The node is constructed with an {@link EMFCompareNodeFactory} factory that
 * may be used by sub-classes to create their structure for elements within the
 * model.
 * <p>
 * This {@link EMFCompareNodeFactory} can be sub-classed for different
 * (non-generic structure based) model comparison. i.e. the factory can omit /
 * add / ignore certain children for particular elements and so on.
 * 
 * @author aallway
 * @since 30 Sep 2010
 */
public abstract class EMFCompareNode extends XpdCompareNode {

    private EMFCompareNodeFactory compareNodeFactory;

    private EStructuralFeature feature;

    /**
     * @param compareNodeFactory
     */
    public EMFCompareNode(Object parentCompareNode, EStructuralFeature feature,
            EMFCompareNodeFactory compareNodeFactory) {
        super(parentCompareNode, compareNodeFactory.getCompareNodeContentType());
        this.feature = feature;
        this.compareNodeFactory = compareNodeFactory;
    }

    /**
     * @return the compareNodeFactory
     */
    public EMFCompareNodeFactory getCompareNodeFactory() {
        return compareNodeFactory;
    }

    /**
     * The raw object that this EMF node wraps up. The type of this object is
     * dependent on the type (may be a String for attribute node, maybe an EList
     * and so on.
     * 
     * @return EMF object or value that this node wraps.
     */
    public abstract Object getObject();

    /**
     * @return The structure feature of this object in it's parent.
     */
    public final EStructuralFeature getFeature() {
        return feature;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return child compare nodes for emf model children
     */
    @Override
    protected final Object[] createChildren() {
        return createChildren(compareNodeFactory);
    }

    /**
     * Implementation of {@link XpdCompareNode#createChildren()} but that
     * provides the {@link EMFCompareNodeFactory} that can should be used to
     * create children for given EObjects.
     * <p>
     * Not ethat it is not a requirement that all children are
     * {@link EMFCompareNode} implementations, or even {@link XpdCompareNode}
     * implementations. Specialised {@link EMFCompareNodeFactory} sub-classes
     * can return other compare nodes of their own choosing (although the
     * {@link StructureDiffViewer} / Differencer needs them to implement
     * {@link ITypedElement}, {@link IStructureComparator})
     * 
     * @param compareNodeFactory
     * 
     * @return children
     */
    protected abstract Object[] createChildren(
            EMFCompareNodeFactory compareNodeFactory);

    /**
     * This method gives the concrete class the opportunity to exclude creation
     * of children for the given feature
     * <p>
     * <b>Note that excluding children will also exclude them from difference
     * comparison an copy non-conflicting Children from right / left on
     * three-way comparison merge!).</b>
     * 
     * @param feature
     * @param child
     * 
     * @return <code>true</code> to exclude child.
     */
    protected boolean excludeChildFeature(EStructuralFeature feature,
            Object child) {
        return false;
    }

    /**
     * In order for the same {@link EMFCompareMergeViewer} comapre tree
     * left/right viewer to be used again when user re-selects the input by
     * dbl-click other item in the tree in top window, all getTypes() shoudl
     * return same thing (I think).
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getType()
     * 
     * @return
     */
    @Override
    public String getType() {
        return compareNodeFactory.getCompareNodeContentType();
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        /*
         * If there is no 'feature' for this element then we must be at the root
         * of EMF model
         */
        if (feature == null) {
            return "__root__";
        }

        /*
         * Use standard the ExtendedMetaData "name" attribute if there is one,
         * fall-back on the simple feature name if there is no meta data name OR
         * it begins with ":" because that will just be a default name that EMF
         * gives things when they're not named explicitly by us.
         */
        if (feature instanceof EStructuralFeatureExtendedMetaData.Holder) {
            EStructuralFeatureExtendedMetaData extendedMetaData =
                    ((EStructuralFeatureExtendedMetaData.Holder) feature)
                            .getExtendedMetaData();
            if (extendedMetaData != null) {
                String name = extendedMetaData.getName();
                if (name != null && name.length() > 0 && !name.startsWith(":")) { //$NON-NLS-1$
                    return name;
                }
            }
        }

        return feature.getName();
    }

    /**
     * Make our sub-classes use an actual object to locate override
     * {@link #calculateLocationInParent(Object, Object)} instead
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#calculateLocationInParent()
     * 
     * @return location identifier of this object in its parent
     */
    @Override
    protected final String calculateLocationInParent() {
        return calculateLocationInParent(getObject(), getFeature(), getParent());
    }

    /**
     * Equivalent of {@link #getLocationInParent()} but can be used to calculate
     * location (segment) for any element - hence can be used by
     * {@link EListCompareNode} and {@link EFeatureMapCompareNode} when locating
     * the insertion position for a node based on it's position relative to it's
     * siblings in the source list. AND more importantly, based on the ACTUAL
     * eList rather than the "latest cached set of compare nodes" - so will work
     * when multiple changes performed in same list without recaching it).
     * <p>
     * When this method is invoked, it is assumed that the given object if FOR
     * the same parent type as this node itself.
     * 
     * @param object
     * @param parentNode
     *            This may or may NOT be the parent node of this node (if not it
     *            will be the equivalent parent ndoe in another tree)
     * 
     * @return String uniquely identifyign this element in it's parent (i.e.
     *         this node's segment of the overall location path.
     */
    public String calculateLocationInParent(Object object,
            EStructuralFeature feature, Object parentNode) {
        if (feature == null) {
            return "__root__";
        }

        return calculateDefaultLocationInParent(feature);
    }

    /**
     * @param feature
     * @return Default location in parent based purely on feature id (hjence
     *         will not work for elements in a list.
     */
    public static String calculateDefaultLocationInParent(
            EStructuralFeature feature) {
        String location = feature.getFeatureID() + ":" + feature.getName(); //$NON-NLS-1$
        return location;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#isEditable()
     * 
     * @return true if there is a WorkingCopyCompareNode that is editable.
     */
    @Override
    public boolean isEditable() {
        /*
         * We are editable if node has been set to editable AND the ancestor
         * working copy node is editable.
         */
        WorkingCopyCompareNode workingCopyCompareNode =
                getWorkingCopyCompareNodeAncestor();

        if (workingCopyCompareNode != null) {
            return workingCopyCompareNode.isEditable();
        }

        return false;
    }

    /**
     * @return The ancestor node that wraps the model#s working copy or
     *         <code>null</code> if cannot be found.
     */
    public WorkingCopyCompareNode getWorkingCopyCompareNodeAncestor() {
        if (this instanceof WorkingCopyCompareNode) {
            return (WorkingCopyCompareNode) this;
        }

        Object parent = getParent();

        while (parent instanceof IChildCompareNode) {
            if (parent instanceof WorkingCopyCompareNode) {
                break;
            }
            parent = ((IChildCompareNode) parent).getParent();
        }

        if (parent instanceof WorkingCopyCompareNode) {
            return (WorkingCopyCompareNode) parent;
        }

        return null;
    }

    /**
     * @return The working copy that this EMF node's model object belongs to.
     */
    public WorkingCopy getWorkingCopy() {
        WorkingCopyCompareNode wNode = getWorkingCopyCompareNodeAncestor();
        if (wNode != null) {
            return wNode.getWorkingCopy();
        }
        return null;
    }

    /**
     * This method is self contained (builds a command and execute's on
     * destinaiton sides working copy command stack) ONLY just in case an EMF
     * node is used from a non- {@link EMFCompareMergeViewer}.
     *<p>
     * In reality it is expected that the getReplaceCommand method will be used
     * by {@link EMFCompareMergeViewer} (so that multiple replacements in a
     * single operation can be compiled into a single command)
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#replace(org.eclipse.compare.ITypedElement,
     *      org.eclipse.compare.ITypedElement)
     * 
     * @param dest
     * @param src
     * 
     * @return replacement for destination child node or <code>null</code> if
     *         removed.
     */
    @Override
    public final ITypedElement replace(final ITypedElement dest,
            final ITypedElement src) {
        boolean useRecordingCommandScheme = true;

        /*
         * Save object to use for locating child in this node (so can return the
         * new value for it after re-caching children as required by the
         * interface.
         */
        Object objectForLocation = null;

        if (dest != null) {
            if (dest instanceof XpdCompareNode) {
                objectForLocation = ((XpdCompareNode) dest).getLocationPath();
            } else {
                objectForLocation = dest;
            }

        } else if (src != null) {
            if (src instanceof XpdCompareNode) {
                objectForLocation = ((XpdCompareNode) src).getLocationPath();
            } else {
                objectForLocation = src;
            }
        }

        Object newDest = dest;

        WorkingCopyCompareNode workingCopyCompareNode =
                getWorkingCopyCompareNodeAncestor();

        if (workingCopyCompareNode != null) {
            AbstractWorkingCopy workingCopy =
                    workingCopyCompareNode.getWorkingCopy();

            /*
             * Get the command to replace the dest child with the source child.
             */
            EditingDomain editingDomain = workingCopy.getEditingDomain();
            if (editingDomain != null) {

                Command cmd =
                        new RecordingCommand(
                                (TransactionalEditingDomain) editingDomain) {

                            @Override
                            protected void doExecute() {
                                if (dest instanceof EMFCompareNode) {
                                    if (src == null) {
                                        /*
                                         * Target exists but source does not,
                                         * remove target.
                                         */
                                        ((EMFCompareNode) dest)
                                                .removeYourself(EMFCompareNode.this);

                                    } else {
                                        /*
                                         * Target exists and source exists,
                                         * replace target.
                                         */
                                        ((EMFCompareNode) src)
                                                .replaceYourself((ITypedElement) ((EMFCompareNode) dest)
                                                        .getParent(),
                                                        (EMFCompareNode) dest);
                                    }

                                } else {
                                    if (src instanceof EMFCompareNode) {
                                        /*
                                         * Target doesn't exist yet, add/set
                                         * child.
                                         */
                                        ((EMFCompareNode) src)
                                                .addYourself(EMFCompareNode.this);

                                    } else {
                                        throw new RuntimeException(
                                                "Cannot replace null child with null child! EObject: "
                                                        + getName());
                                    }
                                }
                            }
                        };

                /*
                 * Execute the command if possible.
                 */
                if (cmd != null && cmd.canExecute()) {
                    if (editingDomain.getCommandStack() != null) {
                        /*
                         * Dispose the children of this object to get them
                         * reloaded on next getChildren() call.
                         */
                        this.setNeedsRefresh();

                        /* Execute the command */
                        editingDomain.getCommandStack().execute(cmd);

                        WorkingCopyCompareNode wcNode =
                                getWorkingCopyCompareNodeAncestor();
                        if (wcNode != null) {
                            wcNode.refreshAsNecessary();
                        } else {
                            this.refreshAsNecessary();
                        }

                        /*
                         * Find the new destination node that replaced original
                         * destination node.
                         */

                        if (objectForLocation != null) {
                            Object[] children = getChildren();
                            for (Object child : children) {
                                Object childLocationObject;

                                /*
                                 * Use getObjectForLocation in XpdCompareNode's
                                 * as this is what defines the object to use for
                                 * tree location equivalence.
                                 */
                                if (child instanceof XpdCompareNode) {
                                    childLocationObject =
                                            ((XpdCompareNode) child)
                                                    .getLocationPath();
                                } else {
                                    childLocationObject = child;
                                }

                                if (objectForLocation
                                        .equals(childLocationObject)) {
                                    newDest = child;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return (ITypedElement) newDest;
    }

    /**
     * Add a COPY of yourself to given target.
     * 
     * @param targetParent
     */
    public final void addYourself(ITypedElement targetParent) {
        AncestorOperationNotification acn =
                new AncestorOperationNotification(
                        AncestorOperationType.PRE_ADD, this, targetParent,
                        getObject(), null);

        notifyDescendentsAscendents(acn);

        Object newEMFObject = doAddYourself(targetParent);

        if (newEMFObject != null) {
            acn.setType(AncestorOperationType.POST_ADD);
            acn.setNewEMFObject(newEMFObject);

            notifyDescendentsAscendents(acn);
        }
    }

    /**
     * Add a COPY of yourself to given target.
     * 
     * @param targetParent
     * 
     * @return new object added to parent.
     */
    protected abstract Object doAddYourself(ITypedElement targetParent);

    /**
     * Remove yourself from the given target.
     * 
     * @param targetParent
     */
    public final void removeYourself(ITypedElement targetParent) {
        AncestorOperationNotification acn =
                new AncestorOperationNotification(
                        AncestorOperationType.PRE_REMOVE, this, targetParent,
                        getObject(), null);

        notifyDescendentsAscendents(acn);

        doRemoveYourself(targetParent);

        acn.setType(AncestorOperationType.POST_REMOVE);

        notifyDescendentsAscendents(acn);
    }

    /**
     * Remove yourself from the given target.
     * 
     * @param targetParent
     */
    protected abstract void doRemoveYourself(ITypedElement targetParent);

    /**
     * Replace the equivalent of yourself (targetNode) in the target parent with
     * a copy of yourself.
     * 
     * @param targetParent
     * @param targetNode
     */
    public final void replaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {

        AncestorOperationNotification acn =
                new AncestorOperationNotification(
                        AncestorOperationType.PRE_REPLACE, this, targetParent,
                        getObject(), null);
        acn.setReplacingTargetNode(targetNode);

        notifyDescendentsAscendents(acn);

        Object newEMFObject = doReplaceYourself(targetParent, targetNode);

        if (newEMFObject != null) {
            acn.setType(AncestorOperationType.POST_REPLACE);
            acn.setNewEMFObject(newEMFObject);

            notifyDescendentsAscendents(acn);
        }
    }

    /**
     * Replace the equivalent of yourself in the target parent witha copy of
     * yourself.
     * 
     * @param targetParent
     * @param targetNode
     *            TODO
     * 
     * @return new object replaced in parent.
     */
    protected abstract Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode);

    /**
     * Find this node (or the equivalent of this node) in the given node's
     * descendents.
     * 
     * @param ancestorNode
     * 
     * @return me or equivalent of me in the descendeds of ancestorNode or
     *         <code>null</code> if not found.
     */
    public EMFCompareNode findMeInDescendents(Object ancestorNode) {
        if (this.equals(ancestorNode)) {
            return (EMFCompareNode) ancestorNode;
        }

        if (ancestorNode instanceof XpdCompareNode) {
            for (Object childNode : ((XpdCompareNode) ancestorNode)
                    .getChildren()) {
                EMFCompareNode found = findMeInDescendents(childNode);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    /**
     * Notify ascendents and descendents of the operation performed on this
     * node.
     * 
     * @param acn
     */
    private void notifyDescendentsAscendents(AncestorOperationNotification acn) {
        notifyDescendents(acn);
        notifyAscendents(acn);
    }

    /**
     * Recursively notify all descendeents of a change that is about to
     * 
     * @param notifier
     * @param beforeChange
     */
    private void notifyDescendents(AncestorOperationNotification acn) {
        Object[] children = getChildren();
        if (children != null) {
            for (Object child : children) {
                if (child instanceof EMFCompareNode) {
                    /* Notify child of operation */
                    ((EMFCompareNode) child).ancestorOperationPerformed(acn);

                    /* Notify descendednts. */
                    ((EMFCompareNode) child).notifyDescendents(acn);

                } else if (child instanceof XpdCompareNode) {
                    recursNotifyThruNonEMFChildren(acn, (XpdCompareNode) child);
                }

            }
        }

        return;
    }

    /**
     * For non-EMF children recurs thru their descendents looking EMF nodes to
     * notify.
     * 
     * @param child
     */
    private void recursNotifyThruNonEMFChildren(
            AncestorOperationNotification acn, XpdCompareNode node) {
        for (Object child : node.getChildren()) {
            if (child instanceof EMFCompareNode) {
                ((EMFCompareNode) child).notifyDescendents(acn);

            } else if (child instanceof XpdCompareNode) {
                recursNotifyThruNonEMFChildren(acn, (XpdCompareNode) child);
            }
        }
    }

    /**
     * Notify our ancestors that an operation is being performed on this node.
     * 
     * @param acn
     */
    private void notifyAscendents(AncestorOperationNotification acn) {

        Object parent = getParent();
        while (parent != null) {
            if (parent instanceof EMFCompareNode) {
                ((EMFCompareNode) parent).descendentOperationPerformed(acn);
            }

            if (parent instanceof IChildCompareNode) {
                parent = ((IChildCompareNode) parent).getParent();
            } else {
                parent = null;
            }
        }

        return;
    }

    /**
     * Notfication that an operation on one of this node's ancestors was
     * performed.
     * 
     * @param acn
     */
    protected void ancestorOperationPerformed(AncestorOperationNotification acn) {
        return;
    }

    /**
     * Notificaiton that one of the descendents of this node is having / has had
     * an operation perfortmed on it.
     * 
     * @param acn
     */
    protected void descendentOperationPerformed(
            AncestorOperationNotification acn) {
        return;
    }

}
