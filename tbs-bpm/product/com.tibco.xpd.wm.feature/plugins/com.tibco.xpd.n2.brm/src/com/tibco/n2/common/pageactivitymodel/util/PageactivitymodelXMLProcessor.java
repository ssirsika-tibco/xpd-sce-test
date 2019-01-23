/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel.util;

import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

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
public class PageactivitymodelXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageactivitymodelXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        PageactivitymodelPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the PageactivitymodelResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new PageactivitymodelResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new PageactivitymodelResourceFactoryImpl());
        }
        return registrations;
    }

} //PageactivitymodelXMLProcessor
