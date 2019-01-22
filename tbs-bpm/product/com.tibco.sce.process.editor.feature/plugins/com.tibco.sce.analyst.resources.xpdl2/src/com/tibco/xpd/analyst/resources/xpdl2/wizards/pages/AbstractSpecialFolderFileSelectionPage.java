/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceSorter;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.resources.TypedElementSelectionValidator;
import com.tibco.xpd.ui.resources.TypedViewerFilter;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Select project and package wizard page. Extends
 * <code>CreationWizardProjectSelectionPage</code>
 * 
 * @see CreationWizardProjectSelectionPage
 * 
 * @author njpatel
 */
public abstract class AbstractSpecialFolderFileSelectionPage extends
        CreationWizardProjectSelectionPage {

    private Text txtPackageFile;

    private Button btnPackageFile;

    protected IFile packageFile;

    /**
     * Project, package and process (optional) selection page used by the New
     * Wizards.
     */
    public AbstractSpecialFolderFileSelectionPage(String title,
            String description) {
        super();
        // Set the title and message for this page
        setTitle(title);
        setDescription(description);
    }

    protected abstract String getFileNotExistMessage();

    protected abstract String getFileNameEmptyMessage();

    protected abstract String getFileTypeNameLabel();

    protected abstract String getFileSelectionMessage();

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

            if (selectedElement instanceof IProject) {
                /*
                 * A project is selected so find if there any packages folders
                 * defined for this project and pick the first one that is
                 * accessible as the default packages folder
                 */
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig((IProject) selectedElement);
                List<SpecialFolder> sFolders = new ArrayList<SpecialFolder>();

                if (config != null && config.getSpecialFolders() != null) {
                    EList<SpecialFolder> processFolders =
                            config.getSpecialFolders()
                                    .getFoldersOfKind(getSpecialFolderKind());
                    if (processFolders != null) {
                        sFolders.addAll(processFolders);
                    }
                }

                if (!sFolders.isEmpty()) {
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
                        packagesFolderContainer = sFolder.getFolder();
                    } else {
                        // No packages folder found so set project as container
                        packagesFolderContainer = (IContainer) selectedElement;
                    }
                } else {
                    // No package folders specified so just as the project
                    // as the target container
                    packagesFolderContainer = (IContainer) selectedElement;
                }

            } else if (selectedElement instanceof SpecialFolder) {
                // ProjectFolder selected so set the associated IFolder as the
                // container
                packagesFolderContainer =
                        ((SpecialFolder) selectedElement).getFolder();
            } else if (selectedElement instanceof IResource) {
                // Get the packages folder then contains this container
                packagesFolderContainer =
                        getPackagesFolderContainer((IResource) selectedElement);

                // If this is a file then try to get working copy. If successful
                // then set the packageFile
                if (selectedElement instanceof IFile
                        && packagesFolderContainer instanceof IFolder) {
                    IFile file = (IFile) selectedElement;

                    WorkingCopy wc = getWorkingCopy(file);

                    if (wc != null) {
                        packageFile = file;
                    }
                }

            } else if (selectedElement instanceof AbstractAssetGroup
                    || selectedElement instanceof EObject) {
                // Process asset group / EObject selected so should be able to
                // get container
                // and packages folder from the working copy of the group's
                // parent / EObject
                WorkingCopy wc = null;

                if (selectedElement instanceof AbstractAssetGroup) {
                    AbstractAssetGroup group =
                            (AbstractAssetGroup) selectedElement;
                    wc = getWorkingCopy(group.getParent());
                } else {
                    wc = getWorkingCopy(selectedElement);
                }

                if (wc != null) {
                    if ((packageFile = (IFile) wc.getEclipseResources().get(0)) != null) {
                        packagesFolderContainer =
                                getPackagesFolderContainer(packageFile);
                        if (!(packagesFolderContainer instanceof IFolder)) {
                            packageFile = null;
                        }
                    }
                }
            }
        }

        // If the packages folder container or the packages file is null then
        // this page is not complete
        setPageComplete(packagesFolderContainer != null && packageFile != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardProjectSelectionPage#getPackageFile
     * ()
     */
    public IFile getPackageFile() {
        return packageFile;
    }

    /**
     * Get the EObject container of this artifact. This will be a
     * <code>Package</code>
     * 
     * @return <li><code>Package</code> - If user selected to create artifact in
     *         package.
     */
    public EObject getEContainer() {
        if (packageFile != null) {
            // Return the package
            WorkingCopy wc = getWorkingCopy(packageFile);

            if (wc != null)
                return wc.getRootElement();
        }

        return null;
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
        // Call the super version to validate the main part of this page
        boolean ret = super.validatePage();

        // Now validate the packages file entry
        if (ret) {

            String packageFileName = txtPackageFile.getText();

            // Check if filename specified. If not then warn user
            if (packageFileName != null && packageFileName.length() > 0) {
                // Check if the file exists
                IResource resource =
                        packagesFolderContainer.findMember(packageFileName);

                if (resource == null
                        || (resource != null && !resource.isAccessible())) {
                    setErrorMessage(getFileNotExistMessage());
                    ret = false;
                } else {
                    // Packages valid
                    // Update the package file
                    packageFile = (IFile) resource;
                    ret = true;
                }
            } else {
                setErrorMessage(getFileNameEmptyMessage());
                ret = false;
            }
        }

        // If valid then clear error messages
        if (ret) {
            setErrorMessage(null);
        }

        return ret;
    }

    private ModifyListener validatePageModifyListener = new ModifyListener() {
        @Override
        public void modifyText(ModifyEvent e) {
            setPageComplete(validatePage());
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardProjectSelectionPage#
     * createPackageSelection(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createPackageSelection(Composite parent) {

        createLabel(parent, getFileTypeNameLabel());
        txtPackageFile = createText(parent);

        /*
         * XPD-6766: Validate if the user types in a xpdl file name instead of
         * using the browse button to select the xpdl file
         */
        txtPackageFile.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {

                setPageComplete(validatePage());
            }
        });
        /* Set the default package file name */
        if (packagesFolderContainer != null && packageFile != null) {
            /* Set the packages folder relative path */
            txtPackageFile.setText(packageFile.getName());
        }

        addPacakgeFileModifyListeners(validatePageModifyListener);
        btnPackageFile = new Button(parent, SWT.PUSH);
        btnPackageFile.setLayoutData(new GridData());
        btnPackageFile.setText(Messages.PackageSelectionPage_5);
        btnPackageFile.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                browseForPackage();
            }
        });
    }

    public void addPacakgeFileModifyListeners(ModifyListener modifyListener) {
        if (txtPackageFile != null) {
            txtPackageFile.addModifyListener(modifyListener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#
     * browseForPackage()
     */
    protected void browseForPackage() {

        if (packagesFolderContainer != null) {
            ISelectionStatusValidator validator =
                    new TypedElementSelectionValidator(
                            new Class[] { IFile.class }, false);
            ArrayList rejectedElements = new ArrayList(0);
            ViewerFilter typedFilter =
                    new TypedViewerFilter(new Class[] { IFolder.class,
                            IFile.class }, rejectedElements.toArray());

            ILabelProvider labelProvider = new WorkbenchLabelProvider();
            ITreeContentProvider contentProvider =
                    new WorkbenchContentProvider();
            ElementTreeSelectionDialog dialog =
                    new ElementTreeSelectionDialog(getShell(), labelProvider,
                            contentProvider);
            dialog.setTitle(Messages.PackageSelectionPage_6);
            dialog.setValidator(validator);
            dialog.setMessage(getFileSelectionMessage());
            dialog.addFilter(typedFilter);
            dialog.addFilter(new ViewerFilter() {
                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    // Only allow XPDL2 packages
                    if (element instanceof IFile)
                        return (getWorkingCopy(element) != null);
                    else
                        // Folder
                        return true;
                }
            });
            dialog.setInput(packagesFolderContainer);
            dialog.setInitialSelection(packageFile);
            dialog.setSorter(new ResourceSorter(ResourceSorter.NAME));
            if (dialog.open() == Window.OK) {
                IFile file = (IFile) dialog.getFirstResult();
                packageFile = file;
                updateWizard();
                if (packageFile != null) {
                    txtPackageFile.setText(packageFile.getName());
                } else {
                    txtPackageFile.setText(""); //$NON-NLS-1$
                }
            }
        }

    }

    // Method required by wizards in a case where the wizard is selected from
    // File->New. Temporarily used for <code>NewProcessWizard</code> only.
    private void updateWizard() {
    }

    /**
     * Get's the working copy from the item passed in. The item should be either
     * an IFile or EObject
     * 
     * @param item
     *            IFile or Eobject
     * @return If successful then WorkingCopy, <strong>null</strong> otherwise
     */
    protected WorkingCopy getWorkingCopy(Object item) {
        WorkingCopy wc = null;

        if (item != null) {
            if (item instanceof IFile) {
                XpdProjectResourceFactory fact =
                        XpdResourcesPlugin
                                .getDefault()
                                .getXpdProjectResourceFactory(((IFile) item).getProject());

                if (fact != null) {
                    wc = fact.getWorkingCopy((IFile) item);

                    // If this is not an XPDL2 package then reset wc
                    if (wc != null
                            && wc.getWorkingCopyEPackage() != Xpdl2Package.eINSTANCE) {
                        wc = null;
                    }
                }

            } else if (item instanceof EObject) {
                wc = WorkingCopyUtil.getWorkingCopyFor((EObject) item);
            }
        }

        return wc;
    }

}
