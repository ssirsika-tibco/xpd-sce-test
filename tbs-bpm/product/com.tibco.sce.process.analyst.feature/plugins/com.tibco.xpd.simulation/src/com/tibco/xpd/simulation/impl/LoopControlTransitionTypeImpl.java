/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.LoopControlTransitionType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Control Transition Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl#getDecisionActivity <em>Decision Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl#getToActivity <em>To Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoopControlTransitionTypeImpl extends EObjectImpl implements
        LoopControlTransitionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getDecisionActivity() <em>Decision Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecisionActivity()
     * @generated
     * @ordered
     */
    protected static final String DECISION_ACTIVITY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDecisionActivity() <em>Decision Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecisionActivity()
     * @generated
     * @ordered
     */
    protected String decisionActivity = DECISION_ACTIVITY_EDEFAULT;

    /**
     * The default value of the '{@link #getToActivity() <em>To Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToActivity()
     * @generated
     * @ordered
     */
    protected static final String TO_ACTIVITY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getToActivity() <em>To Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToActivity()
     * @generated
     * @ordered
     */
    protected String toActivity = TO_ACTIVITY_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LoopControlTransitionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.LOOP_CONTROL_TRANSITION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDecisionActivity() {
        return decisionActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDecisionActivity(String newDecisionActivity) {
        String oldDecisionActivity = decisionActivity;
        decisionActivity = newDecisionActivity;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY,
                    oldDecisionActivity, decisionActivity));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getToActivity() {
        return toActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setToActivity(String newToActivity) {
        String oldToActivity = toActivity;
        toActivity = newToActivity;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY,
                    oldToActivity, toActivity));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY:
            return getDecisionActivity();
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY:
            return getToActivity();
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
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY:
            setDecisionActivity((String) newValue);
            return;
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY:
            setToActivity((String) newValue);
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
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY:
            setDecisionActivity(DECISION_ACTIVITY_EDEFAULT);
            return;
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY:
            setToActivity(TO_ACTIVITY_EDEFAULT);
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
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY:
            return DECISION_ACTIVITY_EDEFAULT == null ? decisionActivity != null
                    : !DECISION_ACTIVITY_EDEFAULT.equals(decisionActivity);
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY:
            return TO_ACTIVITY_EDEFAULT == null ? toActivity != null
                    : !TO_ACTIVITY_EDEFAULT.equals(toActivity);
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
        result.append(" (decisionActivity: "); //$NON-NLS-1$
        result.append(decisionActivity);
        result.append(", toActivity: "); //$NON-NLS-1$
        result.append(toActivity);
        result.append(')');
        return result.toString();
    }

} //LoopControlTransitionTypeImpl