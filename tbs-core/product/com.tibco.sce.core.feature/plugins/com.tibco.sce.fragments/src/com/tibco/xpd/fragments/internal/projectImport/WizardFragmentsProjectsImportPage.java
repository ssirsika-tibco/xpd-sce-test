/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Red Hat, Inc - extensive changes to allow importing of Archive Files
 *     Philippe Ombredanne (pombredanne@nexb.com)
 *          - Bug 101180 [Import/Export] Import Existing Project into Workspace default widget is back button , should be text field
 *     Martin Oberhuber (martin.oberhuber@windriver.com)
 *          - Bug 187318[Wizards] "Import Existing Project" loops forever with cyclic symbolic links
 *     Remy Chi Jian Suen  (remy.suen@gmail.com)
 *          - Bug 210568 [Import/Export] [Import/Export] - Refresh button does not update list of projects
 *******************************************************************************/
package com.tibco.xpd.fragments.internal.projectImport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

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
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsImporter;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsImportExtension;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * The WizardProjectsImportPage is the page that allows the user to import
 * projects from a particular location.
 */
public class WizardFragmentsProjectsImportPage extends AbstractXpdWizardPage {

    /**
     * The name of the folder containing metadata information for the workspace.
     */
    private static final String METADATA_FOLDER = ".metadata"; //$NON-NLS-1$

    /**
     * The import structure provider.
     * 
     * @since 3.4
     */
    private ILeveledImportStructureProvider structureProvider;

    /**
     * Class declared public only for test suite.
     * 
     */
    public class ProjectRecord {
        File projectSystemFile;

        Object projectArchiveFile;

        String projectName;

        Object parent;

        int level;

        IProjectDescription description;

        /**
         * Create a record for a project based on the info in the file.
         * 
         * @param file
         */
        ProjectRecord(File file) {
            projectSystemFile = file;
            setProjectName();
        }

        /**
         * @param file
         *            The Object representing the .project file
         * @param parent
         *            The parent folder of the .project file
         * @param level
         *            The number of levels deep in the provider the file is
         */
        ProjectRecord(Object file, Object parent, int level) {
            this.projectArchiveFile = file;
            this.parent = parent;
            this.level = level;
            setProjectName();
        }

        /**
         * Set the name of the project based on the projectFile.
         */
        private void setProjectName() {
            try {
                if (projectArchiveFile != null) {
                    InputStream stream = structureProvider
                            .getContents(projectArchiveFile);

                    // If we can get a description pull the name from there
                    if (stream == null) {
                        if (projectArchiveFile instanceof ZipEntry) {
                            IPath path = new Path(
                                    ((ZipEntry) projectArchiveFile).getName());
                            projectName = path.segment(path.segmentCount() - 2);
                        } else if (projectArchiveFile instanceof TarEntry) {
                            IPath path = new Path(
                                    ((TarEntry) projectArchiveFile).getName());
                            projectName = path.segment(path.segmentCount() - 2);
                        }
                    } else {
                        description = ResourcesPlugin.getWorkspace()
                                .loadProjectDescription(stream);
                        stream.close();
                        projectName = description.getName();
                    }

                }

                // If we don't have the project name try again
                if (projectName == null) {
                    IPath path = new Path(projectSystemFile.getPath());
                    // if the file is in the default location, use the directory
                    // name as the project name
                    if (isDefaultLocation(path)) {
                        projectName = path.segment(path.segmentCount() - 2);
                        description = ResourcesPlugin.getWorkspace()
                                .newProjectDescription(projectName);
                    } else {
                        description = ResourcesPlugin.getWorkspace()
                                .loadProjectDescription(path);
                        projectName = description.getName();
                    }

                }
            } catch (CoreException e) {
                // no good couldn't get the name
            } catch (IOException e) {
                // no good couldn't get the name
            }
        }

