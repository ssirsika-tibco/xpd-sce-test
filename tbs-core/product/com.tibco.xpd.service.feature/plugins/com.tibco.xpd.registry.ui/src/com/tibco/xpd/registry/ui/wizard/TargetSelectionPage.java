/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.wizard.WsdlImportWizard.IImportProjectProvider;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Page to allows selection of where to put the imported WSDL files, and in the
 * case of importing a single file: to choose a new name for the file.
 */
public class TargetSelectionPage extends AbstractXpdWizardPage {
    /** The dialog title identifier. */
    private static final String TARGET_TITLE =
            Messages.TargetSelectionPage_title;

    public static final char pathSeparator = '/';

    /** The number of columns on the destination page. */
    private static final int DESTINATION_PAGE_COLUMNS = 3;

    private Text destinationLocationText;

    private Text destinationFileNameText;

    private final ModifyListener modifyListener;

    /**
     * Field to hold the given destination location value until the control is
     * created
     */
    private String initialDestinationLocation;

    /**
     * The list of which URIs to import and what to call the imported files
     */
    private WsdlImportMapping[] wsdlImportMappings;

    /**
     * Flag to indicate whether the ui components have been created yet
     */
    private boolean initialised;

    /**
     * Where there is a multiple import we just drop the imported files in a
     * single location. This check box indicates what to do if the imported
     * files already exist.
     */
    private Button overwriteExistingResourcesCheckBox;

    /**
     * This is just a flag indicating what state the checkbox is in for the
     * above overwriteExistingResourcesCheckBox. Needed so we can check from the
     * wizard container what the user has selected.
     */
    private boolean overwriteExistingResources;

