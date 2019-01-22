/**
 */
package com.tibco.xpd.globalSignalDefinition.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc --> The <b>Resource Factory</b> associated with the
 * package. <!-- end-user-doc -->
 * @see com.tibco.xpd.globalSignalDefinition.util.GlobalSignalDefinitionResourceImpl
 * @generated
 */
public class GlobalSignalDefinitionResourceFactoryImpl extends
        ResourceFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NO
     */
    @Override
    public Resource createResource(URI uri) {
        XMLResource result = new GlobalSignalDefinitionResourceImpl(uri);
        result.getDefaultSaveOptions()
                .put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        result.getDefaultLoadOptions()
                .put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

        result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION,
                Boolean.TRUE);

        result.getDefaultLoadOptions()
                .put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
                        Boolean.TRUE);
        result.getDefaultSaveOptions()
                .put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
                        Boolean.TRUE);

        result.getDefaultLoadOptions()
                .put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

        result.setTrackingModification(true);
        return result;
    }

} // GlobalSignalDefinitionResourceFactoryImpl
