/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.util;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EaijavaXMLProcessor extends XMLProcessor {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$


    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EaijavaXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        EaijavaPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the EaijavaResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Map getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new EaijavaResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new EaijavaResourceFactoryImpl());
        }
        return registrations;
    }

} //EaijavaXMLProcessor
