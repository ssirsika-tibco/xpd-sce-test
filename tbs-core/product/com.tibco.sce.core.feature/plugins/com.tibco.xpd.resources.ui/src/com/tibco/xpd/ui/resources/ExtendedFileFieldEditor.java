/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Adds a "Browse Workspace..." button to the JFace FileFieldEditor class.
 * 
 * @author nwilson
 */
public class ExtendedFileFieldEditor extends FileFieldEditor {
    private Button browseWorkspace;

    /**
     * Creates a file field editor.
     * 
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param parent
     *            the parent of the field editor's control
     */
    public ExtendedFileFieldEditor(String name, String labelText,
            Composite parent) {
        super(name, labelText, false, parent);
        setChangeButtonText(Messages.ExtendedFileFieldEditor_Browse);//$NON-NLS-1$
    }

    /**
     * Creates a file field editor.
     * 
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param enforceAbsolute
     *            <code>true</code> if the file path must be absolute, and
     *            <code>false</code> otherwise
     * @param parent
     *            the parent of the field editor's control
     */
    public ExtendedFileFieldEditor(String name, String labelText,
            boolean enforceAbsolute, Composite parent) {
        super(name, labelText, enforceAbsolute, parent);
        setChangeButtonText(Messages.ExtendedFileFieldEditor_Browse);
    }

    @Override
    public int getNumberOfControls() {
        return 4;
    }

    @Override
    protected void doFillIntoGrid(Composite parent, int numColumns) {
        super.doFillIntoGrid(parent, numColumns - 1);
        browseWorkspace = getBrowseWorkspaceButton(parent);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        int widthHint =
                convertHorizontalDLUsToPixels(browseWorkspace,
                        IDialogConstants.BUTTON_WIDTH);
        gd.widthHint =
                Math.max(widthHint, browseWorkspace.computeSize(SWT.DEFAULT,
                        SWT.DEFAULT,
                        true).x);
        browseWorkspace.setLayoutData(gd);
    }

    private Button getBrowseWorkspaceButton(Composite parent) {
        if (browseWorkspace == null) {
            browseWorkspace = new Button(parent, SWT.PUSH);
            browseWorkspace.setText(Messages.ExtendedFileFieldEditor_0);
            browseWorkspace.setFont(parent.getFont());
            browseWorkspace.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent evt) {
                    String newValue = browseWorkspacePressed();
                    if (newValue != null) {
                        setStringValue(newValue);
                    }
                }
            });
            browseWorkspace.addDisposeListener(new DisposeListener() {
                @Override
                public void widgetDisposed(DisposeEvent event) {
                    browseWorkspace = null;
                }
            });
        } else {
            checkParent(browseWorkspace, parent);
        }
        return browseWorkspace;
    }

    private String browseWorkspacePressed() {
        String file = null;
        SingleResourceSelectionDialog dialog =
                new SingleResourceSelectionDialog(getShell());
        if (dialog.open() == SingleResourceSelectionDialog.OK) {
            file = dialog.getFile();
        }
        return file;
    }
}
