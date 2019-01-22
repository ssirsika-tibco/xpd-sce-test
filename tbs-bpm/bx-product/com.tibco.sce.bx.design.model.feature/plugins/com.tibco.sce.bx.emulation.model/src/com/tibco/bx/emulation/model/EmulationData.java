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
 * A representation of the model object '<em><b>Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.EmulationData#getProcessNodes <em>Process Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getEmulationData()
 * @model extendedMetaData="name='EmulationData' kind='elementOnly'"
 * @generated
 */
public interface EmulationData extends EObject {
	/**
	 * Returns the value of the '<em><b>Process Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.ProcessNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Nodes</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getEmulationData_ProcessNodes()
	 * @model containment="true" keys="id"
	 *        extendedMetaData="name='ProcessNode' kind='element' wrap='ProcessNodes'"
	 * @generated
	 */
	EList<ProcessNode> getProcessNodes();

} // EmulationData
