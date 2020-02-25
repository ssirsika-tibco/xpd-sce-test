/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.resources.TypedElementSelectionValidator;
import com.tibco.xpd.ui.resources.TypedViewerFilter;

/**
 * Container selection wizard page for the New Wizards. This abstract class will
 * allow the selection of Project. This page also needs input for package but
 * this will be left upto the subclasses to provide by extending the abstract
 * method <code>createPackageSelection</code>. Validation for the packages file
 * will also be required through extending method <code>validatePage</code>
 * 
 * @author njpatel
 */
public abstract class CreationWizardProjectSelectionPage extends WizardPage {

    protected static final int NO_OF_COLUMNS = 3;

    protected Text txtPackagesFolder;

    protected String packageFolderName;

    protected Button btnPackagesFolderBrowse;

    protected IContainer packagesFolderContainer = null;

    protected boolean switchedToDefaultSpecialFolder = false;

    private IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
            .getRoot();

    public CreationWizardProjectSelectionPage() {
        super("containerSelectionPage"); //$NON-NLS-1$
    }

    /**
     * Initialisation of the wizard page. This does nothing here, Subclasses can
     * extend this to set up the initial selection of project and packages
     * folder
     * 
     * @param selection
     */
    public abstract void init(IStructuredSelection selection);

    /**
     * @return The special folder kind for this type of xpdl package
     */
    protected abstract String getSpecialFolderKind();

    /**
     * @return The label for the file type folder. control
     */
    protected abstract String getFileFolderLabelText();

    /**
     * @return The description text just above the controls.
     */
    protected abstract String getPageInternalDescription();

    protected abstract String getNotCorrectSpecialFolderMessage();

    protected abstract String getBrowseSpecialFolderTitle();

    public IContainer getPackagesFolderContainer() {
        return packagesFolderContainer;
    }

    // MR 39946 - begin
    public Text getTxtPackagesFolder() {
        return txtPackagesFolder;
    }

    // MR 39946 - end
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {

        Composite rootContainer = new Composite(parent, SWT.NULL);
        rootContainer.setLayout(new GridLayout());
        rootContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
        PlatformUI
                .getWorkbench()
                .getHelpSystem()
                .setHelp(rootContainer,
                        "com.tibco.xpd.process.analyst.doc.CreatePack1"); //$NON-NLS-1$
        // Add project and package selection
        createInputSelection(rootContainer);

        // Create optional settings
        createOptionSelection(rootContainer);

        // Set the packages folder text
        String txt = getContainerText(packagesFolderContainer);
        if (packageFolderName != null)
            txtPackagesFolder.setText(packageFolderName);
        else if (txt != null) {
            txtPackagesFolder.setText(txt);
        }

        // Saket edited:
        // if (txt != null) {
        // txtPackagesFolder.setText(txt);
        // } else if (packageFolderName != null)
        // txtPackagesFolder.setText(packageFolderName);
        //

        setControl(rootContainer);
        if (!isAssetPage())
            setPageComplete(validatePage());
        else
            setPageComplete(isAssetPage());
    }

    /**
     * Include any optional controls on this page
     * 
     * @param parent
     */
    protected void createOptionSelection(Composite parent) {
        // Can be overloaded
    }

