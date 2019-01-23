/*
 * Copyright (c) TIBCO Software Inc. 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport.importtasks;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Post import task to restore missing special folders in an imported project.
 * 
 * @author njpatel
 * @since 3.5.3
 */
public class RestoreSpecialFoldersPostImportProjectTask implements
        IPostImportProjectTask {

    public RestoreSpecialFoldersPostImportProjectTask() {
    }

    @Override
    public void runPostImportTask(IProject project, IProgressMonitor monitor)
            throws CoreException {

        /*
         * Restore all missing folders that are marked as special folders.
         */
        List<IFolder> specialFolders = getSpecialFolders(project);
        monitor.beginTask(Messages.RestoreSpecialFoldersPostImportProjectTask_restoreSpecialFolders_monitor_shortdesc,
                specialFolders.size());
        try {
            for (IFolder folder : specialFolders) {
                if (!folder.exists()) {
                    /*
                     * Ensure all parent folders are also created
                     */
                    ProjectUtil.createFolder(folder, false, null);
                }
                monitor.worked(1);
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Get all the expected special folders in the given project.
     * 
     * @param project
     * @return
     */
    private List<IFolder> getSpecialFolders(IProject project) {
        List<IFolder> folders = new ArrayList<IFolder>();

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && config.getSpecialFolders() != null) {
            for (SpecialFolder sf : config.getSpecialFolders().getFolders()) {
                if (sf.getLocation() != null) {
                    folders.add(project.getFolder(sf.getLocation()));
                }
            }
        }

        return folders;
    }

}
