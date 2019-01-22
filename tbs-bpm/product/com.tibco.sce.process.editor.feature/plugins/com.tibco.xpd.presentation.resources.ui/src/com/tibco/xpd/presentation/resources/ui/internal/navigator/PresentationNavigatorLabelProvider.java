/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.navigator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Provides labels for Presentation content in Project Explorer view.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class PresentationNavigatorLabelProvider extends
        ProjectExplorerLabelProvider {

    // Adapter factory label provider
    private final AdapterFactoryLabelProvider factoryLabelProvider;

    /**
     * Content label provider.
     */
    public PresentationNavigatorLabelProvider() {
        factoryLabelProvider = new TransactionalAdapterFactoryLabelProvider(
                XpdResourcesPlugin.getDefault().getEditingDomain(),
                XpdResourcesPlugin.getDefault().getAdapterFactory());
    }

    @Override
    public String getText(Object element) {

        if (element instanceof EObject
                && isFromPackage((EObject) element, ChannelsPackage.eINSTANCE)) {
            return factoryLabelProvider.getText(element);
        }
        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {

        if (element instanceof EObject
                && isFromPackage((EObject) element, ChannelsPackage.eINSTANCE)) {
            return factoryLabelProvider.getImage(element);
        }

        return super.getImage(element);
    }

    /**
     * Check if the given <code>EObject</code> is from the provided package.
     * 
     * @param eo
     *            <code>EObject</code> to test.
     * @param ePackage
     *            the package to test.
     * @return <code>true</code> if from the tested package, <code>false</code>
     *         otherwise.
     */
    private boolean isFromPackage(EObject eo, EPackage ePackage) {
        boolean fromPackage = false;

        if (eo != null && eo.eClass() != null) {
            EPackage pkg = eo.eClass().getEPackage();

            fromPackage = pkg != null && pkg == ePackage;
        }

        return fromPackage;
    }
}
