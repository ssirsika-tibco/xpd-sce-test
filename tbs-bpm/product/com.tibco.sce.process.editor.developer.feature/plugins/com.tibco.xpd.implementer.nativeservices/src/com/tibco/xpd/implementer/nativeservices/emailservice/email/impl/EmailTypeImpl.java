/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl#getBody <em>Body</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl#getAttachment <em>Attachment</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl#getSMTP <em>SMTP</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EmailTypeImpl extends EObjectImpl implements EmailType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected DefinitionType definition = null;

	/**
	 * The default value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected static final String BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected String body = BODY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttachment() <em>Attachment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttachment()
	 * @generated
	 * @ordered
	 */
	protected AttachmentType attachment = null;

	/**
	 * The cached value of the '{@link #getSMTP() <em>SMTP</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSMTP()
	 * @generated
	 * @ordered
	 */
	protected SMTPType sMTP = null;

	/**
	 * The cached value of the '{@link #getError() <em>Error</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getError()
	 * @generated
	 * @ordered
	 */
	protected ErrorType error = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmailTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmailPackage.Literals.EMAIL_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionType getDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinition(DefinitionType newDefinition,
			NotificationChain msgs) {
		DefinitionType oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.EMAIL_TYPE__DEFINITION,
					oldDefinition, newDefinition);
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
	public void setDefinition(DefinitionType newDefinition) {
		if (newDefinition != definition) {
			NotificationChain msgs = null;
			if (definition != null)
				msgs = ((InternalEObject) definition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__DEFINITION, null,
						msgs);
			if (newDefinition != null)
				msgs = ((InternalEObject) newDefinition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__DEFINITION, null,
						msgs);
			msgs = basicSetDefinition(newDefinition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.EMAIL_TYPE__DEFINITION, newDefinition,
					newDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(String newBody) {
		String oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.EMAIL_TYPE__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttachmentType getAttachment() {
		return attachment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttachment(AttachmentType newAttachment,
			NotificationChain msgs) {
		AttachmentType oldAttachment = attachment;
		attachment = newAttachment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.EMAIL_TYPE__ATTACHMENT,
					oldAttachment, newAttachment);
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
	public void setAttachment(AttachmentType newAttachment) {
		if (newAttachment != attachment) {
			NotificationChain msgs = null;
			if (attachment != null)
				msgs = ((InternalEObject) attachment).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__ATTACHMENT, null,
						msgs);
			if (newAttachment != null)
				msgs = ((InternalEObject) newAttachment).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__ATTACHMENT, null,
						msgs);
			msgs = basicSetAttachment(newAttachment, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.EMAIL_TYPE__ATTACHMENT, newAttachment,
					newAttachment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SMTPType getSMTP() {
		return sMTP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSMTP(SMTPType newSMTP,
			NotificationChain msgs) {
		SMTPType oldSMTP = sMTP;
		sMTP = newSMTP;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.EMAIL_TYPE__SMTP, oldSMTP,
					newSMTP);
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
	public void setSMTP(SMTPType newSMTP) {
		if (newSMTP != sMTP) {
			NotificationChain msgs = null;
			if (sMTP != null)
				msgs = ((InternalEObject) sMTP).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EmailPackage.EMAIL_TYPE__SMTP,
						null, msgs);
			if (newSMTP != null)
				msgs = ((InternalEObject) newSMTP).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EmailPackage.EMAIL_TYPE__SMTP,
						null, msgs);
			msgs = basicSetSMTP(newSMTP, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.EMAIL_TYPE__SMTP, newSMTP, newSMTP));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorType getError() {
		return error;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetError(ErrorType newError,
			NotificationChain msgs) {
		ErrorType oldError = error;
		error = newError;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, EmailPackage.EMAIL_TYPE__ERROR, oldError,
					newError);
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
	public void setError(ErrorType newError) {
		if (newError != error) {
			NotificationChain msgs = null;
			if (error != null)
				msgs = ((InternalEObject) error)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__ERROR, null, msgs);
			if (newError != null)
				msgs = ((InternalEObject) newError)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- EmailPackage.EMAIL_TYPE__ERROR, null, msgs);
			msgs = basicSetError(newError, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.EMAIL_TYPE__ERROR, newError, newError));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EmailPackage.EMAIL_TYPE__DEFINITION:
			return basicSetDefinition(null, msgs);
		case EmailPackage.EMAIL_TYPE__ATTACHMENT:
			return basicSetAttachment(null, msgs);
		case EmailPackage.EMAIL_TYPE__SMTP:
			return basicSetSMTP(null, msgs);
		case EmailPackage.EMAIL_TYPE__ERROR:
			return basicSetError(null, msgs);
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
		case EmailPackage.EMAIL_TYPE__DEFINITION:
			return getDefinition();
		case EmailPackage.EMAIL_TYPE__BODY:
			return getBody();
		case EmailPackage.EMAIL_TYPE__ATTACHMENT:
			return getAttachment();
		case EmailPackage.EMAIL_TYPE__SMTP:
			return getSMTP();
		case EmailPackage.EMAIL_TYPE__ERROR:
			return getError();
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
		case EmailPackage.EMAIL_TYPE__DEFINITION:
			setDefinition((DefinitionType) newValue);
			return;
		case EmailPackage.EMAIL_TYPE__BODY:
			setBody((String) newValue);
			return;
		case EmailPackage.EMAIL_TYPE__ATTACHMENT:
			setAttachment((AttachmentType) newValue);
			return;
		case EmailPackage.EMAIL_TYPE__SMTP:
			setSMTP((SMTPType) newValue);
			return;
		case EmailPackage.EMAIL_TYPE__ERROR:
			setError((ErrorType) newValue);
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
		case EmailPackage.EMAIL_TYPE__DEFINITION:
			setDefinition((DefinitionType) null);
			return;
		case EmailPackage.EMAIL_TYPE__BODY:
			setBody(BODY_EDEFAULT);
			return;
		case EmailPackage.EMAIL_TYPE__ATTACHMENT:
			setAttachment((AttachmentType) null);
			return;
		case EmailPackage.EMAIL_TYPE__SMTP:
			setSMTP((SMTPType) null);
			return;
		case EmailPackage.EMAIL_TYPE__ERROR:
			setError((ErrorType) null);
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
		case EmailPackage.EMAIL_TYPE__DEFINITION:
			return definition != null;
		case EmailPackage.EMAIL_TYPE__BODY:
			return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT
					.equals(body);
		case EmailPackage.EMAIL_TYPE__ATTACHMENT:
			return attachment != null;
		case EmailPackage.EMAIL_TYPE__SMTP:
			return sMTP != null;
		case EmailPackage.EMAIL_TYPE__ERROR:
			return error != null;
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
		result.append(" (body: "); //$NON-NLS-1$
		result.append(body);
		result.append(')');
		return result.toString();
	}

} //EmailTypeImpl