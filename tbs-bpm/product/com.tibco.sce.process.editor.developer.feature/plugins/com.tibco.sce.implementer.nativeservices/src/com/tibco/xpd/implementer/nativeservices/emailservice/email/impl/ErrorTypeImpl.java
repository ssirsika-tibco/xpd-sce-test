/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl#getReturnCode <em>Return Code</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl#getReturnMessage <em>Return Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorTypeImpl extends EObjectImpl implements ErrorType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getReturnCode() <em>Return Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCode()
	 * @generated
	 * @ordered
	 */
	protected static final String RETURN_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnCode() <em>Return Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnCode()
	 * @generated
	 * @ordered
	 */
	protected String returnCode = RETURN_CODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getReturnMessage() <em>Return Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String RETURN_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnMessage() <em>Return Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnMessage()
	 * @generated
	 * @ordered
	 */
	protected String returnMessage = RETURN_MESSAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmailPackage.Literals.ERROR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnCode() {
		return returnCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnCode(String newReturnCode) {
		String oldReturnCode = returnCode;
		returnCode = newReturnCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.ERROR_TYPE__RETURN_CODE, oldReturnCode,
					returnCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnMessage() {
		return returnMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnMessage(String newReturnMessage) {
		String oldReturnMessage = returnMessage;
		returnMessage = newReturnMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.ERROR_TYPE__RETURN_MESSAGE, oldReturnMessage,
					returnMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EmailPackage.ERROR_TYPE__RETURN_CODE:
			return getReturnCode();
		case EmailPackage.ERROR_TYPE__RETURN_MESSAGE:
			return getReturnMessage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EmailPackage.ERROR_TYPE__RETURN_CODE:
			setReturnCode((String) newValue);
			return;
		case EmailPackage.ERROR_TYPE__RETURN_MESSAGE:
			setReturnMessage((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
		case EmailPackage.ERROR_TYPE__RETURN_CODE:
			setReturnCode(RETURN_CODE_EDEFAULT);
			return;
		case EmailPackage.ERROR_TYPE__RETURN_MESSAGE:
			setReturnMessage(RETURN_MESSAGE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EmailPackage.ERROR_TYPE__RETURN_CODE:
			return RETURN_CODE_EDEFAULT == null ? returnCode != null
					: !RETURN_CODE_EDEFAULT.equals(returnCode);
		case EmailPackage.ERROR_TYPE__RETURN_MESSAGE:
			return RETURN_MESSAGE_EDEFAULT == null ? returnMessage != null
					: !RETURN_MESSAGE_EDEFAULT.equals(returnMessage);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (returnCode: "); //$NON-NLS-1$
		result.append(returnCode);
		result.append(", returnMessage: "); //$NON-NLS-1$
		result.append(returnMessage);
		result.append(')');
		return result.toString();
	}

} //ErrorTypeImpl