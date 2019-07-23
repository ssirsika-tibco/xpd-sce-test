/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.rasc.ui.RascUiActivator;

/**
 * Menu action to create a new draft of a project.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class CreateNewDraftAction extends BaseSelectionListenerAction {

    private GovernanceStateService gss;

    private List<IProject> projects;

    /**
     * Constructor for the action.
     * 
     * @param text
     *            The action label.
     * @param gss
     *            The governance state service.
     * @param projects
     *            The selected projects.
     */
    protected CreateNewDraftAction(String text, GovernanceStateService gss, List<IProject> projects) {
        super(text);
        this.gss = gss;
        this.projects = projects;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     *
     */
    @Override
    public void run() {
        for (IProject project : projects) {
            try {
                gss.createNewDraft(project);
            } catch (CoreException e) {
                RascUiActivator.getLogger().error("Could not unlock project " + project.getName()); //$NON-NLS-1$
            }
        }

    }

}
