/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.gen.ui.preferences;

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

import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.gen.ui.internal.Messages;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class BomGenPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    /*
     * XPD-3664 (XPD-3636, XPD-3697): setting it to true by default to enable
     * validation of generated xsds
     */
    private boolean enableXSDValidation = true;

    private boolean enableWSDLValidation = false;

    private boolean enableBOMGeneration = false;

    private boolean enableBOMReGenerationOnImport = true;

    private boolean enableDeleteOnCleanForBomXsdBuilder = false;

    private Button enableXSDValidationBtn;

    private Button enableWSDLValidationBtn;

    private Button enableDeleteBomInWsdlToBomBuilderOnClean;

    private Button enableRegenerateBomOnProjectImport;

    private Button enableDeleteXsdInBomToXsdBuilderOnClean;

    public BomGenPreferencePage() {
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        Composite section = createXSDGenSection(root);
        section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        section = createWSDLGenSection(root);
        section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        section = createBOMGenSection(root);
        section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        section = createBomXsdDeleteOnCleanSection(root);
        section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        return root;
    }

    @Override
    public void init(IWorkbench workbench) {
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            /**
             * XPD-5248: MUST NOT set defaults=true on init of preference page!
             * 
             * That means that preference will be false _until user even just
             * views preference page_ and then it will change to become true and
             * appear that it has been true all along (checkbox-ticked) even
             * though it wasn't.
             * 
             * Moved to BomGenPreferenceInitializer which is the correct place
             * to set defaults.
             */

            enableXSDValidation =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION);
            enableWSDLValidation =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION);

            enableBOMGeneration =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_WSDLBOM_GENERATION);

            enableBOMReGenerationOnImport =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT);

            enableDeleteOnCleanForBomXsdBuilder =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_BOMXSD_GENERATION);
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
            store.setValue(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION,
                    enableXSDValidationBtn.getSelection());

            store.setValue(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION,
                    enableWSDLValidationBtn.getSelection());

            store.setValue(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_WSDLBOM_GENERATION,
                    enableDeleteBomInWsdlToBomBuilderOnClean.getSelection());

            store.setValue(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT,
                    enableRegenerateBomOnProjectImport.getSelection());

            store.setValue(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_BOMXSD_GENERATION,
                    enableDeleteXsdInBomToXsdBuilderOnClean.getSelection());

            return true;
        }

        return false;
    }

    @Override
    protected void performDefaults() {
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            // Button for XSD Validation
            enableXSDValidation =
                    store.getDefaultBoolean(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION);
            enableXSDValidationBtn.setSelection(enableXSDValidation);

            // Button for WSDL Validation
            enableWSDLValidation =
                    store.getDefaultBoolean(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION);
            enableWSDLValidationBtn.setSelection(enableWSDLValidation);

            // Button for WSDL to BOM
            enableBOMGeneration =
                    store.getDefaultBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_WSDLBOM_GENERATION);
            enableDeleteBomInWsdlToBomBuilderOnClean
                    .setSelection(enableBOMGeneration);

            // Button for BOM regeneration on project import
            enableBOMReGenerationOnImport =
                    store.getDefaultBoolean(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT);
            enableRegenerateBomOnProjectImport
                    .setSelection(enableBOMReGenerationOnImport);

            // Button for BOM to XSD
            enableDeleteOnCleanForBomXsdBuilder =
                    store.getDefaultBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_BOMXSD_GENERATION);
            enableDeleteXsdInBomToXsdBuilderOnClean
                    .setSelection(enableBOMGeneration);

        }
    }

    /**
     * Creates checkbox to enable XSD validation preference
     * 
     * @param parent
     * @return
     */
    private Composite createXSDGenSection(Composite parent) {
        Group grp = new Group(parent, SWT.NONE);
        grp.setLayout(new GridLayout());
        grp.setText(Messages.BomGenPreferencePage_xsdGenerationOption_group_title);

        enableXSDValidationBtn = new Button(grp, SWT.CHECK);
        enableXSDValidationBtn
                .setText(Messages.BomGenPreferencePage_xsdEnableValidation_label);
        enableXSDValidationBtn.setSelection(enableXSDValidation);

        return grp;
    }

    /**
     * Creates checkbox to enable WSDL validation preference
     * 
     * @param parent
     * @return
     */
    private Composite createWSDLGenSection(Composite parent) {
        Group grp = new Group(parent, SWT.NONE);
        grp.setLayout(new GridLayout());
        grp.setText(Messages.BomGenPreferencePage_wsdlGenerationOption_group_title);

        enableWSDLValidationBtn = new Button(grp, SWT.CHECK);
        enableWSDLValidationBtn
                .setText(Messages.BomGenPreferencePage_wsdlEnableValidation_label);
        enableWSDLValidationBtn.setSelection(enableWSDLValidation);

        return grp;
    }

    /**
     * Creates checkbox to enable delete of BOM on clean for a WSDL to BOM
     * builder and also for re-generation of BOM on import
     * 
     * @param parent
     * @return
     */
    private Composite createBOMGenSection(Composite parent) {
        Group grp = new Group(parent, SWT.NONE);
        grp.setLayout(new GridLayout());
        grp.setText(Messages.BomGenPreferencePage_bomGenerationOption_group_title);
        enableDeleteBomInWsdlToBomBuilderOnClean = new Button(grp, SWT.CHECK);
        enableDeleteBomInWsdlToBomBuilderOnClean
                .setText(Messages.BomGenPreferencePage_bomEnableGeneration_label);
        enableDeleteBomInWsdlToBomBuilderOnClean
                .setSelection(enableBOMGeneration);

        enableRegenerateBomOnProjectImport = new Button(grp, SWT.CHECK);
        enableRegenerateBomOnProjectImport
                .setText(Messages.BomGenPreferencePage_bomEnableReGenerationOnImport_label);
        enableRegenerateBomOnProjectImport
                .setSelection(enableBOMReGenerationOnImport);

        return grp;
    }

    /**
     * Creates checkbox to enable delete of XSD on clean for a BOM to XSD
     * builder.
     * 
     * @param parent
     * @return
     */
    private Composite createBomXsdDeleteOnCleanSection(Composite parent) {
        Group grp = new Group(parent, SWT.NONE);
        grp.setLayout(new GridLayout());
        grp.setText(Messages.BomGenPreferencePage_xsdGenOptions_group_label);
        enableDeleteXsdInBomToXsdBuilderOnClean = new Button(grp, SWT.CHECK);
        enableDeleteXsdInBomToXsdBuilderOnClean
                .setText(Messages.BomGenPreferencePage_regenerateXsd_button);
        enableDeleteXsdInBomToXsdBuilderOnClean
                .setSelection(enableDeleteOnCleanForBomXsdBuilder);

        return grp;
    }

}