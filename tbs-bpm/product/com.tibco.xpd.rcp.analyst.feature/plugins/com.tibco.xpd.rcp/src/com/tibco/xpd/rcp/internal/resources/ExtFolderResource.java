/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager.ProjectCompatibilityWithCode;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * This manages the opening of projects from a given folder location.
 * 
 * @author njpatel
 */
public class ExtFolderResource extends AbstractRCPContainer {

    private final File folder;

    /**
     * 
     */
    public ExtFolderResource(File folder) {
        this.folder = folder;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return folder != null ? folder.getName() : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getPath()
     * 
     * @return
     */
    @Override
    public IPath getPath() {
        return folder != null ? new Path(folder.getAbsolutePath()) : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#doCreateProjectResource(java.lang.String)
     * 
     * @param name
     * @return
     */
    @Override
    protected ProjectResource doCreateProjectResource(String name) {
        if (name != null) {

            IPath location = null;

            if (folder.isDirectory()) {
                /*
                 * Check if the folder has a .project file in it.
                 */
                File dotProjectFile = new File(folder, ".project"); //$NON-NLS-1$

                if (dotProjectFile != null && dotProjectFile.exists()
                        && dotProjectFile.isFile()) {
                    /*
                     * XPD-7442: If the selected directory has .project file in
                     * it that means that the user has selected a project ot
                     * open instead of folder which contains project, hence we
                     * cannot create a new project under the directory because
                     * that would create a project inside a project which is
                     * incorrect. Hence we get the parent directory and create
                     * the new project inside it. So basically the new project
                     * would be a sibling of the opened project.
                     */
                    File parentFile = folder.getParentFile();

                    if (parentFile != null && parentFile.isDirectory()) {
                        /*
                         * get the parent directory location
                         */
                        location =
                                new Path(parentFile.getAbsolutePath())
                                        .append(name);
                    }
                } else {
                    /*
                     * this means that the selected folder/directory was not a
                     * Project and hence it is safe to create the new project
                     * under the directory.
                     */
                    location = new Path(folder.getAbsolutePath()).append(name);
                }
            }
            if (location != null) {
                // Create project in the external folder location

                return RCPResourceFactory.createProjectResource(name, location);
            } else {
                /*
                 * For any reason if the location for the new project is null
                 * the we do 'RCPResourceFactory.createProjectResource(name)'
                 * because that would create the new project directly under the
                 * 'static' analyst workspace(see javadoc of
                 * ProjectResource.getProjectDescription(...) ) hence the new
                 * project would still be created BUT in the static
                 * workspace.(win-win)
                 */
                return RCPResourceFactory.createProjectResource(name);
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#introspect()
     * 
     * @return
     */
    @Override
    public List<String> introspect(IProgressMonitor monitor) {
        List<File> projects = getProjects(folder, monitor);
        List<String> projectNames = new ArrayList<String>(projects.size());

        for (File project : projects) {
            projectNames.add(project.getName());
        }

        return projectNames;
    }

    /**
     * Get all folders that are Eclipse projects from the given folder location.
     * 
     * @param folder
     * @param monitor
     * @return
     */
    public static List<File> getProjects(File folder, IProgressMonitor monitor) {
        monitor.beginTask(Messages.ExtFolderResource_searchingForProjects_monitor_shortdesc,
                IProgressMonitor.UNKNOWN);
        List<File> studioProjects = new ArrayList<File>();
        try {
            getStudioProjects(folder, studioProjects, monitor);
        } finally {
            monitor.done();
        }

        return studioProjects;
    }

    /**
     * Get all Studio projects from the given folder location. This will recurse
     * through any sub-folders found.
     * 
     * @param folder
     * @param projects
     */
    private static void getStudioProjects(File folder, List<File> projects,
            IProgressMonitor monitor) {
        /*
         * If the given folder itself is a project then try to open that,
         * otherwise look at subfolders for projects
         */

        monitor.subTask(folder.getAbsolutePath());

        if (folder.isDirectory() && isProject(folder)) {
            if (isStudioProject(folder)) {
                projects.add(folder);
            }
        } else {
            for (File child : folder.listFiles()) {
                if (child.isDirectory()) {
                    if (isProject(child)) {
                        if (isStudioProject(child)) {
                            projects.add(child);
                        }
                    } else {
                        // Recurse into the folder to see if it contains
                        // projects
                        getStudioProjects(child, projects, monitor);
                    }
                }

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
        }
    }

    /**
     * Check if the given folder is a Studio project.
     * 
     * @param child
     * @return
     */
    private static boolean isStudioProject(File folder) {
        /*
         * Check if a .project file exists under the given folder. If it does
         * then this is an Eclipse project.
         */
        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) {
                if (child.isFile()
                        && XpdResourcesPlugin.PROJECTCONFIGFILE.equals(child
                                .getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if this folder is an Eclipse project.
     * 
     * @param folder
     *            a directory
     * @return
     */
    private static boolean isProject(File folder) {
        /*
         * Check if a .project file exists under the given folder. If it does
         * then this is an Eclipse project.
         */
        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) {
                if (child.isFile()
                        && IProjectDescription.DESCRIPTION_FILE_NAME
                                .equals(child.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPContainer#doOpen(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    public boolean doOpen(IProgressMonitor monitor) throws CoreException {
        List<File> projects = getProjects(folder, monitor);

        if (projects.isEmpty()) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            RCPActivator.PLUGIN_ID,
                            String.format(Messages.ExtFolderResource_noProjectsFound_error_longdesc,
                                    folder.getAbsolutePath())));
        }

        monitor.beginTask(String
                .format(Messages.ExtFolderResource_opening_monitor_shortdesc,
                        folder.getName()), projects.size() * 10);

        try {
            List<IProject> iProjects = new ArrayList<IProject>();
            for (File file : projects) {
                ProjectResource projectResource =
                        RCPResourceFactory
                                .createProjectResource(getProjectName(file),
                                        new Path(file.getAbsolutePath()));

                projectResource.open(new SubProgressMonitorEx(monitor, 5));

                addProjectResource(projectResource);
                iProjects.add(projectResource.getProject());
            }

            /*
             * Make sure these projects are not from a later version of Studio.
             * If any are then throw exception
             */
            boolean isMigrationRequired = false;
            for (IProject project : iProjects) {
                ProjectCompatibilityWithCode code =
                        ProjectAssetMigrationManager.getInstance()
                                .getProjectCompatibilityWithCode(project);

                if (code == ProjectCompatibilityWithCode.NEWER) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    RCPActivator.PLUGIN_ID,
                                    String.format(Messages.MAAResource_incompatibleVersion_error_longdesc,
                                            getName())));
                } else if (code == ProjectCompatibilityWithCode.OLDER) {
                    isMigrationRequired = true;
                }
            }

            if (isMigrationRequired) {
                if (canMigrate()) {
                    runPostImportTasks(iProjects, new SubProgressMonitorEx(
                            monitor, 5));
                } else {
                    // User decided not to migrate
                    return false;
                }
            }
        } finally {
            monitor.done();
        }

        return true;
    }

    /**
     * Get the name of the project from the project description. If no project
     * description is found then this will return the name of the file (folder)
     * passed.
     * 
     * @param projectRoot
     *            the project root folder.
     * @return
     */
    @SuppressWarnings("restriction")
    private String getProjectName(File projectRoot) {

        IPath path =
                new Path(projectRoot.getAbsolutePath())
                        .append(IProjectDescription.DESCRIPTION_FILE_NAME);
        File file = path.toFile();

        if (file != null && file.exists()) {
            try {
                IProjectDescription description =
                        IDEWorkbenchPlugin.getPluginWorkspace()
                                .loadProjectDescription(path);

                if (description != null) {
                    return description.getName();
                }
            } catch (CoreException e) {
                // Do nothing
            }
        }

        return projectRoot.getName();
    }

    /**
     * @return
     */
    private boolean canMigrate() {
        final Boolean[] migrate = new Boolean[] { false };
        final Display display = XpdResourcesPlugin.getStandardDisplay();
        display.syncExec(new Runnable() {

            @Override
            public void run() {
                migrate[0] =
                        MessageDialog.openQuestion(display.getActiveShell(),
                                Messages.ExtFolderResource_migrateProjects_dialog_title,
                                Messages.ExtFolderResource_migrateProjects_dialog_longdesc);
            }
        });
        return migrate[0];
    }

    /**
     * Run the post-import tasks on the given projects. This will fix typical
     * issues after import (e.g. missing special folders, builder/nature issues)
     * and also migrate the project if required.
     * 
     * @param projects
     *            projects being opened (imported)
     * @param monitor
     * @return
     */
    private IStatus runPostImportTasks(List<IProject> projects,
            IProgressMonitor monitor) {
        return PostImportUtil.getInstance().performPostImportTasks(projects,
                monitor);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPResource#doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected boolean doSave(IProgressMonitor monitor) throws CoreException {
        Collection<ProjectResource> projectResources = getProjectResources();
        monitor.beginTask(Messages.ExtFolderResource_saving_monitor_shortdesc,
                projectResources.size());
        try {
            boolean projectSaved = false;
            for (ProjectResource project : projectResources) {
                projectSaved |=
                        project.save(new SubProgressMonitorEx(monitor, 1));
            }

            if (!projectSaved) {
                // No projects have been saved as none was dirty
                return false;
            }
        } finally {
            monitor.done();
        }
        return true;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ExtFolderResource) {
            return ((ExtFolderResource) obj).folder.equals(folder);
        }
        return super.equals(obj);
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return folder.hashCode();
    }
}
