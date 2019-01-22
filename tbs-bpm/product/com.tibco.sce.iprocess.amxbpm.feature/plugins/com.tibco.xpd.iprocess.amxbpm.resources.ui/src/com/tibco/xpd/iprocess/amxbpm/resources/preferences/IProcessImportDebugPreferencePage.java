/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.resources.preferences;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;
import com.tibco.xpd.iprocess.amxbpm.resources.IProcessAMXBPMResourcePlugin;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;

/**
 * Preference pge for iProcess Import/Convert to BPM Debug mode. Lets user
 * select between the Debug modes NONE, BASIC and ENHANCED modes.Defaults to
 * NONE.
 * 
 * @author aprasad
 * @since 22-Apr-2014
 */
public class IProcessImportDebugPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private IWorkbenchWindow activeWorkbenchWindow;

    private Button noDebugButton;

    private Button basicDebugButton;

    private Button enhancedDebugButton;

    private String currentDebugMode;

    public static final String IPM_IMPORT_BPM_DEBUG_MODE =
            "isInIpmImportToBpmDebugMode"; //$NON-NLS-1$

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return IProcessAMXBPMResourcePlugin.getDefault().getPreferenceStore();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().applyTo(root);

        Group importDebugModeGroup = new Group(root, SWT.NONE);

        GridDataFactory.fillDefaults().grab(true, false)
                .align(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING)
                .applyTo(importDebugModeGroup);

        GridLayoutFactory.swtDefaults().applyTo(importDebugModeGroup);
        importDebugModeGroup
                .setText(Messages.IProcessImportDebugPreference_ImportIPMPreferenceGroupTitle);
        noDebugButton = new Button(importDebugModeGroup, SWT.RADIO);
        noDebugButton
                .setText(Messages.IProcessImportDebugPreference_NoDebugButtonTitle);
        basicDebugButton = new Button(importDebugModeGroup, SWT.RADIO);
        basicDebugButton
                .setText(Messages.IProcessImportDebugPreference_BasicDebugButtonTitle);
        enhancedDebugButton = new Button(importDebugModeGroup, SWT.RADIO);
        enhancedDebugButton
                .setText(Messages.IProcessImportDebugPreference_EnhancedDebugButton);

        SelectionListener ipmListener = new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                if (noDebugButton.equals(e.getSource())) {
                    currentDebugMode = DEBUG_MODE.NONE.name();
                } else

                if (basicDebugButton.equals(e.getSource())) {
                    currentDebugMode = DEBUG_MODE.BASIC.name();
                } else

                if (enhancedDebugButton.equals(e.getSource())) {
                    currentDebugMode = DEBUG_MODE.ENHANCED.name();
                }

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub

            }

        };

        noDebugButton.addSelectionListener(ipmListener);
        basicDebugButton.addSelectionListener(ipmListener);
        enhancedDebugButton.addSelectionListener(ipmListener);
        loadValues();
        return root;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {

        String defaultValue =
                getPreferenceStore()
                        .getDefaultString(IPM_IMPORT_BPM_DEBUG_MODE);

        if (defaultValue == null || defaultValue.isEmpty()) {

            getPreferenceStore().setDefault(IPM_IMPORT_BPM_DEBUG_MODE,
                    DEBUG_MODE.NONE.name());

        }

        loadIPMDebugMode(DEBUG_MODE.NONE.name());

        super.performDefaults();
    }

    /**
     * @param ipmImportDebugMode
     */
    private void loadIPMDebugMode(String ipmImportDebugMode) {

        noDebugButton.setSelection(DEBUG_MODE.NONE.name()
                .equals(ipmImportDebugMode));

        basicDebugButton.setSelection(DEBUG_MODE.BASIC.name()
                .equals(ipmImportDebugMode));

        enhancedDebugButton.setSelection(DEBUG_MODE.ENHANCED.name()
                .equals(ipmImportDebugMode));
    }

    /**
     * Loads values into controls from preference store.
     */
    private void loadValues() {

        String ipmImportDebugMode =
                getPreferenceStore().getString(IPM_IMPORT_BPM_DEBUG_MODE);
        // New Workspace launch, when default is not set
        if (ipmImportDebugMode.isEmpty()) {

            performDefaults();

        } else {

            loadIPMDebugMode(ipmImportDebugMode);

        }
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {

        if (this.currentDebugMode != null) {
            if (this.currentDebugMode != getPreferenceStore()
                    .getString(IPM_IMPORT_BPM_DEBUG_MODE)) {

                getPreferenceStore().setValue(IPM_IMPORT_BPM_DEBUG_MODE,
                        this.currentDebugMode);

            }
        }

        return true;
    }

    /**
     * Method reads and returns the debug preference.
     * 
     * @return saved preference value {@link DEBUG_MODE}.
     */

    public static DEBUG_MODE getDebugMode() {

        String ipmImportDebugMode =
                IProcessAMXBPMResourcePlugin.getDefault().getPreferenceStore()
                        .getString(IPM_IMPORT_BPM_DEBUG_MODE);

        if (DEBUG_MODE.BASIC.name().equals(ipmImportDebugMode)) {
            return DEBUG_MODE.BASIC;
        }
        if (DEBUG_MODE.ENHANCED.name().equals(ipmImportDebugMode)) {
            return DEBUG_MODE.ENHANCED;
        }

        return DEBUG_MODE.NONE;

    }
}
