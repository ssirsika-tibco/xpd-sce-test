/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.importexport.maa;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileManipulations;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.resources.ui.internal.importexport.ProjectRecord;
import com.tibco.xpd.resources.ui.internal.importexport.ProjectsImportMigrationValidator;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * wizard page used in importing MAA file. (This class is a replica of
 * org.eclipse.ui.internal.wizards.datatransfer.WizardProjectsImportPage.class
 * and has been customised to suit MAA import wizard)
 * 
 * @author bharge
 * 
 */
public class MAAImportWizardPage extends AbstractXpdWizardPage implements
        IOverwriteQuery {

    /**
     * The name of the folder containing metadata information for the workspace.
     */
    public static final String METADATA_FOLDER = ".metadata"; //$NON-NLS-1$

    /**
     * The import structure provider.
     * 
     * @since 3.4
     */
    // private ILeveledImportStructureProvider structureProvider;

    // dialog store id constants
    private final static String STORE_COPY_PROJECT_ID =
            "WizardProjectsImportPage.STORE_COPY_PROJECT_ID"; //$NON-NLS-1$

    private final static String STORE_ARCHIVE_SELECTED =
            "WizardProjectsImportPage.STORE_ARCHIVE_SELECTED"; //$NON-NLS-1$

    private Text directoryPathField;

    private CheckboxTreeViewer projectsList;

    private ProjectRecord[] selectedProjects = new ProjectRecord[0];

    // Keep track of the directory that we browsed to last time
    // the wizard was invoked.
    private static String previouslyBrowsedDirectory = ""; //$NON-NLS-1$

    // Keep track of the archive that we browsed to last time
    // the wizard was invoked.
    private static String previouslyBrowsedArchive = ""; //$NON-NLS-1$

    private Button projectFromDirectoryRadio;

    private Button projectFromArchiveRadio;

    private Text archivePathField;

    private Button browseDirectoriesButton;

    private Button browseArchivesButton;

    private IProject[] wsProjects;

    // constant from WizardArchiveFileResourceImportPage1
    private static final String[] FILE_IMPORT_MASK = { "*.maa", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$

    // The last selected path to minimize searches
    private String lastPath;

    // The last time that the file or folder at the selected path was modified
    // to mimize searches
    private long lastModified;

    private Button selectAll;

    private Button deselectAll;

    private Button refresh;

    private Composite buttonsComposite;

    private Button selectOnlyValidBtn;

    /**
     * Creates a new project creation wizard page.
     * 
     */
    public MAAImportWizardPage() {
        this("wizardExternalProjectsPage"); //$NON-NLS-1$
    }

    /**
     * Create a new instance of the receiver.
     * 
     * @param pageName
     */
    public MAAImportWizardPage(String pageName) {
        super(pageName);
        setPageComplete(false);
        setTitle(Messages.MaaImportWizardPage_Selection_title);
        setDescription(Messages.MaaImportWizardPage_ImportProjectsDescription);
    }

    private boolean validatePage() {
        setMessage(null);
        setErrorMessage(null);

        Set<ProjectRecord> checkedProjects = getSelectedProjects(projectsList);

        /*
         * Sid ACE-???? Check if there are projects that require migration whose dependent projects are no already in
         * the workspace (and any other migration considerations).
         */
        boolean showSelectValidOnlyButton = false;

        IStatus validMigrationStatus =
                ProjectsImportMigrationValidator.getDefault().validateProjectImportMigration(checkedProjects);

        if (!validMigrationStatus.isOK()) {
            setErrorMessage(validMigrationStatus.getMessage());
            showSelectValidOnlyButton = true;

        } else {

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> duplicates = new ArrayList<String>();

            for (Object object : projectsList.getCheckedElements()) {
                if (object instanceof ProjectRecord) {
                    ProjectRecord projectRecord = (ProjectRecord) object;
                    if (!names.contains(projectRecord.getProjectName())) {
                        names.add(projectRecord.getProjectName());
                    } else {
                        if (!duplicates.contains(projectRecord.getProjectName())) {
                            duplicates.add(projectRecord.getProjectName());
                        }
                    }
                }
            }

            for (Object object : projectsList.getCheckedElements()) {
                if (object instanceof ProjectRecord) {
                    ProjectRecord projectRecord = (ProjectRecord) object;
                    if (duplicates.contains(projectRecord.getProjectName())) {
                        setErrorMessage(Messages.MaaImportWizardPage_DuplicateFileError_message);
                        return false;
                    }
                }
            }
        }

        showHideButton(selectOnlyValidBtn, showSelectValidOnlyButton);
        showHideButton(selectAll, !showSelectValidOnlyButton);
        showHideButton(deselectAll, true);
        showHideButton(refresh, true);

        buttonsComposite.layout(true);
        return true;
    }

    /**
     * @return Set of ProjectRecords corresponding to checked items in a CheckboxTreeViewer
     */
    private Set<ProjectRecord> getSelectedProjects(CheckboxTreeViewer viewer) {

        Set<ProjectRecord> selectedProjects = new HashSet<ProjectRecord>();
        for (Object obj : viewer.getCheckedElements()) {
            if (obj instanceof ProjectRecord) {
                selectedProjects.add((ProjectRecord) obj);
            } else {
                // assert failure
            }
        }
        return selectedProjects;
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

        initializeDialogUnits(parent);

        Composite workArea = new Composite(parent, SWT.NONE);
        setControl(workArea);

        workArea.setLayout(new GridLayout());
        workArea.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        Composite minSz = new Composite(workArea, SWT.NONE);
        GridLayout gl = new GridLayout();
        gl.marginLeft = gl.marginRight = gl.marginTop = gl.marginBottom = 0;
        minSz.setLayout(gl);

        GridData gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);

        if (Display.getDefault() != null) {
            gd.minimumWidth = Math.min(800, (int) (Display.getDefault().getClientArea().width * 0.8));
            gd.minimumHeight = Math.min(500, (int) (Display.getDefault().getClientArea().height * 0.8));
        }

        minSz.setLayoutData(gd);

        createProjectsRoot(minSz);
        createProjectsList(minSz);
        restoreWidgetValues();
        Dialog.applyDialogFont(minSz);

    }

    /**
     * Create the checkbox list for the found projects.
     * 
     * @param workArea
     */
    private void createProjectsList(Composite workArea) {

        Label title = new Label(workArea, SWT.NONE);
        title.setText(Messages.MaaImportWizardPage_ProjectsListTitle);

        Composite listComposite = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        layout.makeColumnsEqualWidth = false;
        listComposite.setLayout(layout);

        listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

        projectsList = new CheckboxTreeViewer(listComposite, SWT.BORDER);
        GridData listData =
                new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL
                        | GridData.FILL_BOTH);
        projectsList.getControl().setLayoutData(listData);

        projectsList.setContentProvider(new ITreeContentProvider() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java
             * .lang.Object)
             */
            @Override
            public Object[] getChildren(Object parentElement) {
                return null;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IStructuredContentProvider#getElements
             * (java.lang.Object)
             */
            @Override
            public Object[] getElements(Object inputElement) {
                return getValidProjects();
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java
             * .lang.Object)
             */
            @Override
            public boolean hasChildren(Object element) {
                return false;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java
             * .lang.Object)
             */
            @Override
            public Object getParent(Object element) {
                return null;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.jface.viewers.IContentProvider#dispose()
             */
            @Override
            public void dispose() {

            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
             * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
             */
            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }

        });

        projectsList.setLabelProvider(new LabelProvider() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
             */
            @Override
            public String getText(Object element) {
                return ((ProjectRecord) element).getProjectLabel();
            }
        });

        projectsList.addCheckStateListener(new ICheckStateListener() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged
             * (org.eclipse.jface.viewers.CheckStateChangedEvent)
             */
            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                if (projectsList.getCheckedElements().length > 0) {
                    setPageComplete(validatePage());
                }
            }
        });

        projectsList.setInput(this);
        projectsList.setComparator(new ViewerComparator());
        createSelectionButtons(listComposite);
    }

    /**
     * Create the selection buttons in the listComposite.
     * 
     * @param listComposite
     */
    private void createSelectionButtons(Composite listComposite) {
        buttonsComposite = new Composite(listComposite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        buttonsComposite.setLayout(layout);

        buttonsComposite.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_BEGINNING));

        selectOnlyValidBtn = new Button(buttonsComposite, SWT.PUSH);
        selectOnlyValidBtn.setText(Messages.MAAImportWizardPage_SelectValidProjects_button);
        selectOnlyValidBtn.setBackground(new Color(null, new RGB(255, 120, 0)));
        selectOnlyValidBtn.setForeground(new Color(null, new RGB(255, 255, 255)));
        selectOnlyValidBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                /*
                 * Get the list of projects that can be imported not including those that require migration and do not
                 * have their dependencies already in workspace.
                 */
                Collection<ProjectRecord> projectsThatAreSafeToImport = ProjectsImportMigrationValidator.getDefault()
                        .getProjectsThatAreSafeToImport(Arrays.asList(getValidProjects()));

                projectsList.setCheckedElements(projectsThatAreSafeToImport.toArray());

                setPageComplete(validatePage());
            }
        });
        Dialog.applyDialogFont(selectOnlyValidBtn);
        showHideButton(selectOnlyValidBtn, false);

        selectAll = new Button(buttonsComposite, SWT.PUSH);
        selectAll.setText(Messages.MaaImportWizardPage_selectAll);
        selectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                projectsList.setCheckedElements(selectedProjects);
                if (projectsList.getCheckedElements().length > 0) {
                    setPageComplete(validatePage());
                }
            }
        });
        Dialog.applyDialogFont(selectAll);
        showHideButton(selectAll, true);

        deselectAll = new Button(buttonsComposite, SWT.PUSH);
        deselectAll.setText(Messages.MaaImportWizardPage_deselectAll);
        deselectAll.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {

                projectsList.setCheckedElements(new Object[0]);
                setPageComplete(false);
            }
        });
        Dialog.applyDialogFont(deselectAll);
        showHideButton(deselectAll, true);

        refresh = new Button(buttonsComposite, SWT.PUSH);
        refresh.setText(Messages.MaaImportWizardPage_refresh);
        refresh.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (projectFromDirectoryRadio.getSelection()) {
                    updateProjectsList(directoryPathField.getText().trim());
                } else {
                    updateProjectsList(archivePathField.getText().trim());
                }
            }
        });
        Dialog.applyDialogFont(refresh);
        showHideButton(refresh, true);
    }

    /**
     * @param selectOnlyValid
     * @param show
     */
    private void showHideButton(Button button, boolean show) {
        setButtonLayoutData(button);

        if (!show) {
            Object ld = button.getLayoutData();

            if (ld instanceof GridData) {
                ((GridData) ld).heightHint = 0;
                ((GridData) ld).verticalIndent = 0;
                ((GridData) ld).minimumHeight = 0;

            }

        }

        button.setVisible(show);
    }

    /**
     * Create the area where you select the root directory for the projects.
     * 
     * @param workArea
     *            Composite
     */
    private void createProjectsRoot(Composite workArea) {

        // project specification group
        Composite projectGroup = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        layout.makeColumnsEqualWidth = false;
        layout.marginWidth = 0;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // new project from directory radio button
        projectFromDirectoryRadio = new Button(projectGroup, SWT.RADIO);
        projectFromDirectoryRadio
                .setText(Messages.MaaImportWizardPage_RootSelectTitle);

        // project location entry field
        this.directoryPathField = new Text(projectGroup, SWT.BORDER);

        this.directoryPathField.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

        // browse button
        browseDirectoriesButton = new Button(projectGroup, SWT.PUSH);
        browseDirectoriesButton.setText(Messages.MaaImportWizardPage_browse);
        setButtonLayoutData(browseDirectoriesButton);

        // new project from archive radio button
        projectFromArchiveRadio = new Button(projectGroup, SWT.RADIO);
        projectFromArchiveRadio
                .setText(Messages.MaaImportWizardPage_ArchiveSelectTitle);

        // project location entry field
        archivePathField = new Text(projectGroup, SWT.BORDER);

        archivePathField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        // browse button
        browseArchivesButton = new Button(projectGroup, SWT.PUSH);
        browseArchivesButton.setText(Messages.MaaImportWizardPage_browse);
        setButtonLayoutData(browseArchivesButton);

        projectFromDirectoryRadio.setSelection(true);
        archivePathField.setEnabled(false);
        browseArchivesButton.setEnabled(false);

        browseDirectoriesButton.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.SelectionAdapter#widgetS
             * elected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleLocationDirectoryButtonPressed();
            }

        });

        browseArchivesButton.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleLocationArchiveButtonPressed();
            }

        });

        directoryPathField.addTraverseListener(new TraverseListener() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse
             * .swt.events.TraverseEvent)
             */
            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_RETURN) {
                    e.doit = false;
                    updateProjectsList(directoryPathField.getText().trim());
                }
            }

        });

        directoryPathField.addFocusListener(new FocusAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt
             * .events.FocusEvent)
             */
            @Override
            public void focusLost(org.eclipse.swt.events.FocusEvent e) {
                updateProjectsList(directoryPathField.getText().trim());
            }

        });

        archivePathField.addTraverseListener(new TraverseListener() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse
             * .swt.events.TraverseEvent)
             */
            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_RETURN) {
                    e.doit = false;
                    updateProjectsList(archivePathField.getText().trim());
                }
            }

        });

        archivePathField.addFocusListener(new FocusAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt
             * .events.FocusEvent)
             */
            @Override
            public void focusLost(org.eclipse.swt.events.FocusEvent e) {
                updateProjectsList(archivePathField.getText().trim());
            }
        });

        projectFromDirectoryRadio.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                directoryRadioSelected();
            }
        });

        projectFromArchiveRadio.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                archiveRadioSelected();
            }
        });
    }

    private void archiveRadioSelected() {
        if (projectFromArchiveRadio.getSelection()) {
            directoryPathField.setEnabled(false);
            browseDirectoriesButton.setEnabled(false);
            archivePathField.setEnabled(true);
            browseArchivesButton.setEnabled(true);
            updateProjectsList(archivePathField.getText());
            archivePathField.setFocus();
            setErrorMessage(null);
            setMessage(Messages.MaaImportWizardPage_Selection_longdesc);
        }
    }

    private void directoryRadioSelected() {
        if (projectFromDirectoryRadio.getSelection()) {
            directoryPathField.setEnabled(true);
            browseDirectoriesButton.setEnabled(true);
            archivePathField.setEnabled(false);
            browseArchivesButton.setEnabled(false);
            updateProjectsList(directoryPathField.getText());
            directoryPathField.setFocus();
            setMessage(Messages.MaaImportWizardPage_ImportProjectsDescription);
        }
    }

    /*
     * (non-Javadoc) Method declared on IDialogPage. Set the focus on path
     * fields when page becomes visible.
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && this.projectFromDirectoryRadio.getSelection()) {
            this.directoryPathField.setFocus();
        }
        if (visible && this.projectFromArchiveRadio.getSelection()) {
            this.archivePathField.setFocus();
        }
    }

    /**
     * Update the list of projects based on path. Method declared public only
     * for test suite.
     * 
     * @param path
     */
    public void updateProjectsList(final String path) {
        // on an empty path empty selectedProjects
        if (path == null || path.length() == 0) {
            selectedProjects = new ProjectRecord[0];
            projectsList.refresh(true);
            projectsList.setCheckedElements(selectedProjects);
            if (projectsList.getCheckedElements().length > 0) {
                setPageComplete(validatePage());
            }
            lastPath = path;
            return;
        }

        final File directory = new File(path);
        long modified = directory.lastModified();
        if (path.equals(lastPath) && lastModified == modified) {
            // since the file/folder was not modified and the path did not
            // change, no refreshing is required
            return;
        }

        lastPath = path;
        lastModified = modified;

        // We can't access the radio button from the inner class so get the
        // status beforehand
        final boolean dirSelected =
                this.projectFromDirectoryRadio.getSelection();
        try {
            getContainer().run(true, true, new IRunnableWithProgress() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.jface.operation.IRunnableWithProgress#run(org
                 * .eclipse.core.runtime.IProgressMonitor)
                 */
                @Override
                public void run(IProgressMonitor monitor) {

                    monitor.beginTask(Messages.MaaImportWizardPage_SearchingMessage,
                            100);
                    selectedProjects = new ProjectRecord[0];
                    Collection files = new ArrayList();
                    monitor.worked(10);
                    if (!dirSelected
                            && ArchiveFileManipulations.isZipFile(path)) {
                        ZipFile sourceFile = getSpecifiedZipSourceFile(path);
                        if (sourceFile == null) {
                            return;
                        }

                        ILeveledImportStructureProvider structureProvider =
                                new ZipLeveledStructureProvider(sourceFile);
                        Object child = structureProvider.getRoot();

                        if (!collectProjectFilesFromProvider(files,
                                child,
                                0,
                                monitor,
                                structureProvider)) {
                            return;
                        }
                        Iterator filesIterator = files.iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor.subTask(Messages.MaaImportWizardPage_ProcessingMessage);
                        while (filesIterator.hasNext()) {
                            selectedProjects[index++] =
                                    (ProjectRecord) filesIterator.next();
                        }
                    }

                    else if (dirSelected && directory.isDirectory()) {
                        if (!collectProjectFilesFromDirectory(files,
                                directory,
                                null,
                                monitor)) {
                            return;
                        }
                        Iterator filesIterator = files.iterator();

                        /**
                         * when root directory is selected to import maa files
                         * from, converting each maa file into a zip file
                         */
                        Collection zipFiles = new ArrayList();
                        while (filesIterator.hasNext()) {
                            File file = (File) filesIterator.next();
                            try {
                                ZipFile sourceFile = new ZipFile(file);
                                if (sourceFile == null) {
                                    return;
                                }
                                ILeveledImportStructureProvider structureProvider =
                                        new ZipLeveledStructureProvider(
                                                sourceFile);
                                Object child = structureProvider.getRoot();

                                if (!collectProjectFilesFromProvider(zipFiles,
                                        child,
                                        0,
                                        monitor,
                                        structureProvider)) {
                                    return;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Iterator zipFilesIterator = zipFiles.iterator();
                        selectedProjects = new ProjectRecord[zipFiles.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor.subTask(Messages.MaaImportWizardPage_ProcessingMessage);
                        while (zipFilesIterator.hasNext()) {
                            selectedProjects[index++] =
                                    (ProjectRecord) zipFilesIterator.next();
                        }

                    } else {
                        monitor.worked(60);
                    }
                    monitor.done();
                }

            });
        } catch (InvocationTargetException e) {
            IDEWorkbenchPlugin.log(e.getMessage(), e);
        } catch (InterruptedException e) {
            // Nothing to do if the user interrupts.
        }

        projectsList.refresh(true);
        projectsList.setCheckedElements(getValidProjects());
        if (getValidProjects().length < selectedProjects.length) {
            setMessage(Messages.MaaImportWizardPage_projectsInWorkspace,
                    WARNING);
        } else {
            setMessage(Messages.MaaImportWizardPage_ImportProjectsDescription);
        }

        setPageComplete(projectsList.getCheckedElements().length > 0 && validatePage());
    }

    /**
     * Answer a handle to the zip file currently specified as being the source.
     * Return null if this file does not exist or is not of valid format.
     */
    private ZipFile getSpecifiedZipSourceFile(String fileName) {
        if (fileName.length() == 0) {
            return null;
        }

        try {
            return new ZipFile(fileName);
        } catch (ZipException e) {
            displayErrorDialog(Messages.MaaImportWizardPage_maaBadFormat);
        } catch (IOException e) {
            displayErrorDialog(Messages.MaaImportWizardPage_maaCouldNotRead);
        }

        archivePathField.setFocus();
        return null;
    }

    /**
     * Display an error dialog with the specified message.
     * 
     * @param message
     *            the error message
     */
    protected void displayErrorDialog(String message) {
        MessageDialog.openError(getContainer().getShell(),
                getErrorDialogTitle(),
                message);
    }

    /**
     * Get the title for an error dialog. Subclasses should override.
     */
    protected String getErrorDialogTitle() {
        return Messages.MaaImportWizardPage_internalErrorTitle;
    }

    /**
     * Collect the list of .project files that are under directory into files.
     * 
     * @param files
     * @param directory
     * @param directoriesVisited
     *            Set of canonical paths of directories, used as recursion guard
     * @param monitor
     *            The monitor to report to
     * @return boolean <code>true</code> if the operation was completed.
     */
    private boolean collectProjectFilesFromDirectory(Collection files,
            File directory, Set directoriesVisited, IProgressMonitor monitor) {

        if (monitor.isCanceled()) {
            return false;
        }
        monitor.subTask(NLS.bind(Messages.MaaImportWizardPage_CheckingMessage,
                directory.getPath()));
        File[] contents = directory.listFiles();
        if (contents == null)
            return false;

        /** Initialise recursion guard for recursive symbolic links */
        if (directoriesVisited == null) {
            directoriesVisited = new HashSet();
            try {
                directoriesVisited.add(directory.getCanonicalPath());
            } catch (IOException exception) {
                StatusManager.getManager()
                        .handle(StatusUtil.newStatus(IStatus.ERROR,
                                exception.getLocalizedMessage(),
                                exception));
            }
        }

        /** first look for .maa files */
        final String dotProject = ".maa"; //$NON-NLS-1$
        for (int i = 0; i < contents.length; i++) {
            File file = contents[i];
            if (file.isFile() && file.getName().contains(dotProject)) {
                files.add(file);
            }
        }
        if (files.size() > 0) {
            return true;
        }
        return true;
    }

    /**
     * Collect the list of .project files that are under directory into files.
     * 
     * @param files
     * @param monitor
     *            The monitor to report to
     * @param structureProvider
     * @return boolean <code>true</code> if the operation was completed.
     */
    private boolean collectProjectFilesFromProvider(Collection files,
            Object entry, int level, IProgressMonitor monitor, ILeveledImportStructureProvider structureProvider) {

        if (monitor.isCanceled()) {
            return false;
        }
        monitor.subTask(NLS.bind(Messages.MaaImportWizardPage_CheckingMessage,
                structureProvider.getLabel(entry)));
        List children = structureProvider.getChildren(entry);
        if (children == null) {
            children = new ArrayList(1);
        }
        Iterator childrenEnum = children.iterator();
        while (childrenEnum.hasNext()) {
            Object child = childrenEnum.next();
            if (structureProvider.isFolder(child)) {
                collectProjectFilesFromProvider(files,
                        child,
                        level + 1,
                        monitor,
                        structureProvider);
            }
            String elementLabel = structureProvider.getLabel(child);
            if (elementLabel.equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {
                Object configFile = null;
                for (Object c : children) {
                    if (".config".equals(structureProvider.getLabel(c))) { //$NON-NLS-1$
                        configFile = c;
                    }
                }

                files.add(new ProjectRecord(structureProvider, child, entry, level, configFile));
            }
        }
        return true;
    }

    /**
     * The browse button has been selected. Select the location.
     */
    protected void handleLocationDirectoryButtonPressed() {

        DirectoryDialog dialog =
                new DirectoryDialog(directoryPathField.getShell());
        dialog.setMessage(Messages.MaaImportWizardPage_SelectDialogTitle);

        String dirName = directoryPathField.getText().trim();
        if (dirName.length() == 0) {
            dirName = previouslyBrowsedDirectory;
        }

        if (dirName.length() == 0) {
            dialog.setFilterPath(IDEWorkbenchPlugin.getPluginWorkspace()
                    .getRoot().getLocation().toOSString());
        } else {
            File path = new File(dirName);
            if (path.exists()) {
                dialog.setFilterPath(new Path(dirName).toOSString());
            }
        }

        String selectedDirectory = dialog.open();
        if (selectedDirectory != null) {
            previouslyBrowsedDirectory = selectedDirectory;
            directoryPathField.setText(previouslyBrowsedDirectory);
            updateProjectsList(selectedDirectory);
        }

    }

    /**
     * The browse button has been selected. Select the location.
     */
    protected void handleLocationArchiveButtonPressed() {

        FileDialog dialog = new FileDialog(archivePathField.getShell());
        dialog.setFilterExtensions(FILE_IMPORT_MASK);
        dialog.setText(Messages.MaaImportWizardPage_SelectArchiveDialogTitle);

        String fileName = archivePathField.getText().trim();
        if (fileName.length() == 0) {
            fileName = previouslyBrowsedArchive;
        }

        if (fileName.length() == 0) {
            dialog.setFilterPath(IDEWorkbenchPlugin.getPluginWorkspace()
                    .getRoot().getLocation().toOSString());
        } else {
            File path = new File(fileName).getParentFile();
            if (path != null && path.exists()) {
                dialog.setFilterPath(path.toString());
            }
        }

        String selectedArchive = dialog.open();
        if (selectedArchive != null) {
            previouslyBrowsedArchive = selectedArchive;
            archivePathField.setText(previouslyBrowsedArchive);
            updateProjectsList(selectedArchive);
        }

    }

    /**
     * Create the selected projects
     * 
     * @return boolean <code>true</code> if all project creations were
     *         successful.
     */
    public boolean createProjects() {
        saveWidgetValues();
        final Object[] selected = projectsList.getCheckedElements();
        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
            @Override
            protected void execute(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                try {
                    monitor.beginTask("", selected.length); //$NON-NLS-1$
                    if (monitor.isCanceled()) {
                        throw new OperationCanceledException();
                    }
                    for (int i = 0; i < selected.length; i++) {
                        createExistingProject((ProjectRecord) selected[i],
                                new SubProgressMonitor(monitor, 1));
                    }
                } finally {
                    monitor.done();
                }
            }
        };
        // run the new project creation operation
        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            // one of the steps resulted in a core exception
            Throwable t = e.getTargetException();
            String message = Messages.MaaImportWizardPage_errorMessage;
            IStatus status;
            if (t instanceof CoreException) {
                status = ((CoreException) t).getStatus();
            } else {
                status =
                        new Status(IStatus.ERROR,
                                IDEWorkbenchPlugin.IDE_WORKBENCH, 1, message, t);
            }
            ErrorDialog.openError(getShell(), message, null, status);
            return false;
        }

        for (ProjectRecord projectRecord : selectedProjects) {
            ArchiveFileManipulations.closeStructureProvider(projectRecord.getStructureProvider(), getShell());
        }
        return true;
    }

    /**
     * Performs clean-up if the user cancels the wizard without doing anything
     */
    public void performCancel() {
        for (ProjectRecord projectRecord : selectedProjects) {
            ArchiveFileManipulations.closeStructureProvider(projectRecord.getStructureProvider(), getShell());
        }
    }

    /**
     * Create the project described in record. If it is successful return true.
     * 
     * @param record
     * @return boolean <code>true</code> if successful
     * @throws InterruptedException
     */
    private boolean createExistingProject(final ProjectRecord record,
            IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException {
        String projectName = record.getProjectName();
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject project = workspace.getRoot().getProject(projectName);
        if (record.getProjectDescription() == null) {
            // error case
            record.setProjectDescription(workspace.newProjectDescription(projectName));
            IPath locationPath =
                    new Path(record.getProjectSystemFile().getAbsolutePath());
            // If it is under the root use the default location
            if (Platform.getLocation().isPrefixOf(locationPath)) {
                record.getProjectDescription().setLocation(null);
            } else {
                record.getProjectDescription().setLocation(locationPath);
            }
        } else {
            record.getProjectDescription().setName(projectName);
        }
        if (record.getProjectArchiveFile() != null) {
            // import from archive
            List fileSystemObjects =
                    record.getStructureProvider().getChildren(record.getParent());
            record.getStructureProvider().setStrip(record.getLevel());
            ImportOperation operation =
                    new ImportOperation(project.getFullPath(),
                            record.getStructureProvider().getRoot(), record.getStructureProvider(), this,
                            fileSystemObjects);
            operation.setContext(getShell());
            operation.run(monitor);

            /*
             * Run post-import tasks on the imported project
             */
            PostImportUtil.getInstance()
                    .performPostImportTasks(Collections.singleton(project),
                            SubProgressMonitorEx
                                    .createMainProgressMonitor(monitor, 1));

            return true;
        }

        return true;
    }

    /**
     * The <code>WizardDataTransfer</code> implementation of this
     * <code>IOverwriteQuery</code> method asks the user whether the existing
     * resource at the given path should be overwritten.
     * 
     * @param pathString
     * @return the user's reply: one of <code>"YES"</code>, <code>"NO"</code>,
     *         <code>"ALL"</code>, or <code>"CANCEL"</code>
     */
    @Override
    public String queryOverwrite(String pathString) {

        Path path = new Path(pathString);

        String messageString;
        // Break the message up if there is a file name and a directory
        // and there are at least 2 segments.
        if (path.getFileExtension() == null || path.segmentCount() < 2) {
            messageString =
                    NLS.bind(Messages.MaaImportWizardPage_existsQuestion,
                            pathString);
        } else {
            messageString =
                    NLS.bind(Messages.MaaImportWizardPage_overwriteNameAndPathQuestion,
                            path.lastSegment(),
                            path.removeLastSegments(1).toOSString());
        }

        final MessageDialog dialog =
                new MessageDialog(getContainer().getShell(),
                        IDEWorkbenchMessages.Question, null, messageString,
                        MessageDialog.QUESTION, new String[] {
                                IDialogConstants.YES_LABEL,
                                IDialogConstants.YES_TO_ALL_LABEL,
                                IDialogConstants.NO_LABEL,
                                IDialogConstants.NO_TO_ALL_LABEL,
                                IDialogConstants.CANCEL_LABEL }, 0);
        String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };
        // run in syncExec because callback is from an operation,
        // which is probably not running in the UI thread.
        getControl().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                dialog.open();
            }
        });
        return dialog.getReturnCode() < 0 ? CANCEL : response[dialog
                .getReturnCode()];
    }

    /**
     * Method used for test suite.
     * 
     * @return Button the Import from Directory RadioButton
     */
    public Button getProjectFromDirectoryRadio() {
        return projectFromDirectoryRadio;
    }

    /**
     * Method used for test suite.
     * 
     * @return CheckboxTreeViewer the viewer containing all the projects found
     */
    public CheckboxTreeViewer getProjectsList() {
        return projectsList;
    }

    /**
     * Retrieve all the projects in the current workspace.
     * 
     * @return IProject[] array of IProject in the current workspace
     */
    private IProject[] getProjectsInWorkspace() {
        if (wsProjects == null) {
            wsProjects =
                    IDEWorkbenchPlugin.getPluginWorkspace().getRoot()
                            .getProjects();
        }
        return wsProjects;
    }

    /**
     * Get the array of valid project records that can be imported from the
     * source workspace or archive, selected by the user. If a project with the
     * same name exists in both the source workspace and the current workspace,
     * it will not appear in the list of projects to import and thus cannot be
     * selected for import.
     * 
     * Method declared public for test suite.
     * 
     * @return ProjectRecord[] array of projects that can be imported into the
     *         workspace
     */
    public ProjectRecord[] getValidProjects() {
        List validProjects = new ArrayList();
        for (int i = 0; i < selectedProjects.length; i++) {
            if (!isProjectInWorkspace(selectedProjects[i].getProjectName())) {
                validProjects.add(selectedProjects[i]);
            }
        }
        return (ProjectRecord[]) validProjects
                .toArray(new ProjectRecord[validProjects.size()]);
    }

    /**
     * Determine if the project with the given name is in the current workspace.
     * 
     * @param projectName
     *            String the project name to check
     * @return boolean true if the project with the given name is in this
     *         workspace
     */
    private boolean isProjectInWorkspace(String projectName) {
        if (projectName == null) {
            return false;
        }
        IProject[] workspaceProjects = getProjectsInWorkspace();
        for (int i = 0; i < workspaceProjects.length; i++) {
            if (projectName.equals(workspaceProjects[i].getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Use the dialog store to restore widget values to the values that they
     * held last time this wizard was used to completion.
     * 
     * Method declared public only for use of tests.
     */
    public void restoreWidgetValues() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {

            // radio selection
            boolean archiveSelected =
                    settings.getBoolean(STORE_ARCHIVE_SELECTED);
            projectFromDirectoryRadio.setSelection(!archiveSelected);
            projectFromArchiveRadio.setSelection(archiveSelected);
            if (archiveSelected) {
                archiveRadioSelected();
            } else {
                directoryRadioSelected();
            }
        }
    }

    /**
     * Since Finish was pressed, write widget values to the dialog store so that
     * they will persist into the next invocation of this wizard page.
     * 
     * Method declared public only for use of tests.
     */
    public void saveWidgetValues() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {

            settings.put(STORE_ARCHIVE_SELECTED,
                    projectFromArchiveRadio.getSelection());
        }
    }

}
