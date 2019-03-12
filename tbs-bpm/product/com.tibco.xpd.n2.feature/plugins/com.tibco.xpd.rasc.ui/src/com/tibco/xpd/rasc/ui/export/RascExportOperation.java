/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.tibco.xpd.rasc.core.RascActivator;
import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.exception.RascGenerationException;
import com.tibco.xpd.rasc.ui.Messages;

/**
 * Operation to export projects.
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class RascExportOperation implements IRunnableWithProgress {

    /**
     * The dialog showing the progress status.
     */
    private ExportProgressMonitorDialog dialog;

    /**
     * The projects to export.
     */
    private List<IProject> projects;

    /**
     * The absolute or project relative path.
     */
    private String path;

    /**
     * True if the path is relative to the project root, false if it is
     * absolute.
     */
    private boolean isProjectRelative;

    /**
     * @param projects
     *            The projects to export.
     */
    public RascExportOperation(ExportProgressMonitorDialog dialog,
            List<IProject> projects, String path, boolean isProjectRelative) {
        this.dialog = dialog;
        this.projects = projects;
        this.path = path;
        this.isProjectRelative = isProjectRelative;
    }

    /**
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void run(IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException {
        RascController controller =
                RascActivator.getDefault().getRascController();
        monitor.beginTask(Messages.RascExportOperation_ProgressTitle,
                projects.size() * 2);
        for (IProject project : projects) {
            dialog.setStatus(project, ""); //$NON-NLS-1$
        }
        for (IProject project : projects) {
            dialog.setStatus(project,
                    Messages.RascExportOperation_ValidatingStatus);
            Thread.sleep(2000);
            dialog.setStatus(project, Messages.RascExportOperation_ValidStatus);
            monitor.worked(1);
        }
        Thread.sleep(2000);
        for (IProject project : projects) {
            dialog.setStatus(project,
                    Messages.RascExportOperation_ExportingStatus);
            try {
                controller.generateRasc(project, getPath(project), null);
                dialog.setStatus(project,
                        Messages.RascExportOperation_CompleteStatus);
            } catch (RascGenerationException e) {
                dialog.setStatus(project, Messages.RascExportOperation_ErrorStatus);
            }
            monitor.worked(1);
        }
        monitor.done();
    }

    /**
     * @param project
     * @return
     */
    private IFile getPath(IProject project) {
        IFile target = null;
        if (isProjectRelative) {
            target = project.getFolder(path)
                    .getFile(project.getName() + ".rasc"); //$NON-NLS-1$
        } else {

        }
        return target;
    }

}
