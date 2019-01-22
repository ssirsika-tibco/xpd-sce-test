/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.dnd;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;
import com.tibco.xpd.fragments.internal.operations.CreateCategoryOperation;
import com.tibco.xpd.fragments.internal.operations.CreateFragmentOperation;
import com.tibco.xpd.fragments.internal.operations.FragmentElementOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Fragments drop target adapter that can be implemented by the fragment's
 * contributor extension that wants to support dropping of objects onto the
 * fragments view.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentDropTargetAdapter extends DropTargetAdapter {

    private FragmentsProvider provider;

    /**
     * Fragments drop target adapter that will allow contributors to support
     * dropping of objects onto the fragments view to create new
     * fragments/categories.
     */
    public FragmentDropTargetAdapter() {
    }

    /**
     * Set the provider contributing this drop adapter.
     * 
     * @param provider
     *            fragment provider.
     */
    public void setProvider(FragmentsProvider provider) {
        this.provider = provider;
    }

    /**
     * Get the {@link Transfer} types supported by this drop adapter.
     * 
     * @return array of supporte <code>Transfer</code> types.
     */
    public abstract Transfer[] getTransferTypes();

    /**
     * Check if the given transfer can be dropped onto the target fragment
     * category.
     * 
     * @param event
     *            drop target event
     * @param transferType
     *            current transfer type
     * @param targetCategory
     *            target category
     * @return
     */
    protected abstract boolean canDrop(DropTargetEvent event,
            Transfer transferType, IFragmentCategory targetCategory);

    /**
     * Do the drop to create a fragment or category.
     * <p>
     * Use {@link #createCategory(IFragmentCategory, String, String)
     * createCategory} to create a category or
     * {@link #createFragment(IFragmentCategory, String, String, String, ImageData)
     * createFragment} to create a fragment under the given target category.
     * </p>
     * 
     * @param event
     *            drop target event
     * @param transferType
     *            current transfer type
     * @param targetCategory
     *            target category
     * @throws CoreException
     */
    protected abstract void doDrop(DropTargetEvent event,
            Transfer transferType, IFragmentCategory targetCategory)
            throws CoreException;

    /**
     * Create a category under the given parent.
     * 
     * @param parent
     *            parent category
     * @param name
     *            name of the category
     * @param description
     *            short description of the category, <code>null</code> if no
     *            description required
     * @return created fragment category.
     * @throws CoreException
     */
    protected final IFragmentCategory createCategory(IFragmentCategory parent,
            String name, String description) throws CoreException {
        IFragmentCategory cat = null;

        if (parent instanceof FragmentCategoryImpl) {
            CreateCategoryOperation op = new CreateCategoryOperation(
                    (FragmentCategoryImpl) parent, null, name, description,
                    false);
            execute(op);
            // Get result
            cat = op.getCategory();
        }
        return cat;
    }

    /**
     * Create a fragment under the given parent.
     * 
     * @param parent
     *            parent category
     * @param name
     *            name of the fragment
     * @param description
     *            short description of the fragment, <code>null</code> if no
     *            description required
     * @param data
     *            fragment data, this cannot be <code>null</code>
     * @param imgData
     *            image data of the fragment image, or <code>null</code> if no
     *            image available
     * @return created fragment
     * @throws CoreException
     */
    protected final IFragment createFragment(IFragmentCategory parent,
            String name, String description, String data, ImageData imgData)
            throws CoreException {
        IFragment frag = null;

        if (parent instanceof FragmentCategoryImpl) {
            CreateFragmentOperation op = new CreateFragmentOperation(
                    (FragmentCategoryImpl) parent, null, name, description,
                    data, imgData);

            execute(op);
            // Get result
            frag = op.getFragment();
        }

        return frag;
    }

    /**
     * Execute the given operation.
     * 
     * @param op
     *            operation.
     * @throws CoreException
     */
    private void execute(FragmentElementOperation op) throws CoreException {
        try {
            IStatus status = FragmentsUtil.execute(op, null);
            if (status.isOK()) {
            } else {
                throw new CoreException(status);
            }
        } catch (ExecutionException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    FragmentsActivator.PLUGIN_ID, e.getLocalizedMessage(), e));
        }
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
        event.detail = event.item != null ? DND.DROP_COPY : DND.DROP_NONE;

        for (TransferData data : event.dataTypes) {
            if (getSupportedType(data) != null) {
                event.currentDataType = data;
                break;
            }
        }
    }

    @Override
    public void dragOperationChanged(DropTargetEvent event) {
        if (event.detail == DND.DROP_MOVE) {
            event.detail = DND.DROP_COPY;
        }
    }

    @Override
    public void dragOver(DropTargetEvent event) {
        Transfer transfer = getSupportedType(event.currentDataType);
        if (transfer != null) {
            Widget item = event.item;

            if (item != null) {
                Object targetData = item.getData();
                IFragmentCategory category = null;

                if (targetData instanceof IFragment) {
                    category = ((IFragment) targetData).getParent();
                } else if (targetData instanceof IFragmentCategory) {
                    category = (IFragmentCategory) targetData;
                }

                if (!provider.equals(((FragmentElementImpl) category)
                        .getProvider())) {
                    return;
                }

                if (category != null && !category.isSystem()) {
                    if (canDrop(event, transfer, category)) {
                        event.detail = DND.DROP_COPY;
                    } else {
                        event.detail = DND.DROP_NONE;
                    }
                } else {
                    // Cannot drop on a system category
                    event.detail = DND.DROP_NONE;
                }
            } else {
                event.detail = DND.DROP_NONE;
            }
        }
    }

    @Override
    public void drop(DropTargetEvent event) {
        Transfer transfer = getSupportedType(event.currentDataType);

        if (transfer != null) {
            Cursor origCursor = null;
            Cursor waitCursor = null;

            try {
                if (event.display != null) {
                    origCursor = event.display.getActiveShell().getCursor();
                    waitCursor = new Cursor(event.display, SWT.CURSOR_WAIT);
                    event.display.getActiveShell().setCursor(waitCursor);
                }
                Object data = event.item != null ? event.item.getData() : null;
                IFragmentCategory category = null;

                if (data instanceof IFragment) {
                    category = ((IFragment) data).getParent();
                } else if (data instanceof IFragmentCategory) {
                    category = (IFragmentCategory) data;
                }

                if (!category.isSystem()
                        && provider.equals(((FragmentElementImpl) category)
                                .getProvider())) {
                    try {
                        doDrop(event, transfer, category);
                    } catch (CoreException e) {
                        ErrorDialog
                                .openError(
                                        null,
                                        "Drop Fragment",
                                        "An error occurred during the drop onto the fragments view.",
                                        new Status(IStatus.ERROR,
                                                FragmentsActivator.PLUGIN_ID, e
                                                        .getLocalizedMessage(),
                                                e));
                    }
                }
            } finally {
                if (waitCursor != null) {
                    waitCursor.dispose();
                    if (event.display != null) {
                        event.display.getActiveShell().setCursor(origCursor);
                    }

                }
            }
        }
    }

    /**
     * Get the supported transfer type from the give <code>TransferData</code>.
     * 
     * @param data
     *            <code>TransferData</code>.
     * @return supported <code>Transfer</code> type found or null if no transfer
     *         type in the transfer data is supported.
     */
    protected Transfer getSupportedType(TransferData data) {
        Transfer[] types = getTransferTypes();

        if (types != null) {
            for (Transfer type : types) {
                if (type.isSupportedType(data)) {
                    return type;
                }
            }
        }

        return null;
    }

}
