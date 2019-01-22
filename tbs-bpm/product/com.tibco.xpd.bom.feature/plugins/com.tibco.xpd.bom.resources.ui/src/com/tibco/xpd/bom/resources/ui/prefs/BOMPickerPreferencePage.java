/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.prefs;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;

/**
 * BOM picker preference page.
 * 
 * @author njpatel
 * 
 */
public class BOMPickerPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    /**
     * Preference store id for "Always set project reference without asking".
     */
    public static final String ALWAYS_SET_PROJ_REF_ID = "alwaysSetProjectReference"; //$NON-NLS-1$

    private boolean alwaysSet = false;

    private Button chkAlwaysSet;

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);

        chkAlwaysSet = new Button(root, SWT.CHECK);
        chkAlwaysSet
                .setText(Messages.BOMPickerPreferencePage_setProjectReferenceWithoutAsking_label);
        chkAlwaysSet.setSelection(alwaysSet);

        chkAlwaysSet.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                alwaysSet = chkAlwaysSet.getSelection();
            }
        });

        return root;
    }

    @Override
    public boolean performOk() {
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            store.setValue(ALWAYS_SET_PROJ_REF_ID, alwaysSet);
        }

        return true;
    }

    @Override
    protected void performDefaults() {
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            // Reset to false
            alwaysSet = false;
            if (chkAlwaysSet != null && !chkAlwaysSet.isDisposed()) {
                chkAlwaysSet.setSelection(alwaysSet);
            }
        }
        super.performDefaults();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            alwaysSet = store.getBoolean(ALWAYS_SET_PROJ_REF_ID);
        }
    }

    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return Activator.getDefault().getPreferenceStore();
    }

}
