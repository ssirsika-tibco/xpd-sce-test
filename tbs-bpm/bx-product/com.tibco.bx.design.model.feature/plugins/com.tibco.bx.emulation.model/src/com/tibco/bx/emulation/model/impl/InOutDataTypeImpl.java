/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Parameter;

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
 * An implementation of the model object '<em><b>In Out Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.InOutDataTypeImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.InOutDataTypeImpl#getSoapMessage <em>Soap Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InOutDataTypeImpl extends NamedElementImpl implements InOutDataType {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The default value of the '{@link #getSoapMessage() <em>Soap Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSoapMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String SOAP_MESSAGE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSoapMessage() <em>Soap Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSoapMessage()
	 * @generated
	 * @ordered
	 */
	protected String soapMessage = SOAP_MESSAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InOutDataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.IN_OUT_DATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSoapMessage() {
		return soapMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSoapMessage(String newSoapMessage) {
		String oldSoapMessage = soapMessage;
		soapMessage = newSoapMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.IN_OUT_DATA_TYPE__SOAP_MESSAGE, oldSoapMessage, soapMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS:
				return getParameters();
			case EmulationPackage.IN_OUT_DATA_TYPE__SOAP_MESSAGE:
				return getSoapMessage();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case EmulationPackage.IN_OUT_DATA_TYPE__SOAP_MESSAGE:
				setSoapMessage((String)newValue);
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
			case EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS:
				getParameters().clear();
				return;
			case EmulationPackage.IN_OUT_DATA_TYPE__SOAP_MESSAGE:
				setSoapMessage(SOAP_MESSAGE_EDEFAULT);
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
			case EmulationPackage.IN_OUT_DATA_TYPE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case EmulationPackage.IN_OUT_DATA_TYPE__SOAP_MESSAGE:
				return SOAP_MESSAGE_EDEFAULT == null ? soapMessage != null : !SOAP_MESSAGE_EDEFAULT.equals(soapMessage);
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
		result.append(" (soapMessage: "); //$NON-NLS-1$
		result.append(soapMessage);
		result.append(')');
		return result.toString();
	}

} //InOutDataTypeImpl
