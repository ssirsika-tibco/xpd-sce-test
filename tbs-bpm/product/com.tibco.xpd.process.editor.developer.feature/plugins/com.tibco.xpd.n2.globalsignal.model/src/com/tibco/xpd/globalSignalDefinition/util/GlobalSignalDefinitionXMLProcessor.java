/**
 */
package com.tibco.xpd.globalSignalDefinition.util;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;

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
public class GlobalSignalDefinitionXMLProcessor extends XMLProcessor {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        GlobalSignalDefinitionPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the GlobalSignalDefinitionResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new GlobalSignalDefinitionResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new GlobalSignalDefinitionResourceFactoryImpl());
        }
        return registrations;
    }

} //GlobalSignalDefinitionXMLProcessor
