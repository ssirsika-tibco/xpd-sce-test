/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.properites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.wsdl.Port;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.wsdl.ui.WsdlPortWrapper;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Property source for WsdlPortTypeWrapper object.
 * <p>
 * <i>Created: 20 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class PortWrapperPropertySource extends WsdlWrapperPropertySource {

    private static final String PORT_BINDING_LABEL = Messages.PortWrapperPropertySource_Binding_label;
    private static final String PORT_BINDING_ID = "portBinding"; //$NON-NLS-1$
    private static final Object SOAP_LOCATION_URL_ID = "soapBindingLocationUrl"; //$NON-NLS-1$
    private static final String SOAP_LOCATION_URL_LABEL = Messages.PortWrapperPropertySource_Address_label;

    private IPropertyDescriptor[] descriptors;

    /**
     * The constructor.
     * 
     * @param wsdlPortWrapper
     *            the wrapper for which the property source is created.
     */
    public PortWrapperPropertySource(WsdlPortWrapper wsdlPortWrapper) {
        super(wsdlPortWrapper);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource#getEditableValue()
     */
    @Override
    public Object getEditableValue() {
        return null;
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource#getPropertyDescriptors()
     */
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descriptors == null) {
            List<IPropertyDescriptor> baseDescriptiors = new ArrayList<IPropertyDescriptor>(
                    Arrays.asList(super.getPropertyDescriptors()));
            PropertyDescriptor bindingDescriptor = new PropertyDescriptor(
                    PORT_BINDING_ID, PORT_BINDING_LABEL);
            bindingDescriptor.setAlwaysIncompatible(true);
            baseDescriptiors.add(bindingDescriptor);
            if (getSoapBindingAddressUrl() != null) {
                PropertyDescriptor soapUrlDescriptor = new PropertyDescriptor(
                        SOAP_LOCATION_URL_ID, SOAP_LOCATION_URL_LABEL);
                soapUrlDescriptor.setAlwaysIncompatible(true);
                baseDescriptiors.add(soapUrlDescriptor);
            }
            descriptors = baseDescriptiors
                    .toArray(new IPropertyDescriptor[baseDescriptiors.size()]);
        }
        return descriptors;
    }

    /*
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    @Override
    public Object getPropertyValue(Object id) {
        if (PORT_BINDING_ID.equals(id) && getPort().getBinding() != null
                && getPort().getBinding().getQName() != null) {
            QName name = getPort().getBinding().getQName();
            return (name.getPrefix() != null && name.getPrefix().trim()
                    .length() > 0) ? name.getPrefix() + ':'
                    + name.getLocalPart() : name.getLocalPart();
        } else if (SOAP_LOCATION_URL_ID.equals(id)) {
            return getSoapBindingAddressUrl();
        }
        return super.getPropertyValue(id);
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource#isPropertySet(java.lang.Object)
     */
    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource#resetPropertyValue(java.lang.Object)
     */
    @Override
    public void resetPropertyValue(Object id) {
    }

    /*
     * @see com.tibco.xpd.wsdl.ui.properites.WsdlWrapperPropertySource#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    @Override
    public void setPropertyValue(Object id, Object value) {
    }

    private Port getPort() {
        return (Port) (getWrapper()).getObject();
    }

    @SuppressWarnings("unchecked")
    private String getSoapBindingAddressUrl() {
        StringBuilder sb = new StringBuilder();
        if (getPort().getBinding() != null) {
            List extensibilityElements = getPort().getExtensibilityElements();
            for (Object element : extensibilityElements) {
                if (element instanceof SOAPAddress) {
                    SOAPAddress addres = (SOAPAddress) element;
                    if (sb.length() > 0) {
                        sb.append(';').append(' ');
                    }
                    sb.append(addres.getLocationURI());
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
        }
        return null;
    }
}