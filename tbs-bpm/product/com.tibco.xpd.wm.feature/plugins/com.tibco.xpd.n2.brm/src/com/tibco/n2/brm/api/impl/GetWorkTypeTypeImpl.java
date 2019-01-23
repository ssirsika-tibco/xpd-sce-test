/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkTypeType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Type Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkTypeTypeImpl#getWorkTypeID <em>Work Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkTypeTypeImpl extends EObjectImpl implements GetWorkTypeType {
    /**
     * The default value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected String workTypeID = WORK_TYPE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkTypeTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_TYPE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeID() {
        return workTypeID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeID(String newWorkTypeID) {
        String oldWorkTypeID = workTypeID;
        workTypeID = newWorkTypeID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_TYPE_TYPE__WORK_TYPE_ID, oldWorkTypeID, workTypeID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_TYPE_TYPE__WORK_TYPE_ID:
                return getWorkTypeID();
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
            case N2BRMPackage.GET_WORK_TYPE_TYPE__WORK_TYPE_ID:
                setWorkTypeID((String)newValue);
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
            case N2BRMPackage.GET_WORK_TYPE_TYPE__WORK_TYPE_ID:
                setWorkTypeID(WORK_TYPE_ID_EDEFAULT);
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
            case N2BRMPackage.GET_WORK_TYPE_TYPE__WORK_TYPE_ID:
                return WORK_TYPE_ID_EDEFAULT == null ? workTypeID != null : !WORK_TYPE_ID_EDEFAULT.equals(workTypeID);
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
        result.append(" (workTypeID: ");
        result.append(workTypeID);
        result.append(')');
        return result.toString();
    }

} //GetWorkTypeTypeImpl
