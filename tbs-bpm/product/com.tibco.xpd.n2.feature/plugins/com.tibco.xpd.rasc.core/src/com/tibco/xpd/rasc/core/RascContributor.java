/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A contract interface for the components wishing to contribute artifacts to
 * the RASC generated for a given project.
 * <p>
 * Because a given implementation could be called by several threads, the
 * implementations of this interface must be thread-safe. Principally, this
 * means they must be stateless, or carry their state in some sort of context
 * class.
 *
 * @author pwatson
 * @since 26 Feb 2019
 */
public interface RascContributor {
    /**
     * A unique identifier of this RASC contributor. Used to identify
     * contributor dependencies.
     * 
     * @return the unique identifier for this contributor implementation.
     */
    public String getId();

    /**
     * Invoked by the RascController to determine if the contributor has any
     * contributions to include in the RASC for the given IProject.
     * 
     * @param aProject
     *            the project from which the RASC content is to be generated.
     * @return <code>true</code> if the contributor would generate any
     *         contributions for the given project should its process() method
     *         be called.
     */
    public boolean hasContributionsFor(IProject aProject);

    /**
     * Invoked by the RascController to allow the contributor to generate (from
     * the given IProject) whatever content it wishes and add it to the RASC via
     * the given RascWriter.
     * 
     * @param aProject
     *            the project from which the RASC content is to be generated.
     * @param aProgressMonitor
     *            the progress monitor to use for reporting progress to the
     *            user. It is the caller's responsibility to call done() on the
     *            given monitor. Accepts null, indicating that no progress
     *            should be reported and that the operation cannot be cancelled.
     * @param aWriter
     *            a writer to allow the contributor to add content to the RASC.
     * @throws Exception
     *             any exception that the contributor raises will be logged and
     *             wrapped in a RascContributionException, before being rethrown
     *             by the RascController.
     */
    public void process(IProject aProject, IProgressMonitor aProgressMonitor,
            RascWriter aWriter) throws Exception;
}
