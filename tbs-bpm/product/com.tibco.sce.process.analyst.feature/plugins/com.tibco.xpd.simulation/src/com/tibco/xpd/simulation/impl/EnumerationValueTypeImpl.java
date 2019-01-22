/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enumeration Value Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumerationValueTypeImpl extends EObjectImpl implements
        EnumerationValueType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * The default value of the '{@link #getWeightingFactor() <em>Weighting Factor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeightingFactor()
     * @generated
     * @ordered
     */
    protected static final double WEIGHTING_FACTOR_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getWeightingFactor() <em>Weighting Factor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeightingFactor()
     * @generated
     * @ordered
     */
    protected double weightingFactor = WEIGHTING_FACTOR_EDEFAULT;

    /**
     * This is true if the Weighting Factor attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean weightingFactorESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EnumerationValueTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.ENUMERATION_VALUE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ENUMERATION_VALUE_TYPE__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ENUMERATION_VALUE_TYPE__VALUE, oldValue,
                    value));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getWeightingFactor() {
        return weightingFactor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWeightingFactor(double newWeightingFactor) {
        double oldWeightingFactor = weightingFactor;
        weightingFactor = newWeightingFactor;
        boolean oldWeightingFactorESet = weightingFactorESet;
        weightingFactorESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR,
                    oldWeightingFactor, weightingFactor,
                    !oldWeightingFactorESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWeightingFactor() {
        double oldWeightingFactor = weightingFactor;
        boolean oldWeightingFactorESet = weightingFactorESet;
        weightingFactor = WEIGHTING_FACTOR_EDEFAULT;
        weightingFactorESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR,
                    oldWeightingFactor, WEIGHTING_FACTOR_EDEFAULT,
                    oldWeightingFactorESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWeightingFactor() {
        return weightingFactorESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.ENUMERATION_VALUE_TYPE__DESCRIPTION:
            return getDescription();
        case SimulationPackage.ENUMERATION_VALUE_TYPE__VALUE:
            return getValue();
        case SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR:
            return new Double(getWeightingFactor());
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
        case SimulationPackage.ENUMERATION_VALUE_TYPE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case SimulationPackage.ENUMERATION_VALUE_TYPE__VALUE:
            setValue((String) newValue);
            return;
        case SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR:
            setWeightingFactor(((Double) newValue).doubleValue());
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
        case SimulationPackage.ENUMERATION_VALUE_TYPE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case SimulationPackage.ENUMERATION_VALUE_TYPE__VALUE:
            setValue(VALUE_EDEFAULT);
            return;
        case SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR:
            unsetWeightingFactor();
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
        case SimulationPackage.ENUMERATION_VALUE_TYPE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case SimulationPackage.ENUMERATION_VALUE_TYPE__VALUE:
            return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT
                    .equals(value);
        case SimulationPackage.ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR:
            return isSetWeightingFactor();
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
        result.append(" (description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", value: "); //$NON-NLS-1$
        result.append(value);
        result.append(", weightingFactor: "); //$NON-NLS-1$
        if (weightingFactorESet)
            result.append(weightingFactor);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //EnumerationValueTypeImpl