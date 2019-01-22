/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.util;

import com.tibco.n2.brm.api.N2BRMPackage;

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
public class N2BRMXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        N2BRMPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the N2BRMResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new N2BRMResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new N2BRMResourceFactoryImpl());
        }
        return registrations;
    }

} //N2BRMXMLProcessor
