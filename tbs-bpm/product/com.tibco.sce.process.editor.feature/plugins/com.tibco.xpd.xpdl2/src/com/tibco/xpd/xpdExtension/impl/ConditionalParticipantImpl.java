/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Expression;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Participant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConditionalParticipantImpl#getPerformerScript <em>Performer Script</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalParticipantImpl extends EObjectImpl
        implements ConditionalParticipant {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getPerformerScript() <em>Performer Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPerformerScript()
     * @generated
     * @ordered
     */
    protected Expression performerScript;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConditionalParticipantImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CONDITIONAL_PARTICIPANT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getPerformerScript() {
        return performerScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPerformerScript(
            Expression newPerformerScript, NotificationChain msgs) {
        Expression oldPerformerScript = performerScript;
        performerScript = newPerformerScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT,
                    oldPerformerScript, newPerformerScript);
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
    public void setPerformerScript(Expression newPerformerScript) {
        if (newPerformerScript != performerScript) {
            NotificationChain msgs = null;
            if (performerScript != null)
                msgs = ((InternalEObject) performerScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT,
                        null,
                        msgs);
            if (newPerformerScript != null)
                msgs = ((InternalEObject) newPerformerScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetPerformerScript(newPerformerScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT,
                    newPerformerScript, newPerformerScript));
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
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT:
            return basicSetPerformerScript(null, msgs);
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
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT:
            return getPerformerScript();
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
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT:
            setPerformerScript((Expression) newValue);
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
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT:
            setPerformerScript((Expression) null);
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
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT:
            return performerScript != null;
        }
        return super.eIsSet(featureID);
    }

} //ConditionalParticipantImpl
