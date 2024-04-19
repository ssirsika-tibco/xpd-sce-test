/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Expression;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enablement Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl#getInitializerActivities <em>Initializer Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl#getPreconditionExpression <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnablementTypeImpl extends EObjectImpl implements EnablementType
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInitializerActivities() <em>Initializer Activities</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializerActivities()
	 * @generated
	 * @ordered
	 */
	protected InitializerActivitiesType	initializerActivities;

	/**
	 * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreconditionExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression				preconditionExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnablementTypeImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.ENABLEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitializerActivitiesType getInitializerActivities()
	{
		return initializerActivities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitializerActivities(InitializerActivitiesType newInitializerActivities,
			NotificationChain msgs)
	{
		InitializerActivitiesType oldInitializerActivities = initializerActivities;
		initializerActivities = newInitializerActivities;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES, oldInitializerActivities,
					newInitializerActivities);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitializerActivities(InitializerActivitiesType newInitializerActivities)
	{
		if (newInitializerActivities != initializerActivities)
		{
			NotificationChain msgs = null;
			if (initializerActivities != null) msgs = ((InternalEObject) initializerActivities).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES, null, msgs);
			if (newInitializerActivities != null) msgs = ((InternalEObject) newInitializerActivities).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES, null, msgs);
			msgs = basicSetInitializerActivities(newInitializerActivities, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES, newInitializerActivities,
				newInitializerActivities));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getPreconditionExpression()
	{
		return preconditionExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreconditionExpression(Expression newPreconditionExpression,
			NotificationChain msgs)
	{
		Expression oldPreconditionExpression = preconditionExpression;
		preconditionExpression = newPreconditionExpression;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION, oldPreconditionExpression,
					newPreconditionExpression);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreconditionExpression(Expression newPreconditionExpression)
	{
		if (newPreconditionExpression != preconditionExpression)
		{
			NotificationChain msgs = null;
			if (preconditionExpression != null) msgs = ((InternalEObject) preconditionExpression).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION, null, msgs);
			if (newPreconditionExpression != null)
				msgs = ((InternalEObject) newPreconditionExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION, null,
						msgs);
			msgs = basicSetPreconditionExpression(newPreconditionExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION, newPreconditionExpression,
				newPreconditionExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES:
				return basicSetInitializerActivities(null, msgs);
			case XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION:
				return basicSetPreconditionExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES:
				return getInitializerActivities();
			case XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES:
				setInitializerActivities((InitializerActivitiesType) newValue);
				return;
			case XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION:
				setPreconditionExpression((Expression) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES:
				setInitializerActivities((InitializerActivitiesType) null);
				return;
			case XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION:
				setPreconditionExpression((Expression) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES:
				return initializerActivities != null;
			case XpdExtensionPackage.ENABLEMENT_TYPE__PRECONDITION_EXPRESSION:
				return preconditionExpression != null;
		}
		return super.eIsSet(featureID);
	}

} //EnablementTypeImpl
