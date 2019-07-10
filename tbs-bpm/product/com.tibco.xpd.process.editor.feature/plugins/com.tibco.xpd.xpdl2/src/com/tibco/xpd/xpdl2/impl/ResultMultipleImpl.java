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

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Result Multiple</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl#getResultCompensation <em>Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl#getResultError <em>Result Error</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResultMultipleImpl extends EObjectImpl implements ResultMultiple {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The cached value of the '{@link #getTriggerResultMessage() <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultMessage()
     * @generated
     * @ordered
     */
    protected TriggerResultMessage triggerResultMessage;

    /**
     * The cached value of the '{@link #getTriggerResultLink() <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultLink()
     * @generated
     * @ordered
     */
    protected TriggerResultLink triggerResultLink;

    /**
     * The cached value of the '{@link #getResultCompensation() <em>Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResultCompensation()
     * @generated
     * @ordered
     */
    protected TriggerResultCompensation resultCompensation;

    /**
     * The cached value of the '{@link #getResultError() <em>Result Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResultError()
     * @generated
     * @ordered
     */
    protected ResultError resultError;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResultMultipleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.RESULT_MULTIPLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultMessage getTriggerResultMessage() {
        return triggerResultMessage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultMessage(TriggerResultMessage newTriggerResultMessage,
            NotificationChain msgs) {
        TriggerResultMessage oldTriggerResultMessage = triggerResultMessage;
        triggerResultMessage = newTriggerResultMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                            oldTriggerResultMessage, newTriggerResultMessage);
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
    public void setTriggerResultMessage(TriggerResultMessage newTriggerResultMessage) {
        if (newTriggerResultMessage != triggerResultMessage) {
            NotificationChain msgs = null;
            if (triggerResultMessage != null)
                msgs = ((InternalEObject) triggerResultMessage).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            if (newTriggerResultMessage != null)
                msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                    newTriggerResultMessage, newTriggerResultMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink getTriggerResultLink() {
        return triggerResultLink;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultLink(TriggerResultLink newTriggerResultLink, NotificationChain msgs) {
        TriggerResultLink oldTriggerResultLink = triggerResultLink;
        triggerResultLink = newTriggerResultLink;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK, oldTriggerResultLink, newTriggerResultLink);
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
    public void setTriggerResultLink(TriggerResultLink newTriggerResultLink) {
        if (newTriggerResultLink != triggerResultLink) {
            NotificationChain msgs = null;
            if (triggerResultLink != null)
                msgs = ((InternalEObject) triggerResultLink).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            if (newTriggerResultLink != null)
                msgs = ((InternalEObject) newTriggerResultLink).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            msgs = basicSetTriggerResultLink(newTriggerResultLink, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK,
                    newTriggerResultLink, newTriggerResultLink));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCompensation getResultCompensation() {
        return resultCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResultCompensation(TriggerResultCompensation newResultCompensation,
            NotificationChain msgs) {
        TriggerResultCompensation oldResultCompensation = resultCompensation;
        resultCompensation = newResultCompensation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION, oldResultCompensation, newResultCompensation);
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
    public void setResultCompensation(TriggerResultCompensation newResultCompensation) {
        if (newResultCompensation != resultCompensation) {
            NotificationChain msgs = null;
            if (resultCompensation != null)
                msgs = ((InternalEObject) resultCompensation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newResultCompensation != null)
                msgs = ((InternalEObject) newResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetResultCompensation(newResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION,
                    newResultCompensation, newResultCompensation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResultError getResultError() {
        return resultError;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResultError(ResultError newResultError, NotificationChain msgs) {
        ResultError oldResultError = resultError;
        resultError = newResultError;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR, oldResultError, newResultError);
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
    public void setResultError(ResultError newResultError) {
        if (newResultError != resultError) {
            NotificationChain msgs = null;
            if (resultError != null)
                msgs = ((InternalEObject) resultError).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR,
                        null,
                        msgs);
            if (newResultError != null)
                msgs = ((InternalEObject) newResultError).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR,
                        null,
                        msgs);
            msgs = basicSetResultError(newResultError, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR,
                    newResultError, newResultError));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return basicSetTriggerResultMessage(null, msgs);
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK:
            return basicSetTriggerResultLink(null, msgs);
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION:
            return basicSetResultCompensation(null, msgs);
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR:
            return basicSetResultError(null, msgs);
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
        case Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return getTriggerResultMessage();
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK:
            return getTriggerResultLink();
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION:
            return getResultCompensation();
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR:
            return getResultError();
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
        case Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) newValue);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) newValue);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION:
            setResultCompensation((TriggerResultCompensation) newValue);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR:
            setResultError((ResultError) newValue);
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
        case Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) null);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) null);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION:
            setResultCompensation((TriggerResultCompensation) null);
            return;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR:
            setResultError((ResultError) null);
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
        case Xpdl2Package.RESULT_MULTIPLE__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return triggerResultMessage != null;
        case Xpdl2Package.RESULT_MULTIPLE__TRIGGER_RESULT_LINK:
            return triggerResultLink != null;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_COMPENSATION:
            return resultCompensation != null;
        case Xpdl2Package.RESULT_MULTIPLE__RESULT_ERROR:
            return resultError != null;
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(')');
        return result.toString();
    }

} //ResultMultipleImpl
