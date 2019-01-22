/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Org Entity Config Attributes Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesResponseTypeImpl#isSuccess <em>Success</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetOrgEntityConfigAttributesResponseTypeImpl extends EObjectImpl implements SetOrgEntityConfigAttributesResponseType {
    /**
     * The default value of the '{@link #isSuccess() <em>Success</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSuccess()
     * @generated
     * @ordered
     */
    protected static final boolean SUCCESS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSuccess() <em>Success</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSuccess()
     * @generated
     * @ordered
     */
    protected boolean success = SUCCESS_EDEFAULT;

    /**
     * This is true if the Success attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean successESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SetOrgEntityConfigAttributesResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSuccess(boolean newSuccess) {
        boolean oldSuccess = success;
        success = newSuccess;
        boolean oldSuccessESet = successESet;
        successESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS, oldSuccess, success, !oldSuccessESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSuccess() {
        boolean oldSuccess = success;
        boolean oldSuccessESet = successESet;
        success = SUCCESS_EDEFAULT;
        successESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS, oldSuccess, SUCCESS_EDEFAULT, oldSuccessESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSuccess() {
        return successESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS:
                return isSuccess();
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS:
                setSuccess((Boolean)newValue);
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS:
                unsetSuccess();
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS:
                return isSetSuccess();
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
        result.append(" (success: ");
        if (successESet) result.append(success); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //SetOrgEntityConfigAttributesResponseTypeImpl
