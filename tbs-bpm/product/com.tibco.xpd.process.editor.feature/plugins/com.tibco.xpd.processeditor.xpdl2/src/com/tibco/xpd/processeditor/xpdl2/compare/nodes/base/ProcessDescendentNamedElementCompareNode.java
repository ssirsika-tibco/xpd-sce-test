/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.PackageCompareNode;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.ProcessCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification.AncestorOperationType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Class representing ANY NamedElement Node that is a descendant of
 * ProcessCompareNode
 * <p>
 * Just has a couple of useful methods to locate ancestor process compare node
 * etc <b>Therefore it is not necessary to extend this class just because a node
 * is a descendant of process</b>, it might just be useful.
 * 
 * @author aallway
 * @since 23 Nov 2010
 */
public class ProcessDescendentNamedElementCompareNode extends
        NamedElementCompareNode {

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public ProcessDescendentNamedElementCompareNode(NamedElement namedElement,
            int listIndex, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(namedElement, listIndex, feature, parentCompareNode,
                compareNodeFactory);
    }

    /**
     * NOTE that the notifierAncestorProcessNode is in SOURCE tree for
     * ADD/REPLACE and TARGET TREE for remove.
     * 
     * @param acn
     * @return
     */
    protected ProcessCompareNode getTargetAncestorProcessNode(
            AncestorOperationNotification acn) {
        if (AncestorOperationType.PRE_REMOVE.equals(acn.getType())
                || AncestorOperationType.POST_REMOVE.equals(acn.getType())) {
            /*
             * Remove is called ion the target node so atarget ancestor process
             * node will be process ancestor of this node.
             */
            return getAncestorProcessNode(this);

        } else {
            /*
             * Add and replace is called on the source node so get the ancestor
             * process node relative to the target parent of notification.
             */
            return getAncestorProcessNode(acn.getTargetParent());
        }

    }

    /**
     * 
     * @param node
     * @return Target's {@link Process} object or <code>null</code>
     */
    protected Process getAncestorProcess(Object node) {
        IChildCompareNode parent = getAncestorProcessNode(node);

        if (parent instanceof ProcessCompareNode) {
            return ((ProcessCompareNode) parent).getProcess();
        }
        return null;
    }

    /**
     * @param node
     * @return Target's {@link ProcessCompareNode} or <code>null</code>
     */
    protected ProcessCompareNode getAncestorProcessNode(Object node) {
        if (node instanceof IChildCompareNode) {
            IChildCompareNode parent = (IChildCompareNode) node;

            while (parent != null && !(parent instanceof ProcessCompareNode)) {
                Object p = parent.getParent();
                if (p instanceof IChildCompareNode) {
                    parent = (IChildCompareNode) p;
                } else {
                    parent = null;
                }
            }

            if (parent instanceof ProcessCompareNode) {
                return (ProcessCompareNode) parent;
            }
        }

        return null;
    }

    /**
     * 
     * @param targetParent
     * @return Target's {@link Package} object or <code>null</code>
     */
    protected Package getTargetPackage(EMFCompareNode targetParent) {
        IChildCompareNode parent = targetParent;
        while (parent != null && !(parent instanceof PackageCompareNode)) {
            Object p = parent.getParent();
            if (p instanceof IChildCompareNode) {
                parent = (IChildCompareNode) p;
            } else {
                parent = null;
            }
        }

        if (parent instanceof PackageCompareNode) {
            return ((PackageCompareNode) parent).getPackage();
        }
        return null;
    }
}
