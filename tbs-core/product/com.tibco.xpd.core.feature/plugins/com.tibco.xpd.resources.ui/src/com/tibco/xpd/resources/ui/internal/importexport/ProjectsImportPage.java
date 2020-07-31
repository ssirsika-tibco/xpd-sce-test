/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileManipulations;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.TarException;
import org.eclipse.ui.internal.wizards.datatransfer.TarFile;
import org.eclipse.ui.internal.wizards.datatransfer.TarLeveledStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * The WizardProjectsImportPage is the page that allows the user to import
 * projects from a particular location.
 * 
 * @since 3.5.2
 */
@SuppressWarnings("restriction")
public class ProjectsImportPage extends AbstractXpdWizardPage implements IOverwriteQuery {

    /*
     * COPY OF
     * org.eclipse.ui.internal.wizards.datatransfer.WizardProjectsImportPage
     * (with customisation)
     */

    private enum ProjectAssociationEnum {
        REFERENCED, REFERENCING
    };

    private Button selectReferncedProjectsBtn;

    private Button selectReferencingProjectsBtn;

    /**
     * The name of the folder containing metadata information for the workspace.
     */
    public static final String METADATA_FOLDER = ".metadata"; //$NON-NLS-1$

    /**
     * The import structure provider.
     */
    private ILeveledImportStructureProvider structureProvider;

    // dialog store id constants
    private final static String STORE_COPY_PROJECT_ID =
            "WizardProjectsImportPage.STORE_COPY_PROJECT_ID"; //$NON-NLS-1$

    private final static String STORE_ARCHIVE_SELECTED =
            "WizardProjectsImportPage.STORE_ARCHIVE_SELECTED"; //$NON-NLS-1$

    private Text directoryPathField;

    private CheckboxTreeViewer projectsList;

    private Button copyCheckbox;

    private boolean copyFiles = false;

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
    private static final String[] FILE_IMPORT_MASK = {
            "*.jar;*.zip;*.tar;*.tar.gz;*.tgz", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$

    // The last selected path to minimize searches
    private String lastPath;

    // The last time that the file or folder at the selected path was modified
    // to mimize searches
    private long lastModified;

    private Button refreshBtn;

    private Button deselectAllBtn;

    private Button selectAllBtn;

    private Button selectOnlyValidBtn;

    private Composite buttonsComposite;

    /**
     * Creates a new project creation wizard page.
     * 
     */
    public ProjectsImportPage() {
        this("wizardExternalProjectsPage"); //$NON-NLS-1$
    }

    /**
     * Create a new instance of the receiver.
     * 
     * @param pageName
     */
    public ProjectsImportPage(String pageName) {
        super(pageName);
        setPageComplete(false);
        setTitle(Messages.ProjectsImportPage_title);
        setDescription(Messages.ProjectsImportPage_selectDirForProject_shortdesc);
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
        workArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        Composite minSz = new Composite(workArea, SWT.NONE);
        GridLayout gl = new GridLayout();
        gl.marginLeft = gl.marginRight = gl.marginTop = gl.marginBottom = 0;
        minSz.setLayout(gl);

        GridData gd = new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);

        if (Display.getDefault() != null) {
            gd.widthHint = Math.min(800, (int) (Display.getDefault().getClientArea().width * 0.8));
            gd.heightHint = Math.min(500, (int) (Display.getDefault().getClientArea().height * 0.8));
        }

        minSz.setLayoutData(gd);

        createProjectsRoot(minSz);
        createProjectsList(minSz);
        createOptionsArea(minSz);
        restoreWidgetValues();
        Dialog.applyDialogFont(minSz);

    }

