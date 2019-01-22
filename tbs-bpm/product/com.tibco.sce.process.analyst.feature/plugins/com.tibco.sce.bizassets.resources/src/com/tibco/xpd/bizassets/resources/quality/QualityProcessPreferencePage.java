/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.quality;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.bizassets.resources.internal.Messages;

/**
 * @author nwilson
 */
public class QualityProcessPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    /**
     * 
     */
    public QualityProcessPreferencePage() {
    }

    /**
     * @param title
     */
    public QualityProcessPreferencePage(String title) {
        super(title);
    }

    /**
     * @param title
     * @param image
     */
    public QualityProcessPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
    }

    /**
     * @param parent
     * @return
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(Messages.QualityProcessPreferencePage_text);
        return label;
    }

    /**
     * @param workbench
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        noDefaultAndApplyButton();
    }

}
