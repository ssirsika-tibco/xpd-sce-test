/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.util;

import com.tibco.xpd.script.descriptor.DescriptorPackage;

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
public class DescriptorXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DescriptorXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        DescriptorPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the DescriptorResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new DescriptorResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new DescriptorResourceFactoryImpl());
        }
        return registrations;
    }

} //DescriptorXMLProcessor
