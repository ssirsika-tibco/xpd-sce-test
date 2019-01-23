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
 * Set properties operation for a fragment element.
 * 
 * @author njpatel
 * 
 */
public class SetPropertiesOperation extends FragmentElementOperation {

    private final FragmentElementImpl element;
    private final String name;
    private final String oldName;
    private final String description;
    private final String oldDescription;

    /**
     * Set properties operation for a fragment element.
     * 
     * @param element
     *            fragment element to set properties of.
     * @param name
     *            new name for the element.
     * @param description
     *            new description for the element, <code>null</code> if no
     *            description required.
     */
    public SetPropertiesOperation(FragmentElementImpl element, String name,
            String description) {
        super(
                element instanceof IFragmentCategory ? Messages.SetPropertiesOperation_category_label
                        : Messages.SetPropertiesOperation_fragment_label, element);

        Assert.isNotNull(element, "Fragment element"); //$NON-NLS-1$
        Assert.isNotNull(name, "Fragment element name"); //$NON-NLS-1$

        this.element = element;
        this.name = name;
        this.description = description;

        this.oldName = element.getName();
        this.oldDescription = element.getDescription();
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

        return doExecute(monitor, info, name, description,
                Messages.SetPropertiesOperation_setProperties_label);
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

        return doExecute(monitor, info, oldName, oldDescription,
                Messages.SetPropertiesOperation_undoSetProperties_label);
    }

    /**
     * Set the name and description of the element.
     * 
     * @param monitor
     * @param info
     * @param name
     *            name to set
     * @param description
     *            description to set, <code>null</code> if no description
     *            required.
     * @param progressMessage
     *            message for the progress monitor.
     * @return result status.
     */
    private IStatus doExecute(IProgressMonitor monitor, IAdaptable info,
            String name, String description, String progressMessage) {
        IStatus status = Status.OK_STATUS;

        if (element != null) {
            if (monitor != null) {
                monitor.beginTask(progressMessage, 1);
            }

            try {
                element.setDetails(null, name, description);
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
