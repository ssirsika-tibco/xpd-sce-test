/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * This interface should be implemented by any export wizard that requires to
 * carry out additional operations on export. Subtasks will be executed after
 * every resource in the export selection has been exported.
 * <p>
 * Subtasks will be registered using the <code>registerSubTask</code> method
 * of the export wizard.
 * </p>
 * <p>
 * NOTE: If the export destination is the project exports folder and more
 * resources are added under here by the sub tasks then the workspace doesn't
 * need to be synched. This will be done by the export wizard once all
 * registered sub tasks have been executed.
 * </p>
 * 
 * @author njpatel
 * 
 */
public interface ExportSubTask {

    /**
     * This method will be executed after the export of each selected resource
     * in the export wizard.
     * 
     * @param monitor
     *            Progress monitor
     * @param inputFile
     *            The file being processed.
     * @param outputFile
     *            The processed file.This will be a <code>java.io.File</code>.
     * 
     * @throws CoreException
     */
    public void perform(SubProgressMonitor monitor, final IFile inputFile,
            final File outputFile) throws CoreException;

    /**
     * Dispose resources
     */
    public void dispose();
}
