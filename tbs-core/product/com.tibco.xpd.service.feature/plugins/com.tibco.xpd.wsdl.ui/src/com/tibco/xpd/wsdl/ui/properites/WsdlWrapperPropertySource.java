/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.properites;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.wsdl.ui.WsdlObjectWrapper;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Base property source class for WSDL wrapper objects. This base property
 * source provides only read-only "Name" property for every element. If there is
 * a need to provide additional properties for particular elements then
 * subclasses should prepend property descriptor's array from superclass.
 * <p>
 * <i>Created: 20 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WsdlWrapperPropertySource implements IPropertySource {

    /** Name property of WSDL element. */
    public static final String NAME_PROPERTY_ID = "name"; //$NON-NLS-1$

    public static final String NAME_PROPERTY_LABEL = Messages.WsdlWrapperPropertySource_Name_Label;

    private IPropertyDescriptor[] descriptors;

    private final WsdlObjectWrapper wsdlObjectWrapper;

    public WsdlWrapperPropertySource(WsdlObjectWrapper wsdlObjectWrapper) {
        this.wsdlObjectWrapper = wsdlObjectWrapper;
    }

    public Object getEditableValue() {
        return null;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descriptors == null) {
            PropertyDescriptor nameDescriptor = new PropertyDescriptor(
                    NAME_PROPERTY_ID, NAME_PROPERTY_LABEL);
            nameDescriptor.setAlwaysIncompatible(true);
            descriptors = new IPropertyDescriptor[] { nameDescriptor };
        }
        return descriptors;
    }

    public Object getPropertyValue(Object id) {
        if (NAME_PROPERTY_ID.equals(id)) {
            return wsdlObjectWrapper.getWsdlObjectLocalName();
        }
        return null;
    }

    public boolean isPropertySet(Object id) {
        return false;
    }

    public void resetPropertyValue(Object id) {
    }

    public void setPropertyValue(Object id, Object value) {
    }

    public WsdlObjectWrapper getWrapper() {
        return wsdlObjectWrapper;
    }

}