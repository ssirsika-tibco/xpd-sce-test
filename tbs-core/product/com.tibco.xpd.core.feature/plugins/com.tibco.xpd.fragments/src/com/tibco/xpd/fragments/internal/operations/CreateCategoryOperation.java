/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Create category operation. This will create the given category and add it to
 * the parent category.
 * 
 * @author njpatel
 * 
 */
public class CreateCategoryOperation extends FragmentElementOperation {

    private final String id;
    private final String key;
    private final String name;
    private final String description;
    private final boolean isSystem;
    private FragmentCategoryImpl newCategory;
    private final FragmentCategoryImpl parent;

    /**
     * Create category operation.
     * 
     * @param parent
     *            parent category.
     * @param key
     *            unique key set by the provider that can be used by itself to
     *            identify this category.
     * @param name
     *            name of the category to create.
     * @param description
     *            description of the category, <code>null</code> if no
     *            description required.
     * @param isSystem
     *            is category a system category.
     */
    public CreateCategoryOperation(FragmentCategoryImpl parent, String key,
            String name, String description, boolean isSystem) {
        super(Messages.CreateCategoryOperation_label, parent);
        this.parent = parent;

        Assert.isNotNull(parent, "Parent Fragment"); //$NON-NLS-1$
        Assert.isNotNull(name, "Fragment category name"); //$NON-NLS-1$
        Assert.isTrue(parent.getResource() instanceof IFolder
                && parent.getResource().exists(),
                "Parent category has no underlying folder"); //$NON-NLS-1$

        this.id = FragmentsUtil.createUniqueID();
        this.key = key;
        this.name = name;
        this.description = description;
        this.isSystem = isSystem;
    }

    /**
     * Get the newly create category.
     * 
     * @return created category or <code>null</code> if operation failed to
     *         create category or this operation was undone.
     */
    public FragmentCategoryImpl getCategory() {
        return newCategory;
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        try {
            newCategory = (FragmentCategoryImpl) FragmentsManager.getInstance()
                    .createCategory(parent, id, key, name, description,
                            isSystem, monitor);
            refreshAndSelectInFragmentView(parent, newCategory);
            // Edit the category name so user can change the name if they wish
            editElement(newCategory, 0);

        } catch (CoreException e) {
            status = e.getStatus();
        }

        return status;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (parent != null && newCategory != null) {

            try {
                FragmentsManager.getInstance().deleteFragmentElement(
                        newCategory, monitor);
                newCategory = null;
                refreshAndSelectInFragmentView(parent, parent);
            } catch (CoreException e) {
                status = e.getStatus();
            }
        }

        return status;
    }

}