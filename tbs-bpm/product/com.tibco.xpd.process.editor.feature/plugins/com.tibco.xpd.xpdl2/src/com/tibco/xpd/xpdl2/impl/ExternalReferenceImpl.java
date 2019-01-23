/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl#getXref <em>Xref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExternalReferenceImpl extends DataTypeImpl implements
        ExternalReference {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected static final String LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected String location = LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected static final String NAMESPACE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected String namespace = NAMESPACE_EDEFAULT;

    /**
     * The default value of the '{@link #getXref() <em>Xref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXref()
     * @generated
     * @ordered
     */
    protected static final String XREF_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getXref() <em>Xref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXref()
     * @generated
     * @ordered
     */
    protected String xref = XREF_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExternalReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.EXTERNAL_REFERENCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(String newLocation) {
        String oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EXTERNAL_REFERENCE__LOCATION, oldLocation,
                    location));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNamespace(String newNamespace) {
        String oldNamespace = namespace;
        namespace = newNamespace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EXTERNAL_REFERENCE__NAMESPACE, oldNamespace,
                    namespace));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getXref() {
        return xref;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setXref(String newXref) {
        String oldXref = xref;
        xref = newXref;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EXTERNAL_REFERENCE__XREF, oldXref, xref));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.EXTERNAL_REFERENCE__LOCATION:
            return getLocation();
        case Xpdl2Package.EXTERNAL_REFERENCE__NAMESPACE:
            return getNamespace();
        case Xpdl2Package.EXTERNAL_REFERENCE__XREF:
            return getXref();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.EXTERNAL_REFERENCE__LOCATION:
            setLocation((String) newValue);
            return;
        case Xpdl2Package.EXTERNAL_REFERENCE__NAMESPACE:
            setNamespace((String) newValue);
            return;
        case Xpdl2Package.EXTERNAL_REFERENCE__XREF:
            setXref((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.EXTERNAL_REFERENCE__LOCATION:
            setLocation(LOCATION_EDEFAULT);
            return;
        case Xpdl2Package.EXTERNAL_REFERENCE__NAMESPACE:
            setNamespace(NAMESPACE_EDEFAULT);
            return;
        case Xpdl2Package.EXTERNAL_REFERENCE__XREF:
            setXref(XREF_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.EXTERNAL_REFERENCE__LOCATION:
            return LOCATION_EDEFAULT == null ? location != null
                    : !LOCATION_EDEFAULT.equals(location);
        case Xpdl2Package.EXTERNAL_REFERENCE__NAMESPACE:
            return NAMESPACE_EDEFAULT == null ? namespace != null
                    : !NAMESPACE_EDEFAULT.equals(namespace);
        case Xpdl2Package.EXTERNAL_REFERENCE__XREF:
            return XREF_EDEFAULT == null ? xref != null : !XREF_EDEFAULT
                    .equals(xref);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (location: "); //$NON-NLS-1$
        result.append(location);
        result.append(", namespace: "); //$NON-NLS-1$
        result.append(namespace);
        result.append(", xref: "); //$NON-NLS-1$
        result.append(xref);
        result.append(')');
        return result.toString();
    }

} //ExternalReferenceImpl
