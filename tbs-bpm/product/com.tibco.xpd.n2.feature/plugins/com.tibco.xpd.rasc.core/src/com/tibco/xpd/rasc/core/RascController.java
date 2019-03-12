/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.rasc.core.exception.RascGenerationException;

/**
 * Implementations are responsible for co-ordinating the generation of
 * deployment RASCs from given projects.
 *
 * @author pwatson
 * @since 26 Feb 2019
 */
public interface RascController {
    /**
     * Generates a deployment RASC for the given project and outputs it to the
     * given File.
     * 
     * @param aProject
     *            the project for which the RASC is to be generated.
     * @param aFile
     *            the file to which the RASC is to be written, overwriting any
     *            existing file content.
     * @param aProgressMonitor
     *            allows the RascController and the RascContributors to convey
     *            their progress to the caller.
     * @throws RascGenerationException
     */
    public void generateRasc(IProject aProject, IFile aFile,
            IProgressMonitor aProgressMonitor)
            throws RascGenerationException;

    /**
     * Generates a deployment RASC for the given project and outputs it to the
     * given File.
     * 
     * @param aProject
     *            the project for which the RASC is to be generated.
     * @param aFile
     *            the file to which the RASC is to be written, overwriting any
     *            existing file content.
     * @param aProgressMonitor
     *            allows the RascController and the RascContributors to convey
     *            their progress to the caller.
     * @throws RascGenerationException
     */
    public void generateRasc(IProject aProject, File aFile,
            IProgressMonitor aProgressMonitor) throws RascGenerationException;
}
