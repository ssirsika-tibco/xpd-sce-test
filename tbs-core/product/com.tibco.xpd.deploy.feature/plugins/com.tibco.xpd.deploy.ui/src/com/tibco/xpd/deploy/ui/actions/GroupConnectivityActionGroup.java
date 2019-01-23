/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

/**
 * Groups actions related to group connectivity related operations.
 * <p>
 * <i>Created: 7 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz, Gary Lewis
 * 
 */
public class GroupConnectivityActionGroup extends ActionGroup {

    private final ConnectServerGroupAction connectServerGroupAction;

    private final DisconnectServerGroupAction disconnectServerGroupAction;

    private final DeleteServerGroupAction deleteServerGroupAction;

    private final DeployModuleGroupAction deployModuleGroupAction;

    /**
     * Creates a group and passes the reference to the viewer.
     * 
     * @param viewer
     *            the viewer associated actions operate on.
     */
    public GroupConnectivityActionGroup(StructuredViewer viewer) {

        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

        connectServerGroupAction = new ConnectServerGroupAction(viewer);
        disconnectServerGroupAction = new DisconnectServerGroupAction(viewer);
        deployModuleGroupAction = new DeployModuleGroupAction(viewer);

        // Delete action
        deleteServerGroupAction = new DeleteServerGroupAction();
        deleteServerGroupAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        deleteServerGroupAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        deleteServerGroupAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
     * action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.add(deployModuleGroupAction);
        menu.add(new Separator());
        menu.add(connectServerGroupAction);
        menu.add(disconnectServerGroupAction);
        menu.add(deleteServerGroupAction);
        menu.add(new Separator());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars
     * )
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteServerGroupAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.
     * ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            IStructuredSelection structuredSelection = (IStructuredSelection) context
                    .getSelection();
            deployModuleGroupAction.selectionChanged(structuredSelection);
            connectServerGroupAction.selectionChanged(structuredSelection);
            disconnectServerGroupAction.selectionChanged(structuredSelection);
            deleteServerGroupAction.selectionChanged(structuredSelection);
        }
    }
}
