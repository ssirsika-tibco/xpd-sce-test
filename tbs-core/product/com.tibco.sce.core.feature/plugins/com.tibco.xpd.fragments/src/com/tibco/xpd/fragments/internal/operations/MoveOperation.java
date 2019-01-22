/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.ide.undo.MoveResourcesOperation;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;

/**
 * Fragment/category move operation.
 * 
 * @author njpatel
 */
public class MoveOperation extends FragmentElementOperation {

    private static final String MOVE_CATEGORY = Messages.MoveOperation_category_label;
    private static final String MOVE_FRAGMENT = Messages.MoveOperation_fragment_label;
    private final FragmentCategoryImpl newParent;
    private FragmentElementImpl element;

    private final MoveResourcesOperation op;
    private FragmentCategoryImpl oldParent;
    private boolean isCategory;
    private final String key;
    private final String name;
    private final String description;

    /**
     * Fragment/category move operation.
     * 
     * @param newParent
     *            target parent
     * @param element
     *            element to move
     */
    public MoveOperation(FragmentCategoryImpl newParent,
            FragmentElementImpl element) {
        super(element instanceof IFragmentCategory ? MOVE_CATEGORY
                : MOVE_FRAGMENT, newParent);

        Assert.isNotNull(newParent, "Parent category"); //$NON-NLS-1$
        Assert.isLegal(newParent.getResource() instanceof IFolder,
                "Parent category resource"); //$NON-NLS-1$
        Assert.isLegal(newParent.getResource().exists(),
                Messages.MoveOperation_parentCategory_resourceNotFound_message);

        if (element instanceof IFragmentCategory) {
            Assert.isNotNull(element,
                    Messages.MoveOperation_categoryIsNull_error_message);
            Assert
                    .isLegal(
                            element.getResource() instanceof IFolder,
                            Messages.MoveOperation_categoryResource_notFound_error_message);
            Assert
                    .isLegal(
                            element.getResource().exists(),
                            Messages.MoveOperation_categoryResource_notExist_error_message);

            isCategory = true;
        } else {
            Assert.isNotNull(element, "Fragment"); //$NON-NLS-1$
            Assert.isLegal(element.getResource() instanceof IFile,
                    "Fragment resource"); //$NON-NLS-1$
            Assert.isLegal(element.getResource().exists(),
                    "Fragment resource doesn't exist"); //$NON-NLS-1$

            isCategory = false;
        }

        this.newParent = newParent;
        this.element = element;

        // Store the details of the element
        key = element.getKey();
        name = element.getName();
        description = element.getDescription();

        IPath newPath = newParent.getResource().getFullPath();
        Collection<IResource> resourcesToMove = element.getAllResources();

        op = new MoveResourcesOperation(resourcesToMove
                .toArray(new IResource[resourcesToMove.size()]), newPath,
                isCategory ? MOVE_CATEGORY : MOVE_FRAGMENT);
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (op != null) {
            // Remove element from it's parent
            oldParent = (FragmentCategoryImpl) ((IContainedFragmentElement) element)
                    .getParent();
            if (oldParent != null) {
                oldParent.removeChild(element);
                element.dispose();
                element = null;

                refreshAndSelectInFragmentView(oldParent, null);
            }

            // Move resource
            status = op.execute(monitor, info);

            // Add element to new parent
            if (status.isOK()) {
                if (isCategory) {
                    IFolder folder = getMovedFolder(op);
                    if (folder != null) {
                        element = newParent.createChildCategory(folder);
                    }
                } else {
                    IFile file = getMovedFragmentFile(op);

                    if (file != null) {
                        element = newParent.createChildFragment(file);
                    }
                }

                if (element != null) {
                    // Update the details
                    try {
                        element.setDetails(key, name, description);
                    } catch (CoreException e) {
                        status = e.getStatus();
                    }
                    refreshAndSelectInFragmentView(newParent, element);
                }
            }
        }

        return status;
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && oldParent != null && element != null;
    }

    @Override
    public boolean canRedo() {
        return newParent != null && element != null;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (op != null) {
            // Remove category from current parent
            newParent.removeChild(element);
            element.dispose();
            element = null;

            refreshAndSelectInFragmentView(newParent, null);
        }

        // Move resource back to original location
        status = op.undo(monitor, info);

        if (status.isOK()) {
            if (isCategory) {
                IFolder folder = getMovedFolder(op);

                if (folder != null) {
                    // Add element back to old parent
                    element = oldParent.createChildCategory(folder);
                }
            } else {
                IFile file = getMovedFragmentFile(op);

                if (file != null) {
                    element = oldParent.createChildFragment(file);
                }
            }
            if (element != null) {
                // Update the element's details
                try {
                    element.setDetails(key, name, description);
                } catch (CoreException e) {
                    status = e.getStatus();
                }
                refreshAndSelectInFragmentView(oldParent, element);
            }
        }

        return status;
    }

    @Override
    public void dispose() {
        if (op != null) {
            op.dispose();
        }
        super.dispose();
    }

    /**
     * Get the restored folder (in case of category move) when this operation is
     * undone.
     * 
     * @param op
     * @return folder.
     */
    private IFolder getMovedFolder(MoveResourcesOperation op) {
        IFolder folder = null;

        if (op != null && op.getAffectedObjects() != null
                && op.getAffectedObjects().length > 0
                && op.getAffectedObjects()[0] instanceof IFolder) {
            folder = (IFolder) op.getAffectedObjects()[0];

        }

        return folder;
    }

    /**
     * Get the restored fragment file (in case of fragment move) when this
     * operation is undone.
     * 
     * @param op
     * @return file.
     */
    private IFile getMovedFragmentFile(MoveResourcesOperation op) {
        IFile file = null;

        if (op != null && op.getAffectedObjects() != null
                && op.getAffectedObjects().length > 0) {

            for (Object next : op.getAffectedObjects()) {
                if (next instanceof IFile) {
                    IFile f = (IFile) next;

                    if (f.getFileExtension() != null
                            && f.getFileExtension().equals(
                                    FragmentConsts.FRAGMENT_FILE_EXT)) {
                        file = f;
                        break;
                    }
                }
            }

        }

        return file;
    }

}
