/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata.impl;

import com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType;
import com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType;
import com.tibco.xpd.simulation.empiricaldata.DocumentRoot;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalData;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataFactory;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage;
import com.tibco.xpd.simulation.empiricaldata.Enumeration;
import com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType;
import com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType;
import com.tibco.xpd.simulation.empiricaldata.Parameter;
import com.tibco.xpd.simulation.empiricaldata.Period;
import com.tibco.xpd.simulation.empiricaldata.PeriodType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmpiricalDataPackageImpl extends EPackageImpl implements
		EmpiricalDataPackage {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass empiricalDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass periodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dayOfMonthPeriodTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dayOfWeekPeriodTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum hourOfDayPeriodTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum monthOfYearPeriodTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum periodTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dayOfMonthPeriodTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dayOfWeekPeriodTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType hourOfDayPeriodTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType monthOfYearPeriodTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType periodTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmpiricalDataPackageImpl() {
		super(eNS_URI, EmpiricalDataFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EmpiricalDataPackage init() {
		if (isInited)
			return (EmpiricalDataPackage) EPackage.Registry.INSTANCE
					.getEPackage(EmpiricalDataPackage.eNS_URI);

		// Obtain or create and register package
		EmpiricalDataPackageImpl theEmpiricalDataPackage = (EmpiricalDataPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) instanceof EmpiricalDataPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI)
				: new EmpiricalDataPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEmpiricalDataPackage.createPackageContents();

		// Initialize created meta-data
		theEmpiricalDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEmpiricalDataPackage.freeze();

		return theEmpiricalDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EmpiricalData() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmpiricalData() {
		return empiricalDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEmpiricalData_Period() {
		return (EReference) empiricalDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumeration() {
		return enumerationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumeration_Name() {
		return (EAttribute) enumerationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumeration_WeightingFactor() {
		return (EAttribute) enumerationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_Enumeration() {
		return (EReference) parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Name() {
		return (EAttribute) parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPeriod() {
		return periodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPeriod_Parameter() {
		return (EReference) periodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPeriod_Period() {
		return (EReference) periodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPeriod_Name() {
		return (EAttribute) periodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPeriod_Type() {
		return (EAttribute) periodEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPeriod_WeightingFactor() {
		return (EAttribute) periodEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDayOfMonthPeriodType() {
		return dayOfMonthPeriodTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDayOfWeekPeriodType() {
		return dayOfWeekPeriodTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getHourOfDayPeriodType() {
		return hourOfDayPeriodTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMonthOfYearPeriodType() {
		return monthOfYearPeriodTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPeriodType() {
		return periodTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDayOfMonthPeriodTypeObject() {
		return dayOfMonthPeriodTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDayOfWeekPeriodTypeObject() {
		return dayOfWeekPeriodTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getHourOfDayPeriodTypeObject() {
		return hourOfDayPeriodTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getMonthOfYearPeriodTypeObject() {
		return monthOfYearPeriodTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPeriodTypeObject() {
		return periodTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmpiricalDataFactory getEmpiricalDataFactory() {
		return (EmpiricalDataFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EMPIRICAL_DATA);

		empiricalDataEClass = createEClass(EMPIRICAL_DATA);
		createEReference(empiricalDataEClass, EMPIRICAL_DATA__PERIOD);

		enumerationEClass = createEClass(ENUMERATION);
		createEAttribute(enumerationEClass, ENUMERATION__NAME);
		createEAttribute(enumerationEClass, ENUMERATION__WEIGHTING_FACTOR);

		parameterEClass = createEClass(PARAMETER);
		createEReference(parameterEClass, PARAMETER__ENUMERATION);
		createEAttribute(parameterEClass, PARAMETER__NAME);

		periodEClass = createEClass(PERIOD);
		createEReference(periodEClass, PERIOD__PARAMETER);
		createEReference(periodEClass, PERIOD__PERIOD);
		createEAttribute(periodEClass, PERIOD__NAME);
		createEAttribute(periodEClass, PERIOD__TYPE);
		createEAttribute(periodEClass, PERIOD__WEIGHTING_FACTOR);

		// Create enums
		dayOfMonthPeriodTypeEEnum = createEEnum(DAY_OF_MONTH_PERIOD_TYPE);
		dayOfWeekPeriodTypeEEnum = createEEnum(DAY_OF_WEEK_PERIOD_TYPE);
		hourOfDayPeriodTypeEEnum = createEEnum(HOUR_OF_DAY_PERIOD_TYPE);
		monthOfYearPeriodTypeEEnum = createEEnum(MONTH_OF_YEAR_PERIOD_TYPE);
		periodTypeEEnum = createEEnum(PERIOD_TYPE);

		// Create data types
		dayOfMonthPeriodTypeObjectEDataType = createEDataType(DAY_OF_MONTH_PERIOD_TYPE_OBJECT);
		dayOfWeekPeriodTypeObjectEDataType = createEDataType(DAY_OF_WEEK_PERIOD_TYPE_OBJECT);
		hourOfDayPeriodTypeObjectEDataType = createEDataType(HOUR_OF_DAY_PERIOD_TYPE_OBJECT);
		monthOfYearPeriodTypeObjectEDataType = createEDataType(MONTH_OF_YEAR_PERIOD_TYPE_OBJECT);
		periodTypeObjectEDataType = createEDataType(PERIOD_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(
				documentRootEClass,
				DocumentRoot.class,
				"DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getDocumentRoot_Mixed(),
				ecorePackage.getEFeatureMapEntry(),
				"mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getDocumentRoot_XMLNSPrefixMap(),
				ecorePackage.getEStringToStringMapEntry(),
				null,
				"xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getDocumentRoot_XSISchemaLocation(),
				ecorePackage.getEStringToStringMapEntry(),
				null,
				"xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getDocumentRoot_EmpiricalData(),
				this.getEmpiricalData(),
				null,
				"empiricalData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				empiricalDataEClass,
				EmpiricalData.class,
				"EmpiricalData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getEmpiricalData_Period(),
				this.getPeriod(),
				null,
				"period", null, 0, -1, EmpiricalData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				enumerationEClass,
				Enumeration.class,
				"Enumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getEnumeration_Name(),
				theXMLTypePackage.getString(),
				"name", null, 1, 1, Enumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getEnumeration_WeightingFactor(),
				theXMLTypePackage.getDouble(),
				"weightingFactor", null, 1, 1, Enumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				parameterEClass,
				Parameter.class,
				"Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getParameter_Enumeration(),
				this.getEnumeration(),
				null,
				"enumeration", null, 1, -1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getParameter_Name(),
				theXMLTypePackage.getString(),
				"name", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				periodEClass,
				Period.class,
				"Period", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getPeriod_Parameter(),
				this.getParameter(),
				null,
				"parameter", null, 0, -1, Period.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getPeriod_Period(),
				this.getPeriod(),
				null,
				"period", null, 0, -1, Period.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getPeriod_Name(),
				theXMLTypePackage.getNMTOKEN(),
				"name", null, 1, 1, Period.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getPeriod_Type(),
				this.getPeriodType(),
				"type", "MonthOfYear", 1, 1, Period.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(
				getPeriod_WeightingFactor(),
				theXMLTypePackage.getDouble(),
				"weightingFactor", null, 1, 1, Period.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(dayOfMonthPeriodTypeEEnum, DayOfMonthPeriodType.class,
				"DayOfMonthPeriodType"); //$NON-NLS-1$
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D1_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D2_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D3_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D4_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D5_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D6_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D7_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D8_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D9_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D10_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D11_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D12_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D13_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D14_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D15_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D16_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D17_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D18_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D19_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D20_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D21_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D22_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D23_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D24_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D25_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D26_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D27_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D28_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D29_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D30_LITERAL);
		addEEnumLiteral(dayOfMonthPeriodTypeEEnum,
				DayOfMonthPeriodType.D31_LITERAL);

		initEEnum(dayOfWeekPeriodTypeEEnum, DayOfWeekPeriodType.class,
				"DayOfWeekPeriodType"); //$NON-NLS-1$
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.MONDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.TUESDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.WEDNESDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.THURSDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.FRIDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.SATURDAY_LITERAL);
		addEEnumLiteral(dayOfWeekPeriodTypeEEnum,
				DayOfWeekPeriodType.SUNDAY_LITERAL);

		initEEnum(hourOfDayPeriodTypeEEnum, HourOfDayPeriodType.class,
				"HourOfDayPeriodType"); //$NON-NLS-1$
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H0_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H1_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H2_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H3_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H4_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H5_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H6_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H7_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H8_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H9_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H10_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H11_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H12_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H13_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H14_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H15_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H16_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H17_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H18_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H19_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H20_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H21_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H22_LITERAL);
		addEEnumLiteral(hourOfDayPeriodTypeEEnum,
				HourOfDayPeriodType.H23_LITERAL);

		initEEnum(monthOfYearPeriodTypeEEnum, MonthOfYearPeriodType.class,
				"MonthOfYearPeriodType"); //$NON-NLS-1$
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.JANRUARY_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.FEBRUARY_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.MARCH_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.APRIL_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.MAY_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.JUNE_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.JULY_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.AUGUST_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.SEPTEMBER_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.OCTOBER_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.NOVEMBER_LITERAL);
		addEEnumLiteral(monthOfYearPeriodTypeEEnum,
				MonthOfYearPeriodType.DECEMBER_LITERAL);

		initEEnum(periodTypeEEnum, PeriodType.class, "PeriodType"); //$NON-NLS-1$
		addEEnumLiteral(periodTypeEEnum, PeriodType.MONTH_OF_YEAR_LITERAL);
		addEEnumLiteral(periodTypeEEnum, PeriodType.DAY_OF_MONTH_LITERAL);
		addEEnumLiteral(periodTypeEEnum, PeriodType.DAY_OF_WEEK_LITERAL);
		addEEnumLiteral(periodTypeEEnum, PeriodType.HOUR_OF_DAY_LITERAL);

		// Initialize data types
		initEDataType(
				dayOfMonthPeriodTypeObjectEDataType,
				DayOfMonthPeriodType.class,
				"DayOfMonthPeriodTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(
				dayOfWeekPeriodTypeObjectEDataType,
				DayOfWeekPeriodType.class,
				"DayOfWeekPeriodTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(
				hourOfDayPeriodTypeObjectEDataType,
				HourOfDayPeriodType.class,
				"HourOfDayPeriodTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(
				monthOfYearPeriodTypeObjectEDataType,
				MonthOfYearPeriodType.class,
				"MonthOfYearPeriodTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(
				periodTypeObjectEDataType,
				PeriodType.class,
				"PeriodTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
		addAnnotation(this, source, new String[] { "qualified", "false" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(dayOfMonthPeriodTypeEEnum, source, new String[] {
				"name", "DayOfMonthPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(dayOfMonthPeriodTypeObjectEDataType, source,
				new String[] { "name", "DayOfMonthPeriodType:Object", //$NON-NLS-1$ //$NON-NLS-2$
						"baseType", "DayOfMonthPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(dayOfWeekPeriodTypeEEnum, source, new String[] {
				"name", "DayOfWeekPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(dayOfWeekPeriodTypeObjectEDataType, source, new String[] {
				"name", "DayOfWeekPeriodType:Object", //$NON-NLS-1$ //$NON-NLS-2$
				"baseType", "DayOfWeekPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(documentRootEClass, source, new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDocumentRoot_Mixed(), source, new String[] {
				"kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
				"name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDocumentRoot_XSISchemaLocation(), source,
				new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
						"name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(getDocumentRoot_EmpiricalData(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "EmpiricalData", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(empiricalDataEClass, source, new String[] {
				"name", "EmpiricalData", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getEmpiricalData_Period(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Period", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(enumerationEClass, source, new String[] {
				"name", "Enumeration", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getEnumeration_Name(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getEnumeration_WeightingFactor(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "WeightingFactor", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(hourOfDayPeriodTypeEEnum, source, new String[] {
				"name", "HourOfDayPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(hourOfDayPeriodTypeObjectEDataType, source, new String[] {
				"name", "HourOfDayPeriodType:Object", //$NON-NLS-1$ //$NON-NLS-2$
				"baseType", "HourOfDayPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(monthOfYearPeriodTypeEEnum, source, new String[] {
				"name", "MonthOfYearPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(monthOfYearPeriodTypeObjectEDataType, source,
				new String[] { "name", "MonthOfYearPeriodType:Object", //$NON-NLS-1$ //$NON-NLS-2$
						"baseType", "MonthOfYearPeriodType" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(parameterEClass, source, new String[] {
				"name", "Parameter", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getParameter_Enumeration(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Enumeration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getParameter_Name(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(periodEClass, source, new String[] { "name", "Period", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPeriod_Parameter(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Parameter", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPeriod_Period(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Period", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPeriod_Name(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPeriod_Type(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Type", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPeriod_WeightingFactor(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "WeightingFactor", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(periodTypeEEnum, source, new String[] {
				"name", "PeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(periodTypeObjectEDataType, source, new String[] {
				"name", "PeriodType:Object", //$NON-NLS-1$ //$NON-NLS-2$
				"baseType", "PeriodType" //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

} //EmpiricalDataPackageImpl
