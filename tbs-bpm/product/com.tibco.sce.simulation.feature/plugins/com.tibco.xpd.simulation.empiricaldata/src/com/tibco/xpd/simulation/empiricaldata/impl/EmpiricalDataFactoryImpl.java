/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata.impl;

import com.tibco.xpd.simulation.empiricaldata.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmpiricalDataFactoryImpl extends EFactoryImpl implements
		EmpiricalDataFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EmpiricalDataFactory init() {
		try {
			EmpiricalDataFactory theEmpiricalDataFactory = (EmpiricalDataFactory) EPackage.Registry.INSTANCE
					.getEFactory("file:/E:/XPD/EmpiricalData.xsd"); //$NON-NLS-1$ 
			if (theEmpiricalDataFactory != null) {
				return theEmpiricalDataFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EmpiricalDataFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmpiricalDataFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case EmpiricalDataPackage.DOCUMENT_ROOT:
			return createDocumentRoot();
		case EmpiricalDataPackage.EMPIRICAL_DATA:
			return createEmpiricalData();
		case EmpiricalDataPackage.ENUMERATION:
			return createEnumeration();
		case EmpiricalDataPackage.PARAMETER:
			return createParameter();
		case EmpiricalDataPackage.PERIOD:
			return createPeriod();
		default:
			throw new IllegalArgumentException(
					"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case EmpiricalDataPackage.DAY_OF_MONTH_PERIOD_TYPE:
			return createDayOfMonthPeriodTypeFromString(eDataType, initialValue);
		case EmpiricalDataPackage.DAY_OF_WEEK_PERIOD_TYPE:
			return createDayOfWeekPeriodTypeFromString(eDataType, initialValue);
		case EmpiricalDataPackage.HOUR_OF_DAY_PERIOD_TYPE:
			return createHourOfDayPeriodTypeFromString(eDataType, initialValue);
		case EmpiricalDataPackage.MONTH_OF_YEAR_PERIOD_TYPE:
			return createMonthOfYearPeriodTypeFromString(eDataType,
					initialValue);
		case EmpiricalDataPackage.PERIOD_TYPE:
			return createPeriodTypeFromString(eDataType, initialValue);
		case EmpiricalDataPackage.DAY_OF_MONTH_PERIOD_TYPE_OBJECT:
			return createDayOfMonthPeriodTypeObjectFromString(eDataType,
					initialValue);
		case EmpiricalDataPackage.DAY_OF_WEEK_PERIOD_TYPE_OBJECT:
			return createDayOfWeekPeriodTypeObjectFromString(eDataType,
					initialValue);
		case EmpiricalDataPackage.HOUR_OF_DAY_PERIOD_TYPE_OBJECT:
			return createHourOfDayPeriodTypeObjectFromString(eDataType,
					initialValue);
		case EmpiricalDataPackage.MONTH_OF_YEAR_PERIOD_TYPE_OBJECT:
			return createMonthOfYearPeriodTypeObjectFromString(eDataType,
					initialValue);
		case EmpiricalDataPackage.PERIOD_TYPE_OBJECT:
			return createPeriodTypeObjectFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case EmpiricalDataPackage.DAY_OF_MONTH_PERIOD_TYPE:
			return convertDayOfMonthPeriodTypeToString(eDataType, instanceValue);
		case EmpiricalDataPackage.DAY_OF_WEEK_PERIOD_TYPE:
			return convertDayOfWeekPeriodTypeToString(eDataType, instanceValue);
		case EmpiricalDataPackage.HOUR_OF_DAY_PERIOD_TYPE:
			return convertHourOfDayPeriodTypeToString(eDataType, instanceValue);
		case EmpiricalDataPackage.MONTH_OF_YEAR_PERIOD_TYPE:
			return convertMonthOfYearPeriodTypeToString(eDataType,
					instanceValue);
		case EmpiricalDataPackage.PERIOD_TYPE:
			return convertPeriodTypeToString(eDataType, instanceValue);
		case EmpiricalDataPackage.DAY_OF_MONTH_PERIOD_TYPE_OBJECT:
			return convertDayOfMonthPeriodTypeObjectToString(eDataType,
					instanceValue);
		case EmpiricalDataPackage.DAY_OF_WEEK_PERIOD_TYPE_OBJECT:
			return convertDayOfWeekPeriodTypeObjectToString(eDataType,
					instanceValue);
		case EmpiricalDataPackage.HOUR_OF_DAY_PERIOD_TYPE_OBJECT:
			return convertHourOfDayPeriodTypeObjectToString(eDataType,
					instanceValue);
		case EmpiricalDataPackage.MONTH_OF_YEAR_PERIOD_TYPE_OBJECT:
			return convertMonthOfYearPeriodTypeObjectToString(eDataType,
					instanceValue);
		case EmpiricalDataPackage.PERIOD_TYPE_OBJECT:
			return convertPeriodTypeObjectToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmpiricalData createEmpiricalData() {
		EmpiricalDataImpl empiricalData = new EmpiricalDataImpl();
		return empiricalData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumeration createEnumeration() {
		EnumerationImpl enumeration = new EnumerationImpl();
		return enumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Period createPeriod() {
		PeriodImpl period = new PeriodImpl();
		return period;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DayOfMonthPeriodType createDayOfMonthPeriodTypeFromString(
			EDataType eDataType, String initialValue) {
		DayOfMonthPeriodType result = DayOfMonthPeriodType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDayOfMonthPeriodTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DayOfWeekPeriodType createDayOfWeekPeriodTypeFromString(
			EDataType eDataType, String initialValue) {
		DayOfWeekPeriodType result = DayOfWeekPeriodType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDayOfWeekPeriodTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HourOfDayPeriodType createHourOfDayPeriodTypeFromString(
			EDataType eDataType, String initialValue) {
		HourOfDayPeriodType result = HourOfDayPeriodType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHourOfDayPeriodTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonthOfYearPeriodType createMonthOfYearPeriodTypeFromString(
			EDataType eDataType, String initialValue) {
		MonthOfYearPeriodType result = MonthOfYearPeriodType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMonthOfYearPeriodTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PeriodType createPeriodTypeFromString(EDataType eDataType,
			String initialValue) {
		PeriodType result = PeriodType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPeriodTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DayOfMonthPeriodType createDayOfMonthPeriodTypeObjectFromString(
			EDataType eDataType, String initialValue) {
		return (DayOfMonthPeriodType) createDayOfMonthPeriodTypeFromString(
				EmpiricalDataPackage.Literals.DAY_OF_MONTH_PERIOD_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDayOfMonthPeriodTypeObjectToString(
			EDataType eDataType, Object instanceValue) {
		return convertDayOfMonthPeriodTypeToString(
				EmpiricalDataPackage.Literals.DAY_OF_MONTH_PERIOD_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DayOfWeekPeriodType createDayOfWeekPeriodTypeObjectFromString(
			EDataType eDataType, String initialValue) {
		return (DayOfWeekPeriodType) createDayOfWeekPeriodTypeFromString(
				EmpiricalDataPackage.Literals.DAY_OF_WEEK_PERIOD_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDayOfWeekPeriodTypeObjectToString(EDataType eDataType,
			Object instanceValue) {
		return convertDayOfWeekPeriodTypeToString(
				EmpiricalDataPackage.Literals.DAY_OF_WEEK_PERIOD_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HourOfDayPeriodType createHourOfDayPeriodTypeObjectFromString(
			EDataType eDataType, String initialValue) {
		return (HourOfDayPeriodType) createHourOfDayPeriodTypeFromString(
				EmpiricalDataPackage.Literals.HOUR_OF_DAY_PERIOD_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHourOfDayPeriodTypeObjectToString(EDataType eDataType,
			Object instanceValue) {
		return convertHourOfDayPeriodTypeToString(
				EmpiricalDataPackage.Literals.HOUR_OF_DAY_PERIOD_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonthOfYearPeriodType createMonthOfYearPeriodTypeObjectFromString(
			EDataType eDataType, String initialValue) {
		return (MonthOfYearPeriodType) createMonthOfYearPeriodTypeFromString(
				EmpiricalDataPackage.Literals.MONTH_OF_YEAR_PERIOD_TYPE,
				initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMonthOfYearPeriodTypeObjectToString(
			EDataType eDataType, Object instanceValue) {
		return convertMonthOfYearPeriodTypeToString(
				EmpiricalDataPackage.Literals.MONTH_OF_YEAR_PERIOD_TYPE,
				instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PeriodType createPeriodTypeObjectFromString(EDataType eDataType,
			String initialValue) {
		return (PeriodType) createPeriodTypeFromString(
				EmpiricalDataPackage.Literals.PERIOD_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPeriodTypeObjectToString(EDataType eDataType,
			Object instanceValue) {
		return convertPeriodTypeToString(
				EmpiricalDataPackage.Literals.PERIOD_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmpiricalDataPackage getEmpiricalDataPackage() {
		return (EmpiricalDataPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static EmpiricalDataPackage getPackage() {
		return EmpiricalDataPackage.eINSTANCE;
	}

} //EmpiricalDataFactoryImpl
