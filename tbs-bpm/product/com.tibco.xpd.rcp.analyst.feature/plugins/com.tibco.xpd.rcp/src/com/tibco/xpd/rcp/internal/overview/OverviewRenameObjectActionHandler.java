/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.actions.RenameProjectAction;
import com.tibco.xpd.rcp.ribbon.RibbonConsts;

/**
 * Action handler for the rename action in the overview editor.
 * 
 * @author mtorres
 * 
 */
/* public */class OverviewRenameObjectActionHandler extends Action implements
        ISelectionChangedListener {

    private IAction globalAction;

    private Action renameAction;

    private final ISelectionProvider provider;

    private final IWorkbenchPartSite site;

    /**
     * @param graphicalViewer
     */
    protected OverviewRenameObjectActionHandler(IWorkbenchPartSite site,
            ISelectionProvider provider) {
        this.site = site;
        this.provider = provider;
        provider.addSelectionChangedListener(this);
        globalAction =
                RCPActivator
                        .getGlobalAction(RibbonConsts.ACTION_RENAME.getId());
        setId(ActionFactory.RENAME.getId());
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
            if (sel.size() == 1 && sel.getFirstElement() instanceof IProject) {
                RenameProjectAction projectRenameAction =
                        new RenameProjectAction(site.getWorkbenchWindow());
                projectRenameAction.setSelectedProject((IProject) sel
                        .getFirstElement());
                renameAction = projectRenameAction;
                success = true;
            }
            // else if (sel.getFirstElement() instanceof Process
            // || sel.getFirstElement() instanceof ProcessInterface
            // || sel.getFirstElement() instanceof Organization) {
            // RenameElementAction renameElementAction =
            // new RenameElementAction(getSite()
            // .getWorkbenchWindow());
            // renameElementAction.setSelection(selection);
            // renameAction = renameElementAction;
            // success = true;
            // }

            // else
            // if (sel.getFirstElement() instanceof Package
            // || sel.getFirstElement() instanceof Model
            // || sel.getFirstElement() instanceof OrgModel) {
            // IFile xpdlFile =
            // WorkingCopyUtil.getFile((EObject) sel
            // .getFirstElement());
            // if (xpdlFile != null) {
            // DeleteResourceAction resourceDA =
            // new DeleteResourceAction(getSite());
            // resourceDA.selectionChanged(new StructuredSelection(
            // xpdlFile));
            // renameAction = resourceDA;
            // success = true;
            // }
            // } else if (sel.getFirstElement() instanceof Process
            // || sel.getFirstElement() instanceof ProcessInterface) {
            // DeleteAction bpmDA = new DeleteAction(getSite().getShell());
            // bpmDA.selectionChanged(sel);
            // renameAction = bpmDA;
            // success = true;
            // } else if (sel.getFirstElement() instanceof Organization) {
            // EditingDomain ed =
            // WorkingCopyUtil.getEditingDomain((EObject) sel
            // .getFirstElement());
            // if (ed != null) {
            // OMDeleteAction omDA = new OMDeleteAction(ed);
            // omDA.selectionChanged(sel);
            // renameAction = omDA;
            // success = true;
            // }
            // }
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
        if (renameAction != null) {
            renameAction.run();
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
}