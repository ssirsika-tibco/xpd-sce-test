/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.internal.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositePropertySource;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Eclipse Adapter factory for adapting EObject into property source. Exact list
 * of classes (subclasses of EObject) that has property source is specified in
 * plugin.xml.
 * 
 * @author wzurek
 */
public class PropertySourceAdapterFactory implements IAdapterFactory {

    /**
     * Produce property source object for given EObject. It use
     * IItemPropertySource as a base.
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof EObject) {
            EObject eo = (EObject) adaptableObject;
            TransactionalEditingDomain ed = TransactionUtil
                    .getEditingDomain(eo);
            if (ed instanceof AdapterFactoryEditingDomain) {
                AdapterFactory adapterFactory = ((AdapterFactoryEditingDomain) ed)
                        .getAdapterFactory();
                IItemPropertySource propsrc = (IItemPropertySource) adapterFactory
                        .adapt(eo, IItemPropertySource.class);
                if (propsrc != null) {
                    EMFCompositePropertySource propertySource = new EMFCompositePropertySourceEx(
                            eo, propsrc, null);
                    return propertySource;
                }
            }
        }
        return null;
    }

    /**
     * list of produced types.
     */
    @SuppressWarnings("unchecked")
    public Class[] getAdapterList() {
        return new Class[] { IPropertySource.class };
    }

}
