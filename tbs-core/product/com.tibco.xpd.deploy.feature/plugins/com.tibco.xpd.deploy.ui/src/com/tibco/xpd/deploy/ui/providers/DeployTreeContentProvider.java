/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import java.util.Set;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;

import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Provides content for objects of deployment model.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployTreeContentProvider implements IPipelinedTreeContentProvider {

    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final AdapterFactoryContentProvider adapterFactoryContentProvider;
    private final Object[] serverContainerParent = new Object[1];

    public DeployTreeContentProvider() {
        ServerManager serverManager = DeployUIActivator.getServerManager();
        AdapterFactory adapterFactory = serverManager.getAdapterFactory();
        adapterFactoryContentProvider = new AdapterFactoryContentProvider(
                adapterFactory);

    }

    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof EObject) {
            return adapterFactoryContentProvider.getChildren(parentElement);
        }
        return EMPTY_ARRAY;
    }

    public Object getParent(Object element) {
        if (element instanceof ServerContainer) {
            return serverContainerParent[0];
        } else if (element instanceof EObject) {
            return adapterFactoryContentProvider.getParent(element);
        }
        return null;
    }

    public boolean hasChildren(Object element) {
        if (element instanceof EObject) {
            return adapterFactoryContentProvider.hasChildren(element);
        }
        return false;
    }

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof IWorkspaceRoot) {
            serverContainerParent[0] = DeployUIActivator.getServerManager()
                    .getServerContainer();
            return serverContainerParent;
        }
        return EMPTY_ARRAY;
    }

    public void dispose() {
        if (adapterFactoryContentProvider != null)
            adapterFactoryContentProvider.dispose();
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        adapterFactoryContentProvider.inputChanged(viewer, oldInput, newInput);
    }

    @SuppressWarnings("unchecked")
    public void getPipelinedChildren(Object aParent, Set theCurrentChildren) {
    }

    @SuppressWarnings("unchecked")
    public void getPipelinedElements(Object anInput, Set theCurrentElements) {
        if (anInput instanceof IWorkspaceRoot) {
            theCurrentElements.add(DeployUIActivator.getServerManager()
                    .getServerContainer());
        }
    }

    public Object getPipelinedParent(Object anObject, Object aSuggestedParent) {
        return aSuggestedParent;
    }

    public PipelinedShapeModification interceptAdd(
            PipelinedShapeModification anAddModification) {
        return null;
    }

    public boolean interceptRefresh(
            PipelinedViewerUpdate aRefreshSynchronization) {
        return false;
    }

    public PipelinedShapeModification interceptRemove(
            PipelinedShapeModification aRemoveModification) {
        return null;
    }

    public boolean interceptUpdate(PipelinedViewerUpdate anUpdateSynchronization) {
        return false;
    }

    public void init(ICommonContentExtensionSite aConfig) {

    }

    public void restoreState(IMemento aMemento) {

    }

    public void saveState(IMemento aMemento) {

    }

}
