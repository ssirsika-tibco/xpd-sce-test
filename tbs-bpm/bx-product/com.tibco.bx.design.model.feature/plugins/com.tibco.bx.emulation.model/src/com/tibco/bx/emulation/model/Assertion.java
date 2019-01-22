/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assertion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.Assertion#getProcessNode <em>Process Node</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.Assertion#isAccessible <em>Accessible</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getAssertion()
 * @model extendedMetaData="name='Assertion_._type' kind='elementOnly'"
 * @generated
 */
public interface Assertion extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Process Node</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.tibco.bx.emulation.model.ProcessNode#getAssertions <em>Assertions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Node</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Node</em>' container reference.
	 * @see #setProcessNode(ProcessNode)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getAssertion_ProcessNode()
	 * @see com.tibco.bx.emulation.model.ProcessNode#getAssertions
	 * @model opposite="assertions" resolveProxies="false"
	 * @generated
	 */
	ProcessNode getProcessNode();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.Assertion#getProcessNode <em>Process Node</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Node</em>' container reference.
	 * @see #getProcessNode()
	 * @generated
	 */
	void setProcessNode(ProcessNode value);

	/**
	 * Returns the value of the '<em><b>Accessible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accessible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accessible</em>' attribute.
	 * @see #setAccessible(boolean)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getAssertion_Accessible()
	 * @model extendedMetaData="name='Accessible' kind='element'"
	 * @generated
	 */
	boolean isAccessible();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.Assertion#isAccessible <em>Accessible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accessible</em>' attribute.
	 * @see #isAccessible()
	 * @generated
	 */
	void setAccessible(boolean value);

} // Assertion
