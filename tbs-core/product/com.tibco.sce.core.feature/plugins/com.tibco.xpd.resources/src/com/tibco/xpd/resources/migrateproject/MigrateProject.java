/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.migrateproject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Abstract class that will be extended by
 * <code>com.tibco.xpd.resources.migrateProject</code> extension to contribute
 * to the migration of a project from an earlier version of Studio.
 * 
 * @author njpatel
 * 
 */
public abstract class MigrateProject {

    private IAction action;

    private IWorkbenchPart targetPart;

    /**
     * Default constructor.
     */
    public MigrateProject() {

    }

    /**
     * Set the action proxy that handles the presentation portion of the action.
     * 
     * @param action
     *            Action proxy.
     */
    public void setAction(IAction action) {
        this.action = action;
    }

    /**
     * Sets the active part for the delegate. The active part is commonly used
     * to get a working context for the action, such as the shell for any dialog
     * which is needed.
     * 
     * @param targetPart
     *            Active part of the delegate.
     */
    public void setTargetPart(IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    /**
     * Get the action proxy that handles the presentation portion of the action.
     * 
     * @return Action proxy.
     */
    protected IAction getAction() {
        return action;
    }

    /**
     * Get the active part of the delegate.
     * 
     * @return Active part.
     */
    protected IWorkbenchPart getTargetPart() {
        return targetPart;
    }

    /**
     * Migrate the Studio 1.1 <i>project</i> to the latest version. This method
     * is called from within a <code>{@link WorkspaceModifyOperation}</code>.
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    public abstract void migrate(IProject project, IProgressMonitor monitor)
            throws CoreException;

}
