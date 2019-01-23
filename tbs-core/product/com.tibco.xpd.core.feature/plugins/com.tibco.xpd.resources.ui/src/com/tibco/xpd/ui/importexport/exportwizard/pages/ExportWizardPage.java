/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard.pages;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.ISelectionValidator;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;

/**
 * Export's main wizard page. This page will allow the user to select the files
 * to export from the projects and select the export destination. The
 * destination can either be the exports workspace folder in the project, or an
 * external path in the file system.
 * <p>
 * You can use {@link #createUserControl(Composite) createUserControl} to add
 * additional controls to this page. This will appear after the destination
 * section.
 * </p>
 * <p>
 * {@link #setSelectionValidator(ISelectionValidator) setSelectionValidator} can
 * be called to register a validator that will be given a chance to validate the
 * selection in the tree view.
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.3
 * 
 */
public class ExportWizardPage extends AbstractXpdWizardPage implements
        SelectionListener {

    /**
     * Option to allow the export to a project.
     */
    public static final int PROJECT_DESTINATION_OPTION = 0x1;

    /**
     * Option to allow the export to the file system.
     */
    public static final int FILESYSTEM_DESTINATION_OPTION = 0x2;

    // Tree view control
    private ExplorerTreeViewer fileViewer;

    // This will be set to the initial selection and subsequently to the current
    // selection in the tree
    private IStructuredSelection selection;

    private ExportDestination exportDestination = ExportDestination.PROJECT;

    private String destinationPath;

    // Path selection controls
    private Button btnProject;

    private Button btnPath;

    private Text txtPath;

    private Button btnBrowse;

    // The destination folder browser preference key
    private static final String BROWSEDESTFOLDERKEY = "DESTBROWSEFOLDER"; //$NON-NLS-1$

    private final Object input;

    private final ViewerSorter sorter;

    private final ViewerFilter[] filters;

    private final IExportDataProvider provider;

    private ISelectionValidator validator;

    private final int destinationOptions;

    private boolean isEmptySelectionAnError = false;

    private static String EXPORT_DESTINATION_PREFERENCE_CONSTANT =
            ".ExportPage.ExportFolder";

    /**
     * This interface should be implemented by a wizard that will use this page.
     * 
     * @author njpatel
     */
    public interface IExportDataProvider {
        public String getWorkspaceExportFolder();

        public String getPreference(String key);

        public void setPreference(String key, String value);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updatePageCompletion();
    }

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial selection to be made in the tree viewer.
     * @param input
     *            The input of the tree viewer.
     * @param sorter
     *            The <code>ViewerSorter</code> to apply to the tree viewer.
     * 
     * @param filters
     *            An array of <code>ViewerFilter</code>s to apply to filter the
     *            content of the tree viewer.
     */
    public ExportWizardPage(IStructuredSelection selection, Object input,
            ViewerSorter sorter, ViewerFilter[] filters,
            IExportDataProvider provider) {
        this(selection, input, sorter, filters, provider,
                PROJECT_DESTINATION_OPTION | FILESYSTEM_DESTINATION_OPTION);
    }

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial selection to be made in the tree viewer.
     * @param input
     *            The input of the tree viewer.
     * @param sorter
     *            The <code>ViewerSorter</code> to apply to the tree viewer.
     * 
     * @param filters
     *            An array of <code>ViewerFilter</code>s to apply to filter the
     *            content of the tree viewer.
     * @param destinationOptions
     *            flag to indicate what destination options to provide the user.
     *            The values available are {@link #PROJECT_DESTINATION_OPTION}
     *            and {@link #FILESYSTEM_DESTINATION_OPTION}. These values can
     *            also be combined.
     */
    public ExportWizardPage(IStructuredSelection selection, Object input,
            ViewerSorter sorter, ViewerFilter[] filters,
            IExportDataProvider provider, int destinationOptions) {
        super("Export"); //$NON-NLS-1$
        this.selection = selection;
        this.input = input;
        this.sorter = sorter;
        this.filters = filters;
        this.provider = provider;
        this.destinationOptions = destinationOptions;
    }

    /**
     * Set the selection validator. This validator will be asked to validate the
     * current selection in this page.
     * <p>
     * NOTE: The validator will be passed the {@link IStructuredSelection} of
     * files selected in this page for export.
     * </p>
     * 
     * @param validator
     */
    public void setSelectionValidator(ISelectionValidator validator) {
        this.validator = validator;
    }

    @Override
    public void dispose() {

        fileViewer.dispose();
        fileViewer = null;

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
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
        // Create custom area
        Control userCtrl = createUserControl(container);
        if (userCtrl != null) {
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            userCtrl.setLayoutData(data);
        }

        setControl(container);

        // Update page completion
        updatePageCompletion();
    }

    /**
     * Create a user defined control that will appear after the destination
     * selection. Default implementation returns <code>null</code>, subclasses
     * may extend to add user content.
     * 
     * @param container
     * @return
     */
    protected Control createUserControl(Composite container) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
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
                    String szSelection;
                    // Show Directory Dialog
                    DirectoryDialog dDialog = new DirectoryDialog(getShell());
                    dDialog.setText(Messages.ExportWizardPageIO_selectFolder_title);
                    dDialog.setMessage(Messages.ExportWizardPageIO_selectFolder_message);

                    // If we have path already selected then set the dialog
                    // filter
                    if (txtPath.getText().length() > 0)
                        dDialog.setFilterPath(txtPath.getText());

                    // Open dialog
                    szSelection = dDialog.open();

                    if (szSelection != null) {
                        // Set the selected path in the text control
                        txtPath.setText(szSelection);
                    }

                } else if (source == btnPath || source == btnProject) {
                    // Enable the path text control and browse button if the
                    // path radio button is selected
                    if (txtPath != null && !txtPath.isDisposed()) {
                        txtPath.setEnabled(btnPath.getSelection());
                        btnBrowse.setEnabled(btnPath.getSelection());
                    }
                }

                // Update page completion
                updatePageCompletion();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Nothing to do
    }

    /**
     * Returns a list of files selected in the viewer. If this page is
     * configured to display contents of the files then you can use
     * {@link #getSelectedElements()} to get the selection of the actual
     * elements selected in the viewer.
     * 
     * @see #getSelectedElements()
     * 
     * @return IStructuredSelection List of files selected, empty list if no
     *         files selected
     */
    public IStructuredSelection getSelectedFiles() {
        return selection;
    }

    /**
     * Get the items selected in the selection viewer. If this page is
     * configured to show contents of the file then this will return the actual
     * selection of the elements (as opposed to selected files returned by
     * {@link #getSelectedFiles()}.
     * 
     * @see #getSelectedFiles()
     * 
     * @return
     */
    public IStructuredSelection getSelectedElements() {
        if (fileViewer != null && !fileViewer.getControl().isDisposed()) {
            return fileViewer.getSelectedElements();
        }
        return StructuredSelection.EMPTY;
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
     * Set the initial destination file system path.
     * 
     * @param path
     */
    public final void setDestinationPath(IPath path) {
        destinationPath = path.toOSString();
    }

    /**
     * Gets the export destination path
     * 
     * @return Returns IPath to destination choice was
     *         <code>{@link ExportDestination#PATH}</code>, otherwise returns
     *         <code>null</code>.
     */
    public final IPath getDestinationPath() {
        return (destinationPath != null ? new Path(destinationPath) : null);
    }

    /**
     * Update the page. This will re-validate the page.
     */
    public void update() {
        getShell().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                updatePageCompletion();
            }
        });
    }

    /**
     * Validate the page.
     */
    protected void updatePageCompletion() {
        boolean complete = true;

        setPageComplete(false);

        // Check that we have selection in the tree
        selection = fileViewer.getSelectedFiles();

        if (selection == null || selection.isEmpty()) {
            // No selection
            if (isEmptySelectionAnError) {
                setMessage(Messages.ExportWizardPageIO_selectElementsToExport_shortdesc,
                        IMessageProvider.ERROR);
            } else {
                setMessage(Messages.ExportWizardPageIO_selectElementsToExport_shortdesc);
            }
            setErrorMessage(null);
            complete = false;
        }

        // If files are selected then validate the destination
        if (complete) {
            // If the destination type is set to path then verify path
            exportDestination =
                    btnProject != null && btnProject.getSelection() ? ExportDestination.PROJECT
                            : ExportDestination.PATH;
            if (exportDestination == ExportDestination.PATH) {
                // Check that the folder exists
                destinationPath = txtPath.getText();

                provider.setPreference(EXPORT_DESTINATION_PREFERENCE_CONSTANT,
                        "true");
                if (destinationPath != "" && checkFolderExists(destinationPath)) { //$NON-NLS-1$
                    // Store the selection in the preferences
                    provider.setPreference(BROWSEDESTFOLDERKEY,
                            txtPath.getText());
                } else if (destinationPath == "") { //$NON-NLS-1$
                    /*
                     * XPD-5742: Saket: Setting error message for empty path.
                     */
                    setErrorMessage(Messages.InputOutputSelectionWizardPage_DestPathEmptyError_message);
                    complete = false;
                } else {
                    // Invalid folder
                    setErrorMessage(Messages.ExportWizardPageIO_pathNotFoundErr_shortdesc);
                    complete = false;
                }
            } else if (exportDestination == ExportDestination.PROJECT)
                provider.setPreference(EXPORT_DESTINATION_PREFERENCE_CONSTANT,
                        "false");
            {

            }
        }

        // If there is a validator registered then get it to validate the
        // selection
        if (complete && validator != null) {
            String msg = validator.isValid(selection);
            if (msg != null && msg.length() > 0) {
                setErrorMessage(msg);
                complete = false;
            }
        }

        // If complete then clear error
        if (complete) {
            setErrorMessage(null);
            setMessage(null);
        }

        setPageComplete(complete);
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
        tExpandAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_EXPAND_ALL));
        tExpandAll.setToolTipText(Messages.ExportWizardPageIO_expandAll_label);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll
                .setToolTipText(Messages.ExportWizardPageIO_collapseAll_label);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.collapseAll();
            }
        });

        // Package tree viewer
        fileViewer = new ExplorerTreeViewer(container, input, sorter, filters);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.widthHint = 100;
        gridData.heightHint = 150;
        fileViewer.getControl().setLayoutData(gridData);

        fileViewer.setInitialSelection(selection);
        fileViewer
                .addPostSelectionChangedListener(new ISelectionChangedListener() {
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

        // Container for the Select/unselect all buttons
        final Composite cmpTreeSelect = new Composite(container, SWT.NULL);
        RowLayout selectLayout = new RowLayout();
        selectLayout.type = SWT.HORIZONTAL;
        cmpTreeSelect.setLayout(selectLayout);

        // Select All button
        final Button btnSelectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnSelectAll.setText(Messages.ExportWizardPageIO_selectAll_label);
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Unselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll.setText(Messages.ExportWizardPageIO_unselectAll_label);
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(false);
                updatePageCompletion();
            }
        });
    }

    /**
     * Create the destination selector control
     * 
     * @param container
     */
    private void createDestinationControl(Composite container) {
        /*
         * Get the preference value for export destination
         */
        String preferenceValue = null;
        if (getWizard() != null && getWizard().getClass() != null) {
            preferenceValue =
                    provider.getPreference(EXPORT_DESTINATION_PREFERENCE_CONSTANT);
        }
        GridData gridData;
        // Get the project export folder path
        String szProjectDestFolder = provider.getWorkspaceExportFolder();

        // Get the last used destination path
        String szDestPath = provider.getPreference(BROWSEDESTFOLDERKEY);

        // Destination group
        final Group grpDestination = new Group(container, SWT.NULL);
        grpDestination
                .setText(Messages.ExportWizardPageIO_destinationGroup_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        grpDestination.setLayoutData(gridData);
        grpDestination.setLayout(new GridLayout(3, false));

        if ((destinationOptions & PROJECT_DESTINATION_OPTION) != 0) {
            // Destination - Project radio button
            btnProject = new Button(grpDestination, SWT.RADIO);
            btnProject.setText(Messages.ExportWizardPageIO_project_label);
            /*
             * XPD-5008: Saket- Set the button state to what user selected
             * previously. If there was no previous selection made OR the
             * preference value is some garbage value and NOT "true" or "false",
             * then by default enable btnProject.
             */
            // btnProject.setSelection(true);
            if (preferenceValue != null
                    && (preferenceValue.equalsIgnoreCase("true") || preferenceValue
                            .equalsIgnoreCase("false"))) {
                btnProject.setSelection(preferenceValue
                        .equalsIgnoreCase("false") ? true : false);
            } else {
                btnProject.setSelection(true);
            }
            btnProject.addSelectionListener(this);
            // add destination location for Project selection
            Label lblLocation = new Label(grpDestination, SWT.NULL);
            lblLocation.setText(String
                    .format(Messages.ExportWizardPageIO_location_label,
                            szProjectDestFolder));
            gridData = new GridData();
            gridData.horizontalSpan = 2;
            lblLocation.setLayoutData(gridData);
        }

        if ((destinationOptions & FILESYSTEM_DESTINATION_OPTION) != 0) {
            if ((destinationOptions & PROJECT_DESTINATION_OPTION) != 0) {
                /*
                 * Destination - Path radio button (only add if both options
                 * available)
                 */
                btnPath = new Button(grpDestination, SWT.RADIO);
                btnPath.setText(Messages.ExportWizardPageIO_path_label);
                /*
                 * XPD-5008: Saket- Set the button state to what user selected
                 * previously. If there was no previous selection made OR the
                 * preference value is some garbage value and NOT "true" or
                 * "false", then by default disable btnPath.
                 */
                if (preferenceValue != null
                        && (preferenceValue.equalsIgnoreCase("true") || preferenceValue
                                .equalsIgnoreCase("false"))) {
                    btnPath.setSelection(preferenceValue
                            .equalsIgnoreCase("true") ? true : false);
                } else {
                    btnPath.setSelection(false);
                }
                btnPath.addSelectionListener(this);
            } else {
                Label lbl = new Label(grpDestination, SWT.NONE);
                lbl.setText(Messages.ExportWizardPageIO_path_label);
            }

            // Path input text control
            txtPath = new Text(grpDestination, SWT.BORDER);
            gridData = new GridData(GridData.FILL_BOTH);
            txtPath.setLayoutData(gridData);

            // If we have previously selected path then set that
            if (szDestPath != null)
                txtPath.setText(szDestPath);

            /*
             * txtPath should be enabled depending upon the state of btnPath
             */
            txtPath.setEnabled(btnPath.getSelection());
            txtPath.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    // Update page completion
                    updatePageCompletion();
                }
            });

            if (destinationPath != null) {
                txtPath.setText(destinationPath);
            }

            // Path browse button
            btnBrowse = new Button(grpDestination, SWT.NONE);
            btnBrowse.setText(Messages.ExportWizardPageIO_browse_label);
            /*
             * btnBrowse should be enabled depending upon the state of btnPath
             */
            btnBrowse.setEnabled(btnPath.getSelection());
            btnBrowse.addSelectionListener(this);
        }
    }

    /**
     * Check if the given path exists. If it doesn't then an error message is
     * displayed, otherwise attempt to load it
     * 
     * @param szFile
     */
    private boolean checkFolderExists(String szFile) {
        boolean bValid = false;

        File file = new File(szFile);

        if (file.isDirectory()) {
            // clear error message and load the XSL
            setErrorMessage(null);
            bValid = true;
        } else {
            setErrorMessage(Messages.ExportWizardPageIO_pathNotFoundErr_shortdesc);
        }

        return bValid;
    }

    public void setEmptySelectionAnError(boolean isEmptySelectionAnError) {
        this.isEmptySelectionAnError = isEmptySelectionAnError;
    }

}
