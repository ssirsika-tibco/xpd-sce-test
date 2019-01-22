package com.tibco.xpd.resources.ui.compare.nodes.emf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;

/**
 * An {@link EListCompareNode} that represents only a portion of the given
 * EList.
 * <p>
 * This is useful in situations where an aritficual 'grouping' of elements
 * within a single list is required.
 * <p>
 * It also provides the ability for the EList to NOT be a direct child of the
 * parent compare node (i.e. the actual parent is virtualised and provided by
 * the sub-class).
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public abstract class FilteredEListCompareNode extends WrappedEListCompareNode {

    private String locationInParentSuffix;

    /**
     * 
     * @param list
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     * @param label
     * @param image
     * @param locationInParentSuffix
     *            This is required when splitting the SAME list into two or more
     *            lists within the SAME parent (each must be uniquely id'd from
     *            the others).
     */
    public FilteredEListCompareNode(EList list, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory,
            String label, Image image, String locationInParentSuffix) {
        super(list, feature, parentCompareNode, compareNodeFactory, label,
                image);
        this.locationInParentSuffix = locationInParentSuffix;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#calculateLocationInParent(java.lang.Object,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param object
     * @param feature
     * @param parentNode
     * @return
     */
    @Override
    public String calculateLocationInParent(Object object,
            EStructuralFeature feature, Object parentNode) {
        return super.calculateLocationInParent(object, feature, parentNode)
                + locationInParentSuffix;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#excludeChildFeature(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object)
     * 
     * @param feature
     * @param child
     * 
     * @return <code>true</code> if this EList member should be filtered out of
     *         this object.
     */
    @Override
    protected boolean excludeChildFeature(EStructuralFeature feature,
            Object child) {
        return !includeInVirtualParent(child);
    }

    /**
     * @param child
     * @return <code>true</code> if the object is a child of the process.
     */
    protected abstract boolean includeInVirtualParent(Object object);

    /**
     * This method allows the sub-class to return the target EList (which need
     * not necessarily be a feature of it's wrapped EObject).
     * 
     * @param targetParent
     * 
     * @return EList that is the target for children of this list.
     */
    protected abstract EList getTargetList(EMFCompareNode targetParent);

    /**
     * * Only add our own filtered entries from target List.
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    public Object doAddYourself(ITypedElement targetParent) {
        EList targetList = getTargetList((EMFCompareNode) targetParent);
        if (targetList != null) {

            ArrayList<Object> copies = new ArrayList<Object>();

            for (Object child : getChildren()) {
                Object copy = null;

                if (child instanceof EObjectCompareNode) {
                    copy =
                            EcoreUtil.copy(((EObjectCompareNode) child)
                                    .getEObject());
                } else if (child instanceof EAttributeCompareNode) {
                    copy = ((EAttributeCompareNode) child).getEAttributeValue();
                } else {
                    throw new RuntimeException(
                            "Unexpected child element type encountered adding: '" + this + "' To: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$    
                }

                if (copy != null) {
                    copies.add(copy);
                }
            }

            if (copies.size() > 0) {
                targetList.addAll(copies);
            }

            return copies;
        }

        throw new RuntimeException(
                "Problem encountered adding: '" + this + "' To: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$    

    }

    /**
     * Only remove our own filtered entries from target List.
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    public void doRemoveYourself(ITypedElement targetParent) {
        EList targetList = getTargetList((EMFCompareNode) targetParent);
        if (targetList != null) {

            /*
             * Remove all the entries that would not be excluded from teh target
             * list (i.e. the ones that this filter WOULD include are the ones
             * that are being replaced).
             */
            List<Object> toRemove = new ArrayList<Object>();
            for (Object targetChild : targetList) {
                // TODO - I THINK THIS MAYBE ONLY WORKS FOR WRAPPED LISTS (WHAT
                // IS FEATURE OF CHILD OF LIST??!
                EStructuralFeature feature = getFeature();

                if (!excludeChildFeature(feature, targetChild)) {
                    toRemove.add(targetChild);
                }
            }

            if (toRemove.size() > 0) {
                targetList.removeAll(toRemove);
            }

            return;
        }

        throw new RuntimeException(
                "Problem encountered removing: '" + this + "' From: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      EMFCompareNode)
     * 
     * @param targetParent
     * @return
     */
    @Override
    public Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        EList targetList = getTargetList((EMFCompareNode) targetParent);
        if (targetList != null) {
            /*
             * Remove all the entries that would not be excluded from teh target
             * list (i.e. the ones that this filter WOULD include are the ones
             * that are being replaced).
             */
            doRemoveYourself(targetParent);

            return doAddYourself(targetParent);

        }

        throw new RuntimeException(
                "Problem encountered replacing equivalent of: '" + this + "' In: '" + targetParent + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
