/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * @author nwilson
 */
public class PasswordDialog extends Dialog {

    private Text password;

    private String passwordText;

    /**
     * @param parentShell
     */
    public PasswordDialog(Shell parentShell) {
        super(parentShell);

    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    @Override
    protected void configureShell(Shell newShell) {
        newShell.setText(Messages.PasswordDialog_Title);
        Rectangle bounds = newShell.getDisplay().getBounds();
        int x = (bounds.width - 300) / 2;
        int y = (bounds.height - 130) / 2;
        newShell.setBounds(x, y, 300, 130);
        super.configureShell(newShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(1, false);
        composite.setLayout(layout);

        Composite passwordComposite = new Composite(composite, SWT.NONE);
        layout = new GridLayout(2, false);
        passwordComposite.setLayout(layout);
        GridData gd = new GridData(GridData.FILL_BOTH);
        passwordComposite.setLayoutData(gd);

        Label passwordLabel = new Label(passwordComposite, SWT.NONE);
        passwordLabel.setText(Messages.PasswordDialog_Label);
        passwordLabel.setLayoutData(new GridData());

        password = new Text(passwordComposite, SWT.PASSWORD | SWT.BORDER);
        password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        password.setEchoChar('*');

        return composite;
    }

    @Override
    protected void okPressed() {
        passwordText = password.getText();
        super.okPressed();
    }

    /**
     * @return
     */
    public String getPassword() {
        return passwordText;
    }

}
