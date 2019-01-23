package com.tibco.xpd.resources.ui.components.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * An implementation of a delete action for elements in EMF model. Action will
 * take a set of EMF features determining what objects can be deleted.
 * <p>
 * <i>Created: 15 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerDeleteEMFAction extends ViewerDeleteAction {
    private final Set<? extends EStructuralFeature> deletableFeatures;

    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    /**
     * @param viewer
     */
    public ViewerDeleteEMFAction(StructuredViewer viewer,
            Set<? extends EStructuralFeature> deletableFeatures) {
        super(viewer);
        this.deletableFeatures = deletableFeatures;
    }

    protected Set<? extends EStructuralFeature> getDeletableFeatures() {
        return deletableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        if (canDelete(selection)) {
            // Cancel any editing
            StructuredViewer viewer = getViewer();
            if (viewer instanceof ColumnViewer) {
                ((ColumnViewer) viewer).cancelEditing();
            }
            List<?> selectedObjects = selection.toList();
            List<Object> unwrapped = new ArrayList<>();
            for (Object o : selectedObjects) {
                Object object = AdapterFactoryEditingDomain.unwrap(o);
                unwrapped.add(object);
            }
            Command deleteCmd = createDeleteCommand(ed, unwrapped);
            ed.getCommandStack().execute(deleteCmd);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canDelete(IStructuredSelection selection) {
        if (selection != null && !selection.isEmpty()) {
            boolean anythingToDelete = false;
            List<?> selectedObjects = selection.toList();
            List<Object> unwrapped = new ArrayList<>();
            for (Object o : selectedObjects) {
                Object object = AdapterFactoryEditingDomain.unwrap(o);
                unwrapped.add(object);
                if (object instanceof EObject) {
                    final EObject eObject = (EObject) object;
                    final EStructuralFeature containingFeature =
                            eObject.eContainingFeature();
                    if (containingFeature != null
                            && getDeletableFeatures()
                                    .contains(containingFeature)) {
                        anythingToDelete = true;
                        continue;
                    } else {
                        return false;
                    }
                }
            }
            if (anythingToDelete) {
                Command deleteCmd = createDeleteCommand(ed, unwrapped);
                return deleteCmd.canExecute();
            }
        }
        return false;
    }

    protected Command createDeleteCommand(TransactionalEditingDomain ed,
            List<?> selectedObjects) {
        return DeleteCommand.create(ed, selectedObjects);
    }
}