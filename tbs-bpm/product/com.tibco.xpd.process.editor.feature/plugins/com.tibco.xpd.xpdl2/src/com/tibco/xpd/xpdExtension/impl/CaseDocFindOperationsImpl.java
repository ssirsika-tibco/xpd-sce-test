/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Doc Find Operations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl#getFindByFileNameOperation <em>Find By File Name Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl#getFindByQueryOperation <em>Find By Query Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl#getReturnCaseDocRefsField <em>Return Case Doc Refs Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl#getCaseRefField <em>Case Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseDocFindOperationsImpl extends EObjectImpl implements CaseDocFindOperations
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright							= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getFindByFileNameOperation() <em>Find By File Name Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFindByFileNameOperation()
	 * @generated
	 * @ordered
	 */
	protected FindByFileNameOperation	findByFileNameOperation;

	/**
	 * The cached value of the '{@link #getFindByQueryOperation() <em>Find By Query Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFindByQueryOperation()
	 * @generated
	 * @ordered
	 */
	protected FindByQueryOperation		findByQueryOperation;

	/**
	 * The default value of the '{@link #getReturnCaseDocRefsField() <em>Return Case Doc Refs Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCaseDocRefsField()
	 * @generated
	 * @ordered
	 */
	protected static final String		RETURN_CASE_DOC_REFS_FIELD_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getReturnCaseDocRefsField() <em>Return Case Doc Refs Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCaseDocRefsField()
	 * @generated
	 * @ordered
	 */
	protected String					returnCaseDocRefsField				= RETURN_CASE_DOC_REFS_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected static final String		CASE_REF_FIELD_EDEFAULT				= null;

	/**
	 * The cached value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected String					caseRefField						= CASE_REF_FIELD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CaseDocFindOperationsImpl()
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
		return XpdExtensionPackage.Literals.CASE_DOC_FIND_OPERATIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FindByFileNameOperation getFindByFileNameOperation()
	{
		return findByFileNameOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFindByFileNameOperation(FindByFileNameOperation newFindByFileNameOperation,
			NotificationChain msgs)
	{
		FindByFileNameOperation oldFindByFileNameOperation = findByFileNameOperation;
		findByFileNameOperation = newFindByFileNameOperation;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION,
					oldFindByFileNameOperation, newFindByFileNameOperation);
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
	public void setFindByFileNameOperation(FindByFileNameOperation newFindByFileNameOperation)
	{
		if (newFindByFileNameOperation != findByFileNameOperation)
		{
			NotificationChain msgs = null;
			if (findByFileNameOperation != null) msgs = ((InternalEObject) findByFileNameOperation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION,
					null, msgs);
			if (newFindByFileNameOperation != null)
				msgs = ((InternalEObject) newFindByFileNameOperation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION,
						null, msgs);
			msgs = basicSetFindByFileNameOperation(newFindByFileNameOperation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION, newFindByFileNameOperation,
				newFindByFileNameOperation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FindByQueryOperation getFindByQueryOperation()
	{
		return findByQueryOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFindByQueryOperation(FindByQueryOperation newFindByQueryOperation,
			NotificationChain msgs)
	{
		FindByQueryOperation oldFindByQueryOperation = findByQueryOperation;
		findByQueryOperation = newFindByQueryOperation;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION, oldFindByQueryOperation,
					newFindByQueryOperation);
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
	public void setFindByQueryOperation(FindByQueryOperation newFindByQueryOperation)
	{
		if (newFindByQueryOperation != findByQueryOperation)
		{
			NotificationChain msgs = null;
			if (findByQueryOperation != null) msgs = ((InternalEObject) findByQueryOperation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION,
					null, msgs);
			if (newFindByQueryOperation != null) msgs = ((InternalEObject) newFindByQueryOperation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION,
					null, msgs);
			msgs = basicSetFindByQueryOperation(newFindByQueryOperation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION, newFindByQueryOperation,
				newFindByQueryOperation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnCaseDocRefsField()
	{
		return returnCaseDocRefsField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnCaseDocRefsField(String newReturnCaseDocRefsField)
	{
		String oldReturnCaseDocRefsField = returnCaseDocRefsField;
		returnCaseDocRefsField = newReturnCaseDocRefsField;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD, oldReturnCaseDocRefsField,
				returnCaseDocRefsField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCaseRefField()
	{
		return caseRefField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCaseRefField(String newCaseRefField)
	{
		String oldCaseRefField = caseRefField;
		caseRefField = newCaseRefField;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD, oldCaseRefField, caseRefField));
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
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION:
				return basicSetFindByFileNameOperation(null, msgs);
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION:
				return basicSetFindByQueryOperation(null, msgs);
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
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION:
				return getFindByFileNameOperation();
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION:
				return getFindByQueryOperation();
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD:
				return getReturnCaseDocRefsField();
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD:
				return getCaseRefField();
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
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION:
				setFindByFileNameOperation((FindByFileNameOperation) newValue);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION:
				setFindByQueryOperation((FindByQueryOperation) newValue);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD:
				setReturnCaseDocRefsField((String) newValue);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD:
				setCaseRefField((String) newValue);
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
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION:
				setFindByFileNameOperation((FindByFileNameOperation) null);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION:
				setFindByQueryOperation((FindByQueryOperation) null);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD:
				setReturnCaseDocRefsField(RETURN_CASE_DOC_REFS_FIELD_EDEFAULT);
				return;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD:
				setCaseRefField(CASE_REF_FIELD_EDEFAULT);
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
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION:
				return findByFileNameOperation != null;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION:
				return findByQueryOperation != null;
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD:
				return RETURN_CASE_DOC_REFS_FIELD_EDEFAULT == null ? returnCaseDocRefsField != null
						: !RETURN_CASE_DOC_REFS_FIELD_EDEFAULT.equals(returnCaseDocRefsField);
			case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD:
				return CASE_REF_FIELD_EDEFAULT == null ? caseRefField != null
						: !CASE_REF_FIELD_EDEFAULT.equals(caseRefField);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (returnCaseDocRefsField: "); //$NON-NLS-1$
		result.append(returnCaseDocRefsField);
		result.append(", caseRefField: "); //$NON-NLS-1$
		result.append(caseRefField);
		result.append(')');
		return result.toString();
	}

} //CaseDocFindOperationsImpl
