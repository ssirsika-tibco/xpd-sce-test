/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.util;

import com.tibco.xpd.scheduling.calendar.CalendarPackage;

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
public class CalendarXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalendarXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        CalendarPackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the CalendarResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new CalendarResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new CalendarResourceFactoryImpl());
        }
        return registrations;
    }

} //CalendarXMLProcessor
