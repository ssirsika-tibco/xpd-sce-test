/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.ui.IRefreshableTitle;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Used for querying and setting the governance state of a project.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class GovernanceStateService {

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
        if (!project.isAccessible()) {
            return false;
        }
        return project.hasNature(XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
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
        ProjectUtil.addNature(project, XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
        refreshEditorLabels();
    }

    /**
     * 
     */
    private void refreshEditorLabels() {
        for (IWorkbenchPage page:PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()) {
            for (IEditorReference ref:page.getEditorReferences()) {
                IEditorPart editor = ref.getEditor(false);
                if (editor instanceof IRefreshableTitle) {
                    ((IRefreshableTitle) editor).refreshTitle();
                }
            }
        }
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
        ProjectUtil.removeNature(project, XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
        refreshEditorLabels();
        // FIXME Increment the version as part of ACE-2032
    }
}
