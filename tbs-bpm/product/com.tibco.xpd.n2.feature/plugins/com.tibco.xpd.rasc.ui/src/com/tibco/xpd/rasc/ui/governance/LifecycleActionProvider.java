package com.tibco.xpd.rasc.ui.governance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * Action provider for the project lifecycle governance context menu
 * contributions.
 *
 * @author nwilson
 * @since 19 Jul 2019
 */
public class LifecycleActionProvider extends CommonActionProvider {

    /**
     * The service for querying and managing governance states.
     */
    private GovernanceStateService gss;

    /**
     * Service for governance state related UI utilities.
     */
    private GovernanceStateUIService gsus;

    /**
     * Constructor.
     */
    public LifecycleActionProvider() {
        gss = new GovernanceStateService();
        gsus = new GovernanceStateUIService();
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     * 
     * @param menu
     *            The menu manager.
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        AtomicBoolean hasLockedProjects = new AtomicBoolean();
        AtomicBoolean hasUnlockedProjects = new AtomicBoolean();
        try {
            IStructuredSelection selection = getSelection();
            List<IProject> projects = getSelectedProjects(selection);
            checkSelectedProjects(projects, hasLockedProjects, hasUnlockedProjects);
            // If we have any projects selected add the menu options.
            if (hasLockedProjects.get() || hasUnlockedProjects.get()) {
                MenuManager subMenu = new MenuManager(Messages.LifecycleActionProvider_DeploymentMenuLabel,
                        RascUiActivator.getImageDescriptor(RascUiConstants.ICON_DEPLOYMENT), null);

                GenerateArtifactsAction generateDraftArtifacts = new GenerateArtifactsAction(
                        Messages.LifecycleActionProvider_GenerateDraftMenuLabel, selection, false);
                LockForProductionAction lockForProduction = new LockForProductionAction(
                        Messages.LifecycleActionProvider_LockForProductionMenuLabel, gss, gsus, projects);
                GenerateArtifactsAction generateProductionArtifacts = new GenerateArtifactsAction(
                        Messages.LifecycleActionProvider_GenerateProductionMenuLabel, selection, true);
                CreateNewDraftAction createNewDraft =
                        new CreateNewDraftAction(Messages.LifecycleActionProvider_CreateDraftMenuLabel, gss, gsus,
                                projects);

                subMenu.add(generateDraftArtifacts);
                subMenu.add(lockForProduction);
                subMenu.add(generateProductionArtifacts);
                subMenu.add(createNewDraft);

                // If any projects are locked disable Generate Draft and Lock
                // actions
                if (hasLockedProjects.get()) {
                    generateDraftArtifacts.setEnabled(false);
                    lockForProduction.setEnabled(false);
                }
                // If any projects are unlocked disable Generate Production and
                // Create New Draft actions
                if (hasUnlockedProjects.get()) {
                    generateProductionArtifacts.setEnabled(false);
                    createNewDraft.setEnabled(false);
                }
                menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
            }
        } catch (CoreException e) {
            // Couldn't read status, don't add the menu.
            RascUiActivator.getLogger().error(e);
        }
    }

    /**
     * Get the IStructuredSelection for the currently selected projects.
     * 
     * @return the current selection.
     */
    private IStructuredSelection getSelection() {
        IStructuredSelection ss = null;
        ActionContext ctx = getContext();
        if (ctx != null) {
            ISelection selection = ctx.getSelection();
            if (selection instanceof IStructuredSelection) {
                ss = (IStructuredSelection) selection;
            }
        }
        return ss;
    }

    /**
     * Get a list of selected projects from an IStructuredSelection.
     * 
     * @param selection
     *            The selection.
     * @return The project list.
     */
    private List<IProject> getSelectedProjects(IStructuredSelection selection) {
        List<IProject> projects = new ArrayList<>();
        for (Object next : selection.toList()) {
            if (next instanceof IProject) {
                projects.add((IProject) next);
            }
        }
        return projects;
    }

    /**
     * Check whether a list of projects contains Locked projects, Unlocked
     * projects or both.
     * 
     * @param projects
     *            The list of projects to check.
     * @param hasLockedProjects
     *            Will be set to true if the project contains Unlocked projects.
     * @param hasUnlockedProjects
     *            Will be set to true if the project contains Locked projects.
     * @throws CoreException
     *             If there was a problem checking a project governance state.
     */
    private void checkSelectedProjects(List<IProject> projects, AtomicBoolean hasLockedProjects,
            AtomicBoolean hasUnlockedProjects) throws CoreException {
        for (IProject project : projects) {
            if (gss.isLockedForProduction(project)) {
                hasLockedProjects.set(true);
            } else {
                hasUnlockedProjects.set(true);
            }
        }
    }

}
