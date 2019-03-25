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

    private Text adminUrl;

    /**
     * @param parent
     * @param style
     */
    public AdminUrlPropertyPanel(BaseXpdToolkit toolkit, Composite parent,
            int style) {
        super(parent, style);
        setLayout(new GridLayout(2, false));
        Label adminUrlLabel = toolkit.createLabel(parent, Messages.AdminUrlPropertyPanel_BaseUrlLabel);
        adminUrlLabel.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false));
        String defaultText = RascUiActivator.getDefault().getAdminBaseUrl();
        adminUrl = toolkit.createText(parent,
                defaultText,
                RascUiActivator.ADMIN_BASE_URL);
        adminUrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
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
