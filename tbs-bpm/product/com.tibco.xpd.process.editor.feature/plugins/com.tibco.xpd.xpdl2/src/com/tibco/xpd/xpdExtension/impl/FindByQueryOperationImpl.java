/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Expression;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Find By Query Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.FindByQueryOperationImpl#getCaseDocumentQueryExpression <em>Case Document Query Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FindByQueryOperationImpl extends EObjectImpl implements FindByQueryOperation
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getCaseDocumentQueryExpression() <em>Case Document Query Expression</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseDocumentQueryExpression()
	 * @generated
	 * @ordered
	 */
	protected EList<CaseDocumentQueryExpression>	caseDocumentQueryExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FindByQueryOperationImpl()
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
		return XpdExtensionPackage.Literals.FIND_BY_QUERY_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CaseDocumentQueryExpression> getCaseDocumentQueryExpression()
	{
		if (caseDocumentQueryExpression == null)
		{
			caseDocumentQueryExpression = new EObjectContainmentEList<CaseDocumentQueryExpression>(
					CaseDocumentQueryExpression.class, this,
					XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION);
		}
		return caseDocumentQueryExpression;
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
			case XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION:
				return ((InternalEList< ? >) getCaseDocumentQueryExpression()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION:
				return getCaseDocumentQueryExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION:
				getCaseDocumentQueryExpression().clear();
				getCaseDocumentQueryExpression().addAll((Collection< ? extends CaseDocumentQueryExpression>) newValue);
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
			case XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION:
				getCaseDocumentQueryExpression().clear();
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
			case XpdExtensionPackage.FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION:
				return caseDocumentQueryExpression != null && !caseDocumentQueryExpression.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FindByQueryOperationImpl
