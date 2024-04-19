/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Expression;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enablement Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.EnablementType#getInitializerActivities <em>Initializer Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.EnablementType#getPreconditionExpression <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEnablementType()
 * @model extendedMetaData="name='Enablement_._type' kind='elementOnly'"
 * @generated
 */
public interface EnablementType extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Initializer Activities</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Activities that must be completed prior to enablement of ad-hoc activity.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Initializer Activities</em>' containment reference.
	 * @see #setInitializerActivities(InitializerActivitiesType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEnablementType_InitializerActivities()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='InitializerActivities' namespace='##targetNamespace'"
	 * @generated
	 */
	InitializerActivitiesType getInitializerActivities();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.EnablementType#getInitializerActivities <em>Initializer Activities</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initializer Activities</em>' containment reference.
	 * @see #getInitializerActivities()
	 * @generated
	 */
	void setInitializerActivities(InitializerActivitiesType value);

	/**
	 * Returns the value of the '<em><b>Precondition Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Boolean precondition that must be completed prior to enablement of ad-hoc activity.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Precondition Expression</em>' containment reference.
	 * @see #setPreconditionExpression(Expression)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEnablementType_PreconditionExpression()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='PreconditionExpression' namespace='##targetNamespace'"
	 * @generated
	 */
	Expression getPreconditionExpression();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.EnablementType#getPreconditionExpression <em>Precondition Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition Expression</em>' containment reference.
	 * @see #getPreconditionExpression()
	 * @generated
	 */
	void setPreconditionExpression(Expression value);

} // EnablementType
