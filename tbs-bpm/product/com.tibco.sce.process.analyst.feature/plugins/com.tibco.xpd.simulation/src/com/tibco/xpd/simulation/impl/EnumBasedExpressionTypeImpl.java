/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Based Expression Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl#getEnumValue <em>Enum Value</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl#getParamName <em>Param Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumBasedExpressionTypeImpl extends EObjectImpl implements
        EnumBasedExpressionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getEnumValue() <em>Enum Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumValue()
     * @generated
     * @ordered
     */
    protected static final Object ENUM_VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEnumValue() <em>Enum Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumValue()
     * @generated
     * @ordered
     */
    protected Object enumValue = ENUM_VALUE_EDEFAULT;

    /**
     * The default value of the '{@link #getParamName() <em>Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParamName()
     * @generated
     * @ordered
     */
    protected static final Object PARAM_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParamName() <em>Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParamName()
     * @generated
     * @ordered
     */
    protected Object paramName = PARAM_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EnumBasedExpressionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.ENUM_BASED_EXPRESSION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getEnumValue() {
        return enumValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnumValue(Object newEnumValue) {
        Object oldEnumValue = enumValue;
        enumValue = newEnumValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE,
                    oldEnumValue, enumValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getParamName() {
        return paramName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParamName(Object newParamName) {
        Object oldParamName = paramName;
        paramName = newParamName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME,
                    oldParamName, paramName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE:
            return getEnumValue();
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME:
            return getParamName();
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
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE:
            setEnumValue(newValue);
            return;
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME:
            setParamName(newValue);
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
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE:
            setEnumValue(ENUM_VALUE_EDEFAULT);
            return;
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME:
            setParamName(PARAM_NAME_EDEFAULT);
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
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE:
            return ENUM_VALUE_EDEFAULT == null ? enumValue != null
                    : !ENUM_VALUE_EDEFAULT.equals(enumValue);
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME:
            return PARAM_NAME_EDEFAULT == null ? paramName != null
                    : !PARAM_NAME_EDEFAULT.equals(paramName);
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
        result.append(" (enumValue: "); //$NON-NLS-1$
        result.append(enumValue);
        result.append(", paramName: "); //$NON-NLS-1$
        result.append(paramName);
        result.append(')');
        return result.toString();
    }

} //EnumBasedExpressionTypeImpl