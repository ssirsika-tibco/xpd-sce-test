/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.ide.undo.DeleteResourcesOperation;

import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;

/**
 * Delete category operation. This will delete the given category and update the
 * parent category.
 * 
 * @author njpatel
 * 
 */
public class DeleteCategoryOperation extends DeleteOperation {

    private static final String DELETE_FRAGMENT_CATEGORY = Messages.DeleteCategoryOperation_label;
    private FragmentCategoryImpl parent;
    private FragmentCategoryImpl category;
    private DeleteResourcesOperation deleteResourceOperation;
    private String key;
    private String name;
    private String description;
    private boolean isSystem;

    /**
     * Delete category operation.
     * 
     * @param parent
     *            parent category.
     * @param category
     *            category to delete.
     */
    public DeleteCategoryOperation(FragmentCategoryImpl parent,
            FragmentCategoryImpl category) {
        super(DELETE_FRAGMENT_CATEGORY, category);

        Assert.isNotNull(parent, "Parent fragment category"); //$NON-NLS-1$
        Assert.isNotNull(category, "Fragment category to delete"); //$NON-NLS-1$
        Assert
                .isLegal(
                        parent.getChildren().contains(category),
                        Messages.DeleteCategoryOperation_categoryNotValidChild_error_message);

        this.parent = parent;
        this.category = category;
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (monitor != null) {
            monitor
                    .beginTask(
                            Messages.DeleteCategoryOperation_deleteCategory_monitor_shortdesc,
                            2);
        }

        IResource res = category.getResource();

        int idx = getIndexof(parent, category);
        // Record the details so that they can re-applied on undo
        key = category.getKey();
        name = category.getName();
        description = category.getDescription();
        isSystem = category.isSystem();

        // Remove category from its parent
        parent.removeChild(category);
        category.dispose();
        category = null;

        reselectItem(parent, idx);

        if (monitor != null) {
            monitor.worked(1);
        }

        // Delete the resource
        if (res != null) {
            deleteResourceOperation = new DeleteResourcesOperation(
                    new IResource[] { res }, DELETE_FRAGMENT_CATEGORY, false);
            status = deleteResourceOperation.execute(monitor, info);

            if (monitor != null) {
                monitor.worked(1);
            }
        }

        if (monitor != null) {
            monitor.done();
        }

        return status;
    }

    @Override
    public boolean canRedo() {
        return super.canRedo() && deleteResourceOperation == null
                && category != null;
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && deleteResourceOperation != null
                && category == null;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (deleteResourceOperation != null) {
            if (monitor != null) {
                monitor
                        .beginTask(
                                Messages.DeleteCategoryOperation_undoDelete_monitor_shortdesc,
                                2);
            }

            status = deleteResourceOperation
                    .undo(monitor != null ? new SubProgressMonitor(monitor, 1)
                            : null, info);

            if (monitor != null) {
                monitor.worked(1);
            }

            if (status.isOK()) {
                IFolder folder = getFolder(deleteResourceOperation);
                // Dispose the resource operation
                deleteResourceOperation.dispose();
                deleteResourceOperation = null;

                if (folder != null) {
                    // Recreate the category
                    category = parent.createChildCategory(folder);
                    if (category != null) {
                        try {
                            // Restore the details
                            category.setDetails(key, name, description);
                            category.setIsSystem(isSystem);
                        } catch (CoreException e) {
                            status = e.getStatus();
                        }
                    }
                    refreshAndSelectInFragmentView(parent, category);

                    if (monitor != null) {
                        monitor.worked(1);
                    }
                }
            }
        }

        if (monitor != null) {
            monitor.done();
        }

        return status;
    }

    /**
     * Get the folder restored when this operation is undone.
     * 
     * @param operation
     * @return folder.
     */
    private IFolder getFolder(DeleteResourcesOperation operation) {
        IFolder folder = null;

        if (operation != null) {
            Object[] affectedObjects = operation.getAffectedObjects();

            if (affectedObjects != null
                    && affectedObjects[0] instanceof IFolder) {
                folder = (IFolder) affectedObjects[0];
            }
        }

        return folder;
    }

}
