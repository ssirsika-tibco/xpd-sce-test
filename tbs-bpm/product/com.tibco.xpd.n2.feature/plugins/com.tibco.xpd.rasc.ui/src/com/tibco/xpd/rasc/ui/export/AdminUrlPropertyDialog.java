/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.rasc.ui.Messages;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * Dialog to ask the user to set the admin base URL if they haven't already done
 * so.
 *
 * @author nwilson
 * @since 25 Mar 2019
 */
public class AdminUrlPropertyDialog extends IconAndMessageDialog {

    private AdminUrlPropertyPanel panel;

    /**
     * @param parentShell
     */
    public AdminUrlPropertyDialog(Shell parentShell) {
        super(parentShell);
        setBlockOnOpen(true);
        message = Messages.AdminUrlPropertyDialog_Message;
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.AdminUrlPropertyDialog_Title);
    }

    /**
     * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
     */
    @Override
    protected Image getImage() {
        return getInfoImage();
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite messageArea = new Composite(area, SWT.NONE);
        messageArea.setLayout(new GridLayout(2, false));
        messageArea
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        createMessageArea(messageArea);
        BaseXpdToolkit toolkit = new XpdWizardToolkit(area);
        panel = new AdminUrlPropertyPanel(toolkit, area, SWT.NONE);
        panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        return area;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     *
     */
    @Override
    protected void okPressed() {
        RascUiActivator.getDefault().setAdminBaseUrl(panel.getAdminBaseUrl());
        super.okPressed();
    }

}
