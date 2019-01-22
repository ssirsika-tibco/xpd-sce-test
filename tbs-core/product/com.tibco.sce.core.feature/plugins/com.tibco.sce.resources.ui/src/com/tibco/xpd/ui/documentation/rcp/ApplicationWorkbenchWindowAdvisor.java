/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.rcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.actions.ExportDocAction;

/**
 * @author mtorres
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private String projectName = null;

    private IPath outputPath = null;

    public ApplicationWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer, String projectName,
            IPath outputPath) {
        super(configurer);
        this.projectName = projectName;
        this.outputPath = outputPath;
    }

    /**
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#postWindowOpen()
     * 
     */
    @Override
    public void postWindowCreate() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        List<IResource> selectedResources = getSelectedResources(projectName);
        if (selectedResources != null && !selectedResources.isEmpty()) {
            final ExportDocAction action = new ExportDocAction();
            action.setOutputPath(outputPath);
            action.setSelectedResources(selectedResources);
            try {
                action.run(new NullProgressMonitor());
                BrowserControl.displayURL(action.getOutputPath()
                        .append("index.html").toPortableString());//$NON-NLS-1$
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out
                    .println(Messages.ApplicationWorkbenchWindowAdvisor_ResourcesNotFound);
        }
        configurer.getWindow().getWorkbench().close();
        throw new RuntimeException("This is expected exception. Please ignore."); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
     * 
     * @param configurer
     * @return
     */
    @Override
    public ActionBarAdvisor createActionBarAdvisor(
            IActionBarConfigurer configurer) {
        return new ActionBarAdvisor(configurer) {
            @Override
            public void fillActionBars(int flags) {
                // DO NOTHING
            }
        };
    }

    private List<IResource> getSelectedResources(String projectName) {
        if (projectName != null) {
            if (!isAbsolutePath(projectName)) {
                return getSelectedResourcesForProjectName(projectName);
            } else {
                return getSelectedResourcesForProjectPath(projectName);
            }
        }
        return Collections.emptyList();
    }

    private List<IResource> getSelectedResourcesForProjectName(
            String projectName) {
        IProject aProject =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (aProject != null) {
            if (!aProject.exists()) {
                System.out
                        .println(Messages.ApplicationWorkbenchWindowAdvisor_ProjectNotExist);
            }
            if (!aProject.isOpen()) {
                try {
                    System.out
                            .println(Messages.ApplicationWorkbenchWindowAdvisor_ClosedProject);
                    aProject.open(new NullProgressMonitor());
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(aProject);
            if (projectConfig != null) {
                SpecialFolders specialFolders =
                        projectConfig.getSpecialFolders();
                // Get all resources for special folders
                if (specialFolders != null) {
                    EList<SpecialFolder> folders = specialFolders.getFolders();
                    if (folders != null && !folders.isEmpty()) {
                        List<IResource> selectedFiles =
                                new ArrayList<IResource>();
                        for (SpecialFolder specialFolder : folders) {
                            String kind = specialFolder.getKind();
                            if (kind != null) {
                                List<IResource> allDeepResourcesInSpecialFolderOfKind =
                                        SpecialFolderUtil
                                                .getAllDeepResourcesInSpecialFolderOfKind(aProject,
                                                        kind,
                                                        null,
                                                        false);
                                if (allDeepResourcesInSpecialFolderOfKind != null
                                        && !allDeepResourcesInSpecialFolderOfKind
                                                .isEmpty()) {
                                    selectedFiles
                                            .addAll(allDeepResourcesInSpecialFolderOfKind);
                                }
                            }
                        }
                        return selectedFiles;
                    }
                } else {
                    System.out
                            .println(Messages.ApplicationWorkbenchWindowAdvisor_SpecialFolderNull);
                }
            } else {
                System.out
                        .println(Messages.ApplicationWorkbenchWindowAdvisor_ProjectConfigNull);
            }
        } else {
            System.out
                    .println(Messages.ApplicationWorkbenchWindowAdvisor_ProjectNull);
        }
        return Collections.emptyList();
    }

    private boolean isAbsolutePath(String projectName) {
        IPath projectPath = new Path(projectName);
        return projectPath.isAbsolute();
    }

    private List<IResource> getSelectedResourcesForProjectPath(
            String projectPath) {
        // Switch autobuilding off otherwise we get a workspace lock
        IWorkspaceDescription description =
                ResourcesPlugin.getWorkspace().getDescription();
        if (description != null) {
            description.setAutoBuilding(false);
        }
        try {
            ResourcesPlugin.getWorkspace().setDescription(description);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        ProjectImporter projectImporter =
                ProjectImporter.createFolderProjectImporter(projectName);
        if (projectImporter.performImport()) {
            IProjectDescription projectDescription =
                    projectImporter
                            .getProjectDescription(new Path(projectPath));
            if (projectDescription != null
                    && projectDescription.getName() != null) {
                return getSelectedResourcesForProjectName(projectDescription
                        .getName());
            }
        } else {
            System.out
                    .println(Messages.ApplicationWorkbenchWindowAdvisor_ProjectNotExist);
        }
        return Collections.emptyList();
    }

}
