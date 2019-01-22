/**
 */
package com.tibco.xpd.rsd.util;

import com.tibco.xpd.rsd.*;
import com.tibco.xpd.rsd.AbstractResponse;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ModelElement;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.rsd.RsdPackage
 * @generated
 */
public class RsdSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static RsdPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RsdSwitch() {
        if (modelPackage == null) {
            modelPackage = RsdPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case RsdPackage.SERVICE: {
                Service service = (Service)theEObject;
                T result = caseService(service);
                if (result == null) result = caseNamedElement(service);
                if (result == null) result = caseModelElement(service);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.RESOURCE: {
                Resource resource = (Resource)theEObject;
                T result = caseResource(resource);
                if (result == null) result = caseNamedElement(resource);
                if (result == null) result = caseParameterContainer(resource);
                if (result == null) result = caseModelElement(resource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.METHOD: {
                Method method = (Method)theEObject;
                T result = caseMethod(method);
                if (result == null) result = caseNamedElement(method);
                if (result == null) result = caseModelElement(method);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.MODEL_ELEMENT: {
                ModelElement modelElement = (ModelElement)theEObject;
                T result = caseModelElement(modelElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.NAMED_ELEMENT: {
                NamedElement namedElement = (NamedElement)theEObject;
                T result = caseNamedElement(namedElement);
                if (result == null) result = caseModelElement(namedElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.REQUEST: {
                Request request = (Request)theEObject;
                T result = caseRequest(request);
                if (result == null) result = caseParameterContainer(request);
                if (result == null) result = casePayloadRefContainer(request);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.ABSTRACT_RESPONSE: {
                AbstractResponse abstractResponse = (AbstractResponse)theEObject;
                T result = caseAbstractResponse(abstractResponse);
                if (result == null) result = caseParameterContainer(abstractResponse);
                if (result == null) result = casePayloadRefContainer(abstractResponse);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.PARAMETER: {
                Parameter parameter = (Parameter)theEObject;
                T result = caseParameter(parameter);
                if (result == null) result = caseNamedElement(parameter);
                if (result == null) result = caseModelElement(parameter);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.PARAMETER_CONTAINER: {
                ParameterContainer parameterContainer = (ParameterContainer)theEObject;
                T result = caseParameterContainer(parameterContainer);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.PAYLOAD_REFERENCE: {
                PayloadReference payloadReference = (PayloadReference)theEObject;
                T result = casePayloadReference(payloadReference);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.PAYLOAD_REF_CONTAINER: {
                PayloadRefContainer payloadRefContainer = (PayloadRefContainer)theEObject;
                T result = casePayloadRefContainer(payloadRefContainer);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.FAULT: {
                Fault fault = (Fault)theEObject;
                T result = caseFault(fault);
                if (result == null) result = caseNamedElement(fault);
                if (result == null) result = caseAbstractResponse(fault);
                if (result == null) result = caseModelElement(fault);
                if (result == null) result = caseParameterContainer(fault);
                if (result == null) result = casePayloadRefContainer(fault);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case RsdPackage.RESPONSE: {
                Response response = (Response)theEObject;
                T result = caseResponse(response);
                if (result == null) result = caseAbstractResponse(response);
                if (result == null) result = caseParameterContainer(response);
                if (result == null) result = casePayloadRefContainer(response);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Service</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseService(Service object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResource(Resource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMethod(Method object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelElement(ModelElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Request</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Request</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequest(Request object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Response</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Response</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractResponse(AbstractResponse object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameter(Parameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterContainer(ParameterContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Payload Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Payload Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePayloadReference(PayloadReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Payload Ref Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Payload Ref Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePayloadRefContainer(PayloadRefContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fault</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fault</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFault(Fault object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Response</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Response</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResponse(Response object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //RsdSwitch
