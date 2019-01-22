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
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.fragments.FragmentsContributor.ClipboardFragmentData;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Create fragment operation. This will create the given fragment and add it to
 * the parent category.
 * 
 * @author njpatel
 * 
 */
public class CreateFragmentOperation extends FragmentElementOperation {

    private final String id;
    private final String key;
    private final String name;
    private final String description;
    private final String data;
    private final ImageData imgData;
    private FragmentImpl newFragment;
    private final FragmentCategoryImpl parent;

    /**
     * Create fragment operation.
     * 
     * @param parent
     *            parent category.
     * @param key
     *            unique key set by the provider that can be used by itself to
     *            identify this category.
     * @param name
     *            name of fragment to create.
     * @param description
     *            description of fragment, <code>null</code> if no description
     *            required.
     * @param data
     *            fragment data.
     * @param imageData
     *            fragment image data, <code>null</code> if no image available.
     */
    public CreateFragmentOperation(FragmentCategoryImpl parent, String key,
            String name, String description, String data, ImageData imageData) {
        super(Messages.CreateFragmentOperation_label, parent);

        Assert.isNotNull(name, "Fragment name"); //$NON-NLS-1$
        Assert.isNotNull(data, "Fragment data"); //$NON-NLS-1$
        Assert.isNotNull(parent, "Parent category"); //$NON-NLS-1$
        Assert.isTrue(parent.getResource() instanceof IFolder
                && parent.getResource().exists(),
                "Parent category has no underlying folder"); //$NON-NLS-1$

        this.id = FragmentsUtil.createUniqueID();
        this.parent = parent;
        this.key = key;
        this.name = name;
        this.description = description;
        this.data = data;
        this.imgData = imageData;
    }

    /**
     * Create fragment operation.
     * 
     * @param parent
     *            parent category.
     * @param clipboardData
     *            data from the clipboard to generate the fragment.
     */
    public CreateFragmentOperation(FragmentCategoryImpl parent,
            ClipboardFragmentData clipboardData) {
        this(parent, null, clipboardData.getName(), clipboardData
                .getDescription(), clipboardData.getData(), clipboardData
                .getImageData());
    }

    /**
     * Get the created fragment.
     * 
     * @return created fragment or <code>null</code> if failed to create, or
     *         this operation was undone.
     */
    public FragmentImpl getFragment() {
        return newFragment;
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (parent != null && newFragment == null) {
            try {
                newFragment = (FragmentImpl) FragmentsManager.getInstance()
                        .createFragment(parent, id, key, name, description,
                                data, imgData, monitor);
                refreshAndSelectInFragmentView(parent, newFragment);
            } catch (CoreException e) {
                status = e.getStatus();
            }
        }

        return status;
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && parent != null && newFragment != null;
    }

    @Override
    public boolean canRedo() {
        return super.canRedo() && parent != null && newFragment == null;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (parent != null && newFragment != null) {
            try {
                FragmentsManager.getInstance().deleteFragmentElement(
                        newFragment, monitor);
                newFragment = null;
                refreshAndSelectInFragmentView(parent, parent);
            } catch (CoreException e) {
                status = e.getStatus();
            }
        }

        return status;
    }
}
