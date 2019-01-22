/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StructuredConditionType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structured Condition Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl#getParameterId <em>Parameter Id</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl#getParameterValue <em>Parameter Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StructuredConditionTypeImpl extends EObjectImpl implements
        StructuredConditionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getParameterId() <em>Parameter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterId()
     * @generated
     * @ordered
     */
    protected static final String PARAMETER_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParameterId() <em>Parameter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterId()
     * @generated
     * @ordered
     */
    protected String parameterId = PARAMETER_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperator()
     * @generated
     * @ordered
     */
    protected static final String OPERATOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperator()
     * @generated
     * @ordered
     */
    protected String operator = OPERATOR_EDEFAULT;

    /**
     * The default value of the '{@link #getParameterValue() <em>Parameter Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterValue()
     * @generated
     * @ordered
     */
    protected static final String PARAMETER_VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParameterValue() <em>Parameter Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterValue()
     * @generated
     * @ordered
     */
    protected String parameterValue = PARAMETER_VALUE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StructuredConditionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.STRUCTURED_CONDITION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getParameterId() {
        return parameterId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameterId(String newParameterId) {
        String oldParameterId = parameterId;
        parameterId = newParameterId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_ID,
                    oldParameterId, parameterId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOperator(String newOperator) {
        String oldOperator = operator;
        operator = newOperator;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.STRUCTURED_CONDITION_TYPE__OPERATOR,
                    oldOperator, operator));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameterValue(String newParameterValue) {
        String oldParameterValue = parameterValue;
        parameterValue = newParameterValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE,
                    oldParameterValue, parameterValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_ID:
            return getParameterId();
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__OPERATOR:
            return getOperator();
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE:
            return getParameterValue();
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
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_ID:
            setParameterId((String) newValue);
            return;
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__OPERATOR:
            setOperator((String) newValue);
            return;
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE:
            setParameterValue((String) newValue);
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
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_ID:
            setParameterId(PARAMETER_ID_EDEFAULT);
            return;
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__OPERATOR:
            setOperator(OPERATOR_EDEFAULT);
            return;
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE:
            setParameterValue(PARAMETER_VALUE_EDEFAULT);
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
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_ID:
            return PARAMETER_ID_EDEFAULT == null ? parameterId != null
                    : !PARAMETER_ID_EDEFAULT.equals(parameterId);
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__OPERATOR:
            return OPERATOR_EDEFAULT == null ? operator != null
                    : !OPERATOR_EDEFAULT.equals(operator);
        case SimulationPackage.STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE:
            return PARAMETER_VALUE_EDEFAULT == null ? parameterValue != null
                    : !PARAMETER_VALUE_EDEFAULT.equals(parameterValue);
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
        result.append(" (parameterId: "); //$NON-NLS-1$
        result.append(parameterId);
        result.append(", operator: "); //$NON-NLS-1$
        result.append(operator);
        result.append(", parameterValue: "); //$NON-NLS-1$
        result.append(parameterValue);
        result.append(')');
        return result.toString();
    }

} //StructuredConditionTypeImpl