/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Definition Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getCc <em>Cc</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getBcc <em>Bcc</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getReplyTo <em>Reply To</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl#getSubject <em>Subject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DefinitionTypeImpl extends EObjectImpl implements DefinitionType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected FromType from = null;

	/**
	 * The default value of the '{@link #getTo() <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected static final String TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected String to = TO_EDEFAULT;

	/**
	 * The default value of the '{@link #getCc() <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCc()
	 * @generated
	 * @ordered
	 */
	protected static final String CC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCc() <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCc()
	 * @generated
	 * @ordered
	 */
	protected String cc = CC_EDEFAULT;

	/**
	 * The default value of the '{@link #getBcc() <em>Bcc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBcc()
	 * @generated
	 * @ordered
	 */
	protected static final String BCC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBcc() <em>Bcc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBcc()
	 * @generated
	 * @ordered
	 */
	protected String bcc = BCC_EDEFAULT;

	/**
	 * The default value of the '{@link #getReplyTo() <em>Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplyTo()
	 * @generated
	 * @ordered
	 */
	protected static final String REPLY_TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReplyTo() <em>Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplyTo()
	 * @generated
	 * @ordered
	 */
	protected String replyTo = REPLY_TO_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
	protected String headers = HEADERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIORITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected String priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected String subject = SUBJECT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DefinitionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmailPackage.Literals.DEFINITION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FromType getFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFrom(FromType newFrom,
			NotificationChain msgs) {
		FromType oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.DEFINITION_TYPE__FROM,
					oldFrom, newFrom);
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
	public void setFrom(FromType newFrom) {
		if (newFrom != from) {
			NotificationChain msgs = null;
			if (from != null)
				msgs = ((InternalEObject) from).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.DEFINITION_TYPE__FROM, null,
						msgs);
			if (newFrom != null)
				msgs = ((InternalEObject) newFrom).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.DEFINITION_TYPE__FROM, null,
						msgs);
			msgs = basicSetFrom(newFrom, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__FROM, newFrom, newFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(String newTo) {
		String oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCc(String newCc) {
		String oldCc = cc;
		cc = newCc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__CC, oldCc, cc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBcc(String newBcc) {
		String oldBcc = bcc;
		bcc = newBcc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__BCC, oldBcc, bcc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReplyTo() {
		return replyTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplyTo(String newReplyTo) {
		String oldReplyTo = replyTo;
		replyTo = newReplyTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__REPLY_TO, oldReplyTo, replyTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaders() {
		return headers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaders(String newHeaders) {
		String oldHeaders = headers;
		headers = newHeaders;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__HEADERS, oldHeaders, headers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(String newPriority) {
		String oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__PRIORITY, oldPriority,
					priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(String newSubject) {
		String oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.DEFINITION_TYPE__SUBJECT, oldSubject, subject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EmailPackage.DEFINITION_TYPE__FROM:
			return basicSetFrom(null, msgs);
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
		case EmailPackage.DEFINITION_TYPE__FROM:
			return getFrom();
		case EmailPackage.DEFINITION_TYPE__TO:
			return getTo();
		case EmailPackage.DEFINITION_TYPE__CC:
			return getCc();
		case EmailPackage.DEFINITION_TYPE__BCC:
			return getBcc();
		case EmailPackage.DEFINITION_TYPE__REPLY_TO:
			return getReplyTo();
		case EmailPackage.DEFINITION_TYPE__HEADERS:
			return getHeaders();
		case EmailPackage.DEFINITION_TYPE__PRIORITY:
			return getPriority();
		case EmailPackage.DEFINITION_TYPE__SUBJECT:
			return getSubject();
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
		case EmailPackage.DEFINITION_TYPE__FROM:
			setFrom((FromType) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__TO:
			setTo((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__CC:
			setCc((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__BCC:
			setBcc((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__REPLY_TO:
			setReplyTo((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__HEADERS:
			setHeaders((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__PRIORITY:
			setPriority((String) newValue);
			return;
		case EmailPackage.DEFINITION_TYPE__SUBJECT:
			setSubject((String) newValue);
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
		case EmailPackage.DEFINITION_TYPE__FROM:
			setFrom((FromType) null);
			return;
		case EmailPackage.DEFINITION_TYPE__TO:
			setTo(TO_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__CC:
			setCc(CC_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__BCC:
			setBcc(BCC_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__REPLY_TO:
			setReplyTo(REPLY_TO_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__HEADERS:
			setHeaders(HEADERS_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__PRIORITY:
			setPriority(PRIORITY_EDEFAULT);
			return;
		case EmailPackage.DEFINITION_TYPE__SUBJECT:
			setSubject(SUBJECT_EDEFAULT);
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
		case EmailPackage.DEFINITION_TYPE__FROM:
			return from != null;
		case EmailPackage.DEFINITION_TYPE__TO:
			return TO_EDEFAULT == null ? to != null : !TO_EDEFAULT.equals(to);
		case EmailPackage.DEFINITION_TYPE__CC:
			return CC_EDEFAULT == null ? cc != null : !CC_EDEFAULT.equals(cc);
		case EmailPackage.DEFINITION_TYPE__BCC:
			return BCC_EDEFAULT == null ? bcc != null : !BCC_EDEFAULT
					.equals(bcc);
		case EmailPackage.DEFINITION_TYPE__REPLY_TO:
			return REPLY_TO_EDEFAULT == null ? replyTo != null
					: !REPLY_TO_EDEFAULT.equals(replyTo);
		case EmailPackage.DEFINITION_TYPE__HEADERS:
			return HEADERS_EDEFAULT == null ? headers != null
					: !HEADERS_EDEFAULT.equals(headers);
		case EmailPackage.DEFINITION_TYPE__PRIORITY:
			return PRIORITY_EDEFAULT == null ? priority != null
					: !PRIORITY_EDEFAULT.equals(priority);
		case EmailPackage.DEFINITION_TYPE__SUBJECT:
			return SUBJECT_EDEFAULT == null ? subject != null
					: !SUBJECT_EDEFAULT.equals(subject);
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
		result.append(" (to: "); //$NON-NLS-1$
		result.append(to);
		result.append(", cc: "); //$NON-NLS-1$
		result.append(cc);
		result.append(", bcc: "); //$NON-NLS-1$
		result.append(bcc);
		result.append(", replyTo: "); //$NON-NLS-1$
		result.append(replyTo);
		result.append(", headers: "); //$NON-NLS-1$
		result.append(headers);
		result.append(", priority: "); //$NON-NLS-1$
		result.append(priority);
		result.append(", subject: "); //$NON-NLS-1$
		result.append(subject);
		result.append(')');
		return result.toString();
	}

} //DefinitionTypeImpl