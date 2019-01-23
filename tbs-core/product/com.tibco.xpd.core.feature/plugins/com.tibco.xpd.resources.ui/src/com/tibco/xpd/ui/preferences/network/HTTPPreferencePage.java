/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.preferences.network;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.resources.PreferenceStoreKeys;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * HTTP preference store contributed to General->Network Connections.
 * 
 * @author njpatel
 * @since 3.3
 */
public class HTTPPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Text timeoutTxt;

    /** Timeout value in milliseconds */
    private int timeout;

    public HTTPPreferencePage() {
    }

    public HTTPPreferencePage(String title) {
        super(title);
    }

    public HTTPPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout(2, false));

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.HTTPPreferencePage_timeout_label);

        timeoutTxt = new Text(root, SWT.BORDER);
        timeoutTxt
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        setTimeoutValue(timeoutTxt);
        timeoutTxt.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                String value = timeoutTxt.getText();
                boolean isValid = true;
                if (value.length() == 0) {
                    isValid = false;
                } else {
                    try {
                        timeout = Integer.parseInt(value);
                        timeout *= 1000; // Convert to milliseconds
                    } catch (NumberFormatException e1) {
                        isValid = false;
                        timeout = 0;
                    }
                }

                if (isValid) {
                    setErrorMessage(null);
                    setValid(true);
                } else {
                    setErrorMessage(Messages.HTTPPreferencePage_timeout_error_message);
                    setValid(false);
                }
            }
        });

        return root;
    }

    @Override
    protected void performDefaults() {
        getPreferenceStore().setToDefault(PreferenceStoreKeys.HTTP_TIMEOUT);
        initialize();
        setTimeoutValue(timeoutTxt);
    }

    @Override
    public boolean performOk() {
        getPreferenceStore()
                .setValue(PreferenceStoreKeys.HTTP_TIMEOUT, timeout);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        setPreferenceStore(XpdResourcesPlugin.getDefault().getPreferenceStore());
        initialize();
    }

    private void initialize() {
        timeout = getPreferenceStore().getInt(PreferenceStoreKeys.HTTP_TIMEOUT);
    }

    /**
     * Set the timeout value in the preference page.
     * 
     * @param txtCntrl
     */
    private void setTimeoutValue(Text txtCntrl) {
        if (txtCntrl != null && !txtCntrl.isDisposed()) {
            txtCntrl.setText(String.valueOf(timeout / 1000));
        }
    }
}
