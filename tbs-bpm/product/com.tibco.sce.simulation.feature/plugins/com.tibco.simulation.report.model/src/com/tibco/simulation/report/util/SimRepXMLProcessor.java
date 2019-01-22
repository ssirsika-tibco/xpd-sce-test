/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.util;

import com.tibco.simulation.report.SimRepPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SimRepXMLProcessor extends XMLProcessor {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepXMLProcessor() {
        super(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
        extendedMetaData.putPackage(null, SimRepPackage.eINSTANCE);
    }

    /**
     * Register for "*" and "xml" file extensions the SimRepResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new SimRepResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new SimRepResourceFactoryImpl());
        }
        return registrations;
    }

} //SimRepXMLProcessor
