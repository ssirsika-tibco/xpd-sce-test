/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Information</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.ErrorInformation#getFailedAssertion <em>Failed Assertion</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ErrorInformation#getErrorMessage <em>Error Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getErrorInformation()
 * @model extendedMetaData="name='ErrorInformation_._type' kind='elementOnly'"
 * @generated
 */
public interface ErrorInformation extends EmulationElement {
	/**
	 * Returns the value of the '<em><b>Failed Assertion</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Failed Assertion</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Failed Assertion</em>' reference.
	 * @see #setFailedAssertion(Assertion)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getErrorInformation_FailedAssertion()
	 * @model extendedMetaData="name='FailedAssertion' kind='element'"
	 * @generated
	 */
	Assertion getFailedAssertion();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ErrorInformation#getFailedAssertion <em>Failed Assertion</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Failed Assertion</em>' reference.
	 * @see #getFailedAssertion()
	 * @generated
	 */
	void setFailedAssertion(Assertion value);

	/**
	 * Returns the value of the '<em><b>Error Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error Message</em>' attribute.
	 * @see #setErrorMessage(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getErrorInformation_ErrorMessage()
	 * @model extendedMetaData="name='ErrorMessage' kind='attribute'"
	 * @generated
	 */
	String getErrorMessage();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ErrorInformation#getErrorMessage <em>Error Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error Message</em>' attribute.
	 * @see #getErrorMessage()
	 * @generated
	 */
	void setErrorMessage(String value);

} // ErrorInformation
