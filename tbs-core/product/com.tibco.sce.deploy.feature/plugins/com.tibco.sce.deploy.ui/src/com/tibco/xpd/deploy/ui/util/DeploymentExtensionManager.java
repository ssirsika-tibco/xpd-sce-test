/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.wizards.deploy.ISimpleDeployWizard;
import com.tibco.xpd.deploy.ui.wizards.deploy.ModuleDeploymentWizardNode;

/**
 * Manages deployment extensions.
 * <p>
 * <i>Created: 18 Dec 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class DeploymentExtensionManager {

    private static final DeploymentExtensionManager INSTANCE =
            new DeploymentExtensionManager();

    private static final String DEPLOY_WIZARDS_EXTENSION_POINT =
            "deployWizards"; //$NON-NLS-1$

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

    private static final String ICON_ATTRIBUTE = "icon"; //$NON-NLS-1$

    private static final String SERVER_TYPE_ID_ATTRIBUTE = "serverTypeId"; //$NON-NLS-1$

    private static final String PRIORITY_ATTRIBUTE = "priority"; //$NON-NLS-1$

    /**
     * Private constructor to prevent instantiation. Use #getInstance() instead.
     */
    private DeploymentExtensionManager() {
    }

    public static DeploymentExtensionManager getInstance() {
        return INSTANCE;
    }

    public ModuleDeploymentWizardNode[] getDeploymentWizardNodes(Server server) {
        String serverTypeId = server.getServerType().getId();
        ArrayList<ModuleDeploymentWizardNode> wizardsNodes =
                new ArrayList<ModuleDeploymentWizardNode>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements =
                registry
                        .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                                DEPLOY_WIZARDS_EXTENSION_POINT);
        for (int i = 0; i < elements.length; i++) {
            String extServerTypeId =
                    getAttribute(elements[i], SERVER_TYPE_ID_ATTRIBUTE, null);
            if (serverTypeId.equals(extServerTypeId)) {
                String wizardId = getAttribute(elements[i], ID_ATTRIBUTE, null);
                String wizardName =
                        getAttribute(elements[i], NAME_ATTRIBUTE, null);
                ImageDescriptor wizardImage =
                        getImageDescriptor(elements[i], ICON_ATTRIBUTE);
                String priority =
                        getAttribute(elements[i], PRIORITY_ATTRIBUTE, "0");
                int intPriority = 0;
                try {
                    intPriority = Integer.parseInt(priority);
                } catch (NumberFormatException e) {
                    // IGNORE
                }
                try {
                    ISimpleDeployWizard wizard =
                            (ISimpleDeployWizard) elements[i]
                                    .createExecutableExtension(CLASS_ATTRIBUTE);
                    wizard.setServer(server);
                    ModuleDeploymentWizardNode node =
                            new ModuleDeploymentWizardNode(wizard, wizardName,
                                    wizardImage, intPriority);
                    wizardsNodes.add(node);
                } catch (CoreException e) {
                    throw new IllegalArgumentException(
                            "Cannot create executable extension for: " //$NON-NLS-1$
                                    + wizardId, e);
                }
            }
        }
        // The wizards will be sorted according to priority
        Collections.sort(wizardsNodes,
                new Comparator<ModuleDeploymentWizardNode>() {
                    public int compare(ModuleDeploymentWizardNode o1,
                            ModuleDeploymentWizardNode o2) {
                        if (o1.getPriority() > o2.getPriority()) {
                            return 0;
                        } else if (o1.getPriority() < o2.getPriority()) {
                            return -1;
                        }
                        return 1;
                    }
                });
        return wizardsNodes.toArray(new ModuleDeploymentWizardNode[wizardsNodes
                .size()]);
    }

    private String getAttribute(IConfigurationElement configElement,
            String attrName, String defaultValue) {
        String value = configElement.getAttribute(attrName);
        if (value != null) {
            return value;
        }
        if (defaultValue != null) {
            return defaultValue;
        }
        throw new IllegalArgumentException("Missing attribute: " + attrName); //$NON-NLS-1$
    }

    private ImageDescriptor getImageDescriptor(
            IConfigurationElement configElement, String attrName) {
        String iconPath = configElement.getAttribute(attrName);
        // Get the icon descriptor
        if (iconPath != null) {
            return AbstractUIPlugin.imageDescriptorFromPlugin(configElement
                    .getNamespaceIdentifier(), iconPath);
        }
        return null;
    }
}
