/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.providers;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.uml2.uml.Element;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.properties.EMFCompositePropertySourceEx;

/**
 * <code>Classifier</code> properties provider for the properties service.
 * This provides the <code>Classifier</code> properties in the advanced tab.
 * 
 * @author njpatel
 * 
 */
public class ClassifierPropertiesProvider implements IPropertiesProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider#getPropertySource(java.lang.Object)
     */
    public ICompositePropertySource getPropertySource(Object object) {
        ICompositePropertySource source = null;

        if (object instanceof Element) {
            TransactionalEditingDomain ed = TransactionUtil
                    .getEditingDomain(object);

            if (ed instanceof AdapterFactoryEditingDomain) {
                IItemPropertySource propSource = (IItemPropertySource) ((AdapterFactoryEditingDomain) ed)
                        .getAdapterFactory().adapt(object,
                                IItemPropertySource.class);
                source = new EMFCompositePropertySourceEx(
                        object,
                        propSource,
                        Messages.ClassifierPropertiesProvider_generalCategory_label);
            }
        }
        return source;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void addProviderChangeListener(IProviderChangeListener listener) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof GetPropertySourceOperation) {
            Object object = ((GetPropertySourceOperation) operation)
                    .getObject();

            return object instanceof Element;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
     */
    public void removeProviderChangeListener(IProviderChangeListener listener) {
        // Nothing to do

    }

}
