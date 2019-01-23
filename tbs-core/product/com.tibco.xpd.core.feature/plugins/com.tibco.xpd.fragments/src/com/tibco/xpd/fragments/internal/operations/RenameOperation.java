/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;

/**
 * Rename operation for a fragment elements.
 * 
 * @author njpatel
 * 
 */
public class RenameOperation extends FragmentElementOperation {

    private final FragmentElementImpl element;
    private final String newName;
    private final String oldName;

    /**
     * Rename operation for a fragment elements.
     * 
     * @param element
     *            fragment element to rename.
     * @param name
     *            new name to set.
     */
    public RenameOperation(FragmentElementImpl element, String name) {
        super(
                element instanceof IFragmentCategory ? Messages.RenameOperation_category_label
                        : Messages.RenameOperation_fragment_label, element);

        Assert.isNotNull(element, "Fragment element"); //$NON-NLS-1$
        Assert.isNotNull(name, "Fragment name"); //$NON-NLS-1$
        this.element = element;
        this.newName = name;

        this.oldName = element.getName();
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

        return doExecute(monitor, info, newName,
                Messages.RenameOperation_renameOperation_label);
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

        return doExecute(monitor, info, oldName,
                Messages.RenameOperation_undoRenameOperation_label);
    }

    /**
     * Rename the fragment element with the given name.
     * 
     * @param monitor
     * @param info
     * @param name
     *            name to set
     * @param progressMessage
     *            message to set in the progress monitor.
     * @return result status.
     */
    private IStatus doExecute(IProgressMonitor monitor, IAdaptable info,
            String name, String progressMessage) {
        IStatus status = Status.OK_STATUS;

        if (element != null) {
            if (monitor != null) {
                monitor.beginTask(progressMessage, 1);
            }

            try {
                // Not changing the description
                element.setDetails(element.getKey(), name, element
                        .getDescription());
            } catch (CoreException e) {
                status = e.getStatus();
            } finally {
                if (monitor != null) {
                    monitor.worked(1);
                    monitor.done();
                }

                IFragmentCategory parent = getParent(element);
                if (parent != null) {
                    refreshAndSelectInFragmentView(parent, element);
                }
            }
        }

        return status;
    }

    /**
     * Get the parent of the given element
     * 
     * @param element
     * @return parent category or <code>null</code> if not found.
     */
    private IFragmentCategory getParent(FragmentElementImpl element) {
        IFragmentCategory parent = null;

        if (element instanceof IContainedFragmentElement) {
            parent = ((IContainedFragmentElement) element).getParent();
        }

        return parent;
    }

}
