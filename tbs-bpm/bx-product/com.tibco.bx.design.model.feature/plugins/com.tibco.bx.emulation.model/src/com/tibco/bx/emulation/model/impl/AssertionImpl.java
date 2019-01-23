/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assertion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.AssertionImpl#getProcessNode <em>Process Node</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.AssertionImpl#isAccessible <em>Accessible</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssertionImpl extends NamedElementImpl implements Assertion {
	/**
	 * The default value of the '{@link #isAccessible() <em>Accessible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAccessible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACCESSIBLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAccessible() <em>Accessible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAccessible()
	 * @generated
	 * @ordered
	 */
	protected boolean accessible = ACCESSIBLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssertionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.ASSERTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNode getProcessNode() {
		if (eContainerFeatureID != EmulationPackage.ASSERTION__PROCESS_NODE) return null;
		return (ProcessNode)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessNode(ProcessNode newProcessNode, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProcessNode, EmulationPackage.ASSERTION__PROCESS_NODE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessNode(ProcessNode newProcessNode) {
		if (newProcessNode != eInternalContainer() || (eContainerFeatureID != EmulationPackage.ASSERTION__PROCESS_NODE && newProcessNode != null)) {
			if (EcoreUtil.isAncestor(this, newProcessNode))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProcessNode != null)
				msgs = ((InternalEObject)newProcessNode).eInverseAdd(this, EmulationPackage.PROCESS_NODE__ASSERTIONS, ProcessNode.class, msgs);
			msgs = basicSetProcessNode(newProcessNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.ASSERTION__PROCESS_NODE, newProcessNode, newProcessNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAccessible() {
		return accessible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessible(boolean newAccessible) {
		boolean oldAccessible = accessible;
		accessible = newAccessible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.ASSERTION__ACCESSIBLE, oldAccessible, accessible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProcessNode((ProcessNode)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				return basicSetProcessNode(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				return eInternalContainer().eInverseRemove(this, EmulationPackage.PROCESS_NODE__ASSERTIONS, ProcessNode.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				return getProcessNode();
			case EmulationPackage.ASSERTION__ACCESSIBLE:
				return isAccessible() ? Boolean.TRUE : Boolean.FALSE;
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
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				setProcessNode((ProcessNode)newValue);
				return;
			case EmulationPackage.ASSERTION__ACCESSIBLE:
				setAccessible(((Boolean)newValue).booleanValue());
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
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				setProcessNode((ProcessNode)null);
				return;
			case EmulationPackage.ASSERTION__ACCESSIBLE:
				setAccessible(ACCESSIBLE_EDEFAULT);
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
			case EmulationPackage.ASSERTION__PROCESS_NODE:
				return getProcessNode() != null;
			case EmulationPackage.ASSERTION__ACCESSIBLE:
				return accessible != ACCESSIBLE_EDEFAULT;
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
		result.append(" (accessible: "); //$NON-NLS-1$
		result.append(accessible);
		result.append(')');
		return result.toString();
	}

} //AssertionImpl
