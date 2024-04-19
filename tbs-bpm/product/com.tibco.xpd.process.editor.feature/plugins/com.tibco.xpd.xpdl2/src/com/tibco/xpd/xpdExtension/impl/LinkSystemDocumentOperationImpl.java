/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link System Document Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl#getDocumentId <em>Document Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl#getReturnCaseDocRefField <em>Return Case Doc Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl#getCaseRefField <em>Case Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkSystemDocumentOperationImpl extends EObjectImpl implements LinkSystemDocumentOperation
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright							= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getDocumentId() <em>Document Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentId()
	 * @generated
	 * @ordered
	 */
	protected static final String	DOCUMENT_ID_EDEFAULT				= null;

	/**
	 * The cached value of the '{@link #getDocumentId() <em>Document Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentId()
	 * @generated
	 * @ordered
	 */
	protected String				documentId							= DOCUMENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getReturnCaseDocRefField() <em>Return Case Doc Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCaseDocRefField()
	 * @generated
	 * @ordered
	 */
	protected static final String	RETURN_CASE_DOC_REF_FIELD_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getReturnCaseDocRefField() <em>Return Case Doc Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCaseDocRefField()
	 * @generated
	 * @ordered
	 */
	protected String				returnCaseDocRefField				= RETURN_CASE_DOC_REF_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected static final String	CASE_REF_FIELD_EDEFAULT				= null;

	/**
	 * The cached value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected String				caseRefField						= CASE_REF_FIELD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkSystemDocumentOperationImpl()
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
		return XpdExtensionPackage.Literals.LINK_SYSTEM_DOCUMENT_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocumentId()
	{
		return documentId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentId(String newDocumentId)
	{
		String oldDocumentId = documentId;
		documentId = newDocumentId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID, oldDocumentId, documentId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnCaseDocRefField()
	{
		return returnCaseDocRefField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnCaseDocRefField(String newReturnCaseDocRefField)
	{
		String oldReturnCaseDocRefField = returnCaseDocRefField;
		returnCaseDocRefField = newReturnCaseDocRefField;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD, oldReturnCaseDocRefField,
				returnCaseDocRefField));
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
				XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD, oldCaseRefField, caseRefField));
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
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID:
				return getDocumentId();
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD:
				return getReturnCaseDocRefField();
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD:
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
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID:
				setDocumentId((String) newValue);
				return;
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD:
				setReturnCaseDocRefField((String) newValue);
				return;
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD:
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
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID:
				setDocumentId(DOCUMENT_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD:
				setReturnCaseDocRefField(RETURN_CASE_DOC_REF_FIELD_EDEFAULT);
				return;
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD:
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
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID:
				return DOCUMENT_ID_EDEFAULT == null ? documentId != null : !DOCUMENT_ID_EDEFAULT.equals(documentId);
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD:
				return RETURN_CASE_DOC_REF_FIELD_EDEFAULT == null ? returnCaseDocRefField != null
						: !RETURN_CASE_DOC_REF_FIELD_EDEFAULT.equals(returnCaseDocRefField);
			case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD:
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
		result.append(" (documentId: "); //$NON-NLS-1$
		result.append(documentId);
		result.append(", returnCaseDocRefField: "); //$NON-NLS-1$
		result.append(returnCaseDocRefField);
		result.append(", caseRefField: "); //$NON-NLS-1$
		result.append(caseRefField);
		result.append(')');
		return result.toString();
	}

} //LinkSystemDocumentOperationImpl
