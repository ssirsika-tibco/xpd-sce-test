package com.tibco.xpd.bom.resources.ui.internal.properties;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.swt.widgets.Control;

/**
 * Property modifier that use popup-dialog for modifying a property.
 * 
 * @author wzurek
 */
public interface PropertyModifier {
    /**
     * @param parentControl
     * @param object
     * @param itemPropertyDescriptor
     * 
     * @return
     */
    Object performAction(Control parentControl,
            IItemPropertyDescriptor itemPropertyDescriptor, Object object);

    /**
     * @param object
     * @return
     */
    Object getPropertyValue(Object object);

    /**
     * @param object
     * @param value
     */
    void setPropertyValue(Object object, Object value);

    /**
     * @param object
     */
    void resetPropertyValue(Object object);
}