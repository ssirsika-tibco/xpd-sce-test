/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.UniformRealDistribution;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Uniform Real Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.UniformRealDistributionImpl#getLowerBorder <em>Lower Border</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.UniformRealDistributionImpl#getUpperBorder <em>Upper Border</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UniformRealDistributionImpl extends EObjectImpl implements
        UniformRealDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getLowerBorder() <em>Lower Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLowerBorder()
     * @generated
     * @ordered
     */
    protected static final double LOWER_BORDER_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getLowerBorder() <em>Lower Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLowerBorder()
     * @generated
     * @ordered
     */
    protected double lowerBorder = LOWER_BORDER_EDEFAULT;

    /**
     * This is true if the Lower Border attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lowerBorderESet;

    /**
     * The default value of the '{@link #getUpperBorder() <em>Upper Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpperBorder()
     * @generated
     * @ordered
     */
    protected static final double UPPER_BORDER_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getUpperBorder() <em>Upper Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpperBorder()
     * @generated
     * @ordered
     */
    protected double upperBorder = UPPER_BORDER_EDEFAULT;

    /**
     * This is true if the Upper Border attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean upperBorderESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UniformRealDistributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.UNIFORM_REAL_DISTRIBUTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getLowerBorder() {
        return lowerBorder;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLowerBorder(double newLowerBorder) {
        double oldLowerBorder = lowerBorder;
        lowerBorder = newLowerBorder;
        boolean oldLowerBorderESet = lowerBorderESet;
        lowerBorderESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER,
                    oldLowerBorder, lowerBorder, !oldLowerBorderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLowerBorder() {
        double oldLowerBorder = lowerBorder;
        boolean oldLowerBorderESet = lowerBorderESet;
        lowerBorder = LOWER_BORDER_EDEFAULT;
        lowerBorderESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER,
                    oldLowerBorder, LOWER_BORDER_EDEFAULT, oldLowerBorderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLowerBorder() {
        return lowerBorderESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getUpperBorder() {
        return upperBorder;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUpperBorder(double newUpperBorder) {
        double oldUpperBorder = upperBorder;
        upperBorder = newUpperBorder;
        boolean oldUpperBorderESet = upperBorderESet;
        upperBorderESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER,
                    oldUpperBorder, upperBorder, !oldUpperBorderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetUpperBorder() {
        double oldUpperBorder = upperBorder;
        boolean oldUpperBorderESet = upperBorderESet;
        upperBorder = UPPER_BORDER_EDEFAULT;
        upperBorderESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER,
                    oldUpperBorder, UPPER_BORDER_EDEFAULT, oldUpperBorderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetUpperBorder() {
        return upperBorderESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER:
            return new Double(getLowerBorder());
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER:
            return new Double(getUpperBorder());
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
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER:
            setLowerBorder(((Double) newValue).doubleValue());
            return;
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER:
            setUpperBorder(((Double) newValue).doubleValue());
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
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER:
            unsetLowerBorder();
            return;
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER:
            unsetUpperBorder();
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
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER:
            return isSetLowerBorder();
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER:
            return isSetUpperBorder();
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
        result.append(" (lowerBorder: "); //$NON-NLS-1$
        if (lowerBorderESet)
            result.append(lowerBorder);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", upperBorder: "); //$NON-NLS-1$
        if (upperBorderESet)
            result.append(upperBorder);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //UniformRealDistributionImpl