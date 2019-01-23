/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Single resource selection dialog.
 * 
 * @deprecated (since 3.1) The {@link BaseObjectPicker} should be used instead
 *             with the right filters applied for resource selection. This
 *             dialog shows the 'Navigator' style view rather than the 'Project
 *             Explorer' view that is preferred.
 */
public class SingleResourceSelectionDialog extends Dialog implements
        ISelectionChangedListener {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 250;

    private TreeViewer viewer;
    private IResource resource;

    protected SingleResourceSelectionDialog(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        getShell().setText(Messages.SingleResourceSelectionDialog_0);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        Composite composite = (Composite) super.createDialogArea(parent);
        viewer = new TreeViewer(composite);
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(WorkbenchLabelProvider
                .getDecoratingWorkbenchLabelProvider());
        viewer.setInput(root);
        GridData viewerLayoutData = new GridData();
        viewerLayoutData.grabExcessHorizontalSpace = true;
        viewerLayoutData.grabExcessVerticalSpace = true;
        viewerLayoutData.widthHint = DEFAULT_WIDTH;
        viewerLayoutData.heightHint = DEFAULT_HEIGHT;
        viewerLayoutData.horizontalAlignment = SWT.FILL;
        viewerLayoutData.verticalAlignment = SWT.FILL;
        viewer.getTree().setLayoutData(viewerLayoutData);
        viewer.addSelectionChangedListener(this);
        return composite;
    }

    public String getFile() {
        String file = null;
        if (resource != null) {
            file = resource.getLocation().toString();
        }
        return file;
    }

    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) viewer
                .getSelection();
        if (selection.size() == 1) {
            Object object = selection.getFirstElement();
            if (object instanceof IResource) {
                resource = (IResource) object;
            }
        }
    }

}
