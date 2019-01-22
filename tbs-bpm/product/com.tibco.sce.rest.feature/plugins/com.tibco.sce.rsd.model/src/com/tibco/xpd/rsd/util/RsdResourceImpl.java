/**
 */
package com.tibco.xpd.rsd.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.rsd.util.RsdResourceFactoryImpl
 * @generated
 */
public class RsdResourceImpl extends XMIResourceImpl {
    /**
     * Creates an instance of the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param uri
     *            the URI of the new resource.
     * @generated NOT
     */
    public RsdResourceImpl(URI uri) {
        super(uri);
        setTrackingModification(true);
    }

    @Override
    protected boolean useUUIDs() {
        return true;
    }
} // RsdResourceImpl
