/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page for the Studio export wizard that allows the selection of the
 * projects to export and the target location of the archive.
 * 
 * @since 3.5.2
 */
public class ProjectExportPage extends AbstractXpdWizardPage {

    private static final String PREV_DESTINATION_KEY =
            "ProjectExportPage.previousDestinations"; //$NON-NLS-1$

    /*
     * Store the isCompress details to the Preference.
     */
    private static final String PREV_COMPRESS_PROJ_KEY =
            "ProjectExportPage.previousCompressProj"; //$NON-NLS-1$

    private static final String MESSAGE = Messages.ProjectExportPage_shortdesc;

    /** Output archive type */
    public enum FileType {
        ZIP, TAR;
    }

    public static final String ZIP_EXT = "zip"; //$NON-NLS-1$

    public static final String TAR_EXT = "tar"; //$NON-NLS-1$

    public static final String TAR_GZ_EXT = "tar.gz"; //$NON-NLS-1$

    public static final String TGZ_EXT = "tgz"; //$NON-NLS-1$

    private CheckboxTreeViewer viewer;

    private String outputPath;

    private FileType fileType = FileType.ZIP;

    /*
     * SCF-282: we always want projects to be compressed.
     */
    private boolean isCompressed = true;

    private Button saveInZipBtn;

    private Button saveInTarBtn;

    private Button compressBtn;

    private Button selectReferncedProjectsBtn;

    private Button selectReferencingProjectsBtn;

    private INavigatorContentService navigatorService;

    private Text archiveOutputTxt;

    private IStructuredSelection selection;

    /**
     * @param pageName
     */
    public ProjectExportPage(String pageName) {
        super(pageName);
        setTitle(Messages.ProjectExportPage_title);
        setMessage(MESSAGE);
    }

