/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.navigator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.ui.properties.descriptor.PropertyLabelProvider;

/**
 * Provides labels for header of property pages.
 * 
 */
public class HeaderMetaLabelProvider extends PropertyLabelProvider {

    private final AdapterFactoryLabelProvider afLabelProvider;

    public HeaderMetaLabelProvider() {
        ComposedAdapterFactory composedAdapterFactory =
                new ComposedAdapterFactory(
                        ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
        afLabelProvider =
                new AdapterFactoryLabelProvider(composedAdapterFactory);
    }

    /**
     * Get meta-data name of the EObject, it takes translatable description of
     * the EClass from the model.edit plug-in.
     * <p>
     * Example: for EObject of type Activity it will return 'Activity', as
     * opposite to getText() which will return activity name.
     */
    @Override
    public String getText(Object element) {
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }
        if (element instanceof EObject) {
            EObject eo = (EObject) element;

            ResourceLocator resourceLocator = getResourceLocator(eo);
            if (resourceLocator != null) {
                EClass eclass = eo.eClass();
                return resourceLocator.getString("_UI_" //$NON-NLS-1$
                        + eclass.getName() + "_type"); //$NON-NLS-1$				
            }
        }
        return super.getText(element);
    }

    @Override
    public Image getImage(Object sel) {
        Object element = sel;
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }
        if (element instanceof EObject) {
            Image image = afLabelProvider.getImage(element);
            if (image != null) {
                return image;
            }
        }
        return super.getImage(element);
    }

    /**
     * First tries to use adapterFactory to find IItemLabelProvider adapter
     * (which is usually an ItemProviderAdapter and a ResourceLocator). (The
     * benefit of this step is that the adapter will be created if it doesn't
     * exist yet.)
     * 
     * Next if first step failed then it'll try to get first ResourceLocator
     * (usually an ItemProviderAdapter) form object's adapters. Adapter should
     * be already associated with an object in this case. If both steps fails
     * then 'null' is returned.
     */
    private ResourceLocator getResourceLocator(EObject eo) {
        Adapter labelAdapter =
                afLabelProvider.getAdapterFactory().adapt(eo,
                        IItemLabelProvider.class);
        if (labelAdapter instanceof ResourceLocator) {
            return (ResourceLocator) labelAdapter;
        }
        for (Adapter adapter : eo.eAdapters()) {
            if (adapter instanceof ResourceLocator) {
                return (ResourceLocator) adapter;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (afLabelProvider != null) {
            afLabelProvider.dispose();
        }
        super.dispose();
    }

}
