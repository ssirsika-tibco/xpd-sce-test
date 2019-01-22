/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata.impl;

import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage;
import com.tibco.xpd.simulation.empiricaldata.Parameter;
import com.tibco.xpd.simulation.empiricaldata.Period;
import com.tibco.xpd.simulation.empiricaldata.PeriodType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Period</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl#getPeriod <em>Period</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PeriodImpl extends EObjectImpl implements Period {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList parameter = null;

	/**
	 * The cached value of the '{@link #getPeriod() <em>Period</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriod()
	 * @generated
	 * @ordered
	 */
	protected EList period = null;

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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final PeriodType TYPE_EDEFAULT = PeriodType.MONTH_OF_YEAR_LITERAL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected PeriodType type = TYPE_EDEFAULT;

	/**
	 * This is true if the Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean typeESet = false;

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
	protected PeriodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmpiricalDataPackage.Literals.PERIOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList(Parameter.class, this,
					EmpiricalDataPackage.PERIOD__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPeriod() {
		if (period == null) {
			period = new EObjectContainmentEList(Period.class, this,
					EmpiricalDataPackage.PERIOD__PERIOD);
		}
		return period;
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
					EmpiricalDataPackage.PERIOD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PeriodType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(PeriodType newType) {
		PeriodType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		boolean oldTypeESet = typeESet;
		typeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmpiricalDataPackage.PERIOD__TYPE, oldType, type,
					!oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetType() {
		PeriodType oldType = type;
		boolean oldTypeESet = typeESet;
		type = TYPE_EDEFAULT;
		typeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					EmpiricalDataPackage.PERIOD__TYPE, oldType, TYPE_EDEFAULT,
					oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetType() {
		return typeESet;
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
					EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR,
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
					EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR,
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EmpiricalDataPackage.PERIOD__PARAMETER:
			return ((InternalEList) getParameter()).basicRemove(otherEnd, msgs);
		case EmpiricalDataPackage.PERIOD__PERIOD:
			return ((InternalEList) getPeriod()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EmpiricalDataPackage.PERIOD__PARAMETER:
			return getParameter();
		case EmpiricalDataPackage.PERIOD__PERIOD:
			return getPeriod();
		case EmpiricalDataPackage.PERIOD__NAME:
			return getName();
		case EmpiricalDataPackage.PERIOD__TYPE:
			return getType();
		case EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR:
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
		case EmpiricalDataPackage.PERIOD__PARAMETER:
			getParameter().clear();
			getParameter().addAll((Collection) newValue);
			return;
		case EmpiricalDataPackage.PERIOD__PERIOD:
			getPeriod().clear();
			getPeriod().addAll((Collection) newValue);
			return;
		case EmpiricalDataPackage.PERIOD__NAME:
			setName((String) newValue);
			return;
		case EmpiricalDataPackage.PERIOD__TYPE:
			setType((PeriodType) newValue);
			return;
		case EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR:
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
		case EmpiricalDataPackage.PERIOD__PARAMETER:
			getParameter().clear();
			return;
		case EmpiricalDataPackage.PERIOD__PERIOD:
			getPeriod().clear();
			return;
		case EmpiricalDataPackage.PERIOD__NAME:
			setName(NAME_EDEFAULT);
			return;
		case EmpiricalDataPackage.PERIOD__TYPE:
			unsetType();
			return;
		case EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR:
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
		case EmpiricalDataPackage.PERIOD__PARAMETER:
			return parameter != null && !parameter.isEmpty();
		case EmpiricalDataPackage.PERIOD__PERIOD:
			return period != null && !period.isEmpty();
		case EmpiricalDataPackage.PERIOD__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case EmpiricalDataPackage.PERIOD__TYPE:
			return isSetType();
		case EmpiricalDataPackage.PERIOD__WEIGHTING_FACTOR:
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
		result.append(", type: "); //$NON-NLS-1$
		if (typeESet)
			result.append(type);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", weightingFactor: "); //$NON-NLS-1$
		if (weightingFactorESet)
			result.append(weightingFactor);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //PeriodImpl
