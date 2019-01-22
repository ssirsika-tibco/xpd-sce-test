/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Type Fault Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.WorkTypeFaultType#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.api.exception.ExceptionPackage#getWorkTypeFaultType()
 * @model extendedMetaData="name='WorkTypeFault_._type' kind='elementOnly'"
 * @generated
 */
public interface WorkTypeFaultType extends EObject {
    /**
     * Returns the value of the '<em><b>Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Error</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Error</em>' containment reference.
     * @see #setError(ErrorLine)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getWorkTypeFaultType_Error()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='error'"
     * @generated
     */
    ErrorLine getError();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.WorkTypeFaultType#getError <em>Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Error</em>' containment reference.
     * @see #getError()
     * @generated
     */
    void setError(ErrorLine value);

} // WorkTypeFaultType
