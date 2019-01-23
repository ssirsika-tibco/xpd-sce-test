/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractPreferencePageContributor;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;

/**
 * Contributes controls to the Process Modeler preference page.
 * 
 * @author aallway
 * @since 4 Jul 2014
 */
public class FlowAnalyzerPreferenceContributor extends
        AbstractPreferencePageContributor {

    /**
     * The preference store id of the BPM flow analyzer timeout.
     */
    public static final String FLOW_ANALYZER_TIMEOUT_PREF =
            "bpm_flow_analyzer_timeout"; //$NON-NLS-1$

    public static final long FLOW_ANALYZER_TIMEOUT_DEFAULT = 30;

    private StringFieldEditor analyzerTimeoutEditor;

    public FlowAnalyzerPreferenceContributor() {
        setTitle(Messages.FlowAnalyzerPreferenceContributor_BPMProcessesGroup_label);
    }

    /**
     * @return The process flow analyzer timeout in seconds setting - 0 = no
     *         timeout.
     */
    public static long getFlowAnalyzerTimeout() {
        IPreferenceStore preferenceStore =
                BundleActivator.getDefault().getPreferenceStore();

        long timeout = preferenceStore.getLong(FLOW_ANALYZER_TIMEOUT_PREF);
        if (timeout < 0) {
            timeout = 0;
        }

        return timeout;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public Control createContents(Composite parent) {
        Group root = new Group(parent, SWT.NONE);
        GridLayout gl = new GridLayout(1, false);
        root.setLayout(gl);

        root.setText(Messages.FlowAnalyzerPreferenceContributor_BPMProcessesGroup_label);

        analyzerTimeoutEditor =
                new StringFieldEditor(
                        FLOW_ANALYZER_TIMEOUT_PREF,
                        Messages.FlowAnalyzerPrefernceContributor_FLowAnalyzerPrefPage_Timeout_label,
                        root) {
                    /**
                     * @see org.eclipse.jface.preference.StringFieldEditor#valueChanged()
                     * 
                     */
                    @Override
                    protected void valueChanged() {
                        if (!validateAnalyzerTimeout(analyzerTimeoutEditor
                                .getStringValue())) {
                            FlowAnalyzerPreferenceContributor.this
                                    .setErrorMessage(Messages.FlowAnalyzerPreferenceContributor_FlowAnalyzerTimeout_error);

                        } else {
                            FlowAnalyzerPreferenceContributor.this
                                    .setErrorMessage(null);
                        }
                    }
                };

        analyzerTimeoutEditor.setPreferenceStore(BundleActivator.getDefault()
                .getPreferenceStore());

        analyzerTimeoutEditor.setStringValue(String.valueOf(BundleActivator
                .getDefault().getPreferenceStore()
                .getLong(FLOW_ANALYZER_TIMEOUT_PREF)));

        Label labelControl = analyzerTimeoutEditor.getLabelControl(root);
        GridData ld = new GridData();
        ld.horizontalIndent = 5;
        labelControl.setLayoutData(ld);

        labelControl
                .setToolTipText(Messages.FlowAnalyzerPreferenceContributor_FlowAnalyzerTimeout_tooltip);

        Text textControl = analyzerTimeoutEditor.getTextControl(root);
        textControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        textControl
                .setToolTipText(Messages.FlowAnalyzerPreferenceContributor_AnalyzerTimeoutEditor_tooltip);

        textControl.addVerifyListener(new DigitTextVerifyListener());

        // analyzerTimeoutEditor.setTextLimit(String.valueOf(Long.MAX_VALUE)
        // .length());

        analyzerTimeoutEditor
                .setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);

        return root;
    }

    /**
     * 
     * @param value
     * @return true if value is a valid timeout.
     */
    private boolean validateAnalyzerTimeout(String value) {
        boolean ok = true;

        if (value == null || value.length() == 0) {
            ok = false;

        } else {
            try {
                Long timeout = Long.valueOf(value);
                if (timeout < 0) {
                    ok = false;
                }
            } catch (NumberFormatException ex) {
                ok = false;
            }
        }
        return ok;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractPreferencePageContributor#performOk()
     * 
     * @return
     */
    @Override
    public boolean performOk() {
        String stringValue = analyzerTimeoutEditor.getStringValue();

        if (!validateAnalyzerTimeout(stringValue)) {
            return false;
        }

        analyzerTimeoutEditor.store();

        return true;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractPreferencePageContributor#performDefaults()
     * 
     */
    @Override
    public void performDefaults() {
        BundleActivator
                .getDefault()
                .getPreferenceStore()
                .setDefault(FLOW_ANALYZER_TIMEOUT_PREF,
                        FLOW_ANALYZER_TIMEOUT_DEFAULT);

        analyzerTimeoutEditor.setStringValue(String
                .valueOf(FLOW_ANALYZER_TIMEOUT_DEFAULT));

    }

}