        /**
         * Returns whether the given project description file path is in the
         * default location for a project
         * 
         * @param path
         *            The path to examine
         * @return Whether the given path is the default location for a project
         */
        private boolean isDefaultLocation(IPath path) {
            // The project description file must at least be within the project,
            // which is within the workspace location
            if (path.segmentCount() < 2)
                return false;
            return path.removeLastSegments(2).toFile().equals(
                    Platform.getLocation().toFile());
        }

        /**
         * Get the name of the project
         * 
         * @return String
         */
        public String getProjectName() {
            return projectName;
        }

        /**
         * Gets the label to be used when rendering this project record in the
         * UI.
         * 
         * @return String the label
         * @since 3.4
         */
        public String getProjectLabel() {
            if (description == null)
                return projectName;

            String path = projectSystemFile == null ? structureProvider
                    .getLabel(parent) : projectSystemFile.getParent();

            return String
                    .format(
                            Messages.WizardFragmentsProjectsImportPage_projectNameAndPath_label,
                            projectName, path);
        }
    }

    // dialog store id constants
    private final static String STORE_ARCHIVE_SELECTED = "WizardFragmentsProjectsImportPage.STORE_ARCHIVE_SELECTED"; //$NON-NLS-1$

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

    /** Mapping between project name and its importer(s) */
    private final Map<String, Set<FragmentsImportExtension>> projectNameImporterMap;
    /** Mapping between project nature and its importer(s) */
    private final Map<String, Set<FragmentsImportExtension>> projectNatureImporterMap;

