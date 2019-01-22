/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.registry.ui.wizard;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * @author bharge
 * 
 */
public class ServicesFolderPicker extends BaseObjectPicker {

    /** Return value for SERVICES_FOLDER button pressed */
    public static final int SERVICES_FOLDER = IDialogConstants.CLIENT_ID + 2;

    private static final String WSDL_SPECIAL_FOLDER = "wsdl"; //$NON-NLS-1$    

    private Button servicesFolderbtn;

    @Override
    protected void updateButtonsEnableState(IStatus status) {
        Object object = getFirstResult();
        super.updateButtonsEnableState(status);
        if (object instanceof IProject || object instanceof SpecialFolder) {
            servicesFolderbtn.setEnabled(true);

        } else {
            servicesFolderbtn.setEnabled(false);
        }
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // if the CreateServicesFolder button is enabled only then create the
        // Service Descriptors Special Folder
        servicesFolderbtn =
                createButton(parent,
                        SERVICES_FOLDER,
                        Messages.ServicesFolderPicker_servicesFolder_button,
                        false);
        super.createButtonsForButtonBar(parent);
    }

    @Override
    protected void buttonPressed(int buttonId) {
        super.buttonPressed(buttonId);
        // creating the Service Descriptors Special Folder under the selected
        // project
        if (SERVICES_FOLDER == buttonId) {
            servicesFolderbtn.setEnabled(false);
            Object object = getFirstResult();
            if (object != null && object instanceof SpecialFolder) {
                object = ((SpecialFolder) object).getProject();
            }
            if (object != null && object instanceof IProject) {
                final IProject project = (IProject) object;
                BusyIndicator.showWhile(null, new Runnable() {
                    public void run() {
                        IFolder serviceFolder = null;
                        try {
                            boolean exists = true;
                            int index = 0;
                            while (exists) {
                                String scriptDescriptorName =
                                        Messages.GeneratedServicesFolderName;
                                if (index > 0) {
                                    scriptDescriptorName =
                                            scriptDescriptorName + index;
                                }
                                serviceFolder =
                                        project.getFolder(scriptDescriptorName);
                                if (serviceFolder.exists()) {
                                    exists = true;
                                } else {
                                    exists = false;
                                    serviceFolder.create(true,
                                            true,
                                            new NullProgressMonitor());
                                    serviceFolder =
                                            project
                                                    .getFolder(scriptDescriptorName);
                                    // normal folder will be created, if the
                                    // below is not done!
                                    ProjectConfig projectConfig =
                                            XpdResourcesPlugin.getDefault()
                                                    .getProjectConfig(project);
                                    if (projectConfig.getSpecialFolders()
                                            .getFolder(serviceFolder,
                                                    WSDL_SPECIAL_FOLDER) == null) {

                                        projectConfig.getSpecialFolders()
                                                .addFolder(serviceFolder,
                                                        WSDL_SPECIAL_FOLDER);

                                    }
                                    getTreeViewer().refresh();
                                }
                                index++;
                            }
                        } catch (CoreException e) {
                            Activator.getDefault().getLogger().error(e);
                        }
                    }
                });
            }
            servicesFolderbtn.setEnabled(true);
        }// else{
        // }
    }

    public ServicesFolderPicker(Shell parent) {
        super(parent);

        // Only show Service special folders
        addFilter(new SpecialFoldersOnlyFilter(Collections
                .singleton(WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND)) {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof IFile) {
                    return false;
                }

                return super.select(viewer, parentElement, element);
            }
        });

        setValidator(new ISelectionStatusValidator() {

            public IStatus validate(Object[] selection) {
                if (selection != null && selection.length == 1) {
                    if (selection[0] instanceof SpecialFolder) {
                        return new Status(IStatus.OK, Activator.PLUGIN_ID,
                                "", null); //$NON-NLS-1$
                    }
                }
                return new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        Messages.WsdlConfigurationPage_selectServicesFolder_message,
                        null);
            }

        });
    }

    @Override
    public void setInitialSelection(Object selection) {
        if (selection instanceof IPath) {
            String workspaceLocation =
                    ResourcesPlugin.getWorkspace().getRoot().getLocation()
                            .toPortableString();
            String folderPath = ((IPath) selection).toPortableString();
            folderPath = folderPath.replace(workspaceLocation, ""); //$NON-NLS-1$        	
            IPath path = new Path(folderPath);
            IResource resource =
                    ResourcesPlugin.getWorkspace().getRoot().findMember(path);

            if (resource instanceof IFolder) {
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(resource.getProject());

                if (config != null) {
                    SpecialFolder folder =
                            config.getSpecialFolders()
                                    .getFolder((IFolder) resource);

                    selection = folder != null ? folder : resource;
                }
            } else {
                selection = resource;
            }
        }
        super.setInitialSelection(selection);
    }

}
