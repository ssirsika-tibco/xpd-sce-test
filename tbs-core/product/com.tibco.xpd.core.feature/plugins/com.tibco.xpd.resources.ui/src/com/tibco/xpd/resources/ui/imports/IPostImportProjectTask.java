/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui.imports;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Interface to be implemented by extensions of extension point
 * <code>com.tibco.xpd.resources.ui.postImportProjectTask</code> to run a post
 * project import task.
 * 
 * @author njpatel
 * @since 3.5.3
 */
public interface IPostImportProjectTask {

    /**
     * Task to run after the given project has been imported into the workspace.
     * Note that this is already running in a workspace modify operation.
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    void runPostImportTask(IProject project, IProgressMonitor monitor)
            throws CoreException;
}
