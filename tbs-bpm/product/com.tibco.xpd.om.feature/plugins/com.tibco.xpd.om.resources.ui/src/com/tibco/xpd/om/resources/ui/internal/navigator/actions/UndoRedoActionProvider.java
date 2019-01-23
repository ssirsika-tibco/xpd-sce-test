package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
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

import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class UndoRedoActionProvider extends CommonActionProvider {

    private final TransactionalEditingDomain editingDomain;
    private IWorkbenchPartSite partSite;
    private UndoRedoActionGroup undoRedoGroup;

    public UndoRedoActionProvider() {
        editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();
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

                if (elem instanceof EObject) {
                    resource = ((EObject) elem).eResource();
                } else if (elem instanceof TransientItemProvider) {
                    resource = ((EObject) ((TransientItemProvider) elem)
                            .getTarget()).eResource();
                } else if (elem instanceof IFile) {
                    URI uri = URI.createFileURI(((IFile) elem).getFullPath()
                            .toString());
                    resource = editingDomain.getResourceSet().getResource(uri,
                            false);
                }

                if (resource != null) {
                    undoContext = new ResourceUndoContext(editingDomain,
                            resource);
                }
            }
        }

        return undoContext;
    }

}
