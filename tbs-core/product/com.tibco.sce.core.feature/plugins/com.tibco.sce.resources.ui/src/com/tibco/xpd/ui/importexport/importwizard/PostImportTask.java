/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface should be implemented by any import wizard that require to
 * carry out additional operations each import's output file after ALL have been
 * created (as opposed to ImportSubTask which runs directly on each individual
 * file after it is imported).
 * <p>
 * Subtasks will be registered using the <code>registerPostImportTask</code>
 * method of the import wizard.
 * </p>
 * 
 * @author aallway
 * @since 01-Nov-12
 */
public interface PostImportTask {

    /**
     * This method will be executed after the import of each selected resource
     * in the import wizard.
     * <p>
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
    public void perform(IProgressMonitor monitor, final IFile outputFile)
            throws CoreException;

    /**
     * Dispose resources
     */
    public void dispose();
}
