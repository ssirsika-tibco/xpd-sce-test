/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.impl;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextFactory;
import com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EnumlitextPackageImpl extends EPackageImpl implements EnumlitextPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass domainValueEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass singleValueEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rangeValueEClass = null;

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
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private EnumlitextPackageImpl() {
        super(eNS_URI, EnumlitextFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link EnumlitextPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static EnumlitextPackage init() {
        if (isInited) return (EnumlitextPackage)EPackage.Registry.INSTANCE.getEPackage(EnumlitextPackage.eNS_URI);

        // Obtain or create and register package
        EnumlitextPackageImpl theEnumlitextPackage = (EnumlitextPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EnumlitextPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EnumlitextPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theEnumlitextPackage.createPackageContents();

        // Initialize created meta-data
        theEnumlitextPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theEnumlitextPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(EnumlitextPackage.eNS_URI, theEnumlitextPackage);
        return theEnumlitextPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDomainValue() {
        return domainValueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDomainValue_Value() {
        return (EAttribute)domainValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSingleValue() {
        return singleValueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRangeValue() {
        return rangeValueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRangeValue_Lower() {
        return (EAttribute)rangeValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRangeValue_Upper() {
        return (EAttribute)rangeValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRangeValue_LowerInclusive() {
        return (EAttribute)rangeValueEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRangeValue_UpperInclusive() {
        return (EAttribute)rangeValueEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumlitextFactory getEnumlitextFactory() {
        return (EnumlitextFactory)getEFactoryInstance();
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
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        domainValueEClass = createEClass(DOMAIN_VALUE);
        createEAttribute(domainValueEClass, DOMAIN_VALUE__VALUE);

        singleValueEClass = createEClass(SINGLE_VALUE);

        rangeValueEClass = createEClass(RANGE_VALUE);
        createEAttribute(rangeValueEClass, RANGE_VALUE__LOWER);
        createEAttribute(rangeValueEClass, RANGE_VALUE__UPPER);
        createEAttribute(rangeValueEClass, RANGE_VALUE__LOWER_INCLUSIVE);
        createEAttribute(rangeValueEClass, RANGE_VALUE__UPPER_INCLUSIVE);
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
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        singleValueEClass.getESuperTypes().add(this.getDomainValue());
        rangeValueEClass.getESuperTypes().add(this.getDomainValue());

        // Initialize classes and features; add operations and parameters
        initEClass(domainValueEClass, DomainValue.class, "DomainValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDomainValue_Value(), ecorePackage.getEString(), "value", null, 1, 1, DomainValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(singleValueEClass, SingleValue.class, "SingleValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(rangeValueEClass, RangeValue.class, "RangeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRangeValue_Lower(), ecorePackage.getEString(), "lower", null, 1, 1, RangeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRangeValue_Upper(), ecorePackage.getEString(), "upper", null, 1, 1, RangeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRangeValue_LowerInclusive(), ecorePackage.getEBoolean(), "lowerInclusive", "false", 0, 1, RangeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRangeValue_UpperInclusive(), ecorePackage.getEBoolean(), "upperInclusive", "false", 0, 1, RangeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //EnumlitextPackageImpl
