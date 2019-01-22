/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>In Out Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.InOutDataType#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.InOutDataType#getSoapMessage <em>Soap Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getInOutDataType()
 * @model
 * @generated
 */
public interface InOutDataType extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getInOutDataType_Parameters()
	 * @model containment="true" keys="id"
	 *        extendedMetaData="kind='element' name='Parameter' wrap='Parameters'"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Soap Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Soap Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Soap Message</em>' attribute.
	 * @see #setSoapMessage(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getInOutDataType_SoapMessage()
	 * @model extendedMetaData="kind='element' name='SoapMessage'"
	 * @generated
	 */
	String getSoapMessage();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.InOutDataType#getSoapMessage <em>Soap Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Soap Message</em>' attribute.
	 * @see #getSoapMessage()
	 * @generated
	 */
	void setSoapMessage(String value);

} // InOutDataType
