package com.tibco.xpd.resources.ui.components.actions;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * An implementation of a MoveUp action for elements in EMF model. Action will
 * take a set of EMF features determining what objects can be movable.
 * <p>
 * <i>Created: 15 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerMoveUpEMFAction extends ViewerMoveUpAction {
    private final Set<? extends EStructuralFeature> movableFeatures;

    /**
     * @param viewer
     *            The viewer.
     * @param movableFeatures
     *            The features that can be moved.
     */
    public ViewerMoveUpEMFAction(StructuredViewer viewer,
            Set<? extends EStructuralFeature> movableFeatures) {
        super(viewer);
        this.movableFeatures = movableFeatures;
    }

    /**
     * @return The features that can be moved by this action.
     */
    protected Set<? extends EStructuralFeature> getMovableFeatures() {
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        if (selection != null && selection.size() == 1) {
            Object firstElement = selection.getFirstElement();
            Object o = AdapterFactoryEditingDomain.unwrap(firstElement);
            if (o instanceof EObject) {
                // Cancel any editing
                StructuredViewer viewer = getViewer();
                if (viewer instanceof ColumnViewer) {
                    ((ColumnViewer) viewer).cancelEditing();
                }
                final EObject eObject = (EObject) o;
                EObject next = getPrevious(viewer, eObject);
                if (next != null) {
                    checkConditionsAndMove(eObject, next);
                }

                getViewer().setSelection(new StructuredSelection(firstElement),
                        true);

            }
        }
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canMoveUp(IStructuredSelection selection,
            StructuredViewer viewer) {
        if (selection != null && selection.size() == 1) {
            Object o =
                    AdapterFactoryEditingDomain.unwrap(selection
                            .getFirstElement());
            if (o instanceof EObject) {
                final EObject eObject = (EObject) o;
                if (getPrevious(viewer, eObject) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds the previous EObject in the viewer taking filtering into account.
     * 
     * @param viewer
     *            The viewer.
     * @param current
     *            The current EObject.
     * @return The EObject before the current one in the viewer.
     */
    protected EObject getPrevious(StructuredViewer viewer, EObject current) {
        EObject previous = null;
        EObject parent = current.eContainer();
        IContentProvider content = viewer.getContentProvider();

        ViewerFilter[] filters = viewer.getFilters();

        if (content instanceof IStructuredContentProvider) {
            /*
             * We wish to find the siblings of the current element so that we
             * can get the previous sibling before which the current element is
             * to be moved.
             */
            Object[] siblings = null;

            if (content instanceof ITreeContentProvider) {
                ITreeContentProvider treeContentProvider =
                        (ITreeContentProvider) content;

                /*
                 * If the Content provider is a tree content provider the get
                 * all the top level elements first
                 */
                Object[] elements =
                        treeContentProvider.getElements(viewer.getInput());

                boolean foundCurrentElement = false;
                /*
                 * check if our current element is one of the top level elements
                 */
                for (Object object : elements) {
                    if (current.equals(object)) {
                        foundCurrentElement = true;
                        break;
                    }
                }

                if (foundCurrentElement) {
                    /*
                     * if the current element is one of the top level elements
                     * then we have found the siblings that we were looking for.
                     */
                    siblings = elements;
                } else {
                    /*
                     * else get all the sibling via the parent of the current
                     * elmeent by performing parent.getChildren.
                     */
                    siblings = treeContentProvider.getChildren(parent);
                }

            } else {
                /*
                 * If it is a structured content provider simply get the
                 * elements.
                 */
                siblings =
                        ((IStructuredContentProvider) content)
                                .getElements(parent);
            }

            if (siblings != null && siblings.length > 0) {

                if (filters != null) {
                    for (ViewerFilter filter : filters) {
                        siblings = filter.filter(viewer, parent, siblings);
                    }
                }
                EObject last = null;
                for (Object child : siblings) {
                    if (current.equals(child)) {
                        previous = last;
                        break;
                    }
                    if (child instanceof EObject) {
                        last = (EObject) child;
                    }
                }
            }
        }
        return previous;
    }

    /**
     * Checks the final conditions if the object can be moved before its
     * previous object and performs the move. Implementers may override this
     * method in case they wish to check conditions differently and call
     * {@link #doMoveBefore(EObject, EObject, EStructuralFeature)} to perform
     * the move.
     * 
     * @param current
     *            The currrent EObject.
     * @param previous
     *            The previous EObject.
     */
    protected void checkConditionsAndMove(EObject current, EObject previous) {

        EStructuralFeature containingFeature = current.eContainingFeature();
        if (containingFeature != null && containingFeature.isMany()
                && containingFeature.isOrdered()
                && getMovableFeatures().contains(containingFeature)) {

            doMoveBefore(current, previous, containingFeature);
        }
    }

    /**
     * Moves the current EOject to a position just before the previous EObject
     * in the filtered list.
     * 
     * @param objecttoMove
     *            the current object to move
     * @param previousObject
     *            the previous EObject
     * @param containingFeat
     *            the containing feature of the object being moved.
     */
    protected final void doMoveBefore(EObject objecttoMove,
            EObject previousObject, EStructuralFeature containingFeat) {

        if (objecttoMove != null && previousObject != null) {

            EStructuralFeature containingFeature = containingFeat;

            if (containingFeature == null) {
                containingFeature = objecttoMove.eContainingFeature();
            }

            if (containingFeature != null) {

                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                EObject container = objecttoMove.eContainer();
                Object containingObject = container.eGet(containingFeature);
                if (containingObject instanceof EList) {
                    final EList<?> list = (EList<?>) containingObject;
                    int i = list.indexOf(objecttoMove);
                    int j = list.indexOf(previousObject);
                    final int first = Math.min(i, j);
                    final int last = Math.max(i, j);
                    ed.getCommandStack().execute(new RecordingCommand(ed) {
                        @Override
                        protected void doExecute() {
                            list.move(first, last);
                        }
                    });
                }
            }
        }
    }
}