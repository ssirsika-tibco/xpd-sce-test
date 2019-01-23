/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.util;

import com.tibco.n2.common.organisation.api.OrganisationPackage;

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
public class OrganisationXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        OrganisationPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the OrganisationResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new OrganisationResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new OrganisationResourceFactoryImpl());
        }
        return registrations;
    }

} //OrganisationXMLProcessor
