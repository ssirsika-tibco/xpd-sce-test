/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.WebServiceFaultCatch;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Service Fault Catch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl#getBlockActivity <em>Block Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl#getTransitionRef <em>Transition Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl#getFaultName <em>Fault Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WebServiceFaultCatchImpl extends EObjectImpl implements WebServiceFaultCatch {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getBlockActivity() <em>Block Activity</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBlockActivity()
     * @generated
     * @ordered
     */
    protected BlockActivity blockActivity;

    /**
     * The cached value of the '{@link #getTransitionRef() <em>Transition Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransitionRef()
     * @generated
     * @ordered
     */
    protected TransitionRef transitionRef;

    /**
     * The default value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFaultName()
     * @generated
     * @ordered
     */
    protected static final String FAULT_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFaultName()
     * @generated
     * @ordered
     */
    protected String faultName = FAULT_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WebServiceFaultCatchImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.WEB_SERVICE_FAULT_CATCH;
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
    public NotificationChain basicSetMessage(Message newMessage, NotificationChain msgs) {
        Message oldMessage = message;
        message = newMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE, oldMessage, newMessage);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE,
                        null,
                        msgs);
            if (newMessage != null)
                msgs = ((InternalEObject) newMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE,
                        null,
                        msgs);
            msgs = basicSetMessage(newMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE,
                    newMessage, newMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BlockActivity getBlockActivity() {
        return blockActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBlockActivity(BlockActivity newBlockActivity, NotificationChain msgs) {
        BlockActivity oldBlockActivity = blockActivity;
        blockActivity = newBlockActivity;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY, oldBlockActivity, newBlockActivity);
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
    public void setBlockActivity(BlockActivity newBlockActivity) {
        if (newBlockActivity != blockActivity) {
            NotificationChain msgs = null;
            if (blockActivity != null)
                msgs = ((InternalEObject) blockActivity).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY,
                        null,
                        msgs);
            if (newBlockActivity != null)
                msgs = ((InternalEObject) newBlockActivity).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY,
                        null,
                        msgs);
            msgs = basicSetBlockActivity(newBlockActivity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY,
                    newBlockActivity, newBlockActivity));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitionRef getTransitionRef() {
        return transitionRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTransitionRef(TransitionRef newTransitionRef, NotificationChain msgs) {
        TransitionRef oldTransitionRef = transitionRef;
        transitionRef = newTransitionRef;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF, oldTransitionRef, newTransitionRef);
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
    public void setTransitionRef(TransitionRef newTransitionRef) {
        if (newTransitionRef != transitionRef) {
            NotificationChain msgs = null;
            if (transitionRef != null)
                msgs = ((InternalEObject) transitionRef).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF,
                        null,
                        msgs);
            if (newTransitionRef != null)
                msgs = ((InternalEObject) newTransitionRef).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF,
                        null,
                        msgs);
            msgs = basicSetTransitionRef(newTransitionRef, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF,
                    newTransitionRef, newTransitionRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFaultName() {
        return faultName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFaultName(String newFaultName) {
        String oldFaultName = faultName;
        faultName = newFaultName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_FAULT_CATCH__FAULT_NAME,
                    oldFaultName, faultName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE:
            return basicSetMessage(null, msgs);
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY:
            return basicSetBlockActivity(null, msgs);
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF:
            return basicSetTransitionRef(null, msgs);
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
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE:
            return getMessage();
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY:
            return getBlockActivity();
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF:
            return getTransitionRef();
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__FAULT_NAME:
            return getFaultName();
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
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE:
            setMessage((Message) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY:
            setBlockActivity((BlockActivity) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF:
            setTransitionRef((TransitionRef) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__FAULT_NAME:
            setFaultName((String) newValue);
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
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE:
            setMessage((Message) null);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY:
            setBlockActivity((BlockActivity) null);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF:
            setTransitionRef((TransitionRef) null);
            return;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__FAULT_NAME:
            setFaultName(FAULT_NAME_EDEFAULT);
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
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__MESSAGE:
            return message != null;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY:
            return blockActivity != null;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__TRANSITION_REF:
            return transitionRef != null;
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH__FAULT_NAME:
            return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (faultName: "); //$NON-NLS-1$
        result.append(faultName);
        result.append(')');
        return result.toString();
    }

} //WebServiceFaultCatchImpl
