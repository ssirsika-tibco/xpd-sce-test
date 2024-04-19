/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initializer Activities Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.InitializerActivitiesType#getActivityRef <em>Activity Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInitializerActivitiesType()
 * @model extendedMetaData="name='InitializerActivities_._type' key='elementOnly'"
 * @generated
 */
public interface InitializerActivitiesType extends EObject
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
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Activity reference (by acitivity Id)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Activity Ref</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInitializerActivitiesType_ActivityRef()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ActivityRef' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ActivityRef> getActivityRef();

} // InitializerActivitiesType
