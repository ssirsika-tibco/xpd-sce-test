/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Org Entity Config Attributes Available Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl#getStartAt <em>Start At</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl#getNumToReturn <em>Num To Return</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetOrgEntityConfigAttributesAvailableTypeImpl extends EObjectImpl implements GetOrgEntityConfigAttributesAvailableType {
    /**
     * The default value of the '{@link #getStartAt() <em>Start At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartAt()
     * @generated
     * @ordered
     */
    protected static final int START_AT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getStartAt() <em>Start At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartAt()
     * @generated
     * @ordered
     */
    protected int startAt = START_AT_EDEFAULT;

    /**
     * This is true if the Start At attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean startAtESet;

    /**
     * The default value of the '{@link #getNumToReturn() <em>Num To Return</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumToReturn()
     * @generated
     * @ordered
     */
    protected static final int NUM_TO_RETURN_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNumToReturn() <em>Num To Return</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumToReturn()
     * @generated
     * @ordered
     */
    protected int numToReturn = NUM_TO_RETURN_EDEFAULT;

    /**
     * This is true if the Num To Return attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean numToReturnESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetOrgEntityConfigAttributesAvailableTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getStartAt() {
        return startAt;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartAt(int newStartAt) {
        int oldStartAt = startAt;
        startAt = newStartAt;
        boolean oldStartAtESet = startAtESet;
        startAtESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT, oldStartAt, startAt, !oldStartAtESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartAt() {
        int oldStartAt = startAt;
        boolean oldStartAtESet = startAtESet;
        startAt = START_AT_EDEFAULT;
        startAtESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT, oldStartAt, START_AT_EDEFAULT, oldStartAtESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartAt() {
        return startAtESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNumToReturn() {
        return numToReturn;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNumToReturn(int newNumToReturn) {
        int oldNumToReturn = numToReturn;
        numToReturn = newNumToReturn;
        boolean oldNumToReturnESet = numToReturnESet;
        numToReturnESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN, oldNumToReturn, numToReturn, !oldNumToReturnESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNumToReturn() {
        int oldNumToReturn = numToReturn;
        boolean oldNumToReturnESet = numToReturnESet;
        numToReturn = NUM_TO_RETURN_EDEFAULT;
        numToReturnESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN, oldNumToReturn, NUM_TO_RETURN_EDEFAULT, oldNumToReturnESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNumToReturn() {
        return numToReturnESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT:
                return getStartAt();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN:
                return getNumToReturn();
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
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT:
                setStartAt((Integer)newValue);
                return;
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN:
                setNumToReturn((Integer)newValue);
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
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT:
                unsetStartAt();
                return;
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN:
                unsetNumToReturn();
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
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT:
                return isSetStartAt();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN:
                return isSetNumToReturn();
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
        result.append(" (startAt: ");
        if (startAtESet) result.append(startAt); else result.append("<unset>");
        result.append(", numToReturn: ");
        if (numToReturnESet) result.append(numToReturn); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetOrgEntityConfigAttributesAvailableTypeImpl
