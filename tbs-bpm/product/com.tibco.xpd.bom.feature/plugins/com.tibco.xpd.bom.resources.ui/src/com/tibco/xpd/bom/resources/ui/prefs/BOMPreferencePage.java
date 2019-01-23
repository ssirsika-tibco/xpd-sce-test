/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.prefs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

import com.tibco.xpd.bom.resources.ui.internal.Messages;

/**
 * BOM's main preference page.
 * 
 * @author njpatel
 * 
 */
public class BOMPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, IWorkbenchPropertyPage {

    private IAdaptable element;

    /**
     * BOM's main preference page.
     */
    public BOMPreferencePage() {
        noDefaultAndApplyButton();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.BOMPreferencePage_longdesc);

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     */
    public IAdaptable getElement() {
        return element;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime
     * .IAdaptable)
     */
    public void setElement(IAdaptable element) {
        this.element = element;
    }

}
