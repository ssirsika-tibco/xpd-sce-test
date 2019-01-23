/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractPreferencePageContributor;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * The "Process Modeler" Business Studio preference page.
 * <p>
 * Now made contributable via the
 * <code>com.tibco.xpd.processeditor.xpdl2.processEditorProeferenceContribution</code>
 * extension point
 * 
 * @author aallway
 * @since forever
 */
public class ProcessEditorPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private BooleanFieldEditor dontAskSaveBooleanEditor;

    private BooleanFieldEditor dontAskProjectReference;

    private Button wsdlChecksumOption;

    private IPreferenceStore preferenceStore;

    private final IPreferenceStore xpdl2ResourcesPluginStore;

    private List<AbstractPreferencePageContributor> preferenceExtensions;

    public ProcessEditorPreferencePage() {
        super(
                Messages.ProcessEditorPreferencePage_ProcessEditorPreferencePage_title);
        preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        xpdl2ResourcesPluginStore =
                Xpdl2ResourcesPlugin.getDefault().getPreferenceStore();

        /*
         * Sid XPD-6407 Allow contribution of controls to process modeler pref
         * page.
         */
        loadExtensionContributions();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());

        /*
         * If in RCP application then don't show these options
         */
        if (!XpdResourcesPlugin.isRCP()) {
            dontAskSaveBooleanEditor =
                    new BooleanFieldEditor(
                            ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                            Messages.ProcessEditorPreferencePage_SaveProcEditorsPreference_shortdesc,
                            composite);
            dontAskSaveBooleanEditor.setPreferenceStore(preferenceStore);
            dontAskSaveBooleanEditor.load();

            dontAskProjectReference =
                    new BooleanFieldEditor(
                            Xpdl2ResourcesConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE,
                            Messages.ProcessEditorPreferencePage_dontAskToSetProjectReference_preference_shortdesc,
                            composite);

            // Using the analyst resources plugin's preference store as this
            // preference is used in that plugin by ProcessUIUtil
            dontAskProjectReference
                    .setPreferenceStore(xpdl2ResourcesPluginStore);
            dontAskProjectReference.load();

            /*
             * Add a WSDL group to contain the checksum option
             */
            Group wsdlGroup = new Group(composite, SWT.NONE);
            wsdlGroup
                    .setText(Messages.ProcessEditorPreferencePage_wsdl_group_label);
            GridLayout layout = new GridLayout();
            wsdlGroup.setLayout(layout);
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
            data.verticalIndent = 10;
            wsdlGroup.setLayoutData(data);

            /*
             * Using Button rather than the BooleanFieldEditor as the latter
             * doesn't layout properly in a Group.
             */
            wsdlChecksumOption = new Button(wsdlGroup, SWT.CHECK);
            wsdlChecksumOption
                    .setText(Messages.ProcessEditorPreferencePage_updateGeneratedWsdlTargetNamespace_option_label);
            wsdlChecksumOption
                    .setData(Xpdl2ResourcesConsts.PREF_GENERATE_CHECKSUM_FOR_GENERATED_WSDL_NAMESPACE);
            wsdlChecksumOption.setSelection(xpdl2ResourcesPluginStore
                    .getBoolean((String) wsdlChecksumOption.getData()));

        } else {
            Label lbl = new Label(composite, SWT.WRAP);
            lbl.setText(Messages.ProcessEditorPreferencePage_expandCategory_label);
            noDefaultAndApplyButton();
        }

        /*
         * Sid XPD-6407 Allow contribution of controls to process modeler pref
         * page.
         */
        for (AbstractPreferencePageContributor contributor : preferenceExtensions) {
            contributor.initFromProcessModelerPage(this);

            Composite extContainer = new Composite(composite, SWT.NONE);
            extContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            extContainer.setLayout(fillLayout);

            contributor.createContents(extContainer);

        }

        return composite;
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     * 
     * @return
     */
    @Override
    public boolean performOk() {
        if (dontAskProjectReference != null) {
            dontAskSaveBooleanEditor.store();
            dontAskProjectReference.store();
            xpdl2ResourcesPluginStore.setValue((String) wsdlChecksumOption
                    .getData(), wsdlChecksumOption.getSelection());
        }

        /*
         * Sid XPD-6407 Allow contribution of controls to process modeler pref
         * page.
         */
        for (PreferencePage contributor : preferenceExtensions) {
            contributor.performOk();
        }

        return super.performOk();
    }

    @Override
    protected void performDefaults() {
        if (dontAskProjectReference != null) {
            dontAskProjectReference.loadDefault();
            dontAskSaveBooleanEditor.loadDefault();
            xpdl2ResourcesPluginStore.setToDefault((String) wsdlChecksumOption
                    .getData());
            wsdlChecksumOption.setSelection(xpdl2ResourcesPluginStore
                    .getBoolean((String) wsdlChecksumOption.getData()));
        }

        /*
         * Sid XPD-6407 Allow contribution of controls to process modeler pref
         * page.
         */
        for (AbstractPreferencePageContributor contributor : preferenceExtensions) {
            contributor.performDefaults();
        }

        super.performDefaults();
    }

    /**
     * Load the preference page contributions from
     * <code>com.tibco.xpd.processeditor.xpdl2.processEditorProeferenceContribution</code>
     * extension point
     */
    private void loadExtensionContributions() {

        preferenceExtensions =
                new ArrayList<AbstractPreferencePageContributor>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                "processEditorPreferenceContribution"); //$NON-NLS-1$

        if (point != null) {
            // Get all extensions of the convert project extension point
            IExtension[] exts = point.getExtensions();

            if (exts != null) {
                for (IExtension eachExtension : exts) {
                    IConfigurationElement[] elements =
                            eachExtension.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            if ("processModelerPageContributor".equals(elem //$NON-NLS-1$
                                    .getName())) {
                                Object obj = null;

                                try {
                                    obj =
                                            elem.createExecutableExtension("preferencePageContribution"); //$NON-NLS-1$

                                    if (obj instanceof AbstractPreferencePageContributor) {
                                        preferenceExtensions
                                                .add((AbstractPreferencePageContributor) obj);

                                    } else {
                                        Xpdl2ProcessEditorPlugin
                                                .getDefault()
                                                .getLogger()
                                                .error(String.format("%s contributed invalid class type (not instanceof com.tibco.xpd.processeditor.xpdl2.extensions.AbstractPreferencePageContributor)", //$NON-NLS-1$
                                                        elem.getContributor()
                                                                .getName()));
                                    }
                                } catch (Exception e) {
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getLogger()
                                            .error(e,
                                                    String.format("Exception loading processEditorPreferenceContribution from contributor %s", //$NON-NLS-1$
                                                            elem.getContributor()
                                                                    .getName()));
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
