/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;

/**
 * Base class for deployment wizards property pages.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class BaseDeployWizardPage extends AbstractXpdSelectionWizardPage {

    /**
     * Constructor.
     */
    public BaseDeployWizardPage(String pageName) {
        super(pageName);
    }

    protected Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        return label;
    }

    protected Combo createCombo(Composite parent) {
        Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return combo;
    }

    protected Text createText(Composite parent, String defaultText) {
        Text textFeld = new Text(parent, SWT.SINGLE | SWT.BORDER);
        textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textFeld.setText(defaultText);
        return textFeld;
    }
    
    protected Text createPasswordText(Composite parent, String defaultText) {
        Text textFeld = new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
        textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textFeld.setText(defaultText);
        return textFeld;
    }

    protected Button createCheckbox(Composite parent, boolean defaultValue) {
        Button button = new Button(parent, SWT.CHECK);
        button.setSelection(defaultValue);
        return button;
    }

    protected Button createPushButton(Composite parent, String text) {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        return button;
    }

}