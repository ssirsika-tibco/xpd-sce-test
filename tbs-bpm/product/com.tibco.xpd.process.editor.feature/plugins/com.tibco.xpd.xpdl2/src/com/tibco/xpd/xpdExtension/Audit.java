/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Audit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Auditing to perform on activity.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.Audit#getAuditEvent <em>Audit Event</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.Audit#getAny <em>Any</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAudit()
 * @model extendedMetaData="name='Audit_._type' kind='elementOnly'"
 * @generated
 */
public interface Audit extends EObject
{

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Audit Event</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.AuditEvent}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Audit to perform for particular events that happen to activity.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Audit Event</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAudit_AuditEvent()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AuditEvent' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AuditEvent> getAuditEvent();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAudit_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':1' processing='lax'"
	 * @generated
	 */
	FeatureMap getAny();

} // Audit