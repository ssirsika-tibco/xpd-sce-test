/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * 
 * @see com.tibco.example.model.util.ModelResourceFactoryImpl
 * @generated
 */
public class ModelResourceImpl extends XMLResourceImpl {
    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param uri
     *            the URI of the new resource.
     * 
     *            THIS METHOD IS RPEVENTED FROM REGENERATING - SEE BELOW
     * 
     * @generated NOT
     */
    public ModelResourceImpl(URI uri) {
        super(uri);

        /**
         * IF WE DO NOT setTrackingModification(true) IT DOES NOT UPDATE THE
         * DIRTY FLAG CORRECTLY.
         */
        setTrackingModification(true);
    }

} // ModelResourceImpl
