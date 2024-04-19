/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Resource Patterns</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used to define process work allocation strategy.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getSeparationOfDutiesActivities <em>Separation Of Duties Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getRetainFamiliarActivities <em>Retain Familiar Activities</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessResourcePatterns()
 * @model extendedMetaData="name='ProcessResourcePatterns' kind='elementOnly'"
 * @generated
 */
public interface ProcessResourcePatterns extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Separation Of Duties Activities</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Separation Of Duties Activities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Separation Of Duties Activities</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessResourcePatterns_SeparationOfDutiesActivities()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SeparationOfDutiesActivities' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SeparationOfDutiesActivities> getSeparationOfDutiesActivities();

	/**
	 * Returns the value of the '<em><b>Retain Familiar Activities</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.RetainFamiliarActivities}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retain Familiar Activities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retain Familiar Activities</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessResourcePatterns_RetainFamiliarActivities()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='RetainFamiliarActivities' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<RetainFamiliarActivities> getRetainFamiliarActivities();

} // ProcessResourcePatterns
