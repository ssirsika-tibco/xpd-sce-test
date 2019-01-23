/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constant Real Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ConstantRealDistributionImpl#getConstantValue <em>Constant Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstantRealDistributionImpl extends EObjectImpl implements
        ConstantRealDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getConstantValue() <em>Constant Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConstantValue()
     * @generated
     * @ordered
     */
    protected static final double CONSTANT_VALUE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getConstantValue() <em>Constant Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConstantValue()
     * @generated
     * @ordered
     */
    protected double constantValue = CONSTANT_VALUE_EDEFAULT;

    /**
     * This is true if the Constant Value attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean constantValueESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConstantRealDistributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.CONSTANT_REAL_DISTRIBUTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getConstantValue() {
        return constantValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setConstantValue(double newConstantValue) {
        double oldConstantValue = constantValue;
        constantValue = newConstantValue;
        boolean oldConstantValueESet = constantValueESet;
        constantValueESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE,
                    oldConstantValue, constantValue, !oldConstantValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetConstantValue() {
        double oldConstantValue = constantValue;
        boolean oldConstantValueESet = constantValueESet;
        constantValue = CONSTANT_VALUE_EDEFAULT;
        constantValueESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE,
                    oldConstantValue, CONSTANT_VALUE_EDEFAULT,
                    oldConstantValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetConstantValue() {
        return constantValueESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE:
            return new Double(getConstantValue());
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
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE:
            setConstantValue(((Double) newValue).doubleValue());
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
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE:
            unsetConstantValue();
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
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE:
            return isSetConstantValue();
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
        result.append(" (constantValue: "); //$NON-NLS-1$
        if (constantValueESet)
            result.append(constantValue);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //ConstantRealDistributionImpl