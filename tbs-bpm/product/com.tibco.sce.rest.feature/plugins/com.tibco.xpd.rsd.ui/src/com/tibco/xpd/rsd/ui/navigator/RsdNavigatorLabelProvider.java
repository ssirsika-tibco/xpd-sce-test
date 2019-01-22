/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * REST Service Descriptor content label provider.
 * 
 * @author jarciuch
 * @since 27 Feb 2015
 */
public class RsdNavigatorLabelProvider extends ProjectExplorerLabelProvider {

    private final AdapterFactoryLabelProvider factoryLabelProvider;

    public RsdNavigatorLabelProvider() {
        factoryLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(Object element) {
        if (isFromRsdPackage(element)) {
            return factoryLabelProvider.getText(element);
        }
        return super.getText(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object element) {
        if (isFromRsdPackage(element)) {
            return factoryLabelProvider.getImage(element);
        }
        return super.getImage(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription(Object anElement) {
        return super.getDescription(anElement);
    }

    /**
     * @return <code>true</code> if object is an EObject from RsdPackage.
     */
    /* package */static boolean isFromRsdPackage(Object o) {
        if (o instanceof EObject) {
            return isFromPackage((EObject) o,
                    Collections.singleton(RsdPackage.eINSTANCE.getName()));
        }
        return false;
    }

    /**
     * Check if the given <code>EObject</code> is from one of a packages with a
     * given names.
     * 
     * @param eo
     *            <code>EObject</code> to test.
     * @return <code>true</code> if from the <code>UMLPackage</code>,
     *         <code>false</code> otherwise.
     */
    private static boolean isFromPackage(final EObject eo,
            final Collection<String> packageNames) {
        if (eo != null && eo.eClass() != null) {
            EPackage pkg = eo.eClass().getEPackage();
            return pkg != null && packageNames.contains(pkg.getName());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (factoryLabelProvider != null) {
            factoryLabelProvider.dispose();
        }
        super.dispose();
    }
}