    /**
     * Create the package selected controls
     * 
     * @param parent
     */
    protected void createInputSelection(Composite parent) {
        GridData gridData = null;

        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = NO_OF_COLUMNS;
        container.setLayout(layout);
        container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label lblInfo = new Label(container, SWT.NONE);
        gridData = new GridData();
        gridData.horizontalSpan = NO_OF_COLUMNS;
        gridData.verticalSpan = 5;
        lblInfo.setLayoutData(gridData);
        lblInfo.setText(getPageInternalDescription());

        createLabel(container, getFileFolderLabelText());
        txtPackagesFolder = createText(container);
        txtPackagesFolder.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });

        btnPackagesFolderBrowse = new Button(container, SWT.PUSH);
        btnPackagesFolderBrowse
                .setText(Messages.CreationWizardProjectSelectionPage_3);
        btnPackagesFolderBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                browseForProject();
            }
        });

        // Add any package selection container
        createPackageSelection(container);

    }

    /**
     * Subclass should extend this to provide input for packages
     * 
     * @param parent
     */
    protected abstract void createPackageSelection(Composite parent);

    /**
     * Create a label control with the given string
     * 
     * @param container
     * @param label
     * @return
     */
    protected Label createLabel(Composite container, String label) {
        return createLabel(container, label, 0);
    }

    /**
     * Create a label control with the given string at the given indent level
     * 
     * @param container
     * @param label
     * @param indent
     * @return
     */
    protected Label createLabel(Composite container, String label, int indent) {
        Label lbl = new Label(container, SWT.WRAP);
        GridData gData = new GridData();
        // gData.horizontalIndent = indent;
        // gData.widthHint = 95;
        lbl.setLayoutData(gData);
        lbl.setText(label);

        return lbl;
    }

    /**
     * Create a text control
     * 
     * @param container
     * @return
     */
    protected Text createText(Composite container) {
        Text txt = new Text(container, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txt.setLayoutData(gData);

        return txt;
    }

    /**
     * validate the input on this page
     * 
     * @return
     */
    protected boolean validatePage() {
        boolean ret = false;
        setErrorMessage(null);
        String containerPath = txtPackagesFolder.getText();

        if (workspaceRoot != null) {
            if (containerPath != null && containerPath.length() > 0) {

                // Remove any trailing slashes
                containerPath = containerPath.replaceAll("" + IPath.SEPARATOR //$NON-NLS-1$
                        + "$", ""); //$NON-NLS-1$ //$NON-NLS-2$

                IContainer container = null;
                // If no separator in the path then it must be a project
                if (!containerPath.contains("" + IPath.SEPARATOR)) { //$NON-NLS-1$
                    container = workspaceRoot.getProject(containerPath);
                } else {
                    // This is not a project so must be a folder
                    container =
                            workspaceRoot.getFolder(new Path(containerPath));
                }

                if (container != null && container.isAccessible()) {

                    // If this is a project then it's not a packages folder
                    if (container instanceof IProject) {
                        // Not a packages folder so warn user
                        setErrorMessage(getNotCorrectSpecialFolderMessage());
                    } else if (container instanceof SpecialFolder) {
                        SpecialFolders sFolders =
                                getSpecialFolders(container.getProject());

                        if (sFolders != null
                                && sFolders.getFolder((IFolder) container,
                                        getSpecialFolderKind()) == null) {
                            // Not a packages folder so warn user
                            setErrorMessage(getNotCorrectSpecialFolderMessage());
                        } else {
                            // Clear message
                            setMessage(null);
                            // OK to continue
                            ret = true;
                        }
                    } else if (container instanceof IFolder) {
                        IFolder folder = (IFolder) container;
                        setMessage(null);
                        ret = true;
                    }

                    /* Finally check if the project is locked. */
                    try {
                        if (ret && container != null
                                && new GovernanceStateService().isLockedForProduction(container.getProject())) {
                            setErrorMessage(Messages.CreationWizardProjectSelectionPage_ProjectLockedError);
                            ret = false;
                        }
                    } catch (CoreException e) {
                        Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                    }

                    // Update target container
                    if (ret) {
                        packagesFolderContainer = container;
                    } else {
                        packagesFolderContainer = null;
                    }

                } else {
                    String msg =
                            MessageFormat
                                    .format(Messages.CreationWizardProjectSelectionPage_10,
                                            new Object[] { containerPath });
                    setErrorMessage(msg);
                }

            } else {
                setErrorMessage(Messages.CreationWizardProjectSelectionPage_11);
            }
        } else {
            setErrorMessage(Messages.CreationWizardProjectSelectionPage_12);
        }

        // If page valid then unset any error messages
        if (ret) {
            // Don't clear messages as there may be a valid warning message
            setErrorMessage(null);
        }

        return ret;
    }

    /**
     * Get the packages folder that contains the given resource
     * 
     * @param resource
     * 
     * @return <code>IFolder</code> of the packages folder if the resource is in
     *         a packages folder. If the resource is not in a packages folder
     *         then <code>IProject</code> of the resource will be returned.
     */
    protected IContainer getPackagesFolderContainer(IResource resource) {
        IContainer container = null;

        switchedToDefaultSpecialFolder = false;

        if (resource != null) {
            SpecialFolders sFolders = getSpecialFolders(resource.getProject());

            if (sFolders != null) {

                // Get the containing packages folder if the resource is in a
                // package folder
                SpecialFolder sf = sFolders.getFolderContainer(resource);

                if (sf != null) {
                    if (sf.getKind().equals(getSpecialFolderKind())) {

                        if (resource instanceof IFolder) {
                            return (IFolder) resource;
                        } else {
                            /*
                             * XPD-7264: File location New wizard page for
                             * process-related elements is not initialised
                             * correctly for items in a special folder
                             * sub-folder).
                             * 
                             * That's because we used to use the sf.getFolder()
                             * BUT sFOlders.getFolderContainer() always returns
                             * the ROOT special folder (as it basically checks
                             * for any special folder path that prefixes the
                             * given resource path).
                             * 
                             * So now we use the resource container (if we know
                             * it's in a special folder of somekind then the
                             * container MUST be a special folder or sub-folder
                             * of one.
                             */
                            return (resource.getParent());
                        }
                    }
                }
            }

            // If no packages folder then set the project as container
            if (container == null) {
                container = resource.getProject();
            }
        }

        return container;
    }

    /**
     * Get the path of the resource relative to the packages folder (or project
     * if not in a packags folder) it's in.
     * 
     * @param resource
     * 
     * @return <code>String</code> relative path
     */
    protected String getPackagesFolderRelativePath(IResource resource) {
        String path = ""; //$NON-NLS-1$

        // Make sure the packages container and the resource given are not the
        // same
        // If they are then return the blank string
        if (resource != null && !resource.equals(packagesFolderContainer)) {
            // Get the count of path segments that match the packages folder
            // container path
            int segments =
                    resource.getFullPath()
                            .matchingFirstSegments(packagesFolderContainer.getFullPath());

            // If there are matching segments then remove them from the path of
            // the resource to give us the relative path to the packages folder
            // (or project)
            //
            // NOTE: There should be AT LEAST 2 matching segments at start of
            // folder path and resource path IF it the selected resource is of
            // correct type for the type of special folder we're creating in
            // (other wise the user has done maoin File->New->Object whilst
            // different file kind slected - so we will be defaulting to first
            // special folder for correct type.

            if (segments > 1) {
                IPath relativePath =
                        resource.getFullPath().removeFirstSegments(segments);

                path = relativePath.toString();

                if (path != null && path.charAt(0) == IPath.SEPARATOR)
                    path = path.substring(1);
            }
        }

        return path;
    }

    /**
     * Get the text representation of the container - this would be the full
     * path of the container relative to the workspace
     * 
     * @param container
     * @return <code>String</code> path relative to the workspace
     */
    private String getContainerText(IContainer container) {
        String packagesFolder = null;

        if (container != null) {
            packagesFolder = container.getFullPath().toString();
            // Remove leading slash
            if (packagesFolder != null) {
                if (packagesFolder.charAt(0) == IPath.SEPARATOR)
                    packagesFolder = packagesFolder.substring(1);
            }
        }

        return packagesFolder;
    }

    // MR 39946 - begin
    protected Class[] getAcceptedClasses() {
        return new Class[] { SpecialFolder.class, IFolder.class };
    }

    // MR 39946 - end
    /**
     * Show browse for project dialog
     */
    protected void browseForProject() {

        if (workspaceRoot != null) {
            Class[] acceptedClasses =
                    new Class[] { IProject.class, SpecialFolder.class,
                            IFolder.class };
            ISelectionStatusValidator validator =
                    new TypedElementSelectionValidator(getAcceptedClasses(),
                            false);
            ArrayList rejectedElements = new ArrayList(0);
            ViewerFilter typedFilter =
                    new TypedViewerFilter(acceptedClasses,
                            rejectedElements.toArray());

            ElementTreeSelectionDialog dialog =
                    new ElementTreeSelectionDialog(getShell(),
                            new PackagesFolderLabelProvider(),
                            new PackagesFolderContentProvider());
            dialog.setTitle(getBrowseSpecialFolderTitle());
            dialog.setValidator(validator);
            dialog.addFilter(typedFilter);
            dialog.setInput(workspaceRoot);
            dialog.setAllowMultiple(false);

            if (packagesFolderContainer != null) {

                // If project then select project
                if (packagesFolderContainer instanceof IProject) {
                    dialog.setInitialSelection(packagesFolderContainer);
                } else if (packagesFolderContainer instanceof SpecialFolder) {
                    // This is a folder so get the ProjectFolder associated with
                    // it

                    // SpecialFolders sFolders =
                    // getSpecialFolders(packagesFolderContainer
                    // .getProject());
                    //
                    // if (sFolders != null) {
                    // SpecialFolder sFolder =
                    // sFolders.getFolder((IFolder) packagesFolderContainer);
                    //
                    // if (sFolder != null) {
                    // dialog.setInitialSelection(sFolder);
                    // }
                    // }

                    dialog.setInitialSelection(packagesFolderContainer);

                } else if (packagesFolderContainer instanceof IFolder) {
                    /*
                     * If it's an IFolder that's really a special folder then
                     * set that as selection because the content provider uses
                     * those.
                     */
                    String specialFolderKind =
                            SpecialFolderUtil
                                    .getSpecialFolderKind((IFolder) packagesFolderContainer);

                    if (specialFolderKind != null
                            && specialFolderKind.length() > 0) {
                        dialog.setInitialSelection(SpecialFolderUtil
                                .getSpecialFolderOfKind(((IFolder) packagesFolderContainer)
                                        .getProject(),
                                        specialFolderKind));
                    } else {
                        dialog.setInitialSelection(packagesFolderContainer);
                    }

                }
            }

            if (dialog.open() == Dialog.OK) {
                Object selection = dialog.getFirstResult();

                resetPackageFolderContainer(selection);
            }
        }
    }

    /**
     * @param selection
     */
    protected void resetPackageFolderContainer(Object selection) {
        if (selection instanceof SpecialFolder)
            packagesFolderContainer = ((SpecialFolder) selection).getFolder();
        else if (selection instanceof IFolder) {
            packagesFolderContainer = (IContainer) selection;
        } else
            packagesFolderContainer = (IContainer) selection;

        if (packagesFolderContainer != null) {
            txtPackagesFolder
                    .setText(getContainerText(packagesFolderContainer));
        }
    }

    /**
     * Content provider for the project browse dialog
     * 
     * @author njpatel
     * 
     */
    class PackagesFolderContentProvider implements ITreeContentProvider {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang
         * .Object)
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang
         * .Object)
         */
        @Override
        public Object getParent(Object element) {
            Object parent = null;
            if (element instanceof SpecialFolder) {
                // Get the project
                return ((SpecialFolder) element).getProject();

            } else if (element instanceof IResource) {
                // return ((IResource) element).getParent();
                parent = ((IResource) element).getParent();

            }

            /*
             * Our content use Special-Folder BUT the parent of a Sub-Folder is
             * just the IFolder that's the sub-folder or resource's parent
             * folder in workspace.
             * 
             * So must see if parent is a folder whether it's a special folder
             * and return that instead
             */
            if (parent instanceof IFolder) {
                String specialFolderKind =
                        SpecialFolderUtil
                                .getSpecialFolderKind((IFolder) parent);
                if (specialFolderKind != null && specialFolderKind.length() > 0) {
                    parent =
                            SpecialFolderUtil
                                    .getSpecialFolderOfKind(((IFolder) parent)
                                            .getProject(), specialFolderKind);
                }
            }

            return parent;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang
         * .Object)
         */
        @Override
        public boolean hasChildren(Object element) {

            if (element instanceof IProject || element instanceof SpecialFolder
                    || element instanceof IFolder) {
                return getElements(element) != null
                        && getElements(element).length > 0;
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
         * java.lang.Object)
         */
        @Override
        @SuppressWarnings("unchecked")
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof IWorkspaceRoot) {
                /*
                 * Only return XPD projects that contain required special folder
                 * kind.
                 */
                List<IProject> xpdProjects = new ArrayList<IProject>();

                for (IProject project : ((IWorkspaceRoot) inputElement)
                        .getProjects()) {
                    try {
                        if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                            if (!project.getName().startsWith(".")) { //$NON-NLS-1$
                                /*
                                 * Recurs to see if the object has any child
                                 * special folders.
                                 */
                                Object[] elements = getElements(project);
                                if (elements != null && elements.length > 0) {
                                    xpdProjects.add(project);
                                }

                            }
                        }
                    } catch (CoreException e) {
                    }
                }

                return xpdProjects.toArray();

            } else if (inputElement instanceof IProject) {
                // Only list the packages folders for the project
                SpecialFolders sFolders =
                        getSpecialFolders((IProject) inputElement);

                if (sFolders != null) {
                    EList<SpecialFolder> specialFolders =
                            sFolders.getFoldersOfKind(getSpecialFolderKind());
                    if (specialFolders != null) {
                        return specialFolders.toArray(new Object[specialFolders
                                .size()]);
                    }
                }
            } else if (inputElement instanceof SpecialFolder
                    || inputElement instanceof IFolder) {
                /*
                 * return the sub folder(s) under a process package special
                 * folder
                 */
                IFolder folder = null;

                if (inputElement instanceof SpecialFolder) {
                    folder = ((SpecialFolder) inputElement).getFolder();
                } else {
                    folder = (IFolder) inputElement;
                }

                List<IFolder> folders = new ArrayList<IFolder>();
                try {
                    for (IResource res : folder.members()) {
                        if (res instanceof IFolder) {
                            folders.add((IFolder) res);
                        }
                    }

                    return folders.toArray();
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
         * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Do nothing
        }

    }

    /**
     * Label provider for the project browse dialog
     * 
     * @author njpatel
     * 
     */
    class PackagesFolderLabelProvider extends LabelProvider {

        private WorkbenchLabelProvider provider = new WorkbenchLabelProvider();

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {

            if (element instanceof EObject) {
                return WorkingCopyUtil.getImage((EObject) element);
            } else if (provider != null) {
                return provider.getImage(element);
            }

            return super.getImage(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {
            if (element instanceof EObject) {
                return WorkingCopyUtil.getText((EObject) element);
            } else if (provider != null) {
                return provider.getText(element);
            }

            return super.getText(element);
        }
    }

    /**
     * Get the <code>SpecialFolders</code> model from the project's config.
     * 
     * @param project
     * @return
     */
    private SpecialFolders getSpecialFolders(IProject project) {
        SpecialFolders ret = null;

        if (project != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            if (config != null) {
                ret = config.getSpecialFolders();

            }
        }

        return ret;

    }

    protected boolean isAssetPage() {
        return false;
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem()
                .displayHelp("com.tibco.xpd.process.analyst.doc.CreatePack1"); //$NON-NLS-1$      
    }
}