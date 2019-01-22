/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade.util;

import com.tibco.n2.common.attributefacade.AttributefacadePackage;

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
public class AttributefacadeXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributefacadeXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        AttributefacadePackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the AttributefacadeResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new AttributefacadeResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new AttributefacadeResourceFactoryImpl());
        }
        return registrations;
    }

} //AttributefacadeXMLProcessor
