/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Data Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl#getCaseAccessOperations <em>Case Access Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl#getCaseReferenceOperations <em>Case Reference Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GlobalDataOperationImpl extends EObjectImpl implements GlobalDataOperation
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getCaseAccessOperations() <em>Case Access Operations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseAccessOperations()
	 * @generated
	 * @ordered
	 */
	protected CaseAccessOperationsType		caseAccessOperations;

	/**
	 * The cached value of the '{@link #getCaseReferenceOperations() <em>Case Reference Operations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseReferenceOperations()
	 * @generated
	 * @ordered
	 */
	protected CaseReferenceOperationsType	caseReferenceOperations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalDataOperationImpl()
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
		return XpdExtensionPackage.Literals.GLOBAL_DATA_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CaseAccessOperationsType getCaseAccessOperations()
	{
		return caseAccessOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCaseAccessOperations(CaseAccessOperationsType newCaseAccessOperations,
			NotificationChain msgs)
	{
		CaseAccessOperationsType oldCaseAccessOperations = caseAccessOperations;
		caseAccessOperations = newCaseAccessOperations;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS, oldCaseAccessOperations,
					newCaseAccessOperations);
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
	public void setCaseAccessOperations(CaseAccessOperationsType newCaseAccessOperations)
	{
		if (newCaseAccessOperations != caseAccessOperations)
		{
			NotificationChain msgs = null;
			if (caseAccessOperations != null) msgs = ((InternalEObject) caseAccessOperations).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS, null,
					msgs);
			if (newCaseAccessOperations != null) msgs = ((InternalEObject) newCaseAccessOperations).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS, null,
					msgs);
			msgs = basicSetCaseAccessOperations(newCaseAccessOperations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS, newCaseAccessOperations,
				newCaseAccessOperations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CaseReferenceOperationsType getCaseReferenceOperations()
	{
		return caseReferenceOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCaseReferenceOperations(CaseReferenceOperationsType newCaseReferenceOperations,
			NotificationChain msgs)
	{
		CaseReferenceOperationsType oldCaseReferenceOperations = caseReferenceOperations;
		caseReferenceOperations = newCaseReferenceOperations;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS, oldCaseReferenceOperations,
					newCaseReferenceOperations);
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
	public void setCaseReferenceOperations(CaseReferenceOperationsType newCaseReferenceOperations)
	{
		if (newCaseReferenceOperations != caseReferenceOperations)
		{
			NotificationChain msgs = null;
			if (caseReferenceOperations != null) msgs = ((InternalEObject) caseReferenceOperations).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS, null,
					msgs);
			if (newCaseReferenceOperations != null)
				msgs = ((InternalEObject) newCaseReferenceOperations).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS,
						null, msgs);
			msgs = basicSetCaseReferenceOperations(newCaseReferenceOperations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS, newCaseReferenceOperations,
				newCaseReferenceOperations));
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
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS:
				return basicSetCaseAccessOperations(null, msgs);
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS:
				return basicSetCaseReferenceOperations(null, msgs);
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
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS:
				return getCaseAccessOperations();
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS:
				return getCaseReferenceOperations();
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
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS:
				setCaseAccessOperations((CaseAccessOperationsType) newValue);
				return;
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS:
				setCaseReferenceOperations((CaseReferenceOperationsType) newValue);
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
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS:
				setCaseAccessOperations((CaseAccessOperationsType) null);
				return;
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS:
				setCaseReferenceOperations((CaseReferenceOperationsType) null);
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
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS:
				return caseAccessOperations != null;
			case XpdExtensionPackage.GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS:
				return caseReferenceOperations != null;
		}
		return super.eIsSet(featureID);
	}

} //GlobalDataOperationImpl
