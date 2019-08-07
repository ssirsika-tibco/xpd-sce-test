/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.rasc.core.governance.GovernanceStateService;
import com.tibco.xpd.rasc.ui.RascUiActivator;

/**
 * Menu action to lock projects for production.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class LockForProductionAction extends BaseSelectionListenerAction {

    private GovernanceStateService gss;

    private GovernanceStateUIService gsus;

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
    protected LockForProductionAction(String text, GovernanceStateService gss, GovernanceStateUIService gsus,
            List<IProject> projects) {
        super(text);
        this.gss = gss;
        this.gsus = gsus;
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
                gss.lockForProduction(project);
                gsus.refreshEditorLabels();
            } catch (CoreException e) {
                RascUiActivator.getLogger().error("Could not lock project " + project.getName()); //$NON-NLS-1$
            }
        }
    }

}
