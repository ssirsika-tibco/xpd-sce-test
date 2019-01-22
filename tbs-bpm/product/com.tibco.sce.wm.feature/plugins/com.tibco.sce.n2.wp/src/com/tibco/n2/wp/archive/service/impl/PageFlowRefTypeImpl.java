/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.PageFlowRefType;
import com.tibco.n2.wp.archive.service.WPPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page Flow Ref Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowRefTypeImpl#getRefId <em>Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageFlowRefTypeImpl extends EObjectImpl implements PageFlowRefType {
    /**
     * The default value of the '{@link #getRefId() <em>Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRefId()
     * @generated
     * @ordered
     */
    protected static final String REF_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRefId() <em>Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRefId()
     * @generated
     * @ordered
     */
    protected String refId = REF_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PageFlowRefTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.PAGE_FLOW_REF_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRefId() {
        return refId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRefId(String newRefId) {
        String oldRefId = refId;
        refId = newRefId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_REF_TYPE__REF_ID, oldRefId, refId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case WPPackage.PAGE_FLOW_REF_TYPE__REF_ID:
                return getRefId();
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
            case WPPackage.PAGE_FLOW_REF_TYPE__REF_ID:
                setRefId((String)newValue);
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
            case WPPackage.PAGE_FLOW_REF_TYPE__REF_ID:
                setRefId(REF_ID_EDEFAULT);
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
            case WPPackage.PAGE_FLOW_REF_TYPE__REF_ID:
                return REF_ID_EDEFAULT == null ? refId != null : !REF_ID_EDEFAULT.equals(refId);
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (refId: ");
        result.append(refId);
        result.append(')');
        return result.toString();
    }

} //PageFlowRefTypeImpl
