/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl#getStructuredCondition <em>Structured Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionSimulationDataTypeImpl extends EObjectImpl implements
        TransitionSimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #isParameterDeterminedCondition() <em>Parameter Determined Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isParameterDeterminedCondition()
     * @generated
     * @ordered
     */
    protected static final boolean PARAMETER_DETERMINED_CONDITION_EDEFAULT =
            true;

    /**
     * The cached value of the '{@link #isParameterDeterminedCondition() <em>Parameter Determined Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isParameterDeterminedCondition()
     * @generated
     * @ordered
     */
    protected boolean parameterDeterminedCondition =
            PARAMETER_DETERMINED_CONDITION_EDEFAULT;

    /**
     * This is true if the Parameter Determined Condition attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean parameterDeterminedConditionESet;

    /**
     * The cached value of the '{@link #getStructuredCondition() <em>Structured Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStructuredCondition()
     * @generated
     * @ordered
     */
    protected StructuredConditionType structuredCondition;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TransitionSimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.TRANSITION_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isParameterDeterminedCondition() {
        return parameterDeterminedCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameterDeterminedCondition(
            boolean newParameterDeterminedCondition) {
        boolean oldParameterDeterminedCondition = parameterDeterminedCondition;
        parameterDeterminedCondition = newParameterDeterminedCondition;
        boolean oldParameterDeterminedConditionESet =
                parameterDeterminedConditionESet;
        parameterDeterminedConditionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION,
                    oldParameterDeterminedCondition,
                    parameterDeterminedCondition,
                    !oldParameterDeterminedConditionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetParameterDeterminedCondition() {
        boolean oldParameterDeterminedCondition = parameterDeterminedCondition;
        boolean oldParameterDeterminedConditionESet =
                parameterDeterminedConditionESet;
        parameterDeterminedCondition = PARAMETER_DETERMINED_CONDITION_EDEFAULT;
        parameterDeterminedConditionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION,
                    oldParameterDeterminedCondition,
                    PARAMETER_DETERMINED_CONDITION_EDEFAULT,
                    oldParameterDeterminedConditionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetParameterDeterminedCondition() {
        return parameterDeterminedConditionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StructuredConditionType getStructuredCondition() {
        return structuredCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStructuredCondition(
            StructuredConditionType newStructuredCondition,
            NotificationChain msgs) {
        StructuredConditionType oldStructuredCondition = structuredCondition;
        structuredCondition = newStructuredCondition;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION,
                            oldStructuredCondition, newStructuredCondition);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStructuredCondition(
            StructuredConditionType newStructuredCondition) {
        if (newStructuredCondition != structuredCondition) {
            NotificationChain msgs = null;
            if (structuredCondition != null)
                msgs =
                        ((InternalEObject) structuredCondition)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION,
                                        null,
                                        msgs);
            if (newStructuredCondition != null)
                msgs =
                        ((InternalEObject) newStructuredCondition)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION,
                                        null,
                                        msgs);
            msgs = basicSetStructuredCondition(newStructuredCondition, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION,
                    newStructuredCondition, newStructuredCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION:
            return basicSetStructuredCondition(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION:
            return isParameterDeterminedCondition() ? Boolean.TRUE
                    : Boolean.FALSE;
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION:
            return getStructuredCondition();
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
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION:
            setParameterDeterminedCondition(((Boolean) newValue).booleanValue());
            return;
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION:
            setStructuredCondition((StructuredConditionType) newValue);
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
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION:
            unsetParameterDeterminedCondition();
            return;
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION:
            setStructuredCondition((StructuredConditionType) null);
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
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION:
            return isSetParameterDeterminedCondition();
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION:
            return structuredCondition != null;
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
        result.append(" (parameterDeterminedCondition: "); //$NON-NLS-1$
        if (parameterDeterminedConditionESet)
            result.append(parameterDeterminedCondition);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //TransitionSimulationDataTypeImpl