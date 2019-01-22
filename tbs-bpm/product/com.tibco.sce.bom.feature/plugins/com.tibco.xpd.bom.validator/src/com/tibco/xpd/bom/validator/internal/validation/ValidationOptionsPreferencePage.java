/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator.internal.validation;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Preference page to enable/disable the BOM XSD and WSDL validation. This page
 * will be available both at workspace (preferences) and project (properties)
 * level.
 * 
 * @author njpatel
 * 
 */
public class ValidationOptionsPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, IWorkbenchPropertyPage {

    // Data set on the page when called as a link for a preference or properties
    // page. This will indicate not show the link to go "back".
    private static final Object LINKED = new Object();

    private static final String PREFERENCE_PAGE_ID =
            "com.tibco.xpd.bom.validator.preferences.validation"; //$NON-NLS-1$

    private static final String PROPERTIES_PAGE_ID =
            "com.tibco.xpd.bom.validator.properties.validation"; //$NON-NLS-1$

    private Group optionsGrp;

    private Button wsdlOption;

    private Button xsdOption;

    private Button projectSpecificOption;

    private Button xsdValidateOption;

    private Group importOptionsGrp;

    private boolean enableWsdl;

    private boolean enableXsd;

    private boolean enableValidateXsd;

    private boolean valueChanged = false;

    private IProject project;

    private Link workspaceLink;

    private Link projectLink;

    private BOMValidationPreferenceManager preferenceManager;

    private IPreferenceStore store;

    /**
     * Preference/properties page to set enable/disable of the BOM XSD and WSDL
     * validation.
     */
    public ValidationOptionsPreferencePage() {
        this(""); //$NON-NLS-1$
    }

    /**
     * Preference/properties page to set enable/disable of the BOM XSD and WSDL
     * validation.
     * 
     * @param title
     *            title of this preference page
     */
    public ValidationOptionsPreferencePage(String title) {
        super(title);
        initialize();
        noDefaultAndApplyButton();
    }

    @Override
    public void applyData(Object data) {
        if (data == LINKED) {
            // Hide the hyperlink
            if (workspaceLink != null && !workspaceLink.isDisposed()) {
                workspaceLink.setVisible(false);
            } else if (projectLink != null && !projectLink.isDisposed()) {
                projectLink.setVisible(false);
            }
        }
    }

