/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Resource Patterns</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used to define Activity work allocation strategy
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getAllocationStrategy <em>Allocation Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getPiling <em>Piling</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getWorkItemPriority <em>Work Item Priority</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityResourcePatterns()
 * @model extendedMetaData="name='ActivityResourcePatterns' kind='elementOnly'"
 * @generated
 */
public interface ActivityResourcePatterns extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Allocation Strategy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allocation Strategy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation Strategy</em>' containment reference.
	 * @see #setAllocationStrategy(AllocationStrategy)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityResourcePatterns_AllocationStrategy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AllocationStrategy' namespace='##targetNamespace'"
	 * @generated
	 */
	AllocationStrategy getAllocationStrategy();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getAllocationStrategy <em>Allocation Strategy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation Strategy</em>' containment reference.
	 * @see #getAllocationStrategy()
	 * @generated
	 */
	void setAllocationStrategy(AllocationStrategy value);

	/**
	 * Returns the value of the '<em><b>Piling</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Piling</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Piling</em>' containment reference.
	 * @see #setPiling(PilingInfo)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityResourcePatterns_Piling()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Piling' namespace='##targetNamespace'"
	 * @generated
	 */
	PilingInfo getPiling();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getPiling <em>Piling</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Piling</em>' containment reference.
	 * @see #getPiling()
	 * @generated
	 */
	void setPiling(PilingInfo value);

	/**
	 * Returns the value of the '<em><b>Work Item Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Work Item Priority</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Work Item Priority</em>' containment reference.
	 * @see #setWorkItemPriority(WorkItemPriority)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityResourcePatterns_WorkItemPriority()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='WorkItemPriority' namespace='##targetNamespace'"
	 * @generated
	 */
	WorkItemPriority getWorkItemPriority();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getWorkItemPriority <em>Work Item Priority</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Work Item Priority</em>' containment reference.
	 * @see #getWorkItemPriority()
	 * @generated
	 */
	void setWorkItemPriority(WorkItemPriority value);

} // ActivityResourcePatterns
