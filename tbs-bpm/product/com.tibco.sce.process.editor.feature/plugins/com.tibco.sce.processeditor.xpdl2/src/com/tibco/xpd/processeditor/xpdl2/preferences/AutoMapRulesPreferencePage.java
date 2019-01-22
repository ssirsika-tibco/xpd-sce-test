/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcherExtensionPointHelper;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 *Auto-map preference page. This page lists all the auto-map contributions and
 * lets the user chose which ones to disable.
 * 
 * @author rsomayaj
 * @since 3.3 (22 Jun 2010)
 */
public class AutoMapRulesPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    /**
     * 
     */
    public static final String AUTOMAP_PREFIX = "automap:"; //$NON-NLS-1$

    private IWorkbench workbench;

    private IPreferenceStore preferenceStore;

    private List<BooleanFieldEditor> booleanFieldEditors =
            new ArrayList<BooleanFieldEditor>();

    /**
     * @param title
     */
    public AutoMapRulesPreferencePage() {
        super(Messages.AutoMapRulesPreferencePage_AutomapRules_label);
        preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
    }

    /**
     * @param title
     * @param image
     */
    public AutoMapRulesPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite preferencePageComposite = new Composite(parent, SWT.None);
        preferencePageComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
        Label lblPreferencePageComposite =
                new Label(preferencePageComposite, SWT.None);
        lblPreferencePageComposite
                .setText(Messages.AutoMapRulesPreferencePage_TypeMatchersHeader_label);

        // For each type matcher contribution that has "showOnPreferencePage" as
        // true, create a row item that has a boolean field editor to ask the
        // user whether to include that contributor or not
        List<MappingTypeMatcher> contributions =
                MappingTypeMatcherExtensionPointHelper.getContributions();
        for (MappingTypeMatcher mappingTypeMatcher : contributions) {
            Boolean showInPreferencePage =
                    mappingTypeMatcher.getShowInPreferencePage();
            if (showInPreferencePage) {
                String typeMatcherName = mappingTypeMatcher.getName();

                String typeMatcherId = mappingTypeMatcher.getId();
                if (typeMatcherId != null) {
                    String fieldEditorName = AUTOMAP_PREFIX + typeMatcherId;
                    BooleanFieldEditor booleanFieldEditor =
                            new BooleanFieldEditor(fieldEditorName,
                                    typeMatcherName, preferencePageComposite);
                    booleanFieldEditor.setPreferenceStore(preferenceStore);

                    preferenceStore.setDefault(fieldEditorName, true);
                    booleanFieldEditor.load();
                    booleanFieldEditors.add(booleanFieldEditor);
                }
            }
        }
        return preferencePageComposite;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     * 
     * @param workbench
     */
    @Override
    public void init(IWorkbench workbench) {
        this.workbench = workbench;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     * 
     * @return
     */
    @Override
    public boolean performOk() {
        for (BooleanFieldEditor booleanFieldEditor : booleanFieldEditors) {
            booleanFieldEditor.store();
        }
        return super.performOk();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     * 
     */
    @Override
    protected void performDefaults() {
        for (BooleanFieldEditor booleanFieldEditor : booleanFieldEditors) {
            booleanFieldEditor.loadDefault();
        }
    }

}
