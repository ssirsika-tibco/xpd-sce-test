/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.preferences.documentation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.util.ImagePicker;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;

/**
 * Creates Documentation top-level Preference page
 * 
 * <p>
 * <i>Created: 28 June 2010</i>
 * 
 * @author mtorres
 * 
 */
public class DocumentationGeneralPreferencePage extends
        PropertyAndPreferencePage implements SelectionListener {

    private IAdaptable element;

    private Button dontAskToOverwrite;

    private Button btnUseCustomLogo;

    private Button btnBrowse;

    private Composite imagePickerContainer;

    private Text txtImageFileName;

    private CLabel lblImageFile;

    public static final String PROP_ID =
            "com.tibco.xpd.ui.preferences.documentation.DocumentationGeneralPropertyPage"; //$NON-NLS-1$

    public static final String PREF_ID =
            "com.tibco.xpd.ui.preferences.documentation.DocumentationGeneralPreferencePage"; //$NON-NLS-1$

    /**
     * Default value asking when exporting documentation value
     */
    private static final boolean DEFAULT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION =
            false;

    public DocumentationGeneralPreferencePage() {
    }

    @Override
    protected Control createPreferenceContent(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);

        if (!isProjectPreferencePage()) {
            dontAskToOverwrite = new Button(root, SWT.CHECK);
            dontAskToOverwrite
                    .setText(Messages.DocumentationGeneralPreferencePage_DocumentationExportDontAskToOverwriteFiles);
            dontAskToOverwrite.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));
        }

        /*
         * Add controls for custom logo selection to be used in exported
         * documentation
         */

        btnUseCustomLogo = new Button(root, SWT.CHECK);
        btnUseCustomLogo
                .setText(Messages.DocumentationGeneralPreferencePage_UseCustomLogo);
        btnUseCustomLogo
                .setData(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE);
        btnUseCustomLogo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnUseCustomLogo.addSelectionListener(this);

        imagePickerContainer = new Composite(root, SWT.NONE);
        imagePickerContainer.setLayout(new GridLayout(3, false));

        lblImageFile = new CLabel(imagePickerContainer, SWT.NONE);
        GridData gd1 = new GridData();
        gd1.horizontalIndent = 0;
        lblImageFile.setLayoutData(gd1);
        lblImageFile
                .setText(Messages.DocumentationGeneralPreferencePage_LogoFileLabel);

        txtImageFileName =
                new Text(imagePickerContainer, SWT.SINGLE | SWT.BORDER);
        txtImageFileName.setText(""); //$NON-NLS-1$
        GridData gd2 = new GridData();
        gd2.widthHint = 250;
        txtImageFileName.setLayoutData(gd2);
        txtImageFileName.setEnabled(false);

        btnBrowse = new Button(imagePickerContainer, SWT.PUSH);
        GridData gd3 = new GridData();
        gd3.widthHint = 100;
        btnBrowse.setLayoutData(gd3);
        btnBrowse
                .setText(Messages.DocumentationGeneralPreferencePage_BrowseButtonText);
        btnBrowse.setEnabled(false);
        btnBrowse.addSelectionListener(this);

        setControlsFromOptions();
        return root;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#getPreferencePageID()
     * 
     * @return
     */
    @Override
    protected String getPreferencePageID() {
        return PREF_ID;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#getPropertyPageID()
     * 
     * @return
     */
    @Override
    protected String getPropertyPageID() {
        return PROP_ID;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean hasProjectSpecificOptions(IProject project) {

        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node =
                        preferences
                                .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                if (node != null) {
                    return node
                            .getBoolean(PreferenceStoreKeys.HAS_PROJECT_SPECIFIC_DOC_SETTINGS,
                                    false);
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(XpdResourcesUIActivator.getDefault()
                .getPreferenceStore());
    }

    private void setControlsFromOptions() {
        if (isProjectPreferencePage()) {

            if (getProject() != null && btnUseCustomLogo != null
                    && txtImageFileName != null) {
                IEclipsePreferences preferences =
                        getProjectPreferences(getProject());

                if (preferences != null) {
                    Preferences node =
                            preferences
                                    .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                    if (node != null) {
                        boolean prefUseCustomLogo =
                                node.getBoolean(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE,
                                        false);
                        btnUseCustomLogo.setSelection(prefUseCustomLogo);
                        String prefLogoFilePath =
                                node.get(PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE,
                                        ""); //$NON-NLS-1$
                        txtImageFileName.setText(prefLogoFilePath);
                        txtImageFileName.setEnabled(btnUseCustomLogo
                                .getSelection());
                        btnBrowse.setEnabled(btnUseCustomLogo.getSelection());
                    }
                }
            }

        } else {
            IPreferenceStore store = getPreferenceStore();
            if (store != null && dontAskToOverwrite != null
                    && btnUseCustomLogo != null && txtImageFileName != null) {
                boolean prefAskOverwrite =
                        store.getBoolean(PreferenceStoreKeys.DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION);
                dontAskToOverwrite.setSelection(prefAskOverwrite);
                boolean useCustomLogo =
                        store.getBoolean(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE);
                btnUseCustomLogo.setSelection(useCustomLogo);
                btnBrowse.setEnabled(useCustomLogo);
                txtImageFileName
                        .setText(store
                                .getString(PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE));
                txtImageFileName.setEnabled(useCustomLogo);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     */
    @Override
    public IAdaptable getElement() {
        return element;
    }

    @Override
    protected void performDefaults() {
        if (dontAskToOverwrite != null) {
            dontAskToOverwrite
                    .setSelection(DEFAULT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION);
        }

        if (btnUseCustomLogo != null && txtImageFileName != null
                && btnBrowse != null) {
            btnUseCustomLogo.setSelection(false);
            txtImageFileName.setText(""); //$NON-NLS-1$
            txtImageFileName.setEnabled(false);
            btnBrowse.setEnabled(false);
        }
    }

    @Override
    public boolean performOk() {
        if (isProjectPreferencePage()) {
            IProject project = getProject();
            if (project != null && btnUseCustomLogo != null
                    && txtImageFileName != null) {

                saveToProjectPreference(project,
                        PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE,
                        String.valueOf(btnUseCustomLogo.getSelection()));

                saveToProjectPreference(project,
                        PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE,
                        txtImageFileName.getText());
            }
        } else {
            IPreferenceStore store = getPreferenceStore();
            if (store != null && dontAskToOverwrite != null) {

                boolean prefAskOverwrite = dontAskToOverwrite.getSelection();
                store.setValue(PreferenceStoreKeys.DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION,
                        prefAskOverwrite);
                store.setValue(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE,
                        String.valueOf(btnUseCustomLogo.getSelection()));
                store.setValue(PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE,
                        txtImageFileName.getText());
            }
        }
        return true;
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     * 
     * @param e
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        if (e.widget != null && e.widget == btnUseCustomLogo
                && btnBrowse != null && txtImageFileName != null) {

            boolean checked = ((Button) e.widget).getSelection();
            btnBrowse.setEnabled(checked);
            txtImageFileName.setEnabled(checked);

        } else if (e.widget != null && e.widget == btnBrowse
                && txtImageFileName != null) {

            ImagePicker picker =
                    new ImagePicker(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell());
            // Only allow PNG file selection
            picker.imageFileExtensions.clear();
            picker.imageFileExtensions.add("png"); //$NON-NLS-1$
            if (isProjectPreferencePage()) {
                picker.setInput(getProject());
            } else {
                picker.setInput(ResourcesPlugin.getWorkspace().getRoot());
            }
            if (picker.open() == picker.OK) {
                Object pick = picker.getResult()[0];
                if (pick instanceof IFile) {
                    txtImageFileName.setText(((IFile) pick).getFullPath()
                            .toString());
                }
            }
        }

    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     * 
     * @param e
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage#enableProjectSpecificSettings(boolean)
     * 
     * @param useProjectSpecificSettings
     */
    @Override
    protected void enableProjectSpecificSettings(
            boolean useProjectSpecificSettings) {
        setHasProjectSpecificProcDocSettings(getProject(),
                useProjectSpecificSettings);
        super.enableProjectSpecificSettings(useProjectSpecificSettings);
    }

    public static void setHasProjectSpecificProcDocSettings(IProject project,
            boolean hasProjectSpecificProcDocSettings) {
        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node =
                        preferences
                                .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                if (node != null) {
                    node.putBoolean(PreferenceStoreKeys.HAS_PROJECT_SPECIFIC_DOC_SETTINGS,
                            hasProjectSpecificProcDocSettings);
                }
            }
        }
    }

    /**
     * Get the project preferences of the given project.
     * 
     * @param project
     * @return
     */
    private static IEclipsePreferences getProjectPreferences(IProject project) {
        ProjectScope scope = new ProjectScope(project);
        IEclipsePreferences preferences =
                scope.getNode(XpdResourcesUIActivator.ID);
        return preferences;
    }

    /**
     * 
     * @param project
     * @param prefName
     * @param prefValue
     */
    private static void saveToProjectPreference(IProject project,
            String prefName, String prefValue) {
        if (project != null && prefName != null && prefValue != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node =
                        preferences
                                .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                if (node != null) {
                    node.put(prefName, prefValue);
                }
                try {
                    preferences.flush();
                } catch (BackingStoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }
    }

}
