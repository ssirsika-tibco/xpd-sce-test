/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Intermediate Input</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.IntermediateInput#getRequestMessage <em>Request Message</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.IntermediateInput#getResponseMessage <em>Response Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getIntermediateInput()
 * @model extendedMetaData="name='IntermediateInput_._type' kind='elementOnly'"
 * @generated
 */
public interface IntermediateInput extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Request Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Request Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Request Message</em>' attribute.
	 * @see #setRequestMessage(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getIntermediateInput_RequestMessage()
	 * @model extendedMetaData="kind='element' name='RequestMessage'"
	 * @generated
	 */
	String getRequestMessage();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.IntermediateInput#getRequestMessage <em>Request Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Request Message</em>' attribute.
	 * @see #getRequestMessage()
	 * @generated
	 */
	void setRequestMessage(String value);

	/**
	 * Returns the value of the '<em><b>Response Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response Message</em>' attribute.
	 * @see #setResponseMessage(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getIntermediateInput_ResponseMessage()
	 * @model extendedMetaData="kind='element' name='ResponseMessage'"
	 * @generated
	 */
	String getResponseMessage();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.IntermediateInput#getResponseMessage <em>Response Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response Message</em>' attribute.
	 * @see #getResponseMessage()
	 * @generated
	 */
	void setResponseMessage(String value);

} // IntermediateInput
