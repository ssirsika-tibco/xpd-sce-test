/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;

/**
 * Preference page for all things XSD import related
 * 
 * @author rgreen
 * @since 22 Jun 2012
 */
public class XsdTransformPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Group importOptionsGrp;

    private Button xsdAnnotationRemovalOption;

    private boolean enableDisplayXsdAnnotationRemovalWarning = true;

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     * 
     * @param workbench
     */
    @Override
    public void init(IWorkbench workbench) {
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {
            enableDisplayXsdAnnotationRemovalWarning =
                    store.getBoolean(XsdTransformPreferenceConstants.DONT_SHOW_XSD_ANNOTATION_REMOVAL_WARNING);
        }

    }

    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return Activator.getDefault().getPreferenceStore();
    }

    @Override
    public boolean performOk() {
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {
            store.setValue(XsdTransformPreferenceConstants.DONT_SHOW_XSD_ANNOTATION_REMOVAL_WARNING,
                    xsdAnnotationRemovalOption.getSelection());
            return true;
        }
        return false;
    }

    @Override
    protected void performDefaults() {
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {
            // Button for XSD Validation
            enableDisplayXsdAnnotationRemovalWarning =
                    store.getDefaultBoolean(XsdTransformPreferenceConstants.DONT_SHOW_XSD_ANNOTATION_REMOVAL_WARNING);
            xsdAnnotationRemovalOption
                    .setSelection(enableDisplayXsdAnnotationRemovalWarning);

        }
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        importOptionsGrp = new Group(root, SWT.NONE);
        importOptionsGrp.setLayout(new GridLayout());
        importOptionsGrp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        importOptionsGrp.setText(Messages.XsdTransformPreferencePage_ImportOptions_label);

        xsdAnnotationRemovalOption = new Button(importOptionsGrp, SWT.CHECK);
        xsdAnnotationRemovalOption
                .setText(Messages.XsdTransformPreferencePage_DisplayWarningXSDWillNotBePreserved_label);
        xsdAnnotationRemovalOption
                .setSelection(enableDisplayXsdAnnotationRemovalWarning);

        // xsdAnnotationRemovalOption.addSelectionListener(listener);

        return null;
    }

}
