/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard.pages;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;

/**
 * Export's main wizard page. This page will allow the user to select the xpdl
 * files to export from the projects and select the export destination. The
 * destination can either be the exports workspace folder in the project, or an
 * external path in the file system.
 * 
 * @author njpatel
 * 
 * @deprecated Use {@link ExportWizardPage} instead.
 * 
 */
public class ExportWizardPageIO extends AbstractXpdWizardPage implements SelectionListener {

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
    public ExportWizardPageIO(IStructuredSelection selection, Object input,
            ViewerSorter sorter, ViewerFilter[] filters) {
        super("Export"); //$NON-NLS-1$
        this.selection = selection;
        this.input = input;
        this.sorter = sorter;
        this.filters = filters;
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
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
                    dDialog
                            .setText(Messages.ExportWizardPageIO_selectFolder_title);
                    dDialog
                            .setMessage(Messages.ExportWizardPageIO_selectFolder_message);

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
                    txtPath.setEnabled(btnPath.getSelection());
                    btnBrowse.setEnabled(btnPath.getSelection());
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
    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Returns a list of files selected in the File Viewer
     * 
     * @return IStructuredSelection List of files selected, empty list if no
     *         files selected
     */
    public IStructuredSelection getSelectedFiles() {
        return selection;
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
     * Gets the export destination path
     * 
     * @return Returns IPath if destination choice was
     *         <code>{@link ExportDestination#PATH}</code>, otherwise returns
     *         <code>null</code>.
     */
    public final IPath getDestinationPath() {

        return (destinationPath != null ? new Path(destinationPath) : null);
    }

    /**
     * Check page completion
     */
    protected void updatePageCompletion() {
        boolean complete = true;

        setPageComplete(false);

        // Check that we have selection in the tree
        selection = fileViewer.getSelectedFiles();

        if (selection == null || selection.isEmpty()) {
            // No selection
            setMessage(Messages.ExportWizardPageIO_selectElementsToExport_shortdesc);
            complete = false;
        }

        // If files are selected then validate the destination
        if (complete) {
            // If the destination type is set to path then verify path
            exportDestination = btnProject.getSelection() ? ExportDestination.PROJECT
                    : ExportDestination.PATH;
            if (exportDestination == ExportDestination.PATH) {
                // Check that the folder exists
                destinationPath = txtPath.getText();

                if (destinationPath != "" && checkFolderExists(destinationPath)) { //$NON-NLS-1$
                    // Store the selection in the preferences
                    ((AbstractExportWizard) getWizard()).setPreference(
                            BROWSEDESTFOLDERKEY, txtPath.getText());
                } else if (destinationPath == "") { //$NON-NLS-1$
                    // Folder doesn't exist
                    setMessage(Messages.ExportWizardPageIO_selectOutputPath_shortdesc);
                    complete = false;
                } else {
                    // Invalid folder
                    setErrorMessage(Messages.ExportWizardPageIO_pathNotFoundErr_shortdesc);
                    complete = false;
                }
            }
        }

        // If complete then clear error
        if (complete) {
            setErrorMessage(null);
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
                .getImageRegistry().get(
                        XpdResourcesUIConstants.EXPORT_EXPAND_ALL));
        tExpandAll.setToolTipText(Messages.ExportWizardPageIO_expandAll_label);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry().get(
                        XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll
                .setToolTipText(Messages.ExportWizardPageIO_collapseAll_label);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
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
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Unselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll.setText(Messages.ExportWizardPageIO_unselectAll_label);
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
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
        GridData gridData;
        // Get the project export folder path
        String szProjectDestFolder = ((AbstractExportWizard) getWizard())
                .getWorkspaceExportFolder();

        // Get the last used destination path
        String szDestPath = ((AbstractExportWizard) getWizard())
                .getPreference(BROWSEDESTFOLDERKEY);

        // Destination group
        final Group grpDestination = new Group(container, SWT.NULL);
        grpDestination
                .setText(Messages.ExportWizardPageIO_destinationGroup_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        grpDestination.setLayoutData(gridData);
        grpDestination.setLayout(new GridLayout(3, false));

        // Destination - Project radio button
        btnProject = new Button(grpDestination, SWT.RADIO);
        btnProject.setText(Messages.ExportWizardPageIO_project_label);
        // select project as default
        btnProject.setSelection(true);
        btnProject.addSelectionListener(this);
        // add destination location for Project selection
        Label lblLocation = new Label(grpDestination, SWT.NULL);
        lblLocation.setText(String
                .format(Messages.ExportWizardPageIO_location_label,
                        szProjectDestFolder));
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        lblLocation.setLayoutData(gridData);

        // Destination - Path radio button
        btnPath = new Button(grpDestination, SWT.RADIO);
        btnPath.setText(Messages.ExportWizardPageIO_path_label);
        btnPath.addSelectionListener(this);

        // Path input text control
        txtPath = new Text(grpDestination, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        txtPath.setLayoutData(gridData);

        // If we have previously selected path then set that
        if (szDestPath != null)
            txtPath.setText(szDestPath);

        // Disable the text control as default
        txtPath.setEnabled(false);
        txtPath.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                // Update page completion
                updatePageCompletion();
            }
        });

        // Path browse button
        btnBrowse = new Button(grpDestination, SWT.NONE);
        btnBrowse.setText(Messages.ExportWizardPageIO_browse_label);
        // Disable the browse button as default
        btnBrowse.setEnabled(false);
        btnBrowse.addSelectionListener(this);
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

}
