/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard.pages;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardResourceImportPage;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider;
import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider2;

/**
 * Import wizard page that provides a viewer to select the files to import and a
 * target project selector.
 * 
 * @author njpatel
 */
public class ImportWizardPageIO extends AbstractXpdWizardResourceImportPage
        implements SelectionListener {

    // dialog button IDs
    private final static Integer BTN_FOLDERBROWSE = new Integer(
            IDialogConstants.CLIENT_ID + 1);

    private final static Integer BTN_SELECTALL = new Integer(
            IDialogConstants.SELECT_ALL_ID);

    private final static Integer BTN_DESELECTALL = new Integer(
            IDialogConstants.DESELECT_ALL_ID);

    private Combo cmbFromDirectory = null;

    private Button chkReplaceAll = null;

    private Button btnSelectAll = null;

    private Button btnDeselectAll = null;

    // Folder browse preference key - to store the last selected path
    private static final String BROWSEFOLDERKEY = "BROWSEFOLDER"; //$NON-NLS-1$

    // From Directory combo preference key - to store the last 5 paths selected
    protected static final String FROMDIRCOMBOKEY = "FROMDIRCOMBO"; //$NON-NLS-1$

    private final IImportWizardPageProvider provider;

    private final IStructuredSelection selection;

    /**
     * (non-Javadoc) Method declared on IDialogPage.
     */
    @Override
    public void createControl(Composite parent) {

        /*
         * Copied from the super class to allow us to hide the destination
         * selection controls in the RCP application.
         */

        initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        composite.setFont(parent.getFont());

        createSourceGroup(composite);

        // Don't show destination selection in the RCP
        if (!XpdResourcesPlugin.isRCP()) {
            createDestinationGroup(composite);
        }

        createOptionsGroup(composite);

        restoreWidgetValues();
        updateWidgetEnablements();
        setPageComplete(determinePageCompletion());
        setErrorMessage(null); // should not initially have error message

        setControl(composite);
    }

    /**
     * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getResourcePath()
     * 
     * @return
     */
    @Override
    protected IPath getResourcePath() {
        if (XpdResourcesPlugin.isRCP()) {
            /*
             * In RCP the target selection controls will be hidden so we need to
             * just return the initial selection.
             */
            Assert.isNotNull(selection);

            Object element = selection.getFirstElement();

            if (element instanceof IResource) {
                return ((IResource) element).getFullPath();
            }
        }
        return super.getResourcePath();
    }

    /**
     * Import wizard page to select the files to import and choose the target
     * project.
     * 
     * @param selection
     */
    public ImportWizardPageIO(IStructuredSelection selection,
            IImportWizardPageProvider provider) {
        super("Import", selection); //$NON-NLS-1$
        this.selection = selection;
        this.provider = provider;

        if (provider == null) {
            throw new NullPointerException(
                    "IImportWizardPageProvider not specified."); //$NON-NLS-1$
        }
    }

    /**
     * Get the selected files for import
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List getSelectedFiles() {
        return getSelectedResources();
    }

    /**
     * Get whether the user selected to overwrite existing resources
     * 
     * @return boolean
     */
    public boolean getOverwriteExistingResources() {
        final boolean[] replaceAll = new boolean[1];

        getContainer().getShell().getDisplay().syncExec(new Runnable() {

            @Override
            public void run() {
                // XPD-5994: null check is required, the extending Wizard Page
                // might select not exclude the overwrite checkbox option
                if (chkReplaceAll != null) {
                    replaceAll[0] = chkReplaceAll.getSelection();
                } else {
                    replaceAll[0] = false;
                }
            }

        });

        return replaceAll[0];
    }

    /**
     * Get the selected destination folder.
     * 
     * @return IFolder
     * 
     * @deprecated (since 3.0) Use {@link #getDestinationContainer()} to get the
     *             selected destination.
     */
    @Deprecated
    public final IFolder getDestinationFolder() {
        final IFolder[] folder = new IFolder[1];

        // Run in UI thread as it will access an UI control
        getContainer().getShell().getDisplay().syncExec(new Runnable() {

            @Override
            public void run() {
                folder[0] = (IFolder) getSpecifiedContainer();
            }

        });
        return folder[0];
    }

    /**
     * Get the selected destination container.
     * 
     * @since 3.0
     * 
     * @return <code>IContainer</code>
     */
    public final IContainer getDestinationContainer() {
        final IContainer[] container = new IContainer[1];

        // Run in UI thread as it will access an UI control
        getContainer().getShell().getDisplay().syncExec(new Runnable() {

            @Override
            public void run() {
                container[0] = getSpecifiedContainer();
            }

        });
        return container[0];

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.dialogs.WizardResourceImportPage#createSourceGroup(org
     * .eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createSourceGroup(Composite parent) {
        createRootDirectoryGroup(parent);
        createFileSelectionGroup(parent);
        createSelectDeselectAllGroup(parent);

        // Select the first folder from the from selection as default
        if (cmbFromDirectory.getItemCount() > 0) {
            cmbFromDirectory.select(0);
            updateFromSourceSelection();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.dialogs.WizardDataTransferPage#createOptionsGroupButtons
     * (org.eclipse.swt.widgets.Group)
     */
    @Override
    protected void createOptionsGroupButtons(Group optionsGroup) {
        chkReplaceAll = new Button(optionsGroup, SWT.CHECK);
        chkReplaceAll
                .setText(Messages.ImportWizardPageIO_overwriteExistingFiles_label);
    }

    /**
     * Create the Select All and Deselect All buttons under the source viewer
     * 
     * @param parent
     */
    protected void createSelectDeselectAllGroup(Composite parent) {
        Composite srcContainer = new Composite(parent, SWT.NONE);
        srcContainer.setLayout(new GridLayout(2, false));
        srcContainer.setFont(parent.getFont());
        srcContainer.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL));

        btnSelectAll = new Button(srcContainer, SWT.PUSH);
        btnSelectAll.setText(Messages.ImportWizardPageIO_selectAll_label);
        btnSelectAll.setData(BTN_SELECTALL);
        btnSelectAll.addSelectionListener(this);

        btnDeselectAll = new Button(srcContainer, SWT.PUSH);
        btnDeselectAll.setText(Messages.ImportWizardPageIO_unselectAll_label);
        btnDeselectAll.setData(BTN_DESELECTALL);
        btnDeselectAll.addSelectionListener(this);
    }

    /**
     * Create the group for creating the root directory
     * 
     * @param parent
     */
    protected void createRootDirectoryGroup(Composite parent) {
        Composite srcContainer = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();

        layout.numColumns = 3;
        srcContainer.setLayout(layout);
        srcContainer.setFont(parent.getFont());
        srcContainer.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL));

        Label lblFromDirectory = new Label(srcContainer, SWT.NONE);
        lblFromDirectory.setText(Messages.ImportWizardPageIO_fromDir_label);

        cmbFromDirectory = new Combo(srcContainer, SWT.NONE);
        cmbFromDirectory.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        cmbFromDirectory.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                /*
                 * When from directory selection changes update the preference
                 * list so that the last selected is promoted to top of list.
                 */
                addToFromDirectoryPreferenceList(cmbFromDirectory.getText());

                /* And update rest of controls. */
                updateFromSourceSelection();
            }
        });

        cmbFromDirectory.addFocusListener(new FocusListener() {
            /*
             * @see FocusListener.focusGained(FocusEvent)
             */
            @Override
            public void focusGained(FocusEvent e) {
                // Do nothing when getting focus
            }

            /*
             * @see FocusListener.focusLost(FocusEvent)
             */
            @Override
            public void focusLost(FocusEvent e) {
                // If new selection then update the folder/file view
                if (addImportRoot(cmbFromDirectory.getText())) {
                    updateFromSourceSelection();
                }
            }
        });

        /*
         * Sid XPD_4131: It is natural to expect the <enter> to update from the
         * data in the combo
         */
        cmbFromDirectory.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    updateFromSourceSelection();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        // Get the list of saved paths from the preference
        String[] szSavedPaths = getFromDirectoryPreferenceList();

        if (szSavedPaths != null) {
            for (int idx = 0; idx < szSavedPaths.length; idx++) {
                cmbFromDirectory.add(szSavedPaths[idx]);
            }
        }

        Button btnFolderBrowse = new Button(srcContainer, SWT.PUSH);
        btnFolderBrowse.setText(Messages.ImportWizardPageIO_browse_label);
        btnFolderBrowse.setData(BTN_FOLDERBROWSE);
        btnFolderBrowse.addSelectionListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getFileProvider()
     */
    @Override
    protected ITreeContentProvider getFileProvider() {
        return new WorkbenchContentProvider() {
            @Override
            public Object[] getChildren(Object o) {
                if (o instanceof FilteredFSElement) {
                    FilteredFSElement element = (FilteredFSElement) o;
                    return element
                            .getFiles(FileSystemStructureProvider.INSTANCE)
                            .getChildren(element);
                }
                return new Object[0];
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getFolderProvider()
     */
    @Override
    protected ITreeContentProvider getFolderProvider() {
        return new WorkbenchContentProvider() {
            @Override
            public Object[] getChildren(Object o) {
                if (o instanceof FilteredFSElement) {
                    FilteredFSElement element = (FilteredFSElement) o;
                    return element
                            .getFolders(FileSystemStructureProvider.INSTANCE)
                            .getChildren(element);
                }
                return new Object[0];
            }

            @Override
            public boolean hasChildren(Object o) {
                if (o instanceof FilteredFSElement) {
                    FilteredFSElement element = (FilteredFSElement) o;

                    if (element.isPopulated())
                        return getChildren(element).length > 0;
                    // If we have not populated then wait until asked
                    return true;
                }
                return false;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.WizardDataTransferPage#validateSourceGroup()
     */
    @Override
    protected boolean validateSourceGroup() {
        boolean bRet;

        // Check that the folder specified in the from combo is a folder
        bRet = isDirectory(cmbFromDirectory.getText());

        if (bRet) {
            enableSelectDeselectAll(true);
            // Check that a resource selection has been made
            if (getSelectedFiles() == null || getSelectedFiles().size() == 0) {
                setErrorMessage(Messages.ImportWizardPageIO_noResourceSelectedErr_message);
                bRet = false;
            }
        } else {
            setMessage(Messages.ImportWizardPageIO_sourceNotEmpty_message);
            enableSelectDeselectAll(false);
        }

        if (bRet) {
            // No error so clear error message
            setErrorMessage(null);
        }

        return bRet;
    }

    @Override
    protected boolean determinePageCompletion() {
        boolean ret = super.determinePageCompletion();

        /*
         * XPD-4271: Don't run this test in the Studio Analysts application as
         * the destination controls are hidden.
         */
        if (ret && !XpdResourcesPlugin.isRCP()) {
            String errMsg = null;
            String warnMsg = null;
            String infoMsg = null;

            // Check that the destination is a packages folder
            IContainer container = getSpecifiedContainer();

            if (container != null) {
                IStatus status =
                        provider.validateDestinationContainer(container);

                if (status != null) {
                    if (!status.isOK()) {
                        switch (status.getSeverity()) {
                        case IStatus.ERROR:
                            errMsg =
                                    status.getMessage() != null ? status
                                            .getMessage() : null;
                            break;
                        case IStatus.INFO:
                            infoMsg =
                                    status.getMessage() != null ? status
                                            .getMessage() : null;
                        default:
                            warnMsg =
                                    status.getMessage() != null ? status
                                            .getMessage() : null;
                            break;
                        }
                    }
                } else {
                    throw new NullPointerException(
                            "validateDestinationContainer returned null."); //$NON-NLS-1$
                }

                // Check if we need to validate the selected resources
                if (errMsg == null
                        && provider instanceof IImportWizardPageProvider2) {
                    status =
                            ((IImportWizardPageProvider2) provider)
                                    .validateResourceSelection(getSelectedResources());

                    if (status != null && !status.isOK()) {
                        switch (status.getSeverity()) {
                        case IStatus.ERROR:
                            errMsg =
                                    status.getMessage() != null ? status
                                            .getMessage() : null;
                            break;
                        case IStatus.INFO:
                            if (infoMsg == null) {
                                infoMsg =
                                        status.getMessage() != null ? status
                                                .getMessage() : null;
                            }
                        default:
                            if (warnMsg == null) {
                                warnMsg =
                                        status.getMessage() != null ? status
                                                .getMessage() : null;
                            }
                            break;
                        }
                    }
                }
            } else {
                // Not a packages folder
                errMsg =
                        Messages.ImportWizardPageIO_destFolderNotFoundErr_message;
            }

            // Update the message on the page
            if (errMsg != null) {
                setErrorMessage(errMsg);
                ret = false;
            } else if (warnMsg != null) {
                setMessage(warnMsg, WARNING);
            } else if (infoMsg != null) {
                setMessage(infoMsg, INFORMATION);
            } else {
                setMessage(null);
            }
        }

        return ret;
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

        if (e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();

            // Destination root browse button
            if (btn.getData().equals(BTN_FOLDERBROWSE)) {
                String szSelection = null;
                // Show the directory selection dialog
                DirectoryDialog dDialog = new DirectoryDialog(getShell());
                dDialog.setText(Messages.ImportWizardPageIO_importFromDirDialog_title);
                dDialog.setMessage(Messages.ImportWizardPageIO_importFromDirDialog_message);

                // Set the last browse position from the preferences
                dDialog.setFilterPath(((AbstractImportWizard) getWizard())
                        .getPreference(BROWSEFOLDERKEY));

                szSelection = dDialog.open();
                if (szSelection != null) {
                    // If new selection made then update the folder/file view
                    if (addImportRoot(szSelection)) {
                        updateFromSourceSelection();

                        // Store the selected path in the preferences
                        ((AbstractImportWizard) getWizard())
                                .setPreference(BROWSEFOLDERKEY, szSelection);
                    }

                }
                // Select All button
            } else if (btn.getData().equals(BTN_SELECTALL)) {
                setAllSelections(true);
                // Deselect All button
            } else if (btn.getData().equals(BTN_DESELECTALL)) {
                setAllSelections(false);
            }
        }
        // Update the page
        updateWidgetEnablements();
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
        // TODO Auto-generated method stub

    }

    @Override
    protected void updateWidgetEnablements() {
        /*
         * Overriden as when page complete is true the message is cleared - this
         * is not ideal as destination container validation may want to display
         * warning but still allow completion
         */
        boolean pageComplete = determinePageCompletion();
        setPageComplete(pageComplete);
    }

    /**
     * Update the source viewer with the selection made in the root selector
     */
    @SuppressWarnings("restriction")
    protected void updateFromSourceSelection() {
        FilteredFSElement currentRoot = getFileSystemTree();
        this.selectionGroup.setRoot(currentRoot);

        updateWidgetEnablements();
    }

    /**
     * Get the file system tree of the selected path
     */
    protected FilteredFSElement getFileSystemTree() {

        final File sourceDirectory = new File(cmbFromDirectory.getText());

        // If the given directory is valid diretory then get the files
        if (sourceDirectory.exists() && sourceDirectory.isDirectory()) {
            final FilteredFSElement[] results = new FilteredFSElement[1];

            BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
                @Override
                public void run() {
                    // Create the root element from the supplied file system
                    // object
                    results[0] = createRootElement(sourceDirectory);
                }
            });

            return results[0];
        }

        return null;
    }

    /**
     * Creates and returns a <code>FileSystemElement</code> if the specified
     * file system object merits one. The criteria for this are: Also create the
     * children.
     */
    protected FilteredFSElement createRootElement(Object fileSystemObject) {
        IImportStructureProvider importStructureProvider =
                FileSystemStructureProvider.INSTANCE;

        boolean isContainer =
                importStructureProvider.isFolder(fileSystemObject);
        String elementLabel =
                importStructureProvider.getLabel(fileSystemObject);

        // Apply the file extension filter to the File System Element object
        String[] fileExtensionFilter = provider.getFileExtensionFilter();
        if (fileExtensionFilter != null) {
            FilteredFSElement.setFileExtensionFilter(Arrays
                    .asList(fileExtensionFilter));
        } else {
            FilteredFSElement.setFileExtensionFilter(null);
        }

        // Use an empty label so that display of the element's full name
        // doesn't include a confusing label
        FilteredFSElement dummyParent = new FilteredFSElement("", null, true);//$NON-NLS-1$
        dummyParent.setPopulated();

        FilteredFSElement result =
                new FilteredFSElement(elementLabel, dummyParent, isContainer);
        result.setFileSystemObject(fileSystemObject);
        // Get the files for the element so as to build the first level
        result.getFiles(importStructureProvider);

        return dummyParent;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.dialogs.WizardResourceImportPage#
     * handleContainerBrowseButtonPressed()
     */
    @Override
    protected void handleContainerBrowseButtonPressed() {
        // Display the picker dialog
        SelectionDialog dialog =
                provider.getDestinationContainerSelectionDialog();

        if (dialog != null && dialog.open() == Dialog.OK) {
            // OK clicked so store the selected packages folder
            if (dialog.getResult()[0] instanceof IAdaptable) {
                IFolder folder =
                        (IFolder) ((IAdaptable) dialog.getResult()[0])
                                .getAdapter(IFolder.class);

                if (folder != null) {
                    String folderName =
                            folder.getProject().getName()
                                    + IPath.SEPARATOR
                                    + folder.getProjectRelativePath()
                                            .toString();

                    setErrorMessage(null);
                    setContainerFieldValue(folderName);
                }
            }
        }
    }

    /**
     * Add the selected folder into the combo list
     * 
     * @param szSelection
     * @return boolean
     */
    private boolean addImportRoot(String szSelection) {
        boolean bRet = false;

        // Check that the selection made is not already the current selection
        if (cmbFromDirectory.getText() != szSelection) {

            // See if the item is already added to the combo list
            int nIndex = cmbFromDirectory.indexOf(szSelection);

            if (nIndex >= 0) {
                // Selection already present in list
                cmbFromDirectory.select(nIndex);
            } else {
                cmbFromDirectory.setText(szSelection);
                cmbFromDirectory.add(szSelection);
            }

            bRet = true;
        }

        /*
         * Always Update the preference list with the new selection as this is
         * now able to simply promote new or existing entry to start of list (so
         * whatever user last selected will come back as first entry in combo.
         */
        addToFromDirectoryPreferenceList(szSelection);

        return bRet;
    }

    /**
     * Enable/disable the Select All and Deselect All buttons
     * 
     * @param bEnable
     */
    private void enableSelectDeselectAll(boolean bEnable) {
        btnSelectAll.setEnabled(bEnable);
        btnDeselectAll.setEnabled(bEnable);
    }

    /**
     * Get a list of previously selected source locations in the wizard page
     * source selection.
     * 
     * @return List of selected source location paths
     */
    protected String[] getFromDirectoryPreferenceList() {
        String szPref =
                ((AbstractImportWizard) getWizard())
                        .getPreference(FROMDIRCOMBOKEY);

        // ((ImportXPDLWizard)getWizard()).setPreference(FROMDIRCOMBOKEY, "");

        if (szPref != null && szPref.length() > 0) {
            // The preference list is \1 delimited
            return szPref.split("\1"); //$NON-NLS-1$
        }
        // No preference set
        return null;
    }

    /**
     * Add the selected source path to the preference list to persist it.
     * 
     * @param szPath
     *            The selected source path
     */
    private void addToFromDirectoryPreferenceList(String szPath) {
        String[] szEntries = getFromDirectoryPreferenceList();
        String szPref = null;

        // Only add the entry to the preference list if the path given
        // is a valid directory
        if (isDirectory(szPath)) {
            // Add the new path to the preference string
            szPref = szPath;

            // Insert (or promote) this path to the top of the preferences list.

            if (szEntries != null) {
                int numPaths = 1;

                int nLength = szEntries.length;

                for (int idx = 0; idx < nLength && numPaths <= 5; idx++) {
                    if (!szPath.equals(szEntries[idx])) {
                        szPref += "\1" + szEntries[idx]; //$NON-NLS-1$
                        numPaths++;
                    }
                }
            }

            // Add the preference
            ((AbstractImportWizard) getWizard()).setPreference(FROMDIRCOMBOKEY,
                    szPref);
        }
    }

    /**
     * Check if the given path is a folder
     * 
     * @param szPath
     *            The source path to test
     * @return <code>true</code> if the path exists and is a folder,
     *         <code>false</code> otherwise.
     */
    private boolean isDirectory(String szPath) {
        File sourceDirectory = new File(szPath);

        // Return true if the given path exists and is a directory
        return (sourceDirectory.exists() && sourceDirectory.isDirectory());
    }

}
