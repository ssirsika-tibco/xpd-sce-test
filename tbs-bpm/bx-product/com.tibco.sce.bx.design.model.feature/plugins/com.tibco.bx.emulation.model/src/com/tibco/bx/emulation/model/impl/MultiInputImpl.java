/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.MultiInput;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Input</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.MultiInputImpl#getResponseMessage <em>Response Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MultiInputImpl extends InputImpl implements MultiInput {
	/**
	 * The default value of the '{@link #getResponseMessage() <em>Response Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String RESPONSE_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResponseMessage() <em>Response Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseMessage()
	 * @generated
	 * @ordered
	 */
	protected String responseMessage = RESPONSE_MESSAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiInputImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.MULTI_INPUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponseMessage(String newResponseMessage) {
		String oldResponseMessage = responseMessage;
		responseMessage = newResponseMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.MULTI_INPUT__RESPONSE_MESSAGE, oldResponseMessage, responseMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.MULTI_INPUT__RESPONSE_MESSAGE:
				return getResponseMessage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmulationPackage.MULTI_INPUT__RESPONSE_MESSAGE:
				setResponseMessage((String)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case EmulationPackage.MULTI_INPUT__RESPONSE_MESSAGE:
				setResponseMessage(RESPONSE_MESSAGE_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EmulationPackage.MULTI_INPUT__RESPONSE_MESSAGE:
				return RESPONSE_MESSAGE_EDEFAULT == null ? responseMessage != null : !RESPONSE_MESSAGE_EDEFAULT.equals(responseMessage);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (responseMessage: "); //$NON-NLS-1$
		result.append(responseMessage);
		result.append(')');
		return result.toString();
	}

} //MultiInputImpl
