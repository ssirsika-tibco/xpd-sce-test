/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.rasc.ui.export;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

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
     * @param projects
     *            The projects to export.
     */
    public RascExportOperation(ExportProgressMonitorDialog dialog,
            List<IProject> projects) {
        this.dialog = dialog;
        this.projects = projects;
    }

    /**
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void run(IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException {
        monitor.beginTask("Export RASC", projects.size() * 2);
        for (IProject project : projects) {
            dialog.setStatus(project, "");
        }
        for (IProject project : projects) {
            dialog.setStatus(project, "Validating");
            Thread.sleep(2000);
            dialog.setStatus(project, "Valid");
            monitor.worked(1);
        }
        Thread.sleep(2000);
        for (IProject project : projects) {
            dialog.setStatus(project, "Exporting");
            Thread.sleep(2000);
            dialog.setStatus(project, "Complete");
            monitor.worked(1);
        }
        monitor.done();
    }

}
