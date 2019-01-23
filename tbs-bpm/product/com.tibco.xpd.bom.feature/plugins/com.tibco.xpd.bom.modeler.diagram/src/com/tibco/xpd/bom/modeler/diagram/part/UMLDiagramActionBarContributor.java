/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.bom.modeler.diagram.internal.BOMEditorRenameAction;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

public class UMLDiagramActionBarContributor extends DiagramActionBarContributor {

    private QuickSearchPopupAction quickSearchAction;

    private BOMEditorRenameAction renameAction;

    /**
     * @generated
     */
    @Override
    protected Class getEditorClass() {
        return UMLDiagramEditor.class;
    }

    /**
     * @generated
     */
    @Override
    protected String getEditorId() {
        return UMLDiagramEditor.ID;
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor#init(org.eclipse.ui.IActionBars)
     * 
     * @param bars
     */
    @Override
    public void init(IActionBars bars) {

        createActions();
        super.init(bars);
        setActionHandlers(bars);

        /*
         * XPD-4942: The following is work-around for a bug in GMF
         * (https://bugs.eclipse.org/bugs/show_bug.cgi?id=346648). This should
         * be removed once we move to the newer version of GMF.
         */
        // use the default global action handlers.
        bars.setGlobalActionHandler(GlobalActionId.SAVE, null);
    }

    private void setActionHandlers(IActionBars bars) {

        bars.setGlobalActionHandler(ActionFactory.FIND.getId(),
                quickSearchAction);
        bars.setGlobalActionHandler(ActionFactory.RENAME.getId(), renameAction);
    }

    private void createActions() {
        quickSearchAction =
                new QuickSearchPopupAction(
                        Messages.UMLDiagramActionBarContributor_FindDiagramElementsLabel);

        renameAction = new BOMEditorRenameAction(getPage());
    }

    /**
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
     * 
     * @param toolBarManager
     */
    @Override
    public void contributeToToolBar(IToolBarManager toolBarManager) {

        super.contributeToToolBar(toolBarManager);
        toolBarManager.add(quickSearchAction);
    }

}
