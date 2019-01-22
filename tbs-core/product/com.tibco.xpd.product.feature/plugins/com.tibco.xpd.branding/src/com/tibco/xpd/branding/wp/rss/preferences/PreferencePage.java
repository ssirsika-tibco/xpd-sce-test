/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.rss.preferences;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    public PreferencePage() {
        super(GRID);
        setPreferenceStore(BrandingPlugin.getDefault().getPreferenceStore());
        setDescription(""); //$NON-NLS-1$
    }

    @Override
    public Control createContents(Composite parent) {
        Composite cp = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(1, false);
        gl.marginTop = 0;
        cp.setLayout(gl);
        Label lb = new Label(cp, SWT.NONE);
        lb.setText(Messages.RSSViewer_URL_label);
        lb.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Control contents = super.createContents(cp);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 10;
        contents.setLayoutData(gd);

        return (cp);

    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        StringFieldEditor stringFieldEditor = new UrlStringFieldEditor(
                PreferenceConstants.CUSTOM_URL, Messages.PreferencePage_customUrl_label2,
                getFieldEditorParent());
        addField(stringFieldEditor);
        stringFieldEditor.setEmptyStringAllowed(false);
        stringFieldEditor.setErrorMessage(Messages.PreferencePage_emptyUrl_message);
        stringFieldEditor
                .setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

    class UrlStringFieldEditor extends StringFieldEditor {

        public UrlStringFieldEditor(String name, String labelText,
                Composite parent) {
            super(name, labelText, parent);
        }

        @Override
        protected boolean doCheckState() {
            String stringValue = getStringValue();
            boolean isValid = true;
            try {
                new URL(stringValue);
            } catch (MalformedURLException e) {
                isValid = false;
                setErrorMessage(e.getMessage());
            }
            return isValid;
        }

    }

}