    public TargetSelectionPage() {
        super(TARGET_TITLE);
        setTitle(TARGET_TITLE);
        setDescription(Messages.TargetSelectionPage_longdesc);
        overwriteExistingResources = false;
        initialDestinationLocation = ""; //$NON-NLS-1$        
        wsdlImportMappings = new WsdlImportMapping[] {};
        modifyListener = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                Object source = modifyEvent.getSource();
                if (source == destinationFileNameText) {
                    if (wsdlImportMappings.length == 1) {
                        wsdlImportMappings[0]
                                .setDestinationFileName(destinationFileNameText
                                        .getText());
                    }
                }
                if (source == destinationLocationText) {
                    initialDestinationLocation =
                            destinationLocationText.getText();
                }
                setPageComplete(validatePage(false, true));
            }
        };
    }

    /**
     * Ensures that the mapping is always set so we can set the filename against
     * it - we don't want to get a null pointer later on
     */
    public void resetMappings() {
        if (wsdlImportMappings == null || wsdlImportMappings.length == 0) {
            wsdlImportMappings =
                    new WsdlImportMapping[] { new WsdlImportMapping(
                            destinationFileNameText.getText().trim(), "") };//$NON-NLS-1$
        }
        syncToMappings();
    }

    /**
     * @return the list of URIs and destination file names so that the import
     *         can be performed.
     */
    WsdlImportMapping[] getWsdlMappings() {
        return wsdlImportMappings;
    }

    /**
     * Tell this page which wsdls to import
     */
    void setWsdlMappings(WsdlImportMapping[] mappings) {
        wsdlImportMappings = mappings;
        if (initialised) {
            syncToMappings();
        }
    }

    private void syncToMappings() {
        if (wsdlImportMappings.length == 0) {
            destinationFileNameText.setText("");//$NON-NLS-1$
            destinationFileNameText.setEnabled(false);
        } else if (wsdlImportMappings.length == 1) {
            destinationFileNameText.setText(wsdlImportMappings[0]
                    .getDestinationFileName());
            destinationFileNameText.setEnabled(true);
        } else {
            destinationFileNameText
                    .setText(Messages.TargetSelectionPage_Import_multiple_files_short_description);
            destinationFileNameText.setEnabled(false);
        }
        // SCF-309: Some selections of resource might
        // have changed in prev page, need to reset the overwrite check box
        // enable/selection state, depending on the selection contains a
        // resource already existing in the target location or not.
        resetOverwriteCheckBox();
        setPageComplete(validatePage(false, true));
    }

    /**
     * Resets the Overwrite CheckBox, for the selected wsdl files. Enables the
     * overwrite checkbox if atleast one of the selected file exists in the
     * target location, disables and deselects otherwise.
     */
    private void resetOverwriteCheckBox() {

        // reset overwrite flag, will be recalculated later.
        overwriteExistingResources = false;

        String locationString = destinationLocationText.getText().trim();

        if (!locationString.isEmpty()) {

            boolean atleastOneDestinationFileExists = false;
            File locationFile = getTargetLocation(locationString);

            for (WsdlImportMapping wsdlImportMapping : wsdlImportMappings) {

                final File destinationFile =
                        new File(locationFile,
                                wsdlImportMapping.getDestinationFileName());

                if (destinationFile.exists()) {
                    atleastOneDestinationFileExists = true;
                }
            }
            if (atleastOneDestinationFileExists) {
                overwriteExistingResourcesCheckBox.setEnabled(true);
            } else {
                overwriteExistingResourcesCheckBox.setEnabled(false);
                overwriteExistingResourcesCheckBox.setSelection(false);
            }

        }
    }

    /**
     * @param location
     *            Sets the target location. Where there is a multiple import,
     *            all files have to be imported to the same location, and the
     *            names match the source.
     */
    public void setLocation(String location) {
        initialDestinationLocation = location;
    }

    /**
     * @param parent
     *            The parent composite.
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(DESTINATION_PAGE_COLUMNS, false);
        layout.verticalSpacing = 10;
        control.setLayout(layout);
        Label projectLabel = new Label(control, SWT.NONE);
        projectLabel.setText(Messages.TargetSelectionPage_FileName_location);
        destinationLocationText = new Text(control, SWT.BORDER | SWT.FLAT);
        Button projectBrowse = new Button(control, SWT.NONE);
        projectBrowse.setText(Messages.TargetSelectionPage_Browse_button);
        destinationLocationText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, false));
        // destinationLocationText.setText(initialDestinationLocation);
        // MR 35822 - begin
        IProject project = null;
        if (getWizard() instanceof IImportProjectProvider) {
            project = ((IImportProjectProvider) getWizard()).getProject();
        }
        SpecialFolder specialFolderOfKind =
                SpecialFolderUtil.getSpecialFolderOfKind(project,
                        WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);

        if (null != project && null != specialFolderOfKind) {
            destinationLocationText.setText(project.getName()
                    + TargetSelectionPage.pathSeparator
                    + specialFolderOfKind.getLocation());
        } else {
            destinationLocationText.setText(initialDestinationLocation);
        }
        // MR 35822 - end
        Label nameLabel = new Label(control, SWT.NONE);
        nameLabel.setText(Messages.TargetSelectionPage_FileName_label);
        destinationFileNameText = new Text(control, SWT.BORDER | SWT.FLAT);
        destinationFileNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, false));
        destinationFileNameText.setEnabled(false);

        destinationLocationText.addModifyListener(modifyListener);
        destinationFileNameText.addModifyListener(modifyListener);

        projectBrowse.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            @Override
            public void widgetSelected(SelectionEvent e) {

                ServicesFolderPicker picker =
                        new ServicesFolderPicker(getShell());
                picker.setTitle(Messages.TargetSelectionPage_FolderPicker_title);
                picker.setMessage(Messages.TargetSelectionPage_FolderSelection_shortdesc);
                picker.setInput(ResourcesPlugin.getWorkspace().getRoot());
                IPath outputFolderIPath =
                        ResourcesPlugin.getWorkspace().getRoot().getLocation()
                                .append(initialDestinationLocation);

                picker.setInitialSelection(outputFolderIPath);// + "/" +
                // location);

                if (picker.open() == ServicesFolderPicker.OK) {
                    Object result = picker.getFirstResult();

                    if (result instanceof SpecialFolder) {
                        IFolder folder = ((SpecialFolder) result).getFolder();
                        // set location with folder path
                        outputFolderIPath = folder.getFullPath();

                        destinationLocationText.setText(outputFolderIPath
                                .makeRelative().toString());
                    }
                }

                // final WorkspaceResourceDialog dialog = new
                // WorkspaceResourceDialog(
                // getShell(), new WorkbenchLabelProvider(),
                // new WorkbenchContentProvider());
                // dialog.setAllowMultiple(false);
                // dialog
                // .setTitle(Messages.TargetSelectionPage_FolderPicker_title);
                // dialog
                // .setMessage(Messages.
                // TargetSelectionPage_FolderSelection_shortdesc);
                // dialog.setShowNewFolderControl(true);
                // dialog.setValidator(new ISelectionStatusValidator() {
                // public IStatus validate(Object[] selection) {
                // dialog.validate(selection);
                // IStatus status;
                // if (selection.length == 1
                // && selection[0] instanceof IContainer) {
                // status = new Status(Status.OK, Activator.PLUGIN_ID,
                //									Status.OK, "", null); //$NON-NLS-1$
                // } else {
                // status = new Status(
                // Status.ERROR,
                // Activator.PLUGIN_ID,
                // Status.ERROR,
                // Messages.TargetSelectionPage_FolderSelectionError_message,
                // null);
                // }
                // return status;
                // }
                // });
                // dialog.loadContents();
                // if (dialog.open() == Window.OK) {
                // IContainer[] folders = dialog.getSelectedContainers();
                // if (folders.length == 1) {
                // destinationLocationText.setText(folders[0]
                // .getFullPath().toString());
                // }
                // }
            }
        });

        overwriteExistingResourcesCheckBox = new Button(control, SWT.CHECK);
        overwriteExistingResourcesCheckBox.setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, false, false, 2, 1));
        overwriteExistingResourcesCheckBox
                .setText(Messages.TargetSelectionPage_Overwrite_Existing_Resources_label);
        overwriteExistingResourcesCheckBox
                .addSelectionListener(new SelectionListener() {

                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        setPageComplete(validatePage(false, true));
                    }

                    @Override
                    public void widgetDefaultSelected(SelectionEvent e) {
                        // do nothing
                    }

                });

        setControl(control);
        initialised = true;
        if (wsdlImportMappings != null) {
            syncToMappings();
        }
        setPageComplete(validatePage(true, false));
    }

    /**
     * @return true if ok to proceed
     */
    private boolean validatePage(final boolean setFocus,
            final boolean showMessage) {
        if (!initialised) {
            return false;
        }

        setMessage(null);
        setErrorMessage(null);

        // empty location
        final String locationString = destinationLocationText.getText().trim();
        if (locationString.length() == 0) {
            if (showMessage) {
                setErrorMessage(Messages.TargetSelectionPage_EmptyLocation_longdesc);
            }
            if (setFocus) {
                destinationLocationText.setFocus();
            }
            return false;
        }

        File locationFile = getTargetLocation(locationString);
        if ((locationFile == null) || (!locationFile.exists())) {
            if (showMessage) {
                setErrorMessage(Messages.TargetSelectionPage_Location_Does_Not_Exist_error_message);
            }
            if (setFocus) {
                destinationLocationText.setFocus();
            }
            return false;
        }
        // location is not a directory
        if (!locationFile.isDirectory()) {
            if (showMessage) {
                setErrorMessage(Messages.TargetSelectionPage_Location_is_not_a_folder_error_message);
            }
            if (setFocus) {
                destinationLocationText.setFocus();
            }
            return false;
        }

        // location is not writable
        if (!locationFile.canWrite()) {
            if (showMessage) {
                setErrorMessage(Messages.TargetSelectionPage_Cannot_Write_to_location_error_message);
            }
            if (setFocus) {
                destinationLocationText.setFocus();
            }
            return false;
        }

        // check if the location contains Service Descriptors Special Folder.
        IPath path = new Path(locationString);
        IResource resource =
                ResourcesPlugin.getWorkspace().getRoot().findMember(path);
        if (resource instanceof IProject) {
            if (showMessage) {
                setErrorMessage(Messages.TargetSelectionPage_FolderSelectionError_message);
            }
            return false;
        }

        int countOfValidUrls = 0;
        for (WsdlImportMapping thisWsdlImportMapping : wsdlImportMappings) {
            if (thisWsdlImportMapping.getSourceUrl() != null) {
                countOfValidUrls++;
            }
        }

        // there are no files to import
        if (countOfValidUrls == 0) {
            // We always show this message regardless of the state of the
            // showMessage flag
            // because there are no controls on this page which the user can
            // change to
            // rectify the problem.

            if (wsdlImportMappings.length == 0) {
                setErrorMessage(Messages.TargetSelectionPage_There_are_no_wsdl_files_to_import_error_message);
            } else if (wsdlImportMappings.length == 1) {
                setErrorMessage(Messages.TargetSelectionPage_The_wsdl_url_to_the_service_cannot_be_obtained_error_message);
            } else {
                setErrorMessage(Messages.TargetSelectionPage_The_wsdl_url_to_none_of_the_services_can_be_obtained);
            }
            return false;
        }

        // there are some null urls in the list
        if (countOfValidUrls != wsdlImportMappings.length) {
            setMessage(Messages.TargetSelectionPage_A_wsdl_url_could_not_be_found_for_some_of_the_services,
                    WARNING);
        }

        // a file already exists and overwrite is not checked
        if (!overwriteExistingResourcesCheckBox.getSelection()) {
            for (WsdlImportMapping wsdlImportMapping : wsdlImportMappings) {
                final File destinationFile =
                        new File(locationFile,
                                wsdlImportMapping.getDestinationFileName());

                if (destinationFile.exists()) {
                    // A file with same name might exist in the destination
                    // folder,
                    // hence let user choose to override the same.
                    overwriteExistingResourcesCheckBox.setEnabled(true);
                    if (showMessage) {
                        setErrorMessage(Messages.TargetSelectionPage_Destination_files_already_exist_error_message);
                    } else {
                        setMessage(Messages.TargetSelectionPage_Destination_files_already_exist_error_message,
                                WARNING);
                    }
                    return false;
                }
            }
        } else {
            overwriteExistingResources = true;
        }

        // a file exists which cannot be overwritten
        for (WsdlImportMapping wsdlImportMapping : wsdlImportMappings) {
            final File destinationFile =
                    new File(locationFile,
                            wsdlImportMapping.getDestinationFileName());

            // SCF-309: Importing from URL/File should show Warning message if
            // the chosen file name duplicates another WSDL's file name

            IResource sameNamedWsdl =
                    getSameNameWsdlInScope(wsdlImportMapping, locationFile);
            if (sameNamedWsdl != null) {

                setMessage(String.format(Messages.TargetSelectionPage_Same_Named_Wsdl_Exits,
                        sameNamedWsdl.getName(),
                        sameNamedWsdl.getProject().getName()),
                        WARNING);
                // Disable Override checkbox , as the duplicate file exists in
                // some other folder, and not the destination folder.
                overwriteExistingResourcesCheckBox.setEnabled(false);
            }

            if (!destinationFile.exists()) {
                continue;
            }
            // SCF-309: Enabling the Check Box Here ,
            // can again disable the check
            // box when the next file in the list exists in referenced folder,
            // at line no 503.
            if ((!destinationFile.canWrite()) || destinationFile.isDirectory()) {
                if (showMessage) {
                    setErrorMessage(Messages.TargetSelectionPage_Destination_Files_Cannot_be_overwritten_error_message);
                }
                return false;
            }
        }
        // SCF-309: Let User choose to deselect
        // overwrite option , if user has selected to overwrite a resource. [the
        // required error marker will show on deselection as expected]
        if (overwriteExistingResources) {
            overwriteExistingResourcesCheckBox.setEnabled(true);
        }
        // the editable file name is not a valid filename for this platform
        if (destinationFileNameText.isEnabled()) {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IStatus result =
                    workspace.validateName(destinationFileNameText.getText(),
                            IResource.FILE);
            if (!result.isOK()) {
                if (showMessage) {
                    setErrorMessage(result.getMessage());
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a WSDL with given name already exists in the service
     * special folder of the target project or its referenced Projects
     * Hierarchy.Returns true if found false otherwise.Excludes the wsdl under
     * the targetProject , to allow overriding .
     * 
     * @param wsdlImportMapping
     * @param targetFolder
     * @return {@link IResource} wsdl resource with same name as in
     *         {@link WsdlImportMapping} in the target Project or any of the
     *         Project in the referenced Hierarchy.
     */
    private IResource getSameNameWsdlInScope(
            WsdlImportMapping wsdlImportMapping, File targetFolder) {

        if (targetFolder != null && targetFolder.exists()) {

            IProject currentProject = null;
            File parentFile = targetFolder.getParentFile();
            IWorkspace workspace = ResourcesPlugin.getWorkspace();

            while (parentFile != null) {

                IPath location =
                        Path.fromOSString(parentFile.getAbsolutePath());
                // get parent container at the location
                IContainer iResource =
                        workspace.getRoot().getContainerForLocation(location);
                // the parent IProject container is the current project.
                if (iResource instanceof IProject) {

                    currentProject = (IProject) iResource;
                    break;// found Project
                }
                // else keep looking until parent project is found.
                parentFile = parentFile.getParentFile();
            }

            if (currentProject != null) {

                try {
                    // get Referenced Project Hierarchy
                    Set<IProject> projectsToSearch =
                            ProjectUtil2
                                    .getReferencedProjectsHierarchy(currentProject,
                                            true);
                    // add current project also.
                    projectsToSearch.add(currentProject);
                    // Get referencing Projects Hierarchy
                    projectsToSearch.addAll(ProjectUtil2
                            .getReferencingProjectsHierarchy(currentProject,
                                    projectsToSearch));

                    IPath location =
                            Path.fromOSString(targetFolder.getAbsolutePath());
                    // get parent container at the location
                    IContainer iTargetFolderResource =
                            workspace.getRoot()
                                    .getContainerForLocation(location);

                    for (IProject iProject : projectsToSearch) {

                        IResource wsdl =
                                wsdlWithGivenNameInProject(wsdlImportMapping,
                                        iProject,
                                        iTargetFolderResource);
                        if (wsdl != null) {
                            return wsdl;
                        }
                    }

                } catch (CyclicDependencyException e) {
                    // Do not do anything
                }
            }
        }

        return null;
    }

    /**
     * Searches and returns wsdl with given name referenced in
     * {@link WsdlImportMapping} in the project.Excludes the wsdl under the
     * iTargetFolderResource , to allow overriding .
     * 
     * @param wsdlImportMapping
     * @param iProject
     * @param iTargetFolderResource
     * @return {@link IResource} wsdl with given name in
     *         {@link WsdlImportMapping}, from the given project's Service
     *         special folder, if exists null otherwise.Excludes the wsdl under
     *         the iTargetFolderResource , to allow overriding .
     */
    private IResource wsdlWithGivenNameInProject(
            WsdlImportMapping wsdlImportMapping, IProject iProject,
            IContainer iTargetFolderResource) {

        // get all wsdl files under service special folder of this project.
        ArrayList<IResource> wsdlFiles =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(iProject,
                        WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND,
                        WsdlUIPlugin.WSDL_FILE_EXTENSION);

        for (IResource iResource : wsdlFiles) {
            if (!iTargetFolderResource.equals(iResource.getParent())) {
                if (iResource
                        .getName()
                        .equalsIgnoreCase(wsdlImportMapping.getDestinationFileName())) {
                    return iResource;
                }

            }
        }
        return null;
    }

    /**
     * @return The resolution of the currently entered location as a resource
     */
    public IContainer getDestinationLocationResource() {
        IContainer folder = null;
        final StringBuilder projectNameBuilder = new StringBuilder();
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                projectNameBuilder.append(destinationLocationText.getText());
            }
        });
        File targetLocation = getTargetLocation(projectNameBuilder.toString());
        if (targetLocation != null && targetLocation.exists()) {
            IWorkspaceRoot workspaceRoot =
                    ResourcesPlugin.getWorkspace().getRoot();
            String absolutePath = targetLocation.getAbsolutePath();
            folder =
                    workspaceRoot
                            .getContainerForLocation(new Path(absolutePath));
        }
        return folder;
    }

    public void setFileName(String guessFileName) {
        wsdlImportMappings[0].setDestinationFileName(guessFileName);
    }

    public IFile getResource() {
        IPath path = new Path(wsdlImportMappings[0].getDestinationFileName());
        return getDestinationLocationResource().getFile(path);
    }

    /**
     * Returns whether user checked the overwrite existing resources checbox
     * 
     * @return
     */
    public boolean isOverwriteExistingResources() {
        return overwriteExistingResources;
    }

    /**
     * takes the value entered buy the user in project location text field and
     * using that returns an instance of java.io.File if the path is valid.
     * Handles the situation if the project is within workspace or not.
     * 
     * @param locationString
     * @return
     */
    private File getTargetLocation(String locationString) {
        File wsdlFileContainer = null;
        if (locationString == null || locationString.trim().length() < 1) {
            return wsdlFileContainer;
        }
        int indexOf = locationString.indexOf(TargetSelectionPage.pathSeparator);
        String projectName = null;
        String containerLocation = null;
        if (indexOf != -1) {
            projectName = locationString.substring(0, indexOf);
            containerLocation = locationString.substring(indexOf + 1);

        } else {
            projectName = locationString;
        }
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = workspaceRoot.getProjects();
        IProject targetProject = null;
        for (IProject project : projects) {
            if (project.getName().equals(projectName)) {
                targetProject = project;
                break;
            }
        }
        if (targetProject != null && targetProject.isOpen()) {
            IPath projectLocation = targetProject.getLocation();
            String targetLocation = null;
            if (containerLocation != null
                    && containerLocation.trim().length() > 0) {
                targetLocation =
                        projectLocation.append(new Path(containerLocation))
                                .toPortableString();
            } else {
                targetLocation = projectLocation.toOSString();
            }
            if (targetLocation != null) {
                wsdlFileContainer = new File(targetLocation);
            }
        }
        return wsdlFileContainer;
    }
}
