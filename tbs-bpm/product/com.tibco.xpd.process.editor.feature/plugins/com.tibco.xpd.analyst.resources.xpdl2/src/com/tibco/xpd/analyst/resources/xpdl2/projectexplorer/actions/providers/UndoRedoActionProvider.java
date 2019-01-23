/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006.  All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.TransactionalWorkingCopy;

/**
 * Project Explorer's Undo/Redo actions provider
 * 
 * @author njpatel
 * 
 */
public class UndoRedoActionProvider extends CommonActionProvider {

    private final TransactionalEditingDomain editingDomain;
    private IWorkbenchPartSite partSite;
    private UndoRedoActionGroup undoRedoGroup;

    public UndoRedoActionProvider() {
        editingDomain = TransactionalEditingDomain.Registry.INSTANCE
                .getEditingDomain(XpdConsts.EDITING_DOMAIN_ID);
    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);

        ICommonViewerWorkbenchSite viewSite = (ICommonViewerWorkbenchSite) site
                .getViewSite();
        partSite = viewSite.getSite();
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        IUndoContext undoContext = getResourceUndoContext();

        if (undoContext != null && partSite != null) {

            if (undoRedoGroup != null) {
                undoRedoGroup.dispose();
            }

            undoRedoGroup = new UndoRedoActionGroup(partSite, undoContext, true);
            undoRedoGroup.setContext(getContext());
            undoRedoGroup.fillActionBars(actionBars);
        }
    }

    @Override
    public void dispose() {
        if (undoRedoGroup != null) {
            undoRedoGroup.dispose();
        }

        super.dispose();
    }

    private IUndoContext getResourceUndoContext() {
        IUndoContext undoContext = null;

        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection = (IStructuredSelection) getContext()
                    .getSelection();

            if (selection.size() == 1) {
                Resource resource = null;

                Object elem = selection.getFirstElement();
                while (elem instanceof INavigatorGroup) {
                    INavigatorGroup group = (INavigatorGroup) elem;
                    elem = group.getParent();
                }
                if (elem instanceof EObject) {
                    resource = ((EObject) elem).eResource();
                    if (resource != null) {
                        undoContext = new ResourceUndoContext(editingDomain,
                                resource);
                    }
                } else if (elem instanceof IFile) {
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy((IFile) elem);
                    if (wc instanceof TransactionalWorkingCopy) {
                        undoContext = ((TransactionalWorkingCopy) wc)
                                .getUndoContext();
                    }
                }

            }
        }

        return undoContext;
    }

}
