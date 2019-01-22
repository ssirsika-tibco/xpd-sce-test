/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Input</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.MultiInput#getResponseMessage <em>Response Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getMultiInput()
 * @model
 * @generated
 */
public interface MultiInput extends Input {
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
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getMultiInput_ResponseMessage()
	 * @model extendedMetaData="kind='element' name='ResponseMessage'"
	 * @generated
	 */
	String getResponseMessage();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.MultiInput#getResponseMessage <em>Response Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response Message</em>' attribute.
	 * @see #getResponseMessage()
	 * @generated
	 */
	void setResponseMessage(String value);

} // MultiInput
