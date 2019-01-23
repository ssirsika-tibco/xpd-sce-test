/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.resources.wc.gmf.XpdResourceFactory;

/**
 * <!-- begin-user-doc --> The <b>Resource Factory</b> associated with the
 * package. <!-- end-user-doc -->
 * 
 * @see com.tibco.xpd.om.core.om.util.OMResourceImpl
 * @generated NOT
 */
public class OMResourceFactoryImpl extends XpdResourceFactory {
    /**
     * Creates an instance of the resource factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public OMResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Resource createResource(URI uri) {
        // TODO Refactor to load and discover types lazily when browsing for
        // types.
        // Makes sure that standard types are always loaded when the OM Resource
        // is loaded.

        //if (!uri.toString().startsWith("pathmap:")) { //$NON-NLS-1$
        // URI typesURI = URI
        //                    .createURI("pathmap://OM_TYPES/StandardOMTypes.meta.om"); //$NON-NLS-1$
        // XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet()
        // .getResource(typesURI, true);
        // }
        return super.createResource(uri);
    }

} // OMResourceFactoryImpl
