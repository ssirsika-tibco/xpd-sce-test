/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;

/**
 * Label Provider for tabbed property sheets contributions from RSD editor.
 * 
 * @author jarciuch
 * @since 1 Mar 2015
 */
public class RsdPropertyLabelProvider extends DecoratingLabelProvider {

    /**
     * Creates a label provider.
     */
    public RsdPropertyLabelProvider() {

        super(new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                .getDefault().getEditingDomain(), RsdUIPlugin.getPlugin()
                .getItemProvidersAdapterFactory()), null);
        AdapterFactoryLabelProvider labelProvider =
                (AdapterFactoryLabelProvider) getLabelProvider();

        /*
         * XPD-7793: Tabbed property page label providers acting as listeners
         * are not always properly removed when underlying widget is disposed
         * leading to WidgetDisposedException (eclipse bug). Hence we cannot use
         * dynamic labels (XPD-7639 - has already changed labels to static
         * ones.)
         */
        // labelProvider.setFireLabelUpdateNotifications(true);
        labelProvider.setFireLabelUpdateNotifications(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(Object element) {
        EObject unwrapped = unwrap(element);
        return WorkingCopyUtil.getMetaText(unwrapped);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object element) {
        Object unwrapped = unwrap(element);
        return super.getImage(unwrapped);
    }

    /**
     * Unwraps element (selection) and tries to get an EObject (by casting or
     * adoption).
     * 
     * @param element
     *            the element to unwrap.
     * @return unwrapped EObject or 'null'.
     */
    private EObject unwrap(Object element) {
        EObject eo = null;
        if (element instanceof IStructuredSelection) {
            eo = unwrap(((IStructuredSelection) element).getFirstElement());
        }
        if (element instanceof IAdaptable) {
            Object eObjAdapter =
                    ((IAdaptable) element).getAdapter(EObject.class);
            if (eObjAdapter != null && eObjAdapter instanceof EObject) {
                eo = (EObject) eObjAdapter;
            }
        } else if (element instanceof EObject) {
            eo = (EObject) element;
        }
        return eo;
    }

}