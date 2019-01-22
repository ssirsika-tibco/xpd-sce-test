package com.tibco.xpd.bom.resources.ui.internal.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositePropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * Extension to the EMFCompositePropertySource that supports parsers in place of
 * simple features.
 * 
 * @author wzurek
 */
public class EMFCompositePropertySourceEx extends EMFCompositePropertySource {

    /**
     * @param object
     * @param itemPropertySource
     * @param category
     */
    public EMFCompositePropertySourceEx(Object object,
            IItemPropertySource itemPropertySource, String category) {
        super(object, itemPropertySource, category);
    }

    @Override
    protected IPropertyDescriptor newPropertyDescriptor(
            IItemPropertyDescriptor itemPropertyDescriptor) {
        return new EMFCompositeSourcePropertyDescriptorEx(getObject(),
                itemPropertyDescriptor, getCategory());
    }

    @Override
    public boolean isPropertySet(Object propertyId) {
        return true;
    }

    protected void refreshCache() {
        getLocalDescriptors().clear();
        Collection<IPropertyDescriptor> result = new ArrayList<IPropertyDescriptor>();
        List<IItemPropertyDescriptor> itemPropertyDescriptors = itemPropertySource
                .getPropertyDescriptors(object);
        if (itemPropertyDescriptors != null) {
            for (IItemPropertyDescriptor itemPropertyDescriptor : itemPropertyDescriptors) {
                result.add(createPropertyDescriptor(itemPropertyDescriptor));
            }
        }
    }

    @Override
    public void setPropertyValue(Object propertyId, Object value) {
        super.setPropertyValue(propertyId, value);
        refreshCache();
    }

    @Override
    public void resetPropertyValue(Object propertyId) {
        super.resetPropertyValue(propertyId);
        refreshCache();
    }
}