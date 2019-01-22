/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Edit action provider for the UML objects in the BOM file.
 * 
 * @author njpatel
 * 
 */
public class BOMEditActionProvider extends CommonActionProvider {

    private BOMCopyAction copyAction;

    private BOMPasteAction pasteAction;

    private DeleteAction deleteAction;

    private RenameAction renameAction;

    /**
     * Default constructor.
     */
    public BOMEditActionProvider() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
     * .ICommonActionExtensionSite)
     */
    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        copyAction = new BOMCopyAction(ed);
        copyAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

        pasteAction = new BOMPasteAction(ed);
        pasteAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

        deleteAction =
                new DeleteAction(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), true) {
                    @Override
                    public Command createCommand(Collection<?> selection) {

                        CompositeCommand result =
                                new CompositeCommand(deleteAction.getText());

                        for (Object obj : selection) {
                            if (obj instanceof EObject) {
                                EObject eo = (EObject) obj;
                                DestroyElementRequest req =
                                        new DestroyElementRequest(eo, false);
                                ICommand editCommand =
                                        ElementTypeRegistry.getInstance()
                                                .getElementType(eo)
                                                .getEditCommand(req);
                                if (editCommand != null) {
                                    result.add(editCommand);
                                }
                            }
                        }
                        return new EMFOperationCommand(XpdResourcesPlugin
                                .getDefault().getEditingDomain(), result);
                    }

                    @Override
                    public boolean updateSelection(
                            IStructuredSelection selection) {
                        boolean update = true;

                        // Cannot delete model objects
                        for (Iterator<?> iter = selection.iterator(); iter
                                .hasNext();) {
                            if (iter.next() instanceof Model) {
                                update = false;
                                break;
                            }
                        }

                        return update ? super.updateSelection(selection)
                                : update;
                    }
                };
        deleteAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

        if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            // Set the action bars so that the copy/paste is handled correctly
            // from the inline editor
            renameAction =
                    new RenameAction(
                            site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree(),
                            ((ICommonViewerWorkbenchSite) site.getViewSite())
                                    .getActionBars());
        } else {
            renameAction =
                    new RenameAction(site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree());
        }

        renameAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, renameAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, pasteAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, deleteAction);

    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        updateSelection();

        actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                copyAction);
        actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                pasteAction);
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteAction);
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
    }

    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();

        if (!selectionValid(selection)) {
            selection = StructuredSelection.EMPTY;
        }

        copyAction.selectionChanged(selection);
        pasteAction.selectionChanged(selection);
        deleteAction.selectionChanged(selection);
        renameAction.selectionChanged(selection);

        Object firstElement = selection.getFirstElement();
        if (firstElement instanceof EObject) {

            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor((EObject) firstElement);

            /*
             * Working copy is read only if the project is pre-compiled. Then we
             * want to disable this action
             */
            boolean isWorkingCopyReadOnly = false;
            if (null != wc) {

                isWorkingCopyReadOnly = wc.isReadOnly();
            }
            if (isWorkingCopyReadOnly) {

                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                deleteAction.setEnabled(false);
                renameAction.setEnabled(false);
            } else {

                copyAction.setEnabled(true);
                pasteAction.setEnabled(true);
                deleteAction.setEnabled(true);
                renameAction.setEnabled(true);
            }
        }
    }

    /**
     * Checks if all selected objects are of the same type and are siblings.
     * 
     * @param selection
     * @return <code>true</code> if all objects are of same type and are
     *         siblings, <code>false</code> otherwise.
     */
    private boolean selectionValid(IStructuredSelection selection) {
        boolean valid = true;

        if (selection != null && selection.size() > 1) {
            EObject firstItem = (EObject) selection.getFirstElement();

            for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                    && valid;) {
                valid =
                        (((EObject) iter.next()).eContainer() == firstItem
                                .eContainer());
            }
        }

        return selection != null ? valid : false;
    }

    /**
     * Get the notation objects for all UML objects in the selection. If
     * notation objects are found and they belong to different diagrams then an
     * empty list will be returned. If no notation diagram for an element is
     * found then the element itself will be returned in the list.
     * 
     * @param selection
     *            selection of UML objects.
     * @return
     */
    public static List<EObject> getNotationObjects(Collection<?> selection,
            EditingDomain editingDomain) {

        Assert.isNotNull(editingDomain, "Editing Domain"); //$NON-NLS-1$

        ResourceSet rSet = editingDomain.getResourceSet();
        // List<View> views = new ArrayList<View>();
        List<EObject> views = new ArrayList<EObject>();
        Model model = null;

        if (rSet != null) {
            CrossReferenceAdapter referenceAdapter =
                    CrossReferenceAdapter.getCrossReferenceAdapter(rSet);

            if (referenceAdapter != null) {
                for (Object sel : selection) {
                    if (sel instanceof EObject) {
                        boolean noNotation = true;
                        Set<?> referencers =
                                referenceAdapter
                                        .getInverseReferencers((EObject) sel,
                                                NotationPackage.eINSTANCE
                                                        .getView_Element(),
                                                null);

                        if (referencers != null && !referencers.isEmpty()) {
                            for (Object ref : referencers) {
                                if (ref instanceof View) {
                                    View view = (View) ref;
                                    noNotation = false;
                                    if (view.getDiagram() != null
                                            && view.getDiagram().getElement() instanceof Model) {
                                        boolean foundNotation = false;
                                        if (model != null) {
                                            foundNotation =
                                                    model == view.getDiagram()
                                                            .getElement();
                                        } else {
                                            model =
                                                    (Model) view.getDiagram()
                                                            .getElement();
                                            foundNotation = true;
                                        }

                                        if (foundNotation
                                                && view.getDiagram()
                                                        .getType()
                                                        .equals("Business Object Model")) { //$NON-NLS-1$
                                            if (sel instanceof Model) {
                                                if (view instanceof Diagram) {
                                                    /**
                                                     * If our target is a Model
                                                     * then we are interested in
                                                     * the Diagram and not the
                                                     * Node, and hence we are
                                                     * adding this extra check
                                                     * which is not there in the
                                                     * original method present
                                                     * in BOMEditActionProvider
                                                     */
                                                    views.add(view);
                                                    break;
                                                }
                                            } else {
                                                views.add(view);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // If no notations found for the given element then just
                        // add the element
                        if (noNotation) {
                            views.add((EObject) sel);
                        }
                    }
                }
            }
        }
        return views.size() == selection.size() ? views
                : new ArrayList<EObject>(0);
    }

}
