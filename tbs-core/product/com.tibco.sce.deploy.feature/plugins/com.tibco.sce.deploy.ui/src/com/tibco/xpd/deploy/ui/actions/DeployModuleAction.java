/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.LateBindingSupport;
import com.tibco.xpd.deploy.model.extension.ResourceBinding;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.deploy.ui.util.DeploymentExtensionManager;
import com.tibco.xpd.deploy.ui.wizards.deploy.DeployModuleWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.IBindingSupportWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.ISimpleDeployWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.ModuleDeploymentWizardNode;

/**
 * Shows deployment dialog, and invokes deploy action for selected modules.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployModuleAction extends AbstractDeploymentAction {
    public static final String ID =
            DeployUIActivator.PLUGIN_ID + ".DeployModuleAction"; //$NON-NLS-1$

    protected DeployModuleAction(StructuredViewer aViewer) {
        super(Messages.DeployModuleAction_DeployModule_action);
        setToolTipText(Messages.DeployModuleAction_DeployModuleAction_tooltip);
        setId(ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {

        if (!DeployUtil.saveAllDirtyResourcesInWS()) {
            return;
        }
        // It is guaranteed that all resources are saved.
        IStructuredSelection structSel = getStructuredSelection();
        if (structSel.getFirstElement() instanceof Server) {
            final Server selectedServer = (Server) structSel.getFirstElement();
            ModuleDeploymentWizardNode[] deploymentWizardNodes =
                    DeploymentExtensionManager.getInstance()
                            .getDeploymentWizardNodes(selectedServer);
            ISimpleDeployWizard wizard;
            if (deploymentWizardNodes.length == 1) {
                wizard = deploymentWizardNodes[0].getDeployWizard();
            } else {
                wizard = new DeployModuleWizard();
            }
            wizard.setServer(selectedServer);
            WizardDialog dialog =
                    new WizardDialog(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell(), wizard);
            dialog.open();
            // simple wizard takes care of everything in the performFinish
            if (dialog.getReturnCode() == Dialog.OK) {
                ISimpleDeployWizard w = getUnwrappedWizard(wizard);
                if (w instanceof IDeployWizard) {
                    if (w instanceof IBindingSupportWizard) {
                        Collection<ResourceBinding> bindings =
                                ((IBindingSupportWizard) w).getBindings();
                        if (selectedServer.getConnection() != null
                                && selectedServer.getConnection()
                                        .getAdapter(LateBindingSupport.class) instanceof LateBindingSupport) {
                            LateBindingSupport bindingSupport =
                                    (LateBindingSupport) selectedServer
                                            .getConnection()
                                            .getAdapter(LateBindingSupport.class);
                            Map<URL, Collection<ResourceBinding>> bindingMap =
                                    new LinkedHashMap<URL, Collection<ResourceBinding>>();
                            for (ResourceBinding rb : bindings) {
                                URL url = rb.getLocalModuleURL();
                                Collection<ResourceBinding> moduleBindings =
                                        bindingMap.get(url);
                                if (moduleBindings == null) {
                                    moduleBindings =
                                            new LinkedHashSet<ResourceBinding>();
                                    bindingMap.put(url, moduleBindings);
                                }
                                moduleBindings.add(rb);
                            }
                            for (URL moduleUrl : bindingMap.keySet()) {
                                bindingSupport.applyModuleBindings(moduleUrl,
                                        bindingMap.get(moduleUrl));
                            }
                        }
                    }
                    IDeployWizard deployWizard = (IDeployWizard) w;
                    // set new workspace modules;
                    Collection<WorkspaceModule> workspaceModules =
                            deployWizard.getWorkspaceModules();
                    if (workspaceModules != null) {
                        selectedServer.getWorkspaceModules().clear();
                        selectedServer.getWorkspaceModules()
                                .addAll(workspaceModules);
                        DeployUIActivator.getServerManager()
                                .saveServerContainer();
                    }
                    new DeployAction(selectedServer, deployWizard
                            .getModulesUrls(), true).run();
                }
            }
        }
    }

    private ISimpleDeployWizard getUnwrappedWizard(ISimpleDeployWizard w) {
        return w instanceof DeployModuleWizard ? ((DeployModuleWizard) w)
                .getSelectedModuleWizard() : w;
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
        if (selection.size() == 1
                && selection.getFirstElement() instanceof Server) {
            Connection connection =
                    ((Server) selection.getFirstElement()).getConnection();
            if (connection != null) {
                try {
                    return connection.isConnected();
                } catch (ConnectionException e) {
                    handleConnectionExeption(connection.getServer(), e);
                }
            }
        }
        return false;
    }
}
