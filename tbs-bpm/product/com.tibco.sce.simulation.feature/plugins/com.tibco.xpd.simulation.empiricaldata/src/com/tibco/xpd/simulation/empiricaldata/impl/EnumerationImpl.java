/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata.impl;

import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage;
import com.tibco.xpd.simulation.empiricaldata.Enumeration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enumeration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumerationImpl extends EObjectImpl implements Enumeration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	protected boolean weightingFactorESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumerationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmpiricalDataPackage.Literals.ENUMERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmpiricalDataPackage.ENUMERATION__NAME, oldName, name));
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
					EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR,
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
					EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR,
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EmpiricalDataPackage.ENUMERATION__NAME:
			return getName();
		case EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR:
			return new Double(getWeightingFactor());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EmpiricalDataPackage.ENUMERATION__NAME:
			setName((String) newValue);
			return;
		case EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR:
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case EmpiricalDataPackage.ENUMERATION__NAME:
			setName(NAME_EDEFAULT);
			return;
		case EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR:
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EmpiricalDataPackage.ENUMERATION__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case EmpiricalDataPackage.ENUMERATION__WEIGHTING_FACTOR:
			return isSetWeightingFactor();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", weightingFactor: "); //$NON-NLS-1$
		if (weightingFactorESet)
			result.append(weightingFactor);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //EnumerationImpl
