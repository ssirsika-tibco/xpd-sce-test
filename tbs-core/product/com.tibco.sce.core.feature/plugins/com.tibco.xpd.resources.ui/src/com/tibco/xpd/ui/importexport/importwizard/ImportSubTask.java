/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * This interface should be implemented by any import wizard that require to
 * carry out additional operations on import. This task will be executed after
 * every resource in the import selection has been imported.
 * <p>
 * Subtasks will be registered using the <code>registerSubTask</code> method
 * of the import wizard.
 * </p>
 * 
 * @author njpatel
 * 
 */
public interface ImportSubTask {

    /**
     * This method will be executed after the import of each selected resource
     * in the import wizard.
     * 
     * @param monitor
     *            Progress monitor
     * @param inputFile
     *            The file being processed.
     * @param outputFile
     *            The processed file.This will be a <code>IFile</code>.
     * 
     * @throws CoreException
     */
    public void perform(SubProgressMonitor monitor, final File inputFile,
            final IFile outputFile) throws CoreException;

    /**
     * Dispose resources
     */
    public void dispose();
}
