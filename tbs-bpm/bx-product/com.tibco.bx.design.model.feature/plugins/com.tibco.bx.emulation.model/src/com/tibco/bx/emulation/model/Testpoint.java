/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Testpoint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.Testpoint#getProcessNode <em>Process Node</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.Testpoint#getLang <em>Lang</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.Testpoint#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getTestpoint()
 * @model extendedMetaData="name='Testpoint_._type' kind='elementOnly'"
 * @generated
 */
public interface Testpoint extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Process Node</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.tibco.bx.emulation.model.ProcessNode#getTestpoints <em>Testpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Node</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Node</em>' container reference.
	 * @see #setProcessNode(ProcessNode)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getTestpoint_ProcessNode()
	 * @see com.tibco.bx.emulation.model.ProcessNode#getTestpoints
	 * @model opposite="testpoints" resolveProxies="false"
	 * @generated
	 */
	ProcessNode getProcessNode();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.Testpoint#getProcessNode <em>Process Node</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Node</em>' container reference.
	 * @see #getProcessNode()
	 * @generated
	 */
	void setProcessNode(ProcessNode value);

	/**
	 * Returns the value of the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lang</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lang</em>' attribute.
	 * @see #setLang(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getTestpoint_Lang()
	 * @model extendedMetaData="name='Lang' kind='element'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.Testpoint#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getTestpoint_Expression()
	 * @model extendedMetaData="name='Expression' kind='element'"
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.Testpoint#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

} // Testpoint
