/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Service category preference page.
 * 
 * @author njpatel
 * 
 */
public class ServiceCategoryPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    public ServiceCategoryPreferencePage() {
        noDefaultAndApplyButton();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        Label lbl = new Label(root, SWT.WRAP);
        lbl.setText(Messages.ServiceCategoryPreferencePage_expandCategoryToSetSpecificProps_longdesc);

        return root;
    }

    @Override
    public void init(IWorkbench workbench) {

    }

}
