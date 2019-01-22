/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.models;

import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Base Label provider used by the label providers of the model provider.
 * 
 * @author njpatel
 */
/* public */class AbstractLabelProvider extends ProjectExplorerLabelProvider {

    // Adapter factory label provider
    private final TransactionalAdapterFactoryLabelProvider factoryLabelProvider;

    private final ILabelDecorator decorator;

    /**
     * 
     */
    public AbstractLabelProvider() {
        factoryLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());

        decorator = PlatformUI.getWorkbench().getDecoratorManager();
    }

    /**
     * @see org.eclipse.jface.viewers.BaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
        decorator.addListener(listener);
        super.addListener(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.BaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
        decorator.removeListener(listener);
        super.removeListener(listener);
    }

    /**
     * @return the factoryLabelProvider
     */
    protected TransactionalAdapterFactoryLabelProvider getFactoryLabelProvider() {
        return factoryLabelProvider;
    }

    /**
     * @return the decorator
     */
    protected ILabelDecorator getDecorator() {
        return decorator;
    }

    /**
     * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        factoryLabelProvider.dispose();
        super.dispose();
    }

}
