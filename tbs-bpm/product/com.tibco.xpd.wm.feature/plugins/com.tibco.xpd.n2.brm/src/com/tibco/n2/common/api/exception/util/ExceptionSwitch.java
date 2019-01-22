/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.util;

import com.tibco.n2.common.api.exception.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

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
 * @see com.tibco.n2.common.api.exception.ExceptionPackage
 * @generated
 */
public class ExceptionSwitch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ExceptionPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionSwitch() {
        if (modelPackage == null) {
            modelPackage = ExceptionPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case ExceptionPackage.DEPLOYMENT_PARAMETER_FAULT_TYPE: {
                DeploymentParameterFaultType deploymentParameterFaultType = (DeploymentParameterFaultType)theEObject;
                T result = caseDeploymentParameterFaultType(deploymentParameterFaultType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.DETAILED_ERROR_LINE: {
                DetailedErrorLine detailedErrorLine = (DetailedErrorLine)theEObject;
                T result = caseDetailedErrorLine(detailedErrorLine);
                if (result == null) result = caseErrorLine(detailedErrorLine);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.DETAILED_EXCEPTION: {
                DetailedException detailedException = (DetailedException)theEObject;
                T result = caseDetailedException(detailedException);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.ERROR_LINE: {
                ErrorLine errorLine = (ErrorLine)theEObject;
                T result = caseErrorLine(errorLine);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.DOCUMENT_ROOT: {
                DocumentRoot documentRoot = (DocumentRoot)theEObject;
                T result = caseDocumentRoot(documentRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE: {
                InternalServiceFaultType internalServiceFaultType = (InternalServiceFaultType)theEObject;
                T result = caseInternalServiceFaultType(internalServiceFaultType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.INVALID_WORK_TYPE_FAULT_TYPE: {
                InvalidWorkTypeFaultType invalidWorkTypeFaultType = (InvalidWorkTypeFaultType)theEObject;
                T result = caseInvalidWorkTypeFaultType(invalidWorkTypeFaultType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ExceptionPackage.WORK_TYPE_FAULT_TYPE: {
                WorkTypeFaultType workTypeFaultType = (WorkTypeFaultType)theEObject;
                T result = caseWorkTypeFaultType(workTypeFaultType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Deployment Parameter Fault Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Deployment Parameter Fault Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeploymentParameterFaultType(DeploymentParameterFaultType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Detailed Error Line</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Detailed Error Line</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDetailedErrorLine(DetailedErrorLine object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Detailed Exception</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Detailed Exception</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDetailedException(DetailedException object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Error Line</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Error Line</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseErrorLine(ErrorLine object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Internal Service Fault Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Internal Service Fault Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInternalServiceFaultType(InternalServiceFaultType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Invalid Work Type Fault Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Invalid Work Type Fault Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInvalidWorkTypeFaultType(InvalidWorkTypeFaultType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Type Fault Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Type Fault Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkTypeFaultType(WorkTypeFaultType object) {
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
    public T defaultCase(EObject object) {
        return null;
    }

} //ExceptionSwitch
