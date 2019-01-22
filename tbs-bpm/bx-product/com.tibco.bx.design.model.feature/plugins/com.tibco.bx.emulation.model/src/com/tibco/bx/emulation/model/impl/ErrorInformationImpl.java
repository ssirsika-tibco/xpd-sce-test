/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ErrorInformation;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Information</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ErrorInformationImpl#getFailedAssertion <em>Failed Assertion</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ErrorInformationImpl#getErrorMessage <em>Error Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorInformationImpl extends EmulationElementImpl implements ErrorInformation {
	/**
	 * The cached value of the '{@link #getFailedAssertion() <em>Failed Assertion</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFailedAssertion()
	 * @generated
	 * @ordered
	 */
	protected Assertion failedAssertion;

	/**
	 * The default value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered
	 */
	protected String errorMessage = ERROR_MESSAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorInformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.ERROR_INFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assertion getFailedAssertion() {
		if (failedAssertion != null && failedAssertion.eIsProxy()) {
			InternalEObject oldFailedAssertion = (InternalEObject)failedAssertion;
			failedAssertion = (Assertion)eResolveProxy(oldFailedAssertion);
			if (failedAssertion != oldFailedAssertion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION, oldFailedAssertion, failedAssertion));
			}
		}
		return failedAssertion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assertion basicGetFailedAssertion() {
		return failedAssertion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFailedAssertion(Assertion newFailedAssertion) {
		Assertion oldFailedAssertion = failedAssertion;
		failedAssertion = newFailedAssertion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION, oldFailedAssertion, failedAssertion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorMessage(String newErrorMessage) {
		String oldErrorMessage = errorMessage;
		errorMessage = newErrorMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.ERROR_INFORMATION__ERROR_MESSAGE, oldErrorMessage, errorMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION:
				if (resolve) return getFailedAssertion();
				return basicGetFailedAssertion();
			case EmulationPackage.ERROR_INFORMATION__ERROR_MESSAGE:
				return getErrorMessage();
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
			case EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION:
				setFailedAssertion((Assertion)newValue);
				return;
			case EmulationPackage.ERROR_INFORMATION__ERROR_MESSAGE:
				setErrorMessage((String)newValue);
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
			case EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION:
				setFailedAssertion((Assertion)null);
				return;
			case EmulationPackage.ERROR_INFORMATION__ERROR_MESSAGE:
				setErrorMessage(ERROR_MESSAGE_EDEFAULT);
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
			case EmulationPackage.ERROR_INFORMATION__FAILED_ASSERTION:
				return failedAssertion != null;
			case EmulationPackage.ERROR_INFORMATION__ERROR_MESSAGE:
				return ERROR_MESSAGE_EDEFAULT == null ? errorMessage != null : !ERROR_MESSAGE_EDEFAULT.equals(errorMessage);
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
		result.append(" (errorMessage: "); //$NON-NLS-1$
		result.append(errorMessage);
		result.append(')');
		return result.toString();
	}

} //ErrorInformationImpl
