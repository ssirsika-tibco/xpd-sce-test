/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExceptionFactoryImpl extends EFactoryImpl implements ExceptionFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ExceptionFactory init() {
        try {
            ExceptionFactory theExceptionFactory = (ExceptionFactory)EPackage.Registry.INSTANCE.getEFactory("http://exception.api.common.n2.tibco.com"); 
            if (theExceptionFactory != null) {
                return theExceptionFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ExceptionFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case ExceptionPackage.DEPLOYMENT_PARAMETER_FAULT_TYPE: return createDeploymentParameterFaultType();
            case ExceptionPackage.DETAILED_ERROR_LINE: return createDetailedErrorLine();
            case ExceptionPackage.DETAILED_EXCEPTION: return createDetailedException();
            case ExceptionPackage.ERROR_LINE: return createErrorLine();
            case ExceptionPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE: return createInternalServiceFaultType();
            case ExceptionPackage.INVALID_WORK_TYPE_FAULT_TYPE: return createInvalidWorkTypeFaultType();
            case ExceptionPackage.WORK_TYPE_FAULT_TYPE: return createWorkTypeFaultType();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case ExceptionPackage.SEVERITY_TYPE:
                return createSeverityTypeFromString(eDataType, initialValue);
            case ExceptionPackage.SEVERITY_TYPE_OBJECT:
                return createSeverityTypeObjectFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case ExceptionPackage.SEVERITY_TYPE:
                return convertSeverityTypeToString(eDataType, instanceValue);
            case ExceptionPackage.SEVERITY_TYPE_OBJECT:
                return convertSeverityTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeploymentParameterFaultType createDeploymentParameterFaultType() {
        DeploymentParameterFaultTypeImpl deploymentParameterFaultType = new DeploymentParameterFaultTypeImpl();
        return deploymentParameterFaultType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DetailedErrorLine createDetailedErrorLine() {
        DetailedErrorLineImpl detailedErrorLine = new DetailedErrorLineImpl();
        return detailedErrorLine;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DetailedException createDetailedException() {
        DetailedExceptionImpl detailedException = new DetailedExceptionImpl();
        return detailedException;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ErrorLine createErrorLine() {
        ErrorLineImpl errorLine = new ErrorLineImpl();
        return errorLine;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InternalServiceFaultType createInternalServiceFaultType() {
        InternalServiceFaultTypeImpl internalServiceFaultType = new InternalServiceFaultTypeImpl();
        return internalServiceFaultType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InvalidWorkTypeFaultType createInvalidWorkTypeFaultType() {
        InvalidWorkTypeFaultTypeImpl invalidWorkTypeFaultType = new InvalidWorkTypeFaultTypeImpl();
        return invalidWorkTypeFaultType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeFaultType createWorkTypeFaultType() {
        WorkTypeFaultTypeImpl workTypeFaultType = new WorkTypeFaultTypeImpl();
        return workTypeFaultType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SeverityType createSeverityTypeFromString(EDataType eDataType, String initialValue) {
        SeverityType result = SeverityType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSeverityTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SeverityType createSeverityTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createSeverityTypeFromString(ExceptionPackage.Literals.SEVERITY_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSeverityTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertSeverityTypeToString(ExceptionPackage.Literals.SEVERITY_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionPackage getExceptionPackage() {
        return (ExceptionPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ExceptionPackage getPackage() {
        return ExceptionPackage.eINSTANCE;
    }

} //ExceptionFactoryImpl
