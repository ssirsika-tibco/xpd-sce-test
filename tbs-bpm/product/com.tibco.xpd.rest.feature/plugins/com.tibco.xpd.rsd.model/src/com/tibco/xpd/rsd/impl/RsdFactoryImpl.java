/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.RsdFactory;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class RsdFactoryImpl extends EFactoryImpl implements RsdFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public static RsdFactory init() {
        try {
            RsdFactory theRsdFactory = (RsdFactory)EPackage.Registry.INSTANCE.getEFactory(RsdPackage.eNS_URI);
            if (theRsdFactory != null) {
                return theRsdFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new RsdFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public RsdFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case RsdPackage.SERVICE: return createService();
            case RsdPackage.RESOURCE: return createResource();
            case RsdPackage.METHOD: return createMethod();
            case RsdPackage.REQUEST: return createRequest();
            case RsdPackage.PARAMETER: return createParameter();
            case RsdPackage.PAYLOAD_REFERENCE: return createPayloadReference();
            case RsdPackage.FAULT: return createFault();
            case RsdPackage.RESPONSE: return createResponse();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case RsdPackage.HTTP_METHOD:
                return createHttpMethodFromString(eDataType, initialValue);
            case RsdPackage.DATA_TYPE:
                return createDataTypeFromString(eDataType, initialValue);
            case RsdPackage.PARAMETER_STYLE:
                return createParameterStyleFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case RsdPackage.HTTP_METHOD:
                return convertHttpMethodToString(eDataType, instanceValue);
            case RsdPackage.DATA_TYPE:
                return convertDataTypeToString(eDataType, instanceValue);
            case RsdPackage.PARAMETER_STYLE:
                return convertParameterStyleToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Service createService() {
        ServiceImpl service = new ServiceImpl();
        return service;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Resource createResource() {
        ResourceImpl resource = new ResourceImpl();
        return resource;
    }

    /**
     * <!-- begin-user-doc --> Also creates and sets request and response
     * attributes.<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Method createMethod() {
        MethodImpl method = new MethodImpl();
        Request request = createRequest();
        method.setRequest(request);
        Response response = createResponse();
        method.setResponse(response);
        return method;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Request createRequest() {
        RequestImpl request = new RequestImpl();
        return request;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Parameter createParameter() {
        ParameterImpl parameter = new ParameterImpl();
        return parameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public PayloadReference createPayloadReference() {
        PayloadReferenceImpl payloadReference = new PayloadReferenceImpl();
        return payloadReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Fault createFault() {
        FaultImpl fault = new FaultImpl();
        return fault;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Response createResponse() {
        ResponseImpl response = new ResponseImpl();
        return response;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public HttpMethod createHttpMethodFromString(EDataType eDataType,
            String initialValue) {
        HttpMethod result = HttpMethod.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertHttpMethodToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DataType createDataTypeFromString(EDataType eDataType,
            String initialValue) {
        DataType result = DataType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertDataTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ParameterStyle createParameterStyleFromString(EDataType eDataType,
            String initialValue) {
        ParameterStyle result = ParameterStyle.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertParameterStyleToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RsdPackage getRsdPackage() {
        return (RsdPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static RsdPackage getPackage() {
        return RsdPackage.eINSTANCE;
    }

} // RsdFactoryImpl
