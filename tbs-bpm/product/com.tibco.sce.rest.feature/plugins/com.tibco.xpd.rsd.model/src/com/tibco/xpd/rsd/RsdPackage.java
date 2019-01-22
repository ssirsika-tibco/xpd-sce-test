/**
 */
package com.tibco.xpd.rsd;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.rsd.RsdFactory
 * @model kind="package"
 * @generated
 */
public interface RsdPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "rsd";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://com.tibco.xpd/rsd";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "rsd";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    RsdPackage eINSTANCE = com.tibco.xpd.rsd.impl.RsdPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.ModelElementImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getModelElement()
     * @generated
     */
    int MODEL_ELEMENT = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT__ID = 0;

    /**
     * The number of structural features of the '<em>Model Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Model Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.NamedElementImpl <em>Named Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.NamedElementImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAME = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_OPERATION_COUNT = MODEL_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.ServiceImpl <em>Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.ServiceImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getService()
     * @generated
     */
    int SERVICE = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Resources</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__RESOURCES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Context Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__CONTEXT_PATH = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Media Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__MEDIA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Service</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Service</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.ResourceImpl <em>Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.ResourceImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getResource()
     * @generated
     */
    int RESOURCE = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__METHODS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Path Template</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__PATH_TEMPLATE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.MethodImpl <em>Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.MethodImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getMethod()
     * @generated
     */
    int METHOD = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Request</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__REQUEST = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Http Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__HTTP_METHOD = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Faults</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__FAULTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__RESPONSE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.ParameterContainer <em>Parameter Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.ParameterContainer
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameterContainer()
     * @generated
     */
    int PARAMETER_CONTAINER = 8;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_CONTAINER__PARAMETERS = 0;

    /**
     * The number of structural features of the '<em>Parameter Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Parameter Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_CONTAINER_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.RequestImpl <em>Request</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.RequestImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getRequest()
     * @generated
     */
    int REQUEST = 5;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST__PARAMETERS = PARAMETER_CONTAINER__PARAMETERS;

    /**
     * The feature id for the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST__PAYLOAD_REFERENCE = PARAMETER_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Content Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST__CONTENT_TYPE = PARAMETER_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Accept</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST__ACCEPT = PARAMETER_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Request</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST_FEATURE_COUNT = PARAMETER_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Request</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUEST_OPERATION_COUNT = PARAMETER_CONTAINER_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.AbstractResponseImpl <em>Abstract Response</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.AbstractResponseImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getAbstractResponse()
     * @generated
     */
    int ABSTRACT_RESPONSE = 6;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_RESPONSE__PARAMETERS = PARAMETER_CONTAINER__PARAMETERS;

    /**
     * The feature id for the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_RESPONSE__PAYLOAD_REFERENCE = PARAMETER_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Abstract Response</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_RESPONSE_FEATURE_COUNT = PARAMETER_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Abstract Response</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_RESPONSE_OPERATION_COUNT = PARAMETER_CONTAINER_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.ParameterImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameter()
     * @generated
     */
    int PARAMETER = 7;

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
     * The feature id for the '<em><b>Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER__STYLE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER__DATA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Mandatory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER__MANDATORY = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER__DEFAULT_VALUE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Fixed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER__FIXED = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Parameter</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Parameter</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl <em>Payload Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.PayloadReferenceImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getPayloadReference()
     * @generated
     */
    int PAYLOAD_REFERENCE = 9;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE__NAMESPACE = 0;

    /**
     * The feature id for the '<em><b>Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE__REF = 1;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE__LOCATION = 2;

    /**
     * The feature id for the '<em><b>Media Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE__MEDIA_TYPE = 3;

    /**
     * The feature id for the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE__ARRAY = 4;

    /**
     * The number of structural features of the '<em>Payload Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Payload Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REFERENCE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.PayloadRefContainer <em>Payload Ref Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.PayloadRefContainer
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getPayloadRefContainer()
     * @generated
     */
    int PAYLOAD_REF_CONTAINER = 10;

    /**
     * The feature id for the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REF_CONTAINER__PAYLOAD_REFERENCE = 0;

    /**
     * The number of structural features of the '<em>Payload Ref Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REF_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Payload Ref Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_REF_CONTAINER_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.FaultImpl <em>Fault</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.FaultImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getFault()
     * @generated
     */
    int FAULT = 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT__PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT__PAYLOAD_REFERENCE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Status Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT__STATUS_CODE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Fault</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Fault</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.impl.ResponseImpl <em>Response</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.impl.ResponseImpl
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getResponse()
     * @generated
     */
    int RESPONSE = 12;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSE__PARAMETERS = ABSTRACT_RESPONSE__PARAMETERS;

    /**
     * The feature id for the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSE__PAYLOAD_REFERENCE = ABSTRACT_RESPONSE__PAYLOAD_REFERENCE;

    /**
     * The number of structural features of the '<em>Response</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSE_FEATURE_COUNT = ABSTRACT_RESPONSE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Response</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSE_OPERATION_COUNT = ABSTRACT_RESPONSE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.HttpMethod <em>Http Method</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.HttpMethod
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getHttpMethod()
     * @generated
     */
    int HTTP_METHOD = 13;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.DataType <em>Data Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.DataType
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getDataType()
     * @generated
     */
    int DATA_TYPE = 14;

    /**
     * The meta object id for the '{@link com.tibco.xpd.rsd.ParameterStyle <em>Parameter Style</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.rsd.ParameterStyle
     * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameterStyle()
     * @generated
     */
    int PARAMETER_STYLE = 15;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Service <em>Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Service</em>'.
     * @see com.tibco.xpd.rsd.Service
     * @generated
     */
    EClass getService();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.rsd.Service#getResources <em>Resources</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resources</em>'.
     * @see com.tibco.xpd.rsd.Service#getResources()
     * @see #getService()
     * @generated
     */
    EReference getService_Resources();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Service#getContextPath <em>Context Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Context Path</em>'.
     * @see com.tibco.xpd.rsd.Service#getContextPath()
     * @see #getService()
     * @generated
     */
    EAttribute getService_ContextPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Service#getMediaType <em>Media Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Media Type</em>'.
     * @see com.tibco.xpd.rsd.Service#getMediaType()
     * @see #getService()
     * @generated
     */
    EAttribute getService_MediaType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Resource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource</em>'.
     * @see com.tibco.xpd.rsd.Resource
     * @generated
     */
    EClass getResource();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.rsd.Resource#getMethods <em>Methods</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Methods</em>'.
     * @see com.tibco.xpd.rsd.Resource#getMethods()
     * @see #getResource()
     * @generated
     */
    EReference getResource_Methods();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Resource#getPathTemplate <em>Path Template</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Path Template</em>'.
     * @see com.tibco.xpd.rsd.Resource#getPathTemplate()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_PathTemplate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Method <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Method</em>'.
     * @see com.tibco.xpd.rsd.Method
     * @generated
     */
    EClass getMethod();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.rsd.Method#getRequest <em>Request</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Request</em>'.
     * @see com.tibco.xpd.rsd.Method#getRequest()
     * @see #getMethod()
     * @generated
     */
    EReference getMethod_Request();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Method#getHttpMethod <em>Http Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Http Method</em>'.
     * @see com.tibco.xpd.rsd.Method#getHttpMethod()
     * @see #getMethod()
     * @generated
     */
    EAttribute getMethod_HttpMethod();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.rsd.Method#getFaults <em>Faults</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Faults</em>'.
     * @see com.tibco.xpd.rsd.Method#getFaults()
     * @see #getMethod()
     * @generated
     */
    EReference getMethod_Faults();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.rsd.Method#getResponse <em>Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Response</em>'.
     * @see com.tibco.xpd.rsd.Method#getResponse()
     * @see #getMethod()
     * @generated
     */
    EReference getMethod_Response();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Element</em>'.
     * @see com.tibco.xpd.rsd.ModelElement
     * @generated
     */
    EClass getModelElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.ModelElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.rsd.ModelElement#getId()
     * @see #getModelElement()
     * @generated
     */
    EAttribute getModelElement_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Element</em>'.
     * @see com.tibco.xpd.rsd.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.NamedElement#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.rsd.NamedElement#getName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Request <em>Request</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Request</em>'.
     * @see com.tibco.xpd.rsd.Request
     * @generated
     */
    EClass getRequest();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Request#getContentType <em>Content Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Content Type</em>'.
     * @see com.tibco.xpd.rsd.Request#getContentType()
     * @see #getRequest()
     * @generated
     */
    EAttribute getRequest_ContentType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Request#getAccept <em>Accept</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Accept</em>'.
     * @see com.tibco.xpd.rsd.Request#getAccept()
     * @see #getRequest()
     * @generated
     */
    EAttribute getRequest_Accept();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.AbstractResponse <em>Abstract Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Abstract Response</em>'.
     * @see com.tibco.xpd.rsd.AbstractResponse
     * @generated
     */
    EClass getAbstractResponse();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Parameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter</em>'.
     * @see com.tibco.xpd.rsd.Parameter
     * @generated
     */
    EClass getParameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Parameter#getStyle <em>Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Style</em>'.
     * @see com.tibco.xpd.rsd.Parameter#getStyle()
     * @see #getParameter()
     * @generated
     */
    EAttribute getParameter_Style();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Parameter#getDataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Data Type</em>'.
     * @see com.tibco.xpd.rsd.Parameter#getDataType()
     * @see #getParameter()
     * @generated
     */
    EAttribute getParameter_DataType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Parameter#isMandatory <em>Mandatory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mandatory</em>'.
     * @see com.tibco.xpd.rsd.Parameter#isMandatory()
     * @see #getParameter()
     * @generated
     */
    EAttribute getParameter_Mandatory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Parameter#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see com.tibco.xpd.rsd.Parameter#getDefaultValue()
     * @see #getParameter()
     * @generated
     */
    EAttribute getParameter_DefaultValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Parameter#isFixed <em>Fixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fixed</em>'.
     * @see com.tibco.xpd.rsd.Parameter#isFixed()
     * @see #getParameter()
     * @generated
     */
    EAttribute getParameter_Fixed();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.ParameterContainer <em>Parameter Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Container</em>'.
     * @see com.tibco.xpd.rsd.ParameterContainer
     * @generated
     */
    EClass getParameterContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.rsd.ParameterContainer#getParameters <em>Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameters</em>'.
     * @see com.tibco.xpd.rsd.ParameterContainer#getParameters()
     * @see #getParameterContainer()
     * @generated
     */
    EReference getParameterContainer_Parameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.PayloadReference <em>Payload Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Payload Reference</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference
     * @generated
     */
    EClass getPayloadReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.PayloadReference#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Namespace</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference#getNamespace()
     * @see #getPayloadReference()
     * @generated
     */
    EAttribute getPayloadReference_Namespace();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.PayloadReference#getRef <em>Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ref</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference#getRef()
     * @see #getPayloadReference()
     * @generated
     */
    EAttribute getPayloadReference_Ref();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.PayloadReference#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference#getLocation()
     * @see #getPayloadReference()
     * @generated
     */
    EAttribute getPayloadReference_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.PayloadReference#getMediaType <em>Media Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Media Type</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference#getMediaType()
     * @see #getPayloadReference()
     * @generated
     */
    EAttribute getPayloadReference_MediaType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.PayloadReference#isArray <em>Array</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Array</em>'.
     * @see com.tibco.xpd.rsd.PayloadReference#isArray()
     * @see #getPayloadReference()
     * @generated
     */
    EAttribute getPayloadReference_Array();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.PayloadRefContainer <em>Payload Ref Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Payload Ref Container</em>'.
     * @see com.tibco.xpd.rsd.PayloadRefContainer
     * @generated
     */
    EClass getPayloadRefContainer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.rsd.PayloadRefContainer#getPayloadReference <em>Payload Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Payload Reference</em>'.
     * @see com.tibco.xpd.rsd.PayloadRefContainer#getPayloadReference()
     * @see #getPayloadRefContainer()
     * @generated
     */
    EReference getPayloadRefContainer_PayloadReference();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Fault <em>Fault</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Fault</em>'.
     * @see com.tibco.xpd.rsd.Fault
     * @generated
     */
    EClass getFault();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.rsd.Fault#getStatusCode <em>Status Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Status Code</em>'.
     * @see com.tibco.xpd.rsd.Fault#getStatusCode()
     * @see #getFault()
     * @generated
     */
    EAttribute getFault_StatusCode();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.rsd.Response <em>Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Response</em>'.
     * @see com.tibco.xpd.rsd.Response
     * @generated
     */
    EClass getResponse();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.rsd.HttpMethod <em>Http Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Http Method</em>'.
     * @see com.tibco.xpd.rsd.HttpMethod
     * @generated
     */
    EEnum getHttpMethod();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.rsd.DataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Data Type</em>'.
     * @see com.tibco.xpd.rsd.DataType
     * @generated
     */
    EEnum getDataType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.rsd.ParameterStyle <em>Parameter Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Parameter Style</em>'.
     * @see com.tibco.xpd.rsd.ParameterStyle
     * @generated
     */
    EEnum getParameterStyle();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    RsdFactory getRsdFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.ServiceImpl <em>Service</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.ServiceImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getService()
         * @generated
         */
        EClass SERVICE = eINSTANCE.getService();

        /**
         * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVICE__RESOURCES = eINSTANCE.getService_Resources();

        /**
         * The meta object literal for the '<em><b>Context Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE__CONTEXT_PATH = eINSTANCE.getService_ContextPath();

        /**
         * The meta object literal for the '<em><b>Media Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE__MEDIA_TYPE = eINSTANCE.getService_MediaType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.ResourceImpl <em>Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.ResourceImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getResource()
         * @generated
         */
        EClass RESOURCE = eINSTANCE.getResource();

        /**
         * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__METHODS = eINSTANCE.getResource_Methods();

        /**
         * The meta object literal for the '<em><b>Path Template</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__PATH_TEMPLATE = eINSTANCE.getResource_PathTemplate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.MethodImpl <em>Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.MethodImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getMethod()
         * @generated
         */
        EClass METHOD = eINSTANCE.getMethod();

        /**
         * The meta object literal for the '<em><b>Request</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference METHOD__REQUEST = eINSTANCE.getMethod_Request();

        /**
         * The meta object literal for the '<em><b>Http Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute METHOD__HTTP_METHOD = eINSTANCE.getMethod_HttpMethod();

        /**
         * The meta object literal for the '<em><b>Faults</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference METHOD__FAULTS = eINSTANCE.getMethod_Faults();

        /**
         * The meta object literal for the '<em><b>Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference METHOD__RESPONSE = eINSTANCE.getMethod_Response();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.ModelElementImpl <em>Model Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.ModelElementImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getModelElement()
         * @generated
         */
        EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_ELEMENT__ID = eINSTANCE.getModelElement_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.NamedElementImpl <em>Named Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.NamedElementImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.RequestImpl <em>Request</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.RequestImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getRequest()
         * @generated
         */
        EClass REQUEST = eINSTANCE.getRequest();

        /**
         * The meta object literal for the '<em><b>Content Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REQUEST__CONTENT_TYPE = eINSTANCE.getRequest_ContentType();

        /**
         * The meta object literal for the '<em><b>Accept</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REQUEST__ACCEPT = eINSTANCE.getRequest_Accept();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.AbstractResponseImpl <em>Abstract Response</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.AbstractResponseImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getAbstractResponse()
         * @generated
         */
        EClass ABSTRACT_RESPONSE = eINSTANCE.getAbstractResponse();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.ParameterImpl <em>Parameter</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.ParameterImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameter()
         * @generated
         */
        EClass PARAMETER = eINSTANCE.getParameter();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER__STYLE = eINSTANCE.getParameter_Style();

        /**
         * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER__DATA_TYPE = eINSTANCE.getParameter_DataType();

        /**
         * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER__MANDATORY = eINSTANCE.getParameter_Mandatory();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER__DEFAULT_VALUE = eINSTANCE.getParameter_DefaultValue();

        /**
         * The meta object literal for the '<em><b>Fixed</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER__FIXED = eINSTANCE.getParameter_Fixed();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.ParameterContainer <em>Parameter Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.ParameterContainer
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameterContainer()
         * @generated
         */
        EClass PARAMETER_CONTAINER = eINSTANCE.getParameterContainer();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_CONTAINER__PARAMETERS = eINSTANCE.getParameterContainer_Parameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl <em>Payload Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.PayloadReferenceImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getPayloadReference()
         * @generated
         */
        EClass PAYLOAD_REFERENCE = eINSTANCE.getPayloadReference();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_REFERENCE__NAMESPACE = eINSTANCE.getPayloadReference_Namespace();

        /**
         * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_REFERENCE__REF = eINSTANCE.getPayloadReference_Ref();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_REFERENCE__LOCATION = eINSTANCE.getPayloadReference_Location();

        /**
         * The meta object literal for the '<em><b>Media Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_REFERENCE__MEDIA_TYPE = eINSTANCE.getPayloadReference_MediaType();

        /**
         * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_REFERENCE__ARRAY = eINSTANCE.getPayloadReference_Array();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.PayloadRefContainer <em>Payload Ref Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.PayloadRefContainer
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getPayloadRefContainer()
         * @generated
         */
        EClass PAYLOAD_REF_CONTAINER = eINSTANCE.getPayloadRefContainer();

        /**
         * The meta object literal for the '<em><b>Payload Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAYLOAD_REF_CONTAINER__PAYLOAD_REFERENCE = eINSTANCE.getPayloadRefContainer_PayloadReference();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.FaultImpl <em>Fault</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.FaultImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getFault()
         * @generated
         */
        EClass FAULT = eINSTANCE.getFault();

        /**
         * The meta object literal for the '<em><b>Status Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FAULT__STATUS_CODE = eINSTANCE.getFault_StatusCode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.impl.ResponseImpl <em>Response</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.impl.ResponseImpl
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getResponse()
         * @generated
         */
        EClass RESPONSE = eINSTANCE.getResponse();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.HttpMethod <em>Http Method</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.HttpMethod
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getHttpMethod()
         * @generated
         */
        EEnum HTTP_METHOD = eINSTANCE.getHttpMethod();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.DataType <em>Data Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.DataType
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getDataType()
         * @generated
         */
        EEnum DATA_TYPE = eINSTANCE.getDataType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.rsd.ParameterStyle <em>Parameter Style</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.rsd.ParameterStyle
         * @see com.tibco.xpd.rsd.impl.RsdPackageImpl#getParameterStyle()
         * @generated
         */
        EEnum PARAMETER_STYLE = eINSTANCE.getParameterStyle();

    }

} //RsdPackage
