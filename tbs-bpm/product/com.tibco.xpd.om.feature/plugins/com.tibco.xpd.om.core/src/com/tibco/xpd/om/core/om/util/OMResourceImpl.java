/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.core.om.util.OMResourceFactoryImpl
 * @generated
 */
public class OMResourceImpl extends XMIResourceImpl {
    /**
     * Creates an instance of the resource.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param uri the URI of the new resource.
     * @generated
     */
    public OMResourceImpl(URI uri) {
        super(uri);
    }

    @Override
    protected boolean useUUIDs() {
        return true;
    }

} // OMResourceImpl
