/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.worktype.util;

import com.tibco.n2.common.worktype.WorktypePackage;

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
public class WorktypeXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorktypeXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        WorktypePackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the WorktypeResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new WorktypeResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new WorktypeResourceFactoryImpl());
        }
        return registrations;
    }

} //WorktypeXMLProcessor
