/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.channeltype.util;

import com.tibco.n2.common.channeltype.ChanneltypePackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ChanneltypeXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChanneltypeXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        ChanneltypePackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the ChanneltypeResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new ChanneltypeResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new ChanneltypeResourceFactoryImpl());
        }
        return registrations;
    }

} //ChanneltypeXMLProcessor
