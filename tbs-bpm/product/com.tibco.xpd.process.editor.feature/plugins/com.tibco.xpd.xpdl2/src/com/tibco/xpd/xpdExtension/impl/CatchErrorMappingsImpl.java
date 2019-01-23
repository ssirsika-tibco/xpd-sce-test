/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Message;
import org.eclipse.emf.common.notify.Notification;
import com.tibco.xpd.xpdl2.DataMapping;

import java.util.Collection;

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
 * An implementation of the model object '<em><b>Catch Error Mappings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CatchErrorMappingsImpl#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CatchErrorMappingsImpl extends EObjectImpl
        implements CatchErrorMappings {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMessage() <em>Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessage()
     * @generated
     * @ordered
     */
    protected Message message;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CatchErrorMappingsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CATCH_ERROR_MAPPINGS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message getMessage() {
        return message;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMessage(Message newMessage,
            NotificationChain msgs) {
        Message oldMessage = message;
        message = newMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE,
                            oldMessage, newMessage);
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
    public void setMessage(Message newMessage) {
        if (newMessage != message) {
            NotificationChain msgs = null;
            if (message != null)
                msgs = ((InternalEObject) message).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE,
                        null,
                        msgs);
            if (newMessage != null)
                msgs = ((InternalEObject) newMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE,
                        null,
                        msgs);
            msgs = basicSetMessage(newMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE,
                    newMessage, newMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE:
            return basicSetMessage(null, msgs);
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
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE:
            return getMessage();
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
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE:
            setMessage((Message) newValue);
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
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE:
            setMessage((Message) null);
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
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS__MESSAGE:
            return message != null;
        }
        return super.eIsSet(featureID);
    }

} //CatchErrorMappingsImpl
