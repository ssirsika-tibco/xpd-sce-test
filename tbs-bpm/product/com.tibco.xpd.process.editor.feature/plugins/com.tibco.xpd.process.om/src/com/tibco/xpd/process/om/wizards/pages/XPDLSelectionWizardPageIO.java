/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.wizards.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.wizards.AbstractXPDLSelectionWizard;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExplorerTreeViewer;

/**
 * Wizard page for selecting multiple or single XPDL files for importing the om
 * participants in to.
 * 
 * @author glewis
 * 
 */
public class XPDLSelectionWizardPageIO extends AbstractXpdWizardPage implements
        SelectionListener {

    // Tree view control
    private ExplorerTreeViewer fileViewer;

    // This will be set to the initial selection and subsequently to the current
    // selection in the tree
    private IStructuredSelection selection;

    private String destinationPath;

    private Text txtPath;

    private Text omNameTxt;

    private Button btnBrowse;

    // The destination folder browser preference key
    private static final String BROWSEDESTFOLDERKEY = "DESTBROWSEFOLDER"; //$NON-NLS-1$

    private final Object input;

    private final ViewerSorter sorter;

    private String defaultOMName = null;

    private final ViewerFilter[] filters;

    private IFolder selectedFolder = null;

    private final boolean bIsDestinationRequired;

    private static final String REFLECTION_OM_RESOURCES_UI_PLUGIN_ID = "com.tibco.xpd.om.resources.ui"; //$NON-NLS-1$

    private static final String REFLECTION_OM_RESOURCES_UI_PACKAGEFOLDERPICKER_CLASS = "com.tibco.xpd.om.resources.ui.pickers.PackageFolderPicker"; //$NON-NLS-1$

    private static final String REFLECTION_OM_SET_ALLOW_MULTIPLE_METHOD = "setAllowMultiple"; //$NON-NLS-1$

    private static final String REFLECTION_OM_SET_INPUT_METHOD = "setInput"; //$NON-NLS-1$

    private static final String REFLECTION_OM_OPEN_METHOD = "open"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_RESULT_METHOD = "getResult"; //$NON-NLS-1$

    private Bundle omResourcesBundle = null;

    private Class omPackagePickerCls = null;

    private Method setAllowMultipleMeth = null;

    private Method setInputMeth = null;

    private Method openMeth = null;

    private Method getResultMeth = null;

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
    public XPDLSelectionWizardPageIO(IStructuredSelection selection,
            Object input, ViewerSorter sorter, ViewerFilter[] filters,
            boolean bIsDestinationRequired) {
        super(Messages.XPDLSelectionWizardPageIO_Import_title);
        this.selection = selection;
        this.input = input;
        this.sorter = sorter;
        this.filters = filters;
        this.bIsDestinationRequired = bIsDestinationRequired;

        try {
            omResourcesBundle = Platform
                    .getBundle(REFLECTION_OM_RESOURCES_UI_PLUGIN_ID);
            if (omResourcesBundle != null) {
                omPackagePickerCls = omResourcesBundle
                        .loadClass(REFLECTION_OM_RESOURCES_UI_PACKAGEFOLDERPICKER_CLASS);

                Class setAllowMultParam = boolean.class;
                Class setInputParam = Object.class;

                setAllowMultipleMeth = omPackagePickerCls.getMethod(
                        REFLECTION_OM_SET_ALLOW_MULTIPLE_METHOD,
                        setAllowMultParam);
                setInputMeth = omPackagePickerCls.getMethod(
                        REFLECTION_OM_SET_INPUT_METHOD, setInputParam);
                openMeth = omPackagePickerCls.getMethod(
                        REFLECTION_OM_OPEN_METHOD, null);
                getResultMeth = omPackagePickerCls.getMethod(
                        REFLECTION_OM_GET_RESULT_METHOD, null);
            }
        } catch (Exception e) {
        }
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

        if (bIsDestinationRequired) {
            createOMNameControl(container);
            // Create the destination selector output controls
            createDestinationControl(container);
        }

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
                    try {
                        handleContainerBrowseButtonPressed();
                    } catch (Exception e1) {
                        int h = 0;
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
     * Gets the export destination folder
     */
    public final IFolder getDestinationFolder() {

        return selectedFolder;
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
            setMessage(Messages.XPDLSelectionWizardPageIO_selectFileToImportTo_shortdesc);
            complete = false;
        }

        // If files are selected then validate the destination
        if (complete && txtPath != null) {
            // Check that the folder exists
            destinationPath = txtPath.getText();

            if (destinationPath != "") { //$NON-NLS-1$
                // Store the selection in the preferences
                ((AbstractXPDLSelectionWizard) getWizard()).setPreference(
                        BROWSEDESTFOLDERKEY, txtPath.getText());
            } else if (destinationPath == "") { //$NON-NLS-1$
                // Folder doesn't exist
                setMessage(Messages.XPDLSelectionWizardPageIO_selectOutputPath_shortdesc);
                complete = false;
            } else {
                // Invalid folder
                setErrorMessage(Messages.XPDLSelectionWizardPageIO_pathNotFoundErr_shortdesc);
                complete = false;
            }
        }
        if (complete && omNameTxt != null) {
            defaultOMName = omNameTxt.getText();
            if (defaultOMName != "") { //$NON-NLS-1$                
                if (fileNameExists(getDestinationFolder(), defaultOMName
                        + ".om")) {
                    complete = false;
                }
            } else if (defaultOMName == "") { //$NON-NLS-1$
                // Folder doesn't exist
                setErrorMessage(Messages.XPDLSelectionWizardPageIO_selectOutputPath_shortdesc);
                complete = false;
            }
        }

        // If complete then clear error
        if (complete) {
            setErrorMessage(null);
        }

        setPageComplete(complete);
    }

    public String getDefaultOMName() {
        return defaultOMName + ".om";
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
        tExpandAll
                .setToolTipText(Messages.XPDLSelectionWizardPageIO_expandAll_label);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry().get(
                        XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll
                .setToolTipText(Messages.XPDLSelectionWizardPageIO_collapseAll_label);
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
        btnSelectAll
                .setText(Messages.XPDLSelectionWizardPageIO_selectAll_label);
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Unselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll
                .setText(Messages.XPDLSelectionWizardPageIO_unselectAll_label);
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
        GridData gridData;

        IFile selectedFile = (IFile) selection.getFirstElement();
        IProject project = selectedFile.getProject();
        // Get the last used destination path
        SpecialFolder specialFolderOfKind = SpecialFolderUtil
                .getSpecialFolderOfKind(project, "om");
        String szDestPath = ""; //$NON-NLS-1$
        if (specialFolderOfKind != null) {
            szDestPath = project.getFullPath().append(
                    specialFolderOfKind.getFolder().getProjectRelativePath())
                    .toString();
        }

        // Destination group
        final Group grpDestination = new Group(container, SWT.NULL);
        grpDestination
                .setText(Messages.XPDLSelectionWizardPageIO_destinationGroup_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        grpDestination.setLayoutData(gridData);
        grpDestination.setLayout(new GridLayout(2, false));

        // Path input text control
        txtPath = new Text(grpDestination, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        txtPath.setLayoutData(gridData);

        // If we have previously selected path then set that
        if (szDestPath != null && szDestPath.length() > 0) {
            txtPath.setText(szDestPath);
            selectedFolder = ResourcesPlugin.getWorkspace().getRoot()
                    .getFolder(new Path(szDestPath));
        }

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
        btnBrowse.setText(Messages.XPDLSelectionWizardPageIO_browse_label);
        btnBrowse.addSelectionListener(this);
    }

    /**
     * Create the destination selector control
     * 
     * @param container
     */
    private void createOMNameControl(Composite container) {
        GridData gridData;

        // Destination group
        final Group omNameDestination = new Group(container, SWT.NULL);
        omNameDestination
                .setText(Messages.XPDLSelectionWizardPageIO_name_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        omNameDestination.setLayoutData(gridData);
        omNameDestination.setLayout(new GridLayout());

        // Path input text control
        omNameTxt = new Text(omNameDestination, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        omNameTxt.setLayoutData(gridData);

        IFile selectedFile = (IFile) selection.getFirstElement();
        IProject project = selectedFile.getProject();
        SpecialFolder specialFolderOfKind = SpecialFolderUtil
                .getSpecialFolderOfKind(project, "om");
        if (specialFolderOfKind != null) {
            omNameTxt.setText(getOrgModelNameToCreate(
                    specialFolderOfKind.getFolder(), "OrganizationModel")
                    .replace(".om", ""));
        }

        omNameTxt.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                // Update page completion
                updatePageCompletion();
            }
        });
    }

    /**
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * 
     */
    protected void handleContainerBrowseButtonPressed()
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor constructor = omPackagePickerCls
                .getConstructor(Shell.class);
        Object packageFolderPickerObj = constructor.newInstance(getShell());
        if (packageFolderPickerObj != null) {
            setAllowMultipleMeth.invoke(packageFolderPickerObj, false);
            setInputMeth.invoke(packageFolderPickerObj, ResourcesPlugin
                    .getWorkspace().getRoot());
            Object retVal = openMeth.invoke(packageFolderPickerObj);
            if (((Integer) retVal).intValue() == Dialog.OK) {
                Object[] result = (Object[]) getResultMeth
                        .invoke(packageFolderPickerObj);
                // OK clicked so store the selected packages folder
                if (result[0] instanceof IAdaptable) {
                    selectedFolder = (IFolder) ((IAdaptable) result[0])
                            .getAdapter(IFolder.class);

                    if (selectedFolder != null) {
                        String folderName = selectedFolder.getProject()
                                .getName()
                                + IPath.SEPARATOR
                                + selectedFolder.getProjectRelativePath()
                                        .toString();

                        setErrorMessage(null);
                        txtPath.setText(folderName);
                    }
                }
            }
        }
    }

    /**
     * Get the package name to create
     * 
     * @param container
     * @return The package name
     */
    public String getOrgModelNameToCreate(IFolder folder, String modelFileName) {

        if (folder != null) {
            String tempFileName = modelFileName + ".om"; //$NON-NLS-1$
            for (int i = 1; fileNameExists(folder, tempFileName); ++i) {
                tempFileName = modelFileName + i + ".om"; //$NON-NLS-1$ //$NON-NLS-2$                  
            }

            return tempFileName;
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Check if the file name already exists in the projects export folder
     * 
     * @param project
     * @param modelFileName
     * 
     * @return true if the passed modelFileName is already in use
     */
    private boolean fileNameExists(IFolder folder, String modelFileName) {
        boolean fileNameExists = false;

        if (folder != null) {
            IResource resource = folder.findMember(modelFileName);
            if (resource != null) {
                fileNameExists = true;
                return fileNameExists;
            }
        }

        return fileNameExists;
    }
}
