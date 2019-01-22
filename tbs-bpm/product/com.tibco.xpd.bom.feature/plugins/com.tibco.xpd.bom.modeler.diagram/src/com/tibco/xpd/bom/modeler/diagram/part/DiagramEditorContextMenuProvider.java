/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.ContributionItemService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @generated
 */
public class DiagramEditorContextMenuProvider extends
        DiagramContextMenuProvider {

    /**
     * @generated
     */
    private IWorkbenchPart part;

    /**
     * @generated
     */
    private DeleteElementAction deleteAction;

    /**
     * @generated
     */
    public DiagramEditorContextMenuProvider(IWorkbenchPart part,
            EditPartViewer viewer) {
        super(part, viewer);
        this.part = part;
        deleteAction = new DeleteElementAction(part);
        deleteAction.init();
    }

    /**
     * @generated
     */
    public void dispose() {
        if (deleteAction != null) {
            deleteAction.dispose();
            deleteAction = null;
        }
        super.dispose();
    }

    /**
     * @generated
     */
    public void buildContextMenu(final IMenuManager menu) {
        getViewer().flush();
        try {
            TransactionUtil.getEditingDomain((EObject) getViewer()
                    .getContents().getModel()).runExclusive(new Runnable() {

                public void run() {
                    ContributionItemService
                            .getInstance()
                            .contributeToPopupMenu(DiagramEditorContextMenuProvider.this,
                                    part);
                    menu.remove(ActionIds.ACTION_DELETE_FROM_MODEL);
                    menu.appendToGroup("editGroup", deleteAction); //$NON-NLS-1$

                    // If there are more than one item selected, then disable
                    // the add options. If enabled it can behave a bit
                    // strangely, adding multiple elements to some classes
                    if (getViewer().getSelectedEditParts().size() > 1) {
                        if (menu.find("addAttribute") != null) { //$NON-NLS-1$
                            menu.remove("addAttribute"); //$NON-NLS-1$
                        }
                        if (menu.find("addOperation") != null) { //$NON-NLS-1$
                            menu.remove("addOperation"); //$NON-NLS-1$
                        }
                        if (menu.find("addCaseIdentifier") != null) { //$NON-NLS-1$
                            menu.remove("addCaseIdentifier"); //$NON-NLS-1$
                        }
                        if (menu.find("addEnumerationLiteral") != null) { //$NON-NLS-1$
                            menu.remove("addEnumerationLiteral"); //$NON-NLS-1$
                        }
                    }
                }
            });
        } catch (Exception e) {
            BOMDiagramEditorPlugin.getInstance()
                    .logError("Error building context menu", e); //$NON-NLS-1$
        }
    }
}