    // constant from WizardArchiveFileResourceImportPage1
    private static final String[] FILE_IMPORT_MASK = {
            "*.jar;*.zip;*.tar;*.tar.gz;*.tgz", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$

    // The last selected path to minimize searches
    private String lastPath;
    // The last time that the file or folder at the selected path was modified
    // to mimize searches
    private long lastModified;

    /**
     * Creates a new project creation wizard page.
     * 
     */
    public WizardFragmentsProjectsImportPage() {
        this("wizardExternalFragmentsProjectsPage"); //$NON-NLS-1$
    }

    /**
     * Create a new instance of the receiver.
     * 
     * @param pageName
     */
    public WizardFragmentsProjectsImportPage(String pageName) {
        super(pageName);
        setPageComplete(false);
        setTitle(Messages.WizardFragmentsProjectsImportPage_title);
        setDescription(Messages.WizardFragmentsProjectsImportPage_shortdesc);

        projectNameImporterMap = new HashMap<String, Set<FragmentsImportExtension>>();
        projectNatureImporterMap = new HashMap<String, Set<FragmentsImportExtension>>();

        // Get list of project names that the imports are interested in
        FragmentsImportExtension[] importers = FragmentsExtensionHelper
                .getInstance().getAllImporters();

        if (importers != null) {
            for (FragmentsImportExtension importer : importers) {
                // Check if the project natures have been specified before
                // checking project names
                String[] names = importer.getProjectNatures();

                if (names != null && names.length > 0) {
                    for (String name : names) {
                        Set<FragmentsImportExtension> extensions = projectNatureImporterMap
                                .get(name);

                        if (extensions == null) {
                            extensions = new HashSet<FragmentsImportExtension>();
                            projectNatureImporterMap.put(name, extensions);
                        }
                        extensions.add(importer);
                    }
                } else {
                    // Get the project names
                    names = importer.getProjectNames();
                    if (names != null && names.length > 0) {
                        for (String name : names) {
                            Set<FragmentsImportExtension> extensions = projectNameImporterMap
                                    .get(name);

                            if (extensions == null) {
                                extensions = new HashSet<FragmentsImportExtension>();
                                projectNameImporterMap.put(name, extensions);
                            }

                            extensions.add(importer);
                        }
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {

        initializeDialogUnits(parent);

        Composite workArea = new Composite(parent, SWT.NONE);
        setControl(workArea);

        workArea.setLayout(new GridLayout());
        workArea.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        createProjectsRoot(workArea);
        createProjectsList(workArea);
        restoreWidgetValues();
        Dialog.applyDialogFont(workArea);

    }

    /**
     * Create the checkbox list for the found projects.
     * 
     * @param workArea
     */
    private void createProjectsList(Composite workArea) {

        Label title = new Label(workArea, SWT.NONE);
        title
                .setText(Messages.WizardFragmentsProjectsImportPage_projects_label);

        Composite listComposite = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        layout.makeColumnsEqualWidth = false;
        listComposite.setLayout(layout);

        listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

        projectsList = new CheckboxTreeViewer(listComposite, SWT.BORDER);
        GridData listData = new GridData(GridData.GRAB_HORIZONTAL
                | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        projectsList.getControl().setLayoutData(listData);

        projectsList.setContentProvider(new ITreeContentProvider() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java
             * .lang.Object)
             */
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
            public Object getParent(Object element) {
                return null;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.jface.viewers.IContentProvider#dispose()
             */
            public void dispose() {

            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
             * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
             */
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
        Composite buttonsComposite = new Composite(listComposite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        buttonsComposite.setLayout(layout);

        buttonsComposite.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_BEGINNING));

        Button selectAll = new Button(buttonsComposite, SWT.PUSH);
        selectAll
                .setText(Messages.WizardFragmentsProjectsImportPage_selectAll_button);
        selectAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                projectsList.setCheckedElements(selectedProjects);
                setPageComplete(projectsList.getCheckedElements().length > 0);
            }
        });
        Dialog.applyDialogFont(selectAll);
        setButtonLayoutData(selectAll);

        Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
        deselectAll
                .setText(Messages.WizardFragmentsProjectsImportPage_deselectAll_button2);
        deselectAll.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            public void widgetSelected(SelectionEvent e) {

                projectsList.setCheckedElements(new Object[0]);
                setPageComplete(false);
            }
        });
        Dialog.applyDialogFont(deselectAll);
        setButtonLayoutData(deselectAll);

        Button refresh = new Button(buttonsComposite, SWT.PUSH);
        refresh
                .setText(Messages.WizardFragmentsProjectsImportPage_refresh_button);
        refresh.addSelectionListener(new SelectionAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
             * .swt.events.SelectionEvent)
             */
            public void widgetSelected(SelectionEvent e) {
                if (projectFromDirectoryRadio.getSelection()) {
                    updateProjectsList(directoryPathField.getText().trim());
                } else {
                    updateProjectsList(archivePathField.getText().trim());
                }
            }
        });
        Dialog.applyDialogFont(refresh);
        setButtonLayoutData(refresh);
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
                .setText(Messages.WizardFragmentsProjectsImportPage_selectRootDirectory_label);

        // project location entry field
        this.directoryPathField = new Text(projectGroup, SWT.BORDER);

        this.directoryPathField.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

        // browse button
        browseDirectoriesButton = new Button(projectGroup, SWT.PUSH);
        browseDirectoriesButton
                .setText(Messages.WizardFragmentsProjectsImportPage_browse_button);
        setButtonLayoutData(browseDirectoriesButton);

        // new project from archive radio button
        projectFromArchiveRadio = new Button(projectGroup, SWT.RADIO);
        projectFromArchiveRadio
                .setText(Messages.WizardFragmentsProjectsImportPage_selectArchiveFile_label);

        // project location entry field
        archivePathField = new Text(projectGroup, SWT.BORDER);

        archivePathField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        // browse button
        browseArchivesButton = new Button(projectGroup, SWT.PUSH);
        browseArchivesButton
                .setText(Messages.WizardFragmentsProjectsImportPage_browse_button);
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
        }
    }

