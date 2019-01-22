/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.workmodel.util;

import com.tibco.n2.brm.workmodel.WorkmodelPackage;

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
public class WorkmodelXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkmodelXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        WorkmodelPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the WorkmodelResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new WorkmodelResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new WorkmodelResourceFactoryImpl());
        }
        return registrations;
    }

} //WorkmodelXMLProcessor
