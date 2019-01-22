/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.DocumentRoot;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ErrorInformation;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.StateType;
import com.tibco.bx.emulation.model.Testpoint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmulationPackageImpl extends EPackageImpl implements EmulationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testpointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emulationDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emulationElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outputEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass errorInformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intermediateInputEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiInputEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stateTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inOutDataTypeEClass = null;

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
	private EClass documentRootEClass = null;

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
	 * @see com.tibco.bx.emulation.model.EmulationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmulationPackageImpl() {
		super(eNS_URI, EmulationFactory.eINSTANCE);
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
	public static EmulationPackage init() {
		if (isInited) return (EmulationPackage)EPackage.Registry.INSTANCE.getEPackage(EmulationPackage.eNS_URI);

		// Obtain or create and register package
		EmulationPackageImpl theEmulationPackage = (EmulationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof EmulationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new EmulationPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theEmulationPackage.createPackageContents();

		// Initialize created meta-data
		theEmulationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEmulationPackage.freeze();

		return theEmulationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestpoint() {
		return testpointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestpoint_ProcessNode() {
		return (EReference)testpointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestpoint_Lang() {
		return (EAttribute)testpointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestpoint_Expression() {
		return (EAttribute)testpointEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssertion() {
		return assertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertion_ProcessNode() {
		return (EReference)assertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertion_Accessible() {
		return (EAttribute)assertionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessNode() {
		return processNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_Testpoints() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_Assertions() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_Input() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_Output() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessNode_Alias() {
		return (EAttribute)processNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessNode_Description() {
		return (EAttribute)processNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessNode_State() {
		return (EAttribute)processNodeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_ErrorInformation() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_IntermediateInputs() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessNode_ModelType() {
		return (EAttribute)processNodeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessNode_MultiInputNodes() {
		return (EReference)processNodeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmulationData() {
		return emulationDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEmulationData_ProcessNodes() {
		return (EReference)emulationDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmulationElement() {
		return emulationElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Id() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInput() {
		return inputEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOutput() {
		return outputEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getErrorInformation() {
		return errorInformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getErrorInformation_FailedAssertion() {
		return (EReference)errorInformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getErrorInformation_ErrorMessage() {
		return (EAttribute)errorInformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntermediateInput() {
		return intermediateInputEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntermediateInput_RequestMessage() {
		return (EAttribute)intermediateInputEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntermediateInput_ResponseMessage() {
		return (EAttribute)intermediateInputEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiInput() {
		return multiInputEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiInput_ResponseMessage() {
		return (EAttribute)multiInputEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStateType() {
		return stateTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInOutDataType() {
		return inOutDataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInOutDataType_Parameters() {
		return (EReference)inOutDataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInOutDataType_SoapMessage() {
		return (EAttribute)inOutDataTypeEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getParameter_Value() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
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
	public EAttribute getDocumentRoot_Fixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmulationFactory getEmulationFactory() {
		return (EmulationFactory)getEFactoryInstance();
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
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__FIXED);

		emulationDataEClass = createEClass(EMULATION_DATA);
		createEReference(emulationDataEClass, EMULATION_DATA__PROCESS_NODES);

		emulationElementEClass = createEClass(EMULATION_ELEMENT);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__ID);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		processNodeEClass = createEClass(PROCESS_NODE);
		createEReference(processNodeEClass, PROCESS_NODE__TESTPOINTS);
		createEReference(processNodeEClass, PROCESS_NODE__ASSERTIONS);
		createEReference(processNodeEClass, PROCESS_NODE__INPUT);
		createEReference(processNodeEClass, PROCESS_NODE__OUTPUT);
		createEAttribute(processNodeEClass, PROCESS_NODE__ALIAS);
		createEAttribute(processNodeEClass, PROCESS_NODE__DESCRIPTION);
		createEAttribute(processNodeEClass, PROCESS_NODE__STATE);
		createEReference(processNodeEClass, PROCESS_NODE__ERROR_INFORMATION);
		createEReference(processNodeEClass, PROCESS_NODE__INTERMEDIATE_INPUTS);
		createEAttribute(processNodeEClass, PROCESS_NODE__MODEL_TYPE);
		createEReference(processNodeEClass, PROCESS_NODE__MULTI_INPUT_NODES);

		testpointEClass = createEClass(TESTPOINT);
		createEReference(testpointEClass, TESTPOINT__PROCESS_NODE);
		createEAttribute(testpointEClass, TESTPOINT__LANG);
		createEAttribute(testpointEClass, TESTPOINT__EXPRESSION);

		assertionEClass = createEClass(ASSERTION);
		createEReference(assertionEClass, ASSERTION__PROCESS_NODE);
		createEAttribute(assertionEClass, ASSERTION__ACCESSIBLE);

		inOutDataTypeEClass = createEClass(IN_OUT_DATA_TYPE);
		createEReference(inOutDataTypeEClass, IN_OUT_DATA_TYPE__PARAMETERS);
		createEAttribute(inOutDataTypeEClass, IN_OUT_DATA_TYPE__SOAP_MESSAGE);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__VALUE);

		inputEClass = createEClass(INPUT);

		outputEClass = createEClass(OUTPUT);

		errorInformationEClass = createEClass(ERROR_INFORMATION);
		createEReference(errorInformationEClass, ERROR_INFORMATION__FAILED_ASSERTION);
		createEAttribute(errorInformationEClass, ERROR_INFORMATION__ERROR_MESSAGE);

		intermediateInputEClass = createEClass(INTERMEDIATE_INPUT);
		createEAttribute(intermediateInputEClass, INTERMEDIATE_INPUT__REQUEST_MESSAGE);
		createEAttribute(intermediateInputEClass, INTERMEDIATE_INPUT__RESPONSE_MESSAGE);

		multiInputEClass = createEClass(MULTI_INPUT);
		createEAttribute(multiInputEClass, MULTI_INPUT__RESPONSE_MESSAGE);

		// Create enums
		stateTypeEEnum = createEEnum(STATE_TYPE);
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
		namedElementEClass.getESuperTypes().add(this.getEmulationElement());
		processNodeEClass.getESuperTypes().add(this.getNamedElement());
		testpointEClass.getESuperTypes().add(this.getNamedElement());
		assertionEClass.getESuperTypes().add(this.getNamedElement());
		inOutDataTypeEClass.getESuperTypes().add(this.getNamedElement());
		parameterEClass.getESuperTypes().add(this.getNamedElement());
		inputEClass.getESuperTypes().add(this.getInOutDataType());
		outputEClass.getESuperTypes().add(this.getInOutDataType());
		errorInformationEClass.getESuperTypes().add(this.getEmulationElement());
		intermediateInputEClass.getESuperTypes().add(this.getNamedElement());
		multiInputEClass.getESuperTypes().add(this.getInput());

		// Initialize classes and features; add operations and parameters
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDocumentRoot_Fixed(), ecorePackage.getEFeatureMapEntry(), "fixed", null, 0, -1, DocumentRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(emulationDataEClass, EmulationData.class, "EmulationData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEmulationData_ProcessNodes(), this.getProcessNode(), null, "processNodes", null, 0, -1, EmulationData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		getEmulationData_ProcessNodes().getEKeys().add(this.getNamedElement_Id());

		initEClass(emulationElementEClass, EmulationElement.class, "EmulationElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getNamedElement_Id(), ecorePackage.getEString(), "id", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(processNodeEClass, ProcessNode.class, "ProcessNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProcessNode_Testpoints(), this.getTestpoint(), this.getTestpoint_ProcessNode(), "testpoints", null, 0, -1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		getProcessNode_Testpoints().getEKeys().add(this.getNamedElement_Id());
		initEReference(getProcessNode_Assertions(), this.getAssertion(), this.getAssertion_ProcessNode(), "assertions", null, 0, -1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		getProcessNode_Assertions().getEKeys().add(this.getNamedElement_Id());
		initEReference(getProcessNode_Input(), this.getInput(), null, "input", null, 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProcessNode_Output(), this.getOutput(), null, "output", null, 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProcessNode_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProcessNode_Description(), ecorePackage.getEString(), "description", null, 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProcessNode_State(), this.getStateType(), "state", "COMPLETED", 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getProcessNode_ErrorInformation(), this.getErrorInformation(), null, "errorInformation", null, 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProcessNode_IntermediateInputs(), this.getIntermediateInput(), null, "intermediateInputs", null, 0, -1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProcessNode_ModelType(), ecorePackage.getEString(), "modelType", "", 0, 1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getProcessNode_MultiInputNodes(), this.getMultiInput(), null, "multiInputNodes", null, 0, -1, ProcessNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(testpointEClass, Testpoint.class, "Testpoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTestpoint_ProcessNode(), this.getProcessNode(), this.getProcessNode_Testpoints(), "processNode", null, 0, 1, Testpoint.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTestpoint_Lang(), ecorePackage.getEString(), "lang", null, 0, 1, Testpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTestpoint_Expression(), ecorePackage.getEString(), "expression", null, 0, 1, Testpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(assertionEClass, Assertion.class, "Assertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAssertion_ProcessNode(), this.getProcessNode(), this.getProcessNode_Assertions(), "processNode", null, 0, 1, Assertion.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAssertion_Accessible(), ecorePackage.getEBoolean(), "accessible", null, 0, 1, Assertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(inOutDataTypeEClass, InOutDataType.class, "InOutDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInOutDataType_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, InOutDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		getInOutDataType_Parameters().getEKeys().add(this.getNamedElement_Id());
		initEAttribute(getInOutDataType_SoapMessage(), ecorePackage.getEString(), "soapMessage", null, 0, 1, InOutDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getParameter_Value(), ecorePackage.getEString(), "value", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(inputEClass, Input.class, "Input", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(outputEClass, Output.class, "Output", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(errorInformationEClass, ErrorInformation.class, "ErrorInformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getErrorInformation_FailedAssertion(), this.getAssertion(), null, "failedAssertion", null, 0, 1, ErrorInformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getErrorInformation_ErrorMessage(), ecorePackage.getEString(), "errorMessage", null, 0, 1, ErrorInformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(intermediateInputEClass, IntermediateInput.class, "IntermediateInput", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIntermediateInput_RequestMessage(), ecorePackage.getEString(), "requestMessage", null, 0, 1, IntermediateInput.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getIntermediateInput_ResponseMessage(), ecorePackage.getEString(), "responseMessage", null, 0, 1, IntermediateInput.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(multiInputEClass, MultiInput.class, "MultiInput", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getMultiInput_ResponseMessage(), ecorePackage.getEString(), "responseMessage", null, 0, 1, MultiInput.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(stateTypeEEnum, StateType.class, "StateType"); //$NON-NLS-1$
		addEEnumLiteral(stateTypeEEnum, StateType.COMPLETED);
		addEEnumLiteral(stateTypeEEnum, StateType.TERMINATED);
		addEEnumLiteral(stateTypeEEnum, StateType.FAULT);

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
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "kind", "mixed", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", null //$NON-NLS-1$
		   });		
		addAnnotation
		  (getDocumentRoot_Fixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "Fixed" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (emulationDataEClass, 
		   source, 
		   new String[] {
			 "name", "EmulationData", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getEmulationData_ProcessNodes(), 
		   source, 
		   new String[] {
			 "name", "ProcessNode", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "ProcessNodes" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getNamedElement_Id(), 
		   source, 
		   new String[] {
			 "name", "Id", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getNamedElement_Name(), 
		   source, 
		   new String[] {
			 "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (processNodeEClass, 
		   source, 
		   new String[] {
			 "name", "ProcessNode_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Testpoints(), 
		   source, 
		   new String[] {
			 "name", "Testpoint", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "Testpoints" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Assertions(), 
		   source, 
		   new String[] {
			 "name", "Assertion", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "Assertions" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Input(), 
		   source, 
		   new String[] {
			 "name", "Input", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Output(), 
		   source, 
		   new String[] {
			 "name", "Output", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Alias(), 
		   source, 
		   new String[] {
			 "name", "Alias", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_Description(), 
		   source, 
		   new String[] {
			 "name", "Description", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_State(), 
		   source, 
		   new String[] {
			 "name", "State", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_ErrorInformation(), 
		   source, 
		   new String[] {
			 "name", "ErrorInformation", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_IntermediateInputs(), 
		   source, 
		   new String[] {
			 "name", "IntermediateInput", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "IntermediateInputs" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_ModelType(), 
		   source, 
		   new String[] {
			 "name", "ModelType", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getProcessNode_MultiInputNodes(), 
		   source, 
		   new String[] {
			 "name", "MultiInput", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "MultiInputs" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (testpointEClass, 
		   source, 
		   new String[] {
			 "name", "Testpoint_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getTestpoint_Lang(), 
		   source, 
		   new String[] {
			 "name", "Lang", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getTestpoint_Expression(), 
		   source, 
		   new String[] {
			 "name", "Expression", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (assertionEClass, 
		   source, 
		   new String[] {
			 "name", "Assertion_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getAssertion_Accessible(), 
		   source, 
		   new String[] {
			 "name", "Accessible", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getInOutDataType_Parameters(), 
		   source, 
		   new String[] {
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "Parameter", //$NON-NLS-1$ //$NON-NLS-2$
			 "wrap", "Parameters" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getInOutDataType_SoapMessage(), 
		   source, 
		   new String[] {
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "SoapMessage" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (parameterEClass, 
		   source, 
		   new String[] {
			 "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "Parameter_._type" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getParameter_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (inputEClass, 
		   source, 
		   new String[] {
			 "name", "Input_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (outputEClass, 
		   source, 
		   new String[] {
			 "name", "Output_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (stateTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "State_._type" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (errorInformationEClass, 
		   source, 
		   new String[] {
			 "name", "ErrorInformation_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getErrorInformation_FailedAssertion(), 
		   source, 
		   new String[] {
			 "name", "FailedAssertion", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getErrorInformation_ErrorMessage(), 
		   source, 
		   new String[] {
			 "name", "ErrorMessage", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (intermediateInputEClass, 
		   source, 
		   new String[] {
			 "name", "IntermediateInput_._type", //$NON-NLS-1$ //$NON-NLS-2$
			 "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getIntermediateInput_RequestMessage(), 
		   source, 
		   new String[] {
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "RequestMessage" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (getIntermediateInput_ResponseMessage(), 
		   source, 
		   new String[] {
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "ResponseMessage" //$NON-NLS-1$ //$NON-NLS-2$
		   });		
		addAnnotation
		  (multiInputEClass, 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getMultiInput_ResponseMessage(), 
		   source, 
		   new String[] {
			 "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			 "name", "ResponseMessage" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //EmulationPackageImpl
