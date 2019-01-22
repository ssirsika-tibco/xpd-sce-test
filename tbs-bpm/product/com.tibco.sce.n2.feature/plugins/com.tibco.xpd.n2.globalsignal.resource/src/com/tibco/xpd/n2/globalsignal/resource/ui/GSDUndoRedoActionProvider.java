/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

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

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Undo/Redo action provider for Global Signal Definition project.
 * 
 * @author sajain
 * @since Feb 27, 2015
 */
public class GSDUndoRedoActionProvider extends CommonActionProvider {

    /**
     * Editing domain.
     */
    private final TransactionalEditingDomain editingDomain;

    /**
     * Part site.
     */
    private IWorkbenchPartSite partSite;

    /**
     * Undo Redo action group.
     */
    private UndoRedoActionGroup undoRedoGroup;

    /**
     * Undo/Redo action provider for Global Signal Definition project.
     */
    public GSDUndoRedoActionProvider() {

        editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    @Override
    public void init(ICommonActionExtensionSite site) {

        super.init(site);

        ICommonViewerWorkbenchSite viewSite =
                (ICommonViewerWorkbenchSite) site.getViewSite();

        partSite = viewSite.getSite();
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {

        IUndoContext undoContext = getResourceUndoContext();

        if (undoContext != null && partSite != null) {

            if (undoRedoGroup != null) {

                undoRedoGroup.dispose();
            }

            undoRedoGroup =
                    new UndoRedoActionGroup(partSite, undoContext, true);

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

    /**
     * Return undo context.
     * 
     * @return Undo context.
     */
    private IUndoContext getResourceUndoContext() {

        IUndoContext undoContext = null;

        /*
         * Check if selection is valid.
         */
        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {

            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            /*
             * Only one item should be selected.
             */
            if (selection.size() == 1) {

                Resource resource = null;

                Object elem = selection.getFirstElement();

                /*
                 * Proceed according to the type of object.
                 */
                if (elem instanceof EObject) {

                    resource = ((EObject) elem).eResource();

                } else if (elem instanceof INavigatorGroup) {

                    if (((INavigatorGroup) elem).getParent() instanceof EObject) {

                        resource =
                                ((EObject) ((INavigatorGroup) elem).getParent())
                                        .eResource();
                    }

                } else if (elem instanceof IFile) {

                    URI uri =
                            URI.createFileURI(((IFile) elem).getFullPath()
                                    .toString());

                    resource =
                            editingDomain.getResourceSet().getResource(uri,
                                    false);
                }

                if (resource != null) {

                    undoContext =
                            new ResourceUndoContext(editingDomain, resource);
                }
            }
        }

        return undoContext;
    }

}
