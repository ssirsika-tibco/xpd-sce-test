package com.tibco.xpd.resources.ui.compare.nodes.emf;

import org.eclipse.compare.ITypedElement;

/**
 * Notification of an operation performed on one of an {@link EMFCompareNode}'s
 * ancestors.
 * 
 * 
 * @author aallway
 * @since 22 Oct 2010
 */
public class AncestorOperationNotification {
    /**
     * The operation performed.
     */
    public enum AncestorOperationType {
        PRE_ADD, POST_ADD, PRE_REMOVE, POST_REMOVE, PRE_REPLACE, POST_REPLACE
    }

    /**
     * The targetParent node passed to {@link #notifierNode}
     * add/remove/replaceYourself() method
     */
    private ITypedElement targetParent;

    /** The node whose add/remove/replaceYourself() method was invoked. */
    private EMFCompareNode notifierNode;

    /** The old EMF model object (if there is one) */
    private Object oldEMFObject;

    /** The new EMF value if there is one. */
    private Object newEMFObject;

    /** The type of cahneg performed. */
    private AncestorOperationType type;

    private EMFCompareNode replacingTargetNode = null;

    /**
     * @param notifierNode
     * @param targetParent
     * @param oldEMFObject
     * @param newEMFObject
     */
    public AncestorOperationNotification(AncestorOperationType type,
            EMFCompareNode notifierNode, ITypedElement targetParent,
            Object oldEMFObject, Object newEMFObject) {
        this.type = type;
        this.notifierNode = notifierNode;
        this.targetParent = targetParent;
        this.oldEMFObject = oldEMFObject;
        this.newEMFObject = newEMFObject;
    }

    /**
     * @return the targetParent
     */
    public ITypedElement getTargetParent() {
        return targetParent;
    }

    /**
     * @return the notifierNode
     */
    public EMFCompareNode getNotifierNode() {
        return notifierNode;
    }

    /**
     * @return the oldEMFObject
     */
    public Object getOldEMFObject() {
        return oldEMFObject;
    }

    /**
     * @return the newEMFObject
     */
    public Object getNewEMFObject() {
        return newEMFObject;
    }

    /**
     * @return the type
     */
    public AncestorOperationNotification.AncestorOperationType getType() {
        return type;
    }

    /**
     * @param oldEMFObject
     *            the oldEMFObject to set
     */
    public void setOldEMFObject(Object oldEMFObject) {
        this.oldEMFObject = oldEMFObject;
    }

    /**
     * @param newEMFObject
     *            the newEMFObject to set
     */
    public void setNewEMFObject(Object newEMFObject) {
        this.newEMFObject = newEMFObject;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(AncestorOperationType type) {
        this.type = type;
    }

    /**
     * @return the replacingTargetNode
     */
    public EMFCompareNode getReplacingTargetNode() {
        return replacingTargetNode;
    }

    /**
     * @param replacingTargetNode
     *            the replacingTargetNode to set
     */
    public void setReplacingTargetNode(EMFCompareNode replacingTargetNode) {
        this.replacingTargetNode = replacingTargetNode;
    }

}