/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.ui.dialogs.DetailsDialog;

/**
 * Dialog used to warn user that the resource being opened/created is already
 * open for editing. This dialog will also allow the user to force the open of
 * the file.
 * 
 * @author njpatel
 * 
 */
public class ResourceAlreadyOpenDialog extends DetailsDialog {

    private static final int WIDTH_HINT = 320;

    public static final int ID_FORCE_OPEN = IDialogConstants.CLIENT_ID + 1;

    private String message;

    private String detailsMsg;

    public ResourceAlreadyOpenDialog(Shell parentShell, String dialogTitle,
            String message, String detailsMsg) {
        super(parentShell, dialogTitle);
        this.message = message;
        this.detailsMsg = detailsMsg;
    }

    @Override
    protected boolean includeCancelButton() {
        return false;
    }

    @Override
    protected Composite createDropDownDialogArea(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 10;
        layout.marginHeight = 10;
        root.setLayout(layout);

        Label lbl = new Label(root, SWT.WRAP);
        GridData data = new GridData();
        data.widthHint = WIDTH_HINT;
        lbl.setLayoutData(data);

        lbl.setText(detailsMsg);

        Button btn = new Button(root, SWT.NONE);
        btn.setText(Messages.FileAlreadyOpenDialog_forceOpen_button);
        btn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        btn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setReturnCode(ID_FORCE_OPEN);
                close();
            }
        });

        return root;
    }

    @Override
    protected void createMainDialogArea(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        Label imgLbl = new Label(root, SWT.NONE);
        imgLbl.setImage(getImage());

        Label lbl = new Label(root, SWT.WRAP);
        GridData data = new GridData();
        data.widthHint = WIDTH_HINT;
        lbl.setLayoutData(data);
        lbl.setText(message);
    }

    private Image getImage() {
        return getShell().getDisplay().getSystemImage(SWT.ICON_WARNING);
    }

    @Override
    protected void updateEnablements() {
        // Nothing to do
    }

}
