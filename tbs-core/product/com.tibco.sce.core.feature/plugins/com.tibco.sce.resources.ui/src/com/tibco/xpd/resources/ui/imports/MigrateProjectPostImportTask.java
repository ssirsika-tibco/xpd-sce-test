/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui.imports;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Post project import task to check if project requires miration and to perform
 * that migration.
 * 
 * @author aallway
 * @since 13 Jan 2012
 */
public class MigrateProjectPostImportTask implements IPostImportProjectTask {

    /**
     * @see com.tibco.xpd.resources.ui.imports.IPostImportProjectTask#runPostImportTask(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void runPostImportTask(IProject project, IProgressMonitor monitor)
            throws CoreException {
        try {
            if (ProjectAssetMigrationManager.getInstance()
                    .doesProjectNeedMigrating(project)) {
                monitor.beginTask(Messages.MigrateProjectPostImportTask_MigrateToLatestVersion_message,
                        1);

                ProjectAssetMigrationManager.getInstance().migrate(project,
                        false,
                        SubProgressMonitorEx
                                .createSubTaskProgressMonitor(monitor, 1));
            }

        } finally {
            monitor.done();
        }
    }

}