    /**
     * Set the current wizard selection.
     * 
     * @param selection
     *            the selection to set
     */
    public void setSelection(IStructuredSelection selection) {
        this.selection = selection;
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        Composite viewer = createViewer(root);
        viewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite outputContent = createOutputContent(root);
        outputContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));

        Composite optionsContent = createOptionsContent(root);
        optionsContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));

        setPageComplete(false);
        setControl(root);

        refreshAssociatedProjectsCtrls();
    }

    /**
     * Store the details to the preference
     * 
     * @param key
     *            : the key to be stored
     * @param value
     *            : the value of the key above
     */
    private void storeDetailsToPreference(String key, String value) {
        getDialogSettings().put(key, value);
    }

    /**
     * Gets the value of the key specified from the preference.
     * 
     * @param key
     * @return
     */
    private String getDetailsFromPreference(String key) {
        String value = getDialogSettings().get(key);
        return value != null ? value : ""; //$NON-NLS-1$
    }

    /**
     * Persist values to the preferences.
     */
    public void persistValues() {
        if (archiveOutputTxt != null && !archiveOutputTxt.isDisposed()) {
            storeDetailsToPreference(PREV_DESTINATION_KEY,
                    archiveOutputTxt.getText());
            if (isCompressed) {
                storeDetailsToPreference(PREV_COMPRESS_PROJ_KEY, "true"); //$NON-NLS-1$
            } else {
                storeDetailsToPreference(PREV_COMPRESS_PROJ_KEY, "false"); //$NON-NLS-1$
            }
        }
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (navigatorService != null) {
            navigatorService.dispose();
        }
        super.dispose();
    }

    /**
     * Get the type of output file to create.
     * 
     * @return FileType.
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Check if the output archive should be compressed.
     * 
     * @return <code>true</code> if the output archive should be compressed.
     */
    public boolean isCompressed() {
        return isCompressed;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * Create the output file selection section.
     * 
     * @param parent
     * @return
     */
    private Composite createOutputContent(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout(3, false));

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.ProjectExportPage_toArchiveFile_label);

        archiveOutputTxt = new Text(root, SWT.BORDER);
        archiveOutputTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        /*
         * get the value of the Compress checkbox from the preference.
         */
        if (getDetailsFromPreference(PREV_COMPRESS_PROJ_KEY).equals("false")) { //$NON-NLS-1$
            isCompressed = false;
        }

        String destinationValue =
                getDetailsFromPreference(PREV_DESTINATION_KEY);
        int projectsExported = getSelectedProjects().size();
        if (!destinationValue.isEmpty() && !getSelectedProjects().isEmpty()
                && projectsExported == 1) {
            destinationValue =
                    updateOutputPath(destinationValue, getSelectedProjects()
                            .iterator().next());
        } else if (!destinationValue.isEmpty() && projectsExported != 1) {
            destinationValue = updateOutputPath(destinationValue);

        }

        archiveOutputTxt.setText(destinationValue);

        archiveOutputTxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                updateOutputFileType(outputPath);
            }
        });

        archiveOutputTxt.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                if (!archiveOutputTxt.getText().trim().equals(outputPath)) {
                    // Value has changed
                    validatePage();
                }
            }
        });

        Button browse = new Button(root, SWT.NONE);
        browse.setText("..."); //$NON-NLS-1$
        browse.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
                dlg.setText(Messages.ProjectExportPage_selectArchiveDlg_title);
                dlg.setFilterExtensions(new String[] { String
                        .format("*.%1$s;*.%2$s;*.%3$s;*.%4$s", //$NON-NLS-1$
                                ZIP_EXT,
                                TAR_EXT,
                                TAR_GZ_EXT,
                                TGZ_EXT) });
                String path = dlg.open();

                if (path != null) {
                    /*
                     * SCF-282 : On closing the dialog we should get the path
                     * depending upon the type of file the user selected.
                     */
                    archiveOutputTxt
                            .setText(getPathWithAppropriateExtension(path));
                    updateOutputFileType(outputPath);
                }
            }
        });

        return root;
    }

    /**
     * Returns the path with the appropriate file extension(extension that is
     * currently selected)
     * 
     * @param path
     * @return
     */
    private String getPathWithAppropriateExtension(String path) {
        if (fileType != null) {
            String fileExtension = getFileExtension(new Path(path));

            if (fileExtension != null) {
                /*
                 * If we already have an extension then it should be the one
                 * currently selected by the user. If both are the same then
                 * exit.
                 */
                if (fileType.toString().equalsIgnoreCase(fileExtension)) {
                    return path;
                }
                int lastIndexOf = path.lastIndexOf(fileExtension);
                path = path.substring(0, lastIndexOf - 1);
            }
            switch (fileType) {
            case ZIP:
                path = path + "." + ZIP_EXT; //$NON-NLS-1$
                break;

            case TAR:
                if (isCompressed) {
                    path = path + "." + TAR_GZ_EXT; //$NON-NLS-1$

                } else {
                    path = path + "." + TAR_EXT; //$NON-NLS-1$

                }
                break;
            }
        }
        return path;
    }

    /**
     * Update the file name in the given path with the name of the project.
     * 
     * @param currentPath
     * @param project
     * @return
     */
    private String updateOutputPath(String currentPath, IProject project) {
        /*
         * Update the output path according to the project selected
         */
        if (currentPath != null && !currentPath.isEmpty() && project != null) {
            /*
             * Rather than having unnecessary checks of comparing the project
             * and file extension, just replace the last segment with the
             * expected segment.
             */
            IPath path = new Path(currentPath);
            IPath append = path.removeLastSegments(1).append(project.getName());
            String fileExtension = getFileExtension(path);
            currentPath = append.toOSString() + "."; //$NON-NLS-1$
            if (fileExtension != null) {
                currentPath = currentPath + fileExtension;
            } else {
                /* If file extension is null, set it to zip. */
                currentPath = currentPath + ZIP_EXT;
            }
        }
        return currentPath;
    }

    /**
     * Gets the extension of the path passed. We wrote this method because
     * {@link Path#getFileExtension()} fails when we have the file extension as
     * <b>"filename.tar.gz"</b>
     * 
     * @param path
     * @return file extension else <code>null</code> if no file extension is
     *         specified.
     */
    private String getFileExtension(IPath path) {
        String lastSegment = path.lastSegment();

        if (lastSegment.endsWith("." + TAR_GZ_EXT)) { //$NON-NLS-1$
            return TAR_GZ_EXT;

        } else if (lastSegment.endsWith("." + TAR_EXT)) { //$NON-NLS-1$
            return TAR_EXT;

        } else if (lastSegment.endsWith("." + ZIP_EXT)) { //$NON-NLS-1$
            return ZIP_EXT;

        }
        return null;
    }

    /**
     * Removes the previous file name(xyz.zip) from the path in case no projects
     * are selected or more than one project is selected.
     * 
     * @param currentPath
     * @return
     */
    private String updateOutputPath(String currentPath) {
        IPath path = new Path(currentPath);
        IPath newPath = path.removeLastSegments(1).addTrailingSeparator();
        currentPath = newPath.toOSString();
        return currentPath;
    }

    private void validatePage() {
        boolean isPageComplete = true;

        setErrorMessage(null);
        setMessage(null);

        if (getSelection().length == 0) {
            setErrorMessage(Messages.ProjectExportPage_noProjectsSelected_error_shortdesc);
            isPageComplete = false;
        } else
            try {
                // Warning Message: archive output not set
                outputPath = archiveOutputTxt.getText().trim();
                if (outputPath.length() <= 0) {
                    setMessage(Messages.ProjectExportPage_enterDestinationArchiveFile_message);
                    isPageComplete = false;
                }

                // Warning Message: available but unselected projects exist that
                // are referenced by current selection
                if (!getUnselectedReferencedProjects().isEmpty()) {
                    setMessage(Messages.ProjectExportPage_availableReferencedProjectsUnselected_warning_shortdesc,
                            DialogPage.WARNING);
                    // Warning Message: available but unselected projects exist
                    // that reference the current selection
                } else if (!getUnselectedReferencingProjects().isEmpty()) {
                    setMessage(Messages.ProjectExportPage_availableReferencingProjectsUnselected_warning_shortdesc,
                            DialogPage.WARNING);
                    // Guidance message
                } else {
                    setMessage(MESSAGE);
                }
            } catch (CyclicDependencyException e) {
                setErrorMessage(Messages.ProjectExportPage_cyclicalReferencing_message);
                isPageComplete = false;
            }

        setPageComplete(isPageComplete);
    }

    /**
     * Update the UI with the output file type selection based on the output
     * file selected.
     * 
     * @param path
     */
    private void updateOutputFileType(String path) {
        if (path != null && path.length() > 0) {
            if (path.endsWith("." + TAR_EXT) || path.endsWith("." + TAR_GZ_EXT) //$NON-NLS-1$ //$NON-NLS-2$
                    || path.endsWith("." + TGZ_EXT)) { //$NON-NLS-1$
                enableTarOption();

                if (!isCompressed) {
                    /* Do this only if isCompressed is false. */
                    enableCompress(path.endsWith("." + TAR_GZ_EXT) //$NON-NLS-1$
                            || path.endsWith("." + TGZ_EXT)); //$NON-NLS-1$
                }

            } else {
                enableZipOption();
                enableCompress(isCompressed);
            }
        }
    }

    /**
     * Update the output file name extension based on the archive option
     * selected.
     */
    private void updateOutputFilePath() {
        if (outputPath != null && !outputPath.isEmpty()) {
            IPath path = new Path(outputPath);
            String fileName = path.lastSegment();
            String ext = ""; //$NON-NLS-1$
            String newExt = null;

            int idx = 0;
            String fileExtension = getFileExtension(path);
            if (fileExtension != null) {

                if (fileExtension.equals(TAR_GZ_EXT)) {
                    idx = fileName.lastIndexOf("." + TAR_GZ_EXT); //$NON-NLS-1$

                } else {
                    idx = fileName.lastIndexOf('.');

                }

            } else {
                /* file with no extension specified */
                idx = fileName.length() - 1;

            }

            if (idx > 0) {
                ext = fileName.substring(idx + 1);
            }
            /*
             * If the extension does not match the output selection then change
             * the extension
             */
            switch (fileType) {
            case ZIP:
                if (!ext.equals(ZIP_EXT)) {
                    newExt = ZIP_EXT;
                }
                break;

            case TAR:
                if (isCompressed) {
                    if (!ext.equals(TAR_GZ_EXT) || !ext.equals(TGZ_EXT)) {
                        newExt = TAR_GZ_EXT;
                    }
                } else {
                    if (!ext.equals(TAR_EXT)) {
                        newExt = TAR_EXT;
                    }
                }
                break;
            }

            if (newExt != null) {
                // Update the path with the new extension
                if (idx > 0) {
                    fileName = fileName.substring(0, idx + 1);
                }

                if (!fileName.endsWith(".")) {
                    /* if filename doesn't end with a dot "." , append it. */
                    fileName = fileName.concat("."); //$NON-NLS-1$
                }
                path =
                        path.removeLastSegments(1)
                                .append(fileName.concat(newExt));
                archiveOutputTxt.setText(path.toOSString());
            }
        }
    }

    /**
     * Create the options section.
     * 
     * @param parent
     * @return
     */
    private Composite createOptionsContent(Composite parent) {
        Group optionsGrp = new Group(parent, SWT.NONE);
        optionsGrp.setText(Messages.ProjectExportPage_options_label);
        optionsGrp.setLayout(new GridLayout(2, true));

        saveInZipBtn = new Button(optionsGrp, SWT.RADIO);
        saveInZipBtn.setText(Messages.ProjectExportPage_saveZip_button);
        saveInZipBtn.setSelection(fileType == FileType.ZIP);

        saveInTarBtn = new Button(optionsGrp, SWT.RADIO);
        saveInTarBtn.setText(Messages.ProjectExportPage_saveTar_button);
        GridData data = new GridData();
        data.horizontalIndent = 10;
        saveInTarBtn.setLayoutData(data);
        saveInTarBtn.setSelection(fileType == FileType.TAR);

        compressBtn = new Button(optionsGrp, SWT.CHECK);
        compressBtn.setText(Messages.ProjectExportPage_compress_button);
        data = new GridData();
        data.verticalIndent = 5;
        data.horizontalSpan = 2;
        compressBtn.setLayoutData(data);
        compressBtn.setSelection(isCompressed);

        SelectionListener listener = new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.widget instanceof Button) {
                    Button btn = (Button) e.widget;

                    /*
                     * For radio buttons then only interested in selection event
                     */
                    if ((btn == saveInTarBtn || btn == saveInZipBtn)
                            && btn.getSelection()) {
                        if (btn == saveInZipBtn) {
                            enableZipOption();
                        } else {
                            enableTarOption();
                        }
                        updateOutputFilePath();
                    } else if (btn == compressBtn) {
                        enableCompress(compressBtn.getSelection());
                        updateOutputFilePath();
                    }

                }
            }
        };

        saveInZipBtn.addSelectionListener(listener);
        saveInTarBtn.addSelectionListener(listener);
        compressBtn.addSelectionListener(listener);

        /*
         * If the default path has already been set then update the export
         * options accordingly
         */
        if (!archiveOutputTxt.getText().isEmpty()) {
            updateOutputFileType(archiveOutputTxt.getText());
        }

        return optionsGrp;
    }

    /**
     * Choose the zip output.
     */
    private void enableZipOption() {
        fileType = FileType.ZIP;
        saveInZipBtn.setSelection(true);
        saveInTarBtn.setSelection(false);
    }

    /**
     * Choose the tar output.
     */
    private void enableTarOption() {
        fileType = FileType.TAR;
        saveInZipBtn.setSelection(false);
        saveInTarBtn.setSelection(true);
    }

    /**
     * Change the state of the compress option.
     * 
     * @param enabled
     *            <code>true</code> to selection compression.
     */
    private void enableCompress(boolean enabled) {
        isCompressed = enabled;
        compressBtn.setSelection(enabled);
    }

    /**
     * Create the main project selection viewer.
     * 
     * @param root
     */
    private Composite createViewer(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        viewer = new CheckboxTreeViewer(root);
        viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        navigatorService =
                NavigatorContentServiceFactory.INSTANCE
                        .createContentService(ProjectExplorer.VIEW_ID, viewer);

        viewer.setLabelProvider(navigatorService.createCommonLabelProvider());
        viewer.setContentProvider(new ProjectContentProvider());
        viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
        viewer.addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof IProject) {
                    IProject project = (IProject) element;

                    return (project.isAccessible() && !project.getName()
                            .startsWith(".")); //$NON-NLS-1$
                }
                return false;
            }
        });

        if (selection != null) {
            List<IProject> projectsToSelect = new ArrayList<IProject>();

            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object next = iter.next();
                if (next instanceof IResource) {
                    IProject project = ((IResource) next).getProject();
                    if (project != null) {
                        projectsToSelect.add(project);
                    }
                }
            }
            if (!projectsToSelect.isEmpty()) {
                viewer.setCheckedElements(projectsToSelect.toArray());
            }
        }

        Composite selectGroup = new Composite(root, SWT.NONE);
        selectGroup.setLayout(new RowLayout());

        SelectionListener selectionListenerRefresh = new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshAssociatedProjectsCtrls();
            }

        };

        final Button selectAllBtn = new Button(selectGroup, SWT.NONE);
        selectAllBtn.setText(Messages.ProjectExportPage_selectAll_button);

        final Button deselectAllBtn = new Button(selectGroup, SWT.NONE);
        deselectAllBtn.setText(Messages.ProjectExportPage_deselectAll_button);

        selectReferncedProjectsBtn = new Button(selectGroup, SWT.NONE);
        selectReferncedProjectsBtn
                .setText(Messages.ProjectExportPage_selectReferenced_button);

        selectReferencingProjectsBtn = new Button(selectGroup, SWT.NONE);
        selectReferencingProjectsBtn
                .setText(Messages.ProjectExportPage_selectReferencing_button);

        SelectionListener listener = new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                viewer.setAllChecked(e.widget == selectAllBtn);
            }
        };

        selectAllBtn.addSelectionListener(listener);
        selectAllBtn.addSelectionListener(selectionListenerRefresh);
        deselectAllBtn.addSelectionListener(listener);
        deselectAllBtn.addSelectionListener(selectionListenerRefresh);

        selectReferncedProjectsBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                try {
                    Set<IProject> unselectedReferencedProjects =
                            getUnselectedReferencedProjects();
                    unselectedReferencedProjects.addAll(getSelectedProjects());
                    viewer.setCheckedElements(unselectedReferencedProjects
                            .toArray());
                } catch (CyclicDependencyException e1) {
                    // do nothing
                }

            }
        });
        selectReferncedProjectsBtn
                .addSelectionListener(selectionListenerRefresh);

        selectReferencingProjectsBtn
                .addSelectionListener(new SelectionAdapter() {

                    @Override
                    public void widgetSelected(SelectionEvent e) {

                        try {
                            Set<IProject> unselectedReferencingProjects =
                                    getUnselectedReferencingProjects();
                            unselectedReferencingProjects
                                    .addAll(getSelectedProjects());
                            viewer.setCheckedElements(unselectedReferencingProjects
                                    .toArray());
                        } catch (CyclicDependencyException e1) {
                            // do nothing
                        }
                    }
                });
        selectReferencingProjectsBtn
                .addSelectionListener(selectionListenerRefresh);

        viewer.addPostSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                refreshAssociatedProjectsCtrls();
            }

        });

        return root;
    }

    /**
     * Refresh those UI controls that should reflect the status of any
     * selected/unselected associated projects
     * 
     */
    private void refreshAssociatedProjectsCtrls() {

        try {
            boolean hasUnselected = false;

            hasUnselected = !getUnselectedReferencedProjects().isEmpty();
            selectReferncedProjectsBtn.setEnabled(hasUnselected);

            hasUnselected = !getUnselectedReferencingProjects().isEmpty();
            selectReferencingProjectsBtn.setEnabled(hasUnselected);
        } catch (CyclicDependencyException e) {
            // do nothing
        }

        validatePage();
    }

    /**
     * @return all available projects that are not selected but are referenced
     *         by selected projects
     * @throws CyclicDependencyException
     */
    private Set<IProject> getUnselectedReferencedProjects()
            throws CyclicDependencyException {
        return getUnselectedAssociatedProjects(false);
    }

    /**
     * @return all available projects that are not selected but are referencing
     *         the selected projects
     * @throws CyclicDependencyException
     */
    private Set<IProject> getUnselectedReferencingProjects()
            throws CyclicDependencyException {
        return getUnselectedAssociatedProjects(true);
    }

    /**
     * All available projects that are not selected but are associated to the
     * selected projects (either through their referencing or them being
     * referenced)
     * 
     * @param forReferencingProjects
     * @return
     * @throws CyclicDependencyException
     */
    private Set<IProject> getUnselectedAssociatedProjects(
            boolean forReferencingProjects) throws CyclicDependencyException {

        Set<IProject> associatedProjects = Collections.<IProject> emptySet();
        if (viewer != null) {
            Set<IProject> selectedProjects = getSelectedProjects();
            try {
                associatedProjects =
                        (forReferencingProjects) ? ProjectUtil2
                                .getReferencingProjectsHierarchies(selectedProjects)
                                : ProjectUtil2
                                        .getReferencedProjectsHierarchies(selectedProjects,
                                                false);
            } catch (CyclicDependencyException e) {
                String fmtMsg =
                        "Cyclic dependency found relating to one of the following projects selected: %s (%s)"; //$NON-NLS-1$                
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(fmtMsg,
                                        selectedProjects.toString(),
                                        e.getMessage()));
                throw e;
            }
            associatedProjects.removeAll(selectedProjects);
        }
        return associatedProjects;
    }

    /**
     * Get the selection from the viewer.
     * 
     * @param viewer
     * @return selection casted to <code>IProject</code>
     */
    private Set<IProject> getSelectedProjects() {

        Set<IProject> selectedProjects = new HashSet<IProject>();
        for (Object obj : getSelection()) {
            if (obj instanceof IProject)
                selectedProjects.add((IProject) obj);
        }
        return selectedProjects;
    }

    /**
     * Get the selection from the viewer.
     * 
     * @return
     */
    public Object[] getSelection() {
        return viewer.getCheckedElements();
    }

    private class ProjectContentProvider implements ITreeContentProvider {

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof IWorkspaceRoot) {
                return ((IWorkspaceRoot) inputElement).getProjects();
            }

            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         * 
         * @param parentElement
         * @return
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Object getParent(Object element) {
            if (element instanceof IProject) {
                return ((IProject) element).getParent();
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public boolean hasChildren(Object element) {
            return false;
        }

    }

}