/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface EmpiricalDataPackage extends EPackage {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "empiricaldata"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "file:/E:/XPD/EmpiricalData.xsd"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "EmpiricalData"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmpiricalDataPackage eINSTANCE = com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.DocumentRootImpl
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Empirical Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EMPIRICAL_DATA = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataImpl <em>Empirical Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataImpl
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getEmpiricalData()
	 * @generated
	 */
	int EMPIRICAL_DATA = 1;

	/**
	 * The feature id for the '<em><b>Period</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPIRICAL_DATA__PERIOD = 0;

	/**
	 * The number of structural features of the '<em>Empirical Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPIRICAL_DATA_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getEnumeration()
	 * @generated
	 */
	int ENUMERATION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Weighting Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__WEIGHTING_FACTOR = 1;

	/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.ParameterImpl
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__ENUMERATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl <em>Period</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriod()
	 * @generated
	 */
	int PERIOD = 4;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD__PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Period</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD__PERIOD = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD__NAME = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Weighting Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD__WEIGHTING_FACTOR = 4;

	/**
	 * The number of structural features of the '<em>Period</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERIOD_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType <em>Day Of Month Period Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfMonthPeriodType()
	 * @generated
	 */
	int DAY_OF_MONTH_PERIOD_TYPE = 5;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType <em>Day Of Week Period Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfWeekPeriodType()
	 * @generated
	 */
	int DAY_OF_WEEK_PERIOD_TYPE = 6;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType <em>Hour Of Day Period Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getHourOfDayPeriodType()
	 * @generated
	 */
	int HOUR_OF_DAY_PERIOD_TYPE = 7;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType <em>Month Of Year Period Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getMonthOfYearPeriodType()
	 * @generated
	 */
	int MONTH_OF_YEAR_PERIOD_TYPE = 8;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.empiricaldata.PeriodType <em>Period Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriodType()
	 * @generated
	 */
	int PERIOD_TYPE = 9;

	/**
	 * The meta object id for the '<em>Day Of Month Period Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfMonthPeriodTypeObject()
	 * @generated
	 */
	int DAY_OF_MONTH_PERIOD_TYPE_OBJECT = 10;

	/**
	 * The meta object id for the '<em>Day Of Week Period Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfWeekPeriodTypeObject()
	 * @generated
	 */
	int DAY_OF_WEEK_PERIOD_TYPE_OBJECT = 11;

	/**
	 * The meta object id for the '<em>Hour Of Day Period Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getHourOfDayPeriodTypeObject()
	 * @generated
	 */
	int HOUR_OF_DAY_PERIOD_TYPE_OBJECT = 12;

	/**
	 * The meta object id for the '<em>Month Of Year Period Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getMonthOfYearPeriodTypeObject()
	 * @generated
	 */
	int MONTH_OF_YEAR_PERIOD_TYPE_OBJECT = 13;

	/**
	 * The meta object id for the '<em>Period Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriodTypeObject()
	 * @generated
	 */
	int PERIOD_TYPE_OBJECT = 14;

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.empiricaldata.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getEmpiricalData <em>Empirical Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Empirical Data</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DocumentRoot#getEmpiricalData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EmpiricalData();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.empiricaldata.EmpiricalData <em>Empirical Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Empirical Data</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalData
	 * @generated
	 */
	EClass getEmpiricalData();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.empiricaldata.EmpiricalData#getPeriod <em>Period</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Period</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalData#getPeriod()
	 * @see #getEmpiricalData()
	 * @generated
	 */
	EReference getEmpiricalData_Period();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Enumeration
	 * @generated
	 */
	EClass getEnumeration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Enumeration#getName()
	 * @see #getEnumeration()
	 * @generated
	 */
	EAttribute getEnumeration_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor <em>Weighting Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weighting Factor</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor()
	 * @see #getEnumeration()
	 * @generated
	 */
	EAttribute getEnumeration_WeightingFactor();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.empiricaldata.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.empiricaldata.Parameter#getEnumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enumeration</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Parameter#getEnumeration()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Enumeration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.empiricaldata.Period <em>Period</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Period</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period
	 * @generated
	 */
	EClass getPeriod();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.empiricaldata.Period#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period#getParameter()
	 * @see #getPeriod()
	 * @generated
	 */
	EReference getPeriod_Parameter();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.empiricaldata.Period#getPeriod <em>Period</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Period</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period#getPeriod()
	 * @see #getPeriod()
	 * @generated
	 */
	EReference getPeriod_Period();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Period#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period#getName()
	 * @see #getPeriod()
	 * @generated
	 */
	EAttribute getPeriod_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Period#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period#getType()
	 * @see #getPeriod()
	 * @generated
	 */
	EAttribute getPeriod_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor <em>Weighting Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weighting Factor</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor()
	 * @see #getPeriod()
	 * @generated
	 */
	EAttribute getPeriod_WeightingFactor();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType <em>Day Of Month Period Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Day Of Month Period Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
	 * @generated
	 */
	EEnum getDayOfMonthPeriodType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType <em>Day Of Week Period Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Day Of Week Period Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
	 * @generated
	 */
	EEnum getDayOfWeekPeriodType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType <em>Hour Of Day Period Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Hour Of Day Period Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
	 * @generated
	 */
	EEnum getHourOfDayPeriodType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType <em>Month Of Year Period Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Month Of Year Period Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
	 * @generated
	 */
	EEnum getMonthOfYearPeriodType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.simulation.empiricaldata.PeriodType <em>Period Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Period Type</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @generated
	 */
	EEnum getPeriodType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType <em>Day Of Month Period Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Day Of Month Period Type Object</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
	 * @model instanceClass="com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType"
	 *        extendedMetaData="name='DayOfMonthPeriodType:Object' baseType='DayOfMonthPeriodType'" 
	 * @generated
	 */
	EDataType getDayOfMonthPeriodTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType <em>Day Of Week Period Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Day Of Week Period Type Object</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
	 * @model instanceClass="com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType"
	 *        extendedMetaData="name='DayOfWeekPeriodType:Object' baseType='DayOfWeekPeriodType'" 
	 * @generated
	 */
	EDataType getDayOfWeekPeriodTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType <em>Hour Of Day Period Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Hour Of Day Period Type Object</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
	 * @model instanceClass="com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType"
	 *        extendedMetaData="name='HourOfDayPeriodType:Object' baseType='HourOfDayPeriodType'" 
	 * @generated
	 */
	EDataType getHourOfDayPeriodTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType <em>Month Of Year Period Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Month Of Year Period Type Object</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
	 * @model instanceClass="com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType"
	 *        extendedMetaData="name='MonthOfYearPeriodType:Object' baseType='MonthOfYearPeriodType'" 
	 * @generated
	 */
	EDataType getMonthOfYearPeriodTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.simulation.empiricaldata.PeriodType <em>Period Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Period Type Object</em>'.
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @model instanceClass="com.tibco.xpd.simulation.empiricaldata.PeriodType"
	 *        extendedMetaData="name='PeriodType:Object' baseType='PeriodType'" 
	 * @generated
	 */
	EDataType getPeriodTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmpiricalDataFactory getEmpiricalDataFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.DocumentRootImpl
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE
				.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE
				.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Empirical Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EMPIRICAL_DATA = eINSTANCE
				.getDocumentRoot_EmpiricalData();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataImpl <em>Empirical Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataImpl
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getEmpiricalData()
		 * @generated
		 */
		EClass EMPIRICAL_DATA = eINSTANCE.getEmpiricalData();

		/**
		 * The meta object literal for the '<em><b>Period</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMPIRICAL_DATA__PERIOD = eINSTANCE.getEmpiricalData_Period();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EnumerationImpl
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getEnumeration()
		 * @generated
		 */
		EClass ENUMERATION = eINSTANCE.getEnumeration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATION__NAME = eINSTANCE.getEnumeration_Name();

		/**
		 * The meta object literal for the '<em><b>Weighting Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATION__WEIGHTING_FACTOR = eINSTANCE
				.getEnumeration_WeightingFactor();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.ParameterImpl
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Enumeration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__ENUMERATION = eINSTANCE
				.getParameter_Enumeration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl <em>Period</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.PeriodImpl
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriod()
		 * @generated
		 */
		EClass PERIOD = eINSTANCE.getPeriod();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERIOD__PARAMETER = eINSTANCE.getPeriod_Parameter();

		/**
		 * The meta object literal for the '<em><b>Period</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERIOD__PERIOD = eINSTANCE.getPeriod_Period();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERIOD__NAME = eINSTANCE.getPeriod_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERIOD__TYPE = eINSTANCE.getPeriod_Type();

		/**
		 * The meta object literal for the '<em><b>Weighting Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERIOD__WEIGHTING_FACTOR = eINSTANCE
				.getPeriod_WeightingFactor();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType <em>Day Of Month Period Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfMonthPeriodType()
		 * @generated
		 */
		EEnum DAY_OF_MONTH_PERIOD_TYPE = eINSTANCE.getDayOfMonthPeriodType();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType <em>Day Of Week Period Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfWeekPeriodType()
		 * @generated
		 */
		EEnum DAY_OF_WEEK_PERIOD_TYPE = eINSTANCE.getDayOfWeekPeriodType();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType <em>Hour Of Day Period Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getHourOfDayPeriodType()
		 * @generated
		 */
		EEnum HOUR_OF_DAY_PERIOD_TYPE = eINSTANCE.getHourOfDayPeriodType();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType <em>Month Of Year Period Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getMonthOfYearPeriodType()
		 * @generated
		 */
		EEnum MONTH_OF_YEAR_PERIOD_TYPE = eINSTANCE.getMonthOfYearPeriodType();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.empiricaldata.PeriodType <em>Period Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriodType()
		 * @generated
		 */
		EEnum PERIOD_TYPE = eINSTANCE.getPeriodType();

		/**
		 * The meta object literal for the '<em>Day Of Month Period Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfMonthPeriodTypeObject()
		 * @generated
		 */
		EDataType DAY_OF_MONTH_PERIOD_TYPE_OBJECT = eINSTANCE
				.getDayOfMonthPeriodTypeObject();

		/**
		 * The meta object literal for the '<em>Day Of Week Period Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getDayOfWeekPeriodTypeObject()
		 * @generated
		 */
		EDataType DAY_OF_WEEK_PERIOD_TYPE_OBJECT = eINSTANCE
				.getDayOfWeekPeriodTypeObject();

		/**
		 * The meta object literal for the '<em>Hour Of Day Period Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getHourOfDayPeriodTypeObject()
		 * @generated
		 */
		EDataType HOUR_OF_DAY_PERIOD_TYPE_OBJECT = eINSTANCE
				.getHourOfDayPeriodTypeObject();

		/**
		 * The meta object literal for the '<em>Month Of Year Period Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getMonthOfYearPeriodTypeObject()
		 * @generated
		 */
		EDataType MONTH_OF_YEAR_PERIOD_TYPE_OBJECT = eINSTANCE
				.getMonthOfYearPeriodTypeObject();

		/**
		 * The meta object literal for the '<em>Period Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
		 * @see com.tibco.xpd.simulation.empiricaldata.impl.EmpiricalDataPackageImpl#getPeriodTypeObject()
		 * @generated
		 */
		EDataType PERIOD_TYPE_OBJECT = eINSTANCE.getPeriodTypeObject();

	}

} //EmpiricalDataPackage
