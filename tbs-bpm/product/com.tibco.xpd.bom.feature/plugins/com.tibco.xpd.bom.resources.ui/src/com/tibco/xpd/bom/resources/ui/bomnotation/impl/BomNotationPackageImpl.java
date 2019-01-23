/**
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.bom.resources.ui.bomnotation.impl;

import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationFactory;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gmf.runtime.notation.NotationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BomNotationPackageImpl extends EPackageImpl implements BomNotationPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass shapeGradientStyleEClass = null;

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
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private BomNotationPackageImpl() {
        super(eNS_URI, BomNotationFactory.eINSTANCE);
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
    public static BomNotationPackage init() {
        if (isInited) return (BomNotationPackage)EPackage.Registry.INSTANCE.getEPackage(BomNotationPackage.eNS_URI);

        // Obtain or create and register package
        BomNotationPackageImpl theBomNotationPackage = (BomNotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof BomNotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new BomNotationPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        EcorePackage.eINSTANCE.eClass();
        NotationPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theBomNotationPackage.createPackageContents();

        // Initialize created meta-data
        theBomNotationPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theBomNotationPackage.freeze();

        return theBomNotationPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getShapeGradientStyle() {
        return shapeGradientStyleEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getShapeGradientStyle_GradStartColor() {
        return (EAttribute)shapeGradientStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getShapeGradientStyle_GradEndColor() {
        return (EAttribute)shapeGradientStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BomNotationFactory getBomNotationFactory() {
        return (BomNotationFactory)getEFactoryInstance();
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
        shapeGradientStyleEClass = createEClass(SHAPE_GRADIENT_STYLE);
        createEAttribute(shapeGradientStyleEClass, SHAPE_GRADIENT_STYLE__GRAD_START_COLOR);
        createEAttribute(shapeGradientStyleEClass, SHAPE_GRADIENT_STYLE__GRAD_END_COLOR);
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

        // Obtain other dependent packages
        NotationPackage theNotationPackage = (NotationPackage)EPackage.Registry.INSTANCE.getEPackage(NotationPackage.eNS_URI);
        EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        shapeGradientStyleEClass.getESuperTypes().add(theNotationPackage.getStyle());

        // Initialize classes and features; add operations and parameters
        initEClass(shapeGradientStyleEClass, ShapeGradientStyle.class, "ShapeGradientStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getShapeGradientStyle_GradStartColor(), theEcorePackage.getEInt(), "gradStartColor", "16777215", 0, 1, ShapeGradientStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getShapeGradientStyle_GradEndColor(), theEcorePackage.getEInt(), "gradEndColor", "0", 0, 1, ShapeGradientStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        // Create resource
        createResource(eNS_URI);
    }

} //BomNotationPackageImpl
