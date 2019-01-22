/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.eclipse.ui.ide.undo.CopyResourcesOperation;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Fragment/category copy operation. This will be run during drag-copy in the
 * fragments view.
 * 
 * @author njpatel
 * 
 */
public class CopyOperation extends FragmentElementOperation {

    private CopyResourcesOperation op;
    private final FragmentCategoryImpl target;
    private final FragmentElementImpl element;
    private FragmentElementImpl copyElement;
    private final String nameOfCopy;
    private final String id;
    private final String key;
    private final String description;

    /**
     * Fragment/category copy operation.
     * 
     * @param target
     *            target of copy.
     * @param element
     *            element to copy.
     * @param nameOfCopy
     *            name to give the copy element.
     */
    public CopyOperation(FragmentCategoryImpl target,
            FragmentElementImpl element, String nameOfCopy) {
        super(
                element instanceof IFragmentCategory ? Messages.CopyOperation_category_label
                        : Messages.CopyOperation_fragment_label, target);

        Assert.isNotNull(target, "Parent category"); //$NON-NLS-1$
        Assert.isLegal(target.getResource() instanceof IFolder,
                "Parent category resource"); //$NON-NLS-1$
        Assert
                .isLegal(
                        target.getResource().exists(),
                        Messages.CopyOperation_parentCategory_resourceNotFound_err_message);

        if (element instanceof IFragmentCategory) {
            Assert
                    .isNotNull(
                            element,
                            Messages.CopyOperation_fragmentCategory_notFound_error_message);
            Assert
                    .isLegal(
                            element.getResource() instanceof IFolder,
                            Messages.CopyOperation_categoryResource_notFound_error_message);
            Assert
                    .isLegal(
                            element.getResource().exists(),
                            Messages.CopyOperation_fragmentCategory_resourceNotFound_error_message);
        } else {
            Assert.isNotNull(element, "Fragment"); //$NON-NLS-1$
            Assert.isLegal(element.getResource() instanceof IFile,
                    "Fragment resource"); //$NON-NLS-1$
            Assert.isLegal(element.getResource().exists(),
                    "Fragment resource doesn't exist"); //$NON-NLS-1$
        }

        this.id = FragmentsUtil.createUniqueID();
        this.target = target;
        this.element = element;
        this.description = element.getDescription();
        this.key = element.getKey();
        this.nameOfCopy = nameOfCopy != null ? nameOfCopy : element
                .getNameLabel();
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

        IStatus status = Status.OK_STATUS;

        Collection<IResource> resources = element.getAllResources();
        List<IPath> paths = new ArrayList<IPath>();

        IResource resource = target.getResource();

        if (resource instanceof IFolder) {
            IPath destPath = resource.getFullPath().append(id);

            for (IResource res : resources) {
                if (res.getFileExtension() != null) {
                    paths
                            .add(destPath.addFileExtension(res
                                    .getFileExtension()));
                } else {
                    paths.add(destPath);
                }
            }
            op = new CopyResourcesOperation(resources
                    .toArray(new IResource[resources.size()]), paths
                    .toArray(new IPath[paths.size()]),
                    Messages.CopyOperation_copy_operation_label);

            status = op.execute(monitor, info);

            if (status.isOK()) {
                Object[] objects = op.getAffectedObjects();

                if (objects != null && objects.length > 0) {
                    if (element instanceof IFragmentCategory) {
                        // Copied category
                        if (objects[0] instanceof IFolder) {
                            copyElement = target
                                    .createChildCategory((IFolder) objects[0]);
                        }
                    } else /* copied fragment */{
                        IFile fragmentFile = null;

                        for (Object obj : objects) {
                            if (obj instanceof IFile) {
                                IFile file = (IFile) obj;

                                if (file.getFileExtension() != null
                                        && file
                                                .getFileExtension()
                                                .equals(
                                                        FragmentConsts.FRAGMENT_FILE_EXT)) {
                                    fragmentFile = file;
                                    break;
                                }
                            }
                        }

                        if (fragmentFile != null) {
                            copyElement = target
                                    .createChildFragment(fragmentFile);
                        }
                    }

                    if (copyElement != null) {
                        try {
                            copyElement
                                    .setDetails(key, nameOfCopy, description);
                        } catch (CoreException e) {
                            FragmentsActivator.getDefault().getLogger()
                                    .error(e);
                        }

                        refreshAndSelectInFragmentView(target, copyElement);
                    }
                }
            }
        }

        return status;
    }

    @Override
    public boolean canRedo() {
        return super.canRedo() && op == null && copyElement == null;
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && op != null && copyElement != null;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        IStatus status = Status.OK_STATUS;

        if (target != null && copyElement != null) {
            target.removeChild(copyElement);
            copyElement.dispose();

            refreshAndSelectInFragmentView(target, target);

            if (op != null) {
                status = op.undo(monitor, info);
            }

        }

        return status;
    }

}
