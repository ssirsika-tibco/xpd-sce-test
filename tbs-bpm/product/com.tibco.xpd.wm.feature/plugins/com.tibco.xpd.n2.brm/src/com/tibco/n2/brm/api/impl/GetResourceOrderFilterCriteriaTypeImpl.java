/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Resource Order Filter Criteria Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaTypeImpl#getResourceID <em>Resource ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetResourceOrderFilterCriteriaTypeImpl extends EObjectImpl implements GetResourceOrderFilterCriteriaType {
    /**
     * The default value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected String resourceID = RESOURCE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetResourceOrderFilterCriteriaTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceID(String newResourceID) {
        String oldResourceID = resourceID;
        resourceID = newResourceID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID, oldResourceID, resourceID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                return getResourceID();
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
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                setResourceID((String)newValue);
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
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                setResourceID(RESOURCE_ID_EDEFAULT);
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
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                return RESOURCE_ID_EDEFAULT == null ? resourceID != null : !RESOURCE_ID_EDEFAULT.equals(resourceID);
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
        result.append(" (resourceID: ");
        result.append(resourceID);
        result.append(')');
        return result.toString();
    }

} //GetResourceOrderFilterCriteriaTypeImpl
