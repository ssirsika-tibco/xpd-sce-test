/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard.pages;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export's main wizard page. This page will allow the user to select the
 * objects to export from the workspace and select the export destination. The
 * destination can either be the exports workspace folder in the project, or an
 * external path in the file system.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class AbstractInputOutputSelectionWizardPage
        extends AbstractXpdWizardPage implements SelectionListener {

    private static final String PREVIOUS_SELECTED_PATH = "PreviousSelectedPath"; //$NON-NLS-1$

    public static final Logger LOG =
            XpdResourcesUIActivator.getDefault().getLogger();

    // Tree view control
    protected ExplorerTreeViewer resourceViewer;

    // This will be set to the initial selection and subsequently to the current
    // selection in the tree
    private final IStructuredSelection selection;

    private boolean initialized = false;

    /**
     * Set to <code>true</code> when the external path option should be a file
     * rather than a folder. By default this will be a folder location.
     * 
     * @since 3.5.20
     */
    private boolean isExternalLocationFile = false;

    /**
     * Set whether the external export folder should exist.
     * 
     * @since 3.5.20
     */
    private boolean externalFolderShouldExist = true;

    /**
     * @return the initialized
     */
    public boolean isInitialized() {
        return initialized;
    }

    private Button btnProject;

    private Button btnPath;

    private Text txtPath;

    private Button btnBrowse;

    private Label lblLocation;

    private ExportDestination exportDestination = ExportDestination.PROJECT;

    private final boolean expandAllOnInit;

    private static String EXPORT_DESTINATION_PREFERENCE_CONSTANT =
            ".ExportPage.ExportFolder"; //$NON-NLS-1$

    /**
     * Creates an instance of this wizard page.
     * 
     * @param selection
     *            initial selection to be set in the viewer.
     * @param expandAllOninit
     *            if all content should be initially expanded.
     * @since 3.7.0
     */
    public AbstractInputOutputSelectionWizardPage(
            IStructuredSelection selection, boolean expandAllOninit) {
        super("InputOutputSelectionWizardPage"); //$NON-NLS-1$
        setTitle(Messages.InputOutputSelectionWizardPage_title);
        setDescription(Messages.InputOutputSelectionWizardPage_longdesc);
        this.selection = selection;
        this.expandAllOnInit = expandAllOninit;
    }

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial selection to be made in the tree viewer.
     */
    public AbstractInputOutputSelectionWizardPage(
            IStructuredSelection selection) {
        this(selection, false);
    }

    /**
     * Set the external path location to be a location to a file rather than a
     * folder. This will change the validation and browse button behaviour
     * accordingly.
     * <p>
     * <strong>NOTE:</strong> If <code>isFile</code> is set to <code>true</code>
     * then {@link #getFileDialog(Shell)} needs to be implemented to provide the
     * file selection dialog.
     * </p>
     * 
     * @param isFile
     *            <code>true</code> if the external path should be to a file,
     *            <code>false</code> (default) to a folder.
     * 
     * @since 3.5.20
     */
    protected void setIsExternalLocationToFile(boolean isFile) {
        isExternalLocationFile = isFile;
    }

    /**
     * If the external path option is set to be a File then this method needs to
     * be implemented to provide the appropriate file dialog. (Default
     * implementation just returns <code>null</code>.)
     * 
     * @param shell
     * @return File dialog to show to the user.
     * @since 3.5.20
     */
    protected FileDialog getFileDialog(Shell shell) {
        return null;
    }

    /**
     * Set whether the external (export) folder location should already exist.
     * This changes the validation behaviour. By default this is set to
     * <code>true</code>.
     * 
     * @param shouldExist
     *            <code>true</code> if the external folder should exist,
     *            <code>false</code> if the any external folders will be created
     *            automatically by the wizard.
     * @since 3.5.20
     */
    protected void setShouldExternalFolderExist(boolean shouldExist) {
        externalFolderShouldExist = shouldExist;
    }

    @Override
    public void dispose() {
        if (resourceViewer != null) {
            resourceViewer.dispose();
            resourceViewer = null;
        }
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
     * widgets .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);

        GridLayout gridLayout = new GridLayout();
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Create the package viewer control
        createTreeControl(container);

        // Create the destination selector output controls
        createDestinationControl(container);

        setControl(container);

        // Update page completion
        updatePageCompletion();
        initialized = true;
    }

    /**
     * Check page completion
     */
    protected abstract void validatePageCompletion();

    protected void updatePageCompletion() {

        setErrorMessage(null);
        setPageComplete(true);
        validatePageCompletion();

        exportDestination = btnProject != null && btnProject.getSelection()
                ? ExportDestination.PROJECT
                : ExportDestination.PATH;

        if (getWizard() != null && getWizard().getClass() != null) {
            if (exportDestination == ExportDestination.PATH) {
                setPreference(
                        getWizard().getClass().getName()
                                + EXPORT_DESTINATION_PREFERENCE_CONSTANT,
                        true);
            } else if (exportDestination == ExportDestination.PROJECT) {
                setPreference(
                        getWizard().getClass().getName()
                                + EXPORT_DESTINATION_PREFERENCE_CONSTANT,
                        false);
            }
        }

        // Validate system path.
        if (getLocationType() == DestinationLocationType.SYSTEM_FILE) {
            String locationPath = getLocationPath();
            if (locationPath.trim().length() == 0) {
                // Empty path
                if (initialized) {
                    setErrorMessage(String.format(
                            Messages.InputOutputSelectionWizardPage_DestPathEmptyError_message));
                }
                txtPath.setFocus();
                setPageComplete(false);
                return;
            }

            File dest = new File(locationPath);

            if (isExternalLocationFile) {
                // External path should be to a file - so get the parent as that
                // will be the folder
                dest = dest.getParentFile();
            }

            if (externalFolderShouldExist
                    && (!dest.exists() || !dest.isDirectory())) {
                if (initialized) {
                    setErrorMessage(String.format(
                            Messages.InputOutputSelectionWizardPage_InvalidPathError_message));
                }
                txtPath.setFocus();
                setPageComplete(false);
                return;
            }

        }
    }

    public List<Object> getSelectedObjects() {
        Object[] checkedItems = resourceViewer.getCheckedElements();
        return Arrays.asList(checkedItems);
    }

    /**
     * Update the check state of a given list of items. Items not in the list
     * are unaffected.
     * 
     * @param items
     *            The list of items to update.
     * @param checked
     *            true to set as checked, false to set as unchecked.
     */
    protected void updateSelectedObjects(Collection<? extends Object> items,
            boolean checked) {
        for (Object item : items) {
            resourceViewer.setChecked(item, checked);
        }
    }

    /**
     * Returns the destination selection.
     * <p>
     * If destination is <code>{@link ExportDestination#PATH}</code> then use
     * <code>getDestinationPath()</code> to get the path
     * </p>
     * 
     * @return <code>{@link ExportDestination#PROJECT}</code> if export to local
     *         project or <code>{@link ExportDestination#PATH}</code> if
     *         exporting to a given path.
     * 
     */
    public final ExportDestination getDestinationSelection() {
        return exportDestination;
    }

    /**
     * Create the file viewer control
     * 
     * @param container
     */
    private void createTreeControl(Composite container) {
        GridData gridData;

        // Expand/Collapse all toolbar
        final ToolBar tBar = new ToolBar(container, SWT.HORIZONTAL | SWT.FLAT);
        tBar.setLayout(new RowLayout(SWT.HORIZONTAL));
        gridData = new GridData(SWT.END, 0, false, false);
        tBar.setLayoutData(gridData);

        final ToolItem tExpandAll = new ToolItem(tBar, SWT.PUSH);
        tExpandAll.setImage(
                XpdResourcesUIActivator.getDefault().getImageRegistry()
                        .get(XpdResourcesUIConstants.EXPORT_EXPAND_ALL));
        tExpandAll.setToolTipText(
                Messages.InputOutputSelectionWizardPage_ExpandAll_tooltip);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(
                XpdResourcesUIActivator.getDefault().getImageRegistry()
                        .get(XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll.setToolTipText(
                Messages.InputOutputSelectionWizardPage_CollapseAll_tooltip);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.collapseAll();
            }
        });

        Object input = ResourcesPlugin.getWorkspace().getRoot();
        ViewerSorter viewerSorter = new ViewerSorter();
        ViewerFilter[] viewerFilters = createViewerFilters();

        // Package tree viewer
        resourceViewer = new ExplorerTreeViewer(container, input, viewerSorter,
                viewerFilters);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.widthHint = 100;
        gridData.heightHint = 150;
        resourceViewer.getControl().setLayoutData(gridData);

        resourceViewer.setInitialSelection(selection);
        resourceViewer.addPostSelectionChangedListener(
                new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        // Update page completion
                        updatePageCompletion();
                    }
                });

        //
        // Do not expand all (not expanding will just cause the parent nodes
        // of the selected elements to be expanded - which is what we want
        // really anyway).
        // It is a bad idea to expand all because this will always cause a get
        // children on things (like xpdl files etc) and that will always cause
        // the file to be loaded into memory. So expandAll causes every files
        // that matches the filter to be loaded into memory.
        //
        // fileViewer.expandAll();
        //

        /*
         * The expandAll can be done now for some of the inheriting classes by
         * using overloaded constructor. Please use it carefully as explained it
         * the above comment.
         */
        if (expandAllOnInit) {
            resourceViewer.expandAll();
        }

        // Container for the Select / Deselect all buttons
        final Composite cmpTreeSelect = new Composite(container, SWT.NULL);
        RowLayout selectLayout = new RowLayout();
        selectLayout.type = SWT.HORIZONTAL;
        cmpTreeSelect.setLayout(selectLayout);

        // Select All button
        final Button btnSelectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnSelectAll.setText(
                Messages.InputOutputSelectionWizardPage_SelectAll_button);
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Deselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll.setText(
                Messages.InputOutputSelectionWizardPage_DeselectAll_button);
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.setAllChecked(false);
                updatePageCompletion();
            }
        });

        createExtraTreeControlButtons(cmpTreeSelect);
    }

    /**
     * Override to add your own buttons to the button row.
     * 
     * @param cmpTreeSelect
     *            The composite to add tree buttons to.
     */
    protected void createExtraTreeControlButtons(Composite cmpTreeSelect) {
        // Do nothing by default
    }

    protected ViewerFilter[] createViewerFilters() {
        return new ViewerFilter[] {
                // XPD projects content only.
                new XpdNatureProjectsOnly(),

                // Only projects.
                new ViewerFilter() {
                    @Override
                    public boolean select(Viewer viewer, Object parentElement,
                            Object element) {
                        if (element instanceof IWorkspaceRoot
                                || element instanceof IProject) {
                            return true;
                        }
                        return false;
                    }
                } };
    }

    protected abstract String getWorkspaceExportFolder();

    /**
     * Create the destination selector control
     * 
     * @param container
     */
    protected void createDestinationControl(Composite container) {
        /*
         * Get the preference value for export destination
         */
        boolean preferenceValue = false;
        if (getWizard() != null && getWizard().getClass() != null) {
            preferenceValue = getPreference(getWizard().getClass().getName()
                    + EXPORT_DESTINATION_PREFERENCE_CONSTANT);
        }
        GridData gridData;
        // Get the project export folder path
        String projectDestFolder = getWorkspaceExportFolder();

        // TODO JA Use wizard preferences to store/retrieve values.

        // Get the last used destination path
        // String destPath =
        // ((AbstractExportWizard) getWizard())
        // .getPreference(BROWSEDESTFOLDERKEY);
        IDialogSettings dialogSettings =
                XpdResourcesUIActivator.getDefault().getDialogSettings();
        String destPath = ""; //$NON-NLS-1$
        if (!isExternalLocationFile
                && null != dialogSettings.get(PREVIOUS_SELECTED_PATH)) {
            destPath = dialogSettings.get(PREVIOUS_SELECTED_PATH);
        }

        // Destination group
        final Group grpDestination = new Group(container, SWT.NULL);
        grpDestination.setText(
                Messages.InputOutputSelectionWizardPage_DestinationGroup_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        grpDestination.setLayoutData(gridData);
        grpDestination.setLayout(new GridLayout(3, false));

        // Destination - Project radio button
        btnProject = new Button(grpDestination, SWT.RADIO);
        btnProject
                .setText(Messages.InputOutputSelectionWizardPage_Project_label);
        /*
         * XPD-5008: Saket- Set the button state to what user selected
         * previously. If there was no previous selection made, then by default
         * enable btnProject.
         */
        if (getWizard() != null && getWizard().getClass() != null) {
            btnProject.setSelection(!preferenceValue);
        } else {
            btnProject.setSelection(true);
        }
        btnProject.addSelectionListener(this);
        lblLocation = new Label(grpDestination, SWT.NULL);
        lblLocation.setText(String.format(
                Messages.InputOutputSelectionWizardPage_Location_lablel,
                projectDestFolder));
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        lblLocation.setLayoutData(gridData);

        // Destination - Path radio button
        btnPath = new Button(grpDestination, SWT.RADIO);
        btnPath.setText(Messages.InputOutputSelectionWizardPage_Path_label);
        /*
         * XPD-5008: Saket- Set the button state to what user selected
         * previously. If there was no previous selection made, then by default
         * disable btnPath.
         */
        if (getWizard() != null && getWizard().getClass() != null) {
            btnPath.setSelection(preferenceValue);
        } else {
            btnPath.setSelection(false);
        }
        btnPath.addSelectionListener(this);

        // Path input text control
        txtPath = new Text(grpDestination, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        txtPath.setLayoutData(gridData);

        // If we have previously selected path then set that
        txtPath.setText(destPath);
        /*
         * txtPath should be enabled depending upon the state of btnPath
         */
        txtPath.setEnabled(preferenceValue);
        txtPath.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                // Update page completion
                updatePageCompletion();
            }
        });

        txtPath.addFocusListener(new FocusAdapter() {
            /**
             * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
             * 
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                txtPath.setText(updateTextPath(txtPath.getText()));

                // Only persist last folder location
                if (!isExternalLocationFile) {
                    String path = txtPath.getText();

                    if (!path.isEmpty()) {
                        File file = new File(path);
                        if (file.isDirectory()) {
                            // set the selected path
                            IDialogSettings dialogSettings =
                                    XpdResourcesUIActivator.getDefault()
                                            .getDialogSettings();
                            dialogSettings.put(PREVIOUS_SELECTED_PATH,
                                    txtPath.getText());
                        }
                    }
                }
            }
        });

        // Path browse button
        btnBrowse = new Button(grpDestination, SWT.NONE);
        btnBrowse
                .setText(Messages.InputOutputSelectionWizardPage_Browse_button);
        /*
         * btnBrowse should be enabled depending upon the state of btnPath
         */
        btnBrowse.setEnabled(preferenceValue);
        btnBrowse.addSelectionListener(this);
    }

    /**
     * Returns the preference value for the specific 'key'.
     * 
     * @param string
     * @return
     */
    private boolean getPreference(String key) {
        IPreferenceStore pStore =
                XpdResourcesPlugin.getDefault().getPreferenceStore();
        if (pStore.contains(key)) {
            return (pStore.getBoolean(key));
        }
        return false;
    }

    /**
     * Assigns the value 'value' for the preference recognised by the specified
     * 'key'.
     * 
     * @param key
     * @param value
     */
    private void setPreference(String key, boolean value) {
        if (key != null && key != "") { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesPlugin.getDefault().getPreferenceStore();
            pStore.setValue(key, value);
        }
    }

    /**
     * Update the external location path on the text control de-activation (or
     * browse dialog selection). Subclasses may override to change the path.
     * This implementation does just returns the path unchanged.
     * <p>
     * This method could be used to add a file extension, for example, if
     * external location is a file.
     * </p>
     * 
     * @param path
     * @return
     * @since 3.5.20
     */
    protected String updateTextPath(String path) {
        return path;
    }

    /**
     * Enable/Disable the local path option (export to project location).
     * 
     * @param enabled
     * @since 3.5.20
     */
    protected final void enableLocalPath(boolean enabled) {
        if (btnProject != null && !btnProject.isDisposed()) {
            btnProject.setEnabled(enabled);
            lblLocation.setEnabled(enabled);
            if (!enabled && btnProject.getSelection()) {
                btnProject.setSelection(false);
                btnPath.setSelection(true);
                enableDestinationPathControls(true);
            }
        }
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();

        if (source instanceof Button) {
            Button btn = (Button) source;

            // Only execute if the radio button is selected or a button is
            // pushed
            boolean execute = true;

            if ((btn.getStyle() & SWT.RADIO) == SWT.RADIO) {
                execute = btn.getSelection();
            }

            if (execute) {
                // If browse button clicked...
                if (source == btnBrowse) {
                    // Browse button selected so display the file selection
                    // dialog
                    String selection;

                    if (isExternalLocationFile) {
                        // Show file dialog
                        FileDialog dialog = getFileDialog(getShell());

                        Assert.isNotNull(dialog,
                                "getFileDialog(shell) has not been implemented"); //$NON-NLS-1$

                        selection = dialog.open();
                    } else {
                        // Show Directory Dialog
                        DirectoryDialog dDialog =
                                new DirectoryDialog(getShell());
                        dDialog.setText(
                                Messages.InputOutputSelectionWizardPage_SelectFolderDialog_message);
                        dDialog.setMessage(
                                Messages.InputOutputSelectionWizardPage_SelectFolderDialog_longdesc);

                        // If we have path already selected then set the dialog
                        // filter
                        if (txtPath.getText().length() > 0) {
                            dDialog.setFilterPath(txtPath.getText());
                        }

                        // Open dialog
                        selection = dDialog.open();
                    }

                    if (selection != null) {
                        selection = updateTextPath(selection);

                        // Set the selected path in the text control
                        txtPath.setText(selection);

                        // Only persist last folder location
                        if (!isExternalLocationFile) {
                            // set the selected path
                            IDialogSettings dialogSettings =
                                    XpdResourcesUIActivator.getDefault()
                                            .getDialogSettings();
                            dialogSettings.put(PREVIOUS_SELECTED_PATH,
                                    txtPath.getText());
                        }
                    }

                } else if (source == btnPath || source == btnProject) {
                    // Enable the path text control and browse button if the
                    // path radio button is selected
                    enableDestinationPathControls(btnPath.getSelection());
                }

                // Update page completion
                updatePageCompletion();
            }
        }
    }

    /**
     * Set the enable-ment of the external path destination controls.
     * 
     * @param enabled
     */
    private void enableDestinationPathControls(boolean enabled) {
        txtPath.setEnabled(enabled);
        btnBrowse.setEnabled(enabled);
    }

    public DestinationLocationType getLocationType() {
        if (btnProject.getSelection()) {
            return DestinationLocationType.PROJECT;
        } else {
            return DestinationLocationType.SYSTEM_FILE;
        }
    }

    public String getLocationPath() {
        switch (getLocationType()) {
        case PROJECT:
            return getWorkspaceExportFolder();
        case SYSTEM_FILE:
            return txtPath.getText().toString();
        default:
            throw new AssertionError("Unknown locationType"); //$NON-NLS-1$
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
    }

    // TODO JA: No workspace cleanup should be needed. Need some more testing.
    // If true remove, else uncomment.

    // private boolean workspaceCleanupDone = false;
    //
    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // public void setVisible(boolean visible) {
    // if (visible && !workspaceCleanupDone) {
    // try {
    // getContainer().run(true, true, new IRunnableWithProgress() {
    // public void run(IProgressMonitor monitor)
    // throws InvocationTargetException,
    // InterruptedException {
    // try {
    // ProjectDAAGenerator.getInstance()
    // .cleanAllProjects(monitor);
    // } catch (CoreException e) {
    // new InvocationTargetException(e);
    // }
    // }
    // });
    // } catch (InvocationTargetException e) {
    // LOG.error(e.getCause());
    // } catch (InterruptedException e) {
    // // do nothing
    // }
    // workspaceCleanupDone = true;
    // }
    // super.setVisible(visible);
    // }
}
