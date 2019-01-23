/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author rsomayaj
 * 
 */
public class TestWizardPage extends WizardPage {

    /**
     * @param pageName
     */
    protected TestWizardPage() {
        super("TestWizardPage"); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.None);
        container.setLayout(new GridLayout());
        setControl(container);
    }

}
