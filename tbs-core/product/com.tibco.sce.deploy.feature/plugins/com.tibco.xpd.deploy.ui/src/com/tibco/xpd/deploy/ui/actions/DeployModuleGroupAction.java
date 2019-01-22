/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.deploy.DeployToServerGroupWizard;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Shows deployment dialog, and invokes deploy action for selected modules.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployModuleGroupAction extends AbstractDeploymentAction {
    public static final String ID = DeployUIActivator.PLUGIN_ID
            + ".DeployModuleAction"; //$NON-NLS-1$

    protected DeployModuleGroupAction(StructuredViewer aViewer) {
        super(Messages.DeployModuleGroupAction_deployToServerGroup_action);
        setToolTipText(Messages.DeployModuleGroupAction_deployToServerGroup_tooltip);
        setId(ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        IStructuredSelection structSel = getStructuredSelection();
        if (structSel.getFirstElement() instanceof ServerGroup) {
            if (PlatformUI.getWorkbench().saveAllEditors(true)) {
                DeployToServerGroupWizard wizard = new DeployToServerGroupWizard();
                final ServerGroup selectedServerGroup = (ServerGroup) structSel
                        .getFirstElement();
                wizard.setServerGroup(selectedServerGroup);
                WizardDialog dialog = new WizardDialog(PlatformUI
                        .getWorkbench().getActiveWorkbenchWindow().getShell(),
                        wizard);
                dialog.open();
                if (dialog.getReturnCode() == Dialog.OK) {
                    Map<Server, List<URL>> serverModulesUrls = wizard
                            .getServerModulesUrls();
                    for (Server server : serverModulesUrls.keySet()) {
                        List<URL> urls = serverModulesUrls.get(server);
                        new DeployAction(server, urls, true).run();

                        // logging
                        Logger log = DeployUIActivator.getDefault().getLogger();
                        if (log.isDebugEnabled()) {
                            StringBuilder message = new StringBuilder();
                            message.append("Deployin to Server: "); //$NON-NLS-1$
                            message.append(server);
                            message.append('\n');
                            for (URL url : urls) {
                                message.append('\t');
                                message.append(url);
                                message.append('\n');
                            }
                            log.debug(message.toString());
                        }
                    }
                }
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
        return true;

    }
}
