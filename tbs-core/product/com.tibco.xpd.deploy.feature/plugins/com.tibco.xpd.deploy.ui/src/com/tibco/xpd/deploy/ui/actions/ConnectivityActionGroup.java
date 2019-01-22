/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;

/**
 * Groups actions related to server connectivity related operations.
 * <p>
 * <i>Created: 7 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectivityActionGroup extends ActionGroup {

    private final ConnectServerAction connectServerAction;

    private final DisconnectServerAction disconnectServerAction;

    private final DeleteServerAction deleteServerAction;

    private final DeployModuleAction deployModuleAction;

    /**
     * Creates a group and passes the reference to the viewer.
     * 
     * @param viewer
     *            the viewer associated actions operate on.
     */
    public ConnectivityActionGroup(StructuredViewer viewer) {

        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

        ImageRegistry ir = DeployUIActivator.getDefault().getImageRegistry();

        connectServerAction = new ConnectServerAction(viewer);
        connectServerAction.setImageDescriptor(ir
                .getDescriptor(DeployUIConstants.IMG_CONNECT_SERVER));

        disconnectServerAction = new DisconnectServerAction(viewer);
        disconnectServerAction.setImageDescriptor(ir
                .getDescriptor(DeployUIConstants.IMG_DISCONNECT_SERVER));

        deployModuleAction = new DeployModuleAction(viewer);

        // Delete action
        deleteServerAction = new DeleteServerAction();
        deleteServerAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        deleteServerAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        deleteServerAction
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
        menu.add(deployModuleAction);
        menu.add(new Separator());
        if (!isConnectivityUISuppressed(getContext())) {
            menu.add(connectServerAction);
            menu.add(disconnectServerAction);
        }
        menu.add(deleteServerAction);
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
                deleteServerAction);
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
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) context.getSelection();
            deployModuleAction.selectionChanged(structuredSelection);
            connectServerAction.selectionChanged(structuredSelection);
            disconnectServerAction.selectionChanged(structuredSelection);
            deleteServerAction.selectionChanged(structuredSelection);
        }
    }

    private boolean isConnectivityUISuppressed(ActionContext context) {
        if (context != null) {
            IStructuredSelection sel =
                    (IStructuredSelection) context.getSelection();
            if (sel.size() == 1 && sel.getFirstElement() instanceof Server
                    && ((Server) sel.getFirstElement()).getServerType() != null) {
                Server server = (Server) sel.getFirstElement();
                return server.getServerType().isSuppressConectivity();
            }
        }

        return false;
    }
}
