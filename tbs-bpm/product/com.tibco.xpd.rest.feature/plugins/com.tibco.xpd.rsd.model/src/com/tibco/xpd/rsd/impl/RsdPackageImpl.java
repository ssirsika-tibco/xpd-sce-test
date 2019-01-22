/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.AbstractResponse;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ModelElement;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.RsdFactory;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RsdPackageImpl extends EPackageImpl implements RsdPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass serviceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass methodEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelElementEClass = null;

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
    private EClass requestEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass abstractResponseEClass = null;

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
    private EClass parameterContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass payloadReferenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass payloadRefContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass faultEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass responseEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum httpMethodEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum dataTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum parameterStyleEEnum = null;

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
     * @see com.tibco.xpd.rsd.RsdPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private RsdPackageImpl() {
        super(eNS_URI, RsdFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link RsdPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static RsdPackage init() {
        if (isInited) return (RsdPackage)EPackage.Registry.INSTANCE.getEPackage(RsdPackage.eNS_URI);

        // Obtain or create and register package
        RsdPackageImpl theRsdPackage = (RsdPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RsdPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RsdPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theRsdPackage.createPackageContents();

        // Initialize created meta-data
        theRsdPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theRsdPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(RsdPackage.eNS_URI, theRsdPackage);
        return theRsdPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getService() {
        return serviceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getService_Resources() {
        return (EReference)serviceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getService_ContextPath() {
        return (EAttribute)serviceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getService_MediaType() {
        return (EAttribute)serviceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResource() {
        return resourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_Methods() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_PathTemplate() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMethod() {
        return methodEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMethod_Request() {
        return (EReference)methodEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMethod_HttpMethod() {
        return (EAttribute)methodEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMethod_Faults() {
        return (EReference)methodEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMethod_Response() {
        return (EReference)methodEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelElement() {
        return modelElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelElement_Id() {
        return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
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
    public EAttribute getNamedElement_Name() {
        return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRequest() {
        return requestEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRequest_ContentType() {
        return (EAttribute)requestEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRequest_Accept() {
        return (EAttribute)requestEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAbstractResponse() {
        return abstractResponseEClass;
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
    public EAttribute getParameter_Style() {
        return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameter_DataType() {
        return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameter_Mandatory() {
        return (EAttribute)parameterEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameter_DefaultValue() {
        return (EAttribute)parameterEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameter_Fixed() {
        return (EAttribute)parameterEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterContainer() {
        return parameterContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterContainer_Parameters() {
        return (EReference)parameterContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPayloadReference() {
        return payloadReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadReference_Namespace() {
        return (EAttribute)payloadReferenceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadReference_Ref() {
        return (EAttribute)payloadReferenceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadReference_Location() {
        return (EAttribute)payloadReferenceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadReference_MediaType() {
        return (EAttribute)payloadReferenceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadReference_Array() {
        return (EAttribute)payloadReferenceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPayloadRefContainer() {
        return payloadRefContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPayloadRefContainer_PayloadReference() {
        return (EReference)payloadRefContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFault() {
        return faultEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFault_StatusCode() {
        return (EAttribute)faultEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResponse() {
        return responseEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getHttpMethod() {
        return httpMethodEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDataType() {
        return dataTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getParameterStyle() {
        return parameterStyleEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RsdFactory getRsdFactory() {
        return (RsdFactory)getEFactoryInstance();
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
        serviceEClass = createEClass(SERVICE);
        createEReference(serviceEClass, SERVICE__RESOURCES);
        createEAttribute(serviceEClass, SERVICE__CONTEXT_PATH);
        createEAttribute(serviceEClass, SERVICE__MEDIA_TYPE);

        resourceEClass = createEClass(RESOURCE);
        createEReference(resourceEClass, RESOURCE__METHODS);
        createEAttribute(resourceEClass, RESOURCE__PATH_TEMPLATE);

        methodEClass = createEClass(METHOD);
        createEReference(methodEClass, METHOD__REQUEST);
        createEAttribute(methodEClass, METHOD__HTTP_METHOD);
        createEReference(methodEClass, METHOD__FAULTS);
        createEReference(methodEClass, METHOD__RESPONSE);

        modelElementEClass = createEClass(MODEL_ELEMENT);
        createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);

        namedElementEClass = createEClass(NAMED_ELEMENT);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

        requestEClass = createEClass(REQUEST);
        createEAttribute(requestEClass, REQUEST__CONTENT_TYPE);
        createEAttribute(requestEClass, REQUEST__ACCEPT);

        abstractResponseEClass = createEClass(ABSTRACT_RESPONSE);

        parameterEClass = createEClass(PARAMETER);
        createEAttribute(parameterEClass, PARAMETER__STYLE);
        createEAttribute(parameterEClass, PARAMETER__DATA_TYPE);
        createEAttribute(parameterEClass, PARAMETER__MANDATORY);
        createEAttribute(parameterEClass, PARAMETER__DEFAULT_VALUE);
        createEAttribute(parameterEClass, PARAMETER__FIXED);

        parameterContainerEClass = createEClass(PARAMETER_CONTAINER);
        createEReference(parameterContainerEClass, PARAMETER_CONTAINER__PARAMETERS);

        payloadReferenceEClass = createEClass(PAYLOAD_REFERENCE);
        createEAttribute(payloadReferenceEClass, PAYLOAD_REFERENCE__NAMESPACE);
        createEAttribute(payloadReferenceEClass, PAYLOAD_REFERENCE__REF);
        createEAttribute(payloadReferenceEClass, PAYLOAD_REFERENCE__LOCATION);
        createEAttribute(payloadReferenceEClass, PAYLOAD_REFERENCE__MEDIA_TYPE);
        createEAttribute(payloadReferenceEClass, PAYLOAD_REFERENCE__ARRAY);

        payloadRefContainerEClass = createEClass(PAYLOAD_REF_CONTAINER);
        createEReference(payloadRefContainerEClass, PAYLOAD_REF_CONTAINER__PAYLOAD_REFERENCE);

        faultEClass = createEClass(FAULT);
        createEAttribute(faultEClass, FAULT__STATUS_CODE);

        responseEClass = createEClass(RESPONSE);

        // Create enums
        httpMethodEEnum = createEEnum(HTTP_METHOD);
        dataTypeEEnum = createEEnum(DATA_TYPE);
        parameterStyleEEnum = createEEnum(PARAMETER_STYLE);
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
        serviceEClass.getESuperTypes().add(this.getNamedElement());
        resourceEClass.getESuperTypes().add(this.getNamedElement());
        resourceEClass.getESuperTypes().add(this.getParameterContainer());
        methodEClass.getESuperTypes().add(this.getNamedElement());
        namedElementEClass.getESuperTypes().add(this.getModelElement());
        requestEClass.getESuperTypes().add(this.getParameterContainer());
        requestEClass.getESuperTypes().add(this.getPayloadRefContainer());
        abstractResponseEClass.getESuperTypes().add(this.getParameterContainer());
        abstractResponseEClass.getESuperTypes().add(this.getPayloadRefContainer());
        parameterEClass.getESuperTypes().add(this.getNamedElement());
        faultEClass.getESuperTypes().add(this.getNamedElement());
        faultEClass.getESuperTypes().add(this.getAbstractResponse());
        responseEClass.getESuperTypes().add(this.getAbstractResponse());

        // Initialize classes, features, and operations; add parameters
        initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getService_Resources(), this.getResource(), null, "resources", null, 0, -1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getService_ContextPath(), ecorePackage.getEString(), "contextPath", null, 0, 1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getService_MediaType(), ecorePackage.getEString(), "mediaType", "application/json", 0, 1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getResource_Methods(), this.getMethod(), null, "methods", null, 0, -1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_PathTemplate(), ecorePackage.getEString(), "pathTemplate", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(methodEClass, Method.class, "Method", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMethod_Request(), this.getRequest(), null, "request", null, 1, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMethod_HttpMethod(), this.getHttpMethod(), "httpMethod", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMethod_Faults(), this.getFault(), null, "faults", null, 0, -1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMethod_Response(), this.getResponse(), null, "response", null, 1, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(modelElementEClass, ModelElement.class, "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getModelElement_Id(), ecorePackage.getEString(), "id", null, 0, 1, ModelElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(requestEClass, Request.class, "Request", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRequest_ContentType(), ecorePackage.getEString(), "contentType", "application/json", 0, 1, Request.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRequest_Accept(), ecorePackage.getEString(), "accept", "application/json", 0, 1, Request.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(abstractResponseEClass, AbstractResponse.class, "AbstractResponse", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getParameter_Style(), this.getParameterStyle(), "style", "QUERY", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameter_DataType(), this.getDataType(), "dataType", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameter_Mandatory(), ecorePackage.getEBoolean(), "mandatory", "false", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameter_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameter_Fixed(), ecorePackage.getEBoolean(), "fixed", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(parameterContainerEClass, ParameterContainer.class, "ParameterContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getParameterContainer_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, ParameterContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(payloadReferenceEClass, PayloadReference.class, "PayloadReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPayloadReference_Namespace(), ecorePackage.getEString(), "namespace", null, 0, 1, PayloadReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPayloadReference_Ref(), ecorePackage.getEString(), "ref", null, 0, 1, PayloadReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPayloadReference_Location(), ecorePackage.getEString(), "location", null, 0, 1, PayloadReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPayloadReference_MediaType(), ecorePackage.getEString(), "mediaType", null, 0, 1, PayloadReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPayloadReference_Array(), ecorePackage.getEBoolean(), "array", null, 0, 1, PayloadReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(payloadRefContainerEClass, PayloadRefContainer.class, "PayloadRefContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPayloadRefContainer_PayloadReference(), this.getPayloadReference(), null, "payloadReference", null, 0, 1, PayloadRefContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(faultEClass, Fault.class, "Fault", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFault_StatusCode(), ecorePackage.getEString(), "statusCode", null, 0, 1, Fault.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(responseEClass, Response.class, "Response", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Initialize enums and add enum literals
        initEEnum(httpMethodEEnum, HttpMethod.class, "HttpMethod");
        addEEnumLiteral(httpMethodEEnum, HttpMethod.GET);
        addEEnumLiteral(httpMethodEEnum, HttpMethod.PUT);
        addEEnumLiteral(httpMethodEEnum, HttpMethod.POST);
        addEEnumLiteral(httpMethodEEnum, HttpMethod.DELETE);

        initEEnum(dataTypeEEnum, DataType.class, "DataType");
        addEEnumLiteral(dataTypeEEnum, DataType.TEXT);
        addEEnumLiteral(dataTypeEEnum, DataType.INTEGER);
        addEEnumLiteral(dataTypeEEnum, DataType.DECIMAL);
        addEEnumLiteral(dataTypeEEnum, DataType.BOOLEAN);
        addEEnumLiteral(dataTypeEEnum, DataType.DATE);
        addEEnumLiteral(dataTypeEEnum, DataType.TIME);
        addEEnumLiteral(dataTypeEEnum, DataType.DATE_TIME);

        initEEnum(parameterStyleEEnum, ParameterStyle.class, "ParameterStyle");
        addEEnumLiteral(parameterStyleEEnum, ParameterStyle.PATH);
        addEEnumLiteral(parameterStyleEEnum, ParameterStyle.QUERY);
        addEEnumLiteral(parameterStyleEEnum, ParameterStyle.HEADER);

        // Create resource
        createResource(eNS_URI);
    }

} //RsdPackageImpl
