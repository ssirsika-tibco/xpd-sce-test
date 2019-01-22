/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class WorkListFacadeXMLProcessor extends XMLProcessor {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListFacadeXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        WorkListFacadePackage.eINSTANCE.eClass();
    }

    /**
     * Register for "*" and "xml" file extensions the WorkListFacadeResourceFactoryImpl factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION,
                    new WorkListFacadeResourceFactoryImpl());
            registrations.put(STAR_EXTENSION,
                    new WorkListFacadeResourceFactoryImpl());
        }
        return registrations;
    }

} // WorkListFacadeXMLProcessor
