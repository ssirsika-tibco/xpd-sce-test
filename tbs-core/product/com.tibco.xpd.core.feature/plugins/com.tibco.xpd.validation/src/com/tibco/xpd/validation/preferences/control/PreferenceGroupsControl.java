/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.preferences.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;
import com.tibco.xpd.validation.provider.IPreferenceGroup;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * This will create a control (<code>Composite</code>) containing all validation
 * preference groups set with a given preference id. Each preference group will
 * be set in an expandable composite with the issues belonging to it displayed
 * in a description - combo pair (similar style to the Java compiler
 * Error/Warning preference page).
 * <p>
 * If the state of the expandable groups need to be persisted then set the
 * <code>IDialogSettings</code> of the plug-in using
 * <code>{@link #setDialogSetting(IDialogSettings, String)}</code> and call
 * <code>{@link #dispose()}</code> when the control needs disposing to persist
 * the status. Alternatively, <code>{@link #storeExpansionState()}</code> can be
 * called to force the storing of the expansion state of each group.
 * </p>
 * <p>
 * Use <code>{@link #createControl(Composite, int)}</code> to create the
 * composite that will contain the preference groups.
 * </p>
 * <p>
 * Use <code>{@link #hasValueChanged()}</code> to determine if any severity
 * values in the included issues have been changed and
 * <code>{@link #saveChangedPreferences()}</code> to save these changes to the
 * preference store. Use <code>{@link #restoreDefaults()}</code> to reset all
 * issue severity values to their defaults.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class PreferenceGroupsControl {

    /**
     * Severity enum. Four severity levels are allowed - Error, Warning, Info
     * and Ignore. The value of this levels correspond to the
     * <code>{@link IMarker}</code> severity values. Ignore will be set to a
     * value of -1 (this indicates that this issue should be ignored).
     * 
     * @author njpatel
     * 
     */
    protected enum Severity {
        ERROR(IMarker.SEVERITY_ERROR), WARNING(IMarker.SEVERITY_WARNING), INFO(
                IMarker.SEVERITY_INFO), IGNORE(-1);

        private int value;

        private static final String ERROR_STR =
                Messages.AbstractValidationPreferencePage_error_label;

        private static final String WARNING_STR =
                Messages.AbstractValidationPreferencePage_warning_label;

        private static final String INFO_STR =
                Messages.AbstractValidationPreferencePage_info_label;

        private static final String IGNORE_STR =
                Messages.AbstractValidationPreferencePage_ignore_label;

        Severity(int value) {
            this.value = value;
        }

        /**
         * Get the <code>{@link IMarker}</code> severity value
         * 
         * @return <code>{@link IMarker#SEVERITY_ERROR}</code>,
         *         <code>{@link IMarker#SEVERITY_INFO}</code>,
         *         <code>{@link IMarker#SEVERITY_WARNING}</code> or -1 (Ignore).
         */
        public int getValue() {
            return value;
        }

        /**
         * Text equivalent of the severity level.
         * 
         * @return "Error", "Warning", "Info" or "Ignore"
         */
        public String getText() {
            String text = ""; //$NON-NLS-1$

            switch (value) {
            case IMarker.SEVERITY_ERROR:
                text = ERROR_STR;
                break;

            case IMarker.SEVERITY_WARNING:
                text = WARNING_STR;
                break;

            case IMarker.SEVERITY_INFO:
                text = INFO_STR;
                break;

            case -1:
                text = IGNORE_STR;
                break;
            }

            return text;
        }

        /**
         * Get the <code>Severity</code> value corresponding to the given text
         * 
         * @param text
         * 
         * @return <code>Severity</code>.
         */
        public static Severity getSeverity(String text) {
            Severity severity = null;

            if (text != null) {
                if (text.equals(ERROR_STR)) {
                    severity = ERROR;
                } else if (text.equals(WARNING_STR)) {
                    severity = WARNING;
                } else if (text.equals(INFO_STR)) {
                    severity = INFO;
                } else if (text.equals(IGNORE_STR)) {
                    severity = IGNORE;
                }
            }

            return severity;
        }

        /**
         * Get the Severity corresponding to the <code>{@link IMarker}</code>
         * severity value, or -1 (Ignore).
         * 
         * @param value
         * @return <code>Severity</code> if value is a valid severity value,
         *         <code>null</code> will be returned otherwise.
         */
        public static Severity getSeverity(int value) {
            Severity severity = null;

            switch (value) {
            case IMarker.SEVERITY_ERROR:
                severity = ERROR;
                break;

            case IMarker.SEVERITY_WARNING:
                severity = WARNING;
                break;

            case IMarker.SEVERITY_INFO:
                severity = INFO;
                break;

            case -1:
                severity = IGNORE;
                break;
            }

            return severity;
        }
    }

    private final String preferenceId;

    private final IPreferenceGroup[] groups;

    private List<ExpandableComposite> expComposites =
            new ArrayList<ExpandableComposite>();

    private List<Combo> issueCombos = new ArrayList<Combo>();

    private static final String SETTINGS_EXPANDED_NAME = "expanded"; //$NON-NLS-1$

    private IDialogSettings dialogSettings;

    private String sectionId;

    private Map<String, Severity> changedPreferences =
            new HashMap<String, Severity>();

    private boolean empty;

    /**
     * Constructor.
     * 
     * @param preferenceId
     *            groups to include with this preference id.
     */
    public PreferenceGroupsControl(String preferenceId) {
        if (preferenceId == null) {
            throw new NullPointerException("Preference id is null."); //$NON-NLS-1$
        }

        this.preferenceId = preferenceId;

        // Get the groups associated with the preference id
        groups =
                ValidationManager.getInstance().getValidationEngine()
                        .getPreferenceGroupsByPreferenceId(preferenceId);

        empty = true;
        for (IPreferenceGroup grp : groups) {
            if (grp.getIssues().length != 0) {
                empty = false;
                break;
            }
        }
    }

    /**
     * Get the preference id assigned to this control.
     * 
     * @return preferenceId
     */
    public String getPreferenceId() {
        return preferenceId;
    }

    /**
     * Get the groups associated with the set preference id.
     * 
     * @return Array of <code>IPreferenceGroup</code> objects for each
     *         preference group with the given preference id. If no groups are
     *         found an empty array will be returned.
     */
    public IPreferenceGroup[] getPreferenceGroups() {
        return groups;
    }

    /**
     * Set the IDialogSettings to persist the state of the groups.
     * 
     * @param dialogSettings
     *            <code>IDialogSettings</code> of the plugin
     * @param sectionId
     *            Section to add to the dialog settings to persist this data.
     */
    public void setDialogSetting(IDialogSettings dialogSettings,
            String sectionId) {
        this.dialogSettings = dialogSettings;
        this.sectionId = sectionId;

        if (this.dialogSettings == null) {
            throw new NullPointerException("Dialog settings is null."); //$NON-NLS-1$
        }

        if (this.sectionId == null) {
            throw new NullPointerException("Section id is null."); //$NON-NLS-1$
        } else if (this.sectionId.length() == 0) {
            throw new IllegalArgumentException("Section id cannot be blank."); //$NON-NLS-1$
        }
    }

    /**
     * Check if the preference values in this group have changed.
     * 
     * @return <code>true</code> if values have changed.
     */
    public boolean hasValueChanged() {
        return !changedPreferences.isEmpty();
    }

    /**
     * Save all changed preferences.
     * 
     * @return <code>true</code> if changed values were saved to the preference
     *         store, <code>false</code> if no values needed saving.
     */
    public boolean saveChangedPreferences() {
        boolean saved = false;

        for (String issueId : changedPreferences.keySet()) {
            // Get the default value of this issue
            int defaultValue =
                    ValidationPreferenceUtil.getDefaultPreferenceValue(issueId);
            int newValue = changedPreferences.get(issueId).getValue();

            // If the default value is the same as the new value then reset the
            // preference to default
            if (defaultValue == newValue) {
                ValidationPreferenceUtil.setPreferenceToDefault(issueId);
            } else {
                // Update the preference value
                ValidationPreferenceUtil.setPreferenceValue(issueId, newValue);
            }
            saved = true;
        }
        // Clear all changed preferences
        changedPreferences.clear();

        return saved;
    }

    /**
     * Create the expandable groups in a single composite.
     * 
     * @param parent
     *            parent composite that will contain the groups.
     * @param minWidth
     *            the minimum width this control should be set to.
     * @return Composite containing all the groups arranged vertically.
     */
    public Composite createControl(Composite parent, int minWidth) {
        Composite grpComposite;
        ScrolledForm form = new ScrolledForm(parent);
        form.setExpandHorizontal(true);
        form.setExpandVertical(true);
        GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gData.minimumWidth = minWidth;
        form.setLayoutData(gData);
        Composite formBody = form.getBody();
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        formBody.setLayout(layout);
        formBody.setFont(parent.getFont());

        for (IPreferenceGroup grp : groups) {
            grpComposite = createGroup(formBody, grp);

            if (grpComposite != null) {
                grpComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP,
                        true, false));
            }
        }

        restoreExpansionState();

        return form;
    }

    /**
     * Restore all the defaule severity values for all issues in this group
     */
    public void restoreDefaults() {
        // Restore all the default values
        for (Combo cmb : issueCombos) {
            int value =
                    ValidationPreferenceUtil
                            .getDefaultPreferenceValue((String) cmb.getData());
            Severity severity = Severity.getSeverity(value);
            if (severity != null) {
                // If value has changed then update combo and add to map
                if (!cmb.getText().equals(severity.getText())) {
                    cmb.setText(severity.getText());
                    changedPreferences.put((String) cmb.getData(), severity);
                }
            }
        }
    }

    /**
     * Dispose off any resources. Also stores the expansion state of the groups
     * if a <code>IDialogSettings</code> is provided.
     */
    public void dispose() {
        storeExpansionState();
    }

    /**
     * Create the expandable group in the given <code>Composite</code>.
     * 
     * @param parent
     *            parent <code>Composite</code>.
     * 
     * @return group composite. This will be <code>null</code> if the group
     *         contains no issues.
     */
    @SuppressWarnings("unchecked")
    protected Composite createGroup(Composite parent, IPreferenceGroup group) {
        IssueInfo[] issues = group.getIssues();
        ExpandableComposite grpComposite = null;
        if (issues.length > 0) {
            grpComposite = new ExpandableComposite(parent, SWT.NONE);
            grpComposite.setFont(JFaceResources.getFontRegistry()
                    .getBold(JFaceResources.DIALOG_FONT));
            grpComposite.setText(group.getName());

            Composite container = new Composite(grpComposite, SWT.NONE);
            GridLayout layout = new GridLayout(2, false);
            layout.marginRight = 0;
            layout.marginLeft = 10;
            layout.marginBottom = 5;
            layout.marginTop = 0;
            container.setLayout(layout);
            container.setFont(parent.getFont());

            // Sorted
            List<IssueInfo> list = Arrays.asList(group.getIssues());
            Collections.sort(list);

            // IssueInfo[] issues2 = (IssueInfo[])list.toArray();
            // for (IssueInfo issue : issues2) {
            // addCombo(container, issue);
            // }

            for (IssueInfo issueInfo : list) {
                addCombo(container, issueInfo);
            }
            grpComposite.setClient(container);
            expComposites.add(grpComposite);

            grpComposite.addExpansionListener(new ExpansionAdapter() {
                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    ExpandableComposite composite =
                            (ExpandableComposite) e.getSource();
                    ScrolledForm form = getParentScrolledForm(composite);

                    if (form != null) {
                        form.reflow(true);
                    }
                }
            });
        }

        return grpComposite;
    }

    /**
     * Get the parent <code>ScrolledForm</code> of the given <i>composite</i>.
     * 
     * @param composite
     * @return The ancestor <code>ScrolledForm</code> object. If none found then
     *         <code>null</code> will be returned.
     */
    private ScrolledForm getParentScrolledForm(Composite composite) {

        if (composite != null) {
            if (composite instanceof ScrolledForm) {
                return (ScrolledForm) composite;
            }

            return getParentScrolledForm(composite.getParent());
        }

        return null;
    }

    /**
     * Add the issue description - severity combo pairing to the <i>parent</i>
     * <code>Composite</code>.
     * 
     * @param parent
     * @param issue
     */
    private void addCombo(Composite parent, IssueInfo issue) {
        Label lbl = new Label(parent, SWT.NONE);
        GridData gData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gData.widthHint = 10;
        lbl.setLayoutData(gData);

        String text =
                issue.getPreferenceDescription() != null ? issue
                        .getPreferenceDescription() : issue.getMessage();
        // Trim the text and insert : at end of string
        text = text.trim();
        text = text.replaceAll("\\.$", ":"); //$NON-NLS-1$ //$NON-NLS-2$
        if (!text.endsWith(":")) { //$NON-NLS-1$
            text += ":"; //$NON-NLS-1$
        }
        lbl.setText(text);

        Combo cmb = new Combo(parent, SWT.NONE);
        cmb.add(Severity.ERROR.getText());
        cmb.add(Severity.WARNING.getText());
        cmb.add(Severity.INFO.getText());
        cmb.add(Severity.IGNORE.getText());
        cmb.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
        cmb.setData(issue.getId());
        cmb.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Combo src = (Combo) e.getSource();

                // If value has changed then add to change map
                Severity newSeverity = Severity.getSeverity(src.getText());
                String issueId = (String) src.getData();
                // Get current set severity
                int currentValue =
                        ValidationPreferenceUtil.getPreferenceValue(issueId);

                if (newSeverity.getValue() != currentValue) {
                    changedPreferences.put(issueId, newSeverity);
                } else if (changedPreferences.containsKey(issueId)) {
                    // Value set back to current value so remove the issue from
                    // change map
                    changedPreferences.remove(issueId);
                }
            }
        });

        issueCombos.add(cmb);

        int value = ValidationPreferenceUtil.getPreferenceValue(issue.getId());

        Severity severity = Severity.getSeverity(value);

        if (severity != null) {
            cmb.setText(severity.getText());
        }
    }

    /**
     * Store the expansion state of any groups. This will be stored in the
     * <code>IDialogSettings</code> set in
     * {@link #setDialogSetting(IDialogSettings, String) setDialogSetting}.
     */
    public void storeExpansionState() {
        if (expComposites != null) {

            if (dialogSettings != null) {
                // Create new section
                IDialogSettings settings =
                        dialogSettings.addNewSection(sectionId);

                for (int idx = 0; idx < expComposites.size(); idx++) {
                    settings.put(SETTINGS_EXPANDED_NAME + String.valueOf(idx),
                            expComposites.get(idx).isExpanded());
                }
            }
        }
    }

    /**
     * Restore the expansion state of any groups. This will be read from the
     * <code>IDialogSettings</code> set using
     * {@link #setDialogSetting(IDialogSettings, String) setDialogSetting}.
     */
    public void restoreExpansionState() {
        if (expComposites != null) {

            if (dialogSettings != null) {
                // Get relevant section
                IDialogSettings settings = dialogSettings.getSection(sectionId);

                for (int idx = 0; idx < expComposites.size(); idx++) {
                    if (settings == null) {
                        // Always expand the first group
                        expComposites.get(idx).setExpanded(idx == 0);
                    } else {
                        expComposites.get(idx).setExpanded(settings
                                .getBoolean(SETTINGS_EXPANDED_NAME
                                        + String.valueOf(idx)));
                    }
                }
            }
        }
    }

    /**
     * Return if there are any issues in any group.
     * 
     * @return true if there is at least one issue to be shown.
     */
    public boolean isEmpty() {
        return empty;
    }
}
