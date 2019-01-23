/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.newproject.BusinessProcessAssetConfig;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project selection wizard page.
 * <p>
 * This class abstracted to allow for difference between creating an XPDL
 * resource that is (a) A Process Package or (b) A Task LIbrary.
 * 
 * @see CreationWizardProjectSelectionPage
 * 
 * @author njpatel, aallway (this class abstracted from original
 *         {@link ProjectSelectPage}
 */
public abstract class AbstractXpdlProjectSelectPage extends
        CreationWizardProjectSelectionPage implements IAssetWizardPage,
        IPackageTextAndContainerPage {

    public static final String VENDOR_NAME = "TIBCO"; //$NON-NLS-1$

    public static final String USER_NAME_PROPERTY = "user.name"; //$NON-NLS-1$

    public static final String CREATEDBYATTRIB = "CreatedBy"; //$NON-NLS-1$

    public static final String BUSINESS_STUDIO_CONST = "TIBCO Business Studio"; //$NON-NLS-1$

    public static final String VERSION_ID = "1.0"; //$NON-NLS-1$

    protected Text txtPackageFile;

    private Object configurationObject;

    private boolean assetPage = false;

    private boolean createPackage = true;

    // Package file string (including any subfolders)
    protected String packageFileName = null;

    private Package xpdl2Package;

    private Button packageSelect;

    private IWizardPage nextPage;

    private IWizardPage defaultNextPage;

    /**
     * Project selection page
     * 
     */
    public AbstractXpdlProjectSelectPage(String title, String description) {
        super();

        setTitle(title);
        setDescription(description);
    }

    /**
     * @return the base XPDL file name (now used only in the event that no
     *         project is selected initially).
     */
    protected abstract String getBaseFileName();

    /**
     * @return The selected project name else a default file name.
     */
    protected String getProjectOrBaseFileName() {
        IContainer container = getPackagesFolderContainer();
        if (container != null && container.isAccessible()) {
            if (container instanceof IProject) {
                return container.getName();
            } else if (container.getProject() != null) {
                return container.getProject().getName();
            }
        }
        return getBaseFileName();
    }

    /**
     * @return The file extension.
     */
    protected abstract String getFileExtension();

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardProjectSelectionPage#
     * getPackagesFolderContainer()
     * 
     * If the packageFileName is contained within a folder then need to return
     * the actual folder where the package will be created. If no folder found
     * then return the super value
     */
    @Override
    public IContainer getPackagesFolderContainer() {
        IContainer container = super.getPackagesFolderContainer();

        if (container != null && container.isAccessible()
                && packageFileName != null) {
            // The package file is created in a subfolder so get the subfolder
            IPath folderPath = getPath(packageFileName);

            if (folderPath != null && folderPath.segmentCount() > 0) {
                IFolder folder = container.getFolder(folderPath);

                if (folder != null)
                    return folder;
            }
        }

        return container;
    }

    /**
     * Get the name of the file of the package.
     * 
     * @return
     */
    public String getPackageFileName() {
        String fileName = getFileName(packageFileName);

        return fileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#init
     * (org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IStructuredSelection selection) {
        if (selection != null && !selection.isEmpty()) {
            Object selectedElement = selection.getFirstElement();

            // If the selectedElement is a file then get it's container
            if (selectedElement instanceof IFile) {
                selectedElement = ((IFile) selectedElement).getParent();
            }

            if (selectedElement instanceof IProject
                    && ((IProject) selectedElement).isAccessible()) {

                packagesFolderContainer =
                        getPackageFolderContainer((IProject) selectedElement);
                // Set the package file
                packageFileName =
                        getPackageNameToCreate(packagesFolderContainer);

            } else if (selectedElement instanceof SpecialFolder) {
                // ProjectFolder selected so set the associated IFolder as the
                // container
                if (((SpecialFolder) selectedElement).getKind()
                        .equals(getSpecialFolderKind())) {
                    packagesFolderContainer =
                            ((SpecialFolder) selectedElement).getFolder();
                } else {
                    packagesFolderContainer =
                            getPackageFolderContainer(((SpecialFolder) selectedElement)
                                    .getFolder().getProject());
                }

                // Set the package file
                packageFileName =
                        getPackageNameToCreate(packagesFolderContainer);

            } else if (selectedElement instanceof IFolder) {
                // Get the package folder that this container belongs to
                packagesFolderContainer =
                        getPackagesFolderContainer((IResource) selectedElement);
                // packageFileName =
                // getPackagesFolderRelativePath((IResource) selectedElement);
                //
                // if (packageFileName.length() > 0) {
                // packageFileName += IPath.SEPARATOR;
                // }
                packageFileName =
                        getPackageNameToCreate((IContainer) selectedElement);
            }

            createPackage();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardProjectSelectionPage#
     * createPackageSelection(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createPackageSelection(Composite parent) {

        // Add the package selection to the UI

        createLabel(parent, Messages.ProjectSelectPage_File_label);

        txtPackageFile = createText(parent);

        if (packageFileName != null) {
            txtPackageFile.setText(packageFileName);
            // XPD-55
            txtPackageFile.setFocus();

            int dotIdx = packageFileName.lastIndexOf("."); //$NON-NLS-1$
            if (dotIdx < 0) {
                dotIdx = packageFileName.length();
            }

            txtPackageFile.setSelection(0, dotIdx);
        }

        txtPackageFile.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {

                // Update package file name
                packageFileName = txtPackageFile.getText();

                // If the right file extension isn't added then do so
                if (packageFileName != null && packageFileName.length() > 0) {
                    String regEx = ".+\\" + getFileExtension(); //$NON-NLS-1$
                    if (!packageFileName.matches(regEx)) {
                        packageFileName += getFileExtension();
                    }
                }
                if (!assetPage)
                    setPageComplete(validatePage());
                else
                    setPageComplete(assetPage);
            }
        });
    }

    @Override
    protected void createInputSelection(Composite parent) {
        if (!assetPage)
            super.createInputSelection(parent);
        else {
            Composite container = new Composite(parent, SWT.NONE);
            container.setLayout(new GridLayout());
            container.setLayoutData(new GridData(GridData.FILL_BOTH));

            Group packageGroup = new Group(container, SWT.NONE);
            packageGroup
                    .setText(Messages.ProjectSelectPage_PackageGroup_shortdesc);
            packageGroup.setLayout(new GridLayout(2, false));
            packageGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            packageSelect = new Button(packageGroup, SWT.CHECK);
            packageSelect
                    .setText(Messages.ProjectSelectPage_CreatePkgButton_label);
            GridData gridData = new GridData();
            gridData.horizontalSpan = 2;
            packageSelect.setLayoutData(gridData);
            packageSelect.setSelection(true);
            packageSelect.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    if (packageSelect.getSelection()) {
                        createPackage = true;
                        createPackage();
                        txtPackagesFolder.setEnabled(true);
                        txtPackageFile.setEnabled(true);
                        nextPage = defaultNextPage;
                    } else {
                        createPackage = false;
                        xpdl2Package = null;
                        txtPackageFile.setEnabled(false);
                        txtPackagesFolder.setEnabled(false);
                        nextPage = getNextAssetPage();
                    }

                    getWizard().getContainer().updateButtons();
                }
            });

            createLabel(packageGroup, getFileFolderLabelText());
            txtPackagesFolder = createText(packageGroup);
            txtPackagesFolder.setEditable(false);
            // Add any package selection container
            createPackageSelection(packageGroup);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#validatePage
     * ()
     */
    @Override
    protected boolean validatePage() {
        boolean ret = super.validatePage();

        if (ret) {
            // Check if filename specified. If not then warn user
            if (packageFileName != null && packageFileName.length() > 0) {
                // if the package name contains a folder path then validate the
                // folder name
                IPath folderPath = getPath(packageFileName);

                if (folderPath != null) {
                    String[] segments = folderPath.segments();
                    IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    IStatus status = Status.OK_STATUS;

                    // Validate each segment
                    for (int x = 0; x < segments.length && status.isOK(); x++) {
                        status =
                                workspace.validateName(segments[x],
                                        IResource.FOLDER);
                    }

                    if (!status.isOK()) {
                        setErrorMessage(status.getMessage());
                        ret = false;
                    }
                }

                if (ret) {
                    // Validate file name
                    IStatus status =
                            ResourcesPlugin.getWorkspace()
                                    .validateName(packageFileName,
                                            IResource.FILE);

                    if (!status.isOK()) {
                        setErrorMessage(status.getMessage());
                        ret = false;
                    } else {

                        if (packageFileName.contains("%")) { //$NON-NLS-1$
                            setErrorMessage(Messages.ProjectSelectPage_InvalidCharactersInProcessPackageName_shortmsg);
                            ret = false;
                        } else {
                            // Check if the file already exists
                            boolean fileExists =
                                    fileNameExists(packagesFolderContainer.getProject(),
                                            packageFileName);
                            if (fileExists) {
                                setErrorMessage(Messages.ProjectSelectPage_FileExistsError_shortmsg);
                                ret = false;
                            } else {
                                ret = true;
                            }
                        }

                    }
                }
            } else {
                setErrorMessage(Messages.ProjectSelectPage_PackageFileEmptyError_shortmsg);
                ret = false;
            }
        }
        return ret;
    }

    /**
     * Get the package name to create
     * 
     * @param container
     * @return The package name
     */
    protected String getPackageNameToCreate(IContainer container) {

        if (container != null) {
            // Create the default model filename
            String modelFileName =
                    getProjectOrBaseFileName() + getFileExtension();

            for (int i = 1; fileNameExists(container.getProject(),
                    modelFileName); ++i) {
                modelFileName =
                        getProjectOrBaseFileName() + i + getFileExtension();
            }

            return modelFileName;
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Check if the file name already exists in the project and its referenced
     * projects.
     * 
     * @param container
     * @param modelFileName
     * 
     * @return true if the passed modelFileName is already in use
     */
    private boolean fileNameExists(IProject project, String modelFileName) {
        if (project != null) {
            Set<IContainer> processPackageFolderContainers =
                    getPackageFolderContainers(project);

            for (IContainer container : processPackageFolderContainers) {

                if (container instanceof IFolder) {

                    IFolder folder = (IFolder) container;

                    /*
                     * XPD-7307: Saket: Need to make sure that we take XPDLs in
                     * sub-folders into account as well.
                     */
                    if (findMember(folder, modelFileName) != null) {

                        return true;
                    }
                }
            }
        }

        try {
            Set<IProject> refProjects =
                    ProjectUtil2.getReferencedProjectsHierarchy(project, true);

            for (IProject refProject : refProjects) {
                Set<IContainer> processPackageFolderContainers =
                        getPackageFolderContainers(refProject);
                for (IContainer container : processPackageFolderContainers) {

                    /*
                     * XPD-7307: Saket: Need to make sure that we take XPDLs in
                     * sub-folders into account as well.
                     */
                    if (container instanceof SpecialFolder) {

                        IFolder folder =
                                ((SpecialFolder) container).getFolder();

                        if (findMember(folder, modelFileName) != null) {

                            return true;
                        }
                    }
                }
            }

        } catch (CyclicDependencyException e1) {
            // Do nothing
        }

        return false;
    }

    /**
     * Recursive method to find a model file of name 'modelFileName' in the
     * folder resource identified by 'resource'. Return the file if such file is
     * found, otherwise return <code>null</code>.
     * 
     * @param resource
     * @param modelFileName
     * 
     * @return The file if file with name 'modelFileName' is found, otherwise
     *         return <code>null</code>.
     */
    private Object findMember(IResource resource, String modelFileName) {

        /*
         * If the resource is an IFolder instance, then call the method
         * recursively on it's members and if we find a file of the specified
         * name, then return that file.
         */
        if (resource instanceof IFolder) {

            try {

                for (IResource eachResource : ((IFolder) resource).members()) {

                    Object found = findMember(eachResource, modelFileName);

                    if (found != null) {

                        return found;
                    }
                }

            } catch (CoreException e) {

                List<String> argList = new ArrayList<String>();

                argList.add(modelFileName);

                Xpdl2ResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(String.format(Messages.AbstractXpdlProjectSelectPage_ProblemCreatingModelFile_message,
                                argList));

                e.printStackTrace();
            }

        } else if (resource instanceof IFile
                && modelFileName.equals(resource.getName())) {

            /*
             * If we find a file of the specified name, return it.
             */

            return resource;
        }

        return null;

    }

    /**
     * Get the process package folder container for a given project If no
     * Special folders are found, this method returns the project as the folder
     * container
     * 
     * @param project
     * @return The package folder container
     */
    protected IContainer getPackageFolderContainer(IProject project) {
        IContainer processPackageFolderContainer = null;
        /*
         * Find if there any packages folders defined for this project and pick
         * the first one that is accessible as the default packages folder
         */
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        EList sFolders = null;

        if (config != null && config.getSpecialFolders() != null) {
            sFolders =
                    config.getSpecialFolders()
                            .getFoldersOfKind(getSpecialFolderKind());
        }

        if (sFolders != null) {
            SpecialFolder sFolder = null;

            for (Iterator<?> iter = sFolders.iterator(); iter.hasNext()
                    && sFolder == null;) {
                SpecialFolder sf = (SpecialFolder) iter.next();
                // If the associated folder is not accessible then
                // check the next packages folder
                if (sf != null && sf.getFolder() != null
                        && sf.getFolder().isAccessible()) {
                    sFolder = sf;
                }
            }

            // If we have a packages folder then set it as the container
            if (sFolder != null) {
                processPackageFolderContainer = sFolder.getFolder();
            } else {
                // No packages folder found so set project as container
                processPackageFolderContainer = project;
            }
        } else {
            // No package folders specified so just as the project
            // as the target container
            processPackageFolderContainer = project;
        }
        return processPackageFolderContainer;
    }

    /**
     * Get the process package folder container for a given project If no
     * Special folders are found, this method returns the project as the folder
     * container
     * 
     * @param project
     * @return The package folder container
     */
    private Set<IContainer> getPackageFolderContainers(IProject project) {
        Set<IContainer> processPackageFolderContainer =
                new HashSet<IContainer>();
        /*
         * Find if there any packages folders defined for this project and pick
         * the first one that is accessible as the default packages folder
         */
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        EList sFolders = null;

        if (config != null && config.getSpecialFolders() != null) {
            sFolders =
                    config.getSpecialFolders()
                            .getFoldersOfKind(getSpecialFolderKind());
        }

        if (sFolders != null) {
            for (Iterator<?> iter = sFolders.iterator(); iter.hasNext();) {
                SpecialFolder sf = (SpecialFolder) iter.next();
                // If the associated folder is not accessible then
                // check the next packages folder
                if (sf != null && sf.getFolder() != null
                        && sf.getFolder().isAccessible()) {
                    processPackageFolderContainer.add(sf.getFolder());
                }
            }

            // If we have a packages folder then set it as the container
            if (processPackageFolderContainer.isEmpty()) {
                // No packages folder found so set project as container
                processPackageFolderContainer.add(project);
            }
        } else {
            // No package folders specified so just as the project
            // as the target container
            processPackageFolderContainer.add(project);
        }
        return processPackageFolderContainer;
    }

    /**
     * Return the path in the given filename
     * 
     * @param fileName
     * @return
     */
    private IPath getPath(String fileName) {

        if (fileName != null) {
            IPath path = new Path(fileName);

            if (path != null) {
                return path.removeLastSegments(1);
            }
        }

        return null;
    }

    /**
     * Returns the last segment from the path given
     * 
     * @param fileName
     * @return
     */
    private String getFileName(String fileName) {

        if (fileName != null) {
            IPath path = new Path(fileName);

            return path.lastSegment();
        }

        return null;
    }

    @Override
    public Text getTxtPackageFile() {
        return txtPackageFile;
    }

    @Override
    public void init(Object config) {
        this.configurationObject = config;
        this.assetPage = true;

        if (configurationObject != null) {
            packageFolderName =
                    ((BusinessProcessAssetConfig) configurationObject)
                            .getSpecialFolderName();
            packageFileName = getProjectOrBaseFileName() + getFileExtension();
        }
        createPackage();

    }

    @Override
    public void updateConfiguration() {
        ((BusinessProcessAssetConfig) configurationObject)
                .setXpdl2Package(xpdl2Package);
        if (txtPackageFile != null && txtPackageFile.getText() != null
                && !txtPackageFile.getText().equals("")) { //$NON-NLS-1$
            ((BusinessProcessAssetConfig) configurationObject)
                    .setPackageFileName(txtPackageFile.getText());
        } else {
            ((BusinessProcessAssetConfig) configurationObject)
                    .setPackageFileName(packageFileName);
        }
    }

    @Override
    protected boolean isAssetPage() {
        return assetPage;
    }

    public void createPackage() {
        if (xpdl2Package == null) {
            xpdl2Package = Xpdl2Factory.eINSTANCE.createPackage();
            // In case package file name hasn't been initialized.
            if (packageFileName == null) {
                packageFileName =
                        getProjectOrBaseFileName() + getFileExtension();
            }
            String pckgName =
                    packageFileName.substring(0,
                            packageFileName.indexOf(getFileExtension()));
            xpdl2Package.setName(NameUtil.getInternalName(pckgName, false));
            Xpdl2ModelUtil
                    .setOtherAttribute(xpdl2Package,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            pckgName);

            PackageHeader packageHeader =
                    Xpdl2Factory.eINSTANCE.createPackageHeader();
            packageHeader.setVendor(VENDOR_NAME);
            packageHeader.setCreated(new Date().toString());
            xpdl2Package.setPackageHeader(packageHeader);

            RedefinableHeader redefinableHeader =
                    Xpdl2Factory.eINSTANCE.createRedefinableHeader();
            redefinableHeader.setAuthor(System.getProperty(USER_NAME_PROPERTY));
            redefinableHeader.setVersion(VERSION_ID);
            xpdl2Package.setRedefinableHeader(redefinableHeader);

            ExtendedAttribute attrib =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(CREATEDBYATTRIB);
            attrib.setValue(BUSINESS_STUDIO_CONST);
            xpdl2Package.getExtendedAttributes().add(attrib);

            attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(XpdlMigrate.FORMAT_VERSION_ATT_NAME);
            attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
            xpdl2Package.getExtendedAttributes().add(attrib);

            /* Store the OriginalFormatVersion that xpdl was created in. */
            attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(XpdlMigrate.ORIGINAL_FORMAT_VERSION_ATT_NAME);
            attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
            xpdl2Package.getExtendedAttributes().add(attrib);

        }
    }

    public Package getPackageObject() {
        return xpdl2Package;
    }

    /**
     * If the user-selected project package folder does not exist - create it.
     * <p>
     * This is done in a workspace operation.
     * 
     * @return true on success.
     */
    public boolean createPackageFolder() {
        try {
            if (!getPackagesFolderContainer().isAccessible()
                    && getPackagesFolderContainer() instanceof IFolder) {

                // Create the subfolders
                getContainer().run(false,
                        false,
                        new WorkspaceModifyOperation() {

                            @Override
                            protected void execute(IProgressMonitor monitor)
                                    throws CoreException,
                                    InvocationTargetException,
                                    InterruptedException {
                                createFolder((IFolder) getPackagesFolderContainer(),
                                        monitor);
                            }

                            /**
                             * Create the given folder, also create any parent
                             * folders that don't exist
                             */
                            private void createFolder(IFolder folder,
                                    IProgressMonitor monitor)
                                    throws CoreException {
                                if (folder != null && !folder.isAccessible()) {
                                    // If the parent is a folder and not
                                    // accessible then
                                    // create parent first
                                    if (folder.getParent() != null
                                            && !folder.getParent()
                                                    .isAccessible()) {
                                        createFolder((IFolder) folder
                                                .getParent(), monitor);
                                    }

                                    // Create the folder
                                    folder.create(true, true, monitor);
                                }
                            }

                        });
            }
        } catch (InvocationTargetException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canFlipToNextPage() {
        return createPackage || getNextPage() != null;
    }

    @Override
    public IWizardPage getNextPage() {
        defaultNextPage = super.getNextPage();
        if (nextPage != null)
            return nextPage;
        if (assetPage && !createPackage)
            return getNextAssetPage();
        if (!createPackage) {
            getWizard().getContainer().updateButtons();
            return null;
        }
        return super.getNextPage();
    }

    private IWizardPage getNextAssetPage() {
        IWizardPage page = getWizard().getPage("packageTemplateSelection"); //$NON-NLS-1$
        if (page != null)
            nextPage = getWizard().getNextPage(page);
        return nextPage;
    }

    @Override
    public void dispose() {
        if (xpdl2Package != null)
            xpdl2Package = null;
        super.dispose();
    }
}