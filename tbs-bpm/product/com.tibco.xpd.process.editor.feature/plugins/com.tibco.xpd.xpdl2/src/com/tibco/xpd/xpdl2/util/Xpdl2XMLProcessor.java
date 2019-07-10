/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class Xpdl2XMLProcessor extends XMLProcessor {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2XMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        Xpdl2Package.eINSTANCE.eClass();
    }

    /**
     * Register for "*" and "xml" file extensions the Xpdl2ResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    protected Map getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new Xpdl2ResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new Xpdl2ResourceFactoryImpl());
            registrations.put("xpdl", new Xpdl2ResourceFactoryImpl()); //$NON-NLS-1$
        }
        return registrations;
    }

} //Xpdl2XMLProcessor
