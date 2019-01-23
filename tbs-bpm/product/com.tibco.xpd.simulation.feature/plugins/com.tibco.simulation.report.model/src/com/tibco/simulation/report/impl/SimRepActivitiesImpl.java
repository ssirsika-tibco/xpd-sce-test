/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activities</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivitiesImpl#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepActivitiesImpl extends EObjectImpl implements
        SimRepActivities {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getActivity() <em>Activity</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivity()
     * @generated
     * @ordered
     */
    protected EList<SimRepActivity> activity;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepActivitiesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_ACTIVITIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SimRepActivity> getActivity() {
        if (activity == null) {
            activity =
                    new EObjectContainmentEList<SimRepActivity>(
                            SimRepActivity.class, this,
                            SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY);
        }
        return activity;
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
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            return ((InternalEList<?>) getActivity()).basicRemove(otherEnd,
                    msgs);
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
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            return getActivity();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            getActivity().clear();
            getActivity()
                    .addAll((Collection<? extends SimRepActivity>) newValue);
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
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            getActivity().clear();
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
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            return activity != null && !activity.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //SimRepActivitiesImpl
