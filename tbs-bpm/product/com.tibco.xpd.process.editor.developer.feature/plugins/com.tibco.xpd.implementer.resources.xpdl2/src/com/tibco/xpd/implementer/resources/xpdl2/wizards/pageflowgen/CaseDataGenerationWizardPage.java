/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.dialogs.FileSelectionBrowserControl;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Wizard page that comes up for
 * {@link com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen.CaseDataProcessGenerationWizard}
 * that provides the user the option to select a xpdl file/process package
 * folder from the available projects where the new process for case data will
 * get generated
 * 
 * 
 * @author bharge
 * @since 15 Oct 2013
 */
public class CaseDataGenerationWizardPage extends AbstractXpdWizardPage
        implements Listener {

    private String fileExtension;

    List<Class> caseClassSelectionList;

    private FileSelectionBrowserControl fileSelectionControl;

    private Text fileNameText;

    private String fileName;

    private String newFileLabel;

    private IFile existingXpdlFile;

    boolean createNewPackage = false;

    private IProject project;

    IFolder processPackageFolder;

    /**
     * Constructor to create the wizard page for one or more case classes
     * 
     * @param pageName
     * @param selection
     * @param fileExtension
     * @param List
     *            <umlClass>
     */
    public CaseDataGenerationWizardPage(String pageName, String fileExtension,
            List<Class> caseClassSelectionList, String wizardPageTitle,
            String wizardPageDesc) {

        this.fileExtension = fileExtension;
        this.caseClassSelectionList = caseClassSelectionList;

        setTitle(wizardPageTitle);
        setDescription(wizardPageDesc);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.WIZARD_NEW_PAGEFLOW_PROCESS));
        setPageComplete(false);
    }

    /**
     * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {

        IFile bomFile =
                WorkingCopyUtil.getFile(caseClassSelectionList.iterator()
                        .next());
        String bomFileName = bomFile.getName();
        bomFileName = bomFileName.substring(0, bomFileName.indexOf(".")); //$NON-NLS-1$
        String xpdlFileName = bomFileName + "." + fileExtension; //$NON-NLS-1$
        /* Show default message when page first shown */
        setErrorMessage(null);
        setMessage(null);

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        ViewerFilter[] viewerFilters = createViewerFilters();

        /* Create the container selection group */
        fileSelectionControl = new FileSelectionBrowserControl();
        Composite ctrl = fileSelectionControl.createControl(container);
        ctrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        for (ViewerFilter filter : viewerFilters) {

            fileSelectionControl.addFilter(filter);
        }

        fileSelectionControl.setListener(new XpdlFileSelectionListener());

        Composite fileGroup = new Composite(container, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        fileGroup.setLayout(layout);
        fileGroup
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        /* Create the file input */
        Label lbl = new Label(fileGroup, SWT.NONE);
        lbl.setText(getNewFileLabel());

        fileNameText = new Text(fileGroup, SWT.BORDER);
        fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        fileNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                setFileName(fileNameText.getText());
                ProjectConfig projectConfig =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                if (null != projectConfig) {

                    List<IFolder> xpdlFolders =
                            projectConfig
                                    .getSpecialFolders()
                                    .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                    if (xpdlFolders != null && !xpdlFolders.isEmpty()) {

                        IFolder folder = xpdlFolders.get(0);
                        IFile xpdlFileForFileNameEntered =
                                folder.getFile(fileNameText.getText());
                        /*
                         * user must have entered xpdl file name that may not
                         * exist, so create new package
                         */
                        if (!xpdlFileForFileNameEntered.exists()) {

                            setCreateNewPackage(true);
                        }
                    }
                }
            }
        });

        /* Set given file name as default */
        setFileName(xpdlFileName);
        fileNameText.addListener(SWT.Modify, this);
        setControl(container);

        /* Set focus on the file name text control */
        fileNameText.setFocus();

    }

    /**
     * @return the project
     */
    public IProject getProject() {

        return project;
    }

    /**
     * Get the new file label to be set for the new file text control.
     * 
     * @return label. If none specified then default will be returned.
     */
    protected String getNewFileLabel() {

        if (newFileLabel == null) {

            newFileLabel =
                    Messages.NewXpdlFileInPageflowGenerationPage_fileName_label;
        }

        return newFileLabel;
    }

    /**
     * Set the file name. This will add the default file extension to the file
     * name if one does not exist.
     * 
     * @param fileName
     *            name of new file.
     */
    public void setFileName(String fileName) {

        if (fileName == null) {

            fileName = ""; //$NON-NLS-1$
        }

        /* Set the file extension if one is not set */
        if (fileName.length() > 0 && fileExtension != null
                && !fileName.endsWith("." + fileExtension)) { //$NON-NLS-1$

            fileName = fileName.concat("." + fileExtension); //$NON-NLS-1$
        }

        /* Update the file text control if present, otherwise store the value */
        if (fileNameText != null && !fileNameText.isDisposed()) {

            fileNameText.setText(fileName);
            fileNameText.setSelection(0, fileName.length());
            this.fileName = fileName;
        }
    }

    /**
     * 
     * @return xpdlFileName
     */
    public String getFileName() {

        return fileName;
    }

    /**
     * @return the processPackageFolder
     */
    public IFolder getProcessPackageFolder() {
        return processPackageFolder;
    }

    /**
     * create the filters on the tree viewer
     * 
     * @return ViewerFilter[]
     */
    private ViewerFilter[] createViewerFilters() {

        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

        filters.add(new XpdNatureProjectsOnly(false));
        filters.add(new NoFileContentFilter());
        filters.add(new SpecialFoldersOnlyFilter(Collections
                .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)));
        filters.add(new FileExtensionInclusionFilter(Collections
                .singleton(Xpdl2ResourcesConsts.XPDL_EXTENSION)));

        return filters.toArray(new ViewerFilter[filters.size()]);
    }

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * 
     * @param event
     */
    @Override
    public void handleEvent(Event event) {
        // do nothing
    }

    /**
     * @return the createNewPackage
     */
    public boolean isCreateNewPackage() {

        return createNewPackage;
    }

    /**
     * @param createNewPackage
     *            the createNewPackage to set
     */
    public void setCreateNewPackage(boolean createNewPackage) {
        this.createNewPackage = createNewPackage;
    }

    /**
     * @return the existingXpdlFile
     */
    public IFile getExistingXpdlFile() {
        return existingXpdlFile;
    }

    /**
     * @param existingXpdlFile
     *            the existingXpdlFile to set
     */
    public void setExistingXpdlFile(IFile xpdlFile) {
        this.existingXpdlFile = xpdlFile;
    }

    /**
     * XpdlFileSelection listener that will update the file name text when a
     * existing xpdl file is selected in the tree viewer
     * 
     * @author bharge
     * @since 8 Jan 2014
     */
    private class XpdlFileSelectionListener implements Listener {

        /**
         * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
         * 
         * @param event
         */
        @Override
        public void handleEvent(Event event) {

            if (event.widget instanceof Tree) {
                GovernanceStateService governanceStateService = new GovernanceStateService();

                TreeItem[] selection = ((Tree) event.widget).getSelection();
                if (selection != null && selection.length > 0) {

                    Object item = selection[0].getData();
                    
                    if (item instanceof IProject) {
                        /* if project is selected in the tree viewer */
                        project = (IProject) item;
                        setPageComplete(false);
                        setErrorMessage(String.format("Select a process package or folder"));

                    } else {
                        /* if existing xpdl file is selected */
                        if (item instanceof IFile) {
    
                            IFile xpdlFile = (IFile) item;
                            if (Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(xpdlFile
                                    .getFileExtension())) {
    
                                setFileName(xpdlFile.getName());
                                setCreateNewPackage(false);
                                setExistingXpdlFile(xpdlFile);
                                project = xpdlFile.getProject();
    
                            }
                        } else if (item instanceof SpecialFolder) {
                            /* if Process Packages special folder is selected */
                            SpecialFolder specialFolder = (SpecialFolder) item;
                            project = WorkingCopyUtil.getProjectFor(specialFolder);
                            IFolder folder = specialFolder.getFolder();
                            IFile xpdlFile = folder.getFile(fileNameText.getText());
    
                            if (!xpdlFile.exists()) {
    
                                setCreateNewPackage(true);
                            } else {
    
                                setCreateNewPackage(false);
                                setExistingXpdlFile(xpdlFile);
                            }
    
                            setFileName(xpdlFile.getName());
    
                        }

                        try {
                            if (project != null && !governanceStateService.isLockedForProduction(project)) {
                                setPageComplete(true);
                                setErrorMessage(null);

                            } else {
                                setPageComplete(false);

                                if (project != null) {
                                    setErrorMessage(String.format("Project '%1$s' is locked", project.getName()));
                                } else {
                                    setErrorMessage(String.format("Select a process package or folder"));
                                }
                            }
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }

                    }

                    setProcessPackagesFolder();

                }
            }
        }

        /**
         * set the process packages special folder for the selected project
         */
        protected void setProcessPackagesFolder() {

            if (null != project) {

                ProjectConfig projectConfig =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                List<IFolder> xpdlFolders =
                        projectConfig
                                .getSpecialFolders()
                                .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                if (xpdlFolders != null && !xpdlFolders.isEmpty()) {

                    processPackageFolder = xpdlFolders.get(0);
                }
            }
        }
    }

}
