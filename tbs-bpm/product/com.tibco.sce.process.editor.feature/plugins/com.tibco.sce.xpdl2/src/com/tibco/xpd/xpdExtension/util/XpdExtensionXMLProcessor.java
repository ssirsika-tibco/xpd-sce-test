/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class XpdExtensionXMLProcessor extends XMLProcessor {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XpdExtensionXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        XpdExtensionPackage.eINSTANCE.eClass();
    }

    /**
     * Register for "*" and "xml" file extensions the XpdExtensionResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION,
                    new XpdExtensionResourceFactoryImpl());
            registrations.put(STAR_EXTENSION,
                    new XpdExtensionResourceFactoryImpl());
        }
        return registrations;
    }

} //XpdExtensionXMLProcessor
