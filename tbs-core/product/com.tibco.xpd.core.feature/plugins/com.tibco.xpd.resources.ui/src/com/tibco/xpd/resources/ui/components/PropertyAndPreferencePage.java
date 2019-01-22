package com.tibco.xpd.resources.ui.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * Base class for a project property and preference pages. The subclass of this
 * class should be referenced from both
 * <code>org.eclipse.ui.preferencePages</code> and
 * <code>org.eclipse.ui.propertyPages</code> extensions, and specified ids in
 * the extensions should match these returned by {@link #getPreferencePageID()}
 * and {@link #getPropertyPageID()} respectively.
 * <p/>
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public abstract class PropertyAndPreferencePage extends PreferencePage
        implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

    private Control fConfigurationBlockControl;

    private ControlEnableState contentEnableState;

    private Link changeWorkspaceSettings;

    private Button useProjectSettingButton;

    private IStatus contentStatus;

    private Composite parentComposite;

    private IProject project; // project or null

    private Map<String, Object> pageData;

    public static final String DATA_NO_LINK =
            "PropertyAndPreferencePage.nolink"; //$NON-NLS-1$

    public PropertyAndPreferencePage() {
        contentStatus = new StatusInfo();
        contentEnableState = null;
        project = null;
        pageData = null;
    }

    /**
     * The method to create content of a property/preference page.
     * 
     * @param composite
     *            the parent composite.
     * @return the parent control for a page.
     */
    protected abstract Control createPreferenceContent(Composite composite);

    /**
     * Checks if there are project specific options for the page.
     * 
     * @param project
     *            the context project.
     * @return <code>true</code> if there are project specific options for the
     *         provided project, <code>false</code> otherwise.
     */
    protected abstract boolean hasProjectSpecificOptions(IProject project);

    /**
     * Returns id of a preference page as defined in
     * 'org.eclipse.ui.preferencePages' extension for the associated preference
     * page.
     * 
     * @return id of a preference page as defined in
     *         'org.eclipse.ui.preferencePages' extension for the associated
     *         preference page.
     */
    protected abstract String getPreferencePageID();

    /**
     * Returns id of a property page as defined in
     * 'org.eclipse.ui.propertyPages' extension for the associated property
     * page.
     * 
     * @return id of a preference page as defined in
     *         'org.eclipse.ui.propertyPages' extension for the associated
     *         property page.
     */
    protected abstract String getPropertyPageID();

    protected boolean supportsProjectSpecificOptions() {
        return getPropertyPageID() != null;
    }

    protected boolean offerLink() {
        // Don't show link in RCP application
        if (XpdResourcesPlugin.isRCP()) {
            return false;
        }
        return pageData == null
                || !Boolean.TRUE.equals(pageData.get(DATA_NO_LINK));
    }

    @Override
    protected Label createDescriptionLabel(Composite parent) {
        parentComposite = parent;
        if (isProjectPreferencePage()) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setFont(parent.getFont());
            GridLayout layout = new GridLayout();
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.numColumns = 2;
            composite.setLayout(layout);
            composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));

            SelectionAdapter listener = new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    boolean enabled = useProjectSettingButton.getSelection();
                    enableProjectSpecificSettings(enabled);

                    if (enabled && getData() != null) {
                        applyData(getData());
                    }

                }
            };

            useProjectSettingButton = new Button(composite, SWT.CHECK);
            useProjectSettingButton.addSelectionListener(listener);
            useProjectSettingButton
                    .setText(Messages.PropertyAndPreferencePage_enableProjectSpecificSettings_button);

            if (offerLink()) {
                GridDataFactory.swtDefaults().applyTo(useProjectSettingButton);
                changeWorkspaceSettings =
                        createLink(composite,
                                Messages.PropertyAndPreferencePage_configureWorkspaceSettings_link);
                changeWorkspaceSettings.setLayoutData(new GridData(SWT.END,
                        SWT.CENTER, false, false));
            } else {
                GridDataFactory.swtDefaults().span(2, 1)
                        .applyTo(useProjectSettingButton);
            }

            Label horizontalLine =
                    new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
            horizontalLine.setLayoutData(new GridData(GridData.FILL,
                    GridData.FILL, true, false, 2, 1));
            horizontalLine.setFont(composite.getFont());
        } else if (supportsProjectSpecificOptions() && offerLink()) {
            changeWorkspaceSettings =
                    createLink(parent,
                            Messages.PropertyAndPreferencePage_configureProjectSpecificSettings_link);
            changeWorkspaceSettings.setLayoutData(new GridData(SWT.END,
                    SWT.CENTER, true, false));
        }

        return super.createDescriptionLabel(parent);
    }

    /*
     * @see
     * org.eclipse.jface.preference.IPreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setFont(parent.getFont());

        GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);

        fConfigurationBlockControl = createPreferenceContent(composite);
        fConfigurationBlockControl.setLayoutData(data);

        if (isProjectPreferencePage()) {
            boolean useProjectSettings =
                    hasProjectSpecificOptions(getProject());
            enableProjectSpecificSettings(useProjectSettings);
        }

        Dialog.applyDialogFont(composite);
        return composite;
    }

    protected Link createLink(Composite composite, String text) {
        Link link = new Link(composite, SWT.NONE);
        link.setFont(composite.getFont());
        link.setText("<A>" + text + "</A>"); //$NON-NLS-1$//$NON-NLS-2$
        link.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                doLinkActivated((Link) e.widget);
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                doLinkActivated((Link) e.widget);
            }
        });
        return link;
    }

    protected boolean useProjectSettings() {
        return isProjectPreferencePage() && useProjectSettingButton != null
                && useProjectSettingButton.getSelection();
    }

    protected boolean isProjectPreferencePage() {
        return project != null;
    }

    protected IProject getProject() {
        return project;
    }

    /**
     * Handle link activation.
     * 
     * @param link
     *            the link
     */
    protected final void doLinkActivated(Link link) {
        Map<String, Object> data = getData();
        if (data == null)
            data = new HashMap<String, Object>();
        data.put(DATA_NO_LINK, Boolean.TRUE);

        if (isProjectPreferencePage()) {
            openWorkspacePreferences(data);
        } else {
            HashSet<IProject> projectsWithSpecifics = new HashSet<IProject>();
            IProject[] projects =
                    ResourcesPlugin.getWorkspace().getRoot().getProjects();
            try {
                for (int i = 0; i < projects.length; i++) {
                    if (hasProjectSpecificOptions(projects[i])) {
                        projectsWithSpecifics.add(projects[i]);
                    }
                }
            } catch (Exception e) {
                // ignore
            }
            ProjectSelectionPicker dialog =
                    new ProjectSelectionPicker(getShell(),
                            projectsWithSpecifics,
                            getProjectSelectionDialogFilters());
            if (dialog.open() == Window.OK) {
                Object[] res = dialog.getResult();
                if (res != null && res.length > 0) {
                    openProjectProperties((IProject) res[0], data);
                }
            }
        }
    }

    protected ViewerFilter[] getProjectSelectionDialogFilters() {
        return new ViewerFilter[] { new XpdNatureProjectsOnly(false) };
    }

    protected final void openWorkspacePreferences(Object data) {
        String id = getPreferencePageID();
        PreferencesUtil.createPreferenceDialogOn(getShell(),
                id,
                new String[] { id },
                data).open();
    }

    protected final void openProjectProperties(IProject project, Object data) {
        String id = getPropertyPageID();
        if (id != null) {
            PreferencesUtil.createPropertyDialogOn(getShell(),
                    project,
                    id,
                    new String[] { id },
                    data).open();
        }
    }

    protected void enableProjectSpecificSettings(
            boolean useProjectSpecificSettings) {
        useProjectSettingButton.setSelection(useProjectSpecificSettings);
        enablePreferenceContent(useProjectSpecificSettings);
        updateLinkVisibility();
        doStatusChanged();
    }

    private void updateLinkVisibility() {
        if (changeWorkspaceSettings == null
                || changeWorkspaceSettings.isDisposed()) {
            return;
        }

        if (isProjectPreferencePage()) {
            changeWorkspaceSettings.setEnabled(!useProjectSettings());
        }
    }

    protected void setPreferenceContentStatus(IStatus status) {
        contentStatus = status;
        doStatusChanged();
    }

    protected IStatus getPreferenceContentStatus() {
        return contentStatus;
    }

    protected void doStatusChanged() {
        if (!isProjectPreferencePage() || useProjectSettings()) {
            updateStatus(contentStatus);
        } else {
            updateStatus(new StatusInfo());
        }
    }

    protected void enablePreferenceContent(boolean enable) {
        if (enable) {
            if (contentEnableState != null) {
                contentEnableState.restore();
                contentEnableState = null;
            }
        } else {
            if (contentEnableState == null) {
                contentEnableState =
                        ControlEnableState.disable(fConfigurationBlockControl);
            }
        }
    }

    /*
     * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        if (useProjectSettings()) {
            enableProjectSpecificSettings(false);
        }
        super.performDefaults();
    }

    private void updateStatus(IStatus status) {
        setValid(!status.matches(IStatus.ERROR));
        String message = status.getMessage();
        if (message != null && message.length() == 0) {
            message = null;
        }
        switch (status.getSeverity()) {
        case IStatus.OK:
            this.setMessage(message, IMessageProvider.NONE);
            this.setErrorMessage(null);
            break;
        case IStatus.WARNING:
            this.setMessage(message, IMessageProvider.WARNING);
            this.setErrorMessage(null);
            break;
        case IStatus.INFO:
            this.setMessage(message, IMessageProvider.INFORMATION);
            this.setErrorMessage(null);
            break;
        default:
            this.setMessage(null);
            this.setErrorMessage(message);
            break;
        }
    }

    public void init(IWorkbench workbench) {
    }

    public IAdaptable getElement() {
        return project;
    }

    public void setElement(IAdaptable element) {
        project = (IProject) element.getAdapter(IResource.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyData(Object data) {
        if (data instanceof Map) {
            this.pageData = (Map<String, Object>) data;
        }
        if (changeWorkspaceSettings != null) {
            if (!offerLink()) {
                changeWorkspaceSettings.dispose();
                parentComposite.layout(true, true);
            }
        }
    }

    protected Map<String, Object> getData() {
        return pageData;
    }

    /**
     * Project selection picker.
     * 
     * @author Jan Arciuchiewicz, njpatel
     * 
     */
    protected static class ProjectSelectionPicker extends SelectionDialog {

        // sizing constants
        private final static int HEIGHT = 250;

        private final static int WIDTH = 300;

        private static final String DIALOG_SETTING_ID =
                "propertyAndPreferencePage.projectSelection"; //$NON-NLS-1$

        private boolean showProjectsWithSettingsOnly;

        private TableViewer viewer;

        private final Collection<IProject> projectsWithSettings;

        private final ViewerFilter[] projectFilters;

        public ProjectSelectionPicker(Shell parent,
                Collection<IProject> projectsWithSettings,
                ViewerFilter[] projectFilters) {
            super(parent);
            this.projectFilters = projectFilters;
            setTitle(Messages.PropertyAndPreferencePage_projectSpecificConfig_title);
            this.projectsWithSettings = projectsWithSettings;
            showProjectsWithSettingsOnly = getDialogSetting(false);
            setHelpAvailable(false);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = (Composite) super.createDialogArea(parent);

            Label lbl = new Label(root, SWT.NONE);
            lbl
                    .setText(Messages.PropertyAndPreferencePage_selectProjectToConfigure_label);
            viewer = new TableViewer(root);
            configure(viewer);
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.heightHint = HEIGHT;
            data.widthHint = WIDTH;
            viewer.getControl().setLayoutData(data);

            final Button opt = new Button(root, SWT.CHECK);
            opt
                    .setText(Messages.PropertyAndPreferencePage_onlyProjectWithSettings_button);
            opt.setSelection(showProjectsWithSettingsOnly);
            opt.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    showProjectsWithSettingsOnly = opt.getSelection();
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

                public Object[] getElements(Object inputElement) {
                    Object[] objs = null;
                    if (inputElement instanceof IWorkspaceRoot) {
                        objs = ((IWorkspaceRoot) inputElement).getProjects();
                    }
                    return objs != null ? objs : new Object[0];
                }

                public void dispose() {
                }

                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }
            });
            if (projectFilters != null && projectFilters.length > 0) {
                viewer.setFilters(projectFilters);
            }
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

                public void selectionChanged(SelectionChangedEvent event) {
                    doSelectionChange(((IStructuredSelection) event
                            .getSelection()).toArray());
                }
            });

            viewer.addDoubleClickListener(new IDoubleClickListener() {
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
         * {@inheritDoc}
         */
        @Override
        protected void okPressed() {
            setDialogSetting(showProjectsWithSettingsOnly);
            super.okPressed();
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
                    XpdResourcesUIActivator.getDefault().getDialogSettings()
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
            XpdResourcesUIActivator.getDefault().getDialogSettings()
                    .put(DIALOG_SETTING_ID, value);
        }
    }
}
