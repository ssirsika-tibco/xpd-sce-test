/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

/**
 * <!-- begin-user-doc --> The <b>Resource Factory</b> associated with the
 * package. <!-- end-user-doc -->
 * @see com.tibco.xpd.presentation.channels.util.ChannelsResourceImpl
 * @generated
 */
public class ChannelsResourceFactoryImpl extends ResourceFactoryImpl {

    private final static String UTF8_XMI_ENCODING = "UTF-8"; //$NON-NLS-1$

    /**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public ChannelsResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Resource createResource(URI uri) {
        XMIResource resource = new ChannelsResourceImpl(uri);
        if (!resource.getEncoding().equals(UTF8_XMI_ENCODING))
            resource.setEncoding(UTF8_XMI_ENCODING);
        return resource;
    }

} // ChannelsResourceFactoryImpl
