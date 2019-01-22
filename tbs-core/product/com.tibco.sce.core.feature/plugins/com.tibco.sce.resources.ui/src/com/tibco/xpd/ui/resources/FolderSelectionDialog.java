/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.NewFolderDialog;
import org.eclipse.ui.views.navigator.ResourceSorter;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Folder selection dialog based on label provider and content provider to
 * enable using different folder contexts and filtering.
 * 
 * Source of this class is based on FolderSelectionDialog class from Eclipse JDT
 * plugin.
 * 
 * @author jarciuch
 * @deprecated (since 3.1) The {@link BaseObjectPicker} should be used instead
 *             with the right filters applied for folder selection. This dialog
 *             shows the 'Navigator' style view rather than the 'Project
 *             Explorer' view that is preferred.
 */
public class FolderSelectionDialog extends ElementTreeSelectionDialog implements
        ISelectionChangedListener {

    private Button newFolderButton;

    private IContainer selectedContainer;

    private boolean canCreateNewFolder;

    public FolderSelectionDialog(Shell parent, ILabelProvider labelProvider,
            ITreeContentProvider contentProvider) {
        this(parent, labelProvider, contentProvider, true);
    }

    public FolderSelectionDialog(Shell parent, ILabelProvider labelProvider,
            ITreeContentProvider contentProvider, boolean canCreateNewFolder) {
        super(parent, labelProvider, contentProvider);
        setSorter(new ResourceSorter(ResourceSorter.NAME));
        this.canCreateNewFolder = canCreateNewFolder;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
     * .Composite)
     */
    protected Control createDialogArea(Composite parent) {
        Composite da = (Composite) super.createDialogArea(parent);
        getTreeViewer().addSelectionChangedListener(this);
        if (canCreateNewFolder) {
            Button button = new Button(da, SWT.PUSH);
            button.setText(Messages.FolderSelectionDialog_0);
            button.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    newFolderButtonPressed();
                }
            });
            button.setFont(parent.getFont());
            GridData data = new GridData();
            data.heightHint = convertVerticalDLUsToPixels(14);
            button.setLayoutData(data);
            newFolderButton = button;
        }
        applyDialogFont(da);
        return da;
    }

    private void updateNewFolderButtonState() {
        if (canCreateNewFolder) {
            IStructuredSelection selection = (IStructuredSelection) getTreeViewer()
                    .getSelection();
            selectedContainer = null;
            if (selection.size() == 1) {
                Object firstSelected = selection.getFirstElement();
                if (firstSelected instanceof IContainer) {
                    selectedContainer = (IContainer) firstSelected;
                }
            }
            newFolderButton.setEnabled(selectedContainer != null);
        }
    }

    protected void newFolderButtonPressed() {
        NewFolderDialog dialog = new NewFolderDialog(getShell(),
                selectedContainer);
        if (dialog.open() == Window.OK) {
            TreeViewer treeViewer = getTreeViewer();
            treeViewer.refresh(selectedContainer);
            Object createdFolder = dialog.getResult()[0];
            treeViewer.reveal(createdFolder);
            treeViewer.setSelection(new StructuredSelection(createdFolder));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
     * org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        updateNewFolderButtonState();
    }

}