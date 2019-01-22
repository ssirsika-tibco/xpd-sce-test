/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;

/**
 * Base class for all fragment operations. This will set the correct
 * {@link FragmentContext undo context} for the operations.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentElementOperation extends AbstractOperation {

    private FragmentsViewPart viewPart;
    private final boolean canUndo;

    /**
     * Base class for all fragment operations.
     * 
     * @param label
     *            operation label
     * @param context
     *            operation context
     */
    public FragmentElementOperation(String label, IFragmentElement context) {
        this(label, context, true);
    }

    /**
     * Base class for all fragment operations.
     * 
     * @param label
     *            operation label
     * @param context
     *            operation context
     * @param canUndo
     *            set to <code>true</code> if this operation can be undone,
     *            <code>false</code> otherwise.
     */
    public FragmentElementOperation(String label, IFragmentElement context,
            boolean canUndo) {
        super(label);
        Assert.isNotNull(context, "Fragment operation context"); //$NON-NLS-1$
        this.canUndo = canUndo;
        viewPart = FragmentsManager.getInstance().getFragmentsViewPart();
        updateContext(context);
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        // Re-execute this command
        return execute(monitor, info);
    }

    /**
     * Refresh the itemToRefresh in the fragment tree view and then select the
     * itemToSelect.
     * 
     * @param itemToRefresh
     *            item to refresh in the tree viewer, <code>null</code> if no
     *            item should be refreshed
     * @param itemToSelect
     *            item to select in the tree viewer after refresh,
     *            <code>null</code> if no item should be selected
     */
    protected void refreshAndSelectInFragmentView(
            IFragmentElement itemToRefresh, IFragmentElement itemToSelect) {

        if (viewPart != null && viewPart.getTreeViewer() != null) {
            if (itemToRefresh != null) {
                viewPart.refresh(itemToRefresh);
            }

            if (itemToSelect != null) {
                viewPart.getTreeViewer().setSelection(
                        new StructuredSelection(itemToSelect), true);
            }
        }
    }

    /**
     * Edit the given element's column in the fragment viewer.
     * 
     * @param selection
     *            item to edit
     * @param column
     *            item's column to edit
     */
    protected void editElement(Object selection, int column) {
        if (selection != null && viewPart != null
                && viewPart.getTreeViewer() != null
                && !viewPart.getTreeViewer().getControl().isDisposed()) {
            viewPart.getTreeViewer().editElement(selection, column);

        }
    }

    /**
     * Update the context of this operation to the fragment context.
     * 
     * @param operationContext
     *            operation context
     */
    private void updateContext(IFragmentElement operationContext) {
        IUndoContext[] contexts = getContexts();
        String[] providerIds = null;

        // Get the provider id
        if (operationContext instanceof FragmentElementImpl) {
            FragmentsProvider provider = ((FragmentElementImpl) operationContext)
                    .getProvider();

            if (provider != null) {
                providerIds = new String[] { provider.getId() };
            }
        }

        if (contexts != null) {
            for (IUndoContext context : contexts) {
                removeContext(context);
            }
        }
        addContext(new FragmentContext(providerIds, canUndo));
    }
}
