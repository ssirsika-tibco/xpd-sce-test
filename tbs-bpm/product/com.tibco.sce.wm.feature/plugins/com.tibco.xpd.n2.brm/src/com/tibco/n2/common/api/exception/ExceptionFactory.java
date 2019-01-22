/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.api.exception.ExceptionPackage
 * @generated
 */
public interface ExceptionFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ExceptionFactory eINSTANCE = com.tibco.n2.common.api.exception.impl.ExceptionFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Deployment Parameter Fault Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Deployment Parameter Fault Type</em>'.
     * @generated
     */
    DeploymentParameterFaultType createDeploymentParameterFaultType();

    /**
     * Returns a new object of class '<em>Detailed Error Line</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Detailed Error Line</em>'.
     * @generated
     */
    DetailedErrorLine createDetailedErrorLine();

    /**
     * Returns a new object of class '<em>Detailed Exception</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Detailed Exception</em>'.
     * @generated
     */
    DetailedException createDetailedException();

    /**
     * Returns a new object of class '<em>Error Line</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Error Line</em>'.
     * @generated
     */
    ErrorLine createErrorLine();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Internal Service Fault Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Internal Service Fault Type</em>'.
     * @generated
     */
    InternalServiceFaultType createInternalServiceFaultType();

    /**
     * Returns a new object of class '<em>Invalid Work Type Fault Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Invalid Work Type Fault Type</em>'.
     * @generated
     */
    InvalidWorkTypeFaultType createInvalidWorkTypeFaultType();

    /**
     * Returns a new object of class '<em>Work Type Fault Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Type Fault Type</em>'.
     * @generated
     */
    WorkTypeFaultType createWorkTypeFaultType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ExceptionPackage getExceptionPackage();

} //ExceptionFactory
