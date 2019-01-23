/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deployment Parameter Fault Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.DeploymentParameterFaultType#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDeploymentParameterFaultType()
 * @model extendedMetaData="name='DeploymentParameterFault_._type' kind='elementOnly'"
 * @generated
 */
public interface DeploymentParameterFaultType extends EObject {
    /**
     * Returns the value of the '<em><b>Error</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.api.exception.ErrorLine}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Error</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Error</em>' containment reference list.
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDeploymentParameterFaultType_Error()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='error'"
     * @generated
     */
    EList<ErrorLine> getError();

} // DeploymentParameterFaultType
