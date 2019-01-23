/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.realdata.impl;

import com.tibco.xpd.simulation.realdata.Case;
import com.tibco.xpd.simulation.realdata.Cases;
import com.tibco.xpd.simulation.realdata.DocumentRoot;
import com.tibco.xpd.simulation.realdata.Paramter;
import com.tibco.xpd.simulation.realdata.RealDataFactory;
import com.tibco.xpd.simulation.realdata.RealDataPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class RealDataPackageImpl extends EPackageImpl implements
		RealDataPackage {

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
	private EClass caseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass casesEClass = null;

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
	private EClass paramterEClass = null;

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
	 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RealDataPackageImpl() {
		super(eNS_URI, RealDataFactory.eINSTANCE);
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
	public static RealDataPackage init() {
		if (isInited)
			return (RealDataPackage) EPackage.Registry.INSTANCE
					.getEPackage(RealDataPackage.eNS_URI);

		// Obtain or create and register package
		RealDataPackageImpl theRealDataPackage = (RealDataPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) instanceof RealDataPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI)
				: new RealDataPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRealDataPackage.createPackageContents();

		// Initialize created meta-data
		theRealDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRealDataPackage.freeze();

		return theRealDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCase() {
		return caseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCase_Paramter() {
		return (EReference) caseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCase_StartTime() {
		return (EAttribute) caseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCases() {
		return casesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCases_Case() {
		return (EReference) casesEClass.getEStructuralFeatures().get(0);
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
	public EReference getDocumentRoot_Cases() {
		return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParamter() {
		return paramterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParamter_Name() {
		return (EAttribute) paramterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParamter_Value() {
		return (EAttribute) paramterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RealDataFactory getRealDataFactory() {
		return (RealDataFactory) getEFactoryInstance();
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
		caseEClass = createEClass(CASE);
		createEReference(caseEClass, CASE__PARAMTER);
		createEAttribute(caseEClass, CASE__START_TIME);

		casesEClass = createEClass(CASES);
		createEReference(casesEClass, CASES__CASE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CASES);

		paramterEClass = createEClass(PARAMTER);
		createEAttribute(paramterEClass, PARAMTER__NAME);
		createEAttribute(paramterEClass, PARAMTER__VALUE);
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
				caseEClass,
				Case.class,
				"Case", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getCase_Paramter(),
				this.getParamter(),
				null,
				"paramter", null, 0, -1, Case.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getCase_StartTime(),
				theXMLTypePackage.getDateTime(),
				"startTime", null, 1, 1, Case.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				casesEClass,
				Cases.class,
				"Cases", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getCases_Case(),
				this.getCase(),
				null,
				"case", null, 0, -1, Cases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
				getDocumentRoot_Cases(),
				this.getCases(),
				null,
				"cases", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(
				paramterEClass,
				Paramter.class,
				"Paramter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getParamter_Name(),
				theXMLTypePackage.getString(),
				"name", null, 1, 1, Paramter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getParamter_Value(),
				theXMLTypePackage.getString(),
				"value", null, 1, 1, Paramter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
		addAnnotation(caseEClass, source, new String[] { "name", "Case", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCase_Paramter(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Paramter", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCase_StartTime(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "startTime", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(casesEClass, source, new String[] { "name", "Cases", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCases_Case(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Case", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
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
		addAnnotation(getDocumentRoot_Cases(), source, new String[] {
				"kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Cases", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(paramterEClass, source, new String[] {
				"name", "Paramter", //$NON-NLS-1$ //$NON-NLS-2$
				"kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getParamter_Name(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getParamter_Value(), source, new String[] {
				"kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Value", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

} //RealDataPackageImpl
