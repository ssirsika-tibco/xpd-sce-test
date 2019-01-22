/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.internal.properties;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

/**
 * ItemPropertyDescriptor based on GMF parser.
 * 
 * @author wzurek
 * 
 */
public class ItemPropertyDescriptorForModifier extends ItemPropertyDescriptor {

    private PropertyModifier modifier;

    /**
     * The Constructor.
     * 
     * All parameters apart from parser are passed to the parent.
     * 
     * @see ItemPropertyDescriptor
     * 
     * @param adapterFactory
     * @param resourceLocator
     * @param displayName
     * @param description
     * @param isSettable
     * @param multiLine
     * @param sortChoices
     * @param staticImage
     * @param category
     * @param filterFlags
     * @param modifier
     */
    public ItemPropertyDescriptorForModifier(AdapterFactory adapterFactory,
            ResourceLocator resourceLocator, String displayName,
            String description, boolean isSettable, boolean multiLine,
            boolean sortChoices, Object staticImage, String category,
            String[] filterFlags, PropertyModifier modifier) {
        super(adapterFactory, resourceLocator, displayName, description, null,
                isSettable, multiLine, sortChoices, staticImage, category,
                filterFlags);
        this.modifier = modifier;
    }

    @Override
    public Object getFeature(Object object) {
        return modifier;
    }

    @Override
    public Object getPropertyValue(Object object) {
        return modifier.getPropertyValue(object);
    }

    @Override
    public void setPropertyValue(Object object, Object value) {
        modifier.setPropertyValue(object, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#resetPropertyValue(java.lang.Object)
     */
    @Override
    public void resetPropertyValue(Object object) {
        modifier.resetPropertyValue(object);
    }
}