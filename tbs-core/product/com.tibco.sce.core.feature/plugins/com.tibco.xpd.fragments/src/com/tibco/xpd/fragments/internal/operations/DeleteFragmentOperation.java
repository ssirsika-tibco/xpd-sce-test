/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ide.undo.DeleteResourcesOperation;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;

/**
 * Delete fragment operation. This will delete the given fragment and update the
 * parent category.
 * 
 * @author njpatel
 * 
 */
public class DeleteFragmentOperation extends DeleteOperation {

    private static final String DELETE_FRAGMENT = Messages.DeleteFragmentOperation_label;
    private final FragmentCategoryImpl parent;
    private FragmentImpl fragment;
    private DeleteResourcesOperation deleteFragmentOp;
    private final String key;
    private final String name;
    private final String description;

    /**
     * Delete fragment operation.
     * 
     * @param parent
     *            parent category.
     * @param fragment
     *            fragment to delete.
     */
    public DeleteFragmentOperation(FragmentCategoryImpl parent,
            FragmentImpl fragment) {
        super(DELETE_FRAGMENT, parent);

        Assert.isNotNull(parent, "Fragment Category"); //$NON-NLS-1$
        Assert.isNotNull(fragment, "Fragment to delete"); //$NON-NLS-1$
        Assert
                .isLegal(
                        parent.getChildren().contains(fragment),
                        Messages.DeleteFragmentOperation_fragmentNotValidChild_error_message);

        this.parent = parent;
        this.fragment = fragment;

        Collection<IResource> resourcesToDelete = fragment.getAllResources();
        key = fragment.getKey();
        name = fragment.getName();
        description = fragment.getDescription();

        // Create the delete operation
        if (!resourcesToDelete.isEmpty()) {
            deleteFragmentOp = new DeleteResourcesOperation(resourcesToDelete
                    .toArray(new IResource[resourcesToDelete.size()]),
                    DELETE_FRAGMENT, false);
        }
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        TreeViewer viewer = getViewer();

        if (parent != null && fragment != null) {
            // Find current index of the selected item
            int idx = -1;
            if (viewer != null) {
                idx = getIndexof(parent, fragment);
            }
            parent.removeChild(fragment);
            fragment.dispose();

            // Reselect an item in the tree
            reselectItem(parent, idx);
        }

        if (deleteFragmentOp != null) {
            status = deleteFragmentOp.execute(monitor, info);
        }

        fragment = null;

        return status;
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && deleteFragmentOp != null;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        IFile fragmentFile = null;
        // This will not be null but just checking anyway
        if (deleteFragmentOp != null) {
            status = deleteFragmentOp.undo(monitor, info);
            if (status.isOK()) {
                fragmentFile = getFragmentsFile(deleteFragmentOp
                        .getAffectedObjects());
            }
        }

        if (status.isOK() && parent != null && fragment == null) {

            if (fragmentFile != null) {
                fragment = parent.createChildFragment(fragmentFile);
                if (fragment != null) {
                    try {
                        fragment.setDetails(key, name, description);
                    } catch (CoreException e) {
                        status = e.getStatus();
                    }
                }

                // Refresh the tree and select the fragment
                refreshAndSelectInFragmentView(parent, fragment);

            } else {
                status = new Status(
                        IStatus.ERROR,
                        FragmentsActivator.PLUGIN_ID,
                        Messages.DeleteFragmentOperation_unableToResourceDelete_error_message);
            }
        }

        return status;
    }

    /**
     * This will search the restored resources for the fragments file.
     * 
     * @param affectedObjects
     *            resources affected by the
     *            {@link DeleteResourcesOperation#getAffectedObjects()
     *            DeleteResourcesOperation}.
     * @return the restored fragment's file.
     */
    private IFile getFragmentsFile(Object[] affectedObjects) {
        IFile fragmentFile = null;
        if (affectedObjects != null) {
            for (Object next : affectedObjects) {
                if (next instanceof IFile
                        && ((IFile) next).getFileExtension() != null
                        && ((IFile) next).getFileExtension().equals(
                                FragmentConsts.FRAGMENT_FILE_EXT)) {
                    fragmentFile = (IFile) next;
                    break;
                }
            }
        }
        return fragmentFile;
    }

}
