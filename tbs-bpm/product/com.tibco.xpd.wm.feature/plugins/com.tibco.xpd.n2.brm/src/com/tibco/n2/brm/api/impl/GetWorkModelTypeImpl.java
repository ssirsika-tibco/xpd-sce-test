/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkModelType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Model Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkModelTypeImpl#getWorkModelID <em>Work Model ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkModelTypeImpl extends EObjectImpl implements GetWorkModelType {
    /**
     * The default value of the '{@link #getWorkModelID() <em>Work Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelID()
     * @generated
     * @ordered
     */
    protected static final String WORK_MODEL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkModelID() <em>Work Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelID()
     * @generated
     * @ordered
     */
    protected String workModelID = WORK_MODEL_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkModelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_MODEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkModelID() {
        return workModelID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelID(String newWorkModelID) {
        String oldWorkModelID = workModelID;
        workModelID = newWorkModelID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_MODEL_TYPE__WORK_MODEL_ID, oldWorkModelID, workModelID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_MODEL_TYPE__WORK_MODEL_ID:
                return getWorkModelID();
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
            case N2BRMPackage.GET_WORK_MODEL_TYPE__WORK_MODEL_ID:
                setWorkModelID((String)newValue);
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
            case N2BRMPackage.GET_WORK_MODEL_TYPE__WORK_MODEL_ID:
                setWorkModelID(WORK_MODEL_ID_EDEFAULT);
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
            case N2BRMPackage.GET_WORK_MODEL_TYPE__WORK_MODEL_ID:
                return WORK_MODEL_ID_EDEFAULT == null ? workModelID != null : !WORK_MODEL_ID_EDEFAULT.equals(workModelID);
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
        result.append(" (workModelID: ");
        result.append(workModelID);
        result.append(')');
        return result.toString();
    }

} //GetWorkModelTypeImpl
