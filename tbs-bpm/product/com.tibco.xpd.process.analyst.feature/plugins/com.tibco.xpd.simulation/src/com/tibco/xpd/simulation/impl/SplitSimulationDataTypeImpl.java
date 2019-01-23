/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Split Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl#isParameterDeterminedSplit <em>Parameter Determined Split</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl#getSplitParameter <em>Split Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SplitSimulationDataTypeImpl extends EObjectImpl implements
        SplitSimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #isParameterDeterminedSplit() <em>Parameter Determined Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isParameterDeterminedSplit()
     * @generated
     * @ordered
     */
    protected static final boolean PARAMETER_DETERMINED_SPLIT_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isParameterDeterminedSplit() <em>Parameter Determined Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isParameterDeterminedSplit()
     * @generated
     * @ordered
     */
    protected boolean parameterDeterminedSplit =
            PARAMETER_DETERMINED_SPLIT_EDEFAULT;

    /**
     * This is true if the Parameter Determined Split attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean parameterDeterminedSplitESet;

    /**
     * The cached value of the '{@link #getSplitParameter() <em>Split Parameter</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSplitParameter()
     * @generated
     * @ordered
     */
    protected SplitParameterType splitParameter;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SplitSimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.SPLIT_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isParameterDeterminedSplit() {
        return parameterDeterminedSplit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameterDeterminedSplit(boolean newParameterDeterminedSplit) {
        boolean oldParameterDeterminedSplit = parameterDeterminedSplit;
        parameterDeterminedSplit = newParameterDeterminedSplit;
        boolean oldParameterDeterminedSplitESet = parameterDeterminedSplitESet;
        parameterDeterminedSplitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT,
                    oldParameterDeterminedSplit, parameterDeterminedSplit,
                    !oldParameterDeterminedSplitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetParameterDeterminedSplit() {
        boolean oldParameterDeterminedSplit = parameterDeterminedSplit;
        boolean oldParameterDeterminedSplitESet = parameterDeterminedSplitESet;
        parameterDeterminedSplit = PARAMETER_DETERMINED_SPLIT_EDEFAULT;
        parameterDeterminedSplitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT,
                    oldParameterDeterminedSplit,
                    PARAMETER_DETERMINED_SPLIT_EDEFAULT,
                    oldParameterDeterminedSplitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetParameterDeterminedSplit() {
        return parameterDeterminedSplitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SplitParameterType getSplitParameter() {
        return splitParameter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSplitParameter(
            SplitParameterType newSplitParameter, NotificationChain msgs) {
        SplitParameterType oldSplitParameter = splitParameter;
        splitParameter = newSplitParameter;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER,
                            oldSplitParameter, newSplitParameter);
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
    public void setSplitParameter(SplitParameterType newSplitParameter) {
        if (newSplitParameter != splitParameter) {
            NotificationChain msgs = null;
            if (splitParameter != null)
                msgs =
                        ((InternalEObject) splitParameter)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER,
                                        null,
                                        msgs);
            if (newSplitParameter != null)
                msgs =
                        ((InternalEObject) newSplitParameter)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER,
                                        null,
                                        msgs);
            msgs = basicSetSplitParameter(newSplitParameter, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER,
                    newSplitParameter, newSplitParameter));
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
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER:
            return basicSetSplitParameter(null, msgs);
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
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT:
            return isParameterDeterminedSplit() ? Boolean.TRUE : Boolean.FALSE;
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER:
            return getSplitParameter();
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
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT:
            setParameterDeterminedSplit(((Boolean) newValue).booleanValue());
            return;
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER:
            setSplitParameter((SplitParameterType) newValue);
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
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT:
            unsetParameterDeterminedSplit();
            return;
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER:
            setSplitParameter((SplitParameterType) null);
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
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT:
            return isSetParameterDeterminedSplit();
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER:
            return splitParameter != null;
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
        result.append(" (parameterDeterminedSplit: "); //$NON-NLS-1$
        if (parameterDeterminedSplitESet)
            result.append(parameterDeterminedSplit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SplitSimulationDataTypeImpl