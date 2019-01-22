/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * 
 * <p>
 * <i>Created: 14 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeleteServerGroupAction extends AbstractDeploymentAction {

    public static final String IDE_WORKBENCH = "org.eclipse.ui.ide"; //$NON-NLS-1$

    public static final String PREFIX = IDE_WORKBENCH + "."; //$NON-NLS-1$

    public static final String DELETE_RESOURCE_ACTION = PREFIX
            + "delete_resource_action_context"; //$NON-NLS-1$

    /**
     * The id of this action.
     */
    public static final String ID = PlatformUI.PLUGIN_ID
            + ".DeleteServerGroupAction";//$NON-NLS-1$

    /**
     * Delete Action
     */
    public DeleteServerGroupAction() {
        super(Messages.DeleteServerGroupAction_delete_action);
        setToolTipText(Messages.DeleteServerGroupAction_delete_tooltip);
        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                DELETE_RESOURCE_ACTION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        String title = Messages.DeleteServerGroupAction_confirmDelete_title;
        String msg = Messages.DeleteServerGroupAction_confirmDeletion_message;
        boolean decision = MessageDialog.openQuestion(s, title, msg);
        if (decision) {
            IStructuredSelection structSel = getStructuredSelection();
            if (structSel.size() > 0
                    && allInstanceOf(structSel.toList(), ServerGroup.class)) {
                ServerManager manager = DeployUIActivator.getServerManager();
                EditingDomain editingDomain = manager.getEditingDomain();
                ServerContainer serverContainer = manager.getServerContainer();
                CompoundCommand compoundCmd = new CompoundCommand();
                for (Iterator<?> iter = structSel.toList().iterator(); iter
                        .hasNext();) {
                    ServerGroup serverGroup = (ServerGroup) iter.next();

                    Command cmd;
                    cmd = RemoveCommand.create(editingDomain, serverGroup,
                            DeployPackage.eINSTANCE.getServerGroup_Members(),
                            serverGroup.getMembers());
                    compoundCmd.append(cmd);
                    cmd = RemoveCommand.create(editingDomain, serverContainer,
                            DeployPackage.eINSTANCE
                                    .getServerContainer_ServerGroups(),
                            serverGroup);
                    compoundCmd.append(cmd);
                }
                if (compoundCmd.canExecute()) {
                    editingDomain.getCommandStack().execute(compoundCmd);
                }
                manager.saveServerContainer();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() > 0
                && allInstanceOf(selection.toList(), ServerGroup.class)) {
            return true;
        }
        return false;
    }

    /**
     * Check if all items in the list are of the same type (instance of the same
     * class).
     * 
     * @param items
     * @param type
     *            type of the object to check.
     * @return <code>true</code> if all objects of type "type",
     *         <code>false</code> otherwise.
     */
    private boolean allInstanceOf(List<?> items, Class<?> type) {
        if (items == null) {
            return false;
        }
        for (Iterator<?> iter = items.iterator(); iter.hasNext();) {
            if (!type.isAssignableFrom(iter.next().getClass())) {
                return false;
            }
        }
        return true;
    }
}