    /**
     * Preference/properties page to set enable/disable of the BOM XSD and WSDL
     * validation.
     * 
     * @param title
     *            title of this preference page
     * @param image
     *            image for this preference page
     */
    public ValidationOptionsPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
        initialize();
        noDefaultAndApplyButton();
    }

    /**
     * Initialize the option values from the workspace or project preference
     * store.
     */
    private void initialize() {
        if (preferenceManager == null) {
            preferenceManager =
                    new BOMValidationPreferenceManager(getPreferenceStore());
        }

        if (null == store) {
            store = doGetPreferenceStore();
        }

        enableXsd =
                preferenceManager
                        .getPreferenceSetting(ValidationDestination.XSD);
        enableWsdl =
                preferenceManager
                        .getPreferenceSetting(ValidationDestination.WSDL);

        enableValidateXsd = store.getBoolean(BOMValidationUtil.VALIDATE_XSD);

        // If project specific settings are defined then override with those
        if (isProjectProperties() && isProjectSpecificSet()) {
            enableXsd =
                    preferenceManager.getPropertiesSetting(project,
                            ValidationDestination.XSD);
            enableWsdl =
                    preferenceManager.getPropertiesSetting(project,
                            ValidationDestination.WSDL);
            enableValidateXsd =
                    store.getBoolean(BOMValidationUtil.VALIDATE_XSD);
        }
    }

    /**
     * Update the options on this page with the latest value set.
     */
    private void updateOptions() {
        if (xsdOption != null && !xsdOption.isDisposed()) {
            xsdOption.setSelection(enableXsd);
            wsdlOption.setSelection(enableWsdl);
            xsdValidateOption.setSelection(enableValidateXsd);
        }
    }

    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return BOMValidatorActivator.getDefault().getPreferenceStore();
    }

    @Override
    protected Control createContents(Composite parent) {
        OptionSelectionListener listener = new OptionSelectionListener();
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        if (isProjectProperties()) {
            Composite section = new Composite(root, SWT.NONE);
            section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
            section.setLayout(new GridLayout(2, false));
            projectSpecificOption = new Button(section, SWT.CHECK);
            projectSpecificOption
                    .setText(Messages.ValidationOptionsPreferencePage_EnableProjSetting_label);
            projectSpecificOption.setSelection(isProjectSpecificSet());
            projectSpecificOption.setLayoutData(new GridData(SWT.FILL,
                    SWT.CENTER, true, false));
            projectSpecificOption.addSelectionListener(listener);

            workspaceLink = new Link(section, SWT.NONE);
            workspaceLink
                    .setText("<A>" + Messages.ValidationOptionsPreferencePage_ConfigureWorkspaceSettings_label //$NON-NLS-1$
                            + "</A>"); //$NON-NLS-1$
            workspaceLink.setLayoutData(new GridData(SWT.END, SWT.CENTER,
                    false, false));
            workspaceLink.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    doLinkActivated((Link) e.widget);
                }

                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    doLinkActivated((Link) e.widget);
                }
            });

            Label separator = new Label(root, SWT.SEPARATOR | SWT.HORIZONTAL);
            separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
        } else {
            Composite section = new Composite(root, SWT.NONE);
            section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
            section.setLayout(new GridLayout());
            /*
             * Show apply project specific settings
             */
            projectLink = new Link(section, SWT.NONE);
            projectLink
                    .setText("<A>" //$NON-NLS-1$
                            + Messages.ValidationOptionsPreferencePage_ConfigureProjectSettings_label
                            + "</A>"); //$NON-NLS-2$
            projectLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
                    false));
            projectLink.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    doLinkActivated((Link) e.widget);
                }

                @Override
                public void widgetSelected(SelectionEvent e) {
                    doLinkActivated((Link) e.widget);
                }
            });
        }

        optionsGrp = new Group(root, SWT.NONE);
        optionsGrp
                .setText(Messages.ValidationOptionsPreferencePage_ValidationOptions_label);
        optionsGrp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        optionsGrp.setLayout(new GridLayout());

        // WDSL option
        wsdlOption = new Button(optionsGrp, SWT.CHECK);
        wsdlOption
                .setText(Messages.ValidationOptionsPreferencePage_EnableWsdlExportGen_button);
        wsdlOption.addSelectionListener(listener);

        // XSD option
        xsdOption = new Button(optionsGrp, SWT.CHECK);
        xsdOption
                .setText(Messages.ValidationOptionsPreferencePage_EnableXsdExport_button);
        xsdOption.addSelectionListener(listener);
        GridData data = new GridData();
        // data.horizontalIndent = 20;
        xsdOption.setLayoutData(data);

        Label lbl = new Label(optionsGrp, SWT.WRAP);
        lbl.setText(Messages.ValidationOptionsPreferencePage_SameCapabilityMayBeEnabledByDestinations_longdesc);
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.horizontalIndent = 16;
        lbl.setLayoutData(data);

        /* XPD-4556: Validate XSD option */
        importOptionsGrp = new Group(root, SWT.NONE);
        importOptionsGrp
                .setText(Messages.ValidationOptionsPreferencePage_ValidateXSDOption_label);
        importOptionsGrp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        importOptionsGrp.setLayout(new GridLayout());

        xsdValidateOption = new Button(importOptionsGrp, SWT.CHECK);
        xsdValidateOption
                .setText(Messages.ValidationOptionsPreferencePage_ValidateXSDOption_button);
        xsdValidateOption.setSelection(doGetPreferenceStore()
                .getBoolean(BOMValidationUtil.VALIDATE_XSD));
        xsdValidateOption.addSelectionListener(listener);

        lbl = new Label(root, SWT.WRAP);
        lbl.setText(isProjectProperties() ? Messages.ValidationOptionsPreferencePage_ApplyingChangeWillTriggerProjBuild_longdesc
                : Messages.ValidationOptionsPreferencePage_ApplyingChangeWillTriggerFullBuild_longdesc);
        lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        updateOptions();
        enableOptions(!isProjectProperties() || isProjectSpecificSet());

        return root;
    }

    @Override
    public boolean performOk() {
        if (valueChanged) {
            if (isProjectProperties() && projectSpecificOption != null) {
                // Project specific value set
                updateProjectSpecificValues(projectSpecificOption
                        .getSelection());
            } else {
                updateWorkspaceValues();
            }

            // Run build
            BuildJob job =
                    new BuildJob(
                            Messages.ValidationOptionsPreferencePage_ValidationBuild_label,
                            project);
            job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory()
                    .buildRule());
            job.setSystem(true);
            job.schedule();
        }

        return super.performOk();
    }

    /**
     * Update the workspace preference store with the values set in this page.
     */
    private void updateWorkspaceValues() {
        preferenceManager.setPreferenceSetting(ValidationDestination.XSD,
                enableXsd);
        preferenceManager.setPreferenceSetting(ValidationDestination.WSDL,
                enableWsdl);
        store.setValue(BOMValidationUtil.VALIDATE_XSD, enableValidateXsd);
    }

    /**
     * Update the project preference store with the values set in this page.
     */
    private void updateProjectSpecificValues(boolean applyProjectSpecificValues) {
        if (applyProjectSpecificValues) {
            preferenceManager.setPropertiesSetting(project,
                    ValidationDestination.XSD,
                    enableXsd);
            preferenceManager.setPropertiesSetting(project,
                    ValidationDestination.WSDL,
                    enableWsdl);
            store.setValue(BOMValidationUtil.VALIDATE_XSD, enableValidateXsd);
        } else {
            preferenceManager.removeProperties(project);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
    }

    /**
     * Display workspace preference dialog when the configure hyperlink is
     * activated.
     * 
     * @param widget
     */
    private void doLinkActivated(Link widget) {

        if (widget == workspaceLink) {
            // Show workspace preference page
            PreferenceDialog dlg =
                    PreferencesUtil.createPreferenceDialogOn(getShell(),
                            PREFERENCE_PAGE_ID,
                            new String[] { PREFERENCE_PAGE_ID },
                            LINKED);
            if (dlg.open() == Dialog.OK
                    && !projectSpecificOption.getSelection()) {
                // Project specific option not selected so update the workspace
                // values
                initialize();
                updateOptions();
            }
        } else if (widget == projectLink) {
            ProjectSelectionPicker picker =
                    new ProjectSelectionPicker(getShell());
            if (picker.open() == Dialog.OK) {
                Object[] result = picker.getResult();
                if (result != null && result[0] instanceof IProject) {
                    PreferenceDialog dlg =
                            PreferencesUtil.createPropertyDialogOn(getShell(),
                                    (IProject) result[0],
                                    PROPERTIES_PAGE_ID,
                                    new String[] { PROPERTIES_PAGE_ID },
                                    LINKED);
                    dlg.open();
                }
            }
        }
    }

    /**
     * Enable/disable the options on this preference page.
     * 
     * @param enabled
     */
    private void enableOptions(boolean enabled) {
        if (xsdOption != null && !xsdOption.isDisposed()) {
            xsdOption.setEnabled(enabled);
            wsdlOption.setEnabled(enabled);
        }
        if (null != xsdValidateOption && !xsdValidateOption.isDisposed()) {
            xsdValidateOption.setEnabled(enabled);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     */
    @Override
    public IAdaptable getElement() {
        return project;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime
     * .IAdaptable)
     */
    @Override
    public void setElement(IAdaptable element) {
        project = (IProject) element.getAdapter(IProject.class);
        initialize();
    }

    /**
     * Is the project specific value set.
     * 
     * @return
     */
    public boolean isProjectSpecificSet() {
        boolean ret = false;
        if (isProjectProperties()) {
            ret = preferenceManager.isProjectSettingsSet(project);
        }
        return ret;
    }

    /**
     * Check if this page is being viewed as the project properties.
     * 
     * @return <code>true</code> if project properties, <code>false</code> if
     *         preference page.
     */
    private boolean isProjectProperties() {
        return project != null;
    }

    /**
     * Selection listener for options on this page.
     * 
     * @author njpatel
     * 
     */
    private class OptionSelectionListener extends SelectionAdapter {
        @Override
        public void widgetSelected(SelectionEvent e) {
            Widget widget = e.widget;

            if (widget instanceof Button) {
                Button btn = (Button) widget;

                if (btn == projectSpecificOption) {
                    valueChanged = true;
                    enableOptions(projectSpecificOption.getSelection());
                } else if (btn == wsdlOption) {
                    if (enableWsdl != wsdlOption.getSelection()) {
                        valueChanged = true;
                        enableWsdl = wsdlOption.getSelection();
                        // Make sure the XSD is enabled if wsdl is enabled
                        if (enableWsdl && !enableXsd) {
                            xsdOption.setSelection(true);
                            enableXsd = true;
                        }
                    }
                } else if (btn == xsdOption) {
                    if (enableXsd != xsdOption.getSelection()) {
                        valueChanged = true;
                        enableXsd = xsdOption.getSelection();
                        // If XSD is disabled then disable WSDL
                        if (!enableXsd && enableWsdl) {
                            wsdlOption.setSelection(false);
                            enableWsdl = false;
                        }
                    }
                } else if (btn == xsdValidateOption) {
                    if (enableValidateXsd != xsdValidateOption.getSelection()) {
                        valueChanged = true;
                        enableValidateXsd = xsdValidateOption.getSelection();
                    }
                }
            }
        }
    }

    /**
     * Project selection picker.
     * 
     * @author njpatel
     * 
     */
    private class ProjectSelectionPicker extends SelectionDialog {

        // sizing constants
        private final static int HEIGHT = 250;

        private final static int WIDTH = 300;

        private static final String DIALOG_SETTING_ID =
                "validationOptionsPreferencePage.projectSelection"; //$NON-NLS-1$

        private boolean showProjectsWithSettingsOnly;

        private TableViewer viewer;

        private List<IProject> projectsWithSettings;

        public ProjectSelectionPicker(Shell parent) {
            super(parent);
            setTitle(Messages.ValidationOptionsPreferencePage_ProjectSpecificConfiguration_label);
            projectsWithSettings = preferenceManager.getProjectsWithSettings();
            showProjectsWithSettingsOnly =
                    projectsWithSettings.isEmpty() ? false
                            : getDialogSetting(true);
            setHelpAvailable(false);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = (Composite) super.createDialogArea(parent);

            Label lbl = new Label(root, SWT.NONE);
            lbl.setText(Messages.ValidationOptionsPreferencePage_ProjectToConfig_label);
            viewer = new TableViewer(root);
            configure(viewer);
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.heightHint = HEIGHT;
            data.widthHint = WIDTH;
            viewer.getControl().setLayoutData(data);

            final Button opt = new Button(root, SWT.CHECK);
            opt.setText(Messages.ValidationOptionsPreferencePage_ShowOnlyProjectsWithSettings_button);
            opt.setSelection(showProjectsWithSettingsOnly);
            opt.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    showProjectsWithSettingsOnly = opt.getSelection();
                    setDialogSetting(showProjectsWithSettingsOnly);
                    viewer.refresh();
                }
            });

            return root;
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            super.createButtonsForButtonBar(parent);
            // Disable the ok button initially
            doSelectionChange(null);
        }

        /**
         * Configure the viewer - set the content provider, label providers,
         * filters, listeners and set the input.
         * 
         * @param viewer
         */
        private void configure(TableViewer viewer) {
            viewer.setLabelProvider(new WorkbenchLabelProvider());
            viewer.setContentProvider(new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    Object[] objs = null;
                    if (inputElement instanceof IWorkspaceRoot) {
                        objs = ((IWorkspaceRoot) inputElement).getProjects();
                    }
                    return objs != null ? objs : new Object[0];
                }

                @Override
                public void dispose() {
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }
            });
            viewer.addFilter(new XpdNatureProjectsOnly(false));
            viewer.addFilter(new ViewerFilter() {
                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    if (showProjectsWithSettingsOnly
                            && element instanceof IProject
                            && !projectsWithSettings.contains(element)) {
                        return false;
                    }
                    return true;
                }
            });
            viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
            viewer.addSelectionChangedListener(new ISelectionChangedListener() {

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    doSelectionChange(((IStructuredSelection) event
                            .getSelection()).toArray());
                }
            });

            viewer.addDoubleClickListener(new IDoubleClickListener() {
                @Override
                public void doubleClick(DoubleClickEvent event) {
                    okPressed();
                }
            });
        }

        /**
         * Update the selection.
         * 
         * @param selection
         */
        private void doSelectionChange(Object[] selection) {
            Button btn = getOkButton();
            if (selection != null && selection.length > 0) {
                setSelectionResult(selection);
            } else {
                setSelectionResult(null);
            }

            if (btn != null && !btn.isDisposed()) {
                btn.setEnabled(selection != null && selection.length > 0);
            }
        }

        /**
         * Get the dialog settings for whether projects with settings should
         * only be shown.
         * 
         * @param defaultValue
         *            value to return if no dialog settings set
         * @return
         */
        private boolean getDialogSetting(boolean defaultValue) {
            boolean ret = defaultValue;

            String value =
                    BOMValidatorActivator.getDefault().getDialogSettings()
                            .get(DIALOG_SETTING_ID);
            if (value != null) {
                ret = Boolean.parseBoolean(value);
            }

            return ret;
        }

        /**
         * Persist the check box status of
         * "show only projects with project settings". Update the
         * 
         * @param value
         */
        private void setDialogSetting(boolean value) {
            BOMValidatorActivator.getDefault().getDialogSettings()
                    .put(DIALOG_SETTING_ID, value);
        }
    }
}
