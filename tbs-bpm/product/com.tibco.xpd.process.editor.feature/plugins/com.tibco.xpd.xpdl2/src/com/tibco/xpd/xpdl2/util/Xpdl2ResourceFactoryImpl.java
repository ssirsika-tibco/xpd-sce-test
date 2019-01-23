/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.util.Xpdl2ResourceImpl
 * @generated
 */
public class Xpdl2ResourceFactoryImpl extends ResourceFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2ResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     */
    public Resource createResource(URI uri) {
        XMLResource result = new Xpdl2ResourceImpl(uri);
        result.getDefaultSaveOptions()
                .put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        result.getDefaultLoadOptions()
                .put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

        result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION,
                Boolean.TRUE);
        result.getDefaultSaveOptions()
                .put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
                        Boolean.TRUE);

        result.getDefaultLoadOptions()
                .put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

        //Set UTF-8 encoding
        result.getDefaultSaveOptions()
                .put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$

        return result;
    }

} //Xpdl2ResourceFactoryImpl
