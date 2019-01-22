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
 * A representation of the model object '<em><b>Error Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Single error or warning message, containing an error code, message string and parameters.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.ErrorLine#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.ErrorLine#getCode <em>Code</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.ErrorLine#getMessage <em>Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.api.exception.ExceptionPackage#getErrorLine()
 * @model extendedMetaData="name='ErrorLine' kind='elementOnly'"
 * @generated
 */
public interface ErrorLine extends EObject {
    /**
     * Returns the value of the '<em><b>Parameter</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Array of parameter values that can be applied to a foreign language message.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Parameter</em>' attribute list.
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getErrorLine_Parameter()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='parameter'"
     * @generated
     */
    EList<String> getParameter();

    /**
     * Returns the value of the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Code associated with this error.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Code</em>' attribute.
     * @see #setCode(String)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getErrorLine_Code()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='code'"
     * @generated
     */
    String getCode();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.ErrorLine#getCode <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Code</em>' attribute.
     * @see #getCode()
     * @generated
     */
    void setCode(String value);

    /**
     * Returns the value of the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Default English form of the error message.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message</em>' attribute.
     * @see #setMessage(String)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getErrorLine_Message()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='message'"
     * @generated
     */
    String getMessage();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.ErrorLine#getMessage <em>Message</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message</em>' attribute.
     * @see #getMessage()
     * @generated
     */
    void setMessage(String value);

} // ErrorLine
