/**
 *(c) Copyright 2009, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */
package com.tibco.xpd.ant.tasks;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

import com.tibco.amf.tools.packager.util.CmdLineUtils;
import com.tibco.xpd.n2.ant.tasks.AntTasksActivator;

/**
 * 
 * @author mtorres
 */
public class ImportProjectTask extends Task {

    private String allProjectsLoc;

    private boolean copyFiles = true;

    private boolean buildAfterImport = true;

    final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;

    @Override
    public void execute() throws BuildException {
        TaskUtil.initTargetPlatformIfNeeded(true);
        try {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        doImport(monitor);
                        if (isBuildAfterImport()) {
                            CmdLineUtils.buildAllProjects();
                        }
                    } catch (InvocationTargetException e) {
                        throw new CoreException(new Status(IStatus.ERROR,
                                AntTasksActivator.PLUGIN_ID, e.getMessage(), e));
                    } catch (InterruptedException e) {
                        throw new CoreException(new Status(IStatus.ERROR,
                                AntTasksActivator.PLUGIN_ID, e.getMessage(), e));
                    }
                }
            },
                    new ConsoleProgressMonitor());
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }



    @SuppressWarnings( { "unchecked", "restriction" })
    private void doImport(IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException,
            CoreException {
        if (null == monitor) {
            monitor = new NullProgressMonitor();
        }
        String allProjectsLoc = getAllProjectsLoc();
        if (allProjectsLoc != null) {
            File[] allProjectsInFolder = getAllProjectsInFolder(allProjectsLoc);
            if (allProjectsInFolder == null || allProjectsInFolder.length == 0) {
                throw new BuildException(
                        "No projects found to import from location: "
                                + allProjectsLoc);
            }
            for (File projectLoc : allProjectsInFolder) {
                IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                String projectName = null;

                if (projectLoc.exists()) {
                    File dotPrjFile = getDotProjectFile(projectLoc);
                    if (dotPrjFile == null) {
                        monitor.setTaskName(
                                "No project is found to import from location: "
                                        + projectLoc);
                        continue;
                    }else {
                        try {
                        IPath path = new Path(dotPrjFile.getPath());
                        IProjectDescription description =
                                IDEWorkbenchPlugin.getPluginWorkspace()
                                        .loadProjectDescription(path);
                        projectName = description.getName();
                        } catch (CoreException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                } else {
                    monitor.setTaskName(
                            "No project is found to import from location: "
                                    + projectLoc);
                    continue;
                }                

                monitor.beginTask(MessageFormat.format("Importing project ",
                        new Object[] { projectName }), 15);

                IProject project = root.getProject(projectName);

                if (isCopyFiles()) {
                    List filesToImport =
                            FileSystemStructureProvider.INSTANCE
                                    .getChildren(projectLoc);

                    IPath path = root.getFullPath().append(projectName);
                    ImportOperation op =
                            new ImportOperation(path, projectLoc,
                                    FileSystemStructureProvider.INSTANCE,
                                    new IOverwriteQuery() {
                                        public String queryOverwrite(
                                                String pathString) {
                                            return IOverwriteQuery.NO_ALL;
                                        }
                                    }, filesToImport);
                    op.setOverwriteResources(true);
                    op.setCreateContainerStructure(false);
                    op.run(monitor);
                    monitor.done();
                } else {
                    File dotProjectFile = null;
                    if (projectLoc.isDirectory()) {
                        File[] files = projectLoc.listFiles();
                        for (File file : files) {
                            if (file.isFile()
                                    && file
                                            .getName()
                                            .equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {
                                dotProjectFile = file;
                                break;
                            }
                        }
                    }
                    if (dotProjectFile != null) {
                        IProjectDescription description = null;
                        IPath path = new Path(dotProjectFile.getPath());
                        description =
                                ResourcesPlugin.getWorkspace()
                                        .loadProjectDescription(path);
                        project.create(description, new SubProgressMonitor(
                                monitor, 5));
                        project.open(new SubProgressMonitor(monitor, 5));
                        project.refreshLocal(IResource.DEPTH_INFINITE,
                                new SubProgressMonitor(monitor, 5));
                        monitor.done();
                    }
                }
            }
        } else {
            throw new BuildException(
                    "allProjectsLoc is null, please provide a value");
        }
    }

    private File[] getAllProjectsInFolder(String allProjectsLoc) {
        File parentFolder = new File(allProjectsLoc);
        if (parentFolder != null && parentFolder.exists()
                && parentFolder.isDirectory()) {
            return parentFolder.listFiles();
        }
        return new File[0];
    }

    private File getDotProjectFile(File projectLoc) {
        File prjFiles[] =
                projectLoc
                        .listFiles(createFileTypeFilter(IProjectDescription.DESCRIPTION_FILE_NAME));
        if (prjFiles.length != 0) {
            return prjFiles[0];
        }
        return null;
    }

    private FilenameFilter createFileTypeFilter(final String extension) {
        FilenameFilter fileNameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(extension.trim()))
                    return true;
                return false;
            }
        };
        return fileNameFilter;
    }

    public void setCopyFiles(boolean copyFiles) {
        this.copyFiles = copyFiles;
    }

    public boolean isCopyFiles() {
        return copyFiles;
    }

    public String getAllProjectsLoc() {
        return allProjectsLoc;
    }

    public void setAllProjectsLoc(String allProjectsLoc) {
        this.allProjectsLoc = allProjectsLoc;
    }

    public boolean isBuildAfterImport() {
        return buildAfterImport;
    }

    public void setBuildAfterImport(boolean buildAfterImport) {
        this.buildAfterImport = buildAfterImport;
    }
}
