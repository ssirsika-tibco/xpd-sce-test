/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.NamedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Retain Familiar Activities</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RetainFamiliarActivities#getActivityRef <em>Activity Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetainFamiliarActivities()
 * @model extendedMetaData="name='RetainFamiliarActivities' kind='elementOnly'"
 * @generated
 */
public interface RetainFamiliarActivities extends NamedElement
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Activity Ref</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.ActivityRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activity Ref</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity Ref</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetainFamiliarActivities_ActivityRef()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ActivityRef' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ActivityRef> getActivityRef();

} // RetainFamiliarActivities