    /*
     * (non-Javadoc) Method declared on IDialogPage. Set the focus on path
     * fields when page becomes visible.
     */
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
            setMessage(Messages.WizardFragmentsProjectsImportPage_selectDirectoryToSearch_message);
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
        final boolean dirSelected = this.projectFromDirectoryRadio
                .getSelection();
        try {
            getContainer().run(true, true, new IRunnableWithProgress() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.jface.operation.IRunnableWithProgress#run(org
                 * .eclipse.core.runtime.IProgressMonitor)
                 */
                public void run(IProgressMonitor monitor) {
                    monitor
                            .beginTask(
                                    Messages.WizardFragmentsProjectsImportPage_searchingForProjects_monitor_label,
                                    100);
                    selectedProjects = new ProjectRecord[0];
                    monitor.worked(10);
                    if (!dirSelected
                            && ArchiveFileManipulations.isTarFile(path)) {
                        Collection<ProjectRecord> files = new ArrayList<ProjectRecord>();
                        TarFile sourceTarFile = getSpecifiedTarSourceFile(path);
                        if (sourceTarFile == null) {
                            return;
                        }

                        structureProvider = new TarLeveledStructureProvider(
                                sourceTarFile);
                        Object child = structureProvider.getRoot();

                        if (!collectProjectFilesFromProvider(files, child, 0,
                                monitor)) {
                            return;
                        }
                        Iterator<ProjectRecord> filesIterator = files
                                .iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor
                                .subTask(Messages.WizardFragmentsProjectsImportPage_ProcessingResults_monitor_label);
                        while (filesIterator.hasNext()) {
                            selectedProjects[index++] = filesIterator.next();
                        }
                    } else if (!dirSelected
                            && ArchiveFileManipulations.isZipFile(path)) {
                        ZipFile sourceFile = getSpecifiedZipSourceFile(path);
                        Collection<ProjectRecord> files = new ArrayList<ProjectRecord>();
                        if (sourceFile == null) {
                            return;
                        }
                        structureProvider = new ZipLeveledStructureProvider(
                                sourceFile);
                        Object child = structureProvider.getRoot();

                        if (!collectProjectFilesFromProvider(files, child, 0,
                                monitor)) {
                            return;
                        }
                        Iterator<ProjectRecord> filesIterator = files
                                .iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor
                                .subTask(Messages.WizardFragmentsProjectsImportPage_ProcessingResults_monitor_label);
                        while (filesIterator.hasNext()) {
                            selectedProjects[index++] = filesIterator.next();
                        }
                    } else if (dirSelected && directory.isDirectory()) {

                        Collection<File> files = new ArrayList<File>();
                        if (!collectProjectFilesFromDirectory(files, directory,
                                null, monitor)) {
                            return;
                        }
                        Iterator<File> filesIterator = files.iterator();
                        selectedProjects = new ProjectRecord[files.size()];
                        int index = 0;
                        monitor.worked(50);
                        monitor
                                .subTask(Messages.WizardFragmentsProjectsImportPage_ProcessingResults_monitor_label);
                        while (filesIterator.hasNext()) {
                            File file = (File) filesIterator.next();
                            selectedProjects[index] = new ProjectRecord(file);
                            index++;
                        }
                    } else {
                        monitor.worked(60);
                    }
                    monitor.subTask(""); //$NON-NLS-1$
                    monitor.done();
                }

            });
        } catch (InvocationTargetException e) {
            FragmentsActivator.getDefault().getLogger().error(e);
        } catch (InterruptedException e) {
            // Nothing to do if the user interrupts.
        }

        projectsList.refresh(true);
        projectsList.setCheckedElements(getValidProjects());
        if (getValidProjects().length == 0) {
            setMessage(
                    Messages.WizardFragmentsProjectsImportPage_noFragmentsProjectFound_message,
                    WARNING);
        } else {
            setMessage(Messages.WizardFragmentsProjectsImportPage_selectDirectoryToSearch_message);
        }
        setPageComplete(projectsList.getCheckedElements().length > 0);
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
            displayErrorDialog(Messages.WizardFragmentsProjectsImportPage_srcFileNotZipFile_error_message);
        } catch (IOException e) {
            displayErrorDialog(Messages.WizardFragmentsProjectsImportPage_CannotReadSrcFile_error_message);
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
            displayErrorDialog(Messages.WizardFragmentsProjectsImportPage_srcFileNotTarFile_error_message);
        } catch (IOException e) {
            displayErrorDialog(Messages.WizardFragmentsProjectsImportPage_CannotReadSrcFile_error_message);
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
                getErrorDialogTitle(), message);
    }

    /**
     * Get the title for an error dialog. Subclasses should override.
     */
    protected String getErrorDialogTitle() {
        return Messages.WizardFragmentsProjectsImportPage_InternalErrorDlg_title;
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
    private boolean collectProjectFilesFromDirectory(Collection<File> files,
            File directory, Set<String> directoriesVisited,
            IProgressMonitor monitor) {

        if (monitor.isCanceled()) {
            return false;
        }
        monitor
                .subTask(String
                        .format(
                                Messages.WizardFragmentsProjectsImportPage_Checking_monitor_label,
                                directory.getPath()));
        File[] contents = directory.listFiles();
        if (contents == null)
            return false;

        // Initialize recursion guard for recursive symbolic links
        if (directoriesVisited == null) {
            directoriesVisited = new HashSet<String>();
            try {
                directoriesVisited.add(directory.getCanonicalPath());
            } catch (IOException exception) {
                StatusManager.getManager().handle(
                        newStatus(IStatus.ERROR, exception
                                .getLocalizedMessage(), exception));
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
                        StatusManager.getManager().handle(
                                newStatus(IStatus.ERROR, exception
                                        .getLocalizedMessage(), exception));

                    }
                    collectProjectFilesFromDirectory(files, contents[i],
                            directoriesVisited, monitor);
                }
            }
        }
        return true;
    }

    /**
     * This method must not be called outside the workbench.
     * 
     * Utility method for creating status.
     * 
     * @param severity
     * @param message
     * @param exception
     * @return {@link IStatus}
     */
    private IStatus newStatus(int severity, String message, Throwable exception) {

        String statusMessage = message;
        if (message == null || message.trim().length() == 0) {
            if (exception == null) {
                throw new IllegalArgumentException();
            } else if (exception.getMessage() == null) {
                statusMessage = exception.toString();
            } else {
                statusMessage = exception.getMessage();
            }
        }

        return new Status(severity, FragmentsActivator.PLUGIN_ID, severity,
                statusMessage, exception);
    }

    /**
     * Collect the list of .project files that are under directory into files.
     * 
     * @param files
     * @param monitor
     *            The monitor to report to
     * @return boolean <code>true</code> if the operation was completed.
     */
    private boolean collectProjectFilesFromProvider(
            Collection<ProjectRecord> files, Object entry, int level,
            IProgressMonitor monitor) {

        if (monitor.isCanceled()) {
            return false;
        }
        monitor
                .subTask(String
                        .format(
                                Messages.WizardFragmentsProjectsImportPage_Checking_monitor_label,
                                structureProvider.getLabel(entry)));
        List<?> children = structureProvider.getChildren(entry);
        if (children == null) {
            children = new ArrayList<Object>(1);
        }
        Iterator<?> childrenEnum = children.iterator();
        while (childrenEnum.hasNext()) {
            Object child = childrenEnum.next();
            if (structureProvider.isFolder(child)) {
                collectProjectFilesFromProvider(files, child, level + 1,
                        monitor);
            }
            String elementLabel = structureProvider.getLabel(child);
            if (elementLabel.equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {
                files.add(new ProjectRecord(child, entry, level));
            }
        }
        return true;
    }

    /**
     * The browse button has been selected. Select the location.
     */
    protected void handleLocationDirectoryButtonPressed() {

        DirectoryDialog dialog = new DirectoryDialog(directoryPathField
                .getShell());
        dialog
                .setMessage(Messages.WizardFragmentsProjectsImportPage_SelectRootDirectory_dlg_message);

        String dirName = directoryPathField.getText().trim();
        if (dirName.length() == 0) {
            dirName = previouslyBrowsedDirectory;
        }

        if (dirName.length() == 0) {
            dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot()
                    .getLocation().toOSString());
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
        dialog
                .setText(Messages.WizardFragmentsProjectsImportPage_selectArchiveFile_dlg_message);

        String fileName = archivePathField.getText().trim();
        if (fileName.length() == 0) {
            fileName = previouslyBrowsedArchive;
        }

        if (fileName.length() == 0) {
            dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot()
                    .getLocation().toOSString());
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
     * Import the selected fragment projects.
     * 
     * @param monitor
     *            progress monitor
     * @throws InvocationTargetException
     */
    public void importProjects(IProgressMonitor monitor)
            throws InvocationTargetException {
        saveWidgetValues();
        final List<Object> selected = new ArrayList<Object>();

        // Run in UI thread as accessing UI control
        getShell().getDisplay().syncExec(new Runnable() {
            public void run() {
                Object[] checkedElements = projectsList.getCheckedElements();

                if (checkedElements != null) {
                    selected.addAll(Arrays.asList(checkedElements));
                }
            }
        });

        monitor
                .beginTask(
                        Messages.WizardFragmentsProjectsImportPage_importingProjects_monitor_label,
                        selected.size());
        try {
            for (Object sel : selected) {
                importExistingProject((ProjectRecord) sel,
                        new SubProgressMonitor(monitor, 1));

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
        } finally {
            if (structureProvider != null) {
                ArchiveFileManipulations.closeStructureProvider(
                        structureProvider, getShell());
                structureProvider = null;
            }
            monitor.done();
        }
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
     * @throws InvocationTargetException
     */
    private void importExistingProject(final ProjectRecord record,
            IProgressMonitor monitor) throws InvocationTargetException {
        String projectName = record.getProjectName();
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();

        Set<FragmentsImporter> importers = getImporters(record);
        if (importers != null && !importers.isEmpty()) {
            if (record.description == null) {
                // error case
                record.description = workspace
                        .newProjectDescription(projectName);
                IPath locationPath = new Path(record.projectSystemFile
                        .getAbsolutePath());

                // If it is under the root use the default location
                if (Platform.getLocation().isPrefixOf(locationPath)) {
                    record.description.setLocation(null);
                } else {
                    record.description.setLocation(locationPath);
                }
            } else {
                record.description.setName(projectName);
            }

            Object root = null;
            IImportStructureProvider importStructureProvider = null;

            if (record.projectArchiveFile != null) {
                // Import from archive
                structureProvider.setStrip(record.level);
                importStructureProvider = structureProvider;
                root = record.parent;
            } else {
                // Importing from file system
                URI locationURI = record.description.getLocationURI();
                if (locationURI != null) {
                    root = new File(locationURI);
                    importStructureProvider = FileSystemStructureProvider.INSTANCE;
                }
            }

            if (root != null && importStructureProvider != null) {
                monitor.beginTask("", importers.size()); //$NON-NLS-1$
                monitor
                        .setTaskName(String
                                .format(
                                        Messages.WizardFragmentsProjectsImportPage_importing_monitor_label,
                                        projectName));
                FragmentsManager manager = FragmentsManager.getInstance();

                // Process registered importers
                for (FragmentsImporter importer : importers) {
                    try {
                        // Get the root category for this provider
                        IFragmentCategory rootCategory = null;
                        String providerId = importer.getProviderId();

                        if (providerId != null) {
                            rootCategory = manager.getRootCategory(providerId);

                            if (rootCategory == null) {
                                throw new NullPointerException(
                                        String
                                                .format(
                                                        Messages.WizardFragmentsProjectsImportPage_RootCategoryOfProviderIsNull_error_message,
                                                        projectName, providerId));
                            }
                        }

                        importer.importProject(root, importStructureProvider,
                                rootCategory,
                                new SubProgressMonitor(monitor, 1));
                    } catch (CoreException e) {
                        throw new InvocationTargetException(e);
                    }
                }
            }
        }
        monitor.done();
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
     * Get the array of valid project records that can be imported from the
     * source workspace or archive, selected by the user. This will only select
     * projects that are recognised as fragments projects (identified by the
     * fragment importers).
     * 
     * @return ProjectRecord[] array of fragments projects that can be imported.
     */
    private ProjectRecord[] getValidProjects() {
        List<ProjectRecord> validProjects = new ArrayList<ProjectRecord>();

        for (ProjectRecord rec : selectedProjects) {
            Set<FragmentsImporter> importers = getImporters(rec);
            if (importers != null && !importers.isEmpty()) {
                validProjects.add(rec);
            }
        }
        return (ProjectRecord[]) validProjects
                .toArray(new ProjectRecord[validProjects.size()]);
    }

    /**
     * Get the importers that are registered for this project. This will check
     * the project natures and then the project name for importer matches.
     * 
     * @param rec
     *            project record
     * @return Set of {@link FragmentsImporter} for all importers registered
     *         with the either the nature or the name of this project.
     * @throws CoreException
     */
    private Set<FragmentsImporter> getImporters(ProjectRecord rec) {
        Set<FragmentsImporter> importers = null;

        if (rec != null) {
            importers = new HashSet<FragmentsImporter>();
            if (rec.description != null
                    && rec.description.getNatureIds() != null) {
                for (String id : rec.description.getNatureIds()) {
                    if (projectNatureImporterMap.containsKey(id)) {
                        Set<FragmentsImportExtension> extensions = projectNatureImporterMap
                                .get(id);

                        if (extensions != null) {
                            for (FragmentsImportExtension ext : extensions) {
                                FragmentsImporter importer = null;
                                try {
                                    importer = ext.getImporter();
                                } catch (CoreException e) {
                                    FragmentsActivator
                                            .getDefault()
                                            .getLogger()
                                            .error(
                                                    e,
                                                    String
                                                            .format(
                                                                    Messages.WizardFragmentsProjectsImportPage_CannotAccessImporter_error_message,
                                                                    ext.getId()));
                                }
                                if (importer != null) {
                                    importers.add(importer);
                                }
                            }
                        }
                    }
                }
            }

            if (projectNameImporterMap.containsKey(rec.getProjectName())) {
                Set<FragmentsImportExtension> extensions = projectNameImporterMap
                        .get(rec.getProjectName());

                if (extensions != null) {
                    for (FragmentsImportExtension ext : extensions) {
                        FragmentsImporter importer = null;
                        try {
                            importer = ext.getImporter();
                        } catch (CoreException e) {
                            FragmentsActivator
                                    .getDefault()
                                    .getLogger()
                                    .error(
                                            e,
                                            String
                                                    .format(
                                                            Messages.WizardFragmentsProjectsImportPage_CannotAccessImporter_error_message,
                                                            ext.getId()));
                        }
                        if (importer != null) {
                            importers.add(importer);
                        }
                    }
                }
            }
        }

        return importers;
    }

    /**
     * Use the dialog store to restore widget values to the values that they
     * held last time this wizard was used to completion.
     */
    private void restoreWidgetValues() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
            // radio selection
            boolean archiveSelected = settings
                    .getBoolean(STORE_ARCHIVE_SELECTED);
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
     */
    private void saveWidgetValues() {
        getShell().getDisplay().asyncExec(new Runnable() {

            public void run() {
                IDialogSettings settings = getDialogSettings();
                if (settings != null && !projectFromArchiveRadio.isDisposed()) {
                    settings.put(STORE_ARCHIVE_SELECTED,
                            projectFromArchiveRadio.getSelection());
                }
            }

        });
    }
}