    /**
     * Create the area with the extra options.
     * 
     * @param workArea
     */
    private void createOptionsArea(Composite workArea) {
        Composite optionsGroup = new Composite(workArea, SWT.NONE);
        optionsGroup.setLayout(new GridLayout());
        optionsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        copyCheckbox = new Button(optionsGroup, SWT.CHECK);
        copyCheckbox
                .setText(Messages.ProjectsImportPage_copyProjectsIntoWorkspace_button);
        copyCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        copyCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                copyFiles = copyCheckbox.getSelection();
            }
        });
    }

    /**
     * Create the checkbox list for the found projects.
     * 
     * @param workArea
     */
    private void createProjectsList(Composite workArea) {

        Label title = new Label(workArea, SWT.NONE);
        title.setText(Messages.ProjectsImportPage_projects_label);

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
                setPageComplete(projectsList.getCheckedElements().length > 0);
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

        SelectionListener selectionListenerRefresh = new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshAssociatedProjectsCtrls(projectsList);
            }

        };

        selectOnlyValidBtn = new Button(buttonsComposite, SWT.PUSH);
        selectOnlyValidBtn.setText(Messages.ProjectsImportPage_SelectValidProjects_button);
        selectOnlyValidBtn.setBackground(new Color(null, new RGB(255, 120, 0)));
        selectOnlyValidBtn.setForeground(new Color(null, new RGB(255, 255, 255)));
        selectOnlyValidBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                /* Get the list of projects that can be imported not including those that require migration and do not have their dependencies already in workspace. */
                Collection<ProjectRecord> projectsThatAreSafeToImport = ProjectsImportMigrationValidator.getDefault()
                        .getProjectsThatAreSafeToImport(Arrays.asList(getValidProjects()));

                projectsList.setCheckedElements(projectsThatAreSafeToImport.toArray());
            }
        });
        selectOnlyValidBtn.addSelectionListener(selectionListenerRefresh);
        Dialog.applyDialogFont(selectOnlyValidBtn);
        showHideButton(selectOnlyValidBtn, false);

        selectAllBtn = new Button(buttonsComposite, SWT.PUSH);
        selectAllBtn.setText(Messages.ProjectsImportPage_selectAll_button);
        selectAllBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                projectsList.setCheckedElements(selectedProjects);
            }
        });
        selectAllBtn.addSelectionListener(selectionListenerRefresh);
        Dialog.applyDialogFont(selectAllBtn);
        showHideButton(selectAllBtn, true);

        deselectAllBtn = new Button(buttonsComposite, SWT.PUSH);
        deselectAllBtn.setText(Messages.ProjectsImportPage_deselectAll_button);
        deselectAllBtn.addSelectionListener(new SelectionAdapter() {
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
            }
        });
        deselectAllBtn.addSelectionListener(selectionListenerRefresh);
        Dialog.applyDialogFont(deselectAllBtn);
        showHideButton(deselectAllBtn, true);

        refreshBtn = new Button(buttonsComposite, SWT.PUSH);
        refreshBtn.setText(Messages.ProjectsImportPage_refresh_button);
        refreshBtn.addSelectionListener(new SelectionAdapter() {
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
        refreshBtn.addSelectionListener(selectionListenerRefresh);
        Dialog.applyDialogFont(refreshBtn);
        showHideButton(refreshBtn, true);

        // referenced projects button
        selectReferncedProjectsBtn = new Button(buttonsComposite, SWT.PUSH);
        selectReferncedProjectsBtn
                .setText(Messages.InputOutputSelectionWizardPage_selectReferenced_btnDesc);
        showHideButton(selectReferncedProjectsBtn, true);

        selectReferncedProjectsBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                try {
                    Set<ProjectRecord> projectSelection =
                            getAllAssociatedProjects(ProjectAssociationEnum.REFERENCED);
                    projectSelection.addAll(getSelectedProjects(projectsList));
                    projectsList.setCheckedElements(projectSelection.toArray());
                } catch (CyclicDependencyException e1) {
                    // do nothing;
                } finally {
                    refreshAssociatedProjectsCtrls(projectsList);
                }
            }

        });
        selectReferncedProjectsBtn
                .addSelectionListener(selectionListenerRefresh);

        selectReferencingProjectsBtn = new Button(buttonsComposite, SWT.NONE);
        selectReferencingProjectsBtn
                .setText(Messages.InputOutputSelectionWizardPage_selectReferencing_btnDesc);
        showHideButton(selectReferencingProjectsBtn, true);

        selectReferencingProjectsBtn
                .addSelectionListener(new SelectionAdapter() {

                    @Override
                    public void widgetSelected(SelectionEvent e) {

                        try {
                            Set<ProjectRecord> projectSelection =
                                    getAllAssociatedProjects(ProjectAssociationEnum.REFERENCING);
                            projectSelection
                                    .addAll(getSelectedProjects(projectsList));
                            projectsList.setCheckedElements(projectSelection
                                    .toArray());
                        } catch (CyclicDependencyException e1) {
                            // do nothing;
                        }
                    }

                });
        selectReferencingProjectsBtn
                .addSelectionListener(selectionListenerRefresh);

        projectsList.addCheckStateListener(new ICheckStateListener() {

            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                refreshAssociatedProjectsCtrls(projectsList);
            }

        });

        refreshAssociatedProjectsCtrls(projectsList);

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
     * Refresh those UI controls that should reflect the status any selected/unselected associated projects
     * 
     * @param viewer
     */
    private void refreshAssociatedProjectsCtrls(CheckboxTreeViewer viewer) {

        Set<ProjectRecord> selectedProjects = getSelectedProjects(viewer);
        Set<ProjectRecord> associatedProjects;

        try {
            associatedProjects =
                    getAllAssociatedProjects(ProjectAssociationEnum.REFERENCED);
            associatedProjects.removeAll(selectedProjects);
            selectReferncedProjectsBtn
                    .setEnabled(!associatedProjects.isEmpty());

            associatedProjects =
                    getAllAssociatedProjects(ProjectAssociationEnum.REFERENCING);
            associatedProjects.removeAll(selectedProjects);
            selectReferencingProjectsBtn.setEnabled(!associatedProjects
                    .isEmpty());

        } catch (CyclicDependencyException e) {
            // do nothing
        }

        setPageComplete(validatePage());

    }

    /**
     * 
     */
    private boolean validatePage() {

        boolean ret = false;

        setMessage(null);
        setErrorMessage(null);

        // Associated projects checks
        Set<ProjectRecord> selectedProjects = getSelectedProjects(projectsList);

        boolean showSelectValidOnlyButton = false;

        if (!selectedProjects.isEmpty()) {
            try {
                /*
                 * Sid ACE-???? Check if there are projects that require migration whose dependent projects are no
                 * already in the workspace (and any other migration considerations).
                 */
                IStatus validMigrationStatus = ProjectsImportMigrationValidator.getDefault()
                        .validateProjectImportMigration(selectedProjects);

                if (!validMigrationStatus.isOK()) {
                    setErrorMessage(validMigrationStatus.getMessage());

                    showSelectValidOnlyButton = true;

                } else {

                    Set<String> hangingReferencedProjects = new HashSet<String>();
                    Set<ProjectRecord> nonSelectedReferencedProjects =
                            getAllAssociatedProjects(ProjectAssociationEnum.REFERENCED, hangingReferencedProjects);

                    nonSelectedReferencedProjects.removeAll(selectedProjects);
                    if (!nonSelectedReferencedProjects.isEmpty()) {
                        setMessage(
                                Messages.InputOutputSelectionWizardPage_availableReferencedProjectsUnselected_warning_shortdesc,
                                DialogPage.WARNING);
                    } else if (!hangingReferencedProjects.isEmpty()) {
                        String msg =
                                Messages.InputOutputSelectionWizardPage_unavailableReferencedProjects_warning_shortdesc;

                        // Construct arg for issue msg
                        final String SEPARATOR = " / "; //$NON-NLS-1$
                        StringBuilder sb = new StringBuilder();
                        for (String token : hangingReferencedProjects) {
                            sb.append(token).append(SEPARATOR);
                        }
                        sb.setLength(sb.lastIndexOf(SEPARATOR));

                        setMessage(String.format(msg, sb.toString()), DialogPage.WARNING);
                    } else {
                        Set<ProjectRecord> nonSelectedReferencingProjects =
                                getAllAssociatedProjects(ProjectAssociationEnum.REFERENCING);
                        nonSelectedReferencingProjects.removeAll(selectedProjects);
                        if (!nonSelectedReferencingProjects.isEmpty()) {
                            setMessage(
                                    Messages.InputOutputSelectionWizardPage_availableReferencingProjectsUnselected_warning_shortdesc);
                        }
                    }
                    ret = true;
                }

            } catch (CyclicDependencyException e) {
                setErrorMessage(Messages.InputOutputSelectionWizardPage_cyclicalReferencing_message);
            }
        }

        showHideButton(selectOnlyValidBtn, showSelectValidOnlyButton);
        showHideButton(selectAllBtn, !showSelectValidOnlyButton);
        showHideButton(deselectAllBtn, true);
        showHideButton(refreshBtn, true);
        showHideButton(selectReferencingProjectsBtn, !showSelectValidOnlyButton);
        showHideButton(selectReferncedProjectsBtn, !showSelectValidOnlyButton);

        buttonsComposite.layout(true);
        
        return ret;
    }

    /**
     * @return Set of ProjectRecords corresponding to checked items in a
     *         CheckboxTreeViewer
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

    /**
     * Return all projects associated with those selected in the manner
     * specified by <code>projAssociation</code>. Ignores those projects that
     * are unavailable for import
     * 
     * @param projAssociation
     * @return set of projects associated with the selected projects, in the
     *         manner specified by <code>projAssociation</code>
     * @throws CyclicDependencyException
     */
    private Set<ProjectRecord> getAllAssociatedProjects(
            ProjectAssociationEnum projAssociation)
            throws CyclicDependencyException {

        return getAllAssociatedProjects(projAssociation, null);
    }

    /**
     * Return all projects associated with those selected in the manner
     * specified by <code>projAssociation</code>. Populates a collection with
     * all those projects found to be unavailable for import
     * 
     * @param projAssociation
     * @param hangingProjectReferences
     * @return
     * @throws CyclicDependencyException
     */
    private Set<ProjectRecord> getAllAssociatedProjects(
            ProjectAssociationEnum projAssociation,
            Set<String> hangingProjectReferences)
            throws CyclicDependencyException {

        Set<IProjectDescription> checkedProjects =
                getProjectDescriptions(getCheckedProjects());
        Map<String, ProjectRecord> availableProjects = getAvailableProjects();
        ProjectRecord[] availableProjectsArr =
                availableProjects.values()
                        .toArray(new ProjectRecord[availableProjects.size()]);

        // obtain associated projects
        Set<IProjectDescription> associatedProjects = null;
        try {
            switch (projAssociation) {
            case REFERENCED: {
                associatedProjects =
                        ProjectUtil2
                                .getReferencedProjectsHierarchies(checkedProjects,
                                        hangingProjectReferences,
                                        getProjectDescriptions(availableProjectsArr));
                break;
            }
            case REFERENCING: {
                associatedProjects =
                        ProjectUtil2
                                .getReferencingProjectsHierarchies(checkedProjects,
                                        getProjectDescriptions(availableProjectsArr));
                break;
            }
            }
        } catch (CyclicDependencyException e) {
            String fmtMsg =
                    "Cyclic dependency found relating to one of the following projects selected: %s (%s)"; //$NON-NLS-1$ 
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e, String.format(fmtMsg, checkedProjects.toString()));
        }

        // convert associated projects collection to ProjectRecords
        Set<ProjectRecord> ret = new HashSet<ProjectRecord>();
        if (associatedProjects != null) {
            Set<String> workspaceProjects = getWorkspaceProjectNames();
            for (IProjectDescription projDesc : associatedProjects) {
                if (!workspaceProjects.contains(projDesc.getName())) {
                    ret.add(availableProjects.get(projDesc.getName()));
                }
            }
        }

        return ret;
    }

    /**
     * @return collection of project names for all projects currently in
     *         workspace
     */
    private Set<String> getWorkspaceProjectNames() {
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();

        Set<String> projNames = new HashSet<String>();
        for (IProject proj : projects) {
            projNames.add(proj.getName());
        }

        return projNames;
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
                .setText(Messages.ProjectsImportPage_selectRootDir_button);

        // project location entry field
        this.directoryPathField = new Text(projectGroup, SWT.BORDER);

        this.directoryPathField.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

        // browse button
        browseDirectoriesButton = new Button(projectGroup, SWT.PUSH);
        browseDirectoriesButton
                .setText(Messages.ProjectsImportPage_browse_button);
        setButtonLayoutData(browseDirectoriesButton);

        // new project from archive radio button
        projectFromArchiveRadio = new Button(projectGroup, SWT.RADIO);
        projectFromArchiveRadio
                .setText(Messages.ProjectsImportPage_selectArchive_button);

        // project location entry field
        archivePathField = new Text(projectGroup, SWT.BORDER);

        archivePathField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        // browse button
        browseArchivesButton = new Button(projectGroup, SWT.PUSH);
        browseArchivesButton.setText(Messages.ProjectsImportPage_browse_button);
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
            copyCheckbox.setSelection(true);
            copyCheckbox.setEnabled(false);
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
            copyCheckbox.setEnabled(true);
            copyCheckbox.setSelection(copyFiles);
        }
    }

    /**
     * @return Set of <code>ProjectDescription</code>s extracted from an array
     *         of <code>ProjectDescription</code>s
     */
    private Set<IProjectDescription> getProjectDescriptions(
            ProjectRecord[] projects) {

        Set<IProjectDescription> ret = new HashSet<IProjectDescription>();

        for (ProjectRecord proj : projects)
            ret.add(proj.getProjectDescription());

        return ret;
    }

    /**
     * @return array of <code>ProjectRecord</code>s corresponding to checked
     *         item of the CheckboxTreeViewer
     */
    private ProjectRecord[] getCheckedProjects() {

        Object[] checkedElements = projectsList.getCheckedElements();
        ProjectRecord[] checkedProjects =
                new ProjectRecord[checkedElements.length];

        for (int i = 0; i < checkedElements.length; i++) {
            checkedProjects[i] = (ProjectRecord) checkedElements[i];
        }

        return checkedProjects;
    }

    /**
     * @return Map of <code>ProjectRecord</code>s corresponding to all items
     *         contained in the CheckboxTreeViewer. Items are keyed on their
     *         descriptions.
     */
    private Map<String, ProjectRecord> getAvailableProjects() {

        Map<String, ProjectRecord> ret = new HashMap<String, ProjectRecord>();
        for (ProjectRecord proj : selectedProjects) {
            String key = proj.getProjectDescription().getName();
            ret.put(key, proj);
        }

        return ret;
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
            setMessage(Messages.ProjectsImportPage_selectDirForProject_shortdesc);
            selectedProjects = new ProjectRecord[0];
            projectsList.refresh(true);
            projectsList.setCheckedElements(selectedProjects);
            setPageComplete(projectsList.getCheckedElements().length > 0);
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

                    monitor.beginTask(Messages.ProjectsImportPage_searchingForProjects_monitor_shortdesc,
                            100);
                    selectedProjects = new ProjectRecord[0];
                    Collection files = new ArrayList();
                    monitor.worked(10);
                    if (!dirSelected
                            && ArchiveFileManipulations.isTarFile(path)) {
                        TarFile sourceTarFile = getSpecifiedTarSourceFile(path);
                        if (sourceTarFile == null) {
                            return;
                        }

                        structureProvider =
                                new TarLeveledStructureProvider(sourceTarFile);
                        Object child = structureProvider.getRoot();

                        if (!collectProjectFilesFromProvider(files,
                                child,
                                0,
                                monitor)) {
                            return;
                        }
                        Iterator filesIterator = files.iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor.subTask(Messages.ProjectsImportPage_processingResults_monitor_shortdesc);
                        while (filesIterator.hasNext()) {
                            selectedProjects[index++] =
                                    (ProjectRecord) filesIterator.next();
                        }
                    } else if (!dirSelected
                            && ArchiveFileManipulations.isZipFile(path)) {
                        ZipFile sourceFile = getSpecifiedZipSourceFile(path);
                        if (sourceFile == null) {
                            return;
                        }
                        structureProvider =
                                new ZipLeveledStructureProvider(sourceFile);
                        Object child = structureProvider.getRoot();

                        if (!collectProjectFilesFromProvider(files,
                                child,
                                0,
                                monitor)) {
                            return;
                        }
                        Iterator filesIterator = files.iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor.subTask(Messages.ProjectsImportPage_processingResults_monitor_shortdesc);
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
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor.subTask(Messages.ProjectsImportPage_processingResults_monitor_shortdesc);

                        while (filesIterator.hasNext()) {
                            File file = (File) filesIterator.next();

                            String projectFilePath = file.getPath();
                            String configFilePath =
                                    projectFilePath.replace(IProjectDescription.DESCRIPTION_FILE_NAME, ".config"); //$NON-NLS-1$

                            selectedProjects[index] = new ProjectRecord(file, new File(configFilePath));
                            index++;
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
            setMessage(Messages.ProjectsImportPage_existingProjectsHidden_shortdesc,
                    WARNING);
        } else {
            setMessage(Messages.ProjectsImportPage_selectDirForProject_shortdesc);
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
            displayErrorDialog(Messages.ProjectsImportPage_notValidZip_error_shortdesc);
        } catch (IOException e) {
            displayErrorDialog(Messages.ProjectsImportPage_cannotReadSrcFile_error_shortdesc);
        }

        archivePathField.setFocus();
        return null;
    }

    /**
     * Answer a handle to the zip file currently specified as being the source.
     * Return null if this file does not exist or is not of valid format.
     */
    private TarFile getSpecifiedTarSourceFile(String fileName) {
        if (fileName.length() == 0) {
            return null;
        }

        try {
            return new TarFile(fileName);
        } catch (TarException e) {
            displayErrorDialog(Messages.ProjectsImportPage_notValidTar_error_shortdesc);
        } catch (IOException e) {
            displayErrorDialog(Messages.ProjectsImportPage_cannotReadSrcFile_error_shortdesc);
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
        return Messages.ProjectsImportPage_internalError_errDlg_title;
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
        monitor.subTask(String
                .format(Messages.ProjectsImportPage_checking_monitor_shortdesc,
                        directory.getPath()));
        File[] contents = directory.listFiles();
        if (contents == null)
            return false;

        // Initialize recursion guard for recursive symbolic links
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

        // first look for project description files
        final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
        for (int i = 0; i < contents.length; i++) {
            File file = contents[i];
            if (file.isFile() && file.getName().equals(dotProject)) {
                files.add(file);
                // don't search sub-directories since we can't have nested
                // projects
                return true;
            }
        }
        // no project description found, so recurse into sub-directories
        for (int i = 0; i < contents.length; i++) {
            if (contents[i].isDirectory()) {
                if (!contents[i].getName().equals(METADATA_FOLDER)) {
                    try {
                        String canonicalPath = contents[i].getCanonicalPath();
                        if (!directoriesVisited.add(canonicalPath)) {
                            // already been here --> do not recurse
                            continue;
                        }
                    } catch (IOException exception) {
                        StatusManager.getManager()
                                .handle(StatusUtil.newStatus(IStatus.ERROR,
                                        exception.getLocalizedMessage(),
                                        exception));

                    }
                    collectProjectFilesFromDirectory(files,
                            contents[i],
                            directoriesVisited,
                            monitor);
                }
            }
        }
        return true;
    }

    /**
     * Collect the list of .project files that are under directory into files.
     * 
     * @param files
     * @param monitor
     *            The monitor to report to
     * @return boolean <code>true</code> if the operation was completed.
     */
    private boolean collectProjectFilesFromProvider(Collection files,
            Object entry, int level, IProgressMonitor monitor) {

        if (monitor.isCanceled()) {
            return false;
        }
        monitor.subTask(String
                .format(Messages.ProjectsImportPage_checking_monitor_shortdesc,
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
                        monitor);
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
        dialog.setMessage(Messages.ProjectsImportPage_selectProjectRootDirDlg_title);

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
     * The archive browse button has been selected. Select the location.
     */
    protected void handleLocationArchiveButtonPressed() {

        FileDialog dialog = new FileDialog(archivePathField.getShell());
        dialog.setFilterExtensions(FILE_IMPORT_MASK);
        dialog.setText(Messages.ProjectsImportPage_selectArchiveDlg_title);

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
        final ProjectRecord[] selectedProjects = getCheckedProjects();

        IRunnableWithProgress op = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                final IWorkspace workspace = ResourcesPlugin.getWorkspace();

                /*
                 * Lock workspace during import and only broadcast workspace
                 * change at the end of the import
                 */
                try {
                    workspace.run(new ImportProjectsRunnable(selectedProjects),
                            workspace.getRoot(),
                            IWorkspace.AVOID_UPDATE,
                            monitor);
                } catch (CoreException e) {
                    throw new InvocationTargetException(e);
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
            String message =
                    Messages.ProjectsImportPage_problemsImportingProject_error_shortdesc;
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
        ArchiveFileManipulations.closeStructureProvider(structureProvider,
                getShell());
        return true;
    }

    /**
     * Performs clean-up if the user cancels the wizard without doing anything
     */
    public void performCancel() {
        ArchiveFileManipulations.closeStructureProvider(structureProvider,
                getShell());
    }

    /**
     * Create the project described in record. If it is successful return true.
     * 
     * @param record
     * @return boolean <code>true</code> if successful
     * @throws InterruptedException
     */
    private boolean createExistingProject(final ProjectRecord record,
            SubProgressMonitorEx monitor) throws InvocationTargetException,
            InterruptedException {

        try {
            String projectName = record.getProjectName();

            monitor.beginTask("", 1); //$NON-NLS-1$

            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            final IProject project =
                    workspace.getRoot().getProject(projectName);

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
                importFromArchive(record,
                        project,
                        SubProgressMonitorEx
                                .createSubTaskProgressMonitor(monitor, 1));

            } else {
                // Import from files.
                importFromFileSystem(record,
                        projectName,
                        workspace,
                        project,
                        SubProgressMonitorEx
                                .createSubTaskProgressMonitor(monitor, 1));
            }

        } finally {
            monitor.done();
        }

        return true;
    }

    /**
     * @param record
     * @param projectName
     * @param workspace
     * @param project
     * @param monitor
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private void importFromFileSystem(final ProjectRecord record,
            String projectName, final IWorkspace workspace,
            final IProject project, IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException {
        try {
            monitor.beginTask("", 100); //$NON-NLS-1$

            // import from file system
            File importSource = null;
            if (copyFiles) {
                // import project from location copying files - use default
                // project
                // location for this workspace
                URI locationURI = record.getProjectDescription().getLocationURI();
                // if location is null, project already exists in this location
                // or
                // some error condition occured.
                if (locationURI != null) {
                    importSource = new File(locationURI);
                    IProjectDescription desc =
                            workspace.newProjectDescription(projectName);
                    desc.setBuildSpec(record.getProjectDescription().getBuildSpec());
                    desc.setComment(record.getProjectDescription().getComment());
                    desc.setDynamicReferences(record.getProjectDescription()
                            .getDynamicReferences());
                    desc.setNatureIds(record.getProjectDescription().getNatureIds());
                    desc.setReferencedProjects(record.getProjectDescription()
                            .getReferencedProjects());
                    record.setProjectDescription(desc);
                }
            }

            project.create(record.getProjectDescription(),
                    SubProgressMonitorEx
                    .createSubTaskProgressMonitor(monitor, 20));

            project.open(IResource.BACKGROUND_REFRESH, SubProgressMonitorEx
                    .createSubTaskProgressMonitor(monitor, 40));

            // import operation to import project files if copy checkbox is
            // selected
            if (copyFiles && importSource != null) {
                List filesToImport =
                        FileSystemStructureProvider.INSTANCE
                                .getChildren(importSource);

                StudioImportOperation operation =
                        new StudioImportOperation(project.getFullPath(),
                                importSource,
                                FileSystemStructureProvider.INSTANCE, this,
                                filesToImport);

                operation.setContext(getShell());
                operation.setOverwriteResources(true); // need to overwrite
                // .project, .classpath
                // files
                operation.setCreateContainerStructure(false);
                operation.run(SubProgressMonitorEx
                        .createSubTaskProgressMonitor(monitor, 40));
            }

        } catch (CoreException e) {
            throw new InvocationTargetException(e);

        } finally {
            monitor.done();
        }
    }

    /**
     * @param record
     * @param project
     * @param monitor
     * @return
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private void importFromArchive(final ProjectRecord record,
            final IProject project, IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException {
        List fileSystemObjects = structureProvider.getChildren(record.getParent());
        structureProvider.setStrip(record.getLevel());
        StudioImportOperation operation =
                new StudioImportOperation(project.getFullPath(),
                        structureProvider.getRoot(), structureProvider, this,
                        fileSystemObjects);
        operation.setContext(getShell());
        operation.run(monitor);
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
                    String.format(Messages.ProjectsImportPage_overwriteExistingFile_question_shortdesc,
                            pathString);
        } else {
            messageString =
                    String.format(Messages.ProjectsImportPage_overwriteResource_question_shortdesc,
                            path.lastSegment(),
                            path.removeLastSegments(1).toOSString());
        }

        final MessageDialog dialog =
                new MessageDialog(getContainer().getShell(),
                        Messages.ProjectsImportPage_question_dialog_title,
                        null, messageString, MessageDialog.QUESTION,
                        new String[] { IDialogConstants.YES_LABEL,
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
            // checkbox
            copyFiles = settings.getBoolean(STORE_COPY_PROJECT_ID);
            copyCheckbox.setSelection(copyFiles);

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
            settings.put(STORE_COPY_PROJECT_ID, copyCheckbox.getSelection());

            settings.put(STORE_ARCHIVE_SELECTED,
                    projectFromArchiveRadio.getSelection());
        }
    }

    /**
     * Method used for test suite.
     * 
     * @return Button copy checkbox
     */
    public Button getCopyCheckbox() {
        return copyCheckbox;
    }

    /**
     * Import and clean up projects.
     * 
     */
    private class ImportProjectsRunnable implements IWorkspaceRunnable {

        private final Object[] selectedProjects;

        /*
         * We will run all the post import tasks AFTER importing all the
         * projects. HOWEVER, if the user cancels in the middle of the imports
         * then we should still perform the post import tasks on all of those we
         * have successfully imported.
         */
        ArrayList<IProject> importedProjects = new ArrayList<IProject>();

        final Map<String, ProjectRecord> projectsSet =
                new HashMap<String, ProjectRecord>();

        final Set<String> projectsPendingCreation = new HashSet<String>();

        /**
         * 
         */
        public ImportProjectsRunnable(ProjectRecord[] selectedProjects) {
            this.selectedProjects = selectedProjects;

            for (ProjectRecord rec : selectedProjects) {
                String projName = rec.getProjectDescription().getName();
                projectsSet.put(projName, rec);
                projectsPendingCreation.add(projName);
            }
        }

        /**
         * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param monitor
         * @throws CoreException
         */
        @Override
        public void run(IProgressMonitor monitor) throws CoreException {
            List<IStatus> errStatusList = new ArrayList<IStatus>();

            try {
                monitor.beginTask("", selectedProjects.length * 2); //$NON-NLS-1$

                /*
                 * One of the monitor's in the stack sets the subTask name to
                 * "Processing Results" which will linger until someone else
                 * sets a subtask name - so we will unset it here.
                 */
                monitor.subTask(""); //$NON-NLS-1$

                for (ProjectRecord projRec : projectsSet.values()) {
                    createProjectHierarchy(projRec, monitor);
                }

                /*
                 * Run the post import tasks AFTER ALL the imports.
                 * 
                 * This is because any one of the import tasks COULD cause the
                 * working copies to be loaded for the files in a project.
                 * 
                 * This will cause the files to be indexed.
                 * 
                 * If a dependent project hasn't been imported yet then all
                 * sorts of indexing could fail.
                 */
                PostImportUtil.getInstance()
                        .performPostImportTasks(importedProjects,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1));

            } catch (InvocationTargetException e) {
                IStatus status = null;
                // Log and continue with next project
                if (e.getTargetException() instanceof CoreException) {
                    status =
                            ((CoreException) e.getTargetException())
                                    .getStatus();
                }

                if (status == null) {
                    status =
                            new Status(IStatus.ERROR,
                                    XpdResourcesUIActivator.ID,
                                    e.getLocalizedMessage(),
                                    e.getTargetException() != null ? e
                                            .getTargetException() : e);
                }
                errStatusList.add(status);
            } catch (InterruptedException e) {
                throw new OperationCanceledException();
            } finally {
                monitor.done();
            }

            if (!errStatusList.isEmpty()) {
                /*
                 * Problems during import so report them
                 */
                throw new CoreException(
                        new MultiStatus(
                                XpdResourcesUIActivator.ID,
                                0,
                                errStatusList.toArray(new IStatus[errStatusList
                                        .size()]),
                                Messages.ProjectsImportPage_errorsDuringImport_error_shortdesc,
                                null));
            }

        }

        /**
         * @param projRec
         * @param monitor
         * @throws InterruptedException
         * @throws InvocationTargetException
         */
        private void createProjectHierarchy(ProjectRecord projRec,
                IProgressMonitor monitor) throws InvocationTargetException,
                InterruptedException {

            IProject[] references = projRec.getProjectDescription().getReferencedProjects();

            /* If user has cancelled then stop */
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            // create all descendant projects first using recursive calls
            if ((references != null) && (references.length > 0)) {
                for (IProject proj : references) {

                    if (projectsPendingCreation.contains(proj.getName())) {
                        createProjectHierarchy(projectsSet.get(proj.getName()),
                                monitor);
                    }
                }
            }

            // create current project
            if (projectsPendingCreation.contains(projRec.getProjectDescription().getName())) {

                IProject createdProj =
                        createProject(projRec,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1));
                if (createdProj != null) {
                    projectsPendingCreation.remove(projRec.getProjectDescription()
                            .getName());
                    importedProjects.add(createdProj);
                }
            }

        }

        /**
         * @param projectRecord
         * @param monitor
         * @throws InvocationTargetException
         * @throws InterruptedException
         * 
         * @return The project created.
         */
        protected IProject createProject(ProjectRecord projectRecord,
                SubProgressMonitorEx monitor) throws InvocationTargetException,
                InterruptedException {

            try {
                IProject project = null;
                monitor.beginTask(String
                        .format(Messages.ProjectsImportPage_importingProject_monitor_shortdesc,
                                projectRecord.getProjectName()),
                        1);

                if (createExistingProject(projectRecord,
                        SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1))) {

                    /*
                     * Run any post-import tasks to clean up the imported
                     * project (XPD-2901 - But ONLY on XPD Nature Projects!!).
                     */
                    project = getProject(projectRecord);
                }

                return project;

            } finally {
                monitor.done();
            }
        }

        /**
         * Get the project from the project record.
         * 
         * @param projectRecord
         * @return
         */
        private IProject getProject(ProjectRecord projectRecord) {
            String projectName = projectRecord.getProjectName();

            if (projectName != null) {
                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(projectRecord.getProjectName());
                if (project != null && project.isAccessible()) {
                    return project;
                }
            }
            return null;
        }
    }

}
