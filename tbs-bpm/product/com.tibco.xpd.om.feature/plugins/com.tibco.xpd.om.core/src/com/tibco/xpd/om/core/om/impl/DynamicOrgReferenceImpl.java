/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Org Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DynamicOrgReferenceImpl extends EObjectImpl implements
        DynamicOrgReference {
    /**
     * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected DynamicOrgUnit from;

    /**
     * The cached value of the '{@link #getTo() <em>To</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected Organization to;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DynamicOrgReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.DYNAMIC_ORG_REFERENCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DynamicOrgUnit getFrom() {
        if (from != null && from.eIsProxy()) {
            InternalEObject oldFrom = (InternalEObject) from;
            from = (DynamicOrgUnit) eResolveProxy(oldFrom);
            if (from != oldFrom) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.DYNAMIC_ORG_REFERENCE__FROM, oldFrom,
                            from));
            }
        }
        return from;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DynamicOrgUnit basicGetFrom() {
        return from;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFrom(DynamicOrgUnit newFrom) {
        DynamicOrgUnit oldFrom = from;
        from = newFrom;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.DYNAMIC_ORG_REFERENCE__FROM, oldFrom, from));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Organization getTo() {
        if (to != null && to.eIsProxy()) {
            InternalEObject oldTo = (InternalEObject) to;
            to = (Organization) eResolveProxy(oldTo);
            if (to != oldTo) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.DYNAMIC_ORG_REFERENCE__TO, oldTo, to));
            }
        }
        return to;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Organization basicGetTo() {
        return to;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTo(Organization newTo) {
        Organization oldTo = to;
        to = newTo;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.DYNAMIC_ORG_REFERENCE__TO, oldTo, to));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.DYNAMIC_ORG_REFERENCE__FROM:
            if (resolve)
                return getFrom();
            return basicGetFrom();
        case OMPackage.DYNAMIC_ORG_REFERENCE__TO:
            if (resolve)
                return getTo();
            return basicGetTo();
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
        case OMPackage.DYNAMIC_ORG_REFERENCE__FROM:
            setFrom((DynamicOrgUnit) newValue);
            return;
        case OMPackage.DYNAMIC_ORG_REFERENCE__TO:
            setTo((Organization) newValue);
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
        case OMPackage.DYNAMIC_ORG_REFERENCE__FROM:
            setFrom((DynamicOrgUnit) null);
            return;
        case OMPackage.DYNAMIC_ORG_REFERENCE__TO:
            setTo((Organization) null);
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
        case OMPackage.DYNAMIC_ORG_REFERENCE__FROM:
            return from != null;
        case OMPackage.DYNAMIC_ORG_REFERENCE__TO:
            return to != null;
        }
        return super.eIsSet(featureID);
    }

} //DynamicOrgReferenceImpl
