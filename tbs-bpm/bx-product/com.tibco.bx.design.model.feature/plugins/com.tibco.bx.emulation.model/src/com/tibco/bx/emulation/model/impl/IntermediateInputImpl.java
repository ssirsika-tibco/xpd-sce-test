/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.ProcessNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Intermediate Input</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.IntermediateInputImpl#getRequestMessage <em>Request Message</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.IntermediateInputImpl#getResponseMessage <em>Response Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IntermediateInputImpl extends NamedElementImpl implements IntermediateInput {
	/**
	 * The default value of the '{@link #getRequestMessage() <em>Request Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String REQUEST_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRequestMessage() <em>Request Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestMessage()
	 * @generated
	 * @ordered
	 */
	protected String requestMessage = REQUEST_MESSAGE_EDEFAULT;

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
	protected IntermediateInputImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.INTERMEDIATE_INPUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRequestMessage() {
		return requestMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestMessage(String newRequestMessage) {
		String oldRequestMessage = requestMessage;
		requestMessage = newRequestMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.INTERMEDIATE_INPUT__REQUEST_MESSAGE, oldRequestMessage, requestMessage));
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
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.INTERMEDIATE_INPUT__RESPONSE_MESSAGE, oldResponseMessage, responseMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.INTERMEDIATE_INPUT__REQUEST_MESSAGE:
				return getRequestMessage();
			case EmulationPackage.INTERMEDIATE_INPUT__RESPONSE_MESSAGE:
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
			case EmulationPackage.INTERMEDIATE_INPUT__REQUEST_MESSAGE:
				setRequestMessage((String)newValue);
				return;
			case EmulationPackage.INTERMEDIATE_INPUT__RESPONSE_MESSAGE:
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
			case EmulationPackage.INTERMEDIATE_INPUT__REQUEST_MESSAGE:
				setRequestMessage(REQUEST_MESSAGE_EDEFAULT);
				return;
			case EmulationPackage.INTERMEDIATE_INPUT__RESPONSE_MESSAGE:
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
			case EmulationPackage.INTERMEDIATE_INPUT__REQUEST_MESSAGE:
				return REQUEST_MESSAGE_EDEFAULT == null ? requestMessage != null : !REQUEST_MESSAGE_EDEFAULT.equals(requestMessage);
			case EmulationPackage.INTERMEDIATE_INPUT__RESPONSE_MESSAGE:
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
		result.append(" (requestMessage: "); //$NON-NLS-1$
		result.append(requestMessage);
		result.append(", responseMessage: "); //$NON-NLS-1$
		result.append(responseMessage);
		result.append(')');
		return result.toString();
	}

} //IntermediateInputImpl
