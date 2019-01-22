/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preferences;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.WsSoapBindingSection.SoapVersion;

/**
 * Preference page to set the default binding preference (soap over http or soap
 * over jms). For a provider, service virtualization and the binding preference
 * selected in this page will be the two bindings generated
 * 
 * see {@BpmBindingPreferenceUtil} to
 * initialise/load/save the preferences
 * 
 * @author bharge
 * @since 22 Mar 2012
 */
public class BpmServiceBindingPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Button btnSoapOverHttp;

    private Button btnSoapOverJms;

    private Label lblDescription;

    private ComboViewer versionComboViewer;

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     * 
     * @param workbench
     */
    @Override
    public void init(IWorkbench workbench) {
        // do nothing
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
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
        GridLayoutFactory.swtDefaults().applyTo(root);

        Group bindingGroup = new Group(root, SWT.NONE);
        GridDataFactory.swtDefaults().grab(true, false).applyTo(bindingGroup);
        GridLayoutFactory.swtDefaults().applyTo(bindingGroup);
        bindingGroup
                .setText(Messages.BpmServiceBindingPreferencePage_TibcoBpmServiceBinding_label);

        lblDescription = new Label(bindingGroup, SWT.NONE);
        lblDescription
                .setText(Messages.BpmServiceBindingPreferencePage_TibcoBpmServiceBinding_desc);

        btnSoapOverHttp = new Button(bindingGroup, SWT.RADIO);
        btnSoapOverHttp
                .setText(Messages.BpmServiceBindingPreferencePage_SoapOverHttp_button);

        btnSoapOverJms = new Button(bindingGroup, SWT.RADIO);
        btnSoapOverJms
                .setText(Messages.BpmServiceBindingPreferencePage_SoapOverJms_button);

        Composite versionComposite = new Composite(bindingGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 5)
                .applyTo(versionComposite);
        Label versionLabel = new Label(versionComposite, SWT.NONE);
        versionLabel
                .setText(Messages.BpmServiceBindingPreferencePage_SoapVersion_label);
        Combo versionCombo = new Combo(versionComposite, SWT.DEFAULT);
        versionComboViewer = new ComboViewer(versionCombo);
        versionComboViewer.setContentProvider(new ArrayContentProvider());
        versionComboViewer.setLabelProvider(new LabelProvider());
        versionComboViewer.setInput(SoapVersion.values());

        Label bindingLabel = new Label(bindingGroup, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).indent(20, 0)
                .applyTo(bindingLabel);
        bindingLabel
                .setText(Messages.BpmServiceBindingPreferencePage_NoteAboutBindingPreference_shortdesc);

        loadValues();
        return root;
    }

    /**
     * 
     */
    private void loadValues() {
        btnSoapOverHttp.setSelection(getPreferenceStore()
                .getBoolean(BpmBindingPreferenceUtil.IS_HTTP_BINDING));
        btnSoapOverJms.setSelection(getPreferenceStore()
                .getBoolean(BpmBindingPreferenceUtil.IS_JMS_BINDING));
        versionComboViewer.setSelection(new StructuredSelection(
                BpmBindingPreferenceUtil.getSoapVersionPreference()), true);
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     * 
     */
    @Override
    protected void performDefaults() {
        btnSoapOverHttp.setSelection(getPreferenceStore()
                .getDefaultBoolean(BpmBindingPreferenceUtil.IS_HTTP_BINDING));
        btnSoapOverJms.setSelection(getPreferenceStore()
                .getDefaultBoolean(BpmBindingPreferenceUtil.IS_JMS_BINDING));
        versionComboViewer.setSelection(new StructuredSelection(
                BpmBindingPreferenceUtil.getDefaultSoapVersionPreference()),
                true);
        super.performDefaults();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     * 
     * @return
     */
    @Override
    public boolean performOk() {
        boolean shouldPerform = saveValues();
        if (shouldPerform) {
            Xpdl2ProcessEditorPlugin.getDefault().savePluginPreferences();
        }
        return true;
    }

    /**
     * 
     */
    private boolean saveValues() {

        boolean changed = false;

        boolean currentHttpMode = btnSoapOverHttp.getSelection();
        boolean currentJmsMode = btnSoapOverJms.getSelection();
        SoapVersion currentSoapVersion = getCurrentSoapVersion();

        if (currentHttpMode != getPreferenceStore()
                .getBoolean(BpmBindingPreferenceUtil.IS_HTTP_BINDING)
                || currentJmsMode != getPreferenceStore()
                        .getBoolean(BpmBindingPreferenceUtil.IS_JMS_BINDING)) {
            getPreferenceStore()
                    .setValue(BpmBindingPreferenceUtil.IS_HTTP_BINDING,
                            currentHttpMode);
            getPreferenceStore()
                    .setValue(BpmBindingPreferenceUtil.IS_JMS_BINDING,
                            currentJmsMode);
            changed = true;
        }
        if (currentSoapVersion != BpmBindingPreferenceUtil
                .getSoapVersionPreference()) {
            // These are enums so != in if is ok.
            BpmBindingPreferenceUtil
                    .setSoapVersionPreference(currentSoapVersion);
            changed = true;
        }

        return changed;
    }

    private SoapVersion getCurrentSoapVersion() {
        IStructuredSelection s =
                (IStructuredSelection) versionComboViewer.getSelection();
        Assert.isTrue(!s.isEmpty(),
                "Default SOAP version preference must always be set."); //$NON-NLS-1$
        return (SoapVersion) s.getFirstElement();
    }
}
