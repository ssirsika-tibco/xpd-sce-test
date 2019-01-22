package com.tibco.xpd.bizassets.resources.migrate;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.bizassets.resources.Messages;
import com.tibco.xpd.resources.migrateproject.MigrateProject;

public class BusinessAssets extends MigrateProject {

    @Override
    public void migrate(IProject project, IProgressMonitor monitor)
            throws CoreException {
        SubProgressMonitor sub = new SubProgressMonitor(monitor, 2);
        sub.beginTask(Messages.BusinessAssets_StartingBusinessAssetsCreation_longdesc,
                2);
        sub.worked(1);
        doTask(project, sub);
    }

    private void doTask(IProject project, SubProgressMonitor monitor)
            throws CoreException {
        // // 1. Create folder
        IFolder folder =
                project.getFolder(BusinessAssetsConstants.BUSINESSASSETS_FOLDER);
        if (!folder.isAccessible()) {
            boolean force = true;
            boolean local = true;
            folder.create(force, local, null);
        }
        monitor.worked(1);
    }

}
