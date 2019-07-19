/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Used for querying and setting the governance state of a project.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class GovernanceStateService {

    /**
     * Nature ID used to mark projects as Locked for Production.
     */
    private static final String LOCKED_FOR_PRODUCTION_NATURE = "com.tibco.xpd.resources.lockedForProductionNature"; //$NON-NLS-1$

    /**
     * Checks if a project is Locked for Production.
     * 
     * @param project
     *            The project to check.
     * @return true if it is locked.
     * @throws CoreException
     *             if the state could not be checked.
     */
    boolean isLockedForProduction(IProject project) throws CoreException {
        return project.hasNature(LOCKED_FOR_PRODUCTION_NATURE);
    }

    /**
     * Sets a project as Locked for Production.
     * 
     * @param project
     *            The project to lock.
     * @throws CoreException
     *             if the project could not be locked.
     */
    void lockForProduction(IProject project) throws CoreException {
        ProjectUtil.addNature(project, LOCKED_FOR_PRODUCTION_NATURE);
    }

    /**
     * Creates a new draft of a project by unlocking it and incrementing the
     * minor version.
     * 
     * @param project
     *            The project to create a new draft for.
     * @throws CoreException
     *             if the project could not be unlocked.
     */
    void createNewDraft(IProject project) throws CoreException {
        ProjectUtil.removeNature(project, LOCKED_FOR_PRODUCTION_NATURE);
        // FIXME Increment the version as part of ACE-2032
    }
}
