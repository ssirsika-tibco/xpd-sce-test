/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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
public class AdminUrlPropertyDialog extends Dialog {

    private AdminUrlPropertyPanel panel;

    private Button hideCheckbox;

    /**
     * @param parentShell
     */
    public AdminUrlPropertyDialog(Shell parentShell) {
        super(parentShell);
        setBlockOnOpen(true);
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.AdminUrlPropertyDialog_Message);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        BaseXpdToolkit toolkit = new XpdWizardToolkit(area);
        panel = new AdminUrlPropertyPanel(toolkit, area, SWT.NONE);
        panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite infoArea = new Composite(area, SWT.NONE);
        infoArea.setLayout(new GridLayout(3, false));
        infoArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        hideCheckbox = toolkit.createCheckbox(infoArea,
                false,
                Messages.AdminUrlPropertyDialog_HideDialogCheckbox,
                RascUiActivator.HIDE_ADMIN_BASE_URL);
        hideCheckbox.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

        Composite spacer = new Composite(infoArea, SWT.NONE);
        spacer.setLayoutData(new GridData(8, 1));

        Label infoIcon = new Label(infoArea, SWT.NONE);
        infoIcon.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage("IMG_OBJS_INFO_TSK")); //$NON-NLS-1$
        infoIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        Label infoText = new Label(infoArea, SWT.NONE);
        infoText.setText(Messages.AdminUrlPropertyDialog_InfoMessage);
        infoText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        return area;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     *
     */
    @Override
    protected void okPressed() {
        RascUiActivator.getDefault()
                .setHideAdminUrlDialog(hideCheckbox.getSelection());
        RascUiActivator.getDefault().setAdminBaseUrl(panel.getAdminBaseUrl());
        super.okPressed();
    }

}
