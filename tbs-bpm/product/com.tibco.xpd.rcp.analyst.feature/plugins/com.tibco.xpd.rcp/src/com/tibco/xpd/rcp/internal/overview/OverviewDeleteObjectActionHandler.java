/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.navigator.actions.OMDeleteAction;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.ExtFolderResource;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.MAAResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.ribbon.RibbonConsts;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action handler for the delete action in the overview editor.
 * 
 * @author mtorres
 */
@SuppressWarnings("restriction")
/* public */class OverviewDeleteObjectActionHandler extends Action implements
        ISelectionChangedListener {

    private IAction globalAction;

    private Action deleteAction;

    private final ISelectionProvider provider;

    private final ProjectViewer projectViewer;

    private final ISelectionProvider selectionProvider;

    private final IWorkbenchPartSite site;

    /**
     * @param graphicalViewer
     */
    protected OverviewDeleteObjectActionHandler(IWorkbenchPartSite site,
            ISelectionProvider provider, ProjectViewer projectViewer) {
        this.site = site;
        selectionProvider = provider;
        this.provider = provider;
        this.projectViewer = projectViewer;
        provider.addSelectionChangedListener(this);
        globalAction =
                RCPActivator
                        .getGlobalAction(RibbonConsts.ACTION_DELETE.getId());
        setId(ActionFactory.DELETE.getId());
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        boolean success = false;
        ISelection selection = event.getSelection();

        if (selection instanceof IStructuredSelection) {

            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.getFirstElement() instanceof IProject) {
                IRCPContainer resource = RCPResourceManager.getResource();
                final int singleMaaProject = 1;
                DeleteProjectAction projectDA =
                        new DeleteProjectAction(site.getShell(), sel,
                                projectViewer);
                deleteAction = projectDA;
                /*
                 * XPD-4466:Enable the "Delete" button only when there are more
                 * than one MAA projects present. i.e. disable the button when a
                 * single MAA project is present.
                 */
                if (resource instanceof MAAResource
                        && resource.getProjectResources().size() > 1
                        || resource instanceof ExtFolderResource) {

                    success = true;
                }

            } else if (sel.getFirstElement() instanceof Package
                    || sel.getFirstElement() instanceof Model
                    || sel.getFirstElement() instanceof OrgModel) {
                IFile file =
                        WorkingCopyUtil
                                .getFile((EObject) sel.getFirstElement());
                if (file != null) {
                    DeleteResourceAction resourceDA =
                            new DeleteResourceAction(site);
                    resourceDA.selectionChanged(new StructuredSelection(file));
                    deleteAction = resourceDA;
                    success = true;
                }
            } else if (sel.getFirstElement() instanceof Process
                    || sel.getFirstElement() instanceof ProcessInterface) {
                DeleteAction bpmDA = new DeleteAction(site.getShell());
                bpmDA.selectionChanged(sel);
                deleteAction = bpmDA;
                success = true;
            } else if (sel.getFirstElement() instanceof Organization) {
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain((EObject) sel
                                .getFirstElement());
                if (ed != null) {

                    OMDeleteAction omDA = new OMDeleteAction(ed);
                    omDA.setShell(site.getShell());
                    omDA.setAskUserBeforeDelete();
                    omDA.selectionChanged(sel);
                    deleteAction = omDA;
                    success = true;
                }
            }
        }
        if (globalAction != null) {
            globalAction.setEnabled(success);
        }
        return;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (deleteAction != null) {
            deleteAction.run();
            // select the project
            provider.setSelection(selectionProvider.getSelection());
        }
    }

    /**
     * Stop listening of the model
     */
    public void dispose() {
        provider.removeSelectionChangedListener(this);
    }

    /**
     * @see org.eclipse.jface.action.Action#isEnabled()
     * 
     * @return
     */
    @Override
    public boolean isEnabled() {
        if (globalAction != null) {
            return globalAction.isEnabled();
        }
        return false;
    }

    /**
     * Action to delete a project.
     * 
     */
    private static class DeleteProjectAction extends Action {
        private final Shell shell;

        private final ProjectViewer viewer;

        private final IStructuredSelection selection;

        /**
         * Action to delete the given project.
         */
        public DeleteProjectAction(Shell shell, IStructuredSelection selection,
                ProjectViewer viewer) {
            this.shell = shell;
            this.selection = selection;
            this.viewer = viewer;
        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         * 
         */
        @Override
        public void run() {
            List<IProject> projects = new ArrayList<IProject>();

            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object next = iter.next();
                if (next instanceof IProject) {
                    projects.add((IProject) next);
                }
            }

            if (!projects.isEmpty()) {
                // Once the project has been deleted update the selection in the
                // project viewer to the index of this project
                int idx = viewer.getIndex(projects.get(0));

                RefactoringWizardOpenOperation op =
                        new RefactoringWizardOpenOperation(
                                new RCPDeleteProjectWizard(
                                        projects.toArray(new IProject[projects
                                                .size()])));
                try {
                    if (op.run(shell,
                            Messages.ProjectViewOverviewPage_deleteProject_dialog_title) == IDialogConstants.OK_ID) {
                        // Update the selection as the selected project has been
                        // deleted
                        viewer.setSelection(idx);
                    }

                } catch (InterruptedException e) {
                    // User probably cancelled - do nothing
                }
            }
        }
    }

}