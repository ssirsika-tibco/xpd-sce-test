/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rasc.ui.Messages;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;

/**
 * Property panel for setting the admin base URL.
 *
 * @author nwilson
 * @since 25 Mar 2019
 */
public class AdminUrlPropertyPanel extends Composite {

    private static final String DEFUALT_URL =
            "https://<domain>/admin-app/index.html"; //$NON-NLS-1$

    private Text adminUrl;

    private Label warning;

    /**
     * @param parent
     * @param style
     */
    public AdminUrlPropertyPanel(BaseXpdToolkit toolkit, Composite parent,
            int style) {
        super(parent, style);
        setLayout(new GridLayout(2, false));
        Label adminUrlLabel = toolkit.createLabel(this,
                Messages.AdminUrlPropertyPanel_BaseUrlLabel);
        adminUrlLabel.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false));
        String defaultText = RascUiActivator.getDefault().getAdminBaseUrl();
        if (defaultText == null || defaultText.length() == 0) {
            defaultText = DEFUALT_URL;
        }
        adminUrl = toolkit
                .createText(this, defaultText, RascUiActivator.ADMIN_BASE_URL);
        adminUrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        warning = toolkit.createLabel(this, ""); //$NON-NLS-1$
        warning.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        warning.setForeground(
                getShell().getDisplay().getSystemColor(SWT.COLOR_RED));

        adminUrl.addModifyListener(event -> updateMessages());
        updateMessages();
    }

    void updateMessages() {
        if (DEFUALT_URL.equals(adminUrl.getText())) {
            warning.setText(Messages.DefaultUrlWarning0);
        } else {
            warning.setText(""); //$NON-NLS-1$
        }
    }

    /**
     * @return The text from the admin URL input control.
     */
    public String getAdminBaseUrl() {
        return adminUrl.getText();
    }

    /**
     * Reset any changes to the input controls.
     */
    public void reset() {
        adminUrl.setText(RascUiActivator.getDefault().getAdminBaseUrl());
    }

}
