/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardNode;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.util.DeploymentExtensionManager;

/**
 * ContentProvider for module types and theirs deployment wizards associated
 * with server type.
 * <p>
 * <i>Created: 30 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ModuleTypesContentProvider implements ITreeContentProvider {

    private static final Object[] EMPTY_TABLE = new Object[0];

    private IWizardNode[] nodes;

    /** {@inheritDoc} */
    public Object[] getChildren(Object parentElement) {
        return EMPTY_TABLE;
    }

    /** {@inheritDoc} */
    public Object getParent(Object element) {
        return null;
    }

    /** {@inheritDoc} */
    public boolean hasChildren(Object element) {
        return false;
    }

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof Server) {
            if (nodes == null) {
                nodes = DeploymentExtensionManager.getInstance()
                        .getDeploymentWizardNodes((Server) inputElement);
            }
            return nodes;
        }
        return EMPTY_TABLE;
    }

    public void dispose() {
        // do nothing
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // do nothing
    }

}
