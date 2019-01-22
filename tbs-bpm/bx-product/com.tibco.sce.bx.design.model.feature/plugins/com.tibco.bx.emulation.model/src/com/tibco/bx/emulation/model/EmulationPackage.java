/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.bx.emulation.model.EmulationFactory
 * @model kind="package"
 * @generated
 */
public interface EmulationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///emulation.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "emulation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmulationPackage eINSTANCE = com.tibco.bx.emulation.model.impl.EmulationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.EmulationElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.EmulationElementImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getEmulationElement()
	 * @generated
	 */
	int EMULATION_ELEMENT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.NamedElementImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.TestpointImpl <em>Testpoint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.TestpointImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getTestpoint()
	 * @generated
	 */
	int TESTPOINT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.AssertionImpl <em>Assertion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.AssertionImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getAssertion()
	 * @generated
	 */
	int ASSERTION = 6;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl <em>Process Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.ProcessNodeImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getProcessNode()
	 * @generated
	 */
	int PROCESS_NODE = 4;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.EmulationDataImpl <em>Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.EmulationDataImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getEmulationData()
	 * @generated
	 */
	int EMULATION_DATA = 1;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.InOutDataTypeImpl <em>In Out Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.InOutDataTypeImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getInOutDataType()
	 * @generated
	 */
	int IN_OUT_DATA_TYPE = 7;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.InputImpl <em>Input</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.InputImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getInput()
	 * @generated
	 */
	int INPUT = 9;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.OutputImpl <em>Output</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.OutputImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getOutput()
	 * @generated
	 */
	int OUTPUT = 10;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.ErrorInformationImpl <em>Error Information</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.ErrorInformationImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getErrorInformation()
	 * @generated
	 */
	int ERROR_INFORMATION = 11;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.StateType <em>State Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.StateType
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getStateType()
	 * @generated
	 */
	int STATE_TYPE = 14;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.ParameterImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 8;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.DocumentRootImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Fixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FIXED = 0;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Process Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMULATION_DATA__PROCESS_NODES = 0;

	/**
	 * The number of structural features of the '<em>Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMULATION_DATA_FEATURE_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMULATION_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__ID = EMULATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = EMULATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = EMULATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Testpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__TESTPOINTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assertions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__ASSERTIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__INPUT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__OUTPUT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__ALIAS = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__STATE = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Error Information</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__ERROR_INFORMATION = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Intermediate Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__INTERMEDIATE_INPUTS = NAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Model Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__MODEL_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Multi Input Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__MULTI_INPUT_NODES = NAMED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Process Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Process Node</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT__PROCESS_NODE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT__LANG = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT__EXPRESSION = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Testpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTPOINT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Process Node</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION__PROCESS_NODE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Accessible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION__ACCESSIBLE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Assertion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_OUT_DATA_TYPE__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_OUT_DATA_TYPE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_OUT_DATA_TYPE__PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Soap Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_OUT_DATA_TYPE__SOAP_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>In Out Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_OUT_DATA_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT__ID = IN_OUT_DATA_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT__NAME = IN_OUT_DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT__PARAMETERS = IN_OUT_DATA_TYPE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Soap Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT__SOAP_MESSAGE = IN_OUT_DATA_TYPE__SOAP_MESSAGE;

	/**
	 * The number of structural features of the '<em>Input</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_FEATURE_COUNT = IN_OUT_DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT__ID = IN_OUT_DATA_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT__NAME = IN_OUT_DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT__PARAMETERS = IN_OUT_DATA_TYPE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Soap Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT__SOAP_MESSAGE = IN_OUT_DATA_TYPE__SOAP_MESSAGE;

	/**
	 * The number of structural features of the '<em>Output</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_FEATURE_COUNT = IN_OUT_DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Failed Assertion</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_INFORMATION__FAILED_ASSERTION = EMULATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Error Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_INFORMATION__ERROR_MESSAGE = EMULATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Error Information</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_INFORMATION_FEATURE_COUNT = EMULATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.IntermediateInputImpl <em>Intermediate Input</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.IntermediateInputImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getIntermediateInput()
	 * @generated
	 */
	int INTERMEDIATE_INPUT = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERMEDIATE_INPUT__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERMEDIATE_INPUT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Request Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERMEDIATE_INPUT__REQUEST_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Response Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERMEDIATE_INPUT__RESPONSE_MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Intermediate Input</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERMEDIATE_INPUT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.bx.emulation.model.impl.MultiInputImpl <em>Multi Input</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.bx.emulation.model.impl.MultiInputImpl
	 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getMultiInput()
	 * @generated
	 */
	int MULTI_INPUT = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT__ID = INPUT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT__NAME = INPUT__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT__PARAMETERS = INPUT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Soap Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT__SOAP_MESSAGE = INPUT__SOAP_MESSAGE;

	/**
	 * The feature id for the '<em><b>Response Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT__RESPONSE_MESSAGE = INPUT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multi Input</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_INPUT_FEATURE_COUNT = INPUT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.Testpoint <em>Testpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Testpoint</em>'.
	 * @see com.tibco.bx.emulation.model.Testpoint
	 * @generated
	 */
	EClass getTestpoint();

	/**
	 * Returns the meta object for the container reference '{@link com.tibco.bx.emulation.model.Testpoint#getProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Process Node</em>'.
	 * @see com.tibco.bx.emulation.model.Testpoint#getProcessNode()
	 * @see #getTestpoint()
	 * @generated
	 */
	EReference getTestpoint_ProcessNode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.Testpoint#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see com.tibco.bx.emulation.model.Testpoint#getLang()
	 * @see #getTestpoint()
	 * @generated
	 */
	EAttribute getTestpoint_Lang();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.Testpoint#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see com.tibco.bx.emulation.model.Testpoint#getExpression()
	 * @see #getTestpoint()
	 * @generated
	 */
	EAttribute getTestpoint_Expression();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.Assertion <em>Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assertion</em>'.
	 * @see com.tibco.bx.emulation.model.Assertion
	 * @generated
	 */
	EClass getAssertion();

	/**
	 * Returns the meta object for the container reference '{@link com.tibco.bx.emulation.model.Assertion#getProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Process Node</em>'.
	 * @see com.tibco.bx.emulation.model.Assertion#getProcessNode()
	 * @see #getAssertion()
	 * @generated
	 */
	EReference getAssertion_ProcessNode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.Assertion#isAccessible <em>Accessible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accessible</em>'.
	 * @see com.tibco.bx.emulation.model.Assertion#isAccessible()
	 * @see #getAssertion()
	 * @generated
	 */
	EAttribute getAssertion_Accessible();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.ProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Node</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode
	 * @generated
	 */
	EClass getProcessNode();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.ProcessNode#getTestpoints <em>Testpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Testpoints</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getTestpoints()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_Testpoints();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.ProcessNode#getAssertions <em>Assertions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertions</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getAssertions()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_Assertions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.bx.emulation.model.ProcessNode#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getInput()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_Input();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.bx.emulation.model.ProcessNode#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getOutput()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_Output();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.ProcessNode#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getAlias()
	 * @see #getProcessNode()
	 * @generated
	 */
	EAttribute getProcessNode_Alias();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.ProcessNode#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getDescription()
	 * @see #getProcessNode()
	 * @generated
	 */
	EAttribute getProcessNode_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.ProcessNode#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getState()
	 * @see #getProcessNode()
	 * @generated
	 */
	EAttribute getProcessNode_State();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.bx.emulation.model.ProcessNode#getErrorInformation <em>Error Information</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Error Information</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getErrorInformation()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_ErrorInformation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.ProcessNode#getIntermediateInputs <em>Intermediate Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intermediate Inputs</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getIntermediateInputs()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_IntermediateInputs();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.ProcessNode#getModelType <em>Model Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Type</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getModelType()
	 * @see #getProcessNode()
	 * @generated
	 */
	EAttribute getProcessNode_ModelType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.ProcessNode#getMultiInputNodes <em>Multi Input Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multi Input Nodes</em>'.
	 * @see com.tibco.bx.emulation.model.ProcessNode#getMultiInputNodes()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_MultiInputNodes();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.EmulationData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data</em>'.
	 * @see com.tibco.bx.emulation.model.EmulationData
	 * @generated
	 */
	EClass getEmulationData();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.EmulationData#getProcessNodes <em>Process Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Process Nodes</em>'.
	 * @see com.tibco.bx.emulation.model.EmulationData#getProcessNodes()
	 * @see #getEmulationData()
	 * @generated
	 */
	EReference getEmulationData_ProcessNodes();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.EmulationElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see com.tibco.bx.emulation.model.EmulationElement
	 * @generated
	 */
	EClass getEmulationElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see com.tibco.bx.emulation.model.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.NamedElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.bx.emulation.model.NamedElement#getId()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.bx.emulation.model.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.Input <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input</em>'.
	 * @see com.tibco.bx.emulation.model.Input
	 * @generated
	 */
	EClass getInput();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.Output <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Output</em>'.
	 * @see com.tibco.bx.emulation.model.Output
	 * @generated
	 */
	EClass getOutput();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.ErrorInformation <em>Error Information</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Information</em>'.
	 * @see com.tibco.bx.emulation.model.ErrorInformation
	 * @generated
	 */
	EClass getErrorInformation();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.bx.emulation.model.ErrorInformation#getFailedAssertion <em>Failed Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Failed Assertion</em>'.
	 * @see com.tibco.bx.emulation.model.ErrorInformation#getFailedAssertion()
	 * @see #getErrorInformation()
	 * @generated
	 */
	EReference getErrorInformation_FailedAssertion();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.ErrorInformation#getErrorMessage <em>Error Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Error Message</em>'.
	 * @see com.tibco.bx.emulation.model.ErrorInformation#getErrorMessage()
	 * @see #getErrorInformation()
	 * @generated
	 */
	EAttribute getErrorInformation_ErrorMessage();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.IntermediateInput <em>Intermediate Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intermediate Input</em>'.
	 * @see com.tibco.bx.emulation.model.IntermediateInput
	 * @generated
	 */
	EClass getIntermediateInput();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.IntermediateInput#getRequestMessage <em>Request Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Request Message</em>'.
	 * @see com.tibco.bx.emulation.model.IntermediateInput#getRequestMessage()
	 * @see #getIntermediateInput()
	 * @generated
	 */
	EAttribute getIntermediateInput_RequestMessage();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.IntermediateInput#getResponseMessage <em>Response Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Response Message</em>'.
	 * @see com.tibco.bx.emulation.model.IntermediateInput#getResponseMessage()
	 * @see #getIntermediateInput()
	 * @generated
	 */
	EAttribute getIntermediateInput_ResponseMessage();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.MultiInput <em>Multi Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Input</em>'.
	 * @see com.tibco.bx.emulation.model.MultiInput
	 * @generated
	 */
	EClass getMultiInput();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.MultiInput#getResponseMessage <em>Response Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Response Message</em>'.
	 * @see com.tibco.bx.emulation.model.MultiInput#getResponseMessage()
	 * @see #getMultiInput()
	 * @generated
	 */
	EAttribute getMultiInput_ResponseMessage();

	/**
	 * Returns the meta object for enum '{@link com.tibco.bx.emulation.model.StateType <em>State Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>State Type</em>'.
	 * @see com.tibco.bx.emulation.model.StateType
	 * @generated
	 */
	EEnum getStateType();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.InOutDataType <em>In Out Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>In Out Data Type</em>'.
	 * @see com.tibco.bx.emulation.model.InOutDataType
	 * @generated
	 */
	EClass getInOutDataType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.bx.emulation.model.InOutDataType#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.tibco.bx.emulation.model.InOutDataType#getParameters()
	 * @see #getInOutDataType()
	 * @generated
	 */
	EReference getInOutDataType_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.InOutDataType#getSoapMessage <em>Soap Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Soap Message</em>'.
	 * @see com.tibco.bx.emulation.model.InOutDataType#getSoapMessage()
	 * @see #getInOutDataType()
	 * @generated
	 */
	EAttribute getInOutDataType_SoapMessage();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see com.tibco.bx.emulation.model.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.bx.emulation.model.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.bx.emulation.model.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.bx.emulation.model.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.bx.emulation.model.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.bx.emulation.model.DocumentRoot#getFixed <em>Fixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Fixed</em>'.
	 * @see com.tibco.bx.emulation.model.DocumentRoot#getFixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Fixed();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmulationFactory getEmulationFactory();

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
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.TestpointImpl <em>Testpoint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.TestpointImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getTestpoint()
		 * @generated
		 */
		EClass TESTPOINT = eINSTANCE.getTestpoint();

		/**
		 * The meta object literal for the '<em><b>Process Node</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTPOINT__PROCESS_NODE = eINSTANCE.getTestpoint_ProcessNode();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTPOINT__LANG = eINSTANCE.getTestpoint_Lang();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTPOINT__EXPRESSION = eINSTANCE.getTestpoint_Expression();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.AssertionImpl <em>Assertion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.AssertionImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getAssertion()
		 * @generated
		 */
		EClass ASSERTION = eINSTANCE.getAssertion();

		/**
		 * The meta object literal for the '<em><b>Process Node</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION__PROCESS_NODE = eINSTANCE.getAssertion_ProcessNode();

		/**
		 * The meta object literal for the '<em><b>Accessible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION__ACCESSIBLE = eINSTANCE.getAssertion_Accessible();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl <em>Process Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.ProcessNodeImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getProcessNode()
		 * @generated
		 */
		EClass PROCESS_NODE = eINSTANCE.getProcessNode();

		/**
		 * The meta object literal for the '<em><b>Testpoints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__TESTPOINTS = eINSTANCE.getProcessNode_Testpoints();

		/**
		 * The meta object literal for the '<em><b>Assertions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__ASSERTIONS = eINSTANCE.getProcessNode_Assertions();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__INPUT = eINSTANCE.getProcessNode_Input();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__OUTPUT = eINSTANCE.getProcessNode_Output();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_NODE__ALIAS = eINSTANCE.getProcessNode_Alias();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_NODE__DESCRIPTION = eINSTANCE.getProcessNode_Description();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_NODE__STATE = eINSTANCE.getProcessNode_State();

		/**
		 * The meta object literal for the '<em><b>Error Information</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__ERROR_INFORMATION = eINSTANCE.getProcessNode_ErrorInformation();

		/**
		 * The meta object literal for the '<em><b>Intermediate Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__INTERMEDIATE_INPUTS = eINSTANCE.getProcessNode_IntermediateInputs();

		/**
		 * The meta object literal for the '<em><b>Model Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_NODE__MODEL_TYPE = eINSTANCE.getProcessNode_ModelType();

		/**
		 * The meta object literal for the '<em><b>Multi Input Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__MULTI_INPUT_NODES = eINSTANCE.getProcessNode_MultiInputNodes();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.EmulationDataImpl <em>Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.EmulationDataImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getEmulationData()
		 * @generated
		 */
		EClass EMULATION_DATA = eINSTANCE.getEmulationData();

		/**
		 * The meta object literal for the '<em><b>Process Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMULATION_DATA__PROCESS_NODES = eINSTANCE.getEmulationData_ProcessNodes();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.EmulationElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.EmulationElementImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getEmulationElement()
		 * @generated
		 */
		EClass EMULATION_ELEMENT = eINSTANCE.getEmulationElement();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.NamedElementImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__ID = eINSTANCE.getNamedElement_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.InputImpl <em>Input</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.InputImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getInput()
		 * @generated
		 */
		EClass INPUT = eINSTANCE.getInput();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.OutputImpl <em>Output</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.OutputImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getOutput()
		 * @generated
		 */
		EClass OUTPUT = eINSTANCE.getOutput();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.ErrorInformationImpl <em>Error Information</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.ErrorInformationImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getErrorInformation()
		 * @generated
		 */
		EClass ERROR_INFORMATION = eINSTANCE.getErrorInformation();

		/**
		 * The meta object literal for the '<em><b>Failed Assertion</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ERROR_INFORMATION__FAILED_ASSERTION = eINSTANCE.getErrorInformation_FailedAssertion();

		/**
		 * The meta object literal for the '<em><b>Error Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERROR_INFORMATION__ERROR_MESSAGE = eINSTANCE.getErrorInformation_ErrorMessage();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.IntermediateInputImpl <em>Intermediate Input</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.IntermediateInputImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getIntermediateInput()
		 * @generated
		 */
		EClass INTERMEDIATE_INPUT = eINSTANCE.getIntermediateInput();

		/**
		 * The meta object literal for the '<em><b>Request Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERMEDIATE_INPUT__REQUEST_MESSAGE = eINSTANCE.getIntermediateInput_RequestMessage();

		/**
		 * The meta object literal for the '<em><b>Response Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERMEDIATE_INPUT__RESPONSE_MESSAGE = eINSTANCE.getIntermediateInput_ResponseMessage();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.MultiInputImpl <em>Multi Input</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.MultiInputImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getMultiInput()
		 * @generated
		 */
		EClass MULTI_INPUT = eINSTANCE.getMultiInput();

		/**
		 * The meta object literal for the '<em><b>Response Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTI_INPUT__RESPONSE_MESSAGE = eINSTANCE.getMultiInput_ResponseMessage();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.StateType <em>State Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.StateType
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getStateType()
		 * @generated
		 */
		EEnum STATE_TYPE = eINSTANCE.getStateType();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.InOutDataTypeImpl <em>In Out Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.InOutDataTypeImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getInOutDataType()
		 * @generated
		 */
		EClass IN_OUT_DATA_TYPE = eINSTANCE.getInOutDataType();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IN_OUT_DATA_TYPE__PARAMETERS = eINSTANCE.getInOutDataType_Parameters();

		/**
		 * The meta object literal for the '<em><b>Soap Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IN_OUT_DATA_TYPE__SOAP_MESSAGE = eINSTANCE.getInOutDataType_SoapMessage();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.ParameterImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.bx.emulation.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.bx.emulation.model.impl.DocumentRootImpl
		 * @see com.tibco.bx.emulation.model.impl.EmulationPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Fixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__FIXED = eINSTANCE.getDocumentRoot_Fixed();

	}

} //EmulationPackage
