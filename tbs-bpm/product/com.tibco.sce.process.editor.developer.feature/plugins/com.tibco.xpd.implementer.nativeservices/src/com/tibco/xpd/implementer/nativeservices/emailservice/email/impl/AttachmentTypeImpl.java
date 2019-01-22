/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attachment Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl#getFiles <em>Files</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttachmentTypeImpl extends EObjectImpl implements AttachmentType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFiles() <em>Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiles()
	 * @generated
	 * @ordered
	 */
	protected FilesType files = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttachmentTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmailPackage.Literals.ATTACHMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.ATTACHMENT_TYPE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilesType getFiles() {
		return files;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFiles(FilesType newFiles,
			NotificationChain msgs) {
		FilesType oldFiles = files;
		files = newFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.ATTACHMENT_TYPE__FILES,
					oldFiles, newFiles);
			if (msgs == null)
				msgs = notification;
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
	public void setFiles(FilesType newFiles) {
		if (newFiles != files) {
			NotificationChain msgs = null;
			if (files != null)
				msgs = ((InternalEObject) files).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.ATTACHMENT_TYPE__FILES, null,
						msgs);
			if (newFiles != null)
				msgs = ((InternalEObject) newFiles).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.ATTACHMENT_TYPE__FILES, null,
						msgs);
			msgs = basicSetFiles(newFiles, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.ATTACHMENT_TYPE__FILES, newFiles, newFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EmailPackage.ATTACHMENT_TYPE__FILES:
			return basicSetFiles(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EmailPackage.ATTACHMENT_TYPE__VALUE:
			return getValue();
		case EmailPackage.ATTACHMENT_TYPE__FILES:
			return getFiles();
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
		case EmailPackage.ATTACHMENT_TYPE__VALUE:
			setValue((String) newValue);
			return;
		case EmailPackage.ATTACHMENT_TYPE__FILES:
			setFiles((FilesType) newValue);
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
		case EmailPackage.ATTACHMENT_TYPE__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		case EmailPackage.ATTACHMENT_TYPE__FILES:
			setFiles((FilesType) null);
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
		case EmailPackage.ATTACHMENT_TYPE__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT
					.equals(value);
		case EmailPackage.ATTACHMENT_TYPE__FILES:
			return files != null;
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
		result.append(" (value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //AttachmentTypeImpl